package com.apptive.adapter;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.apptive.activity.Utilities;
import com.apptive.adapter.SelectListAdapter.ViewHolder;
import com.apptive.datainfo.LawyerListDataInfo;
import com.apptive.datainfo.NoticeListDataInfo;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.apptive.lawyerprofile.LawyerProfile;
import com.apptive.login.LoginHostActivity;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LawyerListAdapter extends BaseAdapter {

	private ArrayList<LawyerListDataInfo> datainfo;
	private LayoutInflater inflater;
	private ViewHolder viewHolder;
	private Context con;
	private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();
    private DisplayImageOptions options;

	public LawyerListAdapter(Context context,ArrayList<LawyerListDataInfo> datainfo, DisplayImageOptions option ) {
		this.datainfo = datainfo;
		this.options = option;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		con = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datainfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datainfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final int pos = position;
		if(v == null){
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.lawyer_list_item, null);
			Utilities.setGlobalFont(v, con); 
			viewHolder.name = (TextView)v.findViewById(R.id.name);
			//viewHolder.local = (TextView)v.findViewById(R.id.local);
			//viewHolder.bubin = (TextView)v.findViewById(R.id.bubin);
			//viewHolder.LawyerImg = (ImageView)v.findViewById(R.id.LawyerImage);
			//viewHolder.LawyerEmptyImg = (ImageView)v.findViewById(R.id.LawyerEmptyImage);
			//viewHolder.introduce = (TextView)v.findViewById(R.id.introduce_list);
			v.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder)v.getTag();
		}
		
		viewHolder.name.setText(datainfo.get(pos).getName());
		viewHolder.local.setText(datainfo.get(pos).getLocal());
		viewHolder.bubin.setText(datainfo.get(pos).getBubin());
		viewHolder.introduce.setText(datainfo.get(pos).getIntroduce());
		
		//디폴트 이미지
		if( datainfo.get(pos).getSmallImage().equals("") ){
			viewHolder.LawyerEmptyImg.setVisibility(View.VISIBLE);
			viewHolder.LawyerImg.setVisibility(View.GONE);
			if( datainfo.get(pos).getIsWoman().equals("0") ){
				viewHolder.LawyerEmptyImg.setBackgroundResource(R.drawable.img_man);
			}
			else{
				viewHolder.LawyerEmptyImg.setBackgroundResource(R.drawable.img_woman);
			}
		}else{
			viewHolder.LawyerEmptyImg.setVisibility(View.GONE);
			viewHolder.LawyerImg.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage( datainfo.get(pos).getSmallImage() , viewHolder.LawyerImg, options, animateFirstListener);
		}
		
		return v;
	}	
	
	/*
	 * ViewHolder 
	 * getView의 속도 향상을 위해 쓴다.
	 * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
	 * */
	class ViewHolder{
		public TextView name = 	null;
		public TextView local = null;
		public TextView bubin = null;
		public ImageView LawyerImg = null;
		public ImageView LawyerEmptyImg = null;
		public TextView	introduce = null;
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

	public void refreshAdapter(ArrayList<LawyerListDataInfo> items) {
		  this.datainfo.clear();
		  this.datainfo.addAll(items);
		  notifyDataSetChanged();
	}

}
