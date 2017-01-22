package com.madpixels.tgadmintools.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.Utils;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.activity.MainActivity;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.BanTask;
import com.madpixels.tgadmintools.entities.BannedWord;
import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.entities.ChatCommand;
import com.madpixels.tgadmintools.entities.ChatTask;
import com.madpixels.tgadmintools.entities.ChatTaskManager;
import com.madpixels.tgadmintools.entities.FormattedTagText;
import com.madpixels.tgadmintools.entities.LogEntity;
import com.madpixels.tgadmintools.helper.CommandsExecutor;
import com.madpixels.tgadmintools.helper.TaskValues;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.utils.AdminUtils;
import com.madpixels.tgadmintools.utils.CommonUtils;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;
import org.drinkless.td.libcore.telegram.TdApi.UpdateNewMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Snake on 08.03.2016.
 */
public class ServiceChatTask extends Service {

    private static boolean isStoppedByUser = false;
    public static boolean IS_RUNNING = false;
    private long mTimeServiceStart;
    boolean isStartForeground = false;
    private MyNotification foregroundNotification;
    private int startID;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void start(Context mContext) {
        if (!IS_RUNNING && DBHelper.getInstance().isChatRulesExists()) {
            mContext.startService(new Intent(mContext, ServiceChatTask.class));
            ServiceBackgroundStarter.startService(mContext); //run background starter helper
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        MyLog.log("ServiceChatTasks removed");
        // if(!isStopByUser)
        //     startService(new Intent(this, ServiceStickerScanner.class));
        super.onTaskRemoved(rootIntent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startID = startId;
        MyLog.log("ServiceChatTasks started. " + startID);
        if (ServiceChatTask.IS_RUNNING) {
            MyLog.log("ServiceChatTasks is already running");
            TgH.init(this);
            TgH.send(new TdApi.GetAuthState());
            return START_STICKY;
        }

        if (BuildConfig.DEBUG)
            LogUtil.showLogNotification("ServiceChatTasks started with rules exists");

        isStoppedByUser = false;
        IS_RUNNING = true;
        mTimeServiceStart = System.currentTimeMillis();

        TgH.init(this, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TgH.setUpdatesHandler(onUpdate);
            }
        });

        isStartForeground = Sets.getBoolean(Const.START_SERVICE_AS_FOREGROUND, true);
        if (isStartForeground) {
            createForegroundNotification();
        }

        return START_STICKY;
    }

    private void createForegroundNotification() {
        foregroundNotification = new MyNotification(300, this);
        PendingIntent pIntent = PendingIntent.getActivity(this, 11, new Intent(this, MainActivity.class), 0);
        foregroundNotification.setContentIntent(pIntent);
        foregroundNotification.mNotificationBuilder.setPriority(Notification.PRIORITY_MIN);
        foregroundNotification.mNotificationBuilder.setContentInfo(getString(R.string.text_notification_descr_background));
        foregroundNotification.startForeground(this);
    }

    void updateForegroundNotification() {
        long sec = (System.currentTimeMillis() - mTimeServiceStart) / 1000;
        foregroundNotification.mNotificationBuilder.setContentText("uptime: " + Utils.convertSecToReadableDate(sec));
        foregroundNotification.alert();
    }


    final Client.ResultHandler onUpdate = new Client.ResultHandler() {
        @Override
        public void onResult(TdApi.TLObject object) {
            if (object.getConstructor() != TdApi.UpdateNewMessage.CONSTRUCTOR)
                return;

            TdApi.UpdateNewMessage message = (TdApi.UpdateNewMessage) object;

            if (isStartForeground && BuildConfig.DEBUG)
                updateForegroundNotification();

            if (BuildConfig.DEBUG && message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;

                if (msgContent.text.equals("p")) {
                    sendPing(message.message);
                    return;
                }
            }

            ChatTaskManager taskManager = new ChatTaskManager(message.message.chatId, true);
            if (taskManager.isEmpty())
                return;

            if (TgUtils.isUserJoined(message.message)) {
                if (taskManager.hasWelcomeText()) {
                    long diffSeconds = (System.currentTimeMillis() / 1000) - message.message.date;
                    if (diffSeconds < 60) {// No welcome if this old update by internet connection
                        String welcomeText = taskManager.getTask(ChatTask.TYPE.WELCOME_USER, false).mText;
                        if (!TextUtils.isEmpty(welcomeText)) {
                            new SendWelcomeMessage(message, welcomeText);
                        }
                    }
                }
                checkUserForInviteByAdmin(message);
            }

            //Check for command execute:
            if (taskManager.hasCommands() && message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;
                if (msgContent.text.startsWith("/") && msgContent.text.length() > 1) {
                    final String[] params = msgContent.text.split(" ", 2);
                    final String command = params[0].substring(1).toLowerCase();
                    ChatCommand cmd = DBHelper.getInstance().getChatCommand(taskManager.chatId, command);
                    if (cmd != null) {
                        if (checkUserIsMuted(message, taskManager)) // ignore commands from muted users
                            return;
                        new ChatCommandExecute(message, taskManager, cmd, params);
                        return;
                    }
                }
            }

            if (!message.message.canBeDeleted)
                return;

            // Игнорируем все сообщения от нас самих.
            if (!BuildConfig.DEBUG) {
                if (TgH.selfProfileId == message.message.senderUserId)
                    return;
            }

            if (taskManager.isRemoveLeaveMessage(message)) {
                AdminUtils.deleteMessage(message, null);
                logLeaveUserMessageDeleted(message, taskManager);
                return;
            }

            if (taskManager.isRemoveJoinedMessage(message)) {
                AdminUtils.deleteMessage(message, null);
                logJoinMessageDeleted(message, taskManager);
                return;
            }


            checkMessageForTask(message, taskManager);
        }
    };

