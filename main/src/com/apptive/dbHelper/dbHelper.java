package com.apptive.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper{
	
	private static final String DB_NAME = "intolaw.db";
	private static final String DB_TABLE1 = "hometown";
	private static final int DB_VERSION = 2;

	private Context context;
	
	public dbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion < newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE1 );
			onCreate(db);
		}
	}
	
}
