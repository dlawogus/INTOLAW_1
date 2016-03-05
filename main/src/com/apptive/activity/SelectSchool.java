package com.apptive.activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import com.apptive.adapter.BaseListSelectedAdapter;
import com.apptive.adapter.BaseSchoolListAdapter;
import com.apptive.datainfo.SchoolSelectDataInfo;
import com.apptive.dbHelper.dbHelper;
import com.apptive.intolaw.MainActivity;
import com.apptive.intolaw.R;
import com.apptive.intolaw.garbageCollection;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class SelectSchool extends BaseActivity {
	
	private ListView schoollist;			//학교 리스트
	private ListView selectedschoollist;	//선택된 학교 리스트
	private EditText editschool;
	
	private ImageButton highschoolBtn;
	private ImageButton universityBtn;
	private ImageButton graduatedschoolBtn;
	private ImageButton lawschoolBtn;
	
	private Button confirmBtn;
	private ImageButton backBtn;
	
	private Boolean highschoolSelect		=	false;
	private Boolean universitySelect		=	true;
	private Boolean graduatedschoolSelect	=	false;
	private Boolean lawschoolSelect			= 	false;
	
	//학교 리스트 어래이 & 어댑터
	private ArrayList<String> arraylist;
	private ArrayList<String> arraylist_id;
	private BaseSchoolListAdapter m_Adapter 	= 	null;
	
	//선택된 학교 리스트 어래이 & 어댑터
	//public static ArrayList<SchoolSelectDataInfo> selectedlist;
	public static BaseListSelectedAdapter selectedlistadapter 	=	null;
	
	public void finish(){
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
	    setContentView(R.layout.activity_selectschool);

	    //리스트뷰
	    schoollist = (ListView)findViewById(R.id.schoollist);   
	    //선택된 학교 리스트뷰
	    selectedschoollist = (ListView)findViewById(R.id.selectedlist);
	   
	    if( DetailSearch.selectedlist == null )
	    	DetailSearch.selectedlist = new ArrayList<SchoolSelectDataInfo>();					//리스트 초기화
	    
	    selectedlistadapter = new BaseListSelectedAdapter(this,DetailSearch.selectedlist);
	    selectedschoollist.setAdapter(selectedlistadapter);
	    		
        //디비 오픈
	    MainActivity.mDbHelper = new dbHelper(this);
        MainActivity.mdb = MainActivity.mDbHelper.getWritableDatabase(); 
        
        arraylist = new ArrayList<String>();
        arraylist_id = new ArrayList<String>();
	    editschool = (EditText) findViewById(R.id.editschool);
	    
        // ListView에 어댑터 연결
	    m_Adapter = new BaseSchoolListAdapter(SelectSchool.this,arraylist);
        schoollist.setAdapter(m_Adapter);
        
        //리스트 빌드
	    buildList("all");
	    
	    //선택된 학교 리스트 빌드
	    
	    //글자 입력시 마다 호출
	    editschool.addTextChangedListener(new TextWatcher() {
	    	public void onTextChanged(CharSequence s, int start, int before, int count) {
	    		String query = s.toString(); //입력받은 글자 저장
	    		//arraylist.clear();
	    		//arraylist_id.clear();
	            buildList(query);
	    	}
	    	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	    	public void afterTextChanged(Editable s) {}
	    });
	    
	    //리스트 아이템 클릭 리스너
	   	schoollist.setOnItemClickListener(new OnItemClickListener() {
			 
	   		@Override
			 public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
	   			String index =  arraylist_id.get(position).toString();
	   			String name  =  arraylist.get(position).toString();
	   			//Toast.makeText(getApplicationContext(),t, Toast.LENGTH_LONG).show();
	   			//고등학교, 대학교, 대학원, 로스쿨  각 한개의 학교만 선택할수 있다. 
	   			if( highschoolSelect && DetailSearch.highSchool==null && DetailSearch.university==null
	   					&& DetailSearch.graduatedSchool == null && DetailSearch.lawSchool==null ){
	   				DetailSearch.school = index;
	   				DetailSearch.schoolname = name;
	   				DetailSearch.highSchool = index;
	   				DetailSearch.school_kind = "1";
	   				DetailSearch.selectedlist.add(new  SchoolSelectDataInfo(name,"highschool") );
		   			//Toast.makeText(SelectSchool.this, t, Toast.LENGTH_SHORT).show();
		   			selectedlistadapter.notifyDataSetChanged();
	   			}
	   			else if( universitySelect && DetailSearch.highSchool==null && DetailSearch.university==null
	   					&& DetailSearch.graduatedSchool == null && DetailSearch.lawSchool==null){
	   				DetailSearch.school = index;
	   				DetailSearch.schoolname = name;
	   				DetailSearch.university = index;
	   				DetailSearch.school_kind = "2";
	   				DetailSearch.selectedlist.add(new  SchoolSelectDataInfo(name,"university"));
		   			selectedlistadapter.notifyDataSetChanged();
	   			}
	   			else if( graduatedschoolSelect && DetailSearch.highSchool==null && DetailSearch.university==null
	   					&& DetailSearch.graduatedSchool == null && DetailSearch.lawSchool==null ){
	   				DetailSearch.school = index;
	   				DetailSearch.schoolname = name;
	   				DetailSearch.graduatedSchool = index;
	   				DetailSearch.school_kind = "4";
	   				DetailSearch.selectedlist.add(new  SchoolSelectDataInfo(name,"graduatedschool"));
		   			selectedlistadapter.notifyDataSetChanged();
	   			}
	   			else if( lawschoolSelect && DetailSearch.highSchool==null && DetailSearch.university==null
	   					&& DetailSearch.graduatedSchool == null && DetailSearch.lawSchool==null ){
	   				DetailSearch.school = index;
	   				DetailSearch.schoolname = name;
	   				DetailSearch.lawSchool = index;
	   				DetailSearch.school_kind = "3";
	   				DetailSearch.selectedlist.add(new  SchoolSelectDataInfo(name,"lawschool"));
		   			selectedlistadapter.notifyDataSetChanged();
	   			}
	   			else{
	   				Toast.makeText(SelectSchool.this, "한 개의 학교만 선택할 수 있습니다", Toast.LENGTH_SHORT	).show();
	   			}
	   			//Toast.makeText(SelectSchool.this, index, Toast.LENGTH_SHORT	).show();
	   		}
		});
	    
	   	//완료 버튼
	   	confirmBtn = (Button) findViewById(R.id.ok_school);
	   	confirmBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int index = DetailSearch.datainfo.indexOf("출신학교");	
				
				DetailSearch.mSelectListAdapter.notifyDataSetChanged();
				finish();
			}
	   	});
	   	//뒤로가기
	   	backBtn = (ImageButton) findViewById(R.id.back_school);
	   	backBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//int index = DetailSearch.datainfo.indexOf("출신학교");	
				//DetailSearch.mSelectListAdapter.notifyDataSetChanged();
				finish();
			}
	   	});	   	
	   	
	    highschoolBtn = (ImageButton) findViewById(R.id.highschoolBtn);
	    highschoolBtn.setBackgroundResource(R.drawable.icon_high_53);
	    highschoolBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !highschoolSelect ){
					//고등학교 버튼 눌렀을때 
					highschoolBtn.setBackgroundResource(R.drawable.icon_high_54);
					universityBtn.setBackgroundResource(R.drawable.icon_univ);
					graduatedschoolBtn.setBackgroundResource(R.drawable.icon_taehakwon_51);
					lawschoolBtn.setBackgroundResource(R.drawable.icon_lawschool);
					universitySelect = false;
					graduatedschoolSelect = false;
					lawschoolSelect = false;
					highschoolSelect = true;
					//에디트 창 초기화
					editschool.setText("");
					//리스트 모두 출력
					buildList("all");
				}else{
					highschoolBtn.setBackgroundResource(R.drawable.icon_high_53);
					highschoolSelect = false;
				}
			}
	    });
	    
	    universityBtn = (ImageButton) findViewById(R.id.universityBtn);
	    //대학교 기본 선택
	    universityBtn.setBackgroundResource(R.drawable.icon_univ2);
	    universityBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !universitySelect ){
					//대학교 버튼 눌렀을때 
					highschoolBtn.setBackgroundResource(R.drawable.icon_high_53);
					universityBtn.setBackgroundResource(R.drawable.icon_univ2);
					graduatedschoolBtn.setBackgroundResource(R.drawable.icon_taehakwon_51);
					lawschoolBtn.setBackgroundResource(R.drawable.icon_lawschool);
					graduatedschoolSelect = false;
					lawschoolSelect = false;
					highschoolSelect = false;
					universitySelect = true;
					//에디트 창 초기화
					editschool.setText("");
					//리스트 모두 출력
					buildList("all");
				}else{
					universityBtn.setBackgroundResource(R.drawable.icon_univ2);
					universitySelect = false;
				}
			}
	    });
	    
	    graduatedschoolBtn = (ImageButton) findViewById(R.id.graduatedschoolBtn);
	    graduatedschoolBtn.setBackgroundResource(R.drawable.icon_taehakwon_51);
	    graduatedschoolBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !graduatedschoolSelect ){
					//대학원
					highschoolBtn.setBackgroundResource(R.drawable.icon_high_53);
					universityBtn.setBackgroundResource(R.drawable.icon_univ);
					graduatedschoolBtn.setBackgroundResource(R.drawable.icon_taehakwon_52);
					lawschoolBtn.setBackgroundResource(R.drawable.icon_lawschool);
					lawschoolSelect = false;
					highschoolSelect = false;
					universitySelect = false;
					graduatedschoolSelect = true;
					//에디트 창 초기화
					editschool.setText("");
					//리스트 모두 출력
					buildList("all");
				}else{
					graduatedschoolBtn.setBackgroundResource(R.drawable.icon_taehakwon_51);
					graduatedschoolSelect = false;
				}
			}
	    });
	    
	    lawschoolBtn = (ImageButton) findViewById(R.id.lawschoolBtn);
	    lawschoolBtn.setBackgroundResource(R.drawable.icon_lawschool);
	    lawschoolBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if( !lawschoolSelect ){
					//로스쿨 버튼 눌렀을때 
					highschoolBtn.setBackgroundResource(R.drawable.icon_high_53);
					universityBtn.setBackgroundResource(R.drawable.icon_univ);
					graduatedschoolBtn.setBackgroundResource(R.drawable.icon_taehakwon_51);
					lawschoolBtn.setBackgroundResource(R.drawable.icon_lawschool2);
					graduatedschoolSelect = false;
					highschoolSelect = false;
					universitySelect = false;
					lawschoolSelect = true;
					//에디트 창 초기화
					editschool.setText("");
					//리스트 모두 출력
					buildList("all");
				}else{
					lawschoolBtn.setBackgroundResource(R.drawable.icon_lawschool);
					lawschoolSelect = false;
				}
			}
	    });
	      
	}

	private void buildList(String query){
		String select = null;
		arraylist.clear();
		arraylist_id.clear();
		if( highschoolSelect )
			select = "1";
		else if( universitySelect )
			select = "2";
		else if( lawschoolSelect )
			select = "3";
		else if( graduatedschoolSelect )
			select = "4";
		
		if( query.equals("all") )
			query = "";
		
		int count1;
        Cursor mCount = MainActivity.mdb.rawQuery("SELECT count(*) from school_name where name like '%"+query+"%'and kind='"+select+"'", null);
		mCount.moveToFirst();
		count1 = mCount.getInt(0);
        mCount.close();
        Cursor result = MainActivity.mdb.rawQuery("SELECT * from school_name where name like '%"+query+"%'and kind='"+select+"' Order by name", null);
        result.moveToFirst();

        for(int i=0; i< count1; i++){
        	arraylist.add(result.getString(1));
        	arraylist_id.add(result.getString(0));
        	result.moveToNext();
        }   
        
        result.close();
        
        //리스트 갱신
        m_Adapter.notifyDataSetChanged();
	}
	
    @Override
    protected void onDestroy() {
	    garbageCollection.recursiveRecycle(getWindow().getDecorView());
	    System.gc();
	    super.onDestroy();
   }
}

