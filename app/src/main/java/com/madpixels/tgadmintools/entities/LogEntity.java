package com.madpixels.tgadmintools.entities;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.Utils;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.helper.TaskValues;
import com.madpixels.tgadmintools.utils.CommonUtils;
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

        String actionTitle = "";
        String logMsg = null;

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
                logTime = String.format("%s: %s", TaskValues.getString(R.string.log_title_time),
                        CommonUtils.tsToDate(json.optLong("ts") / 1000));
            if (userId != 0)
                userIdText = String.format("User id: %s\n", userId);

            actionTitle = TaskValues.getString(TaskValues.getTitleLogAction(action), action.name());

            switch (action) {
                //NOTE add new type here when new type implemented
                case AutoKickFromGroup:
                    long ts;
                    logMsg = chatTitle + userIdText + userFullName + logTime;


                    break;
                case AutoReturnToChat:
                    if (json.has("error")) {
                        logMsg = json.getString("error") + "\n";
                        logMsg += "User id: " + json.getInt("userId") + "\n";
                    } else
                        logMsg = chatTitle + userFullName;
                    logMsg += logTime;

                    break;
                case USER_MUTED:
                    logMsg = chatTitle + userFullName + logTime;
                    String msgText = json.optString("payload");
                    logMsg += String.format("\n%s: %s\n", TaskValues.getString(R.string.log_title_message), msgText);

                    break;

                case USER_UNMUTED:
                    logMsg = chatTitle + userFullName + logTime;
                    break;

                case AutoUnban:
                    if (json.has("error")) {
                        logMsg = json.getString("error") + "\n" +
                                chatTitle + userFullName;
                        actionTitle = "Error on unban user";
                    } else
                        logMsg = chatTitle + userFullName;
                    logMsg += logTime;

                    break;
                case BanForLink:
                    // actionTitle = getString(R.string.logAction_banForLink);
                    logMsg = chatTitle + userFullName + logTime;
                    JSONObject payload = json.getJSONObject("payload");
                    if (payload.has("error"))
                        logMsg += "\nOperation error: " + payload.getString("error");
                    else {
                        long banTime = payload.getLong("banAge");
                        linkUrl = payload.getString("link");
                        logMsg += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_banurl), linkUrl);
                        logMsg += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime / 1000));//ban length
                    }
                    logMsg += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_message), payload.getString("text"));

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
                    logMsg = chatTitle + userIdText + userFullName + logTime;

                    payload = json.getJSONObject("payload");
                    long banAge = payload.getLong("banAge");
                    if (banAge > 0) {
                        logMsg += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banAge / 1000));//ban length
                        logMsg += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_banuntil),
                                CommonUtils.tsToDate((ts + banAge) / 1000));// ban until
                    }


                    break;

                    /* Remove messaage: */
                case RemoveLink:
                    logMsg = chatTitle + userIdText + userFullName +
                            String.format("%s: %s\n", TaskValues.getString(R.string.log_title_banurl), json.getString("payload")) +//banned url
                            logTime;

                    break;
                case DeleteMsgBlackWord:
                    logMsg = chatTitle + userIdText + userFullName + logTime +
                            String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bannedword), json.getString("payload"));

                    break;
                case RemoveFlood:
                    payload = new JSONObject(json.getString("payload"));
                    String messageText = payload.getString("msg");
                    int limit = payload.optInt("limit");
                    int floodCount = payload.optInt("flood");

                    logMsg = chatTitle + userIdText + userFullName + logTime;
                    logMsg += String.format("\nFlood: %s/%s", floodCount, limit);
                    logMsg += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_message), messageText);

                    break;
                case RemoveVoice:
                case RemoveDocs:
                case RemoveGame:
                case RemoveGif:
                case RemoveImage:
                case RemoveSticker:
                case RemoveAudio:
                case RemoveVideo:
                    logMsg = chatTitle + userIdText + userFullName + logTime;

                    break;
                case RemoveMuted:
                    String message = json.optString("payload");
                    logMsg = chatTitle + userIdText + "Message: " + message + "\n" + userFullName + logTime;

                    break;

                case RemoveJoinMessage:
                    logMsg = chatTitle + userFullName + userIdText + logTime + "\n";
                    if (json.has("payload")) {
                        JSONArray invited_users = new JSONArray(json.getString("payload"));
                        String users = "Invited members (" + invited_users.length() + "):\n";
                        for (int n = 0; n < invited_users.length(); n++) {
                            JSONObject ju = invited_users.getJSONObject(n);
                            String member_username = ju.optString("username");
                            if (!member_username.isEmpty())
                                member_username = " username: @" + member_username + "\n";

                            users += String.format(" id: %s\n name: %s\n%s", ju.optString("id"),
                                    ju.optString("name"), member_username);
                        }
                        logMsg += users;
                    }


                    break;

                case RemoveLeaveMessage:
                    logMsg = chatTitle + userFullName + userIdText + logTime;

                    break;

                    /* Other */
                case CommandExecError:
                    payload = json.getJSONObject("payload");
                    logMsg = chatTitle + logTime + "\n" +
                            String.format("Command: %s\n", payload.getString("cmd")) +
                            String.format("Error: %s\n", payload.getString("error")) +
                            String.format("Body: %s\n", payload.getString("answer"));

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

                    logMsg = chatTitle + userIdText + userFullName + logTime;

                    break;

                case BanWordsFloodWarn:
                    logMsg = chatTitle + userIdText + userFullName + logTime;

                    break;
                case LinksFloodAttempt:
                    payload = json.getJSONObject("payload");
                    logMsg = chatTitle + userIdText + userFullName +
                            String.format("%s: %s\n", TaskValues.getString(R.string.log_title_banurl), payload.getString("link")) +
                            // "Link: " + payload.getString("link") + "\n" +
                            logTime;

                    break;
                case AutoUnbanByAdminInvite:
                    payload = json.getJSONObject("payload");
                    logMsg = chatTitle + userIdText + userFullName +
                            String.format("%s: %s\n", TaskValues.getString(R.string.log_title_admin), payload.getString("adminName")) +
                            // "Admin: " + payload.getString("adminName") + "\n" +
                            logTime;

                    break;

                case BanForBlackWord:
                    logMsg = chatTitle + userIdText + userFullName + logTime;
                    payload = json.getJSONObject("payload");
                    long banTime = payload.getLong("banAge") / 1000;
                    logMsg += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime)) +
                            String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bannedword), payload.getString("word")) +
                            String.format("\n%s: %s", TaskValues.getString(R.string.log_title_message), payload.getString("text"));

                    break;


                case BanManually:
                    payload = json.getJSONObject("payload");
                    banTime = payload.getLong("banAge") / 1000;
                    logMsg = chatTitle + userFullName + logTime +
                            String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime));

                    String r = payload.getString("banText");
                    if (!r.isEmpty())
                        logMsg += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_ban_reason), r);

                    break;


                case BanForFlood:
                    logMsg = chatTitle + userIdText + userFullName + logTime;
                    payload = json.getJSONObject("payload");
                    banTime = payload.getLong("banAge") / 1000;
                    logMsg += String.format("\n%s: %s", TaskValues.getString(R.string.log_title_bantime), Utils.convertSecToReadableDate(banTime));

                    break;

                case BOT_ERROR:
                    logMsg ="Chat id: " + json.optLong("chatId") + "\n" +
                            "Error code: " + json.optInt("error_code") + "\n" +
                            logTime+
                            "Error: " + json.optString("description");
                    if(json.has("payload"))
                        logMsg+="\nMessage body:\n"+json.optString("payload")+"\n";

                    break;

                case CMDTitleChanged:
                    logMsg = chatTitle + userIdText + userFullName +
                            String.format("%s: %s\n", TaskValues.getString(R.string.log_title_oldChangedChatTitle), json.optString("oldTitle")) +
                            logTime;
                    break;

                default:
                    actionTitle = action.name();
                    logMsg = jsonData;
                    break;
            }
        } catch (JSONException e) {
            MyLog.log(e);
            logMsg = jsonData;// on parsing error show raw data
        }

        logText = logMsg;
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
