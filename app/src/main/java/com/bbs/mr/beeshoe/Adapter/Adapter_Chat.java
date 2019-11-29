package com.bbs.mr.beeshoe.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bbs.mr.beeshoe.Model.Model_Chat;
import com.bbs.mr.beeshoe.R;

import java.util.ArrayList;

public class Adapter_Chat extends BaseAdapter {
    private Context mContext;
    private ArrayList<Model_Chat> model;



    public Adapter_Chat(Context context, ArrayList<Model_Chat> messages) {
        super();
        this.mContext = context;
        this.model = messages;
    }
    @Override
    public int getCount() {
        return model.size();
    }
    @Override
    public Object getItem(int position) {
        return model.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Model_Chat model = (Model_Chat) this.getItem(position);
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_chat, parent, false);
            holder.admin =  convertView.findViewById(R.id.tvAdminMess);
            holder.user = convertView.findViewById(R.id.tvUserMess);
            holder.layoutAdmin = convertView.findViewById(R.id.adminContainer);
            holder.layoutUser = convertView.findViewById(R.id.userContainer);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        if (model.isMine()){
            holder.layoutAdmin.setVisibility(View.GONE);
            holder.layoutUser.setVisibility(View.VISIBLE);
            holder.user.setText(model.getMessage());
        } else {
            holder.layoutUser.setVisibility(View.GONE);
            holder.layoutAdmin.setVisibility(View.VISIBLE);
            holder.admin.setText(model.getMessage());
        }

        return convertView;
    }
    private static class ViewHolder
    {
        TextView admin,user;
        LinearLayout layoutAdmin,layoutUser;
    }

    @Override
    public long getItemId(int position) {
        //Unimplemented, because we aren't using Sqlite.
        return position;
    }
}
