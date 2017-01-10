package com.madpixels.tgadmintools.entities;

/**
 * Created by Snake on 19.09.2016.
 */

public class ChatTask {
    public boolean isEnabled = false;
    public Object payload;


    public enum TYPE {STICKERS, IMAGES, LINKS, BANWORDS, VOICE, LeaveMsg, JoinMsg, FLOOD, GAME, DOCS,
        GIF, AUDIO, VIDEO, COMMAND, MutedUsers, CHAT_BOT, WELCOME_USER}

    public TYPE mType;
    public int id=0; // id in database
    public long chat_id;

    public int mAllowCountPerUser = 3;
    public long mBanTimeSec = 60 * 5; //default 5 min, 0 - forever.
    public boolean isReturnOnBanExpired = true;
    public String mWarnTextFirst, mWarnTextLast, mText;
    public long mWarningsDuringTime = 5 * 60;// 5 minute default. .
    public boolean isPublicToChat=false;

    public boolean isRemoveMessage = false;
    public boolean isBanUser = false;

    /**
     * One of value: 0 - never (silent ban), 1 - on last message, 2 - on first message, 3 - on first and last, 4 - on Second message and last (skip first warn),  5 - everytime
     */
    public int mWarnFrequency = 3; // >0 - isWarnBeforeBan

    public ChatTask(TYPE type, long chat_id) {
        this(type.name(), chat_id);
    }

    public ChatTask(String type, long chat_id) {
        mType = TYPE.valueOf(type);
        this.chat_id = chat_id;
    }

    public boolean isWarnBeforeBan() {
        return mWarnFrequency > 0;
    }

    /**
     * @param pCurrentWarnsCount
     * @return true if current user warning counts in chosen selection. See description on: {@link #mWarnFrequency}
     */
    public boolean isWarnAvailable(int pCurrentWarnsCount) {
        switch (mWarnFrequency) {
            case 1:
                return pCurrentWarnsCount == mAllowCountPerUser - 1; // only when last attempt
            case 2:
                return pCurrentWarnsCount == 0; // only when first attempt
            case 3:
                return pCurrentWarnsCount == 0 || pCurrentWarnsCount == mAllowCountPerUser - 1; // first or last
            case 4:
                return pCurrentWarnsCount == 1 || pCurrentWarnsCount == mAllowCountPerUser - 1; // 2'd and last tryes
            case 5:
                return true; // always

            default:
                return false;
        }
    }

    /**
     * 1 - text of first warning, 2 - text of last warning.
     */
    public int selectWarningText(int pCurrentWarnsCount) {
        switch (mWarnFrequency) {
            case 1:
                return 2; // only when last attempt
            case 2:
                return 1; // only when first attempt
            case 3:
                return pCurrentWarnsCount == 0 ? 1 : 2; // first / last
            case 4:
                return pCurrentWarnsCount == 1 ? 1 : 2; // 2'd and last tryes
            case 5:
                return 1; // always

            default:
                return 0;
        }
    }

}
