package com.bbs.mr.beeshoe.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.mr.beeshoe.Model.Model_SP;
import com.bbs.mr.beeshoe.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_SP extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Model_SP> list;
    private final LayoutInflater inflater;
    private TextView tvName, tvInfo, tvPrice;
    private RatingBar rtb;
    private Button btnAddCart, btnBuy;
    private ListView lvAllPic, lvCmt;
    private ImageView img_info;
    private String[] all_pic;
    private View v_color;
    Adapter_All_Pics adapter_all_pics;

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

    private void OnClickItem(Model_SP model) {

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sp);
        dialog.show();
        DecimalFormat df = new DecimalFormat("#,###");
        all_pic = model.getAll_pic().split(",");
        tvName = dialog.findViewById(R.id.tv_info_name_sp);
        tvInfo = dialog.findViewById(R.id.tv_info_sp);
        tvPrice = dialog.findViewById(R.id.tv_info_gia_sp);
        rtb = dialog.findViewById(R.id.rating_info);
        v_color = dialog.findViewById(R.id.v_color);
        btnAddCart = dialog.findViewById(R.id.btn_info_add_cart);
        btnBuy = dialog.findViewById(R.id.btn_info_buy);
        lvAllPic = dialog.findViewById(R.id.lv_info_all_pic);
        lvCmt = dialog.findViewById(R.id.lv_cmt);
        img_info = dialog.findViewById(R.id.img_info_sp);
        tvName.setText(model.getName());
        tvInfo.setText(model.getInfo());
        tvPrice.setText(df.format(model.getGia()));
        rtb.setRating(model.getRate());
        Picasso.get().load(String.valueOf(model.getThump())).into(img_info);

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

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Add cart", Toast.LENGTH_SHORT).show();
            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Buy", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void OnClickAdd() {
        Toast.makeText(context, "Them gio hang", Toast.LENGTH_SHORT).show();
    }

    private void OnClickBuy() {
        Toast.makeText(context, "Mua ngay .....", Toast.LENGTH_SHORT).show();
        // inflate menu
        /*PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();*/
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

        public TextView name, gia;
        public ImageView thumbnail;
        public Button btn_add, btn_mua;
        public RatingBar rating;


        public ItemViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.tv_name_sp);
            gia = view.findViewById(R.id.tv_gia_sp);
            thumbnail = view.findViewById(R.id.img_sp);
            btn_add = view.findViewById(R.id.btn_them_gio_hang);
            btn_mua = view.findViewById(R.id.btn_mua_ngay);
            rating = view.findViewById(R.id.rating);
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
        holder.gia.setText(String.valueOf(df.format(model.getGia())));
        holder.rating.setRating(model.getRate());
        Picasso.get().load(String.valueOf(model.getThump())).into(holder.thumbnail);
        holder.btn_mua.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                OnClickBuy();
            }
        });
        holder.btn_add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                OnClickAdd();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickItem(list.get(i));
            }
        });

    }

    /*public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name, gia;
        public ImageView thumbnail;
        public Button btn_add, btn_mua;
        public RatingBar rating;


        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.tv_name_sp);
            gia = view.findViewById(R.id.tv_gia_sp);
            thumbnail = view.findViewById(R.id.img_sp);
            btn_add = view.findViewById(R.id.btn_them_gio_hang);
            btn_mua = view.findViewById(R.id.btn_mua_ngay);
            rating = view.findViewById(R.id.rating);
        }

        @Override
        public void onClick(View v) {

        }
    }*/


    //Test start

    //Test end



}
