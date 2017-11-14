package com.rohan.sms_send_schedule.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rohan.sms_send_schedule.R;

public class add_student extends BaseManuActivity {

    databaseHelper_students db;
    EditText c_id;
    EditText name;
    EditText result;
    EditText mob;
    EditText mail;
    EditText attendance;
    EditText fees;

    String r_id;
    String r_name;
    String r_mob;
    String r_mail;
    String r_result;
    String r_attendance;
    String r_fees;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        c_id = (EditText) findViewById(R.id.s_id);
        mob = (EditText) findViewById(R.id.s_mob);
        mail = (EditText) findViewById(R.id.s_mail);
        name = (EditText) findViewById(R.id.s_name);
        result = (EditText) findViewById(R.id.s_result);
        attendance = (EditText) findViewById(R.id.s_attendance);
        fees = (EditText) findViewById(R.id.s_fees);

        //


        db = new databaseHelper_students(this);
      /*  db.add_student("15ce146", "rohan", "8153997302", "rohan.vachhani5@gmail.com", "9", "80%", "1500");
        db.add_student("15ce142", "piyush", "9913617270", "piyushthummar305@gmail.com", "10", "90%", "1200");
        db.add_student("15ce147", "yash", "9924049449", "yashvaghani44@gmail.com", "9.9", "85%", "500");
        db.add_student("15ce009", "kuldip", "8758308387", "bogharakuldip@gmail.com", "8", "75%", "0");*/
    }


    public void AddDetails(View view) {
        r_id = c_id.getText().toString();
        r_name = name.getText().toString();
        r_mob = mob.getText().toString();
        r_mail = mail.getText().toString();
        r_result = result.getText().toString();
        r_attendance = attendance.getText().toString();
        r_fees = fees.getText().toString();

        /*Send_to_db(r_id, r_name, r_mob, r_mail, r_result, r_attendance, r_fees);*/
        Context context = this.getApplicationContext();
        boolean status = db.add_student(r_id, r_name, r_mob, r_mail, r_result, r_attendance, r_fees);
        Toast.makeText(this, "STATUS: " + status, Toast.LENGTH_SHORT).show();
    }

    public void ViewDetails(View view) {
        startActivity(new Intent(getApplicationContext(), DisplayDetails.class));
    }
}
