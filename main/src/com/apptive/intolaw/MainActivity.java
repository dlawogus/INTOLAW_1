/**
* copyright Lim JaeHyun 
 * Intolaw Android Program
 * writen by 임재현 
 * 2015.01.05
 */
package com.apptive.intolaw;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import com.apptive.activity.NoticeActivity;
import com.apptive.adapter.DrawerMenuAdapter;
import com.apptive.dbHelper.dbHelper;
import com.apptive.help.FiveFragment;
import com.apptive.help.FourFragment;
import com.apptive.help.HelpFirst;
import com.apptive.help.OneFragment;
import com.apptive.help.TwoFragment;
import com.apptive.intolaw.R;
import com.apptive.login.LoginHostActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.FragmentManager.BackStackEntry;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	final String TAG = "MainActivity";
	public static FragmentManager fragmentManager;
    //public static DrawerLayout mDrawerLayout;
    //public static ListView mDrawerList;
    //public static DrawerMenuAdapter drawerMenuAdapter = null;
    private long backKeyPressedTime = 0;
    private Toast toast;
    public static int fragmentIndex = 1;	
    private boolean selected = false;	//selectItem()함수 실행여부
    public static int checkedPosition = 1;
    private String mDrawerState = "close";
	public static boolean appFirstStart;
	public static SQLiteDatabase mdb;
	public static dbHelper mDbHelper;
	public final String ROOT_DIR = "/data/data/com.apptive.intolaw/databases/";
	public static final String defaultUrl = "http://ec2-54-64-194-131.ap-northeast-1.compute.amazonaws.com";
	
	private static final String TEST_FILE_NAME = "intolawlawyerImg @#&=+-_.,!()~'%20.png";
	
	//유저 로그인 정보
	public static String UserLogin;
	public static String id;
	public static String email;
	public static String password;
	public static String first_region;
	public static String second_region;
	public static String name;
	public static String is_woman;
	public static String birth;
	public static String job;
	
	public static HttpClient httpclient ;
	public CookieManager cookieManager;

	//공지사항
	public static boolean noticeNew;
	public static int notice_cnt;
	private ProgressDialog pd;
	private Context context;

	private int mLoginResult;
	private String mLoginMessage;
	private int mLogoutResult;
	
	//키워드 맵 어레이
	public static ArrayList<String> keywordMap = new ArrayList<String>();
	public static ArrayList<String> keywordMap_origin = new ArrayList<String>();
	public static int deviceHeight;
	public static int deviceWidth;
	public static DisplayImageOptions options;
	
	//탭바 버튼
	public static ImageView tab1_btn;
	public static ImageView tab2_btn;
	public static ImageView tab3_btn;
	public static ImageView tab4_btn;
	public static ImageView tab5_btn;
	public ViewGroup tab1;
	public ViewGroup tab2;
	public ViewGroup tab3;
	public ViewGroup tab4;
	public ViewGroup tab5;
	public static int mCurrentFragmentIndex;
	public final static int FRAGMENT_ONE = 0;
	public final static int FRAGMENT_TWO = 1;
	public final static int FRAGMENT_THREE = 2;
	public final static int FRAGMENT_FOUR = 3;
	public final static int FRAGMENT_FIVE = 4;
	public static String FRAGMENT_TAG = "TabOneFragment";
	
	// 값 저장하기
	private void savePreferences() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean("appFirstStart", appFirstStart);
		editor.putBoolean("noticeNew", noticeNew);
		editor.putInt("notice_cnt", 0);
		editor.putString("UserLogin", UserLogin);
		editor.putString("id", id);
		editor.putString("email", email);
		editor.putString("password", password);
		editor.putString("first_region", first_region);
		editor.putString("second_region", second_region);
		editor.putString("name", name);
		editor.putString("is_woman", is_woman);
		editor.putString("birth", birth);
		editor.putString("job", job);
		editor.putBoolean("noticeNew", noticeNew);
		editor.putInt("notice_cnt", 0);
		editor.commit();
	}
	
	// 값 불러오기
	private void getPreferences() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		appFirstStart = pref.getBoolean("appFirstStart",true);
		noticeNew = 	pref.getBoolean("noticeNew", true);
		notice_cnt = 	pref.getInt("notice_cnt", 0);
		id = 			pref.getString("id", null);
		email = 		pref.getString("email", null);
		password = 		pref.getString("password", null);
		first_region = 	pref.getString("first_region", null);
		second_region = pref.getString("second_region", null);
		name = 			pref.getString("name", null);
		is_woman = 		pref.getString("is_woman", null);
		birth = 		pref.getString("birth", null);	
		job = 			pref.getString("job", null);
		UserLogin = 	pref.getString("UserLogin", null);
	}
	
	@Override
	public void finish(){
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition( R.anim.start_enter, R.anim.start_exit );
        setContentView(R.layout.tabmainhost);
        setDB();//디비 복사
        context = this;
        
		//값 불러오기기
		getPreferences();
		
		//sd카드에서 이미지 불러오기
		File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME);
		if (!testImageOnSdCard.exists()) {
			copyTestImageToSdCard(testImageOnSdCard);
		}
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.memoryCacheSize(2 * 1024 * 1024) // 2 Mb
			.denyCacheImageMultipleSizesInMemory()
			.discCacheFileNameGenerator(new Md5FileNameGenerator())
			.diskCacheSize(50 * 1024 * 1024) // 50 Mb
			.tasksProcessingOrder(QueueProcessingType.LIFO)
			.writeDebugLogs() // Remove for release app
			.build();
 
		ImageLoader.getInstance().init(config);

		options = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.displayer(new RoundedBitmapDisplayer(20))
			.build();
		
	    mDbHelper = new dbHelper(this);
        mdb = MainActivity.mDbHelper.getWritableDatabase(); 
        
        int count;
	    Cursor mCount = mdb.rawQuery("SELECT count(*) from keyword", null);
		mCount.moveToFirst();
		count = mCount.getInt(0);
		mCount.close();
        Cursor result = mdb.rawQuery("SELECT * from keyword", null);
        result.moveToFirst();	
        for(int i=0; i<count; i++){
        	keywordMap.add( result.getString(1) );
        	keywordMap_origin.add( result.getString(2) );
        	result.moveToNext();
        }
        
		//클라이언트 생성
		httpclient  = new DefaultHttpClient();
		
		//해상도 구하기
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		deviceWidth = displayMetrics.widthPixels;
		deviceHeight= displayMetrics.heightPixels;
		
		Log.v("TAG", deviceWidth + "  " + deviceHeight);
		
		//setSyncCookie();
        fragmentManager = getFragmentManager();
        
     
		if( noticeNew == true){
			Intent intent = new Intent(this, NoticeActivity.class);
			startActivity(intent);
		}
		//도움말 불러오기
		if( appFirstStart == true ){
			Intent intent1 = new Intent(this, HelpFirst.class);
			startActivity(intent1);
			//startActivityForResult(intent, 1);
		}
		if( appFirstStart != false){
			appFirstStart = false;
			savePreferences();
		}
		
		isNetworkStat(this); // 인터넷 연결 확인		
		
		//탭 1메뉴
		tab1 = (ViewGroup)findViewById(R.id.tab1);
		tab1_btn = (ImageView)findViewById(R.id.tab1_btn);
		tab1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mCurrentFragmentIndex = FRAGMENT_ONE;
				fragmentReplace(mCurrentFragmentIndex, true);
			}
		});
		
		//탭 2메뉴
		tab2 = (ViewGroup)findViewById(R.id.tab2);
		tab2_btn = (ImageView)findViewById(R.id.tab2_btn);
		tab2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mCurrentFragmentIndex = FRAGMENT_TWO;
				fragmentReplace(mCurrentFragmentIndex, true);
			}
		});
		
		//탭 3메뉴
		tab3 = (ViewGroup)findViewById(R.id.tab3);
		tab3_btn = (ImageView)findViewById(R.id.tab3_btn);
		tab3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mCurrentFragmentIndex = FRAGMENT_THREE;
				fragmentReplace(mCurrentFragmentIndex, true);
			}
		});
		
		//탭 4메뉴
		tab4 = (ViewGroup)findViewById(R.id.tab4);
		tab4_btn = (ImageView)findViewById(R.id.tab4_btn);
		tab4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mCurrentFragmentIndex = FRAGMENT_FOUR;
				fragmentReplace(mCurrentFragmentIndex, true);
			}
		});
		
		//탭 5메뉴
		tab5 = (ViewGroup)findViewById(R.id.tab5);
		tab5_btn = (ImageView)findViewById(R.id.tab5_btn);
		tab5.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mCurrentFragmentIndex = FRAGMENT_FIVE;
				fragmentReplace(mCurrentFragmentIndex, true);
			}
		});
		

		mCurrentFragmentIndex = FRAGMENT_ONE;
		fragmentReplace(mCurrentFragmentIndex, true);
        
    }
    
	//next값은 앞으로 이동인지 뒤로 이동인지 여부
	public void fragmentReplace(int reqNewFragmentIndex, boolean is_next) {

		Fragment newFragment = getFragment(reqNewFragmentIndex);
		FragmentManager fragmentManager = getFragmentManager();
		
		//Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

		// replace fragment
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.ll_fragment, newFragment);

		switch (reqNewFragmentIndex) {
		case FRAGMENT_ONE:
			tabButtonImage(FRAGMENT_ONE);
			break;
		case FRAGMENT_TWO:
			tabButtonImage(FRAGMENT_TWO);
			break;
		case FRAGMENT_THREE:
			tabButtonImage(FRAGMENT_THREE);
			break;
		case FRAGMENT_FOUR:
			tabButtonImage(FRAGMENT_FOUR);
			break;
		case FRAGMENT_FIVE:
			tabButtonImage(FRAGMENT_FIVE);
			break;
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
			newFragment = new TabOneFragment();
			break;
		case FRAGMENT_TWO:
			newFragment = new TabTwoFragment();
			break;
		case FRAGMENT_THREE:
			newFragment = new TabThreeFragment();
			break;
		case FRAGMENT_FOUR:
			newFragment = new TabFourFragment();
			break;
		case FRAGMENT_FIVE:
			newFragment = new TabFiveFragment();
			break;
		default:
			Log.d(TAG, "Unhandle case");
			break;
		}

		return newFragment;
	}
	
	//뒤로가기 버튼
    public void onBackPressed() {
    	
    	if( FRAGMENT_TAG.equals("LawWebView") ){
			FRAGMENT_TAG = "TabOneFragment";
			Fragment newFragment = new TabOneFragment();
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.ll_fragment, newFragment);
			tabButtonImage(FRAGMENT_ONE);
			transaction.commit();
    	}else if ( FRAGMENT_TAG.equals("FreegigwanList") ){
			FRAGMENT_TAG = "TabOneFragment";
			Fragment newFragment = new TabOneFragment();
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.ll_fragment, newFragment);
			tabButtonImage(FRAGMENT_ONE);
			transaction.commit();	
    	}else if( FRAGMENT_TAG.equals("Freegigwan")){
			FRAGMENT_TAG = "FreegigwanList";
			Fragment newFragment = new FreegigwanList();
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.ll_fragment, newFragment);
			tabButtonImage(FRAGMENT_ONE);
			transaction.commit();	
    	}else{
	        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
	            backKeyPressedTime = System.currentTimeMillis();
	            toast = Toast.makeText(this,"\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
	            toast.show();
	            return;
	        }
	        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
	            finish();
	            toast.cancel();
	        }
    	}
    }
   
    public static void tabButtonImage(int index){
    	switch(index){
	    	case 0:
				tab1_btn.setImageResource(R.drawable.tab1_2);
				tab2_btn.setImageResource(R.drawable.tab2_1);
				tab3_btn.setImageResource(R.drawable.tab3_1);
				tab4_btn.setImageResource(R.drawable.tab4_1);
				tab5_btn.setImageResource(R.drawable.tab5_1);
	    		break;
	    	case 1:
				tab1_btn.setImageResource(R.drawable.tab1_1);
				tab2_btn.setImageResource(R.drawable.tab2_2);
				tab3_btn.setImageResource(R.drawable.tab3_1);
				tab4_btn.setImageResource(R.drawable.tab4_1);
				tab5_btn.setImageResource(R.drawable.tab5_1);
	    		break;
	    	case 2:
				tab1_btn.setImageResource(R.drawable.tab1_1);
				tab2_btn.setImageResource(R.drawable.tab2_1);
				tab3_btn.setImageResource(R.drawable.tab3_2);
				tab4_btn.setImageResource(R.drawable.tab4_1);
				tab5_btn.setImageResource(R.drawable.tab5_1);
	    		break;
	    	case 3:
				tab1_btn.setImageResource(R.drawable.tab1_1);
				tab2_btn.setImageResource(R.drawable.tab2_1);
				tab3_btn.setImageResource(R.drawable.tab3_1);
				tab4_btn.setImageResource(R.drawable.tab4_2);
				tab5_btn.setImageResource(R.drawable.tab5_1);
	    		break;
	    	case 4:
				tab1_btn.setImageResource(R.drawable.tab1_1);
				tab2_btn.setImageResource(R.drawable.tab2_1);
				tab3_btn.setImageResource(R.drawable.tab3_1);
				tab4_btn.setImageResource(R.drawable.tab4_1);
				tab5_btn.setImageResource(R.drawable.tab5_2);
    	}
    }
    
    public static boolean isNetworkStat( Context context ) {
        ConnectivityManager manager = 
           (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo lte_4g = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        boolean blte_4g = false;
        if(lte_4g != null)                             
            blte_4g = lte_4g.isConnected();
        if( mobile != null ) {
            if (mobile.isConnected() || wifi.isConnected() || blte_4g)
                  return true;
        } else {
            if ( wifi.isConnected() || blte_4g )
                  return true;
        }
         
        //Intent intent = new Intent(context, NetworkAlram.class);
        //context.startActivity(intent);
        Toast.makeText(context, "인터넷 연결을 확인해주세요", Toast.LENGTH_LONG).show();
 
        return false; 
    }
    
    //이미지 로더
	public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
    
	//캐쉬로드
	private void copyTestImageToSdCard(final File testImageOnSdCard) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream is = getAssets().open(TEST_FILE_NAME);
					FileOutputStream fos = new FileOutputStream(testImageOnSdCard);
					byte[] buffer = new byte[8192];
					int read;
					try {
						while ((read = is.read(buffer)) != -1) {
							fos.write(buffer, 0, read);
						}
					} finally {
						fos.flush();
						fos.close();
						is.close();
					}
				} catch (IOException e) {
					L.w("Can't copy test image onto SD card");
				}
			}
		}).start();
	}
	
	public void setDB() {
		  File folder = new File(ROOT_DIR);
		  if(folder.exists()) {
		  }
		  else {
		   folder.mkdirs();
		   //Toast.makeText(this, "폴더생성", Toast.LENGTH_LONG).show();
		  }
		   AssetManager assetManager = getResources().getAssets();
		   File outfile = new File(ROOT_DIR+"intolaw.db"); //--폰에 위치할 경로
		   InputStream is = null; 
		   FileOutputStream fo = null;
		   long filesize = 0;    
		   try {
			    // --asset 폴더 및 복사할 DB 지정
				 is = assetManager.open("intolaw.db", AssetManager.ACCESS_BUFFER);  
			     filesize = is.available(); //--사이즈 검증
			     
			    // 파일이 없거나 패키지 폴더에 설치된 DB파일이 포함된 DB파일 보다 크기가 같지않을 경우 DB파일을 덮어 쓴다.
			     if (outfile.length() <= 0) {
				     byte[] tempdata = new byte[(int) filesize];
				     is.read(tempdata); 
				     is.close(); 
				     outfile.createNewFile();
				     fo = new FileOutputStream(outfile);
				     fo.write(tempdata);
				     fo.close();    
			     }else{
			    	 //디버깅용//////////////////
			    	 //Toast.makeText(this, "db있음", Toast.LENGTH_SHORT).show();
			    	 //db.execSQL("DROP TABLE IF EXISTS"+DB_TABLE1 );
			    	 //outfile.delete();
			    	 //setDB();
			     }
		   }catch (IOException e) { 
			  Toast.makeText(this, "db이동실패", Toast.LENGTH_LONG).show();
		   }   
	}
	
	@Override
    protected void onDestroy() {
		super.onDestroy();
		savePreferences();
	}
	
}