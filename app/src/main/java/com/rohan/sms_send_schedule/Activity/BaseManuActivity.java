package com.rohan.sms_send_schedule.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rohan.sms_send_schedule.R;

public class BaseManuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_manu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {                   //parent of all activities/...!because of thi menu
       /* if (item.getItemId() == R.id.menu_settings) {
            startActivityForResult(new Intent(this, SmsSchedulerPreferenceActivity.class), 1);
        }*/

        if (item.getItemId() == R.id.results) {
            //finish();      //if finish then back button will cause CRASH the app...!!!
            startActivity(new Intent(getApplicationContext(), result_notify.class));
        } else if (item.getItemId() == R.id.results_mail) {
            startActivity(new Intent(getApplicationContext(), Result_by_mail.class));
        } else if (item.getItemId() == R.id.about_us) {
            startActivity(new Intent(getApplicationContext(), about_us.class));
        } else if (item.getItemId() == R.id.add_student) {
            startActivity(new Intent(getApplicationContext(), add_student.class));
        }
        //another 2 items greetings and attendance
        return true;
    }
}
