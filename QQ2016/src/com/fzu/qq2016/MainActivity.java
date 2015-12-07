package com.fzu.qq2016;


import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private ImageButton mNews,mConstact,mDeynaimic,mSetting;
	protected static final String TAG = "MainActivity";
	private ViewPager mTabPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mTabPager = (ViewPager) findViewById(R.id.tabpager);		
		mNews=(ImageButton) findViewById(R.id.buttom_news);
		mConstact=(ImageButton) findViewById(R.id.buttom_constact);
		mDeynaimic=(ImageButton) findViewById(R.id.buttom_deynaimic);
		mSetting=(ImageButton) findViewById(R.id.buttom_setting);

		mNews.setOnClickListener(new MyOnClickListener(0));
		mConstact.setOnClickListener(new MyOnClickListener(1));
		mDeynaimic.setOnClickListener(new MyOnClickListener(2));
		mSetting.setOnClickListener(new MyOnClickListener(3));
	//	mChat.setOnClickListener(mChatOnClickListener);
		
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.tab_chat, null);
		View view2 = mLi.inflate(R.layout.tab_contact, null);
		View view3 = mLi.inflate(R.layout.tab_zone, null);
		View view4 = mLi.inflate(R.layout.tab_setting, null);
		
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		
		PagerAdapter mPagerAdapter = new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return views.size();
			}
			
			public void destroyItem(View container,int position,Object object) {
				((ViewPager) container).removeView(views.get(position));
			}
			
			public Object instantiateItem(View container,int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mTabPager.setAdapter(mPagerAdapter);
	}

	public class MyOnClickListener implements View.OnClickListener {

		private int index = 0;
		public MyOnClickListener(int i) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
			
		}
				
	};
	
	public void startchat(View v) {
		Intent intent =new Intent(MainActivity.this,ChatActivity.class);
		startActivity(intent);
	}
//	private OnClickListener mChatOnClickListener=new OnClickListener() {
//		
//		@Override
//		public void onClick(View arg0) {
//			Intent intent =new Intent(MainActivity.this,ChatActivity.class);
//			startActivity(intent);
//			
//		}
//	};

}
