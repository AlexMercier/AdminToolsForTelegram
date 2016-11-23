package com.madpixels.tgadmintools.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.Sets;
import com.madpixels.tgadmintools.Const;

import java.util.ArrayList;

/**
 * Created by Snake on 12.01.2016.
 */
public class DB extends SQLiteOpenHelper {
    final static int DATABASE_VERSION = 3;
    SQLiteDatabase db;

    public final static String BAN_INFO_TABLE = "bans_info", TABLE_AUTO_KICK = "auto_kick_users",
    /* TABLE_ANTISPAM = "antispam_rules",*/ TABLE_WHITE_LINKS = "whitelist_links",
            TABLE_LOG_ACTIONS = "log", TABLE_ANTISPAM_WARNS = "antispam_warns",
            TABLE_CHAT_WELCOME = "chat_welcome_text", TABLE_BLACKLIST_WORDS = "blacklist_words",
            TABLE_CHAT_TASKS = "chat_tasks", TABLE_CHATS_LIST = "chats_list",
            TABLE_CHAT_COMMAND = "chat_commands";

    private final static String
            CREATE_TABLE_TEMPORARY_BANS = "CREATE TABLE " + BAN_INFO_TABLE + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "chat_id INTEGER, " +
            "user_id INTEGER," +
            "user_name TEXT, " +
            "user_login TEXT, " +
            "chatType INTEGER," +
            "from_id INTEGER, " + // id группы либо супегруппы.
            "reason TEXT," +
            "ban_date_ts INTEGER," +
            "ban_age_msec INTEGER," +
            "is_return_on_unban INTEGER, " +
            "unban_errors INTEGER DEFAULT 0, " +
            "UNIQUE(chat_id, user_id)" +
            ");",
            CREATE_TABLE_CHATS_LIST = "CREATE TABLE " + TABLE_CHATS_LIST + " (" +
                    "chat_id INTEGER, " +
                    "json_chat_info TEXT, " +
                    "chat_order INTEGER" +
                    ");",

    CREATE_TABLE_AUTO_KICK_USERS = "CREATE TABLE " + TABLE_AUTO_KICK + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "chat_id INTEGER, " +
            "user_id INTEGER, " +
            "UNIQUE(chat_id, user_id)" +
            ");",
    /*
            CREATE_TABLE_ANTISPAM = "CREATE TABLE " + TABLE_ANTISPAM + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "chat_id INTEGER UNIQUE, " +
                    "is_remove_links INTEGER, " +
                    "is_ban_links INTEGER, " +
                    "ban_links_allow_count INTEGER, " +
                    "ban_link_warn_text TEXT, " +
                    // "ban_link_watch_period_msec INTEGER," +
                    "ban_link_age_msec INTEGER, " +
                    // "is_link_unban_on_timeoff INTEGER," +
                    "is_link_return_on_unban INTEGER, " +

                    "is_remove_sticks INTEGER, " + //(запятая)
                    "is_ban_sticks INTEGER, " +
                    "ban_sticks_allow_count INTEGER," +

                    "ban_stick_warn_text TEXT, " +
                    // "ban_stick_watch_period_msec INTEGER," +
                    "ban_stick_age_msec INTEGER, " +
                    //"is_stick_unban_on_timeoff INTEGER," +
                    "is_stick_return_on_unban INTEGER, " +
                    "is_warn_before_stickers_ban INTEGER, " +
                    "is_warn_before_links_ban INTEGER, " +

                    "option_links_banage_value INTEGER," +
                    "option_links_banage_multiplier INTEGER," +
                    "option_sticks_banage_value INTEGER," +
                    "option_sticks_banage_multiplier INTEGER, " +

                    "isBanForBlackWords INTEGER," +
                    "isDelMsgBlackWords INTEGER," +
                    "option_blackword_banage_val INTEGER," +
                    "option_blackword_banage_mp INTEGER," +
                    "isBlackWordReturnOnBanExp INTEGER," +
                    "ban_blackword_age_msec INTEGER," +
                    "isAlertOnBanWord INTEGER, " +
                    "isWarnBeforeBanForWord INTEGER, " +
                    "ban_words_warn_text TEXT, " +
                    "banWordsFloodLimit INTEGER, " +

                    "is_remove_join_msg INTEGER," +
                    "is_remove_leaved_msg INTEGER" +

                    //  "is_image_flood_enabled INTEGER, " +
                    //  "image_flood_limit INTEGER," +
                    //  "option_images_banage_value INTEGER," +
                    //  "option_images_banage_multiplier INTEGER," +
                    //  "is_images_return_on_unban INTEGER" +
                    ");", */
    CREATE_TABLE_WHITE_LINKS = "CREATE TABLE " + TABLE_WHITE_LINKS + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "link TEXT UNIQUE" +
            " );'",
            CREATE_TABLE_LOG = "CREATE TABLE " + TABLE_LOG_ACTIONS + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "action TEXT, " +
                    "jsonData TEXT, " +
                    "ts INTEGER" +
                    ");",
            CREATE_TABLE_ANTISPAM_WARNS = "CREATE TABLE " + TABLE_ANTISPAM_WARNS + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "chatId INTEGER," +
                    "userId INTEGER," +
                    "action TEXT," +
                    "count INTEGER," +
                    "ts INTEGER," +
                    "UNIQUE(chatId, userId, action)" +
                    ");",
            CREATE_TABLE_CHAT_WELCOMES = "CREATE TABLE " + TABLE_CHAT_WELCOME + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "chatId INTEGER UNIQUE, " +
                    "text TEXT, " +
                    "isEnabled INTEGER" +
                    ");",
            CREATE_TABLE_BLACKLIST_WORDS = "CREATE TABLE " + TABLE_BLACKLIST_WORDS + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "chatId INTEGER, " +
                    "word TEXT, " +
                    "isBan INTEGER, " +
                    "isRemoveMsg INTEGER, " +
                    "UNIQUE(chatId, word)" +
                    ");",
            CREATE_TABLE_CHAT_TASKS = "CREATE TABLE " + TABLE_CHAT_TASKS + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "type TEXT, " +
                    "chat_id INTEGER, " +
                    "isEnabled INTEGER, " +// using only for blackwords enabled.
                    "allow_count INTEGER, " +
                    "ban_age_sec INTEGER, " + //seconds
                    // "ban_age_multiplier INTEGER, " +
                    "is_return_on_ban_expired INTEGER, " +
                    // "is_warn_before_ban INTEGER, " +
                    "warn_text_first TEXT, " +
                    "warn_text_last TEXT," +
                    "warn_freq INTEGER, " +
                    "within_time_sec INTEGER, " +
                    "is_ban INTEGER, " +
                    "is_remove_msg INTEGER," +
                    "UNIQUE(chat_id, type) " +
                    ");",
        CREATE_TABLE_CHAT_COMMANDS = "CREATE TABLE "+TABLE_CHAT_COMMAND+" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "chat_id INTEGER, " +
                "command TEXT, " +
                "type INTEGER, " +
                "is_enabled INTEGER, " +
                "is_admin INTEGER, " +
                "answer TEXT, " +
                "UNIQUE(chat_id, command) " +
                ");";


