package com.madpixels.tgadmintools.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.adapters.AdapterChatUsers;
import com.madpixels.tgadmintools.adapters.AdapterChatUsersLocal;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Snake on 30.12.2016.
 */

public class FragmentSelectUsers extends DialogFragment {

    private Callback onUserSelected;

    ListView lvUsers, lvListMuted;
    AdapterChatUsers mAdapter;
    AdapterChatUsersLocal mAdapterMuted;

    ProgressBar progressBarLoadingChats;
    TextView tvStatus;
    public int chatType;
    public int channelId;
    Button btnShowMuted, btnShowAll;

    int offset = 0;
    boolean isListEnd = false, isLoading = false, isLoadingMuted = false, isListEndMuted = false;
    public int groupId;
    public long chatlId;
    MyToast myToast;

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        if (mAdapter != null)
            mAdapter.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void setOnUserSelected(Callback onUserSelected) {
        this.onUserSelected = onUserSelected;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_select_users, null);
        lvUsers = UIUtils.getView(v, R.id.lvList);
        lvListMuted = UIUtils.getView(v, R.id.lvListAdded);
        lvListMuted.setVisibility(View.GONE);

        progressBarLoadingChats = UIUtils.getView(v, R.id.progressBarLoadingChats);
        tvStatus = UIUtils.getView(v, R.id.tvLoading);
        btnShowMuted = UIUtils.getView(v, R.id.btnShowMuted);
        btnShowAll = UIUtils.getView(v, R.id.btnShowAll);

