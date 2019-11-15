package com.bbs.mr.beeshoe.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bbs.mr.beeshoe.Fragment.Fragment_Home;
import com.bbs.mr.beeshoe.Fragment.Fragment_Nam;
import com.bbs.mr.beeshoe.Fragment.Fragment_Nu;
import com.bbs.mr.beeshoe.Fragment.Fragment_Other;
import com.bbs.mr.beeshoe.Fragment.Fragment_Sandal;
import com.bbs.mr.beeshoe.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String u, p, mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ckShap() < 0) {
            Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
            /*Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);*/
        } else {
            Toast.makeText(this, "Mừng bạn trở lại "+u, Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
        navigationView.setCheckedItem(R.id.nav_home);
        setTitle("Trang chủ");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_acc) {
            // inflate menu
            View vItem = this.findViewById(R.id.nav_acc);
            MenuBuilder menuBuilder = new MenuBuilder(this);
            MenuInflater inflater = new MenuInflater(this);
            inflater.inflate(R.menu.menu_acc, menuBuilder);
            MenuPopupHelper optionsMenu = new MenuPopupHelper(this, menuBuilder, vItem);
            optionsMenu.setForceShowIcon(true);

            // Set Item Click Listener
            menuBuilder.setCallback(new MenuBuilder.Callback() {
                @Override
                public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.acc_login: // Handle option1 Click
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            return true;
                        case R.id.acc_register: // Handle option2 Click
                            return true;
                        case R.id.acc_cart: // Handle option2 Click
                            return true;
                        case R.id.acc_logout: // Handle option2 Click
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onMenuModeChange(MenuBuilder menu) {}
            });


            // Display the menu
            optionsMenu.show();
            /*PopupMenu popup = new PopupMenu(this, vItem);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_acc, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });
            popup.show();*/
        } else if (id == R.id.nav_search) {
            Toast.makeText(this, "Search Actionbar", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
            setTitle("Trang chủ");
        } else if (id == R.id.nav_nam) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Nam()).commit();
            setTitle("Giày nam");
        } else if (id == R.id.nav_nu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Nu()).commit();
            setTitle("Giày nữ");
        } else if (id == R.id.nav_sandal) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Sandal()).commit();
            setTitle("Sandal - Dép");
        } else if (id == R.id.nav_other) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Other()).commit();
            setTitle("Phụ kiện khác");
        } else if (id == R.id.nav_drawer_acc) {
            Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_setting) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public int ckShap() {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        boolean chk = pref.getBoolean("REMEMBER", false);
        if (chk) {
            u = pref.getString("USERNAME", "");
            p = pref.getString("PASSWORD", "");
            return 1;
        }
        return -1;
    }
}
