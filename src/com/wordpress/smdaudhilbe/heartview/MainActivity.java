package com.wordpress.smdaudhilbe.heartview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {
	
	//	method 2
	Handler myHandler = new Handler();

	protected int actualPercentage = 100;
	protected int percentage = 1;
	
	Runnable myRunnable = new Runnable() {
		
		@Override
		public void run() {
			
			if(percentage <= actualPercentage){
				heartView.setOuterHeartPercentToDraw(percentage++);
				
				myHandler.postDelayed(myRunnable, 5);
				heartView.invalidate();
			}
		}
	};
	
	private SimpleHeartViewWithAnimation heartView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		heartView = (SimpleHeartViewWithAnimation)findViewById(R.id.HeartView);
		
		percentage = 1;
		
		//	method 2
		myHandler.postDelayed(myRunnable, 5);
	}
	
//	working fine
//	private HeartViewWithAnimation aHeartViewWithAnimation1;
//	
//	Handler myHandler = new Handler();
//
//	protected int actualPercentage = 50;
//	protected int percentage = 0;
//	
//	Runnable myRunnable = new Runnable() {
//		
//		@Override
//		public void run() {
//			
//			if(percentage <= actualPercentage){
//				aHeartViewWithAnimation1.howMuchPixelsToChangeColor(percentage++);
//				myHandler.postDelayed(myRunnable, 20);
//			}
//		}
//	};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main_activity);
//		
//		aHeartViewWithAnimation1 = (HeartViewWithAnimation)findViewById(R.id.aHeartViewWithAnimation1);
//		myHandler.postDelayed(myRunnable, 20);
//	}
}