package com.example.administrator.myapplication.weixinPhotoPicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.administrator.myapplication.weixinPhotoPicker.photopicker.PhotoPickerActivity;
import com.example.administrator.myapplication.weixinPhotoPicker.photopicker.PhotoPreviewActivity;
import com.example.administrator.myapplication.weixinPhotoPicker.photopicker.SelectModel;
import com.example.administrator.myapplication.weixinPhotoPicker.photopicker.intent.PhotoPickerIntent;
import com.example.administrator.myapplication.weixinPhotoPicker.photopicker.intent.PhotoPreviewIntent;

public class WeixinPhotoPickerActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private static final int MAXIMAGES = 9;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private GridAdapter gridAdapter;
    @Bind(R.id.gridView)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_photo_picker);
        ButterKnife.bind(this);

        gridAdapter = new GridAdapter(imagePaths);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == gridAdapter.getMaxPosition() - 1) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(WeixinPhotoPickerActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(MAXIMAGES); // 最多选择照片数量，默认为9
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(WeixinPhotoPickerActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        imagePaths.clear();
        imagePaths.addAll(paths);
        gridAdapter.notifyDataSetChanged();
        try {
            JSONArray obj = new JSONArray(imagePaths);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private int mMaxPosition;
        private LayoutInflater inflater;

        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            inflater = LayoutInflater.from(WeixinPhotoPickerActivity.this);

        }

        public int getCount() {
            mMaxPosition = listUrls.size() + 1;
            return mMaxPosition;
        }

        public int getMaxPosition() {
            return mMaxPosition;
        }

        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_image_activity_weixinphotopicker, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == mMaxPosition - 1) {
                Glide.with(WeixinPhotoPickerActivity.this).
                        load(R.drawable.ic_addpic).
                        placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
                holder.image.setVisibility(View.VISIBLE);
                if (position == MAXIMAGES && mMaxPosition == MAXIMAGES + 1) {
                    Glide.with(WeixinPhotoPickerActivity.this).
                            load(R.drawable.ic_addpic).
                            placeholder(R.mipmap.default_error)
                            .error(R.mipmap.default_error)
                            .centerCrop()
                            .crossFade()
                            .into(holder.image);
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                final String path = listUrls.get(position);
                Glide.with(WeixinPhotoPickerActivity.this)
                        .load(new File(path))
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }
    }
}
