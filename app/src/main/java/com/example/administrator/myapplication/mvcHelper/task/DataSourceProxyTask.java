package com.example.administrator.myapplication.mvcHelper.task;


import com.example.administrator.myapplication.mvcHelper.mvc.IDataSource;

public class DataSourceProxyTask<DATA> implements Task<DATA, Void> {
	private IDataSource<DATA> datasource;

	public DataSourceProxyTask(IDataSource<DATA> datasource) {
		super();
		this.datasource = datasource;
	}

	@Override
	public Data<DATA, Void> execute(ProgressSender progressSender) throws Exception {
		return Data.madeSuccess(datasource.refresh());
	}

	@Override
	public void cancle() {

	}

	public IDataSource<DATA> getDatasource() {
		return datasource;
	}
}
