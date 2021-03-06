// Copyright (c) 2019.
// Tạo bởi Cừu Đen
//
// Gmail: 0331999bbs@gmail.com
// Phone: 0347079556

package com.bbs.mr.beeshoe.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbs.mr.beeshoe.Activity.MainActivity;
import com.bbs.mr.beeshoe.Model.Model_Cart;
import com.bbs.mr.beeshoe.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

//Tạo bới Cừu Đen
//Email: 0331999bbs@gmail.com
//Phone: 0347079556
public class Adapter_Cart extends BaseAdapter {

    Context context;
    List<Model_Cart> model;
    public static boolean isOrder = true;

    public Adapter_Cart(Context context, List<Model_Cart> model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.size();
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
    public View getView(final int position, View view, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("#,###");
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
            holder = new ViewHolder();
            holder.name = view.findViewById(R.id.tvNameCart);
            holder.gia = view.findViewById(R.id.tvGiaCart);
            holder.cong = view.findViewById(R.id.btnCongSL);
            holder.tru = view.findViewById(R.id.btnTruSL);
            holder.sl = view.findViewById(R.id.tvSlCart);
            holder.img = view.findViewById(R.id.imgPreCart);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Model_Cart modelCart = model.get(position);
        holder.name.setText(String.valueOf(modelCart.getName()));
        holder.gia.setText(String.valueOf(df.format(modelCart.getGia())) + " VND");
        holder.sl.setText(String.valueOf(modelCart.getSl_cart()));
        if (!modelCart.getImg().isEmpty()) {
            Picasso.get().load(String.valueOf(modelCart.getImg())).into(holder.img);
        } else {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/other_image%2Ferror.png?alt=media&token=1ffa3591-6b4c-4dd7-8a47-ba4ac6605f8f").into(holder.img);
        }
        if (isOrder){
            holder.tru.setVisibility(View.GONE);
            holder.cong.setVisibility(View.GONE);
        } else {
            holder.cong.setVisibility(View.VISIBLE);
            if (model.get(position).getSl_cart() == 0) {
                holder.tru.setVisibility(View.GONE);
            } else {
                holder.tru.setVisibility(View.VISIBLE);
            }
        }
        holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = model.get(position).getSl_cart();
                count = count + 1;
                Log.e("cc         ", "onClick: " + count);
                model.get(position).setSl_cart(count);  //update your list like this
                MainActivity.isUpSL = true;
                notifyDataSetChanged();
            }
        });
        holder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = model.get(position).getSl_cart();
                count = count - 1;
                Log.e("cc         ", "onClick: " + count);
                model.get(position).setSl_cart(count);  //update your list like this
                MainActivity.isUpSL = true;
                notifyDataSetChanged();
            }
        });
        return view;
    }

    public class ViewHolder {
        ImageView img;
        TextView name, gia, sl;
        Button cong, tru;
    }
}
