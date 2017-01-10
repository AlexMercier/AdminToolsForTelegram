package com.madpixels.tgadmintools.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.Utils;
import com.madpixels.apphelpers.ui.ActivityExtended;
import com.madpixels.apphelpers.ui.ProgressDialogBuilder;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.adapters.AdapterChats;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import libs.AdHelper;

/**
 * Created by Snake on 21.02.2016.
 */
public class ActivityGroupsList extends ActivityExtended {

    public static boolean reloadOnResume = false;
    ArrayList<TdApi.Chat> mListAdminChats = new ArrayList<>();
    ArrayList<JSONObject> chatsCache = null;

    ProgressBar prgLoadingChats, prgLoadingCachedList;
    AdapterChats mAdapter;
    ListView mListViewChats;
    boolean isShowChannels = false, isShowOnlyCreated = false;

    private long offsetOrder = Long.MAX_VALUE; //вначале получаем с нуля.
    int mCountAdminsGroup = 0;
    private int totalChats = 0;
    TextView tvListCount, tvCountTotalTitle, tvLoadingCount;
    private ChatsInfo chatsInfo = new ChatsInfo();

    private static class ChatsInfo {
        int channels_count = 0;
        int groups_count = 0;
        int supergroups_count = 0;
        int private_chats_count = 0;
        int secret_chats_count = 0;
        int created_groups_count = 0;
        int created_channels_count = 0;
        int admin_groups_count = 0;
        int admin_channels_count = 0;

        void reset() {
            channels_count = groups_count = supergroups_count = private_chats_count = secret_chats_count =
                    created_channels_count = created_groups_count = admin_groups_count = admin_channels_count = 0;
        }

        public int getTotal() {
            return channels_count + groups_count + supergroups_count + private_chats_count + secret_chats_count;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);
        UIUtils.setToolbar(this, R.id.toolbar);
        setTitle(R.string.titleManagingGroups);

        mListViewChats = getView(R.id.listChats);
        View head_list_count = UIUtils.inflate(mContext, R.layout.header_list_count);
        tvListCount = UIUtils.getView(head_list_count, R.id.tvListCount);
        tvCountTotalTitle = UIUtils.getView(head_list_count, R.id.tvTotal);
        UIUtils.setBatchVisibility(View.GONE, tvListCount, tvCountTotalTitle);
        mListViewChats.addHeaderView(head_list_count, null, false);
        prgLoadingChats = getView(R.id.prgLoadingChats);
        mAdapter = new AdapterChats(this);
        mListViewChats.setAdapter(mAdapter);
        registerForContextMenu(mListViewChats);
        mListViewChats.setOnItemClickListener(onItemClickListener);
        tvLoadingCount = getView(R.id.tvLoadingCount);
        prgLoadingCachedList = getView(R.id.prgLoadingCachedList);

        prgLoadingCachedList.setVisibility(View.GONE);

        mCountAdminsGroup = 0;


        new GetChatsCached().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_groups_list, menu);
        //menu.add(0, 101, 0, R.string.create_group);
        //menu.add(0, 102, 0, R.string.create_supergroup);
        //menu.add(0, 103, 0, R.string.action_settings);
        //menu.add(0, 104, 0, R.string.action_log);
        //menu.add(0, 105, 0, R.string.action_chk_show_only_created);
        //menu.findItem(105).setCheckable(true);
        //menu.add(0, 106, 0, R.string.action_chk_show_channels);
        //menu.findItem(106).setCheckable(true);

