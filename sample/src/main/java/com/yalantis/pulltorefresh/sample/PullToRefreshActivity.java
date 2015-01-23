package com.yalantis.pulltorefresh.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yalantis.pulltorefresh.library.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PullToRefreshActivity extends ActionBarActivity {

    public static final int REFRESH_DELAY = 2000;

    private PullToRefreshView mPullToRefreshView;

    private List<Map<String, Integer>> sampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);

        Map<String, Integer> map;
        sampleList = new ArrayList<>();

        int[] icons = {
                R.drawable.icon_1,
                R.drawable.icon_2,
                R.drawable.icon_3};

        int[] colors = {
                R.color.saffron,
                R.color.eggplant,
                R.color.sienna};

        for (int i = 0; i < icons.length; i++) {
            map = new HashMap<>();
            map.put(SampleHolder.KEY_ICON, icons[i]);
            map.put(SampleHolder.KEY_COLOR, colors[i]);
            sampleList.add(map);
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new SampleAdapter());

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
    }

    private class SampleHolder extends RecyclerView.ViewHolder {

        public static final String KEY_ICON = "icon";
        public static final String KEY_COLOR = "color";

        private View mViewItem;
        private ImageView mImageViewIcon;

        private Map<String, Integer> mItem;

        public SampleHolder(View itemView) {
            super(itemView);
            mViewItem = itemView;
            mImageViewIcon = (ImageView) itemView.findViewById(R.id.image_view_icon);
        }

        public void bindItem(Map<String, Integer> item) {
            mItem = item;
            mImageViewIcon.setImageResource(mItem.get(KEY_ICON));
            mViewItem.setBackgroundResource(mItem.get(KEY_COLOR));
        }
    }

    class SampleAdapter extends RecyclerView.Adapter<SampleHolder> {

        @Override
        public SampleHolder onCreateViewHolder(ViewGroup parent, int pos) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new SampleHolder(view);
        }

        @Override
        public void onBindViewHolder(SampleHolder viewHolder, int pos) {
            Map<String, Integer> item = sampleList.get(pos);
            viewHolder.bindItem(item);
        }

        @Override
        public int getItemCount() {
            return sampleList.size();
        }
    }

}
