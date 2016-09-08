package com.madpixels.tgadmintools.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

/**
 * Created by Snake on 14.01.2016.
 */
public class NetUtils {
    int timeout = 25000;
    private boolean XML_REQUEST = false;

    public NetUtils withTimeout(int msec) {
        timeout = msec;
        return this;
    }

    private final static String UA = "Mozilla/5.0 (Linux; Android; TelegramStickersApp Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19";

    public static String getRequest(String url) {
        return new NetUtils().getUrlRequest(url);
    }

    /**
     *
     * @param url may contains params
     * @param params like "name=value"
     * @return
     */
    public String getUrlRequest(final String url, final String...params){
        String paramStr="";
        if(!url.contains("?")){
           paramStr = "?";
        }else{
            paramStr="&";
        }
        try {
            for(int i=0; i<params.length;i++){
                String[] pv = params[i].split("=",2);
                paramStr+= pv[0] +"=" + URLEncoder.encode(pv[1], "UTF-8");
                if(i<params.length-1)
                    paramStr+="&";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        return getUrlRequest(url+paramStr);
    }

    public String getUrlRequest(final String url) {
        try {
            return get(url);
        } catch (Exception e) {
        }
        return "";
    }



    private String get(String url) throws IOException {
        final long ts = System.currentTimeMillis();
        //MyLog.log("Request url "+url);

        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setConnectTimeout(timeout);
        con.setRequestProperty("User-Agent", UA);
        con.setRequestProperty("Accept-Encoding", "gzip,deflate,lzma,sdch");
        if (XML_REQUEST) {
            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        }

        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        String encoding = con.getHeaderField("Content-Encoding");
        boolean gzipped = encoding != null && encoding.toLowerCase().contains("gzip");

        InputStream inputStream = con.getInputStream();
        InputStream responseStream;
        if (gzipped)
            responseStream = new BufferedInputStream(new GZIPInputStream(inputStream));
        else
            responseStream = new BufferedInputStream(inputStream);

        //InputStream inp = new GZIPInputStream(con.getInputStream());
        InputStreamReader is = new InputStreamReader(responseStream);
        BufferedReader in = new BufferedReader(is);
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //MyLog.log("elapsed time " + url + " " + (System.currentTimeMillis() - ts));
        //print result
        final String r = response.toString();
        return r;
    }
}
