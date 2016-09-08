package com.madpixels.apphelpers;

import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Snake on 22.07.2016.
 */
public class DownloadFile {

    private Callback onDownloadProgressCallback;
    private int progressUpdateFreq = 400; //msec
    private boolean cancelled = false;

    public static boolean downloadToFile(final String url, final File saveFile) {
        return new DownloadFile().download(url, saveFile);
    }

    public DownloadFile setOnProgressCallback(Callback onDownloadProgressCallback) {
        this.onDownloadProgressCallback = onDownloadProgressCallback;
        return this;
    }

    public static boolean downloadWithProgressCallback(final String url, final File saveFile, @Nullable Callback callback) {
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.onDownloadProgressCallback = callback;
        return downloadFile.download(url, saveFile);
    }

    public synchronized boolean download(final String url, final File saveFile) {

        try {
            URL urlConn = new URL(url);
            URLConnection c = urlConn.openConnection();
            c.connect();
            final long fileSize = c.getContentLength();
            if (onDownloadProgressCallback != null)
                onDownloadProgressCallback.onCallback(fileSize, 0); //send info about fileSize

            OutputStream output = new FileOutputStream(saveFile);
            InputStream in = c.getInputStream();

            int count;
            byte data[] = new byte[1024];

            long len = 0;
            long time = System.currentTimeMillis();//время начала загрузки
            while ((count = in.read(data)) != -1) {
                output.write(data, 0, count);
                len += count;

                if (onDownloadProgressCallback != null && System.currentTimeMillis() - time >= progressUpdateFreq) {//обновляем гуи каждые 1.5сек.
                    MyLog.log("download update progress " + len + "bytes");
                    onDownloadProgressCallback.onCallback(len, 1);
                    time = System.currentTimeMillis();
                }
                if (cancelled) {
                    output.close();
                    in.close();
                    saveFile.delete();
                    return false;
                }
            }
            output.flush();
            output.close();
            in.close();
            return true;
        } catch (Exception e) {
            saveFile.delete();
            MyLog.log("download file error " + e.getMessage());
        }
        return false;
    }

    public void cancel() {
        cancelled = true;
    }
}
