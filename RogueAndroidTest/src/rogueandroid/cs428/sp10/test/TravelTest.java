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
import rogueandroid.cs428.sp10.model.TravelAction;
import rogueandroid.cs428.sp10.view.DisplayMock;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

/**
 * @author david kristola
 * 
 */
public class TravelTest extends ActivityInstrumentationTestCase2<RogueAndroid> {

    private Character mCharacter;
    private DungeonTileCoordinate mCoord;
    private DisplayMock mDisplay;
    private Dungeon mDungeon;

    public static final int tileSize = 96;

    public TravelTest(String name) {
        super("rogueandroid.cs428.sp10", RogueAndroid.class);
        setName(name);
    }

    protected void setUp() throws Exception {
        super.setUp();

        mDisplay = new rogueandroid.cs428.sp10.view.DisplayMock();

        DungeonMock sampleDungeon = new DungeonMock();
        mDungeon = new Dungeon(sampleDungeon.tiles);

        mCoord = new DungeonTileCoordinate(2, 2);
        DungeonTileCoordinate.setTileSize(tileSize);
        mCharacter = new Character(mDungeon, mCoord);
        mCharacter.setLocation(mCoord, mCoord.toDungeonPixel());
        mCharacter.setDisplayable(mDisplay);
        mDisplay.setXY(mCoord.toDungeonPixel());
    }

    protected void tearDown() throws Exception {
        super.tearDown();

        mDisplay = null;
        mCharacter = null;
        mCoord = null;
    }

    public void testTravelCreation() throws Throwable {
        Debug.print("Entering testTravelCreation");
        DungeonTileCoordinate destination1 = new DungeonTileCoordinate(2, 2);
        TravelAction aTravel = new TravelAction(mCharacter, destination1, mDungeon, null);

        Debug.print("Check actionCompleted");
        Assert.assertTrue(aTravel.actionCompleted());
    }

    public void testTravelMoving() throws Throwable {
        Debug.print("Entering testTravelMoving");
        DungeonTileCoordinate startLoc = new DungeonTileCoordinate(2, 2);
        DungeonTileCoordinate endLoc = new DungeonTileCoordinate(2, 9);

        mCharacter.setLocation(startLoc, startLoc.toDungeonPixel());

        // test for character location
        DungeonTileCoordinate curLoc = mCharacter.getTileLocation();
        Assert.assertTrue(curLoc.equals(startLoc));

        TravelAction aTravel = new TravelAction(mCharacter, endLoc, mDungeon, null);

        // Make sure the destination is correct
        DungeonTileCoordinate curDest = aTravel.getDestination();
        Assert.assertTrue(curDest.equals(endLoc));

        // Extra information is set when the destination is set, check it.
        DungeonPixelCoordinate pixelStep = aTravel.getPixelStep();
        Assert.assertTrue(aTravel.traveling());

        DungeonPixelCoordinate currentPixel;
        currentPixel = aTravel.getPixelDestination();
        Debug.print("destination pixel = " + currentPixel.toString());

        // Let the game run for a while.
        for (int i = 0; i <= 100; i++) {
            aTravel.onGameLoop();
            curLoc = mCharacter.getTileLocation();
            Debug.print("curLoc = " + curLoc.toString());
            pixelStep = aTravel.getPixelStep();
            Debug.print("pixelStep = " + pixelStep.toString());
            currentPixel = aTravel.getPixelLocation();
            Debug.print("currentPixel = " + currentPixel.toString());
            Assert.assertEquals(curLoc.getX(), 2);
        }

        currentPixel = aTravel.getPixelDestination();
        Debug.print("destination pixel = " + currentPixel.toString());

       // test for correct location of character after travel
        curLoc = mCharacter.getTileLocation();
        curDest = aTravel.getDestination();
        Assert.assertTrue(curLoc.equals(endLoc));
        Assert.assertTrue(curDest.equals(endLoc));
        Assert.assertFalse(aTravel.traveling());

        DungeonTileCoordinate endLoc2 = new DungeonTileCoordinate(13, 9);

        mDungeon.setCharacterLocation(curLoc);
        aTravel = new TravelAction(mCharacter, endLoc2, mDungeon, null);

        curLoc = mCharacter.getTileLocation();

        // Debug.print("start Y curLoc = "+curLoc.toString());

        curDest = aTravel.getDestination();
        // Debug.print("start Y  curDest = "+endLoc2.toString());
        // test for correct location of character after travel
        Assert.assertTrue(curLoc.equals(endLoc));
        Assert.assertTrue(curDest.equals(endLoc2));
        Assert.assertTrue(aTravel.traveling());
        for (int i = 0; i <= 130; i++) {
            aTravel.onGameLoop();
            curLoc = mCharacter.getTileLocation();
            Debug.print("curLoc = " + curLoc.toString());
            pixelStep = aTravel.getPixelStep();
            Debug.print("pixelStep = " + pixelStep.toString());
            currentPixel = aTravel.getPixelLocation();
            Debug.print("currentPixel = " + currentPixel.toString());
            Assert.assertEquals(9, curLoc.getY());
        }
        // test for correct location of character after travel
        curLoc = mCharacter.getTileLocation();
        curDest = aTravel.getDestination();
        Assert.assertTrue(curLoc.equals(endLoc2));
        Assert.assertTrue(curDest.equals(endLoc2));
        Assert.assertFalse(aTravel.traveling());
    }

