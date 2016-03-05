package com.apptive.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.apptive.intolaw.garbageCollection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

public class IntroActivity extends BaseActivity implements Runnable{
	
	/** Called when the activity is first created. */
	@Override
	public void finish(){
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
		setContentView(R.layout.intro);
		(new Thread(this)).start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (Exception e) {}
		// 화면 이동
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		//인트로 화면으로 돌아오지 않도록 인트로 화면을 종료
		finish();
	}

    @Override
    protected void onDestroy() {
         //Adapter가 있으면 어댑터에서 생성한 recycle메소드를 실행 
     	garbageCollection.recursiveRecycle(getWindow().getDecorView());
     	System.gc();
     	super.onDestroy();
    }

}


