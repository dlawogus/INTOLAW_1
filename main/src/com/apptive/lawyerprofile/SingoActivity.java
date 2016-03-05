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
import com.apptive.activity.BaseActivity;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager.BackStackEntry;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SingoActivity extends BaseActivity {
	private ProgressDialog pd;
	private Context con;
	private EditText mSingo_email;
	private EditText mSingo_title;
	private EditText mSingo_contents;
	private ImageButton mBack;
	private Button mSend;
	
	private int mResult;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.singo_profile);

	    con = this;
		mSingo_email = (EditText) findViewById(R.id.singo_email);
		mSingo_email.setText(MainActivity.email);
		mSingo_title = (EditText) findViewById(R.id.singo_title);
		mSingo_contents = (EditText) findViewById(R.id.singo_contents);
		
		//뒤로가기
		mBack = (ImageButton) findViewById(R.id.back_singo);
		mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		//보내기 버튼
		mSend = (Button) findViewById(R.id.send_singo);
		mSend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
			
				if( MainActivity.UserLogin == null ){
					Toast.makeText(SingoActivity.this, "로그인을 하셔야 됩니다.", Toast.LENGTH_LONG	).show();
				}else{
					String email = mSingo_email.getText().toString();
					String title = mSingo_title.getText().toString();
					String contents = mSingo_contents.getText().toString();
					
					//String emailAddress[] = {"dlawogus1@naver.com"};
			
					Uri uri = Uri.parse("mailto:intolaw@naver.com");
					Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
					intent.putExtra(Intent.EXTRA_SUBJECT, title);
					intent.putExtra(Intent.EXTRA_TEXT, "답변 받을 메일주소: "+email+"\n"+contents);
					startActivity(intent);
				}
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
		String device_info = "기기명: "+Build.DEVICE+"\n"+		//기기정보
							"제조사: "+Build.MANUFACTURER+"\n"+	//제조사
							"버전: "+Build.VERSION.RELEASE;		//안드로이드 버전

		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
		post.add(new BasicNameValuePair("email",mSingo_email.getText().toString() ));
		post.add(new BasicNameValuePair("title", mSingo_title.getText().toString() ));
		post.add(new BasicNameValuePair("content", mSingo_contents.getText().toString() ));
		post.add(new BasicNameValuePair("device_info", device_info));
		
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
	
	public void getSendFromJSON(){
		//이름 검색
		String url = MainActivity.defaultUrl;
		url += "/question";
		
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
		protected void onPreExecute(){
			MainActivity.isNetworkStat( SingoActivity.this );
			pd = new ProgressDialog(con);
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
			pd.setContentView(R.layout.custom_progress);
		}
		
		@Override
		protected Void doInBackground(String... strs) {
			getSendFromJSON();
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.

		@Override
		protected void onPostExecute(Void result) {
			if( mResult == 1 ){
				finish();
	        	Toast.makeText(con,"빠르게 답변 드리겠습니다.", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(con,"인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
			}
			pd.dismiss();
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa
}