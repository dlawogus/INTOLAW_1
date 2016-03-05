package com.apptive.datainfo;

public class SchoolSelectDataInfo {
		String schoolname;
		String level;
		
		public SchoolSelectDataInfo(String schoolname,String level){
			this.schoolname = schoolname;
			this.level = level;
		}
		public String getSchoolName(){
			return schoolname;
		}
		public String getLevel(){
			return level;
		}
}
