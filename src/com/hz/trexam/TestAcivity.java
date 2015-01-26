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
	
	
	private MyCount mc;//����ʱ������
	private Date date;//������ʾ����ʱ
	private SimpleDateFormat sdf;//���õ���ʱ��ʽ
	private int mNum = 0 ;
	
	
	private List<Fragment> fragList;//���ÿ�����frament
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
		
		//���ÿ�ʼ���ԶԻ���
		alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("ע��");
		alertBuilder.setMessage("������ʼ���ԣ�����ʱ�����45����");
		alertBuilder.setPositiveButton("��ʼ", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//���ü�ʱ��
				
				mc = new MyCount(300000, 1000);
				mc.start();
			}
		});
		
		alertBuilder.show();
		
		
		//���õ���ʱ��ʽ
		
		sdf = new SimpleDateFormat("mm:ss");
		
		
		//��ȡ��Ŀ��Ӧ��fragmentlist
		fragList = CreateFragmentList.getFragmentList();
		
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		
		fragmentTransaction.replace(R.id.content_test, fragList.get(mNum));
		fragmentTransaction.commit();
		
		
		
		
		//������һ�ⰴť
		preBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mNum>0){
					mNum--;
				}
				
				
				
			}
		});
		
		//������һ�ⰴť
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
		
		
		
		//�����ύ��ť
		cmtBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				resultBuilder = new AlertDialog.Builder(TestAcivity.this);
				resultBuilder.setTitle("���ĳɼ�");
				TrExamApplication app = (TrExamApplication)getApplication();
				int grade = app.getCorrectNum();
				resultBuilder.setMessage("���ĵ÷�Ϊ�� "+grade);
				
				resultBuilder.setNegativeButton("�������˵�", new DialogInterface.OnClickListener() {
					
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
