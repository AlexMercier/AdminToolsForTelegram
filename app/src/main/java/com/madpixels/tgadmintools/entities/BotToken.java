package com.madpixels.tgadmintools.entities;

/**
 * Created by Snake on 07.01.2017.
 */

public class BotToken {
    public String mToken;
    public String mUsername;
    public String mFirstName;
    public int bot_id, local_id;
    private String mTokenSafe=null;
    /**
     * return token with replace come chars to * for secure
     * @return
     */
    public String getTokenSafe() {
            return mTokenSafe!=null?mTokenSafe : (mTokenSafe = mToken.substring(0, 10) + "**********"+mToken.substring(25));
    }
}