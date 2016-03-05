package com.apptive.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeActivity extends BaseActivity {

	private ProgressDialog pd;
	//private TextView mNoticeText_title;
	//private TextView mNoticeText_content;
	//private TextView mNoticeText_date;
	//private WebView mWebView;
	private ImageView mNoticeImg;
	
	private Context context;
	private Button mNotice_Ok;
	private Button mNotice_donView;
	
	private String title;
	private String content;
	private String img_notice;
	private String date;
	//private Boolean mNoticeNew;
	//private int mNotice_cnt;
	private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	
	// 값 저장하기
	private void savePreferences() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean("noticeNew", MainActivity.noticeNew);
		editor.putInt("notice_cnt", MainActivity.notice_cnt);
		editor.commit();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //if(MainActivity.noticeNew==false)
	    //	finish();
	    setContentView(R.layout.activity_notice);
	    context = this;
	    
		options = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.displayer(new RoundedBitmapDisplayer(20))
			.build();
	    
	    //mNoticeText_title = (TextView) findViewById(R.id.notice_title);
	    //mNoticeText_content = (TextView) findViewById(R.id.notice_content);
	    //mNoticeText_date = (TextView) findViewById(R.id.notice_date);
	    new JsonLoadingTask().execute();
	    
	    //mWebView = (WebView) findViewById(R.id.noticeWebview);
	    mNoticeImg = (ImageView) findViewById(R.id.noticeView);

	    //확인
	    mNotice_Ok = (Button)findViewById(R.id.notice_ok);
	    mNotice_Ok.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
	    });
	    
	    //다시 보지 않기
	    mNotice_donView = (Button)findViewById(R.id.notice_donView);
	    mNotice_donView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.noticeNew = false;
				savePreferences();
				finish();
			}
	    });
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
	public void getJsonText() {
		
		// 내부적으로 문자열 편집이 가능한 StringBuffer 생성자
		//StringBuffer sb = new StringBuffer();
		
		try {
			String url = MainActivity.defaultUrl;
			url += "/notice";
			String line = getStringFromUrl(url);
			
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			JSONObject object = new JSONObject(line);
			JSONObject inobject = new JSONObject(object.getString("result"));
			
			//새로운 뉴스 
			int temp_cnt = Integer.parseInt( inobject.getString("item_cnt") );
			if( temp_cnt > MainActivity.notice_cnt ){
				MainActivity.noticeNew = true;
				MainActivity.notice_cnt = temp_cnt;
				savePreferences();
			}
		    
			JSONArray objectArray= new JSONArray(inobject.getString("items"));
		
			//맨 마지막 정보만 받음
			JSONObject insideObject = objectArray.getJSONObject(objectArray.length()-1);
			title = insideObject.getString("title");
			content = insideObject.getString("content");
			img_notice = insideObject.getString("imageURL");
			date = insideObject.getString("create_time");

		} catch (Exception e) {
			e.printStackTrace();
		}
		//return sb.toString();
	} // getJsonText
	
	/**
	 *  getInputStreamFromUrl : 주어진 URL 에 대한 입력 스트림(InputStream)을 얻는다.
	 */
	public InputStream getInputStreamFromUrl(String url) {
		InputStream contentStream = null;
		try {
			// HttpClient 를 사용해서 주어진 URL에 대한 입력 스트림을 얻는다.
			//HttpClient httpclient = new DefaultHttpClient();
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
			getJsonText();
			return null;
		} // doInBackground : 백그라운드 작업을 진행한다.
		@Override
		protected void onPostExecute(Void result) {
		    if(MainActivity.noticeNew==false)
		    		finish();
		    
			//String t = "http://duduchina.co.kr/wp-content/uploads/2012/03/android-e1330595592340.jpg";	
			//mNoticeText_content.setText(content);
			//mWebView.loadDataWithBaseURL(null, creHtmlBody(img_notice), "text/html", "utf-8", null);
			//mWebView.loadDataWithBaseURL(null, creHtmlBody(t), "text/html", "utf-8", null);
		    ImageLoader.getInstance().displayImage( img_notice , mNoticeImg , options, animateFirstListener);
			
			pd.dismiss();
		} // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
	} // JsonLoa
}
