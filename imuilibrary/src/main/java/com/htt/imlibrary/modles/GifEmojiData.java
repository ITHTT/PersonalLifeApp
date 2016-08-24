package com.htt.imlibrary.modles;

import com.htt.imlibrary.R;

import java.util.ArrayList;

/**
 * Created by ithtt on 16/8/24.
 */

public class GifEmojiData {
    public static final int[] coverIcons={
            R.mipmap.icon_002_cover,
            R.mipmap.icon_007_cover,
            R.mipmap.icon_010_cover,
            R.mipmap.icon_012_cover,
            R.mipmap.icon_013_cover,
            R.mipmap.icon_018_cover,
            R.mipmap.icon_019_cover,
            R.mipmap.icon_020_cover,
            R.mipmap.icon_021_cover,
            R.mipmap.icon_022_cover,
            R.mipmap.icon_024_cover,
            R.mipmap.icon_027_cover,
            R.mipmap.icon_029_cover,
            R.mipmap.icon_030_cover,
            R.mipmap.icon_035_cover,
            R.mipmap.icon_040_cover
    };

    public static final int[] emoticonRes={
            R.mipmap.icon_002,
            R.mipmap.icon_007,
            R.mipmap.icon_010,
            R.mipmap.icon_012,
            R.mipmap.icon_013,
            R.mipmap.icon_018,
            R.mipmap.icon_019,
            R.mipmap.icon_020,
            R.mipmap.icon_021,
            R.mipmap.icon_022,
            R.mipmap.icon_024,
            R.mipmap.icon_027,
            R.mipmap.icon_029,
            R.mipmap.icon_030,
            R.mipmap.icon_035,
            R.mipmap.icon_040
    };

    public static ArrayList<EmoticonEntity> getGifEmoticonDatas(){
        ArrayList<EmoticonEntity> emoticons=new ArrayList<>(coverIcons.length);
        for(int i=0;i<coverIcons.length;i++){
            EmoticonEntity entity=new EmoticonEntity();
            entity.setIconResource(coverIcons[i]);
            emoticons.add(entity);
        }
        return emoticons;
    }
}
