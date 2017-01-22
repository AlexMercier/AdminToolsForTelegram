package com.madpixels.tgadmintools.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.Utils;
import com.madpixels.apphelpers.ui.ActivityExtended;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.adapters.AdapterLog;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.LogEntity;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.utils.LogUtil;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import libs.AdHelper;

/**
 * Created by Snake on 22.03.2016.
 */
public class ActivityLogView extends ActivityExtended {

    ListView mListView;
    AdapterLog mAdapter;
    TextView tvState;

    boolean isLoading = false, isListEnd = false;
    int offset = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        UIUtils.setToolbarWithBackArrow(this, R.id.toolbar);
        AdHelper.showBanner(findViewById(R.id.adView));
        setTitle(R.string.action_log);
        Instance = this;

        mListView = getView(R.id.listViewLog);
        mAdapter = new AdapterLog(mContext);
        mListView.setAdapter(mAdapter);
        tvState = getView(R.id.tvState);
        registerForContextMenu(mListView);
        mListView.setOnItemClickListener(onItemClickListener);
        mListView.setOnScrollListener(onScrollListener);


        if (mAdapter.isEmpty())
            tvState.setText(R.string.text_list_empty);
        else
            tvState.setVisibility(View.INVISIBLE);

        getLog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem i = menu.add(0, 1, 0, R.string.action_show_log_notification);
        i.setCheckable(true);
        i.setChecked(Sets.getBoolean(Const.NOTIFICATION_LOG, true));

        menu.add(0, 2, 0, R.string.action_clear_log);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                item.setChecked(!item.isChecked());
                Sets.set(Const.NOTIFICATION_LOG, item.isChecked());
                break;
            case 2:
                new AlertDialog.Builder(mContext)
                        .setTitle("Confirm")
                        .setMessage(R.string.action_clear_log)
                        .setNegativeButton(R.string.btnCancel, null)
                        .setPositiveButton(R.string.btnYes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelper.getInstance().clearLog();
                                mAdapter.list.clear();
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static boolean canUnbanUser(LogUtil.Action action){
        switch (action){
            case BanForSticker:case BanForVoice:case BanForGame:case BanForLink:case AutoKickFromGroup:
            case BanForBlackWord: case BanForFlood:
                return true;
        }
        return false;
    }

    private static boolean hasUserInfo(LogEntity log){
        switch (log.action){
            case BanForBlackWord:case DeleteMsgBlackWord:case RemoveSticker:case RemoveLink:case LinksFloodAttempt:
            case StickersFloodWarn: case VoiceFloodWarn: case GameFloodWarn: case GifsFloodWarn: case ImagesFloodWarn:
            case VideoFloodWarn: case DocsFloodWarn:
                return log.hasUserName();
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo cInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int pos = cInfo.position - mListView.getHeaderViewsCount();
        LogEntity log = mAdapter.getItem(pos);
        if (log.hasChatId())
            menu.add(0, 1, 0, R.string.action_open_chat_info);
        if (canUnbanUser(log.action)) {
            menu.add(0, 2, 0, R.string.action_return_to_chat);
        }

        if (log.action == LogUtil.Action.BanForLink || log.action== LogUtil.Action.LinksFloodAttempt) {
            menu.add(0, 5, 0, R.string.action_add_link_to_wl);
        }
        if (hasUserInfo(log)) {
                menu.add(0, 4, 0, R.string.action_open_userninfo);
        }
        if(log.action== LogUtil.Action.BOT_ERROR)
            menu.add(0, 6, 0, "Copy Message");

        menu.add(0, 3, 0, R.string.action_copy);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo cInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = cInfo.position - mListView.getHeaderViewsCount();
        final LogEntity log = mAdapter.getItem(pos);
        log.setChat();

        switch (item.getItemId()) {
            case 1:
                TgH.send(new TdApi.GetChat(log.chatId), new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (object.getConstructor() == TdApi.Chat.CONSTRUCTOR) {
                            TdApi.Chat chat = (TdApi.Chat) object;
                            openChat(chat);
                        } else {
                            MyToast.toastL(mContext, "Error opening chat");
                        }
                    }
                });
                break;
            case 2:
                TgH.send(new TdApi.AddChatMember(log.chatId, log.getUserId(), 5), new Client.ResultHandler() {
                    @Override
                    public void onResult(TdApi.TLObject object) {
                        if (TgUtils.isOk(object)) {
                            MyToast.toast(mContext, "User returned and removed from ban list");
                            DBHelper.getInstance().removeUserFromAutoKick(log.chatId, log.getUserId());
                            DBHelper.getInstance().removeBannedUser(log.chatId, log.getUserId());
                        } else
                            MyToast.toast(mContext, "Add chat participiant error:\n" + object.toString());
                    }
                });
                break;
            case 3:
                String text;
                text = log.getTitle() + "\n" + log.getLogText();
                Utils.copyToClipboard(text, mContext);
                MyToast.toast(mContext, "Text copied");
                break;
            case 4:
                String link = "https://telegram.me/" + log.username;
                Utils.openUrl(link, mContext);
                break;
            case 5:
                try {
                    JSONObject payload = new JSONObject(log.jsonData).getJSONObject("payload");
                    link =  payload.getString("link").replace("http://", "").replace("https://", "").replaceFirst("www.", "");
                    DBHelper.getInstance().addLinkToWhiteList(link);
                    MyToast.toast(mContext, "Link added to whitelist");
                } catch (JSONException e) {
                    MyToast.toast(mContext, "Error parsing json");
                }
                break;
            case 6:
                try {
                    String message = new JSONObject(log.jsonData).optString("payload");
                    if(message.isEmpty())
                        MyToast.toast(mContext, "Text is empty");
                    else{
                        Utils.copyToClipboard(message, mContext);
                        MyToast.toast(mContext, R.string.toast_copied_to_clipboard);
                    }
                } catch (JSONException e) {
                    MyToast.toast(mContext, "Error parsing json");
                }

                break;
        }

        return super.onContextItemSelected(item);
    }

