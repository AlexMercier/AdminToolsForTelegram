package com.madpixels.tgadmintools.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.ui.ActivityExtended;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.entities.ChatCommand;

import java.util.ArrayList;

/**
 * Created by Snake on 23.11.2016.
 */

public class ActivityChatCommands extends ActivityExtended {

    ListView lvCommandsList;
    private long chatId;
    private Adapter mAdapter;
    private ProgressBar prgLoading;
    TextView tvListIsEmpty;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong("chat_id", chatId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands_list);
        // UIUtils.setActionBarWithBackArrow(this);
        UIUtils.setToolbarWithBackArrow(this, R.id.toolbar);
        setTitle(R.string.title_chat_commands);

        Bundle b = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
        chatId = b.getLong("chat_id");

        lvCommandsList = getView(R.id.lvCommandsList);
        prgLoading = getView(R.id.prgLoading);
        tvListIsEmpty = getView(R.id.tvListIsEmpty);

        mAdapter = new Adapter();
        lvCommandsList.setAdapter(mAdapter);
        lvCommandsList.setOnItemClickListener(onItemClickListener);
        registerForContextMenu(lvCommandsList);

        new GetCommands().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 1, 0, R.string.action_add_banword);
        item.setIcon(R.drawable.ic_note_add_white_24dp);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        //menu.add(0, 2, 0, R.string.action_banwords_import_from_chat);
        menu.add(0, 3, 0, R.string.action_clear_banwords);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                dialogAddCommand();
                break;
            case 3:
                dialogClearAll();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 0, R.string.button_delete);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        ChatCommand cmd = mAdapter.getItem(index);

        DBHelper.getInstance().deleteChatCommand(cmd.id);
        mAdapter.list.remove(index);
        mAdapter.notifyDataSetChanged();
        if(mAdapter.isEmpty())
            tvListIsEmpty.setVisibility(View.VISIBLE);

        return super.onContextItemSelected(item);
    }


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ChatCommand cmd = mAdapter.getItem(position);
            dialogEditCommand(cmd, null, null);
        }
    };


    private class GetCommands extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<ChatCommand> list = DBHelper.getInstance().getChatCommands(chatId);
            mAdapter.list.addAll(list);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mAdapter.notifyDataSetChanged();
            prgLoading.setVisibility(View.GONE);
            if(!mAdapter.isEmpty())
                tvListIsEmpty.setVisibility(View.GONE);
        }
    }

    private void dialogAddCommand() {
        dialogEditCommand(null, null, null);
    }

    private void dialogEditCommand(@Nullable final ChatCommand commandEdit, final String cmdDef, String answerDef) {
        View view = UIUtils.inflate(mContext, R.layout.dialog_edit_command);
        final EditText edtCommand = UIUtils.getView(view, R.id.edtCommand);
        final Spinner spinnerCommandType = UIUtils.getView(view, R.id.spinnerCommandType);
        final EditText edtCommandAnswer = UIUtils.getView(view, R.id.edtCommandAnswer);
        final CheckBox chkIsAdmin = UIUtils.getView(view, R.id.chkIsAdmin);
        final CheckBox chkEnable = UIUtils.getView(view, R.id.chkEnable);
        final TextView tvHelpFormat = UIUtils.getView(view, R.id.tvHelpFormat);
        chkEnable.setChecked(true);

        String[] typesArray = getResources().getStringArray(R.array.command_types);
        ArrayAdapter<String> pTypesArrayAdapter = new ArrayAdapter<>(mContext,
                R.layout.item_spinner, R.id.textView, typesArray);
        spinnerCommandType.setAdapter(pTypesArrayAdapter);

        if (commandEdit != null) {
            edtCommand.setText(commandEdit.cmd);
            edtCommandAnswer.setText(commandEdit.answer);
            chkIsAdmin.setChecked(commandEdit.isAdmin);
            chkEnable.setChecked(commandEdit.isEnabled);

            if (commandEdit.type == ChatCommand.CMD_KICK_SENDER)
                spinnerCommandType.setSelection(1);
            else if(commandEdit.type==ChatCommand.CMD_CHANGE_TITLE)
                spinnerCommandType.setSelection(2);
        }

        //Previous value if "empty" error was
        if(!TextUtils.isEmpty(cmdDef))
            edtCommand.setText(cmdDef);
        if(!TextUtils.isEmpty(answerDef))
            edtCommandAnswer.setText(answerDef);


        spinnerCommandType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtCommandAnswer.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                tvHelpFormat.setVisibility(edtCommandAnswer.getVisibility());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        new AlertDialog.Builder(mContext)
                .setView(view)
                .setCancelable(false)
                .setNegativeButton(R.string.btnCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChatCommand cmd = commandEdit==null ? new ChatCommand() : commandEdit;
                        cmd.cmd = edtCommand.getText().toString().trim().toLowerCase();
                        cmd.answer = edtCommandAnswer.getText().toString().trim();
                        cmd.type = spinnerCommandType.getSelectedItemPosition();
                        cmd.isAdmin = chkIsAdmin.isChecked();
                        cmd.isEnabled = chkEnable.isChecked();
                        cmd.chatId = chatId;

                        if (cmd.cmd.startsWith("/")) {
                            if (cmd.cmd.length() > 1)
                                cmd.cmd = cmd.cmd.substring(1);
                            else{//no command entered
                                MyToast.toast(mContext, "Command cannot be empty");
                                dialogEditCommand(commandEdit, cmd.cmd, cmd.answer);
                                return;
                            }
                        }else if(cmd.cmd.isEmpty()){
                            MyToast.toast(mContext, "Command cannot be empty");
                            dialogEditCommand(commandEdit, cmd.cmd, cmd.answer);
                            return;
                        }
                        if(cmd.type==0 && cmd.answer.isEmpty()){
                            MyToast.toast(mContext, "Command answer cannot be empty");
                            dialogEditCommand(commandEdit, cmd.cmd, cmd.answer);
                            return;
                        }

                        if (commandEdit == null) {
                            DBHelper.getInstance().addChatCommand(cmd);
                            mAdapter.list.add(cmd);
                        } else {
                            //update existing command item
                            DBHelper.getInstance().updateCommand(cmd);
                        }
                        mAdapter.notifyDataSetChanged();
                        tvListIsEmpty.setVisibility(View.GONE);
                    }
                })
                .show();


        tvHelpFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = UIUtils.inflate(mContext, R.layout.layout_dialog_help_warning_formattin);
                TextView tvHelpText = UIUtils.getView(view, R.id.tvHelpText);
                String text = getString(R.string.text_help_formatting);

                tvHelpText.setText(Html.fromHtml(text.replaceAll("(\r\n|\n)", "<br />")));

                new AlertDialog.Builder(mContext)
                        .setTitle("Formattin help")
                        .setView(view)
                        //"\nFormatting:\n" +
                        //"You can user tilda ` symbol for highlite text")
                        .setPositiveButton("Ok", null)
                        .show();
            }
        });
    }


    private void dialogClearAll() {
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.title_dialog_clear_all)
                .setMessage(R.string.text_clear_all_commands_for_chat)
                .setNeutralButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.btnYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper.getInstance().clearChatCommands(chatId);
                        mAdapter.list.clear();
                        mAdapter.notifyDataSetChanged();
                        tvListIsEmpty.setVisibility(View.VISIBLE);
                    }
                })
                .show();
    }


    /**
     * Adapter
     */
    class Adapter extends BaseAdapter {

        ArrayList<ChatCommand> list = new ArrayList<>(0);

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public ChatCommand getItem(int position) {
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
                view = UIUtils.inflate(mContext, R.layout.list_item_chat_command);
            }

            ChatCommand cmd = getItem(position);

            TextView tvCommand = UIUtils.getHolderView(view, R.id.tvCommand);
            CheckBox chkIsEnabled = UIUtils.getHolderView(view, R.id.chkIsEnabled);
            ImageView ivIsAdmin = UIUtils.getHolderView(view, R.id.ivIsAdmin);


            if (viewCreated) {
                chkIsEnabled.setOnClickListener(onClickListener);
                //UIUtils.setBatchClickListener(onClickListener, chkIsEnabled);
            }

            chkIsEnabled.setTag(R.id.id_position, position);
            chkIsEnabled.setChecked(cmd.isEnabled);
            ivIsAdmin.setVisibility(cmd.isAdmin?View.VISIBLE:View.GONE);

            tvCommand.setText("/" + cmd.cmd);

            return view;
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = Integer.valueOf(v.getTag(R.id.id_position).toString());
                ChatCommand cmd = getItem(pos);
                switch (v.getId()) {
                    case R.id.chkIsEnabled:
                        cmd.isEnabled = ((CheckBox) v).isChecked();
                        DBHelper.getInstance().updateCommand(cmd);
                        break;
                }
            }
        };
    }

    /**
     * Adapter end
     */
}
