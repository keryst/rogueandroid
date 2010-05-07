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
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.RogueAndroid;
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.view.DisplayableEntity;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This test suite exercises the DisplayableEntity class.
 * 
 * @author David Kristola
 * @author John Svitek
 * @author Drew Glass
 * @author Josh Glovinsky
 * @author Michael Lai
 * @author Henry (Chip) Millson
 * @author Hyun Soon Kim
 * 
 */
public class DisplayableEntityTest extends ActivityInstrumentationTestCase2<RogueAndroid> {

    private Rokon mRokon;
    private TextureAtlas mAtlas;
    private DisplayableEntity mDisplay;

    private final static int tileSize = 96;

    /**
     * Create a test case with a given name.
     * @param name String
     */
    public DisplayableEntityTest(String name) {
        super("rogueandroid.cs428.sp10", RogueAndroid.class);
        setName(name);
    }

    /**
     * Set up the test case.
     */
    protected void setUp() throws Exception {
        super.setUp();
        mRokon = Rokon.createEngine(this.getActivity(), 854, 480);
        mAtlas = new TextureAtlas(1024, 2048);

        mDisplay = new DisplayableEntity(null, mAtlas, mRokon, tileSize,
                                        "graphics/sprites/blueknight.png");
    }

    /**
     * Tear down the test case.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test the creation of a DisplayableEntity.
     * @throws Throwable
     */
    public void testDisplayableEntityCreation() throws Throwable {
        assertTrue(mDisplay.getTexture() instanceof Texture);
        assertTrue(mDisplay.getSprite() instanceof Sprite);
    }

    /**
     * Test the setting of the dungeon pixel location.
     * @throws Throwable
     */
    public void testDisplayableEntityLocation() throws Throwable {
        DungeonPixelCoordinate pixelLocation = new DungeonPixelCoordinate(5, 5);
        DungeonPixelCoordinate.setScreen(0, 0);
        mDisplay.setXY(pixelLocation);
        assertTrue(mDisplay.getSprite().getScreenX() == 5);
        assertTrue(mDisplay.getSprite().getScreenY() == 5);
    }

    /**
     * Tests to see if the screen pixel coordinate is scrolled properly.
     * @throws Throwable
     */
    public void testDisplayableEntityScrolledLocation() throws Throwable {
        DungeonPixelCoordinate pixelLocation = new DungeonPixelCoordinate(5, 5);
        DungeonPixelCoordinate.setScreen(10, 100);
        mDisplay.setXY(pixelLocation);
        assertTrue(mDisplay.getSprite().getScreenX() == 15);
        assertTrue(mDisplay.getSprite().getScreenY() == 105);
        DungeonPixelCoordinate.setScreen(0, 0);
    }

    /**
     * Test the death of a DisplayableEntity.
     * @throws Throwable
     */
    public void testDisplayableEntityDeath() throws Throwable {
        mDisplay.noteDeath();
        assertTrue(mDisplay.getSprite().isDead());
    }

    /**
     * Test the travel animation of a DisplayableEntity.
     * @throws Throwable
     */
    public void testDisplayableEntityTravelAnimation() throws Throwable {
        mDisplay.startTraveling(Direction.POS_X);
        assertTrue(mDisplay.getSprite().isAnimating());
    }

}
