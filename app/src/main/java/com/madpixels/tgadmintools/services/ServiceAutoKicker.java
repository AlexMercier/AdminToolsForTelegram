package com.madpixels.tgadmintools.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.utils.AdminUtils;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.drinkless.td.libcore.telegram.Client.ResultHandler;
import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Сервис для автокика из простых групп
 * Created by Snake on 26.02.2016.
 */
public class ServiceAutoKicker extends Service {

    static boolean IS_RUNNING = false;
    private static boolean isStoppedByUser = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.log("ServiceAutoKicker started");
        isStoppedByUser = false;
        TgH.init(this, new ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if(TgUtils.isOk(object)) // if initialization ok
                    TgH.setUpdatesHandler(updatesHandler);
                else{
                    // show auth error to the user ?
                }
            }
        });

        return START_STICKY;
    }

    final private ResultHandler updatesHandler = new ResultHandler() {
        @Override
        public void onResult(TdApi.TLObject object) {
            // MyLog.log(object.toString());
            if (object.getConstructor() == TdApi.UpdateNewMessage.CONSTRUCTOR) {
                TdApi.UpdateNewMessage umsg = (TdApi.UpdateNewMessage) object;
                if (!umsg.message.canBeDeleted) return;
                if (umsg.message.content.getConstructor() == TdApi.MessageChatJoinByLink.CONSTRUCTOR) {
                    //TODO add invited and etc, and remove from autokick when invited by admin.
                    long chat_id = umsg.message.chatId;
                    int user_id = umsg.message.senderUserId;
                    checkUserForKick(chat_id, user_id);
                } else if (umsg.message.content.getConstructor() == TdApi.MessageChatAddMembers.CONSTRUCTOR) {
                    TdApi.MessageChatAddMembers chatAddParticipants = (TdApi.MessageChatAddMembers) umsg.message.content;
                    TdApi.User[] participiants = chatAddParticipants.members;
                    for (TdApi.User user : participiants) {
                        checkUserCanBeInvited(umsg.message.chatId, umsg.message.senderUserId, user);
                    }
                }
            }
        }
    };

    private void checkUserForKick(final long chat_id, final int user_id) {
        if (DBHelper.getInstance().isUserKicked(chat_id, user_id)) {
            AdminUtils.kickUser(chat_id, user_id, new ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (TgUtils.isOk(object)) {
                        loadChatAndLogAction(chat_id, user_id);
                    }
                }
            });
        }
    }


    void checkUserCanBeInvited(final long chatId, final int fromId, final TdApi.User user) {
        if (DBHelper.getInstance().isUserKicked(chatId, user.id)) {
            TgH.send(new TdApi.GetChat(chatId), new ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR) {
                        final TdApi.Chat chat = (TdApi.Chat) object;
                        AdminUtils.checkUserIsAdminInChat((TdApi.Chat) object, fromId, new Callback() {
                            @Override
                            public void onResult(Object data) {
                                boolean isAdmin = (boolean) data;
                                if (isAdmin) {
                                    unbanUserOnInvitedByAdmin(chat, user, fromId);
                                } else {
                                    AdminUtils.kickUser(chatId, user.id, new ResultHandler() {
                                        @Override
                                        public void onResult(TdApi.TLObject object) {
                                            if (TgUtils.isOk(object)) {
                                                logKickAction(chat, user.id);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public void loadChatAndLogAction(final long chat_id, final int user_id) {
        TgH.send(new TdApi.GetChat(chat_id), new ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TdApi.Chat c = (TdApi.Chat) object;
                logKickAction(c, user_id);
            }
        });
    }

    private void logKickAction(TdApi.Chat chat, int userId) {
        new LogUtil(onLogCallback, chat).logAutoKickUser(chat.id, chat.type.getConstructor(), userId, chat.title);
    }

    void unbanUserOnInvitedByAdmin(TdApi.Chat chat, TdApi.User user, int adminId) {
        DBHelper.getInstance().removeUserFromAutoKick(chat.id, user.id);
        DBHelper.getInstance().removeBanTask(chat.id, user.id);
        new LogUtil(onLogCallback, chat).logUserUnbannedByInvite(chat, user, adminId);
    }

    Callback onLogCallback = new Callback() {
        @Override
        public void onResult(Object data) {
            LogUtil log = (LogUtil) data;
            TdApi.Chat chat = (TdApi.Chat) log.callbackPayload;
            ServiceChatTask.logToChat(chat.id, log.logEntity);
        }
    };


    @Override
    public void onDestroy() {
        TgH.removeUpdatesHandler(updatesHandler);
        IS_RUNNING = false;
        if (isStoppedByUser == false) {
            registerTask(getBaseContext());
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start(Context mContext) {
        if (!IS_RUNNING) {
            IS_RUNNING = true;
            mContext.startService(new Intent(mContext, ServiceAutoKicker.class));
            ServiceBackgroundStarter.startService(mContext);//run background starter helper
        }
    }

    public static void registerTask(Context context) {
        if (!IS_RUNNING && DBHelper.getInstance().getAutoKickCount() > 0) {
            start(context);
            if (BuildConfig.DEBUG)
                LogUtil.showLogNotification("Service AutoKick started");
        }
    }



    public static void stop(Context context) {
        isStoppedByUser = true;
        context.stopService(new Intent(context, ServiceAutoKicker.class));
    }
}
