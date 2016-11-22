package com.madpixels.tgadmintools.helper;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.madpixels.apphelpers.FileUtils2;
import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.Sets;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.Const;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TG;
import org.drinkless.td.libcore.telegram.TdApi;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Snake on 16.01.2016.
 */
public class TgH {

    private static ArrayList<Client.ResultHandler> list = new ArrayList<>(2);
    private static final Object LOCK = new Object();

    public static int selfProfileId;
    public static String selfProfileUsername;

    public static SparseArray<TdApi.User> users = new SparseArray<>();

    private static String getCacheDir(Context c) {
        return c.getCacheDir().getAbsolutePath();
    }

    public static void init(Context c) {
        TG.setFileLogEnabled(false);
        TG.setDir(c.getFilesDir().getAbsolutePath());
        TG.setFilesDir(getCacheDir(c) + "/files/");

        //if(!list.isEmpty())
            startUpdatesHandler();
    }

    /**
     * Init TDLib, check auth state, load profile info and then call resultCallback
     * @param onGetAuthHandler callback Ok or Error return.
     */
    public static void init(Context c, final Client.ResultHandler onGetAuthHandler){
        init(c);
        TgH.send(new TdApi.GetAuthState(), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if(TgUtils.isAuthorized(object)) {
                    getProfile(new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            onGetAuthHandler.onResult(new TdApi.Ok());
                        }
                    });
                }
                else
                    onGetAuthHandler.onResult(new TdApi.Error());
            }
        });
    }

    public static Client TG() {
        return TG.getClientInstance();
    }

    private final static Client.ResultHandler LoopUpdateHandler=new Client.ResultHandler() {
        @Override
        public void onResult(TdApi.TLObject object) {
            if(object.getConstructor()== TdApi.UpdateUser.CONSTRUCTOR){
                updateUser((TdApi.UpdateUser) object);
            }

            synchronized (LOCK) {
                for (Client.ResultHandler r : list)
                    r.onResult(object);
            }
        }
    };

    // public static HashMap<Integer, TdApi.User> users = new HashMap<>();


    private static void updateUser(TdApi.UpdateUser updateUser) {
        // MyLog.log("User: "+updateUser.user.firstName+" "+ updateUser.user.lastName);
        users.put(updateUser.user.id, updateUser.user);
        //TODO memory leak on long usage
        // users.put(updateUser.user.id, updateUser.user);
    }

    public static void startUpdatesHandler() {
        TG.setUpdatesHandler(LoopUpdateHandler);
//        TG.setUpdatesHandler(new Client.ResultHandler() {
//            @Override
//            public void onResult(TdApi.TLObject object) {
//                //MyLog.log("onUpdate main handler");
//                synchronized (LOCK) {
//                    for (Client.ResultHandler r : list)
//                        r.onResult(object);
//                }
//            }
//        });
    }

    public static void setUpdatesHandler(Client.ResultHandler r) {
        list.add(r);
        //if (list.size() == 1)
            startUpdatesHandler();
    }

    public static void removeUpdatesHandler(Client.ResultHandler r) {
        synchronized (LOCK) {
            list.remove(r);
        }
        //if (list.isEmpty())
        //    TG.stopClient();
    }

    public static void markMessageAsReaded(long fromId, int msg_id) {
        TG().send(new TdApi.GetChatHistory(fromId, msg_id, 0, 1), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                MyLog.log("Mark as read result: " + object.toString());
            }
        });
    }

    public static void deleteMessage(long chatId, int msgId) {
        TG().send(new TdApi.DeleteMessages(chatId, new int[]{msgId}), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                MyLog.log(object.toString());
            }
        });
    }

    public static void clearAppCache(final Context c, final FileUtils2.ClearCallback callback) {
        final String dir = getCacheDir(c) + "/files/";
        new File(dir, "log").delete();
        new File(dir, "log.old").delete();
        File dbFile = new File(c.getFilesDir().getAbsolutePath(), "files_db.txt");
        long size = dbFile.length();
        MyLog.log(size + " dbsize");
        if (size > 2 * 1024 * 1024)
            dbFile.delete();

        FileUtils2.clearCacheByFilesCount(c, 100, dir + "photo", true, new FileUtils2.ClearCallback() {
            @Override
            public void onCallback() {
                final int filesCount = this.removedFilesCount;
                FileUtils2.clearCacheByFilesCount(c, 100, dir + "thumb", true, new FileUtils2.ClearCallback() {
                    @Override
                    public void onCallback() {
                        int totalFilesCount = filesCount + this.removedFilesCount;
                        callback.removedFilesCount = totalFilesCount;
                        callback.onCallback();
                    }
                });
            }
        });
    }

    public static void clearCache(final Context c) {
        clearAppCache(c, new FileUtils2.ClearCallback() {
            @Override
            public void onCallback() {
                if (BuildConfig.DEBUG) {
                    MyToast.toast(c, "Cache cleared files: " + removedFilesCount);
                }
            }
        });
    }

    public static TdApi.User createChatUser(long chat_id, String user_name, int user_id, String user_login) {
        TdApi.User chatUser;

        chatUser = new TdApi.User();
        chatUser.id = user_id;
        chatUser.firstName = user_name;
        chatUser.lastName = "";
        chatUser.username = user_login;
        chatUser.type = new TdApi.UserTypeGeneral();
        chatUser.profilePhoto = new TdApi.ProfilePhoto();
        chatUser.profilePhoto.small = new TdApi.File(0, "", 0, "");

        return chatUser;
    }

    public static void send(final TdApi.TLFunction function){
        send(function, TgUtils.emptyResultHandler());
    }


    public static void send(final TdApi.TLFunction function, @Nullable final Client.ResultHandler resultHandler) {
        TG().send(function, resultHandler!=null ? resultHandler:TgUtils.emptyResultHandler());
    }

    public static void sendOnUi(final TdApi.TLFunction f, final Client.ResultHandler resultHandler) {
        final Handler h = new Handler();
        send(f, new Client.ResultHandler() {
            @Override
            public void onResult(final TdApi.TLObject object) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        resultHandler.onResult(object);
                    }
                });
            }
        });
    }

    public static void getProfile() { // load profile without callback
        getProfile(null);
    }

    public static void getProfile(final Client.ResultHandler callback){
        send(new TdApi.GetMe(), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TdApi.User me = (TdApi.User) object;
                selfProfileId = me.id;
                selfProfileUsername = me.username;
                Sets.set(Const.SETS_PROFILE_ID, selfProfileId); //save last know profile
                if(callback!=null)
                    callback.onResult(object);
            }
        });
    }

    //TODO error 429 Too big total timeout 151.000000 //profile temporary banned. Check with @SpamBot

}
