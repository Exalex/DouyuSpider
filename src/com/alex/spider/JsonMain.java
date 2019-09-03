package com.alex.spider;

import java.util.List;
import java.util.Map;

public class JsonMain {
	 private String code;  //状态码

	 private  String msg;  //连接状态
	 
	 private Data data; //数据模型
	 
	 
	 

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Data data) {
		this.data = data;
	}



}
