// Copyright (c) 2019.
// Tạo bởi Cừu Đen
//
// Gmail: 0331999bbs@gmail.com
// Phone: 0347079556

package com.bbs.mr.beeshoe.Activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bbs.mr.beeshoe.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button login;
    private Button register;
    private Button back;
    private EditText user;
    private EditText pass;
    private EditText repass;
    private EditText email, name;
    private SharedPreferences spr;
    String url = "https://datnbbs.000webhostapp.com/register.php";
    boolean isRegisted = false;
    ProgressBar prg;
    CountDownTimer countDown;
    String u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findView();
    }

    void findView() {
        login = findViewById(R.id.btnLoginRe);
        register = findViewById(R.id.btnRegisterRe);
        back = findViewById(R.id.btnBackRegister);
        spr = getSharedPreferences("LOGIN", MODE_PRIVATE);
        user = findViewById(R.id.edtUserRegister);
        pass = findViewById(R.id.edtPassRegister);
        repass = findViewById(R.id.edtRePassRegister);
        email = findViewById(R.id.edtEmailRegister);
        name = findViewById(R.id.edtNameRegister);
        prg = findViewById(R.id.prgRegister);
    }

    public void ButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btnLoginRe:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.btnRegisterRe:
                u = user.getText().toString(); String p = pass.getText().toString();
                if (u.isEmpty()) {
                    Toast.makeText(this, "Chưa nhập email !", Toast.LENGTH_SHORT).show();
                } else if (p.isEmpty()) {
                    Toast.makeText(this, "Chưa nhập mật khẩu !", Toast.LENGTH_SHORT).show();
                } else {
                    Register();
                    WaitForLoad();
                }
                break;
            case R.id.btnBackRegister:
                finish();
                break;
        }
    }

    private void Register() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prg.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                register.setVisibility(View.GONE);
                if (response.equals("success")){
                    isRegisted = true;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("clclclclclclc         ", "onErrorResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user", user.getText().toString());
                params.put("pass", pass.getText().toString());
                params.put("user_name", name.getText().toString());
                params.put("user_email", email.getText().toString());
                return params;
            }
            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue request = Volley.newRequestQueue(RegisterActivity.this);
        request.getCache().remove(url);
        request.add(stringRequest);
    }

    public void setSave(String user) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        edit.putString("USERNAME", user);
        edit.commit();
    }

    BroadcastReceiver checkInternet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            if (connectivityManager.getActiveNetworkInfo() != null) {
                login.setClickable(true);
                register.setClickable(true);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        login.setClickable(false);
                        register.setClickable(false);
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

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checkInternet, filter);
    }
    //hủy bỏ

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(checkInternet);
    }
    private void WaitForLoad() {
        countDown = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                prg.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                register.setVisibility(View.GONE);
                if (isRegisted) {
                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    setSave(u);
                    cancel();
                    finish();
                }
            }

            public void onFinish() {
                prg.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
                register.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}
