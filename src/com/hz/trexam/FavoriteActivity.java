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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hz.trexam.bean.Exam;
import com.hz.trexam.db.ExamDBManger;
import com.hz.trexam.util.GetTheFavoriteList;

/**
 * 
 * @author hz 题目收藏activity
 */
public class FavoriteActivity extends FragmentActivity {

	private int Num = 2;
	private int totalNum;
	private int nowQue = 0;
	private List<Exam> favExamList;
	private ImageButton backBtn;

	private ViewPager mViewPager;
	private FavAdapter favAdapter;
	private TextView examNumView;
	private ImageButton btnDelete;
	private TextView noFavorite;
	private AlertDialog.Builder alertDialog;

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
		noFavorite = (TextView)findViewById(R.id.nofavorite);
		
		if(totalNum>=1){
			examNumView.setText("" + 1 + "/" + totalNum);
		}else{
			examNumView.setText("" + 0 + "/" + totalNum);
			noFavorite.setText("您还没有收藏过题目, 您可以到自学模式中收藏你想要的题目");
			noFavorite.setVisibility(View.VISIBLE);
		}
		

		// 设置删除当前题目按钮
		btnDelete = (ImageButton) findViewById(R.id.topbar_delete_favorite);
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog = new AlertDialog.Builder(FavoriteActivity.this);
				alertDialog.setTitle("注意");
				alertDialog.setMessage("确定要删除当前题目吗？");

				alertDialog.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});

				alertDialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ExamDBManger examDBManger = new ExamDBManger(
										FavoriteActivity.this);
								examDBManger.getWriteDataBaseConn();
								String nowQueStr = String.valueOf(nowQue);
								examDBManger.delete("favorite", "ordernum=?",
										new String[] { nowQueStr });
								totalNum--;
								
								favExamList.remove(nowQue);
								favAdapter.notifyDataSetChanged();
								examNumView.setText("" + nowQue + "/" + totalNum);
								
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
		// 设置返回按钮
		backBtn = (ImageButton) findViewById(R.id.backtomain_favorite);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentToMain = new Intent(FavoriteActivity.this,
						MainActivity.class);
				startActivity(intentToMain);

			}
		});

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
				nowQue = position;
				
				/*
				if (temp <= 2 && (Num + 1) <= totalNum) {
					Num++;
					favAdapter.notifyDataSetChanged();
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

	}

	public class FavAdapter extends FragmentStatePagerAdapter {

		public FavAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {

			return totalNum;
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
