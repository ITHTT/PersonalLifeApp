package com.htt.imlibrary.interfaces;

import android.view.ViewGroup;

import com.htt.imlibrary.views.adapter.EmoticonsAdapter;


public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
