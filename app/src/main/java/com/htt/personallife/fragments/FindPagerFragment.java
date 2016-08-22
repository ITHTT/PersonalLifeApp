package com.htt.personallife.fragments;

import android.os.Bundle;
import android.view.View;

import com.htt.personallife.R;
import com.htt.personallife.app.BaseFragment;

/**
 * Created by Administrator on 2016/8/22.
 */
public class FindPagerFragment extends BaseFragment{
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_find_pager;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }

    @Override
    protected boolean isCanceledEventBus() {
        return false;
    }

    @Override
    protected void notifyEvent(String action) {

    }

    @Override
    protected void notifyEvent(String action, Bundle data) {

    }
}
