package com.apptive.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import com.apptive.activity.Utilities;
import com.apptive.datainfo.NoticeListDataInfo;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.apptive.intolaw.garbageCollection;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeListAdapter extends BaseExpandableListAdapter{
	
	private ArrayList<NoticeListDataInfo> noticelist; 
	private LayoutInflater inflater = null;
	private ViewHolder viewHolder = null;
	//private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
	private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	private Context con;
	
	public NoticeListAdapter(Context c, ArrayList<NoticeListDataInfo> noticelist, DisplayImageOptions options){
		super();
		this.inflater = LayoutInflater.from(c);
		this.noticelist = noticelist;
		this.options = options;
		this.con = c;
	}

	// 그룹 사이즈를 반환한다.
	@Override
	public int getGroupCount() {
		return noticelist.size();
	}

	// 그룹 ID를 반환한다.
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	public String creHtmlBody(String imageUrl){
		StringBuffer sb = new StringBuffer("<HTML>");
		sb.append("<HEAD>");
		sb.append("</HEAD>");
		sb.append("<BODY style='margin:0'; padding:0; text-align:center>");
		sb.append("<img width='100%' heigh='100%' src=\""+imageUrl+"\">");
		sb.append("</BODY>");
		sb.append("</HTML>");
		return sb.toString();
	}
	
	// 그룹뷰 각각의 ROW 
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		if(v == null){
			viewHolder = new ViewHolder();
			if( MainActivity.deviceHeight <= 900 )
				v = inflater.inflate(R.layout.notice_expandedlist_group_item_800, parent, false);
			else
				v = inflater.inflate(R.layout.notice_expandedlist_group_item, parent, false);
			
			Utilities.setGlobalFont(v, con); 
			viewHolder.notice_date = (TextView) v.findViewById(R.id.notice_date);
			viewHolder.notice_title = (TextView) v.findViewById(R.id.notice_title);
			viewHolder.notice_expand_image = (ImageView) v.findViewById(R.id.notice_expand_marker);
			
			v.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)v.getTag();
		}
		
		// 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
		if( isExpanded ){
			//Animation animation1 = AnimationUtils.loadAnimation( con, R.anim.notice_marker_rotate);
			//viewHolder.notice_expand_image.startAnimation(animation1);
			//animation1.setFillAfter(true);
			viewHolder.notice_expand_image.setBackgroundResource(R.drawable.img2);
		}else{
			viewHolder.notice_expand_image.setBackgroundResource(R.drawable.img1);
		}
		
		viewHolder.notice_date.setText(noticelist.get(groupPosition).getDate());
		viewHolder.notice_title.setText(noticelist.get(groupPosition).getTitle());
		
		return v;
	}
	

	// 차일드뷰 ID를 반환한다.
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	// 차일드뷰 각각의 ROW
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		if(v == null){
			viewHolder = new ViewHolder();
			if( MainActivity.deviceHeight <= 900 )
				v = inflater.inflate(R.layout.notice_expandedlist_child_item_800, parent, false);
			else
				v = inflater.inflate(R.layout.notice_expandedlist_child_item, parent, false);
			
			Utilities.setGlobalFont(v, con); 
			viewHolder.notice_img = (ImageView) v.findViewById(R.id.notice_img);
			viewHolder.notice_content = (TextView) v.findViewById(R.id.notice_content);
			v.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)v.getTag();
		}
		
		if( !noticelist.get(groupPosition).getImg().equals("") && !noticelist.get(groupPosition).getImg().equals("path") ){
			viewHolder.notice_img.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage( noticelist.get(groupPosition).getImg() , viewHolder.notice_img, options, animateFirstListener);
		}
		else{
			viewHolder.notice_img.setVisibility(View.GONE);
		}
		
		viewHolder.notice_content.setText(noticelist.get(groupPosition).getContent());
		
		return v;
	}

	@Override
	public boolean hasStableIds() {	return true; }
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	class ViewHolder {
		public ImageView notice_expand_image;
		public ImageView notice_img;
		public TextView notice_title;
		public TextView notice_date;
		public TextView notice_content;
		//public WebView	notice_webview;
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




