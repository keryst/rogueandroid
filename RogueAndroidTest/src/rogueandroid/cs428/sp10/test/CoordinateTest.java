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

import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.ScreenPixelCoordinate;
import rogueandroid.cs428.sp10.RogueAndroid;
import android.test.ActivityInstrumentationTestCase2;
import junit.framework.Assert;

public class CoordinateTest extends
		ActivityInstrumentationTestCase2<RogueAndroid> {

	public CoordinateTest(String name) {
		super("rogueandroid.cs428.sp10",RogueAndroid.class);
		setName(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCoordinateCreation() throws Throwable {
		DungeonTileCoordinate coord = new DungeonTileCoordinate(37, 89);
		// test for correct x and y of new coordinate
		Assert.assertEquals(coord.getX(), 37);
		Assert.assertEquals(coord.getY(), 89);
		
		// test for correct tile size
		DungeonTileCoordinate.setTileSize(24);
		Assert.assertEquals(DungeonTileCoordinate.getTileSize(), 24);
	}

	public void testCoordinateEquals() throws Throwable {
		DungeonTileCoordinate coord1 = new DungeonTileCoordinate(37, 89);
		DungeonTileCoordinate coord2 = new DungeonTileCoordinate(37, 89);
		String foo = new String("foo");
		DungeonPixelCoordinate coord3 = new DungeonPixelCoordinate(37, 89);
		// test for coordinate equals method
		Assert.assertEquals(coord1.getX(), 37);
		Assert.assertEquals(coord1.getY(), 89);
		Assert.assertEquals(coord2.getX(), 37);
		Assert.assertEquals(coord2.getY(), 89);
		Assert.assertEquals(coord3.getX(), 37);
		Assert.assertEquals(coord3.getY(), 89);
		Assert.assertTrue(coord1.equals(coord2));
		Assert.assertTrue(coord2.equals(coord1));
		Assert.assertFalse(coord1.equals(foo));
		Assert.assertFalse(coord1.equals(coord3));
	}

	public void testCoordinateConversionTileToPixel() throws Throwable {
		final DungeonTileCoordinate tile1 = new DungeonTileCoordinate(2, 3);
		final DungeonPixelCoordinate pixel1 = new DungeonPixelCoordinate(2 * 24, 3 * 24);
		// test for correct conversion from dungeon x and y coordinate to dungeon pixel coordinate 
		DungeonTileCoordinate.setTileSize(24);
		DungeonPixelCoordinate pixel2 = tile1.toDungeonPixel();
		Assert.assertTrue(pixel1.equals(pixel2));
	}

	public void testCoordinateConversionPixelToTile() throws Throwable {
		final DungeonTileCoordinate tile1 = new DungeonTileCoordinate(2, 3);
		final DungeonPixelCoordinate pixel1 = new DungeonPixelCoordinate(2 * 24, 3 * 24);
		// test for correct conversion from dungeon pixel coordinate to dungeon x and y coordinate
		DungeonTileCoordinate.setTileSize(24);
		DungeonTileCoordinate tile2 = pixel1.toDungeonTile();
		Assert.assertTrue(tile1.equals(tile2));
	}

	public void testCoordinateConversionScreenToDungeon() throws Throwable {
		final DungeonPixelCoordinate pixel1 = new DungeonPixelCoordinate(100, 200);
		final ScreenPixelCoordinate pixel2 = new ScreenPixelCoordinate(113, 227);
		
		// test for correct conversion from dungeon pixel coordinate  to 
		// screen pixel coordinate 
		DungeonTileCoordinate.setScreen(13, 27);
		ScreenPixelCoordinate pixel3 = pixel1.toScreen();
		Assert.assertTrue(pixel2.equals(pixel3));
		
		// test for correct conversion from dungeon pixel coordinate  to 
		// screen pixel coordinate after scroll
		DungeonTileCoordinate.scrollScreen(3, 2);
		ScreenPixelCoordinate pixel4 = pixel1.toScreen();
		final ScreenPixelCoordinate pixel5 = new ScreenPixelCoordinate(116, 229);
		Assert.assertTrue(pixel5.equals(pixel4));
	}

	public void testCoordinateConversionDungeonToScreen() throws Throwable {
		final DungeonPixelCoordinate pixel1 = new DungeonPixelCoordinate(100, 200);
		final ScreenPixelCoordinate pixel2 = new ScreenPixelCoordinate(113, 227);
		
		// test for correct conversion from dungeon pixel coordinate to 
		// screen pixel coordinate after scroll
		DungeonTileCoordinate.setScreen(13, 27);
		DungeonPixelCoordinate pixel3 = pixel2.toDungeon();
		Assert.assertTrue(pixel1.equals(pixel3));
	}

	public void testDungeonDistance() throws Throwable {
		final DungeonTileCoordinate tile1 = new DungeonTileCoordinate(2, 2);
		final DungeonTileCoordinate tile2 = new DungeonTileCoordinate(2, 3);
		final DungeonTileCoordinate tile3 = new DungeonTileCoordinate(3, 3);
		
		// test for correct distance between two tiles
		Assert.assertEquals(1, tile1.distanceTo(tile2));
		Assert.assertEquals(1, tile2.distanceTo(tile3));
		Assert.assertEquals(2, tile1.distanceTo(tile3));

		Assert.assertEquals(1, tile2.distanceTo(tile1));
		Assert.assertEquals(1, tile3.distanceTo(tile2));
		Assert.assertEquals(2, tile3.distanceTo(tile1));

		Assert.assertEquals(0, tile3.distanceTo(tile3));
	}

}
