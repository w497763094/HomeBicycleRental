package com.wudebin.bicyclerental.rental;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.wudebin.bicyclerental.R;
import com.wudebin.bicyclerental.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wudebin on 15-5-7.
 */
public class RentalDetailActivity extends BaseActivity {
    private Toolbar mToolbar;
    private ViewPager viewPager;
    private List<View> list;
    private LayoutInflater LayoutInflater;
    private Button bookBicycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicycledetail);
        LayoutInflater=getLayoutInflater();
        init();
    }

    public void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.menu_rental);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bookBicycle= (Button) findViewById(R.id.book_bicycle);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        LayoutInflater lf = getLayoutInflater().from(this);
        list = new ArrayList<View>();
        View view = lf.inflate(R.layout.viewpager_item, null);
        View view2 = lf.inflate(R.layout.viewpager_item, null);
        View view3 = lf.inflate(R.layout.viewpager_item, null);
//            ImageView imageView = (ImageView) view.findViewById(R.id.viewpager_image);
//            imageView.setImageResource(R.drawable.bicycle);
        list.add(view);
        list.add(view2);
        list.add(view3);
        MyAdapter adapter = new MyAdapter();
        viewPager.setAdapter(adapter);
        bookBicycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bicycle_collect,menu);
        return super.onCreateOptionsMenu(menu);
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            ((ViewPager) container).removeView(list.get(position));
        }
    }
}
