package com.hz.trexam.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

public class ExamDBHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME ="exam.db";
	private static final int DATABASE_VERSION = 1; 
	
	
	public ExamDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table favorite(pid integer primary key autoincrement, ordernum integer UNIQUE)";
		String sql2 = "create table error(pid integer primary key autoincrement, ordernum integer UNIQUE)";
		db.execSQL(sql);
		db.execSQL(sql2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
}
