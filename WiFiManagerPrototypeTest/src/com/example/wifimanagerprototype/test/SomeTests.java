package com.example.wifimanagerprototype.test;

import com.example.wifimanagerprototype.CompassAZActivity;
import com.example.wifimanagerprototype.CompassActivity;
import com.example.wifimanagerprototype.MainActivity;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class SomeTests extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;
	
	public SomeTests() {
		super("com.example.wifimanagerprototype.MainActivity", MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testSomething() throws Exception {

			solo.assertCurrentActivity("Expected NoteEditor activity", MainActivity.class); 
			System.out.println(solo.searchText("location"));
			solo.clickOnText("location");
			solo.sleep(1000);
			String s = solo.getCurrentActivity().toString();
			Log.d("ROBO", s);
			solo.assertCurrentActivity("should be compass", CompassAZActivity.class);
			
//			if (!solo.searchText("Map"))
//				fail();
		//	solo.clickOnText("Map");
		//	solo.assertCurrentActivity("Expected map activity", "MapActivity"); 
		    }

}
