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
		
		//��ʼ�������Ŀ
		trApp = (TrExamApplication)getApplication();
		trApp.setCorrectNum(0);
		
		//��ʼ����Ŀ�����͵�ǰ��Ŀ���
		nowQuesition.setText("1/100");
		
		//���ÿ�ʼ���ԶԻ���
		alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("ע��");
		alertBuilder.setCancelable(false);
		alertBuilder.setMessage("������ʼ���ԣ�����ʱ�����50����");
		alertBuilder.setPositiveButton("��ʼ", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//���ü�ʱ��
				
				mc = new MyCount(3000000, 1000);
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
					int tempNum = mNum+1;
					nowQuesition.setText(tempNum+"/100");
				}
				
				
				
			}
		});
		
		//������һ�ⰴť
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
		
		//���������ת��ť
		nowQuesition.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoDialog = new AlertDialog.Builder(TestAcivity.this);
				gotoDialog.setTitle("������������ת�������");
				final View view = LayoutInflater.from(TestAcivity.this).inflate(R.layout.editdialog, null);
				gotoDialog.setView(view);//���Ի������������;
				final EditText mEditText = (EditText)view.findViewById(R.id.edit_dialog); 
				
				
				
				gotoDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						try{
							int num = Integer.valueOf(mEditText.getText().toString());
							System.out.println("---------------------->"+num);
							if(num>973||num<1){
								System.out.println("inside---------------------->"+num);
								errorDialog = new AlertDialog.Builder(TestAcivity.this);
								errorDialog.setTitle("���������");
								errorDialog.setMessage("������1~973֮�������");
								errorDialog.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
									
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
							errorDialog.setTitle("���������");
							errorDialog.setMessage("������1~973֮�������");
							errorDialog.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
								
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
			
		//���÷��ذ�ť
		backBtn = (ImageButton)findViewById(R.id.backtomain_test);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentToMain = new Intent(TestAcivity.this, MainActivity.class);
				startActivity(intentToMain);
				
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
			//ʱ�䵽�󵯳��Ի�����ʾ�ɼ�
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
		
	}
	
	public void init(){
		preBtn = (Button)findViewById(R.id.pre_btn);
		nextBtn = (Button)findViewById(R.id.next_btn);
		timeBtn = (Button)findViewById(R.id.time);
		cmtBtn = (Button)findViewById(R.id.commit_btn);
		nowQuesition = (Button)findViewById(R.id.nowquestion);
	}
	
}
