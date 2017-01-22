package com.madpixels.tgadmintools.entities;

/**
 * Created by Snake on 26.02.2016.
 */
public class BanTask {
    public long task_id;
    public long banDate, banAge, tsUnban;
    public long chat_id;
    public boolean isReturnOnUnban;
    public int from_id; // id группы в которой бан
    public int chatType;
    public int user_id;
    public int unbanErrors = 0;
    public boolean isMutedBan = false; //Mute instead of Ban
    public String username;

    public void setBanTiming(long banDate, long age) {
        this.banDate = banDate;
        this.banAge = age;
        tsUnban = banDate + banAge;
    }
}
