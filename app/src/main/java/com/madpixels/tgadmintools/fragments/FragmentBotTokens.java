package com.madpixels.tgadmintools.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.Utils;
import com.madpixels.apphelpers.ui.ProgressDialogBuilder;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.BotToken;
import com.madpixels.tgadmintools.entities.Callback;
import com.madpixels.tgadmintools.helper.TelegramBot;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.utils.TgImageGetter;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Snake on 06.01.2017.
 */

public class FragmentBotTokens extends DialogFragment {

    ListView lvBots;
    EditText edtNewBotToken;
    Button btnInserBotToken;
    Callback onBotSelectedCallback;
    BotsAdapter mAdapter;
    TextView tvUseExistingBotHint;

    /***
     * ==========================================================
     * CONSTRUCTOR
     * ============================================================
     */

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_bot_tokens, null);
        Toolbar toolbar = UIUtils.getView(v, R.id.toolbar);
        toolbar.setTitle(R.string.title_select_bot);

        final Drawable upArrow = ContextCompat.getDrawable(getContext(), R.drawable.back_arrow_white_material);
        upArrow.setColorFilter(getResources().getColor(R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        lvBots = UIUtils.getView(v, R.id.lvBots);
        View header = UIUtils.inflate(getContext(), R.layout.layout_header_bots_list);
        lvBots.addHeaderView(header, null, false);
        registerForContextMenu(lvBots);

        TextView tvOpenBotfather = UIUtils.getView(header, R.id.tvOpenBotfather);
        edtNewBotToken = UIUtils.getView(header, R.id.edtNewBotToken);
        btnInserBotToken = UIUtils.getView(header, R.id.btnInserBotToken);
        tvUseExistingBotHint = UIUtils.getView(header, R.id.tvUseExistingBotHint);

        lvBots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BotToken botToken = mAdapter.getItem(position - lvBots.getHeaderViewsCount());
                onBotSelectedCallback.onResult(botToken.local_id);
                dismiss();
            }
        });

        edtNewBotToken.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    //btnInserBotToken.requestFocus();
                    //btnInserBotToken.focus
                    //return true;
                }
                return false;
            }
        });

        tvOpenBotfather.setOnClickListener(onClickListener);
        btnInserBotToken.setOnClickListener(onClickListener);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mAdapter = new BotsAdapter(getActivity());
        lvBots.setAdapter(mAdapter);

        new LoadExitingBots().execute();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int pos = info.position - lvBots.getHeaderViewsCount();
        if (pos > 0) {//ignore for first 'None' item
            menu.add(0, 101, 0, R.string.option_remove_bot);
            menu.add(0, 102, 0, R.string.action_copy);
        }

        for (int i = 0, n = menu.size(); i < n; i++)
            menu.getItem(i).setOnMenuItemClickListener(onMenuItemClickListener);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private MenuItem.OnMenuItemClickListener onMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            AdapterView.AdapterContextMenuInfo cInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int pos = cInfo.position - lvBots.getHeaderViewsCount();
            BotToken botToken = mAdapter.getItem(pos);

            switch (item.getItemId()) {
                case 101:
                    DBHelper.getInstance().removeBotToken(botToken.mToken);
                    mAdapter.list.remove(pos);
                    mAdapter.notifyDataSetChanged();
                    if(mAdapter.getCount()==1)
                        tvUseExistingBotHint.setVisibility(View.GONE);
                    break;
                case 102:
                    Utils.copyToClipboard(botToken.mToken, getContext());
                    MyToast.toast(getActivity(), getString(R.string.toast_copied_to_clipboard)+"\n"+botToken.mToken);
                    break;
            }
            return true;
        }
    };



    /***
     * ==========================================================
     * End CONSTRUCTOR
     * ============================================================
     */

    /**
     * Callback called with local db bot id if bot selected
     */
    public void setOnBotSelectedCallback(Callback onBotSelectedCallback) {
        this.onBotSelectedCallback = onBotSelectedCallback;
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvOpenBotfather:
                    Utils.openUrl("https://telegram.me/BotFather", getContext());
                    break;
                case R.id.btnInserBotToken:
                    addBotToken();
                    break;
            }
        }
    };

    class LoadExitingBots extends AsyncTask<Void, Void, Void> {
        ArrayList<BotToken> list;

        @Override
        protected Void doInBackground(Void... params) {
            list = DBHelper.getInstance().getBotsList();
            if(list!=null) {
                BotToken bNone = new BotToken();
                bNone.mFirstName = "None";
                bNone.mToken = getString(R.string.text_select_bot_none);
                bNone.local_id = 0;
                list.add(0, bNone);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (list != null && !list.isEmpty()) {
                mAdapter.list.addAll(list);
                mAdapter.notifyDataSetChanged();
            }else{
                tvUseExistingBotHint.setVisibility(View.GONE);
            }
        }
    }


    void addBotToken() {
        final String token = edtNewBotToken.getText().toString().trim();
        if(token.isEmpty()){
            MyToast.toast(getContext(), R.string.toast_bottoken_is_empty);
            edtNewBotToken.requestFocus();
            UIUtils.showSoftKeyboard(edtNewBotToken);
            return;
        }

        final ProgressDialogBuilder pd = new ProgressDialogBuilder(getActivity())
                .setTitle(R.string.label_loading)
                .setCancelable(false)
                .setIndeterminate(true)
                .setMessage("Loading bot info, please wait...");
        pd.setNegativeButton(R.string.btnCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.isCancelled = true;
            }
        });
        pd.show();



        new Thread() {
            @Override
            public void run() {
                // if(BuildConfig.DEBUG)Utils.sleep(2500);
                TelegramBot bot = new TelegramBot(token);
                TdApi.TLObject object = bot.getMe();
                if (pd.isCancelled)
                    return;
                pd.dismiss();
                if (TgUtils.isOk(object)) {
                    final int local_id = DBHelper.getInstance().addBotToke(bot);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onBotSelectedCallback.onResult(local_id);
                        }
                    });
                    dismiss();
                } else {
                    TdApi.Error e = (TdApi.Error) object;
                    if (e.code == 401)
                        MyToast.toast(getActivity(), "Error: Bot token is invalid!\n" + e.message);
                    else
                        MyToast.toast(getActivity(), "Error:\n" + e.message);
                }

            }
        }.start();

    }

    /**
     * Adapter
     */

    private static class BotsAdapter extends BaseAdapter {
        ArrayList<BotToken> list = new ArrayList<>();
        final LayoutInflater inflater;
        TgImageGetter images;

        BotsAdapter(Context c) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            images = new TgImageGetter().setRounded(true).setAdapter(this);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public BotToken getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        HashMap<String, Object> botPics = new HashMap<>();

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_bot_list, parent, false);
            }

            BotToken botToken = getItem(position);
            TextView tvBotFirstname = UIUtils.getHolderView(convertView, R.id.tvBotFirstname);
            TextView tvBotusername = UIUtils.getHolderView(convertView, R.id.tvBotusername);
            TextView tvBotToken = UIUtils.getHolderView(convertView, R.id.tvBotToken);
            ImageView ivBotPic = UIUtils.getHolderView(convertView, R.id.ivBotPic);

            tvBotFirstname.setText(botToken.mFirstName);
            if (botToken.local_id == 0) {//None bot
                ivBotPic.setImageDrawable(null);
                tvBotusername.setText("");
                tvBotToken.setText(botToken.mToken);
                return convertView;
            }
            //124140320:AAFe2_zUdAxGyfkQcYDqLpdNrNjzsbp0IHU
            tvBotusername.setText("@" + botToken.mUsername);
            tvBotToken.setText(botToken.getTokenSafe());

            Object pic = botPics.get(botToken.mUsername);
            if (pic == null) {
                botPics.put(botToken.mUsername, true); // mark true to sure than pic is loading...
                loadBotPic(botToken.mUsername);
            } else if (pic instanceof Integer) {
                int photo_id = (int) pic;
                Bitmap ava = images.getPhoto(photo_id);
                if (ava != null) {
                    ivBotPic.setImageBitmap(ava);
                } else {
                    ivBotPic.setImageResource(R.drawable.no_avatar);
                }
            } else {
                ivBotPic.setImageResource(R.drawable.no_avatar);
            }

            return convertView;
        }

        private void loadBotPic(final String mUsername) {
            TgH.sendOnUi(new TdApi.SearchPublicChat(mUsername), new Client.ResultHandler() {
                @Override
                public void onResult(TdApi.TLObject object) {
                    if(TgUtils.isError(object)) return;
                    TdApi.Chat chat = (TdApi.Chat) object;
                    if (chat.photo != null)
                        botPics.put(mUsername, chat.photo.small.id);
                    notifyDataSetChanged();
                }
            });
        }

        public void onDestroy() {
            images.onDestroy();
        }
    }

    /**
     * End Adapter
     */

}
