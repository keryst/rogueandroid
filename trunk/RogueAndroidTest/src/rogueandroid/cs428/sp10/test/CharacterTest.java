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
import rogueandroid.cs428.sp10.RogueAndroid;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.model.Character;
import rogueandroid.cs428.sp10.model.Dungeon;
import rogueandroid.cs428.sp10.model.DungeonMock;
import rogueandroid.cs428.sp10.model.Entity;
import rogueandroid.cs428.sp10.model.SleepAction;
import rogueandroid.cs428.sp10.model.TravelAction;
import rogueandroid.cs428.sp10.view.DisplayMock;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.Assert;

public class CharacterTest extends ActivityInstrumentationTestCase2<RogueAndroid>  {

	private Character mCharacter;
	private DungeonTileCoordinate mCoord;
	private Dungeon mDungeon;
	private DisplayMock mDisplay;
	
	public static int tileSize = 96;
	
	public CharacterTest(String name) {
		super("rogueandroid.cs428.sp10",RogueAndroid.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		DungeonMock sampleDungeon = new DungeonMock();
		mDungeon = new Dungeon(sampleDungeon.tiles);
		
		mCoord = new DungeonTileCoordinate(2, 2);
		mCharacter = new Character(mDungeon, mCoord);
		mDisplay = new rogueandroid.cs428.sp10.view.DisplayMock();
		mCharacter.setDisplayable(mDisplay);
		DungeonTileCoordinate.setTileSize(tileSize);
		
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		mDungeon = null;
		mCoord = null;
		mCharacter = null;
	}
	
	public void testCharacterCreation() throws Throwable {
		// mCharacter has already been created, make sure it is right.
		// Check the tile coordinate
		DungeonTileCoordinate curLoc = mCharacter.getTileLocation();
		Assert.assertTrue(curLoc.equals(mCoord));
		// Check the pixel coordinate
		DungeonPixelCoordinate curPixel = mCharacter.getPixelLocation();
		Assert.assertTrue(curPixel.equals(mCoord.toDungeonPixel()));
		// Check that the Dungeon knows where the Character is
		Entity entity = mDungeon.getEntityAt(curLoc);
		Assert.assertTrue(entity == mCharacter);
    }
   
	public void testCharacterLocation() throws Throwable {
		DungeonTileCoordinate startLoc = new DungeonTileCoordinate(4, 6);
		mCharacter.setLocation(startLoc, startLoc.toDungeonPixel());
		//test for character location
		DungeonTileCoordinate curLoc = mCharacter.getTileLocation();
		Assert.assertTrue(curLoc.equals(startLoc));
    }

	public void testCharacterAction() throws Throwable {
		// test for the sleep action
		SleepAction snooze = new SleepAction(3);
		mCharacter.queueAction(snooze);
		Assert.assertFalse(snooze.actionCompleted());
		mCharacter.onGameLoop();
		Assert.assertFalse(snooze.actionCompleted());
		mCharacter.onGameLoop();
		Assert.assertFalse(snooze.actionCompleted());
		mCharacter.onGameLoop();
		Assert.assertTrue(snooze.actionCompleted());
    }

	public void testCharacterMovement() throws Throwable {
		DungeonTileCoordinate startLoc = new DungeonTileCoordinate(2, 2);
		DungeonTileCoordinate endLoc = new DungeonTileCoordinate(2, 9);

		mCharacter.setLocation(startLoc,startLoc.toDungeonPixel());

		//test for character location
		DungeonTileCoordinate curLoc = mCharacter.getTileLocation();
		Assert.assertTrue(curLoc.equals(startLoc));
		
		TravelAction aTravel = new TravelAction(mCharacter, endLoc, mDungeon, null);
		mCharacter.queueAction(aTravel);

		// Let the game run for a while.
		for (int i = 0; i <= 100; i++) {
			mCharacter.onGameLoop();
			curLoc = mCharacter.getTileLocation();
			Debug.print("curLoc = "+curLoc.toString());
			Assert.assertEquals(curLoc.getX(), 2);
		}

		// Make sure the character arrived at the travel destination.
		curLoc = mCharacter.getTileLocation();
		Assert.assertTrue(curLoc.equals(endLoc));
		
		DungeonTileCoordinate endLoc2 = new DungeonTileCoordinate(13, 9);

		aTravel = new TravelAction(mCharacter, endLoc2, mDungeon, null);
		mCharacter.queueAction(aTravel);

		curLoc = mCharacter.getTileLocation();
		Assert.assertTrue(curLoc.equals(endLoc));
		for (int i = 0; i <= 130; i++) {
			mCharacter.onGameLoop();
			curLoc = mCharacter.getTileLocation();
			Debug.print("curLoc = "+curLoc.toString());
			//Assert.assertEquals(9, curLoc.getY());
		}

		// Make sure the character arrived at the travel destination.
		curLoc = mCharacter.getTileLocation();
		Assert.assertTrue(curLoc.equals(endLoc2));
    }	
	

}
