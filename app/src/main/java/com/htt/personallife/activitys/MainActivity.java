package com.htt.personallife.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.htt.personallife.R;
import com.htt.personallife.app.BaseActivity;
import com.htt.personallife.fragments.FindPagerFragment;
import com.htt.personallife.fragments.FriendsPagerFragment;
import com.htt.personallife.fragments.MessagePagerFragment;
import com.htt.personallife.fragments.PersonalPagerFragment;
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

    private int currentPager=-1;
    private MessagePagerFragment messagePagerFragment=null;
    private FriendsPagerFragment friendsPagerFragment=null;
    private FindPagerFragment findPagerFragment=null;
    private PersonalPagerFragment personalPagerFragment=null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle outState) {
        titleBar.setTitleBarLeftIcon(0,null);
        titleBar.setRightMenuIcon(R.mipmap.icon_add_item, this);
        setSelectedMenuTab(R.id.tab_message);
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
        outState.putInt("current_pager",currentPager);
    }

    @Override
    protected void handleInstanceState(Bundle outState) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        messagePagerFragment= (MessagePagerFragment) fragmentManager.findFragmentByTag("message_pager");
        friendsPagerFragment=(FriendsPagerFragment)fragmentManager.findFragmentByTag("friends_pager");
        findPagerFragment=(FindPagerFragment)fragmentManager.findFragmentByTag("find_pager");
        personalPagerFragment=(PersonalPagerFragment)fragmentManager.findFragmentByTag("personal_pager");
        int index=outState.getInt("current_pager",R.id.tab_message);
        currentPager=-1;
        setSelectedMenuTab(index);
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
        setTabPager(id);
    }

    protected void setTabPager(int id){
        FragmentTransaction transaction=null;
        if(id!=currentPager){
            //setSelectedTab(tab);
            transaction=this.getSupportFragmentManager().beginTransaction();
            hideFragments(currentPager,transaction);
        }else{
            return;
        }
        switch(id){
            case R.id.tab_message:
                if(messagePagerFragment==null){
                    messagePagerFragment=new MessagePagerFragment();
                    transaction.add(R.id.layout_container,messagePagerFragment,"message_pager");
                }else{
                    transaction.show(messagePagerFragment);
                }
                break;
            case R.id.tab_friends:
                if(friendsPagerFragment==null){
                    friendsPagerFragment=new FriendsPagerFragment();
                    transaction.add(R.id.layout_container,friendsPagerFragment,"friends_pager");
                }else{
                    transaction.show(friendsPagerFragment);
                }
                break;
            case R.id.tab_find:
                if(findPagerFragment==null){
                    findPagerFragment=new FindPagerFragment();
                    transaction.add(R.id.layout_container,findPagerFragment,"find_pager");
                }else{
                    transaction.show(findPagerFragment);
                }
                break;
            case R.id.tab_profile:
                if(personalPagerFragment==null){
                    personalPagerFragment=new PersonalPagerFragment();
                    transaction.add(R.id.layout_container,personalPagerFragment,"personal_pager");
                }else{
                    transaction.show(personalPagerFragment);
                }
                break;
        }
        currentPager=id;
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(int tab,FragmentTransaction transaction){
        if(tab==R.id.tab_message){
            if(messagePagerFragment!=null){
                transaction.hide(messagePagerFragment);
            }
        }else if(tab==R.id.tab_friends){
            if(friendsPagerFragment!=null){
                transaction.hide(friendsPagerFragment);
            }
        }else if(tab==R.id.tab_find){
            if(findPagerFragment!=null){
                transaction.hide(findPagerFragment);
            }
        }else if(tab==R.id.tab_profile){
            if(personalPagerFragment!=null){
                transaction.hide(personalPagerFragment);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
