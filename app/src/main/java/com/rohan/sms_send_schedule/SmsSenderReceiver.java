package com.rohan.sms_send_schedule;

public class SmsSenderReceiver extends WakefulBroadcastReceiver {

    @Override
    protected Class getServiceClass() {
        return com.rohan.sms_send_schedule.SmsSenderService.class;
    }
}
