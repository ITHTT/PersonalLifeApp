package com.htt.personallife.activitys;

import android.content.Intent;
import android.os.Bundle;

import com.htt.personallife.R;
import com.htt.personallife.app.BaseActivity;

/**
 * Created by Administrator on 2016/8/10.
 */
public class LoginActivity extends BaseActivity{
    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected boolean isCanceledEventBus() {
        return false;
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    protected void saveInstanceState(Bundle outState) {

    }

    @Override
    protected void handleInstanceState(Bundle outState) {

    }

    @Override
    protected void notifyEvent(String action) {

    }

    @Override
    protected void notifyEvent(String action, Bundle data) {

    }
}
