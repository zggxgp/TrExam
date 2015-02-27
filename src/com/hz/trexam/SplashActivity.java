package com.hz.trexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);	
				finish();
			}
		}, 2000);
		
		
	}
	
	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
		}
		
	};
}
