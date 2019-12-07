// Copyright (c) 2019.
// Tạo bởi Cừu Đen
//
// Gmail: 0331999bbs@gmail.com
// Phone: 0347079556

package com.bbs.mr.beeshoe.Fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bbs.mr.beeshoe.Adapter.Adapter_SP;
import com.bbs.mr.beeshoe.Model.Model_SP;
import com.bbs.mr.beeshoe.R;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Fragment_Search extends Fragment {
    String url = "https://datnbbs.000webhostapp.com/getAll.php";
    Adapter_SP adapter;
    public static boolean isLoad = true;
    public static List<Model_SP> model;
    RecyclerView recyclerView;
    ProgressBar prg;
    TextView noti;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search, container, false);

        model = new ArrayList<>();

        recyclerView = view.findViewById(R.id.rcv_search);
        prg = view.findViewById(R.id.prgSearch);
        noti = view.findViewById(R.id.tvNotiSearch);
        noti.setVisibility(View.GONE);
        GetAllSP(url);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SetAdapter(model);
        try {
            Glide.with(this).load(R.drawable.ic_launcher_background).into((ImageView) view.findViewById(R.id.img_sp));
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void SetAdapter(List<Model_SP> list) {
        adapter = new Adapter_SP(getContext(), list);
        recyclerView.setAdapter(adapter);
        WaitForLoad();
    }

    public void IsFound(Boolean isFound) {
        if (isFound) {
            noti.setVisibility(View.GONE);
        } else {
            noti.setVisibility(View.VISIBLE);
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }

    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void GetAllSP(String url) {
        final RequestQueue request = Volley.newRequestQueue(getContext());
        final JsonArrayRequest array = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        model.add(new Model_SP(object.getInt("id_sp"),
                                object.getString("name_sp"),
                                object.getString("pic_sp"),
                                object.getString("pics_sp"),
                                object.getString("info_sp"),
                                object.getString("size_sp"),
                                object.getInt("color_sp"),
                                object.getInt("count_click"),
                                object.getDouble("gia_sp"),
                                object.getInt("sl_sp"),
                                object.getInt("sex_sp"),
                                object.getInt("type"),
                                object.getInt("rate")));
                        //model.add(list.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort(model, new Comparator<Model_SP>() {
                    public int compare(Model_SP obj1, Model_SP obj2) {
                        return Integer.valueOf(obj2.getCount_click()).compareTo(Integer.valueOf(obj1.getCount_click())); // To compare integer values
                    }
                });

                isLoad = false;
                adapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        request.add(array);
    }

    private void WaitForLoad() {
        CountDownTimer countDown = new CountDownTimer(50000, 1000) {

            public void onTick(long millisUntilFinished) {
                prg.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                if (!isLoad) {
                    onFinish();
                }
            }

            public void onFinish() {
                prg.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}
