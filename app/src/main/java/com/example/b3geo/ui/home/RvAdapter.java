package com.example.b3geo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.b3geo.R;

import java.util.ArrayList;


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder>{

    Context context;
    ArrayList<Rvdata> rvdatas;

    public RvAdapter(Context context,ArrayList<Rvdata> rvdatas){
        this.context = context;
        this.rvdatas = rvdatas;
    }

    View view;
    @Override
    public RvAdapter.RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        RvViewHolder rvViewHolder = new RvViewHolder(view);
        return rvViewHolder;
    }

    @Override
    public void onBindViewHolder(RvAdapter.RvViewHolder holder, int position) {
        final Rvdata  rvdata = rvdatas.get(position);
        holder.itemName.setText(rvdata.getName());

    }

    @Override
    public int getItemCount() {
        return rvdatas.size();
    }

    public class RvViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView itemImg;
        LinearLayout llItem;

        public RvViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.station_name);
            itemImg = itemView.findViewById(R.id.item_img);
            llItem = itemView.findViewById(R.id.ll_item);
        }
    }
}