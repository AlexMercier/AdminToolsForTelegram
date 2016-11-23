package com.madpixels.tgadmintools.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.Sets;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.activity.MainActivity;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.BannedWord;
import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.entities.ChatCommand;
import com.madpixels.tgadmintools.entities.ChatTask;
import com.madpixels.tgadmintools.entities.ChatTaskControl;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.utils.AdminUtils;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;
import org.drinkless.td.libcore.telegram.TdApi.UpdateNewMessage;
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

    public static void start(Context mContext) {
        if (!IS_RUNNING && DBHelper.getInstance().isChatRulesExists()) {
            mContext.startService(new Intent(mContext, ServiceChatTask.class));
            ServiceBackgroundStarter.startService(mContext); //run background starter helper
            if (BuildConfig.DEBUG)
                LogUtil.showLogNotification("ServiceAntispam started with rules exists");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.log("ServiceAntispam started");
        if (ServiceChatTask.IS_RUNNING) {
            MyLog.log("ServiceAntispam is already running");
            TgH.init(this);
            TgH.send(new TdApi.GetAuthState());
            return START_STICKY;
        }

        isStoppedByUser = false;
        IS_RUNNING = true;

        TgH.init(this, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TgH.setUpdatesHandler(onUpdate);
            }
        });

        boolean isStartForeground = Sets.getBoolean(Const.START_SERVICE_AS_FOREGROUND, true);
        if (isStartForeground) {
            createForegroundNotification();
        }

        return START_STICKY;
    }

    private void createForegroundNotification() {
        MyNotification notification = new MyNotification(300, this);
        PendingIntent pIntent = PendingIntent.getActivity(this, 11, new Intent(this, MainActivity.class), 0);
        notification.setContentIntent(pIntent);
        notification.mNotificationBuilder.setPriority(Notification.PRIORITY_MIN);
        notification.mNotificationBuilder.setContentInfo(getString(R.string.text_notification_descr_background));
        notification.startForeground(this);
    }


    final Client.ResultHandler onUpdate = new Client.ResultHandler() {
        @Override
        public void onResult(TdApi.TLObject object) {
            if (object.getConstructor() == TdApi.UpdateNewMessage.CONSTRUCTOR) {
                TdApi.UpdateNewMessage message = (TdApi.UpdateNewMessage) object;

                long chatId = message.message.chatId;

                if (TgUtils.isUserJoined(message.message)) {
                    String welcomeText = DBHelper.getInstance().getWelcomeText(chatId);
                    if (welcomeText != null)
                        sendWelcomeText(message, welcomeText);
                }

                ChatTaskControl antispamTasks = new ChatTaskControl(chatId, true);

                //Check for command execute:
                if (antispamTasks.hasCommands() && message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                    TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;
                    if (msgContent.text.startsWith("/") && msgContent.text.length() > 1) {
                        final String command = msgContent.text.substring(1).toLowerCase();
                        ChatCommand cmd = DBHelper.getInstance().getChatCommand(antispamTasks.chatId, command);
                        if (cmd != null) {
                            new ChatCommandExecute(message, antispamTasks, cmd);
                            return;
                        }
                    }
                }

                if (!message.message.canBeDeleted)
                    return;


                if (antispamTasks.isEmpty())
                    return;

                if (antispamTasks.isRemoveLeaveMessage(message)) {
                    AdminUtils.deleteMessage(message, null);
                    return;
                }

                if (antispamTasks.isRemoveJoinedMessage(message)) {
                    AdminUtils.deleteMessage(message, null);
                    return;
                }

                if (BuildConfig.DEBUG && message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                    TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;

                    if (msgContent.text.equals("p")) {
                        sendPing(message.message);
                    }
                }

                // Игнорируем все сообщения от нас самих.
                if (TgH.selfProfileId == message.message.senderUserId)
                    return;

                checkMessageForTask(message, antispamTasks);
            }
        }
    };

    private void sendWelcomeText(UpdateNewMessage message, String welcomeText) {
        ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1);
        TdApi.User user;
        if (message.message.content.getConstructor() == TdApi.MessageChatAddMembers.CONSTRUCTOR) {
            TdApi.MessageChatAddMembers msgAddMember = (TdApi.MessageChatAddMembers) message.message.content;
            user = msgAddMember.members[0];
        } else {
            user = TgUtils.getUser(message.message.senderUserId);
        }


        FormattedTagText fText = replaceWarningsShortTags(welcomeText, null, user, 0);
        welcomeText = fText.resultText;
        if (fText.mention != null)
            entities.add(fText.mention);
        //welcomeText = replaceWarningsShortTags(welcomeText, null, user, 0);
        TdApi.InputMessageText msg = new TdApi.InputMessageText(welcomeText, false, false, null, null);
        msg.entities = new TdApi.MessageEntity[entities.size()];
        for (int n = 0; n < entities.size(); n++)
            msg.entities[n] = entities.get(n);

        TdApi.TLFunction f = new TdApi.SendMessage(message.message.chatId, message.message.id, false, true,
                null, msg);
        TgH.send(f);
    }

    private void checkMessageForTask(UpdateNewMessage message, ChatTaskControl antiSpamRule) {

        if (antiSpamRule.hasFloodControl()) {
            long diffSeconds = (System.currentTimeMillis() / 1000) - message.message.date;
            if (diffSeconds < 60) {// if this is a newest message
                new FloodControl(message, antiSpamRule);
            }
        }

        // Check spam links:
        if (antiSpamRule.hasLinksTask()) {
            final boolean allowStickersLinks = Sets.getBoolean(Const.ANTISPAM_ALLOW_STICKERS_LINKS, true); //TODO maybe make this option individually for each chats.
            final DBHelper db = DBHelper.getInstance();
            String link = null;

            if (message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;

                if (msgContent.webPage != null) {
                    //TdApi.WebPage webPage = msgContent.webPage;

                    for (TdApi.MessageEntity e : msgContent.entities) {
                        if (e.getConstructor() != TdApi.MessageEntityUrl.CONSTRUCTOR) continue;
                        TdApi.MessageEntityUrl entityUrl = (TdApi.MessageEntityUrl) e;
                        String url = msgContent.text.substring(entityUrl.offset, entityUrl.offset + entityUrl.length).toLowerCase();
                        if (db.isLinkInWhiteList(url))
                            continue;
                        if (allowStickersLinks && url.contains("telegram.me/addstickers/"))
                            continue;
                        else {
                            link = url;
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
                                String url = msgContent.text.substring(mu.offset, mu.offset + mu.length).toLowerCase();
                                if (db.isLinkInWhiteList(url))
                                    continue;
                                if (allowStickersLinks && url.contains("telegram.me/addstickers/"))
                                    continue;
                                else {
                                    link = url;
                                    break loop;
                                }
                            case TdApi.MessageEntityMention.CONSTRUCTOR:
                                if (isAllowMentions)
                                    continue;
                                TdApi.MessageEntityMention mm = (TdApi.MessageEntityMention) me;
                                String username = msgContent.text.substring(mm.offset, mm.offset + mm.length).toLowerCase();
                                link = username; //TODO add @?
                                break loop;
                        }
                    }
                }

//                else {
//                    String patternStr = "(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
//                    Pattern pattern = Pattern.compile(patternStr);
//                    Matcher matcher = pattern.matcher(msgText.text);
//                    String result = "";
//                    while (matcher.find()) {
//                        result = matcher.group();
//                        result = result.replace(" ", "");
//                        MyLog.log(result);
//                        //String rawName = result.replace("@", "");
//                        //String userHTML="<a href='http://twitter.com/${rawName}'>" + result + "</a>"
//                        //tweetText = tweetText.replace(result,userHTML);
//                    }
//                    //return tweetText;
//                }
                //return;
            }
            if (link != null) {
                new BanForLink(link, message, antiSpamRule);
                return;
            }
        }


        // Check stickers flood:
        if (antiSpamRule.hasStickerTask() && message.message.content.getConstructor() == TdApi.MessageSticker.CONSTRUCTOR) {
            new BanForSticker(message, antiSpamRule);
            return;
        }

        //Check voice flood:
        if (message.message.content.getConstructor() == TdApi.MessageVoice.CONSTRUCTOR) {
            new BanForVoice(message, antiSpamRule);
        }


        // Check banwords usage:
        if (antiSpamRule.hasBanWords() && message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
            int offset = 0;
            TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;
            final String text = msgContent.text.toLowerCase();

            do {
                ChatTask task = antiSpamRule.getTask(ChatTask.TYPE.BANWORDS);
                ArrayList<BannedWord> words = DBHelper.getInstance().getWordsBlackList(task.chat_id, offset, 200, true);

                for (BannedWord word : words) {
                    boolean contains = text.matches(".*\\b" + word.word.toLowerCase() + "\\b.*"); /* \b means word boundary */
                    if (contains) {
                        new BanForBlackWord(message, antiSpamRule, word);
                        return;
                    }
                }


                offset += words.size();
                if (words.size() < 100)
                    break;

            } while (true);
        }


        // if(message.message.content.getConstructor() == TdApi.MessagePhoto.CONSTRUCTOR && taskControl.isImagesFloodEnabled){
        //     new BanForImage(message, taskControl);
        // }
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

    /*
    private class BanForImage extends Ban {

        BanForImage(UpdateNewMessage message, AntiSpamRule taskControl) {
            super(message, taskControl);
            getChat();
        }

        @Override
        void doAction() {
            int tryes = DBHelper.getInstance().getAntiSpamWarnCount(LogUtil.Action.ImagesFloodWarn, message.message.chatId, message.message.fromId, 60 * 60 * 1000);
            int limit = taskControl.mImagesFloodLimit;
            if (tryes < limit) {
                DBHelper.getInstance().setAntiSpamWarnCount(LogUtil.Action.ImagesFloodWarn, message.message.chatId, message.message.fromId, tryes + 1);
            } else { // баним за флуд
                LogUtil.logBanUser(LogUtil.Action.BanForImages, chat, message);
                TgH.send(new TdApi.GetUser(message.message.fromId), new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                        TdApi.User user = (TdApi.User) object;
                        String banReason = getString(R.string.logAction_banForSticker);// TODO banForImages
                        int localChatId = TgUtils.getChatRealId(chat);
                        DBHelper.getInstance().addToBanList(user, message.message.chatId, chat.type.getConstructor(), localChatId,
                                taskControl.imagesBanAge, taskControl.isImagesReturnOnUnban, banReason);
                        ServiceUnbanTask.registerTask(getBaseContext());
                    }
                });

                DBHelper.getInstance().deleteAntiSpamWarnCount(LogUtil.Action.ImagesFloodWarn, message.message.chatId, message.message.fromId);
                if (TgUtils.isGroup(chat.type.getConstructor())) {
                    DBHelper.getInstance().addUserToAutoKick(chat.id, message.message.fromId);
                    ServiceAutoKicker.start(getBaseContext());
                }

                AdminUtils.kickUser(message.message.chatId, message.message.fromId, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR)
                            LogUtil.logBanUser(LogUtil.Action.BanForImages, chat, message);
                    }
                });
            }
        }
    } */

    private abstract class TaskAction {
        ChatTaskControl taskControl;
        ChatTask task;
        UpdateNewMessage message;
        TdApi.Chat chat;

        TaskAction(final TdApi.UpdateNewMessage message, ChatTaskControl chatTasks) {
            this.taskControl = chatTasks;
            this.message = message;
        }

        void getChat() {
            loadChatInfo(message.message.chatId, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    chat = (TdApi.Chat) object;
                    onChatLoad();
                }
            });
        }

        void onChatLoad() {
            checkUserInPhoneBook();
        }

        void onUserInPhoneBookChecked() {
            AdminUtils.checkUserIsAdminInChat(chat, message.message.senderUserId, onCheckIsAdmin);
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
    }


    private class BanForVoice extends TaskAction {

        BanForVoice(UpdateNewMessage message, ChatTaskControl chatTasks) {
            super(message, chatTasks);
            task = chatTasks.getTask(ChatTask.TYPE.VOICE);
            getChat();
        }

        @Override
        void doAction() {
            if (task.isBanUser) {
                final int tryes = DBHelper.getInstance().getAntiSpamWarnCount(ChatTask.TYPE.VOICE, message.message.chatId, message.message.senderUserId, task.mWarningsDuringTime);
                final int limit = task.mAllowCountPerUser;
                if (tryes < limit) {
                    DBHelper.getInstance().setAntiSpamWarnCount(ChatTask.TYPE.VOICE, message.message.chatId, message.message.senderUserId, tryes + 1);
                    LogUtil.logBanForStickerAttempt(chat, message, tryes);
                    if (task.isWarnAvailable(tryes)) {
                        TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                                TdApi.User user = (TdApi.User) object;

                                String warnText = getAlertTitle() + "\n";
                                ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1); // Форматируем сообщение.
                                entities.add(new TdApi.MessageEntityCode(0, warnText.length())); // wrap title to highlited string
                                //TODO send warn via bot if bot token passed.

                                int textType = task.selectWarningText(tryes);
                                String customText = textType == 1 ? task.mWarnTextFirst : task.mWarnTextLast;

                                if (customText == null) {
                                    if (!user.username.isEmpty())
                                        warnText += "@" + user.username + "\n";
                                    else {
                                        //name mention:
                                        String uname = user.firstName + " " + user.lastName;
                                        int start = warnText.length();
                                        warnText += uname + "\n";
                                        entities.add(new TdApi.MessageEntityMentionName(start, uname.length(), user.id));
                                    }

                                    if (textType == 1)
                                        customText = getString(R.string.warntext_voice_default_first);
                                    else
                                        customText = getString(R.string.warntext_voice_default_last);
                                } else {
                                    FormattedTagText fText = replaceWarningsShortTags(customText, task, user, tryes);
                                    customText = fText.resultText;
                                    if (fText.mention != null)
                                        entities.add(fText.mention);
                                    //customText = replaceWarningsShortTags(customText, task, user, tryes);
                                }

                                int offset = warnText.length();
                                int length = customText.length();
                                entities.add(new TdApi.MessageEntityCode(offset, length));
                                warnText += customText;

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
                        });
                    }
                    //////
                } else { // баним за флуд
                    TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                            TdApi.User user = (TdApi.User) object;
                            String banReason = getString(R.string.logAction_banForVoice);
                            int localchatid = TgUtils.getChatRealId(chat);
                            DBHelper.getInstance().addToBanList(user, message.message.chatId, chat.type.getConstructor(), localchatid,
                                    task.mBanTimeSec * 1000, task.isReturnOnBanExpired, banReason);
                            if (task.isReturnOnBanExpired)
                                ServiceUnbanTask.registerTask(getBaseContext());
                        }
                    });

                    DBHelper.getInstance().deleteAntiSpamWarnCount(ChatTask.TYPE.VOICE, message.message.chatId, message.message.senderUserId);
                    if (TgUtils.isGroup(chat.type.getConstructor())) {
                        DBHelper.getInstance().addUserToAutoKick(chat.id, message.message.senderUserId);
                        ServiceAutoKicker.start(getBaseContext());
                    }

                    AdminUtils.kickUser(message.message.chatId, message.message.senderUserId, new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR) {
                                try {
                                    JSONObject payload = new JSONObject().put("banAge", task.mBanTimeSec * 1000);
                                    LogUtil.logBanUser(LogUtil.Action.BanForVoice, chat, message, payload);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }

            if (task.isRemoveMessage) {
                AdminUtils.deleteMessage(message, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR)
                            LogUtil.logDeleteMessage(LogUtil.Action.RemoveVoice, chat, message);
                    }
                });
            }
        }
    }

    private class ChatCommandExecute extends TaskAction {
        ChatCommand pCommand;

        public ChatCommandExecute(UpdateNewMessage message, ChatTaskControl antiSpamRule, ChatCommand cmd) {
            super(message, antiSpamRule);
            task = antiSpamRule.getTask(ChatTask.TYPE.VOICE);
            pCommand = cmd;
            getChat();
        }

        @Override
        void onChatLoad() {
            //check if command only for admins
            if (pCommand.isAdmin) {
                AdminUtils.checkUserIsAdminInChat(chat, message.message.senderUserId, onCheckIsAdmin);
            } else {
                doAction();
            }
        }

        @Override
        void onCheckIsAdmin(boolean isAdmin) {
            if (isAdmin) {
                AdminUtils.checkIsCreator(chat, message.message.senderUserId, new Callback() {
                    @Override
                    public void onResult(Object data) {
                        boolean isCreator = (boolean) data;
                        if (!isCreator)
                            doAction();
                        else
                            sendCreatorDeniedAnswer();
                    }
                });
                doAction();
            } else {
                sendDeniedAnswer();
            }
        }


        @Override
        void doAction() {
            if (pCommand.type == 0) {
                TdApi.SendMessage msgSend = new TdApi.SendMessage();
                msgSend.chatId = message.message.chatId;
                msgSend.replyToMessageId = message.message.id;
                TdApi.InputMessageText msgText = new TdApi.InputMessageText();

                TdApi.User user = TgUtils.getUser(message.message.senderUserId);
                FormattedTagText formattedTagText = replaceWarningsShortTags(pCommand.answer, task, user, 0);
                msgText.text = formattedTagText.resultText;
                if (formattedTagText.mention != null) {
                    msgText.entities = new TdApi.MessageEntity[1];
                    msgText.entities[0]=formattedTagText.mention;
                }

                msgSend.inputMessageContent = msgText;
                TgH.send(msgSend, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        //MyLog.log(object.toString());
                    }
                });
            } else {
                AdminUtils.kickUser(message.message.chatId, message.message.senderUserId, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        MyLog.log(object.toString());
                    }
                });
            }
        }

        void sendDeniedAnswer() {
            TdApi.SendMessage msgSend = new TdApi.SendMessage();
            msgSend.chatId = message.message.chatId;
            msgSend.replyToMessageId = message.message.id;
            TdApi.InputMessageText msgText = new TdApi.InputMessageText();
            msgText.text = "Access denied.\nOnly admin can execute this command";

            msgSend.inputMessageContent = msgText;
            TgH.send(msgSend, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    //MyLog.log(object.toString());
                }
            });
        }

        private void sendCreatorDeniedAnswer() {
            TdApi.SendMessage msgSend = new TdApi.SendMessage();
            msgSend.chatId = message.message.chatId;
            msgSend.replyToMessageId = message.message.id;
            TdApi.InputMessageText msgText = new TdApi.InputMessageText();
            msgText.text = "Creator can't leave the channel";

            msgSend.inputMessageContent = msgText;
            TgH.send(msgSend, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    //MyLog.log(object.toString());
                }
            });
        }

    }


    private class BanForBlackWord extends TaskAction {
        private BannedWord bannedWord;

        BanForBlackWord(UpdateNewMessage message, ChatTaskControl antiSpamRule, final BannedWord bannedWord) {
            super(message, antiSpamRule);
            task = antiSpamRule.getTask(ChatTask.TYPE.BANWORDS);
            this.bannedWord = bannedWord;
            getChat();
        }


        @Override
        void doAction() {
            final TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;

            if (bannedWord.isBanUser) {
                final int tryes = DBHelper.getInstance().getAntiSpamWarnCount(ChatTask.TYPE.BANWORDS, message.message.chatId, message.message.senderUserId, task.mWarningsDuringTime);
                int warnCount = task.mAllowCountPerUser;

                if (tryes < warnCount) {
                    LogUtil.logBanForBanWordAttempt(chat, message, tryes);
                    DBHelper.getInstance().setAntiSpamWarnCount(ChatTask.TYPE.BANWORDS, message.message.chatId, message.message.senderUserId, tryes + 1);
                    if (task.isWarnAvailable(tryes)) {
                        TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                                TdApi.User user = (TdApi.User) object;

                                String warnText = getAlertTitle() + "\n";
                                ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1); // Форматируем сообщение.
                                entities.add(new TdApi.MessageEntityCode(0, warnText.length())); // wrap title to highlited string
                                //TODO send warn via bot if bot token passed.

                                int textType = task.selectWarningText(tryes);
                                String customText = textType == 1 ? task.mWarnTextFirst : task.mWarnTextLast;

                                if (customText == null) {
                                    if (!user.username.isEmpty())
                                        warnText += "@" + user.username + "\n";
                                    else {
                                        //name mention:
                                        String uname = user.firstName + " " + user.lastName;
                                        int start = warnText.length();
                                        warnText += uname + "\n";
                                        entities.add(new TdApi.MessageEntityMentionName(start, uname.length(), user.id));
                                    }

                                    if (textType == 1)
                                        customText = getString(R.string.warntext_banword_first);
                                    else
                                        customText = getString(R.string.warntext_banword_last);
                                } else {
                                    FormattedTagText fText = replaceWarningsShortTags(customText, task, user, tryes);
                                    customText = fText.resultText;
                                    if (fText.mention != null)
                                        entities.add(fText.mention);
                                    //customText = replaceWarningsShortTags(customText, task, user, tryes);
                                }

                                int offset = warnText.length();
                                int length = customText.length();
                                entities.add(new TdApi.MessageEntityCode(offset, length));
                                warnText += customText;

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
                        });
                    }
                } else {
                    TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                            final TdApi.User user = (TdApi.User) object;
                            String banReason = getString(R.string.logAction_banForBlackWord); // TODO custom text
                            int localChatId = TgUtils.getChatRealId(chat);
                            boolean isReturn = task.isReturnOnBanExpired;
                            final long banMsec = task.mBanTimeSec * 1000;
                            DBHelper.getInstance().addToBanList(user, message.message.chatId, chat.type.getConstructor(), localChatId,
                                    banMsec, isReturn, banReason);
                            if (banMsec > 0)
                                ServiceUnbanTask.registerTask(getBaseContext());

                            if (TgUtils.isGroup(chat.type.getConstructor())) {
                                DBHelper.getInstance().addUserToAutoKick(chat.id, message.message.senderUserId);
                                ServiceAutoKicker.start(getBaseContext());
                            }

                            AdminUtils.kickUser(message.message.chatId, message.message.senderUserId, new Client.ResultHandler() {
                                @Override
                                public void onResult(TdApi.TLObject object) {
                                    if (TgUtils.isOk(object)) {
                                        //Reset warns for this user:
                                        DBHelper.getInstance().deleteAntiSpamWarnCount(ChatTask.TYPE.BANWORDS, message.message.chatId, message.message.senderUserId);
                                        try {
                                            JSONObject payload = new JSONObject().put("word", bannedWord.word).put("text", msgContent.text).put("banAge", banMsec);
                                            LogUtil.logBanUser(LogUtil.Action.BanForBlackWord, chat, message, payload);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }

                    });
                }

            }

            if (bannedWord.isDeleteMsg) {
                AdminUtils.deleteMessage(message, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (TgUtils.isOk(object))
                            LogUtil.logDeleteMessage(LogUtil.Action.DeleteMsgBlackWord, chat, message, bannedWord.word);
                    }
                });
            }
        }
    }


    private class BanForSticker extends TaskAction {

        BanForSticker(final TdApi.UpdateNewMessage message, ChatTaskControl antiSpamRule) {
            super(message, antiSpamRule);
            task = antiSpamRule.getTask(ChatTask.TYPE.STICKERS);
            getChat();
        }


        @Override
        void doAction() {
            if (task.isBanUser) {
                long floodTimeLimit = task.mWarningsDuringTime;
                final int tryes = DBHelper.getInstance().getAntiSpamWarnCount(ChatTask.TYPE.STICKERS, message.message.chatId, message.message.senderUserId, floodTimeLimit);
                final int limit = task.mAllowCountPerUser;
                if (tryes < limit) {
                    LogUtil.logBanForStickerAttempt(chat, message, tryes);
                    DBHelper.getInstance().setAntiSpamWarnCount(ChatTask.TYPE.STICKERS, message.message.chatId, message.message.senderUserId, tryes + 1);

                    if (task.isWarnAvailable(tryes)) {
                        TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                                TdApi.User user = (TdApi.User) object;

                                ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1); // Форматируем сообщение.
                                String warnText = getAlertTitle() + "\n";
                                entities.add(new TdApi.MessageEntityCode(0, warnText.length()));

                                int textType = task.selectWarningText(tryes);
                                String customText = textType == 1 ? task.mWarnTextFirst : task.mWarnTextLast;

                                if (customText == null) {
                                    if (!user.username.isEmpty())
                                        warnText += "@" + user.username + "\n";
                                    else {
                                        //name mention:
                                        String uname = user.firstName + " " + user.lastName;
                                        int start = warnText.length();
                                        warnText += uname + "\n";
                                        entities.add(new TdApi.MessageEntityMentionName(start, uname.length(), user.id));
                                    }

                                    if (textType == 1)
                                        customText = getString(R.string.warntext_stickers_default_first);
                                    else
                                        customText = getString(R.string.warntext_stickers_default_last);
                                } else {
                                    FormattedTagText ftext = replaceWarningsShortTags(customText, task, user, tryes);
                                    customText = ftext.resultText;
                                    if (ftext.mention != null)
                                        entities.add(ftext.mention);
                                    //customText = replaceWarningsShortTags(customText, task, user, tryes);
                                }

                                int offset = warnText.length();
                                int length = customText.length();
                                entities.add(new TdApi.MessageEntityCode(offset, length));
                                warnText += customText;

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
                        });
                    }

                } else {

                    TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                            TdApi.User user = (TdApi.User) object;
                            String banReason = getString(R.string.logAction_banForSticker);
                            int localchatid = TgUtils.getChatRealId(chat);
                            DBHelper.getInstance().addToBanList(user, message.message.chatId, chat.type.getConstructor(), localchatid,
                                    task.mBanTimeSec * 1000, task.isReturnOnBanExpired, banReason);
                            ServiceUnbanTask.registerTask(getBaseContext());
                        }
                    });

                    DBHelper.getInstance().deleteAntiSpamWarnCount(ChatTask.TYPE.STICKERS, message.message.chatId, message.message.senderUserId);
                    if (TgUtils.isGroup(chat.type.getConstructor())) {
                        DBHelper.getInstance().addUserToAutoKick(chat.id, message.message.senderUserId);
                        ServiceAutoKicker.start(getBaseContext());
                    }

                    AdminUtils.kickUser(message.message.chatId, message.message.senderUserId, new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR) {
                                try {
                                    JSONObject payload = new JSONObject().put("banAge", task.mBanTimeSec * 1000);
                                    LogUtil.logBanUser(LogUtil.Action.BanForSticker, chat, message, payload);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }

            if (task.isRemoveMessage) {
                AdminUtils.deleteMessage(message, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR)
                            LogUtil.logDeleteMessage(LogUtil.Action.RemoveSticker, chat, message);
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
        //if (message.message.content.getConstructor() == TdApi.MessageWebPage.CONSTRUCTOR) {
        ////    TdApi.MessageWebPage webPage = (TdApi.MessageWebPage) message.message.content;
        //    return webPage.text;
        //}
        if (message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
            TdApi.MessageText msgText = (TdApi.MessageText) message.message.content;
            return msgText.text;
        }

        return message.message.content.toString();
    }

    private class BanForLink extends TaskAction {
        String mLink;

        BanForLink(String link, UpdateNewMessage message, ChatTaskControl antiSpamRule) {
            super(message, antiSpamRule);
            this.mLink = link;
            getChat();
        }

        //переопределяем чтоб проверить ссылку перед баном
        @Override
        public void onAction() {
            loadChatInfo(message.message.chatId, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    chat = (TdApi.Chat) object;
                    checkLinkForAllow();
                }
            });
        }


        void checkLinkForAllow() {
            TgUtils.getChatFullInfo(chat, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (object.getConstructor() == TdApi.ChannelFull.CONSTRUCTOR) { // суперчат
                        TdApi.ChannelFull channelFull = (TdApi.ChannelFull) object;
                        if (channelFull.inviteLink.toLowerCase().contains(mLink)) // это ссылка на сам чат
                            return;
                        if (!channelFull.channel.username.isEmpty() && ("@" + channelFull.channel.username.toLowerCase()).equals(mLink)) // логин группы //TODO проверить
                            return;

                    } else if (object.getConstructor() == TdApi.GroupFull.CONSTRUCTOR) { // группа
                        TdApi.GroupFull groupFull = (TdApi.GroupFull) object;
                        if (groupFull.inviteLink.toLowerCase().contains(mLink)) // это ссылка на текущий чат, скипаем.
                            return;
                    } else {

                    }
                    checkLinkIsChatMember();// проверим может это ссылка на юзера чата
                }
            });
        }


        /**
         * если разрешены ссылки на участников чата то проверим, может это ссылка научастника чата.
         */
        private void checkLinkIsChatMember() {
            boolean isIgnoreLinksForChatMembers = true;
            if (!isIgnoreLinksForChatMembers) {// если выключено, переходим к слеждующему шагу
                checkLinkIsChatAdmins();
                return;
            }

            TgUtils.getGroupLastMembers(chat, new Callback() {
                @Override
                public void onResult(Object data) {
                    TdApi.ChatMember[] participants = (TdApi.ChatMember[]) data;
                    for (TdApi.ChatMember participant : participants) {
                        TdApi.User user = TgUtils.getUser(participant);
                        if (user.username.isEmpty()) continue;
                        String mention = "@" + user.username.toLowerCase();
                        String link = "telegram.me/" + user.username.toLowerCase();
                        if (mLink.equals(mention) || mLink.contains(link))
                            return;
                    }

                    checkLinkIsChatAdmins();
                }
            });
        }

        private void checkLinkIsChatAdmins() {
            AdminUtils.getChatAdmins(chat, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    TdApi.ChatMembers admins = (TdApi.ChatMembers) object;
                    for (TdApi.ChatMember chatMember : admins.members) {
                        TdApi.User user = TgUtils.getUser(chatMember);
                        if (user.username.isEmpty()) continue;
                        String mention = "@" + user.username.toLowerCase();
                        String link = "telegram.me/" + user.username;
                        if (mLink.equals(mention) || mLink.contains(link))
                            return;
                    }
                    doAction();
                }
            });
        }

        @Override
        void doAction() {
            final ChatTask task = taskControl.getTask(ChatTask.TYPE.LINKS);
            if (taskControl.isBanForLinks()) {
                final int tryes = DBHelper.getInstance().getAntiSpamWarnCount(ChatTask.TYPE.LINKS, message.message.chatId, message.message.senderUserId, task.mWarningsDuringTime);
                final int limit = task.mAllowCountPerUser;
                if (tryes < limit) {
                    DBHelper.getInstance().setAntiSpamWarnCount(ChatTask.TYPE.LINKS, message.message.chatId, message.message.senderUserId, tryes + 1);
                    LogUtil.logBanForLinksAttempt(chat, message, tryes, mLink);

                    if (task.isWarnAvailable(tryes)) { // еслиртим при первой попытке спама и при последней.
                        TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;

                                TdApi.User user = (TdApi.User) object;
                                String warnText = getAlertTitle() + "\n";
                                ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1); // Форматируем сообщение.
                                entities.add(new TdApi.MessageEntityCode(0, warnText.length())); // wrap title to highlited string
                                //TODO send warn via bot if bot token passed.

                                int textType = task.selectWarningText(tryes);
                                String customText = textType == 1 ? task.mWarnTextFirst : task.mWarnTextLast;

                                if (customText == null) {
                                    if (!user.username.isEmpty())
                                        warnText += "@" + user.username + "\n";
                                    else {
                                        //name mention:
                                        String uname = user.firstName + " " + user.lastName;
                                        int start = warnText.length();
                                        warnText += uname + "\n";
                                        entities.add(new TdApi.MessageEntityMentionName(start, uname.length(), user.id));
                                    }

                                    if (textType == 1)
                                        customText = getString(R.string.warntext_links_default_firts);
                                    else
                                        customText = getString(R.string.warntext_links_default_last);
                                } else {
                                    FormattedTagText fText = replaceWarningsShortTags(customText, task, user, tryes);
                                    customText = fText.resultText;
                                    if (fText.mention != null)
                                        entities.add(fText.mention);
                                    //customText = replaceWarningsShortTags(customText, task, user, tryes);
                                }

                                int offset = warnText.length();
                                int length = customText.length();
                                entities.add(new TdApi.MessageEntityCode(offset, length));
                                warnText += customText;

                                TdApi.SendMessage msgSend = new TdApi.SendMessage();
                                msgSend.chatId = message.message.chatId;
                                msgSend.replyToMessageId = message.message.id;

                                TdApi.InputMessageText msgText = new TdApi.InputMessageText();
                                msgText.text = warnText;
                                msgSend.inputMessageContent = msgText;
                                msgText.entities = entities.toArray(new TdApi.MessageEntity[0]);

                                TgH.send(msgSend, new Client.ResultHandler() {
                                    @Override
                                    public void onResult(TdApi.TLObject object) {
                                        MyLog.log(object.toString());
                                    }
                                });
                            }
                        });
                    }
                } else {
                    TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                            TdApi.User user = (TdApi.User) object;
                            String banReason = getString(R.string.logAction_banForLink);
                            int localChatId = TgUtils.getChatRealId(chat);
                            DBHelper.getInstance().addToBanList(user, message.message.chatId, chat.type.getConstructor(), localChatId,
                                    task.mBanTimeSec * 1000, task.isReturnOnBanExpired, banReason);
                            if (task.mBanTimeSec > 0)
                                ServiceUnbanTask.registerTask(getBaseContext());
                            if (TgUtils.isGroup(chat.type.getConstructor())) {
                                DBHelper.getInstance().addUserToAutoKick(chat.id, message.message.senderUserId);
                                ServiceAutoKicker.start(getBaseContext());
                            }
                        }
                    });

                    //Reset warns for this user:
                    DBHelper.getInstance().deleteAntiSpamWarnCount(ChatTask.TYPE.LINKS, message.message.chatId, message.message.senderUserId);

                    AdminUtils.kickUser(message.message.chatId, message.message.senderUserId, new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            try {
                                JSONObject payload;
                                String msg = getMessageText(message);
                                if (TgUtils.isOk(object)) {
                                    payload = new JSONObject().put("link", mLink).put("text", msg).put("banAge", task.mBanTimeSec * 1000);
                                } else {
                                    payload = new JSONObject().put("error", object.toString()).put("text", msg);
                                }
                                LogUtil.logBanUser(LogUtil.Action.BanForLink, chat, message, payload);
                            } catch (Exception e) {

                            }

                        }
                    });
                }
            }

            if (task.isRemoveMessage) {
                deleteLinkMessage();
            }
        }

        void deleteLinkMessage() {
            // String msg = getMessageText(message);
            // LogUtil.logDeleteMessage(LogUtil.Action.RemoveLink, chat, message, mLink + "\n" + msg);
            //if (true) return;

            AdminUtils.deleteMessage(message, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (TgUtils.isOk(object)) {
                        String msg = getMessageText(message);
                        LogUtil.logDeleteMessage(LogUtil.Action.RemoveLink, chat, message, mLink + "\n" + msg);
                    }
                }
            });
        }
    }

    class FloodControl extends TaskAction {

        FloodControl(UpdateNewMessage message, ChatTaskControl antiSpamRule) {
            super(message, antiSpamRule);
            this.task = antiSpamRule.getTask(ChatTask.TYPE.FLOOD);
            getChat();
        }

        @Override
        void doAction() {
            if (task.isEnabled) {
                long floodTimeLimit = task.mWarningsDuringTime;
                final int tryes = DBHelper.getInstance().getAntiSpamWarnCount(task.mType, message.message.chatId, message.message.senderUserId, floodTimeLimit);
                final int limit = task.mAllowCountPerUser;
                if (tryes < limit) {
                    // LogUtil.logBanForFloodAttempt(chat, message, tryes);
                    DBHelper.getInstance().setAntiSpamWarnCount(task.mType, message.message.chatId, message.message.senderUserId, tryes + 1);

                    if (task.isWarnAvailable(tryes)) {
                        TgH.send(new TdApi.GetUser(message.message.senderUserId), new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                                TdApi.User user = (TdApi.User) object;

                                ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1); // Форматируем сообщение.
                                String warnText = getAlertTitle() + "\n";
                                entities.add(new TdApi.MessageEntityCode(0, warnText.length()));


                                int textType = task.selectWarningText(tryes);
                                String customText = textType == 1 ? task.mWarnTextFirst : task.mWarnTextLast;

                                if (customText == null) {
                                    if (!user.username.isEmpty())
                                        warnText += "@" + user.username + "\n";
                                    else {
                                        //name mention:
                                        String uname = user.firstName + " " + user.lastName;
                                        int start = warnText.length();
                                        warnText += uname + "\n";
                                        entities.add(new TdApi.MessageEntityMentionName(start, uname.length(), user.id));
                                    }

                                    //if (textType == 1)
                                    //    customText = getString(R.string.warntext_flood);
                                    //else
                                    customText = getString(R.string.warntext_flood_default_last);

                                } else {
                                    FormattedTagText fText = replaceWarningsShortTags(customText, task, user, tryes);
                                    customText = fText.resultText;
                                    if (fText.mention != null)
                                        entities.add(fText.mention);
                                    //customText = replaceWarningsShortTags(customText, task, user, tryes);
                                }

                                int offset = warnText.length();
                                int length = customText.length();
                                entities.add(new TdApi.MessageEntityCode(offset, length));
                                warnText += customText;

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
                        });
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

                                LogUtil.logDeleteMessage(LogUtil.Action.RemoveFlood, chat, message, j.toString());
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
                                int localchatid = TgUtils.getChatRealId(chat);
                                DBHelper.getInstance().addToBanList(user, message.message.chatId, chat.type.getConstructor(), localchatid,
                                        task.mBanTimeSec * 1000, task.isReturnOnBanExpired, banReason);
                                ServiceUnbanTask.registerTask(getBaseContext());
                            }
                        });

                        DBHelper.getInstance().deleteAntiSpamWarnCount(ChatTask.TYPE.STICKERS, message.message.chatId, message.message.senderUserId);
                        if (TgUtils.isGroup(chat.type.getConstructor())) {
                            DBHelper.getInstance().addUserToAutoKick(chat.id, message.message.senderUserId);
                            ServiceAutoKicker.start(getBaseContext());
                        }

                        AdminUtils.kickUser(message.message.chatId, message.message.senderUserId, new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR) {
                                    try {
                                        JSONObject payload = new JSONObject().put("banAge", task.mBanTimeSec * 1000);
                                        LogUtil.logBanUser(LogUtil.Action.BanForFlood, chat, message, payload);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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

    private static class FormattedTagText {
        String resultText;
        TdApi.MessageEntityMentionName mention;
    }

    private static FormattedTagText replaceWarningsShortTags(String customText, @Nullable ChatTask task, TdApi.User user, int tryes) {
        FormattedTagText formattedText = new FormattedTagText();

        customText = customText.replace("%name%", user.firstName + " " + user.lastName);
        customText = customText.replace("%u_id%", user.id + "");
        if (task != null)
            customText = customText.replace("%c_id%", task.chat_id + "");

        customText = customText.replace("%count%", (tryes + 1) + "");
        if (task != null) {
            customText = customText.replace("%max%", task.mAllowCountPerUser + "");
            customText = customText.replace("%left%", (task.mAllowCountPerUser - (tryes + 1)) + "");
        }

        if (!user.username.isEmpty())
            customText = customText.replace("%username%", "@" + user.username);
        else if (customText.contains("%username%")) {
            int index = customText.indexOf("%username%");
            customText = customText.replace("%username%", user.firstName);
            formattedText.mention = new TdApi.MessageEntityMentionName(index, user.firstName.length(), user.id);
        }

        formattedText.resultText = customText;

        return formattedText;
    }

    /**
     * @return first link in text
     */
    static String getLink(final String text) {
        ArrayList<String> links = new ArrayList<String>(2);
        //boolean match_images = false;
        //text ="https://pp.vk.me/c7007/v7007762/11672/4rgHVPWfCdc.jpg;
        //text = "http://i58.fastpic.ru/big/2014/0205/6f/b0258e8556e17a17ea90bc49d498f06f.jpg";
        String regex = "\\(?\\b(ftp://http://|https://|www[.]|@)?[-A-Za-zА-Яа-я0-9+&@#/%?=~_()|!:,.;]*[-A-ZА-Яа-яa-z0-9+&@#/%=~_()|]";
        //if(match_images)
        //    regex+=".(jpg|png|jpeg|bmp|gif)"; // add jpg filter

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        final boolean allowStickers = Sets.getBoolean(Const.ANTISPAM_ALLOW_STICKERS_LINKS, true);
        final DBHelper db = DBHelper.getInstance();
        while (m.find()) {
            String urlStr = m.group();
            if (urlStr.indexOf('.') == -1) continue;
            if (urlStr.indexOf("//") == -1 && urlStr.indexOf(".com") == -1 && urlStr.indexOf(".ru") == -1
                    && urlStr.indexOf(".me") == -1
                    && urlStr.indexOf(".рф") == -1 && urlStr.indexOf(".ua") == -1 && urlStr.indexOf(".org") == -1
                    && !urlStr.startsWith("@"))
                continue;
            // MyLog.log("isLink exists: " + urlStr);
            if (allowStickers && urlStr.contains("telegram.me/addstickers/")) continue;
            if (!db.isLinkInWhiteList(urlStr))
                return urlStr;
            //char[] stringArray1 = urlStr.toCharArray();

            // if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            // {

            // char[] stringArray = urlStr.toCharArray();

            // char[] newArray = new char[stringArray.length-2];
            // System.arraycopy(stringArray, 1, newArray, 0, stringArray.length-2);
            //  urlStr = new String(newArray);
            // System.out.println("Finally Url ="+newArray.toString());

            // }
            //System.out.println("...Url..."+urlStr);
            // links.add(urlStr);
        }
        //return links;
        return null;
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
