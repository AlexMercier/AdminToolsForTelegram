package com.madpixels.tgadmintools.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.Utils;
import com.madpixels.apphelpers.ui.ActivityExtended;
import com.madpixels.apphelpers.ui.ProgressDialogBuilder;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.adapters.AdapterChatUsersLocal;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.BotToken;
import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.entities.ChatLogInfo;
import com.madpixels.tgadmintools.entities.ChatTask;
import com.madpixels.tgadmintools.entities.ChatTaskManager;
import com.madpixels.tgadmintools.fragments.FragmentBotTokens;
import com.madpixels.tgadmintools.fragments.FragmentSelectGroup;
import com.madpixels.tgadmintools.fragments.FragmentSelectUsers;
import com.madpixels.tgadmintools.helper.DialogInputValue;
import com.madpixels.tgadmintools.helper.SendMessageHelper;
import com.madpixels.tgadmintools.helper.TaskValues;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.utils.CommonUtils;
import com.madpixels.tgadmintools.utils.TgImageGetter;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconTextView;
import libs.AdHelper;
import view.FloatingActionButton;

/**
 * Created by Snake on 29.02.2016.
 */
public class ActivityGroupInfo extends ActivityExtended {

    ImageButton btnAva;
    TextView tvChatUsername, tvUsersCount, tvAdminsCount, tvKickedCount, tvMutedUsersCount,
            tvInviteLink, tvChatType, tvBanWordsAllowCount, tvSelectLogEventsToChat, tvWelcomeTextShort,
            tvBtnWarnBanWordsFreq, tvFloodMsgAllowCount, tvSelectBot,
            tvBtnWarnBanFloodFreq, tvChatCommandsCount;
    EmojiconTextView tvTitle, tvChatDescription, tvChatAdmin;
    View viewContent, viewLoading, layerBanForBlackWord,
            layerFloodControl, layerClickOpenCommand;
    Button btnConvertToSuper;
    CheckBox chkAnyoneInviteFriendsSuper, chkAnyoneManageGroup, chkEnableMuteUsers, chkPublicBanWords,
            chkWelcomeText, chkReturnOnBannedWordsExpired, chkBlackListedWordsEnabled, chkPublicBanForFlood,
            chkRemoveJoinedMsg, chkRemoveLeaveMsg, chkEnableLogToChat, chkMuteAll, chkMuteJoined,
            chkFloodControlEnabled, chkBanFloodUser, chkReturnOnBanFloodExpired, chkCommandsEnable;
    EditText //edtWelcomeText,
            edtBannedWordBanTimesVal,
            edtBanWordsFloodTimeVal, edtFloodControlTimeVal,
            edtFloodBanTimeVal;
    Spinner
            spinnerBanWordsFloodTime, spinnerBannedWordBanTimes,
            spinnerFloodControlTime, spinnerBanFloodTime;

    private PopupWindow mCurrentPopupWindow;
    FloatingActionButton fabButton;


    int chatType;
    long chatId;
    int groupId, channelId;
    String chatTitle, chatUsername, chatDescription, chatInviteLink;
    int chatUsersCount, adminsCount, kickedCount;
    boolean isAdmin = false; // creator
    boolean superAanyoneCanInvite, groupAnyoneCanEdit;

    String adminName;
    int adminId = 0;
    boolean isChannel = false;

