package com.madpixels.tgadmintools.utils;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.madpixels.apphelpers.Sets;
import com.madpixels.tgadmintools.App;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.activity.ActivityLogView;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.BanTask;
import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.entities.ChatTask;
import com.madpixels.tgadmintools.entities.LogEntity;
import com.madpixels.tgadmintools.helper.TaskValues;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.services.MyNotification;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snake on 15.03.2016.
 */
public class LogUtil {

    public enum Action {
        StickersFloodWarn, LinksFloodAttempt, RemoveSticker, BanForSticker, RemoveLink, BanForLink,
        AutoKickFromGroup, AutoUnban, AutoUnbanByAdminInvite, AutoReturnToChat, BanWordsFloodWarn,
        BanForImages, BanForBlackWord, DeleteMsgBlackWord, BanManually, ImagesFloodWarn, BanForVoice,
        RemoveVoice, VoiceFloodWarn, BanForFlood, BanForGame, RemoveGame, CommandExecError, RemoveImage,
        GameFloodWarn, BanForDocs, RemoveDocs, DocsFloodWarn, GifsFloodWarn, BanForGif, RemoveGif,
        BanForAudio, RemoveAudio, AudioFloodWarn, VideoFloodWarn, RemoveVideo, BanForVideo, RemoveFlood,
        BOT_ERROR, CMDTitleChanged, RemoveJoinMessage, RemoveLeaveMessage, RemoveMuted
    }

    Callback onLogCallback;
    public Object callbackPayload;
    public LogEntity logEntity;

    public LogUtil() {
    }

    public LogUtil(Callback onLogCallback, Object callbackPayload) {
        this.onLogCallback = onLogCallback;
        this.callbackPayload = callbackPayload;
    }



