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
    final static int DATABASE_VERSION = 6;
    public SQLiteDatabase db;

    public final static String TABLE_BAN_INFO = "bans_info", TABLE_AUTO_KICK = "auto_kick_users",
            TABLE_WHITE_LINKS = "whitelist_links",
            TABLE_LOG_ACTIONS = "log", TABLE_TASK_WARNS = "antispam_warns",
            TABLE_BLACKLIST_WORDS = "blacklist_words",
            TABLE_CHAT_TASKS = "chat_tasks", TABLE_CHATS_LIST = "chats_list",
            TABLE_CHAT_COMMAND = "chat_commands", TABLE_CHAT_LOG = "chat_log",
            TABLE_MUTED_USERS = "muted_users", TABLE_BOTS_TOKEN = "table_bots";

    private final static String
            CREATE_TABLE_TEMPORARY_BANS = "CREATE TABLE " + TABLE_BAN_INFO + " (" +
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
            "is_mute_ban INTEGER DEFAULT 0, " +// mute instead ban (delete all user messages)
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
            CREATE_TABLE_TASK_WARNS = "CREATE TABLE " + TABLE_TASK_WARNS + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "chatId INTEGER," +
                    "userId INTEGER," +
                    "action TEXT," +
                    "count INTEGER," +
                    "ts INTEGER," +
                    "UNIQUE(chatId, userId, action)" +
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
                    "is_return_on_ban_expired INTEGER, " +
                    "warn_text_first TEXT, " +
                    "warn_text_last TEXT," +
                    "text TEXT," + //some payload
                    "warn_freq INTEGER, " +
                    "within_time_sec INTEGER, " +
                    "is_ban INTEGER, " +
                    "is_remove_msg INTEGER, " +
                    "is_public_alert INTEGER, " +
                    "is_mute_on_ban INTEGER DEFAULT 0," +//notify all chat users about ban
                    "UNIQUE(chat_id, type) " +
                    ");",
            CREATE_TABLE_CHAT_COMMANDS = "CREATE TABLE " + TABLE_CHAT_COMMAND + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "chat_id INTEGER, " +
                    "command TEXT, " +
                    "type INTEGER, " +
                    "is_enabled INTEGER, " +
                    "is_admin INTEGER, " +
                    "answer TEXT, " +
                    "UNIQUE(chat_id, command) " +
                    ");",
            CREATE_TABLE_CHAT_LOG = "CREATE TABLE " + TABLE_CHAT_LOG + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "chat_id INTEGER, " +
                    "chat_id_log INTEGER," +
                    "isEnabled INTEGER" +
                    "" +
                    ");",
            CREATE_TABLE_MUTED_USERS = "CREATE TABLE " + TABLE_MUTED_USERS + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "chat_id INTEGER, " +
                    "user_id INTEGER, " +
                    "user_name TEXT, " +
                    "UNIQUE(chat_id, user_id)" +
                    ");",
            CREATE_TABLE_BOTS_TOKEN = "CREATE TABLE " + TABLE_BOTS_TOKEN + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "bot_id INTEGER," +
                    "token TEXT, " +
                    "username TEXT UNIQUE," +
                    "first_name TEXT" +
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
        db.execSQL(CREATE_TABLE_WHITE_LINKS);
        db.execSQL(CREATE_TABLE_LOG);
        db.execSQL(CREATE_TABLE_TASK_WARNS);
        db.execSQL(CREATE_TABLE_BLACKLIST_WORDS);

        db.execSQL(CREATE_TABLE_CHAT_TASKS);
        db.execSQL(CREATE_TABLE_CHATS_LIST);
        db.execSQL(CREATE_TABLE_CHAT_COMMANDS);
        db.execSQL(CREATE_TABLE_CHAT_LOG);
        db.execSQL(CREATE_TABLE_MUTED_USERS);
        db.execSQL(CREATE_TABLE_BOTS_TOKEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            boolean table_tasks_created = false;
            if (oldVersion < 2) {
                table_tasks_created = true;
                db.execSQL(CREATE_TABLE_CHAT_TASKS);
                db.execSQL(CREATE_TABLE_CHATS_LIST);
                db.delete(TABLE_TASK_WARNS, null, null); // cleare table coz we rename some `action` types

                // change blackwords table unique index and add 2 new colums.
                db.execSQL(CREATE_TABLE_BLACKLIST_WORDS.replace(TABLE_BLACKLIST_WORDS, "table_tmp"));
                db.execSQL(" INSERT INTO table_tmp (chatID, word) SELECT chatID, word FROM " + TABLE_BLACKLIST_WORDS);
                db.execSQL("DROP TABLE " + TABLE_BLACKLIST_WORDS);
                db.execSQL("ALTER TABLE table_tmp RENAME TO " + TABLE_BLACKLIST_WORDS);
            }

            if (oldVersion < 3) {
                db.execSQL(CREATE_TABLE_CHAT_COMMANDS);
            }
            if (oldVersion < 4) {
                db.execSQL(CREATE_TABLE_CHAT_LOG);
                db.execSQL(CREATE_TABLE_MUTED_USERS);
                if (!table_tasks_created) //coz on upgrade to 2 we create tablechat_tasks
                    db.execSQL("ALTER TABLE " + TABLE_CHAT_TASKS + " ADD COLUMN text TEXT;");
            }
            if (oldVersion < 5) {
                db.execSQL(CREATE_TABLE_BOTS_TOKEN);
                if (!table_tasks_created) //coz on upgrade to 2 we create tablechat_tasks
                    db.execSQL("ALTER TABLE " + TABLE_CHAT_TASKS + " ADD COLUMN is_public_alert INTEGER;");
            }
            if (oldVersion < 6) {
                db.execSQL("ALTER TABLE " + TABLE_BAN_INFO + " ADD COLUMN is_mute_ban INTEGER DEFAULT 0;");
                if (!table_tasks_created)
                    db.execSQL("ALTER TABLE " + TABLE_CHAT_TASKS + " ADD COLUMN is_mute_on_ban INTEGER DEFAULT 0;");
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

