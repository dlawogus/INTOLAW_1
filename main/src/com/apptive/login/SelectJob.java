package com.apptive.login;

import java.util.ArrayList;

import com.apptive.activity.BaseActivity;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class SelectJob extends BaseActivity {
//	private ListView joblist;
//	private ArrayList<String> array;	
	private Button Job1;
	private Button Job2;
	private Button Job3;
	private Button Job4;
	private Button Job5;
	private Button Job6;
	private Button Job7;
	private Button Job8;
	private ImageButton mBack;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);// 타이틀 없애기
	    if( MainActivity.deviceHeight <= 900 )
	    	setContentView(R.layout.login_select_job_800);
	    else	
	    	setContentView(R.layout.login_select_job);
	    
	    //투명 만들기
	    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	    
	    Job1 = (Button)findViewById(R.id.job_1);
	    Job1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				JoinActivity.mJob = "학생";
				JoinActivity.mEditJob.setText("학생");
				finish();
			}    	
	    });
	    Job2 = (Button)findViewById(R.id.job_2);
	    Job2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				JoinActivity.mJob = "회사원";
				JoinActivity.mEditJob.setText("회사원");
				finish();				
			}    	
	    });
	    Job3 = (Button)findViewById(R.id.job_3);
	    Job3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				JoinActivity.mJob = "변호사";
				JoinActivity.mEditJob.setText("변호사");
				finish();				
			}    	
	    });
	    Job4 = (Button)findViewById(R.id.job_4);
	    Job4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				JoinActivity.mJob = "변호사 사무실 직원";
				JoinActivity.mEditJob.setText("변호사 사무실 직원");
				finish();
			}    	
	    });
	    Job5 = (Button)findViewById(R.id.job_5);
	    Job5.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				JoinActivity.mJob = "전문직";
				JoinActivity.mEditJob.setText("전문직");
				finish();	
			}    	
	    });
	    Job6 = (Button)findViewById(R.id.job_6);
	    Job6.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				JoinActivity.mJob = "자영업";
				JoinActivity.mEditJob.setText("자영업");
				finish();			
			}    	
	    });
	    Job7 = (Button)findViewById(R.id.job_7);
	    Job7.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				JoinActivity.mJob = "주부";
				JoinActivity.mEditJob.setText("주부");
				finish();	
			}    	
	    });
	    Job8 = (Button)findViewById(R.id.job_8);
	    Job8.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				JoinActivity.mJob = "기타";
				JoinActivity.mEditJob.setText("기타");
				finish();			
			}    	
	    });	    
	    
	    mBack = (ImageButton)findViewById(R.id.back_job);
	    mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
	    });
	    /*
	    joblist = (ListView)findViewById(R.id.list_job);
	    array = new ArrayList<String>();
	    array.add("학생");
	    array.add("회사원");
	    array.add("변호사");
	    array.add("변호사 사무실 직원");
	    array.add("전문직(변호사 제외)");
	    array.add("자영업");
	    array.add("주부");
	    array.add("기타");
		ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
		joblist.setAdapter(adapter);
		
		joblist.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch( position ){
					case 0:
						JoinActivity.mJob = "학생";
						JoinActivity.mEditJob.setText("학생");
						finish();
						break;
					case 1:
						JoinActivity.mJob = "회사원";
						JoinActivity.mEditJob.setText("회사원");
						finish();
						break;
					case 2:
						JoinActivity.mJob = "변호사";
						JoinActivity.mEditJob.setText("변호사");
						finish();
						break;
					case 3:
						JoinActivity.mJob = "회사원";
						JoinActivity.mEditJob.setText("회사원");
						finish();
						break;
					case 4:
						JoinActivity.mJob = "회사원";
						JoinActivity.mEditJob.setText("회사원");
						finish();
						break;
					case 5:
						JoinActivity.mJob = "회사원";
						JoinActivity.mEditJob.setText("회사원");
						finish();
						break;
					case 6:
						JoinActivity.mJob = "회사원";
						JoinActivity.mEditJob.setText("회사원");
						finish();
						break;
					case 7:
						JoinActivity.mJob = "회사원";
						JoinActivity.mEditJob.setText("회사원");
						finish();
						break;
				}
			}
		});
		*/
	    
	    // TODO Auto-generated method stub
	}

}
