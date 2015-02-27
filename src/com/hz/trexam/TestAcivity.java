package com.hz.trexam;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Application;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.renderscript.Int2;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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
	private Button nowQuesition;
	private TrExamApplication trApp;
	private ImageButton backBtn;
	
	private AlertDialog.Builder gotoDialog;
	private AlertDialog.Builder errorDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);
		init();
		
		//初始化答对题目
		trApp = (TrExamApplication)getApplication();
		trApp.setCorrectNum(0);
		
		//初始化题目数量和当前题目编号
		nowQuesition.setText("1/100");
		
		//设置开始考试对话框
		alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("注意");
		alertBuilder.setCancelable(false);
		alertBuilder.setMessage("即将开始考试，考试时间持续50分钟");
		alertBuilder.setPositiveButton("开始", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//设置计时器
				
				mc = new MyCount(3000000, 1000);
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
					int tempNum = mNum+1;
					nowQuesition.setText(tempNum+"/100");
				}
				
				
				
			}
		});
		
		//设置下一题按钮
		nextBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mNum<fragList.size()){
					mNum++;
					int tempNum =  mNum+1;
					nowQuesition.setText(tempNum+"/100");
				}
				fragmentManager = getSupportFragmentManager();
				fragmentTransaction = fragmentManager.beginTransaction();
				
				fragmentTransaction.replace(R.id.content_test, fragList.get(mNum));
				fragmentTransaction.commit();
			}
		});
		
		//设置题号跳转按钮
		nowQuesition.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoDialog = new AlertDialog.Builder(TestAcivity.this);
				gotoDialog.setTitle("请输入你想跳转到的题号");
				final View view = LayoutInflater.from(TestAcivity.this).inflate(R.layout.editdialog, null);
				gotoDialog.setView(view);//给对话框添加输入栏;
				final EditText mEditText = (EditText)view.findViewById(R.id.edit_dialog); 
				
				
				
				gotoDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						try{
							int num = Integer.valueOf(mEditText.getText().toString());
							System.out.println("---------------------->"+num);
							if(num>973||num<1){
								System.out.println("inside---------------------->"+num);
								errorDialog = new AlertDialog.Builder(TestAcivity.this);
								errorDialog.setTitle("错误的输入");
								errorDialog.setMessage("请输入1~973之间的数字");
								errorDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										
										
									}
								});
								
								errorDialog.show();
							}else if(num>=1&&num<=973){
								String numStr = mEditText.getText().toString();
								int num2 = Integer.valueOf(numStr);
								fragmentManager = getSupportFragmentManager();
								fragmentTransaction = fragmentManager.beginTransaction();
								
								fragmentTransaction.replace(R.id.content_test, fragList.get(num2));
								fragmentTransaction.commit();
								
								nowQuesition.setText(num2+"/100");
							}
						}catch(Exception e){
							errorDialog = new AlertDialog.Builder(TestAcivity.this);
							errorDialog.setTitle("错误的输入");
							errorDialog.setMessage("请输入1~973之间的数字");
							errorDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									
									
								}
							});
							errorDialog.show();
						}
						
						
						
						
					}
				});
				
				gotoDialog.show();
				
			}
		
		});
			
		//设置返回按钮
		backBtn = (ImageButton)findViewById(R.id.backtomain_test);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentToMain = new Intent(TestAcivity.this, MainActivity.class);
				startActivity(intentToMain);
				
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
			//时间到后弹出对话框显示成绩
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
		
	}
	
	public void init(){
		preBtn = (Button)findViewById(R.id.pre_btn);
		nextBtn = (Button)findViewById(R.id.next_btn);
		timeBtn = (Button)findViewById(R.id.time);
		cmtBtn = (Button)findViewById(R.id.commit_btn);
		nowQuesition = (Button)findViewById(R.id.nowquestion);
	}
	
}
