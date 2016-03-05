package com.apptive.lawyerprofile;

import com.apptive.activity.BaseActivity;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LawyerCaseActivity extends BaseActivity {
	private ImageButton mBack;
	private TextView text_case_title;
	private TextView text_case_content;
	private TextView text_case_keyword;

	private String title;
	private String content;
	private String keyword;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.lawyer_case_profile);
	    
	    Intent intent = getIntent();
	    title = intent.getStringExtra("title");
	    content = intent.getStringExtra("content");
	    keyword = intent.getStringExtra("keyword");
	    
	    text_case_title = (TextView) findViewById(R.id.text_case_title);
	    text_case_title.setText(title);
	    
	    text_case_keyword = (TextView) findViewById(R.id.text_case_keyword);	
	    text_case_keyword.setText(keyword);
	    
	    text_case_content = (TextView) findViewById(R.id.text_case_content);	    
	    text_case_content.setText(content);
	    
	    //뒤로가기
	    mBack = (ImageButton) findViewById(R.id.back_case);
	    mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
	    	
	    });
	    
	}

}
