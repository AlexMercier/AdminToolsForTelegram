package com.madpixels.tgadmintools.utils;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
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
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.services.MyNotification;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snake on 15.03.2016.
 */
public class LogUtil {


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
            logText = jsonData;
            String actionTitle = "";
            try {
                JSONObject json = new JSONObject(jsonData);
                if (json.has("userId"))
                    userId = json.getInt("userId");
                String username = json.optString("username");
                if (!username.isEmpty())
                    username = " @" + username;

                String chatTitle = null, userFullName = null, logTime = null, linkUrl = null;

                if (json.has("chatTitle"))
                    chatTitle = String.format(String.format("%s: %s\n", getString(R.string.log_title_group), json.getString("chatTitle")));
                if (json.has("userFullname")) {
                    userFullName = String.format(String.format("%s: %s%s\n", getString(R.string.log_title_username), json.getString("userFullname"), username));
                }
                if (json.has("ts"))
                    logTime = String.format("%s: %s", getString(R.string.log_title_time), Utils.TimestampToDate(json.optLong("ts") / 1000)); // time
                if (json.has("link"))
                    linkUrl = String.format(String.format("%s: %s", getString(R.string.log_title_banurl), json.getString("link")));
                ///if(json.has("banAge"))
                //    banTime = String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(json.optLong("banAge") / 1000));//ban length

                switch (action) {
                    case AutoKickFromGroup:
                        actionTitle = getString(R.string.logAction_autokick);
                        long ts;
                        String s = chatTitle + userFullName + logTime;
                        logText = s;

                        break;
                    case AutoReturnToChat:
                        actionTitle = getString(R.string.logAction_return_to_chat);
                        if (json.has("error")) {
                            s = json.getString("error") + "\n";
                            s+= "User id: "+json.getInt("userId")+"\n";
                        }
                        else
                            s = chatTitle + userFullName;
                        s += logTime;
                        logText = s;

                        break;
                    case AutoUnban:
                        actionTitle = getString(R.string.logAction_autoUnban);
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
                        actionTitle = getString(R.string.logAction_banForLink);
                        s = chatTitle + userFullName + logTime;
                        JSONObject payload = json.getJSONObject("payload");
                        if (payload.has("error"))
                            s += "\nOperation error: " + payload.getString("error");
                        else {
                            long banTime = payload.getLong("banAge");
                            linkUrl = payload.getString("link");
                            s += String.format("\n%s: %s", getString(R.string.log_title_banurl), linkUrl) ;
                            s += String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime / 1000));//ban length
                        }
                        s += String.format("\n%s: %s", getString(R.string.log_title_message), payload.getString("text"));
                        logText = s;
                        break;
                    case BanForSticker:
                        actionTitle = getString(R.string.logAction_banForSticker);
                        ts = json.getLong("ts");
                        s = chatTitle + userFullName + logTime; // time

