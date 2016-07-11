package com.example.administrator.myapplication.mvcHelper.task;


import com.example.administrator.myapplication.mvcHelper.mvc.RequestHandle;

/**
 * 任务
 * 
 * @author LuckyJayce
 *
 * @param <SUCCESS>
 *            成功的数据类型
 * @param <FAIL>
 *            失败的数据类型
 */
public interface IAsyncTask<SUCCESS, FAIL> extends SuperTask<SUCCESS, FAIL> {

	/**
	 * 执行后台任务
	 * 
	 * @param progressSender
	 *            进度更新发送者
	 * @return
	 * @throws Exception
	 */
	public RequestHandle execute(ResponseSender<SUCCESS, FAIL> sender, ProgressSender progressSender) throws Exception;

}
