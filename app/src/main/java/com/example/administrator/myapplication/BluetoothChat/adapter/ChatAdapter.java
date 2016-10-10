package com.example.administrator.myapplication.BluetoothChat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.BluetoothChat.config.ChatMessageUtils;
import com.example.administrator.myapplication.BluetoothChat.config.CommonViewHolder;
import com.example.administrator.myapplication.BluetoothChat.model.BluChatMsg;
import com.example.administrator.myapplication.BluetoothChat.model.BluChatMsgText;
import com.example.administrator.myapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/4.
 */

public class ChatAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TEXT_SEND = 0;
    private static final int TYPE_TEXT_RECEIVE = 1;
    private Context mContext;
    private List<BluChatMsg> mData;

    public ChatAdapter(Context context, List<BluChatMsg> data) {
        this.mContext = context;
        mData = data;
    }

    @Override
    public int getItemViewType(int position) {
        BluChatMsg msg = mData.get(position);
        if (msg instanceof BluChatMsgText) {
            if (msg.getFrom() == BluChatMsg.RECEIVE)
                return TYPE_TEXT_RECEIVE;
            else
                return TYPE_TEXT_SEND;
        }
        //// TODO: 2016/10/8  
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TEXT_SEND:
                return new CommonViewHolder(View.inflate(mContext, R.layout.item_blu_chat_text_send, null));
            case TYPE_TEXT_RECEIVE:
                return new CommonViewHolder(View.inflate(mContext, R.layout.item_blu_chat_text_receive, null));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CommonViewHolder viewHolder = (CommonViewHolder) holder;
        final BluChatMsg message = mData.get(position);
        String deviceName = message.getDeviceName() + ": ";
        switch (getItemViewType(position)) {
            case TYPE_TEXT_SEND:
                SpannableString contentWithEmoSend = ChatMessageUtils.toSpannableString(mContext, ((BluChatMsgText) message).getText());
                viewHolder.getTv(R.id.tv_text).setText(deviceName + contentWithEmoSend);
                break;
            case TYPE_TEXT_RECEIVE:
                SpannableString contentWithEmoReceive = ChatMessageUtils.toSpannableString(mContext, ((BluChatMsgText) message).getText());
                viewHolder.getTv(R.id.tv_text).setText(deviceName + contentWithEmoReceive);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
