package com.madpixels.tgadmintools.db;

import android.app.AlarmManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.Utils;
import com.madpixels.tgadmintools.App;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.entities.BanTask;
import com.madpixels.tgadmintools.entities.BannedWord;
import com.madpixels.tgadmintools.entities.ChatCommand;
import com.madpixels.tgadmintools.entities.ChatParticipantBan;
import com.madpixels.tgadmintools.entities.ChatTask;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.services.ServiceAutoKicker;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.drinkless.td.libcore.telegram.TdApi;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Snake on 13.01.2016.
 */
public class DBHelper extends DB {

    private static DBHelper Instance;
    Context c;

    public DBHelper(Context context) {
        super(context);
        c = context;
        db = getWritableDatabase();
    }

    public static DBHelper getInstance() {
        synchronized (DBHelper.class) {
            if (Instance == null)
                Instance = new DBHelper(App.getContext());
        }
        if (Instance == null)
            Instance = new DBHelper(App.getContext());
        return Instance;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        this.db = db;
        if (oldVersion < 2) {
            upgradeTableChatTasts();
        }
    }

    private void upgradeTableChatTasts() {// upgrade chat tasks from db v1 to v2
        String sql = "SELECT  * FROM antispam_rules";
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        do {

            long chat_id = c.getLong(c.getColumnIndex("chat_id"));

            if (c.getInt(c.getColumnIndex("is_remove_join_msg")) == 1) {
                ChatTask task = new ChatTask(ChatTask.TYPE.JoinMsg, chat_id);
                task.isEnabled = true;
                saveAntispamTask(task);
            }

            if (c.getInt(c.getColumnIndex("is_remove_leaved_msg")) == 1) {
                ChatTask task = new ChatTask(ChatTask.TYPE.LeaveMsg, chat_id);
                task.isEnabled = true;
                saveAntispamTask(task);
            }

            if (c.getInt(c.getColumnIndex("is_ban_links")) == 1 || c.getInt(c.getColumnIndex("is_remove_links")) == 1) {
                ChatTask task = new ChatTask(ChatTask.TYPE.LINKS, chat_id);

                task.isBanUser = c.getInt(c.getColumnIndex("is_ban_links")) == 1;
                task.isRemoveMessage = c.getInt(c.getColumnIndex("is_remove_links")) == 1;

                task.mAllowCountPerUser = c.getInt(c.getColumnIndex("ban_links_allow_count"));
                String mWarnTextLink = c.getString(c.getColumnIndex("ban_link_warn_text"));
                if (!TextUtils.isEmpty(mWarnTextLink))
                    task.mWarnTextFirst = mWarnTextLink;

                task.isReturnOnBanExpired = c.getInt(c.getColumnIndex("is_link_return_on_unban")) == 1;
                if (c.getInt(c.getColumnIndex("is_warn_before_links_ban")) == 1)
                    task.mWarnFrequency = 3;
                task.mBanTimeSec = c.getInt(c.getColumnIndex("ban_link_age_msec")) / 1000;

                saveAntispamTask(task);
            }

            if (c.getInt(c.getColumnIndex("is_ban_sticks")) == 1 || c.getInt(c.getColumnIndex("is_remove_sticks")) == 1) {
                ChatTask task = new ChatTask(ChatTask.TYPE.STICKERS, chat_id);
                task.isRemoveMessage = c.getInt(c.getColumnIndex("is_remove_sticks")) == 1;
                task.isBanUser = c.getInt(c.getColumnIndex("is_ban_sticks")) == 1;

                if (c.getInt(c.getColumnIndex("is_warn_before_stickers_ban")) == 1)
                    task.mWarnFrequency = 3;

                String mWarnText = c.getString(c.getColumnIndex("ban_stick_warn_text"));
                if (!TextUtils.isEmpty(mWarnText))
                    task.mWarnTextFirst = mWarnText;

                task.mAllowCountPerUser = c.getInt(c.getColumnIndex("ban_sticks_allow_count"));
                task.isReturnOnBanExpired = c.getInt(c.getColumnIndex("is_stick_return_on_unban")) == 1;
                task.mBanTimeSec = c.getInt(c.getColumnIndex("ban_stick_age_msec")) / 1000;

                saveAntispamTask(task);
            }

            if (c.getInt(c.getColumnIndex("isBanForBlackWords")) == 1 || c.getInt(c.getColumnIndex("isDelMsgBlackWords")) == 1) {
                ContentValues cv = new ContentValues(2);
                cv.put("isBan", c.getInt(c.getColumnIndex("isBanForBlackWords")) == 1);
                cv.put("isRemoveMsg", c.getInt(c.getColumnIndex("isDelMsgBlackWords")) == 1);

                db.update(TABLE_BLACKLIST_WORDS, cv, "chatId=?", new String[]{chat_id + ""});

                ChatTask task = new ChatTask(ChatTask.TYPE.BANWORDS, chat_id);
                task.isEnabled = true;
                task.isReturnOnBanExpired = c.getInt(c.getColumnIndex("isBlackWordReturnOnBanExp")) == 1;
                task.mBanTimeSec = c.getInt(c.getColumnIndex("ban_blackword_age_msec")) / 1000;
                task.mAllowCountPerUser = c.getInt(c.getColumnIndex("banWordsFloodLimit"));

//                antiSpamRule.isAlertAboutWordBan = c.getInt(c.getColumnIndex("isAlertOnBanWord")) == 1;  //TODO это надо?
                String warnText = c.getString(c.getColumnIndex("ban_words_warn_text"));
                if (!TextUtils.isEmpty(warnText))
                    task.mWarnTextFirst = warnText;

                if (c.getInt(c.getColumnIndex("isWarnBeforeBanForWord")) == 1)
                    task.mWarnFrequency = 3;

                saveAntispamTask(task);

            }

        } while (c.moveToNext());
        c.close();
        db.execSQL("DROP TABLE antispam_rules");
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

    private boolean isRowExists(final String sql) {
        Cursor c = getTable(sql);
        if (c == null) return false;
        try {
            return isRowExists(c);
        } catch (Exception e) {

        }
        return false;
    }


    private boolean isRowExists(final Cursor cursor) {
        if (cursor == null) return false;
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
        cv.put("chat_id", chat_id); // id чата в списке, локальный для каждого пользователя свой.
        cv.put("from_id", groupIdOrChannelId); // глобальный id чата,  либо группа либо супергруппа id.
        cv.put("chatType", chatType);
        cv.put("user_id", user.id);
        cv.put("user_name", "".format("%s %s", user.firstName, user.lastName));
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

    /**
     * Get closest temporary ban
     *
     * @return expiration ban timestamp or 0.
     */
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

    public void setUnbanError(BanTask ban) {
        if (ban.unbanErrors >= 3) { // если было три ошибки значит юзер не найден.
            removeBannedUser(ban.chat_id, ban.user_id);
            return;
        }
        try {
            ContentValues cv = new ContentValues(2);
            cv.put("unban_errors", ban.unbanErrors + 1);
            db.update(BAN_INFO_TABLE, cv, "_id=?", new String[]{ban.task_id + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void removeBanTask(long chatId, int userId) {
        try {
            int count = db.delete(BAN_INFO_TABLE, "chat_id=? AND user_id=?", new String[]{chatId + "", userId + ""});
            MyLog.log("deleted count " + count);
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void deleteGroup(long chat_id) {
        try {
            int c = db.delete(BAN_INFO_TABLE, "chat_id=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup BAN_INFO_TABLE: " + c);

            c = db.delete(TABLE_AUTO_KICK, "chat_id=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup TABLE_AUTO_KICK: " + c);

            c = db.delete(TABLE_CHAT_TASKS, "chat_id=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup TABLE_ANTISPAM: " + c);

            c = db.delete(TABLE_ANTISPAM_WARNS, "chatId=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup TABLE_ANTISPAM_WARNS: " + c);
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void removeBannedUser(long chat_id, int user_id) {
        try {
            int size = db.delete(BAN_INFO_TABLE, "chat_id=? AND user_id=?", new String[]{chat_id + "", user_id + ""});
            MyLog.log(size + "");
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
                TdApi.User chatUser = TgH.createChatUser(chat_id, user_name, user_id, user_login);
                banUser = new ChatParticipantBan(chatUser);
                banUser.banText = c.getString(c.getColumnIndex("reason"));
                banUser.chat_id = chat_id;
                banUser.banDate = c.getLong(c.getColumnIndex("ban_date_ts"));
                banUser.banAge = c.getLong(c.getColumnIndex("ban_age_msec"));
                banUser.isReturnToChat = c.getInt(c.getColumnIndex("is_return_on_unban")) == 1;
                //TODO при бане сохранять photoId юзера авы или пробовать запросить, так как тг может не вернуть инфу о юзере если его нет в списке.


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

    /*
    public int getTotalBannedCount() {
        return getCount(TABLE_AUTO_KICK);
    }
    */

    /*
    public int getChatRulesCount() {
        int total = 0;
        String sql = "SELECT COUNT(1) FROM " + TABLE_ANTISPAM + " WHERE " +
                "is_remove_links=1 OR is_remove_sticks=1 OR is_ban_sticks=1 OR is_ban_links=1 OR isBanForBlackWords=1 OR isDelMsgBlackWords=1" +
                " OR is_remove_join_msg=1 OR is_remove_leaved_msg=1";
        Cursor c = getTable(sql);
        if (isRowExists(c))
            total += c.getInt(0);

        sql = "SELECT COUNT(1) FROM " + TABLE_CHAT_WELCOME + "WHERE isEnabled=1";
        c = getTable(sql);
        if (isRowExists(c))
            total += c.getInt(0);

        return total;
    }
    */

    public boolean isChatRulesExists() {
        String sql2 = "SELECT COUNT(1) FROM " + TABLE_CHAT_TASKS + " WHERE is_ban=1 OR is_remove_msg=1 OR isEnabled=1 ;";
        if (isRowExists(sql2))
            return true;

        /*
        String sql = "SELECT COUNT(1) FROM " + TABLE_ANTISPAM + " WHERE " +
                "is_remove_links=1 OR is_remove_sticks=1 OR is_ban_sticks=1 OR is_ban_links=1 OR isBanForBlackWords=1 OR isDelMsgBlackWords=1" +
                " OR is_remove_join_msg=1 OR is_remove_leaved_msg=1";
        Cursor c = getTable(sql);
        if (isRowExists(c))
            return true;
        */

        String sql = "SELECT COUNT(1) FROM " + TABLE_CHAT_WELCOME + " WHERE isEnabled=1";

        boolean welomeTextExists = isRowExists(sql);
        return welomeTextExists;
    }

    /**
     * @return List or null
     */
    /*
    public ChatTask getAntispamTasksByType(long chat_id, ChatTask.TYPE type) {
        String sql = "SELECT * FROM " + TABLE_CHAT_TASKS + " WHERE chat_id=? AND type=?";
        Cursor c = getTable(sql, String.valueOf(chat_id), type.name());
        ArrayList<ChatTask> list = getAntispamTasks(c);
        if (list == null || list.isEmpty())
            return null;

        return list.get(0);
    }
    */
    public ArrayList<ChatTask> getAntispamTasks(long chat_id, boolean onlyActiveTasks) {
        String filterActiveTasks = " AND (is_ban=1 OR is_remove_msg=1 OR isEnabled=1) ";
        String sql = "SELECT * FROM " + TABLE_CHAT_TASKS + " WHERE chat_id=? ";
        if (onlyActiveTasks)
            sql += filterActiveTasks;
        Cursor c = getTable(sql, String.valueOf(chat_id));
        return getAntispamTasks(c);
    }

    private ArrayList<ChatTask> getAntispamTasks(Cursor c) {
        if (c == null)
            return null;

        ArrayList<ChatTask> tasks = new ArrayList<>(c.getCount());
        do {
            String type = c.getString(c.getColumnIndex("type"));

            ChatTask task = new ChatTask(type, 0);
            task.id = c.getInt(c.getColumnIndex("_id"));
            task.chat_id = c.getLong(c.getColumnIndex("chat_id"));

            task.mAllowCountPerUser = c.getInt(c.getColumnIndex("allow_count"));
            task.mBanTimeSec = c.getLong(c.getColumnIndex("ban_age_sec"));
            task.isReturnOnBanExpired = c.getInt(c.getColumnIndex("is_return_on_ban_expired")) == 1;
            task.mWarnTextFirst = c.getString(c.getColumnIndex("warn_text_first"));
            task.mWarnTextLast = c.getString(c.getColumnIndex("warn_text_last"));
            task.mWarningsDuringTime = c.getLong(c.getColumnIndex("within_time_sec"));
            task.mWarnFrequency = c.getInt(c.getColumnIndex("warn_freq"));
            task.isBanUser = c.getInt(c.getColumnIndex("is_ban")) == 1;
            task.isRemoveMessage = c.getInt(c.getColumnIndex("is_remove_msg")) == 1;
            task.isEnabled = c.getInt(c.getColumnIndex("isEnabled")) == 1;

            tasks.add(task);
        } while (c.moveToNext());
        c.close();

        return tasks;


    }

    /*
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
        antiSpamRule.isBanForLinks = c.getInt(c.getColumnIndex("is_ban_links")) == 1;
        antiSpamRule.isBanForStickers = c.getInt(c.getColumnIndex("is_ban_sticks")) == 1;


//
        antiSpamRule.mAllowLinksCount = c.getInt(c.getColumnIndex("ban_links_allow_count"));
        antiSpamRule.mWarnTextLink = c.getString(c.getColumnIndex("ban_link_warn_text"));
        antiSpamRule.isLinkReturnOnUnban = c.getInt(c.getColumnIndex("is_link_return_on_unban")) == 1;
        antiSpamRule.mLinkBanAgeMsec = c.getInt(c.getColumnIndex("ban_link_age_msec"));
//
//
        antiSpamRule.isWarnBeforeStickersBan = c.getInt(c.getColumnIndex("is_warn_before_stickers_ban")) == 1;
        antiSpamRule.mAllowStickersCount = c.getInt(c.getColumnIndex("ban_sticks_allow_count"));
        antiSpamRule.mWarnTextStickers = c.getString(c.getColumnIndex("ban_stick_warn_text"));
        antiSpamRule.isStickerReturnOnUnban = c.getInt(c.getColumnIndex("is_stick_return_on_unban")) == 1;
        antiSpamRule.mStickerBanAgeMsec = c.getInt(c.getColumnIndex("ban_stick_age_msec"));

        antiSpamRule.option_link_banage_val = c.getInt(c.getColumnIndex("option_links_banage_value"));
        antiSpamRule.option_links_banage_mp = c.getInt(c.getColumnIndex("option_links_banage_multiplier"));
        antiSpamRule.option_sticks_banage_val = c.getInt(c.getColumnIndex("option_sticks_banage_value"));
        antiSpamRule.option_sticks_banage_mp = c.getInt(c.getColumnIndex("option_sticks_banage_multiplier"));

        antiSpamRule.isBanForBlackWords = c.getInt(c.getColumnIndex("isBanForBlackWords")) == 1;
        antiSpamRule.isDelMsgBlackWords = c.getInt(c.getColumnIndex("isDelMsgBlackWords")) == 1;
        antiSpamRule.isBlackWordReturnOnBanExp = c.getInt(c.getColumnIndex("isBlackWordReturnOnBanExp")) == 1;
        antiSpamRule.option_blackword_banage_val = c.getInt(c.getColumnIndex("option_blackword_banage_val"));
        antiSpamRule.option_blackword_banage_mp = c.getInt(c.getColumnIndex("option_blackword_banage_mp"));
        antiSpamRule.mBlackWordBanAgeMsec = c.getInt(c.getColumnIndex("ban_blackword_age_msec"));
        antiSpamRule.isAlertAboutWordBan = c.getInt(c.getColumnIndex("isAlertOnBanWord")) == 1;
        antiSpamRule.isWarnBeforeBanForWord = c.getInt(c.getColumnIndex("isWarnBeforeBanForWord")) == 1;
        antiSpamRule.mWarnTextBlackWords = c.getString(c.getColumnIndex("ban_words_warn_text"));
        antiSpamRule.banWordsFloodLimit = c.getInt(c.getColumnIndex("banWordsFloodLimit"));
        antiSpamRule.isWarnBeforeLinksBan = c.getInt(c.getColumnIndex("is_warn_before_links_ban")) == 1;

        antiSpamRule.isRemoveJoinMessage = c.getInt(c.getColumnIndex("is_remove_join_msg")) == 1;
        antiSpamRule.isRemoveLeaveMessage = c.getInt(c.getColumnIndex("is_remove_leaved_msg")) == 1;

        c.close();
        return antiSpamRule;
    }
*/


    public void saveAntispamTask(ChatTask task) {
        ContentValues cv = new ContentValues();
        cv.put("type", task.mType.name());
        cv.put("chat_id", task.chat_id);
        cv.put("allow_count", task.mAllowCountPerUser);
        cv.put("ban_age_sec", task.mBanTimeSec);
        cv.put("is_return_on_ban_expired", task.isReturnOnBanExpired);
        cv.put("warn_text_first", task.mWarnTextFirst);
        cv.put("warn_text_last", task.mWarnTextLast);
        cv.put("within_time_sec", task.mWarningsDuringTime);
        cv.put("warn_freq", task.mWarnFrequency);
        cv.put("is_ban", task.isBanUser);
        cv.put("is_remove_msg", task.isRemoveMessage);
        cv.put("isEnabled", task.isEnabled);

        try {
            db.insertWithOnConflict(TABLE_CHAT_TASKS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    /*
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
    */

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
            ban.isReturnToChat = c.getInt(c.getColumnIndex("is_return_on_unban")) == 1;

            c.close();
            return ban;
        }
        return null;
    }

    public void addLinkToWhiteList(String link) {
        ContentValues c = new ContentValues(1);
        c.put("link", link);
        try {
            db.insertWithOnConflict(TABLE_WHITE_LINKS, null, c, SQLiteDatabase.CONFLICT_IGNORE);
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void saveLinksWhiteList(String[] links) {
        try {
            db.delete(TABLE_WHITE_LINKS, null, null);
            if (links.length == 0)
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

    public ArrayList<BannedWord> getWordsBlackList(long chatID, int offset, int count) {
        return getWordsBlackList(chatID, offset, count, false);
    }

    public int getWordsBlackListCount(long chatID) {
        return getCount(TABLE_BLACKLIST_WORDS, "chatId=?", chatID + "");
    }

    /**
     * @return max 100 words or empty list
     */
    public ArrayList<BannedWord> getWordsBlackList(final long chatId, int offset, int count, boolean isReturnEnabled) {
        //int count = 100;
        ArrayList<BannedWord> list = new ArrayList<>();
        String filter = "";
        if (isReturnEnabled) // filter for return only active words
            filter = " AND (isRemoveMsg=1 OR isBan=1) ";

        try {
            Cursor c = getTable("SELECT * FROM " + TABLE_BLACKLIST_WORDS + " WHERE chatId=? " + filter + " ORDER BY _id DESC LIMIT " + offset + ", " + count,
                    new String[]{chatId + ""});
            if (c != null) {
                do {
                    BannedWord word = new BannedWord();
                    word.id = c.getInt(c.getColumnIndex("_id"));
                    word.word = c.getString(c.getColumnIndex("word"));
                    word.isDeleteMsg = c.getInt(c.getColumnIndex("isRemoveMsg")) == 1;
                    word.isBanUser = c.getInt(c.getColumnIndex("isBan")) == 1;

                    list.add(word);
                } while (c.moveToNext());
                c.close();
            }
        } catch (Exception e) {
            MyLog.log(e);
        }
        return list;
    }


    public long addWordToBlackList(final long chatId, final String word, boolean isDeleteMessage, boolean isBanUserForBlackWord) {
        try {
            ContentValues c = new ContentValues(2);
            c.put("chatId", chatId);
            c.put("word", word);
            c.put("isBan", isBanUserForBlackWord);
            c.put("isRemoveMsg", isDeleteMessage);
            long id = db.insertWithOnConflict(TABLE_BLACKLIST_WORDS, null, c, SQLiteDatabase.CONFLICT_IGNORE);
            return id;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
    public void saveWordsBlackList(final long chatId, final String[] words) {
        try {
            db.delete(TABLE_BLACKLIST_WORDS, null, null);
            if (words.length == 0)
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
    */

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
        String sql = "SELECT * FROM " + TABLE_LOG_ACTIONS + " ORDER BY _id DESC LIMIT " + offset + ", 5";
        Cursor c = getTable(sql);

        if (c != null) {
            ArrayList<LogUtil.LogEntity> logs = new ArrayList<>(c.getCount());
            do {
                String action = c.getString(c.getColumnIndex("action"));
                String jsonStr = c.getString(c.getColumnIndex("jsonData"));

                LogUtil.LogEntity log = new LogUtil.LogEntity();
                log.action = LogUtil.Action.valueOf(action);
                log.jsonData = jsonStr;
                log.item_id = c.getInt(c.getColumnIndex("_id"));
                logs.add(log);

            } while (c.moveToNext());
            c.close();
            return logs;
        }

        return null;
    }

    public ArrayList<LogUtil.LogEntity> getLogUpdate(int id) {
        String sql = "SELECT * FROM " + TABLE_LOG_ACTIONS + " WHERE _id > " + id + " ORDER BY _id DESC";
        Cursor c = getTable(sql);

        if (c != null) {
            ArrayList<LogUtil.LogEntity> logs = new ArrayList<>(c.getCount());
            do {
                String action = c.getString(c.getColumnIndex("action"));
                String jsonStr = c.getString(c.getColumnIndex("jsonData"));

                LogUtil.LogEntity log = new LogUtil.LogEntity();
                log.action = LogUtil.Action.valueOf(action);
                log.jsonData = jsonStr;
                log.item_id = c.getInt(c.getColumnIndex("_id"));
                logs.add(log);

            } while (c.moveToNext());
            c.close();
            return logs;
        }

        return null;
    }

    /**
     * @param sec срок действия предупреждения, в секундах.
     * @return
     */
    public int getAntiSpamWarnCount(ChatTask.TYPE type, long chatId, int userId, long sec) {
        long timestamp_value = sec > 0 ? System.currentTimeMillis() - (sec * 1000) : 0; // 0 - whole time, else last violated time.
        String sql = "SELECT count FROM " + TABLE_ANTISPAM_WARNS + " WHERE action=?" +
                " AND chatId=" + chatId + " AND userId=" + userId +
                " AND ts > " + timestamp_value;
        try {
            Cursor c = getTable(sql, type.name());
            if (c != null) {
                int count = c.getInt(c.getColumnIndex("count"));
                c.close();
                return count;
            }
        } catch (Exception e) {
            MyLog.log(e);
        }

        return 0;
    }

    public void setAntiSpamWarnCount(ChatTask.TYPE type, long chatId, int fromId, int attemptsCount) {
        ContentValues cv = new ContentValues(4);
        cv.put("action", type.name());
        cv.put("chatId", chatId);
        cv.put("userId", fromId);
        cv.put("ts", System.currentTimeMillis());
        cv.put("count", attemptsCount);
        try {
            db.insertWithOnConflict(TABLE_ANTISPAM_WARNS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {

        }
    }

    public void resetWarnCountForChat(long chatId) {
        try {
            long id = db.delete(TABLE_ANTISPAM_WARNS, "chatId=?", new String[]{chatId + ""});
            MyLog.log("Deleted: " + id);
        } catch (Exception e) {

        }
    }

    public void deleteAntiSpamWarnCount(ChatTask.TYPE type, long chatId, int fromId) {
        db.delete(TABLE_ANTISPAM_WARNS, "action=? AND chatId=? AND userId=?", new String[]{type.name(), chatId + "", fromId + ""});
    }

    public void clearBanList(long chat_id) {
        try {
            // return getCount(TABLE_AUTO_KICK, "chat_id=?", chatId + "");
            db.delete(TABLE_AUTO_KICK, "chat_id=?", new String[]{chat_id + ""});
            db.delete(BAN_INFO_TABLE, "chat_id=?", new String[]{chat_id + ""});
        } catch (Exception e) {

        }
    }

    public void clearLog() {
        db.delete(TABLE_LOG_ACTIONS, null, null);
    }

    public void clearOldLog() {
        long ts = Sets.getLong(Const.LOG_LIFE_TIME, AlarmManager.INTERVAL_DAY);
        ts = System.currentTimeMillis() - ts;
        try {
            int count = db.delete(TABLE_LOG_ACTIONS, "ts<?", new String[]{ts + ""});
            MyLog.log("old log cleared: " + count);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getWelcomeText(final long chatId) {
        try {
            Cursor c = getTable("SELECT text FROM " + TABLE_CHAT_WELCOME + " WHERE chatId=? AND isEnabled=1 AND text IS NOT NULL AND text !='' ", new String[]{chatId + ""});
            if (c != null) {
                String text = c.getString(c.getColumnIndex("text"));
                c.close();
                return text;
            }
        } catch (Exception e) {

        }
        return null;
    }

    public Object[] getWelcomeTextObject(long chatId) {
        try {
            Cursor c = getTable("SELECT * FROM " + TABLE_CHAT_WELCOME + " WHERE chatId=?", new String[]{chatId + ""});
            if (c != null) {
                boolean enabled = c.getInt(c.getColumnIndex("isEnabled")) == 1;
                String text = c.getString(c.getColumnIndex("text"));
                c.close();
                return new Object[]{enabled, text};
            }
        } catch (Exception e) {

        }
        return null;
    }

    public void setWelcomeText(long chatId, String text, boolean isEnabled) {
        ContentValues cv = new ContentValues(3);
        cv.put("chatId", chatId);
        cv.put("isEnabled", isEnabled);
        cv.put("text", text);
        try {
            long id = db.insertWithOnConflict(TABLE_CHAT_WELCOME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            MyLog.log("save welocme text id: " + id);
        } catch (Exception e) {

        }
    }

    public void updateBannedWord(BannedWord word) {
        ContentValues cv = new ContentValues(4);
        cv.put("word", word.word);
        cv.put("isBan", word.isBanUser);
        cv.put("isRemoveMsg", word.isDeleteMsg);
        try {
            db.update(TABLE_BLACKLIST_WORDS, cv, "_id=?", new String[]{word.id + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }


    public void deleteBannedWord(long id) {
        try {
            db.delete(TABLE_BLACKLIST_WORDS, "_id=?", new String[]{id + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void saveWarnText(String text, boolean isFirstWarn, int id) {
        ContentValues cv = new ContentValues();
        if (isFirstWarn)
            cv.put("warn_text_first", text);
        else
            cv.put("warn_text_last", text);

        try {
            db.update(TABLE_CHAT_TASKS, cv, "_id=?", new String[]{id + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void clearBanWords(long chatID) {
        try {
            db.delete(TABLE_BLACKLIST_WORDS, "chatId=?", new String[]{chatID + ""});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BannedWord> getAllWordsBlackList(long chatIDExclude) {
        String sql = "SELECT * FROM " + TABLE_BLACKLIST_WORDS + " WHERE NOT chatId=? LIMIT 500";
        ArrayList<BannedWord> list = new ArrayList<>();
        try {
            Cursor c = db.rawQuery(sql, new String[]{chatIDExclude + ""});
            if (c.moveToFirst())
                do {
                    BannedWord word = new BannedWord();
                    word.id = c.getInt(c.getColumnIndex("_id"));
                    word.word = c.getString(c.getColumnIndex("word"));
                    word.isDeleteMsg = c.getInt(c.getColumnIndex("isRemoveMsg")) == 1;
                    word.isBanUser = c.getInt(c.getColumnIndex("isBan")) == 1;

                    list.add(word);
                } while (c.moveToNext());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void cacheChats(ArrayList<JSONObject> chatsList) {
        try {
            db.beginTransaction();

            db.delete(TABLE_CHATS_LIST, null, null);
            ContentValues cv = new ContentValues(3);
            for (JSONObject chat : chatsList) {
                cv.put("chat_id", chat.getLong("id"));
                cv.put("json_chat_info", chat.toString());
                cv.put("chat_order", chat.getLong("order"));
                db.insert(TABLE_CHATS_LIST, null, cv);
                cv.clear();
            }

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public ArrayList<TdApi.Chat> getCacheChats() {
        String sql = "SELECT * FROM " + TABLE_CHATS_LIST + " ORDER BY chat_order DESC";
        ArrayList<TdApi.Chat> list = new ArrayList<>();
        try {
            Cursor c = db.rawQuery(sql, null);

            if (c.moveToFirst()) {
                do {
                    String json = c.getString(c.getColumnIndex("json_chat_info"));
                    JSONObject jChat = new JSONObject(json);

                    TdApi.Chat chat1 = new TdApi.Chat();
                    chat1.id = jChat.getLong("id");
                    chat1.title = jChat.getString("title");

                    /*
                    if(jChat.has("photo_id")){
                        TdApi.ChatPhoto photo = new TdApi.ChatPhoto();
                        photo.small = new TdApi.File();
                        photo.small.id = jChat.getInt("photo_id");
                        chat1.photo = photo;
                    }
                    */

                    int role1 = jChat.getInt("role");
                    TdApi.ChatMemberStatus roleStatus;
                    if (role1 == TdApi.ChatMemberStatusCreator.CONSTRUCTOR)
                        roleStatus = new TdApi.ChatMemberStatusCreator();
                    else if (role1 == TdApi.ChatMemberStatusEditor.CONSTRUCTOR)
                        roleStatus = new TdApi.ChatMemberStatusEditor();
                    else
                        roleStatus = new TdApi.ChatMemberStatusModerator();

                    if (TgUtils.isGroup(jChat.getInt("type"))) {
                        TdApi.GroupChatInfo info = new TdApi.GroupChatInfo();
                        info.group = new TdApi.Group();
                        info.group.status = roleStatus;
                        chat1.type = info;
                    } else if (TgUtils.isSuperGroup(jChat.getInt("type"))) {
                        TdApi.ChannelChatInfo info = new TdApi.ChannelChatInfo();
                        info.channel = new TdApi.Channel();
                        info.channel.status = roleStatus;
                        info.channel.id = jChat.getInt("channel_id");
                        if (!jChat.optBoolean("isChannel", false))
                            info.channel.isSupergroup = true;
                        chat1.type = info;
                        if (jChat.has("username")) {
                            info.channel.username = jChat.getString("username");
                        }

                    }
                    list.add(chat1);

                } while (c.moveToNext());
            }
            c.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addChatCommand(ChatCommand cmd) {
        ContentValues cv = new ContentValues(6);
        cv.put("chat_id", cmd.chatId);
        cv.put("type", cmd.type);
        cv.put("command", cmd.cmd);
        cv.put("answer", cmd.answer);
        cv.put("is_admin", cmd.isAdmin);
        cv.put("is_enabled", cmd.isEnabled);
        try {
            long id = db.insertWithOnConflict(TABLE_CHAT_COMMAND, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            cmd.id = id;
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public int getChatCommandsCount(long chatId) {
        String sql = "SELECT COUNT(1) as count FROM " + TABLE_CHAT_COMMAND + " WHERE chat_id=?";

        try {
            Cursor c = getTable(sql, new String[]{chatId + ""});
            if (c != null) {
                int count = c.getInt(c.getColumnIndex("count"));

                c.close();
                return count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public ArrayList<ChatCommand> getChatCommands(long chatId) {
        String sql = "SELECT * FROM " + TABLE_CHAT_COMMAND + " WHERE chat_id=?";
        ArrayList<ChatCommand> list = new ArrayList<>();
        try {
            Cursor c = getTable(sql, new String[]{chatId + ""});
            if (c != null) {
                do {
                    ChatCommand cmd = new ChatCommand();
                    cmd.id = c.getInt(c.getColumnIndex("_id"));
                    cmd.chatId = c.getInt(c.getColumnIndex("chat_id"));
                    cmd.type = c.getInt(c.getColumnIndex("type"));
                    cmd.cmd = c.getString(c.getColumnIndex("command"));
                    cmd.answer = c.getString(c.getColumnIndex("answer"));
                    cmd.isAdmin = c.getInt(c.getColumnIndex("is_admin")) == 1;
                    cmd.isEnabled = c.getInt(c.getColumnIndex("is_enabled")) == 1;
                    list.add(cmd);
                } while (c.moveToNext());
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateCommand(ChatCommand cmd) {
        ContentValues cv = new ContentValues(6);
        // cv.put("chat_id", cmd.chatId);
        cv.put("type", cmd.type);
        cv.put("command", cmd.cmd);
        cv.put("answer", cmd.answer);
        cv.put("is_admin", cmd.isAdmin);
        cv.put("is_enabled", cmd.isEnabled);
        try {
            db.update(TABLE_CHAT_COMMAND, cv, "_id=?", new String[]{cmd.id + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void clearChatCommands(long chatId) {
        try {
            db.delete(TABLE_CHAT_COMMAND, "chat_id=?", new String[]{chatId + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public ChatCommand getChatCommand(long chatId, String command) {
        try {
            Cursor c = getTable("SELECT * FROM " + TABLE_CHAT_COMMAND + " WHERE chat_id=? AND command=? LIMIT 1", new String[]{chatId + "", command});
            if (c != null) {
                ChatCommand cmd = new ChatCommand();
                cmd.id = c.getInt(c.getColumnIndex("_id"));
                cmd.chatId = c.getInt(c.getColumnIndex("chat_id"));
                cmd.type = c.getInt(c.getColumnIndex("type"));
                cmd.cmd = c.getString(c.getColumnIndex("command"));
                cmd.answer = c.getString(c.getColumnIndex("answer"));
                cmd.isAdmin = c.getInt(c.getColumnIndex("is_admin")) == 1;
                cmd.isEnabled = c.getInt(c.getColumnIndex("is_enabled")) == 1;

                c.close();
                return cmd;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteChatCommand(long id) {
        try {
            db.delete(TABLE_CHAT_COMMAND, "_id=?", new String[]{id + ""});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
