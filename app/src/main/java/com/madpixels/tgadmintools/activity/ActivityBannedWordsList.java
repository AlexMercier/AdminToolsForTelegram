package com.madpixels.tgadmintools.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.ui.ActivityExtended;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.BannedWord;

import java.util.ArrayList;

/**
 * Created by Snake on 28.09.2016.
 */

public class ActivityBannedWordsList extends ActivityExtended {

    //Button btnAddBanWord;
    ListView lvWords;
    TextView tvListStatus;

    Adapter adapter;

    long chatID;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong("chat_id", chatID);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannedwords);
        UIUtils.setActionBarWithBackArrow(this);
        setTitle("Banned words");

        Bundle b = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
        chatID = b.getLong("chat_id");
        createView();
    }

    public void createView() {
        // btnAddBanWord = getView(R.id.btnAddBanWord);
        // btnAddBanWord.setOnClickListener(onClickListener);
        lvWords = getView(R.id.listViewBanWords);
        tvListStatus = getView(R.id.tvListStatus);

        tvListStatus.setVisibility(View.INVISIBLE);

        adapter = new Adapter();
        lvWords.setAdapter(adapter);
        lvWords.setOnItemClickListener(onItemClickListener);
        registerForContextMenu(lvWords);
        lvWords.setOnScrollListener(onScrollListener);


        new LoadWords().execute();
    }

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int headersCount = 0;
            if (!isListEnd && !isLoading && totalItemCount > headersCount && firstVisibleItem + visibleItemCount == totalItemCount) {
                new LoadWords().execute();
            }
        }
    };

    int offset = 0;
    boolean isLoading = false, isListEnd = false;

    class LoadWords extends AsyncTask<Void, Void, Void> {
        ArrayList<BannedWord> list;

        @Override
        protected void onPreExecute() {
            isLoading = true;
        }

        @Override
        protected Void doInBackground(Void... params) {
            int count = BuildConfig.DEBUG?6:200;
            list = DBHelper.getInstance().getWordsBlackList(chatID, offset, count);
            offset += list.size();
            if (list.size()<count)
                isListEnd = true;

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!list.isEmpty()) {
                adapter.list.addAll(list);
                adapter.notifyDataSetChanged();
            } else {
                if (adapter.isEmpty()) {
                    tvListStatus.setVisibility(View.VISIBLE);
                    tvListStatus.setText(R.string.text_banned_words_list_empty);
                }
            }
            isLoading = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 1, 0, R.string.action_add_banword);
        item.setIcon(R.drawable.ic_note_add_white_24dp);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        menu.add(0, 3, 0, R.string.action_banwords_import_from_chat);
        menu.add(0, 2, 0, R.string.action_clear_banwords);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                dialogAddBanWord();
                break;
            case 2:
                dialogClearList();
                break;
            case 3:
                dialogAllWords();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogClearList() {
        new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setTitle(R.string.title_dialog_confirm)
                .setMessage(R.string.text_clear_all_banwords_for_chat)
                .setNeutralButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper.getInstance().clearBanWords(chatID);
                        adapter.list.clear();
                        adapter.notifyDataSetChanged();
                    }
                })
                .show();

    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.showContextMenu();
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 0, R.string.button_delete);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        BannedWord word = adapter.getItem(index);

        DBHelper.getInstance().deleteBannedWord(word.id);
        adapter.list.remove(index);
        adapter.notifyDataSetChanged();

        return super.onContextItemSelected(item);
    }

    private void dialogAddBanWord() {
        final View view = UIUtils.inflate(mContext, R.layout.dialog_add_banword);

        final EditText edtTextWord = UIUtils.getView(view, R.id.edtBanWord);
        final CheckBox chkDelBanWord = UIUtils.getView(view, R.id.chkDelBanWord);
        final CheckBox chkBanUserForBanWord = UIUtils.getView(view, R.id.chkBanUserForBanWord);

        chkDelBanWord.setChecked(Sets.getBoolean("chk.bannedword.delete", true));
        chkBanUserForBanWord.setChecked(Sets.getBoolean("chk.bannedword.banuser", false));

        new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setTitle(R.string.title_add_banned_word)
                .setView(view)
                .setNeutralButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.btnAdd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String word = edtTextWord.getText().toString().trim();
                        if (word.isEmpty()) {
                            MyToast.toast(mContext, R.string.toast_word_is_empty);
                            dialogAddBanWord();
                            return;
                        }
                        boolean isDeleteMessage = chkDelBanWord.isChecked();
                        boolean isBanUserForBlackWord = chkBanUserForBanWord.isChecked();

                        long id = DBHelper.getInstance().addWordToBlackList(chatID, word, isDeleteMessage, isBanUserForBlackWord);
                        Sets.set("chk.bannedword.delete", isDeleteMessage);
                        Sets.set("chk.bannedword.banuser", isBanUserForBlackWord);

                        BannedWord bWord = new BannedWord();
                        bWord.word = word;
                        bWord.isBanUser = isBanUserForBlackWord;
                        bWord.isDeleteMsg = isDeleteMessage;
                        bWord.id = id;
                        adapter.list.add(0, bWord);
                        adapter.notifyDataSetChanged();

                        tvListStatus.setVisibility(View.INVISIBLE);

                    }
                })
                .show();
    }

    class Adapter extends BaseAdapter {

        ArrayList<BannedWord> list = new ArrayList<>(0);

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public BannedWord getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            boolean viewCreated;
            if (viewCreated = view == null) {
                view = UIUtils.inflate(mContext, R.layout.list_item_banned_word);
            }

            BannedWord word = getItem(position);

            TextView tvWord = UIUtils.getHolderView(view, R.id.tvBanWord);
            CheckBox chkDelMsg = UIUtils.getHolderView(view, R.id.chkDelMsg);
            CheckBox chkBanUser = UIUtils.getHolderView(view, R.id.chkBanUser);

            if (viewCreated) {
                UIUtils.setBatchClickListener(onClickListener, chkDelMsg, chkBanUser);
            }

            chkDelMsg.setTag(R.id.id_position, position);
            chkBanUser.setTag(R.id.id_position, position);
            chkBanUser.setChecked(word.isBanUser);
            chkDelMsg.setChecked(word.isDeleteMsg);

            tvWord.setText(word.word);

            return view;
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = Integer.valueOf(v.getTag(R.id.id_position).toString());
                BannedWord word = getItem(pos);
                switch (v.getId()) {
                    case R.id.chkDelMsg:
                        word.isDeleteMsg = ((CheckBox) v).isChecked();
                        DBHelper.getInstance().updateBannedWord(word);
                        break;
                    case R.id.chkBanUser:
                        word.isBanUser = ((CheckBox) v).isChecked();
                        DBHelper.getInstance().updateBannedWord(word);
                        break;
                }
            }
        };
    }

    void dialogAllWords() {
        View v = UIUtils.inflate(mContext, R.layout.dialog_all_banwords);

        ListView lvWords = UIUtils.getView(v, R.id.lvAllBannedWords);
        final AdapterAllWords adapterAllWords = new AdapterAllWords();
        lvWords.setAdapter(adapterAllWords);

        new AlertDialog.Builder(mContext)
                .setCancelable(false)
                .setTitle(R.string.title_import_words)
                .setView(v)
                .setPositiveButton(R.string.btnAdd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0;i<adapterAllWords.getCount();i++){
                            BannedWord word = adapterAllWords.getItem(i);
                            if(word.isSelected) {
                                long id = DBHelper.getInstance().addWordToBlackList(chatID, word.word, word.isDeleteMsg, word.isBanUser);
                                word.id = id;
                                adapter.list.add(0, word);
                                adapter.notifyDataSetChanged();
                            }
                        }

                    }
                })
                .setNegativeButton(R.string.btnCancel, null)
                .show();



        ArrayList<BannedWord> words = DBHelper.getInstance().getAllWordsBlackList(chatID);
        adapterAllWords.list.addAll(words);
        adapterAllWords.notifyDataSetChanged();
    }

    private class AdapterAllWords extends BaseAdapter {

        private ArrayList<BannedWord> list = new ArrayList<>();

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public BannedWord getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = UIUtils.inflate(mContext, R.layout.list_item_banned_word);

            BannedWord word = getItem(position);

            CheckBox chkDelMsg = UIUtils.getHolderView(view, R.id.chkDelMsg);
            CheckBox chkSelected = UIUtils.getHolderView(view, R.id.chkBanUser);
            TextView tvBanWord = UIUtils.getHolderView(view, R.id.tvBanWord);

            chkDelMsg.setVisibility(View.GONE);

            tvBanWord.setText(word.word);
            chkSelected.setChecked(word.isSelected);
            chkSelected.setTag(word);

            chkSelected.setOnClickListener(onClickListener);

            return view;
        }

        private View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BannedWord w = (BannedWord) v.getTag();
                w.isSelected = ((CheckBox)v).isChecked();
                notifyDataSetChanged();
            }
        };
    }
}
