package com.madpixels.apphelpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.BuildConfig;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ImageCache {
    private final HashMap<String, Drawable> mDrawablesCache = new HashMap<>(25); //кеш картинок
    private final HashMap<String, int[]> mThumbSize = new HashMap<>(25); //кеш размеров
    private final HashSet<String> mLoadingQuie = new HashSet<>(25); //список сейчас загружаемых изображений
    private final ArrayList<String> mCacheOrder = new ArrayList<>();
    private final HashSet<String> mOriginalQualityList = new HashSet<>();
    private final HashSet<String> mErrorImages = new HashSet<>(0);
    private static final String lockDownload = "1", lockOrder = "2", lockRead = "3";
    private Handler handler;
    private final Context mContext;
    private String mCustomPath = null;
    private boolean filename_as_url_hashset = false;
    private boolean useCustomExt = false;
    private String mCustomExt = null;
    private boolean lockDWL = true;
    private boolean useAnimation = true, isSetTagUrlToView = false;

    private boolean isDestroy = false;
    private static String mImagesDir = null;

    public boolean skipDownloadButRead = false, ignoreDownload = false;
    Runnable mNotificationCallback;

    private int cache_count_limit = 30;
    private boolean useThumbs = false;
    private boolean forceIgnoreThumbs = false;
    private int refresh_delay_msec = 0;

    private boolean isRefreshing = false;
    private boolean refresh_on_read_from_cache = false;
    private boolean force_refresh_each_image = false;
    private boolean ignoreDownloadViaMobile = false;
    private boolean isMakeRounded = false;
    private boolean ignoreUpdate = false;
    boolean isReadFromCacheDirectly = false;
    private boolean isNotifyOnErrors = false;

    public ImageCache(Context c) {
        mContext = c;
        createHandler();
        init(null);
    }


    /**
     * Если опция установлена то изображение загружается сразу, без потока, игнорируются флаги isRounded, resize.
     */
    public ImageCache setReadFromCacheDirectly(boolean b) {
        isReadFromCacheDirectly = b;
        return this;
    }


    private void createHandler() {
        if (Looper.myLooper() == Looper.getMainLooper())
            handler = new Handler();
    }

    /**
     * @param mNotifyCallbackRunnable - коллбэк который будет вызван когда изображение загружено
     */
    public ImageCache(Context mCtx, Runnable mNotifyCallbackRunnable) {
        mContext = mCtx;
        createHandler();
        init(mNotifyCallbackRunnable);
    }

    /**
     * @param h - хэндлер который будет вызывать коллбэк в ЮИ потоке, pass @null if you dont need to run callback's
     */
    public ImageCache(@Nullable Handler h, Context c) {
        mContext = c;
        // handler
        init(null);
    }

    /**
     * @param adapterToNotifyWhenImageLoaded - адаптер который будет обновлен когда изображение загружено
     */
    public ImageCache(Context mCtx, final BaseAdapter adapterToNotifyWhenImageLoaded) {
        mContext = mCtx;
        handler = new Handler();
        init(null);
        withAdapterToRefresh(adapterToNotifyWhenImageLoaded);
    }

    public ImageCache setCallback(final Runnable callback) {
        mNotificationCallback = callback;
        return this;
    }

    private void init(@Nullable Runnable mRunnable) {
        mNotificationCallback = mRunnable;
        if (mImagesDir == null)
            mImagesDir = getImageCacheDir(mContext);
    }


    public ImageCache useThumbs(final boolean use) {
        useThumbs = use;
        return this;
    }


    public ImageCache setUseAnimation(boolean useAnimation) {
        this.useAnimation = useAnimation;
        return this;
    }

    /**
     * при прямой устарновке картинки в виев #setImageOrLoad, ему будет присвоен .setTag(url);
     */
    public ImageCache setTagUrlToViews(boolean isSet) {
        this.isSetTagUrlToView = isSet;
        return this;
    }

    /**
     * по умолчанию, если @useThumbs is false, то изображения показываются в sampleSize=2,
     * метод @useOriginalImages нужен чтобы показывать в оригинальном качестве,игнорируя sampleSize=2
     */
    public ImageCache useOriginalImages(boolean b) {
        forceIgnoreThumbs = b;
        return this;
    }

    /**
     * Сохранять изображение в кеш в виде md5 имени ссылки изображения
     * Нужно в том случае, если у всех скачиваемых изображений одинаокое имя файла, чтобы они не путались в кеше.
     * (Сохраняется только первые 8 символов md5 +.ext)
     *
     * @return
     */
    public ImageCache setSaveImagesAsUrlHashSet(boolean b) {
        filename_as_url_hashset = b;
        return this;
    }


    /**
     * Пользователькое расширение для имени файла, только для загрузки по хешу.
     *
     * @param ext расшриение, без точки, например <i>png</i>
     * @return
     */
    public ImageCache setCustomExt(final String ext) {
        useCustomExt = true;
        mCustomExt = ext;
        return this;
    }

    /**
     * not implemented
     *
     * @param lock enable or disable 1 download at time
     */
    public ImageCache withLockDownload(boolean lock) {
        lockDWL = lock;
        return this;
    }

    /**
     * @param count кол-во изображений в оперативной памяти. по умолчанию 30. 0 -игнорировать лимит
     */
    public ImageCache setCacheLimit(final int count) {
        cache_count_limit = count;
        return this;
    }

    /**
     * игнорирует загрузку но загружает из кеша.
     */
    public ImageCache setIgnoreDownloads(boolean ignore) {
        ignoreDownload = ignore;
        return this;
    }

    public ImageCache setIgnoreUpdates(boolean ignore) {
        ignoreUpdate = ignore;
        return this;
    }

    /**
     * Обновляеть адаптер если изображение не скачалось с ошибкой
     */
    public ImageCache setNotifyOnErrors(boolean b) {
        isNotifyOnErrors = b;
        return this;
    }

    /**
     * игнорирует загрузку если мобильная сеть, но скачивает если вайфай или из кеша.
     */
    public ImageCache setIgnoreDownloadViaMobile(boolean ignore) {
        ignoreDownloadViaMobile = ignore;
        return this;
    }


    public ImageCache setRefreshDelay(final int msec) {
        refresh_delay_msec = msec;
        return this;
    }

    /**
     * Обновить сразу, если загружено из кеша
     */
    public ImageCache setReadFromCacheWhenIgnored(boolean allow) {
        refresh_on_read_from_cache = allow;
        return this;
    }

    /**
     * устанавливать изображения в виевы сразу после загрузки
     */
    public ImageCache setRefreshEachImageAfterLoad(boolean refresh) {
        force_refresh_each_image = refresh;
        return this;
    }

    /**
     * кастомный путь для хранения. если путь не существует, изображения не будут загружаться
     */
    public ImageCache withCustomPath(String s) {
        if (!s.endsWith("/"))
            s += "/";
        mCustomPath = s;
        return this;
    }

    /**
     * Кастомная папка для кеша, полный путь будет {#getCacheDir()/#dir}
     */
    public ImageCache setSubDirectory(String dir) {
        String cachedir = getCacheDir(mContext);
        File fDir = new File(cachedir, dir);
        fDir.mkdirs();
        withCustomPath(fDir.getAbsolutePath());
        return this;
    }

    /**
     * Load image direct to view with other filename instead of url file end name.
     *
     * @param url
     * @param iv
     * @param default_image
     * @param customFlename
     */
    public void loadImageToView(final String url, final ImageView iv, int default_image, final String customFlename) {
        loadImageToView(url, iv, default_image, customFlename, false);
    }

    public void loadImageToView(final String url, final ImageView iv, int default_image, final String customFlename, boolean isRounded) {
        final Runnable onLoadComplete = new Runnable() {
            @Override
            public void run() {
                Drawable d = mDrawablesCache.get(url);
                if (d != null)
                    iv.setImageDrawable(d);
            }
        };

        Drawable d = mDrawablesCache.get(url);
        if (d != null) { // Изображение есть.
            if (Looper.myLooper() == Looper.getMainLooper())
                iv.setImageDrawable(d);
            else
                refreshAdapter(onLoadComplete);
        } else { // Ставим на загрузку, указав коллбэк
            mLoadingQuie.add(url);
            DownloadImageRunnable dwl = new DownloadImageRunnable(url);
            if (customFlename != null && !customFlename.isEmpty())
                dwl.setFilename(customFlename);
            if (isRounded)
                dwl.setRounded(isRounded);
            dwl.setSingleCallback(onLoadComplete);
            new Thread(dwl).start();
            if (default_image != 0)
                if (Looper.myLooper() == Looper.getMainLooper())
                    iv.setImageResource(default_image);
        }
    }

    /**
     * @default_image res_id or 0
     * run only on UI thread
     */
    public void loadImageToView(final String url, final ImageView iv, int default_image) {
        loadImageToView(url, iv, default_image, null);
    }

    /**
     * @default_image res_id or 0
     * run only on UI thread
     */
    public void loadWithCallback(final String url, final Runnable callback) {
        Drawable d = mDrawablesCache.get(url);
        if (d != null) { // Изображение есть.
            if (Looper.myLooper() == Looper.getMainLooper())
                callback.run();
            else
                refreshAdapter(callback);
        } else { // Ставим на загрузку, указав коллбэк
            mLoadingQuie.add(url);
            DownloadImageRunnable dwl = new DownloadImageRunnable(url);
            dwl.setSingleCallback(callback);
            new Thread(dwl).start();
        }
    }

    public Handler getHandler() {
        return handler;
    }

    /**
     * Оставить оригинальное кач-во картинки, не применять {@link #getImgQualBySize} в методе {@link #readImageFile}
     *
     * @param url
     */
    public void setKeepQuality(String url) {
        mOriginalQualityList.add(url);
    }

    public static abstract class DataCallback {
        public abstract void onCallback(Drawable d, Object userData);
    }

    /**
     * load drawable and return userdata and drawable in callback. Callback will be called in UI
     * @param userData Will be returned after drawable loaded.
     * @param callback Will be called in UI.
     */
    public void loadWithDataCallback(final String url, final Object userData, final DataCallback callback) {
        Drawable d = mDrawablesCache.get(url);
        if (d != null) { // Изображение есть.
            if (Looper.myLooper() == Looper.getMainLooper())
                callback.onCallback(d, userData);
            else
                refreshDataAdapter(callback, d, userData);
        } else { // Ставим на загрузку, указав коллбэк
            mLoadingQuie.add(url);
            DownloadImageRunnable dwl = new DownloadImageRunnable(url);
            dwl.setRounded(isMakeRounded);
            dwl.setDataCallback(callback, userData);
            new Thread(dwl).start();
        }
    }

    public boolean isLoading(String id) {
        return mLoadingQuie.contains(id);
    }

    public void setLoadingID(String id) {
        mLoadingQuie.add(id);
    }

    /**
     * Возвращает битмап или null, а так же сставит изображение в очередь на загрузку.
     *
     * @return bitmap if exists or null.
     */
    public Drawable getDrawable(final String url, final String name, boolean isRounded) {
        if (mDrawablesCache.containsKey(url))
            return mDrawablesCache.get(url);
        // Если фотка уже загружается или загрузка запрещена вернем null
        if (mLoadingQuie.contains(url)) {
            return null;
        }
        if (skipDownloadButRead && !refresh_on_read_from_cache) {
            return null;
        } else { // Иначе ставим на загрузку.
            if (isReadFromCacheDirectly) {
                String file = filename_as_url_hashset ? getUrlHashAsFileName(url) : new File(url).getName();
                final File filePhoto = new File(getCacheDirectory(), file);
                if (filePhoto.exists()) {
                    Drawable d = Drawable.createFromPath(filePhoto.getAbsolutePath());
                    mDrawablesCache.put(url, d);
                    return d;
                }
            }

            mLoadingQuie.add(url);
            DownloadImageRunnable r = new DownloadImageRunnable(url);
            Thread t = new Thread(r);
            if (name != null)
                r.setFilename(name);
            r.setRounded(isRounded);

            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        }
        return null;
    }

    /**
     * store by direct filename instead of url
     */
    public Drawable getDrawable(final String url, final boolean isRounded) {
        return getDrawable(url, null, isRounded);
    }

    public Drawable getDrawable(final String url, final String name) {
        return getDrawable(url, name, isMakeRounded);
    }

    public Drawable getDrawable(final String url) {
        return getDrawable(url, null, isMakeRounded);
    }

    public boolean hasImage(final String url) {
        return mDrawablesCache.containsKey(url);
    }

    /**
     * see description of @setImageOrLoadWithSize();
     */
    public void setImageOrLoad(final ImageView v, final String url, final int defaultRes, boolean isRounded) {
        setImageOrLoadBase(v, url, defaultRes, 0, 0, isRounded);
    }

    /**
     * see description of @setImageOrLoadWithSize();
     */
    public void setImageOrLoad(final ImageView v, final String url, final int defaultRes) {
        setImageOrLoadWithSize(v, url, defaultRes, 0, 0);
    }

    HashSet<String> appliedAnimations = new HashSet<>();

    public void setImageOrLoadWithSize(final ImageView v, final String url, final int defaultRes, int w, int h) {
        setImageOrLoadBase(v, url, defaultRes, w, h, isMakeRounded);
    }

    /**
     * call this method on UI thread
     */
    private void setImageOrLoadBase(final ImageView v, final String url, final int defaultRes, int w, int h, boolean isRounded) {
        if (hasImage(url)) {
            final Drawable d = getDrawable(url);
            v.setImageDrawable(d);
            if (isSetTagUrlToView)
                v.setTag(url);

            if (!useAnimation || v.getAnimation() != null) return;
            if (appliedAnimations.contains(url)) return;
            appliedAnimations.add(url);
            Animation a = new AlphaAnimation(0.5f, 1.00f);
            a.setDuration(250);
            v.startAnimation(a);
        } else {
            if (defaultRes != 0)
                v.setImageResource(defaultRes);
            else
                v.setImageBitmap(null);
            if (url.isEmpty())
                return;
            if (w > 0) {
                mThumbSize.put(url, new int[]{w, h});
            }
            if (!force_refresh_each_image)
                getDrawable(url, isRounded);
            else
                loadImageToView(url, v, defaultRes, null, isRounded);
        }
    }

    public boolean isErrorImage(final String url) {
        return mErrorImages.contains(url);
    }

    public void onPause() {
        freeMem();
    }


    private String getCacheDirectory() {
        if (mCustomPath != null)
            return mCustomPath;
        else
            return mImagesDir;
    }

    /**
     * List Adatper который будет вызван .notifyDataSetChanged
     *
     * @param adapterToRefresh
     * @return
     */
    public ImageCache withAdapterToRefresh(final BaseAdapter adapterToRefresh) {
        final Runnable refreshRunnable = new Runnable() {
            @Override
            public void run() {
                adapterToRefresh.notifyDataSetChanged();
            }
        };
        mNotificationCallback = refreshRunnable;
        return this;
    }

    public Drawable downloadImage(String url) {
        if (hasImage(url))
            return getDrawable(url);
        DownloadImageRunnable dwl = new DownloadImageRunnable(url);
        dwl.run();
        if (hasImage(url))
            return getDrawable(url);
        return null;
    }

    /**
     *
     * @param filename имя файла из папки {@link #mCustomPath}
     * @return
     */
    public Drawable getImageFromFile(final String filename) {
        if (hasImage(filename))
            return getDrawable(filename);

        if (mLoadingQuie.contains(filename))
            return null;

        if(ignoreDownload)
            return null;

        mLoadingQuie.add(filename);
        new Thread() {
            @Override
            public void run() {
                Bitmap image = loadFromFile(new File(getCacheDirectory(), filename), null);

                if (image == null) {
                    mErrorImages.add(filename);
                    /* список загрузки очищать не надо чтоб не появилось "вечной загрузки" */
                    return;
                }
                Drawable drawable;
                try {
                    drawable = new BitmapDrawable(mContext.getResources(), image);
                } catch (Exception e) {
                    //throw new RuntimeException(e.getMessage());
                    return;
                }

                mDrawablesCache.put(filename, drawable);
                addToOrder(filename);
                mLoadingQuie.remove(filename);

                // Update UI
                refreshAdapter();
            }
        }.start();

        return null;
    }

    private Bitmap loadFromFile(File filepath, @Nullable String image_url) {
        Bitmap bitmap = null;
        synchronized (lockRead) {
            try {
                if (useThumbs == false && image_url != null && mThumbSize.containsKey(image_url)) // принудительно юзать тхумб
                    bitmap = readImageThumb(filepath, mThumbSize.get(image_url));
                else
                    bitmap = readImageFile(filepath, image_url);
                return bitmap;
            } catch (OutOfMemoryError e) {
                MyLog.log("Out of memory! " + image_url + " Try to freeMem");
                useThumbs = true;
                freeMem();
            }
        }


        return null;
    }

    public ImageCache setRounded(boolean isRounded) {
        isMakeRounded = isRounded;
        return this;
    }

    public void loadBatchWithCallback(final ArrayList<String> urls, final Runnable callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String url : urls)
                    if (!hasImage(url)) {
                        DownloadImageRunnable load = new DownloadImageRunnable(url);
                        // load.setRounded(isRounded);
                        load.run();
                    }
                callback.run();
            }
        }).start();
    }

    public ImageCache setSize(String url, int h, int w) {
        mThumbSize.put(url, new int[]{w, h});
        return this;
    }

    public void restore() {
        isDestroy = false;
    }


    private String getUrlHashAsFileName(final String filename) {
        String newName = Utils.getStringMD5(filename).substring(0, 10);
        if (useCustomExt) {
            newName += "." + mCustomExt;
        } else {
            if (!filename.contains("."))
                newName += ".img";
            else {
                String ext = filename.substring(filename.lastIndexOf("."));
                if (ext.length() <= 4)
                    newName += FileUtils2.setValidFilename(ext);
                else
                    newName += ".img";
            }
        }
        return newName;
    }

    // Даунлоадер класс
    class DownloadImageRunnable implements Runnable {
        private final String image_url;
        private String fileName = null;
        private Runnable mCompleteCallback;
        private boolean isRoundedImage = isMakeRounded;
        private DataCallback mDataCallback;
        private Object mDataForCallback;

        public DownloadImageRunnable(final String url) {
            image_url = url;
        }

        public void setSingleCallback(final Runnable r) {
            mCompleteCallback = r;
        }

        public void setDataCallback(DataCallback callback, Object userData) {
            mDataCallback = callback;
            mDataForCallback = userData;
        }

        public void setFilename(final String fname) {
            fileName = fname;
        }

        public void setRounded(boolean isRounded) {
            isRoundedImage = isRounded;
        }

        @Override
        public void run() {
            if (isDestroy || image_url == null || image_url.isEmpty()) return;
            if (fileName == null)/* если не null значит задан принудительно */
                fileName = new File(image_url).getName();
            if (fileName.isEmpty()) {
                //если фотки нет то просто return.
                return;
            }
            if (filename_as_url_hashset) {
                fileName = getUrlHashAsFileName(image_url);
            }


            //загружаем фото
            final File filePhoto = new File(getCacheDirectory(), fileName);
            Bitmap photo = null;
            //проверим фотку может уже сохранена в файле
            boolean photo_loaded = filePhoto.exists();
            // final boolean loaded_from_cache = photo_loaded;
            if (!photo_loaded) {//если фотки нет на сдкарте,то скачиваем её(если разрешено).
                if (skipDownloadButRead || ignoreDownload) { // если установлено читать из кеша то выходим, так как фотки в кеше нет. Так же выходим если
                    //if (ignoreDownload == true)
                    mLoadingQuie.remove(image_url);
                    return;
                }
                if (ignoreDownloadViaMobile) {
                    mLoadingQuie.remove(image_url);
                    //return;
                }
                File tmp = new File(filePhoto.getAbsoluteFile() + ".tmp");

                if (lockDWL)
                    synchronized (lockDownload) {
                        photo_loaded = downloadBitmapToFile(image_url, tmp);//вернет true если фотка скачалась
                    }
                else {
                    photo_loaded = downloadBitmapToFile(image_url, tmp);//вернет true если фотка скачалась
                }
                if (photo_loaded)
                    tmp.renameTo(filePhoto);
            }
            if (isDestroy) return;

            // если фотка скачалась
            if (photo_loaded) {
                photo = loadFromFile(filePhoto, image_url);

                if (photo == null) {
                    filePhoto.delete();// Если была ошибка то удалим битый файл и просто завершимся
                    mErrorImages.add(image_url);
                    /* список загрузки очищать не надо чтоб не появилось "вечной загрузки" */
                    if (isNotifyOnErrors) {
                        notifyCallback(null);
                    }
                    return;
                }
                Drawable drawable;
                try {
                    if (isRoundedImage)
                        photo = ImageUtils.getRounded(photo);

                    drawable = new BitmapDrawable(mContext.getResources(), photo); //photo.recycle() делать нельзя, крашится;

                } catch (Exception e) {
                    //ACRA.getErrorReporter().putCustomData("bad url", image_url);
                    //ACRA.getErrorReporter().handleSilentException(e);
                    if (BuildConfig.DEBUG)
                        throw new RuntimeException(e.getMessage());
                    return;
                }

                mDrawablesCache.put(image_url, drawable);
                addToOrder(image_url);
                mLoadingQuie.remove(image_url);

                // Update UI
                //if(refreshImmediatelyWhenFromCache && loaded_from_cache)
                //     refreshAdapter(mNotificationCallback);// Принудительно обновить
                //else
                notifyCallback(drawable);
            }
        }

        private void notifyCallback(@Nullable Drawable d) {
            if (mDataCallback != null)
                refreshDataAdapter(mDataCallback, d, mDataForCallback);
            else if (mCompleteCallback != null)
                refreshAdapter(mCompleteCallback);
            else
                refreshAdapter();
        }


    }// Даунлоадер енд.

    /**
     * @param size size[0]-width, size[1] - height of thumb image
     */
    private Bitmap readImageThumb(File filePhoto, int[] size) {
        final Bitmap b = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(filePhoto.getAbsolutePath()), size[0], size[1]);
        return b;
    }

    private Bitmap readImageFile(final File filePhoto, final String image_url) {
        if (!forceIgnoreThumbs) {
                /*
                * Логика выбора квалити: Если тхумбы не заданы то проверим размер, если <64 то 1 если > то 2
                */
            final int quality;
            if (useThumbs)
                quality = getImgQualBySize(filePhoto.length());
            else {
                final long kb = filePhoto.length() / 1024;
                if (image_url!=null && mOriginalQualityList.contains(image_url) && kb<=512)
                    quality = 1;
                else if (kb >= 100)
                    quality = 2;
                else
                    quality = 1;
            }


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = quality;
            options.inDither = true;
            options.inScaled = true;
            return BitmapFactory.decodeFile(filePhoto.getAbsolutePath(), options);
        } else
            return BitmapFactory.decodeFile(filePhoto.getAbsolutePath());
    }


    private static int getImgQualBySize(final long length) {
        //return value must be power of 2
        int kbytesize = (int) (length / 1024);
        int returnValue;
        if (kbytesize <= 50)
            returnValue = 1;
            //else
            //if(kbytesize<50)
            //	returnValue = 2;
        else if (kbytesize < 100)
            returnValue = 2;
        else if (kbytesize < 300)
            returnValue = 4;
        else if (kbytesize < 500)
            returnValue = 8;
        else
            returnValue = 16;
        return returnValue;
    }

    private void addToOrder(final String posterName) {
        synchronized (lockOrder) {
            mCacheOrder.add(posterName);
            if (cache_count_limit == 0 || mCacheOrder.size() < cache_count_limit) {
                return;
            }

            final ArrayList<String> toRemoveList = new ArrayList<>(cache_count_limit / 2);
            for (int i = 0; i < cache_count_limit / 2; i++)
                toRemoveList.add(mCacheOrder.get(i));
            for (final String id : toRemoveList) {
                //Drawable d =
                mDrawablesCache.remove(id);
                //if(b!=null) b.recycle();
                mCacheOrder.remove(id);
            }
        }
    }

    public void freeMem() {
        synchronized (lockOrder) {
            if (mDrawablesCache.size() < 5)
                return;
            final ArrayList<String> toRemoveList = new ArrayList<>(mDrawablesCache.size() - 5);
            for (int i = 0; i < mDrawablesCache.size() - 5; i++)
                toRemoveList.add(mCacheOrder.get(i));
            for (final String id : toRemoveList) {
                //Drawable d =
                mDrawablesCache.remove(id);
                //if(b!=null) b.recycle();
                mCacheOrder.remove(id);
                mOriginalQualityList.remove(id);
            }
            MyLog.log("Free mem cleared: " + toRemoveList.size() + " items");
        }
    }

    public void refreshAdapter() {
        if (isDestroy || mNotificationCallback == null || (skipDownloadButRead && !refresh_on_read_from_cache))
            return;

        if (refresh_delay_msec == 0)
            refreshAdapter(mNotificationCallback);
        else if (!isRefreshing) {
            isRefreshing = true;
            Utils.sleep(refresh_delay_msec + 2);
            handler.post(mNotificationCallback);
            isRefreshing = false;
        }
    }

    private void refreshAdapter(final Runnable mCompleteCallback) {
        if (!isDestroy && mCompleteCallback != null && !ignoreUpdate)
            ((Activity) mContext).runOnUiThread(mCompleteCallback);
    }

    private void refreshDataAdapter(final DataCallback mCompleteCallback, final Drawable d, final Object userData) {
        if (!isDestroy && mCompleteCallback != null && !ignoreUpdate)
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCompleteCallback.onCallback(d, userData);
                }
            });
    }

    // =============================================================================================
    // FILE UTILS
    // =============================================================================================
    private static String mCacheDir = "";

    /**
     * return available cache dir ends with /
     */
    public static String getCacheDir(final Context mContext) {
        if (mCacheDir.isEmpty()) {
            initCache(mContext);
        }
        return mCacheDir;
    }

    public static String getImageCacheDir(final Context mContext) {
        File result = new File(getCacheDir(mContext), "images");
        createNoMedia(result);
        return result.getAbsolutePath() + "/";
    }

    static void initCache(final Context mContext) {
        File result;
        if (mContext.getExternalCacheDir() != null && mContext.getExternalCacheDir().canWrite()) {
            result = mContext.getExternalCacheDir();
        } else {
            result = mContext.getCacheDir();
        }
        mCacheDir = result.getAbsolutePath() + File.separator;
        MyLog.log("Cache dir set to " + mCacheDir);
        createNoMedia(result);
    }

    private static void createNoMedia(final File folder) {
        if (!folder.exists())
            folder.mkdirs();
        if (folder.getAbsolutePath().startsWith("/data/data")) // no need in system dir
            return;
        final String NOMEDIA = ".nomedia";
        final File nomediaFile = new File(folder, NOMEDIA);
        if (!nomediaFile.exists()) { //если файла .nomdeia нет то создадим
            try {
                nomediaFile.createNewFile();
                MyLog.log("was created .nomedia file to " + nomediaFile.getAbsolutePath());
            } catch (IOException e) {
                //Log.d(Globs.TAG,"can't create nomedia file "+e.getMessage());
            }
        }
    }


    /**
     * @param url      прямая ссылка на картинку
     * @param saveFile путь куда сохранить картинку
     * @return true or false
     */
    public static boolean downloadBitmapToFile(final String url, final File saveFile) {

        long ts = System.currentTimeMillis();
        try {
            URL urlConn = new URL(url);
            InputStream in = urlConn.openStream();
            urlConn.openConnection();

            OutputStream output = new FileOutputStream(saveFile);

            int count;
            byte data[] = new byte[1024];
            //long len = 0;

            while ((count = in.read(data)) != -1) {
                output.write(data, 0, count);
                //len+=count;
            }
            output.flush();
            output.close();
            in.close();
            // MyLog.log("downloaded at: " + (System.currentTimeMillis() - ts));
            return true;
        } catch (Exception e) {
            saveFile.delete();
            MyLog.log("download image error " + e.getMessage());
            MyLog.log("error downloaded at: " + (System.currentTimeMillis() - ts));
        }
        return false;
    }

    public void putToQuie(String id, Drawable d) {
        mDrawablesCache.put(id, d);
        addToOrder(id);
    }

    static public Bitmap downloadBitmap(String url) {
        Bitmap returnVal = null;
        URL urlConn;
        try {
            urlConn = new URL(url);
            InputStream in = urlConn.openStream();
            returnVal = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return returnVal;
    }

    public void remove(final String url) {
        synchronized (lockOrder) {
            mErrorImages.remove(url);
            mLoadingQuie.remove(url);
            mDrawablesCache.remove(url);
            mDrawablesCache.remove(url);
        }
    }

    public void clear() {
        synchronized (lockOrder) {
            //for (Bitmap b : mDrawablesCache.values())
            //	b.recycle();
            mDrawablesCache.clear();
            mCacheOrder.clear();
            mErrorImages.clear();
            mLoadingQuie.clear();
            appliedAnimations.clear();
            mOriginalQualityList.clear();
        }
    }

    public void destroy() {
        isDestroy = true;
        clear();
    }


}
