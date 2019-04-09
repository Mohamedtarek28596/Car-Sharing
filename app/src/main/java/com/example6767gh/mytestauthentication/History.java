package com.example6767gh.mytestauthentication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class History extends AppCompatActivity {


    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    FirebaseDatabase firebaseDatabase;
    ArrayList<Trips> allTrips = new ArrayList<>();
     ArrayList<Cars> car1  = new ArrayList<>();;
public String carid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Utils.showLoading(History.this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter= new MainAdapter(allTrips,car1, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        first(new onAction() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                mohamed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(History.this, "Main A 11 Destroy", Toast.LENGTH_SHORT).show();
        //Utils.hideLoading();
    }

    public void first (final onAction action) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Trips").child(FirebaseAuth.getInstance().getUid());
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot trips : dataSnapshot.getChildren()){

                    Trips tripsHistory = trips.getValue(Trips.class);

                    Log.i("mohamed", tripsHistory.getCarID());

                    allTrips.add(tripsHistory);

                    Log.i("mohamed","1");
                }


//mohamed();


//
//               mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
//
//                   @Override
//                   public void onDataChange(DataSnapshot dataSnapshot) {
//                       Log.i("mohamed","16");
//                       for (int j = 0; j<allTrips.size(); j++) {
//                           Log.i("mohamed", "111111");
//                           carid = allTrips.get(j).getCarID();
//                           Log.i("mohamed", "111");
//                           Log.i("mohamed","2");
//                           Cars car1Value = dataSnapshot.child(carid).getValue(Cars.class);
//
//                           car1.add(car1Value);
//
//                       }
//
//                   }
//
//                   @Override
//                   public void onCancelled(DatabaseError databaseError) {
//                       Log.i("mohamed","3");
//                   }
//               });

                Log.i("mohamed","4");


                Log.i("mohamed","5");
                action.onFinish();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("mohamed","6");
                Toast.makeText(History.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected  void mohamed() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference("Cars");
        mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int j = 0; j < allTrips.size(); j++) {
                    Log.i("mohamed", "111111");
                    carid = allTrips.get(j).getCarID();
                    Log.i("mohamed", "111");
                    Log.i("mohamed", "2");
                    Log.i("mohamed", "555");
                    Cars cars = dataSnapshot.child(carid).getValue(Cars.class);
                    car1.add(cars);
                }
                Utils.hideLoading();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}