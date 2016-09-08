package com.madpixels.tgadmintools.utils;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.Utils;
import com.madpixels.tgadmintools.App;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.services.MyNotification;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.activity.ActivityLogView;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.BanTask;
import com.madpixels.tgadmintools.helper.TgH;

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

        private void compileLog() {
            logText = jsonData;
            String actionTitle = "";
            try {
                JSONObject json = new JSONObject(jsonData);
                if (json.has("userId"))
                    userId = json.getInt("userId");
                String username = json.optString("username");
                if(!username.isEmpty())
                    username = " @"+username;

                switch (action) {
                    case AutoKickFromGroup:
                        actionTitle = App.getContext().getString(R.string.logAction_autokick);
                        long ts = json.getLong("ts");
                        String s = "Chat: " + json.getString("chatTitle") + "\nUser: " + json.getString("userFullname") + "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000);
                        logText = s;

                        break;
                    case AutoReturnToChat:
                        actionTitle = App.getContext().getString(R.string.logAction_return_to_chat);
                        if (json.has("error"))
                            s = json.getString("error") + "\n";
                        else
                            s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                    "User: " + json.getString("userFullname") +username+ "\n";
                        ts = json.getLong("ts");
                        s += "Time: " + Utils.TimestampToDate(ts / 1000);
                        logText = s;

                        break;
                    case AutoUnban:
                        actionTitle = App.getContext().getString(R.string.logAction_autoUnban);
                        ts = json.getLong("ts");
                        if (json.has("error")) {
                            s =     json.getString("error") + "\n" +
                                    "Chat: " + json.getString("chatTitle") + "\n" +
                                    "User: " + json.getString("userFullname") +username+ "\n";
                            actionTitle = "Error on unban user";
                        }
                        else
                            s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                    "User: " + json.getString("userFullname") +username+ "\n";
                        s += "Time: " + Utils.TimestampToDate(ts / 1000);
                        logText = s;
                        break;
                    case BanForLink:
                        actionTitle = App.getContext().getString(R.string.logAction_banForLink);
                        ts = json.getLong("ts");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") + "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000);
                        JSONObject payload = json.getJSONObject("payload");
                        if (payload.has("error"))
                            s += "\nOperation error: " + payload.getString("error");
                        else {
                            s += "\nBan link: " + payload.getString("link");
                            s+="\nBan age: "+ Utils.convertSecToReadableDate(payload.getLong("banAge")/1000);
                        }
                        s += "\nMessage: " + payload.getString("text");
                        json.getString("payload");
                        logText = s;
                        break;
                    case BanForSticker:
                        actionTitle = App.getContext().getString(R.string.logAction_banForSticker);
                        ts = json.getLong("ts");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") + "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000);
                        payload = json.getJSONObject("payload");
                        long banAge = payload.getLong("banAge");
                        if (banAge > 0)
                            s += "\nBan Age: " + Utils.convertSecToReadableDate(banAge / 1000);

                        logText = s;
                        break;
                    case RemoveLink:
                        actionTitle = App.getContext().getString(R.string.logAction_removeLink);
                        ts = json.getLong("ts");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") + username + "\n" +
                                "Link: " + json.getString("payload") + "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000);
                        logText = s;
                        break;
                    case RemoveSticker:
                        actionTitle = App.getContext().getString(R.string.logAction_removeSticker);
                        ts = json.getLong("ts");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") + username +  "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000);
                        logText = s;
                        break;

                    case StickersFloodWarn:
                        actionTitle = App.getContext().getString(R.string.logAction_stickersFloodWarn);
                        ts = json.getLong("ts");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") +username+ "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000);
                        logText = s;
                        break;
                    case BanWordsFloodWarn:
                        actionTitle = App.getContext().getString(R.string.logAction_banwordsFloodWarn);
                        ts = json.getLong("ts");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") + "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000);
                        logText = s;
                        break;
                    case LinksFloodAttempt:
                        actionTitle = App.getContext().getString(R.string.logAction_linksFloodWarn);
                        ts = json.getLong("ts");
                        payload = json.getJSONObject("payload");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") +username+ "\n" +
                                "Link: " + payload.getString("link") + "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000);
                        logText = s;
                        break;
                    case AutoUnbanByAdminInvite:
                        actionTitle = App.getContext().getString(R.string.logAction_unbanByAdminInvite);
                        ts = json.getLong("ts");
                        payload = json.getJSONObject("payload");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") + "\n" +
                                "Admin: " + payload.getString("adminName") + "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000);
                        logText = s;
                        break;

                    case BanForBlackWord:
                        actionTitle = App.getContext().getString(R.string.logAction_banForBlackWord);
                        ts = json.getLong("ts");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") + (username) + "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000) ;
                        payload = json.getJSONObject("payload");
                        s +=    "\nBan age: "+Utils.convertSecToReadableDate(payload.getLong("banAge")/1000)+
                                "\nBan word: " + payload.getString("word") +
                                "\nMessage: " + payload.getString("text");
                        logText = s;
                        break;

                    case DeleteMsgBlackWord:
                        actionTitle = App.getContext().getString(R.string.logAction_delMsgWithBlackWord);
                        ts = json.getLong("ts");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") +username+ "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000) + "\n" +
                                "Word: " + json.getString("payload");
                        logText = s;
                        break;
                    case BanManually:
                        actionTitle = App.getContext().getString(R.string.logAction_banUser);
                        ts = json.getLong("ts");
                        payload = json.getJSONObject("payload");
                        s =     "Chat: " + json.getString("chatTitle") + "\n" +
                                "User: " + json.getString("userFullname") +username+ "\n" +
                                "Time: " + Utils.TimestampToDate(ts / 1000) + "\n" +
                                "BanAge: " + Utils.convertSecToReadableDate(payload.getLong("banAge") / 1000);
                        String r = payload.getString("banText");
                        if (!r.isEmpty())
                            s += "\nReason: " + r;
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

        public boolean hasUserName(){
            try {
                JSONObject j = new JSONObject(jsonData);
                if(j.has("username"))
                    username = j.getString("username");
                return username!=null && !username.isEmpty();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }


    }

    public enum Action {
        StickersFloodWarn, LinksFloodAttempt, RemoveSticker, BanForSticker, RemoveLink, BanForLink,
        AutoKickFromGroup, AutoUnban, AutoUnbanByAdminInvite, AutoReturnToChat, BanWordsFloodWarn,
        BanForImages, BanForBlackWord, DeleteMsgBlackWord, BanManually, ImagesFloodWarn
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
        final int user_id = message.message.fromId;
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
        final int user_id = message.message.fromId;

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
        final int user_id = message.message.fromId;
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

    public static void logBanForBanWordAttempt(TdApi.Chat chat, TdApi.UpdateNewMessage message, int attemptsCount) {
        final String chatTitle = chat.title;
        final long chatId = chat.id;
        final int user_id = message.message.fromId;
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
        final int user_id = message.message.fromId;
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
                    logWithUserInfo(Action.AutoUnban,ban.user_id, j);
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
                                          TdApi.ChatParticipant user, String banText, long ban_age) {
        try {
            JSONObject payload = new JSONObject()
                    .put("banAge", ban_age)
                    .put("banText", banText);

            JSONObject j = new JSONObject()
                    .put("chatTitle", chatTitle)
                    .put("chatId", chat_id)
                    .put("userId", user.user.id)
                    .put("chatType", chatType)
                    .put("userFullname", user.user.firstName + " " + user.user.lastName)
                    .put("username", user.user.username)
                    .put("payload", payload);
            logAction(Action.BanManually, j, true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
