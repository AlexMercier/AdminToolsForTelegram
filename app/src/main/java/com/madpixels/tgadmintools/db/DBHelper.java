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
import com.madpixels.tgadmintools.App;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.adapters.AdapterChatUsersLocal;
import com.madpixels.tgadmintools.entities.BanTask;
import com.madpixels.tgadmintools.entities.BannedWord;
import com.madpixels.tgadmintools.entities.BotToken;
import com.madpixels.tgadmintools.entities.ChatCommand;
import com.madpixels.tgadmintools.entities.ChatLogInfo;
import com.madpixels.tgadmintools.entities.ChatParticipantBan;
import com.madpixels.tgadmintools.entities.ChatTask;
import com.madpixels.tgadmintools.entities.LogEntity;
import com.madpixels.tgadmintools.helper.TelegramBot;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.services.ServiceAutoKicker;
import com.madpixels.tgadmintools.utils.CommonUtils;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.drinkless.td.libcore.telegram.TdApi;
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

        if (oldVersion < 4) {
            upgradeTableWelcomeTexts();
        }
    }

    private void upgradeTableWelcomeTexts() {
        /** Move Welcome texts from old db version to new as chat task
         */
        Cursor c = getTable("SELECT * FROM chat_welcome_text WHERE text IS NOT NULL AND text !='' ");
        if (c != null) {
            ArrayList<ContentValues> cvList = new ArrayList<>();

            do {
                String text = c.getString(c.getColumnIndex("text"));
                long chatId = c.getLong(c.getColumnIndex("chatId"));
                boolean isEnabled = c.getInt(c.getColumnIndex("isEnabled")) == 1;

                ContentValues cv = new ContentValues(4);
                cv.put("type", ChatTask.TYPE.WELCOME_USER.name());
                cv.put("chat_id", chatId);
                cv.put("isEnabled", isEnabled);
                cv.put("text", text);
                cvList.add(cv);
            } while (c.moveToNext());
            c.close();
            //Insert:
            for (ContentValues cv : cvList) {
                db.insertWithOnConflict(TABLE_CHAT_TASKS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            }
        }
        db.execSQL("DROP TABLE chat_welcome_text");
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
                saveChatTask(task);
            }

            if (c.getInt(c.getColumnIndex("is_remove_leaved_msg")) == 1) {
                ChatTask task = new ChatTask(ChatTask.TYPE.LeaveMsg, chat_id);
                task.isEnabled = true;
                saveChatTask(task);
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

                saveChatTask(task);
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

                saveChatTask(task);
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

                String warnText = c.getString(c.getColumnIndex("ban_words_warn_text"));
                if (!TextUtils.isEmpty(warnText))
                    task.mWarnTextFirst = warnText;

                if (c.getInt(c.getColumnIndex("isWarnBeforeBanForWord")) == 1)
                    task.mWarnFrequency = 3;

                saveChatTask(task);

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


    /**
     * check <b>value>0</b> in column `count` on query
     */
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

    public void addToBanList(TdApi.User user, long chat_id, int chatType, int groupIdOrChannelId, long ban_age_msec,
                             boolean isReturnOnUnban, String banReason, boolean isMuteInsteadBan) {
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
        cv.put("is_mute_ban", isMuteInsteadBan);
        // cv.put("reason", 0);

        try {
            long id = db.insertWithOnConflict(TABLE_BAN_INFO, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
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
        String sql = "SELECT user_id, ban_date_ts+ban_age_msec as targetTime   FROM " + TABLE_BAN_INFO +
                " WHERE ban_age_msec>0 " +
                " ORDER BY targetTime ASC LIMIT 1";
        Cursor c = getTable(sql);
        if (c != null) {
            do {
                String user_id = c.getString(c.getColumnIndex("user_id"));
                long targetTime = c.getLong(c.getColumnIndex("targetTime"));
                MyLog.log(user_id + ": " + targetTime);
                c.close();
                String s = CommonUtils.tsToDate(targetTime / 1000);
                MyLog.log("targetTime: " + s);
                return targetTime;
            }
            while (c.moveToNext());
        }
        return 0;
    }

    public BanTask getBanTask(long chat_id, int user_id){
        String sql = "SELECT _id, chat_id, from_id, chatType, user_id, user_login, is_return_on_unban, " +
                "is_mute_ban, unban_errors,  ban_date_ts+ban_age_msec as targetTime FROM " +
                TABLE_BAN_INFO + " WHERE chat_id=? AND user_id=?";
        Cursor c = getTable(sql, chat_id+"", user_id+"");
        if(c!=null){
            BanTask bt = new BanTask();
            bt.task_id = c.getLong(c.getColumnIndex("_id"));
            bt.chat_id = c.getLong(c.getColumnIndex("chat_id"));
            bt.from_id = c.getInt(c.getColumnIndex("from_id"));
            bt.chatType = c.getInt(c.getColumnIndex("chatType"));
            bt.user_id =  c.getInt(c.getColumnIndex("user_id"));
            bt.isReturnOnUnban = c.getInt(c.getColumnIndex("is_return_on_unban")) == 1;
            bt.user_id = c.getInt(c.getColumnIndex("user_id"));
            bt.unbanErrors = c.getInt(c.getColumnIndex("unban_errors"));
            bt.username = c.getString(c.getColumnIndex("user_login"));
            bt.isMutedBan = c.getInt(c.getColumnIndex("is_mute_ban"))==1;
            c.close();
            return  bt;
        }
        return null;
    }




    public ArrayList<BanTask> getExpairedBanList() {

        final long expaired_ts = System.currentTimeMillis();
        String sql = "SELECT _id, chat_id, from_id, chatType, user_id, user_login, is_return_on_unban, unban_errors,  ban_date_ts+ban_age_msec as targetTime FROM " +
                TABLE_BAN_INFO +
                " WHERE ban_age_msec>0 AND targetTime<=" + expaired_ts +
                " ORDER BY targetTime DESC";
        Cursor c = getTable(sql);
        if (c != null) {
            ArrayList<BanTask> list = new ArrayList<>(c.getCount());
            do {
                //String user_id = c.getString(c.getColumnIndex("user_id"));
                //long targetTime = c.getLong(c.getColumnIndex("targetTime"));
                //MyLog.log(user_id + ": " + targetTime);

                BanTask bt = new BanTask();
                bt.task_id = c.getLong(c.getColumnIndex("_id"));
                bt.chat_id = c.getLong(c.getColumnIndex("chat_id"));
                bt.from_id = c.getInt(c.getColumnIndex("from_id"));
                bt.chatType = c.getInt(c.getColumnIndex("chatType"));
                bt.user_id =  c.getInt(c.getColumnIndex("user_id"));
                bt.isReturnOnUnban = c.getInt(c.getColumnIndex("is_return_on_unban")) == 1;
                bt.user_id = c.getInt(c.getColumnIndex("user_id"));
                bt.unbanErrors = c.getInt(c.getColumnIndex("unban_errors"));
                bt.username = c.getString(c.getColumnIndex("user_login"));

                //String stime = Utils.TimestampToDate(targetTime / 1000);
                //MyLog.log("test time: " + stime);

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
            db.delete(TABLE_BAN_INFO, "_id=?", new String[]{ban.task_id + ""});
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
            db.update(TABLE_BAN_INFO, cv, "_id=?", new String[]{ban.task_id + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void removeBanTask(long chatId, int userId) {
        try {
            int count = db.delete(TABLE_BAN_INFO, "chat_id=? AND user_id=?", new String[]{chatId + "", userId + ""});
            MyLog.log("deleted count " + count);
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void deleteGroup(long chat_id) {
        try {
            int c = db.delete(TABLE_BAN_INFO, "chat_id=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup BAN_INFO_TABLE: " + c);

            c = db.delete(TABLE_AUTO_KICK, "chat_id=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup TABLE_AUTO_KICK: " + c);

            c = db.delete(TABLE_CHAT_TASKS, "chat_id=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup TABLE_ANTISPAM: " + c);

            c = db.delete(TABLE_TASK_WARNS, "chatId=?", new String[]{chat_id + ""});
            MyLog.log("DeleteGroup TABLE_ANTISPAM_WARNS: " + c);
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void removeBannedUser(long chat_id, int user_id) {
        try {
            int size = db.delete(TABLE_BAN_INFO, "chat_id=? AND user_id=?", new String[]{chat_id + "", user_id + ""});
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
        String sql = "SELECT * FROM " + TABLE_BAN_INFO + " WHERE chat_id=" + chat_id + " ORDER BY _id ASC LIMIT " + offset + ",100 ";
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

    public boolean isChatRulesExists() {
        String sql = "SELECT COUNT(1) FROM " + TABLE_CHAT_TASKS + " WHERE is_ban=1 OR is_remove_msg=1 OR isEnabled=1;";
        return isRowExists(sql);
        //    return true;

       // String sql = "SELECT COUNT(1) FROM " + TABLE_CHAT_WELCOME + " WHERE isEnabled=1";

        //boolean welomeTextExists = isRowExists(sql);
       // return welomeTextExists;
    }

    public ArrayList<ChatTask> getChatTasks(long chat_id, boolean onlyActiveTasks) {
        String filterActiveTasks = " AND ( (type<>'MutedUsers' AND (is_ban=1 OR is_remove_msg=1 OR isEnabled=1)) " +
                " OR (type='MutedUsers' AND isEnabled=1) )";
        String sql = "SELECT * FROM " + TABLE_CHAT_TASKS + " WHERE chat_id=? ";
        if (onlyActiveTasks)
            sql += filterActiveTasks;
        Cursor c = getTable(sql, String.valueOf(chat_id));
        return getChatTasks(c);
    }

    public ChatTask getChatTask(long chat_id, ChatTask.TYPE type) {
        String sql = "SELECT * FROM " + TABLE_CHAT_TASKS + " WHERE chat_id=? AND type=?";
        Cursor c = getTable(sql, String.valueOf(chat_id), type.name());
        ArrayList<ChatTask> tasks = getChatTasks(c);
        if(tasks!=null && tasks.size()>0)
            return tasks.get(0);
        return null;
    }

    private ArrayList<ChatTask> getChatTasks(Cursor c) {
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
            task.mText = c.getString(c.getColumnIndex("text"));
            task.mWarningsDuringTime = c.getLong(c.getColumnIndex("within_time_sec"));
            task.mWarnFrequency = c.getInt(c.getColumnIndex("warn_freq"));
            task.isBanUser = c.getInt(c.getColumnIndex("is_ban")) == 1;
            task.isRemoveMessage = c.getInt(c.getColumnIndex("is_remove_msg")) == 1;
            task.isEnabled = c.getInt(c.getColumnIndex("isEnabled")) == 1;
            task.isPublicToChat = c.getInt(c.getColumnIndex("is_public_alert")) == 1;
            task.isMuteInsteadBan = c.getInt(c.getColumnIndex("is_mute_on_ban"))==1;

            tasks.add(task);
        } while (c.moveToNext());
        c.close();

        return tasks;
    }

    public void saveChatTask(ChatTask task) {
        ContentValues cv = new ContentValues();
        cv.put("type", task.mType.name());
        cv.put("chat_id", task.chat_id);
        cv.put("allow_count", task.mAllowCountPerUser);
        cv.put("ban_age_sec", task.mBanTimeSec);
        cv.put("is_return_on_ban_expired", task.isReturnOnBanExpired);
        cv.put("warn_text_first", task.mWarnTextFirst);
        cv.put("warn_text_last", task.mWarnTextLast);
        cv.put("text", task.mText);
        cv.put("within_time_sec", task.mWarningsDuringTime);
        cv.put("warn_freq", task.mWarnFrequency);
        cv.put("is_ban", task.isBanUser);
        cv.put("is_remove_msg", task.isRemoveMessage);
        cv.put("isEnabled", task.isEnabled);
        cv.put("is_public_alert", task.isPublicToChat);
        cv.put("is_mute_on_ban", task.isMuteInsteadBan);


        try {
            long id = db.insertWithOnConflict(TABLE_CHAT_TASKS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            task.id = (int) id; // update task id if Replace was
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public boolean isBannedById(long chat_id, int user_id){
        int count = getCount(TABLE_BAN_INFO, "chat_id=? AND user_id=?", chat_id+"", user_id+"");
        return count>0;
    }

    public ChatParticipantBan getBanById(long chat_id, int user_id) {
        String sql = "SELECT * FROM " + TABLE_BAN_INFO + " WHERE chat_id=" + chat_id + " AND user_id=" + user_id;
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

    public ArrayList<LogEntity> getLog(int offset, int count) {
        String sql = "SELECT * FROM " + TABLE_LOG_ACTIONS + " ORDER BY _id DESC LIMIT " + offset + ", " + count;
        Cursor c = getTable(sql);

        if (c != null) {
            ArrayList<LogEntity> logs = new ArrayList<>(c.getCount());
            do {
                String action = c.getString(c.getColumnIndex("action"));
                String jsonStr = c.getString(c.getColumnIndex("jsonData"));

                LogEntity log = new LogEntity();
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

    public ArrayList<LogEntity> getLogUpdate(int id) {
        String sql = "SELECT * FROM " + TABLE_LOG_ACTIONS + " WHERE _id > " + id + " ORDER BY _id DESC";
        Cursor c = getTable(sql);

        if (c != null) {
            ArrayList<LogEntity> logs = new ArrayList<>(c.getCount());
            do {
                String action = c.getString(c.getColumnIndex("action"));
                String jsonStr = c.getString(c.getColumnIndex("jsonData"));

                LogEntity log = new LogEntity();
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
    public int getTaskWarnedCount(ChatTask.TYPE type, long chatId, int userId, long sec) {
        long timestamp_value = sec > 0 ? System.currentTimeMillis() - (sec * 1000) : 0; // 0 - whole time, else last violated time.
        String sql = "SELECT count FROM " + TABLE_TASK_WARNS + " WHERE action=?" +
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
            db.insertWithOnConflict(TABLE_TASK_WARNS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {

        }
    }

    public void resetWarnCountForChat(long chatId) {
        try {
            long id = db.delete(TABLE_TASK_WARNS, "chatId=?", new String[]{chatId + ""});
            MyLog.log("Deleted: " + id);
        } catch (Exception e) {

        }
    }

    public void clearWarnsCountForChatTask(long chatId, ChatTask.TYPE pType) {
        try {
            long id = db.delete(TABLE_TASK_WARNS, "chatId=? AND action=?", new String[]{chatId + "", pType.name()});
            MyLog.log("Deleted: " + id);
        } catch (Exception e) {

        }
    }


    public void deleteAntiSpamWarnCount(ChatTask.TYPE type, long chatId, int userId) {
        db.delete(TABLE_TASK_WARNS, "action=? AND chatId=? AND userId=?", new String[]{type.name(), chatId + "", userId + ""});
    }

    public void clearBanList(long chat_id) {
        try {
            // return getCount(TABLE_AUTO_KICK, "chat_id=?", chatId + "");
            db.delete(TABLE_AUTO_KICK, "chat_id=?", new String[]{chat_id + ""});
            db.delete(TABLE_BAN_INFO, "chat_id=?", new String[]{chat_id + ""});
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
        } catch (Exception e) {
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

    public void removeChatTask(int taskID) {
        if (taskID == 0)
            return;

        try {
            db.delete(TABLE_CHAT_TASKS, "_id=?", new String[]{taskID + ""});
        } catch (Exception e) {
            MyLog.log(e);
        }
    }



    public void setChatLogTargetChat(long chatId, long id) {
        ContentValues cv = new ContentValues(3);
        cv.put("chat_id", chatId);
        cv.put("chat_id_log", id);

        updateChatLogInfo(chatId, cv);
    }

    public void setChatLogEnabled(long chatId, boolean isEnabled) {
        ContentValues cv = new ContentValues(3);
        cv.put("chat_id", chatId);
        cv.put("isEnabled", isEnabled);
        updateChatLogInfo(chatId, cv);
    }


    public ChatLogInfo getLogEventsToChat(long chatID, boolean isReturnIfEnabbled) {
        String WHERE = isReturnIfEnabbled ? " AND (isEnabled=1 and chat_id_log!=0)" : "";
        String sql = "SELECT * FROM " + TABLE_CHAT_LOG + " WHERE chat_id=? " + WHERE + " LIMIT 1";
        Cursor c = getTable(sql, chatID + "");
        if (c != null) {
            if (c.moveToFirst()) {
                boolean isEnabled = c.getInt(c.getColumnIndex("isEnabled")) == 1;
                long chatLogId = c.getLong(c.getColumnIndex("chat_id_log"));
                ChatLogInfo chatLogInfo = new ChatLogInfo();
                chatLogInfo.chatID = chatID;
                chatLogInfo.chatLogID = chatLogId;
                chatLogInfo.isEnabled = isEnabled;
                c.close();
                return chatLogInfo;
            }
            c.close();
        }
        return null;
    }

    private void updateChatLogInfo(long chatId, ContentValues cv) {
        try {
            if (isRowExists(TABLE_CHAT_LOG, "chat_id", chatId + "")) {
                db.update(TABLE_CHAT_LOG, cv, "chat_id=?", new String[]{chatId + ""});
            } else {
                db.insert(TABLE_CHAT_LOG, null, cv);
            }
        } catch (Exception e) {
            MyLog.log(e);
        }
    }

    public void addMutedUser(long chatID, int userID, String username) {
        ContentValues cv = new ContentValues();
        cv.put("chat_id", chatID);
        cv.put("user_id", userID);
        cv.put("user_name", username.trim());
        try {
            db.insertWithOnConflict(TABLE_MUTED_USERS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            MyLog.log(e);
        }
    }


    public boolean isUserMuted(long chatId, int senderUserId) {
        String sql = "SELECT COUNT(1) FROM " + TABLE_MUTED_USERS + " WHERE chat_id=? AND user_id=?";
        Cursor c = getTable(sql, chatId + "", senderUserId + "");
        return isRowExists(c);
    }

    public int getMutedCount(long chatId) {
        return getCount(TABLE_MUTED_USERS, "chat_id=?", chatId + "");

    }

    /**
     * @return return a list of "local" users, only id and firstName given.
     */
    public ArrayList<AdapterChatUsersLocal.ChatMemberUser> getMutedUsers(long chatlId, int localOffset, int count) {
        String sql = "SELECT * FROM " + TABLE_MUTED_USERS + " WHERE chat_id=? ORDER BY _id LIMIT " + localOffset + ", " + count;
        Cursor c = getTable(sql, chatlId + "");
        if (c != null) {
            ArrayList<AdapterChatUsersLocal.ChatMemberUser> users = new ArrayList<>();
            do {
                TdApi.User user = new TdApi.User();
                user.id = c.getInt(c.getColumnIndex("user_id"));
                user.firstName = c.getString(c.getColumnIndex("user_name"));

                AdapterChatUsersLocal.ChatMemberUser member = new AdapterChatUsersLocal.ChatMemberUser();
                member.localUser = user;
                member.userId = user.id;
                users.add(member);

            } while (c.moveToNext());
            c.close();
            return users;
        }
        return null;
    }

    public void removeMutedUser(long chatId, int userId) {
        try {
            db.delete(TABLE_MUTED_USERS, "chat_id=? AND user_id=?", new String[]{chatId + "", userId + ""});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int addBotToke(TelegramBot bot) {
        ContentValues cv = new ContentValues(4);
        cv.put("username", bot.username);
        cv.put("token", bot.mToken);
        cv.put("bot_id", bot.id);
        cv.put("first_name", bot.first_name);

        try {
            if(isRowExists(TABLE_BOTS_TOKEN, "username", bot.username)){
                db.update(TABLE_BOTS_TOKEN, cv, "username=?", new String[]{bot.username});
                Cursor c = getTable("SELECT _id FROM "+TABLE_BOTS_TOKEN+" WHERE username=?", bot.username);
                if(c!=null){
                    int id = c.getInt(0);
                    c.close();
                    return id;
                }
            }else{
                long local_id =  db.insert(TABLE_BOTS_TOKEN, null, cv);
                return (int) local_id;
            }
        } catch (Exception e) {
            MyLog.log(e);
        }
        return 0;
    }

    public BotToken getBotToken(String token){
        String sql = "SELECT * FROM "+TABLE_BOTS_TOKEN+" WHERE token=?";
       return getBotToken(sql, token);
    }

    public BotToken getBotToken(int bot_db_id) {
        String sql = "SELECT * FROM "+TABLE_BOTS_TOKEN+" WHERE _id=?";
        return getBotToken(sql, bot_db_id+"");
    }


    public BotToken getBotToken(String query, String whereArgs) {
        Cursor c = getTable(query, whereArgs);
        if(c!=null){
            BotToken botToken = new BotToken();
            botToken.local_id = c.getInt(c.getColumnIndex("_id"));
            botToken.mToken = c.getString(c.getColumnIndex("token"));
            botToken.mUsername = c.getString(c.getColumnIndex("username"));
            botToken.mFirstName = c.getString(c.getColumnIndex("first_name"));
            botToken.bot_id = c.getInt(c.getColumnIndex("bot_id"));
            c.close();

            return botToken;
        }
        return null;
    }

    public ArrayList<BotToken> getBotsList(){
        String sql = "SELECT * FROM "+TABLE_BOTS_TOKEN+" ORDER BY _id DESC";
        Cursor c = getTable(sql);
        if(c!=null){
            ArrayList<BotToken> list = new ArrayList<>(c.getCount());
            do {
                BotToken botToken = new BotToken();
                botToken.local_id = c.getInt(c.getColumnIndex("_id"));
                botToken.mToken = c.getString(c.getColumnIndex("token"));
                botToken.mUsername = c.getString(c.getColumnIndex("username"));
                botToken.mFirstName = c.getString(c.getColumnIndex("first_name"));
                botToken.bot_id = c.getInt(c.getColumnIndex("bot_id"));
                list.add(botToken);
            }while (c.moveToNext());
            c.close();

            return list;
        }
        return null;
    }

    public void removeBotToken(String mToken) {
        try {
            db.delete(TABLE_BOTS_TOKEN, "token=?", new String[]{mToken});
        } catch (Exception e) {

        }
    }

    public ArrayList<BanTask> getMutedBans(long chat_id) {
        String sql = "SELECT * FROM "+TABLE_BAN_INFO+" WHERE chat_id=?";
        Cursor c = getTable(sql, chat_id+"");
        if(c!=null){
            ArrayList<BanTask> list = new ArrayList<>(c.getCount());
            do{
                BanTask bt = new BanTask();
                bt.task_id = c.getLong(c.getColumnIndex("_id"));
                bt.chat_id = c.getLong(c.getColumnIndex("chat_id"));
                bt.from_id = c.getInt(c.getColumnIndex("from_id"));
                bt.chatType = c.getInt(c.getColumnIndex("chatType"));
                bt.user_id =  c.getInt(c.getColumnIndex("user_id"));
                bt.isReturnOnUnban = c.getInt(c.getColumnIndex("is_return_on_unban")) == 1;
                bt.user_id = c.getInt(c.getColumnIndex("user_id"));
                bt.unbanErrors = c.getInt(c.getColumnIndex("unban_errors"));
                bt.username = c.getString(c.getColumnIndex("user_login"));
                list.add(bt);
            }while (c.moveToNext());
            c.close();
            return list;
        }
        return null;
    }
}
