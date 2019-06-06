package com.rsaf.aeld.ttackeyregistry;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView keyList;
    String action;
    private CardArrayAdapter cardArrayAdapter;
    RelativeLayout mainLayout;
    Toolbar toolbar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        action = getIntent().getStringExtra("action");

        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        mainLayout = findViewById(R.id.mainScreen);
        keyList = findViewById(R.id.keycard_listView);
        if (action.equals("home")){
            mainLayout.setVisibility(View.VISIBLE);
            toolbar.setTitle("Key Movement");
            setSupportActionBar(toolbar);
//            fab.setVisibility(View.GONE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);
            cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.keycard);

            for (int i = 0; i < 6; i++) {
                if (i == 0){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i+3));
                    cardArrayAdapter.add(card);
                }
                else if (i == 1){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i+3));
                    cardArrayAdapter.add(card);
                }
                else if (i == 2){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i));
                    cardArrayAdapter.add(card);
                }
                else if (i == 3){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i+2));
                    cardArrayAdapter.add(card);
                }
                else if (i == 4){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i-3));
                    cardArrayAdapter.add(card);
                }
                else if (i == 5){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i-4));
                    cardArrayAdapter.add(card);
                }
            }
            keyList.setAdapter(cardArrayAdapter);

            keyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0){
                        Intent toKeyNo = new Intent(MainActivity.this, MainActivity.class);
                        toKeyNo.putExtra("action", "viewKey");
                        toKeyNo.putExtra("keyNo", String.valueOf(position + 1));
                        startActivity(toKeyNo);
                    }
                    else if (position == 1){
                        Intent toKeyNo = new Intent(MainActivity.this, MainActivity.class);
                        toKeyNo.putExtra("action", "viewKey");
                        toKeyNo.putExtra("keyNo", String.valueOf(position + 1));
                        startActivity(toKeyNo);
                    }
                    else if (position == 2){
                        Intent toKeyNo = new Intent(MainActivity.this, MainActivity.class);
                        toKeyNo.putExtra("action", "viewKey");
                        toKeyNo.putExtra("keyNo", String.valueOf(position + 1));
                        startActivity(toKeyNo);
                    }
                    else if (position == 3){
                        Intent toKeyNo = new Intent(MainActivity.this, MainActivity.class);
                        toKeyNo.putExtra("action", "viewKey");
                        toKeyNo.putExtra("keyNo", String.valueOf(position + 1));
                        startActivity(toKeyNo);
                    }
                    else if (position == 4){
                        Intent toKeyNo = new Intent(MainActivity.this, MainActivity.class);
                        toKeyNo.putExtra("action", "viewKey");
                        toKeyNo.putExtra("keyNo", String.valueOf(position + 1));
                        startActivity(toKeyNo);
                    }
                    else if (position == 5){
                        Intent toKeyNo = new Intent(MainActivity.this, MainActivity.class);
                        toKeyNo.putExtra("action", "viewKey");
                        toKeyNo.putExtra("keyNo", String.valueOf(position + 1));
                        startActivity(toKeyNo);
                    }
                }
            });
        }
        else if (action.equals("viewKey")){
            int keyNo = Integer.parseInt(getIntent().getStringExtra("keyNo"));


        } else if (action.equals("viewAvailable")) {

            cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.keycard);

            for (int i = 0; i < 6; i++) {
                if (i == 0){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i+3));
                    cardArrayAdapter.add(card);
                }
                else if (i == 1){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i+3));
                    cardArrayAdapter.add(card);
                }
                else if (i == 2){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i));
                    cardArrayAdapter.add(card);
                }
                else if (i == 3){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i+2));
                    cardArrayAdapter.add(card);
                }
                else if (i == 4){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i-3));
                    cardArrayAdapter.add(card);
                }
                else if (i == 5){
                    Card card = new Card(String.format("%02d", i+1), String.valueOf(i-4));
                    cardArrayAdapter.add(card);
                }
            }
            keyList.setAdapter(cardArrayAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent activity = null;
        if (item.isChecked()){
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

        if (id == R.id.nav_main) {
            activity = new Intent (MainActivity.this, MainActivity.class);
            activity.putExtra("action", "home");
        } else if (id == R.id.nav_available_keys) {
            activity = new Intent(MainActivity.this, MainActivity.class);
            activity.putExtra("action", "viewAvailable");
        } else if (id == R.id.nav_sign_out) {

        }
        item.setChecked(true);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        startActivity(activity);
        return true;
    }
}
