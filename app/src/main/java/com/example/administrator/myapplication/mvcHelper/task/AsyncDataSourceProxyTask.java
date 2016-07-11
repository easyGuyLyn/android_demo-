package com.example.administrator.myapplication.mvcHelper.task;


import com.example.administrator.myapplication.mvcHelper.mvc.IAsyncDataSource;
import com.example.administrator.myapplication.mvcHelper.mvc.RequestHandle;

public class AsyncDataSourceProxyTask<DATA> implements IAsyncTask<DATA, Void> {
	private IAsyncDataSource<DATA> datasource;

	public AsyncDataSourceProxyTask(IAsyncDataSource<DATA> datasource) {
		super();
		this.datasource = datasource;
	}

	public IAsyncDataSource<DATA> getDatasource() {
		return datasource;
	}

	@Override
	public RequestHandle execute(final ResponseSender<DATA, Void> sender, ProgressSender progressSender) throws Exception {
		com.example.administrator.myapplication.mvcHelper.mvc.ResponseSender<DATA> responseSender = new com.example.administrator.myapplication.mvcHelper.mvc.ResponseSender<DATA>() {
			@Override
			public void sendError(Exception exception) {
				sender.sendError(exception);
			}

			@Override
			public void sendData(DATA data) {
				sender.sendData(data);
			}
		};
		return datasource.refresh(responseSender);
	}
}
