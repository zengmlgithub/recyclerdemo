package com.sanyedu.recyclerviewdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.sanyedu.recyclerviewdemo.R;
import com.sanyedu.recyclerviewdemo.adapter.LoadMoreWrapperAdapter;
import com.sanyedu.recyclerviewdemo.listener.EndlessRecyclerOnScrollListener;
import com.sanyedu.recyclerviewdemo.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * RecyclerView下拉刷新、上拉加载更多
 * 使用封装好的LoadMoreWrapper类实现上拉加载更多
 * Created by yangle on 2017/10/16.
 */

public class LoadMoreWrapperActivity extends AppCompatActivity {

//    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;
    private List<String> dataList = new ArrayList<>();
    private LoadMoreWrapperAdapter loadMoreWrapperAdapter;
    private int curpageNum = 0;
    private int totalSize = 100; //totalsize也是变化的
    private static final int  EVERY_PAGE_COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        init();
    }

    private void init() {

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        recyclerView = findViewById(R.id.recycler_view);

        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));

        // 模拟获取数据
        loadMoreWrapperAdapter = new LoadMoreWrapperAdapter();

        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(loadMoreWrapper);

        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                resetPageInfo();
                // 延时1s关闭下拉刷新
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (dataList.size() < totalSize) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getMoreData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });

        initRecycleData();

    }

    private void resetPageInfo() {
        dataList.clear();
        curpageNum = 0;
        initRecycleData();
        loadMoreWrapper.notifyDataSetChanged();
    }

    private void initRecycleData() {
        //初始化时
        curpageNum ++;
        getMoreData();
        loadMoreWrapperAdapter.setDataList(dataList);
    }

    private void getMoreData() {
        totalSize = (curpageNum -1) * 10;
        int letter = 1;
        for (int i = 0; i < EVERY_PAGE_COUNT; i++) {
            dataList.add(String.valueOf(letter + (curpageNum -1) * 10));
            letter++;
        }

        curpageNum ++;

    }
}
