package com.example.administrator.myapplication.mvcHelper.task;

public interface ResponseSender<SUCCESS, FAIL> {

	public void sendError(Exception exception);

	public void sendData(SUCCESS data);

	public void sendFail(FAIL data);

}