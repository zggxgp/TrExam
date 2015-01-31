package com.hz.trexam.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author hz
 *   数据库操作类
 */

public class ExamDBManger {
	
	private ExamDBHelper examDBHelper;
	private SQLiteDatabase database;
	
	
	public ExamDBManger(Context context) {
		examDBHelper = new ExamDBHelper(context);
	}
	
	public void getWriteDataBaseConn() {
		database = examDBHelper.getWritableDatabase();
	}
	
	public void getReadDataBaseConn() {
		database = examDBHelper.getReadableDatabase();
	}
	
	public void releaseConn() {
		if (database != null) {
			database.close();
		}
	}
	
	
	public boolean insert(String table, String nullColumnHack, ContentValues values){
		boolean flag = false;
		long id = database.insert(table, nullColumnHack, values);
		flag = (id>0?true:false);
		return flag;
	}
	
	public boolean delete(String table, String whereClause, String[] whereArgs) {
		boolean flag = false;
		int count = database.delete(table, whereClause, whereArgs);
		flag = (count > 0 ? true : false);
		return flag;
		
		
	}
	
	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		Cursor cursor = null;
		cursor = database.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);
		return cursor;
	}

	
	

}
