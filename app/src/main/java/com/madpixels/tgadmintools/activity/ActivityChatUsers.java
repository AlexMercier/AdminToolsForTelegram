package com.madpixels.tgadmintools.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.Utils;
import com.madpixels.apphelpers.ui.ActivityExtended;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.adapters.AdapterChatUsers;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.entities.ChatTask;
import com.madpixels.tgadmintools.entities.ChatTaskManager;
import com.madpixels.tgadmintools.helper.TelegramBot;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.services.ServiceAutoKicker;
import com.madpixels.tgadmintools.services.ServiceUnbanTask;
import com.madpixels.tgadmintools.utils.AdminUtils;
import com.madpixels.tgadmintools.utils.CommonUtils;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import libs.AdHelper;

/**
 * Created by Snake on 22.02.2016.
 */
public class ActivityChatUsers extends ActivityExtended {

    int chatType;
    long chat_id;
    boolean isLoading = false, isListEnd = false;
    int offset = 0;
    int channel_id, group_id;
    private String chatTitle;
    private int mTotalCount, mCurrentPosition;

    ListView mListViewUsers;
    AdapterChatUsers mAdapter;
    ProgressBar prgLoading, progressBarLoadMore;
    boolean isAdmin = false;// main admin
    private String additionalType = null;
    TextView tvMembersCout;
    View mHeaderSearch;
    TextView tvSearchQuery;
    ImageView ivCancelSearch;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("type", chatType);
        outState.getLong("chat_id", chat_id);
        outState.putString("title", chatTitle);
        if (additionalType != null)
            outState.putString("filter", additionalType);
        outState.putInt("channel_id", channel_id);
        outState.putInt("group_id", group_id);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_users);
        UIUtils.setToolbarWithBackArrow(this, R.id.toolbar);
        AdHelper.showBanner(findViewById(R.id.adView));
        setTitle(R.string.title_users);

        Bundle b = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();

        chatType = b.getInt("type");
        chat_id = b.getLong("chat_id");
        chatTitle = b.getString("title");
        if (b.containsKey("filter"))
            additionalType = b.getString("filter");
        if (chatType == TdApi.ChannelChatInfo.CONSTRUCTOR) {
            //chat_id = b.getLong("chat_id");
            channel_id = b.getInt("channel_id");
        } else {
            group_id = b.getInt("group_id");
        }

        mListViewUsers = getView(R.id.listViewUsers);
        View viewHeaderSearch = UIUtils.inflate(mContext, R.layout.header_list_search);
        mListViewUsers.addHeaderView(viewHeaderSearch, null, false);
        mHeaderSearch = UIUtils.getView(viewHeaderSearch, R.id.layerSearchResult);
        ivCancelSearch = UIUtils.getView(mHeaderSearch, R.id.ivCancelSearch);
        tvSearchQuery = UIUtils.getView(mHeaderSearch, R.id.tvSearchQuery);
        mHeaderSearch.setVisibility(View.GONE);

        mListViewUsers.setOnScrollListener(onScrollListener);
        mAdapter = new AdapterChatUsers(mContext);
        mListViewUsers.setAdapter(mAdapter);
        prgLoading = getView(R.id.progressBar);
        registerForContextMenu(mListViewUsers);
        mListViewUsers.setOnItemClickListener(onItemClickListener);
        tvMembersCout = getView(R.id.tvMembersCout);
        progressBarLoadMore = getView(R.id.progressBarLoadMore);

        tvMembersCout.setText("");
        progressBarLoadMore.setVisibility(View.GONE);

        ivCancelSearch.setOnClickListener(onClickListener);

        // для начала еще раз считаем инфу о чате
        getChat();
    }

    private void getChat() {
        TdApi.TLFunction f = new TdApi.GetChat(chat_id);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (TgUtils.isError(object)) {
                    MyToast.toast(mContext, object.toString());
                    return;
                }
                TdApi.Chat chat = (TdApi.Chat) object;

                if (!TgUtils.isGroup(chatType)) {
                    TdApi.ChannelChatInfo superGroup = (TdApi.ChannelChatInfo) chat.type;

                    if (superGroup.channel.status.getConstructor() == TdApi.ChatMemberStatusCreator.CONSTRUCTOR)
                        isAdmin = true;
                } else {
                    TdApi.GroupChatInfo groupInfo = (TdApi.GroupChatInfo) chat.type;
                    int memberCount = groupInfo.group.memberCount;
                    if (groupInfo.group.status.getConstructor() == TdApi.ChatMemberStatusCreator.CONSTRUCTOR)
                        isAdmin = true;
                }

                new LoadUsers().execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 102, 0, R.string.title_search);
        item.setIcon(android.R.drawable.ic_menu_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 102:
                dialogSearch();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        TdApi.ChatMember user = mAdapter.getItem(info.position - mListViewUsers.getHeaderViewsCount());
        boolean isUserPrivileged = TgUtils.isUserPrivileged(user.status.getConstructor());
        if (isAdmin || !isUserPrivileged) {  // нельзя кикать админов несуперадмину
            menu.add(0, 6, 0, R.string.action_mute_user);
            menu.add(0, 1, 0, R.string.action_ban_user);
            menu.add(0, 3, 0, R.string.action_remove_user_from_chat);
            if (TgH.selfProfileId == user.userId) {
                menu.getItem(0).setEnabled(false);
                menu.getItem(1).setEnabled(false);
            }
        }

        if (isAdmin && !isUserPrivileged)
            menu.add(0, 2, 0, R.string.action_set_as_admin);
        if (isAdmin && isUserPrivileged) {
            if (TgUtils.isSuperGroup(chatType)) // only supergroup can moders and editors, so we can switch users beetween roles
                menu.add(0, 5, 0, R.string.action_change_user_admin_role);
            menu.add(0, 4, 0, R.string.revoke_user_admin);
            if (TgH.selfProfileId == user.userId)
                menu.findItem(4).setEnabled(false);
        }


        if (BuildConfig.DEBUG)
            menu.add(0, 99, 0, "test unban");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    String searchQuery = null;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo cInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = cInfo.position - mListViewUsers.getHeaderViewsCount();
        final TdApi.ChatMember chatMember = mAdapter.getItem(pos);

        switch (item.getItemId()) {
            case 1:
                showDialogBanUser(chatMember);
                break;
            case 2:
            case 5:
                if (TgUtils.isSuperGroup(chatType))
                    dialogSetUserRole(chatMember);
                else
                    checkGroupPermissions(chatMember);
                break;
            case 3:
                removeUserFromChatButNotBan(chatMember);
                break;
            case 4:
                //TODO for base groups not tested
                setUserRole(chatMember, new TdApi.ChatMemberStatusMember());
                break;
            case 6:
                TdApi.User user = TgUtils.getUser(chatMember);
                DBHelper.getInstance().addMutedUser(chat_id, chatMember.userId, user.username);
                addUserToMuted(user);
                break;
            case 99:
                DBHelper.getInstance().getExpairedBanList();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void checkGroupPermissions(final TdApi.ChatMember user) {
        TgH.sendOnUi(new TdApi.GetGroup(group_id), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TdApi.Group group = (TdApi.Group) object;
                if (!group.anyoneCanEdit)
                    setUserRole(user, new TdApi.ChatMemberStatusEditor());
                else {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Set user role")
                            .setMessage(R.string.dialog_group_all_are_admins)
                            .setNegativeButton(R.string.btnCancel, null)
                            .setPositiveButton(R.string.role_action_switch_continue, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean allCanManage = false;
                                    TdApi.TLFunction f = new TdApi.ToggleGroupEditors(group_id, allCanManage);
                                    TgH.sendOnUi(f, new Client.ResultHandler() {
                                        @Override
                                        public void onResult(TdApi.TLObject object) {
                                            if (object.getConstructor() == TdApi.Group.CONSTRUCTOR)
                                                setUserRole(user, new TdApi.ChatMemberStatusEditor());
                                            else
                                                MyToast.toast(mContext, "Error:\n" + object.toString());
                                        }
                                    });
                                }
                            })
                            .show();
                }
            }
        });
    }

    private void dialogSetUserRole(final TdApi.ChatMember user) {
        View view = UIUtils.inflate(mContext, R.layout.dialog_set_moder);
        new AlertDialog.Builder(mContext)
                .setView(view)
                .setTitle(R.string.title_set_user_role_admin)
                .show();

        RadioButton rbAdmin = UIUtils.getView(view, R.id.rbAdmin);
        rbAdmin.setVisibility(View.GONE);

        RadioButton rbModer = UIUtils.getView(view, R.id.rbModer);
        RadioButton rbEditor = UIUtils.getView(view, R.id.rbEditor);

        if (user.status.getConstructor() == TdApi.ChatMemberStatusModerator.CONSTRUCTOR)
            rbModer.setChecked(true);
        else if (user.status.getConstructor() == TdApi.ChatMemberStatusEditor.CONSTRUCTOR)
            rbEditor.setChecked(true);

        View.OnClickListener rbClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TdApi.ChatMemberStatus role = null;
                switch (v.getId()) {
                    case R.id.rbAdmin: // not work. onyl 1 admin allowed (creator)
                        role = new TdApi.ChatMemberStatusCreator();
                        break;
                    case R.id.rbModer:
                        role = new TdApi.ChatMemberStatusModerator();
                        break;
                    case R.id.rbEditor:
                        role = new TdApi.ChatMemberStatusEditor();
                        break;
                }
                setUserRole(user, role);
            }
        };
        UIUtils.setBatchClickListener(rbClick, rbAdmin, rbModer, rbEditor);

    }

    private void setUserRole(final TdApi.ChatMember user, final TdApi.ChatMemberStatus role) {
        TdApi.TLFunction f = new TdApi.ChangeChatMemberStatus(chat_id, user.userId, role);
        TgH.sendOnUi(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (TgUtils.isOk(object)) {
                    user.status = role;
                    mAdapter.notifyDataSetChanged();
                    MyToast.toast(mContext, R.string.toast_user_role_changed);
                } else {
                    MyToast.toast(mContext, object);
                }
            }
        });
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.showContextMenu();
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivCancelSearch:
                    mHeaderSearch.setVisibility(View.GONE);
                    searchQuery = null;
                    offset = 0;
                    isListEnd = false;
                    isLoading = true;
                    prgLoading.setVisibility(View.VISIBLE);
                    mAdapter.getList().clear();
                    mAdapter.notifyDataSetChanged();
                    mTotalCount = 0;
                    new LoadUsers().execute();
                    break;
            }
        }
    };

    private final Runnable updateCount = new Runnable() {
        @Override
        public void run() {
            String pos = String.valueOf(mCurrentPosition);
            if (mTotalCount > 0)
                pos += "/" + mTotalCount;

            tvMembersCout.setText(pos);
            MyLog.log("TextView update count");
        }
    };

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            mListViewUsers.post(updateCount);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int headersCount = mListViewUsers.getHeaderViewsCount();
            if (firstVisibleItem + visibleItemCount == totalItemCount)
                mCurrentPosition = totalItemCount - headersCount;
            else
                mCurrentPosition = firstVisibleItem - headersCount;
            if (!isListEnd && !isLoading && totalItemCount > headersCount && firstVisibleItem + visibleItemCount == totalItemCount) {
                if (TgUtils.isSuperGroup(chatType)) {
                    progressBarLoadMore.setVisibility(View.VISIBLE);
                    new LoadUsers().execute();
                }
            }
        }
    };


    class LoadUsers {
        List<TdApi.ChatMember> usersList;

        protected void execute() {
            isLoading = true;
            load();
        }

        private void load() {
            switch (chatType) {
                case TdApi.ChannelChatInfo.CONSTRUCTOR:
                    getSupergroupUsers();
                    break;

                case TdApi.GroupChatInfo.CONSTRUCTOR:
                    getGroupUsers();
                    break;
            }
        }

        private void onUpdate() {
            Runnable runnable = new Runnable() {
                public void run() {
                    if (usersList != null && !usersList.isEmpty()) {
                        mAdapter.getList().addAll(usersList);
                        mAdapter.notifyDataSetChanged();
                    }
                    prgLoading.setVisibility(View.INVISIBLE);
                    progressBarLoadMore.setVisibility(View.GONE);
                    isLoading = false;
                    //Repeat search until list not end or result not found
                    if (!isListEnd && searchQuery != null && usersList.isEmpty()) {
                        isLoading = true;
                        new LoadUsers().execute();
                    }
                }
            };
            runOnUiThread(runnable);
        }

        private boolean isUserSearchQueryMatch(TdApi.ChatMember member) {
            TdApi.User u = TgUtils.getUser(member.userId);
            if (u.username.toLowerCase().contains(searchQuery) || u.firstName.toLowerCase().contains(searchQuery) ||
                    u.lastName.toLowerCase().contains(searchQuery) ||
                    String.valueOf(member.userId).contains(searchQuery)) {
                return true;
            }
            return false;
        }

        private void getGroupUsers() {
            TgH.TG().send(new TdApi.GetGroupFull(group_id), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (object.getConstructor() == TdApi.GroupFull.CONSTRUCTOR) {
                        TdApi.GroupFull group = (TdApi.GroupFull) object;
                        isListEnd = true;
                        mTotalCount = group.group.memberCount;

                        if (additionalType == null) {
                            if (searchQuery == null) {
                                usersList = Arrays.asList(group.members);
                            } else {
                                for (TdApi.ChatMember member : group.members) {
                                    if (isUserSearchQueryMatch(member)) {
                                        usersList.add(member);
                                    }
                                }
                            }
                        } else {
                            usersList = new ArrayList<>();
                            for (TdApi.ChatMember cm : group.members) {
                                if (TgUtils.isUserPrivileged(cm.status.getConstructor()) && (searchQuery == null || isUserSearchQueryMatch(cm)))
                                    usersList.add(cm);
                            }
                        }

                        onUpdate();
                    } else {
                        MyToast.toast(getActivity(), "Error loading chat members");
                    }
                }
            });
        }


        private void getSupergroupUsers() {
            final int getCount = offset == 0 ? 25 : 200;
            TdApi.ChannelMembersFilter f;
            if (additionalType == null)
                f = new TdApi.ChannelMembersRecent();
            else
                f = new TdApi.ChannelMembersAdministrators();

            TgH.TG().send(new TdApi.GetChannelMembers(channel_id, f, offset, getCount), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (object.getConstructor() == TdApi.ChatMembers.CONSTRUCTOR) {
                        TdApi.ChatMembers users = (TdApi.ChatMembers) object;

                        offset += users.members.length;
                        if (users.members.length == 0)
                            isListEnd = true;

                        if (searchQuery == null)
                            usersList = Arrays.asList(users.members);
                        else {
                            usersList = new ArrayList<>(5);
                            for (TdApi.ChatMember member : users.members) {
                                if (isUserSearchQueryMatch(member)) {
                                    usersList.add(member);
                                }
                            }
                        }
                        onUpdate();
                    } else {
                        MyToast.toast(mContext, "Error loading chat members");
                    }
                }
            });

            if (mTotalCount == 0 || searchQuery!=null) {
                TgH.send(new TdApi.GetChannelFull(channel_id), new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        TdApi.ChannelFull channelFull = (TdApi.ChannelFull) object;
                        mTotalCount = channelFull.memberCount;
                    }
                });
            }

        }

    }


    void removeUserFromChatButNotBan(final TdApi.ChatMember user) {
        AdminUtils.kickUser(chat_id, user.userId, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                final TdApi.TLFunction f = new TdApi.ChangeChatMemberStatus(chat_id, user.userId, new TdApi.ChatMemberStatusLeft());
                TgH.send(f, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        String msg;
                        if(!TgUtils.isOk(object)){
                            TdApi.Error e = (TdApi.Error) object;
                            msg = getString(R.string.toast_error_remove_user);
                            msg+="\n"+e.message;
                            MyToast.toastL(mContext, msg);
                        }else{
                            msg = getString(R.string.toast_user_was_removed);
                            MyToast.toast(mContext, msg);
                        }

                        if (TgUtils.isOk(object)) {
                            onUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.getList().remove(user);
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    /*
    ==== ban section ===========
     */
    private void showDialogBanUser(final TdApi.ChatMember user) {
        final Calendar mUnbanTargetMillis = Calendar.getInstance();

        View view = getLayoutInflater().inflate(R.layout.dialog_ban_time, null);
        final RadioButton rbBanForever = UIUtils.getView(view, R.id.rbBanForever);
        final RadioButton rbBanTime = UIUtils.getView(view, R.id.rbBanTime);
        final CheckBox chkPublishBanReason = UIUtils.getView(view, R.id.chkPublishBanReason);
        final CheckBox chkBanReturnToChat = UIUtils.getView(view, R.id.chkBanReturnToChat);
        final View layerTimebanOptions = UIUtils.getView(view, R.id.layerTimebanOptions);
        final EditText edtBanText = UIUtils.getView(view, R.id.edtBanText);
        if (TgUtils.isSuperGroup(chatType))
            UIUtils.getView(view, R.id.textViewDescriptionNonSuperGroupBan).setVisibility(View.GONE);

        chkPublishBanReason.setChecked(Sets.getBoolean(Const.CHECK_ALERT_ON_BAN, false));
        rbBanTime.setChecked(true);

        final TextView btnSetValue = UIUtils.getView(view, R.id.btnSetValue);
        final TextView btnBanSelectTime = UIUtils.getView(view, R.id.btnBanSelectTime);
        final TextView btnBanSelectDate = UIUtils.getView(view, R.id.btnBanSelectDate);
        final String[] timeValues = getResources().getStringArray(R.array.ban_times);
        if (Sets.getBoolean(Const.BAN_DEFAULT_IS_RETURN_TO_CHAT, false))
            chkBanReturnToChat.setChecked(true);

        btnSetValue.setText("1 " + timeValues[1]);
        if (Sets.getBoolean("ban.default.forever", true)) {
            rbBanForever.setChecked(true);
            layerTimebanOptions.setVisibility(View.GONE);
        } else {
            rbBanTime.setChecked(true);
        }


        int defTimeVal = Sets.getInteger(Const.BAN_DEFAULT_TIME_VALUE, 1);
        switch (Sets.getInteger(Const.BAN_DEFAULT_TIME, Calendar.MINUTE)) {
            case Calendar.MINUTE:
                btnSetValue.setText(defTimeVal + " " + timeValues[0]);
                mUnbanTargetMillis.add(Calendar.MINUTE, defTimeVal);
                break;
            case Calendar.HOUR_OF_DAY:
                btnSetValue.setText(defTimeVal + " " + timeValues[1]);
                mUnbanTargetMillis.add(Calendar.HOUR_OF_DAY, defTimeVal);
                break;
            case Calendar.DAY_OF_MONTH:
                btnSetValue.setText(defTimeVal + " " + timeValues[2]);
                mUnbanTargetMillis.add(Calendar.DAY_OF_MONTH, defTimeVal);
                break;
            case Calendar.MONTH:
                btnSetValue.setText(defTimeVal + " " + timeValues[3]);
                mUnbanTargetMillis.add(Calendar.MONTH, defTimeVal);
                break;
        }

        final DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(mContext);
        final DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);

        Date dateD = new Date(mUnbanTargetMillis.getTimeInMillis());
        String timeS = timeFormat.format(dateD);
        String dateS = dateFormat.format(dateD);
        // MyLog.log(dateS);

        // String time = Utils.TimestampToDateFormat(mUnbanTargetMillis.getTimeInMillis() / 1000, "HH:mm"); //DONE use local date format?
        // String date = Utils.TimestampToDateFormat(mUnbanTargetMillis.getTimeInMillis() / 1000, "d MMMM, yyyy");
        btnBanSelectTime.setText(timeS);
        btnBanSelectDate.setText(dateS);

        AlertDialog d = new AlertDialog.Builder(this)
                .setTitle(R.string.action_ban_user)
                .setView(view)
                .setPositiveButton(R.string.btnBanUser, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long ban_age = rbBanForever.isChecked() ? 0 : mUnbanTargetMillis.getTimeInMillis() - System.currentTimeMillis();
                        boolean isPublishBanReason = chkPublishBanReason.isChecked();
                        boolean isReturnOnUnban = chkBanReturnToChat.isChecked();
                        String banText = edtBanText.getText().toString().trim();
                        banUser(user, ban_age, isPublishBanReason, isReturnOnUnban, banText);
                        Sets.set("ban.default.forever", rbBanForever.isChecked());
                        Sets.set(Const.BAN_DEFAULT_IS_RETURN_TO_CHAT, isReturnOnUnban);
                    }
                })
                .setNegativeButton(R.string.btnCancel, null)
                .show();


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnSetValue:
                        showDialogSelectTimeValue(new Callback() {
                            @Override
                            public void onResult(Object data) {
                                long millis = (long) data;

                                String timeText = Utils.millisToText(millis);

                                btnSetValue.setText(timeText);

                                long unbanTS = (System.currentTimeMillis() + millis);
                                mUnbanTargetMillis.setTimeInMillis(unbanTS);
                                Date dateD = new Date(unbanTS);
                                // unbanTS = unbanTS / 1000;
                                String timeS = timeFormat.format(dateD);
                                String dateS = dateFormat.format(dateD);
                                //String time = Utils.TimestampToDateFormat(unbanTS, "HH:mm");
                                //String date = Utils.TimestampToDateFormat(unbanTS, "d MMMM, yyyy");

                                btnBanSelectTime.setText(timeS);
                                btnBanSelectDate.setText(dateS);
                            }
                        });
                        break;
                    case R.id.btnBanSelectDate:
                        dialogSelectDate(mUnbanTargetMillis.getTimeInMillis(), new Callback() {
                            @Override
                            public void onResult(Object data) {
                                long unbanTS = (long) data;

                                String timeText = Utils.millisToText(unbanTS - System.currentTimeMillis());
                                btnSetValue.setText(timeText);

                                mUnbanTargetMillis.setTimeInMillis(unbanTS);
                                // unbanTS = unbanTS / 1000;
                                Date dateD = new Date(unbanTS);
                                String dateS = dateFormat.format(dateD);
                                // String date = Utils.TimestampToDateFormat(unbanTS, "d MMMM, yyyy");
                                btnBanSelectDate.setText(dateS);
                            }
                        });
                        break;
                    case R.id.btnBanSelectTime:
                        dialogSelectTime(mUnbanTargetMillis.getTimeInMillis(), new Callback() {
                            @Override
                            public void onResult(Object data) {
                                long unbanTS = (long) data;

                                String timeText = Utils.millisToText(unbanTS - System.currentTimeMillis());
                                btnSetValue.setText(timeText);

                                mUnbanTargetMillis.setTimeInMillis(unbanTS);
                                //unbanTS = unbanTS / 1000;
                                Date dateD = new Date(unbanTS);
                                String timeS = timeFormat.format(dateD);
                                //String time = Utils.TimestampToDateFormat(unbanTS, "HH:mm");
                                btnBanSelectTime.setText(timeS);
                            }
                        });
                        break;
                    case R.id.rbBanForever:
                        layerTimebanOptions.setVisibility(View.GONE);
                        break;
                    case R.id.rbBanTime:
                        layerTimebanOptions.setVisibility(View.VISIBLE);
                        break;
                    case R.id.chkPublishBanReason:
                        Sets.set(Const.CHECK_ALERT_ON_BAN, chkPublishBanReason.isChecked());
                        break;
                }
            }
        };

        UIUtils.setBatchClickListener(onClickListener, rbBanTime, rbBanForever, btnSetValue, btnBanSelectDate,
                btnBanSelectTime, chkPublishBanReason);
    }

    private void banUser(final TdApi.ChatMember chatMember, final long ban_age, final boolean isPublishBanReason, final boolean isReturnOnUnban, final String banText) {
        if (TgUtils.isGroup(chatType)) {
            DBHelper.getInstance().addUserToAutoKick(chat_id, chatMember.userId);
        }
        AdminUtils.kickUser(chat_id, chatMember.userId, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        // MyLog.log(object.toString());
                        if (TgUtils.isOk(object)) {
                            onUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.getList().remove(chatMember);
                                    mAdapter.notifyDataSetChanged();
                                }
                            });

                            saveLocalBanList(chatMember, ban_age, isPublishBanReason, isReturnOnUnban, banText);
                            if (isPublishBanReason)
                                publishBanReason(chatMember, banText, ban_age);

                            TdApi.User chatUser = TgUtils.getUser(chatMember);
                            LogUtil.logBanUserManually(chat_id, chatType, chatTitle, chatUser, banText, ban_age);
                            if (TgUtils.isGroup(chatType)) {
                                ServiceAutoKicker.start(mContext);
                            }
                        } else {
                            MyToast.toast(mContext, "ban error" + object.toString());
                        }
                    }
                }
        );
    }

    private void saveLocalBanList(TdApi.ChatMember chatMember, long ban_age, boolean isPublishBanReason, boolean isReturnOnUnban, final String banText) {
        int from_id = chatType == TdApi.ChannelChatInfo.CONSTRUCTOR ? channel_id : group_id; // группа или супергруппа id.
        TdApi.User user = TgUtils.getUser(chatMember);
        DBHelper.getInstance().addToBanList(user, chat_id, chatType, from_id, ban_age, isReturnOnUnban, banText, false);
        if (ban_age > 0) //TODO проверить почему если >0 мб всегда надо?
            ServiceUnbanTask.registerTask(mContext);
    }

    private void publishBanReason(TdApi.ChatMember chatMember, String banText, long ban_age) {
        TdApi.User user = TgUtils.getUser(chatMember);

        ArrayList<TdApi.MessageEntity> entities;
        TdApi.InputMessageText inputMessage = new TdApi.InputMessageText();

        String strText = getString(R.string.text_publish_banreason);
        if(ban_age>0)
            strText+="\n"+getString(R.string.log_title_banuntil)+": "+
                    CommonUtils.tsToDate( (System.currentTimeMillis()+ban_age)/1000 );

        String botToken = CommonUtils.getBotForChatAlerts(chat_id);
        boolean useBot = botToken != null; //send alert via bot
        if (useBot) {
            String username = user.firstName + " " + user.lastName;
            if (!TextUtils.isEmpty(user.username))
                username += " @" + user.username;
            strText = strText.replace("%username%", "<b>" + TextUtils.htmlEncode(username.trim()) + "</b>");
            strText = strText.replace("%reason%", "<pre>" + TextUtils.htmlEncode(banText) + "</pre>");//code block
            TelegramBot bot = new TelegramBot(botToken);
            bot.sendMessageHtml(chat_id + "", strText);
        } else {
            String userMention = user.firstName + " " + user.lastName;
            int start = strText.indexOf("%username%");
            strText = strText.replace("%username%", userMention);

            entities = new ArrayList<>(1); // message formatting
            entities.add(new TdApi.MessageEntityMentionName(start, userMention.length(), user.id));
            start = strText.indexOf("%reason%", start + userMention.length()) + 1;
            strText = strText.replace("%reason%", "\n" + banText);
            entities.add(new TdApi.MessageEntityCode(start, banText.length()));
            inputMessage.text = strText;
            inputMessage.entities = entities.toArray(new TdApi.MessageEntity[0]);
            TdApi.TLFunction f = new TdApi.SendMessage(chat_id, 0, false, true, null, inputMessage);
            TgH.send(f);
        }
    }

    void dialogSelectTime(final long millis, final Callback callback) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(millis);
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                callback.onResult(c.getTimeInMillis());
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true)
                .show();
    }

    void dialogSelectDate(final long millis, final Callback callback) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(millis);
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                callback.onResult(c.getTimeInMillis());
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    void showDialogSelectTimeValue(final Callback callback) {
        View view = getLayoutInflater().inflate(R.layout.dialog_ban_time_values_select, null);
        final Spinner sp = UIUtils.getView(view, R.id.spinnerSelector);
        final EditText editValue = UIUtils.getView(view, R.id.editValue);

        String[] data = mContext.getResources().getStringArray(R.array.ban_times);
        ArrayAdapter<String> pGenresAdapter = new ArrayAdapter<String>(
                mContext, R.layout.item_spinner, R.id.textView, data);
        sp.setAdapter(pGenresAdapter);

        Dialog dd = new AlertDialog.Builder(mContext)
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = editValue.getText().toString();
                        int timeValue = Integer.valueOf(value);

                        Calendar c = Calendar.getInstance();
                        long msec = c.getTimeInMillis();
                        switch (sp.getSelectedItemPosition()) {
                            case 0:
                                c.add(Calendar.MINUTE, timeValue);
                                Sets.set(Const.BAN_DEFAULT_TIME, Calendar.MINUTE);
                                break;
                            case 1:
                                c.add(Calendar.HOUR_OF_DAY, timeValue);
                                Sets.set(Const.BAN_DEFAULT_TIME, Calendar.HOUR_OF_DAY);
                                break;
                            case 2:
                                c.add(Calendar.DAY_OF_MONTH, timeValue);
                                Sets.set(Const.BAN_DEFAULT_TIME, Calendar.DAY_OF_MONTH);
                                break;
                            case 3:
                                c.add(Calendar.MONTH, timeValue);
                                Sets.set(Const.BAN_DEFAULT_TIME, Calendar.MONTH);
                                break;
                        }
                        Sets.set(Const.BAN_DEFAULT_TIME_VALUE, timeValue);
                        msec = c.getTimeInMillis() - msec;
                        callback.onResult(msec);
                    }
                })
                .show();


    }

    void addUserToMuted(TdApi.User user) {
        DBHelper.getInstance().addMutedUser(chat_id, user.id, user.username);
        final ChatTaskManager cc = new ChatTaskManager(chat_id);
        final ChatTask task = cc.getTask(ChatTask.TYPE.MutedUsers);
        if (!task.isEnabled)
            new AlertDialog.Builder(mContext)
                    .setTitle(R.string.title_task_mute_users)
                    .setMessage(R.string.text_mute_user_now)
                    .setPositiveButton(R.string.btnYes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            task.isEnabled = true;
                            cc.saveTask(task);
                        }
                    })
                    .setNegativeButton(R.string.btnSkip, null)
                    .show();
    }

    void dialogSearch() {
        View view = UIUtils.inflate(mContext, R.layout.dialog_input_text);
        view.findViewById(R.id.tvDescription).setVisibility(View.GONE);
        view.findViewById(R.id.edtInviteUsername).setVisibility(View.GONE);
        TextView tvHint = UIUtils.getView(view, R.id.tvHint);
        final EditText editInputText = UIUtils.getView(view, R.id.editInputText);
        tvHint.setText(R.string.hint_search);
        editInputText.setHint(R.string.title_search);

        new AlertDialog.Builder(mContext)
                .setTitle(R.string.title_search)
                .setView(view)
                .setNegativeButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.title_search, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchQuery = editInputText.getText().toString().toLowerCase().trim();
                        if (searchQuery.isEmpty()) {
                            searchQuery = null;
                            MyToast.toast(mContext, R.string.toast_word_is_empty);
                            dialogSearch();
                            return;
                        }
                        tvSearchQuery.setText(searchQuery);
                        mHeaderSearch.setVisibility(View.VISIBLE);
                        isLoading = true;
                        mAdapter.getList().clear();
                        mAdapter.notifyDataSetChanged();
                        offset = 0;
                        isListEnd = false;
                        prgLoading.setVisibility(View.VISIBLE);
                        new LoadUsers().execute();
                    }
                })
                .show();
    }


    @Override
    public void close() {
        AdHelper.onCloseActivity(this);
    }


    @Override
    protected void onDestroy() {
        if (mAdapter != null)
            mAdapter.onDestroy();
        super.onDestroy();
    }
}