    private void openChat(TdApi.Chat chat) {
        if (!TgUtils.isGroup(chat.type.getConstructor())) {
            TdApi.ChannelChatInfo chi = (TdApi.ChannelChatInfo) chat.type;
            startActivity(new Intent(mContext, ActivityGroupInfo.class)
                    .putExtra("chatType", chat.type.getConstructor())
                    .putExtra("chat_id", chat.id)
                    .putExtra("title", chat.title)
                    .putExtra("channel_id", chi.channel.id));
        } else if (TgUtils.isGroup(chat.type.getConstructor())) {
            TdApi.GroupChatInfo chatInfo = (TdApi.GroupChatInfo) chat.type;
            startActivity(new Intent(mContext, ActivityGroupInfo.class)
                    .putExtra("chatType", chat.type.getConstructor())
                    .putExtra("group_id", chatInfo.group.id)
                    .putExtra("title", chat.title)
                    .putExtra("chat_id", chat.id));
        }
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
            if (!isLoading && !isListEnd && totalItemCount > mListView.getHeaderViewsCount() && firstVisibleItem + visibleItemCount == totalItemCount) {
                isLoading = true;
                getLog();
            }
        }
    };

    public void onNewLogEvent() {
        if (!mAdapter.isEmpty()) {
            int id = mAdapter.getItem(0).item_id;
            final ArrayList<LogEntity> logs = DBHelper.getInstance().getLogUpdate(id);
            if (logs != null) {
                mAdapter.list.addAll(0, logs);
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        Animation anim = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
                        anim.setDuration(500);
                        mListView.getChildAt(0).startAnimation(anim);
                    }
                }, 100);
            }
        }
    }

    private void getLog() {
        new Thread() {
            @Override
            public void run() {
                final int count = mAdapter.isEmpty()?5:50;
                final ArrayList<LogEntity> logs = DBHelper.getInstance().getLog(offset, count);
                onUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (logs != null) {
                            if (logs.size() < count) isListEnd = true;
                            mAdapter.list.addAll(logs);
                            mAdapter.notifyDataSetChanged();
                            tvState.setVisibility(View.GONE);
                            offset += logs.size();
                        } else {
                            isListEnd = true; // on error stop loading
                        }
                        isLoading = false;
                    }
                });

            }
        }.start();
    }

    @Override
    public void close() {
        Instance = null;
        if (mAdapter != null && mAdapter.getCount() > 0) {
            AdHelper.onCloseActivity(this);
        } else
            super.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Instance = null;
    }

    static ActivityLogView Instance;

    public static void updateLog() {
        if (Instance != null && !Instance.isFinishing()) {
            Instance.onNewLogEvent();
        }
    }
}
