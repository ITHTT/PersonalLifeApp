package com.htt.emoticonskeyboard.interfaces;

import android.view.View;
import android.view.ViewGroup;

import com.htt.emoticonskeyboard.data.PageEntity;


public interface PageViewInstantiateListener<T extends PageEntity> {

    View instantiateItem(ViewGroup container, int position, T pageEntity);
}
