package com.rohan.sms_send_schedule.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rohan.sms_send_schedule.CalendarResolver;
import com.rohan.sms_send_schedule.DbHelper;
import com.rohan.sms_send_schedule.R;
import com.rohan.sms_send_schedule.Scheduler;
import com.rohan.sms_send_schedule.SmsModel;
import com.rohan.sms_send_schedule.view.BuilderSimForResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.rohan.sms_send_schedule.Activity.AddSmsActivity.RESULT_SCHEDULED;

public class result_notify extends Activity implements View.OnClickListener {

    private Button button_send;
    private Button button_fetch;
    private TextView textView_info;
    private String contact_no[];
    private String result[];
    private String name[];
    private int FIRST_TIME = 0;
    private int s_id;

    private SmsModel sms;
    private ArrayList<SmsModel> sms_array;
    private static long time_var = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_notify);

        button_fetch = (Button) findViewById(R.id.button_fetch);
        button_send = (Button) findViewById(R.id.button_send);
        textView_info = (TextView) findViewById(R.id.info);

        button_fetch.setOnClickListener(this);
        //for testing ..this will be done by fetching all students data from DATABASE..!
        contact_no = new String[]{"9099850038", "8141543554"};
        name = new String[]{"rohan", "second_student"};
        result = new String[]{"9.2", "8.2"};
        sms = new SmsModel();
        sms_array = new ArrayList<>();

        new com.rohan.sms_send_schedule.view.BuilderSimForResult().setActivity(this).setView(findViewById(R.id.form_sim_card_n)).setSms(sms).build();
        //   s_id = new SmsModel().getSubscriptionId();
    }


    @Override
    public void onClick(View view) {
        sms_array.clear();
        for (int i = 0; i < contact_no.length; i++) {
            sms = new SmsModel();

            if (FIRST_TIME == 0) {
                //   new com.rohan.sms_send_schedule.view.BuilderSimForResult().setActivity(this).setView(findViewById(R.id.form_sim_card_n)).setSms(sms).build();
                s_id = BuilderSimForResult.s_id;
                FIRST_TIME = 1;
            }
            String formMessage = result[i];
            String RecipientName = name[i];
            String formContact = contact_no[i];

            //setting values for sms class' object
            sms.setMessage(formMessage);
            sms.setRecipientName(RecipientName);
            sms.setRecipientNumber(formContact);
            sms.setSubscriptionId(s_id);
            Calendar c = GregorianCalendar.getInstance();
            sms.getCalendar().set(GregorianCalendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
            sms.getCalendar().set(GregorianCalendar.MINUTE, c.get(Calendar.MINUTE));
            sms.getCalendar().set(GregorianCalendar.YEAR, c.get(Calendar.YEAR));
            sms.getCalendar().set(GregorianCalendar.MONTH, c.get(Calendar.MONTH));
            sms.getCalendar().set(GregorianCalendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));

            sms_array.add(sms);

            sendSms(sms);
           /* sms.setStatus(SmsModel.STATUS_PENDING);
            DbHelper.getDbHelper(getApplicationContext()).save(sms);
            new Scheduler(getApplicationContext()).schedule(sms);
            setResult(RESULT_SCHEDULED, new Intent());
            finish();*/


            //FIRST_TIME = 1;

            //  SimUtil.sendSMS(this,1,formContact,null,formMessage,null,null);     //using sim 2
            Toast.makeText(this, "testing toast", Toast.LENGTH_SHORT).show();

            //sendResult(view, sms);

           /* sms.setSubscriptionId(s_id);
            //ob.setTimestampScheduled(time);       //add 3 min. // for proper scheduling
            sms.setStatus(SmsModel.STATUS_PENDING);
            DbHelper.getDbHelper(this).save(sms);
            new Scheduler(getApplicationContext()).schedule(sms);
            setResult(RESULT_SCHEDULED, new Intent());*/

            //while(!sms.getStatus())
            // new com.rohan.sms_send_schedule.view.BuilderMessage().setView(formMessage).setSms(sms).build();
        }
    }

    private void sendSms(SmsModel sms) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<>();
        /*PendingIntent sentPendingIntent = getPendingIntent(sms.getTimestampCreated(), com.rohan.sms_send_schedule.SmsSentReceiver.class);
        PendingIntent deliveredPendingIntent = getPendingIntent(sms.getTimestampCreated(), SmsDeliveredReceiver.class);*/

        SmsManager smsManager = getSmsManager(sms.getSubscriptionId());
        ArrayList<String> smsMessage = smsManager.divideMessage(sms.getMessage());
        boolean deliveryReports = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getBoolean(SmsSchedulerPreferenceActivity.PREFERENCE_DELIVERY_REPORTS, false);
        /*for (int i = 0; i < smsMessage.size(); i++) {
            sentPendingIntents.add(i, sentPendingIntent);
            if (deliveryReports) {
                deliveredPendingIntents.add(i, deliveredPendingIntent);
            }
        }*/
        smsManager.sendMultipartTextMessage(
                sms.getRecipientNumber(),
                null,
                smsMessage,
                sentPendingIntents,
                deliveryReports ? deliveredPendingIntents : null
        );
    }

    public void sendResult(View view, SmsModel ob) {

        //  for (int i=sms_array.size()-1;i>=0;i--) {

        //SmsModel ob = sms_array.get(i);
        long time = System.currentTimeMillis();

        ob.setSubscriptionId(s_id);
        ob.setTimestampScheduled(time + ((time_var) * 1800));       //add 3 min. // for proper scheduling
        ob.setStatus(SmsModel.STATUS_PENDING);
        DbHelper.getDbHelper(this).save(ob);
        new Scheduler(getApplicationContext()).schedule(ob);
        setResult(RESULT_SCHEDULED, new Intent());
        finish();
        time_var += 1;
       /*SmsManager smsManager = getSmsManagerForSubscriptionId(ob.getSubscriptionId());
        smsManager.sendTextMessage(ob.getRecipientNumber(), null, ob.getMessage(), null, null);*/
        // Toast.makeText(this,"finish sending to "+ob.getRecipientName(),Toast.LENGTH_SHORT).show();
           /* for (int i = 0; i < 1000; i++) {
                timepass = i;
            }*/

        //  }

    }

    private PendingIntent getPendingIntent(long smsId, Class receiverClass) {
        Intent intent = new Intent(this, receiverClass);
        intent.setAction(Long.toString(smsId));
        intent.putExtra(DbHelper.COLUMN_TIMESTAMP_CREATED, smsId);
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }

    private SmsManager getSmsManager(int subscriptionId) {
        SmsManager smsManager = SmsManager.getDefault();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            return smsManager;
        }
        SubscriptionManager subscriptionManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        if (null == subscriptionManager) {
            return smsManager;
        }
        if (null == subscriptionManager.getActiveSubscriptionInfo(subscriptionId)) {
            return smsManager;
        }
        return SmsManager.getSmsManagerForSubscriptionId(subscriptionId);
    }


}
