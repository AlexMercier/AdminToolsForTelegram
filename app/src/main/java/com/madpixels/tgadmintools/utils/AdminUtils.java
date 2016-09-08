package com.madpixels.tgadmintools.utils;

import android.support.annotation.Nullable;

import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;

/**
 * Created by Snake on 14.03.2016.
 */
public class AdminUtils {

    /**
     * @param pCallback boolean in callback
     */
    public static void checkUserIsAdminInChat(TdApi.Chat chat, int userId, Callback pCallback){
        if(TgUtils.isSuperGroup(chat.type.getConstructor())){
            TdApi.ChannelChatInfo ci = (TdApi.ChannelChatInfo) chat.type;
            AdminUtils.checkIsAdminInSuperGroup(ci.channel.id, userId, pCallback);
        }else{
            TdApi.GroupChatInfo gi = (TdApi.GroupChatInfo) chat.type;
            AdminUtils.checkIsAdminInGroup(gi.group.id, userId, pCallback);
        }
    }

    public static void getChatAdmins(TdApi.Chat chat, Client.ResultHandler onResult){
        if(TgUtils.isSuperGroup(chat.type.getConstructor())){
            TdApi.ChannelChatInfo ci = (TdApi.ChannelChatInfo) chat.type;
            AdminUtils.getAdminsInSuperGroup(ci.channel.id, onResult);
        }else{
            TdApi.GroupChatInfo gi = (TdApi.GroupChatInfo) chat.type;
            AdminUtils.getAdminsInGroup(gi.group.id, onResult);
        }
    }

    public static void getAdminsInSuperGroup(int channelId, final Client.ResultHandler resultHandler) {
        TdApi.ChannelParticipantsFilter f = new TdApi.ChannelParticipantsAdmins();
        TgH.send(new TdApi.GetChannelParticipants(channelId, f, 0, 25), new Client.ResultHandler() {
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
                if(object.getConstructor()!= TdApi.GroupFull.CONSTRUCTOR)
                    return;
                TdApi.GroupFull group = (TdApi.GroupFull) object;

                // TdApi.ChatParticipants s = new TdApi.ChatParticipants();

                ArrayList<TdApi.ChatParticipant> participants = new ArrayList<>();
                for (TdApi.ChatParticipant cp : group.participants) {
                    if (TgUtils.isUserPrivileged(cp.role.getConstructor()) ) {
                        participants.add(cp);
                        //pCallback.onResult(true);
                        //return;
                    }
                }
                TdApi.ChatParticipant[] participantsArray = participants.toArray(new TdApi.ChatParticipant[0]);
                TdApi.ChatParticipants ss = new TdApi.ChatParticipants(participantsArray.length, participantsArray);
                onResult.onResult(ss);
                //pCallback.onResult(false);
            }
        });
    }

    public static void checkIsAdminInSuperGroup(int channelId, final int userid, final Callback pCallback) {
        TdApi.ChannelParticipantsFilter f = new TdApi.ChannelParticipantsAdmins();
        TgH.send(new TdApi.GetChannelParticipants(channelId, f, 0, 25), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TdApi.ChatParticipants users = (TdApi.ChatParticipants) object;
                for (TdApi.ChatParticipant cp : users.participants) {
                    if (cp.user.id == userid) {
                        pCallback.onResult(true);
                        return;
                    }
                }
                pCallback.onResult(false);
            }
        });
    }

    public static void checkIsAdminInGroup(int groupId, final int userid, final Callback pCallback) {
        TdApi.TLFunction f = new TdApi.GetGroupFull(groupId);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if(object.getConstructor()!= TdApi.GroupFull.CONSTRUCTOR)
                    return;
                TdApi.GroupFull group = (TdApi.GroupFull) object;

                for (TdApi.ChatParticipant cp : group.participants) {
                    if ( cp.user.id == userid && TgUtils.isUserPrivileged(cp.role.getConstructor()) ) {
                        pCallback.onResult(true);
                        return;
                    }
                }
                pCallback.onResult(false);
            }
        });
    }


    public static void deleteMessage(TdApi.UpdateNewMessage messageIds, @Nullable Client.ResultHandler resultHandler) {
        TdApi.TLFunction f = new TdApi.DeleteMessages(messageIds.message.chatId, new int[]{messageIds.message.id});
        TgH.send(f, resultHandler);
    }

    public static void kickUser(final long chat_id, final int user_id, @Nullable Client.ResultHandler callback){
        final TdApi.TLFunction f = new TdApi.ChangeChatParticipantRole(chat_id, user_id, new TdApi.ChatParticipantRoleKicked());
        if(callback==null)
            callback = TgUtils.emptyResultHandler();
        TgH.TG().send(f, callback);
    }

    public static void checkUserIsInContactList(int userId, final Callback onCheckCallback){
        TdApi.TLFunction f = new TdApi.GetUser(userId);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if(object.getConstructor()==TdApi.User.CONSTRUCTOR){
                    TdApi.User u = (TdApi.User) object;
                    onCheckCallback.onResult(!u.phoneNumber.isEmpty());
                }
            }
        });
    }

    public static void checkIsBot(int userId, final Callback onCheckIsBot) {
        TdApi.TLFunction f = new TdApi.GetUser(userId);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if(object.getConstructor()==TdApi.User.CONSTRUCTOR){
                    TdApi.User u = (TdApi.User) object;
                    onCheckIsBot.onResult(u.type.getConstructor() == TdApi.UserTypeBot.CONSTRUCTOR);
                }
            }
        });
    }
}
