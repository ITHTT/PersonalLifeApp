package com.htt.personallife.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htt.personallife.R;
import com.htt.personallife.modles.event.EventObject;
import com.htt.personallife.modles.event.EventType;
import com.htt.personallife.views.widgets.LoadingLayout;
import com.htt.personallife.views.widgets.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by HTT on 2016/8/7.
 */
public abstract class BaseActivity extends AppCompatActivity{
    protected final String TAG=this.getClass().getSimpleName();

    protected TitleBar titleBar;
    protected LoadingLayout loadingLayout;
    protected ViewGroup contentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(!isCanceledEventBus()){
            EventBus.getDefault().register(this);
        }
        initBaseView();
        initViews();
        if(getIntent()!=null){
            getIntentData(getIntent());
        }
        handleInstanceState(savedInstanceState);
    }

    /**
     * 初始化默认的视图布局
     */
    protected void initBaseView(){
        setContentView(R.layout.activity_base_layout);
        titleBar= (TitleBar) this.findViewById(R.id.title_bar);
        loadingLayout=(LoadingLayout)this.findViewById(R.id.layout_loading);
        loadingLayout.showLoading();
        contentContainer= (ViewGroup) this.findViewById(R.id.layout_content_container);
        View view= LayoutInflater.from(this).inflate(getContentViewId(),null);
        if(view!=null){
            ButterKnife.bind(this,view);
            contentContainer.addView(view);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        saveInstanceState(outState);
    }

    /**
     * 页面布局的ID
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     * 是否取消EventBus
     * @return
     */
    protected abstract boolean isCanceledEventBus();

    /**
     * 获取Intent中的数据
     */
    protected abstract void getIntentData(Intent intent);

    /**
     * 保存异常时的数据
     */
    protected abstract void saveInstanceState(Bundle outState);

    /**
     * 处理异常时的情况
     */
    protected abstract void handleInstanceState(Bundle outState);

    /**
     * eventbus消息接收
     * @param action
     */
    protected abstract void notifyEvent(String action);

    /**
     * 带数据的eventbus接收
     * @param action
     * @param data
     */
    protected abstract void notifyEvent(String action, Bundle data);

    /**
     * 设置标题
     * @param title
     */
    protected void setTitle(String title){
        titleBar.setTitleBarTitle(title);
    }

    protected void hideTitleBar(){
        titleBar.setVisibility(View.GONE);
    }

    protected void showLoading(){
        loadingLayout.showLoading();
    }

    protected void showLoading(String msg){
        loadingLayout.showLoading(msg);
    }

    protected void hideLoading(){
        loadingLayout.hideLoad();
    }

    protected void setLoadEmptyInfo(){
        loadingLayout.setLoadResultInfo("暂无数据");
    }

    protected void setLoadFailedInfo(String tip,View.OnClickListener listener){
        loadingLayout.setLoadResultInfo(tip,listener);
    }

    protected void setLoadFailedInfo(String tip,String doTip,View.OnClickListener listener){
        loadingLayout.setLoadResultInfo(tip,doTip,listener);
    }

    protected void setLoadResultInfo(String tip,String doTip,View.OnClickListener listener){
        loadingLayout.setLoadResultInfo(tip,doTip,listener);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEventBus(EventObject eventObject)
    {
        if (eventObject != null)
        {
            String action = eventObject.getEventAction();
            if (action.equals(EventType.EVENT_USER_LOGIN))
            {
                String data = eventObject.getData().getString("user_info");
                if (!TextUtils.isEmpty(data))
                {
//                    UserInfoEntity userInfoEntity = JSONObject.parseObject(data, UserInfoEntity.class);
//                    if (userInfoEntity != null)
//                    {
//                        userLogin(userInfoEntity);
//                    }
                }
            }
            else
            {
                SparseArray<Class> receivers = eventObject.getReceivers();
                if (receivers.size() > 0)
                {
                    int size = receivers.size();
                    for (int i = 0; i < size; i++)
                    {
                        if (receivers.valueAt(i) == this.getClass())
                        {
                            notifyEvent(action, eventObject.getData());
                        }
                    }
                }
                else
                {
                    notifyEvent(action, eventObject.getData());
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEventBus(String action)
    {
        if (action.equals(EventType.EVENT_USER_EXIT))
        {
            //userExit();
        }
        else
        {
            notifyEvent(action);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!isCanceledEventBus()){
            EventBus.getDefault().unregister(this);
        }
    }
}
