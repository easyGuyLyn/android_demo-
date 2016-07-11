package com.example.administrator.myapplication.mvcHelper.mvc.viewhandler;

import android.view.View;
import android.view.View.OnClickListener;

import com.example.administrator.myapplication.mvcHelper.mvc.IDataAdapter;
import com.example.administrator.myapplication.mvcHelper.mvc.ILoadViewFactory;
import com.example.administrator.myapplication.mvcHelper.mvc.MVCHelper;


public interface ViewHandler {

	/**
	 *
	 * @param contentView
	 * @param adapter
	 * @param loadMoreView
	 * @param onClickLoadMoreListener
     * @return 是否有 init ILoadMoreView
     */
	public boolean handleSetAdapter(View contentView, IDataAdapter<?> adapter, ILoadViewFactory.ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener);

	public void setOnScrollBottomListener(View contentView, MVCHelper.OnScrollBottomListener onScrollBottomListener);

}
