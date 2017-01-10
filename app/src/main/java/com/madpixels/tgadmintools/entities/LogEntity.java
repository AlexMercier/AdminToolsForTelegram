package com.madpixels.tgadmintools.entities;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.Utils;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.helper.TaskValues;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Snake
 */

public class LogEntity {
    public int item_id;
    public LogUtil.Action action;
    public String jsonData;

    public int chatType;
    public long chatId;
    public String username;

    private String logText, actionTitle;
    private int userId = 0;


    public LogEntity() {
    }

    public LogEntity(LogUtil.Action action, String jsonData) {
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

    public void compileLog() {
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
                    userIdText = null;

            if (json.has("chatTitle"))
                chatTitle = String.format(String.format("%s: %s %s\n", TaskValues.getString(R.string.log_title_group), json.getString("chatTitle"), chatUsername.trim()));
            if (json.has("userFullname"))
                userFullName = String.format(String.format("%s: %s%s\n", TaskValues.getString(R.string.log_title_username), json.getString("userFullname"), username));
            if (json.has("ts"))
                logTime = String.format("%s: %s", TaskValues.getString(R.string.log_title_time), Utils.TimestampToDate(json.optLong("ts") / 1000));
            if (userId != 0)
                userIdText = String.format("User id: %s\n", userId);


            actionTitle = TaskValues.getString(TaskValues.getTitleLogAction(action), action.name());
            switch (action) {
                //NOTE add new type here when new type implemented
                case AutoKickFromGroup:
                    long ts;
                    String s = chatTitle + userIdText + userFullName + logTime;
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
                        s += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_banurl), linkUrl);
                        s += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime / 1000));//ban length
                    }
                    s += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_message), payload.getString("text"));
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
                    s = chatTitle + userIdText + userFullName + logTime;

                    payload = json.getJSONObject("payload");
                    long banAge = payload.getLong("banAge");
                    if (banAge > 0) {
                        s += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banAge / 1000));//ban length
                        s += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_banuntil), Utils.TimestampToDate((ts + banAge) / 1000));// ban until
                    }

                    logText = s;
                    break;

                    /* Remove messaage: */
                case RemoveLink:
                    s = chatTitle + userIdText + userFullName +
                            String.format("%s: %s\n", TaskValues.getString(R.string.log_title_banurl), json.getString("payload")) +//banned url
                            logTime;
                    logText = s;
                    break;
                case DeleteMsgBlackWord:
                    s = chatTitle + userIdText + userFullName + logTime +
                            String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bannedword), json.getString("payload"));
                    logText = s;
                    break;
                case RemoveFlood:
                    payload = new JSONObject(json.getString("payload"));
                    String messageText = payload.getString("msg");
                    int limit = payload.optInt("limit");
                    int floodCount = payload.optInt("flood");

                    s = chatTitle + userIdText + userFullName + logTime;
                    s += String.format("\nFlood: %s/%s", floodCount, limit);
                    s += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_message), messageText);
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
                    s = chatTitle + userIdText + userFullName + logTime;
                    logText = s;
                    break;
                case RemoveMuted:
                    String message = json.optString("payload");
                    s = chatTitle + userIdText + "Message: " + message + "\n" + userFullName + logTime;
                    logText = s;
                    break;

                case RemoveJoinMessage:
                    s = chatTitle + userFullName + userIdText + logTime + "\n";
                    if (json.has("payload")) {
                        JSONArray invited_users = new JSONArray(json.getString("payload"));
                        String users = "Invited members ("+invited_users.length()+"):\n";
                        for (int n = 0; n < invited_users.length(); n++) {
                            JSONObject ju = invited_users.getJSONObject(n);
                            String member_username = ju.optString("username");
                            if (!member_username.isEmpty())
                                member_username = " username: @" + member_username + "\n";

                            users += String.format(" id: %s\n name: %s\n%s", ju.optString("id"),
                                    ju.optString("name"), member_username);
                        }
                        s+=users;
                    }

                    logText = s;
                    break;

                case RemoveLeaveMessage:
                    s = chatTitle + userFullName + userIdText + logTime;
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

                    s = chatTitle + userIdText + userFullName + logTime;
                    logText = s;
                    break;

                case BanWordsFloodWarn:
                    s = chatTitle + userIdText + userFullName + logTime;
                    logText = s;
                    break;
                case LinksFloodAttempt:
                    payload = json.getJSONObject("payload");
                    s = chatTitle + userIdText + userFullName +
                            String.format("%s: %s\n", TaskValues.getString(R.string.log_title_banurl), payload.getString("link")) +
                            // "Link: " + payload.getString("link") + "\n" +
                            logTime;
                    logText = s;
                    break;
                case AutoUnbanByAdminInvite:
                    payload = json.getJSONObject("payload");
                    s = chatTitle + userIdText + userFullName +
                            String.format("%s: %s\n", TaskValues.getString(R.string.log_title_admin), payload.getString("adminName")) +
                            // "Admin: " + payload.getString("adminName") + "\n" +
                            logTime;
                    logText = s;
                    break;

                case BanForBlackWord:
                    s = chatTitle + userIdText + userFullName + logTime;
                    payload = json.getJSONObject("payload");
                    long banTime = payload.getLong("banAge") / 1000;
                    s += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime)) +
                            String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bannedword), payload.getString("word")) +
                            String.format("\n%s: %s", TaskValues.getString(R.string.log_title_message), payload.getString("text"));
                    logText = s;
                    break;


                case BanManually:
                    payload = json.getJSONObject("payload");
                    banTime = payload.getLong("banAge") / 1000;
                    s = chatTitle + userFullName + logTime +
                            String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime));

                    String r = payload.getString("banText");
                    if (!r.isEmpty())
                        s += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_ban_reason), r);
                    logText = s;
                    break;


                case BanForFlood:
                    s = chatTitle + userIdText + userFullName + logTime;
                    payload = json.getJSONObject("payload");
                    banTime = payload.getLong("banAge") / 1000;
                    s += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime));
                    logText = s;
                    break;

                case BOT_ERROR:
                    s = "Error code: " + json.optInt("error_code") + "\n" +
                            "Error: " + json.optString("description") + "\n" +
                            "Chat id: " + json.optLong("chatId") + "\n" +
                            logTime;
                    logText = s;
                    break;

                case CMDTitleChanged:
                    s = chatTitle + userIdText + userFullName +
                            String.format("%s: %s\n", TaskValues.getString(R.string.log_title_oldChangedChatTitle), json.optString("oldTitle")) +
                            logTime;
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
