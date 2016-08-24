package com.htt.imlibrary.views.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htt.imlibrary.R;
import com.htt.imlibrary.interfaces.EmoticonClickListener;
import com.htt.imlibrary.modles.EmoticonEntity;
import com.htt.imlibrary.modles.EmoticonPageEntity;
import com.htt.imlibrary.utils.imageloader.ImageLoader;

import java.io.IOException;

public class BigEmoticonsAdapter extends EmoticonsAdapter<EmoticonEntity> {

    protected final double DEF_HEIGHTMAXTATIO = 1.6;

    public BigEmoticonsAdapter(Context context, EmoticonPageEntity emoticonPageEntity, EmoticonClickListener onEmoticonClickListener) {
        super(context, emoticonPageEntity, onEmoticonClickListener);
        this.mItemHeight = (int) context.getResources().getDimension(R.dimen.item_emoticon_size_big);
        this.mItemHeightMaxRatio = DEF_HEIGHTMAXTATIO;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_emoticon_big_item, null);
            viewHolder.rootView = convertView;
            viewHolder.ly_root = (LinearLayout) convertView.findViewById(R.id.ly_root);
            viewHolder.iv_emoticon = (ImageView) convertView.findViewById(R.id.iv_emoticon);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindView(position, viewHolder);
        updateUI(viewHolder, parent);
        return convertView;
    }

    protected void updateUI(ViewHolder viewHolder, ViewGroup parent) {
        if(mDefalutItemHeight != mItemHeight){
            viewHolder.iv_emoticon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mItemHeight));
        }
        mItemHeightMax = this.mItemHeightMax != 0 ? this.mItemHeightMax : (int) (mItemHeight * mItemHeightMaxRatio);
        mItemHeightMin = this.mItemHeightMin != 0 ? this.mItemHeightMin : mItemHeight;
        int realItemHeight = ((View) parent.getParent()).getMeasuredHeight() / mEmoticonPageEntity.getLine();
        realItemHeight = Math.min(realItemHeight, mItemHeightMax);
        realItemHeight = Math.max(realItemHeight, mItemHeightMin);
        viewHolder.ly_root.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, realItemHeight));
    }

    protected void bindView(int position, ViewHolder viewHolder) {
        final boolean isDelBtn = isDelBtn(position);
        final EmoticonEntity emoticonEntity = mData.get(position);
        if (isDelBtn) {
            viewHolder.iv_emoticon.setImageResource(R.mipmap.icon_del);
            viewHolder.iv_emoticon.setBackgroundResource(R.drawable.bg_emoticon);
        } else {
            if (emoticonEntity != null) {
                try {
                    viewHolder.iv_emoticon.setImageResource(emoticonEntity.getIconResource());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                viewHolder.iv_emoticon.setBackgroundResource(R.drawable.bg_emoticon);
            }
        }

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnEmoticonClickListener != null) {
                    mOnEmoticonClickListener.onEmoticonClick(emoticonEntity, 2, isDelBtn);
                }
            }
        });
    }

    public static class ViewHolder {
        public View rootView;
        public LinearLayout ly_root;
        public ImageView iv_emoticon;
        public TextView tv_content;
    }
}