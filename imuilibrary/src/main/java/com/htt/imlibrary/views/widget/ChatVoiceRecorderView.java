package com.htt.imlibrary.views.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htt.imlibrary.R;
import com.htt.imlibrary.modles.ChatVoiceRecorder;

/**
 * Created by Administrator on 2016/8/25.
 */
public class ChatVoiceRecorderView extends LinearLayout{
    private Context context;
    private ImageView ivVoiceRecorder;
    private TextView tvVoiceRecorderTip;
    private boolean isCancel=false;

    protected PowerManager.WakeLock wakeLock;
    private ChatVoiceRecorder voiceRecorder=null;
    private Handler recorderHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int audioVolume=msg.what;
            if(isCancel){
                ivVoiceRecorder.setImageResource(R.mipmap.voice_cancle);
            }else if(audioVolume<500){
                ivVoiceRecorder.setImageResource(R.mipmap.voice_1);
            }else if(audioVolume>=500&&audioVolume<2000){
                ivVoiceRecorder.setImageResource(R.mipmap.voice_2);
            }else if(audioVolume>=2000&&audioVolume<8000){
                ivVoiceRecorder.setImageResource(R.mipmap.voice_3);
            }else if(audioVolume>=8000){
                ivVoiceRecorder.setImageResource(R.mipmap.voice_4);
            }
        }
    };

    public ChatVoiceRecorderView(Context context) {
        super(context);
        initViews(context);
    }

    public ChatVoiceRecorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public ChatVoiceRecorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChatVoiceRecorderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context){
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.layout_voice_record, this,true);
        ivVoiceRecorder= (ImageView) this.findViewById(R.id.iv_record_icon);
        tvVoiceRecorderTip=(TextView)this.findViewById(R.id.tv_voice_tips);
        voiceRecorder=new ChatVoiceRecorder(recorderHandler);
        wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK, "demo");
    }

    public void setVoiceFileDir(String dir){
        voiceRecorder.setVoiceFileDir(dir);
    }

    /**
     * 长按说话按钮touch事件
     *
     * @param v
     * @param event
     */
    public boolean onPressToSpeakBtnTouch(View v, MotionEvent event, ChatVoiceRecorderCallback recorderCallback) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                try {
//                    if (ChatRowVoicePlayClickListener.isPlaying)
//                        ChatRowVoicePlayClickListener.currentPlayListener.stopPlayVoice();
                    v.setPressed(true);
                    isCancel=false;
                    startRecording();
                } catch (Exception e) {
                    v.setPressed(false);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() < 0) {
                    isCancel=true;
                    showReleaseToCancelHint();
                } else {
                    isCancel=false;
                    showMoveUpToCancelHint();
                }
                return true;
            case MotionEvent.ACTION_UP:
                v.setPressed(false);
                isCancel=false;
                if (event.getY() < 0) {
                    // discard the recorded audio.
                    discardRecording();
                } else {
                    // stop recording and send voice file
                    try {
                        int length = stopRecoding();
                        if (length > 0) {
                            if (recorderCallback != null) {
                                recorderCallback.onVoiceRecordComplete(getVoiceFilePath(), length);
                            }
                        } else {
                            Toast.makeText(context, "录制时间太短", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            default:
                discardRecording();
                return false;
        }
    }

    public void startRecording() {
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "没有SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            wakeLock.acquire();
            this.setVisibility(View.VISIBLE);
            tvVoiceRecorderTip.setText("手指上滑，取消发送");
            tvVoiceRecorderTip.setBackgroundColor(Color.TRANSPARENT);
            voiceRecorder.startRecording(context);
        } catch (Exception e) {
            e.printStackTrace();
            if (wakeLock.isHeld())
                wakeLock.release();
            if (voiceRecorder != null)
                voiceRecorder.discardRecording();
            this.setVisibility(View.INVISIBLE);
            Toast.makeText(context, "录制失败，请重试", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void showReleaseToCancelHint() {
        tvVoiceRecorderTip.setText("松开手指，取消发送");
        tvVoiceRecorderTip.setBackgroundResource(R.drawable.recording_text_tip_bg);
    }

    public void showMoveUpToCancelHint() {
        tvVoiceRecorderTip.setText("手指上滑，取消发送");
        tvVoiceRecorderTip.setBackgroundColor(Color.TRANSPARENT);
    }

    public void discardRecording() {
        if (wakeLock.isHeld())
            wakeLock.release();
        try {
            // 停止录音
            if (voiceRecorder.isRecording()) {
                voiceRecorder.discardRecording();
                this.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
        }
    }

    public int stopRecoding() {
        this.setVisibility(View.INVISIBLE);
        if (wakeLock.isHeld())
            wakeLock.release();
        return voiceRecorder.stopRecording();
    }

    public String getVoiceFilePath() {
        return voiceRecorder.getVoiceFilePath();
    }

    public String getVoiceFileName() {
        return voiceRecorder.getVoiceFileName();
    }

    public boolean isRecording() {
        return voiceRecorder.isRecording();
    }

    public interface ChatVoiceRecorderCallback {
        /**
         * 录音完毕
         *
         * @param voiceFilePath
         *            录音完毕后的文件路径
         * @param voiceTimeLength
         *            录音时长
         */
        void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength);
    }
}
