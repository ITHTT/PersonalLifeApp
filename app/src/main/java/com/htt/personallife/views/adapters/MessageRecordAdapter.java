package com.htt.personallife.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.htt.personallife.R;
import com.htt.personallife.activitys.ChatListActivity;
import com.htt.personallife.modles.MessageRecordEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MessageRecordAdapter extends RecyclerView.Adapter<MessageRecordAdapter.MessageRecordViewHolder>{
    private List<MessageRecordEntity> messageRecordEntityList=null;

    public MessageRecordAdapter(List<MessageRecordEntity> messageRecordEntityList) {
        this.messageRecordEntityList = messageRecordEntityList;
    }

    @Override
    public MessageRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_record_item,parent,false);
        return new MessageRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageRecordViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=holder.itemView.getContext();
                Intent intent=new Intent(context, ChatListActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageRecordEntityList.size();
    }

    public static final class MessageRecordViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_message_icon)
        ImageView ivMessageIcon;
        @BindView(R.id.tv_message_count)
        TextView tvMessageCount;
        @BindView(R.id.tv_message_title)
        TextView tvMessageTitle;
        @BindView(R.id.tv_message_brief)
        TextView tvMessageBrief;
        @BindView(R.id.tv_message_update_time)
        TextView tvMessageUpdateTime;
        @BindView(R.id.tv_message_top)
        TextView tvMessageTop;
        @BindView(R.id.tv_message_read_state)
        TextView tvMessageReadState;
        @BindView(R.id.tv_message_delete)
        TextView tvMessageDelete;

        public MessageRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
