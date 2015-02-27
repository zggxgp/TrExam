package com.hz.trexam;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hz.trexam.bean.Exam;
import com.hz.trexam.db.ExamDBManger;
import com.hz.trexam.util.GetTheErrorList;

/**
 * 
 * @author hz ����ģʽactivity
 * 
 */
public class ErrorActivity extends FragmentActivity {

	private int Num = 2;
	private int totalNum;
	private List<Exam> errExamList;
	private ImageButton backBtn;

	private ViewPager mViewPager;
	private ErrorAdapter errAdapter;
	private TextView examNumView;
	private TextView noError;
	
	private int nowQue = 0;
	private ImageButton btnDelete;
	private AlertDialog.Builder alertDialog;

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		setContentView(R.layout.error_layout);

		// ��ȡ�����б�
		errExamList = GetTheErrorList.getTheErrorExamList(this);

		// ���ݴ����б�list����������Num
		Num = errExamList.size();
		totalNum = Num;

		// ��ʼ���������͵�ǰ���
		examNumView = (TextView) findViewById(R.id.bottom_bar_text_error);
		noError = (TextView) findViewById(R.id.noerror);

		if (totalNum >=1) {
			examNumView.setText("" + 1 + "/" + totalNum);
		} else {
			examNumView.setText("" + 0 + "/" + totalNum);
			noError.setText("����û�������Ŀ��������Զ���¼�����ģ��");
			noError.setVisibility(View.VISIBLE);
		}

		// ����ɾ����Ŀ��ť
		// ����ɾ����ǰ��Ŀ��ť
		btnDelete = (ImageButton) findViewById(R.id.topbar_delete_favorite);
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog = new AlertDialog.Builder(ErrorActivity.this);
				alertDialog.setTitle("ע��");
				alertDialog.setMessage("ȷ��Ҫɾ����ǰ��Ŀ��");

				alertDialog.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});

				alertDialog.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ExamDBManger examDBManger = new ExamDBManger(
										ErrorActivity.this);
								examDBManger.getWriteDataBaseConn();
								String nowQueStr = String.valueOf(nowQue);
								examDBManger.delete("error", "ordernum=?",
										new String[] { nowQueStr });
								totalNum--;
								
								errExamList.remove(nowQue);
								errAdapter.notifyDataSetChanged();
								if ((nowQue + 1) >totalNum) {
									examNumView.setText("" + nowQue + "/"
											+ totalNum);
									nowQue--;
								}else{
									examNumView.setText("" + (nowQue+1) + "/"
											+ totalNum);
									nowQue--;
								}
								
							}
						});

				alertDialog.show();
			}
		});

		// viewpager����adapter
		errAdapter = new ErrorAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager_error);
		mViewPager.setAdapter(errAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				int templc = position + 1;
				examNumView.setText("" + templc + "/" + Num);
				int temp = Num - position;
				nowQue = position;
				
				
				/*
				if (temp <= 2 && (Num + 1) <= totalNum) {
					Num++;
					errAdapter.notifyDataSetChanged();
				}
				*/
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		// ���÷��ذ�ť
		backBtn = (ImageButton) findViewById(R.id.backtomain_error);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentToMain = new Intent(ErrorActivity.this,
						MainActivity.class);
				startActivity(intentToMain);

			}
		});

	}

	public class ErrorAdapter extends FragmentStatePagerAdapter {

		public ErrorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {

			return totalNum;
		}

		@Override
		public Fragment getItem(int position) {

			return (Fragment) SelfLearningFragment.newInstance(position,
					errExamList);
		}

		@Override
		public Object instantiateItem(ViewGroup arg0, int arg1) {

			return super.instantiateItem(arg0, arg1);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			super.destroyItem(container, position, object);
		}

	}

}
