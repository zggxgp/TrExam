package com.hz.trexam;

import android.app.Application;

public class TrExamApplication extends Application{
	
	private int correctNum = 0;
	
	@Override
	public void onCreate() {
		setCorrectNum(0);
		super.onCreate();
	}

	public int getCorrectNum() {
		return correctNum;
	}

	public void setCorrectNum(int correctNum) {
		this.correctNum = correctNum;
	}
	
	
	
}
