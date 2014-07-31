/**
 * Copyright (c) 2008-2013 	Copyright © 2013 AutoNavi.All rights reserved.  
 * CloudNoteApp - 2013-6-3
 * 
 * 相关描述： 
 * 
 */
package com.ces.cloudnote.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 相关描述：
 *
 * 文件名：SqlLiteUtils.java
 * 作者： AutoNavi 
 * 完成时间：2013-6-3 下午2:22:46 
 * 维护人员：AutoNavi  
 * 维护时间：2013-6-3 下午2:22:46 
 * 维护原因：  
 * 当前版本： v1.0 
 *
 */
public class FeedReaderDbHelper  extends SQLiteOpenHelper{
	//private static final String TEXT_TYPE = " TEXT";
	//private static final String COMMA_SEP = ", ";
	private static final String CREATE_ENTRY_SQL = "CREATE TABLE USER (ID INTEGER PRIMARY KEY, USERNAME VARCHAR(32) NOT NULL, PASSWORD VARCHAR(32))";
	private static final String DROP_ENTRY_SQL = "DROP TABLE";
	
	public static final int DATABASE_VERSION = 3;
	public static final String DATABASE_NAME = "FeedReader.db";

	public FeedReaderDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_ENTRY_SQL);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(DROP_ENTRY_SQL);
		onCreate(db);
	}

}
