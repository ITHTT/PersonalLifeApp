package com.htt.personallife.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htt.personallife.R;
import com.htt.personallife.modles.event.EventObject;
import com.htt.personallife.modles.event.EventType;
import com.htt.personallife.views.widgets.LoadingLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/11.
 */
public abstract class BaseFragment extends Fragment{
    protected final String TAG=this.getClass().getSimpleName();
    protected Activity baseActivity;
    protected ViewGroup contentContainer;
    protected LoadingLayout loadingLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity= (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(!isCanceledEventBus()){
            EventBus.getDefault().register(this);
        }
        View view=inflater.inflate(R.layout.fragment_base_layout,container,false);
        initDefaultView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view,savedInstanceState);
    }

    protected void initDefaultView(View view){
        contentContainer= (ViewGroup) view.findViewById(R.id.layout_content_container);
        loadingLayout= (LoadingLayout) view.findViewById(R.id.layout_loading);
        loadingLayout.hideLoad();
        View contentView=LayoutInflater.from(baseActivity).inflate(getContentViewId(),null);
        if(contentView!=null){
            ButterKnife.bind(this,contentView);
            contentContainer.addView(contentView);
        }
    }

    /**
     * 获取布局ID
     */
    protected abstract int getContentViewId();

    /**
     * 初始化控件
     */
    protected abstract void initViews(View view, Bundle savedInstanceState);

    /**
     * 是否取消EventBus
     * @return
     */
    protected abstract boolean isCanceledEventBus();

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
    public void onDestroy() {
        super.onDestroy();
        if(!isCanceledEventBus()){
            EventBus.getDefault().unregister(this);
        }
    }
}
