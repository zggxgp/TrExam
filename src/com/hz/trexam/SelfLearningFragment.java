package com.hz.trexam;


import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
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
import com.hz.trexam.db.ExamDBManger;

public class SelfLearningFragment extends Fragment{
	
	private int  mNum;//�ڼ�����Ŀ
	private List<Exam> examInfo;//��Ŀ��Ϣlist
	private InputStream is;//��Ŀ��ӦͼƬ������
	
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
		final int tempNum = mNum;
		final View v = inflater.inflate(R.layout.selflearning_fragment, null);
		//������Ŀ
		TextView tvQuestion = (TextView)v.findViewById(R.id.tv_question_area_selflearning);
		tvQuestion.setText(examInfo.get(tempNum).getQuestion());
		
		//������Ŀ��Ӧ��ͼƬ�������ڣ�
		if(examInfo.get(tempNum).getImgName()!=null&&!examInfo.get(tempNum).getImgName().equals("")){
			ImageView imgQuestion = (ImageView)v.findViewById(R.id.iv_question_area_selflearning);
			imgQuestion.setVisibility(View.VISIBLE);
			
			try {
				is = getResources().getAssets().open(examInfo.get(tempNum).getImgName());
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			Bitmap bitMap = BitmapFactory.decodeStream(is);
			imgQuestion.setImageBitmap(bitMap);
		}
		
		
		//����ѡ��
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
		
		//��ʾ������
		RadioGroup rdgp = (RadioGroup)v.findViewById(R.id.option_selflearning);
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
						answerStr="��ȷ";
					}else if(answer==2){
						answerStr="����";
					}
				}
				
				
				
				
				if(answer==answerId){
					TextView resultView = (TextView)v.findViewById(R.id.result_selflearning);
					resultView.setVisibility(View.VISIBLE);
					resultView.setBackgroundColor(Color.GREEN);
					resultView.setText("��ϲ���ش���ȷ��");
					
				}else{
					TextView resultView = (TextView)v.findViewById(R.id.result_selflearning);
					resultView.setVisibility(View.VISIBLE);
					resultView.setBackgroundColor(Color.RED);
					resultView.setText("���ź���������ˡ�����"+answerStr);
					
					//�������ݿ⣬�������ŵ��������
					ExamDBManger examDBManger = new ExamDBManger(getActivity());
					examDBManger.getWriteDataBaseConn();
					ContentValues values = new ContentValues();
					values.put("ordernum", tempNum);
					examDBManger.insert("error", null, values);
				}
			}
		});
		
		
		
		
		return v;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	
}
