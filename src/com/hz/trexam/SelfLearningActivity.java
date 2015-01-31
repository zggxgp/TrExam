package com.hz.trexam;

import java.util.List;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Script.FieldID;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hz.trexam.bean.Exam;
import com.hz.trexam.db.ExamDBManger;
import com.hz.trexam.util.ExamService;

/**
 * 
 * @author hz
 * ��ѧģʽ��activity
 */
public class SelfLearningActivity extends FragmentActivity{
	private int Num=2;//��Ŀ����Ŀ
	private int nowQuestion=0;
	private List<Exam> examInfo;//��Ŵ�XML�н�����������Ŀ����
	
	private ImageButton backBtn;
	private ImageButton favoriteBtn;
	
	private ViewPager mPager;
	private MyAdapter mAdapter;
	private TextView  examNumView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selflearning_layout);
		
		//�ײ���ʾ��ǰ��Ŀ������Ŀ
		examNumView = (TextView)findViewById(R.id.bottom_bar_text_selflearning);
		examNumView.setText("1/973");
		
		//������Ŀ����XML
		try {
			examInfo = ExamService.getExamList(MainActivity.class.getClassLoader().getResourceAsStream("1.xml"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		//�����ղذ�ť
		favoriteBtn = (ImageButton)findViewById(R.id.topbar_favorite_selflearning);
		favoriteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ExamDBManger examDBManger = new ExamDBManger(SelfLearningActivity.this);
				examDBManger.getWriteDataBaseConn();
				ContentValues values = new ContentValues();
				values.put("ordernum", nowQuestion);
				examDBManger.insert("favorite", null, values);
				
				Toast.makeText(SelfLearningActivity.this, "��ӵ��ղ�", Toast.LENGTH_SHORT).show();
			}
		});
		
		//viewpager����adapter
		mAdapter = new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager)findViewById(R.id.pager_selflearning);
		mPager.setAdapter(mAdapter);
		
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				int templc = position+1;
				nowQuestion = position;
				examNumView.setText(""+templc+"/973");
				int temp = Num-position;
				if(temp<=2){
					Num++;
					mAdapter.notifyDataSetChanged();
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
	
		
		
		//���������水ť
		backBtn = (ImageButton)findViewById(R.id.backtomain_selflearning);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intentToMain = new Intent(SelfLearningActivity.this, MainActivity.class);
				startActivity(intentToMain);
				
			}
		});
		
	}
	 
	
	
	public class MyAdapter extends FragmentStatePagerAdapter{
		
		public MyAdapter(FragmentManager fm) {  
            super(fm);  
        }
		
		
		@Override
		public int getCount() {
			
			return Num;
		} 
		
		@Override
		public Fragment getItem(int position) {
			
			return (Fragment)SelfLearningFragment.newInstance(position, examInfo);
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
