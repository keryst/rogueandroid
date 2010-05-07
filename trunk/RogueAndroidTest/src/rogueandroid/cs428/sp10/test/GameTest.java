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

import rogueandroid.cs428.sp10.Game;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

/** Class to test Game class activity creation and screen touch */
public class GameTest extends ActivityInstrumentationTestCase2<Game>  {

	public GameTest() {
		super("rogueandroid.cs428.sp10",Game.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();    	
	}
	
	/** 
	 * Method to test creation of Game activity 
	 * 
	 */
	public void testActivityCreation() {
		final Game game1 = getActivity();
		
		// test to see that game activity was correctly created
		Assert.assertNotNull(game1);
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		
	}
}

