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

import com.stickycoding.Rokon.Rokon;

import rogueandroid.cs428.sp10.RogueAndroid;
import rogueandroid.cs428.sp10.model.DungeonMock;
import rogueandroid.cs428.sp10.view.TileEngineBackground;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import junit.framework.Assert;

//public class TileEngineBackgroundTest extends TestCase {
public class TileEngineBackgroundTest extends ActivityInstrumentationTestCase2<RogueAndroid> {

    public TileEngineBackground tileEngineBackground;
    public Rokon rokon;

    public int expectedNumTilesX = 27;
    public int expectedNumTilesY = 15;
    public int expectedTileSize = 96;

    protected void setUp() throws Exception {
        super.setUp();

        rokon = Rokon.createEngine(this.getActivity(), 854, 480);
        DungeonMock sampleDungeon = new DungeonMock();
        ArrayList<String> mTextureList = new ArrayList<String>();
        String newTexture = new String("location");
        mTextureList.add(newTexture);

        tileEngineBackground = new TileEngineBackground(rokon, expectedTileSize, mTextureList,
                                        expectedNumTilesX, expectedNumTilesY);

        int numX = sampleDungeon.tiles.length;
        int numY = sampleDungeon.tiles[0].length;
        for (int i = 0; i < numX; i++) {
            for (int j = 0; j < numY; j++) {
                tileEngineBackground.setTile(i, j, sampleDungeon.tiles[i][j]);
                tileEngineBackground.setTileBlocking(i, j, sampleDungeon.blocked[i][j]);
            }
        }
    }

    public TileEngineBackgroundTest(String name) {
        super("rogueandroid.cs428.sp10", RogueAndroid.class);
        setName(name);
    }

    public void testGetNumTilesI() {
    	// test that the number of tiles is correct
        Assert.assertEquals(expectedNumTilesX, tileEngineBackground.getNumTilesI());
    }

    public void testGetNumTilesJ() {
    	// test that the number of tiles is correct
        Assert.assertEquals(expectedNumTilesY, tileEngineBackground.getNumTilesJ());
    }

    public void testGetScreenX() {
    	// test that the screen size is correct
        Assert.assertEquals(-5, tileEngineBackground.getScreenX(0));
        Assert.assertEquals(expectedTileSize - 5, tileEngineBackground.getScreenX(1));
    }

    public void testGetScreenY() {
    	// test that the screen size is correct
        Assert.assertEquals(0, tileEngineBackground.getScreenY(0));
        Assert.assertEquals(expectedTileSize, tileEngineBackground.getScreenY(1));
    }

    public void testTileEngineBackground() {
    	// test that the the tile engine background was created
        Assert.assertNotNull(tileEngineBackground);
        Assert.assertEquals(expectedNumTilesX, tileEngineBackground.getNumTilesI());
        Assert.assertEquals(expectedNumTilesY, tileEngineBackground.getNumTilesJ());
    }
}
