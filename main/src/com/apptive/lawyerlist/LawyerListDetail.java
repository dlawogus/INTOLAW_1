package com.apptive.lawyerlist;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class LawyerListDetail extends BaseActivity {
	private ProgressDialog pd;
	private Context context;
	private ListView lawyerlist;
	
	//private ViewGroup keywordLayout;
	//private ViewGroup keywordLayout_in_1;
	//private ViewGroup keywordLayout_in_2;
	private TextView keyword_1;
	
	private LawyerListAdapter mLawyerListAdapter;
	private ArrayList<LawyerListDataInfo> lawyerListArray;
	private ArrayList<LawyerListDataInfo> tempArray;
	private int resultCount = 0;			//검색 변호사 수 
	private int resultTempCount = 0;		//한번 로딩에 검색되는 변호사 수
	private ImageButton mLawyerListBack;			//뒤로가기
	
	private int page = 1;	//페이징 
	//private TextView testText;//테스트
	private ImageView emptyImage;
	private ImageButton emptyBack;
	private String sex = 			null; 	  	//성별	남자 0 , 여자 1
	private int age = 				0; 	 		//나이 
	private String field = 			null; 		//취급분야
	private String career = 		null;		//경력	판검사
	private String hometown = 		null; 		//출신지
	private String hometownDetail=	null;		//출신지 상세
	private String area = 			null; 		//지역
	private String areaDetail=		null;		//지역 상세
	//private String pay = 			null; 		//상담비		무료 1, 유료 0
	private String group = 			null;		//법무법인		개인 1, 법인 0 
	private String examtype = 		null;		//시험종류
	private String examGisu = 		null;
	private String school = 		null;		//출신학교
	private String schoolname = 	null;
	//private PullToRefreshView pullView = null;

	private TextView lawyerNum_text;
	
	private boolean scrollState = false;		//true가 아래방향
	
	private DisplayImageOptions options;
	
	private AsyncTask task;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_lawyerlist);
	    context = this;
	    
		emptyImage = (ImageView) findViewById(R.id.emptyImage);	//검색결과 없음 이미지
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
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.memoryCacheSize(2 * 1024 * 1024) // 2 Mb
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.diskCacheSize(50 * 1024 * 1024) // 50 Mb
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.writeDebugLogs() // Remove for release app
		.build();
 
		ImageLoader.getInstance().init(config);

		options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.img_man)
			.showImageForEmptyUri(R.drawable.img_man)
			.showImageOnFail(R.drawable.img_man)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.displayer(new RoundedBitmapDisplayer(20))
			.build();
		
		
	    //넘어온 intent값 저장
	    Intent intent = getIntent();
	    sex = intent.getStringExtra("sex");
	    age = intent.getIntExtra("age",0);
	    field = intent.getStringExtra("field");
	    career = intent.getStringExtra("career");
	    hometown = intent.getStringExtra("hometown");
	    hometownDetail = intent.getStringExtra("hometownDetail");
	    area = intent.getStringExtra("area");
	    areaDetail = intent.getStringExtra("areaDetail");
	    group = intent.getStringExtra("group");
	    examtype = intent.getStringExtra("examtype");
	    examGisu = intent.getStringExtra("examGisu");
	    school = intent.getStringExtra("school");
	    schoolname = intent.getStringExtra("schoolname");
	    
		if( field == null ){}
		else if( field.equals("모든 분야") )
			field = null;
		if( hometown == null){}
		else if( hometown.equals("모든 지역") )
			hometown = null;
		if( hometownDetail == null){}
		else if( hometownDetail.equals("") || hometownDetail.equals("전체") )
			hometownDetail = null;
		if( area == null){}
		else if( area.equals("모든 지역") )
			area = null;
		if( areaDetail == null){}
		else if( areaDetail.equals("") || areaDetail.equals("전체") )
			areaDetail = null;
	    //Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
	    
	    lawyerListArray = new ArrayList<LawyerListDataInfo>();
	    tempArray = new ArrayList<LawyerListDataInfo>();
	    lawyerlist = (ListView)findViewById(R.id.lawyerlist);
	    mLawyerListAdapter = new LawyerListAdapter(this,lawyerListArray, options);
	   	
	    //변호사 리스트 푸터등록
		View footer = getLayoutInflater().inflate(R.layout.lawyerlist_footer, null, false);
		lawyerlist.addFooterView(footer);
		//검색한 변호사 수 
		lawyerNum_text = (TextView) findViewById(R.id.lawyerNum_text);	
		lawyerNum_text.setText("");
		
	   	//변호사 리스트 헤더등록
		View header = getLayoutInflater().inflate(R.layout.lawyerlist_header, null, false);
		lawyerlist.addHeaderView(header);
		keyword_1 = (TextView) findViewById(R.id.keyword_header);	
		keyword_1.setText("");		
	    
		lawyerlist.setAdapter(mLawyerListAdapter);
		
		//뒤로가기
		mLawyerListBack = (ImageButton)findViewById(R.id.back_lawyerlist);
		mLawyerListBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		/*
		lawyerlist.setOnTouchListener(new OnTouchListener() {
			boolean firstDragFlag = true;
			boolean dragFlag = false;   //현재 터치가 드래그 인지 확인
			float startYPosition = 0;       //터치이벤트의 시작점의 Y(세로)위치
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        switch (event.getAction()) {
		        case MotionEvent.ACTION_MOVE:       //터치를 한 후 움직이고 있으면
		            dragFlag = true;
		            if(firstDragFlag) {     //터치후 계속 드래그 하고 있다면 ACTION_MOVE가 계속 일어날 것임으로 무브를 시작한 첫번째 터치만 값을 저장함
		                startYPosition = event.getY(); //첫번째 터치의 Y(높이)를 저장
		                firstDragFlag= false;   //두번째 MOVE가 실행되지 못하도록 플래그 변경
		            }
		            break;
		        case MotionEvent.ACTION_UP : 
		            float endYPosition = event.getY();
		            firstDragFlag= true;
		 
		            if(dragFlag) {  //드래그를 하다가 터치를 실행
		                // 시작Y가 끝 Y보다 크다면 터치가 아래서 위로 이루어졌다는 것이고, 스크롤은 아래로내려갔다는 뜻이다.
		                // (startYPosition - endYPosition) > 10 은 터치로 이동한 거리가 10픽셀 이상은 이동해야 스크롤 이동으로 감지하겠다는 뜻임으로 필요하지 않으면 제거해도 된다.
		                if((startYPosition > endYPosition) && (startYPosition - endYPosition) > 10) {
		                    //TODO 스크롤 다운 시 작업
		                	//Toast.makeText(LawyerList.this, "TTT", Toast.LENGTH_SHORT).show();
		                	if( scrollState == false ){
			                	//Animation ani = AnimationUtils.loadAnimation(LawyerListDetail.this, R.anim.fadeout);
			                	//keywordLayout.startAnimation(ani);
			                	//keywordLayout.setVisibility(View.GONE);
			                	scrollState = true;
		                	}
		                } 
		                //시작 Y가 끝 보다 작다면 터치가 위에서 아래로 이러우졌다는 것이고, 스크롤이 올라갔다는 뜻이다.
		                else if((startYPosition < endYPosition) && (endYPosition - startYPosition) > 10) {
		                    //TODO 스크롤 업 시 작업
		                	//Toast.makeText(LawyerList.this, "ddde", Toast.LENGTH_SHORT).show();
		                	if( scrollState == true ){	//스크롤이 반대 방향으로 움직였을때만 동작
		                		//Animation ani = AnimationUtils.loadAnimation(LawyerListDetail.this, R.anim.fadein);
		                		//keywordLayout.startAnimation(ani);
		                		//keywordLayout.setVisibility(View.VISIBLE);
		                		scrollState = false;
		                	}
		                }
		            }
		            startYPosition = 0.0f;
		            endYPosition = 0.0f;
		            dragFlag = false;
		            break;
		        }
		        //pullView2.touchDelegate(v, event);
		        return false;
		    }
		});
		*/

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
					if( resultTempCount == 10 ){
						//tempArray =  new ArrayList<LawyerListDataInfo>();
						page++;
						task = new JsonLoadingTask().execute();
					}else{
						//변호사 검색수
						if( lawyerListArray.size() >= 1){
							if( lawyerListArray.size() < 4 ){
								//딜레이 주기
								Handler mHandler = new Handler();
								mHandler.postDelayed(new Runnable() {
									@Override
									public void run() {
										lawyerNum_text.setText( lawyerListArray.size()+"명이 검색되었습니다.");
										task.cancel(true);
									}
								}, 500);
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
		      
	    //리스트 아이템 클릭 리스너
	   	lawyerlist.setOnItemClickListener(new OnItemClickListener() {	 
	   		@Override
			 public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
            		int pos = position - 1; 
    				String index = lawyerListArray.get(pos).getId();
    				Intent intent = new Intent(LawyerListDetail.this,LawyerProfile.class);
    				intent.putExtra("index", index );
    				intent.putExtra("_from", "detail" );
    				intent.putExtra("is_woman", lawyerListArray.get(pos).getIsWoman() );  	
    				intent.putExtra("is_premium", lawyerListArray.get(pos).getLargeImage() );
    				startActivity(intent);

	   		}
		});
	   	
	   	//변호사 리스트 푸터등록
		//View footer = getLayoutInflater().inflate(R.layout.lawyerlist_footer, null, false);
		//lawyerlist.addFooterView(footer);
	
		//검색한 변호사 수 
		//lawyerNum_text = (TextView) findViewById(R.id.lawyerNum_text);	
		
		/*
        pullView = (PullToRefreshView)findViewById(R.id.pull_to_refresh);
        pullView.setListener(new Listener() {
			@Override
			public void onChangeMode(MODE mode) {
				Log.w("cranix","pullView:"+mode);
				switch(mode) {
					case NORMAL:
						lawyerlist.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);	
						break;
					case PULL: case READY_TO_REFRESH:
						if (pullView.isTop()) {
							lawyerlist.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);	
						}
						break;
					case REFRESH:
						//pullView.completeRefresh();
						break;
				}
			}
		});
		*/
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

	public void getDetailDataFromJSON(){
		//법인 검색
		String url = MainActivity.defaultUrl;
		try{
				
			url += "/search/detail";
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
				lawyerListArray.add(datainfo);
			}
			resultTempCount = objectArray_1.length();
			resultCount += resultTempCount;
		}catch(Exception e) {
			e.printStackTrace();
		}
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
	 *  getInputStreamFromUrl : 주어진 URL 에 대한 입력 스트림(InputStream)을 얻는다.
	 */
	public InputStream getInputStreamFromUrl(String url) {
		InputStream contentStream = null;
		// 실제 전송하는 부분
		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
		
		if( sex != null )
			post.add(new BasicNameValuePair("is_woman", sex));					//1
		if( examtype != null)
			post.add(new BasicNameValuePair("exam", examtype));					//2
		if( examGisu != null)
			post.add(new BasicNameValuePair("exam_num", examGisu));				//3
		if( group != null)
			post.add(new BasicNameValuePair("is_single", group));				//4
		if( hometown != null)
			post.add(new BasicNameValuePair("first_home", hometown));			//5
		if( hometownDetail != null)
			post.add(new BasicNameValuePair("second_home", hometownDetail));	//6
		if( career != null)
			post.add(new BasicNameValuePair("career", career));					//7
		if( area != null)
			post.add(new BasicNameValuePair("first_region", area));				//8
		if( areaDetail != null)
			post.add(new BasicNameValuePair("second_region", areaDetail));		//9
		if( field != null)
			post.add(new BasicNameValuePair("field", field));					//10
		if( school != null){
			post.add(new BasicNameValuePair("school", school));					//11
		}
		if( age != 0 )
			post.add(new BasicNameValuePair("age", Integer.toString(age)));		//15
		
		post.add(new BasicNameValuePair("page", Integer.toString(page) ));
		
		
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
			getDetailDataFromJSON();
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.

		@Override
		protected void onPostExecute(Void result) {
			if(resultCount == 0 && page==1 ){
				emptyImage.setVisibility(View.VISIBLE);
				emptyBack.setVisibility(View.VISIBLE);
				emptyImage.setImageResource(R.drawable.img_empty);
			}
			
			//리스트에 중복되는 변호사 제거  
			/*
			HashSet<LawyerListDataInfo> hs = new HashSet<LawyerListDataInfo>(tempArray);
			Iterator it = hs.iterator();
			while( it.hasNext() ){ 
				lawyerListArray.add( (LawyerListDataInfo)it.next() ); 
			} */
			
			//Toast.makeText(context, lawyerListArray.size()+"", Toast.LENGTH_SHORT).show();
			//변호사 검색수
			//lawyerNum_text.setText( lawyerListArray.size()+"명이 검색되었습니다." );
			
			//Toast.makeText(context, examtype, Toast.LENGTH_SHORT).show();
			Log.v( "age :", ""+Integer.toString(age) );
			Log.v( "area :", ""+area );
			Log.v( "area2 :", ""+areaDetail );
			Log.v( "home :", ""+hometown );
			Log.v( "home2 :",""+ hometownDetail );
			Log.v( "field :", ""+field );
			Log.v( "sex :", ""+sex );
			Log.v( "career :", ""+career );
			Log.v( "schoolNameId :",  ""+school);
			//Log.v( "school_kind :", ""+school_kind);			
			Log.v( "examtype :", ""+examtype );
			Log.v( "examGisu :", ""+examGisu );
			Log.v( "group :", ""+group );
			
			int i = 0;
			String str = "";
			if( age != 0 ){
				//keyword_1.setText( Integer.toString(age)+"대" );
				str = "'"+Integer.toString(age)+"대'" ;
				i++;
			}
			if( area != null ){
				if( i == 0){
					if( areaDetail != null)
						str += "'"+area +" "+areaDetail+"'";
					else
						str += area;
				}else{
					if( areaDetail != null)
						str += ", '"+area +" "+areaDetail+"'";
					else		
						str += ", '"+area+"'";
				}
				i++;
			}
			if( hometown != null ){
				if( i == 0 ){
					if( hometownDetail != null)
						str += "'"+hometown +" "+hometownDetail+"'";
					else
						str += "'"+hometown+"'";
				}else{
					if( hometownDetail != null)
						str += ", '"+hometown +" "+hometownDetail+"'";
					else
						str += ", '"+hometown+"'";					
				}
				i++;
			}
			if( field != null ){
				if( i == 0)
					str += "'"+field+"'";
				else
					str += ", '"+field+"'";
				i++;
			}
			if( sex != null ){
				if( i == 0){
					if( sex.equals("1") )
						str += "'여자'";
					else
						str += "'남자'";
				}else{
					if( sex.equals("1") )
						str += ", '여자'";
					else
						str += ", '남자'";
				}
				i++;
			}
			if( career != null ){
				if( i == 0 )
					str += "'"+career+"'";
				else
					str += ", '"+career+"'";
				i++;
			}
			if( examtype != null ){
				if( i == 0){
					if( examGisu != null )
						str += "'"+examtype +" "+examGisu+"회'" ;
					else
						str += "'"+examtype+",";
				}else{
					if( examGisu != null )
						str += ", '"+examtype +" "+examGisu+"회'" ;
					else
						str += ", '"+examtype+"'";
				}
				i++;
			}
			if( group != null ){
				if( i == 0){
					if( group.equals("1") )
						str += "'개인사무소'";
					else
						str += "'단체사무소'";
				}else{
					if( group.equals("1") )
						str += ", '개인사무소'";
					else
						str += ", '단체사무소'";
				}
				i++;
			}
			if( school != null ){
				if( i == 0)
					str += schoolname;
				else
					str += ", "+schoolname;
				i++;
			}

			if( lawyerListArray.size() == 0 )
				keyword_1.setText("");
			else{
				if( str.equals("") )
					keyword_1.setText("'모두'의 검색결과 입니다");
				else
					keyword_1.setText(str+"의 검색결과 입니다");
			}
			
			//if( i > 4 )
				//keywordLayout_in_2.setVisibility(View.VISIBLE);
		
			/*
			keyword_1
			keyword_2
			keyword_3
			keyword_4 
			keyword_5
			keyword_6
			keyword_7
			keyword_8
			keyword_9
			*/
			
			mLawyerListAdapter.notifyDataSetChanged();
			pd.dismiss();
			
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa

	
}
