package com.dreawer.customer.utils;

public class ProduceResult {

	private boolean status = false; // 状态（true:成功、false:失败）
	
	private String message = null;

	public ProduceResult(){
		
	}
	
	public ProduceResult(boolean status){
		this.status = status;
	}
	
	public ProduceResult(boolean status, String message){
		this.status = status;
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
