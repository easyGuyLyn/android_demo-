package com.example.administrator.myapplication.BluetoothChat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/4.
 */

public class ChatAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mData;

    public ChatAdapter(Context context, List<String> data) {
        this.mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(View.inflate(mContext, R.layout.item_speech_text_send, null));
        //return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CommonViewHolder viewHolder = (CommonViewHolder) holder;
        viewHolder.getTv(R.id.tv_text).setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
