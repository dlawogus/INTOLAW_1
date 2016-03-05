package com.apptive.lawyerprofile;

import java.util.List;

import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class officeMapActivity extends FragmentActivity implements OnMyLocationChangeListener{
	//private LocationManager locationManager;
	private CameraPosition cp;
	private String provider;
	private Location location;
	private Criteria criteria;
	private Button myLocationBtn;
	private Button zoomIn;
	private Button zoomOut;
	private Button findRoad;
	
	//사무실 좌표
	private double lat;		
	private double lng;
	//내 좌표
	private double mylat;
	private double mylng;	
	
	private String address;
	private String officeName;
	private float zoomSize = 16;
    private Marker myMarker;
    
    private GoogleMap mGoogleMap;
    private LocationManager locationManager;
    
    private String  gps;
    
    private boolean oneExecuteTag = true;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map_office);
	    Intent intent = getIntent();
	    lat= intent.getDoubleExtra("lat",35.1701722);
	    lng= intent.getDoubleExtra("lng",129.0640274);
	    address = intent.getStringExtra("address");
	    officeName = intent.getStringExtra("officeName");
	    LatLng startingPoint = new LatLng( lat, lng);

	    //MapsInitializer.initialize(this);
	    GooglePlayServicesUtil.isGooglePlayServicesAvailable(officeMapActivity.this);
	   
	    mGoogleMap = ((SupportMapFragment) getSupportFragmentManager()
	            .findFragmentById(R.id.map)).getMap();
	 
	    mGoogleMap.getUiSettings().setCompassEnabled(false); // 확대시 나침반 X
	    mGoogleMap.getUiSettings().setZoomControlsEnabled(false); // + - 버튼 X
	    
	    
	    // 맵 위치이동.
	    cp = new CameraPosition.Builder().target( startingPoint ).zoom(zoomSize).build();
	    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
	    
	    // Map 을 zoom 합니다. 
	    //mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(zoomSize));
	 
	    // 첫번째 마커 설정.
	    MarkerOptions optFirst = new MarkerOptions();
	    optFirst.position(startingPoint);// 위도 • 경도
	    optFirst.title(officeName);
	    optFirst.icon(BitmapDescriptorFactory
	            .fromResource(R.drawable.img_18));
	    mGoogleMap.addMarker(optFirst).showInfoWindow();
	      
	    // 마커 클릭 리스너
	    mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener(){
	        public boolean onMarkerClick(Marker marker) {
	        	//Toast.makeText(officeMapActivity.this, "TT", Toast.LENGTH_SHORT).show();
	        	Intent intent = new Intent(officeMapActivity.this,RoadFindDialog.class);
	        	intent.putExtra("officeName", officeName);
	        	intent.putExtra("address", address);
	        	intent.putExtra("lat", lat);
	        	intent.putExtra("lng", lng);
	        	intent.putExtra("mylat", mylat);
	        	intent.putExtra("mylng", mylng);	        	
	        	startActivity(intent);
	            return false;
	        }
	    });
	    
	    //줌 플러스
	    zoomIn = (Button)findViewById(R.id.zoom_plus);
	    zoomIn.setOnClickListener(new OnClickListener(){
	    	// MapController control = v.getController()
			@Override
			public void onClick(View v) {
				onZoomControl(true);
			}
	    });
	    
	    //줌 마이너스
	    zoomOut = (Button)findViewById(R.id.zoom_minus);
	    zoomOut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				onZoomControl(false);
			}
	    });
	    
	    //길찾기
	    findRoad = (Button)findViewById(R.id.find_btn);
	    findRoad.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		    	String str = "http://m.map.naver.com/index.nhn?"
		    			+ "&elat="+lat+"&elng="+lng+""
		    			+ "&etext="+officeName+"&menu=route&pathType=1";
		    	
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
	    
	    

	    //내위치 보기
	    myLocationBtn = (Button)findViewById(R.id.myloc_btn);
	    myLocationBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				chkGpsService();
			    mGoogleMap.setMyLocationEnabled( true ); // 자기 위치 표시 Enable
			    mGoogleMap.setOnMyLocationChangeListener( officeMapActivity.this ); // 리스너 등록
			    if( mylat != 0 && mylng != 0)
			    	myLocation();
			}
	    });
	   
        
	}

    
	// zoomLevel 변경로직 
	private void onZoomControl(boolean isZoom){
		if(isZoom){
			if( zoomSize < 21){
				zoomSize+=0.5;
				mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(zoomSize)); 	
			}else{
				Toast.makeText(officeMapActivity.this, "최대 줌레벨에 도달하였습니다", Toast.LENGTH_SHORT).show();
			}
		} else {
			if( zoomSize > 1){
				zoomSize -=0.5;
				mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(zoomSize)); 	
			}else{
				Toast.makeText(officeMapActivity.this, "최소 줌레벨에 도달하였습니다", Toast.LENGTH_SHORT).show();
			}
		}
	} 
	
	private boolean chkGpsService() {
		String gps = android.provider.Settings.Secure.getString( this.getContentResolver(), 
										android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		
		if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {
		     // GPS OFF 일때 Dialog 표시 
		     AlertDialog.Builder gsDialog = new AlertDialog.Builder( this ); 
		     gsDialog.setTitle("위치 서비스 설정");   
		     gsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?"); 
		     gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    	 public void onClick(DialogInterface dialog, int which) { 
		    		 // GPS설정 화면으로 이동 
		    		 Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS); 
		    		 intent.addCategory(Intent.CATEGORY_DEFAULT); 
		    		 startActivityForResult(intent,1); 
		    	 } 
		     }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
		    	 	public void onClick(DialogInterface dialog, int which) {
		    	 		//뒤로 돌아가기
		    	 		return;
		    	 	}
		     }).create().show();
		     return false;
		} else { 
		     return true; 
		} 
	}
    
	public void onResume() {        
        try{    
            super.onResume();
            //locationManager.requestLocationUpdates(provider, 10000, 1, (LocationListener) this);
            }catch(NullPointerException e){
                Log.d("onResume", "NullPointerException: " + e);
            }
    }

    @Override
    public void onDestroy() {
        try{
            super.onDestroy();
            //gps위치정보 업데이트 종료
            //locationManager.removeUpdates((LocationListener) this);
        }catch(NullPointerException e){
            Log.d("onDestroy", "NullPointerException: " + e);
        }
    }
    
    @Override
    public void onPause(){
        try{
            super.onPause();
            //locationManager.removeUpdates((LocationListener) this);
        }catch(NullPointerException e){
            Log.d("onDestroy", "NullPointerException: " + e);
        }
    }

    @Override
    public void onLowMemory() {
        try{
            super.onLowMemory();
        }catch(NullPointerException e){
            Log.d("onLowMemory", "NullPointerException: " + e);
        }
    }

    /*
	@Override
	public void onLocationChanged(Location location) {

        mylat = location.getLatitude();    
        mylng = location.getLongitude();
        
        //myLocation();
	}*/
	
	public void myLocation() {
		//마커 제거
		//mGoogleMap.clear();
		
        LatLng latLng = new LatLng(mylat, mylng);
	    
	    // 내위치 마커 설정.
        /*
	    MarkerOptions optFirst = new MarkerOptions();
	    optFirst.position( latLng );// 위도 • 경도
	    optFirst.icon(BitmapDescriptorFactory
	            .fromResource(R.drawable.img_18));
	   
        // Adding marker on the GoogleMap
        myMarker = mGoogleMap.addMarker(optFirst);
        // Showing InfoWindow on the GoogleMap
        myMarker.showInfoWindow();
        */
	    cp = new CameraPosition.Builder().target( latLng ).zoom(16).build();
	    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
		   
	}


	@Override
	public void onMyLocationChange(Location location) {
		// TODO Auto-generated method stub
        mylat = location.getLatitude();    
        mylng = location.getLongitude();
        if( oneExecuteTag){
        	myLocation();
        	oneExecuteTag = false;
        }
	}	
	
    
}
