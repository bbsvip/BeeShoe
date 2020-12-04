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
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import java.util.Objects;

public class Fragment_Acer extends Fragment {

    private static final String[] muc = {
            "Tất cả",
            "Gaming",
            "Aspire",
            "Swift"};
    private static final String[] gia = {
            "Phổ biến",
            "Giá: thấp - cao",
            "Giá: cao - thấp",};
    String url = "https://datnbbs.000webhostapp.com/getAll.php";
    CountDownTimer countDown;
    boolean isLoading = false;
    boolean over = false;
    private int sex = 2;
    private int pointType = 7;
    private boolean isLoad = true;
    private ProgressBar prg;
    private Spinner spn, spn_gia;
    private RecyclerView recyclerView;
    private Adapter_SP adapter;
    private List<Model_SP> model;
    private List<Model_SP> list;
    private List<Model_SP> listSex;
    private List<Model_SP> listType;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.acer, container, false);
        spn = view.findViewById(R.id.spn_nu);
        spn_gia = view.findViewById(R.id.spn_nu_gia);

        prg = view.findViewById(R.id.prgNu);
        ArrayAdapter<String> adapter_muc = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, muc);
        adapter_muc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter_gia = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, gia);
        adapter_gia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn.setAdapter(adapter_muc);
        spn_gia.setAdapter(adapter_gia);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                over = false;
                if (position == 0) {
                    isLoad = true;
                    WaitForLoad();
                    for (int i = 0; i < listSex.size(); i++) {
                        model.clear();
                        listType.clear();
                        listSex.clear();
                        list.clear();
                        GetAllSP(url);
                        setAdapterSP();
                    }
                } else {
                    model.clear();
                    listType.clear();
                    for (int i = 0; i < listSex.size(); i++) {
                        if (position + pointType == listSex.get(i).getType()) {
                            //model.add(listSex.get(i));
                            listType.add(listSex.get(i));
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        if (i == listType.size()) {
                            break;
                        } else {
                            model.add(listType.get(i));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                setAdapterSP();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_gia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Collections.sort(model, new Comparator<Model_SP>() {
                        public int compare(Model_SP obj1, Model_SP obj2) {
                            return Integer.valueOf(obj2.getCount_click()).compareTo(Integer.valueOf(obj1.getCount_click())); // To compare integer values
                        }
                    });
                }
                if (position == 1) {
                    Collections.sort(model, new Comparator<Model_SP>() {
                        public int compare(Model_SP obj1, Model_SP obj2) {
                            // ## Ascending order
                            return Double.valueOf(obj1.getGia()).compareTo(Double.valueOf(obj2.getGia())); // To compare integer values
                        }
                    });
                }
                if (position == 2) {
                    Collections.sort(model, new Comparator<Model_SP>() {
                        public int compare(Model_SP obj1, Model_SP obj2) {
                            // ## Descending order
                            return Double.valueOf(obj2.getGia()).compareTo(Double.valueOf(obj1.getGia())); // To compare integer values
                        }
                    });
                }
                adapter.notifyDataSetChanged();
                setAdapterSP();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView = view.findViewById(R.id.rcv_nu);
        model = new ArrayList<>();
        list = new ArrayList<>();
        listSex = new ArrayList<>();
        listType = new ArrayList<>();

        GetAllSP(url);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapterSP();

        initScrollListener();
        //prepareSP();
        try {
            Glide.with(this).load(R.drawable.ic_launcher_background).into((ImageView) view.findViewById(R.id.img_sp));
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void setAdapterSP() {
        WaitForLoad();
        adapter = new Adapter_SP(getContext(), model);
        recyclerView.setAdapter(adapter);

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
                        list.add(new Model_SP(object.getInt("id_sp"),
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
                Collections.sort(list, new Comparator<Model_SP>() {
                    public int compare(Model_SP obj1, Model_SP obj2) {
                        return Integer.valueOf(obj2.getCount_click()).compareTo(Integer.valueOf(obj1.getCount_click())); // To compare integer values
                    }
                });
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getSex() == sex) {
                        listSex.add(list.get(i));
                    }
                }
                if (listSex.size() > 10) {
                    for (int i = 0; i < 10; i++) {
                        model.add(listSex.get(i));
                    }
                } else {
                    for (int i = 0; i < listSex.size(); i++) {
                        model.add(listSex.get(i));
                    }
                }
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
        array.setShouldCache(false);
        request.getCache().clear();
        request.add(array);
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!over) {
                    if (!isLoading) {
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == model.size() - 1) {
                            //bottom of list!
                            loadMore();
                            isLoading = true;
                        }
                    }
                }
            }
        });
    }

    private void loadMore() {
        model.add(null);
        adapter.notifyItemInserted(model.size() - 1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                model.remove(model.size() - 1);
                int scrollPosition = model.size();
                adapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 5;

                if (spn.getSelectedItemPosition() == 0) {
                    if (model.size() < listSex.size()) {
                        while (currentSize - 1 < nextLimit) {
                            if (currentSize == listSex.size()) {
                                break;
                            } else {
                                model.add(listSex.get(currentSize));
                            }
                            currentSize++;
                        }
                    } else {
                        over = true;
                        Snackbar.make(Objects.requireNonNull(getView()), "Đã xem hết sản phẩm!", Snackbar.LENGTH_LONG)
                                .setAction("", null).show();
                        //Toast.makeText(getContext(), "Đã xem hết sản phẩm !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (model.size() < listType.size()) {
                        while (currentSize - 1 < nextLimit) {
                            if (currentSize == listType.size()) {
                                break;
                            } else {
                                model.add(listType.get(currentSize));
                            }
                            currentSize++;
                        }
                    } else {
                        over = true;
                        Snackbar.make(Objects.requireNonNull(getView()), "Đã xem hết sản phẩm trong mục " + spn.getSelectedItem(), Snackbar.LENGTH_LONG)
                                .setAction("", null).show();
                        //Toast.makeText(getContext(), "Đã xem hết sản phẩm trong mục " + spn.getSelectedItem(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    private void WaitForLoad() {
        countDown = new CountDownTimer(50000, 1000) {

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
}