package com.htt.personallife.views.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htt.personallife.R;
import com.htt.personallife.views.widgets.circularprogressview.CircularProgressView;

/**
 * Created by HTT on 2016/8/7.
 */
public class LoadingLayout extends LinearLayout{
    private CircularProgressView progressView;
    private TextView tvLoadMsg;
    private Button btResult;

    public LoadingLayout(Context context) {
        super(context);
        initViews(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.layout_loading_view,this,true);
        progressView= (CircularProgressView) this.findViewById(R.id.loading_progressview);
        tvLoadMsg=(TextView)this.findViewById(R.id.load_msg);
        btResult=(Button)this.findViewById(R.id.btn_doing);
    }

    private void setLoadingState(){
        if(getVisibility()!= View.VISIBLE){
            setVisibility(View.VISIBLE);
        }
        if(progressView.getVisibility()!=View.VISIBLE){
            progressView.setVisibility(View.VISIBLE);
        }
        if(tvLoadMsg.getVisibility()!=View.VISIBLE){
            tvLoadMsg.setVisibility(View.VISIBLE);
        }
        if(btResult.getVisibility()!=View.GONE){
            btResult.setVisibility(View.GONE);
        }
    }

    public void showLoading(){
        setLoadingState();
    }

    public void showLoading(String msg){
        setLoadingState();
        tvLoadMsg.setText(msg);
    }

    public void hideLoad(){
        if(getVisibility()!=View.GONE){
            setVisibility(View.GONE);
        }
    }

    public void setLoadResultInfo(String info){
        if(getVisibility()!= View.VISIBLE){
            setVisibility(View.VISIBLE);
        }
        if(progressView.getVisibility()!=View.GONE){
            progressView.setVisibility(View.GONE);
        }
        if(tvLoadMsg.getVisibility()!=View.VISIBLE){
            tvLoadMsg.setVisibility(View.VISIBLE);
        }
        if(btResult.getVisibility()!=View.GONE){
            btResult.setVisibility(View.GONE);
        }
        tvLoadMsg.setText(info);
    }

    public void setLoadResultInfo(String info,OnClickListener onClickListener){
        if(getVisibility()!= View.VISIBLE){
            setVisibility(View.VISIBLE);
        }
        if(progressView.getVisibility()!=View.GONE){
            progressView.setVisibility(View.GONE);
        }
        if(tvLoadMsg.getVisibility()!=View.VISIBLE){
            tvLoadMsg.setVisibility(View.VISIBLE);
        }
        if(btResult.getVisibility()!=View.VISIBLE){
            btResult.setVisibility(View.VISIBLE);
        }
        tvLoadMsg.setText(info);
        btResult.setOnClickListener(onClickListener);
    }

    public void setLoadResultInfo(String info,String resultTip,OnClickListener onClickListener){
        if(getVisibility()!= View.VISIBLE){
            setVisibility(View.VISIBLE);
        }
        if(progressView.getVisibility()!=View.GONE){
            progressView.setVisibility(View.GONE);
        }
        if(tvLoadMsg.getVisibility()!=View.VISIBLE){
            tvLoadMsg.setVisibility(View.VISIBLE);
        }
        if(btResult.getVisibility()!=View.VISIBLE){
            btResult.setVisibility(View.VISIBLE);
        }
        tvLoadMsg.setText(info);
        btResult.setText(resultTip);
        btResult.setOnClickListener(onClickListener);
    }
}
