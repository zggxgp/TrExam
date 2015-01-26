package com.hz.trexam;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.hz.trexam.bean.Exam;

public class TestFragment extends Fragment{
	
	private int mNum;//第几道题目
	private List<Exam> examInfo;//题目信息list;
	private InputStream is;//题目对应图片输入流
	private View rootView = null;
	TrExamApplication trApp;
	
	public TestFragment() {
		super();
		
	}
	
	public TestFragment(int mNum, List<Exam> examInfo) {
		super();
		this.examInfo = examInfo;
		this.mNum = mNum;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final int tempNum = mNum;
		if(rootView==null){
			
			rootView = inflater.inflate(R.layout.selflearning_fragment, null);
			
			//设置题目
			TextView tvQuestion = (TextView)rootView.findViewById(R.id.tv_question_area_selflearning);
			tvQuestion.setText(examInfo.get(tempNum).getQuestion());
			
			//设置题目对应的图片（若存在）
			if(examInfo.get(tempNum).getImgName()!=null&&!examInfo.get(tempNum).getImgName().equals("")){
				ImageView imgQuestion = (ImageView)rootView.findViewById(R.id.iv_question_area_selflearning);
				imgQuestion.setVisibility(View.VISIBLE);
				
				try {
					is = getResources().getAssets().open(examInfo.get(tempNum).getImgName());
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				Bitmap bitMap = BitmapFactory.decodeStream(is);
				imgQuestion.setImageBitmap(bitMap);
			}
			
			//设置选项
			String tempOption = examInfo.get(tempNum).getOption();
			String optionArray[] = tempOption.split("@@");
			RadioButton option1 = (RadioButton)rootView.findViewById(R.id.option_0);
			RadioButton option2 = (RadioButton)rootView.findViewById(R.id.option_1);
			if(optionArray.length>2){
				RadioButton option3 = (RadioButton)rootView.findViewById(R.id.option_2);
				RadioButton option4 = (RadioButton)rootView.findViewById(R.id.option_3);
				option3.setVisibility(View.VISIBLE);
				option4.setVisibility(View.VISIBLE);
				
				option1.setText("A     "+optionArray[0]);
				option2.setText("B     "+optionArray[1]);
				option3.setText("C     "+optionArray[2]);
				option4.setText("D     "+optionArray[3]);
			}else{
				option1.setGravity(Gravity.CENTER);
				option2.setGravity(Gravity.CENTER);
				option1.setText(optionArray[0]);
				option2.setText(optionArray[1]);
			}
			
			
			//显示答题结果
			RadioGroup rdgp = (RadioGroup)rootView.findViewById(R.id.option_selflearning);
			rdgp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					int answerId=0;
					String answerStr="Z";
					if(checkedId==R.id.option_0){
						answerId=1;
					}else if(checkedId==R.id.option_1){
						answerId=2;
					}else if(checkedId==R.id.option_2){
						answerId=3;
					}else if(checkedId==R.id.option_3){
						answerId=4;
					}
					
					int answer = Integer.valueOf(examInfo.get(tempNum).getAnswer());
					
					if(examInfo.get(tempNum).getOption().split("@@").length>2){
						if(answer==1){
							answerStr="A";
						}else if(answer==2){
							answerStr="B";
						}else if(answer==3){
							answerStr="C";
						}else if(answer==4){
							answerStr="D";
						}
					}else{
						if(answer==1){
							answerStr="正确";
						}else if(answer==2){
							answerStr="错误";
						}
					}
					
					
					
					//统计答对的题目数量
					if(answer==answerId){
						trApp = (TrExamApplication)getActivity().getApplication();
						int correctNum = trApp.getCorrectNum();
						correctNum++;
						trApp.setCorrectNum(correctNum);
					}
				}
			});
					
		}
		
		ViewGroup parent = (ViewGroup) rootView.getParent();  
		if (parent != null) {  
            parent.removeView(rootView);  
        }   
		
		return rootView;
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	
}