    public void testTravelInterruption() throws Throwable {
        Debug.print("Entering testTravelInterruption");
        DungeonTileCoordinate startLoc = new DungeonTileCoordinate(2, 2);
        DungeonTileCoordinate endLoc = new DungeonTileCoordinate(2, 9);

        mCharacter.setLocation(startLoc, startLoc.toDungeonPixel());

        TravelAction aTravel = new TravelAction(mCharacter, endLoc, mDungeon, null);
        mCharacter.queueAction(aTravel);

        DungeonTileCoordinate curLoc = mCharacter.getTileLocation();

        // Run the game loop until the travel action is in place.
        while (mCharacter.getAction() != aTravel) {
            mCharacter.onGameLoop();
        }

        int countReplaceable = 0;
        int countNotReplaceable = 0;
        int countLoops = 0;

        // Run the game until the character reaches [2, 5].
        while (curLoc.getY() < 5) {
            if (aTravel.actionReplaceable()) {
                countReplaceable += 1;
            } else {
                countNotReplaceable += 1;
            }
            countLoops += 1;
            mCharacter.onGameLoop();
            curLoc = mCharacter.getTileLocation();
            Debug.print("curLoc = " + curLoc.toString());
            Assert.assertEquals(curLoc.getX(), 2);
        }

        Assert.assertTrue(curLoc.equals(new DungeonTileCoordinate(2, 5)));
        Assert.assertEquals(countLoops, (countReplaceable + countNotReplaceable));
        Assert.assertTrue(countReplaceable > 0);
        Assert.assertTrue(countNotReplaceable > 0);

        Assert.assertTrue(aTravel.traveling());

        // Make sure we are still going where originally directed.
        DungeonTileCoordinate curDest = aTravel.getDestination();
        Assert.assertTrue(curDest.equals(endLoc));

        // Now queue up a travel to a new location and make sure we start going
        // there.  We are at [2, 5].
        DungeonTileCoordinate newLoc = new DungeonTileCoordinate(2, 4);
        TravelAction aNewTravel = new TravelAction(mCharacter, newLoc, mDungeon, null);
        mCharacter.queueAction(aNewTravel);

        DungeonTileCoordinate badLoc = new DungeonTileCoordinate(2, 6);

        while (!curLoc.equals(newLoc)) {
            countLoops += 1;
            mCharacter.onGameLoop();
            curLoc = mCharacter.getTileLocation();
            Debug.print("curLoc = " + curLoc.toString());
            // Make sure we stop heading toward [2, 9].
            Assert.assertFalse(curLoc.equals(badLoc));
            // Make sure we don't get stuck in the loop forever.
            Assert.assertTrue(countLoops < 1000);
        }
    }
}
