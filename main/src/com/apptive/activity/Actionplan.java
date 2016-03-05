package com.apptive.activity;

import com.apptive.intolaw.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Actionplan extends BaseActivity {
	private ImageButton mBack;
	private ImageView actionplan;
	private TextView actionplan_text;
	/*
	public String creHtmlBody(){
		StringBuffer sb = new StringBuffer("<HTML>");
		sb.append("<HEAD>");
		sb.append("</HEAD>");
		sb.append("<BODY style='margin:0'; padding:0; text-align:left>");
		sb.append("<p style='font-size:160%'>1.사건 경위서 등으로 사건을 정리한다."
	    			+ "2.아는 만큼 증거를 준비한다."
	    			+ "3.관련 있다고 생각되는 것은 모조리 들고 간다."
	    			+ "4.현재 사건의 진행 정도를 파악해 둔다."
	    			+ "5.관련된 사람들의 관계를 정리한다."
	    			+ "6.중요한 게 있고 급한 게 있다. 무엇이 중요한지 무엇이 급한지 생각해본다."
	    			+ "7.변호사와 수임 계약을 체결할 때를 대비하여 비용을 확보한다.</p>");
		sb.append("</BODY>");
		sb.append("</HTML>");
		return sb.toString();
	}
	*/
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_actionplan);
	    
	    Intent intent = getIntent();
	    int i = intent.getExtras().getInt("actionplan");
	    
	    actionplan = (ImageView)findViewById(R.id.action_img);
	    //actionplan_text = (TextView)findViewById(R.id.action_text);
	    //actionplan_text.setTypeface(Typeface.createFromAsset(getAssets(), "Nototo.ttf"));
	  
	    //webText = (WebView)findViewById(R.id.actionplan_webviewtext);
	    //webText.loadDataWithBaseURL(null, creHtmlBody(), "text/html", "utf-8", null);
	    
	    WebView webView = (WebView)findViewById(R.id.actionplan_webviewtext);
	    String meta = "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>";
	    String style = "<style>@font-face {font-family: 'Nototo'; src: url('file:///android_asset/Nototo.ttf');} body {font-family: 'Nototo';"
	    		+ "letter-spacing:-1px; line-height:150%; font-size:120%; text-align:justify;}</style>";
	    String head = "<head>" + meta + style + "</head>";
	    
	    if( i == 1){
	    	actionplan.setBackgroundResource(R.drawable.b1);
		    String body = "<body>1.사건 경위서 등으로 사건을 정리한다.<br/>"
		    		+ "2.아는 만큼 증거를 준비한다.<br/>"
		    		+ "3.관련 있다고 생각되는 것은 모조리 들고 간다.<br/>"
		    		+ "4.현재 사건의 진행 정도를 파악해 둔다.<br/>"
		    		+ "5.관련된 사람들의 관계를 정리한다.<br/>"
		    		+ "6.중요한 게 있고 급한 게 있다. 무엇이 중요한지 무엇이 급한지 생각해본다.<br/>"
		    		+ "7.변호사와 수임 계약을 체결할 때를 대비하여 비용을 확보한다.<br/>"
		    		+ "</body>";
		    
		    String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );
	    }
	    else if( i == 2){
	    	actionplan.setBackgroundResource(R.drawable.b2);
	    	String body = "<body>1.이 사건의 쟁점은 무엇인가?<br/>"
	    			+ "2.해결책은 무엇인가?<br/>"
	    			+ "3.현재 사건이 진행되는 상태는 어떤가? 나에게 유리한가,불리한가?<br/>"
	    			+ "4.나는 무엇부터 해야 되는가?<br/>"
	    			+ "5.변호사는 무엇을 할 것인가?<br/>"
	    			+ "6.소송이 해결되는 데 얼마나 시간이 걸리는가?<br/>"
	    			+ "7.소송 해결까지 어떤 과정을 거쳐 진행되는가?<br/>"
	    			+ "8.상대방은 어떤 방법으로 방어나 공격을 할 것 같은가?<br/>"
	    			+ "9.8번에 대해 우리는 어떻게 대응할 계획인가?<br/>"
	    			+ "10.관련된 법률적 근거는 무엇인가?<br/>"
	    			+ "11.현 사건과 유사한 소송경험이나 연구 경험이 있는가?<br/>"
	    			+ "12.원하는 결과를 얻어낼 수 있는 확률은 얼마나 되는가?<br/>"
	    			+ "13.패할 가능성은 얼마인가?<br/>"
	    			+ "14.피해를 최소화할 수 있는 방법은 없는가?<br/>"
	    			+ "15.수임료를 포함한 소송비용은 얼마나 되는가?<br/>"
	    			+ "</body>";
	    	
		    String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );
	   
	    }
	   
    	//actionplan_text.setText(Html.fromHtml("<p style='font-size:160%'>"+str+"</p>"));
    	
    	
	    mBack = (ImageButton) findViewById(R.id.back_actionplan);
	    mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
	    });
	    
	    
	    
	}

}
