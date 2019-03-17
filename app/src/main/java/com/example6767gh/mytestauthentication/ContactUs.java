package com.example6767gh.mytestauthentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.net.Uri;
import android.content.Intent;
import android.content.pm.PackageManager;

public class ContactUs extends AppCompatActivity {
    private Button bn;
    private  Button bn2;
    private  Button bn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        bn = (Button) findViewById(R.id.callbutton);
        bn2 = (Button) findViewById(R.id.fbbutton);
        bn3 = (Button) findViewById(R.id.emailbtn);
        bn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:01283557633"));
                startActivity(callIntent);
            }
        });
        bn2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100001114376487"));
                    startActivity(intent);


                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/m7mdtarek96")));
                    return;
                }
            }
        });
        bn3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "m7mdtarek44@gmail.com"});
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });





    }
}
