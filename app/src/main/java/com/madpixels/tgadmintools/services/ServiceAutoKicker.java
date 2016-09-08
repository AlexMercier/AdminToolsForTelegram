package com.madpixels.tgadmintools.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.madpixels.apphelpers.MyLog;
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.log("ServiceAutoKicker started");
        isManualStop = false;
        TgH.init(this, new ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TgH.setUpdatesHandler(r);
            }
        });

        return START_STICKY;
    }

    final private ResultHandler r = new ResultHandler() {
        @Override
        public void onResult(TdApi.TLObject object) {
            // MyLog.log(object.toString());
            if (object.getConstructor() == TdApi.UpdateNewMessage.CONSTRUCTOR) {
                TdApi.UpdateNewMessage umsg = (TdApi.UpdateNewMessage) object;
                if(!umsg.message.canBeDeleted) return;
                if (umsg.message.content.getConstructor() == TdApi.MessageChatJoinByLink.CONSTRUCTOR) {
                    //TODO add invited and etc, and remove from autokick when invited by admin.
                    long chat_id = umsg.message.chatId;
                    int user_id = umsg.message.fromId;
                    checkUserForKick(chat_id, user_id);
                }
                else if(umsg.message.content.getConstructor() == TdApi.MessageChatAddParticipants.CONSTRUCTOR){
                    TdApi.MessageChatAddParticipants chatAddParticipants = (TdApi.MessageChatAddParticipants) umsg.message.content;
                    TdApi.User[] participiants = chatAddParticipants.participants;
                    for(TdApi.User user:participiants){
                        checkUserCanBeInvited(umsg.message.chatId, umsg.message.fromId, user);
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


    void checkUserCanBeInvited(final long chatId, final int fromId, final TdApi.User user){
        if (DBHelper.getInstance().isUserKicked(chatId, user.id)) {
            TgH.send(new TdApi.GetChat(chatId), new ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR){
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
                logKickAction(c,user_id);
            }
        });
    }

    private void logKickAction(TdApi.Chat chat, int userId ){
        LogUtil.logAutoKickUser(chat.id, chat.type.getConstructor(), userId, chat.title);
    }

    void unbanUserOnInvitedByAdmin(TdApi.Chat chat, TdApi.User user, int adminId){
        DBHelper.getInstance().removeUserFromAutoKick(chat.id, user.id);
        DBHelper.getInstance().removeBanTask(chat.id, user.id);
        LogUtil.logUserUnbannedByInvite(chat, user, adminId);
    }


    @Override
    public void onDestroy() {
        TgH.removeUpdatesHandler(r);
        IS_RUNNING = false;
        if (isManualStop == false) {
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
        }
    }

    public static void registerTask(Context context) {
        if (DBHelper.getInstance().getAutoKickCount() > 0) {
            start(context);
            LogUtil.showLogNotification("Service AutoKick started");
        }
    }

    private static boolean isManualStop = false;

    public static void stop(Context context) {
        isManualStop = true;
        context.stopService(new Intent(context, ServiceAutoKicker.class));
    }
}
