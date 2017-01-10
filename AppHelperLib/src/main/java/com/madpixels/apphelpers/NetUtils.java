package com.madpixels.apphelpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by Snake on 01.07.2015.
 */
public class NetUtils {

    int timeout = 25000;
    private boolean isXMLRequest = false;
    private boolean isGZIPEncoding = true;
    private String UA = "Mozilla/5.0 (Linux; Android; TelegramStickersApp Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19";

    public NetUtils withTimeout(int msec) {
        timeout = msec;
        return this;
    }

    public NetUtils withUserAgent(final String ua) {
        UA = ua;
        return this;
    }

    public static String getRequest(final String url) {
        try {
            return new NetUtils().get(url);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String postRequest(final String url, String... params) {
        return new NetUtils().post(url, params);

    }

    public static String getRequestXML(final String url) throws IOException {
        MyLog.log("request post: " + url);
        NetUtils n = new NetUtils();
        n.isXMLRequest = true;
        return n.get(url);
    }


    public String get(String url) throws IOException {
        // MyLog.log("Request: "+url);

        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setConnectTimeout(timeout);
        con.setReadTimeout(timeout);
        con.setRequestProperty("User-Agent", UA);
        if (isGZIPEncoding)
            con.setRequestProperty("Accept-Encoding", "gzip,deflate,lzma,sdch");
        // con.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        if (isXMLRequest) {
            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        }

        con.setRequestMethod("GET");
        int responseCode;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            // HttpUrlConnection will throw an IOException if any 4XX
            // response is sent. If we request the status again, this
            // time the internal status will be properly set, and we'll be
            // able to retrieve it.
            e.printStackTrace();
            responseCode = con.getResponseCode();
        }

        String encoding = con.getHeaderField("Content-Encoding");
        boolean gzipped = encoding != null && encoding.toLowerCase().contains("gzip");

        InputStream inputStream;
        try {
            inputStream = con.getInputStream();
        } catch (Exception e) {
            inputStream = con.getErrorStream();
        }


        InputStream responseStream;
        if (gzipped)
            responseStream = new BufferedInputStream(new GZIPInputStream(inputStream));
        else
            responseStream = new BufferedInputStream(inputStream);

        InputStreamReader is = new InputStreamReader(responseStream);
        BufferedReader in = new BufferedReader(is);
        String inputLine;
        StringBuffer response = new StringBuffer();
        inputLine = in.readLine();
        while (inputLine != null) {
            response.append(inputLine);
            inputLine = in.readLine();
            if (inputLine != null)
                response.append('\r');
        }
        in.close();

        //print result
        final String r = response.toString();
        return r;
    }

    /**
     * @param params параметры вида <i>"param1", "value1", "param2", "value2", ...</i>
     */
    public String post(String targetURL, String... params) {
        URL url;
        HttpURLConnection connection = null;
        try {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < params.length; i += 2) {
                s.append(params[i] + "=" + params[i + 1]);
                if (i + 2 < params.length) s.append("&");
            }
            final byte[] post_data_bytes = s.toString().getBytes("UTF-8");

            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", UA);
            connection.setConnectTimeout(timeout);
            if (isGZIPEncoding)
                connection.setRequestProperty("Accept-Encoding", "gzip,deflate,lzma,sdch");
            // connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(post_data_bytes.length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(post_data_bytes);
            wr.flush();
            wr.close();

            //read Response
            String encoding = connection.getHeaderField("Content-Encoding");
            boolean gzipped = encoding != null && encoding.toLowerCase().contains("gzip");
            int code = connection.getResponseCode();

            InputStream inputStream = code == 200 ? connection.getInputStream() : connection.getErrorStream();
            InputStream responseStream;
            if (gzipped)
                responseStream = new BufferedInputStream(new GZIPInputStream(inputStream));
            else
                responseStream = new BufferedInputStream(inputStream);

            InputStreamReader is = new InputStreamReader(responseStream);
            BufferedReader in = new BufferedReader(is);
            // String inputLine;
            StringBuffer response = new StringBuffer();
            String inputLine = in.readLine();
            while (inputLine != null) {
                response.append(inputLine);
                inputLine = in.readLine();
                if (inputLine != null)
                    response.append('\r');
            }
            in.close();

            //print result
            //final String r = response.toString();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static boolean isConnected(final Context mContext) {
        Boolean returnValue = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni == null) {
                // There are no active networks.
                returnValue = false;
            } else {
                returnValue = true;
            }
        } catch (Exception e) {
            returnValue = true;//for any
        }
        return returnValue;
    }

}
