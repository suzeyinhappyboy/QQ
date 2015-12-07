package com.fzu.qq2016;

public class ChatMsgEntity {

	private String name;
	private String text;
	private boolean isComMeg = true; 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public boolean getMsgType() {
		return isComMeg;
		
	}
	
	public void setMsgType(boolean isComMsg) {
		 isComMeg = isComMsg;
		
	}
	
	public ChatMsgEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public ChatMsgEntity (String name,String date,String text,boolean isComMsg) {
		super();
		this.name = name;
		this.text = text;
		this.isComMeg = isComMsg;
	}
}
