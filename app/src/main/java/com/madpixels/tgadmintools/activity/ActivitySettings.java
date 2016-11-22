package com.madpixels.tgadmintools.activity;

import android.app.AlarmManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.madpixels.apphelpers.Sets;
import com.madpixels.apphelpers.UIUtils;
import com.madpixels.apphelpers.ui.ActivityExtended;
import com.madpixels.tgadmintools.Const;
import com.madpixels.tgadmintools.R;
import com.madpixels.tgadmintools.db.DBHelper;
import com.madpixels.tgadmintools.services.ServiceChatTask;

/**
 * Created by Snake on 21.03.2016.
 */
public class ActivitySettings extends ActivityExtended {

    boolean whiteLinksChanged = false;

    EditText editWhiteListLinks, edtAntispamAlertTitle;
    CheckBox chkAllowStickersInLinks,chkAllowMentionLinks, chkIgnorePhoneUsers, chkShowServiceAlways;

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


        UIUtils.setBatchClickListener(onClickListener, chkAllowStickersInLinks,chkAllowMentionLinks,
                chkAutoRun, chkIgnorePhoneUsers, chkShowServiceAlways);
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
            }
        }
    };

    @Override
    public void onBackPressed() {
        close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                close();
                break;
        }
        return super.onOptionsItemSelected(item);
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
}
