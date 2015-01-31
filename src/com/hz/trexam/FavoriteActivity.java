package com.hz.trexam;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hz.trexam.bean.Exam;
import com.hz.trexam.util.GetTheFavoriteList;

/**
 * 
 * @author hz 题目收藏activity
 */
public class FavoriteActivity extends FragmentActivity {

	private int Num = 2;
	private int totalNum;
	private List<Exam> favExamList;
	private ImageButton backBtn;

	private ViewPager mViewPager;
	private FavAdapter favAdapter;
	private TextView examNumView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.favorite_layout);

		// 获取收藏的题目列表
		favExamList = GetTheFavoriteList.getTheFavoriteExamList(this);

		// 获取总的题目数
		totalNum = favExamList.size();

		// 初始化当前题号和总题目数
		examNumView = (TextView) findViewById(R.id.bottom_bar_text_favorite);
		examNumView.setText("" + 1 + "/" + totalNum);

		// viewpager设置adapter
		favAdapter = new FavAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager_favorite);
		mViewPager.setAdapter(favAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				int templc = position + 1;
				examNumView.setText("" + templc + "/" + totalNum);
				int temp = Num - position;

				if (temp <= 2 && (Num + 1) <= totalNum) {
					Num++;
					favAdapter.notifyDataSetChanged();
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

	}

	public class FavAdapter extends FragmentStatePagerAdapter {

		public FavAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {

			return Num;
		}

		@Override
		public Fragment getItem(int position) {

			return (Fragment) SelfLearningFragment.newInstance(position,
					favExamList);
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
