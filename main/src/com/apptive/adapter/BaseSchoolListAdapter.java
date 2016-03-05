package com.apptive.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import com.apptive.activity.Utilities;
import com.apptive.intolaw.R;
import com.apptive.intolaw.garbageCollection;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


public class BaseSchoolListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private ViewHolder viewHolder;
		private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
		private Context con;
		private ArrayList<String> arraylist;
		
		public BaseSchoolListAdapter(Context context,ArrayList<String> arraylist) {
			this.arraylist = arraylist;
			inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			con = context;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist.size();
		}
		@Override
		public String getItem(int position) {
			return arraylist.get(position);
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
				v = inflater.inflate(R.layout.school_list_item, null);
				Utilities.setGlobalFont(v, con); 
				viewHolder.schoolText = (TextView) v.findViewById(R.id.schooltext);
				
		        v.setTag(viewHolder);
				
			}else {
				viewHolder = (ViewHolder)v.getTag();
			}
			
			viewHolder.schoolText.setText( arraylist.get(pos).toString() );
			
			return v;
		}
		
		/*
		 * ViewHolder 
		 * getView의 속도 향상을 위해 쓴다.
		 * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
		 */
		class ViewHolder{
			public TextView schoolText = null;
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
		}
}
