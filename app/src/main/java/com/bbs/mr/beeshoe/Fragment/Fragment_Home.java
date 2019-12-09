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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bbs.mr.beeshoe.Activity.MainActivity;
import com.bbs.mr.beeshoe.Adapter.Adapter_SP;
import com.bbs.mr.beeshoe.Model.Model_SP;
import com.bbs.mr.beeshoe.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Fragment_Home extends Fragment {
    String url = "https://datnbbs.000webhostapp.com/getAll.php";

    private Button btnNam, btnNu, btnSandal, btnOther, btnAllNam, btnAllNu, btnAllSandal;

    List<Model_SP> listNam;
    List<Model_SP> listNu;
    List<Model_SP> listSandal;
    List<Model_SP> modelNam;
    List<Model_SP> modelNu;
    List<Model_SP> modelSandal;
    RecyclerView rcvNam;
    RecyclerView rcvNu;
    RecyclerView rcvSandal;
    Adapter_SP adapterNam, adapterNu, adapterSandal;
    boolean isLoad = true;
    CountDownTimer countDown;
    ProgressBar prgNam, prgNu, prgSandal;
    ViewFlipper vf;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);
        vf = view.findViewById(R.id.vf_home);
        btnNam = view.findViewById(R.id.btnGiayNamHome);
        btnNu = view.findViewById(R.id.btnGiayNuHome);
        btnSandal = view.findViewById(R.id.btnSandalHome);
        btnOther = view.findViewById(R.id.btnPhuKienHome);
        btnAllNam = view.findViewById(R.id.AllGiayNam);
        btnAllNu = view.findViewById(R.id.AllGiayNu);
        btnAllSandal = view.findViewById(R.id.AllSanDal);
        rcvNam = view.findViewById(R.id.rcvRvNam);
        rcvNu = view.findViewById(R.id.rcvRvNu);
        rcvSandal = view.findViewById(R.id.rcvRvSandal);
        listNam = new ArrayList<>();
        listNu = new ArrayList<>();
        listSandal = new ArrayList<>();
        modelNam = new ArrayList<>();
        modelNu = new ArrayList<>();
        modelSandal = new ArrayList<>();
        prgNam = view.findViewById(R.id.prgRvNam);
        prgNu = view.findViewById(R.id.prgRvNu);
        prgSandal = view.findViewById(R.id.prgRvSandal);

        GetSP(url);

        rcvNam.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        rcvNu.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        rcvSandal.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        rcvNam.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        rcvNu.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        rcvSandal.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        adapterNam = new Adapter_SP(getContext(), modelNam);
        adapterNu = new Adapter_SP(getContext(), modelNu);
        adapterSandal = new Adapter_SP(getContext(), modelSandal);
        WaitForLoad();
        rcvNam.setAdapter(adapterNam);
        rcvNu.setAdapter(adapterNu);
        rcvSandal.setAdapter(adapterSandal);

        BtnClick();
        SetViewFliper();

        return view;
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

            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)

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

    private void BtnClick() {
        btnNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).GiayNam();
            }
        });
        btnNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).GiayNu();
            }
        });
        btnSandal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).Sandal();
            }
        });
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).Other();
            }
        });
        btnAllNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).GiayNam();
            }
        });
        btnAllNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).GiayNu();
            }
        });
        btnAllSandal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).Sandal();
            }
        });
    }

    private void SetViewFliper() {
        ArrayList<String> banners = new ArrayList<>();
        banners.add("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/banner%2Frunning-shoes-banner-1.jpg?alt=media&token=5548a173-d6d2-4f24-8a8a-35ba331077f1");
        banners.add("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/banner%2Fslides_1573098147.png?alt=media&token=8835fe7e-58d7-4121-bf03-174809604842");
        banners.add("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/banner%2Ftstg-products-banner-new.jpg?alt=media&token=18e982a9-95e3-4ddb-b044-86b1a7dbfe2c");
        banners.add("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/banner%2Fwomen-running-shoes-banner-1.jpg?alt=media&token=9fa08250-f93c-4881-b5c5-16439a41a9e0");
        for (int i = 0; i < banners.size(); i++) {
            ImageView img = new ImageView(getContext());
            Picasso.get().load(banners.get(i)).into(img);
            //img.setScaleType(ImageView.ScaleType.FIT_XY);
            vf.addView(img);
        }
        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        vf.setFlipInterval(5000); // 3s
        vf.setInAnimation(in);
        vf.setOutAnimation(out);
        vf.setAutoStart(true);
    }

    private void GetSP(String url) {
        final RequestQueue request = Volley.newRequestQueue(getContext());
        final JsonArrayRequest array = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if (object.getString("sex_sp").equals("1")) {
                            listNam.add(new Model_SP(object.getInt("id_sp"),
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
                        } else if (object.getString("sex_sp").equals("2")) {
                            listNu.add(new Model_SP(object.getInt("id_sp"),
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
                        } else if (object.getString("sex_sp").equals("0")) {
                            listSandal.add(new Model_SP(object.getInt("id_sp"),
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
                        }
                        //model.add(list.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.sort(listNam, new Comparator<Model_SP>() {
                    public int compare(Model_SP obj1, Model_SP obj2) {
                        return Integer.valueOf(obj2.getCount_click()).compareTo(Integer.valueOf(obj1.getCount_click())); // To compare integer values
                    }
                });
                Collections.sort(listNu, new Comparator<Model_SP>() {
                    public int compare(Model_SP obj1, Model_SP obj2) {
                        return Integer.valueOf(obj2.getCount_click()).compareTo(Integer.valueOf(obj1.getCount_click())); // To compare integer values
                    }
                });
                Collections.sort(listSandal, new Comparator<Model_SP>() {
                    public int compare(Model_SP obj1, Model_SP obj2) {
                        return Integer.valueOf(obj2.getCount_click()).compareTo(Integer.valueOf(obj1.getCount_click())); // To compare integer values
                    }
                });
                if (!listNam.isEmpty()){
                    for (int i =0;i<10;i++){
                        modelNam.add(listNam.get(i));
                    }
                }
                if (!listNu.isEmpty()){
                    for (int i =0;i<10;i++){
                        modelNu.add(listNu.get(i));
                    }
                }
                if (!listSandal.isEmpty()){
                    for (int i =0;i<10;i++){
                        modelSandal.add(listSandal.get(i));
                    }
                }

                isLoad = false;
                adapterNam.notifyDataSetChanged();
                adapterNu.notifyDataSetChanged();
                adapterSandal.notifyDataSetChanged();
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
        countDown = new CountDownTimer(50000, 1000) {

            public void onTick(long millisUntilFinished) {
                prgNam.setVisibility(View.VISIBLE);
                prgNu.setVisibility(View.VISIBLE);
                prgSandal.setVisibility(View.VISIBLE);
                rcvNam.setVisibility(View.INVISIBLE);
                rcvNu.setVisibility(View.INVISIBLE);
                rcvSandal.setVisibility(View.INVISIBLE);
                if (!isLoad) {
                    onFinish();
                }
            }

            public void onFinish() {
                prgNam.setVisibility(View.GONE);
                prgNu.setVisibility(View.GONE);
                prgSandal.setVisibility(View.GONE);
                rcvNam.setVisibility(View.VISIBLE);
                rcvNu.setVisibility(View.VISIBLE);
                rcvSandal.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}
