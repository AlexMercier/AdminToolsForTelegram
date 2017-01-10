package com.madpixels.tgadmintools.entities;

import com.madpixels.tgadmintools.App;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.services.ServiceChatTask;

import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;

/**
 * Created by Snake on 20.09.2016.
 */

public class ChatTaskManager {

    public long chatId;
    ArrayList<ChatTask> tasks;

    public ChatTaskManager(long chatId) {
        this(chatId, false);
    }

    public ChatTaskManager(long chatId, boolean onlyActiveTasks) {
        this.chatId = chatId;
        tasks = DBHelper.getInstance().getChatTasks(chatId, onlyActiveTasks);
    }


    public boolean isRemoveLeaveMessage(TdApi.UpdateNewMessage message) {
        ChatTask task = getTask(ChatTask.TYPE.LeaveMsg, false);
        if (task != null &&
                message.message.content.getConstructor() == TdApi.MessageChatDeleteMember.CONSTRUCTOR)
            return task.isEnabled;
        return false;
    }


    public boolean isRemoveJoinedMessage(TdApi.UpdateNewMessage message) {
        ChatTask task = getTask(ChatTask.TYPE.JoinMsg, false);
        if ( task != null && TgUtils.isUserJoined(message.message) )
            return task.isEnabled;
        return false;
    }

    public boolean hasAttachmentTask(ChatTask.TYPE type) {
        ChatTask task = getTask(type, false);
        return task != null && (task.isRemoveMessage || task.isBanUser);
    }

    public boolean hasBanWords() {
        ChatTask task = getTask(ChatTask.TYPE.BANWORDS, false);
        return task != null && (task.isEnabled);
    }

    public boolean hasCommands() {
        ChatTask task = getTask(ChatTask.TYPE.COMMAND, false);
        return task != null && (task.isEnabled);
    }

    public boolean hasWelcomeText() {
        ChatTask task = getTask(ChatTask.TYPE.WELCOME_USER, false);
        return task != null && (task.isEnabled);
    }
    public boolean hasFloodControl() {
        ChatTask task = getTask(ChatTask.TYPE.FLOOD, false);
        return task != null && task.isEnabled;
    }

    public boolean isEmpty() {
        return tasks == null || tasks.isEmpty();
    }

    public ChatTask getTask(ChatTask.TYPE type) {
        return getTask(type, true);
    }

    public ChatTask getTask(ChatTask.TYPE type, boolean createNew) {
        if (tasks == null)
            tasks = new ArrayList<>();

        for (ChatTask task : tasks)
            if (task.mType == type)
                return task;
        if (!createNew)
            return null;

        // If task not exists, create new:
        ChatTask task = new ChatTask(type.toString(), chatId);

        if(type== ChatTask.TYPE.FLOOD) {
            task.mWarnFrequency = 1;
            task.mAllowCountPerUser = 10;
            task.mWarningsDuringTime = 1 * 60;// 1 minute
        }

        if(type== ChatTask.TYPE.BANWORDS){
            task.mWarningsDuringTime = 1 * 60 * 60;// 1 hour by default
        }

        tasks.add(task);
        return task;
    }

    public void saveTask(ChatTask task) {
        DBHelper.getInstance().saveChatTask(task);
        if (task.isBanUser || task.isRemoveMessage || task.isEnabled)
            ServiceChatTask.start(App.getContext());
    }


    public void remove(ChatTask task) {
        tasks.remove(task);
    }
}

