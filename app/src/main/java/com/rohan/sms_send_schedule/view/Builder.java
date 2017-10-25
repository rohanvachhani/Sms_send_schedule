package com.rohan.sms_send_schedule.view;

import android.view.View;

import com.rohan.sms_send_schedule.Activity.AddSmsActivity;
import com.rohan.sms_send_schedule.Activity.result_notify;
import com.rohan.sms_send_schedule.SmsModel;

public abstract class Builder {

    protected View view;
    protected SmsModel sms;
    protected AddSmsActivity activity;
    protected result_notify activity2;

    abstract protected View getView();

    abstract public View build();

    public Builder setView(View view) {
        this.view = view;
        return this;
    }

    public Builder setSms(SmsModel sms) {
        this.sms = sms;
        return this;
    }

    public Builder setActivity(AddSmsActivity activity) {
        this.activity = activity;
        return this;
    }

    public Builder setActivity(result_notify activity2) {
        this.activity2 = activity2;
        return this;
    }
}
