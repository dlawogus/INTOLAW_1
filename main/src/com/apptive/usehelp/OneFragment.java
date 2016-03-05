package com.apptive.usehelp;
import com.apptive.intolaw.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class OneFragment extends Fragment{
	public ImageButton mHelpBack;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View view = inflater.inflate(R.layout.help1fragment, container, false);
		
		//mHelpBack.setVisibility(View.GONE);
		
		return view;
	}

}
