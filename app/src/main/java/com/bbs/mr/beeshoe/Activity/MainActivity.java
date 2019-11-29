package com.bbs.mr.beeshoe.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bbs.mr.beeshoe.Adapter.Adapter_Chat;
import com.bbs.mr.beeshoe.AutoChat;
import com.bbs.mr.beeshoe.Fragment.Fragment_Home;
import com.bbs.mr.beeshoe.Fragment.Fragment_Nam;
import com.bbs.mr.beeshoe.Fragment.Fragment_Nu;
import com.bbs.mr.beeshoe.Fragment.Fragment_Other;
import com.bbs.mr.beeshoe.Fragment.Fragment_Sandal;
import com.bbs.mr.beeshoe.Model.Model_Chat;
import com.bbs.mr.beeshoe.R;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String u, mUser;
    boolean isLogin = false;
    //TextView tvCountCart ;
    public int countcart = 1;
    boolean fisrtBack = false;
    boolean fabHide = false;
    FloatingActionButton fabCart, fabChat;

    ArrayList<Model_Chat> model_chat;
    Adapter_Chat adapter_chat;
    EditText edtMess;
    static Random rand = new Random();
    static String sender;
    ListView lvChat;

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
            //Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
            /*Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);*/
        } else {
            isLogin = true;
            Toast.makeText(this, "Mừng bạn trở lại " + u, Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fabCart = findViewById(R.id.fabCart);
        fabChat = findViewById(R.id.fabChat);
        fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "CC", Toast.LENGTH_SHORT).show();
            }
        });
        fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model_chat = new ArrayList<>();
                adapter_chat = new Adapter_Chat(getBaseContext(),model_chat);

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_chat);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int widthLcl = (int) (displayMetrics.widthPixels*0.9f);
                int heightLcl = (int) (displayMetrics.heightPixels*0.5f);
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = widthLcl;
                lp.height = heightLcl;
                lp.gravity = Gravity.BOTTOM;
                lp.horizontalMargin = 50;
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                lvChat = dialog.findViewById(R.id.lvChat);
                lvChat.setAdapter(adapter_chat);
                edtMess = dialog.findViewById(R.id.edtChatbox);
                Button btnSend = dialog.findViewById(R.id.btnChatboxSend);
                Button back = dialog.findViewById(R.id.btnBackChat);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMessage(edtMess.getText().toString());
                    }
                });

            }
        });
        if (fabHide == false) {
            fabCart.hide();
            fabChat.hide();
        }
        //tvCountCart = findViewById(R.id.tvCart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Cart", Snackbar.LENGTH_LONG)
                        .setAction("", null).show();*/
                //Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                if (fabHide == false) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
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
            if (!fisrtBack) {
                Toast.makeText(this, "Nhấn trở lại một lần nữa để thoát", Toast.LENGTH_SHORT).show();
                fisrtBack = true;
            } else {
                super.onBackPressed();
            }

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
            if (isLogin) {
                menuBuilder.getRootMenu().getItem(0).setTitle("Xin chào " + u);
                menuBuilder.getRootMenu().getItem(1).setVisible(false);
                menuBuilder.getRootMenu().getItem(2).setVisible(true);
                menuBuilder.getRootMenu().getItem(3).setVisible(true);
            } else {
                menuBuilder.getRootMenu().getItem(1).setVisible(true);
                menuBuilder.getRootMenu().getItem(2).setVisible(false);
                menuBuilder.getRootMenu().getItem(3).setVisible(false);
            }
            // Set Item Click Listener
            menuBuilder.setCallback(new MenuBuilder.Callback() {
                @Override
                public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.acc_login: // Handle option1 Click
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            return true;
                        case R.id.acc_register: // Handle option3 Click
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            return true;
                        case R.id.acc_cart: // Handle option4 Click
                            return true;
                        case R.id.acc_logout: // Handle option5 Click
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onMenuModeChange(MenuBuilder menu) {
                }
            });

            // Display the menu
            optionsMenu.show();

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
            Home();
        } else if (id == R.id.nav_nam) {
            GiayNam();
        } else if (id == R.id.nav_nu) {
            GiayNu();
        } else if (id == R.id.nav_sandal) {
            Sandal();
        } else if (id == R.id.nav_other) {
            Other();
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
        SharedPreferences pref = getSharedPreferences("USER", MODE_PRIVATE);
        boolean chk = pref.getBoolean("LOGIN", false);
        if (chk) {
            u = pref.getString("USER", "");
            //p = pref.getString("PASSWORD", "");
            return 1;
        }
        return -1;
    }

    public void Home() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
        setTitle("Trang chủ");

    }

    public void GiayNam() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Nam()).commit();
        setTitle("Giày nam");
    }

    public void GiayNu() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Nu()).commit();
        setTitle("Giày nữ");
    }

    public void Sandal() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Sandal()).commit();
        setTitle("Sandal - Dép");
    }

    public void Other() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Other()).commit();
        setTitle("Phụ kiện khác");
    }

    public void AddCart() {
        countcart++;
        //tvCountCart.setText(countcart);
        Toast.makeText(this, "CC: " + countcart, Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver checkInternet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            if (connectivityManager.getActiveNetworkInfo() != null) {
                /*btnLogin.setEnabled( true );
                btnLogin.setTextColor( Color.WHITE);*/
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       /* btnLogin.setTextColor( Color.rgb( 137, 137, 137 ));
                        btnLogin.setEnabled( false );*/
                        turnOnInternet();
                    }
                }, 1000);

            }
        }
    };

    private void turnOnInternet() {


        Button yes, no;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_network);
        yes = dialog.findViewById(R.id.btnYesCheckNet);
        no = dialog.findViewById(R.id.btnNoCheckNet);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                wifi.setWifiEnabled(true);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showFABMenu() {
        fabHide = true;
        fabCart.show();
        fabChat.show();
        fabCart.animate().translationY(-getResources().getDimension(R.dimen.standard));
        fabChat.animate().translationX(-getResources().getDimension(R.dimen.standard));
    }

    private void closeFABMenu() {
        fabHide = false;
        fabCart.hide();
        fabChat.hide();
        fabCart.animate().translationY(0);
        fabChat.animate().translationX(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checkInternet, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(checkInternet);
    }

    public void sendMessage(String mess)
    {
        if(mess.length() > 0)
        {
            edtMess.setText("");
            addNewMessage(new Model_Chat(mess, true));
            new SendMessage().execute();
        }
    }
    void addNewMessage(Model_Chat model)
    {
        model_chat.add(model);
        adapter_chat.notifyDataSetChanged();
        lvChat.setSelection(model_chat.size()-1);
    }
    private class SendMessage extends AsyncTask<Void, String, String>
    {
        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(2000); //simulate a network call
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.publishProgress(String.format("Cửa hàng đang nhập!", sender));
            try {
                Thread.sleep(2000); //simulate a network call
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.publishProgress(String.format("Cửa hàng đang gửi!", sender));
            try {
                Thread.sleep(3000);//simulate a network call
            }catch (InterruptedException e) {
                e.printStackTrace();
            }


            return AutoChat.messages[rand.nextInt(AutoChat.messages.length-1)];


        }
        @Override
        public void onProgressUpdate(String... v) {

            if(model_chat.get(model_chat.size()-1).isStatusMessage)//check wether we have already added a status message
            {
                model_chat.get(model_chat.size()-1).setMessage(v[0]); //update the status for that
                adapter_chat.notifyDataSetChanged();
                lvChat.setSelection(model_chat.size()-1);
            }
            else{
                addNewMessage(new Model_Chat(true,v[0])); //add new message, if there is no existing status message
            }
        }
        @Override
        protected void onPostExecute(String text) {
            if(model_chat.get(model_chat.size()-1).isStatusMessage)//check if there is any status message, now remove it.
            {
                model_chat.remove(model_chat.size()-1);
            }

            addNewMessage(new Model_Chat(text, false)); // add the orignal message from server.
        }


    }

}
