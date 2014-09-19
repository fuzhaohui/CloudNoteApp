/**
 *
 */
package com.ces.cloudnote.app.utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ces.cloudnote.app.R;

/**
 * @author fan.zhang
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {
	private static String DB_PATH = "/data/data/your_package_name/databases/";
	private static final String DB_NAME = "your_db_name.db";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	/**
	 * inital your database from your local res-raw-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled.
	 * */
	private void initDataBase()throws IOException {

		// Open your local db as the input stream
		// InputStream myInput = myContext.getAssets().open(DB_NAME);
		InputStream myInput = myContext.getResources().openRawResource(
				R.raw.inte_db);
		InputStreamReader reader = new InputStreamReader(myInput);
		BufferedReader breader = new BufferedReader(reader);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME,
		str;

		// Open the empty db as the output stream
		FileWriter myOutput = new FileWriter(outFileName, true);
		while ((str = breader.readLine()) != null) {
			myDataBase.execSQL(str); //exec your SQL line by line.
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		myDataBase = db;

		try {
			this.initDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		boolean readOnly = db.isReadOnly();
	}
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}