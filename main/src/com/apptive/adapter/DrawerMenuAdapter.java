package com.apptive.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import com.apptive.activity.Utilities;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.apptive.intolaw.garbageCollection;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class DrawerMenuAdapter extends BaseAdapter{
	private String datainfo[];
	private LayoutInflater inflater;
	private Context con;
	private ViewHolder viewHolder;
	private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
	
	public DrawerMenuAdapter(Context context) {
		this.datainfo = context.getResources().getStringArray(R.array.planets_array);
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.con = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datainfo.length;
	}
	@Override
	public String getItem(int position) {
		return datainfo[position];
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
    //onDestory에서 쉽게 해제할 수 있도록 메소드 생성
	public void recycle() {
		for (WeakReference<View> ref : mRecycleList) {
			garbageCollection.recursiveRecycle(ref.get());
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final int pos = position;
		
		View v = convertView;
		if(v == null){
			viewHolder = new ViewHolder();
			if( MainActivity.deviceHeight <= 900 )
				v = inflater.inflate(R.layout.drawer_list_item_800, null);
			else
				v = inflater.inflate(R.layout.drawer_list_item, null);
			
			Utilities.setGlobalFont(v, con); 
			viewHolder.imageview = (ImageView)v.findViewById(R.id.drawerMenu);
			viewHolder.loginText = (TextView)v.findViewById(R.id.loginText);
			viewHolder.draw_layout_item = (RelativeLayout)v.findViewById(R.id.drawer_layout_item);
			viewHolder.layout_item_param = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			viewHolder.loginEmail = (TextView)v.findViewById(R.id.loginEmail);
			//viewHolder.layout_item_param = (LayoutParams) v.getLayoutParams();
			
	        v.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder)v.getTag();
		}
			
		viewHolder.loginText.setVisibility(View.GONE);
		viewHolder.loginEmail.setVisibility(View.GONE);
		
		if(pos == 0){
			viewHolder.loginText.setVisibility(View.VISIBLE);
			viewHolder.draw_layout_item.setGravity( Gravity.TOP );
			
			if( MainActivity.deviceHeight <= 900 )
				viewHolder.draw_layout_item.setPadding(0, 5, 0, 30);
			else
				viewHolder.draw_layout_item.setPadding(0, 50, 0, 50);
			
			viewHolder.imageview.setVisibility(View.GONE);
			if( MainActivity.UserLogin == null ){
				viewHolder.loginText.setText("로그인");
				viewHolder.loginEmail.setVisibility(View.GONE);
			}
			else{
				viewHolder.loginText.setText(MainActivity.name+"님 반갑습니다");
				viewHolder.loginEmail.setText(MainActivity.email);
				viewHolder.loginEmail.setVisibility(View.VISIBLE);
			}
		}
		else if(pos == 1)
			viewHolder.imageview.setImageResource(R.drawable.tap1);
		else if(pos == 2)
			viewHolder.imageview.setImageResource(R.drawable.tap2);
		else if(pos == 3)
			viewHolder.imageview.setImageResource(R.drawable.tap3);
		else if(pos == 4)
			viewHolder.imageview.setImageResource(R.drawable.tap4);
		else if(pos == 5)
			viewHolder.imageview.setImageResource(R.drawable.tap5);
		else if(pos == 6)
			viewHolder.imageview.setImageResource(R.drawable.tap6);
		else if(pos == 7)
			viewHolder.imageview.setImageResource(R.drawable.tap7);
		
		return v;
	}
	
	/*
	 * ViewHolder 
	 * getView의 속도 향상을 위해 쓴다.
	 * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
	 */
	class ViewHolder{
		public ImageView imageview = null;
		public TextView loginText = null; 
		public TextView loginEmail = null;
		public RelativeLayout draw_layout_item = null;
		public LayoutParams layout_item_param = null;
	}
	
	//어댑터 제거
	@Override
	protected void finalize() throws Throwable {
		free();
		super.finalize();
	}
	
	private void free(){
		inflater = null;
		viewHolder = null;
		con = null;
	}
}



