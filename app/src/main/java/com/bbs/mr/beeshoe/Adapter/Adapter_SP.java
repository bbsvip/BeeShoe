package com.bbs.mr.beeshoe.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.mr.beeshoe.Model.Model_SP;
import com.bbs.mr.beeshoe.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_SP extends RecyclerView.Adapter<Adapter_SP.ViewHolder> {

    private Context context;
    private List<Model_SP> list;
    private final LayoutInflater inflater;
    private TextView tvName, tvInfo, tvPrice;
    private RatingBar rtb;
    private Button btnAddCart, btnBuy;
    private ListView lvAllPic, lvCmt;
    private ImageView img_info;
    private String[] all_pic;
    Adapter_All_Pics adapter_all_pics;

    public Adapter_SP(Context context, List<Model_SP> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sp, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        DecimalFormat df = new DecimalFormat("#");
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

    private void OnClickItem(Model_SP model) {

        //Toast.makeText(context, "Click at "+id, Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sp);
        dialog.show();
        DecimalFormat df = new DecimalFormat("#");
        all_pic = model.getAll_pic().split(",");
        tvName = dialog.findViewById(R.id.tv_info_name_sp);
        tvInfo = dialog.findViewById(R.id.tv_info_sp);
        tvPrice = dialog.findViewById(R.id.tv_info_gia_sp);
        rtb = dialog.findViewById(R.id.rating_info);
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
        for (int i = 0; i < all_pic.length; i++) {
            Log.i("----------------", all_pic[i]);
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
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name, gia;
        public ImageView thumbnail;
        public Button btn_add, btn_mua;
        public RatingBar rating;

        public ViewHolder(@NonNull View view) {
            super(view);
            //itemView.setOnClickListener(this);
            name = view.findViewById(R.id.tv_name_sp);
            gia = view.findViewById(R.id.tv_gia_sp);
            thumbnail = view.findViewById(R.id.img_sp);
            btn_add = view.findViewById(R.id.btn_them_gio_hang);
            btn_mua = view.findViewById(R.id.btn_mua_ngay);
            rating = view.findViewById(R.id.rating);
            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "CCC", Toast.LENGTH_SHORT).show();

                }
            });*/
        }

        @Override
        public void onClick(View v) {

        }
    }
}
