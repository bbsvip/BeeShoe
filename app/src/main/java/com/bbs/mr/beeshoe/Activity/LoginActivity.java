package com.bbs.mr.beeshoe.Activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bbs.mr.beeshoe.R;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private Button register;
    private Button back;
    private EditText user;
    private EditText pass;
    private CheckBox chk;
    private SharedPreferences spr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        user.setText(pref.getString("USERNAME", ""));
        pass.setText(pref.getString("PASSWORD", ""));
    }

    void findView() {
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);
        back = findViewById(R.id.btnBackLogin);
        spr = getSharedPreferences("LOGIN", MODE_PRIVATE);
        user = findViewById(R.id.edtUserLogin);
        user.setText(spr.getString("USER", ""));
        pass = findViewById(R.id.edtPassLogin);
        pass.setText(spr.getString("PASS", ""));
        chk = findViewById(R.id.check);
        chk.setChecked(spr.getBoolean("CHECK", false));
    }

    public void ButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                String u = user.getText().toString(), p = pass.getText().toString();
                if (u.isEmpty()) {
                    Toast.makeText(this, "Chưa nhập email !", Toast.LENGTH_SHORT).show();
                } else if (p.isEmpty()) {
                    Toast.makeText(this, "Chưa nhập mật khẩu !", Toast.LENGTH_SHORT).show();
                } else {
                    //Boolean chk = db.checkLogin(u, p);
                    if (CheckLogin()) {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        rememberUser(u, p, chk.isChecked());
                        setSave(u);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else if (u.equals("bbs") && p.equals("bbs")) {
                        rememberUser(u, p, chk.isChecked());
                        setSave(u);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Email hoặc mật khẩu sai !", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnRegister:
                //startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btnBackLogin:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
        }
    }

    private boolean CheckLogin() {
        return true;
    }

    public void rememberUser(String u, String p, boolean checked) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!checked) {
            edit.clear();
        } else {
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", checked);
            Log.i("---------------------", "Save user");
        }
        edit.commit();
    }

    public void setSave(String user) {
        SharedPreferences pref = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("USER", user);
        edit.commit();
    }
    BroadcastReceiver checkInternet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager)context.getSystemService( CONNECTIVITY_SERVICE );
            if(connectivityManager.getActiveNetworkInfo() != null){
                login.setClickable(true);
                register.setClickable(true);
            }else{
                new Handler(  ).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        login.setClickable(false);
                        register.setClickable(false);
                        turnOnInternet();
                    }
                }, 1000 );

            }
        }
    };

    private void turnOnInternet() {


        Button yes,no;
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
                WifiManager wifi = (WifiManager)getApplicationContext().getSystemService( WIFI_SERVICE );
                wifi.setWifiEnabled( true );
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION );
        registerReceiver( checkInternet, filter );
    }
    //hủy bỏ

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver( checkInternet );
    }
}
