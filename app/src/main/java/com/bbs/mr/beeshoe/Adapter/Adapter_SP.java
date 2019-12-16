// Copyright (c) 2019.
// Tạo bởi Cừu Đen
//
// Gmail: 0331999bbs@gmail.com
// Phone: 0347079556

package com.bbs.mr.beeshoe.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.mr.beeshoe.Activity.MainActivity;
import com.bbs.mr.beeshoe.Model.Model_SP;
import com.bbs.mr.beeshoe.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_SP extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Model_SP> list;
    private final LayoutInflater inflater;
    private TextView tvName, tvInfo, tvPrice, tvGiaGoc;
    private RatingBar rtb;
    private Button btnAddCart, btnBack;
    private ListView lvAllPic, lvCmt;
    private ImageView img_info;
    private String[] all_pic;
    private View v_color;
    private ScrollView scv;
    Adapter_All_Pics adapter_all_pics;
    private ProgressBar prg;
    String urlImg;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public Adapter_SP(Context context, List<Model_SP> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        if (i == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sp, parent, false);
            return new ItemViewHolder(itemView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, i);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, i);
        }
    }

    private void OnClickItem(final Model_SP model) {

        final Dialog dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sp);
        dialog.show();
        DecimalFormat df = new DecimalFormat("#,###");
        prg = dialog.findViewById(R.id.prgDialog);
        all_pic = model.getAll_pic().split(",");
        tvName = dialog.findViewById(R.id.tv_info_name_sp);
        tvInfo = dialog.findViewById(R.id.tv_info_sp);
        tvPrice = dialog.findViewById(R.id.tv_info_gia_sp);
        tvGiaGoc = dialog.findViewById(R.id.tv_gia_goc);
        tvGiaGoc.setPaintFlags(tvGiaGoc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        rtb = dialog.findViewById(R.id.rating_info);
        v_color = dialog.findViewById(R.id.v_color);
        scv = dialog.findViewById(R.id.scvDialog);
        scv.fullScroll(ScrollView.FOCUS_UP);
        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                prg.setVisibility(View.VISIBLE);
                scv.setVisibility(View.INVISIBLE);
            }

            public void onFinish() {
                prg.setVisibility(View.GONE);
                scv.setVisibility(View.VISIBLE);
            }
        }.start();
        btnBack = dialog.findViewById(R.id.btnBackInfoSp);
        btnAddCart = dialog.findViewById(R.id.btn_info_add_cart);
        lvAllPic = dialog.findViewById(R.id.lv_info_all_pic);
        lvCmt = dialog.findViewById(R.id.lv_cmt);
        img_info = dialog.findViewById(R.id.img_info_sp);
        tvName.setText(model.getName());
        tvInfo.setText(model.getInfo());
        tvPrice.setText(String.valueOf(df.format(model.getGia()))+" VND");
        tvGiaGoc.setText(String.valueOf(df.format(model.getGia()+((model.getGia()*50f)/100f))));
        rtb.setRating(model.getRate());
        if(!model.getThump().isEmpty()){
            Picasso.get().load(String.valueOf(model.getThump())).into(img_info);
            urlImg = "";
            urlImg = model.getThump();
        } else {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/other_image%2Ferror.png?alt=media&token=1ffa3591-6b4c-4dd7-8a47-ba4ac6605f8f").into(img_info);
            urlImg = "https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/other_image%2Ferror.png?alt=media&token=1ffa3591-6b4c-4dd7-8a47-ba4ac6605f8f";
        }

        if (model.getColor() == 1) {
            v_color.setBackgroundColor(Color.BLACK);
        }
        if (model.getColor() == 2) {
            v_color.setBackgroundColor(Color.WHITE);
        }
        if (model.getColor() == 3) {
            v_color.setBackgroundColor(Color.RED);
        }
        if (model.getColor() == 4) {
            v_color.setBackgroundColor(Color.parseColor("#FF9900"));
        }
        if (model.getColor() == 5) {
            v_color.setBackgroundColor(Color.parseColor("#FF33FF"));
        }
        if (model.getColor() == 6) {
            v_color.setBackgroundColor(Color.parseColor("#000044"));
        }
        if (model.getColor() == 7) {
            v_color.setBackgroundColor(Color.parseColor("#550000"));
        }
        if (model.getColor() == 8) {
            v_color.setBackgroundColor(Color.parseColor("#008080"));
        }
        adapter_all_pics = new Adapter_All_Pics(context, all_pic);
        lvAllPic.setAdapter(adapter_all_pics);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickAdd(urlImg,model.getName(),model.getGia());
            }
        });

    }

    private void OnClickAdd(String img,String name,double gia) {
        if(context instanceof MainActivity){
            ((MainActivity)context).AddCart(img,name,gia);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name, gia, tv_sale, gia_goc;
        public ImageView thumbnail;
        public Button btn_add;
        public RatingBar rating;


        public ItemViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.tv_name_sp);
            gia = view.findViewById(R.id.tv_gia_sp);
            thumbnail = view.findViewById(R.id.img_sp);
            btn_add = view.findViewById(R.id.btn_them_gio_hang);
            rating = view.findViewById(R.id.rating);
            tv_sale = view.findViewById(R.id.tv_sale);
            gia_goc = view.findViewById(R.id.tv_gia_goc_sp);
            gia_goc.setPaintFlags(gia_goc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.prb);
        }
    }
    private void showLoadingView(LoadingViewHolder holder, int i) {
        //ProgressBar would be displayed

    }
    private void populateItemRows(ItemViewHolder holder, final int i) {

        DecimalFormat df = new DecimalFormat("#,###");
        final Model_SP model = list.get(i);
        holder.name.setText(model.getName());
        holder.gia.setText(String.valueOf(df.format(model.getGia()))+" VND");
        holder.rating.setRating(model.getRate());
        holder.tv_sale.setText("50");
        holder.gia_goc.setText(String.valueOf(df.format(model.getGia()+((model.getGia()*50f)/100f))));
        if(!model.getThump().isEmpty()){
            Picasso.get().load(String.valueOf(model.getThump())).into(holder.thumbnail);
            urlImg = "";
            urlImg = model.getThump();
            holder.thumbnail.setMaxWidth(200);
        } else {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/other_image%2Ferror.png?alt=media&token=1ffa3591-6b4c-4dd7-8a47-ba4ac6605f8f").into(holder.thumbnail);
            urlImg = "https://firebasestorage.googleapis.com/v0/b/datn-4ec75.appspot.com/o/other_image%2Ferror.png?alt=media&token=1ffa3591-6b4c-4dd7-8a47-ba4ac6605f8f";
            holder.thumbnail.setMaxWidth(200);
        }

        holder.btn_add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                OnClickAdd(urlImg,model.getName(),model.getGia());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickItem(list.get(i));
            }
        });

    }
}
