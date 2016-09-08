package com.madpixels.tgadmintools.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.AntiSpamRule;
import com.madpixels.tgadmintools.helper.DialogInputValue;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.services.ServiceAntispam;
import com.madpixels.tgadmintools.utils.TgImageGetter;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconTextView;
import libs.AdHelper;

/**
 * Created by Snake on 29.02.2016.
 */
public class ActivityGroupInfo extends ActivityExtended {

    ImageButton btnAva;
    TextView tvChatUsername, tvUsersCount, tvAdminsCount, tvKickedCount, tvBanLinksWarningCount, tvBanSticksWarningCount,
            tvInviteLink, tvChatType, tvBanWordsAllowCount;
    EmojiconTextView tvTitle, tvChatDescription, tvChatAdmin;
    View viewContent, viewLoading, layerBanLinksAge, layerBanSticksAge, layerBanForBlackWord;
    Button btnConvertToSuper;
    CheckBox chkAnyoneInviteFriendsSuper, chkAnyoneManageGroup, chkRemoveLinks, chkAutoBanLinks, chkRemoveStickers,
            chkAutoBanSticks,  chkReturnOnBanLinksExpired, chkReturnOnBanSticksExpired,
            chkWelcomeText, chkBanForWordBlackList, chkReturnOnBlackWordUnban, chkDelMsgBlackWords,
            chkRemoveJoinedMsg, chkRemoveLeaveMsg, chkAlertOnBlackWordBan, chkWarnBeforeBanWord,
            chkWarnBeforeStickersBan, chkWarnBeforeLinksBan;
    EditText editTextLinksWarning, editTextSticksWarning, edtLinksBanAgeVal, edtSticksBanAgeVal, edtWelcomeText,
            edtBlacklistWords, edtBlackWordBanAgeVal, edtBanWordWarnText;

    int chatType;
    long chatId;
    int groupId, channelId;
    String chatTitle, chatUsername, chatDescription, chatInviteLink;
    int chatUsersCount, adminsCount, kickedCount;
    boolean isAdmin = false; // creator
    boolean superAanyoneCanInvite, groupAnyoneCanEdit;
    SeekBar seekBarBanLinksCount, seekBarBanSticksCount;

    String adminName;
    int adminId = 0;
    boolean isChannel = false;

    private TdApi.GroupFull groupFull;

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


        UIUtils.include(this, R.id.layer_words_antispam, R.layout.layout_words_antispam);
        UIUtils.include(this, R.id.layer_stickers_antispam, R.layout.layout_stickers_antispam);
        UIUtils.include(this, R.id.layer_links_antispam, R.layout.layout_links_antispam);

        btnAva = getView(R.id.imgBtnChatAva);
        btnConvertToSuper = getView(R.id.btnConvertToSuper);
        tvChatUsername = getView(R.id.tvChatUsername);
        tvTitle = getView(R.id.tvTitle);
        tvChatDescription = getView(R.id.tvChatDescription);
        tvUsersCount = getView(R.id.tvUsersCount);
        tvChatAdmin = getView(R.id.tvMainAdmin);
        tvAdminsCount = getView(R.id.tvAdminsCount);
        tvKickedCount = getView(R.id.tvKickedCount);
        seekBarBanLinksCount = getView(R.id.seekBarBanLinksCount);
        seekBarBanSticksCount = getView(R.id.seekBarBanSticksCount);
        tvBanSticksWarningCount = getView(R.id.tvBanSticksWarningCount);
        tvBanLinksWarningCount = getView(R.id.tvBanLinksWarningCount);
        tvInviteLink = getView(R.id.tvInviteLink);
        editTextLinksWarning = getView(R.id.editTextLinksWarning);
        editTextSticksWarning = getView(R.id.editTextSticksWarning);
        layerBanLinksAge = getView(R.id.layerBanLinksAge);
        layerBanSticksAge = getView(R.id.layerBanSticksAge);
        chkReturnOnBanSticksExpired = getView(R.id.chkReturnOnBanSticksExpired);
        chkReturnOnBanLinksExpired = getView(R.id.chkReturnOnBanLinksExpired);
        tvChatType = getView(R.id.tvChatType);
        edtWelcomeText = getView(R.id.edtWelcomeText);
        chkWelcomeText = getView(R.id.chkWelcomeText);
        edtBlacklistWords = getView(R.id.edtBlacklistWords);
        chkBanForWordBlackList = getView(R.id.chkBanForWordBlackList);
        chkReturnOnBlackWordUnban = getView(R.id.chkReturnOnBlackWordUnban);
        chkDelMsgBlackWords = getView(R.id.chkDelMsgBlackWords);
        layerBanForBlackWord = getView(R.id.layerBanForBlackWord);
        chkRemoveJoinedMsg = getView(R.id.chkRemoveJoinedMsg);
        chkRemoveLeaveMsg = getView(R.id.chkRemoveLeaveMsg);
        chkAlertOnBlackWordBan = getView(R.id.chkAlertOnBlackWordBan);
        chkWarnBeforeBanWord = getView(R.id.chkWarnBeforeBanWord);
        edtBanWordWarnText = getView(R.id.edtBanWordWarnText);
        tvBanWordsAllowCount = getView(R.id.tvBanWordsAllowCount);
        TextView tvNoticePhoneBookEnabled = getView(R.id.tvNoticePhoneBookEnabled);
        chkWarnBeforeStickersBan = getView(R.id.chkWarnBeforeStickersBan);
        chkWarnBeforeLinksBan = getView(R.id.chkWarnBeforeLinksBan);

