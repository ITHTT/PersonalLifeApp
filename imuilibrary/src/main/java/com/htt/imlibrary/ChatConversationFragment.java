package com.htt.imlibrary;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.htt.imlibrary.modles.GifEmojiData;
import com.htt.imlibrary.utils.imageloader.ImageBase;
import com.htt.imlibrary.views.ChatInputEditTextView;
import com.htt.imlibrary.views.adapter.BigEmoticonsAdapter;
import com.htt.imlibrary.views.adapter.EmoticonsAdapter;
import com.htt.imlibrary.views.adapter.PageSetAdapter;
import com.htt.imlibrary.views.widget.ChatExtendMenuView;
import com.htt.imlibrary.views.widget.ChatVoiceRecorderView;
import com.htt.imlibrary.views.widget.EmoticonPageView;
import com.htt.imlibrary.views.widget.EmoticonsEditText;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/24.
 */
public class ChatConversationFragment extends Fragment implements EmoticonClickListener,ChatInputEditTextView.ChatInputEditTextListener
{
    protected ChatInputEditTextView chatInputEditTextView=null;
    protected ListView chatListView;
    protected ChatVoiceRecorderView voiceRecorderView;
    protected PageSetAdapter pageSetAdapter=null;

    protected ChatExtendMenuView chatExtendMenuView=null;
    protected ChatExtendMenuView.ChatExtendMenuItemClickListener chatExtendMenuItemClickListener=null;

    protected String[] menuNames;
    protected int[] menuIcons;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat_conversation, container, false);
        initViews(view);
        return view;
    }


    protected void initViews(View view){
        chatListView=(ListView)view.findViewById(R.id.lv_chat);
        chatInputEditTextView= (ChatInputEditTextView) view.findViewById(R.id.chat_input_edit);
        chatInputEditTextView.setChatInputEditTextListener(this);
        voiceRecorderView=(ChatVoiceRecorderView)view.findViewById(R.id.voice_recorder_view);
        Bundle data=getArguments();
        if(data!=null){
            String voiceFileDir= data.getString("voice_file_dir");
            menuIcons=data.getIntArray("extend_menu_icons");
            menuNames=data.getStringArray("extend_menu_names");
            voiceRecorderView.setVoiceFileDir(voiceFileDir);
        }
        initChatInputEditTextView();
    }

    protected void initChatExtrendMenu(Context context){
        if(menuIcons!=null&&menuNames!=null) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_chat_extend_menu, null);
            chatExtendMenuView = (ChatExtendMenuView) view.findViewById(R.id.chat_extend_menu);
            for (int i = 0; i < menuNames.length; i++) {
                chatExtendMenuView.registerMenuItem(menuNames[i], menuIcons[i], menuIcons[i], chatExtendMenuItemClickListener);
            }
            chatExtendMenuView.initWidgets();
            chatInputEditTextView.addFuncView(view);
        }
    }

    protected void initChatInputEditTextView(){
        final EmoticonsEditText emoticonsEditText=chatInputEditTextView.getEtChat();
        emoticonsEditText.addEmoticonFilter(new ExpressionFilter());
        initChatExtrendMenu(getContext());
        pageSetAdapter=new PageSetAdapter();
        addDefaultEmoticonPageSetEntity(pageSetAdapter);
        addGifEmoticonPageSetEntity(pageSetAdapter);
        chatInputEditTextView.setAdapter(pageSetAdapter);
        chatInputEditTextView.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    protected void addGifEmoticonPageSetEntity(PageSetAdapter adapter){
        ArrayList<EmoticonEntity> emoticonEntities= GifEmojiData.getGifEmoticonDatas();
        PageViewInstantiateListener<EmoticonPageEntity> pageViewInstantiateListener=new PageViewInstantiateListener<EmoticonPageEntity>() {
            @Override
            public View instantiateItem(ViewGroup container, int position, EmoticonPageEntity pageEntity) {
                if (pageEntity.getRootView() == null) {
                    EmoticonPageView pageView = new EmoticonPageView(container.getContext());
                    pageView.setNumColumns(pageEntity.getRow());
                    pageEntity.setRootView(pageView);
                    try {
                        //EmoticonsAdapter adapter = new EmoticonsAdapter(getContext(),pageEntity,null);
                        BigEmoticonsAdapter adapter=new BigEmoticonsAdapter(getContext(),pageEntity,ChatConversationFragment.this);
                        pageView.getEmoticonsGridView().setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return pageEntity.getRootView();
            }
        };
        EmoticonPageSetEntity pageSetEntity
                = new EmoticonPageSetEntity.Builder()
                .setLine(2)
                .setRow(4)
                .setEmoticonList(emoticonEntities)
                .setIPageViewInstantiateItem(pageViewInstantiateListener)
                .setIconUri(ImageBase.Scheme.DRAWABLE.toUri("icon_002_cover"))
                .build();
        pageSetAdapter.add(pageSetEntity);
    }

    @Override
    public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
        EmoticonsEditText editText=chatInputEditTextView.getEtChat();
        if(isDelBtn){
            int action = KeyEvent.ACTION_DOWN;
            int code = KeyEvent.KEYCODE_DEL;
            KeyEvent event = new KeyEvent(action, code);
            editText.onKeyDown(KeyEvent.KEYCODE_DEL, event);
        }else{
            String content = null;
            if(o instanceof EmoticonEntity){
                content = ((EmoticonEntity) o).getContent();
            } else if(o instanceof EmoticonEntity){
                content = ((EmoticonEntity)o).getContent();
            }
            if(TextUtils.isEmpty(content)){
                return;
            }
            int index = editText.getSelectionStart();
            Editable editable = editText.getText();
            editable.insert(index, content);
        }
    }

    @Override
    public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
        return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new ChatVoiceRecorderView.ChatVoiceRecorderCallback() {
            @Override
            public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {

            }
        });
    }

    public void setChatExtendMenuItemClickListener(ChatExtendMenuView.ChatExtendMenuItemClickListener chatExtendMenuItemClickListener) {
        this.chatExtendMenuItemClickListener = chatExtendMenuItemClickListener;
    }
}
