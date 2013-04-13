package com.example.mapstest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DataBaseHelper extends SQLiteOpenHelper{

	//The Android's default system path of your application database.
	private static final String DB_PATH = "/data/data/com.example.mapstest/databases/";
	private static final String DB_NAME = "wifis.db";
	private static final String TABLE_NAME = "Strength";
	// row name
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "Name";
	public static final String KEY_STRENGTH = "StrengthRow";
	public static final String KEY_LONGITUDE = "Longitude";
	public static final String KEY_LATITUDE = "Latitude";
	public static final String KEY_SECURITY = "Security";
	
	private SQLiteDatabase myDB;
	private final Context myContext;

	/**
	 * Constructor
	 * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	 * @param context
	 */
	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}	

	/**
	 * Creates a empty database on the system and rewrites it with your own database.
	 * */
	public void createDataBase() throws IOException{

		boolean dbExist = checkDataBase();

		if(dbExist){
			//do nothing - database already exist
		}else{

			//By calling this method and empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase(){

		SQLiteDatabase checkDB = null;

		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

		}catch(SQLiteException e){
			//database does't exist yet.
		}

		if(checkDB != null){
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException{

		//Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException{

		//Open the database
		String myPath = DB_PATH + DB_NAME;
		myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {

		if(myDB != null)
			myDB.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public int createEntry(String name, String strength, String longitude, String latitude, String security) {
		// create content values for adding info to the table
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_STRENGTH, strength);
		cv.put(KEY_LONGITUDE, longitude);
		cv.put(KEY_LATITUDE, latitude);
		cv.put(KEY_SECURITY, security);
		int success = 1;
		// try an insert the content values
		try {
			myDB.insert(TABLE_NAME, null, cv);
		} catch (Exception e) {
			success = -1;
		}		
		return success;	
	}
	
	/**
	 * prints out the contents of the table in the format 
	 * KEY_ROWID, KEY_NAME, KEY_STRENGTH, KEY_LONGITUDE, KEY_LATITUDE, KEY_SECURITY
	 * @return the table contents or an empty string
	 */
	public String getData() {	
		// our return string
		String result = "";
		String[] columns = new String[] {KEY_ROWID, KEY_NAME, KEY_STRENGTH, KEY_LONGITUDE, KEY_LATITUDE, KEY_SECURITY};
		// create a cursor to query our database
		Cursor c = myDB.query(TABLE_NAME, columns, null, null, null, null, null);
		// index the columns
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iStrength = c.getColumnIndex(KEY_STRENGTH);
		int iLongitude = c.getColumnIndex(KEY_LONGITUDE);
		int iLatitude = c.getColumnIndex(KEY_LATITUDE);
		int iSecurity = c.getColumnIndex(KEY_SECURITY);
		
		for (c.moveToFirst(); !c.isAfterLast();c.moveToNext()) {
			result = result + c.getString(iRow) + " " + c.getString(iName)+ " " + c.getString(iStrength) + " "
			+ c.getString(iLongitude) + " "  + c.getString(iLatitude) + " " + c.getString(iSecurity) + "\n";			
		}
		return result;
	}
	
	public int getRows() {
		String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
	    SQLiteStatement statement = myDB.compileStatement(sql);
	    return (int) statement.simpleQueryForLong();
	}
	
	public void addMarks(GoogleMap map) {
		String[] columns = new String[] {KEY_ROWID, KEY_NAME, KEY_STRENGTH, KEY_LONGITUDE, KEY_LATITUDE, KEY_SECURITY};
		// create a cursor to query our database
		Cursor c = myDB.query(TABLE_NAME, columns, null, null, null, null, null);
		// index the columns
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iStrength = c.getColumnIndex(KEY_STRENGTH);
		int iLongitude = c.getColumnIndex(KEY_LONGITUDE);
		int iLatitude = c.getColumnIndex(KEY_LATITUDE);
		int iSecurity = c.getColumnIndex(KEY_SECURITY);
		
		for (c.moveToFirst(); !c.isAfterLast();c.moveToNext()) {
			double myLat = Double.parseDouble(c.getString(iLatitude));
			double myLong = Double.parseDouble(c.getString(iLongitude));
			LatLng myLatLng = new LatLng(myLat, myLong);
			Marker m = map.addMarker(new MarkerOptions()
					.position(myLatLng)
					.title(c.getString(iName))
					.snippet (c.getString(iStrength)+ " " + c.getString(iSecurity))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));		
		}
		return;
	}

}
