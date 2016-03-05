package com.apptive.datainfo;

public class NoticeListDataInfo {
	private String imgUrl;
	private String title;
	private String content;
	private String date;
	

	public String getImg(){
		return imgUrl;
	}
	public String getTitle(){
		return title;
	}
	public String getContent(){
		return content;
	}
	public String getDate(){
		return date;
	}
	public void setUrl(String url){
		this.imgUrl = url;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setDate(String date){
		this.date = date;
	}
}
