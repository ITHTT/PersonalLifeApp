package com.htt.emoticonskeyboard.interfaces;

public interface EmoticonClickListener<T> {

    void onEmoticonClick(T t, int actionType, boolean isDelBtn);
}
