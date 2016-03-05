package com.apptive.help;

import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.apptive.intolaw.garbageCollection;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HelpFirst extends Activity{

	Context mContext;
	final String TAG = "help";
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureScanner;

	int mCurrentFragmentIndex;
	public final static int FRAGMENT_ONE = 0;
	public final static int FRAGMENT_TWO = 1;
	public final static int FRAGMENT_THREE = 2;
	public final static int FRAGMENT_FOUR = 3;
	//public final static int FRAGMENT_FIVE = 4;
	ImageView imagetab1;
	ImageView imagetab2;
	ImageView imagetab3;
	ImageView imagetab4;
	//ImageView imagetab5;
	Button mHelpBack;
	Button mHelpNext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.helpactivity_host);
        mContext = this;
        gestureScanner = new GestureDetector(this, mGestureListener );
	
		imagetab1= (ImageView)findViewById(R.id.tab1);
		imagetab2= (ImageView)findViewById(R.id.tab2);
		imagetab3= (ImageView)findViewById(R.id.tab3);
		imagetab4= (ImageView)findViewById(R.id.tab4);
		//imagetab5= (ImageView)findViewById(R.id.tab5);
		
		
		mHelpBack = (Button)findViewById(R.id.help_back);
		mHelpBack.setVisibility(View.GONE);
		mHelpBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( mCurrentFragmentIndex == FRAGMENT_ONE+1 )
					mHelpBack.setVisibility(View.GONE);
				if(mCurrentFragmentIndex != FRAGMENT_FOUR-1)
					mHelpNext.setText(" 다음 ");
				if( mCurrentFragmentIndex != FRAGMENT_ONE ){
			    	mCurrentFragmentIndex--;
			    	fragmentReplace(mCurrentFragmentIndex, false);
				}
			}
		});
		
		mHelpNext = (Button)findViewById(R.id.help_next);
		mHelpNext.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(mCurrentFragmentIndex != FRAGMENT_ONE-1)
					mHelpBack.setVisibility(View.VISIBLE);
				if(mCurrentFragmentIndex == FRAGMENT_FOUR-1)
					mHelpNext.setText(" 동의하고 시작하기 ");
				if(mCurrentFragmentIndex==FRAGMENT_FOUR)
			    	finish();
			    else{
			    	mCurrentFragmentIndex++;
			    	fragmentReplace(mCurrentFragmentIndex, true);
			    }
			}
		});
		
		mCurrentFragmentIndex = FRAGMENT_ONE;
		
		fragmentReplace(mCurrentFragmentIndex, true);
		
	}

	//next값은 앞으로 이동인지 뒤로 이동인지 여부
	public void fragmentReplace(int reqNewFragmentIndex, boolean is_next) {

		Fragment newFragment = getFragment(reqNewFragmentIndex);
		FragmentManager fragmentManager = getFragmentManager();
		
		Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

		// replace fragment
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		if( is_next && reqNewFragmentIndex == FRAGMENT_ONE ); //첫 실행시는 애미메이션 없음 
		else if( is_next )
			transaction.setCustomAnimations(R.anim.layout_leftin,R.anim.layout_leftout);
		else
			transaction.setCustomAnimations(R.anim.layout_right_in, R.anim.layout_right_out);
		
		transaction.replace(R.id.ll_fragment, newFragment);
		//헬프탭 이미지 초기화
		imagetab1.setImageResource(R.drawable.icon_one_off); 
		imagetab2.setImageResource(R.drawable.icon_one_off); 
		imagetab3.setImageResource(R.drawable.icon_one_off); 
		imagetab4.setImageResource(R.drawable.icon_one_off); 
		//imagetab5.setImageResource(R.drawable.icon_one_off); 
		switch (reqNewFragmentIndex) {
		case FRAGMENT_ONE:
			imagetab1.setImageResource(R.drawable.icon_one_on);
			break;
		case FRAGMENT_TWO:
			imagetab2.setImageResource(R.drawable.icon_one_on);
			break;
		case FRAGMENT_THREE:
			imagetab3.setImageResource(R.drawable.icon_one_on);
			break;
		case FRAGMENT_FOUR:
			imagetab4.setImageResource(R.drawable.icon_one_on);
			break;
		/*
		case FRAGMENT_FIVE:
			imagetab5.setImageResource(R.drawable.icon_one_on);
			break;
		*/
		default:
			Log.d(TAG, "Unhandle case");
			break;
		}		
		// Commit the transaction
		transaction.commit();
	}

	private Fragment getFragment(int idx) {
		Fragment newFragment = null;
		switch (idx) {
		case FRAGMENT_ONE:
			newFragment = new OneFragment();
			break;
		case FRAGMENT_TWO:
			newFragment = new TwoFragment();
			break;
		case FRAGMENT_THREE:
			//newFragment = new ThreeFragment();
			newFragment = new FourFragment();
			break;
		case FRAGMENT_FOUR:
			//newFragment = new FourFragment();
			newFragment = new FiveFragment();
			break;
		/*
		case FRAGMENT_FIVE:
			newFragment = new FiveFragment();
			break;
		*/
		default:
			Log.d(TAG, "Unhandle case");
			break;
		}

		return newFragment;
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
 
        return gestureScanner.onTouchEvent(event);  // event 전달
    }
    
    OnGestureListener mGestureListener = new OnGestureListener() {
        
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //Toast.makeText(mContext, "onSingleTapUp", Toast.LENGTH_SHORT).show();
            return false;
        }
         
        @Override
        public void onShowPress(MotionEvent e) {
            // TODO Auto-generated method stub
             
        }
         
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                float distanceY) {
            // TODO Auto-generated method stub
            return false;
        }
         
        @Override
        public void onLongPress(MotionEvent e) {
            // TODO Auto-generated method stub
             
        }
         
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            
			//left swipe
			  if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			      //Toast.makeText(mContext, "Left Swipe", Toast.LENGTH_SHORT).show();
				  if(mCurrentFragmentIndex != FRAGMENT_ONE-1)
						mHelpBack.setVisibility(View.VISIBLE);
				  if(mCurrentFragmentIndex == FRAGMENT_FOUR-1)
						mHelpNext.setText(" 동의하고 시작하기 ");
			      if(mCurrentFragmentIndex==FRAGMENT_FOUR);
			    	 //finish();
			      else{
			    	  mCurrentFragmentIndex++;
			    	  fragmentReplace(mCurrentFragmentIndex, true);
			      }
			  }//right swipe
			  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			      //Toast.makeText(mContext, "Right Swipe", Toast.LENGTH_SHORT).show();
				  if( mCurrentFragmentIndex == FRAGMENT_ONE+1 )
					  mHelpBack.setVisibility(View.GONE);
				  if(mCurrentFragmentIndex != FRAGMENT_FOUR-1)
						mHelpNext.setText(" 다음 ");
			      if(mCurrentFragmentIndex!=FRAGMENT_ONE){
			    	  mCurrentFragmentIndex--;
			    	  fragmentReplace(mCurrentFragmentIndex, false);
			      }
			  }
            return false;
        }
         
        @Override
        public boolean onDown(MotionEvent e) {
            // TODO Auto-generated method stub
            return false;
        }
    };
	@Override
   	protected void onDestroy() {
	   	garbageCollection.recursiveRecycle(getWindow().getDecorView());
	   	System.gc();
	   	super.onDestroy();
   	}

}
