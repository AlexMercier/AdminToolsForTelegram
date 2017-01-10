package com.madpixels.tgadmintools.entities;

/**
 * Created by Snake on 23.11.2016.
 */

public class ChatCommand {

    public static int CMD_TEXT = 0, CMD_KICK_SENDER = 1, CMD_CHANGE_TITLE = 2;

    public long id;
    public long chatId;
    public String cmd;
    public String answer;
    public int type;
    public boolean isEnabled = false;
    public boolean isAdmin = false;
}
