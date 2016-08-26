package com.htt.personallife.views.videorecorder;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;

import org.jivesoftware.smack.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 * 摄像头管理类
 */
public class CameraManager {
    /** 最大帧率 */
    public static final int MAX_FRAME_RATE = 25;
    /** 最小帧率 */
    public static final int MIN_FRAME_RATE = 15;

    /** 摄像头对象 */
    protected Camera camera;
    /** 摄像头参数 */
    protected Camera.Parameters mParameters = null;
    /** 摄像头支持的预览尺寸集合 */
    protected List<Camera.Size> mSupportedPreviewSizes;
    /** 画布 */
    protected SurfaceHolder mSurfaceHolder;

    /** 帧率 */
    protected int mFrameRate = 15;
    /** 摄像头类型（前置/后置），默认后置 */
    protected int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    /** 视频码率 */
    protected int mVideoBitrate = 2048;
    /** 状态标记 */
    protected boolean mPrepared, mStartPreview, mSurfaceCreated;


    /** 是否支持前置摄像头 */
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isSupportFrontCamera() {
        if (!DeviceUtils.hasGingerbread()) {
            return false;
        }
        int numberOfCameras = android.hardware.Camera.getNumberOfCameras();
        if (2 == numberOfCameras) {
            return true;
        }
        return false;
    }

    /** 切换前置/后置摄像头 */
    public void switchCamera(int cameraFacingFront) {
        switch (cameraFacingFront) {
            case Camera.CameraInfo.CAMERA_FACING_FRONT:
            case Camera.CameraInfo.CAMERA_FACING_BACK:
                mCameraId = cameraFacingFront;
                stopPreview();
                startPreview();
                break;
        }
    }

    /** 切换前置/后置摄像头 */
    public void switchCamera() {
        if (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            switchCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            switchCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
    }

    /**
     * 自动对焦
     *
     * @param cb
     * @return
     */
    public boolean autoFocus(Camera.AutoFocusCallback cb) {
        if (camera != null) {
            try {
                camera.cancelAutoFocus();

                if (mParameters != null) {
                    String mode = getAutoFocusMode();
                    if (StringUtils.isNotEmpty(mode)) {
                        mParameters.setFocusMode(mode);
                        camera.setParameters(mParameters);
                    }
                }
                camera.autoFocus(cb);
                return true;
            } catch (Exception e) {
//                if (mOnErrorListener != null) {
//                    mOnErrorListener.onVideoError(MEDIA_ERROR_CAMERA_AUTO_FOCUS, 0);
//                }
                if (e != null)
                    Log.e("Yixia", "autoFocus", e);
            }
        }
        return false;
    }

    /** 连续自动对焦 */
    private String getAutoFocusMode() {
        if (mParameters != null) {
            //持续对焦是指当场景发生变化时，相机会主动去调节焦距来达到被拍摄的物体始终是清晰的状态。
            List<String> focusModes = mParameters.getSupportedFocusModes();
            if ((Build.MODEL.startsWith("GT-I950") || Build.MODEL.endsWith("SCH-I959") || Build.MODEL.endsWith("MEIZU MX3")) && isSupported(focusModes, "continuous-picture")) {
                return "continuous-picture";
            } else if (isSupported(focusModes, "continuous-video")) {
                return "continuous-video";
            } else if (isSupported(focusModes, "auto")) {
                return "auto";
            }
        }
        return null;
    }

    /**
     * 手动对焦
     *
     * @param focusAreas 对焦区域
     * @return
     */
    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public boolean manualFocus(Camera.AutoFocusCallback cb, List<Camera.Area> focusAreas) {
        if (camera != null && focusAreas != null && mParameters != null && DeviceUtils.hasICS()) {
            try {
                camera.cancelAutoFocus();
                // getMaxNumFocusAreas检测设备是否支持
                if (mParameters.getMaxNumFocusAreas() > 0) {
                    // mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);//
                    // Macro(close-up) focus mode
                    mParameters.setFocusAreas(focusAreas);
                }

                if (mParameters.getMaxNumMeteringAreas() > 0)
                    mParameters.setMeteringAreas(focusAreas);

                mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
                camera.setParameters(mParameters);
                camera.autoFocus(cb);
                return true;
            } catch (Exception e) {
//                if (mOnErrorListener != null) {
//                    mOnErrorListener.onVideoError(MEDIA_ERROR_CAMERA_AUTO_FOCUS, 0);
//                }
                if (e != null)
                    Log.e("Yixia", "autoFocus", e);
            }
        }
        return false;
    }

    /**
     * 切换闪关灯，默认关闭
     */
    public boolean toggleFlashMode() {
        if (mParameters != null) {
            try {
                final String mode = mParameters.getFlashMode();
                if (TextUtils.isEmpty(mode) || Camera.Parameters.FLASH_MODE_OFF.equals(mode))
                    setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                else
                    setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                return true;
            } catch (Exception e) {
                Log.e("Yixia", "toggleFlashMode", e);
            }
        }
        return false;
    }

    /**
     * 设置闪光灯
     *
     * @param value
     * @see
     * @see
     */
    private boolean setFlashMode(String value) {
        if (mParameters != null && camera != null) {
            try {
                if (Camera.Parameters.FLASH_MODE_TORCH.equals(value) || Camera.Parameters.FLASH_MODE_OFF.equals(value)) {
                    mParameters.setFlashMode(value);
                    camera.setParameters(mParameters);
                }
                return true;
            } catch (Exception e) {
                Log.e("Yixia", "setFlashMode", e);
            }
        }
        return false;
    }

    /** 检测是否支持指定特性 */
    private boolean isSupported(List<String> list, String key) {
        return list != null && list.contains(key);
    }

    /**
     * 预处理一些拍摄参数
     * 注意：自动对焦参数cam_mode和cam-mode可能有些设备不支持，导致视频画面变形，需要判断一下，已知有"GT-N7100", "GT-I9308"会存在这个问题
     *
     */
    @SuppressWarnings("deprecation")
    protected void prepareCameraParaments() {
        if (mParameters == null)
            return;

        List<Integer> rates = mParameters.getSupportedPreviewFrameRates();
        if (rates != null) {
            if (rates.contains(MAX_FRAME_RATE)) {
                mFrameRate = MAX_FRAME_RATE;
            } else {
                Collections.sort(rates);
                for (int i = rates.size() - 1; i >= 0; i--) {
                    if (rates.get(i) <= MAX_FRAME_RATE) {
                        mFrameRate = rates.get(i);
                        break;
                    }
                }
            }
        }

        mParameters.setPreviewFrameRate(mFrameRate);
        // mParameters.setPreviewFpsRange(15 * 1000, 20 * 1000);
        mParameters.setPreviewSize(640, 480);// 3:2

        // 设置输出视频流尺寸，采样率
        mParameters.setPreviewFormat(ImageFormat.NV21);

        //设置自动连续对焦
        String mode = getAutoFocusMode();
        if (StringUtils.isNotEmpty(mode)) {
            mParameters.setFocusMode(mode);
        }

        //设置人像模式，用来拍摄人物相片，如证件照。数码相机会把光圈调到最大，做出浅景深的效果。而有些相机还会使用能够表现更强肤色效果的色调、对比度或柔化效果进行拍摄，以突出人像主体。
        //		if (mCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT && isSupported(mParameters.getSupportedSceneModes(), Camera.Parameters.SCENE_MODE_PORTRAIT))
        //			mParameters.setSceneMode(Camera.Parameters.SCENE_MODE_PORTRAIT);

        if (isSupported(mParameters.getSupportedWhiteBalance(), "auto"))
            mParameters.setWhiteBalance("auto");

        //是否支持视频防抖
        if ("true".equals(mParameters.get("video-stabilization-supported")))
            mParameters.set("video-stabilization", "true");

        //		mParameters.set("recording-hint", "false");
        //
        //		mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        if (!DeviceUtils.isDevice("GT-N7100", "GT-I9308", "GT-I9300")) {
            mParameters.set("cam_mode", 1);
            mParameters.set("cam-mode", 1);
        }
    }

