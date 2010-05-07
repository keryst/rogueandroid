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

import com.stickycoding.Rokon.Debug;
import rogueandroid.cs428.sp10.Game;

import android.test.ActivityInstrumentationTestCase2;

@SuppressWarnings("static-access")
public class XMLTest extends ActivityInstrumentationTestCase2<Game> {

	public XMLTest(String name) {
		super("rogueandroid.cs428.sp10",Game.class);
		setName(name);
		
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

        public void testTileSize() throws Throwable {
		Game game = getActivity();
		// test for correct tile size
		assertEquals(96, game.config().getTileSize());
	}
	
	public void testScreenXPixels() throws Throwable {
		Game game = getActivity();
		Debug.print("game call done");
		// test for correct number of horizontal pixels
		assertEquals(854, game.config().getScreenXPixels());
		//tearDown();
	}

	public void testScreenYPixels() throws Throwable {
		Game game = getActivity();
		// test for correct number of vertical pixels
		assertEquals(480, game.config().getScreenYPixels());
	}

}