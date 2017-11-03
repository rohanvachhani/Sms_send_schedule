package com.rohan.sms_send_schedule.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rohan.sms_send_schedule.R;
import com.rohan.sms_send_schedule.SmsModel;
import com.rohan.sms_send_schedule.view.BuilderSimForResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class result_notify extends Activity {

    private Button button_send;
    private Button button_attendance;
    private Button button_fees;
    private TextView textView_info;
    private String contact_no[];
    private String result[];
    private String name[];
    private String attendance[];
    private String fees_pending[];
    private int FIRST_TIME = 0;
    private int s_id;


    private SmsModel sms;
    private ArrayList<SmsModel> sms_array;
    private static long time_var = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_notify);

        /*button_fetch.setOnClickListener(this);*/
        //for testing ..this will be done by fetching all students data from DATABASE..!
        contact_no = new String[]{"9099850038", "9913617270", "8758308387"};
        name = new String[]{"rohan", "second_student", "Third_student"};
        result = new String[]{"9.2", "8.2", "10"};
        attendance = new String[]{"60%", "80%", "90%"};
        fees_pending = new String[]{"1000", "2000", "0"};

        sms = new SmsModel();

        new com.rohan.sms_send_schedule.view.BuilderSimForResult().setActivity(this).setView(findViewById(R.id.form_sim_card_n)).setSms(sms).build();
        //   s_id = new SmsModel().getSubscriptionId();
    }

    public void sendResult(View view) {                     //FOR SENDING THE RESULTS
       /* sms_array.clear();*/

        for (int i = 0; i < contact_no.length; i++) {
            sms = new SmsModel();

            if (FIRST_TIME == 0) {
                //   new com.rohan.sms_send_schedule.view.BuilderSimForResult().setActivity(this).setView(findViewById(R.id.form_sim_card_n)).setSms(sms).build();
                s_id = BuilderSimForResult.s_id;
                FIRST_TIME = 1;
            }
            String formMessage = "Your Result: " + result[i];
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

            sendSms(sms);
            Toast.makeText(this, "testing toast", Toast.LENGTH_SHORT).show();

        }
    }

    public void sendAttendance(View view) {
        for (int i = 0; i < contact_no.length; i++) {
            sms = new SmsModel();

            if (FIRST_TIME == 0) {
                //   new com.rohan.sms_send_schedule.view.BuilderSimForResult().setActivity(this).setView(findViewById(R.id.form_sim_card_n)).setSms(sms).build();
                s_id = BuilderSimForResult.s_id;
                FIRST_TIME = 1;
            }
            String formMessage = "Your Attandence: " + attendance[i];
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

            sendSms(sms);
            Toast.makeText(this, "testing toast", Toast.LENGTH_SHORT).show();

        }
    }

    public void sendFees(View view) {
        for (int i = 0; i < contact_no.length; i++) {
            sms = new SmsModel();

            if (FIRST_TIME == 0) {
                //   new com.rohan.sms_send_schedule.view.BuilderSimForResult().setActivity(this).setView(findViewById(R.id.form_sim_card_n)).setSms(sms).build();
                s_id = BuilderSimForResult.s_id;
                FIRST_TIME = 1;
            }
            String formMessage = "Your Pending Fees: " + fees_pending[i];
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

            sendSms(sms);
            Toast.makeText(this, "testing toast", Toast.LENGTH_SHORT).show();

        }
    }


    private void sendSms(SmsModel sms) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<>();
        SmsManager smsManager = getSmsManager(sms.getSubscriptionId());
        ArrayList<String> smsMessage = smsManager.divideMessage(sms.getMessage());
        boolean deliveryReports = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext())
                .getBoolean(SmsSchedulerPreferenceActivity.PREFERENCE_DELIVERY_REPORTS, false);

        smsManager.sendMultipartTextMessage(
                sms.getRecipientNumber(),
                null,
                smsMessage,
                sentPendingIntents,
                deliveryReports ? deliveredPendingIntents : null
        );
    }

   /* public void sendResult(View view, SmsModel ob) {

        long time = System.currentTimeMillis();

        ob.setSubscriptionId(s_id);
        ob.setTimestampScheduled(time + ((time_var) * 1800));       //add 3 min. // for proper scheduling
        ob.setStatus(SmsModel.STATUS_PENDING);
        DbHelper.getDbHelper(this).save(ob);
        new Scheduler(getApplicationContext()).schedule(ob);
        setResult(RESULT_SCHEDULED, new Intent());
        finish();
        time_var += 1;

    }*/


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
