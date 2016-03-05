package com.apptive.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import com.apptive.activity.DetailSearch;
import com.apptive.activity.SelectSchool;
import com.apptive.activity.Utilities;
import com.apptive.datainfo.SchoolSelectDataInfo;
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

public class BaseListSelectedAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private ViewHolder viewHolder;
		private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
		private Context con;
		private ArrayList<SchoolSelectDataInfo> arraylist;
		
		public BaseListSelectedAdapter(Context context,ArrayList<SchoolSelectDataInfo> arraylist) {
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
			return arraylist.get(position).getSchoolName();
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
				v = inflater.inflate(R.layout.schoolselected_list_item, null);
				Utilities.setGlobalFont(v, con); 
				viewHolder.cancel = (ImageButton) v.findViewById(R.id.cancel);
				viewHolder.selectedschoolText = (TextView) v.findViewById(R.id.selectedschool);
				
		        v.setTag(viewHolder);
				
			}else {
				viewHolder = (ViewHolder)v.getTag();
			}
			
			viewHolder.selectedschoolText.setText(arraylist.get(pos).getSchoolName());
			viewHolder.cancel.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if( arraylist.get(pos).getLevel().equals("highschool") ){
						DetailSearch.highSchool = null;
						DetailSearch.school = null;
					}
					else if( arraylist.get(pos).getLevel().equals("university") ){
						DetailSearch.university = null;
						DetailSearch.school = null;
					}
					else if( arraylist.get(pos).getLevel().equals("graduatedschool") ){
						DetailSearch.graduatedSchool = null;
						DetailSearch.school = null;
					}
					else if( arraylist.get(pos).getLevel().equals("lawschool") ){
						DetailSearch.lawSchool = null;
						DetailSearch.school = null;
					}
					DetailSearch.selectedlist.remove(pos);
					SelectSchool.selectedlistadapter.notifyDataSetChanged();
				}
			});
			return v;
		}
		
		/*
		 * ViewHolder 
		 * getView의 속도 향상을 위해 쓴다.
		 * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
		 */
		class ViewHolder{
			public TextView selectedschoolText = null;
			public ImageButton	cancel = null;
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
