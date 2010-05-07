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

import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.RogueAndroid;
import rogueandroid.cs428.sp10.model.Dungeon;
import rogueandroid.cs428.sp10.model.DungeonMock;
import rogueandroid.cs428.sp10.model.Monster;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

public class MonsterTest extends ActivityInstrumentationTestCase2<RogueAndroid>  {

	private Monster monster;
	private Dungeon dungeon;

	public static int tileSize = 96;
	
	public MonsterTest() {
		super("rogueandroid.cs428.sp10",RogueAndroid.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		DungeonMock sampleDungeon = new DungeonMock();
		dungeon = new Dungeon(sampleDungeon.tiles);
		
		DungeonPixelCoordinate monsterStart = new DungeonPixelCoordinate(300, 300);
		monster = new Monster(dungeon, monsterStart.toDungeonTile());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
   
	public void testMonsterLocation() throws Throwable {
		DungeonTileCoordinate startLoc = new DungeonTileCoordinate(10, 11);
		
		monster.setLocation(startLoc, startLoc.toDungeonPixel());
		//test for monster location
		DungeonTileCoordinate curLoc = monster.getTileLocation();
		Assert.assertTrue(curLoc.equals(startLoc));
    }
  
}
