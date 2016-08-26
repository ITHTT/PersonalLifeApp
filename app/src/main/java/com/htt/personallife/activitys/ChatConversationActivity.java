package com.htt.personallife.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.htt.imlibrary.ChatConversationFragment;
import com.htt.imlibrary.views.widget.ChatExtendMenuView;
import com.htt.personallife.R;
import com.htt.personallife.app.AppConfigInfo;
import com.htt.personallife.app.BaseActivity;
import com.htt.personallife.views.multiphotopicker.ui.activitys.PhotoPickerActivity;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ChatConversationActivity extends BaseActivity implements ChatExtendMenuView.ChatExtendMenuItemClickListener{
    private ChatConversationFragment chatConversationFragment;
    private int[] extrendRes={com.htt.imlibrary.R.mipmap.icon_extra_picture, com.htt.imlibrary.R.mipmap.icon_extra_video};
    private String[] extrendNmaes={"图片","小视频"};
    @Override
    protected int getContentViewId() {
        return R.layout.activity_chat_list;
    }

    @Override
    protected void initViews(Bundle outState) {
        setTitle("聊天");
        chatConversationFragment=new ChatConversationFragment();
        Bundle data=new Bundle();
        data.putString("voice_file_dir", AppConfigInfo.APP_VOICE_PATH);
        data.putIntArray("extend_menu_icons", extrendRes);
        data.putStringArray("extend_menu_names", extrendNmaes);
        chatConversationFragment.setArguments(data);
        chatConversationFragment.setChatExtendMenuItemClickListener(this);
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

    @Override
    public void onClick(int itemId, View view) {
        Intent intent=null;
        switch (itemId){
            case R.mipmap.icon_extra_picture:
                intent=new Intent(this, PhotoPickerActivity.class);
                intent.putExtra("max_photo_counts", 9);
                startActivityForResult(intent, 0x0001);
                break;
            case R.mipmap.icon_extra_video:

                break;
        }

    }
}
