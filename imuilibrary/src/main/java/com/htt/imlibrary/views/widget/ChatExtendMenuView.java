package com.htt.imlibrary.views.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.htt.imlibrary.R;
import com.htt.imlibrary.utils.EmoticonsKeyboardUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 * 消息输入扩展项布局视图
 */
public class ChatExtendMenuView extends GridView {
    private Context context=null;
    private List<ChatMenuItemModel> itemModels = null;

    public ChatExtendMenuView(Context context) {
        super(context);
        initViews(context,null);
    }

    public ChatExtendMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context,attrs);
    }

    public ChatExtendMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChatExtendMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs){
        this.context=context;
        itemModels=new ArrayList<ChatMenuItemModel>();
        int numColumns=4;
        if(attrs!=null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChatExtendMenuView);
            numColumns = ta.getInt(R.styleable.ChatExtendMenuView_numColumns, 4);
            ta.recycle();
        }
        setNumColumns(numColumns);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        setGravity(Gravity.CENTER_VERTICAL);
        setVerticalSpacing(EmoticonsKeyboardUtils.dip2px(context, 8));
        this.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    }

    public void initWidgets(){
        setAdapter(new ItemAdapter(context,itemModels));
    }

    /**
     * 注册menu item
     *
     * @param name
     *            item名字
     * @param drawableRes
     *            item背景
     * @param itemId
     *             id
     * @param listener
     *            item点击事件
     */
    public void registerMenuItem(String name, int drawableRes, int itemId, ChatExtendMenuItemClickListener listener) {
        ChatMenuItemModel item = new ChatMenuItemModel();
        item.name = name;
        item.image = drawableRes;
        item.id = itemId;
        item.clickListener = listener;
        itemModels.add(item);
    }

    /**
     * 注册menu item
     *
     * @param nameRes
     *            item名字的resource id
     * @param drawableRes
     *            item背景
     * @param itemId
     *             id
     * @param listener
     *            item点击事件
     */
    public void registerMenuItem(int nameRes, int drawableRes, int itemId, ChatExtendMenuItemClickListener listener) {
        registerMenuItem(context.getString(nameRes), drawableRes, itemId, listener);
    }

    public interface ChatExtendMenuItemClickListener{
        void onClick(int itemId, View view);
    }

    public static final class ItemAdapter extends BaseAdapter {
        private List<ChatMenuItemModel>items;
        private Context context;

        public ItemAdapter(Context context, List<ChatMenuItemModel> objects) {
            this.items=objects;
            this.context = context;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(convertView == null){
                convertView=LayoutInflater.from(context).inflate(R.layout.layout_chat_extend_menu_item,parent,false);
                viewHolder=new ViewHolder();
                viewHolder.ivIcon=(ImageView)convertView.findViewById(R.id.image);
                viewHolder.tvTitle=(TextView)convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)convertView.getTag();
            }
            ChatMenuItemModel item=items.get(position);
            viewHolder.tvTitle.setText(item.name);
            viewHolder.ivIcon.setImageResource(item.image);
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatMenuItemModel itemModel = (ChatMenuItemModel)getItem(position);
                    if (itemModel.clickListener != null) {
                       itemModel.clickListener.onClick(itemModel.id, v);
                    }
                }
            });
            return convertView;
        }

        public static final class ViewHolder{
            TextView tvTitle;
            ImageView ivIcon;
        }
    }


    public static final class ChatMenuItemModel{
        String name;
        int image;
        int id;
        ChatExtendMenuItemClickListener clickListener;
    }

}
