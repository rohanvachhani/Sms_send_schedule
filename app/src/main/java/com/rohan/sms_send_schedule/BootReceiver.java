package com.rohan.sms_send_schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "Rescheduling all the sms");
        String action = intent.getAction();
        if (TextUtils.isEmpty(action) || !action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            return;
        }
        ArrayList<com.rohan.sms_send_schedule.SmsModel> pendingSms = com.rohan.sms_send_schedule.DbHelper.getDbHelper(context).get(com.rohan.sms_send_schedule.SmsModel.STATUS_PENDING);
        Iterator<com.rohan.sms_send_schedule.SmsModel> i = pendingSms.iterator();
        com.rohan.sms_send_schedule.Scheduler scheduler = new com.rohan.sms_send_schedule.Scheduler(context);
        while (i.hasNext()) {
            scheduler.schedule(i.next());
        }
    }
}
