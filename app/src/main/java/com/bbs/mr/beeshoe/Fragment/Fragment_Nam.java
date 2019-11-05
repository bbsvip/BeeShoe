package com.bbs.mr.beeshoe.Fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import java.util.List;

public class Fragment_Nam extends Fragment implements AdapterView.OnItemSelectedListener {

    String url = "http://hoadondientuquynhon.com/getAll.php";
    private Spinner spn, spn_gia, spn_size;
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
    private static final String[] size = {
            "Mọi size",
            "38",
            "39",
            "40",
            "41",
            "42",
            "43"};
    private RecyclerView recyclerView;
    private Adapter_SP adapter;
    private List<Model_SP> model;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.giay_nam, container, false);
        spn = view.findViewById(R.id.spn_nam);
        spn_gia = view.findViewById(R.id.spn_nam_gia);
        spn_size = view.findViewById(R.id.spn_nam_size);
        ArrayAdapter<String> adapter_muc = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, muc);
        adapter_muc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter_gia = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, gia);
        adapter_muc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter_size = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, size);
        adapter_muc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter_muc);
        spn.setOnItemSelectedListener(this);
        spn_gia.setAdapter(adapter_gia);
        spn_gia.setOnItemSelectedListener(this);
        spn_size.setAdapter(adapter_size);
        spn_size.setOnItemSelectedListener(this);

        recyclerView = view.findViewById(R.id.rcv_nam);
        model = new ArrayList<>();
        adapter = new Adapter_SP(getContext(), model);

        GetAllSP(url);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(getContext(), "Single Click on position :" + position,
                        Toast.LENGTH_SHORT).show();
            }
        }));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //prepareSP();
        try {
            Glide.with(this).load(R.drawable.ic_launcher_background).into((ImageView) view.findViewById(R.id.img_sp));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public static interface ClickListener {
        public void onClick(View view, int position);
    }

    private void prepareSP() {

        model.add(new Model_SP(1, "giay", "https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/moi_nam%2Fpreview%2Fgiay-moi-nam-c-o-m060-de_m500m500.jpg?alt=media&token=92cff3a8-921d-484c-b4ca-3741fd373689", 100, 900000, 1, 1, 5));
        model.add(new Model_SP(2, "giay", "https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/moi_nam%2Fpreview%2Fgiay-moi-nam-c-o-m060-de_m500m500.jpg?alt=media&token=92cff3a8-921d-484c-b4ca-3741fd373689", 100, 900000, 1, 1, 5));
        model.add(new Model_SP(3, "giay", "https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/moi_nam%2Fpreview%2Fgiay-moi-nam-c-o-m060-de_m500m500.jpg?alt=media&token=92cff3a8-921d-484c-b4ca-3741fd373689", 100, 900000, 1, 1, 5));
        model.add(new Model_SP(4, "giay", "https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/moi_nam%2Fpreview%2Fgiay-moi-nam-c-o-m060-de_m500m500.jpg?alt=media&token=92cff3a8-921d-484c-b4ca-3741fd373689", 100, 900000, 1, 1, 5));
        model.add(new Model_SP(5, "giay", "https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/moi_nam%2Fpreview%2Fgiay-moi-nam-c-o-m060-de_m500m500.jpg?alt=media&token=92cff3a8-921d-484c-b4ca-3741fd373689", 100, 900000, 1, 1, 5));
        model.add(new Model_SP(6, "giay", "https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/moi_nam%2Fpreview%2Fgiay-moi-nam-c-o-m060-de_m500m500.jpg?alt=media&token=92cff3a8-921d-484c-b4ca-3741fd373689", 100, 900000, 1, 1, 5));

    }

    /*private void setAdapter(){
        if (adapter == null){
            adapter = new FruitAdapter(getContext(), R.layout.item_fruit, list);
            lv.setAdapter(adapter);
        } else {
            lv.setSelection(adapter.getCount() - 1);
        }
    }*/
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

    //Spinner item click
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("item", parent.getItemAtPosition(position).toString());
        Log.i("item cc", "" + parent.getSelectedItemPosition());
        Log.i("item cl", parent.getId() + "");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Recycler item click
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    private void GetAllSP(String url) {
        final RequestQueue request = Volley.newRequestQueue(getContext());
        final JsonArrayRequest array = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        model.add(new Model_SP(
                                object.getInt("id_sp"),
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
                                object.getInt("rate")
                        ));
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
