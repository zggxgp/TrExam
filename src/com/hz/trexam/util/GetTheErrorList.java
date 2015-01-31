package com.hz.trexam.util;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.database.Cursor;

import com.hz.trexam.MainActivity;
import com.hz.trexam.bean.Exam;
import com.hz.trexam.db.ExamDBManger;

/**
 * 
 * @author hz
 *	从数据库中获取错题列表
 */
public class GetTheErrorList {
	
	
	public static List<Exam> getTheErrorExamList(Context context){
		List<Exam> errExamList = new ArrayList<Exam>();
		List<Exam> examInfo = new ArrayList<Exam>();
		List<Integer> errOrderList = new ArrayList<Integer>();
		
		errOrderList = getTheErrorList(context);
		
		
		try {
			examInfo = ExamService.getExamList(MainActivity.class.getClassLoader().getResourceAsStream("1.xml"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		for(int i = 0; i<errOrderList.size();i++){
			errExamList.add(examInfo.get(errOrderList.get(i)));
		}
		
		return errExamList;
	}
	
	
	public static List<Integer> getTheErrorList(Context context){
		List<Integer> errOrderList = new ArrayList<Integer>();
		
		ExamDBManger examDBManger = new ExamDBManger(context);
		examDBManger.getReadDataBaseConn();
		Cursor cursor = examDBManger.query("error", null, null, 
										   null, null, null, 
										   null, null);
		while(cursor.moveToNext()){
			
			errOrderList.add(cursor.getInt(cursor.getColumnIndex("ordernum")));
			
		}
		
		return errOrderList;
	}
}
