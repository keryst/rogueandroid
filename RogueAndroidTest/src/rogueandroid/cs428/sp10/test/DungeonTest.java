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

import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.RogueAndroid;
import rogueandroid.cs428.sp10.model.Character;
import rogueandroid.cs428.sp10.model.Dungeon;
import rogueandroid.cs428.sp10.model.DungeonMock;
import rogueandroid.cs428.sp10.view.DisplayMock;

import android.test.ActivityInstrumentationTestCase2;
import junit.framework.Assert;

public class DungeonTest extends ActivityInstrumentationTestCase2<RogueAndroid>  {

        public Character character;
	public DisplayMock mDisplay;
	private Dungeon dungeon;

	
	public static int tileSize = 96;
	
	public DungeonTest() {
		super("rogueandroid.cs428.sp10",RogueAndroid.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		DungeonMock sampleDungeon = new DungeonMock();
		dungeon = new Dungeon(sampleDungeon.tiles);
		
		mDisplay = new rogueandroid.cs428.sp10.view.DisplayMock();
		character = new Character(dungeon, new DungeonTileCoordinate(2,2));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
   
	public void testCharacterLocation() throws Throwable {
		DungeonTileCoordinate startLoc = new DungeonTileCoordinate(5, 9);
		character.setLocation(startLoc,startLoc.toDungeonPixel());
		//test for character location
		DungeonTileCoordinate curLoc = dungeon.getCharacterLocation();
		Assert.assertTrue(curLoc.equals(startLoc));
		
		//test for location in entity matrix
		Assert.assertTrue(dungeon.entityLocatedOnTile(startLoc)==1);
		
		DungeonTileCoordinate emptyLoc = new DungeonTileCoordinate(2, 2);
		Debug.print("emptyLoc = "+dungeon.entityLocatedOnTile(emptyLoc));
		Assert.assertTrue(dungeon.entityLocatedOnTile(emptyLoc)!=1);
    }
	
	public void testIsBlocked() throws Throwable {
		DungeonTileCoordinate blockedLocation = new DungeonTileCoordinate(0, 0);
		DungeonTileCoordinate nonBlockedLocation = new DungeonTileCoordinate(1, 1);

		//test for isBlocked
		Assert.assertEquals(true,dungeon.isBlocked(blockedLocation));
		Assert.assertEquals(false,dungeon.isBlocked(nonBlockedLocation));
	}
  
}
