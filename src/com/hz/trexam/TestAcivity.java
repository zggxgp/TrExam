package com.hz.trexam;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.renderscript.Int2;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hz.trexam.util.CreateFragmentList;

public class TestAcivity extends FragmentActivity{
	
	
	private MyCount mc;//倒计时工具类
	private Date date;//用于显示倒计时
	private SimpleDateFormat sdf;//设置倒计时格式
	private int mNum = 0 ;
	
	
	private List<Fragment> fragList;//存放每道题的frament
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private AlertDialog.Builder alertBuilder;
	private AlertDialog.Builder resultBuilder;
	
	private Button preBtn;
	private Button nextBtn;
	private Button timeBtn;
	private Button cmtBtn;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);
		init();
		
		//设置开始考试对话框
		alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("注意");
		alertBuilder.setMessage("即将开始考试，考试时间持续45分钟");
		alertBuilder.setPositiveButton("开始", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//设置计时器
				
				mc = new MyCount(300000, 1000);
				mc.start();
			}
		});
		
		alertBuilder.show();
		
		
		//设置倒计时格式
		
		sdf = new SimpleDateFormat("mm:ss");
		
		
		//获取题目对应的fragmentlist
		fragList = CreateFragmentList.getFragmentList();
		
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		
		fragmentTransaction.replace(R.id.content_test, fragList.get(mNum));
		fragmentTransaction.commit();
		
		
		
		
		//设置上一题按钮
		preBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mNum>0){
					mNum--;
				}
				
				
				
			}
		});
		
		//设置下一题按钮
		nextBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mNum<fragList.size()){
					mNum++;
				}
				fragmentManager = getSupportFragmentManager();
				fragmentTransaction = fragmentManager.beginTransaction();
				
				fragmentTransaction.replace(R.id.content_test, fragList.get(mNum));
				fragmentTransaction.commit();
			}
		});
		
		
		
		//设置提交按钮
		cmtBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				resultBuilder = new AlertDialog.Builder(TestAcivity.this);
				resultBuilder.setTitle("您的成绩");
				TrExamApplication app = (TrExamApplication)getApplication();
				int grade = app.getCorrectNum();
				resultBuilder.setMessage("您的得分为： "+grade);
				
				resultBuilder.setNegativeButton("返回主菜单", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(TestAcivity.this, MainActivity.class);
						startActivity(intent);
						
					}
				});
				resultBuilder.show();
			}
		});
	}
	
	
	
	public class MyCount extends CountDownTimer{

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
				
		}

		@Override
		public void onTick(long millisUntilFinished) {
			date = new Date(millisUntilFinished);
			String time = sdf.format(date);
			timeBtn.setText(time);
			
		}

		@Override
		public void onFinish() {
			
			
		}
		
	}
	
	public void init(){
		preBtn = (Button)findViewById(R.id.pre_btn);
		nextBtn = (Button)findViewById(R.id.next_btn);
		timeBtn = (Button)findViewById(R.id.time);
		cmtBtn = (Button)findViewById(R.id.commit_btn);
	}
	
}
