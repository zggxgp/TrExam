package com.hz.trexam;


import java.io.Serializable;
import java.util.List;

import android.graphics.Path.Op;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hz.trexam.bean.Exam;

public class SelfLearningFragment extends Fragment{
	int mNum;
	List<Exam> examInfo;
	
	static SelfLearningFragment newInstance(int num,  List<Exam> tempInfo) {  
		SelfLearningFragment slFragment= new SelfLearningFragment(); 
        Bundle args = new Bundle();  
        args.putInt("num", num);  
        Serializable tempList = (Serializable)tempInfo;
        args.putSerializable("examlist", tempList);
        slFragment.setArguments(args);  
        return slFragment;  
    }  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;  
        examInfo = (List<Exam>) getArguments().getSerializable("examlist");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int tempNum = mNum;
		View v = inflater.inflate(R.layout.selflearning_fragment, null);
		//设置题目
		TextView tvQuestion = (TextView)v.findViewById(R.id.tv_question_area_selflearning);
		tvQuestion.setText(examInfo.get(tempNum).getQuestion());
		
		ImageView imgQuestion = (ImageView)v.findViewById(R.id.iv_question_area_selflearning);
		imgQuestion.setVisibility(View.VISIBLE);
		
		//设置选项
		String tempOption = examInfo.get(tempNum).getOption();
		String optionArray[] = tempOption.split("@@");
		RadioButton option1 = (RadioButton)v.findViewById(R.id.option_0);
		RadioButton option2 = (RadioButton)v.findViewById(R.id.option_1);
		if(optionArray.length>2){
			RadioButton option3 = (RadioButton)v.findViewById(R.id.option_2);
			RadioButton option4 = (RadioButton)v.findViewById(R.id.option_3);
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
		
		
		
		
		return v;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	
}
