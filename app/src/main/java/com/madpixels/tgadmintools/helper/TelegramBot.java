package com.madpixels.tgadmintools.helper;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.NetUtils;
import com.madpixels.tgadmintools.BuildConfig;

import org.drinkless.td.libcore.telegram.TdApi;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Snake on 04.01.2017.
 */

public class TelegramBot {

    private final static String API_URL = "https://api.telegram.org/bot%s/%s";

    public String mToken, username, first_name, id;


    public TelegramBot(String token) {
        mToken = token;
    }

    public String sendMessageMarkdown(String userId, String messageMarkdown) {
        return sendMessage(userId, messageMarkdown, true, false);
    }

    public String sendMessageHtml(String userId, String messageHtml) {
        return sendMessage(userId, messageHtml, false, true);
    }

    private void sendMessage(String userId, String message, String[] params) {
        final String method = "sendMessage";
        final String url = String.format(API_URL, mToken, method);

        ArrayList<String> paramsList = new ArrayList<>(2);
        paramsList.add("chat_id");
        paramsList.add(userId);

        paramsList.add("text");
        paramsList.add(message);

        for (String s : params)
            paramsList.add(s);

        final String[] paramsArray = paramsList.toArray(new String[1]);
        final String response = NetUtils.postRequest(url, paramsArray);
        MyLog.log(response);
    }


    public String sendMessage(String userId, String message, boolean isMarkdown, boolean isHtml) {
        final String method = "sendMessage";
        final String url = String.format(API_URL, mToken, method);

        ArrayList<String> params = new ArrayList<>(2);
        params.add("chat_id");
        params.add(userId);

        params.add("text");
        params.add(message);

        if (isMarkdown) {
            params.add("parse_mode");
            params.add("Markdown");
        } else if (isHtml) {
            params.add("parse_mode");
            params.add("HTML");
        }

        final String[] paramsArray = params.toArray(new String[1]);
        final String response = NetUtils.postRequest(url, paramsArray);
        MyLog.log(response);

        return response;
    }

    public String sendMessage(String userId, String message) {
        return sendMessage(userId, message, false, false);
    }

    /**
     * @return {@link TdApi#Ok} on success or {@link TdApi#Error}
     */
    public TdApi.TLObject getMe() {
        String url = String.format(API_URL, mToken, "getMe");
        final String response = NetUtils.getRequest(url);
        if (response.isEmpty())
            return new TdApi.Error();

        try {
            JSONObject json = new JSONObject(response);
            if (json.getBoolean("ok") == false)
                return new TdApi.Error(json.optInt("error_code"), json.optString("description"));
            json = json.getJSONObject("result");
            username = json.getString("username");
            first_name = json.getString("first_name");
            id = json.getInt("id") + "";
            return new TdApi.Ok();
        } catch (JSONException e) {
            e.printStackTrace();
            return new TdApi.Error(0, e.getMessage());
        }

    }
}
