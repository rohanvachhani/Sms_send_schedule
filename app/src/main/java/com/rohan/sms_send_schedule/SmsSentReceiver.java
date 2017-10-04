package com.rohan.sms_send_schedule;

public class SmsSentReceiver extends WakefulBroadcastReceiver {

    @Override
    protected Class getServiceClass() {
        return com.rohan.sms_send_schedule.SmsSentService.class;
    }
}