    public DB(Context context) {
        super(context, getDbName(), null, DATABASE_VERSION);
    }

    private static String getDbName() {
        int s = Sets.getInteger(Const.SETS_PROFILE_ID, 0);
        return "database_" + s + ".db";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TEMPORARY_BANS);
        db.execSQL(CREATE_TABLE_AUTO_KICK_USERS);
        // db.execSQL(CREATE_TABLE_ANTISPAM);
        db.execSQL(CREATE_TABLE_WHITE_LINKS);
        db.execSQL(CREATE_TABLE_LOG);
        db.execSQL(CREATE_TABLE_ANTISPAM_WARNS);
        db.execSQL(CREATE_TABLE_CHAT_WELCOMES);
        db.execSQL(CREATE_TABLE_BLACKLIST_WORDS);

        db.execSQL(CREATE_TABLE_CHAT_TASKS);
        db.execSQL(CREATE_TABLE_CHATS_LIST);
        db.execSQL(CREATE_TABLE_CHAT_COMMANDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 2) {
                db.execSQL(CREATE_TABLE_CHAT_TASKS);
                db.execSQL(CREATE_TABLE_CHATS_LIST);
                db.delete(TABLE_ANTISPAM_WARNS, null, null); // cleare table coz we rename some `action` types

                // change blackwords table unique index and add 2 new colums.
                db.execSQL(CREATE_TABLE_BLACKLIST_WORDS.replace(TABLE_BLACKLIST_WORDS, "table_tmp"));
                db.execSQL(" INSERT INTO table_tmp (chatID, word) SELECT chatID, word FROM " + TABLE_BLACKLIST_WORDS);
                db.execSQL("DROP TABLE " + TABLE_BLACKLIST_WORDS);
                db.execSQL("ALTER TABLE table_tmp RENAME TO " + TABLE_BLACKLIST_WORDS);
            }

            if(oldVersion<3){
                db.execSQL(CREATE_TABLE_CHAT_COMMANDS);
            }
        } catch (SQLException e) {
            MyLog.log(e);
            throw new RuntimeException(e);
        }
    }


    public Cursor getTable(String sql, String... args) {
        try {
            Cursor c = db.rawQuery(sql, args);
            if (c.moveToFirst())
                return c;
        } catch (Exception e) {
            MyLog.log(e);
        }
        return null;
    }

    public Cursor getTable(String sql) {
        return getTable(sql, null);
    }

    /**
     * @param conflictAlgorithm for insert conflict resolver
     *                          'conflictAlgorithm' =
     *                          {@link #SQLiteOpenHelper#CONFLICT_IGNORE}
     *                          OR -1 if any error
     */
    public void batchInsert(final String table, final ArrayList<ContentValues> cv, int conflictAlgorithm) {
        try {
            db.beginTransaction();
            for (ContentValues c : cv) {
                db.insertWithOnConflict(table, null, c, conflictAlgorithm);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


}

