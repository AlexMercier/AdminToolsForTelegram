package com.madpixels.tgadmintools.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    final static int DATABASE_VERSION = 1;
    SQLiteDatabase db;

    public final static String BAN_INFO_TABLE = "bans_info", TABLE_AUTO_KICK = "auto_kick_users",
            TABLE_ANTISPAM = "antispam_rules", TABLE_WHITE_LINKS = "whitelist_links",
            TABLE_LOG_ACTIONS ="log", TABLE_ANTISPAM_WARNS = "antispam_warns",
            TABLE_CHAT_WELCOME = "chat_welcome_text", TABLE_BLACKLIST_WORDS="blacklist_words";

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
            CREATE_TABLE_AUTO_KICK_USERS = "CREATE TABLE " + TABLE_AUTO_KICK + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "chat_id INTEGER, " +
                    "user_id INTEGER, " +
                    "UNIQUE(chat_id, user_id)" +
                    ");",
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
                    ");",
            CREATE_TABLE_WHITE_LINKS = "CREATE TABLE " + TABLE_WHITE_LINKS + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "link TEXT UNIQUE" +
                    " );'",
            CREATE_TABLE_LOG="CREATE TABLE "+TABLE_LOG_ACTIONS+" (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "action TEXT, " +
                    "jsonData TEXT, " +
                    "ts INTEGER" +
                    ");",
            CREATE_TABLE_ANTISPAM_WARNS="CREATE TABLE "+TABLE_ANTISPAM_WARNS+" (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "chatId INTEGER," +
                    "userId INTEGER," +
                    "action TEXT," +
                    "count INTEGER," +
                    "ts INTEGER," +
                    "UNIQUE(chatId, userId, action)" +
                    ");",
            CREATE_TABLE_CHAT_WELCOMES ="CREATE TABLE "+TABLE_CHAT_WELCOME+" (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "chatId INTEGER UNIQUE, " +
                    "text TEXT, " +
                    "isEnabled INTEGER" +
                    ");",
            CREATE_TABLE_BLACKLIST_WORDS="CREATE TABLE "+TABLE_BLACKLIST_WORDS+" (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "chatId INTEGER, " +
                    "word TEXT UNIQUE" +
                    ");";


    public DB(Context context) {
        super(context, getDbName(), null, DATABASE_VERSION);
    }

    private static String getDbName() {
        int s = Sets.getInteger(Const.SETS_PROFILE_ID, 0);
        return "database_"+s+".db";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TEMPORARY_BANS);
        db.execSQL(CREATE_TABLE_AUTO_KICK_USERS);
        db.execSQL(CREATE_TABLE_ANTISPAM);
        db.execSQL(CREATE_TABLE_WHITE_LINKS);
        db.execSQL(CREATE_TABLE_LOG);
        db.execSQL(CREATE_TABLE_ANTISPAM_WARNS);
        db.execSQL(CREATE_TABLE_CHAT_WELCOMES);
        db.execSQL(CREATE_TABLE_BLACKLIST_WORDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 6) {
            //  db.execSQL(CREATE_TABLE_BLACKLIST_WORDS);
        }
    }

    public Cursor getTable(String sql, String...args){
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
     'conflictAlgorithm' =
     * {@link #SQLiteOpenHelper#CONFLICT_IGNORE}
     * OR -1 if any error
     */
    public void batchInsert(final String table, final ArrayList<ContentValues> cv, int conflictAlgorithm){
        try {
            db.beginTransaction();
            for(ContentValues c:cv){
                db.insertWithOnConflict(table,null, c, conflictAlgorithm);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }


}

