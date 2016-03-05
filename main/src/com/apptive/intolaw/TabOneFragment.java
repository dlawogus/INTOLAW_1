package com.apptive.intolaw;

import com.apptive.activity.GoodLawyer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TabOneFragment extends Fragment{
	private ImageButton menu1;
	private ImageButton menu2;
	private ImageButton menu3;
	private ImageButton menu4;
	private ImageButton menu5;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tab1_fragment, container, false);
		
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.homeImagePager);
        final ImagePagerAdapter adapter = new ImagePagerAdapter();
        viewPager.setAdapter(adapter);

        final CirclePageIndicator circleIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
        
        menu1 = (ImageButton) view.findViewById(R.id.mainmenu1);
        menu1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//Toast.makeText(getActivity(), "menu1", Toast.LENGTH_LONG).show();
				// Create new fragment and transaction
				Fragment newFragment = new TabTwoFragment();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();

				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				transaction.replace(R.id.ll_fragment, newFragment);
				
				// Commit the transaction
				transaction.commit();
				
				
				//ft.replace(R.id.content_frame, fragment).setBreadCrumbTitle(6).addToBackStack(null).commit();
				
				MainActivity.mCurrentFragmentIndex=MainActivity.FRAGMENT_TWO;
				MainActivity.tab1_btn.setImageResource(R.drawable.tab1_1);
				MainActivity.tab2_btn.setImageResource(R.drawable.tab2_2);
				MainActivity.tab3_btn.setImageResource(R.drawable.tab3_1);
				MainActivity.tab4_btn.setImageResource(R.drawable.tab4_1);
				MainActivity.tab5_btn.setImageResource(R.drawable.tab5_1);
			}
        });
        
        menu2 = (ImageButton) view.findViewById(R.id.mainmenu2);
        menu2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
			}
        });
        
        menu3 = (ImageButton) view.findViewById(R.id.mainmenu3);
        menu3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
			}
        });
        
        menu4 = (ImageButton) view.findViewById(R.id.mainmenu4);
        menu4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(getActivity(), LawWebView.class);
				//startActivity(intent);
				MainActivity.FRAGMENT_TAG = "LawWebView";
				
				Fragment newFragment = new LawWebView();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();

				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				transaction.replace(R.id.ll_fragment, newFragment);
				
				// Commit the transaction
				transaction.commit();
			
				//ft.replace(R.id.content_frame, fragment).setBreadCrumbTitle(6).addToBackStack(null).commit();
			}
        });
        
        menu5 = (ImageButton) view.findViewById(R.id.mainmenu5);
        menu5.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(getActivity(), FreegigwanList.class);
				//startActivity(intent);
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
        
		return view;
	}

    private class ImagePagerAdapter extends PagerAdapter {
    	//private LayoutInflater inflater;
   
        private final int[] mImages = new int[] {
                R.drawable.banner1,
                R.drawable.banner1,
                R.drawable.banner1,
                R.drawable.banner1,
                R.drawable.banner1
        };

        @Override
        public void destroyItem(final ViewGroup container, final int position, final Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }

        @Override
        public int getCount() {
            return this.mImages.length;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
        	/*
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            assert imageLayout != null;
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.banner_image);
            imageView.setImageResource(this.mImages[position]);
            ((ViewPager) view).addView(imageView, 0);
            */
           
            final Context context = getActivity();
            final ImageView imageView = new ImageView(context);
            //final int padding = context.getResources().getDimensionPixelSize(
            //        R.dimen.padding_medium);
            //imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(this.mImages[position]);
            
            imageView.setOnClickListener(new OnClickListener(){
                public void onClick(View v){
                    //this will log the page number that was click
                    Log.v("뷰페이저 리스너", "This page was clicked: " + position);
                    switch(position){
                    	case 0:
                    		//Intent intent = new Intent(getActivity(),GoodLawyerHost.class);
                    		//startActivity(intent);
                    		Toast.makeText(context, ""+position, Toast.LENGTH_LONG).show();
                    		break;
                    	case 1:
                    		Toast.makeText(context, ""+position, Toast.LENGTH_LONG).show();
                    		break;
                    	case 2:
                    		Toast.makeText(context, ""+position, Toast.LENGTH_LONG).show();
                    		break;
                    	case 3:
                    		Toast.makeText(context, ""+position, Toast.LENGTH_LONG).show();
                    		break;
                    	case 4:
                    		Toast.makeText(context, ""+position, Toast.LENGTH_LONG).show();
                    		break;
                    }
                }
            });
            
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(final View view, final Object object) {
            return view == ((ImageView) object);
        }
    }
		
}