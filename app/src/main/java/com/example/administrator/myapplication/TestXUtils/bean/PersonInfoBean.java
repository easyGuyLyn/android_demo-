package com.example.administrator.myapplication.TestXUtils.bean;

import com.example.administrator.myapplication.TestXUtils.entity.PersonInfoEntity;
import com.example.administrator.myapplication.TestXUtils.util.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;


/**
 * 身份证信息
 * @author 石岩
 */
@HttpResponse(parser = JsonResponseParser.class)
public class PersonInfoBean{
	private String success;
	private PersonInfoEntity result;
	//失败时出现
	private String msg;
	//失败时出现
	private String msgid;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public PersonInfoEntity getResult() {
		return result;
	}
	public void setResult(PersonInfoEntity result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
}
