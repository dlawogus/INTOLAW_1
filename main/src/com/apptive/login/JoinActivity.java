package com.apptive.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import com.apptive.activity.BaseActivity;
import com.apptive.activity.SelectLocal;
import com.apptive.activity.SelectLocal_Join;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.apptive.intolaw.garbageCollection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class JoinActivity extends BaseActivity {
	private ProgressDialog pd;						
	private ImageView mYacgwan;						//약관
	private ImageButton mYacgwanCheck;				//약관동의 버튼
	private ImageView mYacgwan_1;					//개인정보 동의 약관 버튼
	private ImageButton mNext;						//다음 버튼
	private boolean	mYacgwanCheck_ok = false;		//약관동의여부
	private EditText mEditId;						//아이디
	private EditText mEditPass;						//비번
	private EditText mEditPass_Check;				//비번확인
	private Context con;
	
	//JOIN2 화면
	private EditText mEditName;			//이름
	private EditText mEditPhone;		//휴대전화번호
	public static TextView mEditBirth;	//생년월일
	private ImageButton mCheckMan;		//남자 버튼
	private ImageButton mCheckWoman;	//여자 버튼
	private boolean mCheckMan_ok = false;		//남자 버튼 선택
	private boolean	mCheckWoman_ok = false;		//여자 버튼 선택
	public static TextView mEditHome;	//거주지
	public static TextView mEditJob;	//직업
	private ImageButton mJoinOk;		//완료버튼

	private String id, pass, pass_check;			//아이디,패스워드,패스워드확인
	private String mName = 				null;
	private String mPhone = 			null;
	public 	static String mBirth =		null;
	private String mSex = 				null;
	public  static String mHome_first = null;
	public 	static String mHome_second =null;
	public  static String mJob =		null;
	
	private int mJoinResult = 0;	//회원가입 결과	
	private String mJoinMessage;	//회원가입 결과 메세지

	private int mCheckResult = 0; 
	private String mCheckMessage;
	private int is_equal_id = 0;	//똑같은 이메일
	
	 //지역선택 액티비티 선택시 어디서 호출했는지 확인 플래그
	//public static boolean from = false;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    if( MainActivity.deviceHeight <= 900)
	    	setContentView(R.layout.activity_join_800);
	    else
	    	setContentView(R.layout.activity_join);
	    con = this;
	    mEditId = (EditText) findViewById(R.id.join_email);
	    mEditPass = (EditText) findViewById(R.id.join_passwd);
	    mEditPass_Check = (EditText) findViewById(R.id.join_passwd_check);
	    
	    //약관 동의
	    mYacgwanCheck = (ImageButton) findViewById(R.id.yacgwan_check);
	    mYacgwanCheck.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !(mYacgwanCheck_ok) ){
					mYacgwanCheck.setBackgroundResource(R.drawable.img_yacgwan_check2);
					mYacgwanCheck_ok = true;
				}else{
					mYacgwanCheck.setBackgroundResource(R.drawable.img_yacgwan_check);
					mYacgwanCheck_ok = false;
				}
				
			}	
	    });
	    
	    //이용약관 클릭
	    mYacgwan = (ImageView) findViewById(R.id.yacgwan);
	    mYacgwan.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(JoinActivity.this, Yacgwan.class);
				startActivity(intent);
			}  	
	    });
	    
	    mYacgwan_1 = (ImageView) findViewById(R.id.yacgwan_1);
	    mYacgwan_1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(JoinActivity.this, Yacgwan_1.class);
				startActivity(intent);
			}  	
	    });	    
	    
	    //다음버튼
	    mNext = (ImageButton) findViewById(R.id.join_next);
	    mNext.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				id = mEditId.getText().toString();
				id = id.trim();
				pass = mEditPass.getText().toString();
				pass = pass.trim();
				pass_check = mEditPass_Check.getText().toString();
				pass_check = pass_check.trim();
				new JsonCheckTask().execute();
				
			}
	    });    
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
		
		post.add(new BasicNameValuePair("email", id ));			//아이디
		post.add(new BasicNameValuePair("password", pass ));	//패스워드
		post.add(new BasicNameValuePair("name", mName ));		//이름
		post.add(new BasicNameValuePair("is_woman", mSex ));	//성별
		post.add(new BasicNameValuePair("birth", mBirth ));		//생년월일
		post.add(new BasicNameValuePair("first_region", mHome_first ));		//거주지 시
		post.add(new BasicNameValuePair("second_region", mHome_second ));	//거주지 구
		post.add(new BasicNameValuePair("job", mJob ));			//직업
		post.add(new BasicNameValuePair("phone", mPhone ));	//휴대전화번호

		// 연결 HttpClient 객체 생성
		//HttpClient client = new DefaultHttpClient();
		
		// 객체 연결 설정 부분, 연결 최대시간 등등
		HttpParams params = MainActivity.httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		
		// Post객체 생성
		HttpPost httpPost = new HttpPost(url);
		
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
			httpPost.setEntity(entity);
			//client.execute(httpPost);
			HttpResponse response = MainActivity.httpclient.execute(httpPost);
			contentStream = response.getEntity().getContent();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentStream;
	} // getInputStreamFromUrl
	
	public void getJoinFromJSON(){
		//이름 검색
		String url = MainActivity.defaultUrl;
		url += "/user/signup";
		
		try{
			String line = getStringFromUrl(url);
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			JSONObject object = new JSONObject(line);
			mJoinMessage = object.getString("message");
			mJoinResult = object.getInt("success");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 *	스레드에서 향상된 AsyncTask 를 이용하여
	 * UI 처리 및 Background 작업 등을 하나의 클래스에서 작업 할 수 있도록 지원해준다.
	 */
	private class JsonLoadingTask extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute(){
			MainActivity.isNetworkStat( con );
			pd = new ProgressDialog(con);
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
			pd.setContentView(R.layout.custom_progress);
		}
		
		@Override
		protected Void doInBackground(String... strs) {
			getJoinFromJSON();
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.

		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
			if( mJoinResult == 1){
				MainActivity.UserLogin = id;
				MainActivity.name = mName;
				MainActivity.email = id;
				MainActivity.first_region = mHome_first;
				MainActivity.second_region = mHome_second;
				MainActivity.is_woman = mSex;
				MainActivity.birth = mBirth;
				MainActivity.job = mJob;
				finish();
			}
			else{
				finish();
				Toast.makeText(JoinActivity.this, mJoinMessage+"", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(JoinActivity.this, LoginHostActivity.class)); //처음으로 돌아가기
			}
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa
	
	
	// getStringFromUrl : 주어진 URL 페이지를 문자열로 얻는다.
	public String getCheckStringFromUrl(String url) throws UnsupportedEncodingException {
		// 입력스트림을 "UTF-8" 를 사용해서 읽은 후, 라인 단위로 데이터를 읽을 수 있는 BufferedReader 를 생성한다.
		BufferedReader br = new BufferedReader(new InputStreamReader(getInputCheckStreamFromUrl(url), "UTF-8"));
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
	public InputStream getInputCheckStreamFromUrl(String url) {
		InputStream contentStream = null;
		// 실제 전송하는 부분
		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
		
		post.add(new BasicNameValuePair("email", id ));			//아이디
		
		// 객체 연결 설정 부분, 연결 최대시간 등등
		HttpParams params = MainActivity.httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		
		// Post객체 생성
		HttpPost httpPost = new HttpPost(url);
		
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
			httpPost.setEntity(entity);
			//client.execute(httpPost);
			HttpResponse response = MainActivity.httpclient.execute(httpPost);
			contentStream = response.getEntity().getContent();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentStream;
	} // getInputStreamFromUrl
	
	public void getCheckFromJSON(){
		//이름 검색
		String url = MainActivity.defaultUrl;
		url += "/user/validate";
		
		try{
			String line = getStringFromUrl(url);
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			JSONObject object = new JSONObject(line);
			mCheckMessage = object.getString("message");
			JSONObject object_1 = new JSONObject(object.getString("result"));
			mCheckResult = object_1.getInt("is_equal_id");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 *	스레드에서 향상된 AsyncTask 를 이용하여
	 * UI 처리 및 Background 작업 등을 하나의 클래스에서 작업 할 수 있도록 지원해준다.
	 */
	private class JsonCheckTask extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute(){
			MainActivity.isNetworkStat( con );
			pd = new ProgressDialog(con);
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
			pd.setContentView(R.layout.custom_progress);
			
		}
		
		@Override
		protected Void doInBackground(String... strs) {
			getCheckFromJSON();
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.

		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
			
			if( id.equals("") || pass.equals("") || pass_check.equals(""))
				Toast.makeText(JoinActivity.this,"빈칸을 다 채우세요.", Toast.LENGTH_SHORT).show();
			else if( mCheckResult == 1 )
				Toast.makeText(JoinActivity.this, "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
			else if( id.length() > 30 )
				Toast.makeText(JoinActivity.this, "아이디는 30자 이하여야 합니다", Toast.LENGTH_SHORT).show();
			else if( pass.length() < 6 )
				Toast.makeText(JoinActivity.this, "비밀번호는 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
			else if( pass.length() > 15)
				Toast.makeText(JoinActivity.this, "비밀번호는 15자 이하이어야 합니다.", Toast.LENGTH_SHORT).show();			
			else if( !pass.equals(pass_check) )
				Toast.makeText(JoinActivity.this, "비밀번호가 동일하지 않습니다.", Toast.LENGTH_SHORT).show();
			else if( !mYacgwanCheck_ok )
				Toast.makeText(JoinActivity.this, "약관에 동의를 하셔야합니다.", Toast.LENGTH_SHORT).show();
			else{
				
			    if( MainActivity.deviceHeight <= 900)
			    	setContentView(R.layout.activity_join2_800);
			    else
			    	setContentView(R.layout.activity_join2);
				
			    mCheckMan = (ImageButton) findViewById(R.id.join_man);
				mCheckMan.setBackgroundResource(R.drawable.icon_sex_man1);
				mCheckMan.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if( !mCheckMan_ok ){
							mSex = "0";
							mCheckMan.setBackgroundResource(R.drawable.icon_sex_man2);
							mCheckWoman.setBackgroundResource(R.drawable.icon_sex_woman1);
							mCheckMan_ok = true;
							mCheckWoman_ok = false;
						}else{
							mSex = null;
							mCheckMan.setBackgroundResource(R.drawable.icon_sex_man1);
							mCheckWoman.setBackgroundResource(R.drawable.icon_sex_woman1);
							mCheckMan_ok = false;
							mCheckWoman_ok = false;
						}
					}
				});
			
				mCheckWoman = (ImageButton) findViewById(R.id.join_woman);
				mCheckWoman.setBackgroundResource(R.drawable.icon_sex_woman1);
				mCheckWoman.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if( !mCheckWoman_ok ){
							mSex = "1";
							mCheckMan.setBackgroundResource(R.drawable.icon_sex_man1);
							mCheckWoman.setBackgroundResource(R.drawable.icon_sex_woman2);
							mCheckMan_ok = false;
							mCheckWoman_ok = true;
						}else{
							mSex = null;
							mCheckMan.setBackgroundResource(R.drawable.icon_sex_man1);
							mCheckWoman.setBackgroundResource(R.drawable.icon_sex_woman1);
							mCheckMan_ok = false;
							mCheckWoman_ok = false;
						}
					}
				});
				
				mEditHome = (TextView) findViewById(R.id.join_home);
				mEditHome.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(JoinActivity.this, SelectLocal_Join.class);
						//from = true;
						startActivity(intent);
					}
				});
				
				
				//직업선택
				mEditJob = (TextView) findViewById(R.id.join_job);
				mEditJob.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(JoinActivity.this, SelectJob.class);
						startActivity(intent);
					}	
				});
				//생년월일 선택
				mEditBirth = (TextView) findViewById(R.id.join_birth);
				mEditBirth.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(JoinActivity.this, SelectBirth.class);
						startActivity(intent);
					}
				});
				//완료버튼
				mJoinOk = (ImageButton) findViewById(R.id.join_ok);
				mJoinOk.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Toast.makeText(con, "TTT", Toast.LENGTH_SHORT).show();
					    mEditName = (EditText) findViewById(R.id.join_name);
					    mName =  mEditName.getText().toString();
					    mName.trim();
						mEditPhone = (EditText) findViewById(R.id.join_phone);
						mPhone = mEditPhone.getText().toString();
						mPhone.trim();
						if( mName.equals("") || mPhone.equals("") || mBirth==null || mSex==null || mHome_first==null || mJob==null)
							Toast.makeText(JoinActivity.this, "빈칸을 다 채우세요.", Toast.LENGTH_SHORT).show();
						else if( mPhone.length() > 15 )
							Toast.makeText(JoinActivity.this, "휴대폰 번호를 정확하게 입력하세요", Toast.LENGTH_SHORT).show();
						else if( mName.length() > 4 )
							Toast.makeText(JoinActivity.this, "이름은 4자 이하로 입력하세요", Toast.LENGTH_SHORT).show();
						else{
							new JsonLoadingTask().execute();
						}
					}
				});
			
			}
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa

	@Override
   	protected void onDestroy() {
		mName = null;
		mPhone = null;
		mBirth = null;
		mSex = null;
		mHome_first = null;
		mHome_second = null;
		mJob = null; 
		//from = false;

		garbageCollection.recursiveRecycle(getWindow().getDecorView());
		System.gc();
		super.onDestroy();
   	}

}
