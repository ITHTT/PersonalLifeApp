package com.htt.personallife.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.htt.personallife.R;
import com.htt.personallife.app.BaseActivity;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnFocusChange;

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        setTitle("手机注册");
        hideLoading();
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

}
