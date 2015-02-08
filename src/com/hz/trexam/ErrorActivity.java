package com.hz.trexam;

import java.util.List;

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
import com.hz.trexam.util.GetTheErrorList;

/**
 * 
 * @author hz 错题模式activity
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

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		setContentView(R.layout.error_layout);

		// 获取错题列表
		errExamList = GetTheErrorList.getTheErrorExamList(this);

		// 根据错题列表list长度来调整Num
		Num = errExamList.size();
		totalNum = Num;

		// 初始化总题数和当前题号
		examNumView = (TextView) findViewById(R.id.bottom_bar_text_error);
		examNumView.setText("" + 1 + "/" + Num);

		// viewpager设置adapter
		errAdapter = new ErrorAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager_error);
		mViewPager.setAdapter(errAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				int templc = position + 1;
				examNumView.setText("" + templc + "/" + Num);
				int temp = Num - position;

				if (temp <= 2 && (Num + 1) <= totalNum) {
					Num++;
					errAdapter.notifyDataSetChanged();
				}

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

		// 设置返回按钮
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

			return Num;
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
