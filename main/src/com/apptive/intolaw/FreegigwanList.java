package com.apptive.intolaw;

import com.apptive.intolaw.R;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;

public class FreegigwanList extends Fragment {
	private ImageButton back;
	private ImageView free1;
	private ImageView free2;
	private ImageView free3;
	private ImageView free4;
	private ImageView free5;
	private ImageView free6;
	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.activity_freegigwan_list, container, false);
	 
	    back = (ImageButton) view.findViewById(R.id.back_freegigwanlist);
	    back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.FRAGMENT_TAG = "TabOneFragment";
				Fragment newFragment = new TabOneFragment();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				transaction.replace(R.id.ll_fragment, newFragment);
				// Commit the transaction
				transaction.commit();
			}
	    });
	    
		free1 = (ImageButton) view.findViewById(R.id.free_btn1);
		free1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.FRAGMENT_TAG = "Freegigwan";
				Fragment newFragment = new Freegigwan();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				Bundle bundle = new Bundle();
				bundle.putInt("freegigwan", 1);
				newFragment.setArguments(bundle);
				
				transaction.replace(R.id.ll_fragment, newFragment);
				// Commit the transaction
				transaction.commit();
			}
		});
		free2 = (ImageButton) view.findViewById(R.id.free_btn2);
		free2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.FRAGMENT_TAG = "Freegigwan";
				Fragment newFragment = new Freegigwan();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				Bundle bundle = new Bundle();
				bundle.putInt("freegigwan", 2);
				newFragment.setArguments(bundle);
				
				transaction.replace(R.id.ll_fragment, newFragment);
				// Commit the transaction
				transaction.commit();
			}
		});
		free3 = (ImageButton) view.findViewById(R.id.free_btn3);
		free3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.FRAGMENT_TAG = "Freegigwan";
				Fragment newFragment = new Freegigwan();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				Bundle bundle = new Bundle();
				bundle.putInt("freegigwan", 3);
				newFragment.setArguments(bundle);
				
				transaction.replace(R.id.ll_fragment, newFragment);
				// Commit the transaction
				transaction.commit();
			}
		});
		free4 = (ImageButton) view.findViewById(R.id.free_btn4);
		free4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.FRAGMENT_TAG = "Freegigwan";
				Fragment newFragment = new Freegigwan();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				Bundle bundle = new Bundle();
				bundle.putInt("freegigwan", 4);
				newFragment.setArguments(bundle);
				
				transaction.replace(R.id.ll_fragment, newFragment);
				// Commit the transaction
				transaction.commit();
			}
		});
		free5 = (ImageButton) view.findViewById(R.id.free_btn5);
		free5.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.FRAGMENT_TAG = "Freegigwan";
				Fragment newFragment = new Freegigwan();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				Bundle bundle = new Bundle();
				bundle.putInt("freegigwan", 5);
				newFragment.setArguments(bundle);
				
				transaction.replace(R.id.ll_fragment, newFragment);
				// Commit the transaction
				transaction.commit();
			}
		});
		free6 = (ImageButton) view.findViewById(R.id.free_btn6);
		free6.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.FRAGMENT_TAG = "Freegigwan";
				Fragment newFragment = new Freegigwan();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				Bundle bundle = new Bundle();
				bundle.putInt("freegigwan", 6);
				newFragment.setArguments(bundle);
				
				transaction.replace(R.id.ll_fragment, newFragment);
				// Commit the transaction
				transaction.commit();
			}
		});
		
		return view;  
	}

}
