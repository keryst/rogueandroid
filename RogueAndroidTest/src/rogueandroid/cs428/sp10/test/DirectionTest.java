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

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.RogueAndroid;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.Assert;

/**
 * @author david kristola
 *
 */
public class DirectionTest extends
		ActivityInstrumentationTestCase2<RogueAndroid> {

	public DirectionTest(String name) {
		super("rogueandroid.cs428.sp10",RogueAndroid.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPosX() throws Throwable {
		DungeonTileCoordinate startCoord = new DungeonTileCoordinate(37, 89);
		DungeonTileCoordinate endCoord = new DungeonTileCoordinate(38, 89);
		
		Direction facing = Direction.directionBetween(startCoord, endCoord);
		// test to see that facing is the positive x direction
		Assert.assertEquals(facing, Direction.POS_X);
	}

	public void testNegX() throws Throwable {
		DungeonTileCoordinate startCoord = new DungeonTileCoordinate(37, 89);
		DungeonTileCoordinate endCoord = new DungeonTileCoordinate(36, 89);
		
		Direction facing = Direction.directionBetween(startCoord, endCoord);
		// test to see that facing is the negative x direction
		Assert.assertEquals(facing, Direction.NEG_X);
	}

	public void testPosY() throws Throwable {
		DungeonTileCoordinate startCoord = new DungeonTileCoordinate(37, 89);
		DungeonTileCoordinate endCoord = new DungeonTileCoordinate(37, 90);
		
		Direction facing = Direction.directionBetween(startCoord, endCoord);
		// test to see that facing is the positive y direction
		Assert.assertEquals(facing, Direction.POS_Y);
	}

	public void testNegY() throws Throwable {
		DungeonTileCoordinate startCoord = new DungeonTileCoordinate(37, 89);
		DungeonTileCoordinate endCoord = new DungeonTileCoordinate(37, 88);
		
		Direction facing = Direction.directionBetween(startCoord, endCoord);
		// test to see that facing is the negative x direction
		Assert.assertEquals(facing, Direction.NEG_Y);
	}

	public void testException() throws Throwable {
		DungeonTileCoordinate startCoord = new DungeonTileCoordinate(37, 89);
		DungeonTileCoordinate endCoord = new DungeonTileCoordinate(37, 99);
		// test to see that an exception is thrown when
		// two tiles that are not next to each other are tested for 
		// for facing
		Direction facing = Direction.POS_X;
		try {
			facing = Direction.directionBetween(startCoord, endCoord);
			Assert.fail("Missing exception!");
		} catch (Exception e) {
			// The exception was expected!
			Assert.assertEquals(facing, Direction.POS_X);
		}
	}
}