        btnShowMuted.setOnClickListener(onClickListener);
        btnShowAll.setOnClickListener(onClickListener);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        myToast = new MyToast(getActivity());
        mAdapter = new AdapterChatUsers(getActivity());
        lvUsers.setAdapter(mAdapter);
        lvUsers.setOnScrollListener(onScrollListener);
        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TdApi.ChatMember chatMember = mAdapter.getItem(position);
                onUserSelected.onResult(chatMember);
                myToast.fast(R.string.toast_user_added_to_muted);
                mAdapter.getList().remove(chatMember);
                mAdapter.notifyDataSetChanged();
            }
        });

        lvListMuted.setOnScrollListener(onScrollListenerMuted);
        lvListMuted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterChatUsersLocal.ChatMemberUser member = (AdapterChatUsersLocal.ChatMemberUser) mAdapterMuted.getItem(position);
                onUserSelected.onResult(member);
                myToast.fast(R.string.toast_user_removed_from_muted);
                mAdapterMuted.getList().remove(member);
                mAdapterMuted.notifyDataSetChanged();
                if (mAdapterMuted.isEmpty())
                    tvStatus.setText(R.string.text_no_muted_users);
            }
        });

        if (TgUtils.isSuperGroup(chatType))
            loadSuperGroupMembers();
        else {
            getGroupUsers();
        }
    }


    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnShowMuted:
                    if (lvListMuted.getVisibility() == View.VISIBLE)
                        return;

                    btnShowMuted.setBackgroundResource(R.drawable.btn_switch_lists_active);
                    btnShowAll.setBackgroundResource(R.drawable.btn_switch_lists);

                    lvUsers.setVisibility(View.GONE);
                    lvListMuted.setVisibility(View.VISIBLE);

                    Animation right = AnimationUtils.loadAnimation(getActivity(), R.anim.to_right_side);
                    lvListMuted.startAnimation(right);


                    if (mAdapterMuted == null) {
                        mAdapterMuted = new AdapterChatUsersLocal(getActivity());
                        lvListMuted.setAdapter(mAdapterMuted);

                    }
                    getMutedUsers();
                    break;
                case R.id.btnShowAll:
                    if (lvUsers.getVisibility() == View.VISIBLE)
                        return;
                    btnShowMuted.setBackgroundResource(R.drawable.btn_switch_lists);
                    btnShowAll.setBackgroundResource(R.drawable.btn_switch_lists_active);

                    lvUsers.setVisibility(View.VISIBLE);
                    lvListMuted.setVisibility(View.GONE);
                    Animation leftSwipe = AnimationUtils.loadAnimation(getActivity(), R.anim.to_left_side);
                    lvUsers.startAnimation(leftSwipe);

                    mAdapter.notifyDataSetChanged();
                    //tvLoading.setVisibility(View.GONE);
                    break;
            }

        }
    };


    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int headersCount = lvUsers.getHeaderViewsCount();
            if (!isListEnd && !isLoading && totalItemCount > headersCount && firstVisibleItem + visibleItemCount == totalItemCount) {
                if (TgUtils.isSuperGroup(chatType)) {
                    isLoading = true;
                    loadSuperGroupMembers();
                }
            }
        }
    };

    AbsListView.OnScrollListener onScrollListenerMuted = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int headersCount = lvListMuted.getHeaderViewsCount();
            if (!isListEndMuted && !isLoadingMuted && totalItemCount > headersCount && firstVisibleItem + visibleItemCount == totalItemCount) {
                isLoadingMuted = true;
                getMutedUsers();
            }
        }
    };


    void getGroupUsers() {
        progressBarLoadingChats.setVisibility(View.VISIBLE);
        isLoading = true;
        TgH.sendOnUi(new TdApi.GetGroupFull(groupId), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.GroupFull.CONSTRUCTOR) {
                    TdApi.GroupFull group = (TdApi.GroupFull) object;
                    isListEnd = true;

                    List<TdApi.ChatMember> users = Arrays.asList(group.members);


                    mAdapter.getList().addAll(users);
                    mAdapter.notifyDataSetChanged();
                    progressBarLoadingChats.setVisibility(View.GONE);
                    tvStatus.setText(R.string.label_select_user_to_mute);
                    isLoading = false;
                } else {
                    MyToast.toast(getActivity(), "Error loading chat members");
                    dismiss();
                }
            }
        });
    }


    private void loadSuperGroupMembers() {

        final int getCount = mAdapter.isEmpty() ? 25 : 200;
        TdApi.ChannelMembersFilter f = new TdApi.ChannelMembersRecent();

        TgH.sendOnUi(new TdApi.GetChannelMembers(channelId, f, offset, getCount), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.ChatMembers.CONSTRUCTOR) {
                    TdApi.ChatMembers users = (TdApi.ChatMembers) object;

                    offset += users.members.length;
                    if (users.members.length < getCount)
                        isListEnd = true;
                    if (mAdapter.getCount() == 0) {
                        /// setTotal(users.totalCount);
                    }
                    List<TdApi.ChatMember> usersList = Arrays.asList(users.members);
                    mAdapter.getList().addAll(usersList);
                    mAdapter.notifyDataSetChanged();
                    progressBarLoadingChats.setVisibility(View.GONE);
                    tvStatus.setText(R.string.label_select_user_to_mute);
                    isLoading = false;

                } else {
                    MyToast.toast(getActivity(), "Error loading chat members");
                    dismiss();
                }
            }
        });
    }

    void runOnUiThread(Runnable runnable) {
        if (isAdded() && getActivity() != null && !getActivity().isFinishing()) {
            getActivity().runOnUiThread(runnable);
        }
    }

    int offsetMuted = 0;

    void getMutedUsers() {
        isLoadingMuted = true;


        new Thread() {
            @Override
            public void run() {
                final int count = BuildConfig.DEBUG ? 10 : 100;
                final ArrayList users = DBHelper.getInstance().getMutedUsers(chatlId, offsetMuted, count);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (users != null) {
                            mAdapterMuted.getList().addAll(users);
                            mAdapterMuted.notifyDataSetChanged();
                            tvStatus.setText(R.string.label_select_user_to_remove_mute);
                            offsetMuted += users.size();
                            if (users.size() < count)
                                isListEndMuted = true;
                        }
                        if (users == null || users.isEmpty()) {
                            if (mAdapterMuted.isEmpty())
                                tvStatus.setText(R.string.text_no_muted_users);
                            isListEndMuted = true;
                        }

                        isLoadingMuted = false;
                    }
                });
            }
        }.start();


    }

}
