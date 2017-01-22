package com.madpixels.tgadmintools.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.PathUtil;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.ui.ActivityExtended;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.services.ServiceChatTask;

import org.drinkless.td.libcore.telegram.TdApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Snake on 21.03.2016.
 */
public class ActivitySettings extends ActivityExtended {

    boolean whiteLinksChanged = false;

    EditText editWhiteListLinks, edtAntispamAlertTitle;
    CheckBox chkAllowStickersInLinks, chkAllowMentionLinks, chkIgnorePhoneUsers, chkShowServiceAlways;
    TextView tvBackupSettings, tvRestoreBackup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        UIUtils.setToolbarWithBackArrow(this, R.id.toolbar);
        setTitle(R.string.action_settings);

        editWhiteListLinks = getView(R.id.editWhiteListLinks);
        chkAllowStickersInLinks = getView(R.id.chkAllowStickersInLinks);
        CheckBox chkAutoRun = getView(R.id.chkAutoRun);
        chkAllowMentionLinks = getView(R.id.chkAllowMentionLinks);
        chkIgnorePhoneUsers = getView(R.id.chkIgnorePhoneUsers);
        chkShowServiceAlways = getView(R.id.chkShowServiceAlways);
        edtAntispamAlertTitle = getView(R.id.edtAntispamAlertTitle);
        tvBackupSettings = getView(R.id.tvBackupSettings);
        tvRestoreBackup = getView(R.id.tvRestoreBackup);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            findViewById(R.id.tvNoteForDozeAndroid_API23).setVisibility(View.GONE);
        } else {
            findViewById(R.id.tvNoteForDozeAndroid_API23).setOnClickListener(onClickListener);
        }
        chkAllowStickersInLinks.setChecked(Sets.getBoolean(Const.ANTISPAM_ALLOW_STICKERS_LINKS, true));
        chkAllowMentionLinks.setChecked(Sets.getBoolean(Const.ANTISPAM_ALLOW_MENTION_LINKS, true));
        chkIgnorePhoneUsers.setChecked(Sets.getBoolean(Const.ANTISPAM_IGNORE_SHARED_CONTACTS, Const.ANTISPAM_IGNORE_SHARED_CONTACTS_DEFAULT));
        chkShowServiceAlways.setChecked(Sets.getBoolean(Const.START_SERVICE_AS_FOREGROUND, true));
        edtAntispamAlertTitle.setText(Sets.getString(Const.ANTISPAM_ALERT_TITLE, getString(R.string.text_antispam_alert_title)));

        chkAutoRun.setChecked(Sets.getBoolean(Const.AUTORUN, true));
        Spinner spinnerLogLifeTime = getView(R.id.spinnerLogLifeTime);

        String[] banTimes = getResources().getStringArray(R.array.log_lifetime);
        final ArrayAdapter<String> pLogLifeAdapter = new ArrayAdapter<String>(
                mContext, R.layout.item_spinner, R.id.textView, banTimes);
        spinnerLogLifeTime.setAdapter(pLogLifeAdapter);
        spinnerLogLifeTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long ts = 0;
                switch (position) {
                    case 0:
                        ts = AlarmManager.INTERVAL_DAY * 30; // 30 дней
                        break;
                    case 1:
                        ts = AlarmManager.INTERVAL_DAY * 7; // неделя
                        break;
                    case 2:
                        ts = AlarmManager.INTERVAL_DAY * 3; // 30 дней
                        break;
                    case 3:
                        ts = AlarmManager.INTERVAL_DAY; // 30 дней
                        break;
                }
                Sets.set(Const.LOG_LIFE_TIME, ts);
                // MyToast.toastL(mContext, ts + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        long logLifeTime = Sets.getLong(Const.LOG_LIFE_TIME, AlarmManager.INTERVAL_DAY);
        if (logLifeTime == AlarmManager.INTERVAL_DAY)
            spinnerLogLifeTime.setSelection(3);
        else if (logLifeTime == AlarmManager.INTERVAL_DAY * 3)
            spinnerLogLifeTime.setSelection(2);
        else if (logLifeTime == AlarmManager.INTERVAL_DAY * 7)
            spinnerLogLifeTime.setSelection(1);
        else spinnerLogLifeTime.setSelection(0);

        String[] links = DBHelper.getInstance().getLinksWhiteList();
        if (links != null) {
            StringBuilder sb = new StringBuilder();
            for (String s : links)
                sb.append(s).append("\n");
            editWhiteListLinks.setText(sb.toString().trim());
            whiteLinksChanged = false;
        }


        UIUtils.setBatchClickListener(onClickListener, chkAllowStickersInLinks, chkAllowMentionLinks,
                chkAutoRun, chkIgnorePhoneUsers, chkShowServiceAlways, tvBackupSettings, tvRestoreBackup);
        editWhiteListLinks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                whiteLinksChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtAntispamAlertTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                edtAntispamAlertTitle.setTag(System.currentTimeMillis());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                edtAntispamAlertTitle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (edtAntispamAlertTitle.getTag() == null)
                            return;

                        long ts = Long.valueOf(edtAntispamAlertTitle.getTag().toString());
                        if (System.currentTimeMillis() - ts < 1200)
                            return;

                        String text = edtAntispamAlertTitle.getText().toString().trim();
                        Sets.set(Const.ANTISPAM_ALERT_TITLE, text);

                        edtAntispamAlertTitle.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    }
                }, 1300);

            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.chkAllowStickersInLinks:
                    boolean enabled = chkAllowStickersInLinks.isChecked();
                    Sets.set(Const.ANTISPAM_ALLOW_STICKERS_LINKS, enabled);
                    break;
                case R.id.chkAllowMentionLinks:
                    Sets.set(Const.ANTISPAM_ALLOW_MENTION_LINKS, chkAllowMentionLinks.isChecked());
                    break;
                case R.id.chkAutoRun:
                    CheckBox chk = (CheckBox) v;
                    Sets.set(Const.AUTORUN, chk.isChecked());
                    break;
                case R.id.chkIgnorePhoneUsers:
                    Sets.set(Const.ANTISPAM_IGNORE_SHARED_CONTACTS, chkIgnorePhoneUsers.isChecked());
                    break;
                case R.id.chkShowServiceAlways:
                    Sets.set(Const.START_SERVICE_AS_FOREGROUND, chkShowServiceAlways.isChecked());
                    ServiceChatTask.stop(mContext);
                    ServiceChatTask.start(mContext);
                    break;
                case R.id.tvBackupSettings:
                    showSaveFileDialog();
                    break;
                case R.id.tvRestoreBackup:
                    showRestoreDialog();
                    break;
                case R.id.tvNoteForDozeAndroid_API23:
                    dialogAndroidDozeDescription();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        close();
    }

    public void close() {
        if (whiteLinksChanged) {
            saveWhiteLinks();
        }
        finish();
    }

    private void saveWhiteLinks() {
        String text = editWhiteListLinks.getText().toString().trim();
        if (text.isEmpty()) {
            DBHelper.getInstance().saveLinksWhiteList(new String[]{});
        } else {
            String[] lines = text.split("\n");
            DBHelper.getInstance().saveLinksWhiteList(lines);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Const.ACTION_PICK_BACKUP_TO_RESTORE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    try {
                        String filePath = PathUtil.getPath(mContext, uri);
                        if (filePath.endsWith(".db"))
                            restoreBackup(filePath);
                        else
                            MyToast.toast(mContext, "Error, unknown file format. Requared Adt db file");
                    } catch (URISyntaxException e) {
                        MyToast.toast(mContext, e.getMessage());
                    }

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Const.ACTION_REQUEST_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                MyToast.toastL(mContext, "Storage permission granted.\nPlease run backup operation again");
            }

        }
    }


    private void showSaveFileDialog() {
        if (!MainActivity.checkStoragePermissions(this)) {
            MyToast.toast(mContext, "Required Storage Permission");
            return;
        }

        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AdminToolsTelegram/";
        File fDir = new File(dir);
        fDir.mkdirs();

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String filename = "AdminToolsTgBackup_%s_%s_%d.db";
        filename = String.format(filename, new DecimalFormat("00").format(day), new DecimalFormat("00").format(month), year);

        backupDatabase(new File(fDir, filename));
    }

    private void showRestoreDialog() {
        if (!MainActivity.checkStoragePermissions(this)) {
            MyToast.toast(mContext, "Required Storage Permission");
            return;
        }

        Intent intent = new Intent();
        intent.setType("*/*");
        if (Build.VERSION.SDK_INT >= 19) {
            //intent = new Intent("android.intent.action.OPEN_DOCUMENT");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }


        // i.addCategory(Intent.CATEGORY_);
        try {
            startActivityForResult(intent, Const.ACTION_PICK_BACKUP_TO_RESTORE);
        } catch (Exception e) {
            MyToast.toast(mContext, "Error opening File Manager.\n" + e.getMessage());
        }
    }

    private void backupDatabase(File backupFilename) {
        File sd = Environment.getExternalStorageDirectory();
        //File data = Environment.getDataDirectory();
        if (sd.canWrite()) {
            File currentDB = new File(DBHelper.getInstance().db.getPath());
            // File backupDB = //new File(directoryChosenByUser, "AdminToolsTelegram_backup.db");
            MainActivity.stopApplication(mContext);
            DBHelper.getInstance().dbClose();
            try {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupFilename).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                // MyToast.toast(mContext, "AdminTools backup saved to\n" + backupFilename.getAbsolutePath());
                MainActivity.startApplication(mContext);
                saveBackupToCloudStorage(backupFilename);
            } catch (IOException e) {
                MyToast.toast(mContext, "save db error!");
            }
        }
    }

    private void restoreBackup(String backupDBPath) {
        File sd = Environment.getExternalStorageDirectory();
        if (sd.canRead()) {
            String dbPath = DBHelper.getInstance().db.getPath();
            File dbFile = new File(dbPath);
            File backupFile = new File(backupDBPath);
            MainActivity.stopApplication(mContext);
            DBHelper.getInstance().dbClose();
            try {
                FileChannel src = new FileInputStream(backupFile).getChannel();
                FileChannel dst = new FileOutputStream(dbFile).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                try {
                    DBHelper.getInstance();
                } catch (Exception ex) {
                    MyToast.toast(mContext, "Open database error!");
                    dbFile.delete();
                    return;
                }

                MyToast.toast(mContext, "Databse restored");
            } catch (IOException e) {
                MyToast.toastL(mContext, "Read file error:\n" + e.getMessage());
                return;
            }
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            Runtime.getRuntime().exit(0);
        }
    }

    void saveBackupToCloudStorage(final File backupFile) {
        final Runnable uploadFileRunnable = new Runnable() {
            @Override
            public void run() {
                TdApi.TLObject obj = TgH.execute(new TdApi.GetMe());
                if (obj.getConstructor() == TdApi.User.CONSTRUCTOR) {
                    TdApi.User me = (TdApi.User) obj;
                    TdApi.InputFile f = new TdApi.InputFileLocal(backupFile.getAbsolutePath());
                    TdApi.InputMessageDocument doc = new TdApi.InputMessageDocument(f, null, "TelegramAdminToolsBackup File #adtbackup");
                    TdApi.SendMessage sendMsg = new TdApi.SendMessage();
                    sendMsg.inputMessageContent = doc;
                    sendMsg.chatId = me.id;
                    sendMsg.disableNotification = true;
                    obj = TgH.execute(new TdApi.CreatePrivateChat(me.id));
                    if (obj.getConstructor() == TdApi.Chat.CONSTRUCTOR) {
                        TdApi.Chat chat = (TdApi.Chat) obj;
                        sendMsg.chatId = chat.id;
                        obj = TgH.execute(sendMsg);
                        MyLog.log("Save task created " + obj.toString());
                        if (obj.getConstructor() == TdApi.Message.CONSTRUCTOR)
                            MyToast.toast(mContext, "File saved to Cloud Storage");
                        else
                            MyToast.toast(mContext, "Error saving file to Cloud Storage");
                    }
                }
            }
        };


        new AlertDialog.Builder(mContext)
                .setTitle("Save backup")
                .setMessage(getString(R.string.text_backup_saved, backupFile.getAbsolutePath()))
                .setNegativeButton(R.string.btnCancel, null)
                .setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(uploadFileRunnable).start();
                    }
                })
                .show();
    }

    void dialogAndroidDozeDescription() {

        LinearLayout ln = new LinearLayout(mContext);
        ln.setOrientation(LinearLayout.VERTICAL);
        int padding = UIUtils.getPixelsFromDp(16);
        ln.setPadding(padding, padding, padding, padding);
        TextView tv = new TextView(mContext);
        tv.setText(R.string.text_dozen_battery_white_list_api23);
        ln.addView(tv);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv = new TextView(mContext);
            ln.addView(tv);
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (pm.isIgnoringBatteryOptimizations(BuildConfig.APPLICATION_ID))
                tv.setText(R.string.text_battery_optimization_state_disabled);
            else
                tv.setText(R.string.text_battery_optimization_state_enabled);
        }

        new AlertDialog.Builder(mContext)
                .setTitle("Battery Optimization")
                .setView(ln)
                .setNegativeButton(R.string.btnClose, null)
                .show();
    }
}
