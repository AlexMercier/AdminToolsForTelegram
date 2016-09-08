package com.madpixels.tgadmintools.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.Utils;
import com.madpixels.apphelpers.ui.ActivityExtended;
import com.madpixels.apphelpers.ui.ProgressDialogBuilder;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.adapters.AdapterKickedUsers;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.ChatParticipantBan;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import libs.AdHelper;

/**
 * Created by Snake on 21.02.2016.
 */
public class ActivityBanList extends ActivityExtended {

    // ArrayList<ChatParticipantBan> mList = new ArrayList<>();
    AdapterKickedUsers mAdapter;
    boolean isListEnd = false, isLoading = false;
    int offset = 0;
    int channel_id = 0;
    long chat_id;
    int chatType;

    private int mTotalKicked = 0;

    ListView mListViewKicked;
    ProgressBar prgLoading;
    TextView tvTotalKicked;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("type", chatType);
        outState.getLong("chat_id", chat_id);
        outState.putInt("channel_id", channel_id);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banlist);
        setTitle(R.string.title_ban_list);
        Toolbar tb = UIUtils.setToolbarWithBackArrow(this, R.id.toolbar);
        AdHelper.showBanner(findViewById(R.id.adView));

        Bundle b = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();


        chatType = b.getInt("type", 0);
        if (!TgUtils.isGroup(chatType))
            channel_id = b.getInt("channel_id", 0);
        chat_id = b.getLong("chat_id", 0);

        setTitle(R.string.title_activity_banlist);

        mListViewKicked = getView(R.id.listViewBanned);
        prgLoading = getView(R.id.progressBar);
        View listHeader = getLayoutInflater().inflate(R.layout.header_list_count, null);
        mListViewKicked.addHeaderView(listHeader, null, false);
        tvTotalKicked = getView(R.id.tvListCount);
        tvTotalKicked.setText("-");

        mAdapter = new AdapterKickedUsers(this);
        mListViewKicked.setAdapter(mAdapter);
        mListViewKicked.setOnScrollListener(onScrollListener);
        mListViewKicked.setOnItemClickListener(onItemClickListener);
        registerForContextMenu(mListViewKicked);

        if (TgUtils.isGroup(chatType))
            getBannedInGroup();
        else
            getBannedInSuperGroup();
    }


    final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.showContextMenu();
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo cInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int pos = cInfo.position - mListViewKicked.getHeaderViewsCount();
        menu.add(0, 1, 0, R.string.action_remove_from_ban);
        menu.add(0, 2, 0, R.string.action_return_to_chat);

        ChatParticipantBan user = mAdapter.getItem(pos);
        if(!user.chatParticipant.user.username.isEmpty())
            menu.add(0, 3, 0, R.string.action_open_userninfo);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemPosition = info.position - mListViewKicked.getHeaderViewsCount();
        final TdApi.ChatParticipant user = mAdapter.getItem(itemPosition).chatParticipant;

        switch (item.getItemId()) {
            case 1:
                removeFromBlacklist(itemPosition);
                break;
            case 2:
                restoreUserToChat(itemPosition);
                break;
            case 3:
                String link = "https://telegram.me/"+user.user.username;
                Utils.openUrl(link, mContext);
                break;
        }


        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, R.string.action_unban_all);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                dialogConfirmUnbanAll();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    final AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            final int headersCount = mListViewKicked.getHeaderViewsCount();
            if (!isLoading && !isListEnd && totalItemCount > headersCount && firstVisibleItem + visibleItemCount == totalItemCount) {
                prgLoading.setVisibility(View.VISIBLE);
                if (TgUtils.isGroup(chatType))
                    getBannedInGroup();
                else
                    getBannedInSuperGroup();
            }
        }
    };

    private void getBannedInSuperGroup() {
        isLoading = true;
        TgH.TG().send(new TdApi.GetChannelParticipants(channel_id, new TdApi.ChannelParticipantsKicked(), offset, 200), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                MyLog.log(object.toString());
                if (object.getConstructor() == TdApi.ChatParticipants.CONSTRUCTOR) {
                    TdApi.ChatParticipants users = (TdApi.ChatParticipants) object;

                    offset += users.participants.length;
                    if (users.participants.length < 200)
                        isListEnd = true;
                    if (mAdapter.getCount() == 0) {
                        setTotal(users.totalCount);
                    }
                    ArrayList<ChatParticipantBan> chatParticipantBans = new ArrayList<ChatParticipantBan>(users.participants.length);
                    for (TdApi.ChatParticipant cp : users.participants) {
                        chatParticipantBans.add(new ChatParticipantBan(cp).setChatId(chat_id));
                    }

                    //List<TdApi.ChatParticipant> kickedList = Arrays.asList(users.participants);
                    onListUpdate(chatParticipantBans);
                }
            }
        });
    }

    private void getBannedInGroup() {
        isLoading = true;
        new Thread() {
            @Override
            public void run() {
                ArrayList kickedUsers = DBHelper.getInstance().getBanned(chat_id, offset);
                if (offset == 0) {
                    int total = DBHelper.getInstance().getBannedCount(chat_id);
                    setTotal(total);
                }
                offset += kickedUsers.size();
                if (kickedUsers.size() < 100)
                    isListEnd = true;

                onListUpdate(kickedUsers);
            }
        }.start();
    }


    private void onListUpdate(@Nullable final List kickedUsers) {
        onUiThread(new Runnable() {
            @Override
            public void run() {
                if (kickedUsers != null)
                    mAdapter.getList().addAll(kickedUsers);
                mAdapter.notifyDataSetChanged();
                prgLoading.setVisibility(View.INVISIBLE);
                isLoading = false;
            }
        });
    }

    /*
        Methods
     */

    private void setTotal(final int totalCount) {
        mTotalKicked = totalCount;
        onUiThread(new Runnable() {
            @Override
            public void run() {
                tvTotalKicked.setText(String.valueOf(totalCount));
            }
        });
    }

    private void removeFromBlacklist(final int position) {
        final TdApi.ChatParticipant user = mAdapter.getItem(position).chatParticipant;
        if (TgUtils.isGroup(chatType)) {
            removeFromLocalBlackList(user);
        } else {
            unban(user, position, null);
        }

    }

    private void unban(final TdApi.ChatParticipant chatParticipant, final int index, @Nullable final Client.ResultHandler callback) {
        TgH.TG().send(
                new TdApi.ChangeChatParticipantRole(chat_id, chatParticipant.user.id, new TdApi.ChatParticipantRoleLeft()), new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        MyLog.log(object.toString());
                        if (object.getConstructor() == TdApi.Ok.CONSTRUCTOR) {
                            DBHelper.getInstance().removeBannedUser(chat_id, chatParticipant.user.id);
                            if (index > -1) {
                                MyToast.toast(mContext, R.string.toast_user_unblacklisted);
                                mAdapter.getList().remove(index);
                                onListUpdate(null);
                            }
                        }
                        if (callback != null)
                            callback.onResult(object);
                    }
                });
    }

    private void removeFromLocalBlackList(TdApi.ChatParticipant user) {
        DBHelper.getInstance().removeBannedUser(chat_id, user.user.id);
        DBHelper.getInstance().removeUserFromAutoKick(chat_id, user.user.id);

        setTotal(mTotalKicked-1);
    }

    void restoreUserToChat(final int position) {
        TdApi.ChatParticipant user = mAdapter.getItem(position).chatParticipant;
        if (TgUtils.isGroup(chatType)) {
            removeFromLocalBlackList(user);
        } else
            DBHelper.getInstance().removeBannedUser(chat_id, user.user.id);

        // Invite user back to chat:
        TgH.TG().send(new TdApi.AddChatParticipant(chat_id, user.user.id, 1), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                MyLog.log(object.toString());

                MyToast.toast(mContext, R.string.toast_user_was_restored_to_chat);
                mAdapter.getList().remove(position);
                onListUpdate(null);
            }
        });
    }


    private void dialogConfirmUnbanAll() {
        if (mAdapter.isEmpty()) {
            MyToast.toastL(mContext, R.string.toast_banlist_empty);
            return;
        }

        String msg = getString(R.string.msg_confirm_unban_all);
        if (TgUtils.isSuperGroup(chatType))
            msg += "\n" + getString(R.string.msg_unban_all_supergroup);



        new AlertDialog.Builder(mContext)
                .setTitle(R.string.title_activity_banlist)
                .setMessage(msg)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.btnContinue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        unbanAll();
                    }
                })
                .show();
    }

    private void unbanAll() {
        final UnBanAllProcess mUnBanAllProcess = new UnBanAllProcess();
        final GetAllBannedProcess getBannedProcess = new GetAllBannedProcess();

        final ProgressDialog pd = new ProgressDialogBuilder(mContext)
                .setTitle("Loading")
                .setMessage("Please wait")
                .setIndeterminate(true)
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getBannedProcess.cancel();
                        mUnBanAllProcess.cancel();
                    }
                }).build();
                pd.show();


        if(TgUtils.isGroup(chatType)){
            new Thread(){
                @Override
                public void run() {
                    DBHelper.getInstance().clearBanList(chat_id);
                    onUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            mAdapter.getList().clear();
                            mAdapter.notifyDataSetChanged();
                            setTotal(0);
                            MyToast.toastL(mContext, "List cleared");
                        }
                    });
                }
            }.start();
            return;
        }

        Runnable onGetAllComplete = new Runnable() {
            @Override
            public void run() {
                mUnBanAllProcess.users = getBannedProcess.bannedFullList;
                mUnBanAllProcess.onOperationComplete = new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        mAdapter.getList().clear();
                        mAdapter.notifyDataSetChanged();
                        setTotal(0);
                        MyToast.toastL(mContext, "Ban-list cleared");
                    }
                };
                mUnBanAllProcess.start();
            }
        };
        if(TgUtils.isSuperGroup(chatType)) {
            getBannedProcess.onLoadComplete = onGetAllComplete;
            getBannedProcess.get();
        }
    }

    private class GetAllBannedProcess {
        //public int channel_id; //chat_id
        int offset = 0;
        ArrayList<TdApi.ChatParticipant> bannedFullList = new ArrayList<>();
        Runnable onLoadComplete;
        private boolean isCancel = false;


        void get() {
            if (isCancel) return;
            TgH.TG().send(new TdApi.GetChannelParticipants(channel_id, new TdApi.ChannelParticipantsKicked(), offset, 200), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    MyLog.log(object.toString());
                    if (object.getConstructor() == TdApi.ChatParticipants.CONSTRUCTOR) {
                        TdApi.ChatParticipants users = (TdApi.ChatParticipants) object;

                        offset += users.participants.length;
                        List<TdApi.ChatParticipant> kickedList = Arrays.asList(users.participants);
                        bannedFullList.addAll(kickedList);

                        if (users.participants.length == 200)
                            get(); // рекурсия
                        else {
                            onAllGetComplete();
                        }

                    }
                }
            });
        }

        private void onAllGetComplete() {
            if (isCancel) return;
            onUiThread(onLoadComplete);
        }

        public void cancel() {
            isCancel = true;
        }


    }

    private class UnBanAllProcess {
        ArrayList<TdApi.ChatParticipant> users;
        Runnable onOperationComplete;
        private boolean isCancel = false;

        void start() {
            next();
        }

        private void next() {
            if (isCancel)
                return;
            if (users.isEmpty()) {
                DBHelper.getInstance().deleteGroup(chat_id);
                onUiThread(onOperationComplete);
                return;
            }
            TdApi.ChatParticipant user = users.remove(0);
            unban(user, -1, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if (TgUtils.isError(object)){
                        MyToast.toast(mContext, object.toString());
                    }
                    next();
                }
            });
        }

        public void cancel() {
            isCancel = true;
        }
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
