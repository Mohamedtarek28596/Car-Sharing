package com.example6767gh.mytestauthentication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class History extends AppCompatActivity {

    ArrayList<String>  mContact ;
    ArrayList<String>  mContact2 ;
    ArrayList<String>  mContact3 ;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    FirebaseDatabase firebaseDatabase;
    ArrayList<Trips> allTrips = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mContact = new ArrayList<>();
        mContact2 = new ArrayList<>();
        mContact3 = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter= new MainAdapter(mContact,mContact2,mContact3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Trips");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

       @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            for(DataSnapshot trips : dataSnapshot.getChildren()){

                Trips tripsHistory = trips.getValue(Trips.class);
                allTrips.add(tripsHistory);

            }

           for (int j = 0; j<allTrips.size(); j++)
           {
               String startTrip = allTrips.get(j).getStart();
               String endTrip = allTrips.get(j).getEnd();
               String timeTrip = allTrips.get(j).getTime();
               mContact.add(startTrip);
               mContact2.add(endTrip);
               mContact3.add(timeTrip);
           }
           mAdapter.notifyDataSetChanged();
        }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(History.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}