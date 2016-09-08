package com.madpixels.tgadmintools.db;

import android.app.AlarmManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.Utils;
import com.madpixels.tgadmintools.App;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.entities.AntiSpamRule;
import com.madpixels.tgadmintools.entities.BanTask;
import com.madpixels.tgadmintools.entities.ChatParticipantBan;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.services.ServiceAutoKicker;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;

/**
 * Created by Snake on 13.01.2016.
 */
public class DBHelper extends DB {

    private final static Object LOCK = new Object();
    private static DBHelper Instance;
    Context c;

    public DBHelper(Context context) {
        super(context);
        c = context;
        db = getWritableDatabase();
    }

    public static DBHelper getInstance() {
        if(Instance!=null)
            return Instance;
        return
                Instance = new DBHelper(App.getContext());
    }

    private int getCount(String table) {
        return getCount(table, null, null);
    }

    private int getCount(String table, @Nullable String whereClouse, @Nullable String... whereArgs) {
        try {
            if (whereClouse == null) whereClouse = "1";
            Cursor cursor = db.rawQuery("SELECT COUNT(1) FROM " + table + " where " + whereClouse, whereArgs);
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                cursor.close();
                return count;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean isRowExists(final String rawSql) {
        Cursor c = getTable(rawSql);
        if (c == null) return false;
        try {
            return isRowExists(c);
        } catch (Exception e) {

        }
        return false;
    }

    private boolean isRowExists(final Cursor cursor) {
        if(cursor==null) return false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }
        cursor.close();
        return false;
    }

    private boolean isRowExists(String table, String column, String value) {
        try {
            Cursor cursor = db.rawQuery("SELECT COUNT(1) FROM " + table + " WHERE " + column + "=?", new String[]{value});
            return isRowExists(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void dbClose() {
        try {
            Instance = null;
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToBanList(TdApi.User user, long chat_id, int chatType, int groupIdOrChannelId, long ban_age_msec, boolean isReturnOnUnban, String banReason) {
        long ban_ts = System.currentTimeMillis();

        ContentValues cv = new ContentValues(7);
        cv.put("chat_id", chat_id); // id чата в списке
        cv.put("from_id", groupIdOrChannelId); // глобальный id чата,  либо группа либо супергруппа id.
        cv.put("chatType", chatType);
        cv.put("user_id", user.id);
        cv.put("user_name", user.firstName + " " + user.lastName);
        cv.put("user_login", user.username);
        cv.put("ban_date_ts", ban_ts);
        cv.put("ban_age_msec", ban_age_msec);
        cv.put("is_return_on_unban", isReturnOnUnban);
        cv.put("reason", banReason);
        // cv.put("reason", 0);

        try {
            long id = db.insertWithOnConflict(BAN_INFO_TABLE, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            MyLog.log(id + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public long getBanListFirst() {
        String sql = "SELECT user_id, ban_date_ts+ban_age_msec as targetTime   FROM " + BAN_INFO_TABLE +
                " WHERE ban_age_msec>0 " +
                " ORDER BY targetTime ASC LIMIT 1";
        Cursor c = getTable(sql);
        if (c != null) {
            do {
                String user_id = c.getString(c.getColumnIndex("user_id"));
                long targetTime = c.getLong(c.getColumnIndex("targetTime"));
                MyLog.log(user_id + ": " + targetTime);
                c.close();
                String s = Utils.TimestampToDate(targetTime / 1000);
                MyLog.log("targetTime: " + s);
                return targetTime;
            }
            while (c.moveToNext());
        }
        return 0;
    }

    public ArrayList<BanTask> getExpairedBanList() {

        final long expaired_ts = System.currentTimeMillis();
        String sql = "SELECT _id, chat_id, from_id, chatType, user_id,is_return_on_unban, unban_errors,  ban_date_ts+ban_age_msec as targetTime FROM " + BAN_INFO_TABLE +
                " WHERE ban_age_msec>0 AND targetTime<=" + expaired_ts +
                " ORDER BY targetTime DESC";
        Cursor c = getTable(sql);
        if (c != null) {
            ArrayList<BanTask> list = new ArrayList<>(c.getCount());
            do {
                String user_id = c.getString(c.getColumnIndex("user_id"));
                long targetTime = c.getLong(c.getColumnIndex("targetTime"));
                MyLog.log(user_id + ": " + targetTime);

                BanTask bt = new BanTask();
                bt.task_id = c.getLong(c.getColumnIndex("_id"));
                bt.chat_id = c.getLong(c.getColumnIndex("chat_id"));
                bt.from_id = c.getInt(c.getColumnIndex("from_id"));
                bt.chatType = c.getInt(c.getColumnIndex("chatType"));
                bt.user_id = c.getInt(c.getColumnIndex("user_id"));
                bt.isReturnOnUnban = c.getInt(c.getColumnIndex("is_return_on_unban")) == 1;
                bt.user_id = c.getInt(c.getColumnIndex("user_id"));
                bt.unbanErrors = c.getInt(c.getColumnIndex("unban_errors"));

                String stime = Utils.TimestampToDate(targetTime / 1000);
                MyLog.log("test time: " + stime);

                list.add(bt);
            }
            while (c.moveToNext());
            c.close();
            return list;
        }
        return null;
    }

    public void removeBanTask(BanTask ban) {
        try {
            db.delete(BAN_INFO_TABLE, "_id=?", new String[]{ban.task_id + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void setUnbanError(BanTask ban){
        if(ban.unbanErrors>=3){ // если было три ошибки значит юзер не найден.
            removeBannedUser(ban.chat_id, ban.user_id);
            return;
        }
        try {
            ContentValues cv = new ContentValues(2);
            cv.put("unban_errors", ban.unbanErrors+1);
            db.update(BAN_INFO_TABLE, cv, "_id=?", new String[]{ban.task_id + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void removeBanTask(long chatId, int userId) {
        try {
            int count = db.delete(BAN_INFO_TABLE, "chat_id=? AND user_id=?", new String[]{chatId + "", userId+""});
            MyLog.log("deleted count " + count);
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void deleteGroup(long chat_id){
        try {
            int c = db.delete(BAN_INFO_TABLE, "chat_id=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup BAN_INFO_TABLE: "+c);

            c = db.delete(TABLE_AUTO_KICK, "chat_id=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup TABLE_AUTO_KICK: "+c);

            c = db.delete(TABLE_ANTISPAM, "chat_id=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup TABLE_ANTISPAM: "+c);

            c = db.delete(TABLE_ANTISPAM_WARNS, "chatId=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup TABLE_ANTISPAM_WARNS: "+c);
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void removeBannedUser(long chat_id, int user_id) {
        try {
            int size = db.delete(BAN_INFO_TABLE, "chat_id=? AND user_id=?", new String[]{chat_id + "", user_id + ""});
            MyLog.log(size+"");
            if (getAutoKickCount() == 0) {
                ServiceAutoKicker.stop(App.getContext());
            }
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public boolean isUserKicked(final long chat_id, final int user_id) {
        try {
            Cursor cursor = db.rawQuery("SELECT COUNT(1) FROM " + TABLE_AUTO_KICK + " WHERE chat_id=? AND user_id=?", new String[]{chat_id + "", user_id + ""});
            return isRowExists(cursor);
        } catch (Exception e) {
            MyLog.log(e);
            return false;
        }
    }

    public void addUserToAutoKick(long chat_id, int user_id) {
        ContentValues cv = new ContentValues(2);
        cv.put("chat_id", chat_id);
        cv.put("user_id", user_id);
        try {
            db.insertWithOnConflict(TABLE_AUTO_KICK, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void removeUserFromAutoKick(long chat_id, int user_id) {
        try {
            db.delete(TABLE_AUTO_KICK, "chat_id=? AND user_id=?", new String[]{chat_id + "", user_id + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public int getAutoKickCount() {
        try {
            Cursor c = getTable("SELECT COUNT(1) FROM " + TABLE_AUTO_KICK);
            if (c != null) {
                int count = c.getInt(0);
                c.close();
                return count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<ChatParticipantBan> getBanned(long chat_id, int offset) {
        String sql = "SELECT * FROM " + BAN_INFO_TABLE + " WHERE chat_id=" + chat_id + " ORDER BY _id ASC LIMIT " + offset + ",100 ";
        Cursor c = getTable(sql);
        if (c != null) {
            ArrayList<ChatParticipantBan> users = new ArrayList<>(c.getCount());
            do {
                ChatParticipantBan banUser;
                String user_name = c.getString(c.getColumnIndex("user_name"));
                int user_id = c.getInt(c.getColumnIndex("user_id"));
                String user_login = c.getString(c.getColumnIndex("user_login"));
                TdApi.ChatParticipant chatUser = TgH.createChatParticipiant(chat_id, user_name, user_id, user_login);
                banUser = new ChatParticipantBan(chatUser);
                banUser.banText = c.getString(c.getColumnIndex("reason"));
                banUser.chat_id = chat_id;
                banUser.banDate = c.getLong(c.getColumnIndex("ban_date_ts"));
                banUser.banAge = c.getLong(c.getColumnIndex("ban_age_msec"));
                banUser.isReturnToChat = c.getInt(c.getColumnIndex("is_return_on_unban"))==1;
                //TODO при бане сохранять photoId юзера авы или пробовать запросить


                users.add(banUser);
            } while (c.moveToNext());
            c.close();
            return users;
        }

        return new ArrayList<>(0);
    }

    public int getBannedCount(long chatId) {
        return getCount(TABLE_AUTO_KICK, "chat_id=?", chatId + "");
    }

    public int getTotalBannedCount( ) {
        return getCount(TABLE_AUTO_KICK);
    }


    public int getChatRulesCount(){
        int total = 0;
        String sql = "SELECT COUNT(1) FROM " + TABLE_ANTISPAM + " WHERE " +
                "is_remove_links=1 OR is_remove_sticks=1 OR is_ban_sticks=1 OR is_ban_links=1 OR isBanForBlackWords=1 OR isDelMsgBlackWords=1" +
                " OR is_remove_join_msg=1 OR is_remove_leaved_msg=1";
        Cursor c = getTable(sql);
        if (isRowExists(c))
            total += c.getInt(0);

        sql = "SELECT COUNT(1) FROM "+TABLE_CHAT_WELCOME +"WHERE isEnabled=1";
        c = getTable(sql);
        if(isRowExists(c))
            total+=c.getInt(0);

        return total;
    }

    public boolean isChatRulesExists() {
        String sql = "SELECT COUNT(1) FROM " + TABLE_ANTISPAM + " WHERE " +
                "is_remove_links=1 OR is_remove_sticks=1 OR is_ban_sticks=1 OR is_ban_links=1 OR isBanForBlackWords=1 OR isDelMsgBlackWords=1" +
                " OR is_remove_join_msg=1 OR is_remove_leaved_msg=1";
        Cursor c = getTable(sql);
        if (isRowExists(c))
            return true;

        sql = "SELECT COUNT(1) FROM "+TABLE_CHAT_WELCOME +"WHERE isEnabled=1";
        c = getTable(sql);
        return isRowExists(c);
    }

    public AntiSpamRule getAntispamRule(final long chat_id) {
        String sql = "SELECT * FROM " + TABLE_ANTISPAM + " WHERE chat_id=" + chat_id;
        Cursor c = getTable(sql);
        if (c == null) return null;

        AntiSpamRule antiSpamRule = new AntiSpamRule();
        antiSpamRule.id = c.getInt(c.getColumnIndex("_id"));
        antiSpamRule.chat_id = chat_id;
        //int testBool = c.getInt(c.getColumnIndex("is_remove_links"));
        //MyLog.log("testBool=" + testBool);
        antiSpamRule.isRemoveLinks = c.getInt(c.getColumnIndex("is_remove_links")) == 1;
        antiSpamRule.isRemoveStickers = c.getInt(c.getColumnIndex("is_remove_sticks")) == 1;
        antiSpamRule.isBanForLinks = c.getInt(c.getColumnIndex("is_ban_links"))==1;
        antiSpamRule.isBanForStickers = c.getInt(c.getColumnIndex("is_ban_sticks"))==1;


//
        antiSpamRule.mAllowLinksCount = c.getInt(c.getColumnIndex("ban_links_allow_count"));
        antiSpamRule.mWarnTextLink = c.getString(c.getColumnIndex("ban_link_warn_text"));
        antiSpamRule.isLinkReturnOnUnban = c.getInt(c.getColumnIndex("is_link_return_on_unban"))==1;
        antiSpamRule.mLinkBanAgeMsec = c.getInt(c.getColumnIndex("ban_link_age_msec"));
//
//
        antiSpamRule.isWarnBeforeStickersBan = c.getInt(c.getColumnIndex("is_warn_before_stickers_ban"))==1;
        antiSpamRule.mAllowStickersCount = c.getInt(c.getColumnIndex("ban_sticks_allow_count"));
        antiSpamRule.mWarnTextStickers = c.getString(c.getColumnIndex("ban_stick_warn_text"));
        antiSpamRule.isStickerReturnOnUnban = c.getInt(c.getColumnIndex("is_stick_return_on_unban"))==1;
        antiSpamRule.mStickerBanAgeMsec = c.getInt(c.getColumnIndex("ban_stick_age_msec"));

        antiSpamRule.option_link_banage_val = c.getInt(c.getColumnIndex("option_links_banage_value"));
        antiSpamRule.option_links_banage_mp = c.getInt(c.getColumnIndex("option_links_banage_multiplier"));
        antiSpamRule.option_sticks_banage_val = c.getInt(c.getColumnIndex("option_sticks_banage_value"));
        antiSpamRule.option_sticks_banage_mp = c.getInt(c.getColumnIndex("option_sticks_banage_multiplier"));

        antiSpamRule.isBanForBlackWords = c.getInt(c.getColumnIndex("isBanForBlackWords"))==1;
        antiSpamRule.isDelMsgBlackWords = c.getInt(c.getColumnIndex("isDelMsgBlackWords"))==1;
        antiSpamRule.isBlackWordReturnOnBanExp = c.getInt(c.getColumnIndex("isBlackWordReturnOnBanExp"))==1;
        antiSpamRule.option_blackword_banage_val = c.getInt(c.getColumnIndex("option_blackword_banage_val"));
        antiSpamRule.option_blackword_banage_mp = c.getInt(c.getColumnIndex("option_blackword_banage_mp"));
        antiSpamRule.mBlackWordBanAgeMsec = c.getInt(c.getColumnIndex("ban_blackword_age_msec"));
        antiSpamRule.isAlertAboutWordBan =  c.getInt(c.getColumnIndex("isAlertOnBanWord"))==1;
        antiSpamRule.isWarnBeforeBanForWord =  c.getInt(c.getColumnIndex("isWarnBeforeBanForWord"))==1;
        antiSpamRule.mWarnTextBlackWords = c.getString(c.getColumnIndex("ban_words_warn_text"));
        antiSpamRule.banWordsFloodLimit = c.getInt(c.getColumnIndex("banWordsFloodLimit"));
        antiSpamRule.isWarnBeforeLinksBan = c.getInt(c.getColumnIndex("is_warn_before_links_ban"))==1;

        antiSpamRule.isRemoveJoinMessage = c.getInt(c.getColumnIndex("is_remove_join_msg"))==1;
        antiSpamRule.isRemoveLeaveMessage= c.getInt(c.getColumnIndex("is_remove_leaved_msg"))==1;

        c.close();
        return antiSpamRule;
    }

    public void setAntiSpamRule(long chat_id, AntiSpamRule antiSpamRule) {
        ContentValues cv = new ContentValues(6);
        cv.put("chat_id", chat_id);
        cv.put("is_remove_links", antiSpamRule.isRemoveLinks);
        cv.put("is_remove_sticks", antiSpamRule.isRemoveStickers);
        cv.put("is_ban_links", antiSpamRule.isBanForLinks);
        cv.put("is_ban_sticks", antiSpamRule.isBanForStickers);
        cv.put("is_warn_before_links_ban", antiSpamRule.isWarnBeforeLinksBan);

        cv.put("ban_links_allow_count", antiSpamRule.mAllowLinksCount);
        cv.put("ban_link_warn_text", antiSpamRule.mWarnTextLink);
        cv.put("is_link_return_on_unban", antiSpamRule.isLinkReturnOnUnban);
        cv.put("ban_link_age_msec", antiSpamRule.mLinkBanAgeMsec);
//
        cv.put("ban_sticks_allow_count", antiSpamRule.mAllowStickersCount);
        cv.put("ban_stick_warn_text", antiSpamRule.mWarnTextStickers);
        cv.put("is_stick_return_on_unban", antiSpamRule.isStickerReturnOnUnban);
        cv.put("ban_stick_age_msec", antiSpamRule.mStickerBanAgeMsec);
        cv.put("is_warn_before_stickers_ban", antiSpamRule.isWarnBeforeStickersBan);
        cv.put("option_links_banage_value", antiSpamRule.option_link_banage_val);
        cv.put("option_links_banage_multiplier", antiSpamRule.option_links_banage_mp);
        cv.put("option_sticks_banage_value", antiSpamRule.option_sticks_banage_val);
        cv.put("option_sticks_banage_multiplier", antiSpamRule.option_sticks_banage_mp);

        cv.put("isDelMsgBlackWords", antiSpamRule.isDelMsgBlackWords);
        cv.put("isBanForBlackWords", antiSpamRule.isBanForBlackWords);
        cv.put("isBlackWordReturnOnBanExp", antiSpamRule.isBlackWordReturnOnBanExp);
        cv.put("option_blackword_banage_val", antiSpamRule.option_blackword_banage_val);
        cv.put("option_blackword_banage_mp", antiSpamRule.option_blackword_banage_mp);
        cv.put("ban_blackword_age_msec", antiSpamRule.mBlackWordBanAgeMsec);
        cv.put("isAlertOnBanWord", antiSpamRule.isAlertAboutWordBan);
        cv.put("isWarnBeforeBanForWord", antiSpamRule.isWarnBeforeBanForWord);
        cv.put("ban_words_warn_text", antiSpamRule.mWarnTextBlackWords);
        cv.put("banWordsFloodLimit", antiSpamRule.banWordsFloodLimit);

        cv.put("is_remove_join_msg", antiSpamRule.isRemoveJoinMessage);
        cv.put("is_remove_leaved_msg", antiSpamRule.isRemoveLeaveMessage);


        try {
            long insertid = db.insertWithOnConflict(TABLE_ANTISPAM, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            MyLog.log("antiSpamRule insert id " + insertid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ChatParticipantBan getBanById(long chat_id, int user_id) {
        String sql = "SELECT * FROM " + BAN_INFO_TABLE + " WHERE chat_id=" + chat_id + " AND user_id=" + user_id;
        Cursor c = getTable(sql);
        if (c != null) {
            String reason = c.getString(0);

            //String user_id = c.getString(c.getColumnIndex("user_id"));
            //long targetTime = c.getLong(c.getColumnIndex("targetTime"));
           // MyLog.log(user_id + ": " + targetTime);

            ChatParticipantBan ban = new ChatParticipantBan(null);
            ban.banText = c.getString(c.getColumnIndex("reason"));
            ban.banDate = c.getLong(c.getColumnIndex("ban_date_ts"));
            ban.banAge = c.getLong(c.getColumnIndex("ban_age_msec"));
            ban.isReturnToChat = c.getInt(c.getColumnIndex("is_return_on_unban"))==1;

            c.close();
            return ban;
        }
        return null;
    }

    public void saveLinksWhiteList(String[] links) {
        try {
            db.delete(TABLE_WHITE_LINKS, null, null);
            if(links.length==0)
                return;
            ArrayList<ContentValues> cv = new ArrayList<>(links.length);
            for (String s : links) {
                ContentValues c = new ContentValues(1);
                c.put("link", s);
                cv.add(c);
            }
            batchInsert(TABLE_WHITE_LINKS, cv, SQLiteDatabase.CONFLICT_REPLACE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return null или массив ссылок
     */
    public String[] getLinksWhiteList() {
        Cursor c = getTable("SELECT * FROM " + TABLE_WHITE_LINKS + " ORDER BY _id");
        if (c != null) {
            String[] links = new String[c.getCount()];
            int index = 0;
            do {
                String s = c.getString(c.getColumnIndex("link"));
                links[index++] = s;
            } while (c.moveToNext());
            c.close();
            return links;
        }
        return null;
    }


    public boolean isLinkInWhiteList(final String urlStr) {
        return isRowExists(TABLE_WHITE_LINKS, "link", urlStr);
    }

    /**
     * @return null или массив слов
     */
    public String[] getWordsBlackList(final  long chatId) {
        Cursor c = getTable("SELECT * FROM " + TABLE_BLACKLIST_WORDS + " WHERE chatId=? ORDER BY _id", new String[]{chatId+""});
        if (c != null) {
            String[] words = new String[c.getCount()];
            int index = 0;
            do {
                String s = c.getString(c.getColumnIndex("word"));
                words[index++] = s;
            } while (c.moveToNext());
            c.close();
            return words;
        }
        return null;
    }

    public void saveWordsBlackList(final long chatId, final String[] words) {
        try {
            db.delete(TABLE_BLACKLIST_WORDS, null, null);
            if(words.length==0)
                return;
            ArrayList<ContentValues> cv = new ArrayList<>(words.length);
            for (String s : words) {
                ContentValues c = new ContentValues(2);
                c.put("chatId", chatId);
                c.put("word", s);
                cv.add(c);
            }
            batchInsert(TABLE_BLACKLIST_WORDS, cv, SQLiteDatabase.CONFLICT_IGNORE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logAction(String action, String json) {
        ContentValues cv = new ContentValues(2);
        cv.put("action", action);
        cv.put("jsonData", json);
        cv.put("ts", System.currentTimeMillis());
        try {
            db.insert(TABLE_LOG_ACTIONS, null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LogUtil.LogEntity> getLog(int offset) {
        String sql = "SELECT * FROM "+TABLE_LOG_ACTIONS+" ORDER BY _id DESC LIMIT "+offset+", 5";
        Cursor c = getTable(sql);

        if(c!=null){
            ArrayList<LogUtil.LogEntity> logs = new ArrayList<>(c.getCount());
            do{
                String action = c.getString(c.getColumnIndex("action"));
                String jsonStr = c.getString(c.getColumnIndex("jsonData"));

                LogUtil.LogEntity log = new LogUtil.LogEntity();
                log.action = LogUtil.Action.valueOf(action);
                log.jsonData = jsonStr;
                log.item_id = c.getInt(c.getColumnIndex("_id"));
                logs.add(log);

            }while (c.moveToNext());
            c.close();
            return logs;
        }

        return null;
    }

    public ArrayList<LogUtil.LogEntity> getLogUpdate(int id) {
        String sql = "SELECT * FROM "+TABLE_LOG_ACTIONS+" WHERE _id > "+id+" ORDER BY _id DESC";
        Cursor c = getTable(sql);

        if(c!=null){
            ArrayList<LogUtil.LogEntity> logs = new ArrayList<>(c.getCount());
            do{
                String action = c.getString(c.getColumnIndex("action"));
                String jsonStr = c.getString(c.getColumnIndex("jsonData"));

                LogUtil.LogEntity log = new LogUtil.LogEntity();
                log.action = LogUtil.Action.valueOf(action);
                log.jsonData = jsonStr;
                log.item_id = c.getInt(c.getColumnIndex("_id"));
                logs.add(log);

            }while (c.moveToNext());
            c.close();
            return logs;
        }

        return null;
    }

    /**
     * @param msec срок действия предупреждения
     * @return
     */
    public int getAntiSpamWarnCount(LogUtil.Action action, long chatId, int userId, long msec) {
        // long msec = 60*60*1000;// 1 час
        String sql = "SELECT * FROM "+TABLE_ANTISPAM_WARNS+" WHERE action=?"+
                " AND chatId="+chatId+" AND userId="+userId+
                " AND ts > "+(System.currentTimeMillis()-msec);
        try {
            Cursor c = getTable(sql, action.name());
            if(c!=null){
                int count = c.getInt(c.getColumnIndex("count"));
                c.close();
                return  count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void setAntiSpamWarnCount(LogUtil.Action action, long chatId, int fromId, int attemptsCount) {
        ContentValues cv = new ContentValues(4);
        cv.put("action", action.name());
        cv.put("chatId", chatId);
        cv.put("userId", fromId);
        cv.put("ts", System.currentTimeMillis());
        cv.put("count", attemptsCount);
        try{
            db.insertWithOnConflict(TABLE_ANTISPAM_WARNS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        }catch (Exception e){

        }
    }

    public void resetWarnCountForChat(long chatId){
        try{
            long id = db.delete(TABLE_ANTISPAM_WARNS, "chatId=?", new String[]{chatId+""});
            MyLog.log("Deleted: "+id);
        }catch (Exception e){

        }
    }

    public void deleteAntiSpamWarnCount(LogUtil.Action action, long chatId, int fromId) {
        db.delete(TABLE_ANTISPAM_WARNS, "action=? AND chatId=? AND userId=?", new String[]{action.name(), chatId+"",fromId+""});
    }

    public void clearBanList(long chat_id) {
        try{
            // return getCount(TABLE_AUTO_KICK, "chat_id=?", chatId + "");
            db.delete(TABLE_AUTO_KICK, "chat_id=?", new String[]{chat_id+""});
            db.delete(BAN_INFO_TABLE, "chat_id=?", new String[]{chat_id+""});
        }catch (Exception e){

        }
    }

    public void clearLog() {
        db.delete(TABLE_LOG_ACTIONS, null, null);
    }

    public void clearOldLog() {
        long ts = Sets.getLong(Const.LOG_LIFE_TIME, AlarmManager.INTERVAL_DAY);
        ts = System.currentTimeMillis() - ts;
        try {
            int count = db.delete(TABLE_LOG_ACTIONS, "ts<?", new String[]{ts+""});
            MyLog.log("old log cleared: "+count);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getWelcomeText(final long chatId) {
        try{
            Cursor c = getTable("SELECT text FROM "+TABLE_CHAT_WELCOME+" WHERE chatId=? AND isEnabled=1 AND text IS NOT NULL AND text !='' ", new String[]{chatId+""});
            if(c!=null){
                String text = c.getString(c.getColumnIndex("text"));
                c.close();
                return text;
            }
        }catch (Exception e){

        }
        return null;
    }

    public Object[] getWelcomeTextObject(long chatId) {
        try{
            Cursor c = getTable("SELECT * FROM "+TABLE_CHAT_WELCOME+" WHERE chatId=?", new String[]{chatId+""});
            if(c!=null){
                boolean enabled = c.getInt(c.getColumnIndex("isEnabled"))==1;
                String text = c.getString(c.getColumnIndex("text"));
                c.close();
                return new Object[]{enabled, text};
            }
        }catch (Exception e){

        }
        return null;
    }

    public void setWelcomeText(long chatId, String text, boolean isEnabled) {
        ContentValues cv = new ContentValues(3);
        cv.put("chatId", chatId);
        cv.put("isEnabled", isEnabled);
        cv.put("text", text);
        try{
            long id = db.insertWithOnConflict(TABLE_CHAT_WELCOME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            MyLog.log("save welocme text id: "+id);
        }catch (Exception e){

        }
    }
}