    /** 开始预览 */
    public void startPreview() {
        if (mStartPreview || mSurfaceHolder == null || !mPrepared)
            return;
        else
            mStartPreview = true;

        try {

            if (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
                camera = Camera.open();
            else
                camera = Camera.open(mCameraId);

            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(mSurfaceHolder);
            } catch (IOException e) {
//                if (mOnErrorListener != null) {
//                    mOnErrorListener.onVideoError(MEDIA_ERROR_CAMERA_SET_PREVIEW_DISPLAY, 0);
//                }
                Log.e("Yixia", "setPreviewDisplay fail " + e.getMessage());
            }

            //设置摄像头参数
            mParameters = camera.getParameters();
            mSupportedPreviewSizes = mParameters.getSupportedPreviewSizes();//	获取支持的尺寸
            prepareCameraParaments();
            camera.setParameters(mParameters);
            setPreviewCallback();
            camera.startPreview();

            //onStartPreviewSuccess();
//            if (mOnPreparedListener != null)
//                mOnPreparedListener.onPrepared();
        } catch (Exception e) {
            e.printStackTrace();
//            if (mOnErrorListener != null) {
//                mOnErrorListener.onVideoError(MEDIA_ERROR_CAMERA_PREVIEW, 0);
//            }
            Log.e("Yixia", "startPreview fail :" + e.getMessage());
        }
    }

    /** 设置回调 */
    protected void setPreviewCallback() {
        Camera.Size size = mParameters.getPreviewSize();
        if (size != null) {
            PixelFormat pf = new PixelFormat();
            PixelFormat.getPixelFormatInfo(mParameters.getPreviewFormat(), pf);
            int buffSize = size.width * size.height * pf.bitsPerPixel / 8;
            try {
                camera.addCallbackBuffer(new byte[buffSize]);
                camera.addCallbackBuffer(new byte[buffSize]);
                camera.addCallbackBuffer(new byte[buffSize]);
                //camera.setPreviewCallbackWithBuffer(this);
            } catch (OutOfMemoryError e) {
                Log.e("Yixia", "startPreview...setPreviewCallback...", e);
            }
            Log.e("Yixia", "startPreview...setPreviewCallbackWithBuffer...width:" + size.width + " height:" + size.height);
        } else {
            //camera.setPreviewCallback(this);
        }
    }

    /** 停止预览 */
    public void stopPreview() {
        if (camera != null) {
            try {
                camera.stopPreview();
                camera.setPreviewCallback(null);
                // camera.lock();
                camera.release();
            } catch (Exception e) {
                Log.e("Yixia", "stopPreview...");
            }
            camera = null;
        }
        mStartPreview = false;
    }

    /** 释放资源 */
    public void release() {
        //stopAllRecord();
        // 停止视频预览
        stopPreview();
        // 停止音频录制
//        if (mAudioRecorder != null) {
//            mAudioRecorder.interrupt();
//            mAudioRecorder = null;
//        }

        mSurfaceHolder = null;
        mPrepared = false;
        mSurfaceCreated = false;
    }

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
