package com.wudebin.bicyclerental.rentalrecord;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wudebin.bicyclerental.R;
import com.wudebin.bicyclerental.adapter.FooterAdapter;
import com.wudebin.bicyclerental.constant.ActivityAction;

/**
 * Created by wudebin on 15-5-14.
 */
public class RentalRecordAdapter extends FooterAdapter {

    private Context context;
    public RentalRecordAdapter(Context context)
    {
        this.context=context;
    }

    @Override
    public boolean useFooter() {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rentalrecord_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindContentItemView(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(ActivityAction.BICYCLEDETAIL_ACTIVITY);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getContentItemCount() {
        return 10;
    }

    @Override
    public int getContentItemType(int position) {
        return 0;
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}