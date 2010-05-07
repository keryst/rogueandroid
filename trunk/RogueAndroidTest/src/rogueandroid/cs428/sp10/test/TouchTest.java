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

import rogueandroid.cs428.sp10.BoundingBox;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.Game;
import rogueandroid.cs428.sp10.controller.Touch;
import rogueandroid.cs428.sp10.model.AttackAction;
import rogueandroid.cs428.sp10.model.Character;
import rogueandroid.cs428.sp10.model.Dungeon;
import rogueandroid.cs428.sp10.model.DungeonMock;
import rogueandroid.cs428.sp10.model.Monster;
import rogueandroid.cs428.sp10.model.TravelAction;
import rogueandroid.cs428.sp10.view.DisplayMock;
import rogueandroid.cs428.sp10.view.TileEngine;
import rogueandroid.cs428.sp10.view.View;

import android.test.ActivityInstrumentationTestCase2;

/**
 * @author david kristola
 * 
 */
public class TouchTest extends ActivityInstrumentationTestCase2<Game> {

	public TouchTest(String name) {
		super("rogueandroid.cs428.sp10", Game.class);
		setName(name);
	}

	private Character mCharacter;
	private Monster mMonster1; // Next to mCharacter
	private Monster mMonster2; // Far away
	private Dungeon mDungeon;
	private View mView;
	private TileEngine mTileEngine;
	private DungeonTileCoordinate mCoord;
        private DisplayMock mCharacterDisplay;

	private class TileEngineMock implements TileEngine {
		public int mX = 0;
		public int mY = 0;
		public void setTexture(Integer id, String assetPath) {}
		public void setTile(int i, int j, Integer textureId) {}
		public void setTileBlocking(int i, int j, boolean blocked) {}
		public int getScreenX(int tileICoordinate) {return 0;}
		public int getScreenY(int tileJCoordinate) {return 0;}
		public int getNumTilesI() {return 0;}
		public int getNumTilesJ() {return 0;}
		public void setBackground() {}
		public void scroll(int x, int y) {
			Debug.print("TileEngineMock.scroll(" + ((Integer)x).toString() + ", " + ((Integer)y).toString() + ")");
			mX += x;
			mY += y;
		}
		public BoundingBox getBoundingBox() {return new BoundingBox(0, 0, 1000, 1000);}
	}

	public static final int tileSize = 96;

	protected void setUp() throws Exception {
		super.setUp();
		DungeonMock mock = new DungeonMock();
		mDungeon = new Dungeon(mock.tiles);
		mTileEngine = new TileEngineMock();
		mView = new View(mDungeon, mTileEngine, 500, 500);
		mCoord = new DungeonTileCoordinate(2, 2);
		DungeonTileCoordinate.setTileSize(tileSize);
                mCharacterDisplay = new DisplayMock();
		mCharacter = new Character(mDungeon, mCoord);
		mCharacter.setSpeed(10);
                mCharacter.setDisplayable(mCharacterDisplay);
		mMonster1 = new Monster(mDungeon, new DungeonTileCoordinate(2, 3));
		mMonster2 = new Monster(mDungeon, new DungeonTileCoordinate(9, 1));
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		mDungeon = null;
		mView = null;
		mCoord = null;
		mCharacter = null;
		mMonster1 = null;
		mMonster2 = null;
	}

	public void testTapOnSelf() throws Exception {
		int workingX = mCoord.toDungeonPixel().toScreen().getX();
		int workingY = mCoord.toDungeonPixel().toScreen().getY();
		Debug.print("testTapOnSelf at x = " + ((Integer)workingX).toString() + ", y = " + ((Integer)workingY).toString());
		Touch aTouch = new Touch(mView, mDungeon, mCharacter, workingX, workingY);
		aTouch.onTouchUp(workingX, workingY);
		// test to see that no action is set on character
		// when user touches character
		assertTrue(mCharacter.getAction() == null);
	}

