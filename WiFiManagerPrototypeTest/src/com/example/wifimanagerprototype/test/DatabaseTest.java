package com.example.wifimanagerprototype.test;

// import com.example.wifimanagerprototype.StrengthDB;


import org.junit.Assert;
import org.junit.Test;

import com.example.wifimanagerprototype.DrawSurfaceView;
import com.example.wifimanagerprototype.Point;

import android.database.SQLException;

public class DatabaseTest {
	
	// what would be test for an external db
	@Test
	public void getDataTest() {
		
//		//		should throw sql errors for wrong type errors for wrong type		
//		//		StrengthDB db = new StrengthDB();
//		String s = "";
//		try{
//			//		db.createEntry(eduroam, high, 1, 1, open);
//		} catch (SQLException e) {
//			s = exceptionA;
//		}
//		Assert.assertEquals("exceptionA", s);
//		try{
//			//		db.createEntry(eduroam, 10, yes, 1, open);
//		} catch (SQLException e) {
//			s = exceptionB;
//		}
//		Assert.assertEquals("exceptionB", s);
//		try{
//			//		db.createEntry(eduroam, 10, 1, no, open);
//		} catch (SQLException e) {
//			s = exceptionC;
//		}
//		Assert.assertEquals("exceptionC", s);
//		try{
//			//		db.createEntry(eduroam, 10, 1, 1, 50);
//		} catch (SQLException e) {
//			s = exceptionD;
//		}
//		Assert.assertEquals("exceptionD", s);
//		try{
//			//		db.createEntry(10, 10, 1, 1, open);
//		} catch (SQLException e) {
//			s = exceptionE;
//		}
//		Assert.assertEquals("exceptionE", s);
	}
	
	@Test
	// tests the point class to set to 0 if outside max/min ranges of lat/long values as well
	// as basic comparison test
	public void testPoint() {
		Point p = new Point(200,100, "test");
		Assert.assertEquals(p.latitude, 0, 0);
		p = new Point(85,85, "test");
		Assert.assertEquals(p.latitude, 85, 0);
		Assert.assertEquals(p.longitude, 85, 0);
		Assert.assertEquals(p.description, "test");
		p = new Point(90,180, "test");
	}
}
