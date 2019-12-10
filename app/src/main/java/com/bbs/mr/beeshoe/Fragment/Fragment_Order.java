// Copyright (c) 2019.
// Tạo bởi Cừu Đen
//
// Gmail: 0331999bbs@gmail.com
// Phone: 0347079556

package com.bbs.mr.beeshoe.Fragment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bbs.mr.beeshoe.Activity.MainActivity;
import com.bbs.mr.beeshoe.Adapter.Adapter_Cart;
import com.bbs.mr.beeshoe.Model.Model_Cart;
import com.bbs.mr.beeshoe.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Order extends Fragment {

    String url = "http://datnbbs.000webhostapp.com/upOrder.php";
    Button btnPay;
    TextView tvTotal;
    ListView lvPay;
    SharedPreferences pref;
    public static List<Model_Cart> model;
    Adapter_Cart adapter;
    DecimalFormat df;
    ProgressBar prg;

    EditText name, email, address, phone;
    Button save, back;
    boolean haveAddress = false;
    String user;
    boolean isPay = false;
    int count = 0;
    CountDownTimer countDown;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.order, container, false);

        haveAddress = false;
        pref = getActivity().getSharedPreferences("USER", MODE_PRIVATE);
        user = String.valueOf(pref.getString("USER", ""));
        btnPay = view.findViewById(R.id.btnPay);
        tvTotal = view.findViewById(R.id.tvTotalPay);
        lvPay = view.findViewById(R.id.lvOrder);
        model = new ArrayList<>();
        Adapter_Cart.isOrder = true;
        model.clear();
        model.addAll(MainActivity.listCart);
        df = new DecimalFormat("#,###");
        prg = view.findViewById(R.id.prg_Order);
        prg.setVisibility(View.GONE);
        int x = 0;
        double total = 0;
        for (int i = 0; i < model.size(); i++) {
            total = model.get(i).getGia() * model.get(i).getSl_cart();
            x += total;
        }
        tvTotal.setText(String.valueOf(df.format(x)) + " VND");
        adapter = new Adapter_Cart(getContext(), model);
        lvPay.setAdapter(adapter);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haveAddress) {
                    DialogInputAddress();
                } else {
                    for (int i = 0; i < model.size(); i++) {
                        Pay(model.get(i).getName(), model.get(i).getImg(), String.valueOf(model.get(i).getGia()), String.valueOf(model.get(i).getSl_cart()));
                    }
                    WaitForLoad();
                }
            }
        });


        return view;
    }

    private void DialogInputAddress() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_order);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthLcl = (int) (displayMetrics.widthPixels);
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = widthLcl;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        name = dialog.findViewById(R.id.edtNameOrder);
        name.setText(pref.getString("NAME", ""));
        email = dialog.findViewById(R.id.edtEmailOrder);
        email.setText(pref.getString("EMAIL", ""));
        address = dialog.findViewById(R.id.edtAddOrder);
        address.setText(pref.getString("ADDRESS", ""));
        phone = dialog.findViewById(R.id.edtPhoneOrder);
        save = dialog.findViewById(R.id.btnSaveOrder);
        back = dialog.findViewById(R.id.btnBackOrder);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("NAME", name.getText().toString());
                edit.putString("EMAIL", email.getText().toString());
                edit.putString("PHONE", phone.getText().toString());
                edit.putString("ADDRESS", address.getText().toString());
                edit.commit();
                dialog.dismiss();
                haveAddress = true;
            }
        });
    }

    private void Pay(final String name_sp, final String img, final String gia_sp, final String sl_sp) {
        count = 0;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("cccc     ", "onResponse: " + response);
                if (response.equals("success")) {
                    count++;
                    isPay = true;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("clclclclclclc         ", "onErrorResponse: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("user_name", pref.getString("NAME", ""));
                params.put("user_email", pref.getString("EMAIL", ""));
                params.put("user_phone", pref.getString("PHONE", ""));
                params.put("user_address", pref.getString("ADDRESS", ""));
                params.put("name_sp", name_sp);
                params.put("img", img);
                params.put("gia_sp", gia_sp);
                params.put("sl_sp", sl_sp);
                return params;
            }

            @Override
            public Request.Priority getPriority() {
                return Priority.HIGH;
            }
        };
        stringRequest.setShouldCache(false);
        RequestQueue request = Volley.newRequestQueue(getContext());
        request.getCache().remove(url);
        request.add(stringRequest);
    }

    private void WaitForLoad() {
        countDown = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                prg.setVisibility(View.VISIBLE);
                btnPay.setVisibility(View.GONE);
                if (isPay) {
                    cancel();
                    onFinish();
                }
            }

            public void onFinish() {
                prg.setVisibility(View.GONE);
                btnPay.setVisibility(View.VISIBLE);
                if (isPay) {
                    if (count == model.size()) {
                        Toast.makeText(getContext(), "Đã gửi đơn hàng!", Toast.LENGTH_SHORT).show();
                        ((MainActivity) Objects.requireNonNull(getActivity())).Home();
                    }
                }
            }
        }.start();
    }
}

