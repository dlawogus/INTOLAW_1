package com.apptive.lawyerprofile;

import com.apptive.activity.BaseActivity;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class LawyerAlertDialog extends BaseActivity {
	private ImageButton mNo;
	private ImageButton mOk;
	private boolean check = false;
	
	// 값 저장하기
	private void savePreferences() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean("alertCheck", check);
		editor.commit();
	}	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.profile_alert);
	    // TODO Auto-generated method stub

	    //투명 만들기
	    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	    
	    //확인 버튼
	    mOk = (ImageButton) findViewById(R.id.alert_profile_ok);
	    mOk.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
	    });

	    //노  버튼
	    mNo = (ImageButton) findViewById(R.id.alert_profile_no);
	    mNo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				check = true;
				savePreferences();
				finish();
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

}
