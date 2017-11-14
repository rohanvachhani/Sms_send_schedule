package com.rohan.sms_send_schedule.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.database.Cursor;
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

public class result_notify extends BaseManuActivity {

    private String contact_no[];
    private String result[];
    private String name[];
    private String attendance[];
    private String fees_pending[];
    private int FIRST_TIME = 0;
    private int s_id;

    private SmsModel sms;
    private static long time_var = 1;
    private databaseHelper_students myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_notify);

        myDb = new databaseHelper_students(this);
        Cursor res = myDb.getStudent();

        int len = res.getCount();
        name = new String[len];
        contact_no = new String[len];
        result = new String[len];
        attendance = new String[len];
        fees_pending = new String[len];

        if (res.getCount() == 0) {
            // show message
            // showMessage("Error", "Nothing found");
            Toast.makeText(getApplicationContext(), "no students..!!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int i = 0;
            while (res.moveToNext()) {

           /* textView.append("Id :" + res.getString(res.getColumnIndex("id")) + "\n");*/
                //textView.append("Student's Id No. :" + res.getString(res.getColumnIndex("c_id")) + "\n");
                name[i] = res.getString(res.getColumnIndex("name"));
                //  textView.append("Mail Id :" + res.getString(res.getColumnIndex("mail_ID")) + "\n");
                contact_no[i] = res.getString(res.getColumnIndex("mobile_no"));
                result[i] = res.getString(res.getColumnIndex("result"));
                attendance[i] = res.getString(res.getColumnIndex("attendance"));
                fees_pending[i] = res.getString(res.getColumnIndex("fees_pending"));
                i++;
            }

        }


        /*button_fetch.setOnClickListener(this);*/
        //for testing ..this will be done by fetching all students data from DATABASE..!
        /*db = new databaseHelper_students(this);
        db.add_student("15ce146", "rohan", "8153997302", "rohan.vachhani5@gmail.com", "9", "80%", "1500");
        db.add_student("15ce142", "piyush", "9913617270", "piyushthummar305@gmail.com", "10", "90%", "1200");
        db.add_student("15ce147", "yash", "9924049449", "yashvaghani44@gmail.com", "9.9", "85%", "500");
        db.add_student("15ce009", "kuldip", "8758308387", "bogharakuldip@gmail.com", "8", "75%", "0");
        Cursor cursor = db.getStudent();
        if (cursor.moveToFirst()) {
            int count = cursor.getCount();
            contact_no = new String[count];
            name = new String[count];
            result = new String[count];
            attendance = new String[count];
            fees_pending = new String[count];
            int i = 0;
            do {
                contact_no[i] = cursor.getString(cursor.getColumnIndex(databaseHelper_students.col_mobile_no));
                name[i] = cursor.getString(cursor.getColumnIndex(databaseHelper_students.col_name));
                result[i] = cursor.getString(cursor.getColumnIndex(databaseHelper_students.col_result));
                attendance[i] = cursor.getString(cursor.getColumnIndex(databaseHelper_students.col_attendance));
                fees_pending[i] = cursor.getString(cursor.getColumnIndex(databaseHelper_students.col_fees_pending));
                i++;
            } while (cursor.moveToNext());
        }*/

        //important comment
        /*contact_no = new String[]{"9099850038", "9913617270", "8758308387"};
        name = new String[]{"rohan", "second_student", "Third_student"};
        result = new String[]{"9.2", "8.2", "10"};
        attendance = new String[]{"60%", "80%", "90%"};
        fees_pending = new String[]{"1000", "2000", "0"};*/

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
