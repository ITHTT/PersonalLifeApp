package com.htt.personallife.views.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htt.personallife.R;

/**
 * Created by Administrator on 2016/8/11.
 */
public class MenuTabView extends LinearLayout{
    private TextView tvMsg;
    private TextView tvTitle;
    private ImageView ivIcon;
    protected int normalColor;
    protected int selectedColor;

    public MenuTabView(Context context) {
        super(context);
        initViews(context,null);
    }

    public MenuTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context,attrs);
    }

    public MenuTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MenuTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context,attrs);
    }

    private void initViews(Context context,AttributeSet attrs){
        normalColor= context.getResources().getColor(R.color.colorTextColor);
        selectedColor=context.getResources().getColor(R.color.colorPrimary);
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_tab_item,this,true);
        tvMsg=(TextView)this.findViewById(R.id.tab_menu_message);
        tvTitle= (TextView) this.findViewById(R.id.tab_menu_title);
        ivIcon=(ImageView)this.findViewById(R.id.tab_menu_icon);
        if(attrs!=null){
            TypedArray arr=context.obtainStyledAttributes(attrs, R.styleable.MenuTabView);
            int iconRes=arr.getResourceId(R.styleable.MenuTabView_tab_icon,-1);
            if(iconRes>=0){
                ivIcon.setImageResource(iconRes);
            }
            String title=arr.getString(R.styleable.MenuTabView_tab_name);
            if(!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            }
            arr.recycle();
        }
    }

    public void setMenuSelected(boolean isSelected){
        ivIcon.setSelected(isSelected);
        if(isSelected){
            tvTitle.setTextColor(selectedColor);
        }else{
            tvTitle.setTextColor(normalColor);
        }
    }


}
