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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import com.apptive.activity.BaseActivity;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FindPasswordActivity extends BaseActivity {
	private ProgressDialog pd;
	private EditText email_edit_login;
	private Button	check;
	private Context con;
	private String result;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.login_findpassword);
	    con = this;
	
	    //투명 만들기
	    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));	    
	    
	    email_edit_login = (EditText)findViewById(R.id.email_edit_login);
	    
	    check = (Button)findViewById(R.id.check_login);
	    check.setOnClickListener(new OnClickListener(){
	    	@Override
	    	public void onClick(View v) {
	    		new FindPasswdTask().execute();
	    		
	    	}
	    });
    
	}
	
	// getStringFromUrl : 주어진 URL 페이지를 문자열로 얻는다.
	public String getFindStringFromUrl(String url) throws UnsupportedEncodingException {
		
		// 입력스트림을 "UTF-8" 를 사용해서 읽은 후, 라인 단위로 데이터를 읽을 수 있는 BufferedReader 를 생성한다.
		BufferedReader br = new BufferedReader(new InputStreamReader(getFindInputStreamFromUrl(url), "UTF-8"));
		
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
	
	
	public InputStream getFindInputStreamFromUrl(String url) {
		InputStream contentStream = null;
		// 실제 전송하는 부분
		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
		post.add(new BasicNameValuePair("email", email_edit_login.getText().toString() ));
		//post.add(new BasicNameValuePair("password", mEditPass.getText().toString()));
		
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
	
	public void getFindPassword(){
		//이름 검색
		String url = "http://www.lawoafactory.com/find.php";
		try{
			String line = getFindStringFromUrl(url);
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			
			result = line;
			Log.v("test", line);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 *	스레드에서 향상된 AsyncTask 를 이용하여
	 * UI 처리 및 Background 작업 등을 하나의 클래스에서 작업 할 수 있도록 지원해준다.
	 */
	private class FindPasswdTask extends AsyncTask<String, Void, Void> {
		
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
			getFindPassword();
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.

		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
						
			if( result != null )
				finish();

		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa

}
