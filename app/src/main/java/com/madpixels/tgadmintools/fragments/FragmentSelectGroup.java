package com.madpixels.tgadmintools.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.adapters.AdapterChats;
import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;

/**
 * Created by Snake on 29.12.2016.
 */

public class FragmentSelectGroup extends DialogFragment {

    private long offsetOrder = Long.MAX_VALUE;

    AdapterChats mAdapter;
    ListView lvChats;
    TextView tvLoading;
    ProgressBar progressBarLoadingChats;
    private Callback onChatSelected;

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        if(mAdapter!=null)
            mAdapter.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_select_group, null);
        Toolbar toolbar = UIUtils.getView(v, R.id.toolbar);
        toolbar.setTitle("Select chat to forwarding log");

        lvChats = UIUtils.getView(v, R.id.lvList);
        progressBarLoadingChats = UIUtils.getView(v, R.id.progressBarLoadingChats);
        tvLoading = UIUtils.getView(v, R.id.tvLoading);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mAdapter = new AdapterChats(getActivity());
        lvChats.setAdapter(mAdapter);
        lvChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                confirmSelect(mAdapter.getItem(position));
            }
        });

        loadChats();
    }

    void runOnUiThread(Runnable runnable) {
        if (isAdded() && getActivity() != null && !getActivity().isFinishing()) {
            getActivity().runOnUiThread(runnable);
        }
    }

    private void loadChats() {
        TgH.TG().send(new TdApi.GetChats(offsetOrder, 0, 100), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.Chats.CONSTRUCTOR) {
                    final TdApi.Chats cs = (TdApi.Chats) object;
                    final ArrayList chats = new ArrayList<>();

                    for (TdApi.Chat chat : cs.chats) {
                        if (TgUtils.isSuperGroup(chat.type.getConstructor())) {
                            TdApi.ChannelChatInfo channelInfo = (TdApi.ChannelChatInfo) chat.type;
                            if (channelInfo.channel.isSupergroup) {
                                chats.add(chat);
                            }else{
                                if(TgUtils.isUserPrivileged(channelInfo.channel.status.getConstructor())){
                                    chats.add(chat);
                                }
                            }

                        } else if (TgUtils.isGroup(chat.type.getConstructor())) {
                            chats.add(chat);
                        } else
                            continue;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.list.addAll(chats);
                            mAdapter.notifyDataSetChanged();
                        }
                    });


                    if (cs.chats.length >= 100) {
                        offsetOrder = cs.chats[cs.chats.length - 1].order;
                        loadChats();
                    } else {
                        // чаты кончились
                        // runOnUiThread(updateList);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UIUtils.setBatchVisibility(View.GONE, progressBarLoadingChats, tvLoading);
                            }
                        });
                    }


                } else {
                    MyToast.toast(getActivity(), "Loading chats error");
                    dismiss();
                }
            }
        });

    }

    void confirmSelect(final TdApi.Chat chat){
        new AlertDialog.Builder(getActivity())
                .setTitle("Select group")
                .setMessage(chat.title + "\n"+getString(R.string.text_confirm_select_group_for_logging))
                .setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onChatSelected.onResult(chat);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.btnCancel, null)
                .show();
    }

    public void setOnChatSelected(Callback onChatSelected) {
        this.onChatSelected = onChatSelected;
    }
}
