package com.madpixels.apphelpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


/**
 * Created by Snake on 07.11.2015.
 */
public class FileUtils2 {

    //=================================================
    // Clear cache

    public static void clearCache(final Context c) {
        final String cache_dir = ImageCache.getCacheDir(c);
        long images_bytes, docs_bytes;
        int audios_count;
        if (cache_dir.startsWith("/data/data")) {
            images_bytes = 2 * 1024 * 1024; //2 метров
            docs_bytes = images_bytes * 2;// 10 метр
            audios_count = 5;
        } else {
            images_bytes = 5 * 1024 * 1024; //5 метров
            docs_bytes = images_bytes;// 25 метров
            audios_count = 15;
        }
        //String docs_dir  = cache_dir + Const.CACHE_DIR_DOCS;
        //String audio_dir = cache_dir + Const.CACHE_DIR_AUDIOS;
        String image_dir = ImageCache.getImageCacheDir(c);
        MyLog.log("Cache limit set to: imgs:" + images_bytes + "b, docs:" + docs_bytes + "b, audios:" + audios_count + "items");
        clearCacheByMaxSize(c, images_bytes, image_dir);
        //clearCacheByMaxSize(c, docs_bytes, docs_dir);
        //clearCacheByFilesCount(c, audios_count, audio_dir);
        // clearCacheByMaxSize(c, 5*1024*1024, getFilesDir(c));
    }

    /**
     * @return возвращает путь к папке files на карте памяти, или если её нет, то во внутренней памяти
     */
    public static String getFilesDir(Context mContext) {
        File result;
        if (mContext.getExternalFilesDir(null) != null && mContext.getExternalFilesDir(null).canWrite()) {
            result = mContext.getExternalFilesDir(null);
        } else {
            result = mContext.getFilesDir();
        }
        String mFileDir = result.getAbsolutePath() + File.separator;
        MyLog.log("Cache dir set to " + mFileDir);
        return mFileDir;
    }

    public static void clearDirectories(Activity a, String[] dirs, boolean keepRootFolder, ClearCallback clearCallback) {

    }

