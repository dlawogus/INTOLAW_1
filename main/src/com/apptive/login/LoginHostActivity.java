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
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class LoginHostActivity extends TabActivity {
    private ImageButton mBack;
	
	public void finish(){
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}
 
	// 값 저장하기
	private void savePreferences() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("UserLogin", MainActivity.UserLogin);
		editor.commit();
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
	    if( MainActivity.deviceHeight <= 900 )
	    	setContentView(R.layout.activity_login_host_800);
	    else
	    	setContentView(R.layout.activity_login_host);
	    
	    final TabHost tabhost = getTabHost();
	    
	    TabHost.TabSpec spec;
	    Intent intent;
	    Resources res = getResources();
	    
	    intent = new Intent().setClass(this, LoginActivity.class);
	    spec = tabhost.newTabSpec("login");
	    spec.setIndicator("로그인");
	    spec.setContent(intent);
	    tabhost.addTab(spec);
	    		
	    intent = new Intent().setClass(this, JoinActivity.class);
	    spec = tabhost.newTabSpec("join");
	    spec.setIndicator("회원가입");
	    spec.setContent(intent);
	    tabhost.addTab(spec);
	    
	    for(int i=0; i<2; i++){
		    tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#BDBDBD"));
		    tabhost.getTabWidget().getChildAt(i).getLayoutParams().height = 110;
		    TextView tp = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
		    tp.setTextSize(18);
	    }
	    tabhost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#2757A1"));
	    tabhost.setCurrentTab(0);
	    
	    mBack = (ImageButton) findViewById(R.id.back_login);
	    mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
	    });
	    
	    //선택 됬을때
	    tabhost.setOnTabChangedListener(new OnTabChangeListener(){
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if( tabId.equals("login") ){
					tabhost.getTabWidget().getChildAt(0)
						.setBackgroundColor(Color.parseColor("#2757A1"));
					tabhost.getTabWidget().getChildAt(1)
						.setBackgroundColor(Color.parseColor("#BDBDBD"));
				}else{
					tabhost.getTabWidget().getChildAt(0)
						.setBackgroundColor(Color.parseColor("#BDBDBD"));
					tabhost.getTabWidget().getChildAt(1)
						.setBackgroundColor(Color.parseColor("#2757A1"));					
				}
			}
	    });
        
	}

}
