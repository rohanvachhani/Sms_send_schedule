package com.rohan.sms_send_schedule.Activity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rohan.sms_send_schedule.DbHelper;
import com.rohan.sms_send_schedule.R;
import com.rohan.sms_send_schedule.Scheduler;
import com.rohan.sms_send_schedule.SmsModel;
import com.rohan.sms_send_schedule.view.BuilderCancel;
import com.rohan.sms_send_schedule.view.BuilderContact;
import com.rohan.sms_send_schedule.view.BuilderDate;
import com.rohan.sms_send_schedule.view.BuilderMessage;
import com.rohan.sms_send_schedule.view.BuilderRecurringMode;
import com.rohan.sms_send_schedule.view.BuilderSimCard;
import com.rohan.sms_send_schedule.view.BuilderTime;
import com.rohan.sms_send_schedule.view.EmptinessTextWatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public class AddSmsActivity extends BaseManuActivity {

    final public static int RESULT_SCHEDULED = 1;
    final public static int RESULT_UNSCHEDULED = 2;
    final public static String INTENT_SMS_ID = "INTENT_SMS_ID";

    final private static String SMS_STATE = "SMS_STATE";
    final private static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    //runtime permissions from abovr 6.0 !!     /$/ROHAN VACHHANI
    final private String[] permissionsRequired = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_CONTACTS
    };

    private SmsModel sms;
    private ArrayList<String> permissionsGranted = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.menu_settings:
                startActivityForResult(new Intent(this, com.rohan.sms_send_schedule.Activity.SmsSchedulerPreferenceActivity.class), 1);
                break;*/
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionsGranted()) {
            buildForm();
        }
    }

    private void buildForm() {              //user..enter ur info..!!//Rohan vachhani
        EditText formMessage = findViewById(R.id.form_input_message);
        AutoCompleteTextView formContact = findViewById(R.id.form_input_contact);//AutoCompleteTextView-list of completion suggestions..!
        TextWatcher watcherEmptiness = new com.rohan.sms_send_schedule.view.EmptinessTextWatcher(this, formContact, formMessage);
        formContact.addTextChangedListener(watcherEmptiness);
        formMessage.addTextChangedListener(watcherEmptiness);

        new com.rohan.sms_send_schedule.view.BuilderMessage().setView(formMessage).setSms(sms).build();
        new com.rohan.sms_send_schedule.view.BuilderContact().setView(formContact).setSms(sms).setActivity(this).build();

        new com.rohan.sms_send_schedule.view.BuilderSimCard().setActivity(this).setView(findViewById(R.id.form_sim_card)).setSms(sms).build();
        new com.rohan.sms_send_schedule.view.BuilderRecurringMode()
                .setRecurringDayView((Spinner) findViewById(R.id.form_recurring_day))
                .setRecurringMonthView((Spinner) findViewById(R.id.form_recurring_month))
                .setDateView((DatePicker) findViewById(R.id.form_date))
                .setActivity(this)
                .setView(findViewById(R.id.form_recurring_mode))
                .setSms(sms)
                .build()
        ;

        new com.rohan.sms_send_schedule.view.BuilderTime().setActivity(this).setView(findViewById(R.id.form_time)).setSms(sms).build();
        new com.rohan.sms_send_schedule.view.BuilderDate().setActivity(this).setView(findViewById(R.id.form_date)).setSms(sms).build();

        new com.rohan.sms_send_schedule.view.BuilderCancel().setView(findViewById(R.id.button_cancel)).setSms(sms).build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DbHelper.closeDbHelper();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DbHelper.closeDbHelper();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sms);

        long smsId = getIntent().getLongExtra(INTENT_SMS_ID, 0L);
        if (smsId > 0) {
            sms = DbHelper.getDbHelper(this).get(smsId);
        } else if (null != savedInstanceState) {
            sms = savedInstanceState.getParcelable(SMS_STATE);
        }
        if (null == sms) {
            sms = new SmsModel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != sms) {
            outState.putParcelable(SMS_STATE, sms);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (null == sms) {
            sms = savedInstanceState.getParcelable(SMS_STATE);
        }
        if (null == sms) {
            sms = new SmsModel();
        }
    }

    public void scheduleSms(View view) {            //onclick schedule button...
        if (!validateForm()) {
            return;
        }
        sms.setStatus(SmsModel.STATUS_PENDING);
        DbHelper.getDbHelper(this).save(sms);
        new Scheduler(getApplicationContext()).schedule(sms);
        setResult(RESULT_SCHEDULED, new Intent());
        finish();
    }

    public void unscheduleSms(View view) {
        DbHelper.getDbHelper(this).delete(sms.getTimestampCreated());
        new Scheduler(getApplicationContext()).unschedule(sms.getTimestampCreated());
        setResult(RESULT_UNSCHEDULED, new Intent());
        finish();
    }

    private boolean validateForm() {
        boolean result = true;
        if (sms.getTimestampScheduled() < GregorianCalendar.getInstance().getTimeInMillis()) {
            Toast.makeText(getApplicationContext(), getString(R.string.form_validation_datetime), Toast.LENGTH_SHORT).show();
            result = false;
        }
        if (TextUtils.isEmpty(sms.getRecipientNumber())) {
            ((AutoCompleteTextView) findViewById(R.id.form_input_contact)).setError(getString(R.string.form_validation_contact));
            result = false;
        }
        if (TextUtils.isEmpty(sms.getMessage())) {
            ((EditText) findViewById(R.id.form_input_message)).setError(getString(R.string.form_validation_message));
            result = false;
        }
        return result;
    }

    private boolean permissionsGranted() {
        boolean granted = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissionsNotGranted = new ArrayList<>();
            for (String required : this.permissionsRequired) {
                if (checkSelfPermission(required) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNotGranted.add(required);
                } else {
                    this.permissionsGranted.add(required);
                }
            }
            if (permissionsNotGranted.size() > 0) {
                granted = false;
                String[] notGrantedArray = permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]);
                requestPermissions(notGrantedArray, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        }
        return granted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                List<String> requiredPermissions = Arrays.asList(this.permissionsRequired);
                String permission;
                for (int i = 0; i < permissions.length; i++) {
                    permission = permissions[i];
                    if (requiredPermissions.contains(permission) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        this.permissionsGranted.add(permission);
                    }
                }
                if (this.permissionsGranted.size() == this.permissionsRequired.length) {
                    buildForm();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
