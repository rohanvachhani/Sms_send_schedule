package com.rohan.sms_send_schedule.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rohan.sms_send_schedule.R;

public class Result_by_mail extends Activity {

    //Declaring EditText
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;

    //Send button
    private Button buttonSend;
    private String mail_id[];
    private String sub[];
    private String contact_no[];
    private String result[];
    private String name[];
    private String attendance[];
    private String fees_pending[];
    private final String SUB_OF_MAIL= "your Academic Information_by EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_by_mail);

        //Initializing the views
       /* editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);*/

        buttonSend = (Button) findViewById(R.id.buttonSend);
        //Adding click listener

        mail_id = new String[]{"rohan.vachhani5@gmail.com", "piyushthummar305@gmail.com", "bogharakuldip@gmail.com"};
        sub = new String[]{SUB_OF_MAIL, SUB_OF_MAIL, SUB_OF_MAIL};
        name = new String[]{"rohan", "second_student", "Third_student"};
        result = new String[]{"9.2", "8.2", "10"};
        attendance = new String[]{"60%", "80%", "90%"};
        fees_pending = new String[]{"1000", "2000", "0"};

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }


    private void sendEmail() {
        //Getting content for email

        for (int i = 0; i < mail_id.length; i++) {
            SendMail sm;
            String email = "Name: "+name[i] + "\nResult: "+ result[i] + "\nAttendance: "+ attendance[i] + "\nFees Pending: "+fees_pending[i];
            sm = new SendMail(this, mail_id[i], sub[i],email);
            sm.execute();
        }

      /*  String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();*/

        //Creating SendMail object
        /*SendMail sm = new SendMail(this, email, subject, message);*/

        //Executing sendmail to send email
        // sm.execute();
    }

  /*  @Override
    public void onClick(View v) {
        sendEmail();
    }*/
}
