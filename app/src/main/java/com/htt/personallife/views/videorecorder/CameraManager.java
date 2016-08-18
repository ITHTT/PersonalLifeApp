package com.htt.personallife.views.videorecorder;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.Camera;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/8/18.
 * 摄像头管理类
 */
public class CameraManager {

    private Camera camera=null;

    /**
     * 打开摄像头
     */
    public void openCamera(){
        try {
            camera = Camera.open();
        }catch(Exception e){
            if(camera!=null){
                camera.release();
                camera=null;
            }
        }
    }

    @TargetApi(14)
    private  boolean setFocusArea14(Camera.Parameters params, Rect rect) {
        int focus_area_count_max = params.getMaxNumFocusAreas();
        if(focus_area_count_max < 1) {
            return false;
        } else {
            params.setFocusAreas(Arrays.asList(new Camera.Area[]{new Camera.Area(rect, 1)}));
            return true;
        }
    }

}
