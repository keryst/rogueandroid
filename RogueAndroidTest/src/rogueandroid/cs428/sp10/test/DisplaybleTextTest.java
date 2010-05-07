/*
 * Copyright (C) 2010 Glass, Glovinsky, Kim, Kristola, Lai, Millson, Svitek
 *
 * This source code is licensed under the Apache License, Version 2.0.
 * You may not use this file except in compliance with the license.
 * You may obtain a copy of the license at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by written agreement or applicable law, this software
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either implied or express.  See the license for details.
 */

package rogueandroid.cs428.sp10.test;

import com.stickycoding.Rokon.Font;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.TextureAtlas;

import rogueandroid.cs428.sp10.RogueAndroid;
import rogueandroid.cs428.sp10.ScreenPixelCoordinate;
import rogueandroid.cs428.sp10.view.DisplayableEntity;
import rogueandroid.cs428.sp10.view.DisplayableText;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

/**
 * @author Drew Glass
 *
 */
public class DisplaybleTextTest extends ActivityInstrumentationTestCase2<RogueAndroid> {
	
	public Rokon mRokon;
	public TextureAtlas mAtlas;
	public DisplayableEntity mDisplay;
	private DisplayableText mDispHP;
	private Font mFont;
	
	public static int tileSize = 96;
	
	public DisplaybleTextTest(String name) {
		super("rogueandroid.cs428.sp10",RogueAndroid.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		mRokon = Rokon.createEngine(this.getActivity(), 854, 480);
		mAtlas = new TextureAtlas(1024, 2048);
		
		String hpString = "HP";
	
		mAtlas.insert(mFont = new Font("fonts/ARIAL.TTF"));
		mFont.setTextureAtlas(mAtlas);
		
		mDispHP= new DisplayableText(mAtlas, mRokon, hpString, mFont);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testTextLocation() throws Throwable {
		ScreenPixelCoordinate newLocation = new ScreenPixelCoordinate(55, 55);
		mDispHP.setXY(newLocation);
		ScreenPixelCoordinate currentLocation = mDispHP.getXY().toScreen();
		
		// test to see that location of text is set correctly
		Assert.assertTrue(currentLocation.equals(newLocation));

	}

	public void testSetText() throws Throwable {
		mDispHP.setText("NEW HP");
		
		// test to see that the text string is updated correctly
		Assert.assertEquals("NEW HP", mDispHP.getText());
	}
	
	public void testTextScale() throws Throwable {
		mDispHP.setScale(100);
		
		// test to see that the scale of the text is set correctly
		Assert.assertEquals(100, mDispHP.getScale());
	}
}

