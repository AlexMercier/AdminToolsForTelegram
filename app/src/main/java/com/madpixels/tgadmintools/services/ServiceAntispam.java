package com.madpixels.tgadmintools.services;

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
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.AntiSpamRule;
import com.madpixels.tgadmintools.entities.Callback;
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
public class ServiceAntispam extends Service {

    private static boolean stoppedManully = false;
    private static boolean IS_RUNNING = false;

    public static void start(Context mContext) {
        if (!IS_RUNNING && DBHelper.getInstance().isChatRulesExists()) {


            mContext.startService(new Intent(mContext, ServiceAntispam.class));
            if (BuildConfig.DEBUG)
                LogUtil.showLogNotification("ServiceAntispam started with rules exists");
        }
    }

    //TODO  списке юзеров иконку для бота


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.log("ServiceAntispam started");
        if (ServiceAntispam.IS_RUNNING) {
            MyLog.log("ServiceAntispam is already running");
            TgH.init(this);
            TgH.send(new TdApi.GetAuthState());
            return START_STICKY;
        }

        stoppedManully = false;
        IS_RUNNING = true;

        TgH.init(this, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TgH.setUpdatesHandler(onUpdate);
            }
        });

        //MyNotification mm = new MyNotification(17, getBaseContext());
        // startForeground(17, mm.mNotificationBuilder.build());
        return START_STICKY;
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
                    // return;
                }

                if (!message.message.canBeDeleted)
                    return;

                AntiSpamRule antiSpamRule = DBHelper.getInstance().getAntispamRule(chatId);
                if (antiSpamRule == null)
                    return;

                if (antiSpamRule.isRemoveLeaveMessage && message.message.content.getConstructor() == TdApi.MessageChatDeleteParticipant.CONSTRUCTOR) {
                    AdminUtils.deleteMessage(message, null);
                    return;
                    // покинул группу
                    // TdApi.MessageChatDeleteParticipant leaveMsg = (TdApi.MessageChatDeleteParticipant) message.message.content;
                    // if(leaveMsg.user.id==message.message.fromId){
                    //     //self leaved
                    // }
                }
                if (antiSpamRule.isRemoveJoinMessage && TgUtils.isUserJoined(message.message)) {
                    AdminUtils.deleteMessage(message, null);
                    return;
                }

                if (BuildConfig.DEBUG && message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                    TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;

                    if (msgContent.text.equals("tg")) {
                        sendPing(message.message);
                    }
                }

                if (TgH.selfProfileId == message.message.fromId)
                    return;

                checkMessageForAntiSpam(message, antiSpamRule);
            }
        }
    };

    private void sendWelcomeText(UpdateNewMessage message, String welcomeText) {
        TdApi.InputMessageText msg = new TdApi.InputMessageText(welcomeText, false, null);
        TdApi.TLFunction f = new TdApi.SendMessage(message.message.chatId, message.message.id, false, true, true,
                new TdApi.ReplyMarkupNone(), msg);
        TgH.send(f);
    }

    private void checkMessageForAntiSpam(TdApi.UpdateNewMessage message, AntiSpamRule antiSpamRule) {
        if (antiSpamRule.isBanForLinks || antiSpamRule.isRemoveLinks) {
            final boolean allowStickers = Sets.getBoolean(Const.ANTISPAM_ALLOW_STICKERS_LINKS, true);
            final DBHelper db = DBHelper.getInstance();
            String link = null;
            if (message.message.content.getConstructor() == TdApi.MessageWebPage.CONSTRUCTOR) {
                TdApi.MessageWebPage webPage = (TdApi.MessageWebPage) message.message.content;

                for (TdApi.MessageEntity e : webPage.entities) {
                    if (e.getConstructor() != TdApi.MessageEntityUrl.CONSTRUCTOR) continue;
                    TdApi.MessageEntityUrl entityUrl = (TdApi.MessageEntityUrl) e;
                    String url = webPage.text.substring(entityUrl.offset, entityUrl.offset + entityUrl.length).toLowerCase();
                    if (db.isLinkInWhiteList(url))
                        continue;
                    if (allowStickers && url.contains("telegram.me/addstickers/"))
                        continue;
                    else {
                        link = url;
                        break;
                    }
                }

            } else if (message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;

                final boolean isAllowMentions = Sets.getBoolean(Const.ANTISPAM_ALLOW_MENTION_LINKS, true);
                loop:
                for (TdApi.MessageEntity me : msgContent.entities) {
                    switch (me.getConstructor()) {
                        case TdApi.MessageEntityUrl.CONSTRUCTOR:
                            TdApi.MessageEntityUrl mu = (TdApi.MessageEntityUrl) me;
                            String url = msgContent.text.substring(mu.offset, mu.offset + mu.length).toLowerCase();
                            if (db.isLinkInWhiteList(url))
                                continue;
                            if (allowStickers && url.contains("telegram.me/addstickers/"))
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
        if ((antiSpamRule.isBanForStickers || antiSpamRule.isRemoveStickers) && message.message.content.getConstructor() == TdApi.MessageSticker.CONSTRUCTOR) {
            new BanForSticker(message, antiSpamRule);
            return;
        }

        if ((antiSpamRule.isBanForBlackWords || antiSpamRule.isDelMsgBlackWords) && message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
            TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;
            String[] words = DBHelper.getInstance().getWordsBlackList(antiSpamRule.chat_id);
            if (words != null) {
                final String text = msgContent.text.toLowerCase();
                for (String word : words) {
                    if (text.contains(word.toLowerCase())) {
                        new BanForBlackWord(message, antiSpamRule, word);
                        return;
                    }
                }
            }
        }


        // if(message.message.content.getConstructor() == TdApi.MessagePhoto.CONSTRUCTOR && antiSpamRule.isImagesFloodEnabled){
        //     new BanForImage(message, antiSpamRule);
        // }
    }

    private void sendPing(TdApi.Message message) {
        TdApi.SendMessage msgSend = new TdApi.SendMessage();
        msgSend.chatId = message.chatId;
        msgSend.replyToMessageId = message.id;
        TdApi.InputMessageText msgText = new TdApi.InputMessageText();
        msgText.text = "Pong";
        msgSend.message = msgText;
        TgH.send(msgSend, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                // MyLog.log(object.toString());
            }
        });
    }

    private class BanForImage extends Ban {

        BanForImage(UpdateNewMessage message, AntiSpamRule antiSpamRule) {
            super(message, antiSpamRule);
            getChat();
        }

        @Override
        void banAction() {
            int tryes = DBHelper.getInstance().getAntiSpamWarnCount(LogUtil.Action.ImagesFloodWarn, message.message.chatId, message.message.fromId, 60 * 60 * 1000);
            int limit = antiSpamRule.mImagesFloodLimit;
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
                                antiSpamRule.imagesBanAge, antiSpamRule.isImagesReturnOnUnban, banReason);
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
    }

    private abstract class Ban {
        AntiSpamRule antiSpamRule;
        UpdateNewMessage message;
        TdApi.Chat chat;

        Ban(final TdApi.UpdateNewMessage message, AntiSpamRule antiSpamRule) {
            this.antiSpamRule = antiSpamRule;
            this.message = message;
        }

        void getChat() {
            loadChatInfo(message.message.chatId, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    chat = (TdApi.Chat) object;
                    checkUserInPhoneBook();
                }
            });
        }


        void checkUserInPhoneBook() { // друзьям разрешаем спамить.
            if (Sets.getBoolean(Const.ANTISPAM_IGNORE_SHARED_CONTACTS, true))
                AdminUtils.checkUserIsInContactList(message.message.fromId, onCheckIsContact);
            else
                AdminUtils.checkUserIsAdminInChat(chat, message.message.fromId, onCheckIsAdmin);
        }


        final Callback onCheckIsContact = new Callback() {
            @Override
            public void onResult(Object boolData) {
                boolean isContact = (boolean) boolData;
                if (isContact)
                    return;
                AdminUtils.checkUserIsAdminInChat(chat, message.message.fromId, onCheckIsAdmin);
            }
        };

        final Callback onCheckIsAdmin = new Callback() {
            @Override
            public void onResult(Object data) {
                boolean isAdmin = (boolean) data;
                if (isAdmin)
                    return;
                AdminUtils.checkIsBot(message.message.fromId, onCheckIsBot);
            }
        };

        Callback onCheckIsBot = new Callback() {
            @Override
            public void onResult(Object data) {
                boolean isBot = (boolean) data;
                if (isBot) // TODO check if bot allowed
                    return;
                onBan();
            }
        };

        public void onBan() {
            banAction();
        }

        abstract void banAction();
    }

    private class BanForBlackWord extends Ban {
        private String word;

        BanForBlackWord(UpdateNewMessage message, AntiSpamRule antiSpamRule, final String word) {
            super(message, antiSpamRule);
            this.word = word;
            getChat();
        }



        @Override
        void banAction() {
            final TdApi.MessageText msgContent = (TdApi.MessageText) message.message.content;

            if (antiSpamRule.isBanForBlackWords) {
                final int userTryes =  DBHelper.getInstance().getAntiSpamWarnCount(LogUtil.Action.BanWordsFloodWarn, message.message.chatId, message.message.fromId, 60 * 60 * 1000);
                int warnCount =  antiSpamRule.banWordsFloodLimit;

                if(userTryes<warnCount){
                    LogUtil.logBanForBanWordAttempt(chat, message, userTryes);
                    DBHelper.getInstance().setAntiSpamWarnCount(LogUtil.Action.BanWordsFloodWarn, message.message.chatId, message.message.fromId, userTryes + 1);
                    if (antiSpamRule.isWarnBeforeBanForWord && (userTryes == 0 || userTryes == warnCount - 1)) {
                        TgH.send(new TdApi.GetUser(message.message.fromId), new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                                TdApi.User user = (TdApi.User) object;

                                ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1); // Форматируем сообщение.
                                String warnText = "Antispam\n";
                                entities.add(new TdApi.MessageEntityCode(0, 8));

                                if (!user.username.isEmpty())
                                    warnText += "@" + user.username + "\n";
                                String customText = antiSpamRule.getBanWordsWarnText();

                                if (customText != null) {
                                    int offset = warnText.length();
                                    int length = customText.length();
                                    warnText += customText;
                                    entities.add(new TdApi.MessageEntityCode(offset, length));
                                } else {
                                    String text;
                                    if(userTryes==1)
                                        text = getString(R.string.warntext_banword_first);
                                    else
                                        text = getString(R.string.warntext_banword_last);

                                    int offset = warnText.length();
                                    int length = text.length();
                                    warnText += text;
                                    entities.add(new TdApi.MessageEntityCode(offset, length));
                                }

                                TdApi.SendMessage msgSend = new TdApi.SendMessage();
                                msgSend.chatId = message.message.chatId;
                                msgSend.replyToMessageId = message.message.id;
                                TdApi.InputMessageText msgText = new TdApi.InputMessageText();
                                msgText.text = warnText;

                                msgText.entities = new TdApi.MessageEntity[entities.size()];
                                for (int n = 0; n < entities.size(); n++)
                                    msgText.entities[n] = entities.get(n);

                                msgSend.message = msgText;
                                TgH.send(msgSend, new Client.ResultHandler() {
                                    @Override
                                    public void onResult(TdApi.TLObject object) {
                                        MyLog.log(object.toString());
                                    }
                                });
                            }
                        });
                    }
                }else{
                    TgH.send(new TdApi.GetUser(message.message.fromId), new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                            final TdApi.User user = (TdApi.User) object;
                            String banReason = getString(R.string.logAction_banForBlackWord); // TODO custom text
                            int localChatId = TgUtils.getChatRealId(chat);
                            boolean isReturn = antiSpamRule.isBlackWordReturnOnBanExp;
                            long banMsec = antiSpamRule.mBlackWordBanAgeMsec;
                            DBHelper.getInstance().addToBanList(user, message.message.chatId, chat.type.getConstructor(), localChatId,
                                    banMsec, isReturn, banReason);
                            if (banMsec > 0)
                                ServiceUnbanTask.registerTask(getBaseContext());

                            if (TgUtils.isGroup(chat.type.getConstructor())) {
                                DBHelper.getInstance().addUserToAutoKick(chat.id, message.message.fromId);
                                ServiceAutoKicker.start(getBaseContext());
                            }

                            AdminUtils.kickUser(message.message.chatId, message.message.fromId, new Client.ResultHandler() {
                                @Override
                                public void onResult(TdApi.TLObject object) {
                                    if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR) {
                                        alertBan(user);

                                        try {
                                            JSONObject payload = new JSONObject().put("word", word).put("text", msgContent.text).put("banAge", antiSpamRule.mBlackWordBanAgeMsec);

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

            if (antiSpamRule.isDelMsgBlackWords) {
                AdminUtils.deleteMessage(message, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (TgUtils.isOk(object))
                            LogUtil.logDeleteMessage(LogUtil.Action.DeleteMsgBlackWord, chat, message, word);
                    }
                });
            }
        }

        private void alertBan(TdApi.User user) {
                if(antiSpamRule.isAlertAboutWordBan){
                    String username = user.username.isEmpty()?user.firstName+" "+user.lastName : user.username;
                    String warnText = getString(R.string.mesage_user_banned_banword, username);
                    warnText = "Antispam\n"+warnText;

                    ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1); // Форматируем сообщение.
                    entities.add(new TdApi.MessageEntityCode(0, 8));

                    TdApi.SendMessage msgSend = new TdApi.SendMessage();
                    msgSend.chatId = message.message.chatId;
                    msgSend.replyToMessageId = message.message.id;

                    TdApi.InputMessageText msgText = new TdApi.InputMessageText();
                    msgText.text = warnText;
                    msgSend.message = msgText;
                    msgText.entities = entities.toArray(new TdApi.MessageEntity[0]);

                    TgH.send(msgSend, new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            MyLog.log(object.toString());
                        }
                    });
                }
        }
    }

    private class BanForSticker extends Ban {

        BanForSticker(final TdApi.UpdateNewMessage message, AntiSpamRule antiSpamRule) {
            super(message, antiSpamRule);
            getChat();
        }


        @Override
        void banAction() {
            if (antiSpamRule.isBanForStickers) {
                final int tryes = DBHelper.getInstance().getAntiSpamWarnCount(LogUtil.Action.StickersFloodWarn, message.message.chatId, message.message.fromId, 60 * 60 * 1000);
                final int limit = antiSpamRule.mAllowStickersCount;
                if (tryes < limit) {
                    LogUtil.logBanForStickerAttempt(chat, message, tryes);
                    DBHelper.getInstance().setAntiSpamWarnCount(LogUtil.Action.StickersFloodWarn, message.message.chatId, message.message.fromId, tryes + 1);

                    if (antiSpamRule.isWarnBeforeStickersBan && (tryes == 1 || tryes == limit - 1)) {
                        TgH.send(new TdApi.GetUser(message.message.fromId), new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                                TdApi.User user = (TdApi.User) object;

                                ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1); // Форматируем сообщение.
                                String warnText = "Antispam\n";
                                entities.add(new TdApi.MessageEntityCode(0, 8));

                                if (!user.username.isEmpty())
                                    warnText += "@" + user.username + "\n";
                                String customText = antiSpamRule.getStickersWarnText();

                                if (customText != null) {
                                    int offset = warnText.length();
                                    int length = customText.length();
                                    warnText += customText;
                                    entities.add(new TdApi.MessageEntityCode(offset, length));
                                } else {
                                    String text;// = getString(R.string.warntext_stickers_default);
                                    if(tryes == limit - 1)
                                        text = getString(R.string.warntext_stickers_default_last);
                                    else
                                        text = getString(R.string.warntext_stickers_default_first);

                                    int offset = warnText.length();
                                    int length = text.length();
                                    warnText += text;
                                    entities.add(new TdApi.MessageEntityCode(offset, length));
                                }

                                TdApi.SendMessage msgSend = new TdApi.SendMessage();
                                msgSend.chatId = message.message.chatId;
                                msgSend.replyToMessageId = message.message.id;
                                TdApi.InputMessageText msgText = new TdApi.InputMessageText();
                                msgText.text = warnText;

                                msgText.entities = new TdApi.MessageEntity[entities.size()];
                                for (int n = 0; n < entities.size(); n++)
                                    msgText.entities[n] = entities.get(n);

                                msgSend.message = msgText;
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

                    TgH.send(new TdApi.GetUser(message.message.fromId), new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                            TdApi.User user = (TdApi.User) object;
                            String banReason = getString(R.string.logAction_banForSticker);
                            int localchatid = TgUtils.getChatRealId(chat);
                            DBHelper.getInstance().addToBanList(user, message.message.chatId, chat.type.getConstructor(), localchatid,
                                    antiSpamRule.mStickerBanAgeMsec, antiSpamRule.isStickerReturnOnUnban, banReason);
                            ServiceUnbanTask.registerTask(getBaseContext());
                        }
                    });

                    DBHelper.getInstance().deleteAntiSpamWarnCount(LogUtil.Action.StickersFloodWarn, message.message.chatId, message.message.fromId);
                    if (TgUtils.isGroup(chat.type.getConstructor())) {
                        DBHelper.getInstance().addUserToAutoKick(chat.id, message.message.fromId);
                        ServiceAutoKicker.start(getBaseContext());
                    }

                    AdminUtils.kickUser(message.message.chatId, message.message.fromId, new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR) {
                                try {
                                    JSONObject payload = new JSONObject().put("banAge", antiSpamRule.mStickerBanAgeMsec);
                                    LogUtil.logBanUser(LogUtil.Action.BanForSticker, chat, message, payload);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }

            if (antiSpamRule.isRemoveStickers) {
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
        if (message.message.content.getConstructor() == TdApi.MessageWebPage.CONSTRUCTOR) {
            TdApi.MessageWebPage webPage = (TdApi.MessageWebPage) message.message.content;
            return webPage.text;
        }
        if (message.message.content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
            TdApi.MessageText msgText = (TdApi.MessageText) message.message.content;
            return msgText.text;
        }

        return message.message.content.toString();
    }

    private class BanForLink extends Ban {
        String mLink;

        BanForLink(String link, TdApi.UpdateNewMessage message, AntiSpamRule antiSpamRule) {
            super(message, antiSpamRule);
            this.mLink = link;
            getChat();
        }

        //переопределяем чтоб проверить ссылку перед баном
        @Override
        public void onBan() {
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
                        if ( !channelFull.channel.username.isEmpty() && ("@" + channelFull.channel.username.toLowerCase()).equals(mLink)) // логин группы //TODO проверить
                            return;

                    } else if (object.getConstructor() == TdApi.GroupFull.CONSTRUCTOR) { // группа
                        TdApi.GroupFull groupFull = (TdApi.GroupFull) object;
                        if (groupFull.inviteLink.toLowerCase().contains(mLink)) // это ссылка на текущий чат, скипаем.
                            return;
                    } else {

                    }
                    checkDirectIsChatMember();// проверим может это ссылка на юзера чата
                }
            });
        }


        private void checkDirectIsChatMember() {
            // Проверим не ссылка ли это на участника чата
            TgUtils.getGroupLastMembers(chat, new Callback() {
                @Override
                public void onResult(Object data) {
                    TdApi.ChatParticipant[] participants = (TdApi.ChatParticipant[]) data;
                    for (TdApi.ChatParticipant participant : participants) {
                        if (participant.user.username.isEmpty()) continue;
                        String mention = "@" + participant.user.username.toLowerCase();
                        String link = "telegram.me/" + participant.user.username.toLowerCase();
                        if (mLink.equals(mention) || mLink.contains(link))
                            return;
                    }

                    checkDirectIsAdmins();
                }
            });
        }

        private void checkDirectIsAdmins() {
            AdminUtils.getChatAdmins(chat, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    TdApi.ChatParticipants admins = (TdApi.ChatParticipants) object;
                    for (TdApi.ChatParticipant admin : admins.participants) {
                        if (admin.user.username.isEmpty()) continue;
                        String mention = "@" + admin.user.username.toLowerCase();
                        String link = "telegram.me/" + admin.user.username;
                        if (mLink.equals(mention) || mLink.contains(link))
                            return;
                    }
                    banAction();
                }
            });
        }

        @Override
        void banAction() {
            if (antiSpamRule.isBanForLinks) {
                final int tryes = DBHelper.getInstance().getAntiSpamWarnCount(LogUtil.Action.LinksFloodAttempt, message.message.chatId, message.message.fromId, 60 * 60 * 1000);
                final int limit = antiSpamRule.mAllowLinksCount;
                if (tryes < limit) {
                    DBHelper.getInstance().setAntiSpamWarnCount(LogUtil.Action.LinksFloodAttempt, message.message.chatId, message.message.fromId, tryes + 1);
                    LogUtil.logBanForLinksAttempt(chat, message, tryes, mLink);

                    if (antiSpamRule.isWarnBeforeLinksBan && (tryes == 0 || tryes == limit - 1)) { // еслиртим при первой попытке спама и при последней.

                        TgH.send(new TdApi.GetUser(message.message.fromId), new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;

                                TdApi.User user = (TdApi.User) object;
                                String warnText = "Antispam\n";
                                ArrayList<TdApi.MessageEntity> entities = new ArrayList<>(1); // Форматируем сообщение.
                                entities.add(new TdApi.MessageEntityCode(0, 8));

                                if (!user.username.isEmpty())
                                    warnText += "@" + user.username + "\n";

                                String customText = antiSpamRule.getLinksWarnText();
                                if(customText==null){
                                    if(tryes == limit - 1)
                                        customText = getString(R.string.warntext_links_default_last);
                                    else
                                        customText = getString(R.string.warntext_links_default_firts);
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
                                msgSend.message = msgText;
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
                    TgH.send(new TdApi.GetUser(message.message.fromId), new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            if (object.getConstructor() != TdApi.User.CONSTRUCTOR) return;
                            TdApi.User user = (TdApi.User) object;
                            String banReason = getString(R.string.logAction_banForLink);
                            int localchatid = TgUtils.getChatRealId(chat);
                            DBHelper.getInstance().addToBanList(user, message.message.chatId, chat.type.getConstructor(), localchatid,
                                    antiSpamRule.mLinkBanAgeMsec, antiSpamRule.isLinkReturnOnUnban, banReason);
                            ServiceUnbanTask.registerTask(getBaseContext());//TODO не забуспать задачу если banAge==0?
                            if (TgUtils.isGroup(chat.type.getConstructor())) {
                                DBHelper.getInstance().addUserToAutoKick(chat.id, message.message.fromId);
                                ServiceAutoKicker.start(getBaseContext());
                            }
                        }
                    });

                    //Reset warn for this user:
                    DBHelper.getInstance().deleteAntiSpamWarnCount(LogUtil.Action.LinksFloodAttempt, message.message.chatId, message.message.fromId);

                    AdminUtils.kickUser(message.message.chatId, message.message.fromId, new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            try {
                                JSONObject payload;
                                String msg = getMessageText(message);
                                if (TgUtils.isOk(object)) {
                                    payload = new JSONObject().put("link", mLink).put("text", msg).put("banAge", antiSpamRule.mLinkBanAgeMsec);
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

            if (antiSpamRule.isRemoveLinks) {
                deleteLinkMessage();
            }
        }

        void deleteLinkMessage() {
            String msg = getMessageText(message);
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
        stoppedManully = true;
        c.stopService(new Intent(c, ServiceAntispam.class));

    }

    @Override
    public void onDestroy() {
        MyLog.log("ServiceAntispam destroyed");
        TgH.removeUpdatesHandler(onUpdate);
        IS_RUNNING = false;
        if (!stoppedManully) {
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