    /**
     * @return true if user was muted
     */
    private boolean checkUserIsMuted(UpdateNewMessage message, ChatTaskManager chatTasks) {
        final ChatTask task = chatTasks.getTask(ChatTask.TYPE.MutedUsers, false);
        if (task == null || !task.isEnabled) {
            //if (task != null)
            //    task.payload = true;// mark that we check this task
            return false;
        }
        if (task.payload == null/* temporary flag for task not checked yet */) {
            TdApi.Message msg = message.message;
            boolean isAllUsersMuted = task.isRemoveMessage;
            boolean isMuteJoined = task.isBanUser && TgUtils.isUserJoined(msg); //isBanUser== mute all joined users
            if (isMuteJoined) { // Add all joined users to muted users list
                if (msg.content.getConstructor() == TdApi.MessageChatJoinByLink.CONSTRUCTOR)
                    DBHelper.getInstance().addMutedUser(msg.chatId, msg.senderUserId, TgUtils.getUser(msg.senderUserId).username);
                else if (msg.content.getConstructor() == TdApi.MessageChatAddMembers.CONSTRUCTOR) {
                    TdApi.MessageChatAddMembers addMembers = (TdApi.MessageChatAddMembers) msg.content;
                    for (TdApi.User user : addMembers.members)
                        DBHelper.getInstance().addMutedUser(msg.chatId, user.id, user.username);
                }
            }
            if (!isMuteJoined /*skip remove invite message*/ && (isAllUsersMuted || DBHelper.getInstance().isUserMuted(chatTasks.chatId, msg.senderUserId))) {
                new MuteUser(message, chatTasks);
                return true;
            }
            task.payload = true;// mark that we check this task
        }
        return false;
    }

    private void checkMessageForTask(UpdateNewMessage message, ChatTaskManager chatTasks) {

        if (checkUserIsMuted(message, chatTasks))
            return;

        if (chatTasks.hasFloodControl()) {
            long diffSeconds = (System.currentTimeMillis() / 1000) - message.message.date;
            if (diffSeconds < 60) {// if this is a newest message
                new FloodControl(message, chatTasks);
            }
        }

        // Check spam links:
        if (chatTasks.hasAttachmentTask(ChatTask.TYPE.LINKS)) {
            final boolean allowStickersLinks = Sets.getBoolean(Const.ANTISPAM_ALLOW_STICKERS_LINKS, true); //TODO maybe make this option individually for each chats.
            final DBHelper db = DBHelper.getInstance();
            String link = null;
            boolean isMention = false;

            if (message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;

                if (msgContent.webPage != null) {
                    //TdApi.WebPage webPage = msgContent.webPage;

                    for (TdApi.MessageEntity e : msgContent.entities) {
                        if (e.getConstructor() != TdApi.MessageEntityUrl.CONSTRUCTOR) continue;
                        TdApi.MessageEntityUrl entityUrl = (TdApi.MessageEntityUrl) e;
                        String oLink = msgContent.text.substring(entityUrl.offset, entityUrl.offset + entityUrl.length);
                        String url = oLink.toLowerCase();
                        if (db.isLinkInWhiteList(url))
                            continue;
                        if (allowStickersLinks && url.contains(".me/addstickers/"))
                            continue;
                        else {
                            link = oLink;
                            break;
                        }
                    }
                } else {
                    final boolean isAllowMentions = Sets.getBoolean(Const.ANTISPAM_ALLOW_MENTION_LINKS, true);
                    loop:
                    for (TdApi.MessageEntity me : msgContent.entities) {
                        switch (me.getConstructor()) {
                            case TdApi.MessageEntityUrl.CONSTRUCTOR:
                                TdApi.MessageEntityUrl mu = (TdApi.MessageEntityUrl) me;
                                String oLink = msgContent.text.substring(mu.offset, mu.offset + mu.length);
                                String url = oLink.toLowerCase();
                                if (db.isLinkInWhiteList(url))
                                    continue;
                                if (allowStickersLinks && url.contains(".me/addstickers/"))
                                    continue;
                                else {
                                    link = oLink;
                                    break loop;
                                }
                            case TdApi.MessageEntityMention.CONSTRUCTOR:
                                if (isAllowMentions)
                                    continue;
                                TdApi.MessageEntityMention mm = (TdApi.MessageEntityMention) me;
                                String username = msgContent.text.substring(mm.offset, mm.offset + mm.length);
                                link = username.substring(1);
                                isMention = true;
                                break loop;
                        }
                    }
                }
            }
            if (link != null) {
                new BanForLink(link, isMention, message, chatTasks);
                return;
            }
        }

        //Check Game sharing flood:
        if (chatTasks.hasAttachmentTask(ChatTask.TYPE.GAME) && (message.message.content.getConstructor() == TdApi.MessageGame.CONSTRUCTOR
                || message.message.content.getConstructor() == TdApi.MessageGameScore.CONSTRUCTOR)) {
            new BanForAttachment(message, chatTasks, ChatTask.TYPE.GAME);
            return;
        }

        // Check stickers flood:
        if (chatTasks.hasAttachmentTask(ChatTask.TYPE.STICKERS) && message.message.content.getConstructor() == TdApi.MessageSticker.CONSTRUCTOR) {
            new BanForAttachment(message, chatTasks, ChatTask.TYPE.STICKERS);
            //new BanForSticker(message, antiSpamRule);
            return;
        }

        //Check voice flood:
        if (chatTasks.hasAttachmentTask(ChatTask.TYPE.VOICE) && message.message.content.getConstructor() == TdApi.MessageVoice.CONSTRUCTOR) {
            new BanForAttachment(message, chatTasks, ChatTask.TYPE.VOICE);
        }

        //Check Images:
        if (chatTasks.hasAttachmentTask(ChatTask.TYPE.IMAGES) && message.message.content.getConstructor() == TdApi.MessagePhoto.CONSTRUCTOR) {
            new BanForAttachment(message, chatTasks, ChatTask.TYPE.IMAGES);
        }

        //Check GIF:
        if (chatTasks.hasAttachmentTask(ChatTask.TYPE.GIF) && message.message.content.getConstructor() == TdApi.MessageAnimation.CONSTRUCTOR) {
            new BanForAttachment(message, chatTasks, ChatTask.TYPE.GIF);
        }

        //Check Audio:
        if (chatTasks.hasAttachmentTask(ChatTask.TYPE.AUDIO) && message.message.content.getConstructor() == TdApi.MessageAudio.CONSTRUCTOR) {
            new BanForAttachment(message, chatTasks, ChatTask.TYPE.AUDIO);
        }

        //Check Docs:
        if (chatTasks.hasAttachmentTask(ChatTask.TYPE.DOCS) && message.message.content.getConstructor() == TdApi.MessageDocument.CONSTRUCTOR) {
            new BanForAttachment(message, chatTasks, ChatTask.TYPE.DOCS);
        }

        //Check Audio Video:
        if (chatTasks.hasAttachmentTask(ChatTask.TYPE.VIDEO) && message.message.content.getConstructor() == TdApi.MessageVideo.CONSTRUCTOR) {
            new BanForAttachment(message, chatTasks, ChatTask.TYPE.VIDEO);
        }


        // Check banwords usage:
        if (chatTasks.hasBanWords() && message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
            int offset = 0;
            TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;
            final String text = msgContent.text.toLowerCase();

            do {
                ChatTask task = chatTasks.getTask(ChatTask.TYPE.BANWORDS);
                ArrayList<BannedWord> words = DBHelper.getInstance().getWordsBlackList(task.chat_id, offset, 200, true);

                for (BannedWord word : words) {
                    final String s = Pattern.quote(word.word.toLowerCase());
                    Pattern p = Pattern.compile("(?<!\\S)" + s + "(?!\\S)");
                    Matcher m = p.matcher(text);
                    //boolean contains = m.find();//text.matches(".*(?<!\\S)" + s + "(?!\\S).*"); /* \b means word boundary */
                    if (m.find()) {
                        new BanForBlackWord(message, chatTasks, word);
                        s.trim();
                        return;
                    }
                }


                offset += words.size();
                if (words.size() < 100)
                    break;

            } while (true);
        }
    }

