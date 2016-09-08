package com.madpixels.tgadmintools.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.services.ServiceAutoKicker;
import com.madpixels.tgadmintools.services.ServiceUnbanTask;
import com.madpixels.tgadmintools.utils.AdminUtils;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

    ListView mListViewUsers;
    AdapterChatUsers mAdapter;
    ProgressBar prgLoading;
    boolean isAdmin = false;// main admin
    private String additionalType=null;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("type", chatType);
        outState.getLong("chat_id", chat_id);
        outState.putString("title", chatTitle);
        if(additionalType!=null)
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
        if(b.containsKey("filter"))
            additionalType = b.getString("filter");
        if (chatType == TdApi.ChannelChatInfo.CONSTRUCTOR) {
            //chat_id = b.getLong("chat_id");
            channel_id = b.getInt("channel_id");
        } else {
            group_id = b.getInt("group_id");
        }

        mListViewUsers = getView(R.id.listViewUsers);
        mListViewUsers.setOnScrollListener(onScrollListener);
        mAdapter = new AdapterChatUsers(mContext);
        mListViewUsers.setAdapter(mAdapter);
        prgLoading = getView(R.id.progressBar);
        registerForContextMenu(mListViewUsers);
        mListViewUsers.setOnItemClickListener(onItemClickListener);

        // для начала еще раз считаем инфу о чате
        getChat();
        // loadUsers();
    }

    private void getChat() {
        TdApi.TLFunction f = new TdApi.GetChat(chat_id);
        TgH.send(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TdApi.Chat chat = (TdApi.Chat) object;
                if (!TgUtils.isGroup(chatType)) {
                    TdApi.ChannelChatInfo superGroup = (TdApi.ChannelChatInfo) chat.type;
                    if (superGroup.channel.role.getConstructor() == TdApi.ChatParticipantRoleAdmin.CONSTRUCTOR)
                        isAdmin = true;
                } else {
                    TdApi.GroupChatInfo groupInfo = (TdApi.GroupChatInfo) chat.type;
                    if (groupInfo.group.role.getConstructor() == TdApi.ChatParticipantRoleAdmin.CONSTRUCTOR)
                        isAdmin = true;
                }

                MyLog.log(object.toString());
                loadUsers();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 101, 0, R.string.action_remove_deleted_users);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 101:
                dialogAcceptRemoveUsers();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        TdApi.ChatParticipant user = mAdapter.getItem(info.position - mListViewUsers.getHeaderViewsCount());
        boolean isUserPrivileged = TgUtils.isUserPrivileged(user.role.getConstructor());
        if (isAdmin || !isUserPrivileged) {  // нельзя кикать админов несуперадмину
            menu.add(0, 1, 0, R.string.action_ban_user);
            menu.add(0, 3, 0, R.string.action_remove_user_from_chat);
            if (TgH.selfProfileId == user.user.id) {
                menu.getItem(0).setEnabled(false);
                menu.getItem(1).setEnabled(false);
            }
        }

        if (isAdmin && !isUserPrivileged)
            menu.add(0, 2, 0, R.string.action_set_as_admin);
        if (isAdmin && isUserPrivileged) {
            menu.add(0, 4, 0, R.string.revoke_user_admin);
            if (TgH.selfProfileId == user.user.id)
                menu.findItem(4).setEnabled(false);
        }


        if (BuildConfig.DEBUG)
            menu.add(0, 99, 0, "test unban");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo cInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = cInfo.position - mListViewUsers.getHeaderViewsCount();
        TdApi.ChatParticipant user = mAdapter.getItem(pos);

        switch (item.getItemId()) {
            case 1:
                showDialogBanUser(user);
                break;
            case 2:
                if(TgUtils.isSuperGroup(chatType))
                    dialogSetUserRole(user);
                else
                    checkGroupPermissions(user);
                break;
            case 3:
                removeUserFromChat(user);
                break;
            case 4:
                //TODO for base groups not tested
                setUserRole(user, new TdApi.ChatParticipantRoleGeneral());
                break;
            case 99:
                DBHelper.getInstance().getExpairedBanList();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void checkGroupPermissions(final TdApi.ChatParticipant user) {
        TgH.sendOnUi(new TdApi.GetGroup(group_id), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                TdApi.Group group = (TdApi.Group) object;
                if(!group.anyoneCanEdit)
                    setUserRole(user, new TdApi.ChatParticipantRoleEditor());
                else{
                    new AlertDialog.Builder(mContext)
                            .setTitle("Set user role")
                            .setMessage(R.string.dialog_group_all_are_admins)
                            .setNegativeButton(R.string.cancel, null)
                            .setPositiveButton(R.string.role_action_switch_continue, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean allCanManage = false;
                                    TdApi.TLFunction f = new TdApi.ToggleGroupEditors(group_id, allCanManage);
                                    TgH.sendOnUi(f, new Client.ResultHandler() {
                                        @Override
                                        public void onResult(TdApi.TLObject object) {
                                            if (object.getConstructor() == TdApi.Group.CONSTRUCTOR)
                                                setUserRole(user, new TdApi.ChatParticipantRoleEditor());
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

    private void dialogSetUserRole(final TdApi.ChatParticipant user) {
        View view = UIUtils.inflate(mContext, R.layout.dialog_set_moder);
        new AlertDialog.Builder(mContext)
                .setView(view)
                .setTitle("Set user role")
                .show();

        RadioButton rbAdmin = UIUtils.getView(view, R.id.rbAdmin); rbAdmin.setVisibility(View.VISIBLE);
        RadioButton rbModer = UIUtils.getView(view, R.id.rbModer);
        RadioButton rbEditor = UIUtils.getView(view, R.id.rbEditor);
        View.OnClickListener rbClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TdApi.ChatParticipantRole role = null;
                switch (v.getId()){
                    case R.id.rbAdmin:
                        role = new TdApi.ChatParticipantRoleAdmin();
                        break;
                    case R.id.rbModer:
                        role = new TdApi.ChatParticipantRoleModerator();
                        break;
                    case R.id.rbEditor:
                        role = new TdApi.ChatParticipantRoleEditor();
                        break;
                }
                setUserRole(user, role);
            }
        };
        UIUtils.setBatchClickListener(rbClick, rbAdmin,rbModer,rbEditor);

    }

    private void setUserRole(final TdApi.ChatParticipant user, final TdApi.ChatParticipantRole role) {
        TdApi.TLFunction f = new TdApi.ChangeChatParticipantRole(chat_id, user.user.id, role);
        TgH.sendOnUi(f, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if(TgUtils.isOk(object)) {
                    user.role = role;
                    mAdapter.notifyDataSetChanged();
                    MyToast.toast(mContext, R.string.toast_user_role_changed);
                }else{
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

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int headersCount = mListViewUsers.getHeaderViewsCount();
            if (!isListEnd && !isLoading && totalItemCount > headersCount && firstVisibleItem + visibleItemCount == totalItemCount) {
                if (TgUtils.isSuperGroup(chatType)) {
                    getSupergroupUsers();
                }
            }
        }
    };


    void loadUsers() {
        isLoading = true;

        switch (chatType) {
            case TdApi.ChannelChatInfo.CONSTRUCTOR:
                getSupergroupUsers();
                break;

            case TdApi.GroupChatInfo.CONSTRUCTOR:
                getGroupUsers();
                break;
        }
    }

    private void getSupergroupUsers() {
        final int getCount = mAdapter.isEmpty() ? 25 : 200;
        TdApi.ChannelParticipantsFilter f;
        if(additionalType==null)
            f = new TdApi.ChannelParticipantsRecent();
        else
            f = new TdApi.ChannelParticipantsAdmins();

        TgH.TG().send(new TdApi.GetChannelParticipants(channel_id, f, offset, getCount), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                //MyLog.log(object.toString());
                if (object.getConstructor() == TdApi.ChatParticipants.CONSTRUCTOR) {
                    TdApi.ChatParticipants users = (TdApi.ChatParticipants) object;

                    offset += users.participants.length;
                    if (users.participants.length < getCount)
                        isListEnd = true;
                    if (mAdapter.getCount() == 0) {
                        /// setTotal(users.totalCount);
                    }
                    List<TdApi.ChatParticipant> usersList = Arrays.asList(users.participants);
                    mAdapter.getList().addAll(usersList);
                    onUiThread(updateList);
                } else {
                    //TODO error
                }
            }
        });
    }

    void getGroupUsers() {
        TgH.TG().send(new TdApi.GetGroupFull(group_id), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                // MyLog.log(object.toString());
                if (object.getConstructor() == TdApi.GroupFull.CONSTRUCTOR) {
                    TdApi.GroupFull group = (TdApi.GroupFull) object;
                    isListEnd = true;

                    List<TdApi.ChatParticipant> users;

                    if(additionalType==null){
                        users = Arrays.asList(group.participants);
                    }else
                    {
                        users = new ArrayList<>();
                        for(TdApi.ChatParticipant cp:group.participants){
                            if(TgUtils.isUserPrivileged(cp.role.getConstructor()))
                                users.add(cp);
                        }
                    }

                    mAdapter.getList().addAll(users);
                    onUiThread(updateList);
                }
            }
        });
    }


    final Runnable updateList = new Runnable() {
        @Override
        public void run() {
            mAdapter.notifyDataSetChanged();
            prgLoading.setVisibility(View.INVISIBLE);
            isLoading = false;
        }
    };

    void removeUserFromChat(TdApi.ChatParticipant user){
        AdminUtils.kickUser(chat_id, user.user.id, new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                MyToast.toastL(mContext, TgUtils.isOk(object)?"User was removed":"Remove user error");
            }
        });
    }

    /*
    ==== ban section ===========
     */
    private void showDialogBanUser(final TdApi.ChatParticipant user) {
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

        final Button btnSetValue = UIUtils.getView(view, R.id.btnSetValue);
        final Button btnBanSelectTime = UIUtils.getView(view, R.id.btnBanSelectTime);
        final Button btnBanSelectDate = UIUtils.getView(view, R.id.btnBanSelectDate);
        final String[] timeValues = getResources().getStringArray(R.array.ban_times);

        btnSetValue.setText("1 "+timeValues[1]); //TODO english
        if(Sets.getBoolean("ban.default.forever", true)){
            rbBanForever.setChecked(true);
            layerTimebanOptions.setVisibility(View.GONE);
        }else{
            rbBanTime.setChecked(true);
        }


        int defTimeVal = Sets.getInteger(Const.BAN_DEFAULT_TIME_VALUE, 1);
        switch (Sets.getInteger(Const.BAN_DEFAULT_TIME, Calendar.MINUTE)){
            case Calendar.MINUTE:
                btnSetValue.setText(defTimeVal+" "+timeValues[0]);
                mUnbanTargetMillis.add(Calendar.MINUTE, defTimeVal);
                break;
            case Calendar.HOUR_OF_DAY:
                btnSetValue.setText(defTimeVal+" "+timeValues[1]);
                mUnbanTargetMillis.add(Calendar.HOUR_OF_DAY, defTimeVal);
                break;
            case Calendar.DAY_OF_MONTH:
                btnSetValue.setText(defTimeVal+" "+timeValues[2]);
                mUnbanTargetMillis.add(Calendar.DAY_OF_MONTH, defTimeVal);
                break;
            case Calendar.MONTH:
                btnSetValue.setText(defTimeVal+" "+timeValues[3]);
                mUnbanTargetMillis.add(Calendar.MONTH, defTimeVal);
                break;
        }

        String time = Utils.TimestampToDateFormat(mUnbanTargetMillis.getTimeInMillis() / 1000, "HH:mm");
        String date = Utils.TimestampToDateFormat(mUnbanTargetMillis.getTimeInMillis() / 1000, "d MMMM, yyyy");
        btnBanSelectTime.setText(time);
        btnBanSelectDate.setText(date);

        AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("Ban user")
                .setView(view)
                .setPositiveButton("Ban", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long ban_age = rbBanForever.isChecked() ? 0 : mUnbanTargetMillis.getTimeInMillis() - System.currentTimeMillis();
                        boolean isPublishBanReason = chkPublishBanReason.isChecked();
                        boolean isReturnOnUnban = chkBanReturnToChat.isChecked();
                        String banText = edtBanText.getText().toString().trim();
                        banUser(user, ban_age, isPublishBanReason, isReturnOnUnban, banText);
                        Sets.set("ban.default.forever", rbBanForever.isChecked());
                    }
                })
                .setNegativeButton(R.string.cancel, null)
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
                                unbanTS = unbanTS / 1000;
                                String time = Utils.TimestampToDateFormat(unbanTS, "HH:mm");
                                String date = Utils.TimestampToDateFormat(unbanTS, "d MMMM, yyyy");

                                btnBanSelectTime.setText(time);
                                btnBanSelectDate.setText(date);
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
                                unbanTS = unbanTS / 1000;
                                String date = Utils.TimestampToDateFormat(unbanTS, "d MMMM, yyyy");
                                btnBanSelectDate.setText(date);
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
                                unbanTS = unbanTS / 1000;
                                String time = Utils.TimestampToDateFormat(unbanTS, "HH:mm");
                                btnBanSelectTime.setText(time);
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

    private void banUser(final TdApi.ChatParticipant user, final long ban_age, final boolean isPublishBanReason, final boolean isReturnOnUnban, final String banText) {
        if (TgUtils.isGroup(chatType)) {
            DBHelper.getInstance().addUserToAutoKick(chat_id, user.user.id);
        }
        AdminUtils.kickUser(chat_id, user.user.id, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        // MyLog.log(object.toString());
                        if (TgUtils.isOk(object)) {
                            onUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.getList().remove(user);
                                    mAdapter.notifyDataSetChanged();
                                }
                            });

                            saveLocalBanList(user, ban_age, isPublishBanReason, isReturnOnUnban, banText);
                            LogUtil.logBanUserManually(chat_id, chatType, chatTitle, user, banText, ban_age);
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

    private void saveLocalBanList(TdApi.ChatParticipant chatParticipant, long ban_age, boolean isPublishBanReason, boolean isReturnOnUnban, final String banText) {
        int from_id = chatType == TdApi.ChannelChatInfo.CONSTRUCTOR ? channel_id : group_id; // группа или супергруппа id.
        DBHelper.getInstance().addToBanList(chatParticipant.user, chat_id, chatType, from_id, ban_age, isReturnOnUnban, banText);
        if(ban_age>0) //TODO проверить почему если >0 мб всегда надо?
            ServiceUnbanTask.registerTask(mContext);
        if (isPublishBanReason && !banText.isEmpty()) {
            //if (TgUtils.isSuperGroup(chatType)) {
                TdApi.InputMessageText text = new TdApi.InputMessageText();
                String username = chatParticipant.user.username.isEmpty()? // @username либо имя пользователя
                        chatParticipant.user.firstName+" "+chatParticipant.user.lastName :
                        " @"+chatParticipant.user.username;
                String strText = getString(R.string.text_publish_banreason, username, banText);
                text.text = strText;
                TdApi.TLFunction f = new TdApi.SendMessage(chat_id, 0, false, true, true, new TdApi.ReplyMarkupNone(), text);
                TgH.TG().send(f, new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        MyLog.log(object.toString());
                    }
                });
           // }
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

    void dialogAcceptRemoveUsers() {
        new AlertDialog.Builder(mContext)
                .setTitle("Clear deleted accounts")
                .setMessage( R.string.deleted_accounts_hint)
                .show();
        if(true)
            return;
    /*
        new AlertDialog.Builder(mContext)
                .setTitle("Deleted accounts")
                .setMessage("Scan for remove deleted accounts from chat.\n" +
                        "Continue?")
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.btnContinue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new RemoveDeletedUsersProcess().start();
                    }
                })
                .show();
                */
    }

    /*
    private class RemoveDeletedUsersProcess {
        int offset = 0;
        //int removed = 0;
        ArrayList<TdApi.ChatParticipant> deletedUsers = new ArrayList<>();
        int errorsCount = 0;
        ProgressDialog prgDialog;
        boolean isCancelled = false;

        void start() {
            showLoading("Scanning...please wait");
            process();
        }

        void showLoading(final String msg) {
            prgDialog = new ProgressDialogBuilder(mContext)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isCancelled = true;
                        }
                    })
                    .show();
        }

        void process() {
            TgH.send(new TdApi.GetChannelParticipants(channel_id, new TdApi.ChannelParticipantsRecent(), offset, 200), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (object.getConstructor() != TdApi.ChatParticipants.CONSTRUCTOR) {
                        error(object);
                        return;
                    }
                    TdApi.ChatParticipants users = (TdApi.ChatParticipants) object;
                    offset += users.participants.length;
                    for (TdApi.ChatParticipant user : users.participants) {
                        if (isCancelled) return;
                        if (user.user.type.getConstructor() == TdApi.UserTypeDeleted.CONSTRUCTOR) {
                            deletedUsers.add(user);
                            MyLog.log("deleted user: " + user.toString());
                        }
                    }

                    if (isCancelled) return;
                    if (users.participants.length == 200)
                        process(); // next loop
                    else
                        onUiThread(scanComplete);
                }
            });
        }

        Runnable scanComplete = new Runnable() {
            @Override
            public void run() {
                prgDialog.dismiss();
                if (deletedUsers.isEmpty()) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Deleted accounts")
                            .setMessage("This chat has no deleted accounts")
                            .setNegativeButton(R.string.close, null)
                            .setCancelable(false)
                            .show();
                    return;
                }

                new AlertDialog.Builder(mContext)
                        .setTitle("Deleted accounts")
                        .setMessage(getString(R.string.founded_deleted_users, deletedUsers.size()))
                        .setNegativeButton(R.string.cancel, null)
                        .setCancelable(false)
                        .setPositiveButton(R.string.btnContinue, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showLoading("Deleting...please wait");
                                removeUserNext();
                            }
                        })
                        .show();
            }
        };


        private void removeUserNext() {
            if (isCancelled) return;
            if (deletedUsers.isEmpty()) onUiThread(onComplete);
            TdApi.ChatParticipant user = deletedUsers.remove(0);

            final TdApi.TLFunction f = new TdApi.ChangeChatParticipantRole(chat_id, user.user.id, new TdApi.ChatParticipantRoleLeft());
            TgH.TG().send(f, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (TgUtils.isError(object))
                        errorsCount++;
                    removeUserNext();
                }
            });
        }

        Runnable onComplete = new Runnable() {
            @Override
            public void run() {
                prgDialog.dismiss();
                new AlertDialog.Builder(mContext)
                        .setTitle("Deleted users")
                        .setMessage("Operation complete.\n" + (errorsCount > 0 ? "Error " + errorsCount : ""))
                        .setPositiveButton("Close", null)
                        .setCancelable(false)
                        .show();
            }
        };


        private void error(TdApi.TLObject object) {
            MyToast.toast(mContext, object.toString());
            onUiThread(new Runnable() {
                @Override
                public void run() {
                    prgDialog.dismiss();
                }
            });
        }
    }
    */

    @Override
    public void close() {
        AdHelper.onCloseActivity(this);
    }


    @Override
    protected void onDestroy() {
        if(mAdapter!=null)
            mAdapter.onDestroy();
        super.onDestroy();
    }
}
