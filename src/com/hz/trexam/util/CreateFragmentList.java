package com.hz.trexam.util;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

import com.hz.trexam.MainActivity;
import com.hz.trexam.TestFragment;
import com.hz.trexam.bean.Exam;

/**
 * 
 * @author hz
 * 根据生成的100个随机数从选择一百道题目生成对应的fragment然后返回fragmentlist;
 *
 */
public class CreateFragmentList {
	
	public static List<Fragment> getFragmentList(){
		List<Fragment> fragmentList = new ArrayList<Fragment>();
		List<Integer> randomList = MyRandom.getRandomList();
		List<Exam> examInfo = null;
		try {
			examInfo = ExamService.getExamList(MainActivity.class.getClassLoader().getResourceAsStream("1.xml"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		for(int i=0;i<randomList.size();i++){
			TestFragment testFragment = new TestFragment(randomList.get(i), examInfo);
			fragmentList.add(testFragment);
		}
		
		
		return fragmentList;
	}
}
