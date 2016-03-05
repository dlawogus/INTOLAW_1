package com.apptive.datainfo;

public class LawyerListDataInfo {
	String id;
	String name;
	String local;
	String bubin;
	String is_woman;
	String introduce_short;
	//String introduce_long;
	String largeimage;
	String smallimage;

	public LawyerListDataInfo(){}
	public LawyerListDataInfo (String id, String name, String local, String bubin, String is_woman, String largeimage, String smallimage){
		this.id = id;
		this.name = name;
		this.local = local;
		this.bubin = bubin;
		this.is_woman = is_woman;
		this.largeimage = largeimage;
		this.smallimage = smallimage;
	}
	
	public String getIntroduce(){
		return introduce_short;

	}
	
	public void setIntroduce(String introduce_short ){
		this.introduce_short = introduce_short;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setLocal(String local){
		this.local = local;
	}
	public void setBubin(String bubin){
		this.bubin = bubin;
	}
	public void setIs_woman(String is_woman){
		this.is_woman = is_woman;
	}	
	public void setLargeImage(String largeimage){
		this.largeimage = largeimage;
	}
	public void setSmallImage(String smallimage){
		this.smallimage = smallimage;
	}
	public String getId(){
		return id;
	}
	public String getLargeImage(){
		return largeimage;
	}
	public String getSmallImage(){
		return smallimage;
	}
	public String getIsWoman(){
		return is_woman;
	}
	public String getName(){
		return name;
	}
	public String getLocal(){
		return local;
	}
	public String getBubin(){
		return bubin;
	}
	
	//변호사 id가 같으면 두 객체는 같다. 
	@Override
	public boolean equals(Object obj) {	  
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		LawyerListDataInfo t = (LawyerListDataInfo)obj;
		if( id.equals( t.getId() ) ) return true;
		else return false;
	}
	
	//equals함수를 재정의하면 해쉬코드함수도 재정의 되어야한다 
    @Override
    public int hashCode(){
    	return Integer.parseInt(id);
    }
	
}
