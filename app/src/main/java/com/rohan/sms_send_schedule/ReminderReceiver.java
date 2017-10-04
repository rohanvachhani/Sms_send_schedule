package com.rohan.sms_send_schedule;

public class ReminderReceiver extends WakefulBroadcastReceiver {

    @Override
    protected Class getServiceClass() {
        return com.rohan.sms_send_schedule.ReminderService.class;
    }
}
