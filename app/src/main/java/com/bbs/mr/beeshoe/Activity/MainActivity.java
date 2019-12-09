// Copyright (c) 2019.
// Tạo bởi Cừu Đen
//
// Gmail: 0331999bbs@gmail.com
// Phone: 0347079556

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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.mr.beeshoe.Adapter.Adapter_Cart;
import com.bbs.mr.beeshoe.Adapter.Adapter_Chat;
import com.bbs.mr.beeshoe.AutoChat;
import com.bbs.mr.beeshoe.Fragment.Fragment_Account;
import com.bbs.mr.beeshoe.Fragment.Fragment_Home;
import com.bbs.mr.beeshoe.Fragment.Fragment_Nam;
import com.bbs.mr.beeshoe.Fragment.Fragment_Nu;
import com.bbs.mr.beeshoe.Fragment.Fragment_Order;
import com.bbs.mr.beeshoe.Fragment.Fragment_Other;
import com.bbs.mr.beeshoe.Fragment.Fragment_Sandal;
import com.bbs.mr.beeshoe.Fragment.Fragment_Search;
import com.bbs.mr.beeshoe.Model.Model_Cart;
import com.bbs.mr.beeshoe.Model.Model_Chat;
import com.bbs.mr.beeshoe.Model.Model_SP;
import com.bbs.mr.beeshoe.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String url = "https://datnbbs.000webhostapp.com/getCart.php";
    NavigationView navigationView;
    String u, mUser;
    static boolean isLogin = false;
    //TextView tvCountCart ;
    boolean fisrtBack = false;
    boolean fabHide = false;
    FloatingActionButton fabCart, fabChat;

    ArrayList<Model_Chat> model_chat;
    Adapter_Chat adapter_chat;
    EditText edtMess;
    static Random rand = new Random();
    static String sender;
    ListView lvChat;

    Dialog dialog_cart;
    Button btnOrder, btnBackCart;
    TextView tvTotal, tvNotiCart;
    ListView lvCart;
    List<Model_Cart> model_carts;
    public static List<Model_Cart> listCart;
    Adapter_Cart adapter_cart;
    double x = 0;
    DecimalFormat df;
    public static boolean isUpSL = false;
    double total;

    MaterialSearchView searchView;
    Fragment_Search fragmentSearch;
    Toolbar toolbar;

    SharedPreferences pref;

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

        listCart = new ArrayList<>();
        model_carts = new ArrayList<>();
        model_chat = new ArrayList<>();


        df = new DecimalFormat("#,###");
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref = getSharedPreferences("USER", MODE_PRIVATE);

        FloatingActionButton fab = findViewById(R.id.fab);
        fabCart = findViewById(R.id.fabCart);
        fabChat = findViewById(R.id.fabChat);
        fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCart();
                closeFABMenu();
            }
        });
        fabChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
                adapter_chat = new Adapter_Chat(getBaseContext(), model_chat);

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_chat);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int widthLcl = (int) (displayMetrics.widthPixels * 0.9f);
                int heightLcl = (int) (displayMetrics.heightPixels * 0.5f);
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

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
        navigationView.setCheckedItem(R.id.nav_home);
        setTitle("Trang chủ");
        searchView = findViewById(R.id.search_bar);
        Search();
    }

    private void DialogCart() {
        dialog_cart = new Dialog(MainActivity.this);
        dialog_cart.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_cart.setContentView(R.layout.dialog_cart);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthLcl = (int) (displayMetrics.widthPixels);
        int heightLcl = (int) (displayMetrics.heightPixels * 0.9f);
        lp.copyFrom(dialog_cart.getWindow().getAttributes());
        lp.width = widthLcl;
        lp.height = heightLcl;
        lp.gravity = Gravity.CENTER;
        dialog_cart.getWindow().setAttributes(lp);
        Adapter_Cart.isOrder = false;
        dialog_cart.show();
        btnOrder = dialog_cart.findViewById(R.id.btnOrder);
        tvTotal = dialog_cart.findViewById(R.id.tvTotalCart);
        tvNotiCart = dialog_cart.findViewById(R.id.tvNotiCart);
        lvCart = dialog_cart.findViewById(R.id.lvCart);
        if (model_carts.isEmpty()) {
            tvNotiCart.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.GONE);
            btnOrder.setVisibility(View.GONE);
        } else {
            tvNotiCart.setVisibility(View.GONE);
            lvCart.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.VISIBLE);
        }
        adapter_cart = new Adapter_Cart(dialog_cart.getContext(), model_carts);
        lvCart.setAdapter(adapter_cart);
        x = 0;
        for (int i = 0; i < model_carts.size(); i++) {
            total = model_carts.get(i).getGia() * model_carts.get(i).getSl_cart();
            x += total;
        }
        tvTotal.setText(String.valueOf(df.format(x)) + " VND");

        btnBackCart = dialog_cart.findViewById(R.id.btnBackCart);
        btnBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_cart.dismiss();
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    listCart.addAll(model_carts);
                    dialog_cart.dismiss();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Order()).commit();
                    navigationView.setCheckedItem(R.id.nav_home);
                    setTitle("Thanh toán");
                }
            }
        });
    }

    private void Search() {
        /*List<Model_SP> list = new ArrayList<>();
        Fragment_Search.model*/
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                fragmentSearch = (Fragment_Search) getSupportFragmentManager().findFragmentByTag("FragmentSearch");
                if (text != null && !text.isEmpty()) {
                    List<Model_SP> list = new ArrayList<>();
                    for (Model_SP item : Fragment_Search.model) {
                        if (item.getName().toLowerCase().contains(text) || item.getName().toLowerCase().contains(text.toLowerCase())) {
                            list.add(item);
                            fragmentSearch.IsFound(true);
                        }
                    }
                    if (list.isEmpty()) {
                        fragmentSearch.IsFound(false);
                    }
                    fragmentSearch.SetAdapter(list);
                } else {
                    if (!Fragment_Search.isLoad) {
                        fragmentSearch.SetAdapter(Fragment_Search.model);
                        fragmentSearch.IsFound(true);
                    }
                }
                return true;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                fragmentSearch = new Fragment_Search();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragmentSearch, "FragmentSearch").commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentSearch).commit();
                setTitle("Tìm kiếm");
                toolbar.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
                navigationView.setCheckedItem(R.id.nav_home);
                setTitle("Trang chủ");
                toolbar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
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
        MenuItem item = menu.findItem(R.id.nav_search);
        searchView.setMenuItem(item);
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
                menuBuilder.getRootMenu().getItem(0).setTitle("Xin chào " + pref.getString("NAME", null));
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
                            if (isLogin) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Account()).commit();
                                setTitle("Tài khoản");
                            } else {
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                            return true;
                        case R.id.acc_register: // Handle option3 Click
                            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                            return true;
                        case R.id.acc_cart: // Handle option4 Click
                            DialogCart();
                            return true;
                        case R.id.acc_logout: // Handle option5 Click
                            pref.edit().clear();
                            Home();
                            isLogin = false;
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
            if (isLogin) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Account()).commit();
                setTitle("Tài khoản");
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        } else if (id == R.id.nav_setting) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Home() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Home()).commit();
        navigationView.setCheckedItem(R.id.nav_home);
        setTitle("Trang chủ");

    }

    public void GiayNam() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Nam()).commit();
        navigationView.setCheckedItem(R.id.nav_nam);
        setTitle("Giày nam");
    }

    public void GiayNu() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Nu()).commit();
        navigationView.setCheckedItem(R.id.nav_nu);
        setTitle("Giày nữ");
    }

    public void Sandal() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Sandal()).commit();
        navigationView.setCheckedItem(R.id.nav_sandal);
        setTitle("Sandal - Dép");
    }

    public void Other() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_Other()).commit();
        navigationView.setCheckedItem(R.id.nav_other);
        setTitle("Phụ kiện khác");
    }

    public void AddCart(String img, String name, double gia) {
        model_carts.add(new Model_Cart(1, gia, name, img));
        Toast.makeText(this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
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
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checkInternet, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(checkInternet);
    }

    //Gửi tin nhắn

    public void sendMessage(String mess) {
        if (mess.length() > 0) {
            edtMess.setText("");
            addNewMessage(new Model_Chat(mess, true));
            new SendMessage().execute();
        }
    }

    void addNewMessage(Model_Chat model) {
        model_chat.add(model);
        adapter_chat.notifyDataSetChanged();
        lvChat.setSelection(model_chat.size() - 1);
    }


    //Bot chat
    private class SendMessage extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(2000); //simulate a network call
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.publishProgress(String.format(". . .", sender));
            try {
                Thread.sleep(2000); //simulate a network call
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.publishProgress(String.format("...", sender));
            try {
                Thread.sleep(3000);//simulate a network call
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return AutoChat.messages[rand.nextInt(AutoChat.messages.length - 1)];


        }

        @Override
        public void onProgressUpdate(String... v) {

            if (model_chat.get(model_chat.size() - 1).isStatusMessage)//check wether we have already added a status message
            {
                model_chat.get(model_chat.size() - 1).setMessage(v[0]); //update the status for that
                adapter_chat.notifyDataSetChanged();
                lvChat.setSelection(model_chat.size() - 1);
            } else {
                addNewMessage(new Model_Chat(true, v[0])); //add new message, if there is no existing status message
            }
        }

        @Override
        protected void onPostExecute(String text) {
            if (model_chat.get(model_chat.size() - 1).isStatusMessage)//check if there is any status message, now remove it.
            {
                model_chat.remove(model_chat.size() - 1);
            }

            addNewMessage(new Model_Chat(text, false)); // add the orignal message from server.
        }
    }
}
