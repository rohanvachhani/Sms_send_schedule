package com.rohan.sms_send_schedule.Activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.rohan.sms_send_schedule.R;

public class DisplayDetails extends BaseManuActivity {

    TextView textView;
    databaseHelper_students myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);
        textView = findViewById(R.id.text_view);
        display_result();
    }

    public void display_result() {
        myDb = new databaseHelper_students(this);
        Cursor res = myDb.getStudent();
        if (res.getCount() == 0) {
            // show message
            // showMessage("Error", "Nothing found");
            Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
            return;
        }


        while (res.moveToNext()) {

           /* textView.append("Id :" + res.getString(res.getColumnIndex("id")) + "\n");*/
            textView.append("Student's Id No. :" + res.getString(res.getColumnIndex("c_id")) + "\n");
            textView.append("Name :" + res.getString(res.getColumnIndex("name")) + "\n");
            textView.append("Mail Id :" + res.getString(res.getColumnIndex("mail_ID")) + "\n");
            textView.append("Mobile No. :" + res.getString(res.getColumnIndex("mobile_no")) + "\n");
            textView.append("Result :" + res.getString(res.getColumnIndex("result")) + "\n");
            textView.append("Attendance :" + res.getString(res.getColumnIndex("attendance")) + "\n");
            textView.append("Pending Fees :" + res.getString(res.getColumnIndex("fees_pending")) + "\n\n");
        }

    }

}