    public void logAutoKickUser(long chat_id, int chatType, int user_id, String chatTitle) {
        try {
            JSONObject logData = new JSONObject()
                    .put("type", Action.AutoKickFromGroup)
                    .put("chatType", chatType)
                    .put("chatId", chat_id)
                    .put("chatTitle", chatTitle)
                    .put("userId", user_id);
            logWithUserInfo(Action.AutoKickFromGroup, user_id, logData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void logAutoUnbanAndReturnError(BanTask ban, String error) {
        try {
            JSONObject logData = new JSONObject()
                    .put("type", Action.AutoReturnToChat)
                    .put("chatId", ban.chat_id)
                    .put("chatType", ban.chatType)
                    .put("chatTitle", "")
                    .put("error", error)
                    .put("userId", ban.user_id);

            logWithUserInfo(Action.AutoReturnToChat, ban.user_id, logData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void logAutoUnbanAndReturn(final BanTask ban) {
        final JSONObject logData = new JSONObject();
        try {
            logData
                    .put("type", Action.AutoReturnToChat)
                    .put("chatId", ban.chat_id)
                    .put("chatType", ban.chatType)
                    .put("chatTitle", "")
                    .put("userId", ban.user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TgH.send(new TdApi.GetChat(ban.chat_id), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR) {
                    TdApi.Chat chat = (TdApi.Chat) object;
                    try {
                        logData.put("chatTitle", chat.title);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    logWithUserInfo(Action.AutoReturnToChat, ban.user_id, logData);
                }
            }
        });
    }

    private void logAction(final Action action, JSONObject logData) {
        logAction(action, logData, false);
    }

    public void logAction(final Action action, JSONObject logData, boolean silent) {
        try {
            logData.put("ts", System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper.getInstance().logAction(action.name(), logData.toString());
        ActivityLogView.updateLog();
        Analytics.sendReport("LogAction", action.name(), "");

        LogEntity log = new LogEntity();
        log.action = action;
        log.jsonData = logData.toString();

        if (!silent && Sets.getBoolean(Const.NOTIFICATION_LOG, true)) {
            log.compileLog();
            showLogNotification(log.getTitle() + "\n" + log.getLogText());
        }
        callbackLog(log);
    }

    public static void showLogNotification(final String logMessage) {
        MyNotification n = new MyNotification(100, App.getContext());
        n.mNotificationBuilder.setContentText(App.getContext().getText(R.string.notification_show_hint));
        n.setStyle(new NotificationCompat.BigTextStyle());
        n.appendBigText(logMessage);

        Intent intent = new Intent(App.getContext(), ActivityLogView.class);
        PendingIntent pIntent = PendingIntent.getActivity(App.getContext(), 10, intent, 0);
        n.setContentIntent(pIntent);
        n.alert();
    }

    void logWithUserInfo(final Action action, int userId, final JSONObject logData) {
        TgH.send(new TdApi.GetUser(userId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                String userFullName, userName;
                if (object.getConstructor() == TdApi.User.CONSTRUCTOR) {
                    TdApi.User u = (TdApi.User) object;
                    userFullName = u.firstName + " " + u.lastName;
                    userName = u.username;
                } else {
                    userFullName = "";
                    userName = "";
                }
                try {
                    logData.put("userFullname", userFullName.trim()).put("username", userName);
                    logAction(action, logData);
                } catch (Exception e) {
                }
            }
        });
    }

    void callbackLog(LogEntity log) {
        if (onLogCallback == null)
            return;
        this.logEntity = log;
        onLogCallback.onResult(this);
    }

    public void logBanUser(final Action action, TdApi.Chat chat, TdApi.UpdateNewMessage message) {
        logBanUser(action, chat, message, null);
    }

    public void logBanUser(final Action action, TdApi.Chat chat, TdApi.UpdateNewMessage message, JSONObject payload) {
        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.senderUserId;
        String chatUsername = TgUtils.getChatUsername(chat);
        try {
            JSONObject logData = new JSONObject()
                    .put("type", action)
                    .put("chatId", chatId)
                    .put("chatType", chat.type.getConstructor())
                    .put("chatTitle", chatTitle)
                    .put("userId", user_id);
            if(!TextUtils.isEmpty(chatUsername))
                logData.put("chatUsername", chatUsername);
            if (payload != null)
                logData.put("payload", payload);
            logWithUserInfo(action, user_id, logData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void logDeleteMessage(final Action action, TdApi.Chat chat, TdApi.UpdateNewMessage message) {
        logDeleteMessage(action, chat, message, null);
    }

    public void logDeleteMessage(final Action action, TdApi.Chat chat, TdApi.UpdateNewMessage message, String payload) {
        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.senderUserId;
        String chatUsername = TgUtils.getChatUsername(chat);

        try {
            JSONObject logData = new JSONObject()
                    .put("type", action)
                    .put("chatId", chatId)
                    .put("chatType", chat.type.getConstructor())
                    .put("chatTitle", chatTitle)
                    .put("userId", user_id);
            if(!TextUtils.isEmpty(chatUsername))
                logData.put("chatUsername", chatUsername);
            if (payload != null)
                logData.put("payload", payload);
            logWithUserInfo(action, user_id, logData);
        } catch (Exception e) {

        }
    }

    public void logWarningBeforeBanForLink(TdApi.Chat chat, TdApi.UpdateNewMessage message, int attemptsCount, String link) {
        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.senderUserId;
        String chatUsername = TgUtils.getChatUsername(chat);
        try {
            JSONObject logData = new JSONObject()
                    .put("type", Action.LinksFloodAttempt)
                    .put("chatId", chatId)
                    .put("chatTitle", chatTitle)
                    .put("chatType", chat.type.getConstructor())
                    .put("userId", user_id);
            if(!TextUtils.isEmpty(chatUsername))
                logData.put("chatUsername", chatUsername);
            JSONObject payload = new JSONObject().put("count", attemptsCount).put("link", link);
            logData.put("payload", payload);
            logWithUserInfo(Action.LinksFloodAttempt, user_id, logData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void logAutoRemoveFromBanListError(final BanTask ban, final String error) {
        TgH.send(new TdApi.GetChat(ban.chat_id), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                String chatTitle = "";
                if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR) {
                    TdApi.Chat chat = (TdApi.Chat) object;
                    chatTitle = chat.title;
                }
                try {
                    JSONObject j = new JSONObject()
                            .put("chatTitle", chatTitle)
                            .put("chatId", ban.chat_id)
                            .put("userId", ban.user_id)
                            .put("error", error)
                            .put("chatType", ban.chatType);
                    logWithUserInfo(Action.AutoUnban, ban.user_id, j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logAutoRemoveFromBanList(final BanTask ban) {
        TgH.send(new TdApi.GetChat(ban.chat_id), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TdApi.Chat chat = (TdApi.Chat) object;
                try {
                    JSONObject j = new JSONObject()
                            .put("chatTitle", chat.title)
                            .put("chatId", chat.id)
                            .put("userId", ban.user_id)
                            .put("chatType", ban.chatType);
                    logWithUserInfo(Action.AutoUnban, ban.user_id, j);
                    // logAction(Action.AutoUnban, j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logUserUnbannedByInvite(final TdApi.Chat chat, final TdApi.User user, int adminId) {
        TgH.send(new TdApi.GetUser(adminId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TdApi.User admin = (TdApi.User) object;
                try {
                    String chatUsername = TgUtils.getChatUsername(chat);
                    JSONObject j = new JSONObject()
                            .put("chatTitle", chat.title)
                            .put("chatId", chat.id)
                            .put("userId", user.id)
                            .put("chatType", chat.type.getConstructor())
                            .put("userFullname", user.firstName + " " + user.lastName)
                            .put("username", user.username);
                    if(!TextUtils.isEmpty(chatUsername))
                        j.put("chatUsername", chatUsername);

                    JSONObject payload = new JSONObject()
                            .put("adminId", admin.id)
                            .put("adminName", admin.firstName + " " + admin.lastName)
                            .put("adminUsername", admin.username);
                    j.put("payload", payload);
                    logAction(Action.AutoUnbanByAdminInvite, j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logCommandExecute(Action action, TdApi.Chat chat, int userId, String newTitle){
        try {
            JSONObject j = new JSONObject()
                    .put("chatTitle", newTitle)
                    .put("oldTitle", chat.title)
                    .put("chatId", chat.id)
                    .put("userId",userId)
                    .put("chatType", chat.type.getConstructor());
            logWithUserInfo(action,userId, j);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void logBanUserManually(long chat_id, int chatType, String chatTitle,
                                          TdApi.User user, String banText, long ban_age) {
        try {
            JSONObject payload = new JSONObject()
                    .put("banAge", ban_age)
                    .put("banText", banText);

            JSONObject j = new JSONObject()
                    .put("chatTitle", chatTitle)
                    .put("chatId", chat_id)
                    .put("userId", user.id)
                    .put("chatType", chatType)
                    .put("userFullname", user.firstName + " " + user.lastName)
                    .put("username", user.username)
                    .put("payload", payload);
            new LogUtil().
                    logAction(Action.BanManually, j, true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void logWarningBeforeBan(ChatTask.TYPE type, TdApi.Chat chat, TdApi.UpdateNewMessage message, int tryes) {
        Action action;
        action = TaskValues.getWarningAction(type);

        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.senderUserId;
        String chatUsername = TgUtils.getChatUsername(chat);
        try {
            JSONObject logData = new JSONObject()
                    .put("type", action)
                    .put("chatId", chatId)
                    .put("chatType", chat.type.getConstructor())
                    .put("chatTitle", chatTitle)
                    .put("userId", user_id);
            if(!TextUtils.isEmpty(chatUsername))
                logData.put("chatUsername", chatUsername);
            logData.put("payload", tryes);
            logWithUserInfo(action, user_id, logData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void logBotError(int error_code, String description, long chatID) {
        final JSONObject logData = new JSONObject();
        try {
            logData
                    .put("type", Action.BOT_ERROR)
                    .put("error_code", error_code)
                    .put("description", description)
                    .put("chatId", chatID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        logAction(Action.BOT_ERROR, logData);
    }

    public void execCommandError(TdApi.Chat chat, String cmd, String error, String answer) {
        try {
            JSONObject payload = new JSONObject()
                    .put("cmd", cmd)
                    .put("error", error)
                    .put("answer", answer);

            JSONObject j = new JSONObject()
                    .put("chatTitle", chat.title)
                    .put("chatId", chat.id)
                    .put("chatType", chat.type.getConstructor())
                    .put("payload", payload);
            logAction(Action.CommandExecError, j, true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
