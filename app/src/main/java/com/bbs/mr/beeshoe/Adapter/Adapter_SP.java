package com.bbs.mr.beeshoe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        DecimalFormat df = new DecimalFormat("#");
        Model_SP model = list.get(i);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView name, gia;
        public ImageView thumbnail;
        public Button btn_add, btn_mua;
        public RatingBar rating;

        public ViewHolder(@NonNull View view) {
            super(view);
            itemView.setOnClickListener(this);
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
