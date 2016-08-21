package com.htt.personallife.app;

import android.app.Application;

import com.htt.personallife.networks.HttpClientUtil;

/**
 * Created by Administrator on 2016/8/17.
 */
public class PersonalLifeApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AppConfigInfo.initAppConfigInfo(this);
        HttpClientUtil.initHttpClientUtil(this,AppConfigInfo.APP_HTTP_CACHE_PATH);
    }
}
