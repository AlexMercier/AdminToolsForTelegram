package com.madpixels.tgadmintools.utils;

import android.support.annotation.Nullable;

import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Created by Snake on 14.03.2016.
 */
public class AdminUtils {

    /**
     * @param pCallback boolean in callback
     */
    public static void checkUserIsAdminInChat(long chatId, int userId, final Callback pCallback) {
        TgH.send(new TdApi.GetChatMember(chatId, userId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.ChatMember.CONSTRUCTOR) {
                    TdApi.ChatMember member = (TdApi.ChatMember) object;
                    boolean isAdmin = TgUtils.isUserPrivileged(member.status.getConstructor());
                    pCallback.onResult(isAdmin);
                } else
                    pCallback.onResult(false);
            }
        });
    }

    /**
     * Return boolean on callback data
     */
    public static void checkIsCreator(long chatId, int userId, final Callback pCallback) {
        TgH.send(new TdApi.GetChatMember(chatId, userId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.ChatMember.CONSTRUCTOR) {
                    TdApi.ChatMember member = (TdApi.ChatMember) object;
                    boolean isCreator = member.status.getConstructor() == TdApi.ChatMemberStatusCreator.CONSTRUCTOR;
                    pCallback.onResult(isCreator);
                }else
                    pCallback.onResult(false);
            }
        });
    }

    /*
    public static void getChatAdmins(TdApi.Chat chat, Client.ResultHandler onResult) {
        if (TgUtils.isSuperGroup(chat.type.getConstructor())) {
            TdApi.ChannelChatInfo ci = (TdApi.ChannelChatInfo) chat.type;
            AdminUtils.getAdminsInSuperGroup(ci.channel.id, onResult);
        } else {
            TdApi.GroupChatInfo gi = (TdApi.GroupChatInfo) chat.type;
            AdminUtils.getAdminsInGroup(gi.group.id, onResult);
        }
    }


    public static void getAdminsInSuperGroup(int channelId, final Client.ResultHandler resultHandler) {
        TdApi.ChannelMembersFilter f = new TdApi.ChannelMembersAdministrators();
        TgH.send(new TdApi.GetChannelMembers(channelId, f, 0, 25), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                resultHandler.onResult(object);
            }
        });
    }

    public static void getAdminsInGroup(int groupId, final Client.ResultHandler onResult) {
        TdApi.TLFunction f = new TdApi.GetGroupFull(groupId);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() != TdApi.GroupFull.CONSTRUCTOR)
                    return;
                TdApi.GroupFull group = (TdApi.GroupFull) object;

                // TdApi.ChatParticipants s = new TdApi.ChatParticipants();

                ArrayList<TdApi.ChatMember> participants = new ArrayList<>();
                for (TdApi.ChatMember cp : group.members) {
                    if (TgUtils.isUserPrivileged(cp.status.getConstructor())) {
                        participants.add(cp);
                        //pCallback.onResult(true);
                        //return;
                    }
                }
                TdApi.ChatMember[] participantsArray = participants.toArray(new TdApi.ChatMember[0]);
                TdApi.ChatMembers ss = new TdApi.ChatMembers(participantsArray.length, participantsArray);
                onResult.onResult(ss);
                //pCallback.onResult(false);
            }
        });
    }
    */

    public static void deleteMessage(TdApi.UpdateNewMessage messageIds, @Nullable Client.ResultHandler resultHandler) {
        TdApi.TLFunction f = new TdApi.DeleteMessages(messageIds.message.chatId, new int[]{messageIds.message.id});
        TgH.send(f, resultHandler);
    }

    public static void kickUser(final long chat_id, final int user_id, @Nullable Client.ResultHandler resultHandler) {
        final TdApi.TLFunction f = new TdApi.ChangeChatMemberStatus(chat_id, user_id, new TdApi.ChatMemberStatusKicked());
        TgH.send(f, resultHandler);
    }

    public static void checkUserIsInContactList(int userId, final Callback onCheckCallback) {
        TdApi.TLFunction f = new TdApi.GetUser(userId);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.User.CONSTRUCTOR) {
                    TdApi.User u = (TdApi.User) object;
                    onCheckCallback.onResult(!u.phoneNumber.isEmpty());
                }else
                    onCheckCallback.onResult(false);
            }
        });
    }

    public static void checkIsBot(int userId, final Callback onCheckIsBot) {
        TdApi.TLFunction f = new TdApi.GetUser(userId);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.User.CONSTRUCTOR) {
                    TdApi.User u = (TdApi.User) object;
                    onCheckIsBot.onResult(u.type.getConstructor() == TdApi.UserTypeBot.CONSTRUCTOR);
                }else
                    onCheckIsBot.onResult(false);
            }
        });
    }


    public static void checkUserIsChatMember(final long chatId, String mention, final Callback callback) {
        TgH.send(new TdApi.SearchPublicChat(mention), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR) {
                    TdApi.Chat chat = (TdApi.Chat) object;
                    if (chat.type.getConstructor() == TdApi.PrivateChatInfo.CONSTRUCTOR) {
                        TdApi.PrivateChatInfo pi = (TdApi.PrivateChatInfo) chat.type;
                        checkUserIsChatMember(chatId, pi.user.id, callback);
                        return;
                    }
                }
                callback.onResult(false);
            }
        });
    }


    public static void checkUserIsChatMember(long chatId, int userId, final Callback callback) {
        TgH.send(new TdApi.GetChatMember(chatId, userId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if(TgUtils.isError(object)){
                    callback.onResult(false);
                }else {
                    TdApi.ChatMember member = (TdApi.ChatMember) object;
                    boolean isMember = member.status.getConstructor() != TdApi.ChatMemberStatusLeft.CONSTRUCTOR;
                    callback.onResult(isMember);
                }
            }
        });
    }
}
