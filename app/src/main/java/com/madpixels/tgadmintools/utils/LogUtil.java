package com.madpixels.tgadmintools.utils;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.SparseArray;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.Utils;
import com.madpixels.tgadmintools.App;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.activity.ActivityLogView;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.BanTask;
import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.entities.ChatTask;
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

    Callback onLogCallback;
    public Object callbackPayload;
    public LogEntity logEntity;

    public LogUtil() {
    }

    public LogUtil(Callback onLogCallback, Object callbackPayload) {
        this.onLogCallback = onLogCallback;
        this.callbackPayload = callbackPayload;
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
                    .put("chatType", chat.type)
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


    public static class LogEntity {
        public int item_id;
        public Action action;
        public String jsonData;

        public int chatType;
        public long chatId;
        public String username;

        private String logText, actionTitle;
        private int userId = 0;


        public LogEntity() {
        }

        public LogEntity(Action action, String jsonData) {
            this.action = action;
            this.jsonData = jsonData;
            compileLog();
        }

        public String getLog() {
            if (logText == null)
                compileLog();
            return jsonData;
        }

        public String getLogText() {
            if (logText == null)
                compileLog();
            return logText;
        }

        public String getTitle() {
            if (logText == null)
                compileLog();
            return actionTitle;
        }

        public int getUserId() {
            if (userId == 0)
                compileLog();
            return userId;
        }

        static SparseArray<String> strings = new SparseArray<>();

        public static String getString(@StringRes int resID) {
            String value = strings.get(resID);
            if (value == null) {
                value = App.getContext().getString(resID);
                strings.put(resID, value);
            }
            return value;
        }

        private void compileLog() {
            if (logText != null)// already compiled
                return;
            logText = jsonData;
            String actionTitle = "";
            try {
                JSONObject json = new JSONObject(jsonData);
                if (json.has("userId"))
                    userId = json.getInt("userId");
                String username = json.optString("username");
                if (!username.isEmpty())
                    username = " @" + username;

                String chatUsername = json.optString("chatUsername");
                if (!chatUsername.isEmpty())
                    chatUsername = " @" + chatUsername;

                String chatTitle = null, userFullName = null, logTime = null, linkUrl = null,
                        userIdText=null;

                if (json.has("chatTitle"))
                    chatTitle = String.format(String.format("%s: %s %s\n", getString(R.string.log_title_group), json.getString("chatTitle"), chatUsername.trim()));
                if (json.has("userFullname"))
                    userFullName = String.format(String.format("%s: %s%s\n", getString(R.string.log_title_username), json.getString("userFullname"), username));
                if (json.has("ts"))
                    logTime = String.format("%s: %s", getString(R.string.log_title_time), Utils.TimestampToDate(json.optLong("ts") / 1000));
                if(userId!=0)
                    userIdText = String.format("User id: %s\n" , userId);


                actionTitle = getString(TaskValues.getTitleLogAction(action));
                switch (action) {
                    //NOTE add new type here when new type implemented
                    case AutoKickFromGroup:
                        long ts;
                        String s = chatTitle +userIdText+ userFullName + logTime;
                        logText = s;

                        break;
                    case AutoReturnToChat:
                        if (json.has("error")) {
                            s = json.getString("error") + "\n";
                            s += "User id: " + json.getInt("userId") + "\n";
                        } else
                            s = chatTitle + userFullName;
                        s += logTime;
                        logText = s;

                        break;
                    case AutoUnban:
                        if (json.has("error")) {
                            s = json.getString("error") + "\n" +
                                    chatTitle + userFullName;
                            actionTitle = "Error on unban user";
                        } else
                            s = chatTitle + userFullName;
                        s += logTime;
                        logText = s;
                        break;
                    case BanForLink:
                        // actionTitle = getString(R.string.logAction_banForLink);
                        s = chatTitle + userFullName + logTime;
                        JSONObject payload = json.getJSONObject("payload");
                        if (payload.has("error"))
                            s += "\nOperation error: " + payload.getString("error");
                        else {
                            long banTime = payload.getLong("banAge");
                            linkUrl = payload.getString("link");
                            s += String.format("\n%s: %s", getString(R.string.log_title_banurl), linkUrl);
                            s += String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime / 1000));//ban length
                        }
                        s += String.format("\n%s: %s", getString(R.string.log_title_message), payload.getString("text"));
                        logText = s;
                        break;

                    //Ban for attachments:
                    case BanForImages:
                    case BanForDocs:
                    case BanForSticker:
                    case BanForVoice:
                    case BanForGame:
                    case BanForGif:
                    case BanForAudio:
                    case BanForVideo:
                        ts = json.getLong("ts");
                        s = chatTitle +userIdText+ userFullName + logTime;

                        payload = json.getJSONObject("payload");
                        long banAge = payload.getLong("banAge");
                        if (banAge > 0) {
                            s += String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banAge / 1000));//ban length
                            s += String.format("\n%s: %s", getString(R.string.log_title_banuntil), Utils.TimestampToDate((ts + banAge) / 1000));// ban until
                        }

                        logText = s;
                        break;

                    /* Remove messaage: */
                    case RemoveLink:
                        s = chatTitle +userIdText+ userFullName +
                                String.format("%s: %s\n", getString(R.string.log_title_banurl), json.getString("payload")) +//banned url
                                logTime;
                        logText = s;
                        break;
                    case DeleteMsgBlackWord:
                        s = chatTitle +userIdText+ userFullName + logTime +
                                String.format("\n%s: %s", getString(R.string.log_title_bannedword), json.getString("payload"));
                        logText = s;
                        break;
                    case RemoveFlood:
                        payload = new JSONObject(json.getString("payload"));
                        String messageText = payload.getString("msg");
                        int limit = payload.optInt("limit");
                        int floodCount = payload.optInt("flood");

                        s = chatTitle +userIdText+ userFullName + logTime;
                        s += String.format("\nFlood: %s/%s", floodCount, limit);
                        s += String.format("\n%s: %s", getString(R.string.log_title_message), messageText);
                        logText = s;
                        break;
                    case RemoveVoice:
                    case RemoveDocs:
                    case RemoveGame:
                    case RemoveGif:
                    case RemoveImage:
                    case RemoveSticker:
                    case RemoveAudio:
                    case RemoveVideo:
                        s = chatTitle +userIdText+ userFullName + logTime;
                        logText = s;
                        break;
                    case RemoveMuted:
                        String message = json.optString("payload");
                        s = chatTitle +userIdText+"Message: "+message+"\n"+userFullName + logTime;
                        logText = s;
                        break;

                    /* Other */
                    case CommandExecError:
                        payload = json.getJSONObject("payload");
                        s = chatTitle + logTime + "\n" +
                                String.format("Command: %s\n", payload.getString("cmd")) +
                                String.format("Error: %s\n", payload.getString("error")) +
                                String.format("Body: %s\n", payload.getString("answer"));
                        logText = s;
                        break;

                    //Warning for attachments:
                    case GameFloodWarn:
                    case DocsFloodWarn:
                    case ImagesFloodWarn:
                    case GifsFloodWarn:
                    case AudioFloodWarn:
                    case VoiceFloodWarn:
                    case StickersFloodWarn:
                    case VideoFloodWarn:

                        s = chatTitle +userIdText+ userFullName + logTime;
                        logText = s;
                        break;

                    case BanWordsFloodWarn:
                        s = chatTitle +userIdText+ userFullName + logTime;
                        logText = s;
                        break;
                    case LinksFloodAttempt:
                        payload = json.getJSONObject("payload");
                        s = chatTitle +userIdText+ userFullName +
                                String.format("%s: %s\n", getString(R.string.log_title_banurl), payload.getString("link")) +
                                // "Link: " + payload.getString("link") + "\n" +
                                logTime;
                        logText = s;
                        break;
                    case AutoUnbanByAdminInvite:
                        payload = json.getJSONObject("payload");
                        s = chatTitle +userIdText+ userFullName +
                                String.format("%s: %s\n", getString(R.string.log_title_admin), payload.getString("adminName")) +
                                // "Admin: " + payload.getString("adminName") + "\n" +
                                logTime;
                        logText = s;
                        break;

                    case BanForBlackWord:
                        s = chatTitle +userIdText+ userFullName + logTime;
                        payload = json.getJSONObject("payload");
                        long banTime = payload.getLong("banAge") / 1000;
                        s += String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime)) +
                                String.format("\n%s: %s", getString(R.string.log_title_bannedword), payload.getString("word")) +
                                String.format("\n%s: %s", getString(R.string.log_title_message), payload.getString("text"));
                        logText = s;
                        break;


                    case BanManually:
                        payload = json.getJSONObject("payload");
                        banTime = payload.getLong("banAge") / 1000;
                        s = chatTitle + userFullName + logTime +
                                String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime));

                        String r = payload.getString("banText");
                        if (!r.isEmpty())
                            s += String.format("\n%s: %s", getString(R.string.log_title_ban_reason), r);
                        logText = s;
                        break;


                    case BanForFlood:
                        s = chatTitle +userIdText+ userFullName + logTime;
                        payload = json.getJSONObject("payload");
                        banTime = payload.getLong("banAge") / 1000;
                        s += String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime));
                        logText = s;
                        break;


                    default:
                        actionTitle = action.name();
                        break;
                }
            } catch (JSONException e) {
                MyLog.log(e);
            }
            this.actionTitle = actionTitle;

        }

        public boolean hasChatId() {
            try {
                JSONObject j = new JSONObject(jsonData);
                return j.has("chatId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


        public void setChat() {
            try {
                JSONObject j = new JSONObject(jsonData);
                chatId = j.getLong("chatId");
                chatType = j.getInt("chatType");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public boolean hasUserName() {
            try {
                JSONObject j = new JSONObject(jsonData);
                if (j.has("username"))
                    username = j.getString("username");
                return username != null && !username.isEmpty();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


    }

    public enum Action {
        StickersFloodWarn, LinksFloodAttempt, RemoveSticker, BanForSticker, RemoveLink, BanForLink,
        AutoKickFromGroup, AutoUnban, AutoUnbanByAdminInvite, AutoReturnToChat, BanWordsFloodWarn,
        BanForImages, BanForBlackWord, DeleteMsgBlackWord, BanManually, ImagesFloodWarn, BanForVoice,
        RemoveVoice, VoiceFloodWarn, BanForFlood, BanForGame, RemoveGame, CommandExecError, RemoveImage,
        GameFloodWarn, BanForDocs, RemoveDocs, DocsFloodWarn, GifsFloodWarn, BanForGif, RemoveGif,
        BanForAudio, RemoveAudio, AudioFloodWarn, VideoFloodWarn, RemoveVideo, BanForVideo, RemoveFlood,
        RemoveMuted
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
                    logData.put("userFullname", userFullName)
                            .put("username", userName);
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
                    .put("chatType", chat.type)
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
                    .put("chatType", chat.type)
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
                    .put("chatType", chat.type)
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
