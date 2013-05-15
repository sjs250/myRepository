package com.example.wifimanagerprototype.test;

import com.example.wifimanagerprototype.CameraAZActivity;
import com.example.wifimanagerprototype.CameraActivity;
import com.example.wifimanagerprototype.CameraFavouritesActivity;
import com.example.wifimanagerprototype.CompassAZActivity;
import com.example.wifimanagerprototype.CompassActivity;
import com.example.wifimanagerprototype.CompassFavouritesActivity;
import com.example.wifimanagerprototype.MainActivity;
import com.example.wifimanagerprototype.MapActivity;
import com.example.wifimanagerprototype.MapFavoriteActivity;
import com.example.wifimanagerprototype.SettingsActivity;
import com.jayway.android.robotium.solo.Solo;
import com.example.wifimanagerprototype.R;
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
	
	/*
	 * these test must be run with an android emulator open and unlocked or a compatible phone device connected and unlocked
	 */
	public void testLinks() throws Exception {
			solo.assertCurrentActivity("Expected mainactivity activity", MainActivity.class); 
//			these tests wont work on on emulator due to issues with googlemaps, but should be fine on a phone device
//			solo.clickOnText("map");
//			solo.sleep(1000);
//			solo.assertCurrentActivity("should be map", MapActivity.class);
//			solo.goBack();
			solo.clickOnText("location");
			solo.sleep(1000);
			String s = solo.getCurrentActivity().toString();
			Log.d("ROBO", s);
			solo.assertCurrentActivity("should be CompassAZActivity", CompassAZActivity.class);
			//search not currently implemented but should pass
			solo.clickOnText("Search");
			solo.clickOnText("eduroam");
			solo.sleep(1000);
			solo.assertCurrentActivity("should be CompassActivity", CompassActivity.class);
			solo.goBack();
			solo.goBack();
			solo.sleep(1000);
			solo.assertCurrentActivity("Expected mainactivity activity", MainActivity.class);
			solo.clickOnText("camera");
			solo.sleep(1000);
			solo.assertCurrentActivity("Expected CameraAZActivty activity", CameraAZActivity.class);
//			solo.clickOnText("eduroam");
//			solo.sleep(1000);
//			solo.assertCurrentActivity("should be map", CameraActivity.class);
//			solo.goBack();
		    }
	
	// the following menu tests test the various page menus to make sure the pages are correctly linked
	// and the proper activities are launching
	public void testMenuA() throws Exception {
		solo.clickOnMenuItem("Settings");
		solo.assertCurrentActivity("should be settings activity", SettingsActivity.class);	
	}
	
	
	public void testMenuB() throws Exception {
		solo.clickOnText("location");
		solo.clickOnText("eduroam");
		// return to the compass list of activities
		solo.clickOnMenuItem("A - Z");
		solo.sleep(1000);
		solo.assertCurrentActivity("should be CompassAZActivity", CompassAZActivity.class);	
		solo.goBack();
		solo.clickOnMenuItem("Favourite");
		solo.sleep(1000);
		solo.assertCurrentActivity("should be compass favourites", CompassFavouritesActivity.class);
		solo.goBack();
		solo.clickOnMenuItem("Settings");
		solo.assertCurrentActivity("should be settings activity", SettingsActivity.class);
		solo.goBack();
		solo.clickOnMenuItem("Home");
		solo.sleep(1000);
		solo.assertCurrentActivity("should be Main", MainActivity.class);
	}
	
	public void testMenuC() throws Exception {
		solo.clickOnText("location");
		solo.clickOnText("Search");
		solo.sleep(1000);
		solo.assertCurrentActivity("should be CompassAZActivity", CompassAZActivity.class);	
		solo.clickOnMenuItem("Favourite");
		solo.sleep(1000);
		solo.assertCurrentActivity("should be compass favourites", CompassFavouritesActivity.class);
		solo.goBack();
		solo.clickOnMenuItem("Settings");
		solo.assertCurrentActivity("should be settings activity", SettingsActivity.class);
		solo.goBack();
		solo.clickOnMenuItem("Home");
		solo.sleep(1000);
		solo.assertCurrentActivity("should be Main", MainActivity.class);
	}
	
	public void testMenuD() throws Exception {
		solo.clickOnText("Camera");
		solo.sleep(1000);
		solo.assertCurrentActivity("should be CameraAZActivity", CameraAZActivity.class);	
		solo.clickOnMenuItem("Favourite");
		solo.sleep(1000);
		solo.assertCurrentActivity("should be compass favourites", CameraFavouritesActivity.class);
		solo.goBack();
		solo.clickOnMenuItem("Settings");
		solo.assertCurrentActivity("should be settings activity", SettingsActivity.class);
		solo.goBack();
		solo.clickOnMenuItem("Home");
		solo.sleep(1000);
		solo.assertCurrentActivity("should be Main", MainActivity.class);
	}
	
	//tests the buttons which screen out results from a search, this test is presently done using x and y co-ordinates
	//so may not work correctly on a device not of 480-800 dimensions
	public void testScreeningButtons() throws Exception {
		solo.clickOnText("location");
		solo.sleep(1000);
		solo.clickOnScreen(250, 750);
		solo.sleep(1000);
		solo.clickOnScreen(350, 750);
		solo.sleep(1000);
		solo.clickOnScreen(150, 750);
		solo.goBack();
		solo.clickOnText("compass");
		solo.clickOnScreen(250, 750);
		solo.sleep(1000);
		solo.clickOnScreen(350, 750);
		solo.sleep(1000);
		solo.clickOnScreen(150, 750);
	}
	
	public void testInfoCompass() {
		solo.clickOnText("location");
		solo.clickOnText("eduroam");	
		solo.clickOnImage(1);
		solo.sleep(1000);
	}
	
	// the following 4 tests can only be run on an android enabled device, due to issues
	// with googlemaps and lack of functionality of a camera device on an emulator
	
