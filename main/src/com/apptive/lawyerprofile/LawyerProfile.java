package com.apptive.lawyerprofile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import com.apptive.activity.BaseActivity;
import com.apptive.datainfo.ExamDataInfo;
import com.apptive.datainfo.HavecaseDataInfo;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.apptive.lawyerlist.LawyerList;
import com.apptive.login.LoginActivity;
import com.apptive.login.LoginHostActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LawyerProfile extends BaseActivity {

	private ProgressDialog pd;
	private Context context;
	private String index;				//변호사 인덱스
	private String _fromIntent;			//나의 변호사로 부터 실행됬는지 여부
	private ImageButton mBackBtn;		//뒤로가기 버튼
	private TextView nameView;			//이름 
	private ScrollView scrollView;		//스크롤 레이아웃
	
	//기본정보
	//private WebView lawyerWebView;		//변호사 이미지
	private WebView lawyerMapView;		//변호사 사무실
	//private ImageView lawyerImage;		//변호사 이미지
	private ImageView lawyerImage;	//변호사 디폴트 이미지
	
	private TextView officeName;		//사무실 이름
	private TextView slogun;			//변호사 슬로건
	private TextView mIntroduce;		//변호사 소개글
	private TextView lawyerAgeSex;		//변호사 나이 및 성별
	private TextView mOffice_First_region;	//사무실 큰지역
	private TextView mOffice_Second_region;	//사무실 작은지역
	private ImageButton mNamecard;		//명함받기 
	private TextView havecard_text;		//명함받기 텍스트
	private TextView mNamecard_Num;		//명함 받은 명수
	private TextView mBirth;			//출신일
	private TextView mHometown;			//출신지
	private TextView officeName_1;		//밑에 있는 사무실 이름
	private TextView consultTime; 		//상담가능시간
	private TextView mEmail;			//변호사 이메일
	private TextView mHomepage;			//사무실 홈페이지
	private TextView mFax;				//사무실 팩스
	
	//private ListView mCaseList;			//수임사건 리스트
	//private CaseListAdapter mCaseListAdapter;	//수임사건 리스트 어댑터
	private LinearLayout mCaseListView;
	private Button 	 mCaseExpand;		//수임사건 펼치기
	private boolean  is_caseExpand;		//펼침여부
	private ViewGroup caseEmptyMsg;		//수임사건 없음 메세지
	
	//private ListView mSchoolList;		//출신학교
	private LinearLayout mSchoolListView;
	private ViewGroup schoolEmptyMsg;	//등록정보 없음 메세지
	
	//private ListView mExamList;			//출신시험
	private LinearLayout mExamListView;
	private ViewGroup examEmptyMsg;		//출신시험 없음 메세지
	
	//private ListView mCareerList;		//경력
	private LinearLayout mCareerListView;
	private Button   mCareerExpand;		//경력 펼치기
	private boolean  is_careerExpand = false;//펼침 여부
	private ViewGroup careerEmptyMsg;	//경력 없음 메세지
	
	//private ListView mPaperList;		//논문
	private LinearLayout mPaperListView;
	private Button 	 mPaperExpand;		//논문 펼치기
	private boolean  is_paperExpand = false;//펼침 여부
	private ViewGroup paperEmptyMsg;		//논문 정보 없음 메세지
	
	private LinearLayout mFieldLinear1;	//취급분야 동적버튼 레이아웃1
	private LinearLayout mFieldLinear2;	//취급분야 동적버튼 레이아웃2
	private LinearLayout mFieldLinear3;	//취급분야 동적버튼 레이아웃1
	private LinearLayout mFieldLinear4;	//취급분야 동적버튼 레이아웃2
	private Button mField[] = new Button[16];
	private ViewGroup feildEmptyMsg; 	//취급분야 등록정보 없음 메세지
	
	private TextView mapJuso;			//사무실 주소
	private ViewGroup mapLayout_profile;//지도 레이아웃
	//private ImageView img_plus;			//이미지크게보기 이미지
	
	private Button mSingo;				//변호사 신고버튼
	private Button sameOfficeLawyer;	//같은 사무실 변호사 검색
	private Button sameExamLawyer;		//같은 시험 변호사 검색
	private Button sameInstituteLawyer;	//같은 (연수원)기관 출신 검색
	private Button sameSchoolLawyer;	//같은 학교출신 변호사 검색
	private Button mCall;				//전화하기

	//////////////////////////////////////////////////////////////////////////
	private String name;
	private String phone;
	private String home_first_region;
	private String home_second_region;
	private String birth_year;
	private String birth_date;
	private String consult_time;
	private String email;
	private String homepage;
	private String introduce_short = "";
	private String introduce_long;
	private String large_image;
	private String is_woman;
	private String is_free;
	private int namecard_num = 0;
	private String haveNamecard;
	
	//명함받기 결과
	private String mResultNamecard;
	private boolean is_namecardSelect = false;	//명함받기 누름 여부
	
	//경력
	private ArrayList<String> pro_field;
	private ArrayList<String> pro_field_date;
	
	//학교 리스트
	private ArrayList<String> school;
	private ArrayList<String> school_id;
	private ArrayList<String> school_date;
	
	//경력 리스트
	private ArrayList<String> career;
	private ArrayList<String> career_date;
	
	//논문 리스트
	private ArrayList<String> paper;
	private ArrayList<String> paper_date;
	
	//취급분야
	private ArrayList<String> handle_field;
	
	//수임사건
	private ArrayList<HavecaseDataInfo> have_case;
	private ArrayList<HavecaseDataInfo> have_case_temp;
	
	//출신시험
	private ArrayList<ExamDataInfo> exam;
	
	//사무실 정보
	private String office_id;
	private String region_id;
	private String office_name;
	private String address;
	private String office_phone;
	private String office_fax;
	private String office_first_region;
	private String office_second_region;
	private String map_image;
	private double latitude;
	private double longitude;
	
	private String tempString;		//임시 스트링 저장
	private boolean is_alertCheck;		//경고창 확인 여부

	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();

	private LayoutInflater inflater_career;
	
	private LinearLayout career_item;
	private TextView title_career;
	private TextView date_career;

	private LinearLayout paper_item;
	private TextView title_paper;
	private TextView date_paper;
	
	private LinearLayout school_item;
	private TextView title_school;
	private TextView date_school;	
	
	private RelativeLayout exam_item;
	private TextView title_exam;
	private TextView gisu_exam;
	private TextView date_exam;
	
	private LinearLayout case_item;
	private TextView title_case;
	private TextView keyword_case;		
	
	// 값 불러오기
	private void getPreferences() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		is_alertCheck = pref.getBoolean("alertCheck", false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.lawyer_profile);
	    context = this;
	    getPreferences();

		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.white)
		.showImageForEmptyUri(R.drawable.white)
		.showImageOnFail(R.drawable.white)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
	    
		pro_field = 	new ArrayList<String>();			//전문분야 어레이
		pro_field_date =new ArrayList<String>();			//전문분야 날짜 어레이
		school = 		new ArrayList<String>();			//출신학교 어레이
		school_id = 	new ArrayList<String>();	 		//출신학교 아이디 어레이	
		school_date = 	new ArrayList<String>();			//출신학교 날짜 어레이
		career = 		new ArrayList<String>();			//경력 어레이
		career_date = 	new ArrayList<String>();			//경력 날짜 어레이
		paper = 		new ArrayList<String>();			//논문 어레이
		paper_date = 	new ArrayList<String>();			//논문 날짜 어레이		
		handle_field = 	new ArrayList<String>();			//취급분야 어레이
		have_case = 	new ArrayList<HavecaseDataInfo>();	//수임사건기록 어레이
		exam = 			new ArrayList<ExamDataInfo>();		//출신시험
		
	    //넘어온 intent값 저장
	    Intent intent = this.getIntent();
	    _fromIntent = intent.getStringExtra("_from");
	    index = intent.getStringExtra("index");
	    is_woman = intent.getStringExtra("is_woman");
	    large_image = intent.getStringExtra("is_premium");
	    
	    mBackBtn = (ImageButton) findViewById(R.id.back_profile);
	    mBackBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
	    });
	    
	    scrollView = (ScrollView)findViewById(R.id.scrollview_profile);
	    nameView = (TextView)findViewById(R.id.name);								//이름
	    //lawyerWebView = (WebView)findViewById(R.id.lawyerWebView);					//변호사 웹 이미지
	    //lawyerImage = (ImageView)findViewById(R.id.lawyerImage_profile);			//변호사 이미지
	    lawyerImage = (ImageView)findViewById(R.id.lawyerImage_profile);	//변호사 디폴트 이미지
	    lawyerMapView = (WebView)findViewById(R.id.mapWebView_profile);				//변호사 사무실 지도 이미지
		officeName = (TextView)findViewById(R.id.officeName);						//변호사 사무실이름
		slogun = (TextView)findViewById(R.id.slogun);								//변호사 슬로건
		mIntroduce = (TextView)findViewById(R.id.introduce_long);					//변호사 소개글
		lawyerAgeSex = (TextView)findViewById(R.id.lawyerAgeSex);					//변호사 나이 및 성별
		mOffice_First_region = (TextView)findViewById(R.id.first_region_profile);	//변호사 사무실 큰지역
		mOffice_Second_region = (TextView)findViewById(R.id.second_region_profile);	//변호사 사무실 작은지역
		mNamecard_Num = (TextView)findViewById(R.id.profile_card_num);				//변호사 명함받은사람 수
		mBirth = (TextView)findViewById(R.id.birth_profile);						//변호사 생년월일
		mHometown = (TextView)findViewById(R.id.hometown_profile);					//변호사 출신지
		officeName_1 = (TextView)findViewById(R.id.officeName_1);					//변호사 밑에 사무실이름
		consultTime = (TextView)findViewById(R.id.sangdam);							//변호사 상담시간
		mEmail = (TextView)findViewById(R.id.email);								//변호사 이메일
		mHomepage = (TextView)findViewById(R.id.homepage);							//변호사 홈페이지
		mFax = (TextView)findViewById(R.id.fax);									//변호사 팩스
		
        inflater_career = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		//mCaseList =   (ListView)findViewById(R.id.caselist);						//수임사건리스트
        mCaseListView = (LinearLayout)findViewById(R.id.caselistView);
		//mExamList =   (ListView)findViewById(R.id.examlist);						//출신시험 리스트
		mExamListView = (LinearLayout)findViewById(R.id.examlistView);
		//mSchoolList = (ListView)findViewById(R.id.schoollist);					//출신학교리스트
		mSchoolListView = (LinearLayout)findViewById(R.id.schoollistView);
		//mCareerList = (ListView)findViewById(R.id.careerlist);					//경력리스트
		mCareerListView = (LinearLayout)findViewById(R.id.careerlistView);
		//mPaperList =  (ListView)findViewById(R.id.nonmunlist);					//논문리스트
		mPaperListView = (LinearLayout)findViewById(R.id.nonmunlistView);
		
		schoolEmptyMsg = (ViewGroup)findViewById(R.id.schoolEmptyMsg);				//학교정보 없음 메세지
		schoolEmptyMsg.setVisibility(View.GONE);
		
		examEmptyMsg = (ViewGroup)findViewById(R.id.examEmptyMsg);					//출신시험 없음 메세지
		examEmptyMsg.setVisibility(View.GONE);
		
		paperEmptyMsg = (ViewGroup)findViewById(R.id.paperEmptyMsg);				//논문 없음 메세지
		paperEmptyMsg.setVisibility(View.GONE);
		
		caseEmptyMsg = (ViewGroup)findViewById(R.id.caseEmptyMsg);					//수임사건 없음 메세지
		caseEmptyMsg.setVisibility(View.GONE);
		
		careerEmptyMsg = (ViewGroup)findViewById(R.id.careerEmptyMsg);				//경력 없음 메세지
		careerEmptyMsg.setVisibility(View.GONE);
		
		mFieldLinear1 = (LinearLayout)findViewById(R.id.fieldbtnlayout1);
		mFieldLinear2 = (LinearLayout)findViewById(R.id.fieldbtnlayout2);
		mFieldLinear3 = (LinearLayout)findViewById(R.id.fieldbtnlayout3);
		
		mField[0] = (Button) findViewById(R.id.field_btn1);
		mField[1] = (Button) findViewById(R.id.field_btn2);
		mField[2] = (Button) findViewById(R.id.field_btn3);
		mField[3] = (Button) findViewById(R.id.field_btn4);
		mField[4] = (Button) findViewById(R.id.field_btn5);
		mField[5] = (Button) findViewById(R.id.field_btn6);
		mField[6] = (Button) findViewById(R.id.field_btn7);
		mField[7] = (Button) findViewById(R.id.field_btn8);
		mField[8] = (Button) findViewById(R.id.field_btn9);
		mField[9] = (Button) findViewById(R.id.field_btn10);	
	
		for(int i=0 ; i<10; i++)
			mField[i].setVisibility(View.GONE);
		
		mFieldLinear3.setVisibility(View.GONE);
		mFieldLinear2.setVisibility(View.GONE);
		mFieldLinear1.setVisibility(View.GONE);
		feildEmptyMsg = (ViewGroup)findViewById(R.id.feildEmptyMsgLayout);
		feildEmptyMsg.setVisibility(View.GONE);
		
		lawyerImage.setBackgroundResource(R.drawable.white);
		if( large_image.equals("") ){
			//변호사 이미지
			if( is_woman.equals("1") )
				lawyerImage.setBackgroundResource(R.drawable.img_profile_woman);
			else
				lawyerImage.setBackgroundResource(R.drawable.img_profile_man);	
			//lawyerWebView.setVisibility(View.GONE);
		}else{
			ImageLoader.getInstance().displayImage( large_image , lawyerImage, options, animateFirstListener);
		}
		//같은 사무실 변호사 검색
		sameOfficeLawyer = (Button)findViewById(R.id.sameOfficeLawyer);
		sameOfficeLawyer.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LawyerProfile.this,LawyerList.class);
				intent.putExtra("keyword_profile_office", office_id);
				intent.putExtra("keyword_profile_office_keyword", name+" 변호사와 같은 사무실 변호사");
				startActivity(intent);
			}
		});
		
		//같은 시험 변호사 검색
		sameExamLawyer = (Button)findViewById(R.id.sameExamLawyer);
		sameExamLawyer.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LawyerProfile.this,LawyerList.class);
				String title = exam.get(0).getTitle();
				String year = exam.get(0).getExam_Year();
				intent.putExtra("keyword_profile_exam", title );
				//intent.putExtra("keyword_profile_exam_year", year );
				intent.putExtra("keyword_profile_exam_keyword", title+" 변호사");
				startActivity(intent);
			}
		});
		
		//같은 연수원 출신 변호사 검색
		sameInstituteLawyer = (Button)findViewById(R.id.sameInstituteLawyer);
		sameInstituteLawyer.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LawyerProfile.this,LawyerList.class);
				String num = exam.get(0).getTraining_Num();
				intent.putExtra("keyword_profile_institute", num );
				intent.putExtra("keyword_profile_institute_keyword", "연수원 "+num+"기");
				startActivity(intent);
			}
		});
		
		//같은 학교 출신 변호사 검색
		sameSchoolLawyer = (Button)findViewById(R.id.sameSchoolLawyer);
		sameSchoolLawyer.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LawyerProfile.this,LawyerList.class);
				
				for(int i=0; i<school.size(); i++){
					String temp = school.get(i).toString();
					
					if( temp.matches(".*대학교.*") && !temp.matches(".*대학원.*") && !temp.matches(".*부속.*") && !temp.matches(".*부설.*")){
					    int t = temp.indexOf("교"); 
					    if( t == -1 )
					    	t = temp.indexOf("학");		//대학교 또는 대학 검색
					    
					    temp = temp.substring(0,t+1); 
						String schoolIndex = school_id.get(i).toString();
						intent.putExtra("keyword_profile_school", schoolIndex);
						intent.putExtra("keyword_profile_school_keyword", temp);
						//Toast.makeText(context, temp, Toast.LENGTH_SHORT).show();
					}
				}
				startActivity(intent);
			}
		});
		
		//신고 버튼
		mSingo = (Button) findViewById(R.id.singo);
		mSingo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(LawyerProfile.this, "신고", Toast.LENGTH_SHORT	).show();
				Intent intent = new Intent(LawyerProfile.this, SingoActivity.class);
				startActivity(intent);
			}
		});
		
		//분야  버튼
		for(int i=0; i<10 ; i++){
			mField[i].setId(i);
			mField[i].setId(i);
			mField[i].setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					//Toast.makeText(LawyerProfile.this, v.getId()+"", Toast.LENGTH_SHORT).show();
					tempString = mField[v.getId()].getText().toString();
					AlertDialog.Builder builder = new AlertDialog.Builder(LawyerProfile.this);// 여기서 this는 Activity의 this
					builder.setTitle("변호사 검색")        // 제목 설정
					.setMessage( tempString+"(으)로 검색하시겠습니까?")    // 메세지 설정
					.setCancelable(false)      					 // 뒤로 버튼 클릭시 취소 가능 설정
					.setPositiveButton("확인", new DialogInterface.OnClickListener(){       
						 // 확인 버튼 클릭시 설정
						public void onClick(DialogInterface dialog, int whichButton){  
							Intent intent = new Intent(LawyerProfile.this,LawyerList.class);
							intent.putExtra("keyword_home", tempString);
							intent.putExtra("keyword_type", "3");
							Log.v("프로필 취급분야",tempString);
							startActivity(intent);
							dialog.cancel();
						}
					})
					.setNegativeButton("취소", new DialogInterface.OnClickListener(){      
					     // 취소 버튼 클릭시 설정
						public void onClick(DialogInterface dialog, int whichButton){
							dialog.cancel();
						}
					});
					AlertDialog dialog = builder.create();    // 알림창 객체 생성
					dialog.show();    // 알림창 띄우기
				}
			});
		}
		
		//맵 주소 
		mapJuso = (TextView)findViewById(R.id.mapJuso);
		//지도 이미지 클리시
		mapLayout_profile = (ViewGroup)findViewById(R.id.mapLayout_profile);
		mapLayout_profile.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//Toast.makeText(context, "지도 뷰", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LawyerProfile.this, officeMapActivity.class);
				intent.putExtra("lng", longitude);
				intent.putExtra("lat", latitude);
				intent.putExtra("officeName", office_name);
				intent.putExtra("address", address);
				startActivity(intent);
			}
		});
		
		//수임사건 펼치기
		mCaseExpand = (Button)findViewById(R.id.caseExpand);
		mCaseExpand.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !is_caseExpand ){
					mCaseExpand.setText("수임사건 숨기기  ▲");
					is_caseExpand = true;					
					for( int i=3; i<have_case.size(); i++){
						case_item = (LinearLayout) inflater_career.inflate(R.layout.case_list_item, null); 
						title_case = (TextView) case_item.findViewById(R.id.title_case);
						keyword_case = (TextView) case_item.findViewById(R.id.keyword_case);
						title_case.setText( have_case.get(i).getTitle() );
						keyword_case.setText( have_case.get(i).getKeyword() );
						
						title_case.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
						keyword_case.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
						
						mCaseListView.addView(case_item);
						
						case_item.setId(i);
						case_item.setOnClickListener(new OnClickListener(){
							@Override
							public void onClick(View v) {
					   			int pos = v.getId();
					   			Intent intent = new Intent(LawyerProfile.this,LawyerCaseActivity.class);
					   			intent.putExtra("content", have_case.get(pos).getContent() );
					   			intent.putExtra("title", have_case.get(pos).getTitle() );
					   			intent.putExtra("keyword", have_case.get(pos).getKeyword() );
					   			startActivity(intent);
							}
						});
					}			
				}else{
					mCaseExpand.setText("모든 수임사건 펼치기  ▼");
					is_caseExpand = false;		
					for(int i=have_case.size()-1; i > 2 ; i--){
						mCaseListView.removeViewAt(i);
					}				
					
				}
			}
		});
	
	    /*/리스트 아이템 클릭 리스너
		mCaseList.setOnItemClickListener(new OnItemClickListener() {	 
	   		@Override
			 public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
	   			int pos = position;
	   			Intent intent = new Intent(LawyerProfile.this,LawyerCaseActivity.class);
	   			intent.putExtra("content", have_case.get(pos).getContent() );
	   			intent.putExtra("title", have_case.get(pos).getTitle() );
	   			intent.putExtra("keyword", have_case.get(pos).getKeyword() );
	   			startActivity(intent);
	   		}
		});
		*/
		//경력 펼치기 
		mCareerExpand = (Button)findViewById(R.id.careerExpand);					//경력펼침버튼
		mCareerExpand.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !is_careerExpand ){
					mCareerExpand.setText("경력 숨기기  ▲");
					is_careerExpand = true;
					for( int i=3; i<career.size(); i++){
				        career_item = (LinearLayout) inflater_career.inflate(R.layout.career_list_item, null); 
						title_career = (TextView) career_item.findViewById(R.id.title_career);
						date_career = (TextView) career_item.findViewById(R.id.date_career);
						title_career.setText( career.get(i).toString() );
						if(career_date.get(i).toString().endsWith("null") )
							date_career.setText("");
						else
							date_career.setText( career_date.get(i).toString() );	
						
						title_career.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
						date_career.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
						
						mCareerListView.addView(career_item);
					}
				}else{
					for(int i=career.size()-1; i > 2 ; i--){
						mCareerListView.removeViewAt(i);
					}
					mCareerExpand.setText("모든 경력 펼치기  ▼ ");
					is_careerExpand = false;
				}
			}
		});
		
		//논문 펼치기
		mPaperExpand = (Button)findViewById(R.id.paperExpand);
		mPaperExpand.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !is_paperExpand ){
					mPaperExpand.setText("저서 및 논문 숨기기  ▲");
					is_paperExpand = true;
					for( int i=3; i<paper.size(); i++){
				        paper_item = (LinearLayout) inflater_career.inflate(R.layout.career_list_item, null); 
						title_paper = (TextView) paper_item.findViewById(R.id.title_career);
						date_paper = (TextView) paper_item.findViewById(R.id.date_career);
						title_paper.setText( paper.get(i).toString() );
						if(paper_date.get(i).toString().endsWith("null") )
							date_paper.setText("");
						else
							date_paper.setText( paper_date.get(i).toString() );
						
						title_paper.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
						date_paper.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
						
						mPaperListView.addView(paper_item);
					}
				}else{
					for(int i=paper.size()-1; i > 2 ; i--){
						mPaperListView.removeViewAt(i);
					}
					mPaperExpand.setText("모든 저서 및 논문 펼치기  ▼ ");
					is_paperExpand = false;
				}
			}
		});
		
		havecard_text = (TextView)findViewById(R.id.havecard_text);
		//명함받기
		mNamecard = (ImageButton)findViewById(R.id.lawyer_card);	//변호사 명함받기
		mNamecard.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( MainActivity.UserLogin != null)
					new JsonLawyerCardTask().execute();
				else{
					Intent intent = new Intent(LawyerProfile.this,LoginHostActivity.class);
					startActivity(intent);
				}
			}
		});
		
		//이메일
		mEmail.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("mailto:"+email);    
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);    
				it.putExtra(Intent.EXTRA_TEXT, "인투로 앱에서 보고 연락드립니다"); 
				startActivity(it); 
			}
		});
		//홈페이지
		mHomepage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW);
				PackageManager packageManager = getPackageManager();
				Uri uri = Uri.parse(homepage);
				browserIntent.setDataAndType(uri, "text/html");
				List<ResolveInfo> list = packageManager.queryIntentActivities(browserIntent, 0);
				for (ResolveInfo resolveInfo : list) {
				    String activityName = resolveInfo.activityInfo.name;
				    Log.e("activityName", activityName);
				    if (activityName.contains("Browser")) {
				        browserIntent =
				                packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName);
				        ComponentName comp =
				                new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
				        browserIntent.setAction(Intent.ACTION_VIEW);
				        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
				        browserIntent.setComponent(comp);
				        browserIntent.setData(uri);
				        startActivity(browserIntent);
				        break;
				    }
				}
			}
			
		});
		
		//전화하기
		mCall = (Button)findViewById(R.id.call_profile);
		mCall.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LawyerProfile.this,LawyerCallDialog.class);
				intent.putExtra("lawyerIndex", index);
				intent.putExtra("phone", phone);
				startActivity(intent);
			}
		});
		
	    new JsonLoadingTask().execute();
	}
	
	public String creHtmlBody(String imageUrl){
		StringBuffer sb = new StringBuffer("<HTML>");
		sb.append("<HEAD>");
		sb.append("</HEAD>");
		sb.append("<BODY style='margin:0'; padding:0; text-align:center>");
		sb.append("<img width='100%' heigh='100%' src=\""+imageUrl+"\">");
		sb.append("</BODY>");
		sb.append("</HTML>");
		return sb.toString();
	}
	
	//getStringFromUrl : 주어진 URL 페이지를 문자열로 얻는다.
	public String getStringFromUrl(String url) throws UnsupportedEncodingException {
		
		// 입력스트림을 "UTF-8" 를 사용해서 읽은 후, 라인 단위로 데이터를 읽을 수 있는 BufferedReader 를 생성한다.
		BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));
		
		// 읽은 데이터를 저장한 StringBuffer 를 생성한다.
		StringBuffer sb = new StringBuffer();
		
		try {
			// 라인 단위로 읽은 데이터를 임시 저장한 문자열 변수 line
			String line = null;
			
			// 라인 단위로 데이터를 읽어서 StringBuffer 에 저장한다.
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	} // getStringFromUrl
	
	/**
	 *  getInputStreamFromUrl : 주어진 URL 에 대한 입력 스트림(InputStream)을 얻는다.
	 */
	public static InputStream getInputStreamFromUrl(String url) {
		InputStream contentStream = null;
		try {
			//HttpClient 를 사용해서 주어진 URL에 대한 입력 스트림을 얻는다.
			//HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = MainActivity.httpclient.execute( new HttpGet(url) );
			contentStream = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentStream;
	} // getInputStreamFromUrl
	/**
	 * 원격의 데이터를 가지고 JSON 객체를 생성한 다음에 객체에서 데이터 타입별로 저장
	 * 변호사 기본정보 가져오기
	 */
	public void getJsonDefaultInfo() {
		try {
			
			String url = MainActivity.defaultUrl+"/lawyer/"+index+"/default";
			String line = getStringFromUrl(url);
			//Toast.makeText(getApplicationContext(), line, Toast.LENGTH_SHORT).show();
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			
			JSONObject object = new JSONObject(line);
			JSONObject object1 = new JSONObject( object.getString("result") );
			name = object1.getString("name");
			phone = object1.getString("phone");
			home_first_region = object1.getString("first_region");
			home_second_region = object1.getString("second_region");
			birth_year = object1.getString("birth_year");
			birth_date = object1.getString("birth_date");
			consult_time = object1.getString("consult_time");
			email = object1.getString("email");
			homepage = object1.getString("homepage");
			introduce_long = object1.getString("long_intro");
			introduce_short = object1.getString("short_intro");
			large_image = object1.getString("large_image");
			is_woman = object1.getString("is_woman");
			//is_free = object1.getString("is_woman");
			namecard_num = object1.getInt("namecard_num");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // getJsonText
	
	/**
	 * 경력정보 가져오기
	 */
	public void getJsonCareerInfo() {
		
		try {
			String url = MainActivity.defaultUrl+"/lawyer/"+index+"/career";
			String line = getStringFromUrl(url);
		
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			JSONObject object = new JSONObject(line);
			JSONObject object1 = new JSONObject(object.getString("result"));
			
			//전문 분야
			JSONObject inobject1 = new JSONObject(object1.getString("pro_field"));
			JSONArray objectArray1 = new JSONArray(inobject1.getString("items"));
			for( int i = 0; i< objectArray1.length(); i++){
					JSONObject insideObject = objectArray1.getJSONObject(i);
					pro_field.add(insideObject.getString("title"));
					pro_field_date.add(insideObject.getString("get_date"));
			}
			
			//취급분야
			JSONObject inobject2 = new JSONObject(object1.getString("handle_field"));
			JSONArray objectArray2 = new JSONArray(inobject2.getString("items"));
			for( int i = 0; i< objectArray2.length(); i++){
					JSONObject insideObject = objectArray2.getJSONObject(i);
					handle_field.add(insideObject.getString("title"));
			}
			
			//수임사건
			JSONObject inobject3 = new JSONObject(object1.getString("have_case"));
			JSONArray objectArray3 = new JSONArray(inobject3.getString("items"));
			for( int i = 0; i< objectArray3.length(); i++){
					JSONObject insideObject = objectArray3.getJSONObject(i);
					HavecaseDataInfo datainfo = new HavecaseDataInfo();
					datainfo.setTitle( insideObject.getString("title") );
					datainfo.setKeyword( insideObject.getString("keyword") );
					datainfo.setContent( insideObject.getString("content") );
					have_case.add(datainfo);
			}			
			
			//시험정보
			JSONObject inobject4 = new JSONObject(object1.getString("exam"));
			JSONArray objectArray4 = new JSONArray(inobject4.getString("items"));
			for( int i = 0; i< objectArray4.length(); i++){
					JSONObject insideObject = objectArray4.getJSONObject(i);
					ExamDataInfo datainfo = new ExamDataInfo();
					datainfo.setTitle( insideObject.getString("title") );
					datainfo.setExam_Num( insideObject.getString("exam_num") );
					datainfo.setExam_Year( insideObject.getString("exam_year") );
					datainfo.setTraining_num( insideObject.getString("training_num") );
					datainfo.setTraining_year( insideObject.getString("training_year") );
					exam.add(datainfo);
			}		
			
			//경력정보
			JSONObject inobject5 = new JSONObject(object1.getString("career"));
			JSONArray objectArray5 = new JSONArray(inobject5.getString("items"));
			for( int i = 0; i< objectArray5.length(); i++){
					JSONObject insideObject = objectArray5.getJSONObject(i);
					career.add(insideObject.getString("activity"));
					career_date.add(insideObject.getString("period"));
			}		
			
			//학교정보
			JSONObject inobject6 = new JSONObject(object1.getString("school"));
			JSONArray objectArray6 = new JSONArray(inobject6.getString("items"));
			for( int i = 0; i< objectArray6.length(); i++){
					JSONObject insideObject = objectArray6.getJSONObject(i);
					school.add(insideObject.getString("name"));
					school_id.add( insideObject.getString("school_name_id") );
					school_date.add(insideObject.getString("period"));
			}
			
			//저서 및  논문 정보
			JSONObject inobject7 = new JSONObject(object1.getString("paper"));
			JSONArray objectArray7 = new JSONArray(inobject7.getString("items"));
			for( int i = 0; i< objectArray7.length(); i++){
					JSONObject insideObject = objectArray7.getJSONObject(i);
					paper.add(insideObject.getString("title"));
					paper_date.add(insideObject.getString("year"));
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	} // getJsonText
	
	/**
	 * 사무실 정보 가져오기
	 */
	public void getJsonOfficeInfo() {
		
		//내부적으로 문자열 편집이 가능한 StringBuffer 생성자
		//StringBuffer sb = new StringBuffer();	
		try {
			String url = MainActivity.defaultUrl+"/lawyer/"+index+"/office";
			String line = getStringFromUrl(url);
		
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			JSONObject object = new JSONObject(line);
			JSONObject object1 = new JSONObject(object.getString("result"));
			office_id = object1.getString("office_id");
			office_name = object1.getString("name");
			address = object1.getString("address");
			office_phone = object1.getString("phone");
			office_fax = object1.getString("fax");
			office_first_region = object1.getString("first_region");
			office_second_region = object1.getString("second_region");
			map_image = object1.getString("map_image");
			latitude = object1.getDouble("latitude");
			longitude = object1.getDouble("longitude");
					
			//sb.append(object.getString("message"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	} // getJsonText

	/**
	 * 원격의 데이터를 가지고 JSON 객체를 생성한 다음에 객체에서 데이터 타입별로 데이터를 읽어서 StringBuffer에 추가한다.
	 */
	public void getJsonNameCardInfo() {
		
		// 내부적으로 문자열 편집이 가능한 StringBuffer 생성자
		//StringBuffer sb = new StringBuffer();
		
		try {
			String url = MainActivity.defaultUrl+"/lawyer/"+index+"/namecard";
			String line = getStringFromUrl(url);
			
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			JSONObject object = new JSONObject(line);
			haveNamecard = object.getString("isHaveNamecard");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return sb.toString();
	} // getJsonText
	
	// getStringFromUrl : 주어진 URL 페이지를 문자열로 얻는다.
	public String getPostStringFromUrl(String url) throws UnsupportedEncodingException {
		
		// 입력스트림을 "UTF-8" 를 사용해서 읽은 후, 라인 단위로 데이터를 읽을 수 있는 BufferedReader 를 생성한다.
		BufferedReader br = new BufferedReader(new InputStreamReader(getInputPostStreamFromUrl(url), "UTF-8"));
		
		// 읽은 데이터를 저장한 StringBuffer 를 생성한다.
		StringBuffer sb = new StringBuffer();
		
		try {
			// 라인 단위로 읽은 데이터를 임시 저장한 문자열 변수 line
			String line = null;
			
			// 라인 단위로 데이터를 읽어서 StringBuffer 에 저장한다.
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	} // getStringFromUrl
	
	/**
	 *  getInputStreamFromUrl : 주어진 URL 에 대한 입력 스트림(InputStream)을 얻는다.
	 */
	public InputStream getInputPostStreamFromUrl(String url) {
		InputStream contentStream = null;
		// 실제 전송하는 부분
		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
		// 객체 연결 설정 부분, 연결 최대시간 등등
		HttpParams params = MainActivity.httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		
		// Post객체 생성
		HttpPost httpPost = new HttpPost(url);
		
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
			httpPost.setEntity(entity);
			HttpResponse response = MainActivity.httpclient.execute(httpPost);
			contentStream = response.getEntity().getContent();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentStream;
	} // getInputStreamFromUrl	
	public void getLawyerCard(){
		
		try {
			String url = MainActivity.defaultUrl;
			//ec2-54-64-194-131.ap-northeast-1.compute.amazonaws.com/namecard/1?_method=delete
			if( is_namecardSelect==true ){
				url += "/namecard/"+index+"?_method=DELETE";
			}else{
				url += "/namecard/"+index;
			}
			
			String line = getPostStringFromUrl(url);
		
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			JSONObject object = new JSONObject(line);
			mResultNamecard	= object.getString("success");

		} catch (Exception e) {
			e.printStackTrace();
		}
	} // getJsonText
	
	private class JsonLawyerCardTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute(){
			pd = new ProgressDialog(context);
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
			pd.setContentView(R.layout.custom_progress);
		}
		
		@Override
		protected Void doInBackground(String... strs) {
			getLawyerCard();
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.

		@Override
		protected void onPostExecute(Void result) {	
			
			if( mResultNamecard != null && is_namecardSelect==false){
				Toast.makeText(LawyerProfile.this, "명함을 받았습니다", Toast.LENGTH_SHORT).show();
				mNamecard.setBackgroundResource(R.drawable.img_card);
				is_namecardSelect = true;
				namecard_num += 1;
				mNamecard_Num.setText( Integer.toString(namecard_num)+"명");
				havecard_text.setText("명함받음");
			}
			else if( mResultNamecard != null && is_namecardSelect==true ){
				Toast.makeText(LawyerProfile.this, "명함을 삭제했습니다", Toast.LENGTH_SHORT).show();
				mNamecard.setBackgroundResource(R.drawable.img_card_copy);
				is_namecardSelect = false;
				namecard_num -= 1;
				mNamecard_Num.setText( Integer.toString(namecard_num)+"명");
				havecard_text.setText("명함받기");
			}
			else
				Toast.makeText(LawyerProfile.this, "명함 받기를 실패했습니다", Toast.LENGTH_SHORT).show();
			
			pd.dismiss();
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa
	/**
	 *	스레드에서 향상된 AsyncTask 를 이용하여
	 * UI 처리 및 Background 작업 등을 하나의 클래스에서 작업 할 수 있도록 지원해준다.
	 */
	private class JsonLoadingTask extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute(){
			MainActivity.isNetworkStat( context );
			pd = new ProgressDialog(context);
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
			pd.setContentView(R.layout.custom_progress);
		}
		
		@Override
		protected Void doInBackground(String... strs) {
			getJsonDefaultInfo();	//기본정보
			getJsonCareerInfo();	//경력정보
			getJsonOfficeInfo();	//사무실정보
			getJsonNameCardInfo();	//명함 정보
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.

		@Override
		protected void onPostExecute(Void result) {
			//현재 년도 구하기
			int age = 0;
			try{
				long now = System.currentTimeMillis();
				Date date = new Date(now);
				SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
				String strCurYear = CurYearFormat.format(date);
				age = Integer.parseInt(strCurYear)-Integer.parseInt(birth_year)+1;
			}catch(Exception e){
				Log.v("null에러", e+"");
				Toast.makeText(context, "접속 오류 !", Toast.LENGTH_SHORT).show();
				finish();
			}
			//이름
			nameView.setText(name);
			
			//사무실 이름
			officeName.setText(office_name);
			officeName_1.setText(office_name);
			
			if( !consult_time.equals("") )
				consultTime.setText(consult_time);
			else
				consultTime.setText("정보없음");
			
			if( !email.equals("") )
				mEmail.setText(email);
			else
				mEmail.setText("정보없음");
			
			if( !homepage.equals("") )			
				mHomepage.setText(homepage);
			else
				mHomepage.setText("정보없음");
			
			if( !office_fax.equals("") )
				mFax.setText(office_fax);
			else
				mFax.setText("정보없음");
			
			
			//test
			//String t = "http://duduchina.co.kr/wp-content/uploads/2012/03/android-e1330595592340.jpg";
			//large_image = "t";
			
			/*/변호사 이미지
			if( !large_image.equals("") ){	
				ImageLoader.getInstance().displayImage( large_image , lawyerImage, options, animateFirstListener);
			}*/
			
			String url = "http://maps.googleapis.com/maps/api/staticmap?"
					+ "center="+latitude+","+longitude+"&markers=color:blue%7Clabel:"+address+"%7C"+latitude+","+longitude+""
					+ "&zoom=16&size=500x400&markers=color:blue&maptype=roadmap&sensor=false";
			lawyerMapView.loadDataWithBaseURL(null, creHtmlBody(url), "text/html", "utf-8", null);
			
			//lawyerMapView.requestFocus(); 
			//lawyerMapView.setFocusable(false); 
			//lawyerMapView.setFocusableInTouchMode(true);
			//lawyerMapView.loadUrl(url); 
			
			//사무실 지역
			if( office_first_region!=null ){
				mOffice_First_region.setText(office_first_region);
				mOffice_Second_region.setText(office_second_region);
			}else{
				mOffice_First_region.setText("정보없음");
				mOffice_Second_region.setText("정보없음");
			}
				
			//슬로건
			slogun.setText(introduce_short);
			
			//소개글
			//mIntroduce.setTypeface(Typeface.createFromAsset(getAssets(), "Nototo.ttf"));
			if( introduce_long.equals("") )
				mIntroduce.setText("등록된 소개가 없습니다");
			else
				mIntroduce.setText(introduce_long);
			
			
			//생년월일
			if( !birth_date.equals("null") && !birth_date.equals(""))
				mBirth.setText(birth_year+"."+birth_date);
			else
				mBirth.setText(birth_year);
			
			//출신지
			if( !home_first_region.equals("null") && !home_first_region.equals("") )
				mHometown.setText(home_first_region+" "+home_second_region);
			else
				mHometown.setText("정보없음");
				
			//명함 받아간 사람 수
			mNamecard_Num.setText(namecard_num+"명");
			
			
			//내가 명함 받았는지 여부 
			if( MainActivity.UserLogin != null ){
				if( haveNamecard.equals("1") ){
					mNamecard.setBackgroundResource(R.drawable.img_card);
					havecard_text.setText("명함받음");
					is_namecardSelect = true;
				}
				else{
					mNamecard.setBackgroundResource(R.drawable.img_card_copy);
					havecard_text.setText("명함받기");
					is_namecardSelect = false;
				}
			}else{
				mNamecard.setBackgroundResource(R.drawable.img_card_copy);
				havecard_text.setText("명함받기");
			}
			
			//나이 및 성별
			if( is_woman.equals("1") )
				lawyerAgeSex.setText("("+age+"세 / 여자)");
			else
				lawyerAgeSex.setText("("+age+"세 / 남자)");
			
			//수임사건 기록 리스트
			if(have_case.size() == 0){
				caseEmptyMsg.setVisibility(View.VISIBLE);
				mCaseExpand.setVisibility(View.GONE);
			}
			else if( have_case.size() < 4 ){
				//have_case_temp.addAll( have_case );
				mCaseExpand.setVisibility(View.GONE);
				for( int i=0; i<have_case.size(); i++){
					case_item = (LinearLayout) inflater_career.inflate(R.layout.case_list_item, null); 
					title_case = (TextView) case_item.findViewById(R.id.title_case);
					keyword_case = (TextView) case_item.findViewById(R.id.keyword_case);
					title_case.setText( have_case.get(i).getTitle() );
					keyword_case.setText( have_case.get(i).getKeyword() );
					
					title_case.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					keyword_case.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					
					mCaseListView.addView(case_item);
					
					case_item.setId(i);
					case_item.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
				   			int pos = v.getId();
				   			Intent intent = new Intent(LawyerProfile.this,LawyerCaseActivity.class);
				   			intent.putExtra("content", have_case.get(pos).getContent() );
				   			intent.putExtra("title", have_case.get(pos).getTitle() );
				   			intent.putExtra("keyword", have_case.get(pos).getKeyword() );
				   			startActivity(intent);
						}
					});
				}				
			}
			else{
				mCaseExpand.setVisibility(View.VISIBLE);
				for(int i=0; i<3; i++){
					case_item = (LinearLayout) inflater_career.inflate(R.layout.case_list_item, null); 
					title_case = (TextView) case_item.findViewById(R.id.title_case);
					keyword_case = (TextView) case_item.findViewById(R.id.keyword_case);
					title_case.setText( have_case.get(i).getTitle() );
					keyword_case.setText( have_case.get(i).getKeyword() );
					
					title_case.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					keyword_case.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					
					mCaseListView.addView(case_item);
					
					case_item.setId(i);
					case_item.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
				   			int pos = v.getId();
				   			Intent intent = new Intent(LawyerProfile.this,LawyerCaseActivity.class);
				   			intent.putExtra("content", have_case.get(pos).getContent() );
				   			intent.putExtra("title", have_case.get(pos).getTitle() );
				   			intent.putExtra("keyword", have_case.get(pos).getKeyword() );
				   			startActivity(intent);
						}
					});
				}
			}
			
			if( exam.size() == 0)
				examEmptyMsg.setVisibility( View.VISIBLE );
			
			//출신시험
			for( int i=0; i<exam.size(); i++){
		        exam_item = (RelativeLayout) inflater_career.inflate(R.layout.exam_list_item, null); 
				title_exam = (TextView) exam_item.findViewById(R.id.exam_title_item);
				gisu_exam = (TextView) exam_item.findViewById(R.id.exam_gisu_item);
				date_exam = (TextView) exam_item.findViewById(R.id.exam_date_item);
				
				title_exam.setText( exam.get(i).getTitle().toString() );
				gisu_exam.setText( exam.get(i).getExam_Num()+"회" );
				date_exam.setText( exam.get(i).getExam_Year()  );	
				
				title_exam.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
				gisu_exam.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
				date_exam.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
				
				mExamListView.addView(exam_item);
				
				if( exam.get(i).getTitle().equals("사법시험") || exam.get(i).getTitle().equals("사법 시험") ){
					if( !exam.get(i).getTraining_Num().endsWith("null") ){
				        exam_item = (RelativeLayout) inflater_career.inflate(R.layout.exam_list_item, null); 
						title_exam = (TextView) exam_item.findViewById(R.id.exam_title_item);
						gisu_exam = (TextView) exam_item.findViewById(R.id.exam_gisu_item);
						date_exam = (TextView) exam_item.findViewById(R.id.exam_date_item);
						title_exam.setText( "사법 연수원" );
						gisu_exam.setText( " "+exam.get(i).getTraining_Num()+"기 수료"  );
						date_exam.setText( exam.get(i).getTraining_year()  );	
						title_exam.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
						gisu_exam.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
						date_exam.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
						mExamListView.addView(exam_item);
					}
				}
			}
			
			
			if( school.size() == 0)
				schoolEmptyMsg.setVisibility( View.VISIBLE );
			
			//출신학교 리스트
			for( int i=0; i<school.size(); i++){
		        school_item = (LinearLayout) inflater_career.inflate(R.layout.career_list_item, null); 
				title_school = (TextView) school_item.findViewById(R.id.title_career);
				date_school = (TextView) school_item.findViewById(R.id.date_career);
				title_school.setText( school.get(i).toString() );
				date_school.setText( school_date.get(i).toString() );	
				if(school_date.get(i).toString().endsWith("null") )
					date_school.setText("");
				else
					date_school.setText( school_date.get(i).toString() );
				
				title_school.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
				date_school.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
				
				mSchoolListView.addView(school_item);
			}
			
			
			//경력 리스트
			if( career.size() == 0 ){
				careerEmptyMsg.setVisibility(View.VISIBLE);
				mCareerExpand.setVisibility(View.GONE);
			}
			else if( career.size() < 4 ){
				mCareerExpand.setVisibility(View.GONE);
				for( int i=0; i<career.size(); i++){
			        career_item = (LinearLayout) inflater_career.inflate(R.layout.career_list_item, null); 
					title_career = (TextView) career_item.findViewById(R.id.title_career);
					date_career = (TextView) career_item.findViewById(R.id.date_career);
					title_career.setText( career.get(i).toString() );
					if(career_date.get(i).toString().endsWith("null") )
						date_career.setText("");
					else
						date_career.setText( career_date.get(i).toString() );
					
					title_career.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					date_career.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					
					mCareerListView.addView(career_item);
				}
			}
			else{
				mCareerExpand.setVisibility(View.VISIBLE);
				for( int i=0; i<3; i++){
			        career_item = (LinearLayout) inflater_career.inflate(R.layout.career_list_item, null); 
					title_career = (TextView) career_item.findViewById(R.id.title_career);
					date_career = (TextView) career_item.findViewById(R.id.date_career);
					title_career.setText( career.get(i).toString() );
					if(career_date.get(i).toString().endsWith("null") )
						date_career.setText("");
					else
						date_career.setText( career_date.get(i).toString() );
					
					title_career.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					date_career.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					
					mCareerListView.addView(career_item);
				}
			}
			
			//논문 리스트
			if( paper.size() == 0 ){
				paperEmptyMsg.setVisibility(View.VISIBLE);
				mPaperExpand.setVisibility(View.GONE);
			}
			else if( paper.size() < 4 ){
				mPaperExpand.setVisibility(View.GONE);
				for( int i=0; i<paper.size(); i++){
			        paper_item = (LinearLayout) inflater_career.inflate(R.layout.career_list_item, null); 
					title_paper = (TextView) paper_item.findViewById(R.id.title_career);
					date_paper = (TextView) paper_item.findViewById(R.id.date_career);
					title_paper.setText( paper.get(i).toString() );
					if(paper_date.get(i).toString().endsWith("null") )
						date_paper.setText("");
					else
						date_paper.setText( paper_date.get(i).toString() );
					
					title_paper.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					date_paper.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					
					mPaperListView.addView(paper_item);
				}
			}
			else{
				mPaperExpand.setVisibility(View.VISIBLE);
				for( int i=0; i<3; i++){
			        paper_item = (LinearLayout) inflater_career.inflate(R.layout.career_list_item, null); 
					title_paper = (TextView) paper_item.findViewById(R.id.title_career);
					date_paper = (TextView) paper_item.findViewById(R.id.date_career);
					title_paper.setText( paper.get(i).toString() );
					
					if(paper_date.get(i).toString().endsWith("null") )
						date_paper.setText("");
					else
						date_paper.setText( paper_date.get(i).toString() );
					
					title_paper.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					date_paper.setTypeface(Typeface.createFromAsset( getAssets(), "Nototo.ttf.mp3"));
					
					mPaperListView.addView(paper_item);
				}
				
			}
				
			//분야 버튼
			int field_size = pro_field.size()+handle_field.size();
			if( field_size == 0 )
				feildEmptyMsg.setVisibility(View.VISIBLE);
			else if( field_size > 10 ){
				mFieldLinear1.setVisibility(View.VISIBLE);
				mFieldLinear2.setVisibility(View.VISIBLE);
				mFieldLinear3.setVisibility(View.VISIBLE);
			}
			else if( field_size > 7 ){
				mFieldLinear1.setVisibility(View.VISIBLE);
				mFieldLinear2.setVisibility(View.VISIBLE);
				mFieldLinear3.setVisibility(View.VISIBLE);	
			}
			else if( field_size > 4 ){
				mFieldLinear1.setVisibility(View.VISIBLE);
				mFieldLinear2.setVisibility(View.VISIBLE);
			}
			else if( field_size > 0){
				mFieldLinear1.setVisibility(View.VISIBLE);
			}
			
			for(int i=0; i<field_size; i++)
				mField[i].setVisibility(View.VISIBLE);
			
			Log.v("분야", field_size+"" );
			
			for(int i=0; i<pro_field.size(); i++){
				mField[i].setText( pro_field.get(i).toString() );
				mField[i].setBackgroundResource(R.drawable.buttonshape);
			}
		
			for(int i=pro_field.size(); i<(handle_field.size()+pro_field.size()); i++)
				mField[i].setText( handle_field.get(i-pro_field.size()).toString() );
			
			//맵주소
			mapJuso.setText(address);
			
			//로스쿨 변호사시험 출신 연수원 기수 찾기 버튼 없애기
			if( exam.get(0).getTraining_Num() == null	)
				sameInstituteLawyer.setVisibility(View.GONE);	
			else if( exam.get(0).getTraining_Num().equals("null") )
				sameInstituteLawyer.setVisibility(View.GONE);
			else if( exam.get(0).getExam_Num().equals("") )
				sameInstituteLawyer.setVisibility(View.GONE);	
						
			pd.dismiss();
			
		    if( large_image.equals("") && !is_alertCheck){
			    Intent in = new Intent(LawyerProfile.this, LawyerAlertDialog.class);
			    startActivity(in);
		    }
		    
			//스크롤 맨위로 
			scrollView.post(new Runnable() {
			      public void run() {
			          scrollView.scrollTo(0, 0);
			      }
			 });
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa

}
