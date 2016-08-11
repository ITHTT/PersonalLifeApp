package com.htt.personallife.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.htt.personallife.R;
import com.htt.personallife.app.BaseActivity;
import com.htt.personallife.views.widgets.MenuTabView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.tab_message)
    protected MenuTabView tabMessage;
    @BindView(R.id.tab_friends)
    protected MenuTabView tabFriends;
    @BindView(R.id.tab_find)
    protected MenuTabView tabFind;
    @BindView(R.id.tab_profile)
    protected MenuTabView tabProfile;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle outState) {
        titleBar.setTitleBarLeftIcon(0,null);
        setSelectedMenuTab(R.id.tab_message);
        titleBar.setRightMenuIcon(R.mipmap.icon_add,this);
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

    @OnClick({R.id.tab_message,R.id.tab_friends,R.id.tab_find,R.id.tab_profile})
    protected void onMenuItemClicked(View view){
        setSelectedMenuTab(view.getId());
    }

    protected void setSelectedMenuTab(int id){
        if(id==R.id.tab_message){
            tabMessage.setMenuSelected(true);
            tabFriends.setMenuSelected(false);
            tabFind.setMenuSelected(false);
            tabProfile.setMenuSelected(false);
        }else if(id==R.id.tab_friends){
            tabMessage.setMenuSelected(false);
            tabFriends.setMenuSelected(true);
            tabFind.setMenuSelected(false);
            tabProfile.setMenuSelected(false);
        }else if(id==R.id.tab_find){
            tabMessage.setMenuSelected(false);
            tabFriends.setMenuSelected(false);
            tabFind.setMenuSelected(true);
            tabProfile.setMenuSelected(false);
        }else if(id==R.id.tab_profile){
            tabMessage.setMenuSelected(false);
            tabFriends.setMenuSelected(false);
            tabFind.setMenuSelected(false);
            tabProfile.setMenuSelected(true);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