    public static String setValidFilename(final String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    public static class ClearDirectories {
        final ClearCallback mCallback;
        final boolean keepRootFolder;
        final String[] dirs;
        final Context c;
        private int position = 0;
        private int removedCount = 0;

        public ClearDirectories(Context c, String[] dirs, boolean keepRootFolder, ClearCallback clearCallback) {
            this.c = c;
            this.mCallback = clearCallback;
            this.dirs = dirs;
            this.keepRootFolder = keepRootFolder;
            clearNext();
        }

        void clearNext() {
            final String dir = dirs[position];

            clearFolder(c, dir, keepRootFolder, new ClearCallback() {
                @Override
                public void onCallback() {
                    removedCount += removedFilesCount;
                    position++;
                    if (position < dirs.length)
                        clearNext();
                    else {
                        mCallback.removedFilesCount = removedCount;
                        mCallback.onCallback();
                    }
                }
            });
        }
    }

    public static abstract class ClearCallback {
        public int removedFilesCount = 0;
        public long removedFilesSize = 0;

        public abstract void onCallback();
    }

    /**
     * get external(prefer) or internal cache dir.
     */
    public static String getCacheDir(Context c) {
        return ImageCache.getCacheDir(c);
    }

    /**
     * Clear whole folder with subdir's and self remove
     */
    public static void clearFolder(Context c, String folder) {
        clearFolder(c, folder, false);
    }

    public static void clearFolder(Context c, String folder, boolean keepRootDir) {
        clearFolder(c, folder, keepRootDir, null);
    }

    /**
     * @param pCallback run callback when done with msg.arg1 as removed files count
     */
    public static void clearFolder(Context c, String folder, boolean keepRootDir, ClearCallback pCallback) {
        ClearProcessor l = new ClearProcessorLogicAll();
        new ClearProcess(c, l, folder)
                .withKeepRootDir(keepRootDir)
                .withCallback(pCallback)
                .start();
    }

    //public enum ClearLogic {SIZE_LIMIT, COUNT_LIMIT, ALL}

    public static void clearCacheByMaxSize(final Context c, final long totalSizeBytes, final String folder) {
        ClearProcessor l = new ClearProcessorBySizeLimit(totalSizeBytes);
        new ClearProcess(c, l, folder).start();
    }

    public static void clearCacheByFilesCount(final Context c, final int maxCount, final String folder) {
        clearCacheByFilesCount(c, maxCount, folder, false, null);
    }

    public static void clearCacheByFilesCount(final Context c, final int maxCount, final String folder, boolean keepRootDir, ClearCallback pCallback) {
        ClearProcessor l = new ClearProcessorByCountLimit(maxCount);
        new ClearProcess(c, l, folder).withCallback(pCallback).withKeepRootDir(keepRootDir).start();
    }


    // this operation starts in background thread because files count can be more than thousand
    public static class ClearProcess {
        final Context c;
        //final ClearLogic l;
        final String folder;
        // final int pFilesCountLimit;
        // final long pFilesTotalSize;
        boolean keepRootDir = false; /* оставлять ли главную папку */
        ClearCallback mCallback;
        Handler.Callback inProcessCallback;
        private int total_files_removed = 0;
        final ClearProcessor pClearLogicProcessor;
        private final boolean isLogicDeleteAll;

        ClearProcess(final Context c, final ClearProcessor l, final String folder) {
            pClearLogicProcessor = l;
            this.c = c;
            this.folder = folder;
            isLogicDeleteAll = (pClearLogicProcessor instanceof ClearProcessorLogicAll);
        }

        public ClearProcess withKeepRootDir(boolean b) {
            keepRootDir = b;
            return this;
        }

        public ClearProcess withProcessCallback(Handler.Callback callback) {
            inProcessCallback = callback;
            return this;
        }

        private void logCallback(final String message) {

        }

        public ClearProcess withCallback(ClearCallback pCallback) {
            mCallback = pCallback;
            return this;
        }

        void start() {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        clear();
                    } catch (Exception e) {
                        MyLog.log(e);
                    }
                }
            };
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        }

        private void onCallback() {
            if (mCallback != null) {
                mCallback.removedFilesCount = total_files_removed;
                mCallback.onCallback();
            }
        }

        void clear() {
            File f = new File(folder);
            if (!f.exists()) {
                MyLog.log("Cache cleaning: skip, dir not exists " + folder);
                onCallback();
                return;
            } else
                scanRecursive(f, true);
            onCallback();
        }

        void scanRecursive(File dir, boolean rootDir) {
            ArrayList<File> inFiles = new ArrayList<>();
            File[] files = dir.listFiles();
            if (files == null) {
                return;
            }

            for (File file : files) { // составляем список файлов, если папка то запускаем процесс с ее файлами, а затем папку удаляем.
                if (file.isDirectory()) {
                    MyLog.log("scan folder: " + file.getName());
                    scanRecursive(file, false);
                    file.delete();
                } else {
                    inFiles.add(file);
                }
            }

            MyLog.log("Prepare clear files count:" + inFiles.size());
            File[] in = new File[inFiles.size()];
            in = inFiles.toArray(in);
            if (in.length > 1 && isLogicDeleteAll )// при удалении всех сортировка не имеет смысла
                Arrays.sort(in, mSortByModifiedDateComparator);

            pClearLogicProcessor.files = in;
            pClearLogicProcessor.dir = dir.getAbsolutePath();
            pClearLogicProcessor.doClear();
            total_files_removed += pClearLogicProcessor.getRemovedCount();

            if (rootDir && !keepRootDir)
                dir.delete();
        }
    }

    static abstract class ClearProcessor {
        File[] files;
        int removedCount = 0;
        public String dir;

        abstract void doClear();

        int getRemovedCount() {
            return removedCount;
        }
    }

    static class ClearProcessorBySizeLimit extends ClearProcessor {
        final long max_bytes;

        public ClearProcessorBySizeLimit(final long sizeLimitBytes) {
            max_bytes = sizeLimitBytes;
        }

        @Override
        void doClear() {
            long size = 0;

            for (File file : files) {
                size += file.length();
            }

            if (size < max_bytes) {
                MyLog.log("Cache cleaning: skip, byte size " + size + " of " + max_bytes + " in " + dir);
                return;
            }

            final long size10PercentReserved = max_bytes / 100 * 10;
            final long limit = max_bytes - size10PercentReserved;
            for (File file : files) {
                long fsize = file.length();
                file.delete();
                size -= fsize;
                removedCount++;
                if (size < limit) break;
            }
        }
    }

    static class ClearProcessorByCountLimit extends ClearProcessor {
        final int pFilesCountLimit;

        public ClearProcessorByCountLimit(final int pFilesCountLimit) {
            this.pFilesCountLimit = pFilesCountLimit;
        }

        @Override
        void doClear() {
            int count = files.length;
            if (count < pFilesCountLimit) {
                MyLog.log("Cache cleaning: skip, files " + count + " of " + pFilesCountLimit + " in " + dir);
                return;
            }

            final int count10PercentReserved = pFilesCountLimit / 100 * 10; // удаляем все файлы оставив лишь 10% файлов от заданного размера
            final int limit = pFilesCountLimit - count10PercentReserved;

            for (File file : files) {
                file.delete();
                count--;
                removedCount++;
                if (count == limit) break;
            }
        }
    }

    static class ClearProcessorLogicAll extends ClearProcessor {
        @Override
        void doClear() {
            for (File file : files) {
                file.delete();
                removedCount++;
            }
        }
    }

    /**
     * return files sorted by modified date
     */
    static File[] getFilesInDirSorted(final File dir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                // inFiles.addAll(getListFiles(file));
            } else {
                inFiles.add(file);
            }
        }
        File[] in = new File[inFiles.size()];
        in = inFiles.toArray(in);
        if (in.length > 1)
            Arrays.sort(in, mSortByModifiedDateComparator);

        return in;
    }

    static Comparator mSortByModifiedDateComparator = new Comparator() {
        public int compare(Object o1, Object o2) {
            if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                return 1;
            } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                return -1;
            } else {
                return 0;
            }
        }
    };
    // end Clear cache
    //=========================================================================


    public static void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static void saveImageToGallery(Context c, String filename) {
        File file = new File(filename);
        int result = saveImageToGallery(c, filename, file.getName());
        String toast = null;
        switch (result){
            case 1:
                toast = "Изображение сохранено в галерею";
                break;
            case 2:
                toast = "Изображение с именем '"+file.getName()+"' уже существует в галерее";
                break;
            case 3:
                toast="Ошибка при сохранении изображения!";
                break;
        }
        MyToast.toast(c, toast);
    }

    public static int saveImageToGallery(Context c, String filename, String saveFilename) {
        //MyToast toast = new MyToast(c);
        File file = new File(filename);
        File galleryDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        galleryDir.mkdirs();
        File fDst = new File(galleryDir, saveFilename);
        if (fDst.exists()) {
            //toast.fast("Изображение уже существует по пути\n" + fDst.getAbsolutePath());
            return 2;
        }
        try {
            copyFile(file, fDst);
            //toast.fast("Изображение сохранено в\n" + fDst.getAbsolutePath());
        } catch (Exception ex) {
           // toast.fast("Ошибка при сохранении изображения!\n" + ex.getMessage());
            MyLog.log(ex);
            return 3;
        }
        //сообщаем галерее что добавлено новое фото
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(fDst);
        mediaScanIntent.setData(contentUri);
        c.sendBroadcast(mediaScanIntent);
        return 1;
    }

    public static Comparator orderByNameComparator = new Comparator<File>() {
        @Override
        public int compare(File object1, File object2) {
            return object1.getName().compareTo(object2.getName());
        }
    };


    public static String getBaseName(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }

}
