package com.apptive.login;

import com.apptive.activity.BaseActivity;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class SelectBirth extends BaseActivity {
	private Button mBack;
	private Button mBirthOk;
	private DatePicker datePicker;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);// 타이틀 없애기
	    setContentView(R.layout.login_select_birth);
	    
	    //투명 만들기
	    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	    
	    datePicker = (DatePicker)findViewById(R.id.datePicker1);
    	//mYear = datePicker.getYear();
    	//mMonth = datePicker.getMonth();
    	//mDay = datePicker.getDayOfMonth();
	    datePicker.init(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),
                 new DatePicker.OnDateChangedListener() {
	                @Override
	                public void onDateChanged(DatePicker view, int year,
	                         int monthOfYear, int dayOfMonth) {
	                    	mYear = year;
	                    	mMonth = monthOfYear+1;
	                    	mDay = dayOfMonth;
	                }
        });
	    
	    //취소버튼
	    mBack = (Button)findViewById(R.id.back_birth);
	    mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
	    	
	    });
	    
	    //확인버튼
	    mBirthOk = (Button)findViewById(R.id.birth_ok);
	    mBirthOk.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(),"확인", Toast.LENGTH_SHORT).show();
            	JoinActivity.mBirth = Integer.toString(mYear)+"-"
            			+Integer.toString(mMonth)+"-"+Integer.toString(mDay);
            	
            	if( mYear != 0 )
            		JoinActivity.mEditBirth.setText(JoinActivity.mBirth);
            	else
            		JoinActivity.mEditBirth.setText("생년월일을 수정해주세요");
            	
            	finish();
			}
	    });
	    
	    
	}

}
