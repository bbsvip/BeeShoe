package com.bbs.mr.beeshoe.Fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

public class Fragment_Nam extends Fragment {

    String url = "http://hoadondientuquynhon.com/getAll.php";
    private Spinner spn, spn_gia;
    private static final String[] muc = {
            "Tất cả",
            "Giày Mọi",
            "Sabo",
            "Giày Tây",
            "Giày Boot",
            "Comfort",
            "Giày Sneaker Nam",
            "Giày Tăng Chiều Cao"};
    private static final String[] gia = {
            "Phổ biến",
            "Giá: thấp - cao",
            "Giá: cao - thấp",};

    private RecyclerView recyclerView;
    private Adapter_SP adapter;
    private List<Model_SP> model;
    private List<Model_SP> list;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.giay_nam, container, false);
        spn = view.findViewById(R.id.spn_nam);
        spn_gia = view.findViewById(R.id.spn_nam_gia);

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
                model.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (position == 0 && list.get(i).getSex() == 1) {
                        model.add(list.get(i));
                    } else if (list.get(i).getSex() == 1 && position == list.get(i).getType()) {
                        model.add(list.get(i));
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

        recyclerView = view.findViewById(R.id.rcv_nam);
        model = new ArrayList<>();
        list = new ArrayList<>();
        GetAllSP(url);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setAdapterSP();
        //prepareSP();
        try {
            Glide.with(this).load(R.drawable.ic_launcher_background).into((ImageView) view.findViewById(R.id.img_sp));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setAdapterSP() {
        adapter = new Adapter_SP(getContext(), model);
        recyclerView.setAdapter(adapter);
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
                        model.add(list.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
}
