package com.apptive.help;

import com.apptive.intolaw.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ThreeFragment  extends Fragment{
	public Button right;
	public Button left;
	public Button cancle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.help3fragment, container, false);
	
		return v;
	}
	
}
