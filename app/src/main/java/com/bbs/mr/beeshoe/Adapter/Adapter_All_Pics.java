// Copyright (c) 2019.
// Tạo bởi Cừu Đen
//
// Gmail: 0331999bbs@gmail.com
// Phone: 0347079556

package com.bbs.mr.beeshoe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbs.mr.beeshoe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_All_Pics extends BaseAdapter {
    String[] all_pic;
    Context context;

    public Adapter_All_Pics(Context context,String[] all_pic) {
        this.all_pic = all_pic;
        this.context = context;
    }

    @Override
    public int getCount() {
        return all_pic.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_all_pic,parent,false);
            holder = new ViewHolder();
            holder.img = view.findViewById(R.id.img_item_pics);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String url = all_pic[position];
        if (!url.isEmpty()){
            Picasso.get().load(url).into(holder.img);
        } else {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/other_image%2Ferror.png?alt=media&token=1ffa3591-6b4c-4dd7-8a47-ba4ac6605f8f").into(holder.img);
        }

        return view;
    }
    public class ViewHolder{
        ImageView img;
    }
}