    private ChatTaskManager chatTasks;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("chatType", chatType);
        outState.putInt("channel_id", channelId);
        outState.putInt("group_id", groupId);
        outState.putLong("chat_id", chatId);
        outState.putString("title", chatTitle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        UIUtils.setToolbarWithBackArrow(this, R.id.toolbar);
        AdHelper.showBanner(findViewById(R.id.adView));

        Bundle b = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
        chatType = b.getInt("chatType");
        channelId = b.getInt("channel_id");
        groupId = b.getInt("group_id");
        chatTitle = b.getString("title");
        chatId = b.getLong("chat_id");


        viewContent = getView(R.id.scrollViewMainContent);
        viewLoading = getView(R.id.prgLoading);
        viewContent.setVisibility(View.GONE);

        fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(R.drawable.plus_2_32)
                .withButtonColor(Color.WHITE)
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();
        fabButton.setId(R.id.btnFab);
        fabButton.setVisibility(View.INVISIBLE);

        final ScrollView scrollViewMainContent = getView(R.id.scrollViewMainContent);
        //scroller for hide/show fab button
        scrollViewMainContent.setOnTouchListener(new View.OnTouchListener() {
            int startTouchY = 0, lastScrollY = 0;
            boolean SCROLL_DIRECTION_TO_DOWN = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isChannel) // fabButton not for channels
                    return false;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startTouchY = scrollViewMainContent.getScrollY();
                    lastScrollY = startTouchY;
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    int scrollY = scrollViewMainContent.getScrollY();
                    if (scrollY > lastScrollY) {// scroll to Down
                        if (!SCROLL_DIRECTION_TO_DOWN)
                            startTouchY = scrollY;
                        SCROLL_DIRECTION_TO_DOWN = true;
                        if (scrollY - startTouchY > 10)
                            fabButton.hideFloatingActionButton();
                    } else if (scrollY < lastScrollY) {
                        if (SCROLL_DIRECTION_TO_DOWN)
                            startTouchY = scrollY;
                        SCROLL_DIRECTION_TO_DOWN = false;
                        if (startTouchY - scrollY > 2)
                            fabButton.showFloatingActionButton();
                    }
                    lastScrollY = scrollY;
                }
                return false;
            }
        });


        UIUtils.include(this, R.id.layer_words_antispam, R.layout.layout_task_banwords);
        UIUtils.include(this, R.id.layer_flood_control, R.layout.layout_task_flood_control);
        UIUtils.include(this, R.id.layer_commands, R.layout.layout_commands);

        btnAva = getView(R.id.imgBtnChatAva);
        btnConvertToSuper = getView(R.id.btnConvertToSuper);
        tvChatUsername = getView(R.id.tvChatUsername);
        tvTitle = getView(R.id.tvTitle);
        tvChatDescription = getView(R.id.tvChatDescription);
        tvUsersCount = getView(R.id.tvUsersCount);
        tvChatAdmin = getView(R.id.tvMainAdmin);
        tvAdminsCount = getView(R.id.tvAdminsCount);
        tvKickedCount = getView(R.id.tvKickedCount);
        tvInviteLink = getView(R.id.tvInviteLink);
        //btnEditCommands = getView(R.id.btnEditCommands);
        layerClickOpenCommand = getView(R.id.layerClickOpenCommand);
        chkCommandsEnable = getView(R.id.chkCommandsEnable);
        tvChatCommandsCount = getView(R.id.tvChatCommandsCount);
        tvChatType = getView(R.id.tvChatType);
        // edtWelcomeText = getView(R.id.edtWelcomeText);
        tvWelcomeTextShort = getView(R.id.tvWelcomeTextShort);
        chkWelcomeText = getView(R.id.chkWelcomeText);
        chkReturnOnBannedWordsExpired = getView(R.id.chkReturnOnBannedWordsExpired);
        layerBanForBlackWord = getView(R.id.layerBanForBlackWord);
        chkRemoveJoinedMsg = getView(R.id.chkRemoveJoinedMsg);
        chkRemoveLeaveMsg = getView(R.id.chkRemoveLeaveMsg);
        tvBanWordsAllowCount = getView(R.id.tvBanWordsAllowCount);
        TextView tvNoticePhoneBookEnabled = getView(R.id.tvNoticePhoneBookEnabled);
        tvBtnWarnBanWordsFreq = getView(R.id.tvBtnWarnBanWordsFreq);
        chkBlackListedWordsEnabled = getView(R.id.chkBlackListedWordsEnabled);
        edtBanWordsFloodTimeVal = getView(R.id.edtBanWordsFloodTimeVal);
        spinnerBanWordsFloodTime = getView(R.id.spinnerBanWordsFloodTime);
        spinnerBannedWordBanTimes = getView(R.id.spinnerBannedWordBanTimes);
        edtBannedWordBanTimesVal = getView(R.id.edtBannedWordBanTimesVal);
        chkFloodControlEnabled = getView(R.id.chkFloodControlEnabled);
        tvFloodMsgAllowCount = getView(R.id.tvFloodMsgAllowCount);


        edtFloodControlTimeVal = getView(R.id.edtFloodControlTimeVal);
        spinnerFloodControlTime = getView(R.id.spinnerFloodControlTime);
        chkBanFloodUser = getView(R.id.chkBanFloodUser);
        chkReturnOnBanFloodExpired = getView(R.id.chkReturnOnBanFloodExpired);
        edtFloodBanTimeVal = getView(R.id.edtFloodBanTimeVal);
        spinnerBanFloodTime = getView(R.id.spinnerBanFloodTime);
        layerFloodControl = getView(R.id.layerFloodControl);
        tvBtnWarnBanFloodFreq = getView(R.id.tvBtnWarnBanFloodFreq);
        tvSelectLogEventsToChat = getView(R.id.tvSelectLogEventsToChat);
        chkEnableLogToChat = getView(R.id.chkEnableLogToChat);
        chkEnableMuteUsers = getView(R.id.chkEnableMuteUsers);
        View layerClickOpenMuted = getView(R.id.layerClickOpenMuted);
        tvMutedUsersCount = getView(R.id.tvMutedUsersCount);
        chkMuteAll = getView(R.id.chkMuteAll);
        chkMuteJoined = getView(R.id.chkMuteJoined);
        TextView tvBannedWordsCount = getView(R.id.tvBannedWordsCount);
        View layerSelectBot = getView(R.id.layerSelectBot);
        tvSelectBot = getView(R.id.tvSelectBot);
        chkPublicBanWords = getView(R.id.chkPublicBanWords);
        chkPublicBanForFlood = getView(R.id.chkPublicBanForFlood);

        setTitle(R.string.title_group_info);
        tvTitle.setText(chatTitle);

        UIUtils.setBatchClickListener(onClickListener, tvChatUsername, btnConvertToSuper, tvTitle, tvChatAdmin,
                tvInviteLink, tvAdminsCount, tvUsersCount, tvKickedCount, chkReturnOnBannedWordsExpired,
                tvBanWordsAllowCount, tvBannedWordsCount, fabButton, tvSelectLogEventsToChat, chkEnableLogToChat,
                tvBtnWarnBanWordsFreq, chkBlackListedWordsEnabled, chkEnableMuteUsers, tvChatDescription,
                chkFloodControlEnabled, tvFloodMsgAllowCount, chkBanFloodUser, chkReturnOnBanFloodExpired,
                tvBtnWarnBanFloodFreq, tvNoticePhoneBookEnabled, layerClickOpenCommand, chkCommandsEnable,
                layerClickOpenMuted, tvWelcomeTextShort, chkWelcomeText, chkMuteAll, chkMuteJoined,
                layerSelectBot, chkPublicBanWords, chkPublicBanForFlood);

        if (Sets.getBoolean(Const.ANTISPAM_IGNORE_SHARED_CONTACTS, Const.ANTISPAM_IGNORE_SHARED_CONTACTS_DEFAULT)) {
            tvNoticePhoneBookEnabled.setText(getString(R.string.label_notice_ignore_antispam_for_shared));
        } else
            tvNoticePhoneBookEnabled.setVisibility(View.GONE);

        chkAnyoneInviteFriendsSuper = getView(R.id.chkAnyoneInviteFriendsSuper);
        chkAnyoneManageGroup = getView(R.id.chkAnyoneManageGroup);
        if (TgUtils.isGroup(chatType)) {
            chkAnyoneInviteFriendsSuper.setVisibility(View.GONE);
            tvChatUsername.setVisibility(View.GONE);
            findViewById(R.id.viewChatDescription).setVisibility(View.GONE);
        } else {
            chkAnyoneManageGroup.setVisibility(View.GONE);
        }
        UIUtils.setBatchClickListener(onClickListener, chkAnyoneInviteFriendsSuper, chkAnyoneManageGroup);


        UIUtils.setBatchClickListener(onClickListener, chkRemoveLeaveMsg, chkRemoveJoinedMsg);


        chatTasks = new ChatTaskManager(chatId);


        addTask(ChatTask.TYPE.LINKS, false);
        addTask(ChatTask.TYPE.STICKERS, false);
        addTask(ChatTask.TYPE.VOICE, false);
        addTask(ChatTask.TYPE.IMAGES, false);
        addTask(ChatTask.TYPE.GIF, false);
        addTask(ChatTask.TYPE.AUDIO, false);
        addTask(ChatTask.TYPE.VIDEO, false);
        addTask(ChatTask.TYPE.GAME, false);
        addTask(ChatTask.TYPE.DOCS, false);


        setSpinnerBanTimesListener(ChatTask.TYPE.BANWORDS, spinnerBannedWordBanTimes, edtBannedWordBanTimesVal, chkReturnOnBannedWordsExpired);
        setSpinnerFloodSelectorListener(ChatTask.TYPE.BANWORDS, spinnerBanWordsFloodTime, edtBanWordsFloodTimeVal);

        setSpinnerBanTimesListener(ChatTask.TYPE.FLOOD, spinnerBanFloodTime, edtFloodBanTimeVal, chkReturnOnBanFloodExpired);
        setSpinnerFloodSelectorListener(ChatTask.TYPE.FLOOD, spinnerFloodControlTime, edtFloodControlTimeVal);


        if (TgUtils.isSuperGroup(chatType)) {
            btnConvertToSuper.setVisibility(View.GONE);
        } else {
            tvChatType.setText(R.string.chatTypeGroup);
            UIUtils.setTextColotRes(chkRemoveJoinedMsg, R.color.md_grey_500);
            UIUtils.setTextColotRes(chkRemoveLeaveMsg, R.color.md_grey_500);
            UIUtils.setTextColotRes(chkEnableMuteUsers, R.color.md_grey_500);
            UIUtils.setTextColotRes(tvMutedUsersCount, R.color.md_grey_500);
            UIUtils.setTextColotRes(chkMuteAll, R.color.md_grey_500);
            UIUtils.setTextColotRes(chkMuteJoined, R.color.md_grey_500);
        }


        if (!chatTasks.isEmpty()) {
            ChatTask taskJoinMsg = chatTasks.getTask(ChatTask.TYPE.JoinMsg, false);
            if (taskJoinMsg != null) {
                chkRemoveJoinedMsg.setChecked(taskJoinMsg.isEnabled);
            }

            ChatTask taskMutedUsers = chatTasks.getTask(ChatTask.TYPE.MutedUsers, false);
            if (taskMutedUsers != null) {
                chkEnableMuteUsers.setChecked(taskMutedUsers.isEnabled);
                chkMuteAll.setChecked(taskMutedUsers.isRemoveMessage);
                chkMuteJoined.setChecked(taskMutedUsers.isBanUser);
                if (!taskMutedUsers.isEnabled) {
                    UIUtils.setTextColotRes(tvMutedUsersCount, R.color.md_grey_500);
                    UIUtils.setTextColotRes(chkMuteAll, R.color.md_grey_500);
                    UIUtils.setTextColotRes(chkMuteJoined, R.color.md_grey_500);
                }
            }

            ChatTask taskFlood = chatTasks.getTask(ChatTask.TYPE.FLOOD, false);
            if (taskFlood != null) {
                if (taskFlood.isEnabled)
                    chkFloodControlEnabled.setChecked(true);
                else
                    layerFloodControl.setVisibility(View.GONE);
                chkPublicBanForFlood.setChecked(taskFlood.isPublicToChat);

                chkBanFloodUser.setChecked(taskFlood.isBanUser);
                tvFloodMsgAllowCount.setText(taskFlood.mAllowCountPerUser + "");
                chkReturnOnBanFloodExpired.setChecked(taskFlood.isReturnOnBanExpired);
                if (!taskFlood.isBanUser) {
                    edtFloodBanTimeVal.setEnabled(false);
                    spinnerBanFloodTime.setEnabled(false);
                    chkReturnOnBanFloodExpired.setEnabled(false);

                }

                if (taskFlood.mWarningsDuringTime == 0) {
                    edtFloodControlTimeVal.setTag("1");
                    edtFloodControlTimeVal.setText("5");
                    spinnerFloodControlTime.setTag(1);// flag for skip save changes
                    spinnerFloodControlTime.setSelection(0);
                } else {
                    setSecondsFormatToSpinner(taskFlood.mWarningsDuringTime, edtFloodControlTimeVal, spinnerFloodControlTime);
                }

                if (taskFlood.mBanTimeSec > 0) {
                    setSecondsFormatToSpinner(taskFlood.mBanTimeSec, edtFloodBanTimeVal, spinnerBanFloodTime);
                } else {// 0 - permanent ban.

                    edtFloodBanTimeVal.setTag("1");
                    edtFloodBanTimeVal.setText("5");
                    chkReturnOnBanFloodExpired.setEnabled(false);
                    edtFloodBanTimeVal.setEnabled(false);
                    spinnerBanFloodTime.setTag(1);
                    spinnerBanFloodTime.setSelection(0);
                }

                setWarnFrequencyText(tvBtnWarnBanFloodFreq, taskFlood.mWarnFrequency);


            } else {
                setDefaultFloodValues();
            }

            ChatTask taskLeaveMsg = chatTasks.getTask(ChatTask.TYPE.LeaveMsg, false);
            if (taskLeaveMsg != null) {
                chkRemoveLeaveMsg.setChecked(taskLeaveMsg.isEnabled);
            }

            ChatTask taskBanWords = chatTasks.getTask(ChatTask.TYPE.BANWORDS, false);
            if (taskBanWords != null) {
                chkBlackListedWordsEnabled.setChecked(taskBanWords.isEnabled);
                chkPublicBanWords.setChecked(taskBanWords.isPublicToChat);

                if (taskBanWords.mBanTimeSec > 0) {
                    setSecondsFormatToSpinner(taskBanWords.mBanTimeSec, edtBannedWordBanTimesVal, spinnerBannedWordBanTimes);
                } else {// 0 - permanent ban.
                    edtBannedWordBanTimesVal.setTag("1");
                    edtBannedWordBanTimesVal.setText("5");
                    chkReturnOnBannedWordsExpired.setEnabled(false);
                    edtBannedWordBanTimesVal.setEnabled(false);
                    spinnerBannedWordBanTimes.setTag(1);
                    spinnerBannedWordBanTimes.setSelection(0);
                }

                tvBanWordsAllowCount.setText(taskBanWords.mAllowCountPerUser + "");
                chkReturnOnBannedWordsExpired.setChecked(taskBanWords.isReturnOnBanExpired);

                if (taskBanWords.mWarningsDuringTime == 0) {
                    edtBanWordsFloodTimeVal.setTag("1");
                    edtBanWordsFloodTimeVal.setText("5");
                    spinnerBanWordsFloodTime.setTag(1);// flag for skip save changes
                    spinnerBanWordsFloodTime.setSelection(0);
                } else {
                    setSecondsFormatToSpinner(taskBanWords.mWarningsDuringTime, edtBanWordsFloodTimeVal, spinnerBanWordsFloodTime);
                }

                if (!taskBanWords.isEnabled)
                    layerBanForBlackWord.setVisibility(View.GONE);
                setWarnFrequencyText(tvBtnWarnBanWordsFreq, taskBanWords.mWarnFrequency);
            } else {
                setDefaultBannedWordsValues();
            }

            ChatTask taskCommands = chatTasks.getTask(ChatTask.TYPE.COMMAND, false);
            if (taskCommands != null) {
                chkCommandsEnable.setChecked(taskCommands.isEnabled);
                if (!taskCommands.isEnabled) {
                    // layerClickOpenCommand.setEnabled(false);
                }

                updateCommandsCount();
            } else {
                // layerClickOpenCommand.setEnabled(false);
            }

            ChatTask welcomeText = chatTasks.getTask(ChatTask.TYPE.WELCOME_USER, false);
            if (welcomeText != null) {
                chkWelcomeText.setChecked(welcomeText.isEnabled);
                if (!TextUtils.isEmpty(welcomeText.mText))
                    tvWelcomeTextShort.setText(welcomeText.mText);
            }

        } else {
            setDefaultBannedWordsValues();
            setDefaultFloodValues();
        }

        int blackWordsCount = DBHelper.getInstance().getWordsBlackListCount(chatId);
        if (blackWordsCount == 0) {
            tvBannedWordsCount.setText(R.string.btn_banned_words_list_empty);
        } else {
            tvBannedWordsCount.setText(getString(R.string.banned_words_count, blackWordsCount));
            //tvBannedWordsCount.setText(String.format("%d banned words for this chat", blackWordsCount));
        }

        setEditTextListenerToBanTimeValueNew(ChatTask.TYPE.BANWORDS, edtBannedWordBanTimesVal, chkReturnOnBannedWordsExpired, spinnerBannedWordBanTimes);
        setEditTextListenerToBanTimeValueNew(ChatTask.TYPE.FLOOD, edtFloodBanTimeVal, chkReturnOnBanFloodExpired, spinnerBannedWordBanTimes);

        // setWelcomeText();
        updateMutedUsersCount();
        getChatInfo();
    }

    private void setDefaultBannedWordsValues() {
        spinnerBannedWordBanTimes.setTag(1);
        spinnerBannedWordBanTimes.setSelection(1);
        tvBanWordsAllowCount.setText("3");
        layerBanForBlackWord.setVisibility(View.GONE);
        edtBanWordsFloodTimeVal.setTag("1");
        edtBanWordsFloodTimeVal.setText("1");
        spinnerBanWordsFloodTime.setTag(1);
        spinnerBanWordsFloodTime.setSelection(2);
        chkReturnOnBannedWordsExpired.setChecked(true);
    }

    private void setDefaultFloodValues() {
        layerFloodControl.setVisibility(View.GONE);
        tvFloodMsgAllowCount.setText("10");
        edtFloodControlTimeVal.setTag("1");
        edtFloodControlTimeVal.setText("1");
        spinnerFloodControlTime.setTag(1);
        spinnerFloodControlTime.setSelection(1);
        spinnerBanFloodTime.setTag(1);
        spinnerBanFloodTime.setSelection(1);
        tvBtnWarnBanFloodFreq.setText(R.string.text_warn_on_last_warn);

        edtFloodBanTimeVal.setEnabled(false);
        spinnerBanFloodTime.setEnabled(false);
        chkReturnOnBanFloodExpired.setEnabled(false);
    }


    void setEditTextListenerToBanTimeValueNew(final ChatTask.TYPE pType, final EditText editBanTimeVal, final CheckBox chkReturnOnBanExired, final Spinner spinnerBanTime) {
        editBanTimeVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editBanTimeVal.getTag() != null && editBanTimeVal.getTag().toString().equals("1"))// ignore when set default value
                    editBanTimeVal.setTag(null);
                else
                    editBanTimeVal.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(Editable s) {
                editBanTimeVal.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (editBanTimeVal.getTag() == null)
                            return;

                        long ts = Long.valueOf(editBanTimeVal.getTag().toString());
                        if (System.currentTimeMillis() - ts < 900)
                            return;

                        long val = setBanAgeForTask(pType, spinnerBanTime.getSelectedItemPosition(), editBanTimeVal.getText().toString());
                        if (val > -1)
                            chkReturnOnBanExired.setEnabled(val > 0);
                    }
                }, 1000);
            }
        });

    }
    /*
    void setEditTextChangeListenerToSaveBanTimeValueOld(final ChatTask.TYPE pType) {
        final Spinner spinnerTimeMultiplier;
        final EditText edit;
        final CheckBox chkReturnOnBanExired;

        switch (pType) {
            case BANWORDS:
                edit = edtBannedWordBanTimesVal;
                chkReturnOnBanExired = chkReturnOnBannedWordsExpired;
                spinnerTimeMultiplier = spinnerBannedWordBanTimes;
                break;

            case FLOOD:
                edit = edtFloodBanTimeVal;
                chkReturnOnBanExired = chkReturnOnBanFloodExpired;
                spinnerTimeMultiplier = spinnerBanFloodTime;
                break;
            default:
                edit = null;
                spinnerTimeMultiplier = null;
                chkReturnOnBanExired = null;
                break;
        }

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit.getTag() != null && edit.getTag().toString().equals("1"))// ignore when set default value
                    edit.setTag(null);
                else
                    edit.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(Editable s) {
                edit.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (edit.getTag() == null)
                            return;

                        long ts = Long.valueOf(edit.getTag().toString());
                        if (System.currentTimeMillis() - ts < 900)
                            return;

                        long val = setBanAgeForTask(pType, spinnerTimeMultiplier.getSelectedItemPosition(), edit.getText().toString());
                        if (val > -1)
                            chkReturnOnBanExired.setEnabled(val > 0);
                    }
                }, 1000);
            }
        });

    }
    */

    private void setWarnFrequencyText(TextView textView, int warnFrequency) {
        int resIDWarnText = 0;
        switch (warnFrequency) {
            case 0:
                resIDWarnText = R.string.text_warn_no_warn_user;
                break;
            case 1:
                resIDWarnText = R.string.text_warn_on_last_warn;
                break;
            case 2:
                resIDWarnText = R.string.text_warn_on_first_warn;
                break;
            case 3:
                resIDWarnText = R.string.text_warn_on_first_last_warn;
                break;
            case 4:
                resIDWarnText = R.string.text_warn_on_second_and_last_warn;
                break;
            case 5:
                resIDWarnText = R.string.text_warn_always;
                break;
        }
        textView.setText(resIDWarnText);
    }

    private void setSpinnerBanTimesListener(final ChatTask.TYPE pType, final Spinner spinerBanTime, final EditText edtBanTimeVal, final CheckBox chkReturnOnExpired) {
        String[] banTimes = getResources().getStringArray(R.array.auto_ban_times);
        ArrayAdapter<String> pLinksAgessAdapter = new ArrayAdapter<String>(
                mContext, R.layout.item_spinner, R.id.textView, banTimes);

        spinerBanTime.setAdapter(pLinksAgessAdapter);
        spinerBanTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinerBanTime.getTag() != null)//if has tag we skip saving changes.
                    spinerBanTime.setTag(null);//just clear prevous state
                else {
                    edtBanTimeVal.setEnabled(position > 0);
                    chkReturnOnExpired.setEnabled(position > 0);

                    setBanAgeForTask(pType, position, edtBanTimeVal.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerFloodSelectorListener(final ChatTask.TYPE pType, final Spinner spinner, final EditText edtValue) {
        String[] banWithinTimes = getResources().getStringArray(R.array.times_during_warnings);
        ArrayAdapter<String> pTimesAdapter = new ArrayAdapter<String>(
                mContext, R.layout.item_spinner, R.id.textView, banWithinTimes);
        spinner.setAdapter(pTimesAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getTag() != null)
                    spinner.setTag(null);
                else {
                    edtValue.setEnabled(position > 0);
                    saveTaskWarnWithinTime(pType, position, edtValue.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        edtValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtValue.getTag() != null && edtValue.getTag().toString().equals("1"))
                    edtValue.setTag(null);
                else
                    edtValue.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(Editable s) {
                edtValue.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (edtValue.getTag() == null)
                            return;

                        long ts = Long.valueOf(edtValue.getTag().toString());
                        if (System.currentTimeMillis() - ts < 900)
                            return;

                        saveTaskWarnWithinTime(pType, spinner.getSelectedItemPosition(), edtValue.getText().toString());
                    }
                }, 1000);

            }
        });
    }


    private void saveTaskWarnWithinTime(ChatTask.TYPE type, int position, String valueStr) {
        ChatTask task = chatTasks.getTask(type);
        int value;
        try {
            value = Integer.valueOf(valueStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        switch (position) {
            case 0:
                task.mWarningsDuringTime = 0;
                break;
            case 1:
                task.mWarningsDuringTime = TimeUnit.MINUTES.toSeconds(value);
                break;
            case 2:
                task.mWarningsDuringTime = TimeUnit.HOURS.toSeconds(value);
                break;
            case 3:
                task.mWarningsDuringTime = TimeUnit.DAYS.toSeconds(value);
                break;
        }
        chatTasks.saveTask(task);
    }


    /**
     * Spinner must have values: 0-Forever, 1-minutes, 2-hours, 3 - days.
     */
    void setSecondsFormatToSpinner(long seconds, EditText editText, Spinner spinnerForSelection) {
        String text;
        int spinnerPos;
        if (seconds >= TimeUnit.DAYS.toSeconds(1)) {
            spinnerPos = 3;//days selection
            text = String.valueOf((TimeUnit.SECONDS.toDays(seconds)));
        } else if (seconds >= TimeUnit.HOURS.toSeconds(1)) {
            spinnerPos = 2;// selection hours
            text = String.valueOf(TimeUnit.SECONDS.toHours(seconds));
        } else {//minutes
            spinnerPos = 1;// minutes
            text = String.valueOf(TimeUnit.SECONDS.toMinutes(seconds));
        }

        editText.setTag("1");// flag for ignore save on change
        editText.setText(text);
        spinnerForSelection.setTag(1);// flag for skip save changes
        spinnerForSelection.setSelection(spinnerPos);
    }

    /*
    private void setWelcomeText() {
        chkWelcomeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.getInstance().setWelcomeTextEnabled(chatId, chkWelcomeText.isChecked());
                if (chkWelcomeText.isChecked())
                    ServiceChatTask.start(mContext);
            }
        });

        Object[] welcomeTexts = DBHelper.getInstance().getWelcomeTextObject(chatId);
        if (welcomeTexts == null) {
            return;
        }
        boolean isEnabled = (boolean) welcomeTexts[0];
        chkWelcomeText.setChecked(isEnabled);
        if (welcomeTexts[1] != null) {
            String text = welcomeTexts[1].toString();
            tvWelcomeTextShort.setText(text);
        } else {
            tvWelcomeTextShort.setText(R.string.hint_enter_welcome_text);
        }
        //edtWelcomeText.setText(text);

    }
    */

    long setBanAgeForTask(ChatTask.TYPE pType, int selection, String textValue) {
        long banAge = 0;
        int val;

        try {
            val = Integer.valueOf(textValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }

        switch (selection) {
            case 0:
                banAge = 0; // forever ban
                break;
            case 1:
                banAge = TimeUnit.MINUTES.toSeconds(val);
                break;
            case 2:
                banAge = TimeUnit.HOURS.toSeconds(val);
                break;
            case 3:
                banAge = TimeUnit.DAYS.toSeconds(val);
                break;
        }

        ChatTask task = chatTasks.getTask(pType);
        task.mBanTimeSec = banAge;
        chatTasks.saveTask(task);

        return banAge;
    }


    SeekBar.OnSeekBarChangeListener onSeekBarBanCountListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                // ChatTask.TYPE type = ChatTask.TYPE.valueOf(seekBar.getTag(R.id.tag_id_type).toString());
                TextView tvCounter = (TextView) seekBar.getTag(R.id.tag_id_view);
                String text = progress + "";
                tvCounter.setText(text);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            ChatTask.TYPE type = ChatTask.TYPE.valueOf(seekBar.getTag(R.id.tag_id_type).toString());
            int val = seekBar.getProgress();
            ChatTask task = chatTasks.getTask(type);
            task.mAllowCountPerUser = val;
            chatTasks.saveTask(task);

        }
    };

    public static void collapse(final View v) {
        v.setAnimation(null);

        ScaleAnimation scale = new ScaleAnimation(1f, 1f, 1f, 0f);
        scale.setFillAfter(true);
        scale.setDuration(300);
        scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(scale);
    }

    public static void expand(final View v) {
        v.setAnimation(null);
        v.setVisibility(View.VISIBLE);
        ScaleAnimation scale = new ScaleAnimation(1f, 1f, 0f, 1f);
        scale.setFillAfter(true);
        scale.setDuration(250);
        v.startAnimation(scale);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnFab:
                    if (fabButton.isHidden())
                        return;
                    new DialogAddTask().show();
                    break;

                /** ============= BASE TASK ============ **/
                case R.id.chkRemoveMessage:
                    CheckBox cbox = (CheckBox) v;
                    if (TgUtils.isGroup(chatType)) {
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        cbox.setChecked(false);
                        return;
                    }

                    ChatTask.TYPE pType = (ChatTask.TYPE) v.getTag(R.id.tag_id_type);
                    ChatTask task = chatTasks.getTask(pType);
                    task.isRemoveMessage = cbox.isChecked();
                    chatTasks.saveTask(task);
                    break;
                case R.id.chkBanForMessage:
                    pType = (ChatTask.TYPE) v.getTag(R.id.tag_id_type);
                    View layerBanParams = (View) v.getTag(R.id.tag_id_view);
                    cbox = (CheckBox) v;

                    task = chatTasks.getTask(pType);
                    task.isBanUser = cbox.isChecked();
                    chatTasks.saveTask(task);

                    if (cbox.isChecked()) {
                        expand(layerBanParams);
                    } else {
                        collapse(layerBanParams);
                    }
                    break;

                case R.id.tvBtnWarnFreq:
                    pType = (ChatTask.TYPE) v.getTag(R.id.tag_id_type);
                    showPopupForFrequencyTask(pType, (TextView) v);
                    break;

                case R.id.chkReturnOnBanExpired:
                    pType = (ChatTask.TYPE) v.getTag(R.id.tag_id_type);
                    cbox = (CheckBox) v;

                    task = chatTasks.getTask(pType);
                    task.isReturnOnBanExpired = cbox.isChecked();
                    chatTasks.saveTask(task);
                    break;

                case R.id.chkPublicBan:
                    pType = (ChatTask.TYPE) v.getTag(R.id.tag_id_type);
                    cbox = (CheckBox) v;

                    task = chatTasks.getTask(pType);
                    task.isPublicToChat = cbox.isChecked();
                    chatTasks.saveTask(task);
                    break;

                case R.id.tvBanAllowCount:
                    pType = (ChatTask.TYPE) v.getTag(R.id.tag_id_type);
                    SeekBar seekBarAllowCount = (SeekBar) v.getTag(R.id.tag_id_view);
                    setTaskWarnCountDialog(pType, seekBarAllowCount, (TextView) v);
                    break;
                /** ================================== **/


                case R.id.tvChatUsername:
                    if (isAdmin)
                        dialogSetUsername(chatUsername);
                    else
                        MyToast.toast(mContext, R.string.toast_access_denied_admin);
                    break;
                case R.id.btnConvertToSuper:
                    confirmConvertToSuperGroup();
                    break;
                case R.id.tvTitle:
                    dialogRenameChatTitle();
                    break;
                case R.id.tvMainAdmin:
                    if (adminId == 0)
                        return;
                    break;
                case R.id.tvChatDescription:
                    dialogEditChatDescription(null);
                    break;
                case R.id.chkAnyoneInviteFriendsSuper:
                    switchAnyoneCanInviteSuperGroup();
                    break;
                case R.id.chkAnyoneManageGroup:
                    switchAnyoneManageGroup();
                    break;

                /** =================== BANWORDS ================ **/
                case R.id.chkBlackListedWordsEnabled:
                    task = chatTasks.getTask(ChatTask.TYPE.BANWORDS);
                    task.isEnabled = chkBlackListedWordsEnabled.isChecked();
                    layerBanForBlackWord.setVisibility(task.isEnabled ? View.VISIBLE : View.GONE);
                    chatTasks.saveTask(task);
                    break;
                case R.id.chkPublicBanWords:
                    task = chatTasks.getTask(ChatTask.TYPE.BANWORDS);
                    task.isPublicToChat = chkPublicBanWords.isChecked();
                    chatTasks.saveTask(task);
                    break;
                case R.id.tvBannedWordsCount:
                    startActivity(new Intent(mContext, ActivityBannedWordsList.class).putExtra("chat_id", chatId));
                    break;
                case R.id.chkReturnOnBannedWordsExpired:
                    task = chatTasks.getTask(ChatTask.TYPE.BANWORDS);
                    task.isReturnOnBanExpired = chkReturnOnBannedWordsExpired.isChecked();
                    chatTasks.saveTask(task);
                    break;
                case R.id.tvBanWordsAllowCount:
                    setTaskWarnCountDialog(ChatTask.TYPE.BANWORDS, null, tvBanWordsAllowCount);
                    break;

                case R.id.tvBtnWarnBanWordsFreq:
                    showPopupForFrequencyTask(ChatTask.TYPE.BANWORDS, tvBtnWarnBanWordsFreq);
                    break;
                /** ========================= **/


                case R.id.tvInviteLink:
                    if (isAdmin)
                        popupInviteLink();
                    else
                        MyToast.toast(mContext, R.string.toast_access_denied_admin);
                    break;
                case R.id.tvAdminsCount:
                    startActivity(new Intent(mContext, ActivityChatUsers.class)
                            .putExtra("filter", "admins")
                            .putExtra("type", chatType)
                            .putExtra("chat_id", chatId)
                            .putExtra("channel_id", channelId)
                            .putExtra("title", chatTitle)
                            .putExtra("group_id", groupId)
                    );
                    break;
                case R.id.tvUsersCount:
                    startActivity(new Intent(mContext, ActivityChatUsers.class)
                            .putExtra("type", chatType)
                            .putExtra("chat_id", chatId)
                            .putExtra("channel_id", channelId)
                            .putExtra("title", chatTitle)
                            .putExtra("group_id", groupId)
                    );
                    break;


                case R.id.tvKickedCount:
                    if (TgUtils.isSuperGroup(chatType)) {
                        startActivity(new Intent(mContext, ActivityBanList.class)
                                .putExtra("type", chatType)
                                .putExtra("chat_id", chatId)
                                .putExtra("channel_id", channelId));
                    } else {
                        startActivity(new Intent(mContext, ActivityBanList.class)
                                .putExtra("type", chatType)
                                .putExtra("chat_id", chatId));
                    }
                    break;


                case R.id.chkRemoveLeaveMsg:
                    if (TgUtils.isGroup(chatType)) {
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        chkRemoveLeaveMsg.setChecked(false);
                        return;
                    }

                    task = chatTasks.getTask(ChatTask.TYPE.LeaveMsg);
                    task.isEnabled = chkRemoveLeaveMsg.isChecked();
                    chatTasks.saveTask(task);
                    break;

                case R.id.chkRemoveJoinedMsg:
                    if (TgUtils.isGroup(chatType)) {
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        chkRemoveJoinedMsg.setChecked(false);
                        return;
                    }

                    task = chatTasks.getTask(ChatTask.TYPE.JoinMsg);
                    task.isEnabled = chkRemoveJoinedMsg.isChecked();
                    chatTasks.saveTask(task);
                    break;


                /** ================== Messaging flood ============= **/
                case R.id.chkFloodControlEnabled:
                    task = chatTasks.getTask(ChatTask.TYPE.FLOOD);
                    task.isEnabled = chkFloodControlEnabled.isChecked();
                    if (!TgUtils.isSuperGroup(chatType)) {
                        task.isBanUser = true; //Force enable ban user for non-supergroups, coz we can't delete messages at this type of group.
                        chkBanFloodUser.setChecked(true);
                    }
                    chatTasks.saveTask(task);
                    layerFloodControl.setVisibility(task.isEnabled ? View.VISIBLE : View.GONE);
                    break;
                case R.id.tvFloodMsgAllowCount:
                    setTaskWarnCountDialog(ChatTask.TYPE.FLOOD, null, tvFloodMsgAllowCount);
                    break;
                case R.id.chkBanFloodUser:
                    if (!TgUtils.isSuperGroup(chatType)) {
                        //Force enable ban user for non-supergroups, coz we can't delete messages at this type of group.
                        MyToast.toast(mContext, "You can't disable ban users for non-supergroups because telegram not support deletion in non-supergroups");
                        chkBanFloodUser.setChecked(true);
                        return;
                    }

                    task = chatTasks.getTask(ChatTask.TYPE.FLOOD);
                    task.isBanUser = chkBanFloodUser.isChecked();
                    chatTasks.saveTask(task);

                    boolean b = task.isBanUser && spinnerBanFloodTime.getSelectedItemPosition() > 0;
                    chkReturnOnBanFloodExpired.setEnabled(b);
                    edtFloodBanTimeVal.setEnabled(b);
                    spinnerBanFloodTime.setEnabled(b);
                    break;
                case R.id.chkReturnOnBanFloodExpired:
                    task = chatTasks.getTask(ChatTask.TYPE.FLOOD);
                    task.isReturnOnBanExpired = chkReturnOnBanFloodExpired.isChecked();
                    chatTasks.saveTask(task);
                    break;
                case R.id.tvBtnWarnBanFloodFreq:
                    showPopupForFrequencyTask(ChatTask.TYPE.FLOOD, (TextView) v);
                    break;
                case R.id.chkPublicBanForFlood:
                    task = chatTasks.getTask(ChatTask.TYPE.FLOOD);
                    task.isPublicToChat = chkPublicBanForFlood.isChecked();
                    chatTasks.saveTask(task);
                    break;
                /** =========================== **/

                case R.id.tvNoticePhoneBookEnabled:
                    startActivity(new Intent(mContext, ActivitySettings.class));
                    break;

                case R.id.layerClickOpenCommand:
                    Intent intent = new Intent(mContext, ActivityChatCommands.class).putExtra("chat_id", chatId);
                    startActivityForResult(intent, Const.ACTION_REFRESH_COMMANDS);
                    break;


                case R.id.chkCommandsEnable:
                    task = chatTasks.getTask(ChatTask.TYPE.COMMAND);
                    task.isEnabled = chkCommandsEnable.isChecked();
                    // layerClickOpenCommand.setEnabled(task.isEnabled);
                    UIUtils.setTextColotRes(tvChatCommandsCount, task.isEnabled ? R.color.md_teal_500 : R.color.md_blue_grey_500);
                    chatTasks.saveTask(task);
                    break;
                case R.id.tvSelectLogEventsToChat:
                    dialogSelectGroup();
                    break;

                case R.id.chkEnableLogToChat:
                    boolean isEnabled = chkEnableLogToChat.isChecked();
                    DBHelper.getInstance().setChatLogEnabled(chatId, isEnabled);
                    UIUtils.setTextColotRes(tvSelectLogEventsToChat, isEnabled ? R.color.md_teal_500 : R.color.md_grey_500);
                    break;
                case R.id.chkEnableMuteUsers:
                    if (TgUtils.isGroup(chatType)) {
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        chkEnableMuteUsers.setChecked(false);
                        return;
                    }

                    task = chatTasks.getTask(ChatTask.TYPE.MutedUsers);
                    task.isEnabled = chkEnableMuteUsers.isChecked();
                    chatTasks.saveTask(task);

                    UIUtils.setTextColotRes(tvMutedUsersCount, task.isEnabled ? R.color.md_teal_500 : R.color.md_grey_500);
                    UIUtils.setTextColotRes(chkMuteAll, task.isEnabled ? R.color.md_black_1000 : R.color.md_grey_500);
                    UIUtils.setTextColotRes(chkMuteJoined, task.isEnabled ? R.color.md_black_1000 : R.color.md_grey_500);
                    break;
                case R.id.layerClickOpenMuted:
                    if (TgUtils.isGroup(chatType)) {
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        return;
                    }

                    FragmentSelectUsers fUsers = new FragmentSelectUsers();
                    fUsers.chatType = chatType;
                    fUsers.chatlId = chatId;
                    if (TgUtils.isSuperGroup(chatType))
                        fUsers.channelId = channelId;
                    else
                        fUsers.groupId = groupId;

                    fUsers.setOnUserSelected(new Callback() {
                        @Override
                        public void onResult(Object data) {
                            TdApi.ChatMember member = (TdApi.ChatMember) data;
                            if (data instanceof AdapterChatUsersLocal.ChatMemberUser) {
                                DBHelper.getInstance().removeMutedUser(chatId, member.userId);
                            } else {
                                TdApi.User user = TgUtils.getUser(member.userId);
                                DBHelper.getInstance().addMutedUser(chatId, member.userId, user.firstName + " " + user.lastName);
                            }
                            updateMutedUsersCount();
                        }
                    });
                    fUsers.show(getSupportFragmentManager(), "users");
                    break;
                case R.id.chkMuteAll:
                    if (TgUtils.isGroup(chatType)) {
                        chkMuteAll.setChecked(false);
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        return;
                    }

                    task = chatTasks.getTask(ChatTask.TYPE.MutedUsers);
                    task.isRemoveMessage = chkMuteAll.isChecked();
                    chatTasks.saveTask(task);
                    if (task.isEnabled)
                        dialogNotifyChatToReadonlyMode(task.isRemoveMessage);
                    break;
                case R.id.chkMuteJoined:
                    if (TgUtils.isGroup(chatType)) {
                        chkMuteJoined.setChecked(false);
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        return;
                    }

                    task = chatTasks.getTask(ChatTask.TYPE.MutedUsers);
                    task.isBanUser = chkMuteJoined.isChecked();
                    chatTasks.saveTask(task);
                    break;

                case R.id.tvWelcomeTextShort:
                    dialogEditWelcomeText();
                    break;
                case R.id.chkWelcomeText:
                    task = chatTasks.getTask(ChatTask.TYPE.WELCOME_USER);
                    task.isEnabled = chkWelcomeText.isChecked();
                    chatTasks.saveTask(task);
                    break;
                case R.id.layerSelectBot:
                    FragmentBotTokens fragmentBotTokens = new FragmentBotTokens();
                    fragmentBotTokens.setOnBotSelectedCallback(new Callback() {
                        @Override
                        public void onResult(Object data) {
                            int bot_db_id = (int) data;
                            if (bot_db_id == 0) {// remove bot
                                tvSelectBot.setText(R.string.text_bot_not_selected);
                                DBHelper.getInstance().removeChatTask(chatTasks.getTask(ChatTask.TYPE.CHAT_BOT).id);
                                return;
                            }
                            BotToken botToken = DBHelper.getInstance().getBotToken(bot_db_id);
                            if (botToken != null) {
                                tvSelectBot.setText(botToken.mFirstName);
                                ChatTask task = chatTasks.getTask(ChatTask.TYPE.CHAT_BOT);
                                task.mText = botToken.mToken;
                                chatTasks.saveTask(task);
                                TdApi.TLFunction f = new TdApi.ChangeChatMemberStatus(chatId, botToken.bot_id, new TdApi.ChatMemberStatusEditor());
                                TgH.send(f);
                            }
                        }
                    });
                    fragmentBotTokens.show(getSupportFragmentManager(), "groups");
                    break;
            }
        }
    };


    private void updateCommandsCount() {
        int chatCommandsCount = DBHelper.getInstance().getChatCommandsCount(chatId);
        String commandsCountText;
        if (chatCommandsCount == 0)
            commandsCountText = getString(R.string.label_no_commands_in_chat);
        else {
            commandsCountText = " " + Utils.pluralValue(mContext, R.array.commands_plural, chatCommandsCount) + " ";
            commandsCountText = chatCommandsCount + commandsCountText + getString(R.string.label_commands_count_in_chat);
        }
        tvChatCommandsCount.setText(commandsCountText);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Const.ACTION_REFRESH_COMMANDS:
                updateCommandsCount();
                break;
        }
    }
    /*
    void setBanEnabledForTask(ChatTask.TYPE pType, boolean isBan) { //TODO выпилить так как будет единый метод в онклике
        ChatTask task = chatTasks.getTask(pType);
        task.isBanUser = isBan;
        chatTasks.saveTask(task);
    }
    */


    private void showPopupForFrequencyTask(final ChatTask.TYPE pType, final TextView tvPressedTextView) {
        View popupView = UIUtils.inflate(mContext, R.layout.popup_warnuser_frequency);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btnClose = UIUtils.getView(popupView, R.id.btnClose);
        Button btnSave = UIUtils.getView(popupView, R.id.btnSave);
        final EditText edtFirstWarning = UIUtils.getView(popupView, R.id.edtWarningFirst);
        final EditText edtLastWarning = UIUtils.getView(popupView, R.id.edtWarningLast);
        TextView tvHelpWarnFormat = UIUtils.getViewT(popupView, R.id.tvHelpWarnFormat);

        mCurrentPopupWindow = popupWindow;

        final RadioGroup radioGroup = UIUtils.getView(popupView, R.id.rgWarnFrequency);
        final ChatTask task = chatTasks.getTask(pType);

        edtFirstWarning.setText(task.mWarnTextFirst);
        edtLastWarning.setText(task.mWarnTextLast);

        if (pType == ChatTask.TYPE.FLOOD) {
            popupView.findViewById(R.id.rbWarnFreqEvery).setVisibility(View.GONE);
            popupView.findViewById(R.id.rbWarnFreqFirstMsg).setVisibility(View.GONE);
            popupView.findViewById(R.id.rbWarnFreqFirstLast).setVisibility(View.GONE);
            popupView.findViewById(R.id.rbWarnFreqSecondLast).setVisibility(View.GONE);
            if (task.mWarnFrequency == 3) //Flood control have only "last message warn" and "dont warn"
                task.mWarnFrequency = 1;
        }

        int checkedRbID = 0;
        switch (task.mWarnFrequency) {
            case 0:
                checkedRbID = R.id.rbWarnFreqNone;
                UIUtils.setBatchVisibility(View.GONE, edtFirstWarning, edtLastWarning);
                break;
            case 1:
                checkedRbID = R.id.rbWarnFreqLastMsg;
                edtFirstWarning.setVisibility(View.GONE);
                edtLastWarning.setVisibility(View.VISIBLE);
                break;
            case 2:
                checkedRbID = R.id.rbWarnFreqFirstMsg;
                edtFirstWarning.setVisibility(View.GONE);
                edtLastWarning.setVisibility(View.VISIBLE);
                break;
            case 3:
                checkedRbID = R.id.rbWarnFreqFirstLast;
                edtFirstWarning.setVisibility(View.VISIBLE);
                edtLastWarning.setVisibility(View.VISIBLE);
                break;
            case 4:
                checkedRbID = R.id.rbWarnFreqSecondLast;
                edtFirstWarning.setVisibility(View.VISIBLE);
                edtLastWarning.setVisibility(View.VISIBLE);
                break;
            case 5:
                checkedRbID = R.id.rbWarnFreqEvery;
                edtFirstWarning.setVisibility(View.VISIBLE);
                edtLastWarning.setVisibility(View.GONE);
                break;
        }
        RadioButton rbChecked = UIUtils.getView(popupView, checkedRbID);
        rbChecked.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbWarnFreqNone:
                        UIUtils.setBatchVisibility(View.GONE, edtFirstWarning, edtLastWarning);
                        break;
                    case R.id.rbWarnFreqLastMsg:
                        edtFirstWarning.setVisibility(View.GONE);
                        edtLastWarning.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbWarnFreqFirstMsg:
                        edtFirstWarning.setVisibility(View.GONE);
                        edtLastWarning.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbWarnFreqFirstLast:
                        edtFirstWarning.setVisibility(View.VISIBLE);
                        edtLastWarning.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbWarnFreqSecondLast:
                        edtFirstWarning.setVisibility(View.VISIBLE);
                        edtLastWarning.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbWarnFreqEvery:
                        edtFirstWarning.setVisibility(View.VISIBLE);
                        edtLastWarning.setVisibility(View.GONE);
                        break;
                }
            }
        });

        popupWindow.showAtLocation(tvPressedTextView, Gravity.CENTER, 0, 0);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mCurrentPopupWindow = null;
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.update();

        setEditWarnTextWatcher(edtFirstWarning, pType, true);
        setEditWarnTextWatcher(edtLastWarning, pType, false);

        View.OnClickListener onclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnClose:
                        popupWindow.dismiss();
                        break;
                    case R.id.btnSave:
                        int resSelectedRadionID = radioGroup.getCheckedRadioButtonId();
                        switch (resSelectedRadionID) {
                            case R.id.rbWarnFreqNone:
                                saveWarnFrequencyTask(pType, 0);
                                break;
                            case R.id.rbWarnFreqLastMsg:
                                saveWarnFrequencyTask(pType, 1);
                                break;
                            case R.id.rbWarnFreqFirstMsg:
                                saveWarnFrequencyTask(pType, 2);
                                break;
                            case R.id.rbWarnFreqFirstLast:
                                saveWarnFrequencyTask(pType, 3);
                                break;
                            case R.id.rbWarnFreqSecondLast:
                                saveWarnFrequencyTask(pType, 4);
                                break;
                            case R.id.rbWarnFreqEvery:
                                saveWarnFrequencyTask(pType, 5);
                                break;
                        }

                        if (resSelectedRadionID > -1) {
                            RadioButton rbSelected = UIUtils.getView(radioGroup, resSelectedRadionID);
                            tvPressedTextView.setText(rbSelected.getText());
                        }
                        popupWindow.dismiss();

                        break;
                    case R.id.tvHelpWarnFormat:
                        View view = UIUtils.inflate(mContext, R.layout.layout_dialog_help_warning_formattin);
                        TextView tvHelpText = UIUtils.getView(view, R.id.tvHelpText);

                        String text = getString(R.string.text_help_formatting);

                        tvHelpText.setText(Html.fromHtml(text.replaceAll("(\r\n|\n)", "<br />")));

                        new AlertDialog.Builder(mContext)
                                .setTitle("Formattin help")
                                .setView(view)
                                //"\nFormatting:\n" +
                                //"You can user tilda ` symbol for highlite text")
                                .setPositiveButton("Ok", null)
                                .show();
                        break;

                }
            }
        };
        UIUtils.setBatchClickListener(onclick, btnSave, btnClose, tvHelpWarnFormat);
    }

    void setEditWarnTextWatcher(final EditText editText, final ChatTask.TYPE pType, final boolean isFirstWarn) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setTag(System.currentTimeMillis()); //TODO ingore when writing default value? не нужно
            }

            @Override
            public void afterTextChanged(final Editable s) {
                editText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        long ts = Long.valueOf(editText.getTag().toString());
                        if (System.currentTimeMillis() - ts < 1400) return;

                        String text = s.toString().trim();
                        ChatTask task = chatTasks.getTask(pType);
                        if (isFirstWarn)
                            task.mWarnTextFirst = text;
                        else
                            task.mWarnTextLast = text;
                        DBHelper.getInstance().saveWarnText(text, isFirstWarn, task.id);
                        MyLog.log("save warn text for task id " + task.id + " text: " + text + " isFirst: " + isFirstWarn);
                        editText.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);

                    }
                }, 1500);
                editText.getBackground().setColorFilter(getResources().getColor(R.color.md_teal_500), PorterDuff.Mode.SRC_ATOP);
            }
        });

    }

    private void saveWarnFrequencyTask(ChatTask.TYPE pType, int freqType) {
        ChatTask task = chatTasks.getTask(pType);
        task.mWarnFrequency = freqType;
        chatTasks.saveTask(task);
    }


    private void setTaskWarnCountDialog(ChatTask.TYPE pType, @Nullable final SeekBar skToChange, final TextView tvValue) {
        final ChatTask task = chatTasks.getTask(pType);
        new DialogInputValue(mContext)
                .setValue(task.mAllowCountPerUser)
                .onApply(new DialogInputValue.SetValueCallback() {
                    @Override
                    public void onSetValue(int value) {
                        tvValue.setText(value + "");
                        task.mAllowCountPerUser = value;
                        if (skToChange != null)
                            skToChange.setProgress(value);
                        chatTasks.saveTask(task);
                    }
                })
                .showDialog();
    }


