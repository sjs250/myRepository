package com.example.mapstest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * holds the info required to setup a database and table, insert entries into
 * the table view the full table output
 * @author Owner
 *
 */
public class StrengthDB {
	
	// table row names
	public static final String KEY_ROWID = "_id";
	public static final String KEY_STRENGTH = "_wifi_strength";
	public static final String KEY_LONGITUDE = "_location_longitude";
	public static final String KEY_LATITUDE = "_location_latitude";
	// database info
	private static final String DATABASE_NAME = "Strength_DB";
	private static final String DATABASE_TABLE = "StrengthLocation";
	private static final int DATABASE_VERSION = 1;
	
	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DBHelper extends SQLiteOpenHelper {

		/** 
		 * constructor for DBHelper, calls on the supers constructor, and takes
		 * in the DATABASE_NAME, DATABASE_VERSION finals from StrengthDB
		 * @param context
		 */
		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		/**
		 * called only once when the db is first created, sets up the table/s
		 */
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE IF NOT EXISTS" + DATABASE_TABLE + " (" +
				KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				KEY_STRENGTH + " TEXT NOT NULL, " +
				KEY_LONGITUDE + " TEXT NOT NULL, " +
				KEY_LATITUDE + " TEXT NOT NULL);"									
			);		
		}

		@Override
		/**
		 * called when the db version needs to be upgraded, drops our existing
		 * table and calls the onCreate method again
		 */
		public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);		
		}	
	}
	
	/**
	 * constructor for the StrengthDB class, set up the context
	 * @param c
	 */
	public StrengthDB(Context c) {
		ourContext = c;
	}
	
	/**
	 * creates the helper for db management and then uses this to
	 * create a db
	 * @returns this
	 * @throws SQLException
	 */
	public StrengthDB open() throws SQLException{
		// create our helper for managing the database
		ourHelper = new DBHelper(ourContext);
		// creates the database 
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	/**
	 * closes the DBHelper
	 */
	public void close() {
		ourHelper.close();
	}

	/**
	 * attempts to insert the strength, longitude and latitude into the db, 
	 * returns 1 on successful insert -1 on unsuccessful
	 * @param strength
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public int createEntry(String strength, String longitude, String latitude) {
		// create content values for adding info to the table
		ContentValues cv = new ContentValues();
		cv.put(KEY_STRENGTH, strength);
		cv.put(KEY_LONGITUDE, longitude);
		cv.put(KEY_LATITUDE, latitude);
		int success = 1;
		// try an insert the content values
		try {
			ourDatabase.insert(DATABASE_TABLE, null, cv);
		} catch (Exception e) {
			success = -1;
		}		
		return success;	
	}
	
	/**
	 * prints out the contents of the table in the format 
	 * KEY_ROWID, KEY_STRENGTH, KEY_LONGITUDE, KEY_LATITUDE
	 * @return the table contents or an empty string
	 */
	public String getData() {	
		// our return string
		String result = "";
		String[] columns = new String[] {KEY_ROWID, KEY_STRENGTH, KEY_LONGITUDE, KEY_LATITUDE};
		// create a cursor to query our database
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		// index the columns
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iStrength = c.getColumnIndex(KEY_STRENGTH);
		int iLongitude = c.getColumnIndex(KEY_LONGITUDE);
		int iLatitude = c.getColumnIndex(KEY_LATITUDE);
		
		for (c.moveToFirst(); !c.isAfterLast();c.moveToNext()) {
			result = result + c.getString(iRow) + " " + c.getString(iStrength) + " "
			+ c.getShort(iLongitude) + " "  + c.getShort(iLatitude) + "\n";			
		}
		return result;
	}
}
