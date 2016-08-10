package com.htt.personallife.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.htt.personallife.R;
import com.htt.personallife.app.BaseActivity;

import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/10.
 */
public class WelcomeActivity extends BaseActivity{

    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViews() {
        hideTitleBar();
        hideLoading();
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

    @OnClick(R.id.bt_register)
    protected void userRegister(View view){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
