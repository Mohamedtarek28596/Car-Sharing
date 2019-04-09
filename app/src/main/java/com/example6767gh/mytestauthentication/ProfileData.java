package com.example6767gh.mytestauthentication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class ProfileData extends AppCompatActivity  implements View.OnClickListener {


    EditText firstName;
    EditText lastName;
    EditText age;
    EditText phoneNumber;
    EditText trustedEmail;
    String imageUrl;
    Button buttonSave;
    Button btn;

    DatabaseReference databaseUser ;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth mAuth;
    FirebaseUser test = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);


        //  FirebaseUser test = FirebaseAuth.getInstance().getCurrentUser();

        databaseUser = FirebaseDatabase.getInstance().getReference("Users");

        firebaseAuth = FirebaseAuth.getInstance();
        firstName = (EditText)findViewById(R.id.uFirstName);
        lastName =  (EditText)findViewById(R.id.uLastName);
        age = (EditText)findViewById(R.id.uAge);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        trustedEmail =(EditText)findViewById(R.id.uTrustedEmail);
        findViewById(R.id.ButtonNext).setOnClickListener(this);
        btn = (Button)findViewById(R.id.ButtonNext);
        btn.setEnabled(false);

        mAuth = FirebaseAuth.getInstance();



        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        });
    }


    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {

            startActivity(new Intent(this, MainActivity1.class));
            finish();
        }


    }


    private void saveUserInformation (){

        FirebaseUser test = FirebaseAuth.getInstance().getCurrentUser();
        final String name = firstName.getText().toString().trim();
        String lastname = lastName.getText().toString().trim();
        final String Age = age.getText().toString().trim();
        final String number = phoneNumber.getText().toString().trim();
        final String extraEmail = trustedEmail.getText().toString().trim();


        if( (!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(lastname)) && (!TextUtils.isEmpty(Age)) && (!TextUtils.isEmpty(number)) && (!TextUtils.isEmpty(extraEmail))  ){

            final String   id = test.getUid();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(id);
//  mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            //    @Override
            //  public void onDataChange(DataSnapshot dataSnapshot) {
//                    users hh = new users(0,name,lastname,Age,number,extraEmail,imageUrl);
//
            //                  mDatabase.child(id).setValue(hh);

            // mDatabase.getRef().child("uFirstName").setValue(name);

            //mDatabase.child("uLastName").setValue(lastName);
            //mDatabase.child("uAge").setValue(Age);
            //mDatabase.child("uNumber").setValue(number);
            //mDatabase.child("uTrustedEmail").setValue(extraEmail);

            //             }
            //           @Override
            //         public void onCancelled(DatabaseError databaseError) {
            //       }
            // });

            users hh = new users(id,name,lastname,Age,number,extraEmail);

            databaseUser.child(test.getUid()).setValue(hh);

            Toast.makeText(this,"information added ",Toast.LENGTH_LONG).show();

            btn.setEnabled(true);

        }
        else{
            Toast.makeText(this,"please Fill all the information needed",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onClick(View view) {

//        ArrayList<String> permissions=new ArrayList<>();
//        PermissionUtils permissionUtils;
//
//        permissionUtils=new PermissionUtils(ProfileData.this);
//
//        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        permissionUtils.check_permission(permissions,"Need GPS permission for getting your location",1);

        if (ContextCompat.checkSelfPermission(ProfileData.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(ProfileData.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(ProfileData.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(ProfileData.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileData.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(ProfileData.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(ProfileData.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(ProfileData.this, "GOOD", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(this, Home.class));
                    break;
                } else {
                    Toast.makeText(ProfileData.this, "Not GOOD", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}