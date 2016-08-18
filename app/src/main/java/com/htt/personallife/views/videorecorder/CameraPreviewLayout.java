package com.htt.personallife.views.videorecorder;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/8/18.
 * 摄像头预览View
 */
public class CameraPreviewLayout extends FrameLayout{
    public CameraPreviewLayout(Context context) {
        super(context);
    }

    public CameraPreviewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreviewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraPreviewLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();
        int size;
        if(width == 0) {
            size = height;
        } else if(height == 0) {
            size = width;
        } else {
            size = Math.min(width, height);
        }

        int measure_spec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(measure_spec, measure_spec);
    }
}
