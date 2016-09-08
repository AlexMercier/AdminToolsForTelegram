package com.madpixels.tgadmintools.helper;

import com.madpixels.tgadmintools.entities.Callback;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.Client.ResultHandler;
import org.drinkless.td.libcore.telegram.TdApi;


/**
 * Created by Snake on 27.02.2016.
 */
public class TgUtils {


    public final static ResultHandler emptyResultHandler() {
        return new ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {

            }
        };
    }

    // Базовая группа, до 200человек.
    public static boolean isGroup(final int chatType) {
        return chatType== TdApi.GroupChatInfo.CONSTRUCTOR;
    }

    public static boolean isError(final TdApi.TLObject object) {
        return object.getConstructor()== TdApi.Error.CONSTRUCTOR;
    }

    public static boolean isUserPrivileged(final int role) {
        return role == TdApi.ChatParticipantRoleAdmin.CONSTRUCTOR ||
                role == TdApi.ChatParticipantRoleEditor.CONSTRUCTOR ||
                role == TdApi.ChatParticipantRoleModerator.CONSTRUCTOR;
    }

    public static boolean isSuperGroup(final int chatType) {
        return chatType== TdApi.ChannelChatInfo.CONSTRUCTOR;
    }

    public static TdApi.ChatParticipantRole getRole(TdApi.Chat chat){
        if(isGroup(chat.type.getConstructor())){
            TdApi.GroupChatInfo info = (TdApi.GroupChatInfo) chat.type;
            return  info.group.role;
        }else if(isSuperGroup(chat.type.getConstructor())){
            TdApi.ChannelChatInfo info = (TdApi.ChannelChatInfo) chat.type;
            return info.channel.role;
        }
        return null;
    }

    public static boolean isChannel(TdApi.Channel channel) {
        return !channel.isSupergroup;
    }

    public static boolean isChannel(TdApi.Chat chat){
        if(!isSuperGroup(chat.type.getConstructor()))
            return false;
        TdApi.ChannelChatInfo channelChatInfo = (TdApi.ChannelChatInfo) chat.type;
        return isChannel(channelChatInfo.channel);
    }

    public static boolean isOk(final TdApi.TLObject object) {
        return object.getConstructor()== TdApi.Ok.CONSTRUCTOR;
    }

    public static void getChatFullInfo(TdApi.Chat chat, final ResultHandler callbackHandler){
        if(TgUtils.isSuperGroup(chat.type.getConstructor())){
            TdApi.ChannelChatInfo ci = (TdApi.ChannelChatInfo) chat.type;
            TgH.send(new TdApi.GetChannelFull(ci.channel.id), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    callbackHandler.onResult(object);
                }
            });
        }else{
            TdApi.GroupChatInfo gi= (TdApi.GroupChatInfo) chat.type;
            TgH.send(new TdApi.GetGroupFull(gi.group.id), new ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    callbackHandler.onResult(object);
                }
            });
        }
    }

    public static void loadChatInviteLink(TdApi.Chat chat, final Callback callback) {
        if(TgUtils.isSuperGroup(chat.type.getConstructor())){
            TdApi.ChannelChatInfo ci = (TdApi.ChannelChatInfo) chat.type;
            TgH.send(new TdApi.GetChannelFull(ci.channel.id), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if(isError(object)){
                        callback.onResult(null);
                        return;
                    }
                    TdApi.ChannelFull cf = (TdApi.ChannelFull) object;
                    callback.onResult(cf.inviteLink);

                }
            });
        }else{
            TdApi.GroupChatInfo gi= (TdApi.GroupChatInfo) chat.type;
            TgH.send(new TdApi.GetGroupFull(gi.group.id), new ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if(isError(object)){
                        callback.onResult(null);
                        return;
                    }
                    TdApi.GroupFull g = (TdApi.GroupFull) object;
                    callback.onResult(g.inviteLink);
                }
            });
        }
    }

    public static boolean isBot(TdApi.ChatParticipant chatParticipant) {
        return chatParticipant.botInfo.getConstructor()!= TdApi.BotInfoEmpty.CONSTRUCTOR;
    }

    public static int getChatRealId(TdApi.Chat chat) {
        if(isGroup(chat.type.getConstructor())){
            TdApi.GroupChatInfo gi = (TdApi.GroupChatInfo) chat.type;
            return gi.group.id;
        }else{
            TdApi.ChannelChatInfo chi = (TdApi.ChannelChatInfo) chat.type;
            return chi.channel.id;
        }
    }


    public static boolean isUserJoined(final TdApi.Message message) {
        return (message.content.getConstructor() == TdApi.MessageChatJoinByLink.CONSTRUCTOR ||
                message.content.getConstructor()== TdApi.MessageChatAddParticipants.CONSTRUCTOR);
    }

    /**
     * @param callback get last 200 participants
     */
    public static void getGroupLastMembers(TdApi.Chat chat, final Callback callback){
        if(TgUtils.isSuperGroup(chat.type.getConstructor())){
            TdApi.ChannelChatInfo ci = (TdApi.ChannelChatInfo) chat.type;
            TdApi.ChannelParticipantsFilter f = new TdApi.ChannelParticipantsRecent();
            TgH.send(new TdApi.GetChannelParticipants(ci.channel.id, f, 0, 200), new Client.ResultHandler() { //TODO check more 200 or 100
                @Override
                public void onResult(TdApi.TLObject object) {
                    TdApi.ChatParticipants participants = (TdApi.ChatParticipants) object;
                    callback.onResult(participants.participants);
                }
            });
        }else{
            TdApi.GroupChatInfo gi = (TdApi.GroupChatInfo) chat.type;
            TdApi.TLFunction f = new TdApi.GetGroupFull(gi.group.id);
            TgH.send(f, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if(object.getConstructor()!= TdApi.GroupFull.CONSTRUCTOR)
                        return;
                    TdApi.GroupFull group = (TdApi.GroupFull) object;
                    callback.onResult(group.participants);
                }
            });
        }
    }



}
