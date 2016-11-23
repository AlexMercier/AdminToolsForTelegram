package com.madpixels.tgadmintools.entities;

/**
 * Created by Snake on 23.11.2016.
 */

public class ChatCommand {

    public static int TYPE_TEXT = 0, TYPE_KICK_SENDER = 1;

    public long id;
    public long chatId;
    public String cmd;
    public String answer;
    public int type;
    public boolean isEnabled = false;
    public boolean isAdmin = false;
}
