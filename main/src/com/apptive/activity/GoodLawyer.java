package com.apptive.activity;

import com.apptive.intolaw.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodLawyer extends Activity {
	private int good_lawyer;
	private ImageView img_good;
	private ImageButton mBack;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_good_lawyer);
	   
	    Intent intent = getIntent();
	    good_lawyer = intent.getExtras().getInt("goodlawyer");
	    img_good = (ImageView)findViewById(R.id.img_good);
	    //good_text = (TextView)findViewById(R.id.good_text);
	    //good_text.setTypeface(Typeface.createFromAsset(getAssets(), "Nototo.ttf"));
	    WebView webView = (WebView)findViewById(R.id.good_webviewtext);
	    String meta = "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>";
	    String style = "<style>@font-face {font-family: 'Nototo'; src: url('file:///android_asset/Nototo.ttf');} body {font-family: 'Nototo';"
	    		+ "letter-spacing:-1px; line-height:150%; font-size:120%; text-align:justify;}</style>";
	    String head = "<head>" + meta + style + "</head>";
	   
	    if( good_lawyer == 1 ){
	    	img_good.setBackgroundResource(R.drawable.b9);
	    	String body = "<body><b>하나, </b>무조건 이길 수 있다고 말하는 변호사<br/>"
	    			+ "<b>둘, </b>자기한테만 맡기라고 하는 변호사<br/>"
	    			+ "<b>셋, </b>형사사건인 경우 언제까지 석방시킬 수 있다고 장담하는 변호사<br/>"
	    			+ "<b>넷, </b>사건 브로커를 통해서 다가오는 변호사<br/>"
	    			+ "<b>다섯, </b>첫 대면부터 변호사는 만날 수가 없고,사무장과 얘기하라는 변호사<br/>"
	    			+ "<b>여섯, </b>변론기일에 성실히 출석하지 않는 변호사<br/>"
	    			+ "<b>일곱, </b>사건 진행 사항에 대해서 답변을 잘 하지 못하는 변호사<br/>"
	    			+ "<b>여덟, </b>전화하거나 찾아가면 항상 피하는 변호사<br/>"
	    			+ "<b>아홉, </b>돈부터 달라고 하는 변호사<br/>"
	    			+ "<b>열, </b>사건 처리 과정에서 급히 돈이 더 필요하다고 하는 변호사<br/></body>";
		    String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );
	    }
	    else if( good_lawyer == 2 ){
	    	img_good.setBackgroundResource(R.drawable.b10);
	    	String body = "<body><b>하나, </b>변호사가 관련분야에 얼마 만큼의 경험이 있는가?<br/>"
	    			+ "<b>둘, </b>다른 사람이 이전에 비슷한 사례로 만족할 만한 결과를 얻은 적이 있는가?<br/>"
	    			+ "<b>셋, </b>변호사가 개인적인 관심을 보여주고 언제든지 상담에 응할 자세가 되어 있는가?<br/>"
	    			+ "<b>넷, </b>변호사가 지금 사건이 어떻게 진행되고 있는지 결과는 어떤 것이 예상되는 설명을 제대로, 충분히 해 줄 수 있는가?</body>";    
	    	String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );	    	
	    }
	    else if( good_lawyer == 3 ){
	    	img_good.setBackgroundResource(R.drawable.b11);
	    	String body = "<body><b><font size='5'>우리가 병원에가 치료를 받을 때 진료비, 치료비 등이 드는 것과 같이 변호사에게 사건을 맡겨 해결할 때 변호사 선임 비용이 듭니다.</font></b><br/><br/>"
	    			+ "<b>꼭 비용을 들여 변호사를 선임해야 하는지 생각하신다면 다음 문제를 생각해보세요.</b> 인터넷이나 주변 사람을 통해 해결하는 것이 과연 정확한 방법인가? "
	    			+ "만약 잘못된 결과가 나온다면? 나 혼자 재판을 준비해서 판결을 받기까지 받는 스트레스를 돈으로 환산하면 얼마나 될까? 그 시간에 생업에 종사한다면?"
	    			+ "좋지않은 결과가 나왔을 때 얻게되는 피해(징역, 벌금, 명예, 범죄자로의 낙인 등)을 돈으로 환산하면 얼마나 되겠는가?<br/><br/>"
	    			+ "<b>사무실에 지불하는 비용</b>"
	    			+ "<br/><b>1)상담료</b><br/> 변호사 상담은 유료입니다.우리가 병원에서 진료를 할때 진료비를 내는 것 처럼 정확한 해결을 위해선 유료 상담료를 내야합니다."
	    			+ "사건수임만을 목적으로 한 무책임한 무료 상담은 오히려 정확한 해결을 하는데 방해가 될 것입니다.  "
	    			+ "<br/><b>2)착수금</b><br/> 변호사가 소송을 시작하여 종료될 때까지 당사자가 얻는 경제적이익과 사건난이도에 따라 증감됩니다. 착수금은 특별히 변호사에게 책임이 있는 "
	    			+ "사유가 발생하지 않는 한 되돌려 받지 못합니다. 의사가 최선을 다하여 치료하다가 사망하더라도 의료과실이 있지 않는 한 치료비를 되돌려 받지 못하는 것과 같습니다. "
	    			+ "<br/><b>3)성공보수금</b><br/> 변호사가 승소를 하게 되면 착수금 외에 따로 성공대가로 받을 것을 약속하는 돈입니다. 성공보수금은 사건의 중요도, 난이도, 소가, "
	    			+ "지역 등의 제반 여건을 고려하여 결정되는데 소가와 난이도에 따라서 조정될 수 있습니다. 이 비용은 의뢰인과 변호사 사무실사이에서 자유롭게 결정할 수 "
	    			+ "있으며, 의뢰인이 변호사 사무실에 반드시 성공보수금을 지급해야 할 의무는 없으나, 일단 약정을 하면 지급해야 합니다. "
	    			+ "<br/><b>4)소송비용</b><br/> 소송과 관련하여 법원에 납부하는 비용을 말하는데, 기록복사비용, 출장여비, 보석보증금, 인지대, 송달료, 감정료, 예납금, 보증금"
	    			+ " 등과 같이 사건 진행에 필요한 제반 소요 비용을 말합니다. 우리는 보통 변호사 선임 비용이 크기 때문에 이러한 실비까지 의뢰인이 내야 하느냐 하고 반문하실지도 "
	    			+ "모르겠습니다만, 소송비용은 소가에 따라 변호사 비용과 맞먹는 경우도 있으며, 소송비용은 순수한 변호사의 보수에 해당되지 않기 때문에 착수금이나 성공보수금외에 "
	    			+ "들어가는 비용들은 따로 내셔야 합니다. 다만, 변호사와 소송위임계약을 하실 때 소송비용은 따로 지급하지 않도록 계약서에 구체적으로 적어 넣으시는 경우에는 계약서 "
	    			+ "내용대로 될 것입니다. "
	    			+ "<br/><b>5)부가가치세</b><br/> 1999년 1월 1일부터는 변호사도 부가가치세를 납부하도록 되었기 때문에 원칙적으로 의뢰인은 수임료외에 수임료의 10%에 해당하는 금액을 변호사에게 "
	    			+ "지급해야 합니다. 그러나, 법인이 아닌 개인 의뢰인의 경우에는 서로 절충하여 부가가치세를 수임료에 포함시키는 경우도 많습니다.</body>";
		    String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );
	    }
	    else if( good_lawyer == 4 ){
	    	img_good.setBackgroundResource(R.drawable.b12);
	    	String body = "<body><b><font size='5'>법률 전문가와 혼자싸워 이길 수 있으십니까?</font></b><br/>"
	    			+ " 법적 문제로 소송이 발생하면 상대방이 존재합니다. "
	    			+ "민사사건에서 상대방은 이기기위해 변호사를 고용할 것이고, "
	    			+ "형사사건에서 상대방은 검사가 될 것입니다. <b>즉 내가 싸워야 할 대상은 "
	    			+ "일반인이 아닌 법률 전문가입니다.</b> 이들은 당신의 허점을 찾기위해 "
	    			+ "최선을 다할것입니다. 소송에서 이기기위해선 내 말이 사실인지 아닌지가 "
	    			+ "중요한 것이 아니라 법정에서 자신의 말을 증명할 수 있어야 합니다. "
	    			+ "하지만 일반인 입장에서 법정의 룰을 알기란 어렵고 전문가인 이들을 이기기란 "
	    			+ "쉽지 않습니다. 따라서 내가 소송에서 이기기위해서는 법정의 룰에 "
	    			+ "익숙한 변호사의 도움이 반드시 필요합니다.</body>";
	    	String htmlContents = "<html>" + head + body + "</html>";
		    webView.loadDataWithBaseURL( "file:///android_asset/", htmlContents, "text/html", "utf-8", null );
	    }
	    
	    mBack = (ImageButton) findViewById(R.id.back_good);
	    mBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
	    });
	}

}
