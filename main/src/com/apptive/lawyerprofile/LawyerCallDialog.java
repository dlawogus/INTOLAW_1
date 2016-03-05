package com.apptive.lawyerprofile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import com.apptive.activity.Actionplan;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import android.R.style;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class LawyerCallDialog extends Activity {
	private ImageButton mCall;
	private ImageButton mCancel;
	private ViewGroup Help1;
	private ViewGroup Help2;

	private String phone;
	private String index;
	private int mResult;

	private boolean mCallBtnSelect = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.lawyer_call_dialog);
	    
	    //투명 만들기
	    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	    
	    Intent intent = getIntent();
	    phone = intent.getStringExtra("phone");
	    index = intent.getStringExtra("lawyerIndex");
	 
	    Log.v("index",index+"");
	    //전화하기
	    mCall = (ImageButton)findViewById(R.id.call_dialog_btn);
	    mCall.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
				if( phone == null ){
					Toast.makeText(LawyerCallDialog.this, "전화번호 정보가 없습니다", Toast.LENGTH_LONG).show();
				}else{
					if( phone.equals("") ){
						Toast.makeText(LawyerCallDialog.this, "전화번호 정보가 없습니다", Toast.LENGTH_LONG).show();
					}else{
						new JsonLoadingTask().execute();
					}
				}
				
			}
	    });
	    
	    //닫기
	    mCancel = (ImageButton)findViewById(R.id.call_dialog_cancel);
	    mCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
	    });
	    
	    //도움말 1
	    Help1 = (ViewGroup)findViewById(R.id.call_help1);
	    Help1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LawyerCallDialog.this, Actionplan.class);
				intent.putExtra("actionplan", 1);
				startActivity(intent);
			}
	    });
	    
	    //도움말 2
	    Help2 = (ViewGroup)findViewById(R.id.call_help2);
	    Help2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LawyerCallDialog.this, Actionplan.class);
				intent.putExtra("actionplan", 2);
				startActivity(intent);
			}
	    });	    
	    
	}
	
	
	//액티비티 영역밖 클릭시 다이얼로그 종료 방지
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
		Rect dialogBounds = new Rect();
		getWindow().getDecorView().getHitRect(dialogBounds);
		if( !dialogBounds.contains((int)ev.getX(),(int)ev.getY())){
			return false;
		}
		return super.dispatchTouchEvent(ev);
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
	
	public void getCallFromJSON(){
		//이름 검색
		String url = MainActivity.defaultUrl;
		url += "/lawyer/"+index+"/call";
		
		try{
			String line = getStringFromUrl(url);
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			JSONObject object = new JSONObject(line);
			mResult = object.getInt("success");

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
		protected void onPreExecute(){}
		
		@Override
		protected Void doInBackground(String... strs) {
			getCallFromJSON();
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.

		@Override
		protected void onPostExecute(Void result) {
			if( mResult == 1 ){
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
				startActivity(intent);
				Log.v("전화걸기","성공");
			}
			else
				Toast.makeText(LawyerCallDialog.this, "인터넷 연결을 확인하세요", Toast.LENGTH_SHORT).show();
	
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa
}
