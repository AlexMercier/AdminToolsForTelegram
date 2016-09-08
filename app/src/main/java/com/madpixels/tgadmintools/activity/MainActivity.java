package com.madpixels.tgadmintools.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.madpixels.apphelpers.MyLog;
import com.madpixels.apphelpers.MyToast;
import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.Utils;
import com.madpixels.apphelpers.ui.ActivityExtended;
import com.madpixels.tgadmintools.BuildConfig;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.helper.TgH;
import com.madpixels.tgadmintools.helper.TgUtils;
import com.madpixels.tgadmintools.services.ServiceAntispam;
import com.madpixels.tgadmintools.services.ServiceAutoKicker;
import com.madpixels.tgadmintools.services.ServiceGarbageCollector;
import com.madpixels.tgadmintools.services.ServiceUnbanTask;
import com.madpixels.tgadmintools.utils.Analytics;

import org.drinkless.td.libcore.telegram.Client;
import org.drinkless.td.libcore.telegram.TG;
import org.drinkless.td.libcore.telegram.TdApi;

import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;

import libs.AdHelper;

public class MainActivity extends ActivityExtended {

    public static boolean WRITE_LOG = false;

    TextView tvAdditionalStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeLanguage(this);
        setContentView(R.layout.activity_main);
        UIUtils.setToolbar(this, R.id.toolbar);
        AdHelper.setup(this);

        tvAdditionalStatus = (TextView) findViewById(R.id.tvAdditionalStatus);

        Button btnClearCache = (Button) findViewById(R.id.btnClearCache);
        final Button btnStop = (Button) findViewById(R.id.btnStop);
        final CheckBox checkBoxWriteLog = (CheckBox) findViewById(R.id.checkBoxWriteLog);



        if(!BuildConfig.DEBUG) {
            btnClearCache.setVisibility(View.GONE);
            checkBoxWriteLog.setVisibility(View.GONE);
        }
        tvAdditionalStatus.setText("Checking auth...");
        final TextView textViewAppState = getView(R.id.textViewAppState);

