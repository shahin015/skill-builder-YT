package com.shahindemunav.drawerwithbottomnavigation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahindemunav.drawerwithbottomnavigation.Regster.Regstation;
import com.shahindemunav.drawerwithbottomnavigation.payment.Paymnet;


import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final float END_SCALE = 0.85f;
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavView;
    private CoordinatorLayout contentView;
    private FirebaseAuth mauth;
    private ProgressDialog progressDialog;
    private DatabaseReference reference;
    private FirebaseUser mFirebaseUser ;
    private ImageView imageView;
    private String key;
    private String userEmao="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        mauth=FirebaseAuth.getInstance();
         mFirebaseUser=mauth.getCurrentUser();

        progressDialog=new ProgressDialog(this);
        reference=FirebaseDatabase.getInstance().getReference("users");

        initToolbar();
        initNavigation();
        //showBottomNavigation(false);


    }

    private void headerVideinfalite(String keys) {
        View view=navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView imageView=view.findViewById(R.id.imageView);
        TextView key=view.findViewById(R.id.key);
        Button copy=view.findViewById(R.id.copyButton);
        TextView currentBlcnce=view.findViewById(R.id.Curent_blacne);
        TextView navemail=view.findViewById(R.id.nav_email);
        TextView navename=view.findViewById(R.id.nav_name);


        if (key!=null){
            reference.child(keys).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String names=snapshot.child("name").getValue().toString();
                    String emails=snapshot.child("email").getValue().toString();
                    String yourrefkey=snapshot.child("key").getValue().toString();
                    String coin=snapshot.child("Coin").getValue().toString();
                    currentBlcnce.setText(coin);
                    key.setText(yourrefkey);
                   navemail.setText(emails);
                   navename.setText(names);





                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }


    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


     private void initNavigation() {

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavView = findViewById(R.id.bottom_nav_view);
        contentView = findViewById(R.id.content_view);
       ////////////////////////////////////////
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send,
                R.id.bottom_home, R.id.bottom_dashboard, R.id.bottom_notifications)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);


        animateNavigationDrawer();
    }


    private void animateNavigationDrawer() {

        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            SharedPreferences sharedPreferences = getSharedPreferences("check",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("check", "not ok");
            myEdit.commit();
            myEdit.apply();
            super.onBackPressed();
        }

    }

    @Override
    protected void onStart() {



        if (mauth.getCurrentUser()==null){
            Intent i=new Intent(MainActivity.this, Regstation.class);
            startActivity(i);

        }else {

        ////   CallDataBase();


        }
        super.onStart();
    }

    private void CallDataBase() {
        progressDialog.setMessage("Cheacking Account Helth");
        progressDialog.show();
        SharedPreferences sh = getSharedPreferences("name", MODE_PRIVATE);
        key=sh.getString("key","");
        headerVideinfalite(key);
        Intent intent=getIntent();
        userEmao=intent.getStringExtra("email");



      /// Toast.makeText(this, "your key is: "+key, Toast.LENGTH_SHORT).show();
        reference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String accountStatus=snapshot.child("accountStatus").getValue().toString();
                String userEmail=snapshot.child("email").getValue().toString();
                //Toast.makeText(MainActivity.this, userEmail+userEmao, Toast.LENGTH_SHORT).show();



                if (userEmao==null){
                    Toast.makeText(MainActivity.this, "You Have To Login Frist", Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(MainActivity.this,Regstation.class);
                    startActivity(intent1);
                    finish();
                }else {

                    if (userEmao.equals(userEmail)){
                       if (accountStatus.contains("inactive")){
                            Intent intent=new Intent(MainActivity.this, Paymnet.class);
                            intent.putExtra("accountStatus",accountStatus);
                            intent.putExtra("key",key);
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();
                        }else if (accountStatus.contains("Block")){
                            Intent intent=new Intent(MainActivity.this, Paymnet.class);
                            intent.putExtra("accountStatus",accountStatus);
                            intent.putExtra("key",key);
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();

                        }else if (accountStatus.contains("Pending")){
                            Intent intent=new Intent(MainActivity.this, Paymnet.class);
                            intent.putExtra("accountStatus",accountStatus);
                            intent.putExtra("key",key);
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();

                        }else if (accountStatus.contains("Valid")){
                            Toast.makeText(MainActivity.this, "You Account is Ok ", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("check",MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("check", "ok");
                            myEdit.commit();
                            myEdit.apply();
                            progressDialog.dismiss();

                        }else {
                            Intent intent=new Intent(MainActivity.this, Paymnet.class);
                            intent.putExtra("accountStatus",accountStatus);
                            intent.putExtra("key",key);
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();
                        }

                    } else {

                        Toast.makeText(MainActivity.this, "We Don't Allow Dublicate Account", Toast.LENGTH_SHORT).show();
                        Intent d=new Intent(MainActivity.this,Regstation.class);
                        startActivity(d);
                        progressDialog.dismiss();
                        finish();


                    }

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        // Handle action bar actions click
        switch (item.getItemId()) {

            case R.id.logout:
                mauth.signOut();
                startActivity(new Intent(MainActivity.this,Regstation.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        int count=1*60*1000;
//        int totol=count*5;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mauth.signOut();
//            }
//        },totol);



    }

    @Override
    protected void onStop() {
        super.onStop();
      //// mauth.signOut();


    }
}
