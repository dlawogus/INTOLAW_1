package com.apptive.intolaw;

import com.apptive.lawyerlist.LawyerList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TabTwoFragment extends Fragment{
	private Button test;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tab2_fragment, container, false);
		
		test = (Button) view.findViewById(R.id.test);
		test.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LawyerList.class);
				startActivity(intent);
			}
		});
		
		return view;
	}
}