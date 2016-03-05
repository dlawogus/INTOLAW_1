package com.apptive.intolaw;

import com.apptive.activity.BaseActivity;
import com.apptive.intolaw.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

public class LawWebView extends Fragment {
	private ImageButton mBack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_lawwebview, container, false);
       
		WebView webview = (WebView)view.findViewById(R.id.webView1);
        webview.setWebViewClient(new WebClient()); // 응룡프로그램에서 직접 url 처리
        WebSettings set = webview.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        webview.loadUrl("http://mglaw.scourt.go.kr/wsjs/panre/sjs050.do");
        
	    mBack = (ImageButton)view.findViewById(R.id.back_lawweb);
	    mBack.setOnClickListener(new OnClickListener(){
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
		return view;
	}
	
	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_lawwebview);
	    
	   
        WebView webview = (WebView)findViewById(R.id.webView1);
        webview.setWebViewClient(new WebClient()); // 응룡프로그램에서 직접 url 처리
        WebSettings set = webview.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        webview.loadUrl("http://mglaw.scourt.go.kr/wsjs/panre/sjs050.do");
        
	    mBack = (ImageButton) findViewById(R.id.back_lawweb);
	    mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
	    });
	    
	}*/

    class WebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
