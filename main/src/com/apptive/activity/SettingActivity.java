package com.apptive.activity;

import com.apptive.intolaw.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

public class SettingActivity extends BaseActivity {
	private ViewGroup mNoticeLayout;
	private ViewGroup mQuestionLayout;
	private ViewGroup mHelpLayout;
	private ViewGroup mLawyerJoinLayout;
	

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
	    setContentView(R.layout.activity_setting);
	    
		mNoticeLayout = (ViewGroup)findViewById(R.id.noticeLayout);
		mQuestionLayout = (ViewGroup)findViewById(R.id.questionLayout);
		mHelpLayout = (ViewGroup)findViewById(R.id.helpLayout);
		mLawyerJoinLayout = (ViewGroup)findViewById(R.id.lawyerJoinLayout);
	    // TODO Auto-generated method stub
	}

}
