package com.wudebin.bicyclerental.rentalrecord;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wudebin.bicyclerental.R;
import com.wudebin.bicyclerental.fragment.AbstractBaseFragment;
import com.wudebin.bicyclerental.rental.RentalRecyclerAdapter;

/**
 * Created by wudebin on 15-5-11.
 */
public class RentalRecordFragment extends AbstractBaseFragment {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private RentalRecordAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_rental,null);
        init(view);
        return view;
    }

    public void init(View v)
    {
        mAdapter=new RentalRecordAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mToolbar=(Toolbar)v.findViewById(R.id.toolbar);
        mToolbar.setTitle("我的租车");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_drawer);
        ((ActionBarActivity) getActivity()).setSupportActionBar(mToolbar);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView= (RecyclerView) v.findViewById(R.id.rental_recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.rental_swipe_container);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
