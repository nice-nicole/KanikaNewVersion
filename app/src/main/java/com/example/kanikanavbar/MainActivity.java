package com.example.kanikanavbar;

import android.content.Intent;
import android.os.Bundle;

import com.example.kanikanavbar.Fragment.BuyFragment;
import com.example.kanikanavbar.Fragment.ChatFragment;
import com.example.kanikanavbar.Fragment.MechaniciansFragment;
import com.example.kanikanavbar.Fragment.SellFragment;
import com.example.kanikanavbar.Fragment.HomeFragment;
import com.example.kanikanavbar.Model.Person;
import com.example.kanikanavbar.View.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    FirebaseUser user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_buy, R.id.nav_sell,
                R.id.nav_mechanicians, R.id.nav_chats, R.id.nav_exit)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id= menuItem.getItemId();

        if(id== R.id.nav_home){
            getSupportActionBar().setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        }else if(id== R.id.nav_buy){

            getSupportActionBar().setTitle("buy spareparts");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new BuyFragment()).commit();

        }else if(id== R.id.nav_sell){
        getSupportActionBar().setTitle("sell spareparts");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SellFragment()).commit();

        }else if(id== R.id.nav_mechanicians){
        getSupportActionBar().setTitle("Mechanicians");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MechaniciansFragment()).commit();
        }
        else if(id== R.id.nav_chats){
        getSupportActionBar().setTitle("chats");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ChatFragment()).commit();
        }
        else if(id== R.id.nav_exit){

        }
        DrawerLayout d= (DrawerLayout) findViewById(R.id.drawer_layout);
        d.closeDrawer(GravityCompat.START);

        return true;
    }

    public void updateNavaBAr(){
        NavigationView nav= (NavigationView) findViewById(R.id.nav_view);
        View hv= nav.getHeaderView(0);
        final TextView userMail= hv.findViewById(R.id.email);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("Persons").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Person person = dataSnapshot.getValue(Person.class);
                if(person.getEmail() != null){
                    userMail.setText(person.getEmail());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
