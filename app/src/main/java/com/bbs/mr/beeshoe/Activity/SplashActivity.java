package com.bbs.mr.beeshoe.Activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bbs.mr.beeshoe.R;

import java.util.Timer;

public class SplashActivity extends AppCompatActivity {

    ProgressBar prg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getApplicationContext().setTheme(R.style.AppTheme);
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
        setContentView(R.layout.activity_splash);
        prg = findViewById(R.id.prgSplash);

    }

    BroadcastReceiver checkInternet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            if (connectivityManager.getActiveNetworkInfo() != null) {
                int timeout = 2000;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Start home activity
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        // close splash activity
                        finish();
                    }
                }, timeout );
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        prg.setVisibility(View.VISIBLE);
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
}