        setTitle(R.string.title_group_info);
        tvTitle.setText(chatTitle);

        UIUtils.setBatchClickListener(onClickListener, tvChatUsername, btnConvertToSuper, tvTitle, tvChatAdmin, tvChatDescription,
                tvInviteLink, tvAdminsCount, tvUsersCount, tvKickedCount, chkReturnOnBlackWordUnban, chkDelMsgBlackWords,
                tvBanLinksWarningCount, tvBanSticksWarningCount, chkWarnBeforeBanWord, tvBanWordsAllowCount,
                chkWarnBeforeStickersBan, chkWarnBeforeLinksBan);

        if(Sets.getBoolean(Const.ANTISPAM_IGNORE_SHARED_CONTACTS, true)){
            tvNoticePhoneBookEnabled.setText(getString(R.string.label_notice_ignore_antispam_for_shared));
        }else
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

        seekBarBanLinksCount.setOnSeekBarChangeListener(onSeekBarBanLinksCountListener);
        seekBarBanSticksCount.setOnSeekBarChangeListener(onSeekBarBanSticksCount);
        // seekBarBanSticksCount.setEnabled(false);
        // seekBarBanLinksCount.setEnabled(false);

        chkRemoveLinks = getView(R.id.chkRemoveLinks);
        chkAutoBanLinks = getView(R.id.chkAutoBanLinks);
        chkRemoveStickers = getView(R.id.chkRemoveStickers);
        chkAutoBanSticks = getView(R.id.chkAutoBanSticks);

        UIUtils.setBatchClickListener(onClickListener, chkRemoveLinks, chkRemoveStickers, chkAutoBanSticks, chkAutoBanLinks,
                chkReturnOnBanLinksExpired, chkReturnOnBanSticksExpired, chkBanForWordBlackList,
                chkRemoveLeaveMsg, chkRemoveJoinedMsg, chkAlertOnBlackWordBan);