//    private void saveAntispamRule(AntiSpamRule antiSpamRule) {
//        DBHelper.getInstance().setAntiSpamRule(chatId, antiSpamRule);
//        if (antiSpamRule.isBanForLinks || antiSpamRule.isBanForStickers || antiSpamRule.isRemoveLinks || antiSpamRule.isRemoveStickers)
//            ServiceAntispam.start(mContext);
//    }

    private void popupInviteLink() {
        PopupMenu popup = new PopupMenu(mContext, tvInviteLink);
        Menu menu = popup.getMenu();
        if (chatInviteLink == null || chatInviteLink.isEmpty()) {
            menu.add(0, 1, 0, R.string.generate_invite_link);
        } else {
            menu.add(0, 1, 0, R.string.generate_new_invite_link);
            menu.add(0, 2, 0, R.string.action_copy_link);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        if (chatInviteLink == null || chatInviteLink.isEmpty()) {
                            generateInviteLink();
                        } else {
                            dialogConfirmRevokeInviteLink();
                        }
                        break;
                    case 2:
                        Utils.copyToClipboard(chatInviteLink, mContext);
                        MyToast.toast(mContext, R.string.toast_copied_to_clipboard);
                        break;
                }
                return false;
            }
        });

        popup.show();
    }

    void dialogConfirmRevokeInviteLink() {
        new AlertDialog.Builder(mContext)
                .setTitle("Confirm")
                .setMessage(R.string.message_confirm_revoke_link)
                .setNegativeButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.btnContinue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        generateInviteLink();
                    }
                })
                .show();
    }

    void generateInviteLink() {
        TdApi.TLFunction f = new TdApi.ExportChatInviteLink(chatId);
        TgH.sendOnUi(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (TgUtils.isError(object)) {
                    MyToast.toast(mContext, object.toString());
                } else {
                    TdApi.ChatInviteLink inviteLink = (TdApi.ChatInviteLink) object;
                    chatInviteLink = inviteLink.inviteLink;
                    tvInviteLink.setText(chatInviteLink);
                    MyToast.toast(mContext, "Invite link created");
                }
            }
        });
    }



    /*
    private AntiSpamRule getAntispamRule() {
        AntiSpamRule antiSpamRule = DBHelper.getInstance().getAntispamRule(chatId);
        if (antiSpamRule == null) antiSpamRule = new AntiSpamRule();
        return antiSpamRule;
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, R.string.changeChatAbout);
        if (isAdmin)
            menu.add(0, 3, 0, R.string.actionDeleteGroup);

        if (!isChannel)
            menu.add(0, 2, 0, R.string.action_reset_warns);


        MenuItem item = menu.add(0, 4, 0, R.string.title_add_new_task_for_chat);
        item.setIcon(R.drawable.ic_note_add_white_24dp);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        item.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                dialogEditChatDescription(null);
                break;
            case 2:
                DBHelper.getInstance().resetWarnCountForChat(chatId);
                MyToast.toast(mContext, "Reset done");
                break;
            case 3:
                dialogConfirmDeleteGroup();
                break;

            case 4:
                //addTask(ChatTask.TYPE.STICKERS, true);
                new DialogAddTask().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DialogAddTask {
        class TypeItem {
            ChatTask.TYPE mType;
            boolean isEnabled = true;
            String mTitle;

            public TypeItem(ChatTask.TYPE pType) {
                mType = pType;
                isEnabled = chatTasks.getTask(pType, false) == null;
                String title = TaskValues.getTitleForAddTask(pType);

                mTitle = title;// "Add task for " + title;
            }
        }

        private AlertDialog dialog;

        // Адаптер
        class TypesSimpleAdapter extends ArrayAdapter<TypeItem> {
            LayoutInflater inflater;

            public TypesSimpleAdapter(List<TypeItem> listItems) {
                super(mContext, android.R.layout.simple_list_item_1, listItems);
                inflater = ((Activity) mContext).getLayoutInflater();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                boolean created = convertView == null;
                if (created)
                    convertView = inflater.inflate(R.layout.item_task_list, parent, false);
                TypeItem item = getItem(position);

                final TextView tv = UIUtils.getHolderView(convertView, R.id.tvItemTitle);
                TextView tvAdded = UIUtils.getHolderView(convertView, R.id.tvTaskAdded);
                tv.setText(item.mTitle);
                tvAdded.setVisibility(item.isEnabled ? View.GONE : View.VISIBLE);
                UIUtils.setTextColotRes(tv, item.isEnabled ? R.color.md_teal_500 : R.color.md_grey_500);

                return convertView;
            }
        }

        void show() {
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            int padding = UIUtils.getPixelsFromDp(16);
            layout.setPadding(padding, 0, padding, 0);

            TextView tvText = new TextView(mContext);
            tvText.setText(R.string.title_select_task_for_chat);
            tvText.setPadding(UIUtils.getPixelsFromDp(8), 0, 0, 0);
            layout.addView(tvText);

            ListView lv = new ListView(mContext);
            layout.addView(lv);

            final ArrayList<TypeItem> list = new ArrayList<>();
            list.add(new TypeItem(ChatTask.TYPE.LINKS));
            list.add(new TypeItem(ChatTask.TYPE.STICKERS));
            list.add(new TypeItem(ChatTask.TYPE.VOICE));
            list.add(new TypeItem(ChatTask.TYPE.IMAGES));
            list.add(new TypeItem(ChatTask.TYPE.GIF));
            list.add(new TypeItem(ChatTask.TYPE.AUDIO));
            list.add(new TypeItem(ChatTask.TYPE.VIDEO));
            list.add(new TypeItem(ChatTask.TYPE.DOCS));
            list.add(new TypeItem(ChatTask.TYPE.GAME));

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext)
                    .setView(layout)
                    .setTitle(R.string.title_add_new_task_for_chat)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            list.clear();
                        }
                    });

            final TypesSimpleAdapter adapter = new TypesSimpleAdapter(list);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TypeItem item = adapter.getItem(position);
                    if (!item.isEnabled) {
                        MyToast.toast(mContext, R.string.toast_task_already_applied);
                    } else {
                        item.isEnabled = false;
                        addTask(item.mType, true);
                        // adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                }
            });

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final TypeItem item = adapter.getItem(position);
                    if (item.isEnabled)
                        return false;
                    new AlertDialog.Builder(mContext)
                            .setTitle(R.string.title_remove_taskfrom_added)
                            .setMessage(R.string.text_confirm_remove_task)
                            .setPositiveButton(R.string.btnYes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ChatTask task = chatTasks.getTask(item.mType, false);
                                    ViewGroup view = (ViewGroup) task.payload;
                                    ViewGroup vg = (ViewGroup) view.getParent();
                                    vg.removeView(view);

                                    chatTasks.remove(task);
                                    item.isEnabled = true;
                                    DBHelper.getInstance().removeChatTask(task.id);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton(R.string.btnCancel, null)
                            .show();
                    return true;
                }
            });

            dialog = dialogBuilder.show();
        }

    }

    void addTask(ChatTask.TYPE pType, boolean canCreateNewTask) {

        ChatTask task = chatTasks.getTask(pType, false);
        if (task == null && !canCreateNewTask)
            return;

        String title = null, titleRemoveMessages = null, titleBanCheck = null;

        switch (pType) {
            //NOTE add new type here when new type implemented
            case STICKERS:
                title = getString(R.string.title_task_stickers);
                titleRemoveMessages = getString(R.string.chk_remove_messages_stickers);
                titleBanCheck = getString(R.string.chkTitleBanForSticks);
                break;
            case VOICE:
                title = getString(R.string.title_task_voices);
                titleRemoveMessages = getString(R.string.chk_remove_messages_voice);
                titleBanCheck = getString(R.string.chkTitleBanForVoice);
                break;
            case LINKS:
                title = getString(R.string.title_task_links);
                titleRemoveMessages = getString(R.string.chk_remove_messages_links);
                titleBanCheck = getString(R.string.chtTitleBanForLinks);
                break;
            case GAME:
                title = getString(R.string.title_task_game);
                titleRemoveMessages = getString(R.string.chk_remove_messages_game);
                titleBanCheck = getString(R.string.chkTitleBanForGames);
                break;
            case IMAGES:
                title = getString(R.string.title_task_photos);
                titleRemoveMessages = getString(R.string.chk_remove_messages_photos);
                titleBanCheck = getString(R.string.chkTitleBanForPhotos);
                break;
            case DOCS:
                title = getString(R.string.title_task_docs);
                titleRemoveMessages = getString(R.string.chk_remove_messages_docs);
                titleBanCheck = getString(R.string.chkTitleBanForDocs);
                break;
            case GIF:
                title = getString(R.string.title_task_gif);
                titleRemoveMessages = getString(R.string.chk_remove_messages_gif);
                titleBanCheck = getString(R.string.chkTitleBanForGif);
                break;
            case AUDIO:
                title = getString(R.string.title_task_audio);
                titleRemoveMessages = getString(R.string.chk_remove_messages_audio);
                titleBanCheck = getString(R.string.chkTitleBanForAudio);
                break;
            case VIDEO:
                title = getString(R.string.title_task_video);
                titleRemoveMessages = getString(R.string.chk_remove_messages_video);
                titleBanCheck = getString(R.string.chkTitleBanForVideo);
                break;

        }

        final LinearLayout layout = getView(R.id.layerTasks);

        final View view = UIUtils.inflate(mContext, R.layout.layout_base_task);
        CheckBox chkRemoveMessage = UIUtils.getView(view, R.id.chkRemoveMessage);
        CheckBox chkBanForMessage = UIUtils.getView(view, R.id.chkBanForMessage);
        CheckBox chkReturnOnBanExpired = UIUtils.getView(view, R.id.chkReturnOnBanExpired);
        Spinner spinnerBanTime = UIUtils.getView(view, R.id.spinnerBanTime);
        EditText edtBanTimeVal = UIUtils.getView(view, R.id.edtBanTimeVal);
        EditText edtBanFloodTimeVal = UIUtils.getView(view, R.id.edtBanFloodTimeVal);
        Spinner spinnerBanFloodTime = UIUtils.getView(view, R.id.spinnerBanFloodTime);
        TextView tvBtnWarnFreq = UIUtils.getView(view, R.id.tvBtnWarnFreq);
        TextView tvTypeTitle = UIUtils.getView(view, R.id.tvTypeTitle);
        LinearLayout layerBanParams = UIUtils.getView(view, R.id.layerBanParams);
        SeekBar seekBarAllowCount = UIUtils.getView(view, R.id.seekBarAllowCount);
        TextView tvBanAllowCount = UIUtils.getView(view, R.id.tvBanAllowCount);
        CheckBox chkPublicBan = UIUtils.getView(view, R.id.chkPublicBan);

        if (TgUtils.isGroup(chatType))
            chkRemoveMessage.setTextColor(getResources().getColor(R.color.md_grey_500));

        tvTypeTitle.setText(title);
        chkRemoveMessage.setText(titleRemoveMessages);
        chkBanForMessage.setText(titleBanCheck);

        chkRemoveMessage.setTag(R.id.tag_id_type, pType);
        chkBanForMessage.setTag(R.id.tag_id_type, pType);
        chkReturnOnBanExpired.setTag(R.id.tag_id_type, pType);
        chkBanForMessage.setTag(R.id.tag_id_view, layerBanParams);
        tvBtnWarnFreq.setTag(R.id.tag_id_type, pType);
        chkPublicBan.setTag(R.id.tag_id_type, pType);

        seekBarAllowCount.setTag(R.id.tag_id_type, pType);
        seekBarAllowCount.setTag(R.id.tag_id_view, tvBanAllowCount);
        seekBarAllowCount.setOnSeekBarChangeListener(onSeekBarBanCountListener);
        tvBanAllowCount.setTag(R.id.tag_id_type, pType);
        tvBanAllowCount.setTag(R.id.tag_id_view, seekBarAllowCount);

        UIUtils.setBatchClickListener(onClickListener, chkBanForMessage, chkRemoveMessage, tvBtnWarnFreq,
                chkReturnOnBanExpired, tvBanAllowCount, chkPublicBan);

        setSpinnerBanTimesListener(pType, spinnerBanTime, edtBanTimeVal, chkReturnOnBanExpired);
        setSpinnerFloodSelectorListener(pType, spinnerBanFloodTime, edtBanFloodTimeVal);
        setEditTextListenerToBanTimeValueNew(pType, edtBanTimeVal, chkReturnOnBanExpired, spinnerBanTime);

        if (task != null) {
            chkRemoveMessage.setChecked(task.isRemoveMessage);
            chkBanForMessage.setChecked(task.isBanUser);
            if (!task.isBanUser)
                layerBanParams.setVisibility(View.GONE);
            chkReturnOnBanExpired.setChecked(task.isReturnOnBanExpired);
            tvBanAllowCount.setText(task.mAllowCountPerUser + "");
            setWarnFrequencyText(tvBtnWarnFreq, task.mWarnFrequency);
            seekBarAllowCount.setProgress(task.mAllowCountPerUser);
            chkPublicBan.setChecked(task.isPublicToChat);

            if (task.mBanTimeSec > 0)
                setSecondsFormatToSpinner(task.mBanTimeSec, edtBanTimeVal, spinnerBanTime);
            else { // forever ban. permanent
                edtBanTimeVal.setTag("1");
                edtBanTimeVal.setText("5");
                chkReturnOnBanExpired.setEnabled(false);
                edtBanTimeVal.setEnabled(false);
                spinnerBanTime.setTag(1);
                spinnerBanTime.setSelection(0);
            }

            if (task.mWarningsDuringTime == 0) {
                edtBanFloodTimeVal.setTag("1");// flag for ignore save on change to default value
                edtBanFloodTimeVal.setText("5");
                spinnerBanFloodTime.setTag(1);// flag for skip save changes
                spinnerBanFloodTime.setSelection(0);
                edtBanFloodTimeVal.setEnabled(false);
            } else {
                setSecondsFormatToSpinner(task.mWarningsDuringTime, edtBanFloodTimeVal, spinnerBanFloodTime);
            }

        } else {
            //set default values
            layerBanParams.setVisibility(View.GONE);
            seekBarAllowCount.setProgress(3);
            tvBanAllowCount.setText("3");

            //edtBanTimeValue.setEnabled(true);
            //layerBanTimes.setVisibility(View.GONE);
            spinnerBanTime.setTag(1);
            spinnerBanTime.setSelection(1);
            spinnerBanFloodTime.setTag(1);
            spinnerBanFloodTime.setSelection(1);
            chkReturnOnBanExpired.setChecked(true);
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 1 /*px*/, 0, UIUtils.getPixelsFromDp(8));
        layout.addView(view, lp);

        if (task == null && canCreateNewTask) {
            task = chatTasks.getTask(pType, true); // create task instance


            ScrollView scrollViewMainContent = getView(R.id.scrollViewMainContent);
            LinearLayout layerTasksParent = getView(R.id.layerTasksParent);
            scrollViewMainContent.smoothScrollTo(0, layout.getBottom() + layerTasksParent.getTop());

            int fromColor = getResources().getColor(R.color.md_green_200);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, Color.WHITE);
            colorAnimation.setDuration(1000); // milliseconds
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    view.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.start();
        }
        task.payload = view;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (fabButton.getVisibility() == View.VISIBLE && !isChannel)
            menu.findItem(4).setVisible(true); // visible for action Add task
        return super.onPrepareOptionsMenu(menu);
    }

    /*
    ===== methods
     */

    void dialogRenameChatTitle() {
        View v = UIUtils.inflate(mContext, R.layout.dialog_chat_title);
        final EmojiconEditText editText = UIUtils.getView(v, R.id.editInputTitle);
        editText.setText(chatTitle);
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.chat_title)
                .setView(v)
                .setNegativeButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.action_rename, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String newTitle = editText.getText().toString().trim();
                        if (newTitle.isEmpty()) {
                            MyToast.toast(mContext, R.string.error_empty_chat_title);
                            dialogRenameChatTitle();
                            return;
                        }
                        TdApi.TLFunction f = new TdApi.ChangeChatTitle(chatId, newTitle);
                        TgH.sendOnUi(f, new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (TgUtils.isError(object))
                                    MyToast.toast(mContext, "Error:\n" + object.toString());
                                else {
                                    chatTitle = newTitle;
                                    tvTitle.setText(newTitle);
                                }
                            }
                        });
                    }
                })
                .show();
    }

    void dialogConfirmDeleteGroup() {
        LinearLayout view = new LinearLayout(mContext);
        view.setOrientation(LinearLayout.VERTICAL);
        TextView t = new TextView(this);
        t.setText(getString(R.string.text_confirm_deletion_group) + "\n" + chatTitle);
        view.setPadding(16, 8, 16, 8);
        view.addView(t);
        final CheckBox c = new CheckBox(this);
        c.setText(R.string.text_delete_group);
        view.addView(c);
        final AlertDialog d = new AlertDialog.Builder(mContext)
                .setView(view)
                .setTitle(R.string.title_delete_group)
                .setPositiveButton(R.string.btnCancel, null)
                .setNegativeButton(R.string.button_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogConfirmDelete2();
                    }
                })
                .show();
        d.getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(false);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean enabled = c.isChecked();
                d.getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(enabled);
            }
        });
    }

    private void dialogConfirmDelete2() {
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.title_delete_group)
                .setMessage(getString(R.string.text_confirm_deletion_group))
                .setPositiveButton(R.string.btnCancel, null)
                .setNegativeButton(R.string.button_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TdApi.TLFunction f;
                        if (TgUtils.isGroup(chatType)) {
                            f = new TdApi.DeleteChatHistory(chatId, false);
                            final TdApi.TLFunction ff = new TdApi.ChangeChatMemberStatus(chatId, TgH.selfProfileId, new TdApi.ChatMemberStatusLeft());
                            TgH.send(ff);
                        } else {
                            f = new TdApi.DeleteChannel(channelId);
                        }
                        TgH.send(f, new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                MyToast.toast(mContext, R.string.toast_group_deleted);
                                DBHelper.getInstance().deleteGroup(chatId);
                                finish();
                            }
                        });
                    }
                })
                .show();
    }

    private void dialogEditChatDescription(@Nullable String defTitle) {
        View v = UIUtils.inflate(mContext, R.layout.dialog_chat_edit_about);
        final EmojiconEditText editInputDescription = UIUtils.getView(v, R.id.editInputDescription);
        editInputDescription.setText(chatDescription);
        if (defTitle != null)
            editInputDescription.setText(defTitle);
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.titleEditChatDescription)
                .setView(v)
                .setNegativeButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String newChatDescription = editInputDescription.getText().toString().trim();
                        TdApi.TLFunction f = new TdApi.ChangeChannelAbout(channelId, newChatDescription);
                        TgH.sendOnUi(f, new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (TgUtils.isError(object)) {
                                    MyToast.toast(mContext, "Error:\n" + object.toString());
                                    dialogSetUsername(newChatDescription);
                                } else {
                                    chatDescription = newChatDescription;
                                    tvChatDescription.setText(newChatDescription);
                                }
                            }
                        });
                    }
                })
                .show();
    }

    private void getChatInfo() {
        TdApi.TLFunction f = new TdApi.GetChat(chatId);
        TgH.sendOnUi(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (TgUtils.isError(object)) {
                    return;
                }
                TdApi.Chat chat = (TdApi.Chat) object;
                TgImageGetter tgImageGetter = new TgImageGetter();
                if (chat.photo != null)
                    tgImageGetter.setImageToView(btnAva, chat.photo.small);
            }
        });

        if (TgUtils.isGroup(chatType)) {
            getGroupInfo();
        } else {
            getSuperGroupInfo();
        }
    }

    private void getGroupInfo() {
        TdApi.TLFunction f = new TdApi.GetGroupFull(groupId);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (TgUtils.isError(object)) {
                    TdApi.Error error = (TdApi.Error) object;
                    if (error.code == 6 && !isFinishing()) {
                        Utils.sleep(2000); // some sleep and try again, maybe chats will loading later
                        getGroupInfo();
                    } else {
                        MyToast.toast(mContext, "Error: " + error.message);
                        onUiThread(hideLoading);
                    }
                    return;
                }

                TdApi.GroupFull groupFull = (TdApi.GroupFull) object;
                chatUsersCount = groupFull.group.memberCount;
                isAdmin = groupFull.group.status.getConstructor() == TdApi.ChatMemberStatusCreator.CONSTRUCTOR;
                adminId = groupFull.creatorUserId;
                adminsCount = 0;
                chatInviteLink = groupFull.inviteLink;
                groupAnyoneCanEdit = groupFull.group.anyoneCanEdit;
                for (TdApi.ChatMember member : groupFull.members) {
                    if (member.userId == adminId) {
                        if (TgUtils.isUserPrivileged(member.status.getConstructor()))
                            adminsCount++;
                        TdApi.User user = TgUtils.getUser(adminId);
                        adminName = user.firstName + " " + user.lastName;
                    }
                }

                onUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onGroupInfoLoaded();
                        getBotForChat();
                    }
                });
            }
        });
    }

    void switchAnyoneCanInviteSuperGroup() {
        if (!isAdmin) {
            MyToast.toast(mContext, R.string.toast_access_denied_admin);
            chkAnyoneInviteFriendsSuper.setChecked(superAanyoneCanInvite);
            return;
        }
        final ProgressBar prgChangeState = getView(R.id.prgChangeState);
        prgChangeState.setVisibility(View.VISIBLE);
        chkAnyoneInviteFriendsSuper.setEnabled(false);

        boolean canInvite = chkAnyoneInviteFriendsSuper.isChecked();
        TdApi.TLFunction f = new TdApi.ToggleChannelInvites(channelId, canInvite);
        TgH.sendOnUi(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.Channel.CONSTRUCTOR) {
                    TdApi.Channel channel = (TdApi.Channel) object;
                    boolean canInvite = channel.anyoneCanInvite;
                    MyToast.toast(mContext, "Anyone can invite: " + (canInvite ? "True" : "False"));
                } else
                    MyToast.toast(mContext, object);
                prgChangeState.setVisibility(View.GONE);
                chkAnyoneInviteFriendsSuper.setEnabled(true);
                //p.dismiss();
            }
        });
    }

    void switchAnyoneManageGroup() {
        if (!isAdmin) {
            MyToast.toast(mContext, R.string.toast_access_denied_admin);
            return;
        }

        final ProgressBar prgChangeState = getView(R.id.prgChangeState);
        prgChangeState.setVisibility(View.VISIBLE);
        chkAnyoneManageGroup.setEnabled(false);

        boolean canInvite = chkAnyoneManageGroup.isChecked();
        TdApi.TLFunction f = new TdApi.ToggleGroupEditors(groupId, canInvite);
        TgH.sendOnUi(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.Group.CONSTRUCTOR)
                    MyToast.toast(mContext, "Changes saved");
                else
                    MyToast.toast(mContext, "Error:\n" + object.toString());
                //p.dismiss();
                prgChangeState.setVisibility(View.GONE);
                chkAnyoneManageGroup.setEnabled(true);
            }
        });
    }

    Runnable hideLoading = new Runnable() {
        @Override
        public void run() {
            viewLoading.setVisibility(View.GONE);
        }
    };

    private void getSuperGroupInfo() {
        TdApi.TLFunction f = new TdApi.GetChannelFull(channelId);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(final TdApi.TLObject object) {
                // MyLog.log(object.toString());
                if (TgUtils.isError(object)) {
                    TdApi.Error error = (TdApi.Error) object;
                    if (error.code == 6 && !isFinishing()) {
                        Utils.sleep(2000); // some sleep and try again, maybe chats will loading later
                        getSuperGroupInfo();
                    } else {
                        MyToast.toast(mContext, "Error: " + error.message);
                        onUiThread(hideLoading);
                    }
                    return;
                }

                onUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TdApi.ChannelFull channelFull = (TdApi.ChannelFull) object;
                        chatInviteLink = channelFull.inviteLink;
                        chatDescription = channelFull.about;
                        chatUsersCount = channelFull.memberCount;
                        adminsCount = channelFull.administratorCount;
                        kickedCount = channelFull.kickedCount;
                        chatUsername = channelFull.channel.username;
                        isAdmin = channelFull.channel.status.getConstructor() == TdApi.ChatMemberStatusCreator.CONSTRUCTOR;
                        superAanyoneCanInvite = channelFull.channel.anyoneCanInvite;
                        if (isAdmin) {
                            adminId = TgH.selfProfileId;
                            adminName = "@" + TgH.selfProfileUsername;
                            onSuperGroupInfoLoaded();
                        } else
                            getSuperAdmins();

                        if (!channelFull.channel.isSupergroup) {// is channel
                            isChannel = true;
                            findViewById(R.id.layerTasksParent).setVisibility(View.GONE);
                            findViewById(R.id.viewOther).setVisibility(View.GONE);
                            findViewById(R.id.viewWelcomeText).setVisibility(View.GONE);
                            findViewById(R.id.viewCommands).setVisibility(View.GONE);
                            findViewById(R.id.layerLog).setVisibility(View.GONE);
                            findViewById(R.id.layerBot).setVisibility(View.GONE);
                            fabButton.hideFloatingActionButton();

                            tvKickedCount.setVisibility(View.GONE);
                            chkAnyoneInviteFriendsSuper.setVisibility(View.GONE);
                            setTitle(R.string.action_manage_channel);
                            invalidateOptionsMenu();
                            tvChatType.setText(R.string.chatTypeChannel);
                        } else {
                            tvChatType.setText(R.string.chatTypeSuperGroup);
                            getBotForChat();
                        }
                    }
                });
            }
        });
    }

    private void getSuperAdmins() {
        TdApi.TLFunction f = new TdApi.GetChannelMembers(channelId, new TdApi.ChannelMembersAdministrators(), 0, 100);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.ChatMembers.CONSTRUCTOR) {
                    TdApi.ChatMembers users = (TdApi.ChatMembers) object;
                    for (final TdApi.ChatMember chatMember : users.members) {
                        if (chatMember.status.getConstructor() == TdApi.ChatMemberStatusCreator.CONSTRUCTOR) {
                            adminId = chatMember.userId;

                            TgH.send(new TdApi.GetChatMember(channelId, chatMember.userId), new Client.ResultHandler() {
                                @Override
                                public void onResult(TdApi.TLObject object) {
                                    TdApi.User admin = TgUtils.getUser(chatMember);
                                    adminName = admin.firstName + " " + admin.lastName;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            onSuperGroupInfoLoaded();
                                        }
                                    });
                                }
                            });
                            return;
                        }
                    }
                } else {
                    adminName = "";
                    onUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onSuperGroupInfoLoaded();
                        }
                    });
                }
            }
        });
    }

    private void onGroupInfoLoaded() {
        tvChatDescription.setVisibility(View.GONE);
        if (chatInviteLink.isEmpty())
            tvInviteLink.setText(R.string.hint_invite_link_not_created);
        else
            tvInviteLink.setText(chatInviteLink);


        tvUsersCount.setText(getString(R.string.participiantsCount) + " " + chatUsersCount);
        tvKickedCount.setText(getString(R.string.kickedCount) + " " + DBHelper.getInstance().getBannedCount(chatId));

        tvAdminsCount.setText(getString(R.string.adminsCount) + " " + adminsCount);

        viewLoading.setVisibility(View.GONE);
        viewContent.setVisibility(View.VISIBLE);
        tvChatAdmin.setText(adminName);
        chkAnyoneManageGroup.setChecked(groupAnyoneCanEdit);
        if (!isChannel)
            fabButton.setVisibility(View.VISIBLE);
        invalidateOptionsMenu();
        loadChatLogInfo();
    }


    private void onSuperGroupInfoLoaded() {
        tvUsersCount.setText(getString(R.string.participiantsCount) + " " + chatUsersCount);
        tvKickedCount.setText(getString(R.string.kickedCount) + " " + kickedCount);
        tvAdminsCount.setText(getString(R.string.adminsCount) + " " + adminsCount);
        tvChatDescription.setText(chatDescription);
        chkAnyoneInviteFriendsSuper.setChecked(superAanyoneCanInvite);
        if (chatDescription.isEmpty()) {
            tvChatDescription.setText(R.string.hint_no_chat_about);
            UIUtils.setTextColotRes(tvChatDescription, R.color.md_grey_500);
        }

        if (chatInviteLink.isEmpty())
            tvInviteLink.setText(R.string.hint_invite_link_not_created);
        else
            tvInviteLink.setText(chatInviteLink);

        if (chatUsername.isEmpty())
            tvChatUsername.setText(R.string.hint_short_link_not_created);
        else
            tvChatUsername.setText("@" + chatUsername);

        viewLoading.setVisibility(View.GONE);
        viewContent.setVisibility(View.VISIBLE);
        tvChatAdmin.setText(adminName);
        if (!isChannel)
            fabButton.setVisibility(View.VISIBLE);
        invalidateOptionsMenu();
        loadChatLogInfo();
    }

    void loadChatLogInfo() {
        final ChatLogInfo chatLogInfo = DBHelper.getInstance().getLogEventsToChat(chatId, false);
        if (chatLogInfo == null) {
            //tvSelectLogEventsToChat.setText("Tap to configure exporting log to chat");
            return;
        }

        tvSelectLogEventsToChat.setText("Loading chat info...");
        chkEnableLogToChat.setChecked(chatLogInfo.isEnabled);
        if (!chatLogInfo.isEnabled)
            UIUtils.setTextColotRes(tvSelectLogEventsToChat, R.color.md_grey_500);

        TgH.sendOnUi(new TdApi.GetChat(chatLogInfo.chatLogID), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (TgUtils.isError(object)) {
                    tvSelectLogEventsToChat.setText("Error loading chat info");
                    return;
                }

                TdApi.Chat chat = (TdApi.Chat) object;
                tvSelectLogEventsToChat.setText("Forwarding log to chat:\n" + chat.title);
            }
        });

    }

    void dialogSetUsername(@Nullable final String defName) {
        View v = UIUtils.inflate(mContext, R.layout.dialog_set_username);
        final EditText edtInputUsername = UIUtils.getView(v, R.id.edtInputUsername);
        final TextView tvValueHint = UIUtils.getView(v, R.id.tvValueHint);
        if (defName != null) edtInputUsername.setText(defName);
        tvValueHint.setText("");
        AlertDialog d = new AlertDialog.Builder(this)
                .setView(v)
                .setTitle(R.string.dialog_title_edit_link)
                .setNegativeButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String s = edtInputUsername.getText().toString().trim().replace("@", "");
                        TdApi.TLFunction f = new TdApi.ChangeChannelUsername(channelId, s);
                        TgH.TG().send(f, new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                if (!TgUtils.isError(object)) {
                                    tvChatUsername.setText(s);
                                    MyToast.toast(mContext, R.string.toast_chat_link_added);
                                } else {
                                    TdApi.Error er = (TdApi.Error) object;
                                    if (er.code == 400) {
                                        if (er.message.equals("CHANNELS_ADMIN_PUBLIC_TOO_MUCH")) {
                                            MyToast.toast(mContext, "Error code 400:\n" + getString(R.string.error_too_many_public_links));
                                            return;
                                        }
                                        if (er.message.equals("USERNAME_OCCUPIED")) {
                                            MyToast.toastL(mContext, getString(R.string.error_link_already_occupied));
                                            dialogSetUsername(s);
                                            return;
                                        }
                                    }
                                    MyToast.toast(mContext, "Error code " + er.code + "\n" + er.message);
                                    dialogSetUsername(s);
                                }
                            }
                        });
                    }
                })
                .show();
        final Button btnPositive = d.getButton(DialogInterface.BUTTON_POSITIVE);

        edtInputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (s.length() < 5) {
                    btnPositive.setEnabled(false);
                    tvValueHint.setText("Minimum length: 5 characters");
                } else if (str.contains(" ") || str.contains(".") || str.contains("-")) {
                    tvValueHint.setText("Available characters: 'a-z' '0-9' and '_' ");
                    btnPositive.setEnabled(false);
                } else if (Utils.isNumeric(str.substring(0, 1))) {
                    tvValueHint.setText("Link can't starts with numeric");
                    btnPositive.setEnabled(false);
                } else {
                    tvValueHint.setText("");
                    btnPositive.setEnabled(true);
                }
            }
        });
    }

    void confirmConvertToSuperGroup() {
        String msg = getString(R.string.hint_upgrade_to_supergroup);

        new AlertDialog.Builder(mContext)
                .setTitle("Upgrade group")
                .setMessage(msg)
                .setNegativeButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        convertGroupToSuperGroup();
                    }
                })
                .show();
    }

    private void convertGroupToSuperGroup() {
        final ProgressDialog prg = new ProgressDialogBuilder(mContext)
                .setTitle("Loading")
                .setMessage("Please wait...")
                .setIndeterminate(true)
                .setCancelable(false)
                .show();


        new ConvertToSuperGroup(chatId, new Client.ResultHandler() {
            @Override
            public void onResult(final TdApi.TLObject object) {
                onUiThread(new Runnable() {
                    @Override
                    public void run() {
                        prg.dismiss();
                        onConvertGroupToSuperGroupCallback.onResult(object);
                    }
                });
            }
        }).convert();
    }

    Client.ResultHandler onConvertGroupToSuperGroupCallback = new Client.ResultHandler() {
        @Override
        public void onResult(TdApi.TLObject object) {
            btnConvertToSuper.setVisibility(View.GONE);
            TdApi.Chat chat = null;
            if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR)
                chat = (TdApi.Chat) object;
            final boolean success = chat != null && chat.type.getConstructor() == TdApi.ChannelChatInfo.CONSTRUCTOR;
            if (success)
                showConvertSuccessAndFinish();
            else {
                MyToast.toastL(mContext, object.toString());
            }
        }
    };

    private void showConvertSuccessAndFinish() {
        new AlertDialog.Builder(mContext).setCancelable(false)
                .setTitle("Success")
                .setMessage(R.string.action_group_was_converted)
                .setNegativeButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityGroupsList.reloadOnResume = true;
                        finish();
                    }
                })
                .show();
    }

    public static class ConvertToSuperGroup {
        Client.ResultHandler onConverted;
        long chatId;

        public ConvertToSuperGroup(long chatId, Client.ResultHandler onConvertedCallback) {
            this.chatId = chatId;
            this.onConverted = onConvertedCallback;
        }

        void convert() {
            convertGroup();
        }

        void convertGroup() {
            TdApi.TLFunction f = new TdApi.MigrateGroupChatToChannelChat(chatId);
            TgH.send(f, new Client.ResultHandler() {
                @Override
                public void onResult(final TdApi.TLObject object) {
                    onConverted.onResult(object);
                }
            });
        }
    }


    void updateMutedUsersCount() {
        int count = DBHelper.getInstance().getMutedCount(chatId);
        if (count == 0)
            tvMutedUsersCount.setText(R.string.text_no_muted_users);
        else
            tvMutedUsersCount.setText(count + " " + Utils.pluralValue(mContext, R.array.muted_users_plural, count));
    }

    private void dialogNotifyChatToReadonlyMode(final boolean isReadonly) {
        //// This chat is now in read-only mode.
        // This chat is no longer in read-only mode.
        View dv = UIUtils.inflate(mContext, R.layout.dialog_input_text);
        dv.findViewById(R.id.edtInviteUsername).setVisibility(View.GONE);
        dv.findViewById(R.id.tvDescription).setVisibility(View.GONE);
        TextView tvHint = UIUtils.getView(dv, R.id.tvHint);
        final EmojiconEditText edtText = UIUtils.getView(dv, R.id.editInputText);

        tvHint.setText(isReadonly ? R.string.title_chat_in_readonly_mode : R.string.title_chat_no_in_readonly);

        final String defaultMessage = getString(isReadonly ? R.string.text_chat_in_readonly_now : R.string.text_chat_not_in_readonly);
        edtText.setText(Sets.getString(isReadonly ? "text_chat_readonly" : "text_chat_not_readonly", defaultMessage));
        edtText.setMaxLines(6);

        new AlertDialog.Builder(mContext)
                .setTitle("Read-Only Mode")
                .setView(dv)
                .setCancelable(false)
                .setPositiveButton(R.string.btnSend, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = edtText.getText().toString().trim();
                        if (!text.equals(defaultMessage))
                            Sets.set(isReadonly ? "text_chat_readonly" : "text_chat_not_readonly", text);

                        SendMessageHelper.sendMessageItalic(chatId, text);
                    }
                })
                .setNegativeButton(R.string.btnCancel, null)
                .show();

    }

    void dialogEditWelcomeText() {
        View dv = UIUtils.inflate(mContext, R.layout.dialog_input_text);
        dv.findViewById(R.id.edtInviteUsername).setVisibility(View.GONE);
        dv.findViewById(R.id.tvDescription).setVisibility(View.GONE);
        TextView tvHint = UIUtils.getView(dv, R.id.tvHint);
        final EmojiconEditText edtText = UIUtils.getView(dv, R.id.editInputText);
        tvHint.setText(R.string.hint_enter_welcome_text);
        edtText.setMaxLines(6);
        ChatTask welcomeTask = chatTasks.getTask(ChatTask.TYPE.WELCOME_USER, false);
        if (welcomeTask != null && !TextUtils.isEmpty(welcomeTask.mText))
            edtText.setText(welcomeTask.mText);

        new AlertDialog.Builder(mContext)
                .setTitle("Welcome message")
                .setView(dv)
                .setCancelable(false)
                .setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = edtText.getText().toString().trim();
                        ChatTask task = chatTasks.getTask(ChatTask.TYPE.WELCOME_USER);
                        task.mText = text;
                        chatTasks.saveTask(task);
                        //DBHelper.getInstance().setWelcomeText(chatId, text, chkWelcomeText.isChecked());
                        tvWelcomeTextShort.setText(text);
                    }
                })
                .setNegativeButton(R.string.btnCancel, null)
                .show();
    }

    void getBotForChat() {
        ChatTask task = DBHelper.getInstance().getChatTask(chatId, ChatTask.TYPE.CHAT_BOT);
        if (task != null) {
            BotToken botToken = DBHelper.getInstance().getBotToken(task.mText);
            if (botToken == null)
                tvSelectBot.setText(task.mText);
            else
                tvSelectBot.setText(botToken.mFirstName);
        }
    }

    private void dialogSelectGroup() {
        FragmentSelectGroup f = new FragmentSelectGroup();
        f.setOnChatSelected(new Callback() {
            @Override
            public void onResult(Object data) {
                TdApi.Chat chat = (TdApi.Chat) data;
                tvSelectLogEventsToChat.setText(chat.title);
                DBHelper.getInstance().setChatLogTargetChat(chatId, chat.id);

                //Invite bot for selected group in specified:
                String token = CommonUtils.useBotForAlert(chatId);
                if (token == null) return;//bot is not specified  for chat
                BotToken botToken = DBHelper.getInstance().getBotToken(token);
                if (botToken != null) {
                    TdApi.TLFunction f = new TdApi.ChangeChatMemberStatus(chat.id, botToken.bot_id, new TdApi.ChatMemberStatusEditor());
                    TgH.send(f, new Client.ResultHandler() {
                        @Override
                        public void onResult(TdApi.TLObject object) {
                            MyLog.log(object.toString());
                        }
                    });
                }
            }
        });
        f.show(getSupportFragmentManager(), "groups");
    }


    @Override
    public void onBackPressed() {
        if (mCurrentPopupWindow != null) {
            mCurrentPopupWindow.dismiss();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void close() {
        AdHelper.onCloseActivity(this);
    }
}
