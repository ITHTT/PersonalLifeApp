package com.htt.personallife.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.htt.personallife.R;
import com.htt.personallife.app.BaseFragment;
import com.htt.personallife.modles.MessageRecordEntity;
import com.htt.personallife.utils.CommonUtils;
import com.htt.personallife.views.adapters.MessageRecordAdapter;
import com.htt.personallife.views.widgets.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MessagePagerFragment extends BaseFragment{
    @BindView(R.id.message_recyclerview)
    protected RecyclerView recyclerView;

    private List<MessageRecordEntity> messageRecordEntityList=null;
    private MessageRecordAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_message_pager;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(baseActivity));
        getMessageRecords();
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

    private void getMessageRecords(){
        messageRecordEntityList=new ArrayList<>(10);
        for(int i=0;i<10;i++){
            MessageRecordEntity message=new MessageRecordEntity();
            message.setMessageBrief("您有新的消息，请注意查收");
            message.setMessageTitle("新消息");
            messageRecordEntityList.add(message);
        }
        adapter=new MessageRecordAdapter(messageRecordEntityList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerViewDivider(baseActivity,LinearLayoutManager.HORIZONTAL, CommonUtils.dip2px(baseActivity,1), Color.parseColor("#e0e0e0")));
    }
}