        String[] banTimes = getResources().getStringArray(R.array.auto_ban_times);
        ArrayAdapter<String> pLinksAgessAdapter = new ArrayAdapter<String>(
                mContext, R.layout.item_spinner, R.id.textView, banTimes);
        final Spinner spinnerBanTimesLink = getView(R.id.spinnerBanLinksAge);
        edtLinksBanAgeVal = getView(R.id.edtLinksBanAgeVal);
        spinnerBanTimesLink.setAdapter(pLinksAgessAdapter);
        spinnerBanTimesLink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtLinksBanAgeVal.setEnabled(position > 0);
                setBanAgeLinks(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Blackword:
        final Spinner spinnerBanBlackWordAge = getView(R.id.spinnerBanBlackWordAge);
        edtBlackWordBanAgeVal = getView(R.id.edtBlackWordBanAgeVal);
        spinnerBanBlackWordAge.setAdapter(pLinksAgessAdapter);
        spinnerBanBlackWordAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtBlackWordBanAgeVal.setEnabled(position > 0);
                chkReturnOnBlackWordUnban.setEnabled(position > 0);
                if (edtBlackWordBanAgeVal.getText().toString().equals("0"))
                    edtBlackWordBanAgeVal.setText("5");
                setBanBlackwordsAge(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // .blackwords

        /*=============*/
        ArrayAdapter<String> pSticksAgesAdapter = new ArrayAdapter<String>(
                mContext, R.layout.item_spinner, R.id.textView, banTimes);
        final Spinner spinnerBanTimesSticks = getView(R.id.spinnerBanSticksAge);
        edtSticksBanAgeVal = getView(R.id.edtSticksBanAgeVal);
        spinnerBanTimesSticks.setAdapter(pSticksAgesAdapter);
        spinnerBanTimesSticks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtSticksBanAgeVal.setEnabled(position > 0);
                if (edtSticksBanAgeVal.getText().toString().equals("0"))
                    edtSticksBanAgeVal.setText("5");
                setBanAgeSticks(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        if (TgUtils.isSuperGroup(chatType)) {
            btnConvertToSuper.setVisibility(View.GONE);
        } else {
            tvChatType.setText(R.string.chatTypeGroup);
            UIUtils.setTextColotRes(chkRemoveLinks, R.color.md_grey_500);
            UIUtils.setTextColotRes(chkRemoveStickers, R.color.md_grey_500);
            UIUtils.setTextColotRes(chkDelMsgBlackWords, R.color.md_grey_500);
            UIUtils.setTextColotRes(chkRemoveJoinedMsg, R.color.md_grey_500);
            UIUtils.setTextColotRes(chkRemoveLeaveMsg, R.color.md_grey_500);
            //chkRemoveLinks.setVisibility(View.GONE);
            //chkRemoveStickers.setVisibility(View.GONE);
        }

        AntiSpamRule antiSpamRule = DBHelper.getInstance().getAntispamRule(chatId);
        if (antiSpamRule != null) {
            chkRemoveLinks.setChecked(antiSpamRule.isRemoveLinks);
            chkRemoveStickers.setChecked(antiSpamRule.isRemoveStickers);

            chkAutoBanSticks.setChecked(antiSpamRule.isBanForStickers);
            chkAutoBanLinks.setChecked(antiSpamRule.isBanForLinks);
            chkWarnBeforeLinksBan.setChecked(antiSpamRule.isWarnBeforeLinksBan);


            editTextLinksWarning.setText(antiSpamRule.mWarnTextLink);
            editTextSticksWarning.setText(antiSpamRule.mWarnTextStickers);
            seekBarBanLinksCount.setProgress(antiSpamRule.mAllowLinksCount);
            chkWarnBeforeStickersBan.setChecked(antiSpamRule.isWarnBeforeStickersBan);

            editTextSticksWarning.setVisibility(antiSpamRule.isWarnBeforeStickersBan?View.VISIBLE:View.GONE);
            editTextLinksWarning.setVisibility(antiSpamRule.isWarnBeforeLinksBan?View.VISIBLE:View.GONE);

            if (!antiSpamRule.isBanForLinks)
                layerBanLinksAge.setVisibility(View.GONE);
            if (!antiSpamRule.isBanForStickers)
                layerBanSticksAge.setVisibility(View.GONE);

            if (antiSpamRule.option_links_banage_mp > 0)
                spinnerBanTimesLink.setSelection(antiSpamRule.option_links_banage_mp);
            edtLinksBanAgeVal.setText(antiSpamRule.option_link_banage_val + "");
            edtLinksBanAgeVal.setEnabled(antiSpamRule.option_links_banage_mp > 0);
            chkReturnOnBanLinksExpired.setEnabled(edtLinksBanAgeVal.isEnabled());
            chkReturnOnBanLinksExpired.setChecked(antiSpamRule.isLinkReturnOnUnban);

            if (antiSpamRule.option_sticks_banage_mp > 0)
                spinnerBanTimesSticks.setSelection(antiSpamRule.option_sticks_banage_mp);
            edtSticksBanAgeVal.setText(antiSpamRule.option_sticks_banage_val + "");
            edtSticksBanAgeVal.setEnabled(antiSpamRule.option_sticks_banage_mp > 0);
            chkReturnOnBanSticksExpired.setEnabled(edtSticksBanAgeVal.isEnabled());
            chkReturnOnBanSticksExpired.setChecked(antiSpamRule.isStickerReturnOnUnban);
//
            tvBanLinksWarningCount.setText(antiSpamRule.mAllowLinksCount + ""); //TODO переименовать textView
//            seekBarBanLinksCount.setProgress(antiSpamRule.mAllowLinksCount);
//
//
//
            tvBanSticksWarningCount.setText(antiSpamRule.mAllowStickersCount + "");
            seekBarBanSticksCount.setProgress(antiSpamRule.mAllowStickersCount);


            chkBanForWordBlackList.setChecked(antiSpamRule.isBanForBlackWords);
            edtBlackWordBanAgeVal.setText(antiSpamRule.option_blackword_banage_val + "");
            spinnerBanBlackWordAge.setSelection(antiSpamRule.option_blackword_banage_mp);
            chkReturnOnBlackWordUnban = getView(R.id.chkReturnOnBlackWordUnban);
            chkReturnOnBlackWordUnban.setChecked(antiSpamRule.isBlackWordReturnOnBanExp);
            chkDelMsgBlackWords.setChecked(antiSpamRule.isDelMsgBlackWords);
            chkAlertOnBlackWordBan.setChecked(antiSpamRule.isAlertAboutWordBan);
            edtBanWordWarnText.setText(antiSpamRule.mWarnTextBlackWords);
            chkWarnBeforeBanWord.setChecked(antiSpamRule.isWarnBeforeBanForWord);
            tvBanWordsAllowCount.setText(antiSpamRule.banWordsFloodLimit + "");
            layerBanForBlackWord.setVisibility(antiSpamRule.isBanForBlackWords?View.VISIBLE:View.GONE);

            chkRemoveLeaveMsg.setChecked(antiSpamRule.isRemoveLeaveMessage);
            chkRemoveJoinedMsg.setChecked(antiSpamRule.isRemoveJoinMessage);

        } else {
            UIUtils.setBatchVisibility(View.GONE, editTextSticksWarning, editTextLinksWarning, layerBanLinksAge, layerBanSticksAge,
                    chkReturnOnBanLinksExpired, chkReturnOnBanSticksExpired);
            edtLinksBanAgeVal.setEnabled(false);
            chkReturnOnBanLinksExpired.setEnabled(false);
            chkReturnOnBanSticksExpired.setEnabled(false);
            layerBanForBlackWord.setVisibility(View.GONE);
            chkWarnBeforeBanWord.setChecked(true);
            chkWarnBeforeStickersBan.setChecked(true);
        }


        editTextSticksWarning.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextSticksWarning.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextSticksWarning.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        long ts = Long.valueOf(editTextSticksWarning.getTag().toString());
                        if (System.currentTimeMillis() - ts < 1900) return;

                        String text = editTextSticksWarning.getText().toString().trim();
                        AntiSpamRule antiSpamRule = getAntispamRule();
                        antiSpamRule.mWarnTextStickers = text;
                        saveAntispamRule(antiSpamRule);
                    }
                }, 2000);

            }
        });
        editTextLinksWarning.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextLinksWarning.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextLinksWarning.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        long ts = Long.valueOf(editTextLinksWarning.getTag().toString());
                        if (System.currentTimeMillis() - ts < 1900) return;

                        String text = editTextLinksWarning.getText().toString().trim();
                        AntiSpamRule antiSpamRule = getAntispamRule();
                        antiSpamRule.mWarnTextLink = text;
                        saveAntispamRule(antiSpamRule);
                    }
                }, 2000);
            }
        });
        edtLinksBanAgeVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtLinksBanAgeVal.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(Editable s) {
                edtLinksBanAgeVal.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        long ts = Long.valueOf(edtLinksBanAgeVal.getTag().toString());
                        if (System.currentTimeMillis() - ts < 900) return;

                        // String text = edtLinksBanAgeVal.getText().toString().trim();
                        setBanAgeLinks(spinnerBanTimesLink.getSelectedItemPosition());
                    }
                }, 1000);
            }
        });

        edtSticksBanAgeVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtSticksBanAgeVal.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(Editable s) {
                edtSticksBanAgeVal.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        long ts = Long.valueOf(edtSticksBanAgeVal.getTag().toString());
                        if (System.currentTimeMillis() - ts < 900) return;

                        // String text = edtLinksBanAgeVal.getText().toString().trim();
                        setBanAgeSticks(spinnerBanTimesSticks.getSelectedItemPosition());
                    }
                }, 1000);
            }
        });

        edtBlackWordBanAgeVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtBlackWordBanAgeVal.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(Editable s) {
                edtBlackWordBanAgeVal.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        long ts = Long.valueOf(edtBlackWordBanAgeVal.getTag().toString());
                        if (System.currentTimeMillis() - ts > 900)
                            setBanBlackwordsAge(spinnerBanBlackWordAge.getSelectedItemPosition());
                    }
                }, 1000);
            }
        });

        final String[] blackWords = DBHelper.getInstance().getWordsBlackList(chatId);
        if (blackWords != null) {
            StringBuilder sb = new StringBuilder();
            for (String s : blackWords)
                sb.append(s).append("\n");
            edtBlacklistWords.setText(sb.toString().trim());
        }
        edtBlacklistWords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtBlacklistWords.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(final Editable s) {
                edtBlacklistWords.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        long ts = Long.valueOf(edtBlacklistWords.getTag().toString());
                        if (System.currentTimeMillis() - ts < 1400) return;
                        //Save blackList:
                        String text = s.toString().trim();
                        if (!text.isEmpty()) {
                            String[] lines = text.split("\n");
                            DBHelper.getInstance().saveWordsBlackList(chatId, lines);
                            edtBlacklistWords.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        } else {
                            DBHelper.getInstance().saveWordsBlackList(chatId, new String[]{});
                            edtBlacklistWords.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        }
                    }
                }, 1500);
                edtBlacklistWords.getBackground().setColorFilter(getResources().getColor(R.color.md_teal_500), PorterDuff.Mode.SRC_ATOP);
            }
        });


        edtBanWordWarnText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtBanWordWarnText.setTag(System.currentTimeMillis());
            }
            @Override
            public void afterTextChanged(final Editable s) {
                edtBanWordWarnText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        long ts = Long.valueOf(edtBanWordWarnText.getTag().toString());
                        if (System.currentTimeMillis() - ts < 1400) return;

                        String text = s.toString().trim();
                        AntiSpamRule antiSpamRule = getAntispamRule();
                        antiSpamRule.mWarnTextBlackWords = text;
                        saveAntispamRule(antiSpamRule);
                        edtBanWordWarnText.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);

                    }
                }, 1500);
                edtBanWordWarnText.getBackground().setColorFilter( getResources().getColor(R.color.md_teal_500) , PorterDuff.Mode.SRC_ATOP);
            }
        });


        setWelcomeText();
        getChatInfo();
    }

    private void setWelcomeText() {
        edtWelcomeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtWelcomeText.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(Editable s) {
                edtWelcomeText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        long ts = Long.valueOf(edtWelcomeText.getTag().toString());
                        if (System.currentTimeMillis() - ts < 900) return;
                        String text = edtWelcomeText.getText().toString().trim();
                        DBHelper.getInstance().setWelcomeText(chatId, text, chkWelcomeText.isChecked());
                        if (chkWelcomeText.isChecked())
                            ServiceAntispam.start(mContext);
                    }
                }, 1000);
            }
        });

        chkWelcomeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.getInstance().setWelcomeText(chatId, edtWelcomeText.getText().toString().trim(), chkWelcomeText.isChecked());
                if (chkWelcomeText.isChecked())
                    ServiceAntispam.start(mContext);
            }
        });

        Object[] welcomeTexts = DBHelper.getInstance().getWelcomeTextObject(chatId);
        if (welcomeTexts == null) return;
        boolean isEnabled = (boolean) welcomeTexts[0];
        String text = welcomeTexts[1].toString();
        chkWelcomeText.setChecked(isEnabled);
        edtWelcomeText.setText(text);


    }

    void setBanBlackwordsAge(int selection) {
        long banAge = 0;
        int val = 0;

        try {
            val = Integer.valueOf(edtBlackWordBanAgeVal.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        switch (selection) {
            case 0:
                banAge = 0;
                break;
            case 1:
                banAge = val * 60 * 1000;
                break;
            case 2:
                banAge = val * 60 * 1000 * 60;
                break;
            case 3:
                banAge = val * 60 * 1000 * 60 * 24;
                break;
        }
        AntiSpamRule antiSpamRule = getAntispamRule();
        antiSpamRule.mBlackWordBanAgeMsec = banAge;
        antiSpamRule.option_blackword_banage_val = val; //TODO прочитать из бд значения
        antiSpamRule.option_blackword_banage_mp = selection;
        saveAntispamRule(antiSpamRule);
    }

    void setBanAgeLinks(int selection) {
        long banAge = 0;
        int val = 0;

        try {
            val = Integer.valueOf(edtLinksBanAgeVal.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        switch (selection) {
            case 0:
                banAge = 0;
                break;
            case 1:
                banAge = val * 60 * 1000;
                break;
            case 2:
                banAge = val * 60 * 1000 * 60;
                break;
            case 3:
                banAge = val * 60 * 1000 * 60 * 24;
                break;
        }
        AntiSpamRule antiSpamRule = getAntispamRule();
        antiSpamRule.mLinkBanAgeMsec = banAge;
        antiSpamRule.option_link_banage_val = val;
        antiSpamRule.option_links_banage_mp = selection;
        saveAntispamRule(antiSpamRule);
        chkReturnOnBanLinksExpired.setEnabled(banAge > 0);
    }

    void setBanAgeSticks(int selection) {
        long banAge = 0;
        int val = 0;

        try {
            val = Integer.valueOf(edtSticksBanAgeVal.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        switch (selection) {
            case 0:
                banAge = 0;
                break;
            case 1:
                banAge = val * 60 * 1000;
                break;
            case 2:
                banAge = val * 60 * 1000 * 60;
                break;
            case 3:
                banAge = val * 60 * 1000 * 60 * 24;
                break;
        }
        AntiSpamRule antiSpamRule = getAntispamRule();
        antiSpamRule.mStickerBanAgeMsec = banAge;
        antiSpamRule.option_sticks_banage_val = val;
        antiSpamRule.option_sticks_banage_mp = selection;
        saveAntispamRule(antiSpamRule);
        chkReturnOnBanSticksExpired.setEnabled(banAge > 0);
    }

    SeekBar.OnSeekBarChangeListener onSeekBarBanLinksCountListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser)
                tvBanLinksWarningCount.setText(progress + "");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int val = seekBar.getProgress();
            // MyToast.toast(mContext, "" + val);
            AntiSpamRule antiSpamRule = getAntispamRule();
            antiSpamRule.mAllowLinksCount = val;
            saveAntispamRule(antiSpamRule);
        }
    };
    SeekBar.OnSeekBarChangeListener onSeekBarBanSticksCount = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser)
                tvBanSticksWarningCount.setText(progress + "");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int val = seekBar.getProgress();
            MyToast.toast(mContext, "" + val);
            AntiSpamRule antiSpamRule = getAntispamRule();
            antiSpamRule.mAllowStickersCount = val;
            saveAntispamRule(antiSpamRule);
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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
                case R.id.chkAutoBanSticks:
                    AntiSpamRule antiSpamRule = getAntispamRule();
                    antiSpamRule.isBanForStickers = chkAutoBanSticks.isChecked();
                    saveAntispamRule(antiSpamRule);
                    layerBanSticksAge.setVisibility(antiSpamRule.isBanForStickers ? View.VISIBLE : View.GONE);
                    chkReturnOnBanSticksExpired.setVisibility(layerBanSticksAge.getVisibility());
                    chkReturnOnBanSticksExpired.setEnabled(antiSpamRule.isBanForStickers && antiSpamRule.mStickerBanAgeMsec > 0);
                    break;
                case R.id.chkAutoBanLinks:
                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isBanForLinks = chkAutoBanLinks.isChecked();
                    saveAntispamRule(antiSpamRule);
                    chkReturnOnBanLinksExpired.setEnabled(antiSpamRule.isBanForLinks && antiSpamRule.mLinkBanAgeMsec > 0);
                    layerBanLinksAge.setVisibility(antiSpamRule.isBanForLinks ? View.VISIBLE : View.GONE);
                    chkReturnOnBanLinksExpired.setVisibility(layerBanLinksAge.getVisibility());

                    break;
                case R.id.chkRemoveLinks:
                    if (TgUtils.isGroup(chatType)) {
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        chkRemoveLinks.setChecked(false);
                        return;
                    }

                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isRemoveLinks = chkRemoveLinks.isChecked();
                    saveAntispamRule(antiSpamRule);
                    break;
                case R.id.chkRemoveStickers:
                    if (TgUtils.isGroup(chatType)) {
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        chkRemoveStickers.setChecked(false);
                        return;
                    }

                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isRemoveStickers = chkRemoveStickers.isChecked();
                    saveAntispamRule(antiSpamRule);
                    break;
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

                case R.id.chkReturnOnBanLinksExpired:
                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isLinkReturnOnUnban = chkReturnOnBanLinksExpired.isChecked();
                    saveAntispamRule(antiSpamRule);
                    break;
                case R.id.chkReturnOnBanSticksExpired:
                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isStickerReturnOnUnban = chkReturnOnBanSticksExpired.isChecked();
                    saveAntispamRule(antiSpamRule);
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
                case R.id.chkBanForWordBlackList:
                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isBanForBlackWords = chkBanForWordBlackList.isChecked();
                    saveAntispamRule(antiSpamRule);
                    layerBanForBlackWord.setVisibility(antiSpamRule.isBanForBlackWords ? View.VISIBLE : View.GONE);
                    if (antiSpamRule.isBanForBlackWords)
                        ServiceAntispam.start(mContext);
                    break;
                case R.id.chkReturnOnBlackWordUnban:
                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isBlackWordReturnOnBanExp = chkReturnOnBlackWordUnban.isChecked();
                    saveAntispamRule(antiSpamRule);
                    break;
                case R.id.chkDelMsgBlackWords:
                    if (TgUtils.isGroup(chatType)) {
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        chkDelMsgBlackWords.setChecked(false);
                        return;
                    }

                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isDelMsgBlackWords = chkDelMsgBlackWords.isChecked();
                    saveAntispamRule(antiSpamRule);
                    if (antiSpamRule.isDelMsgBlackWords)
                        ServiceAntispam.start(mContext);
                    break;
                case R.id.chkRemoveLeaveMsg:
                    if (TgUtils.isGroup(chatType)) {
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        chkRemoveLeaveMsg.setChecked(false);
                        return;
                    }

                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isRemoveLeaveMessage = chkRemoveLeaveMsg.isChecked();
                    saveAntispamRule(antiSpamRule);
                    if (antiSpamRule.isRemoveLeaveMessage)
                        ServiceAntispam.start(mContext);
                    break;
                case R.id.chkWarnBeforeLinksBan:
                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isWarnBeforeLinksBan = chkWarnBeforeLinksBan.isChecked();
                    saveAntispamRule(antiSpamRule);
                    editTextLinksWarning.setVisibility(antiSpamRule.isWarnBeforeLinksBan?View.VISIBLE:View.GONE);
                    break;
                case R.id.chkRemoveJoinedMsg:
                    if (TgUtils.isGroup(chatType)) {
                        MyToast.toast(mContext, R.string.toast_deletion_not_avail);
                        chkRemoveJoinedMsg.setChecked(false);
                        return;
                    }

                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isRemoveJoinMessage = chkRemoveJoinedMsg.isChecked();
                    saveAntispamRule(antiSpamRule);
                    if (antiSpamRule.isRemoveJoinMessage)
                        ServiceAntispam.start(mContext);
                    break;
                case R.id.tvBanLinksWarningCount:
                    setBanLinksWarnCount();
                    break;
                case R.id.tvBanSticksWarningCount:
                    setBanStickersWarnCount();
                    break;
                case R.id.chkWarnBeforeStickersBan:
                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isWarnBeforeStickersBan = chkWarnBeforeStickersBan.isChecked();
                    saveAntispamRule(antiSpamRule);
                    editTextSticksWarning.setVisibility(antiSpamRule.isWarnBeforeStickersBan?View.VISIBLE:View.GONE);
                    break;
                case R.id.chkAlertOnBlackWordBan:
                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isAlertAboutWordBan = chkAlertOnBlackWordBan.isChecked();
                    saveAntispamRule(antiSpamRule);
                    break;
                case R.id.chkWarnBeforeBanWord:
                    antiSpamRule = getAntispamRule();
                    antiSpamRule.isWarnBeforeBanForWord = chkWarnBeforeBanWord.isChecked();
                    saveAntispamRule(antiSpamRule);
                    break;
                case R.id.tvBanWordsAllowCount:
                    setBanWordsWarnCount();
                    break;
            }
        }
    };

    private void setBanLinksWarnCount() {
        final AntiSpamRule antiSpamRule = getAntispamRule();
        new DialogInputValue(mContext)
                .setValue(antiSpamRule.mAllowLinksCount)
                .onApply(new DialogInputValue.SetValueCallback() {
                    @Override
                    public void onSetValue(int value) {
                        tvBanLinksWarningCount.setText(value + "");
                        antiSpamRule.mAllowLinksCount = value;
                        seekBarBanLinksCount.setProgress(value);
                        saveAntispamRule(antiSpamRule);
                    }
                })
                .showDialog();
    }

    private void setBanWordsWarnCount() {
        final AntiSpamRule antiSpamRule = getAntispamRule();
        new DialogInputValue(mContext)
                .setValue(antiSpamRule.banWordsFloodLimit)
                .onApply(new DialogInputValue.SetValueCallback() {
                    @Override
                    public void onSetValue(int value) {
                        tvBanWordsAllowCount.setText(value + "");
                        antiSpamRule.banWordsFloodLimit = value;
                        saveAntispamRule(antiSpamRule);
                    }
                })
                .showDialog();
    }

    private void setBanStickersWarnCount() {
        final AntiSpamRule antiSpamRule = getAntispamRule();
        new DialogInputValue(mContext)
                .setValue(antiSpamRule.mAllowStickersCount)
                .onApply(new DialogInputValue.SetValueCallback() {
                    @Override
                    public void onSetValue(int value) {
                        tvBanSticksWarningCount.setText(value + "");
                        antiSpamRule.mAllowStickersCount = value;
                        seekBarBanSticksCount.setProgress(value);
                        saveAntispamRule(antiSpamRule);
                    }
                })
                .showDialog();
    }

    private void saveAntispamRule(AntiSpamRule antiSpamRule) {
        DBHelper.getInstance().setAntiSpamRule(chatId, antiSpamRule);
        if (antiSpamRule.isBanForLinks || antiSpamRule.isBanForStickers || antiSpamRule.isRemoveLinks || antiSpamRule.isRemoveStickers)
            ServiceAntispam.start(mContext);
    }

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
                .setNegativeButton(R.string.cancel, null)
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

    private AntiSpamRule getAntispamRule() {
        AntiSpamRule antiSpamRule = DBHelper.getInstance().getAntispamRule(chatId);
        if (antiSpamRule == null) antiSpamRule = new AntiSpamRule();
        return antiSpamRule;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, R.string.changeChatAbout);
        if (isAdmin)
            menu.add(0, 3, 0, R.string.actionDeleteGroup);

        if (!isChannel)
            menu.add(0, 2, 0, R.string.action_reset_warns);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // menu.findItem(3).setVisible(isAdmin);
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
                .setNegativeButton(R.string.cancel, null)
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
        t.setText(R.string.text_confirm_deletion_group);
        view.setPadding(16, 8, 16, 8);
        view.addView(t);
        final CheckBox c = new CheckBox(this);
        c.setText(R.string.text_delete_group);
        view.addView(c);
        final AlertDialog d = new AlertDialog.Builder(mContext)
                .setView(view)
                .setTitle(R.string.title_delete_group)
                .setPositiveButton(R.string.cancel, null)
                .setNegativeButton(R.string.button_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TdApi.TLFunction f;
                        if (TgUtils.isGroup(chatType)) {
                            f = new TdApi.DeleteChatHistory(chatId);
                            final TdApi.TLFunction ff = new TdApi.ChangeChatParticipantRole(chatId, TgH.selfProfileId,
                                    new TdApi.ChatParticipantRoleLeft());
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
        d.getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(false);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean enabled = c.isChecked();
                d.getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(enabled);
            }
        });
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
                .setNegativeButton(R.string.cancel, null)
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
                TdApi.Chat chat = (TdApi.Chat) object;
                TgImageGetter tgImageGetter = new TgImageGetter();
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
                MyLog.log(object.toString());
                TdApi.GroupFull groupFull = (TdApi.GroupFull) object;
                ActivityGroupInfo.this.groupFull = groupFull;
                chatUsersCount = groupFull.group.participantsCount;
                isAdmin = groupFull.group.role.getConstructor() == TdApi.ChatParticipantRoleAdmin.CONSTRUCTOR;
                adminId = groupFull.adminId;
                adminsCount = 0;
                chatInviteLink = groupFull.inviteLink;
                groupAnyoneCanEdit = groupFull.group.anyoneCanEdit;
                for (TdApi.ChatParticipant user : groupFull.participants) {
                    if (user.user.id == adminId) {
                        adminName = user.user.firstName + " " + user.user.lastName;
                        if (TgUtils.isUserPrivileged(user.role.getConstructor()))
                            adminsCount++;
                    }
                }

                onUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onGroupInfoLoaded();
                    }
                });
            }
        });
    }

    void switchAnyoneCanInviteSuperGroup() {
        if (!isAdmin) {
            MyToast.toast(mContext, R.string.toast_access_denied_admin);
            chkAnyoneInviteFriendsSuper.setChecked(false);
            return;
        }
        final ProgressBar prgChangeState = getView(R.id.prgChangeState);
        prgChangeState.setVisibility(View.VISIBLE);
        chkAnyoneInviteFriendsSuper.setEnabled(false);

//        final ProgressDialog p = new ProgressDialogBuilder(mContext)
//                .setCancelable(false)
//                .setTitle("Loading")
//                .setMessage("Please wait...")
//                .show();

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

//        final ProgressDialog p = new ProgressDialogBuilder(mContext)
//                .setCancelable(false)
//                .setTitle("Loading")
//                .setMessage("Please wait...")
//                .show();


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

    private void getSuperGroupInfo() {
        TdApi.TLFunction f = new TdApi.GetChannelFull(channelId);
        TgH.sendOnUi(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                MyLog.log(object.toString());
                TdApi.ChannelFull channelFull = (TdApi.ChannelFull) object;
                chatInviteLink = channelFull.inviteLink;
                chatDescription = channelFull.about;
                chatUsersCount = channelFull.participantsCount;
                adminsCount = channelFull.adminsCount;
                kickedCount = channelFull.kickedCount;
                chatUsername = channelFull.channel.username;
                isAdmin = channelFull.channel.role.getConstructor() == TdApi.ChatParticipantRoleAdmin.CONSTRUCTOR;
                superAanyoneCanInvite = channelFull.channel.anyoneCanInvite;
                if (isAdmin) {
                    adminId = TgH.selfProfileId;
                    adminName = "@" + TgH.selfProfileUsername;
                    onSuperGroupInfoLoaded();
                } else
                    getSuperAdmins();

                if (!channelFull.channel.isSupergroup) {// channel
                    isChannel = true;
                    findViewById(R.id.layerAutoban).setVisibility(View.GONE);
                    tvKickedCount.setVisibility(View.GONE);
                    chkAnyoneInviteFriendsSuper.setVisibility(View.GONE);
                    setTitle(R.string.action_manage_channel);
                    invalidateOptionsMenu();
                    tvChatType.setText(R.string.chatTypeChannel);
                } else {
                    tvChatType.setText(R.string.chatTypeSuperGroup);
                }



            }
        });
    }

    private void getSuperAdmins() {
        TdApi.TLFunction f = new TdApi.GetChannelParticipants(channelId, new TdApi.ChannelParticipantsAdmins(), 0, 100);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.ChatParticipants.CONSTRUCTOR) {
                    TdApi.ChatParticipants users = (TdApi.ChatParticipants) object;
                    for (TdApi.ChatParticipant user : users.participants) {
                        if (user.role.getConstructor() == TdApi.ChatParticipantRoleAdmin.CONSTRUCTOR) {
                            adminName = user.user.firstName + " " + user.user.lastName;
                            adminId = user.user.id;
                            break;
                        }
                    }
                } else {
                    adminName = "";
                }
                onUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onSuperGroupInfoLoaded();
                    }
                });
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
        tvKickedCount.setText(getString(R.string.kickedCount) + " " + DBHelper.getInstance().getBannedCount(chatId));//TODO read database
        //if(groupFull.group.anyoneCanEdit)
        //    tvAdminsCount.setText(R.string.text_all_are_admins_group);
        //else
            tvAdminsCount.setText(getString(R.string.adminsCount) + " " + adminsCount);

        viewLoading.setVisibility(View.GONE);
        viewContent.setVisibility(View.VISIBLE);
        tvChatAdmin.setText(adminName);
        chkAnyoneManageGroup.setChecked(groupAnyoneCanEdit);
        invalidateOptionsMenu();
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
        invalidateOptionsMenu();
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
                .setNegativeButton(R.string.cancel, null)
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
                                        if (er.text.equals("CHANNELS_ADMIN_PUBLIC_TOO_MUCH")) {
                                            MyToast.toast(mContext, "Error code 400:\n" + getString(R.string.error_too_many_public_links));
                                            return;
                                        }
                                        if (er.text.equals("USERNAME_OCCUPIED")) {
                                            MyToast.toastL(mContext, getString(R.string.error_link_already_occupied));
                                            dialogSetUsername(s);
                                            return;
                                        }
                                    }
                                    MyToast.toast(mContext, "Error code " + er.code + "\n" + er.text);
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
                .setNegativeButton(R.string.cancel, null)
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

    @Override
    public void close() {
        AdHelper.onCloseActivity(this);
    }
}
