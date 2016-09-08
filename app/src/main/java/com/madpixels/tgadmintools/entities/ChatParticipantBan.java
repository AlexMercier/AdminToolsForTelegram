package com.madpixels.tgadmintools.entities;

import com.madpixels.apphelpers.Utils;

import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Created by Snake on 13.03.2016.
 */
public class ChatParticipantBan {
    public TdApi.ChatParticipant chatParticipant;
    public String banText;
    public long chat_id;
    public long banDate, banAge;
    public boolean isReturnToChat;

    private String strBanDate, strBanExpired;

    public ChatParticipantBan(TdApi.ChatParticipant chatParticipant){
        this.chatParticipant=chatParticipant;
    }

    public ChatParticipantBan setChatId(long chat_id) {
        this.chat_id=chat_id;
        return this;
    }

    public String getBanDate(){
        if (strBanDate==null)
            strBanDate = Utils.TimestampToDate(banDate/1000);
        return strBanDate;
    }

    public String getBanExpiredTime(){
        if(strBanExpired==null)
            strBanExpired =  Utils.TimestampToDate((banDate+banAge)/1000);
        return strBanExpired;
    }
}
