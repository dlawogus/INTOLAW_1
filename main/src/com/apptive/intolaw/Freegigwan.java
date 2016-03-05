package com.apptive.intolaw;

import com.apptive.intolaw.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Freegigwan extends Fragment {
	private ImageButton back;
	private ImageView img_free;
	private String homepage;
	private int freegigwan;
	private Button go_homepage;

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.activity_freegigwan, container, false);
	    
	    back = (ImageButton) view.findViewById(R.id.back_freegigwan);
	    back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.FRAGMENT_TAG = "FreegigwanList";
				Fragment newFragment = new FreegigwanList();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				transaction.replace(R.id.ll_fragment, newFragment);
				// Commit the transaction
				transaction.commit();
			}
	    });
	    
	    Bundle  extra = getArguments();
	    freegigwan = extra.getInt("freegigwan");
	    
	    //free_text = (TextView) findViewById(R.id.free_text);
	    //free_text.setTypeface(Typeface.createFromAsset(getAssets(), "Nototo.ttf"));
	    //free_text.setTypeface(Typeface.createFromAsset(getAssets(), "NotoSansKR-Thin-Windows-mini.ttf"));
	    
	    //이미지
	    img_free = (ImageView)view.findViewById(R.id.img_free);
	    
	    WebView webView = (WebView)view.findViewById(R.id.freegigwan_webviewtext);
	    String meta = "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>";
	    String style = "<style>@font-face {font-family: 'Nototo'; src: url('file:///android_asset/Nototo.ttf');} body {font-family: 'Nototo';"
	    		+ "letter-spacing:-1px; line-height:150%; font-size:120%; text-align:justify; }</style>";
	    String head = "<head>" + meta + style + "</head>";
	       
	    if( freegigwan == 1 ){
	    	img_free.setBackgroundResource(R.drawable.b3);
	    	String body ="<body>마을변호사란 변호사 사무실이 없는 마을 주민들에게 무료 법률 상담을 해드리는 제도입니다."
	    			+ "변호사가 있는 도시 지역을 제외한 농/어촌등 대부분의 지역에 마을변호사가 있으며 "
	    			+ "자세한 정보는 홈페이지를 통해 확인하세요.</body>";
		    String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );
	    	homepage="http://m.campaign.naver.com/livetogether02";
	    }
	    else if( freegigwan == 2 ){
	    	img_free.setBackgroundResource(R.drawable.b4);                                                  
	    	String body = "<body>법률 홈닥터 제도는 법무부와 지방 자치단체 사회 복지 협의회가 함께 진행하는 사업으로서, "
	    			+ "변호사 자격을 갖춘 법률 홈닥터가 지역 거점 기관에 상주하면서 취약 계층을 비롯한 서민들에게 "
	    			+ "1차 무료 법률 서비스를 제공하는 “찾아가는 서민 법률 주치의”입니다. 2011년 시범 사업을 거쳐, "
	    			+ "2012년 5월 정식 사업을 시작 하였으며 2014년 현재 전국 40곳 지방 자치단체, "
	    			+ "사회 복지 협의회에 법률 홈닥터가 배치되어 활동 중입니다. 자세한 정보는 홈페이지를 통해 확인하세요.</body>";
		    String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );
	    	homepage="http://blog.naver.com/homedoc2013";
	    }
	    else if( freegigwan == 3 ){
	    	img_free.setBackgroundResource(R.drawable.b5);
	    	String body = "<body>법률 구조 제도는 경제적으로 어렵거나 법을 모르기 때문에 법의 보호를 충분히 받지 못하는 "
	    			+ "국민들에게 법률 상담, 변호사 또는 공익 법무관에 의한 소송 대리 및 형사변호 등의 법률적 지원을 "
	    			+ "함으로써 정당한 권리를 적법한 절차에 의하여 보호하고 국민의 기본적 인권을 옹호하는 법률분야의 "
	    			+ "사회 복지 제도입니다. 자세한 정보는 홈페이지를 통해 확인하세요.</body>";	   
		    String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );	
	    	homepage="http://m.klac.or.kr";
	    }
	    else if( freegigwan == 4 ){
	    	img_free.setBackgroundResource(R.drawable.b6);
	    	String body = "<body>중소기업의 설립ㆍ운영ㆍ소멸에 이르는 전 과정에서 발생하는 법률문제에 대해 법무부 "
	    			+ "중소기업 법률지원단 (‘9988 법률지원단’) 소속 법률 전문가들이 법률상담 ㆍ 자문서비스를 "
	    			+ "지원하는 제도입니다. 미지급 대금 회수, 계약서검토, 가압류가처분, 지적재산권침해, "
	    			+ "사업모델 법률검토, 파산 및회생, 지배구조개선 등 중소기업 관련 제반 법률문제에 대한 상담과 "
	    			+ "자문을 제공합니다. 다만, 기업경영과 직접 관련이 없는 경영자 개인의 법률문제나, 기업활동에 "
	    			+ "관련된 형사사건이 아닌 일반 형사사건 (불법행위로 인해 대표자나 종업원이 고소 고발된 사건 등)은 "
	    			+ "상담 대상에서 제외됩니다. 자세한 정보는 홈페이지를 통해 확인하세요.</body>";
		    String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );	
	    	homepage="http://www.9988law.com";
	    }
	    else if( freegigwan == 5 ){
	    	img_free.setBackgroundResource(R.drawable.b7);
	    	String body = "<body>부산 지방 변호사 협회에서는 소속 변호사 2명이 매일 돌아가며 직접 상담을 하고 있으며 상담비용은"
	    			+ " 없습니다. 민사, 형사, 가사, 상사, 행정 등 법률 전반에 걸쳐 실시하고 있습니다. 뿐만 아니라 개인회생, "
	    			+ "파산지원 변호사단 / 민사소액 사건소송 지원 변호사단 / 이주민 법률 지원단 등 다양한 제도를 운영하고 있으며 "
	    			+ "자세한 정보는 홈페이지를 통해 확인하세요.</body>";

		    String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );	
	    	homepage="http://www.busanbar.or.kr";
	    }
	    else if( freegigwan == 6 ){
	    	img_free.setBackgroundResource(R.drawable.b8);
	    	String body = "<body>부산대학교 법률 상담소는 법학 전문 대학원 교수 전원으로 구성된 교내 상담위원과 외부 법률 전문가로 구성된 "
	    			+ "상담 위원들이 전공 영역별로 전문적 법률 서비스를 제공하고 있습니다. 법률상담 자체는 무료이지만, "
	    			+ "무료 법률상담과 관련하여 부산대학교 법률 상담소나 상담 위원들에게 어떠한 민, 현사상의 청구나 이의제기를 할 수 없습니다. "
	    			+ "부산대학교 법률 상담소에 법률상담을 의로하신 분은 이러한 조건에 동의하신 것으로 간주됩니다. "
	    			+ "자세한 정보는 홈페이지를 통해 확인하세요.</body>";

		    String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );	
	    	homepage="http://law.pusan.ac.kr/09_law/law_4.asp";
	    }
	   	    
	    go_homepage = (Button) view.findViewById(R.id.free_homepage);
	    go_homepage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				Uri u = Uri.parse(homepage);
				i.setData(u);
				startActivity(i);
			}
	    });
	    
	    return view;
	}

}
