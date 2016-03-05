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
import com.apptive.http.HttpPostSendReceive;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
	private ProgressDialog pd;
	private EditText mEditId;
	private EditText mEditPass;
	private TextView mLoginResultText;
	private ImageButton mLoginBtn;
	private int mLoginResult ;
	private String mLoginMessage;
	private Context con;
	private Button mFindPass;

	//로그인 정보
	private String UserLogin;
	private String id;
	private String email;
	private String password;
	private String first_region;
	private String second_region;
	private String name;
	private String is_woman;
	private String birth;
	private String job;
	
	ArrayList<String> sendvalue = new ArrayList<String>();
	ArrayList<String> value = new ArrayList<String>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    if( MainActivity.deviceHeight <= 900)
	    	setContentView(R.layout.activity_login_800);
	    else
	    	setContentView(R.layout.activity_login);
	    
	    con = this;
        
        mLoginResultText = (TextView) findViewById(R.id.loginResult);
        mLoginResultText.setVisibility(View.GONE);
  
        mEditId = (EditText) findViewById(R.id.id);
        mEditPass = (EditText) findViewById(R.id.password);
        
        //비밀번호 찾기
        mFindPass = (Button) findViewById(R.id.findPassword);
        mFindPass.setVisibility(View.GONE);
        mFindPass.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//Toast.makeText(con, "비밀번호 찾기", Toast.LENGTH_SHORT).show();
				//new FindPasswdTask().execute();
				Intent intent = new Intent(LoginActivity.this,FindPasswordActivity.class);
				startActivity(intent);
			}
        });
        
        
	    //로그인 버튼
        mLoginBtn = (ImageButton) findViewById(R.id.loginBtn);
        mLoginBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				email = mEditId.getText().toString();
				email.trim();
				password = mEditPass.getText().toString();
				password.trim();
				new JsonLoadingTask().execute();
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
	
	
	public InputStream getInputStreamFromUrl(String url) {
		InputStream contentStream = null;
		// 실제 전송하는 부분
		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
		post.add(new BasicNameValuePair("email", mEditId.getText().toString()));
		post.add(new BasicNameValuePair("password", mEditPass.getText().toString()));
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
	
	public void getLoginFromJSON(){
		//이름 검색
		String url = MainActivity.defaultUrl;
		url += "/user/login";

		//sendvalue.add( "email" );
		//sendvalue.add( "password" );
		//value.add( mEditId.getText().toString() );
		//value.add( mEditPass.getText().toString() );
		//HttpPostSendReceive postsend = new HttpPostSendReceive(sendvalue,value);
		
		try{
			String line = getStringFromUrl(url);
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			JSONObject object = new JSONObject(line);
			mLoginResult = object.getInt("success");
			mLoginMessage = object.getString("message");
			JSONObject object1 = new JSONObject(object.getString("result"));
			id = 			object1.getString("id");
			email = 		object1.getString("email");
			first_region = 	object1.getString("first_region");
			second_region = object1.getString("second_region");
			name = 			object1.getString("name");
			is_woman = 		object1.getString("is_woman");
			birth = 		object1.getString("birth");
			job = 			object1.getString("job");
			
			//Log.v("test", line);
			
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
			getLoginFromJSON();
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.

		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
			if( mLoginResult == 1 ){
				MainActivity.UserLogin = "in";
				MainActivity.id = id;
				MainActivity.email = email;
				MainActivity.password = password;
				MainActivity.first_region = first_region;
				MainActivity.second_region = second_region;
				MainActivity.name = name;
				MainActivity.is_woman = is_woman;
				MainActivity.birth = birth;
				MainActivity.job = job;
				//드로워 메뉴 갱신.
				finish();
			}else{
				mLoginResultText.setVisibility(View.VISIBLE);
				mLoginResultText.setText("아이디와 비밀번호를 확인하여 주세요");
				
				//Log.v("test", sendvalue.get(0).toString() );
				//Log.v("test", sendvalue.get(1).toString() );
				//Log.v("test", value.get(0).toString() );
				//Log.v("test", value.get(1).toString() );				
				//Toast.makeText(LoginActivity.this, mLoginMessage+"", Toast.LENGTH_SHORT).show();
			}
	
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa

}
