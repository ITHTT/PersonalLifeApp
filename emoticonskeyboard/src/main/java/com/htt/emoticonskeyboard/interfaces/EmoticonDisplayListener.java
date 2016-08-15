package com.htt.emoticonskeyboard.interfaces;

import android.view.ViewGroup;

import com.htt.emoticonskeyboard.adpater.EmoticonsAdapter;


public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
