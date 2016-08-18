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
import butterknife.OnClick;
import butterknife.OnFocusChange;

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle outState) {
        setTitle("用户登录");
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
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
