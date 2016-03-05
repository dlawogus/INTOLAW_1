package com.apptive.lawyerprofile;

import java.util.List;

import com.apptive.activity.BaseActivity;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class RoadFindDialog extends BaseActivity {
	private String officeName;
	private String address;
	
	private TextView map_office_name;
	private TextView map_address;
	
	private ImageButton mCancel;
	private ImageButton mFind;
	//사무실 좌표
	private double lat;		
	private double lng;
	//내 좌표
	private double mylat;
	private double mylng;	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.roadfinddialog);	    
	
	    //투명 만들기
	    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	    
	    Intent intent = getIntent();
	    officeName = intent.getStringExtra("officeName");
	    address = intent.getStringExtra("address");
	    
	    //주소 
	    map_address = (TextView)findViewById(R.id.map_address);
	    map_address.setText(address);
	    
	    //사무실이름
	    map_office_name = (TextView)findViewById(R.id.map_office_name);
	    map_office_name.setText(officeName);
	    
	    //사무실 좌표
	    lat = intent.getDoubleExtra("lat",0.0);
	    lng = intent.getDoubleExtra("lng",0.0);
	    
	    //내 좌표
	    mylat = intent.getDoubleExtra("mylat",0.0);
	    mylat = intent.getDoubleExtra("mylng",0.0);
	    
	    //길찾기
	    mFind = (ImageButton)findViewById(R.id.road_find_btn);
	    mFind.setOnClickListener(new OnClickListener(){
	    	String str = "http://m.map.naver.com/index.nhn?"
	    			+ "&elat="+lat+"&elng="+lng+""
	    			+ "&etext="+officeName+"&menu=route&pathType=1";
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW);
				PackageManager packageManager = getPackageManager();
				Uri uri = Uri.parse(str);
				browserIntent.setDataAndType(uri, "text/html");
				List<ResolveInfo> list = packageManager.queryIntentActivities(browserIntent, 0);
				for (ResolveInfo resolveInfo : list) {
				    String activityName = resolveInfo.activityInfo.name;
				    Log.e("activityName", activityName);
				    if (activityName.contains("Browser")) {
				        browserIntent =
				                packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName);
				        ComponentName comp =
				                new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
				        browserIntent.setAction(Intent.ACTION_VIEW);
				        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
				        browserIntent.setComponent(comp);
				        browserIntent.setData(uri);
				        startActivity(browserIntent);
				        break;
				    }
				}
			}
	    });

	    //닫기
	    mCancel = (ImageButton)findViewById(R.id.road_cancel);
	    mCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
	    });	    
	}

}
