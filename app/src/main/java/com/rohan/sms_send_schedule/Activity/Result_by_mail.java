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
    private String msg[];

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

        mail_id = new String[]{"rohan.vachhani5@gmail.com", "rohvac5@gmail.com", "tstrhn@gmail.com"};
        sub = new String[]{"result1", "result2", "result3"};
        msg = new String[]{"9.2", "8.2", "7.2"};

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
            sm = new SendMail(this, mail_id[i], sub[i], msg[i]);
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
