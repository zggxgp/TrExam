package com.hz.trexam.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.hz.trexam.MainActivity;
import com.hz.trexam.bean.Exam;
import com.hz.trexam.db.ExamDBManger;

public class GetTheFavoriteList {
	
	public static List<Exam> getTheFavoriteExamList(Context context){
		List<Exam> favExamList = new ArrayList<Exam>();
		List<Exam> examInfo = new ArrayList<Exam>();
		List<Integer> favOrderList = new ArrayList<Integer>();
		
		favOrderList = getTheFavoriteList(context);
		
		
		try {
			examInfo = ExamService.getExamList(MainActivity.class.getClassLoader().getResourceAsStream("1.xml"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		for(int i = 0; i<favOrderList.size();i++){
			favExamList.add(examInfo.get(favOrderList.get(i)));
		}
		
		return favExamList;
	}
	
	
	public static List<Integer> getTheFavoriteList(Context context){
		List<Integer> favOrderList = new ArrayList<Integer>();
		
		ExamDBManger examDBManger = new ExamDBManger(context);
		examDBManger.getReadDataBaseConn();
		Cursor cursor = examDBManger.query("favorite", null, null, 
										   null, null, null, 
										   null, null);
		while(cursor.moveToNext()){
			
			favOrderList.add(cursor.getInt(cursor.getColumnIndex("ordernum")));
			
		}
		
		return favOrderList;
	}
}
