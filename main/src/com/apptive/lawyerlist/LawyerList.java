package com.apptive.lawyerlist;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import com.apptive.activity.BaseActivity;
import com.apptive.adapter.LawyerListAdapter;
import com.apptive.datainfo.LawyerListDataInfo;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.apptive.lawyerprofile.LawyerProfile;
import com.apptive.login.LoginHostActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class LawyerList extends BaseActivity {
	private ProgressDialog pd;
	private Context context;
	private ListView lawyerlist;

	//private ViewGroup keywordLayout;
	//private ViewGroup keywordLayout_in_1;
	//private ViewGroup keywordLayout_in_2;
	private TextView keyword_1;
	
	//private String keyword_str;
	private String keyword_type = 				"1"; 
	private String keyword_home_str = 			null;
	private String keyword_edit_str = 			null;
	private String keyword_profile_exam =	 	null;		//같은 시험출신 변호사 검색
	private String keyword_profile_exam_year =  null;		//같은 시험출신 년도
	private String keyword_profile_exam_keyword=null;		//같은 시험출신 키워드
	private String keyword_profile_office =		null;	 	//같은 사무실 변호사 검색
	private String keyword_profile_office_keyword=null;	 	//같은 사무실 키워드		
	private String keyword_profile_institute = 	null;		//같은 연수원 출신 변호사 검색
	private String keyword_profile_institute_keyword = null;//같은 연수원 키워드
	private String keyword_profile_school = 	null;		//같은 학교출신 변호사 검색
	private String keyword_profile_school_keyword = null;		//같은 학교출신 변호사 검색
	private LawyerListAdapter mLawyerListAdapter;
	private ArrayList<LawyerListDataInfo> lawyerListArray;
	
	private int resultCount = 0;			//검색 변호사 수 
	private int result_NameCount = 0;		//이름 검색 변호사 수
	private int result_OfficeCount = 0;		//사무실 검색 변호사 수 
	private int result_HandlefieldCount = 0;//취급분야 검색 변호사 수 
	private int result_ProfieldCount = 0;	//전문분야 검색 변호사 수 
	private int resultSameCount = 0;		//같은 변호사 수 
	
	private ImageButton mLawyerListBack;			//뒤로가기	
	//private boolean lastItemVisibleFlag = false;  //화면에 리스트의 마지막 아이템이 보여지는지 체크
	private int page = 1;	//페이징 
	//private TextView testText;//테스트
	private ImageView emptyImage;
	private ImageButton emptyBack;

	private TextView lawyerNum_text;
	private LayoutInflater mInflater;
	
	private boolean scrollState = false;		//true가 아래방향
	private boolean is_fail = false;
	private AsyncTask task;
	//private BottomPullToRefreshView pullView2 = null;		//바닥 리플레시
	
	private DisplayImageOptions options;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_lawyerlist);
	    context = this;
	   
	    options = MainActivity.options;
	   	
	    lawyerListArray = new ArrayList<LawyerListDataInfo>();
		lawyerlist = (ListView) findViewById(R.id.lawyerlist);
		mLawyerListAdapter = new LawyerListAdapter(this,lawyerListArray,options);	
	   	//변호사 리스트 푸터등록
		View footer = getLayoutInflater().inflate(R.layout.lawyerlist_footer, null, false);
		lawyerlist.addFooterView(footer);
		//검색한 변호사 수 
		lawyerNum_text = (TextView) findViewById(R.id.lawyerNum_text);	
		lawyerNum_text.setText("");
		
	   	//변호사 리스트 헤더등록
		View header = getLayoutInflater().inflate(R.layout.lawyerlist_header, null, false);
		lawyerlist.addHeaderView(header);
		//키워드 
		keyword_1 = (TextView) findViewById(R.id.keyword_header);	
		keyword_1.setText("");		
		lawyerlist.setAdapter( mLawyerListAdapter );
		mLawyerListAdapter.notifyDataSetInvalidated();

	    //검색결과 없음 이미지
		emptyImage = (ImageView) findViewById(R.id.emptyImage);	
		emptyImage.setVisibility(View.GONE);
		emptyBack = (ImageButton) findViewById(R.id.emptyback);
		emptyBack.setVisibility(View.GONE);
		emptyBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	
	    //넘어온 intent값 저장
	    Intent intent = getIntent();
	    keyword_home_str = 		intent.getStringExtra("keyword_home");	//홈 키워드
	    keyword_edit_str = 		intent.getStringExtra("keyword_edit");	//에디드 창 검색어
	    keyword_type =			intent.getStringExtra("keyword_type");
	    keyword_profile_school =intent.getStringExtra("keyword_profile_school");
	    keyword_profile_school_keyword =intent.getStringExtra("keyword_profile_school_keyword");
	    keyword_profile_exam = 	intent.getStringExtra("keyword_profile_exam");
	    keyword_profile_exam_keyword = intent.getStringExtra("keyword_profile_exam_keyword");
	    keyword_profile_institute = intent.getStringExtra("keyword_profile_institute");
	    keyword_profile_institute_keyword = intent.getStringExtra("keyword_profile_institute_keyword");	    
	    keyword_profile_office = intent.getStringExtra("keyword_profile_office");
	    keyword_profile_office_keyword = intent.getStringExtra("keyword_profile_office_keyword");

	    //뒤로가기
		mLawyerListBack = (ImageButton)findViewById(R.id.back_lawyerlist);
		mLawyerListBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		lawyerlist.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				View firstView = view.getChildAt(0);
				if (firstView == null) 
					return;
				//if (pullView == null)
				///	return;
				if (firstVisibleItem == 0 && firstView.getTop() == 0) {
					Log.w("cranix","firstVisibleItem:"+firstVisibleItem+",firstView.getTop():"+firstView.getTop());
					//pullView.setTop(true);
				}
				else {
					//pullView.setTop(false);
				}
				
				View lastView = view.getChildAt(view.getChildCount()-1);
				if (lastView == null) {
					return;
				}
				if (totalItemCount == firstVisibleItem + visibleItemCount && lastView.getBottom() <= view.getHeight()) {
					//pullView2.setBottom(true);
		        	//바닥에 닿았을 때 
					Log.v("test 스크롤 리스너", "바닥에 닿음");
					
					if( (keyword_home_str != null) && (result_NameCount==10 || result_OfficeCount==10 || result_HandlefieldCount==10 || result_ProfieldCount==10) ){
						
						page++;
						task = new JsonLoadingTask().execute();
					}
					else if( resultSameCount==10 && (keyword_profile_exam!=null || keyword_profile_school!=null || keyword_profile_institute!=null || keyword_profile_office!=null) ){
						
						page++;
						task = new JsonLoadingTask().execute();
					
					}else{
						//변호사 검색수
						if( lawyerListArray.size() >= 1 ){
							if( lawyerListArray.size() < 4 ){
								//딜레이 주기
								Handler mHandler = new Handler();
								mHandler.postDelayed(new Runnable() {
									@Override
									public void run() {
										lawyerNum_text.setText( lawyerListArray.size()+"명이 검색되었습니다.");
										task.cancel(true);
									}
								}, 200);
							}else{
								lawyerNum_text.setText( lawyerListArray.size()+"명이 검색되었습니다.");
								task.cancel(true);
							}
						}
					}
					//Toast.makeText(LawyerList.this, "bbbbbbbottom", Toast.LENGTH_SHORT).show();
				}
				else {
					//pullView2.setBottom(false);
				}
				
			}
		});
		
		/*
        pullView2 = (BottomPullToRefreshView)findViewById(R.id.pull_to_refresh);
        pullView2.setListener(new BottomPullToRefreshView.Listener() {
			@Override
			public void onChangeMode(com.apptive.lawyerlist.BottomPullToRefreshView.MODE mode) {
				Log.w("cranix","pullView2:"+mode);
				switch(mode) {
					case NORMAL:
						lawyerlist.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
						break;
					case PULL:case READY_TO_REFRESH:
						if (pullView2.isBottom()) {
							lawyerlist.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
						}
						break;
					case REFRESH:
						break;
				}
			}
		});	*/	

	    //리스트 아이템 클릭 리스너
	   	lawyerlist.setOnItemClickListener(new OnItemClickListener() {	 
	   		@Override
			 public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
	        		int pos = position - 1;
					String index = lawyerListArray.get(pos).getId();
					Intent intent = new Intent(LawyerList.this,LawyerProfile.class);
					intent.putExtra("index", index );
					intent.putExtra("_from", "list" );
					intent.putExtra("is_woman", lawyerListArray.get(pos).getIsWoman() );
					intent.putExtra("is_premium", lawyerListArray.get(pos).getLargeImage() );
					startActivity(intent);
	   		}
		});
	   	
	   	//쓰레드 동작
	   	new Handler().postDelayed(new Runnable()
	   	{
	   	    @Override
	   	    public void run()
	   	    {
	   	    	task = new JsonLoadingTask().execute();
	   	    }
	   	}, 200);
	   	
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}	
	
	// getStringFromUrl : 주어진 URL 페이지를 문자열로 얻는다.
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
	 * 원격의 데이터를 가지고 JSON 객체를 생성한 다음에 객체에서 데이터 타입별로 데이터를 읽어서 StringBuffer에 추가한다.
	 */
	
	public void getFieldDataFromJSON(){
		try{
			String url = MainActivity.defaultUrl;
			
			if( keyword_home_str != null){
				//분야 검색
				url += "/search/field?field="+keyword_home_str;
				///search/field?field=";
				url += "&page="+Integer.toString(page);
				String line = getStringFromUrl(url);
				
				// 원격에서 읽어온 데이터로 JSON 객체 생성
				JSONObject object = new JSONObject(line);
				JSONObject object1 = new JSONObject(object.getString("result"));
				
				JSONObject inobject = new JSONObject(object1.getString("pro_field"));
				JSONArray objectArray= new JSONArray(inobject.getString("items"));
				for( int i = 0; i< objectArray.length(); i++){
					JSONObject insideObject = objectArray.getJSONObject(i);
					LawyerListDataInfo datainfo = new LawyerListDataInfo();
					datainfo.setId(insideObject.getString("lawyer_id"));
					datainfo.setName(insideObject.getString("name"));
					datainfo.setLocal(insideObject.getString("first_region")+" "+insideObject.getString("second_region"));
					datainfo.setBubin(insideObject.getString("office_name"));
					datainfo.setIs_woman(insideObject.getString("is_woman"));
					datainfo.setLargeImage(insideObject.getString("large_image"));
					datainfo.setSmallImage(insideObject.getString("small_image"));
					datainfo.setIntroduce( insideObject.getString("short_intro") );
					lawyerListArray.add(datainfo);
					
				}
				
				JSONObject inobject_1 = new JSONObject(object1.getString("handle_field"));
				JSONArray objectArray_1= new JSONArray(inobject_1.getString("items"));
				for( int i = 0; i< objectArray_1.length(); i++){
					JSONObject insideObject = objectArray_1.getJSONObject(i);
					LawyerListDataInfo datainfo = new LawyerListDataInfo();
					datainfo.setId(insideObject.getString("lawyer_id"));
					datainfo.setName(insideObject.getString("name"));
					datainfo.setLocal(insideObject.getString("first_region")+" "+insideObject.getString("second_region"));
					datainfo.setBubin(insideObject.getString("office_name"));
					datainfo.setIs_woman(insideObject.getString("is_woman"));
					datainfo.setLargeImage(insideObject.getString("large_image"));
					datainfo.setSmallImage(insideObject.getString("small_image"));
					datainfo.setIntroduce( insideObject.getString("short_intro"));
					lawyerListArray.add(datainfo);
				}
				result_ProfieldCount = objectArray.length();
				result_HandlefieldCount = objectArray_1.length();
				resultCount += result_ProfieldCount + result_HandlefieldCount;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getNameDataFromJSON(){
		//이름 검색
		String url = MainActivity.defaultUrl;
		try{
			if( keyword_home_str != null){
				
				url += "/search/name?name="+keyword_home_str;
				url += "&page="+Integer.toString(page);
				String line = getStringFromUrl(url);
			
				// 원격에서 읽어온 데이터로 JSON 객체 생성
				JSONObject object = new JSONObject(line);
				JSONObject object1 = new JSONObject(object.getString("result"));
				JSONArray objectArray_2 = new JSONArray(object1.getString("items"));
				for( int i = 0; i< objectArray_2.length(); i++){
					JSONObject insideObject = objectArray_2.getJSONObject(i);
					LawyerListDataInfo datainfo = new LawyerListDataInfo();
					datainfo.setId(insideObject.getString("lawyer_id"));
					datainfo.setName(insideObject.getString("name"));
					datainfo.setLocal(insideObject.getString("first_region")+" "+insideObject.getString("second_region"));
					datainfo.setBubin(insideObject.getString("office_name"));
					datainfo.setIs_woman(insideObject.getString("is_woman"));
					datainfo.setLargeImage(insideObject.getString("large_image"));
					datainfo.setSmallImage(insideObject.getString("small_image"));
					datainfo.setIntroduce( insideObject.getString("short_intro"));
					lawyerListArray.add(datainfo);
				}
				result_NameCount = objectArray_2.length();
				resultCount += result_NameCount;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getOfficeDataFromJSON(){
		//사무실 검색
		String url = MainActivity.defaultUrl;
		try{
			if( keyword_home_str != null ){
				url += "/search/officename?officename="+keyword_home_str;
				url += "&page="+Integer.toString(page);
				String line = getStringFromUrl(url);
			
				// 원격에서 읽어온 데이터로 JSON 객체 생성
				JSONObject object = new JSONObject(line);
				JSONObject object1 = new JSONObject(object.getString("result"));
				JSONArray objectArray_3 = new JSONArray(object1.getString("items"));
				for( int i = 0; i< objectArray_3.length(); i++){
					JSONObject insideObject = objectArray_3.getJSONObject(i);
					LawyerListDataInfo datainfo = new LawyerListDataInfo();
					datainfo.setId(insideObject.getString("lawyer_id"));
					datainfo.setName(insideObject.getString("name"));
					datainfo.setLocal(insideObject.getString("first_region")+" "+insideObject.getString("second_region"));
					datainfo.setBubin(insideObject.getString("office_name"));
					datainfo.setIs_woman(insideObject.getString("is_woman"));
					datainfo.setLargeImage(insideObject.getString("large_image"));
					datainfo.setSmallImage(insideObject.getString("small_image"));
					datainfo.setIntroduce( insideObject.getString("short_intro"));
					lawyerListArray.add(datainfo);
				}
				
				result_OfficeCount = objectArray_3.length();
				resultCount += result_OfficeCount;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getSameOfficeDataFromJSON(){		//같은 사무실 변호사 
		String url = MainActivity.defaultUrl;
		try{
			if( keyword_profile_office != null ){
				url += "/search/office/"+keyword_profile_office;
				url += "&page="+Integer.toString(page);
				String line = getStringFromUrl(url);
			
				// 원격에서 읽어온 데이터로 JSON 객체 생성
				JSONObject object = new JSONObject(line);
				JSONObject object1 = new JSONObject(object.getString("result"));
				JSONArray objectArray_1 = new JSONArray(object1.getString("items"));
				for( int i = 0; i< objectArray_1.length(); i++){
					JSONObject insideObject = objectArray_1.getJSONObject(i);
					LawyerListDataInfo datainfo = new LawyerListDataInfo();
					datainfo.setId(insideObject.getString("lawyer_id"));
					datainfo.setName(insideObject.getString("name"));
					datainfo.setLocal(insideObject.getString("first_region")+" "+insideObject.getString("second_region"));
					datainfo.setBubin(insideObject.getString("office_name"));
					datainfo.setIs_woman(insideObject.getString("is_woman"));
					datainfo.setLargeImage(insideObject.getString("large_image"));
					datainfo.setSmallImage(insideObject.getString("small_image"));
					datainfo.setIntroduce( insideObject.getString("short_intro"));
					lawyerListArray.add(datainfo);
				}
				resultSameCount = objectArray_1.length();
				resultCount += resultSameCount;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void getSameSchoolDataFromJSON(){		//같은 학교출신 변호 찾기
		String url = MainActivity.defaultUrl;
		try{
			if( keyword_profile_school != null ){
				url += "/search/school?school="+keyword_profile_school;
				url += "&page="+Integer.toString(page);
				String line = getStringFromUrl(url);
			
				// 원격에서 읽어온 데이터로 JSON 객체 생성
				JSONObject object = new JSONObject(line);
				JSONObject object1 = new JSONObject(object.getString("result"));
				JSONArray objectArray_1 = new JSONArray(object1.getString("items"));
				for( int i = 0; i< objectArray_1.length(); i++){
					JSONObject insideObject = objectArray_1.getJSONObject(i);
					LawyerListDataInfo datainfo = new LawyerListDataInfo();
					datainfo.setId(insideObject.getString("lawyer_id"));
					datainfo.setName(insideObject.getString("name"));
					datainfo.setLocal(insideObject.getString("first_region")+" "+insideObject.getString("second_region"));
					datainfo.setBubin(insideObject.getString("office_name"));
					datainfo.setIs_woman(insideObject.getString("is_woman"));
					datainfo.setLargeImage(insideObject.getString("large_image"));
					datainfo.setSmallImage(insideObject.getString("small_image"));
					datainfo.setIntroduce( insideObject.getString("short_intro"));
					lawyerListArray.add(datainfo);
				}
				resultSameCount = objectArray_1.length();
				resultCount += resultSameCount;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getSameInstituteDataFromJSON(){		//같은 시험출신 변호 찾기
		String url = MainActivity.defaultUrl;
		try{
			if( keyword_profile_institute != null ){
				url += "/search/training?training="+keyword_profile_institute;
				url += "&page="+Integer.toString(page);
				String line = getStringFromUrl(url);
			
				// 원격에서 읽어온 데이터로 JSON 객체 생성
				JSONObject object = new JSONObject(line);
				JSONObject object1 = new JSONObject(object.getString("result"));
				JSONArray objectArray_1 = new JSONArray(object1.getString("items"));
				for( int i = 0; i< objectArray_1.length(); i++){
					JSONObject insideObject = objectArray_1.getJSONObject(i);
					LawyerListDataInfo datainfo = new LawyerListDataInfo();
					datainfo.setId(insideObject.getString("lawyer_id"));
					datainfo.setName(insideObject.getString("name"));
					datainfo.setLocal(insideObject.getString("first_region")+" "+insideObject.getString("second_region"));
					datainfo.setBubin(insideObject.getString("office_name"));
					datainfo.setIs_woman(insideObject.getString("is_woman"));
					datainfo.setLargeImage(insideObject.getString("large_image"));
					datainfo.setSmallImage(insideObject.getString("small_image"));
					datainfo.setIntroduce( insideObject.getString("short_intro"));
					lawyerListArray.add(datainfo);
				}
				resultSameCount = objectArray_1.length();
				resultCount += resultSameCount;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void	getSameExamDataFromJSON(){		//같은 시험출신 변호 찾기
		String url = MainActivity.defaultUrl;
		try{
			if( keyword_profile_exam != null ){
				url += "/search/exam?exam="+keyword_profile_exam;
				url += "&page="+Integer.toString(page);
				String line = getStringFromUrl(url);
			
				// 원격에서 읽어온 데이터로 JSON 객체 생성
				JSONObject object = new JSONObject(line);
				JSONObject object1 = new JSONObject(object.getString("result"));
				JSONArray objectArray_1 = new JSONArray(object1.getString("items"));
				for( int i = 0; i< objectArray_1.length(); i++){
					JSONObject insideObject = objectArray_1.getJSONObject(i);
					LawyerListDataInfo datainfo = new LawyerListDataInfo();
					datainfo.setId(insideObject.getString("lawyer_id"));
					datainfo.setName(insideObject.getString("name"));
					datainfo.setLocal(insideObject.getString("first_region")+" "+insideObject.getString("second_region"));
					datainfo.setBubin(insideObject.getString("office_name"));
					datainfo.setIs_woman(insideObject.getString("is_woman"));
					datainfo.setLargeImage(insideObject.getString("large_image"));
					datainfo.setSmallImage(insideObject.getString("small_image"));
					datainfo.setIntroduce( insideObject.getString("short_intro"));
					lawyerListArray.add(datainfo);
				}
				resultSameCount = objectArray_1.length();
				resultCount += resultSameCount;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  getInputStreamFromUrl : 주어진 URL 에 대한 입력 스트림(InputStream)을 얻는다.
	 */
	public InputStream getInputStreamFromUrl(String url) {
		InputStream contentStream = null;
		try {
			HttpParams params = MainActivity.httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);
			HttpResponse response = MainActivity.httpclient.execute(new HttpGet(url));		
			contentStream = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentStream;
	} // getInputStreamFromUrl
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
			try{
				if( keyword_home_str != null ){
					if( keyword_type.equals("1") )
						getNameDataFromJSON();		//이름으로 변호사 찾기
					else if( keyword_type.equals("2") )
						getOfficeDataFromJSON();	//사무실로 변호사 찾기
					else if( keyword_type.equals("3") )
						getFieldDataFromJSON();		//분야로 변호사 찾기
				}
				else if( keyword_profile_exam != null){
					getSameExamDataFromJSON();		//같은 시험 변호사 찾기
				}
				else if( keyword_profile_office != null){
					getSameOfficeDataFromJSON();	//같은 사무실 변호사 찾기
				}
				else if( keyword_profile_school != null){
					getSameSchoolDataFromJSON();	//같은 학교출신 변호사 찾기
				}
				else if( keyword_profile_institute != null){
					getSameInstituteDataFromJSON();	//같은 연수원 출신 변호사 찾기
				}
			}catch(Exception e){
				Log.v("에러", e+"");
				is_fail = true;
			}
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.

		@Override
		protected void onPostExecute(Void result) {
	
			if( is_fail ){
				finish();
				Toast.makeText(context, "예상치 못한 오류가 발생하였습니다\n다시 시도해주세요", Toast.LENGTH_SHORT).show();
			}
				
			if(resultCount == 0 && page==1 ){
				emptyImage.setVisibility(View.VISIBLE);
				emptyBack.setVisibility(View.VISIBLE);
				emptyImage.setImageResource(R.drawable.img_empty);
				lawyerNum_text.setText("");
			}
			
			//키워드
			if( keyword_edit_str != null){
				if( keyword_edit_str.equals("") )
					keyword_1.setText("'모두'의 검색결과 입니다");
				else
					keyword_1.setText("'"+keyword_edit_str+"'의 검색결과 입니다");
			}
			else if( keyword_home_str != null ){
				if( keyword_home_str.equals("") )
					keyword_1.setText("'모두'의 검색결과 입니다");
				else
					keyword_1.setText("'"+keyword_home_str+"'의 검색결과 입니다");
			}
			else if( keyword_profile_exam != null)
				keyword_1.setText(keyword_profile_exam_keyword);
			else if( keyword_profile_office != null)
				keyword_1.setText(keyword_profile_office_keyword);
			else if( keyword_profile_school != null)
				keyword_1.setText(keyword_profile_school_keyword);
			else if( keyword_profile_institute != null)
				keyword_1.setText(keyword_profile_institute_keyword);
			
			if( lawyerListArray.size() == 0 )
				keyword_1.setText("");
			
			Log.v("리스트 실행횟수",  lawyerListArray.size()+"");
			
			mLawyerListAdapter.notifyDataSetChanged();
			pd.dismiss();
			//mLawyerListAdapter.refreshAdapter(lawyerListArray);
		
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa
	

}