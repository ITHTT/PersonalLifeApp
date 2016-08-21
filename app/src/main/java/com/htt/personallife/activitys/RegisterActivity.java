package com.htt.personallife.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.htt.personallife.R;
import com.htt.personallife.app.AppConfigInfo;
import com.htt.personallife.app.BaseActivity;
import com.htt.personallife.networks.HttpClientUtil;
import com.htt.personallife.networks.HttpUrls;
import com.htt.personallife.utils.KLog;
import com.htt.personallife.utils.ToastUtil;
import com.htt.personallife.views.dialogs.LoadingProgressDialog;
import com.htt.personallife.views.multiphotopicker.ui.activitys.PhotoPickerActivity;
import com.htt.personallife.views.ucrop.UCrop;
import com.htt.personallife.views.ucrop.UCropActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/8/10.
 */
public class RegisterActivity extends BaseActivity{
    @BindView(R.id.line_user_name)
    protected ImageView ivLineUserName;
    @BindView(R.id.line_user_phone)
    protected ImageView ivLineUserPhone;
    @BindView(R.id.line_user_password)
    protected ImageView ivLineUserPassword;
    @BindView(R.id.et_user_nick_name)
    protected EditText etUserName;
    @BindView(R.id.et_user_password)
    protected EditText etUserPassword;
    @BindView(R.id.et_user_phone)
    protected EditText etUserPhone;
    @BindView(R.id.iv_user_header)
    protected ImageView ivUserHeader;

    private String userHeaderUrl=null;
    private LoadingProgressDialog progressDialog=null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews(Bundle outState) {
        setTitle("手机注册");
    }

    @Override
    protected boolean isCanceledEventBus() {
        return false;
    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    protected void saveInstanceState(Bundle outState) {

    }

    @Override
    protected void handleInstanceState(Bundle outState) {

    }

    @Override
    protected void notifyEvent(String action) {

    }

    @Override
    protected void notifyEvent(String action, Bundle data) {

    }

    protected void showProgressDialog(String message){
        if(progressDialog==null){
            progressDialog=new LoadingProgressDialog(this);
        }
        progressDialog.setMessage(message);
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    protected void dismissProgressDialog(){
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @OnFocusChange({R.id.et_user_nick_name,R.id.et_user_phone,R.id.et_user_password})
    protected void onInputEditFocusChanged(View view){
        setInputEditLineSelectedState(view.getId());
    }

    private void setInputEditLineSelectedState(int id){
        if(id==R.id.et_user_nick_name){
            ivLineUserName.setSelected(true);
            ivLineUserPhone.setSelected(false);
            ivLineUserPassword.setSelected(false);
        }else if(id==R.id.et_user_phone){
            ivLineUserName.setSelected(false);
            ivLineUserPhone.setSelected(true);
            ivLineUserPassword.setSelected(false);
        }else if(id==R.id.et_user_password){
            ivLineUserName.setSelected(false);
            ivLineUserPhone.setSelected(false);
            ivLineUserPassword.setSelected(true);
        }
    }

    @OnCheckedChanged(R.id.cb_password_show)
    protected void OnShowPasswordChecked(CompoundButton view,boolean isChecked){
        etUserPassword.setTransformationMethod(!isChecked ? PasswordTransformationMethod.getInstance() : null);
        etUserPassword.setSelection(etUserPassword.getText().toString().length());
    }

    @OnClick(R.id.iv_user_header)
    protected void onClickSelectPhoto(View view){
        Intent intent=new Intent(this, PhotoPickerActivity.class);
        intent.putExtra("max_photo_counts", 1);
        startActivityForResult(intent, 0x0001);
    }

    @OnClick(R.id.bt_register)
    protected void register(View view){
        String userNickName=etUserName.getText().toString();
        String userPhone=etUserPhone.getText().toString();
        String userPassword=etUserPassword.getText().toString();
        if(TextUtils.isEmpty(userNickName)){
            ToastUtil.show(this,"请输入用户名");
            etUserName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(userPhone)){
            ToastUtil.show(this,"请输入手机号码");
            etUserPhone.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            ToastUtil.show(this,"请输入密码");
            etUserPassword.requestFocus();
            return;
        }

        String url= HttpUrls.HOST_URL+"user/register";
        Map<String,String> params=new HashMap<String,String>();
        params.put("user_name",userNickName);
        params.put("user_phone",userPhone);
        params.put("user_password",userPassword);
        HttpClientUtil.getHttpClientUtil().uploadFiles(TAG, url, null, params, new HttpClientUtil.ProgressResponseCallBack() {
            @Override
            public void loadingProgress(int progress) {
                KLog.i("progress:"+progress);
            }

            @Override
            public void onBefore(Request request) {
                showProgressDialog("注册中...");

            }

            @Override
            public void onError(Call call, Exception error) {
                KLog.e("error:"+error.getMessage());

            }

            @Override
            public void onSuccess(Call call, String response) {
                KLog.i("response:"+response);

            }

            @Override
            public void onFinish(Call call) {
                dismissProgressDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==0x0001){
                if(data!=null){
                    ArrayList<String> images = data.getStringArrayListExtra("selected_photos");
                    if(images!=null&&images.size()>0){
                        String imgUrl=images.get(0);
                        System.out.println("imgUrl:" + imgUrl);
                        //Glide.with(this).load(imgUrl).asBitmap().into(ivUserHeader);
                        userHeaderUrl=AppConfigInfo.APP_IMAGE_PATH+File.separator+System.currentTimeMillis()+".jpg";
                        File outFile=new File(userHeaderUrl);
                        Uri outUri=Uri.fromFile(outFile);
                        Uri inputUri=Uri.fromFile(new File(imgUrl));
                        Intent intent=new Intent(this, UCropActivity.class);
                        intent.putExtra(UCrop.EXTRA_INPUT_URI,inputUri);
                        intent.putExtra(UCrop.EXTRA_OUTPUT_URI,outUri);
                        intent.putExtra(UCrop.EXTRA_ASPECT_RATIO_X,1);
                        intent.putExtra(UCrop.EXTRA_ASPECT_RATIO_Y,1);
                        startActivityForResult(intent, 0x0002);
                    }
                }
            }else if(requestCode==0x0002){
                Uri outUri=data.getParcelableExtra(UCrop.EXTRA_OUTPUT_URI);
                Glide.with(this).load(outUri).asBitmap().into(ivUserHeader);
            }
        }
    }
}
