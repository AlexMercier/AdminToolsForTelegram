package com.madpixels.tgadmintools.entities;

import com.madpixels.apphelpers.Utils;

import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Created by Snake on 13.03.2016.
 */
public class ChatParticipantBan {

    public String banText;
    public long chat_id;
    public long banDate, banAge;
    public boolean isReturnToChat;
    public TdApi.User user;

    private String strBanDate, strBanExpired;

    public ChatParticipantBan(TdApi.User chatParticipant){
        this.user=chatParticipant;
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
