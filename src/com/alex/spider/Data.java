package com.alex.spider;

import java.util.List;
import java.util.Map;

public class Data {
	List<Rl> rl;//实际数据

	Map<String, Integer> ct; //其他信息
	
	public List<Rl> getRl() {
		return rl;
	}
	public void setRl(List<Rl> rl) {
		this.rl = rl;
	}
	public Map<String, Integer> getCt() {
		return ct;
	}
	public void setCt(Map<String, Integer> ct) {
		this.ct = ct;
	}

}
