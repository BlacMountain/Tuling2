package com.example.a67342.tuling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 67342 on 2017/7/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<ListData> lists;
    private Context context;
    private RelativeLayout layout;
    public RecyclerAdapter(List<ListData> lists,
                       Context context){
        this.lists = lists;
        this.context = context;
        layout = new RelativeLayout(context);
    }
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh;
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
            vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        Log.i("Mainactivity", "onBindViewHolder: "+lists.get(position).getSend());
//        if (lists.get(position).getSend() % 5 == 0)//可以设置每五次显示一次时间
            holder.tv_time.setText(lists.get(position).getmTime());
        if (position % 2 == 0){
            holder.layout_right.setVisibility(View.INVISIBLE);
            holder.layout_left.setVisibility(View.VISIBLE);
            holder.tv_left.setText(lists.get(position).getContent());
        }else {
            holder.layout_left.setVisibility(View.INVISIBLE);
            holder.layout_right.setVisibility(View.VISIBLE);
            holder.tv_right.setText(lists.get(position).getContent());;
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_left;
        public TextView tv_right;
        public RelativeLayout layout_left;
        public RelativeLayout layout_right;
        public TextView tv_time;
        public ViewHolder(View view) {
            super(view);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            layout_right = (RelativeLayout) view.findViewById(R.id.Layout_right);
            layout_left = (RelativeLayout) view.findViewById(R.id.Layout_left);
            tv_left = (TextView) view.findViewById(R.id.tv_left);
            tv_right = (TextView) view.findViewById(R.id.tv_right);
        }
    }
}