package com.rohan.sms_send_schedule;

import android.content.Intent;

public class SmsDeliveredService extends com.rohan.sms_send_schedule.SmsIntentService {

    public SmsDeliveredService() {
        super("SmsDeliveredService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        super.onHandleIntent(intent);
        if (timestampCreated == 0) {
            return;
        }
        com.rohan.sms_send_schedule.SmsModel sms = DbHelper.getDbHelper(this).get(timestampCreated);
        sms.setStatus(com.rohan.sms_send_schedule.SmsModel.STATUS_DELIVERED);
        DbHelper.getDbHelper(this).save(sms);
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }
}
