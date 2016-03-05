package com.apptive.datainfo;

public class ExamDataInfo {
	private String title;
	private String exam_num;
	private String exam_year;
	private String training_num;
	private String training_year;
	
	public String getTitle(){
		return title;
	}
	public String getExam_Num(){
		return exam_num;
	}
	public String getExam_Year(){
		return exam_year;
	}
	public String getTraining_Num(){
		return training_num;
	}
	public String getTraining_year(){
		return training_year;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setExam_Num(String exam_num){
		this.exam_num = exam_num;
	}
	
	public void setExam_Year(String exam_year){
		this.exam_year = exam_year;
	}
	
	public void setTraining_num(String training_num){
		this.training_num = training_num;
	}
	public void setTraining_year(String training_year){
		this.training_year = training_year;
	}	

}
