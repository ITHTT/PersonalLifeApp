package com.htt.personallife.views.widgets.chatviews;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.htt.emoticonskeyboard.XhsEmoticonsKeyBoard;
import com.htt.emoticonskeyboard.data.EmoticonEntity;
import com.htt.emoticonskeyboard.interfaces.EmoticonClickListener;
import com.htt.emoticonskeyboard.widget.EmoticonsEditText;
import com.htt.emoticonskeyboard.widget.FuncLayout;
import com.htt.personallife.R;
import com.htt.personallife.app.BaseFragment;
import com.htt.personallife.views.widgets.chatviews.filter.EmojiFilter;
import com.htt.personallife.views.widgets.chatviews.modle.EmojiBean;
import com.htt.personallife.views.widgets.chatviews.utils.ChatCommonUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ChatListFragment extends BaseFragment implements FuncLayout.OnFuncKeyBoardListener{
    @BindView(R.id.keyboard)
    protected XhsEmoticonsKeyBoard keyBoard;
    @BindView(R.id.lv_chat)
    protected ListView lvChat;

    protected EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
            if(isDelBtn){
                ChatCommonUtils.delClick(keyBoard.getEtChat());
            }else{
                String content = null;
                if(o instanceof EmojiBean){
                    content = ((EmojiBean)o).emoji;
                } else if(o instanceof EmoticonEntity){
                    content = ((EmoticonEntity)o).getContent();
                }
                if(TextUtils.isEmpty(content)){
                    return;
                }
                int index = keyBoard.getEtChat().getSelectionStart();
                Editable editable = keyBoard.getEtChat().getText();
                editable.insert(index, content);
            }
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_chat_list;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        initEmoticonsKeyBoardBar();

    }

    private void initEmoticonsKeyBoardBar() {
        EmoticonsEditText emoticonsEditText=keyBoard.getEtChat();
        emoticonsEditText.addEmoticonFilter(new EmojiFilter());
//        emoticonsEditText.addEmoticonFilter(new XhsFilter());
        keyBoard.setAdapter(ChatCommonUtils.getCommonAdapter(baseActivity,emoticonClickListener));
        keyBoard.addOnFuncKeyBoardListener(this);
        //keyBoard.addFuncView(new SimpleUserDefAppsGridView(this));

        keyBoard.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                //scrollToBottom();
            }
        });
        keyBoard.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OnSendBtnClick(keyBoard.getEtChat().getText().toString());
                keyBoard.getEtChat().setText("");
            }
        });
//        keyBoard.getEmoticonsToolBarView().addFixedToolItemView(false, R.mipmap.icon_add, null, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(SimpleChatUserDefActivity.this, "ADD", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected boolean isCanceledEventBus() {
        return false;
    }

    @Override
    protected void notifyEvent(String action) {

    }

    @Override
    protected void notifyEvent(String action, Bundle data) {

    }

    @Override
    public void OnFuncPop(int height) {

    }

    @Override
    public void OnFuncClose() {

    }
}
