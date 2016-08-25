package com.htt.personallife.activitys;

import android.content.Intent;
import android.os.Bundle;

import com.htt.imlibrary.ChatConversationFragment;
import com.htt.personallife.R;
import com.htt.personallife.app.BaseActivity;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ChatConversationActivity extends BaseActivity{
    private ChatConversationFragment chatConversationFragment;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_chat_list;
    }

    @Override
    protected void initViews(Bundle outState) {
        setTitle("聊天");
        chatConversationFragment=new ChatConversationFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_container,chatConversationFragment).commitAllowingStateLoss();
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