        if (BuildConfig.DEBUG)
            menu.add(0, 777, 0, "Ad Interstitial");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_create_group:
                dialogCreateGroup();
                break;
            case R.id.menu_action_create_supergroup:
                dialogCreateSuperGroup();
                break;
            case R.id.menu_action_settings:
                startActivity(new Intent(this, ActivitySettings.class));
                break;
            case R.id.menu_action_log:
                startActivity(new Intent(this, ActivityLogView.class));
                break;
            case R.id.menu_action_show_created_groups:
                isShowOnlyCreated = !isShowOnlyCreated;
                item.setChecked(isShowOnlyCreated);
                prgLoadingChats.setVisibility(View.VISIBLE);
                reloadList();
                break;
            case R.id.menu_action_show_channels:
                isShowChannels = !isShowChannels;
                item.setChecked(isShowChannels);
                prgLoadingChats.setVisibility(View.VISIBLE);
                reloadList();
                break;
            case R.id.menu_action_chats_info:
                String text =
                        "Total chats: " + chatsInfo.getTotal() + "\n" +
                                "Created groups: " + chatsInfo.created_groups_count + "\n" +
                                "Created channels: " + chatsInfo.created_channels_count + "\n" +
                                "Total groups: " + chatsInfo.groups_count + "\n" +
                                "Total supergropus: " + chatsInfo.supergroups_count + "\n" +
                                "Total channels: " + chatsInfo.channels_count + "\n" +
                                "Private chats: " + chatsInfo.private_chats_count + "\n" +
                                "Secret chats: access denied\n" +
                                "Moderation groups: " + chatsInfo.admin_groups_count + "\n" +
                                "Moderation channels: " + chatsInfo.admin_channels_count;
                new AlertDialog.Builder(mContext)
                        .setTitle("Chats stat")
                        .setMessage(text)
                        .setPositiveButton("Ok", null)
                        .show();
                break;
            case 777:
                AdHelper.showInterstitialForce(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.showContextMenu();
        }
    };


    private TdApi.Chat lastClickedChat = null;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        if (info.position < 1) return;
        int pos = info.position - mListViewChats.getHeaderViewsCount();

        TdApi.Chat chat = lastClickedChat = mAdapter.getItem(pos);

        menu.setHeaderTitle(chat.title);

        if (TgUtils.isSuperGroup(chat.type.getConstructor())) {
            TdApi.ChannelChatInfo channel = (TdApi.ChannelChatInfo) chat.type;
            if (!TextUtils.isEmpty(channel.channel.username)) {
                menu.add(0, 4, 0, R.string.action_open_group_link);
            }
        }

        menu.add(0, 1, 0, (TgUtils.isChannel(chat) ? R.string.action_manage_channel : R.string.action_manage_group));
        menu.add(0, 2, 0, R.string.action_manage_chat_users);
        menu.add(0, 3, 0, R.string.action_banlist);


        //if(BuildConfig.DEBUG)
        //    menu.add(0, 444, 0, "ToggleChannelInvites");


        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info.position < 1) return false;
        TdApi.Chat chat = mAdapter.getItem(info.position - mListViewChats.getHeaderViewsCount());
        if(lastClickedChat!=chat)//prevent missclicking
            chat = lastClickedChat;
        switch (item.getItemId()) {
            case 1:
                if (!TgUtils.isGroup(chat.type.getConstructor())) {
                    TdApi.ChannelChatInfo channelChatInfo = (TdApi.ChannelChatInfo) chat.type;
                    startActivity(new Intent(mContext, ActivityGroupInfo.class)
                            .putExtra("chatType", chat.type.getConstructor())
                            .putExtra("chat_id", chat.id)
                            .putExtra("title", chat.title)
                            .putExtra("channel_id", channelChatInfo.channel.id));
                } else if (TgUtils.isGroup(chat.type.getConstructor())) {
                    TdApi.GroupChatInfo chatInfo = (TdApi.GroupChatInfo) chat.type;
                    startActivity(new Intent(mContext, ActivityGroupInfo.class)
                            .putExtra("chatType", chat.type.getConstructor())
                            .putExtra("group_id", chatInfo.group.id)
                            .putExtra("title", chat.title)
                            .putExtra("chat_id", chat.id));
                }

                break;
            case 2: // Users list
                if (!TgUtils.isGroup(chat.type.getConstructor())) {
                    TdApi.ChannelChatInfo channelChatInfo = (TdApi.ChannelChatInfo) chat.type;
                    startActivity(new Intent(mContext, ActivityChatUsers.class)
                            .putExtra("type", chat.type.getConstructor())
                            .putExtra("title", chat.title)
                            .putExtra("chat_id", chat.id)
                            .putExtra("channel_id", channelChatInfo.channel.id));
                } else if (TgUtils.isGroup(chat.type.getConstructor())) {
                    TdApi.GroupChatInfo cInfo = (TdApi.GroupChatInfo) chat.type;
                    startActivity(new Intent(mContext, ActivityChatUsers.class)
                            .putExtra("type", chat.type.getConstructor())
                            .putExtra("group_id", cInfo.group.id)
                            .putExtra("title", chat.title)
                            .putExtra("chat_id", chat.id));
                }
                break;
            case 3: // Ban list
                if (chat.type.getConstructor() == TdApi.ChannelChatInfo.CONSTRUCTOR) {
                    TdApi.ChannelChatInfo cInfo = (TdApi.ChannelChatInfo) chat.type;
                    int channel_id = cInfo.channel.id;
                    startActivity(new Intent(mContext, ActivityBanList.class)
                            .putExtra("type", chat.type.getConstructor())
                            .putExtra("chat_id", chat.id).putExtra("channel_id", channel_id));
                } else if (chat.type.getConstructor() == TdApi.GroupChatInfo.CONSTRUCTOR) {
                    //MyToast.toast(mContext, "Only SuperGroup has blacklist");
                    //TdApi.GroupChatInfo cInfo = (TdApi.GroupChatInfo) chat.type;
                    startActivity(new Intent(mContext, ActivityBanList.class)
                            .putExtra("type", chat.type.getConstructor())
                            .putExtra("chat_id", chat.id));
                }
                break;
            case 4:
                TdApi.ChannelChatInfo channel = (TdApi.ChannelChatInfo) chat.type;
                String link = "https://telegram.me/" + channel.channel.username;
                Utils.openUrl(link, mContext);
                break;
//            case 444:
//                final TdApi.ChannelChatInfo cInfo = (TdApi.ChannelChatInfo) chat.type;
//                TgH.send(new TdApi.ToggleChannelInvites(cInfo.channel.id, false), new Client.ResultHandler() {
//                    @Override
//                    public void onResult(TdApi.TLObject object) {
//                        MyLog.log(object.toString());
//                        cInfo.channel.anyoneCanInvite = false;
//                    }
//                });
//                break;
        }


        return super.onContextItemSelected(item);
    }

    /*
         First we get and show cached chats list, and next load actual chats list
          */
    class GetChatsCached extends AsyncTask<Void, Void, ArrayList<TdApi.Chat>> {

        @Override
        protected ArrayList<TdApi.Chat> doInBackground(Void... params) {
            ArrayList<TdApi.Chat> chats = DBHelper.getInstance().getCacheChats();
            return chats;
        }

        @Override
        protected void onPostExecute(ArrayList<TdApi.Chat> chats) {
            if (!chats.isEmpty()) {
                mAdapter.addAll(chats);
                prgLoadingChats.setVisibility(View.GONE);
                tvLoadingCount.setVisibility(View.GONE);
                prgLoadingCachedList.setVisibility(View.VISIBLE);
            }
            getChats();
        }
    }


    void getChats() {
        if (chatsCache == null)
            chatsCache = new ArrayList<>();

        TgH.TG().send(new TdApi.GetChats(offsetOrder, 0, 100), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                if (object.getConstructor() == TdApi.Chats.CONSTRUCTOR) {
                    final TdApi.Chats cs = (TdApi.Chats) object;
                    for (TdApi.Chat chat : cs.chats) {
                        totalChats++;
                        int role = 0;

                        if (TgUtils.isSuperGroup(chat.type.getConstructor())) {
                            TdApi.ChannelChatInfo channelInfo = (TdApi.ChannelChatInfo) chat.type;
                            role = channelInfo.channel.status.getConstructor();

                            if (channelInfo.channel.isSupergroup) {
                                chatsInfo.supergroups_count++;
                                if (role == TdApi.ChatMemberStatusCreator.CONSTRUCTOR)
                                    chatsInfo.created_groups_count++;
                                else if (TgUtils.isUserPrivileged(role))
                                    chatsInfo.admin_groups_count++;
                            } else {
                                chatsInfo.channels_count++;
                                if (role == TdApi.ChatMemberStatusCreator.CONSTRUCTOR)
                                    chatsInfo.created_channels_count++;
                                else if (TgUtils.isUserPrivileged(role))
                                    chatsInfo.admin_channels_count++;
                            }

                            if (channelInfo.channel.isSupergroup && isShowChannels)
                                continue;
                            else if (!channelInfo.channel.isSupergroup && !isShowChannels)
                                continue;

                        } else if (TgUtils.isGroup(chat.type.getConstructor())) {
                            chatsInfo.groups_count++;
                            if (isShowChannels)
                                continue;
                            TdApi.GroupChatInfo groupInfo = (TdApi.GroupChatInfo) chat.type;
                            role = groupInfo.group.status.getConstructor();
                            if (role == TdApi.ChatMemberStatusEditor.CONSTRUCTOR && groupInfo.group.anyoneCanEdit) {
                                // простая группа и все могут изменять - мы не админ
                                continue;
                            }
                            if (role == TdApi.ChatMemberStatusCreator.CONSTRUCTOR)
                                chatsInfo.created_groups_count++;
                            else
                                chatsInfo.admin_groups_count++;
                        } else if (chat.type.getConstructor() == TdApi.SecretChatInfo.CONSTRUCTOR) {
                            chatsInfo.secret_chats_count++;
                            continue;
                        } else if (chat.type.getConstructor() == TdApi.PrivateChatInfo.CONSTRUCTOR) {
                            chatsInfo.private_chats_count++;
                            continue;
                        } else {
                            continue;
                        }
                        if (isShowOnlyCreated && role != TdApi.ChatMemberStatusCreator.CONSTRUCTOR)// только собственые
                            continue;

                        if (TgUtils.isUserPrivileged(role)) {
                            mListAdminChats.add(chat);
                            mCountAdminsGroup++;

                            try {
                                JSONObject jChat = new JSONObject()
                                        .put("id", chat.id)
                                        .put("title", chat.title)
                                        .put("type", chat.type.getConstructor())
                                        .put("order", chat.order);
                                if (chat.photo != null)
                                    jChat.put("photo_id", chat.photo.small.id);
                                jChat.put("role", TgUtils.getRole(chat).getConstructor());
                                if (TgUtils.isChannel(chat)) {
                                    jChat.put("isChannel", true);
                                }
                                if (TgUtils.isSuperGroup(chat.type.getConstructor())) {
                                    TdApi.ChannelChatInfo channel = (TdApi.ChannelChatInfo) chat.type;
                                    if (!TextUtils.isEmpty(channel.channel.username))
                                        jChat.put("username", channel.channel.username);
                                    jChat.put("channel_id", channel.channel.id);
                                }


                                chatsCache.add(jChat);

                            } catch (JSONException e) {
                                MyLog.log(e);
                            }
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvLoadingCount.setText(getString(R.string.label_loading_chats_count) + " " + totalChats);
                        }
                    });
                    if (cs.chats.length >= 100) {
                        offsetOrder = cs.chats[cs.chats.length - 1].order;
                        getChats();
                    } else {
                        // чаты кончились
                        runOnUiThread(updateList);
                        DBHelper.getInstance().cacheChats(chatsCache);
                        chatsCache = null;
                    }

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvLoadingCount.setText("Loading chats error");
                            tvLoadingCount.setTextColor(Color.RED);
                            prgLoadingChats.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    Runnable updateList = new Runnable() {
        @Override
        public void run() {
            if (isFinishing()) return;

            UIUtils.setBatchVisibility(View.VISIBLE, tvListCount, tvCountTotalTitle);
            tvListCount.setText(mCountAdminsGroup + "");
            mAdapter.clear();
            mAdapter.addAll(mListAdminChats);
            prgLoadingChats.setVisibility(View.GONE);
            tvLoadingCount.setVisibility(View.GONE);
            prgLoadingCachedList.setVisibility(View.GONE);

            AdHelper.showBanner(findViewById(R.id.adView));
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (reloadOnResume) {
            reloadOnResume = false;
            reloadList();
        }
    }

    private void reloadList() {
        if (mAdapter != null) {
            mListAdminChats.clear();
            mAdapter.list.clear();
            offsetOrder = Long.MAX_VALUE;
            mAdapter.notifyDataSetChanged();
            mCountAdminsGroup = 0;
            totalChats = 0;
            chatsInfo.reset();
            getChats();
        }
    }

    void dialogCreateGroup() {
        final ProgressDialog pDlg = new ProgressDialogBuilder(mContext)
                .setTitle("Loading")
                .setMessage("Please wait...")
                .setCancelable(false)
                .build();

        View v = UIUtils.inflate(mContext, R.layout.dialog_input_text);
        TextView tvHint = UIUtils.getView(v, R.id.tvHint);
        tvHint.setText(R.string.create_chat_title);
        final EditText editText = UIUtils.getView(v, R.id.editInputText);
        final EditText edtInviteUsername = UIUtils.getView(v, R.id.edtInviteUsername);

        editText.setHint(R.string.hint_enter_chat_title);
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.dialog_new_chat_title)
                .setView(v)
                .setNegativeButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = editText.getText().toString().trim();
                                String inviteUsername = edtInviteUsername.getText().toString().trim();

                                if (title.isEmpty()) {
                                    MyToast.toast(mContext, R.string.error_empty_chat_title);
                                    return;
                                }
                                pDlg.show();
                                CreateGroup cr = new CreateGroup(title, null);

                                cr.onSuccessCallback = new Client.ResultHandler() {
                                    @Override
                                    public void onResult(final TdApi.TLObject object) {
                                        // MyLog.log(object.toString());
                                        onUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                pDlg.dismiss();
                                                TdApi.Chat chat = (TdApi.Chat) object;
                                                mAdapter.list.add(0, chat);
                                                mAdapter.notifyDataSetChanged();
                                                MyToast.toast(mContext, R.string.group_created);
                                            }
                                        });
                                    }
                                };
                                if (!inviteUsername.isEmpty()) {
                                    cr.setInvitingUsername(inviteUsername);
                                }
                                cr.start();
                            }
                        }

                )
                .

                        show();
    }

    private static int randomBotId = 0;

    private class CreateGroup {
        String title;
        Client.ResultHandler onSuccessCallback, onErrorCallback;
        private String inviteUsername;

        CreateGroup(String title, Client.ResultHandler onSuccessCallback) {
            this.title = title;
            this.onSuccessCallback = onSuccessCallback;
        }

        private void start() {
            if (TextUtils.isEmpty(inviteUsername))
                inviteUsername = "ImageBot";//default invited bot coz telegram allow create chat only when users count 2 or more

            if (randomBotId != 0)
                createGroup();
            else
                TgH.send(new TdApi.SearchPublicChat(inviteUsername), new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        //MyLog.log(object.toString());
                        if (object.getConstructor() == TdApi.PrivateChatInfo.CONSTRUCTOR) {
                            TdApi.PrivateChatInfo chat = (TdApi.PrivateChatInfo) object;
                            TdApi.User user = chat.user;
                            randomBotId = user.id;
                        } else {
                            randomBotId = 122242792;
                        }
                        createGroup();
                    }
                });
        }

        void createGroup() {
            TdApi.TLFunction f = new TdApi.CreateNewGroupChat(new int[]{TgH.selfProfileId, randomBotId}, title);
            TgH.send(f, new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    //MyLog.log(object.toString());
                    if (TgUtils.isError(object)) {
                        onError((TdApi.Error) object);
                    } else {
                        TdApi.Chat newChat = (TdApi.Chat) object;
                        TgH.send(new TdApi.ChangeChatMemberStatus(newChat.id, randomBotId, new TdApi.ChatMemberStatusLeft()),
                                TgUtils.emptyResultHandler());
                        onSuccess(object);
                    }
                }
            });
        }

        void onError(TdApi.Error error) {
            MyToast.toastL(mContext, "Error " + error.code + "\n" + error.message);
            if (onErrorCallback != null)
                onErrorCallback.onResult(error);
        }

        void onSuccess(TdApi.TLObject object) {
            onSuccessCallback.onResult(object);
        }

        public void setInvitingUsername(String inviteUsername) {
            randomBotId = 0;
            if (inviteUsername.startsWith("@"))
                inviteUsername = inviteUsername.substring(1, inviteUsername.length());
            this.inviteUsername = inviteUsername;
        }
    }

    void dialogCreateSuperGroup() {
        final ProgressDialog pDlg = new ProgressDialogBuilder(mContext)
                .setTitle("Loading")
                .setMessage("Please wait...")
                .setCancelable(false)
                .build();

        View v = UIUtils.inflate(mContext, R.layout.dialog_input_text);
        TextView tvHint = UIUtils.getView(v, R.id.tvHint);
        tvHint.setText(R.string.chat_title);
        final EditText editText = UIUtils.getView(v, R.id.editInputText);
        final EditText edtInviteUsername = UIUtils.getView(v, R.id.edtInviteUsername);

        editText.setHint(R.string.hint_enter_chat_title);
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.create_supergroup)
                .setView(v)
                .setNegativeButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editText.getText().toString().trim();
                        String inviteUsername = edtInviteUsername.getText().toString().trim();

                        if (title.isEmpty()) {
                            MyToast.toast(mContext, R.string.error_empty_chat_title);
                            return;
                        }

                        pDlg.show();

                        CreateSuperGroup superGroup = new CreateSuperGroup();
                        superGroup.onCreateCallback = new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                pDlg.dismiss();
                                MyToast.toast(mContext, "SuperGroup created succesfull");
                                // mAdapter.list.clear();
                                // mAdapter.notifyDataSetChanged();
                                // getChats();

                                TdApi.Chat chat = (TdApi.Chat) object;
                                mAdapter.list.add(0, chat);
                                mAdapter.notifyDataSetChanged();
                                // MyToast.toast(mContext, "Group created");

                            }
                        };

                        if (!inviteUsername.isEmpty())
                            superGroup.setInviteUsername(inviteUsername);

                        superGroup.onErrorCallback = new Client.ResultHandler() {
                            @Override
                            public void onResult(TdApi.TLObject object) {
                                pDlg.dismiss();
                                MyToast.toast(mContext, "Error:\n" + object.toString());
                            }
                        };
                        superGroup.start(title);
                    }
                })
                .show();
    }


    class CreateSuperGroup {
        Client.ResultHandler onCreateCallback, onErrorCallback;
        private String inviteUsername;

        void start(String title) {
            CreateGroup createGroup = new CreateGroup(title, onCreateBaseGroup);
            createGroup.onErrorCallback = new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    onError(object);
                }
            };
            if (!TextUtils.isEmpty(inviteUsername))
                createGroup.setInvitingUsername(inviteUsername);
            createGroup.start();
        }

        final Client.ResultHandler onCreateBaseGroup = new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                Client.ResultHandler callback = new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {

                        if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR)
                            onSuccess(object);
                        else
                            onError(object);
                    }
                };

                TdApi.Chat chat = (TdApi.Chat) object;
                ActivityGroupInfo.ConvertToSuperGroup convertToSuperGroup = new ActivityGroupInfo.ConvertToSuperGroup(chat.id, callback);
                convertToSuperGroup.convert();
            }
        };

        private void onError(final TdApi.TLObject error) {
            onUiThread(new Runnable() {
                @Override
                public void run() {
                    onErrorCallback.onResult(error);
                }
            });
        }

        private void onSuccess(final TdApi.TLObject object) {
            onUiThread(new Runnable() {
                @Override
                public void run() {
                    onCreateCallback.onResult(object);
                }
            });
        }

        public void setInviteUsername(String inviteUsername) {
            this.inviteUsername = inviteUsername;
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdapter != null)
            mAdapter.onDestroy();
        super.onDestroy();
    }
}