        findViewById(R.id.btnGroups).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ActivityGroupsList.class));
            }
        });

        TgH.init(this);
        checkAuth();

        checkBoxWriteLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WRITE_LOG = checkBoxWriteLog.isChecked();
                MyLog.WRITE_TO_FILE=WRITE_LOG;
                MyToast.toast(getApplication(), "write is " + (WRITE_LOG ? "enabled" : "disabled"));
            }
        });

        btnClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TgH.clearCache(MainActivity.this);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() == null) {
                    stopApplication();
                    btnStop.setText("Start");
                    btnStop.setTag("1");
                    textViewAppState.setText(R.string.text_appstate_stopped);
                } else {
                    startApplication();
                    btnStop.setText("Stop");
                    textViewAppState.setText(R.string.text_appstate_enabled);
                    btnStop.setTag(null);
                }
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TgH.clearCache(mContext);
            }
        }, 1300);
    }

    public static void initializeLanguage(Context c) {
        if (c.getString(R.string.lang_code).equals("en")) {
            String pCurrentLang = "en";
            try {
                pCurrentLang = Locale.getDefault().getISO3Country();
            } catch (MissingResourceException e) {
                e.printStackTrace();
            }
            String[] ruLangs = new String[]{"UKR", "KAZ", "BLR"};
            if (Arrays.asList(ruLangs).contains(pCurrentLang)) {
                android.content.res.Resources resource = c.getResources();
                // Change locale settings in the app.
                DisplayMetrics dm = resource.getDisplayMetrics();
                android.content.res.Configuration conf = resource.getConfiguration();
                conf.locale = new Locale("ru");
                resource.updateConfiguration(conf, dm); //force set Russian
            }
        }
    }

    void checkAuth() {
        TgH.sendOnUi(new TdApi.GetAuthState(), new Client.ResultHandler() {
            @Override
            public void onResult(final TdApi.TLObject object) {
                checkAuthState(object);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 100, 0, R.string.action_settings);
        menu.add(0, 101, 0, R.string.action_log);
        menu.add(0, 102, 0, R.string.action_change_user);
        menu.add(0, 103, 0, "About");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 100:
                startActivity(new Intent(this, ActivitySettings.class));
                break;
            case 101:
                startActivity(new Intent(this, ActivityLogView.class));
                break;
            case 102:
                dialogChangeAccount();
                break;
            case 103:
                dialogAbout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogChangeAccount() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title_logout)
                .setMessage(R.string.dialog_text_logout)
               .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.btnContinue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeAccount();
                    }
                })
                .show();
    }

    private void changeAccount() {
        TgH.sendOnUi(new TdApi.ResetAuth(true), new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                stopApplication();
                DBHelper.getInstance().dbClose();
                Sets.removeSetting(Const.SETS_PROFILE_ID);
                TgH.init(mContext);
                checkAuth();
            }
        });
    }



    void checkAuthState(TdApi.TLObject object) {
        if (TgUtils.isError(object)) {
            TdApi.Error e = (TdApi.Error) object;
            switch (e.code) {
                case 400:
                    MyToast.toast(MainActivity.this, e.text);
                    switch (e.text){
                        case "PHONE_CODE_INVALID":
                            showLoginDialog(TdApi.AuthStateWaitCode.CONSTRUCTOR);
                            break;
                        case "PHONE_NUMBER_INVALID":
                            showLoginDialog(TdApi.AuthStateWaitPhoneNumber.CONSTRUCTOR);
                            break;
                        case "PASSWORD_HASH_INVALID":
                            showLoginDialog(TdApi.AuthStateWaitPassword.CONSTRUCTOR);
                            break;
                        default:
                            Analytics.sendReport("AuthError", e.text, e.toString());
                            showInstantErrorDialog(e);
                            break;
                    }
                    break;
                default:
                    Analytics.sendReport("AuthError", e.text, e.toString());
                    showInstantErrorDialog(e);
                    break;
            }
            return;
        }

        TdApi.AuthState as = (TdApi.AuthState) object;
        switch (as.getConstructor()) {
            case TdApi.AuthStateWaitCode.CONSTRUCTOR:
                tvAdditionalStatus.setText("Requared sms code...");
                showLoginDialog(as.getConstructor());
                break;
            case TdApi.AuthStateWaitPhoneNumber.CONSTRUCTOR:
                showLoginDialog(as.getConstructor());
                break;
            case TdApi.AuthStateWaitPassword.CONSTRUCTOR:
                tvAdditionalStatus.setText("Requared login password...");
                showLoginDialog(as.getConstructor());
                break;
            case TdApi.AuthStateOk.CONSTRUCTOR:
                tvAdditionalStatus.setText("Status: Authorized");
                onLoginSucces();
                break;
        }
    }


    void onLoginSucces() {
        TgH.getProfile(new Client.ResultHandler() {
            @Override
            public void onResult(TdApi.TLObject object) {
                startActivity(new Intent(mContext, ActivityGroupsList.class));
                ServiceUnbanTask.registerTask(mContext);
                ServiceAutoKicker.registerTask(mContext);
                ServiceAntispam.start(mContext);
                ServiceGarbageCollector.start(mContext);


//                int rulesCount = DBHelper.getInstance().getChatRulesCount(); // кол-во правил чата
//                // int bannedCount = DBHelper.getInstance().getTotalBannedCount();
//                final String text = "Active rules count: "+rulesCount;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tvAdditionalStatus.setText(tvAdditionalStatus.getText().toString()+"\n"+text);
//                    }
//                });
            }
        });
    }

    private void stopApplication() {
        TG.stopClient();
        ServiceAntispam.stop(this);
        ServiceAutoKicker.stop(mContext);
        ServiceUnbanTask.unregister(mContext);
    }

    private void startApplication() {
        TgH.init(MainActivity.this);
        TgH.sendOnUi(new TdApi.GetAuthState(), new Client.ResultHandler() {
            @Override
            public void onResult(final TdApi.TLObject object) {
                ServiceAntispam.start(mContext);
                ServiceAutoKicker.registerTask(mContext);
                ServiceUnbanTask.registerTask(mContext);
            }
        });

    }

    private void showLoginDialog(final int action) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_login, null);
        final AlertDialog d = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.dialog_title_auth)
                .setView(view)
                .show();
        final Button login = (Button) view.findViewById(R.id.bLogin);
        final Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        final EditText eEditText = (EditText) view.findViewById(R.id.ePhone);


        switch (action) {
            case TdApi.AuthStateWaitPhoneNumber.CONSTRUCTOR:
                eEditText.setHint(R.string.auth_phone_hint);
                eEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case TdApi.AuthStateWaitCode.CONSTRUCTOR:
                eEditText.setHint(R.string.auth_confirmcode_hint);
                eEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case TdApi.AuthStateWaitPassword.CONSTRUCTOR:
                eEditText.setHint(R.string.auth_cloud_pass_hint);
                break;
            default:
                // Analytics.sendReport("LogAction", action.name(), "");
                break;
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TgH.send(new TdApi.ResetAuth(true));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = eEditText.getText().toString();

                TdApi.TLFunction func = null;
                switch (action) {
                    case TdApi.AuthStateWaitPhoneNumber.CONSTRUCTOR:
                        func = new TdApi.SetAuthPhoneNumber(text);
                        break;
                    case TdApi.AuthStateWaitCode.CONSTRUCTOR:
                        func = new TdApi.CheckAuthCode(text);
                        break;
                    case TdApi.AuthStateWaitPassword.CONSTRUCTOR:
                        func = new TdApi.CheckAuthPassword(text);
                        break;
                }

                Client c = TgH.TG();
                eEditText.setEnabled(false);
                login.setText(R.string.label_loading);
                c.send(func, new Client.ResultHandler() {
                    @Override
                    public void onResult(final TdApi.TLObject object) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                d.dismiss();
                                checkAuthState(object);
                            }
                        });
                    }
                });
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                eEditText.requestFocus();
                UIUtils.showSoftKeyboard(eEditText);
            }
        }, 500);
    }


    private void showInstantErrorDialog(TdApi.Error e) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Auth error")
                .setMessage("Error code: " + e.code + "\nError message: " + e.text + "\n" +
                        "Try again?")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showLoginDialog(TdApi.AuthStateWaitPhoneNumber.CONSTRUCTOR);
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }


    void dialogAbout(){
        View v = UIUtils.inflate(mContext, R.layout.dialog_about);
        TextView version = UIUtils.getView(v, R.id.tvVersion);
        TextView tvReleaseDate = UIUtils.getView(v, R.id.tvReleaseDate);
        TextView tvRateUs = UIUtils.getView(v, R.id.tvRateUs);
        TextView tvContact = UIUtils.getView(v, R.id.tvContact);

        version.setText("v"+BuildConfig.VERSION_NAME);
        tvReleaseDate.setText(R.string.buildDate);

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.tvContact:
                        Utils.openUrl("https://telegram.me/TgAndroAdminToolsBot", mContext);
                        break;
                    case R.id.tvRateUs:
                        Utils.openStore(mContext);
                        break;
                }
            }
        };

       UIUtils.setBatchClickListener(onClickListener, tvRateUs, tvContact);

        new AlertDialog.Builder(mContext)
                .setTitle("About")
                .setView(v)
                .setNegativeButton("Close", null)
                .show();

    }
}