//	public void testMenuE() throws Exception {
//		solo.clickOnText("Camera");
//		solo.clickOnText("eduroam");
//		solo.sleep(1000);
//		solo.assertCurrentActivity("should be CameraActivity", CameraActivity.class);	
//		solo.clickOnMenuItem("Favourite");
//		solo.sleep(1000);
//		solo.assertCurrentActivity("should be camera favourites", CameraFavouritesActivity.class);
//		solo.goBack();
//		solo.clickOnMenuItem("Settings");
//		solo.sleep(1000);
//		solo.assertCurrentActivity("should be settings activity", SettingsActivity.class);
//		solo.goBack();
//		solo.clickOnMenuItem("Home");
//		solo.sleep(1000);
//		solo.assertCurrentActivity("should be Main", MainActivity.class);
//		}
//	
//	public void testMenuF() throws Exception {
//		solo.clickOnText("Map");
//		solo.sleep(1000);
//		solo.assertCurrentActivity("should be mapActivity", MapActivity.class);	
//		solo.clickOnMenuItem("Favourite");
//		solo.sleep(1000);
//		solo.assertCurrentActivity("should be map favourites", MapFavoriteActivity.class);
//		solo.goBack();
//		solo.clickOnMenuItem("Settings");
//		solo.sleep(1000);
//		solo.assertCurrentActivity("should be settings activity", SettingsActivity.class);
//		solo.goBack();
//		solo.clickOnMenuItem("Home");
//		solo.sleep(1000);
//		solo.assertCurrentActivity("should be Main", MainActivity.class);
//		}
			
//	public void testInfoCheckboxCamera() {
//		solo.clickOnText("map");
//		solo.clickOnImage(1);
//		solo.clickOnText("Ok");
//		solo.sleep(1000);
//		solo.clickOnCheckBox(0);
//	}
//
//	public void testInfoCheckboxMap() {
//		solo.clickOnText("Camera");
//		solo.clickOnText("eduroam");	
//		solo.clickOnImage(1);
//		solo.clickOnText("Ok");
//		solo.sleep(1000);
//		solo.clickOnCheckBox(0);
//	}
	
	
	@Override
	   public void tearDown() throws Exception {
	        solo.finishOpenedActivities();
	  }
}
