package com.example.administrator.myapplication.BluetoothChat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.BluetoothChat.adapter.ChatAdapter;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/4.
 */

public class SingleChatFragment extends Fragment {

    private View mView;
    @Bind(R.id.rv_speech)
    RecyclerView rv_speech;

    private ChatAdapter speechAdapter;
    private List<String> mData = new ArrayList<>();

    public SingleChatFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_single_chat, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initAdapter();
        initData();
    }

    public void initAdapter() {
        rv_speech.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        speechAdapter = new ChatAdapter(getActivity(), mData);
        rv_speech.setAdapter(speechAdapter);
    }

    public void initData() {
        for (int i = 0; i < 100; i++) {
            mData.add("test" + i);
        }
        speechAdapter.notifyDataSetChanged();
    }

}
