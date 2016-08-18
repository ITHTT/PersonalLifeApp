package com.htt.personallife.views.videorecorder;

import android.hardware.Camera;

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

}
