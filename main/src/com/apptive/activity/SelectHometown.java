package com.apptive.activity;

import java.util.ArrayList;

import com.apptive.adapter.BaseExpandableAdapter;
import com.apptive.dbHelper.dbHelper;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class SelectHometown extends BaseActivity {
    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<String>> mChildList = null;
	private ExpandableListView expandablelist;
	private BaseExpandableAdapter expandableAdapter;
	private ImageButton mBack;
	
	public void finish(){
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}
	/** Called when the activity is first created. */	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
	    setContentView(R.layout.activity_selectlocal);
	    // TODO Auto-generated method stub 
        
	    MainActivity.mDbHelper = new dbHelper(this);
        MainActivity.mdb = MainActivity.mDbHelper.getWritableDatabase(); 
        
        expandablelist = (ExpandableListView) findViewById(R.id.locallist);
        mGroupList = new ArrayList<String>();				//그룹
        mChildList = new ArrayList<ArrayList<String>>();	//차일드
        
        int count;
	    Cursor mCount = MainActivity.mdb.rawQuery("SELECT count(*) from local", null);
		mCount.moveToFirst();
		count = mCount.getInt(0);
		mCount.close();
        Cursor result = MainActivity.mdb.rawQuery("SELECT * from local", null);
        result.moveToFirst();
    
        int childCount;
        mGroupList.add("모든 지역");
        ArrayList<String> temp = new ArrayList<String>();
        mChildList.add(temp);
        for(int i=1 ; i <= count ; i++){
        	mGroupList.add(result.getString(1));
        	result.moveToNext();
        	
        	Cursor mChildCount = MainActivity.mdb.rawQuery("SELECT count(*) from "+mGroupList.get(i).toString()+"", null);
        	mChildCount.moveToFirst();
        	childCount = mChildCount.getInt(0);
        	mChildCount.close();
        	Cursor childResult = MainActivity.mdb.rawQuery("SELECT * from "+mGroupList.get(i).toString()+"", null);
        	childResult.moveToFirst();
        	
        	temp = new ArrayList<String>();
        	temp.add( "전체" );
        	for(int j=0; j<childCount ; j++){
        		temp.add( childResult.getString(1) );
        		childResult.moveToNext();
        	}
        	childResult.close();
        	mChildList.add(temp);
        }
        result.close();
        
		expandableAdapter = new BaseExpandableAdapter(this,mGroupList,mChildList);
		expandablelist.setAdapter(expandableAdapter);
		
		//일반 클릭 이벤트
		expandablelist.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
				//Toast.makeText(getApplicationContext(), Integer.toString(childPosition), Toast.LENGTH_SHORT).show();		
				DetailSearch.hometown = mGroupList.get(groupPosition).toString();
				DetailSearch.hometownDetail =mChildList.get(groupPosition).get(childPosition).toString();
				//리스트 갱신
				DetailSearch.mSelectListAdapter.notifyDataSetChanged();
				MainActivity.mdb.close();
				finish();
				return false;
			}
		});
		
		//그룹리스트를 클릭시 리스너
		expandablelist.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				
				if(groupPosition == 0){
					DetailSearch.hometown = mGroupList.get(groupPosition).toString();
					DetailSearch.hometownDetail = "";
					DetailSearch.mSelectListAdapter.notifyDataSetChanged();
					MainActivity.mdb.close();
					finish();
				}
				int groupCount =expandableAdapter.getGroupCount();
				// 한 그룹을 클릭하면 나머지 그룹들은 닫힌다.
				for (int i = 0; i < groupCount; i++) {
					if (!(i == groupPosition))
						expandablelist.collapseGroup(i);
				}
			}
		});
		
		mBack = (ImageButton)findViewById(R.id.back_local);
		mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	
	}

}
