package com.htt.imlibrary.interfaces;

import android.view.View;
import android.view.ViewGroup;

import com.htt.imlibrary.modles.PageEntity;


public interface PageViewInstantiateListener<T extends PageEntity> {

    View instantiateItem(ViewGroup container, int position, T pageEntity);
}