	public void testTapOnSpace() throws Exception {
		DungeonTileCoordinate endLoc = new DungeonTileCoordinate(5, 2);
		int workingX = endLoc.toDungeonPixel().toScreen().getX();
		int workingY = endLoc.toDungeonPixel().toScreen().getY();
		Debug.print("testTapOnSpace at x = " + ((Integer)workingX).toString() + ", y = " + ((Integer)workingY).toString());
		Touch aTouch = new Touch(mView, mDungeon, mCharacter, workingX, workingY);
		aTouch.onTouchUp(workingX, workingY);

                mCharacter.onGameLoop(); // turn the crank
         
        // test to see that travel action is set on character
        // when user touches an empty tile        
		assertTrue(mCharacter.getAction() instanceof TravelAction);
		TravelAction aTravel = (TravelAction)mCharacter.getAction();

		DungeonTileCoordinate curDest = aTravel.getDestination();
		assertTrue(curDest.equals(endLoc));
	}

	public void testTapOnMonster1() throws Exception {
		DungeonTileCoordinate endLoc = mMonster1.getTileLocation();
		int workingX = endLoc.toDungeonPixel().toScreen().getX();
		int workingY = endLoc.toDungeonPixel().toScreen().getY();
		Debug.print("testTapOnMonster1 at x = " + ((Integer)workingX).toString() + ", y = " + ((Integer)workingY).toString());
		Touch aTouch = new Touch(mView, mDungeon, mCharacter, workingX, workingY);
		aTouch.onTouchUp(workingX, workingY);

		mCharacter.onGameLoop(); // turn the crank
		
		// test to see that attack action is set on character
		// when user touches a monster
		assertTrue(mCharacter.getAction() instanceof AttackAction);
		AttackAction anAttack = (AttackAction)mCharacter.getAction();

		assertTrue(anAttack.getmAttacker().equals(mCharacter));
		assertTrue(anAttack.getmTarget().equals(mMonster1));
	}

	public void testTapOnMonster2() throws Exception {
		DungeonTileCoordinate endLoc = mMonster2.getTileLocation();
		int workingX = endLoc.toDungeonPixel().toScreen().getX();
		int workingY = endLoc.toDungeonPixel().toScreen().getY();
		Debug.print("testTapOnMonster2 at x = " + ((Integer)workingX).toString() + ", y = " + ((Integer)workingY).toString());
		Touch aTouch = new Touch(mView, mDungeon, mCharacter, workingX, workingY);
		aTouch.onTouchUp(workingX, workingY);

                mCharacter.onGameLoop(); // turn the crank
        
        // test to see that travel action is set on character
        // when user touches a monster far away
		assertTrue(mCharacter.getAction() instanceof TravelAction);
		TravelAction aTravel = (TravelAction)mCharacter.getAction();

		DungeonTileCoordinate curDest = aTravel.getDestination();
		assertTrue(curDest.equals(endLoc));
	}

	public void testSwipe() throws Exception {
		// Scrolling is clipped in the low number corner, so start further away and move down.
		int workingX = 200;
		int workingY = 200;
		Debug.print("testSwipe at x = " + ((Integer)workingX).toString() + ", y = " + ((Integer)workingY).toString());
		Touch aTouch = new Touch(mView, mDungeon, mCharacter, workingX, workingY);
		workingX -= 20;
		workingY -= 20;
		aTouch.onTouch(workingX, workingY);
		for (int i=0; i<20; i++) {
			workingX -= 7;
			workingY -= 5;
			aTouch.onTouch(workingX, workingY);
		}
		workingX -= 1;
		workingY -= 3;
		aTouch.onTouchUp(workingX, workingY);
		
		// test to see that the tile engine is scrolled
		// when user swipes the screen
		assertEquals(workingX-200, ((TileEngineMock)mTileEngine).mX);
		assertEquals(workingY-200, ((TileEngineMock)mTileEngine).mY);
	}
}
