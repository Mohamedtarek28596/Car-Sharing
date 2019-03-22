package com.example6767gh.mytestauthentication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    FirebaseDatabase firebaseDatabase;
    View v1;
    View v2;
    View v3;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    TextView tit1;
    TextView tit2;
    TextView tit3;
    TextView c1;
    TextView c2;
    TextView c3;
    TextView d1;
    TextView d2;
    TextView d3;
    ArrayList<Cars> allCars = new ArrayList<>();

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected double myLat = 0;
    protected double myLong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.showLoading(Home.this);
        setContentView(R.layout.activity_home);
        v1 = findViewById(R.id.f1);
        v2 = findViewById(R.id.f2);
        v3 = findViewById(R.id.f3);
        img1 = v1.findViewById(R.id.ivProfilePic);
        img2 = v2.findViewById(R.id.ivProfilePic);
        img3 = v3.findViewById(R.id.ivProfilePic);
        tit1 = v1.findViewById(R.id.name);
        tit2 = v2.findViewById(R.id.name);
        tit3 = v3.findViewById(R.id.name);
        c1 = v1.findViewById(R.id.color);
        c2 = v2.findViewById(R.id.color);
        c3 = v3.findViewById(R.id.color);
        d1 = v1.findViewById(R.id.dist);
        d2 = v2.findViewById(R.id.dist);
        d3 = v3.findViewById(R.id.dist);

        Toast.makeText(this, FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                    0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        } catch (Exception io) {
            io.printStackTrace();
        }

        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Cars");



        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot carData : dataSnapshot.getChildren()){

                    Cars carsInformation = carData.getValue(Cars.class);
                    //Toast.makeText(Home.this, carsInformation.getType(), Toast.LENGTH_SHORT).show();
                    allCars.add(carsInformation);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Home.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });







        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void startMap(Cars cars) {
        Intent intent = new Intent(Home.this, MapsActivity.class);
        intent.putExtra("car", cars);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_profile) {
        /*    Toast toast = Toast.makeText(Home.this, "HAHAHAHA", Toast.LENGTH_SHORT);
            toast.show();    */

            startActivity(new Intent(this, ProfileInfo.class));

        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, History.class));

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, MapsActivity.class));

        } else if (id == R.id.nav_contact) {
            startActivity(new Intent(this, ContactUs.class));

        }

        else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, MainActivity1.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    @Override
    public void onLocationChanged(Location location) {
        myLat = location.getLatitude();
        myLong = location.getLongitude();
        //Log.i("elec", myLat + " , " + myLong);
        updateUI();
    }

    private void updateUI() {
        int firstmin = Integer.MAX_VALUE;
        int secmin = Integer.MAX_VALUE;
        int thirdmin = Integer.MAX_VALUE;
        int firstminIndex = 0;
        int secminIndex = 0;
        int thirdminIndex = 0;
        for (int i = 0; i < allCars.size(); i++)
        {
                /* Check if current element is less than
                firstmin, then update first, second and
                third */
            //Toast.makeText(Home.this, "Calc :  " + myLat + " , " + myLong , Toast.LENGTH_SHORT).show();
            double dist = distance(Double.valueOf(allCars.get(i).getLatitude()), myLat,Double.valueOf(allCars.get(i).getLongitude()), myLong,0.0,0.0);
            if (((int)dist) < firstmin)
            {
                thirdmin = secmin;
                secmin = firstmin;
                firstmin = (int)dist;
                firstminIndex = i;
            }

                /* Check if current element is less than
                secmin then update second and third */
            else if (((int)dist) < secmin)
            {
                thirdmin = secmin;
                secmin = ((int)dist);
                secminIndex = i;
            }

                /* Check if current element is less than
                then update third */
            else if (((int)dist) < thirdmin) {
                thirdmin = ((int) dist);
                thirdminIndex = i;
            }
        }

        //Toast.makeText(Home.this, "After", Toast.LENGTH_SHORT).show();


        if (allCars.size() != 0) {
            String trial1 = allCars.get(firstminIndex).getImage();
            String trial2 = allCars.get(secminIndex).getImage();
            String trial3 = allCars.get(thirdminIndex).getImage();

            Picasso.with(getApplication()).load(trial1)
                    .into(img1);
            tit1.setText(allCars.get(firstminIndex).getType());
            c1.setText(allCars.get(firstminIndex).getColor());
            d1.setText(allCars.get(firstminIndex).getNumber());


            Picasso.with(getApplication()).load(trial2)
                    .into(img2);
            tit2.setText(allCars.get(secminIndex).getType());
            c2.setText(allCars.get(secminIndex).getColor());
            d2.setText(allCars.get(secminIndex).getNumber());

            Picasso.with(getApplication()).load(trial3)
                    .into(img3);
            tit3.setText(allCars.get(thirdminIndex).getType());
            c3.setText(allCars.get(thirdminIndex).getColor());
            d3.setText(allCars.get(thirdminIndex).getNumber());

            final int finalFirstminIndex = firstminIndex;
            v1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startMap(allCars.get(finalFirstminIndex));
                }
            });

            final int finalSecminIndex = secminIndex;
            v2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startMap(allCars.get(finalSecminIndex));
                }
            });

            final int finalThirdminIndex = thirdminIndex;
            v3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startMap(allCars.get(finalThirdminIndex));
                }
            });
            Utils.hideLoading();
        }
        }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
}
/*
ArrayList<String> permissions=new ArrayList<>();
PermissionUtils permissionUtils;

permissionUtils=new PermissionUtils(MyLocationUsingLocationAPI.this);

permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

permissionUtils.check_permission(permissions,"Need GPS permission for getting your location",1);
 */