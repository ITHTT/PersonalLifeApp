package com.htt.imlibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.htt.imlibrary.interfaces.EmoticonClickListener;
import com.htt.imlibrary.interfaces.EmoticonDisplayListener;
import com.htt.imlibrary.interfaces.PageViewInstantiateListener;
import com.htt.imlibrary.modles.DefaultEmojiData;
import com.htt.imlibrary.modles.EmoticonEntity;
import com.htt.imlibrary.modles.EmoticonPageEntity;
import com.htt.imlibrary.modles.EmoticonPageSetEntity;
import com.htt.imlibrary.modles.ExpressionFilter;
import com.htt.imlibrary.utils.imageloader.ImageBase;
import com.htt.imlibrary.views.ChatInputEditTextView;
import com.htt.imlibrary.views.adapter.EmoticonsAdapter;
import com.htt.imlibrary.views.adapter.PageSetAdapter;
import com.htt.imlibrary.views.widget.EmoticonPageView;
import com.htt.imlibrary.views.widget.EmoticonsEditText;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/24.
 */
public class ChatConversationFragment extends Fragment implements EmoticonClickListener {
    protected ChatInputEditTextView chatInputEditTextView=null;
    protected ListView chatListView;
    protected PageSetAdapter pageSetAdapter=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat_conversation,container,false);
        initViews(view);
        return view;
    }

    protected void initViews(View view){
        chatListView=(ListView)view.findViewById(R.id.lv_chat);
        chatInputEditTextView= (ChatInputEditTextView) view.findViewById(R.id.chat_input_edit);
        initChatInputEditTextView();
    }

    protected void initChatInputEditTextView(){
        final EmoticonsEditText emoticonsEditText=chatInputEditTextView.getEtChat();
        emoticonsEditText.addEmoticonFilter(new ExpressionFilter());
        pageSetAdapter=new PageSetAdapter();
        addDefaultEmoticonPageSetEntity(pageSetAdapter);
        chatInputEditTextView.setAdapter(pageSetAdapter);
        chatInputEditTextView.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OnSendBtnClick(keyBoard.getEtChat().getText().toString());
                emoticonsEditText.setText("");
            }
        });
    }

    protected void addDefaultEmoticonPageSetEntity(PageSetAdapter adapter){
        ArrayList<EmoticonEntity> emojiArray= (ArrayList<EmoticonEntity>) DefaultEmojiData.getDefaultEmoticons();
        EmoticonPageSetEntity.Builder emojiPageSetEntityBuilder
                = new EmoticonPageSetEntity.Builder()
                .setLine(4)
                .setRow(7)
                .setEmoticonList(emojiArray);

        final EmoticonDisplayListener emoticonDisplayListener=new EmoticonDisplayListener<EmoticonEntity>() {
            @Override
            public void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, final EmoticonEntity o, final boolean isDelBtn) {
                if (o == null && !isDelBtn) {
                    return;
                }
                viewHolder.ly_root.setBackgroundResource(R.drawable.bg_emoticon);
                if (isDelBtn) {
                    viewHolder.iv_emoticon.setImageResource(R.mipmap.icon_del);
                } else {
                    viewHolder.iv_emoticon.setImageResource(o.getIconResource());
                }
                viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onEmoticonClick(o, 1, isDelBtn);
                    }
                });
            }
        };
        emojiPageSetEntityBuilder.setIPageViewInstantiateItem(new PageViewInstantiateListener<EmoticonPageEntity>() {
            @Override
            public View instantiateItem(ViewGroup container, int position, EmoticonPageEntity pageEntity) {
                if (pageEntity.getRootView() == null) {
                    EmoticonPageView pageView = new EmoticonPageView(container.getContext());
                    pageView.setNumColumns(pageEntity.getRow());
                    pageEntity.setRootView(pageView);
                    try {
                        EmoticonsAdapter adapter = new EmoticonsAdapter(getContext(),pageEntity,null);
                        if (emoticonDisplayListener != null) {
                            adapter.setOnDisPlayListener(emoticonDisplayListener);
                        }
                        pageView.getEmoticonsGridView().setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return pageEntity.getRootView();
            }
        });
        emojiPageSetEntityBuilder.setShowDelBtn(EmoticonPageEntity.DelBtnStatus.LAST)
                .setIconUri(ImageBase.Scheme.DRAWABLE.toUri("icon_emoji"));
        pageSetAdapter.add(emojiPageSetEntityBuilder.build());
    }

    @Override
    public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

    }
}