                        payload = json.getJSONObject("payload");
                        long banAge = payload.getLong("banAge");
                        if (banAge > 0) {
                            s += String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banAge / 1000));//ban length
                            s += String.format("\n%s: %s", getString(R.string.log_title_banuntil), Utils.TimestampToDate((ts + banAge) / 1000));// ban until
                        }

                        logText = s;
                        break;
                    case RemoveLink:
                        actionTitle = getString(R.string.logAction_removeLink);
                        // ts = json.getLong("ts");
                        s = chatTitle + userFullName +
                                String.format("%s: %s\n", getString(R.string.log_title_banurl), json.getString("payload")) +//banned url
                                logTime;
                        logText = s;
                        break;
                    case RemoveSticker:
                        actionTitle = getString(R.string.logAction_removeSticker);
                        // ts = json.getLong("ts");
                        s = chatTitle + userFullName + logTime; // time
                        logText = s;
                        break;

                    case StickersFloodWarn:
                        actionTitle = getString(R.string.logAction_stickersFloodWarn);
                        // ts = json.getLong("ts");
                        s = chatTitle + userFullName + logTime; // time
                        logText = s;
                        break;
                    case BanWordsFloodWarn:
                        actionTitle = getString(R.string.logAction_banwordsFloodWarn);
                        // ts = json.getLong("ts");
                        s = chatTitle + userFullName + logTime;
                        logText = s;
                        break;
                    case LinksFloodAttempt:
                        actionTitle = getString(R.string.logAction_linksFloodWarn);
                        // ts = json.getLong("ts");
                        payload = json.getJSONObject("payload");
                        s = chatTitle + userFullName +
                                String.format("%s: %s\n", getString(R.string.log_title_banurl), payload.getString("link")) +
                                // "Link: " + payload.getString("link") + "\n" +
                                logTime;
                        logText = s;
                        break;
                    case AutoUnbanByAdminInvite:
                        actionTitle = getString(R.string.logAction_unbanByAdminInvite);
                        //ts = json.getLong("ts");
                        payload = json.getJSONObject("payload");
                        s = chatTitle + userFullName +
                                String.format("%s: %s\n", getString(R.string.log_title_admin), payload.getString("adminName")) +
                                // "Admin: " + payload.getString("adminName") + "\n" +
                                logTime;
                        logText = s;
                        break;

                    case BanForBlackWord:
                        actionTitle = getString(R.string.logAction_banForBlackWord);
                        // ts = json.getLong("ts");
                        s = chatTitle + userFullName + logTime;
                        payload = json.getJSONObject("payload");
                        long banTime = payload.getLong("banAge") / 1000;
                        s += String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime)) +
                                String.format("\n%s: %s", getString(R.string.log_title_bannedword), payload.getString("word")) +
                                //"\nBan word: " + payload.getString("word") +
                                String.format("\n%s: %s", getString(R.string.log_title_message), payload.getString("text"));
                        logText = s;
                        break;

                    case DeleteMsgBlackWord:
                        actionTitle = getString(R.string.logAction_delMsgWithBlackWord);
                        //ts = json.getLong("ts");
                        s = chatTitle + userFullName + logTime +
                                String.format("\n%s: %s", getString(R.string.log_title_bannedword), json.getString("payload"));
                        logText = s;
                        break;
                    case BanManually:
                        actionTitle = getString(R.string.logAction_banUser);
                        // ts = json.getLong("ts");
                        payload = json.getJSONObject("payload");
                        banTime = payload.getLong("banAge") / 1000;
                        s = chatTitle + userFullName + logTime +
                                String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime));

                        String r = payload.getString("banText");
                        if (!r.isEmpty())
                            s += String.format("\n%s: %s", getString(R.string.log_title_ban_reason), r);
                        logText = s;
                        break;

                    case RemoveFlood:
                        actionTitle = getString(R.string.logAction_delMsgForFlood);
                        payload = new JSONObject(json.getString("payload"));
                        String messageText = payload.getString("msg");
                        int limit = payload.optInt("limit");
                        int floodCount = payload.optInt("flood");

                        s = chatTitle + userFullName + logTime;
                        s+= String.format("\nFlood: %s/%s", floodCount, limit);
                        s += String.format("\n%s: %s", getString(R.string.log_title_message), messageText);
                        logText = s;
                        break;

                    case BanForFlood:
                        actionTitle = getString(R.string.logAction_banForFlood);
                        s = chatTitle + userFullName + logTime;
                        payload = json.getJSONObject("payload");
                        banTime = payload.getLong("banAge") / 1000;
                        s += String.format("\n%s: %s", getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime));// +
                                //String.format("\n%s: %s", getString(R.string.log_title_), payload.getString("word")) +
                                //"\nBan word: " + payload.getString("word") +
                                //String.format("\n%s: %s", getString(R.string.log_title_message), payload.getString("text"));
                        logText = s;
                        break;

                    case RemoveVoice:
                        actionTitle = getString(R.string.logAction_removeVoice);
                        s = chatTitle + userFullName + logTime;
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
        BanForImages, BanForBlackWord, DeleteMsgBlackWord, BanManually, ImagesFloodWarn, BanForVoice, RemoveVoice, VoiceFloodWarn,
        BanForFlood, RemoveFlood
    }

    public static void logAutoKickUser(long chat_id, int chatType, int user_id, String chatTitle) {
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

    public static void logAutoUnbanAndReturnError(BanTask ban, String error) {
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

    public static void logAutoUnbanAndReturn(final BanTask ban) {
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

    private static void logAction(final Action action, JSONObject logData) {
        logAction(action, logData, false);
    }

    public static void logAction(final Action action, JSONObject logData, boolean silent) {
        try {
            logData.put("ts", System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper.getInstance().logAction(action.name(), logData.toString());
        ActivityLogView.updateLog();
        Analytics.sendReport("LogAction", action.name(), "");

        if (!silent && Sets.getBoolean(Const.NOTIFICATION_LOG, true)) {
            LogEntity log = new LogEntity(action, logData.toString());
            showLogNotification(log.getTitle() + "\n" + log.getLogText());
            /*
            MyNotification n = new MyNotification(100, App.getContext());
            n.mNotificationBuilder.setContentText(App.getContext().getText(R.string.notification_show_hint));
            n.setStyle(new NotificationCompat.BigTextStyle());
            n.appendBigText(log.getTitle()+"\n"+log.getLogText());

            Intent intent = new Intent(App.getContext(), ActivityLogView.class);
            PendingIntent pIntent = PendingIntent.getActivity(App.getContext(), 10, intent, 0);
            n.setContentIntent(pIntent);
            n.alert();
            */
        }
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

    static void logWithUserInfo(final Action action, int userId, final JSONObject logData) {
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

    public static void logBanUser(final Action action, TdApi.Chat chat, TdApi.UpdateNewMessage message) {
        logBanUser(action, chat, message, null);
    }

    public static void logBanUser(final Action action, TdApi.Chat chat, TdApi.UpdateNewMessage message, JSONObject payload) {
        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.senderUserId;
        try {
            JSONObject logData = new JSONObject()
                    .put("type", action)
                    .put("chatId", chatId)
                    .put("chatType", chat.type)
                    .put("chatTitle", chatTitle)
                    .put("userId", user_id);
            if (payload != null)
                logData.put("payload", payload);
            logWithUserInfo(action, user_id, logData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void logDeleteMessage(final Action action, TdApi.Chat chat, TdApi.UpdateNewMessage message) {
        logDeleteMessage(action, chat, message, null);
    }


    public static void logDeleteMessage(final Action action, TdApi.Chat chat, TdApi.UpdateNewMessage message, String payload) {
        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.senderUserId;

        try {
            JSONObject logData = new JSONObject()
                    .put("type", action)
                    .put("chatId", chatId)
                    .put("chatType", chat.type)
                    .put("chatTitle", chatTitle)
                    .put("userId", user_id);
            if (payload != null)
                logData.put("payload", payload);
            logWithUserInfo(action, user_id, logData);
        } catch (Exception e) {

        }
    }

    public static void logBanForStickerAttempt(TdApi.Chat chat, TdApi.UpdateNewMessage message, int attemptsCount) {
        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.senderUserId;
        try {
            JSONObject logData = new JSONObject()
                    .put("type", Action.StickersFloodWarn)
                    .put("chatId", chatId)
                    .put("chatType", chat.type)
                    .put("chatTitle", chatTitle)
                    .put("userId", user_id);
            logData.put("payload", attemptsCount);
            logWithUserInfo(Action.StickersFloodWarn, user_id, logData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
    public static void logBanForFloodAttempt(TdApi.Chat chat, TdApi.UpdateNewMessage message, int attemptsCount) {
        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.senderUserId;
        try {
            JSONObject logData = new JSONObject()
                    .put("type", Action.BanForFlood)
                    .put("chatId", chatId)
                    .put("chatType", chat.type)
                    .put("chatTitle", chatTitle)
                    .put("userId", user_id);
            logData.put("payload", attemptsCount);
            logWithUserInfo(Action.BanForFlood, user_id, logData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    */

    public static void logBanForBanWordAttempt(TdApi.Chat chat, TdApi.UpdateNewMessage message, int attemptsCount) {
        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.senderUserId;
        try {
            JSONObject logData = new JSONObject()
                    .put("type", Action.BanWordsFloodWarn)
                    .put("chatId", chatId)
                    .put("chatType", chat.type)
                    .put("chatTitle", chatTitle)
                    .put("userId", user_id);
            logData.put("payload", attemptsCount);
            logWithUserInfo(Action.BanWordsFloodWarn, user_id, logData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void logBanForLinksAttempt(TdApi.Chat chat, TdApi.UpdateNewMessage message, int attemptsCount, String link) {
        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.senderUserId;
        try {
            JSONObject logData = new JSONObject()
                    .put("type", Action.LinksFloodAttempt)
                    .put("chatId", chatId)
                    .put("chatTitle", chatTitle)
                    .put("chatType", chat.type)
                    .put("userId", user_id);
            JSONObject payload = new JSONObject().put("count", attemptsCount).put("link", link);
            logData.put("payload", payload);
            logWithUserInfo(Action.LinksFloodAttempt, user_id, logData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void logAutoRemoveFromBanListError(final BanTask ban, final String error) {
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

    public static void logAutoRemoveFromBanList(final BanTask ban) {
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

    public static void logUserUnbannedByInvite(final TdApi.Chat chat, final TdApi.User user, int adminId) {
        TgH.send(new TdApi.GetUser(adminId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TdApi.User admin = (TdApi.User) object;
                try {
                    JSONObject j = new JSONObject()
                            .put("chatTitle", chat.title)
                            .put("chatId", chat.id)
                            .put("userId", user.id)
                            .put("chatType", chat.type.getConstructor())
                            .put("userFullname", user.firstName + " " + user.lastName)
                            .put("username", user.username);
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
            logAction(Action.BanManually, j, true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
