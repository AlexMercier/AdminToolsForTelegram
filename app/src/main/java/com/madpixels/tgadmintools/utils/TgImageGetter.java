package com.madpixels.tgadmintools.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.madpixels.tgadmintools.helper.TgH;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.HashMap;

/**
 * Created by Snake on 23.02.2016.
 */
public class TgImageGetter {
    HashMap<Integer, Object> avatars = new HashMap<>();
    private Runnable onUpdateHandler;
    private boolean isRounded = false;
    Handler hand = new Handler();

    public TgImageGetter() {
        TgH.setUpdatesHandler(onFileDownloaded);
    }

    /*
    метод будет вызван в UI потоке
     */
    public TgImageGetter setUpdateCallback(final Runnable r) {
        onUpdateHandler = r;
        return this;
    }

    public void onDestroy() {
        TgH.removeUpdatesHandler(onFileDownloaded);
        avatars.clear();
    }

    /**
     * @return bitmap or null
     */
    public Bitmap getPhoto(int photo_id) {
        if (avatars.containsKey(photo_id)) {
            Object o = avatars.get(photo_id);
            if (o instanceof Bitmap)
                return (Bitmap) o;
            return null;
        }

        avatars.put(photo_id, true);
        TgH.send(new TdApi.DownloadFile(photo_id));
        return null;
    }

    //
    private final Client.ResultHandler onFileDownloaded = new Client.ResultHandler() {
        @Override
        public void onResult(TdApi.TLObject object) {
            if (object.getConstructor() == TdApi.UpdateFile.CONSTRUCTOR) {
                final TdApi.UpdateFile f = (TdApi.UpdateFile) object;
                // MyLog.log("File downloaded id: " + f.file.id);
                if (avatars.containsKey(f.file.id)) { // если мы ждём этот файл
                    boolean ok = readBitmap(f.file.id, f.file.path);
                    // MyLog.log("file updated: " + f.file.id+" "+ok);
                    // TODO onCallback.onResult(new TdApi.Ok());
                    invalidate();
                } else {
                    // MyLog.log("File id not in table: " + f.file.id);
                }
            }
        }
    };

    public void invalidate(){
        if (onUpdateHandler != null)
        hand.post(onUpdateHandler);
    }

    boolean readBitmap(int fileId, String path) {
        Bitmap bmp = BitmapFactory.decodeFile(path);
        if (bmp != null) {
            if(isRounded)
                bmp = getRounded(bmp);
            synchronized (this) {
                avatars.put(fileId, bmp);
                //orders.add(fileId);
                if (avatars.size() > 200) {
                    //freeMEM();
                }
            }
            // MyLog.log("bitmap readed to: " + fileId);
            return true;
        }
        return false;
    }

    public void setImageToView(final ImageButton btnAva, final TdApi.File photo) {
        setUpdateCallback(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = getPhoto(photo.id);
                if(bmp!=null)
                    btnAva.setImageBitmap(bmp);
                onDestroy();
            }
        });
        getPhoto(photo.id);
    }

    public static Bitmap getRounded(final Bitmap paramBitmap) {
        //if(Build.VERSION.SDK_INT<17)
        //   return getRoundedCornerImage(paramBitmap);// return not rounded
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        int k = Math.min(j / 2, i / 2);
        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        Canvas localCanvas = new Canvas(localBitmap);
        localCanvas.drawARGB(0, 0, 0, 0);
        localPaint.setStyle(Paint.Style.FILL);
        localCanvas.drawCircle(i / 2, j / 2, k, localPaint);
        localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);

        // circle
        localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setColor(Color.TRANSPARENT);
        localPaint.setStrokeWidth(0.0F);
        localCanvas.drawCircle(i / 2, j / 2, k, localPaint);
        return localBitmap;
    }

    public TgImageGetter setRounded(boolean rounded) {
        this.isRounded = rounded;
        return this;
    }


    public TgImageGetter setAdapter(final BaseAdapter adapter) {
        setUpdateCallback(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        return this;
    }
}
