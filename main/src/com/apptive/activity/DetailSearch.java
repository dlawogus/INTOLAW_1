package com.apptive.activity;

import java.util.ArrayList;

import com.apptive.adapter.BaseListSelectedAdapter;
import com.apptive.adapter.SelectListAdapter;
import com.apptive.datainfo.SchoolSelectDataInfo;
import com.apptive.intolaw.R;
import com.apptive.intolaw.garbageCollection;
import com.apptive.lawyerlist.LawyerList;
import com.apptive.lawyerlist.LawyerListDetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailSearch extends BaseActivity {
	private ImageButton mAge;
	private ImageButton mCareer;
	private ImageButton mHometown;
	private ImageButton mPay;
	private ImageButton mGroup;
	private ImageButton mExamtype;
	private ImageButton mSchool;
	private ImageButton mBack;
	private Button mSearch;
	private TextView detailsearch_text1;

	public boolean mSexSelect = 		false;
	public boolean mAgeSelect = 		false;
	public boolean mCareerSelect = 		false;
	public boolean mHometownSelect = 	false;
	public boolean mPaySelect =			false;
	public boolean mGroupSelect = 		false;
	public boolean mExamtypeSelect = 	false;
	public boolean mSchoolSelect = 		false;
	
	private ListView list;
	public static SelectListAdapter mSelectListAdapter;
	public static ArrayList<String> datainfo;
	private Context con;

	public static String sex = 			null; 	  	//성별	남자 0 , 여자 1
	public static int 	 age = 			0; 	 		//나이 
	public static String field = 		"모든 분야"; 	//취급분야
	public static String career = 		null;		//경력	판검사
	public static String hometown = 	"모든 지역"; 	//출신지
	public static String hometownDetail="";			//출신지 상세
	public static String area = 		"모든 지역"; 	//지역
	public static String areaDetail=	"";			//지역 상세
	//public static String pay = 		null; 		//상담비		무료 1, 유료 0
	public static String group = 		null;		//법무법인		개인 1, 법인 0 
	public static String examtype = 	null;		//시험종류
	public static String examGisu = 	null;
	public static String school = 		null;		//출신학교
	public static String schoolname = 	null; 
	public static String highSchool =	null;		//출신 고등학교
	public static String university = 	null;		//출신 대학교
	public static String graduatedSchool= null;		//출신 대학원
	public static String lawSchool =	null;		//출신 로스쿨
	public static String school_kind = 	null;		//학교 종류
	public String keyword_str =			"";			//키워드
	
	//선택된 학교 리스트 어래이 & 어댑터
	public static ArrayList<SchoolSelectDataInfo> selectedlist;
	
	public void finish(){
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
	    setContentView(R.layout.activity_detailsearch);
	    con = getBaseContext();
		
	    datainfo = new ArrayList<String>();
	    datainfo.add("지역");
	    datainfo.add("취급분야");
	    datainfo.add("성별");
		
	    list = (ListView)findViewById(R.id.selectList);
		mSelectListAdapter = new SelectListAdapter(this,datainfo);
		list.setAdapter(mSelectListAdapter);
		
		mBack = (ImageButton)findViewById(R.id.back_detail);
		mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		detailsearch_text1 = (TextView)findViewById(R.id.detailsearch_text1);
		//detailsearch_text1.setTypeface(Typeface.createFromAsset(con.getAssets(), "Nototo.ttf"));
		
		mSearch = (Button)findViewById(R.id.search);
		mSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DetailSearch.this, LawyerListDetail.class);
				intent.putExtra("sex", sex);
				intent.putExtra("age", age);
				intent.putExtra("hometown", hometown);
				intent.putExtra("field", field);	
				intent.putExtra("career", career);
				intent.putExtra("hometownDetail", hometownDetail);
				intent.putExtra("area", area);
				intent.putExtra("areaDetail", areaDetail);
				intent.putExtra("group", group);
				intent.putExtra("examtype", examtype);
				intent.putExtra("examGisu", examGisu);
				intent.putExtra("school", school);
				intent.putExtra("schoolname", schoolname);
				//Toast.makeText(con, highSchool, Toast.LENGTH_SHORT).show();
				startActivity(intent);
			}
		});
		
		mAge = (ImageButton)findViewById(R.id.age);
		mAge.setBackgroundResource(R.drawable.icon_age1);
		mAge.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !mAgeSelect ){
					mAge.setBackgroundResource(R.drawable.icon_age2);
					//버튼 안눌려 져 있을때 호출	
					datainfo.add(0, "나이");
					mSelectListAdapter.notifyDataSetChanged();
					mAgeSelect = true;
				}
				else{
					//버튼 눌러져 있을 때 호출
					mAge.setBackgroundResource(R.drawable.icon_age1);
					datainfo.remove("나이");
					mSelectListAdapter.notifyDataSetChanged();
					mAgeSelect = false;
					age = 0;
				}
			}
		});
		
		mCareer =(ImageButton)findViewById(R.id.career);
		mCareer.setBackgroundResource(R.drawable.icon_career1);
		mCareer.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !mCareerSelect ){
					mCareer.setBackgroundResource(R.drawable.icon_career2);
					//버튼 안눌려 져 있을때 호출
					datainfo.add(0,"경력");
					mSelectListAdapter.notifyDataSetChanged();
					mCareerSelect = true;
				}
				else{
					//버튼 눌러져 있을 때 호출
					mCareer.setBackgroundResource(R.drawable.icon_career1);
					datainfo.remove("경력");
					mSelectListAdapter.notifyDataSetChanged();
					mCareerSelect = false;
					career = null;
				}
			}
		});
		
		mHometown =(ImageButton)findViewById(R.id.hometown);
		mHometown.setBackgroundResource(R.drawable.icon_hometown1);
		mHometown.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !mHometownSelect ){
					mHometown.setBackgroundResource(R.drawable.icon_hometown2);
					//버튼 안눌려 져 있을때 호출
					datainfo.add(0,"출신지");
					mSelectListAdapter.notifyDataSetChanged();
					mHometownSelect = true;
				}
				else{
					mHometown.setBackgroundResource(R.drawable.icon_hometown1);
					//버튼 눌러져 있을 때 호출
					datainfo.remove("출신지");
					hometown = 	"모든 지역"; 	//출신지
					hometownDetail="";		
					mSelectListAdapter.notifyDataSetChanged();
					mHometownSelect = false;
				}
			}
		});
		/*
		mPay =(Button)findViewById(R.id.pay);
		mPay.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !mPaySelect ){
					mPay.setBackgroundResource(R.drawable.buttonpressed);
					//버튼 안눌려 져 있을때 호출
					datainfo.add(0,"상담비");
					mSelectListAdapter.notifyDataSetChanged();
					mPaySelect = true;
				}
				else{
					//버튼 눌러져 있을 때 호출
					mPay.setBackgroundResource(R.drawable.buttonunpressed);
					datainfo.remove("상담비");
					mSelectListAdapter.notifyDataSetChanged();
					mPaySelect = false;
					pay = null;
				}
			}
		});
		*/
		mGroup =(ImageButton)findViewById(R.id.group);
		mGroup.setBackgroundResource(R.drawable.icon_belong1_03);
		mGroup.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !mGroupSelect ){
					mGroup.setBackgroundResource(R.drawable.icon_belong1_10);
					//버튼 안눌려 져 있을때 호출
					datainfo.add(0,"소속");
					mSelectListAdapter.notifyDataSetChanged();
					mGroupSelect = true;
				}
				else{
					//버튼 눌러져 있을 때 호출
					mGroup.setBackgroundResource(R.drawable.icon_belong1_03);
					datainfo.remove("소속");
					mSelectListAdapter.notifyDataSetChanged();
					mGroupSelect = false;
					group = null;
				}
			}
		});
		
		mExamtype =(ImageButton)findViewById(R.id.examtype);
		mExamtype.setBackgroundResource(R.drawable.icon_exam1);
		mExamtype.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !mExamtypeSelect ){
					mExamtype.setBackgroundResource(R.drawable.icon_exam2);
					//버튼 안눌려 져 있을때 호출
					datainfo.add(0,"시험종류");
					mSelectListAdapter.notifyDataSetChanged();
					mExamtypeSelect = true;
				}
				else{
					//버튼 눌러져 있을 때 호출
					mExamtype.setBackgroundResource(R.drawable.icon_exam1);
					datainfo.remove("시험종류");
					mSelectListAdapter.notifyDataSetChanged();
					mExamtypeSelect = false;
					examtype = null;
				}
			}
			
		});
		
		mSchool =(ImageButton)findViewById(R.id.school);
		mSchool.setBackgroundResource(R.drawable.icon_school1);
		mSchool.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !mSchoolSelect ){
					//버튼 안눌려 져 있을때 호출
					mSchool.setBackgroundResource(R.drawable.icon_school2);
					datainfo.add(0,"출신학교");
					mSelectListAdapter = new SelectListAdapter(con,datainfo);
					list.setAdapter(mSelectListAdapter);
 					mSchoolSelect = true;
					mSelectListAdapter.notifyDataSetChanged();
				}
				else{
					//버튼 눌러져 있을 때 호출
					mSchool.setBackgroundResource(R.drawable.icon_school1);
					datainfo.remove("출신학교");
					mSelectListAdapter = new SelectListAdapter(con,datainfo);
					list.setAdapter(mSelectListAdapter);
					school = null;
					schoolname = null;
					school_kind = null;
					highSchool = null;
					university = null;
					graduatedSchool = null;
					lawSchool = null;
					selectedlist = null;
					mSchoolSelect = false;
					mSelectListAdapter.notifyDataSetChanged();
				}
			}
		});	
	}
	
	@Override
   	protected void onDestroy() {
	   if (mSelectListAdapter != null)
		   mSelectListAdapter.recycle();
		
		sex = 					null; 	  	//성별
		age = 					0; 	 		//나이 
		field = 				"모든 분야"; 	//취급분야
		career = 				null;		//경력
		hometown = 				"모든 지역"; 	//출신지
		hometownDetail=			"";			//출신지 상세
		area = 					"모든 지역"; 	//지역
		areaDetail=				"";			//지역 상세
		//pay = 				null; 		//상담비
		group = 				null;		//소속
		examtype = 				null;		//시험종류
		examGisu = 				null;		//시험기수
		school = 				null;		//출신학교
		schoolname= 			null;
		highSchool =			null;		//출신 고등학교
		university = 			null;		//출신 대학교
		graduatedSchool=		null;		//출신 대학원
		lawSchool =				null;		//출신 로스쿨
		school_kind = 			null;		//학교 종류
		keyword_str = 			null;		//키워드 
		selectedlist = null;	//학교 선택리스트 초기화
		datainfo =					null;
		
	   garbageCollection.recursiveRecycle(getWindow().getDecorView());
	   System.gc();
	   super.onDestroy();
   	}

}
