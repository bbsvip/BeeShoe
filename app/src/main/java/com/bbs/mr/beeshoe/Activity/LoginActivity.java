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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bbs.mr.beeshoe.Model.Model_Acc;
import com.bbs.mr.beeshoe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private Button register;
    private Button back;
    private EditText user;
    private EditText pass;
    private CheckBox chk;
    private SharedPreferences spr;
    String url = "https://datnbbs.000webhostapp.com/login.php";
    List<Model_Acc> list;
    boolean chkAcc = false;
    String u, p;
    ProgressBar prg;
    CountDownTimer countDown;
    String idUser,userName,userEmail,userDate,userAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        user.setText(pref.getString("USERNAME", ""));
        pass.setText(pref.getString("PASSWORD", ""));
        list = new ArrayList<>();
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
        prg = findViewById(R.id.prgLogin);
    }

    public void ButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                u = "";
                p = "";
                u = user.getText().toString();
                p = pass.getText().toString();
                if (u.isEmpty()) {
                    Toast.makeText(this, "Chưa nhập email !", Toast.LENGTH_SHORT).show();
                } else if (p.isEmpty()) {
                    Toast.makeText(this, "Chưa nhập mật khẩu !", Toast.LENGTH_SHORT).show();
                } else {
                    //Boolean chk = db.checkLogin(u, p);
                    Check(u, p);
                    WaitForLoad();
                    rememberUser(u, p, chk.isChecked());
                }
                break;
            case R.id.btnRegister:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btnBackLogin:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
        }
    }

    public void Check(final String u, final String p) {

        Map<String, String> params = new HashMap<>();
        params.put("user", u);
        params.put("pass", p);
        //Create request
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        final StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        list.clear();
                        try {
                            JSONArray a = new JSONArray(response);
                            Log.e("user          ",a.toString());
                            for (int i = 0; i < a.length(); i++) {
                                JSONObject ob = a.getJSONObject(i);
                                list.add(new Model_Acc(ob.getInt("id_acc"),
                                        ob.getString("user"),
                                        ob.getString("pass"),
                                        ob.optString("name"),
                                        ob.optString("email"),
                                        ob.getString("user_date"),
                                        ob.getString("user_address"),
                                        ob.getString("user_favor")));
                            }
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getUser().equals(u)&& list.get(i).getPass().equals(p)){
                                    idUser = String.valueOf(list.get(i).getId_acc());
                                    userName = list.get(i).getUser_name();
                                    userEmail = list.get(i).getUser_email();
                                    userDate = list.get(i).getUser_date();
                                    userAddress = list.get(i).getUser_address();
                                    chkAcc = true;
                                    break;
                                } else {
                                    chkAcc = false;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        request.setShouldCache(false);
        queue.getCache().clear();
        queue.add(request);
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

    public void setSave(String id,String name,String email,String date,String address) {
        SharedPreferences pref = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("ID", Integer.valueOf(id));
        edit.putString("USER", u);
        edit.putString("NAME", name);
        edit.putString("EMAIL", email);
        edit.putString("DATE", date);
        edit.putString("ADDRESS", address);
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
                if (chkAcc) {
                    Toast.makeText(getApplicationContext(), "Xin chào "+userName, Toast.LENGTH_SHORT).show();
                    setSave(idUser,userName,userEmail,userDate,userAddress);
                    MainActivity.isLogin = true;
                    cancel();
                    finish();
                }
            }

            public void onFinish() {
                prg.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}
