package com.example.administrator.myapplication.weixinPhotoPicker.photopicker.intent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;


import java.util.ArrayList;

import com.example.administrator.myapplication.weixinPhotoPicker.photopicker.SimplelPhotoPreviewActivity;

/**
 * 预览照片
 * Created by foamtrace on 2015/8/25.
 */
@SuppressLint("ParcelCreator")
public class SimmplePhotoPreviewIntent extends Intent {

    public SimmplePhotoPreviewIntent(Context packageContext) {
        super(packageContext, SimplelPhotoPreviewActivity.class);
    }

    /**
     * 照片地址
     *
     * @param paths
     */
    public void setPhotoPaths(ArrayList<String> paths) {
        this.putStringArrayListExtra(SimplelPhotoPreviewActivity.EXTRA_PHOTOS, paths);
    }

    /**
     * 当前照片的下标
     *
     * @param currentItem
     */
    public void setCurrentItem(int currentItem) {
        this.putExtra(SimplelPhotoPreviewActivity.EXTRA_CURRENT_ITEM, currentItem);
    }
}