    /**
     * Remove user from local banList when invited by admins
     *
     * @param message
     */
    private void checkUserForInviteByAdmin(final TdApi.UpdateNewMessage message) {
        if (message.message.content.getConstructor() != TdApi.MessageChatAddMembers.CONSTRUCTOR)
            return;

        final TdApi.MessageChatAddMembers chatAddMembers = (TdApi.MessageChatAddMembers) message.message.content;
        boolean hasBanned = false;
        DBHelper db = DBHelper.getInstance();
        for (final TdApi.User user : chatAddMembers.members) {
            if (db.isBannedById(message.message.chatId, user.id)) {
                hasBanned = true;
                break;
            }
        }
        if (!hasBanned)
            return;

        AdminUtils.checkUserIsAdminInChat(message.message.chatId, message.message.senderUserId, new Callback() {
            @Override
            public void onResult(Object data) {
                boolean isAdmin = (boolean) data;
                if (!isAdmin)
                    return;

                TgH.send(new TdApi.GetChat(message.message.chatId), new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (object.getConstructor() != TdApi.Chat.CONSTRUCTOR)
                            return;
                        TdApi.Chat chat = (TdApi.Chat) object;
                        TdApi.Message msg = message.message;

                        for (final TdApi.User user : chatAddMembers.members) {
                            DBHelper.getInstance().removeUserFromAutoKick(msg.chatId, user.id);
                            DBHelper.getInstance().removeBanTask(msg.chatId, user.id);
                            new LogUtil(ServiceAutoKicker.onLogCallback, object).logUserUnbannedByInvite(chat, user, msg.senderUserId);
                        }
                    }
                });
            }
        });
    }

    private class SendWelcomeMessage {
        UpdateNewMessage message;
        String welcomeText;
        TdApi.Chat chat;

        SendWelcomeMessage(UpdateNewMessage message, String welcomeText) {
            this.message = message;
            this.welcomeText = welcomeText;
            TgH.send(new TdApi.GetChat(message.message.chatId), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR)
                        chat = (TdApi.Chat) object;
                    sendWelcomeText();
                }
            });
        }

        private void sendWelcomeText() {
            ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1);
            TdApi.User user;
            if (message.message.content.getConstructor() == TdApi.MessageChatAddMembers.CONSTRUCTOR) {
                TdApi.MessageChatAddMembers msgAddMember = (TdApi.MessageChatAddMembers) message.message.content;
                user = msgAddMember.members[0];
            } else {
                user = TgUtils.getUser(message.message.senderUserId);
            }

            String botToken = CommonUtils.getBotForChatAlerts(message.message.chatId);
            boolean isBOt = botToken!=null;

            FormattedTagText fText = CommonUtils.replaceCustomShortTags(welcomeText, null, user, -1, chat, isBOt); //TODO check html format
            welcomeText = fText.resultText;
            if (fText.mention != null)
                entities.add(fText.mention);

            TdApi.InputMessageText msg = new TdApi.InputMessageText(welcomeText, false, false, null, null);
            msg.entities = new TdApi.MessageEntity[entities.size()];
            for (int n = 0; n < entities.size(); n++)
                msg.entities[n] = entities.get(n);


            if (isBOt) {
                CommonUtils.sendMessageViaBot(botToken, message.message.chatId, welcomeText, true, false);
            } else {
                TdApi.TLFunction f = new TdApi.SendMessage(message.message.chatId, message.message.id, false, true,
                        null, msg);
                TgH.send(f);
            }
        }
    }

    private void sendPing(TdApi.Message message) {
        TdApi.SendMessage msgSend = new TdApi.SendMessage();
        msgSend.chatId = message.chatId;
        msgSend.replyToMessageId = message.id;
        TdApi.InputMessageText msgText = new TdApi.InputMessageText();
        msgText.text = "Pong";
        msgSend.inputMessageContent = msgText;
        TgH.send(msgSend, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                // MyLog.log(object.toString());
            }
        });
    }

    private abstract class TaskAction {
        ChatTaskManager taskControl;
        public ChatTask task;
        public UpdateNewMessage message;
        public TdApi.Chat mChat;
        LogUtil logUtil;

        public LogUtil getLog() {
            if (logUtil == null)
                logUtil = new LogUtil(onLogCallback, taskControl);
            return logUtil;
        }

        TaskAction(final TdApi.UpdateNewMessage message, ChatTaskManager chatTasks) {
            this.taskControl = chatTasks;
            this.message = message;
        }

        void getChat() {
            loadChatInfo(message.message.chatId, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    mChat = (TdApi.Chat) object;
                    onChatLoad();
                }
            });
        }

        void onChatLoad() {
            checkUserInPhoneBook();
        }

        void onUserInPhoneBookChecked() {
            AdminUtils.checkUserIsAdminInChat(mChat.id, message.message.senderUserId, onCheckIsAdmin);
        }

        void onCheckIsAdmin(boolean isAdmin) {
            if (isAdmin)
                return;
            AdminUtils.checkIsBot(message.message.senderUserId, onCheckIsBot);
        }


        void checkUserInPhoneBook() { // друзьям разрешаем спамить.
            if (Sets.getBoolean(Const.ANTISPAM_IGNORE_SHARED_CONTACTS, Const.ANTISPAM_IGNORE_SHARED_CONTACTS_DEFAULT))
                AdminUtils.checkUserIsInContactList(message.message.senderUserId, onCheckIsContact);
            else //go to next step
                onUserInPhoneBookChecked();
        }


        final Callback onCheckIsContact = new Callback() {
            @Override
            public void onResult(Object boolData) {
                boolean isContact = (boolean) boolData;
                if (isContact)
                    return;
                onUserInPhoneBookChecked();
            }
        };

        final Callback onCheckIsAdmin = new Callback() {
            @Override
            public void onResult(Object data) {
                boolean isAdmin = (boolean) data;
                onCheckIsAdmin(isAdmin);
            }
        };

        Callback onCheckIsBot = new Callback() {
            @Override
            public void onResult(Object data) {
                boolean isBot = (boolean) data;
                if (isBot) // TODO check if bot allowed
                    return;
                onAction();
            }
        };

        public void onAction() {
            doAction();
        }

        abstract void doAction();

        void warnUser(final int tryes) {
            TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                    TdApi.User user = (TdApi.User) object;

                    String botToken = CommonUtils.getBotForChatAlerts(task.chat_id);
                    boolean isBot = botToken != null;

                    String warnText = getAlertTitle();
                    if (isBot) // Wrap title to Markdown format for Bot Message
                        warnText = "<code>" + TextUtils.htmlEncode(warnText) + "</code>";
                    warnText += "\n";

                    ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1); // Форматируем сообщение.
                    entities.add(new TdApi.MessageEntityCode(0, warnText.length())); // wrap title to highlited string


                    int textType = task.selectWarningText(tryes);
                    String customText;
                    if (task.mType == ChatTask.TYPE.FLOOD) // Flood have only lastWarnText
                        customText = task.mWarnTextLast;
                    else // Select warning text, for Firts warn or last warn before ban
                        customText = textType == 1 ? task.mWarnTextFirst : task.mWarnTextLast;

                    if (customText == null) {
                        if (!user.username.isEmpty()) {
                            warnText += "@" + user.username + "\n";
                        } else {
                            //name mention:
                            String uname = CommonUtils.safeHtml(user.firstName + " " + user.lastName, isBot);
                            int start = warnText.length();
                            warnText += uname + "\n";
                            entities.add(new TdApi.MessageEntityMentionName(start, uname.length(), user.id));
                        }


                        customText = TaskValues.getWarnText(task.mType, textType);

                    } else {
                        FormattedTagText fText = CommonUtils.replaceCustomShortTags(customText, task, user, tryes, mChat, isBot);
                        customText = fText.resultText;
                        if (fText.mention != null)
                            entities.add(fText.mention);
                    }

                    int offset = warnText.length();
                    int length = customText.length();
                    entities.add(new TdApi.MessageEntityCode(offset, length));
                    warnText += customText;

                    if (isBot) {
                        CommonUtils.sendMessageViaBot(botToken, message.message.chatId, warnText, true, false);
                    } else {
                        TdApi.SendMessage msgSend = new TdApi.SendMessage();
                        msgSend.chatId = message.message.chatId;
                        msgSend.replyToMessageId = message.message.id;
                        TdApi.InputMessageText msgText = new TdApi.InputMessageText();
                        msgText.text = warnText;

                        msgText.entities = new TdApi.MessageEntity[entities.size()];
                        for (int n = 0; n < entities.size(); n++)
                            msgText.entities[n] = entities.get(n);
                        msgSend.inputMessageContent = msgText;
                        TgH.send(msgSend, new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                MyLog.log(object.toString());
                            }
                        });
                    }
                }
            });

        }

        void banForMessage(final Client.ResultHandler onKickUserFromGroup) {
            TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                    final TdApi.User user = (TdApi.User) object;
                    String banReason = TaskValues.getBanReason(task.mType);

                    int localChatId = TgUtils.getChatRealId(mChat);
                    boolean isReturn = task.isReturnOnBanExpired;
                    final long banMsec = task.mBanTimeSec * 1000;
                    DBHelper.getInstance().addToBanList(user, message.message.chatId, mChat.type.getConstructor(), localChatId,
                            banMsec, isReturn, banReason, task.isMuteInsteadBan);
                    if (banMsec > 0)
                        ServiceUnbanTask.registerTask(getBaseContext());

                    if (TgUtils.isGroup(mChat.type.getConstructor())) {
                        DBHelper.getInstance().addUserToAutoKick(mChat.id, message.message.senderUserId);
                        ServiceAutoKicker.start(getBaseContext());
                    }

                    if (task.isMuteInsteadBan) {
                        getLog().logUserWasMuted(mChat, message);
                        isMessageRemoveByTaskMuted();
                        if (task.isPublicToChat) {
                            publishBanReasonToChat();
                        }
                    } else
                        AdminUtils.kickUser(message.message.chatId, message.message.senderUserId, new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (TgUtils.isOk(object)) {
                                    //Reset warns for this user:
                                    DBHelper.getInstance().deleteAntiSpamWarnCount(task.mType, message.message.chatId, message.message.senderUserId);
                                    if (task.isPublicToChat) {
                                        publishBanReasonToChat();
                                    }
                                    onKickUserFromGroup.onResult(object);
                                }
                            }
                        });
                }
            });
        }

        /**
         * If bantask with isMutedBan exists means that user already muted by task settings
         */
        boolean isMessageRemoveByTaskMuted() {
            BanTask banTask = DBHelper.getInstance().getBanTask(task.chat_id, message.message.senderUserId);
            if (banTask != null && banTask.isMutedBan) {
                deleteMessage(new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        String msgText;
                        if (message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                            TdApi.MessageText msg = (TdApi.MessageText) message.message.content;
                            msgText = msg.text;
                        } else {
                            msgText = "Type: " + message.message.content.getClass().getSimpleName();
                        }
                        getLog().logDeleteMessage(LogUtil.Action.RemoveMuted, mChat, message, msgText);
                        MyLog.log("");
                    }
                });
                return true;
            }
            return false;
        }

        /**
         * Send to chat for all users notifications that user was banned/muted
         */
        void publishBanReasonToChat() {
            String title = task.isMuteInsteadBan ?
                    getString(R.string.logAction_userWasMuted) + ": " + Utils.capitalizeString(task.mType.toString()) :
                    TaskValues.getBanReason(task.mType);

            TdApi.User user = TgUtils.getUser(message.message.senderUserId);
            String username = "";
            if (!TextUtils.isEmpty(user.username))
                username = " @" + user.username;
            String userFullName = ((user.firstName + " " + user.lastName).trim() + " " + username).trim();
            String msg =
                    "<b>" + TextUtils.htmlEncode(title) + "</b>\n" +
                            "<b>" + TextUtils.htmlEncode(getString(R.string.log_title_username)) + "</b>: " +
                            userFullName;
            if (task.mBanTimeSec > 0) {
                msg += "\n<b>" + TextUtils.htmlEncode(getString(R.string.log_title_banuntil)) + "</b>: " +
                        CommonUtils.tsToDate(System.currentTimeMillis() / 1000 + (task.mBanTimeSec));
            }

            String botToken = CommonUtils.getBotForChatAlerts(message.message.chatId);
            if (botToken != null) {
                CommonUtils.sendMessageViaBot(botToken, message.message.chatId, msg, true, false);
            } else {
                TdApi.SendMessage msgSend = new TdApi.SendMessage();
                msgSend.chatId = message.message.chatId;
                msgSend.replyToMessageId = message.message.id;
                TdApi.InputMessageText msgText = new TdApi.InputMessageText();
                msgText.text = msg;
                msgText.parseMode = new TdApi.TextParseModeHTML();
                msgSend.inputMessageContent = msgText;
                TgH.send(msgSend);
            }
        }


        void deleteMessage(final Client.ResultHandler onDelete) {
            AdminUtils.deleteMessage(message, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (TgUtils.isOk(object))
                        onDelete.onResult(object);
                }
            });
        }
    }


    public class ChatCommandExecute extends TaskAction {
        public ChatCommand pCommand;
        public String[] params;

        public ChatCommandExecute(UpdateNewMessage message, ChatTaskManager antiSpamRule, ChatCommand cmd, String[] params) {
            super(message, antiSpamRule);
            task = antiSpamRule.getTask(ChatTask.TYPE.COMMAND);
            pCommand = cmd;
            this.params = params;
            getChat();
        }

        @Override
        void onChatLoad() {
            //check if command only for admins
            if (pCommand.isAdmin) {
                AdminUtils.checkUserIsAdminInChat(mChat.id, message.message.senderUserId, onCheckIsAdmin);
            } else {
                doAction();
            }
        }

        @Override
        void onCheckIsAdmin(boolean isAdmin) {
            if (isAdmin) {
                if (pCommand.type == ChatCommand.CMD_KICK_SENDER) {
                    //Creator can't leave channel so not allow self to execute command
                    AdminUtils.checkIsCreator(mChat.id, message.message.senderUserId, new Callback() {
                        @Override
                        public void onResult(Object data) {
                            boolean isCreator = (boolean) data;
                            if (isCreator)
                                sendCreatorDeniedAnswer();
                            else
                                doAction();
                        }
                    });
                } else {
                    doAction();
                }
            } else {
                sendDeniedAnswer();
            }
        }


        @Override
        void doAction() {
            new CommandsExecutor(getBaseContext(), this).executeCommand();
        }

        void sendDeniedAnswer() {
            String text = getString(R.string.text_command_execute_access_denied);

            String botToken = CommonUtils.getBotForChatAlerts(task.chat_id);
            if (botToken != null) {
                CommonUtils.sendMessageViaBot(botToken, message.message.chatId, text, false, false);
            } else {
                TdApi.SendMessage msgSend = new TdApi.SendMessage();
                msgSend.chatId = message.message.chatId;
                msgSend.replyToMessageId = message.message.id;
                TdApi.InputMessageText msgText = new TdApi.InputMessageText();
                msgText.text = text;

                msgSend.inputMessageContent = msgText;
                TgH.send(msgSend, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        //MyLog.log(object.toString());
                    }
                });
            }
        }

        private void sendCreatorDeniedAnswer() {
            String text = getString(R.string.text_command_leave_not_error_creator);
            String botToken = CommonUtils.getBotForChatAlerts(task.chat_id);
            if (botToken != null) {
                CommonUtils.sendMessageViaBot(botToken, message.message.chatId, text, false, false);
            } else {
                TdApi.SendMessage msgSend = new TdApi.SendMessage();
                msgSend.chatId = message.message.chatId;
                msgSend.replyToMessageId = message.message.id;
                TdApi.InputMessageText msgText = new TdApi.InputMessageText();
                msgText.text = text;

                msgSend.inputMessageContent = msgText;
                TgH.send(msgSend, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        //MyLog.log(object.toString());
                    }
                });
            }
        }

    }


    private class BanForBlackWord extends TaskAction {
        private BannedWord bannedWord;

        BanForBlackWord(UpdateNewMessage message, ChatTaskManager antiSpamRule, final BannedWord bannedWord) {
            super(message, antiSpamRule);
            task = antiSpamRule.getTask(ChatTask.TYPE.BANWORDS);
            this.bannedWord = bannedWord;
            getChat();
        }


        @Override
        void doAction() {
            if (bannedWord.isBanUser) {
                final int tryes = DBHelper.getInstance().getTaskWarnedCount(ChatTask.TYPE.BANWORDS,
                        message.message.chatId, message.message.senderUserId, task.mWarningsDuringTime);
                int warnCount = task.mAllowCountPerUser;

                if (tryes < warnCount) {// Warn user to stop
                    DBHelper.getInstance().setAntiSpamWarnCount(ChatTask.TYPE.BANWORDS, message.message.chatId, message.message.senderUserId, tryes + 1);
                    if (task.isWarnAvailable(tryes)) {
                        getLog().logWarningBeforeBan(task.mType, mChat, message, tryes);
                        warnUser(tryes);
                    }
                } else {// Remove prohibited message and ban user if checked.

                    banForMessage(new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            final TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;
                            try {
                                final long banMsec = task.mBanTimeSec * 1000;
                                JSONObject payload = new JSONObject().put("word", bannedWord.word).put("text", msgContent.text).put("banAge", banMsec);
                                getLog().logBanUser(LogUtil.Action.BanForBlackWord, mChat, message, payload);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            if (bannedWord.isDeleteMsg) {
                deleteMessage(new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        getLog().logDeleteMessage(LogUtil.Action.DeleteMsgBlackWord, mChat, message, bannedWord.word);
                    }
                });
            }
        }
    }

    static Callback onLogCallback = new Callback() {
        @Override
        public void onResult(Object data) {
            LogUtil logUtil = (LogUtil) data;
            LogEntity log = logUtil.logEntity;
            ChatTaskManager chatTasks = (ChatTaskManager) logUtil.callbackPayload;
            CommonUtils.forwardLogEventToChat(chatTasks.chatId, log);
        }
    };

    private void logLeaveUserMessageDeleted(final UpdateNewMessage message, final ChatTaskManager taskManager) {
        TgH.send(new TdApi.GetChat(message.message.chatId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR) {
                    TdApi.Chat chat = (TdApi.Chat) object;
                    LogUtil logUtil = new LogUtil(onLogCallback, taskManager);

                    logUtil.logDeleteMessage(LogUtil.Action.RemoveLeaveMessage, chat, message);
                }
            }
        });

    }

    private void logJoinMessageDeleted(final UpdateNewMessage message, final ChatTaskManager taskManager) {
        TgH.send(new TdApi.GetChat(message.message.chatId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR) {
                    TdApi.Chat chat = (TdApi.Chat) object;
                    LogUtil logUtil = new LogUtil(onLogCallback, taskManager);

                    if (message.message.content.getConstructor() == TdApi.MessageChatAddMembers.CONSTRUCTOR) {
                        try {
                            TdApi.MessageChatAddMembers chatAddMembers = (TdApi.MessageChatAddMembers) message.message.content;
                            JSONArray users = new JSONArray();
                            for (TdApi.User u : chatAddMembers.members) {
                                JSONObject ju = new JSONObject()
                                        .put("id", u.id)
                                        .put("name", (u.firstName + " " + u.lastName).trim())
                                        .put("username", u.username);
                                users.put(ju);
                            }
                            logUtil.logDeleteMessage(LogUtil.Action.RemoveJoinMessage, chat, message, users.toString());
                        } catch (JSONException e) {

                        }
                    } else
                        logUtil.logDeleteMessage(LogUtil.Action.RemoveJoinMessage, chat, message);
                }
            }
        });
    }

    /**
     * Attachments like Voice, Sticker, Gif, Video, Audio, Game Score
     */
    private class BanForAttachment extends TaskAction {
        BanForAttachment(final TdApi.UpdateNewMessage message, ChatTaskManager antiSpamRule, ChatTask.TYPE pType) {
            super(message, antiSpamRule);
            task = antiSpamRule.getTask(pType);
            getChat();
        }

        @Override
        void doAction() {
            if (task.isBanUser) {
                long floodTimeLimit = task.mWarningsDuringTime;
                final int tryes = DBHelper.getInstance().getTaskWarnedCount(task.mType, message.message.chatId, message.message.senderUserId, floodTimeLimit);
                final int limit = task.mAllowCountPerUser;

                if (isMessageRemoveByTaskMuted()) {
                    DBHelper.getInstance().deleteAntiSpamWarnCount(task.mType, message.message.chatId, message.message.senderUserId);
                    return;
                }

                if (tryes < limit) {
                    DBHelper.getInstance().setAntiSpamWarnCount(task.mType, message.message.chatId, message.message.senderUserId, tryes + 1);

                    if (task.isWarnAvailable(tryes)) {
                        getLog().logWarningBeforeBan(task.mType, mChat, message, tryes);
                        warnUser(tryes);
                    }

                } else {
                    banForMessage(new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            try {
                                JSONObject payload = new JSONObject().put("banAge", task.mBanTimeSec * 1000);
                                LogUtil.Action action = null;
                                switch (task.mType) {
                                    //NOTE add new type here when new type implemented
                                    case STICKERS:
                                        action = LogUtil.Action.BanForSticker;
                                        break;
                                    case VOICE:
                                        action = LogUtil.Action.BanForVoice;
                                        break;
                                    case GAME:
                                        action = LogUtil.Action.BanForGame;
                                        //TODO add game id, title.
                                        break;
                                    case IMAGES:
                                        action = LogUtil.Action.BanForImages;
                                        //TdApi.MessagePhoto photo = (TdApi.MessagePhoto) message.message.content;
                                        // payload.put("photo_id", photo.photo.id);
                                        break;
                                    case DOCS:
                                        action = LogUtil.Action.BanForDocs;
                                        break;
                                    case GIF:
                                        action = LogUtil.Action.BanForGif;
                                        //TdApi.MessageAnimation animation = (TdApi.MessageAnimation) message.message.content;
                                        //payload.put("animation_id", animation.animation.animation.persistentId);
                                        break;
                                    case AUDIO:
                                        action = LogUtil.Action.BanForAudio;
                                        break;
                                    case VIDEO:
                                        action = LogUtil.Action.BanForVideo;
                                        break;
                                }
                                getLog().logBanUser(action, mChat, message, payload);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            if (task.isRemoveMessage) {
                deleteMessage(new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        LogUtil.Action action = null;
                        //NOTE add new type here when new type implemented
                        switch (task.mType) {
                            case STICKERS:
                                action = LogUtil.Action.RemoveSticker;
                                break;
                            case VOICE:
                                action = LogUtil.Action.RemoveVoice;
                                break;
                            case GAME:
                                action = LogUtil.Action.RemoveGame;
                                break;
                            case IMAGES:
                                action = LogUtil.Action.RemoveImage;
                                break;
                            case DOCS:
                                action = LogUtil.Action.RemoveDocs;
                                break;
                            case GIF:
                                action = LogUtil.Action.RemoveGif;
                                break;
                            case AUDIO:
                                action = LogUtil.Action.RemoveAudio;
                                break;
                            case VIDEO:
                                action = LogUtil.Action.RemoveVideo;
                                break;
                        }
                        getLog().logDeleteMessage(action, mChat, message);
                    }
                });
            }
        }
    }

    /**
     * Callback will be called only when chatId is valid.
     */
    private void loadChatInfo(long chatId, final Client.ResultHandler callback) {
        TgH.send(new TdApi.GetChat(chatId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() != TdApi.Chat.CONSTRUCTOR) {
                    return;
                }
                callback.onResult(object);
            }
        });
    }

    static String getMessageText(TdApi.UpdateNewMessage message) {
        if (message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
            TdApi.MessageText msgText = (TdApi.MessageText) message.message.content;
            return msgText.text;
        }

        return message.message.content.toString();
    }

    private class BanForLink extends TaskAction {
        /**
         * link or mention in lower case
         */
        String mLink;
        String originalLink;// original link as is case sensitive
        boolean isMention;

        BanForLink(String link, boolean isMention, UpdateNewMessage message, ChatTaskManager antiSpamRule) {
            super(message, antiSpamRule);
            this.originalLink = link;
            this.mLink = link.toLowerCase();
            this.isMention = isMention;
            this.task = antiSpamRule.getTask(ChatTask.TYPE.LINKS);
            getChat();
        }

        //переопределяем чтоб проверить ссылку перед баном
        @Override
        public void onAction() {
            checkLinkForAllow();
        }


        void checkLinkForAllow() {
            if (!isMention && mLink.indexOf(".me/") == -1) { // this is not a telegram internal link (to group or chat member)
                doAction();
                return;
            }

            boolean isChatLink = mLink.contains("joinchat");

            if (!isMention && !isChatLink) {// не ссылка на чат
                checkLinkIsChatMember();
                return;
            } else if (isMention) {// if it's mention then load user by this mention and check if it chat member
                TgH.send(new TdApi.SearchPublicChat(mLink), new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (TgUtils.isError(object)) {
                            doAction();
                            return;
                        }

                        TdApi.Chat pChat = (TdApi.Chat) object;
                        if (pChat.id == mChat.id) //link to self chat
                            return;

                        if (pChat.type.getConstructor() == TdApi.PrivateChatInfo.CONSTRUCTOR) {
                            TdApi.PrivateChatInfo chatInfo = (TdApi.PrivateChatInfo) pChat.type;
                            boolean isIgnoreLinksForChatMembers = false;
                            AdminUtils.checkUserIsChatMember(mChat.id, chatInfo.user.id, new Callback() {
                                @Override
                                public void onResult(Object data) {
                                    boolean isMember = (boolean) data;
                                    if (isMember)
                                        return;
                                    doAction();
                                }
                            });
                        } else {
                            doAction();
                        }
                    }
                });
            } else if (isChatLink) {
                TgUtils.getChatFullInfo(mChat, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (object.getConstructor() == TdApi.ChannelFull.CONSTRUCTOR) { // суперчат
                            TdApi.ChannelFull channelFull = (TdApi.ChannelFull) object;
                            if (channelFull.inviteLink.toLowerCase().contains(mLink)) // это ссылка на сам чат
                                return;
                            //if (!channelFull.channel.username.isEmpty() && ("@" + channelFull.channel.username.toLowerCase()).equals(mLink)) // логин группы
                            //    return;

                        } else if (object.getConstructor() == TdApi.GroupFull.CONSTRUCTOR) { // группа
                            TdApi.GroupFull groupFull = (TdApi.GroupFull) object;
                            if (groupFull.inviteLink.toLowerCase().contains(mLink)) // это ссылка на текущий чат, скипаем.
                                return;
                        }
                        doAction();// баним
                    }
                });
            }
        }

        /**
         * если разрешены ссылки на участников чата то проверим, может это ссылка на участника чата.
         */
        private void checkLinkIsChatMember() {
            boolean isIgnoreLinksForChatMembers = BuildConfig.DEBUG ? false : true;
            int pos = mLink.indexOf(".me/");
            String mention = mLink.substring(pos + 4);

            if (!isIgnoreLinksForChatMembers) {// если выключено, переходим к слеждующему шагу
                checkLinkIsChatAdmins(mention);
                return;
            }

            AdminUtils.checkUserIsChatMember(mChat.id, mention, new Callback() {
                @Override
                public void onResult(Object data) {
                    boolean isMember = (boolean) data;
                    if (!isMember)
                        doAction();
                }
            });
        }

        private void checkLinkIsChatAdmins(String username) {
            TgH.send(new TdApi.SearchPublicChat(username), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR) {
                        TdApi.Chat pChat = (TdApi.Chat) object;
                        if (pChat.type.getConstructor() == TdApi.PrivateChatInfo.CONSTRUCTOR) {
                            TdApi.PrivateChatInfo pi = (TdApi.PrivateChatInfo) pChat.type;
                            AdminUtils.checkUserIsAdminInChat(mChat.id, pi.user.id, new Callback() {
                                @Override
                                public void onResult(Object data) {
                                    boolean isAdmin = (boolean) data;
                                    if (!isAdmin)
                                        doAction();
                                }
                            });
                        }
                    } else {
                        doAction();
                    }
                }
            });
        }

        @Override
        void doAction() {
            final ChatTask task = taskControl.getTask(ChatTask.TYPE.LINKS);
            if (task.isBanUser) {
                final int tryes = DBHelper.getInstance().getTaskWarnedCount(ChatTask.TYPE.LINKS, message.message.chatId, message.message.senderUserId, task.mWarningsDuringTime);
                final int limit = task.mAllowCountPerUser;

                // If bantask exists means that user already banned or muted
                if (isMessageRemoveByTaskMuted())
                    return;

                if (tryes < limit) {
                    DBHelper.getInstance().setAntiSpamWarnCount(ChatTask.TYPE.LINKS, message.message.chatId, message.message.senderUserId, tryes + 1);

                    if (task.isWarnAvailable(tryes)) { // алертим при первой попытке спама и при последней.
                        getLog().logWarningBeforeBanForLink(mChat, message, tryes, originalLink);
                        warnUser(tryes);
                    }
                } else {
                    banForMessage(new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            try {
                                JSONObject payload;
                                String msg = getMessageText(message);
                                if (TgUtils.isOk(object)) {
                                    payload = new JSONObject().put("link", originalLink).put("text", msg).put("banAge", task.mBanTimeSec * 1000);
                                } else {
                                    payload = new JSONObject().put("error", object.toString()).put("text", msg);
                                }
                                getLog().logBanUser(LogUtil.Action.BanForLink, mChat, message, payload);
                            } catch (Exception e) {
                            }
                        }
                    });
                }
            }

            if (task.isRemoveMessage) {
                deleteMessage(new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        String msg = getMessageText(message);
                        getLog().logDeleteMessage(LogUtil.Action.RemoveLink, mChat, message, originalLink + "\n" + msg);
                    }
                });
            }
        }
    }

    class MuteUser extends TaskAction {
        MuteUser(UpdateNewMessage message, ChatTaskManager antiSpamRule) {
            super(message, antiSpamRule);
            this.task = antiSpamRule.getTask(ChatTask.TYPE.MutedUsers);
            getChat();
        }

        @Override
        void doAction() {
            deleteMessage(new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    String msgText;
                    if (message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                        TdApi.MessageText msg = (TdApi.MessageText) message.message.content;
                        msgText = msg.text;
                    } else {
                        msgText = "Type: " + message.message.content.getClass().getSimpleName();
                    }

                    getLog().logDeleteMessage(LogUtil.Action.RemoveMuted, mChat, message, msgText);
                }
            });
        }

    }

    class FloodControl extends TaskAction {

        FloodControl(UpdateNewMessage message, ChatTaskManager antiSpamRule) {
            super(message, antiSpamRule);
            this.task = antiSpamRule.getTask(ChatTask.TYPE.FLOOD);
            getChat();
        }

        @Override
        void doAction() {
            if (task.isEnabled) {
                long floodTimeLimit = task.mWarningsDuringTime;
                final int tryes = DBHelper.getInstance().getTaskWarnedCount(task.mType, message.message.chatId, message.message.senderUserId, floodTimeLimit);
                final int limit = task.mAllowCountPerUser;
                if (tryes < limit) {
                    // LogUtil.logBanForFloodAttempt(chat, message, tryes);
                    DBHelper.getInstance().setAntiSpamWarnCount(task.mType, message.message.chatId, message.message.senderUserId, tryes + 1);

                    if (task.isWarnAvailable(tryes)) {
                        // getLog().logBanForFloodAttempt(chat, message, tryes);
                        warnUser(tryes);
                    }

                } else {
                    // Remove overflood message:
                    AdminUtils.deleteMessage(message, new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (TgUtils.isOk(object)) {

                                String messageText;
                                if (message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                                    TdApi.MessageText msg = (TdApi.MessageText) message.message.content;
                                    messageText = msg.text;
                                } else {
                                    try {
                                        //get type of message (Sticker, Image, Video, etc..)
                                        messageText = message.message.content.getClass().getSimpleName();
                                    } catch (Exception e) {
                                        messageText = "null";
                                    }
                                }
                                JSONObject j = new JSONObject();
                                try {
                                    j.put("flood", tryes).put("limit", limit).put("msg", messageText);
                                } catch (JSONException e) {
                                }

                                getLog().logDeleteMessage(LogUtil.Action.RemoveFlood, mChat, message, j.toString());
                            }
                        }
                    });

                    if (task.isBanUser) {
                        TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                                TdApi.User user = (TdApi.User) object;

                                String banReason = getString(R.string.logAction_banForFlood);
                                int localchatid = TgUtils.getChatRealId(mChat);
                                DBHelper.getInstance().addToBanList(user, message.message.chatId, mChat.type.getConstructor(), localchatid,
                                        task.mBanTimeSec * 1000, task.isReturnOnBanExpired, banReason, task.isMuteInsteadBan);
                                ServiceUnbanTask.registerTask(getBaseContext());
                            }
                        });

                        DBHelper.getInstance().deleteAntiSpamWarnCount(ChatTask.TYPE.STICKERS, message.message.chatId, message.message.senderUserId);
                        if (TgUtils.isGroup(mChat.type.getConstructor())) {
                            DBHelper.getInstance().addUserToAutoKick(mChat.id, message.message.senderUserId);
                            ServiceAutoKicker.start(getBaseContext());
                        }

                        AdminUtils.kickUser(message.message.chatId, message.message.senderUserId, new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR) {
                                    try {
                                        JSONObject payload = new JSONObject().put("banAge", task.mBanTimeSec * 1000);
                                        getLog().logBanUser(LogUtil.Action.BanForFlood, mChat, message, payload);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    if (task.isPublicToChat)
                                        publishBanReasonToChat();
                                }
                            }
                        });

                    }
                }
            }
        }

    }

    private String getAlertTitle() {
        String title = Sets.getString(Const.ANTISPAM_ALERT_TITLE, getString(R.string.text_antispam_alert_title));
        return title;
    }


    public static void stop(Context c) {
        isStoppedByUser = true;
        IS_RUNNING = false;
        c.stopService(new Intent(c, ServiceChatTask.class));
    }

    @Override
    public void onDestroy() {
        MyLog.log("ServiceAntispam destroyed");
        TgH.removeUpdatesHandler(onUpdate);
        IS_RUNNING = false;
        //handler.removeCallbacks(repeatingTask);
        if (!isStoppedByUser) {
            start(getBaseContext());
        }
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
