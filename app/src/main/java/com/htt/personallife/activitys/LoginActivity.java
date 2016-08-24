package com.htt.personallife.activitys;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.htt.personallife.R;
import com.htt.personallife.app.BaseActivity;
import com.htt.personallife.networks.HttpClientUtil;
import com.htt.personallife.networks.HttpUrls;
import com.htt.personallife.utils.KLog;
import com.htt.personallife.utils.ToastUtil;
import com.htt.personallife.views.dialogs.LoadingProgressDialog;

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
public class LoginActivity extends BaseActivity{
    @BindView(R.id.line_user_phone)
    protected ImageView ivLineUserPhone;
    @BindView(R.id.line_user_password)
    protected ImageView ivLineUserPassword;
    @BindView(R.id.et_user_password)
    protected EditText etUserPassword;
    @BindView(R.id.et_user_phone)
    protected EditText etUserPhone;

    private LoadingProgressDialog progressDialog=null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle outState) {
        setTitle("用户登录");
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

    @OnFocusChange({R.id.et_user_phone,R.id.et_user_password})
    protected void onInputEditFocusChanged(View view){
        setInputEditLineSelectedState(view.getId());
    }

    private void setInputEditLineSelectedState(int id){
       if(id==R.id.et_user_phone){
            ivLineUserPhone.setSelected(true);
            ivLineUserPassword.setSelected(false);
        }else if(id==R.id.et_user_password){
            ivLineUserPhone.setSelected(false);
            ivLineUserPassword.setSelected(true);
        }
    }

    @OnCheckedChanged(R.id.cb_password_show)
    protected void OnShowPasswordChecked(CompoundButton view,boolean isChecked){
        etUserPassword.setTransformationMethod(!isChecked ? PasswordTransformationMethod.getInstance() : null);
        etUserPassword.setSelection(etUserPassword.getText().toString().length());
    }

    @OnClick(R.id.bt_login)
    protected void login(View view){
        String userPhone=etUserPhone.getText().toString();
        String userPassword=etUserPassword.getText().toString();
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
        String url= HttpUrls.HOST_URL+"user/login";
        Map<String,String> params=new HashMap<String,String>();
        params.put("user_phone",userPhone);
        params.put("user_password",userPassword);
        HttpClientUtil.getHttpClientUtil().sendPostRequest(TAG, url, params, new HttpClientUtil.StringResponseCallBack() {
            @Override
            public void onBefore(Request request) {
                showProgressDialog("登录中...");
            }

            @Override
            public void onError(Call call, Exception error) {
                KLog.e("error:"+error.getMessage());
            }

            @Override
            public void onSuccess(Call call, String response) {
                KLog.i("response:"+response);
                if(!TextUtils.isEmpty(response)){
                    JSONObject obj=JSONObject.parseObject(response);
                    int code=obj.getIntValue("code");
                    String message=obj.getString("message");
                    ToastUtil.show(LoginActivity.this,message);
                    if(code==200){
                        JSONObject dataObj=obj.getJSONObject("data");
                        if(dataObj!=null&&!dataObj.isEmpty()){
                            String token=dataObj.getString("token");
                            KLog.i("token:"+token);
                        }
                    }
                }
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFinish(Call call) {
                dismissProgressDialog();

            }
        });
    }

}
