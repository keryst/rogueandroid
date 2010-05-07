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

public class GameOverTest extends ActivityInstrumentationTestCase2<Game>  {

	public GameOverTest(String name) {
		super("rogueandroid.cs428.sp10",Game.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCharacterDeath() throws Throwable {

		Game game = getActivity();
		game.onLoad();
		game.character.setCurrentHitPoints(0);
		
		Assert.assertTrue(game.character.isDead());
		
		
		game.onGameLoop();
		
		//test to see if game mode is lose
		Assert.assertEquals(2, game.getMode());
    }
	
	public void testBossDeath() throws Throwable {

		Game game = getActivity();
		game.onLoad();
		game.mBoss.setCurrentHitPoints(0);
		
		Assert.assertTrue(game.mBoss.isDead());
		
		
		game.onGameLoop();
		
		//test to see if game mode is win
		Assert.assertEquals(3, game.getMode());
    }

}

