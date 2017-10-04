package com.rohan.sms_send_schedule;

public class SmsDeliveredReceiver extends WakefulBroadcastReceiver {

    @Override
    protected Class getServiceClass() {
        return com.rohan.sms_send_schedule.SmsDeliveredService.class;
    }
}
