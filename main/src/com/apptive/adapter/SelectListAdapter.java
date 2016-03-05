package com.apptive.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import com.apptive.activity.DetailSearch;
import com.apptive.activity.SelectExamtype;
import com.apptive.activity.SelectField;
import com.apptive.activity.SelectHometown;
import com.apptive.activity.SelectLocal;
import com.apptive.activity.SelectSchool;
import com.apptive.activity.Utilities;
import com.apptive.intolaw.R;
import com.apptive.intolaw.garbageCollection;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class SelectListAdapter extends BaseAdapter {
		private ArrayList<String> datainfo;
		private LayoutInflater inflater;
		private ViewHolder viewHolder;
		private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
		private Context con;
	    
		public SelectListAdapter(Context context,ArrayList<String> datainfo) {
			this.datainfo = datainfo;
			inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			con = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return datainfo.size();
		}
		
		@Override
		public String getItem(int position) {
			return datainfo.get(position);
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
				v = inflater.inflate(R.layout.select_list_item, null);
				Utilities.setGlobalFont(v, con); 
				viewHolder.categoryText = (TextView)v.findViewById(R.id.category);
				viewHolder.CommentText = (TextView)v.findViewById(R.id.nextSelectComment);
				viewHolder.imageButton = (ImageView)v.findViewById(R.id.nextSelect);
				viewHolder.next_select = (ViewGroup)v.findViewById(R.id.select_next);
				viewHolder.detail_1 = (ImageButton)v.findViewById(R.id.detailBtn1);
				viewHolder.detail_2 = (ImageButton)v.findViewById(R.id.detailBtn2);
				viewHolder.detail_3 = (ImageButton)v.findViewById(R.id.detailBtn3);
				viewHolder.detail_4 = (ImageButton)v.findViewById(R.id.detailBtn4);
				viewHolder.detail_5 = (ImageButton)v.findViewById(R.id.detailBtn5);
				viewHolder.detail_6 = (ImageButton)v.findViewById(R.id.detailBtn6);
		        v.setTag(viewHolder);
				
			}else {
				viewHolder = (ViewHolder)v.getTag();
			}
			
			if( datainfo.get(pos).equals("지역") ){
				viewHolder.categoryText.setText("지역");
				viewHolder.CommentText.setVisibility(View.VISIBLE);
				viewHolder.imageButton.setVisibility(View.VISIBLE);
				viewHolder.detail_1.setVisibility(View.GONE);
				viewHolder.detail_2.setVisibility(View.GONE);
				viewHolder.detail_3.setVisibility(View.GONE);
				viewHolder.detail_4.setVisibility(View.GONE);
				viewHolder.detail_5.setVisibility(View.GONE);
				viewHolder.detail_6.setVisibility(View.GONE);
				String areaDetail = "";
				if(DetailSearch.areaDetail != "")
					areaDetail = " "+DetailSearch.areaDetail;
					
				viewHolder.CommentText.setText(DetailSearch.area+""+ areaDetail);
				viewHolder.next_select.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(con,SelectLocal.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						con.startActivity(intent);
					}
				});
				
			}
			else if( datainfo.get(pos).equals("취급분야") ){
				viewHolder.categoryText.setText("취급분야");
				viewHolder.CommentText.setVisibility(View.VISIBLE);
				viewHolder.imageButton.setVisibility(View.VISIBLE);
				viewHolder.detail_1.setVisibility(View.GONE);
				viewHolder.detail_2.setVisibility(View.GONE);
				viewHolder.detail_3.setVisibility(View.GONE);
				viewHolder.detail_4.setVisibility(View.GONE);
				viewHolder.detail_5.setVisibility(View.GONE);
				viewHolder.detail_6.setVisibility(View.GONE);
				viewHolder.CommentText.setText(DetailSearch.field);
				viewHolder.next_select.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(con,SelectField.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						con.startActivity(intent);
					}
				});
			}
			else if( datainfo.get(pos).equals("성별") ){
				viewHolder.categoryText.setText("성별");
				viewHolder.CommentText.setVisibility(View.GONE);
				viewHolder.imageButton.setVisibility(View.GONE);
				viewHolder.detail_1.setVisibility(View.VISIBLE);
				viewHolder.detail_2.setVisibility(View.VISIBLE);
				viewHolder.detail_3.setVisibility(View.VISIBLE);
				viewHolder.detail_4.setVisibility(View.GONE);
				viewHolder.detail_5.setVisibility(View.GONE);
				viewHolder.detail_6.setVisibility(View.GONE);
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_sex_man1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_sex_woman1);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_allsex);
				viewHolder.detail_1.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.sex == null || !DetailSearch.sex.equals("0") ){
							DetailSearch.sex = "0";
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_2.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.sex == null || !DetailSearch.sex.equals("1") ){
							DetailSearch.sex = "1";
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_3.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						DetailSearch.sex = null;
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});				
			}
			else if( datainfo.get(pos).equals("나이") ){
				viewHolder.categoryText.setText("나이");
				viewHolder.CommentText.setVisibility(View.GONE);
				viewHolder.imageButton.setVisibility(View.GONE);
				viewHolder.detail_1.setVisibility(View.VISIBLE);
				viewHolder.detail_2.setVisibility(View.VISIBLE);
				viewHolder.detail_3.setVisibility(View.VISIBLE);
				viewHolder.detail_4.setVisibility(View.VISIBLE);
				viewHolder.detail_5.setVisibility(View.GONE);
				viewHolder.detail_6.setVisibility(View.GONE);
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_age_201);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_age_301);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_age_401);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_age_501);
				viewHolder.detail_1.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.age == 0 || !(DetailSearch.age==20) ){
							DetailSearch.age = 20;
						}
						else{
							DetailSearch.age = 0;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_2.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.age == 0 || !(DetailSearch.age==30) ){
							DetailSearch.age = 30;
						}
						else{
							DetailSearch.age = 0;
						}	
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_3.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.age == 0 || !(DetailSearch.age==40) ){
							DetailSearch.age = 40;
						}
						else{
							DetailSearch.age = 0;
						}	
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_4.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.age == 0 || !(DetailSearch.age==50) ){
							DetailSearch.age = 50;
						}
						else{
							DetailSearch.age = 0;
						}	
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
			}
			else if( datainfo.get(pos).equals("경력") ){
				viewHolder.categoryText.setText("경력");
				viewHolder.CommentText.setVisibility(View.GONE);
				viewHolder.imageButton.setVisibility(View.GONE);
				viewHolder.detail_1.setVisibility(View.VISIBLE);
				viewHolder.detail_2.setVisibility(View.VISIBLE);
				viewHolder.detail_3.setVisibility(View.VISIBLE);
				viewHolder.detail_4.setVisibility(View.VISIBLE);
				viewHolder.detail_5.setVisibility(View.GONE);
				viewHolder.detail_6.setVisibility(View.GONE);
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_career_pansa1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_career_gumsa1);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_career_gyosu);
				
				viewHolder.detail_1.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.career == null || !DetailSearch.career.equals("판사") ){
							DetailSearch.career = "판사";
						}
						else{
							DetailSearch.career = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_2.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.career == null || !DetailSearch.career.equals("검사") ){
							DetailSearch.career = "검사";
						}
						else{
							DetailSearch.career = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_3.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.career == null || !DetailSearch.career.equals("군법무관") ){
							DetailSearch.career = "군법무관";
						}
						else{
							DetailSearch.career = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_4.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.career == null || !DetailSearch.career.equals("교수") ){
							DetailSearch.career = "교수";
						}
						else{
							DetailSearch.career = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});

			}
			else if( datainfo.get(pos).equals("출신지") ){
				viewHolder.categoryText.setText("출신지");
				viewHolder.CommentText.setVisibility(View.VISIBLE);
				viewHolder.imageButton.setVisibility(View.VISIBLE);
				viewHolder.detail_1.setVisibility(View.GONE);
				viewHolder.detail_2.setVisibility(View.GONE);
				viewHolder.detail_3.setVisibility(View.GONE);
				viewHolder.detail_4.setVisibility(View.GONE);
				viewHolder.detail_5.setVisibility(View.GONE);
				viewHolder.detail_6.setVisibility(View.GONE);
				String hometownDetail = "";
				if(DetailSearch.hometownDetail != "")
					hometownDetail = " "+ DetailSearch.hometownDetail;
				
				viewHolder.CommentText.setText(DetailSearch.hometown+""+hometownDetail);
				viewHolder.next_select.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(con,SelectHometown.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						con.startActivity(intent);
					}
				});
			}
			/*
			else if( datainfo.get(pos).equals("상담비") ){
				viewHolder.categoryText.setText("상담비");
				viewHolder.CommentText.setVisibility(View.GONE);
				viewHolder.imageButton.setVisibility(View.GONE);
				viewHolder.detail_1.setVisibility(View.VISIBLE);
				viewHolder.detail_2.setVisibility(View.VISIBLE);
				viewHolder.detail_3.setVisibility(View.GONE);
				viewHolder.detail_4.setVisibility(View.GONE);
				viewHolder.detail_5.setVisibility(View.GONE);
				viewHolder.detail_6.setVisibility(View.GONE);
				viewHolder.detail_1.setText("유료");
				viewHolder.detail_2.setText("무료");
				viewHolder.detail_1.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.pay == null || !DetailSearch.pay.equals("0") ){	//유료
							DetailSearch.pay = "0";
						}
						else{
							DetailSearch.pay = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
						
					}
				});
				viewHolder.detail_2.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.pay == null || !DetailSearch.pay.equals("1") ){	//무료
							DetailSearch.pay = "1";
						}
						else{
							DetailSearch.pay = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});

			}*/
			else if( datainfo.get(pos).equals("소속") ){
				viewHolder.categoryText.setText("소속");
				viewHolder.CommentText.setVisibility(View.GONE);
				viewHolder.imageButton.setVisibility(View.GONE);
				viewHolder.detail_1.setVisibility(View.VISIBLE);
				viewHolder.detail_2.setVisibility(View.VISIBLE);
				viewHolder.detail_3.setVisibility(View.GONE);
				viewHolder.detail_4.setVisibility(View.GONE);
				viewHolder.detail_5.setVisibility(View.GONE);
				viewHolder.detail_6.setVisibility(View.GONE);
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_belong_personal1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_belong_bubmoo1);
				viewHolder.detail_1.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.group == null || !DetailSearch.group.equals("1") ){	//개인
							DetailSearch.group = "1";
						}
						else{
							DetailSearch.group = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_2.setOnClickListener(new OnClickListener(){					//법인
					@Override
					public void onClick(View v) {
						if( DetailSearch.group == null || !DetailSearch.group.equals("0") ){
							DetailSearch.group = "0";
						}
						else{
							DetailSearch.group = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
			}
			else if( datainfo.get(pos).equals("시험종류") ){
				viewHolder.categoryText.setText("시험종류");
				viewHolder.CommentText.setVisibility(View.VISIBLE);
				viewHolder.imageButton.setVisibility(View.VISIBLE);
				viewHolder.detail_1.setVisibility(View.VISIBLE);
				viewHolder.detail_2.setVisibility(View.VISIBLE);
				viewHolder.detail_3.setVisibility(View.VISIBLE);
				viewHolder.detail_4.setVisibility(View.GONE);
				viewHolder.detail_5.setVisibility(View.VISIBLE);
				viewHolder.detail_6.setVisibility(View.VISIBLE);
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_exam_sabub1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_exam_yunsu);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_byun1);
				viewHolder.detail_5.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_6.setBackgroundResource(R.drawable.icon_exam_godung1);
				
				if( DetailSearch.examGisu == null )
					viewHolder.CommentText.setText("모든 회차");
				else
					viewHolder.CommentText.setText(DetailSearch.examGisu+" 회");
				
				viewHolder.next_select.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.examtype != null){//시험 선택시만 
							Intent intent = new Intent(con,SelectExamtype.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							con.startActivity(intent);
						}
					}
				});
				
				viewHolder.detail_1.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.examtype != "사법시험"){
							DetailSearch.examtype = "사법시험";
						}
						else{
							DetailSearch.examtype = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				
				viewHolder.detail_2.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.examtype != "사법연수원"){
							DetailSearch.examtype = "사법연수원";
						}
						else{
							DetailSearch.examtype = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_3.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.examtype != "변호사시험"){
							DetailSearch.examtype = "변호사시험";
						}
						else{
							DetailSearch.examtype = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});
				viewHolder.detail_5.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.examtype != "군법무관"){
							DetailSearch.examtype = "군법무관";
						}
						else{
							DetailSearch.examtype = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});				
				viewHolder.detail_6.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if( DetailSearch.examtype != "고등고시"){
							DetailSearch.examtype = "고등고시";
						}
						else{
							DetailSearch.examtype = null;
						}
						DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					}
				});	
				
			}
			else if( datainfo.get(pos).equals("출신학교") ){
				viewHolder.categoryText.setText("출신학교");
				viewHolder.CommentText.setVisibility(View.VISIBLE);
				viewHolder.imageButton.setVisibility(View.VISIBLE);
				viewHolder.detail_1.setVisibility(View.GONE);
				viewHolder.detail_2.setVisibility(View.GONE);
				viewHolder.detail_3.setVisibility(View.GONE);
				viewHolder.detail_4.setVisibility(View.GONE);
				viewHolder.detail_5.setVisibility(View.GONE);
				viewHolder.detail_6.setVisibility(View.GONE);
				viewHolder.CommentText.setText("전체");
				if( DetailSearch.school != null )
					viewHolder.CommentText.setText("선택 완료");
				
				viewHolder.next_select.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(con, SelectSchool.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						con.startActivity(intent);
					}
				});
			}

			
			if( datainfo.get(pos).equals("성별") && (DetailSearch.sex == "0") ){		//남자
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_sex_man2);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_sex_woman1);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_allsex);
			}
			else if(datainfo.get(pos).equals("성별") && (DetailSearch.sex == "1") ){	//여자
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_sex_man1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_sex_woman2);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_allsex);
			}
			else if( datainfo.get(pos).equals("성별") && (DetailSearch.sex == null) ){//모두
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_sex_man1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_sex_woman1);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_allsex2);
			}
			else if(datainfo.get(pos).equals("나이") && (DetailSearch.age==20) ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_age_202);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_age_301);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_age_401);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_age_501);
			}
			else if(datainfo.get(pos).equals("나이") && (DetailSearch.age==30) ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_age_201);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_age_302);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_age_401);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_age_501);
			}
			else if(datainfo.get(pos).equals("나이") && (DetailSearch.age==40) ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_age_201);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_age_301);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_age_402);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_age_501);
			}
			else if(datainfo.get(pos).equals("나이") && (DetailSearch.age==50) ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_age_201);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_age_301);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_age_401);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_age_502);
			}
			else if(datainfo.get(pos).equals("나이") && (DetailSearch.age==0) ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_age_201);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_age_301);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_age_401);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_age_501);
			}
			else if(datainfo.get(pos).equals("경력") && (DetailSearch.career=="판사") ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_career_pansa2);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_career_gumsa1);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_career_gyosu);
			}
			else if(datainfo.get(pos).equals("경력") && (DetailSearch.career=="검사") ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_career_pansa1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_career_gumsa2);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_career_gyosu);
			}
			else if(datainfo.get(pos).equals("경력") && (DetailSearch.career=="군법무관") ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_career_pansa1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_career_gumsa1);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_gunbub2);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_career_gyosu);
			}
			else if(datainfo.get(pos).equals("경력") && (DetailSearch.career=="교수") ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_career_pansa1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_career_gumsa1);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_career_gyosu2);
			}
			else if(datainfo.get(pos).equals("경력") && (DetailSearch.career==null) ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_career_pansa1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_career_gumsa1);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_4.setBackgroundResource(R.drawable.icon_career_gyosu);
			}
			else if(datainfo.get(pos).equals("소속") && (DetailSearch.group=="1") ){//개인
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_belong_personal);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_belong_bubmoo1);
			}
			else if(datainfo.get(pos).equals("소속") && (DetailSearch.group=="0") ){//법인
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_belong_personal1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_belong_bubmoo2);
			}
			else if(datainfo.get(pos).equals("소속") && (DetailSearch.group==null) ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_belong_personal1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_belong_bubmoo1);
			}
			else if(datainfo.get(pos).equals("시험종류") && (DetailSearch.examtype == "사법시험")){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_exam_sabub2);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_exam_yunsu);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_byun1);
				viewHolder.detail_5.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_6.setBackgroundResource(R.drawable.icon_exam_godung1);
			}
			else if(datainfo.get(pos).equals("시험종류") && (DetailSearch.examtype == "사법연수원")){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_exam_sabub1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_exam_yunsu2);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_byun1);
				viewHolder.detail_5.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_6.setBackgroundResource(R.drawable.icon_exam_godung1);
			}	
			else if(datainfo.get(pos).equals("시험종류") && (DetailSearch.examtype == "변호사시험")){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_exam_sabub1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_exam_yunsu);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_byun2);
				viewHolder.detail_5.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_6.setBackgroundResource(R.drawable.icon_exam_godung1);
			}
			else if(datainfo.get(pos).equals("시험종류") && (DetailSearch.examtype == "군법무관")){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_exam_sabub1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_exam_yunsu);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_byun1);
				viewHolder.detail_5.setBackgroundResource(R.drawable.icon_exam_gunbub2);
				viewHolder.detail_6.setBackgroundResource(R.drawable.icon_exam_godung1);
			}
			else if(datainfo.get(pos).equals("시험종류") && (DetailSearch.examtype == "고등고시")){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_exam_sabub1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_exam_yunsu);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_byun1);
				viewHolder.detail_5.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_6.setBackgroundResource(R.drawable.icon_exam_godung2);
			}	
			else if(datainfo.get(pos).equals("시험종류") && (DetailSearch.examtype == null)){
				viewHolder.detail_1.setBackgroundResource(R.drawable.icon_exam_sabub1);
				viewHolder.detail_2.setBackgroundResource(R.drawable.icon_exam_yunsu);
				viewHolder.detail_3.setBackgroundResource(R.drawable.icon_exam_byun1);
				viewHolder.detail_5.setBackgroundResource(R.drawable.icon_exam_gunbub1);
				viewHolder.detail_6.setBackgroundResource(R.drawable.icon_exam_godung1);
			}
			/*
			else if(datainfo.get(pos).equals("상담비") && (DetailSearch.pay=="0") ){//무료
				viewHolder.detail_1.setBackgroundResource(R.drawable.buttonpressed);
				viewHolder.detail_2.setBackgroundResource(R.drawable.buttonunpressed);
			}
			else if(datainfo.get(pos).equals("상담비") && (DetailSearch.pay=="1") ){//유료
				viewHolder.detail_1.setBackgroundResource(R.drawable.buttonunpressed);
				viewHolder.detail_2.setBackgroundResource(R.drawable.buttonpressed);
			}
			else if(datainfo.get(pos).equals("상담비") && (DetailSearch.pay==null) ){
				viewHolder.detail_1.setBackgroundResource(R.drawable.buttonunpressed);
				viewHolder.detail_2.setBackgroundResource(R.drawable.buttonunpressed);
			}*/
			
			return v;
		}
		
		/*
		 * ViewHolder 
		 * getView의 속도 향상을 위해 쓴다.
		 * 한번의 findViewByID 로 재사용 하기 위해 viewHolder를 사용 한다.
		 */
		class ViewHolder{
			public TextView categoryText = null;
			public TextView CommentText = null;
			public ImageView imageButton = null;
			public ViewGroup	next_select = null;
			public ImageButton detail_1 = null;
			public ImageButton detail_2 = null;
			public ImageButton detail_3 = null;
			public ImageButton detail_4 = null;
			public ImageButton detail_5 = null;
			public ImageButton detail_6 = null;
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

