package com.bbs.mr.beeshoe.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bbs.mr.beeshoe.R;

public class Fragment_Sandal extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner spn, spn_gia, spn_size;
    private static final String[] muc = {
            "Tất cả",
            "Dép Nam",
            "Sandal Nam",
            "Dép Nữ",
            "Sandal Nữ"};
    private static final String[] gia = {
            "Phổ biến",
            "Giá: thấp - cao",
            "Giá: cao - thấp",};
    private static final String[] size = {
            "Mọi size",
            "35",
            "36",
            "37",
            "38",
            "39",
            "40",
            "41",
            "42",
            "43"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sandal, container, false);

        spn = view.findViewById(R.id.spn_sandal);
        spn_gia = view.findViewById(R.id.spn_sandal_gia);
        spn_size = view.findViewById(R.id.spn_sandal_size);
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
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("item", parent.getItemAtPosition(position).toString());
        Log.i("item cc", "" + parent.getSelectedItemPosition());
        Log.i("item cl", parent.getId() + "");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
