package com.apptive.activity;

import java.util.ArrayList;

import com.apptive.adapter.ExamtypeAdapter;
import com.apptive.adapter.LawyerListAdapter;
import com.apptive.datainfo.LawyerListDataInfo;
import com.apptive.datainfo.SchoolSelectDataInfo;
import com.apptive.intolaw.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SelectExamtype extends BaseActivity {
	
	private ImageButton mBack;
	private String examtype = null;
	private ArrayList<Integer> arraylist_gisu;
	private ExamtypeAdapter mExamtypeAdapter;
	private ListView examtypeListview;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_selectexamtype);
	    
	    arraylist_gisu = new ArrayList<Integer>();
	    
	    if( DetailSearch.examtype.equals("사법시험") ){
	    	examtype = "사법시험";
	    	for(int i=0; i<=60 ; i++ )
	    		arraylist_gisu.add(i);
	    }
	    else if( DetailSearch.examtype.equals("사법연수원") ){
	    	examtype = "사법연수원";
	    	for(int i=0; i<=50 ; i++ )
	    		arraylist_gisu.add(i);
	    }
	    else if( DetailSearch.examtype.equals("변호사시험") ){
	    	examtype = "변호사시험";
	    	for(int i=0; i<=10 ; i++ )
	    		arraylist_gisu.add(i);
	    }	    
	    else if( DetailSearch.examtype.equals("군법무관") ){
	    	examtype = "군법무관";
	    	for(int i=0; i<=19 ; i++ )
	    		arraylist_gisu.add(i);
	    }	    	
	    else if( DetailSearch.examtype.equals("고등고시") ){
	    	examtype = "고등고시";
	    	for(int i=0; i<=16 ; i++ )
	    		arraylist_gisu.add(i);
	    }
	    
	    examtypeListview = (ListView)findViewById(R.id.examtypelist);
	    mExamtypeAdapter = new ExamtypeAdapter(this, examtype, arraylist_gisu);
	    examtypeListview.setAdapter(mExamtypeAdapter);
	    
	    //리스트 아이템 클릭 리스너
	    examtypeListview.setOnItemClickListener(new OnItemClickListener() {
	   		@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
	   			int gisu = arraylist_gisu.get(position);
	   			if( gisu != 0)
	   				DetailSearch.examGisu = Integer.toString(gisu);
	   			else
	   				DetailSearch.examGisu = null;
	   			
	   			DetailSearch.mSelectListAdapter.notifyDataSetChanged();
	   			finish();
	   		}
		});
	    
	    //뒤로가기
	    mBack = (ImageButton) findViewById(R.id.back_examtype);
	    mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
	    });
	    
	    
	}

}
