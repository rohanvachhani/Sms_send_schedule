package com.rohan.sms_send_schedule;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rohan.sms_send_schedule.Activity.SmsListActivity;
import com.rohan.sms_send_schedule.notification.NotificationManagerWrapper;

public class ReminderService extends com.rohan.sms_send_schedule.SmsIntentService {

    public ReminderService() {
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        super.onHandleIntent(intent);
        if (timestampCreated == 0) {
            return;
        }
        com.rohan.sms_send_schedule.SmsModel sms = DbHelper.getDbHelper(getApplicationContext()).get(timestampCreated);
        if (null == sms) {
            Log.i(getClass().getName(), "No sms created at " + timestampCreated + " found");
            return;
        }
        Log.i(getClass().getName(), "Reminding about sms " + timestampCreated);
        remind(getApplicationContext(), sms);
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }

    static private void remind(Context context, com.rohan.sms_send_schedule.SmsModel sms) {
        Intent intentUnschedule = new Intent(context, com.rohan.sms_send_schedule.UnscheduleService.class);
        intentUnschedule.putExtra(DbHelper.COLUMN_TIMESTAMP_CREATED, sms.getTimestampCreated());
        new NotificationManagerWrapper(context).show(
            sms.getId() + 1,
            NotificationManagerWrapper.getBuilder(context)
                .setTitle(context.getString(R.string.notification_title_will_send_in_an_hour))
                .setMessage(context.getString(R.string.notification_message_will_send_in_an_hour, sms.getRecipientName()))
                .setIntent(new Intent(context, com.rohan.sms_send_schedule.Activity.SmsListActivity.class))
                .addAction(
                    android.R.drawable.ic_menu_close_clear_cancel,
                    R.string.form_button_cancel,
                    PendingIntent.getService(context, sms.getId(), intentUnschedule, PendingIntent.FLAG_UPDATE_CURRENT & Intent.FILL_IN_DATA)
                )
                .build()
        );
    }
}
