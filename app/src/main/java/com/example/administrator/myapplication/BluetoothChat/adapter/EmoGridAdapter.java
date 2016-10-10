package com.example.administrator.myapplication.BluetoothChat.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.myapplication.BluetoothChat.config.TextChatMessage;
import com.example.administrator.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.administrator.myapplication.BluetoothChat.config.ChatMessageUtils.getResId;

/**
 * 在ChatActivity界面中，当按下界面底部的表情按钮后会弹出加载表情的布局。
 * 布局中使用一个ViewPager管理两个界面
 * 两个界面均使用GridView来显示表情
 * 该适配器就是用来加载GridView中显示的表情的
 */
public class EmoGridAdapter extends ListAdapter<TextChatMessage> {
    public EmoGridAdapter(Context context, List<TextChatMessage> datasource) {
        super(context, datasource);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = getInflater().inflate(R.layout.item_emo_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        TextChatMessage txm = getItem(position);
        Bitmap bm = getBitmapFromDrawableName(getContext(), txm.text.substring(1));
        vh.iv.setImageBitmap(bm);
        return convertView;
    }

    public class ViewHolder {
        /**
         * 显式表情
         */
        @Bind(R.id.iv_item_emo_layout)
        ImageView iv;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 根据res/drawable_xxxx中的文件的名称，获得Bitmap格式的图像
     *
     * @param context 获取Resources对象需要一个Context对象
     * @param name    drawable_xxxx文件夹中图像的名称
     * @return 返回name所对应的图像本身
     */
    public static Bitmap getBitmapFromDrawableName(Context context, String name) {
        return BitmapFactory.decodeResource(context.getResources(), getResId(context, name));
    }
}
