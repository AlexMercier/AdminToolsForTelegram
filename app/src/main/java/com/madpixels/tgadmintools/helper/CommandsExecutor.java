package com.madpixels.tgadmintools.helper;

import android.content.Context;
import android.text.TextUtils;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.entities.ChatCommand;
import com.madpixels.tgadmintools.entities.ChatTask;
import com.madpixels.tgadmintools.entities.FormattedTagText;
import com.madpixels.tgadmintools.services.ServiceChatTask;
import com.madpixels.tgadmintools.utils.AdminUtils;
import com.madpixels.tgadmintools.utils.CommonUtils;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

/**
 * Created by Snake on 08.01.2017.
 */

public class CommandsExecutor {

    private Context mContext;
    private final ServiceChatTask.ChatCommandExecute chatCommand;
    private final TdApi.Message message;
    private final ChatTask task;
    final ChatCommand pCommand;

    public CommandsExecutor(Context pContext, ServiceChatTask.ChatCommandExecute pChatCommand) {
        this.mContext = pContext;
        this.chatCommand = pChatCommand;

        message = chatCommand.message.message;
        task = chatCommand.task;
        this.pCommand = chatCommand.pCommand;
    }

    public void executeCommand() {

        if (pCommand.type == ChatCommand.CMD_TEXT) {
            commandSendText();
        } else if (pCommand.type == ChatCommand.CMD_KICK_SENDER) {
           commandKickSelf();
        } else if (pCommand.type == ChatCommand.CMD_CHANGE_TITLE) {
            commandChangeTitle();
        }
    }

    void commandSendText(){
        TdApi.SendMessage msgSend = new TdApi.SendMessage();
        msgSend.chatId = message.chatId;
        msgSend.replyToMessageId = message.id;
        TdApi.InputMessageText msgText = new TdApi.InputMessageText();

        TdApi.User user = TgUtils.getUser(message.senderUserId);
        FormattedTagText formattedTagText = CommonUtils.replaceCustomShortTags(pCommand.answer, task, user, 0);

        String botToken = CommonUtils.useBotForAlert(task.chat_id);

        if (botToken != null) {
            CommonUtils.sendMessageViaBot(botToken, message.chatId, formattedTagText.resultText, false, true);
            return;
        }

        msgText.text = formattedTagText.resultText;
        if (formattedTagText.mention != null) {
            msgText.entities = new TdApi.MessageEntity[1];
            msgText.entities[0] = formattedTagText.mention;
        }

        msgSend.inputMessageContent = msgText;
        msgText.parseMode = new TdApi.TextParseModeMarkdown();
        TgH.send(msgSend, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (TgUtils.isError(object)) {
                    TdApi.Error error = (TdApi.Error) object;
                    chatCommand.getLog().execCommandError(chatCommand.mChat, pCommand.cmd, error.message, pCommand.answer);
                }
            }
        });
    }

    void commandKickSelf(){
        AdminUtils.kickUser(message.chatId, message.senderUserId, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                MyLog.log(object.toString());
            }
        });
    }

    void commandChangeTitle(){
        if (chatCommand.params.length == 1) {// user not send title
            String commandExample = chatCommand.params[0] + " New Title";

            String botToken = CommonUtils.useBotForAlert(task.chat_id);
            if (botToken != null) {
                String msgText = mContext.getString(R.string.text_hint_cmd_change_title) +
                        "\n<pre>" + TextUtils.htmlEncode(commandExample) + "</pre>";
                CommonUtils.sendMessageViaBot(botToken, message.chatId, msgText, true, false);
                return;
            }
            TdApi.InputMessageText messageContent = new TdApi.InputMessageText();
            String msgText = mContext.getString(R.string.text_hint_cmd_change_title) + "\n";
            messageContent.text = msgText + commandExample;
            messageContent.entities = new TdApi.MessageEntity[1];
            messageContent.entities[0] = new TdApi.MessageEntityCode(msgText.length(), commandExample.length());

            TdApi.SendMessage msgSend = new TdApi.SendMessage();
            msgSend.inputMessageContent = messageContent;
            msgSend.chatId = message.chatId;
            msgSend.replyToMessageId = message.id;

            TgH.send(msgSend, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (TgUtils.isError(object)) {
                        TdApi.Error error = (TdApi.Error) object;
                        chatCommand.getLog()
                                .execCommandError(chatCommand.mChat, pCommand.cmd, error.message, pCommand.answer);
                    }
                }
            });
        } else {
            final String userText = chatCommand.params[1];
            TgH.send(new TdApi.ChangeChatTitle(message.chatId, userText), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if(TgUtils.isOk(object)){
                        chatCommand.getLog().logCommandExecute(LogUtil.Action.CMDTitleChanged,
                                chatCommand.mChat, message.senderUserId, userText);
                    }else{
                        TdApi.Error error = (TdApi.Error) object;
                        chatCommand.getLog().execCommandError(chatCommand.mChat, chatCommand.params[0],
                                error.message, chatCommand.params[1]);
                    }
                }
            });
        }
    }
}
