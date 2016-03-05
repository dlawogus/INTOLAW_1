package com.apptive.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.apptive.activity.Utilities;
import com.apptive.datainfo.SchoolSelectDataInfo;
import com.apptive.intolaw.R;
import com.apptive.intolaw.garbageCollection;

public class ExamtypeAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private ViewHolder viewHolder;
	private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
	private Context con;
	private String examtype;
	private ArrayList<Integer> gisu;
	
	public ExamtypeAdapter(Context context,String examtype, ArrayList<Integer> gisu) {
		this.examtype = examtype;
		this.gisu = gisu;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		con = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return gisu.size();
	}
	@Override
	public Integer getItem(int position) {
		return gisu.get(position);
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
			v = inflater.inflate(R.layout.examtype_list_item, null);
			Utilities.setGlobalFont(v, con); 
			viewHolder.examtype_text = (TextView) v.findViewById(R.id.examtype_text);
			viewHolder.examtype_gisu = (TextView) v.findViewById(R.id.examtype_gisu);
	        v.setTag(viewHolder);
			
		}else {
			viewHolder = (ViewHolder)v.getTag();
		}
	
		if( gisu.get(pos) != 0 ){
			viewHolder.examtype_text.setText(examtype+" ");
			viewHolder.examtype_gisu.setText(gisu.get(pos) + "회");
		}else{
			viewHolder.examtype_text.setText("모든 회차");
			viewHolder.examtype_gisu.setText("");
		}
		
		return v;
	}
	
	/*
	 * ViewHolder 
	 * getView의 속도 향상을 위해 쓴다.
	 * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
	 */
	class ViewHolder{
		public TextView examtype_text = null;
		public TextView examtype_gisu = null;
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