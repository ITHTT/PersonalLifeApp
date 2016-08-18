package com.htt.personallife.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.htt.personallife.R;
import com.htt.personallife.app.AppConfigInfo;
import com.htt.personallife.app.BaseActivity;
import com.htt.personallife.views.multiphotopicker.ui.activitys.PhotoPickerActivity;
import com.htt.personallife.views.ucrop.UCrop;
import com.htt.personallife.views.ucrop.UCropActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
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
    @BindView(R.id.iv_user_header)
    protected ImageView ivUserHeader;

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
                        File outFile=new File(AppConfigInfo.APP_IMAGE_PATH,System.currentTimeMillis()+".jpg");
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
