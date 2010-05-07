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
import rogueandroid.cs428.sp10.Game;
import rogueandroid.cs428.sp10.model.AttackAction;
import rogueandroid.cs428.sp10.model.Character;
import rogueandroid.cs428.sp10.model.Dungeon;
import rogueandroid.cs428.sp10.model.DungeonMock;
import rogueandroid.cs428.sp10.model.Monster;
import rogueandroid.cs428.sp10.view.DisplayMock;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Random;

public class AttackActionTest extends ActivityInstrumentationTestCase2<Game> {

	private Character mCharacter;
	private Monster mMonster;
	private DisplayMock mCharacterDisplay;
	private DisplayMock mMonsterDisplay;
	private Dungeon mDungeon;

	private class MyRandom extends Random {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5165271163466456149L;

		public int nextInt(int n) {
			Debug.print("nextInt");
			assertEquals(n, 10);
			return 5;
		}
	}
	
	public AttackActionTest(String name) {
		super("rogueandroid.cs428.sp10", Game.class);
		setName(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		DungeonMock mock = new DungeonMock();
		mCharacterDisplay = new DisplayMock();
		mMonsterDisplay = new DisplayMock();
		mDungeon = new Dungeon(mock.tiles);
		mCharacter = new Character(mDungeon, new DungeonTileCoordinate(2, 2));
		mMonster = new Monster(mDungeon, new DungeonTileCoordinate(2, 3));
		mCharacter.setDisplayable(mCharacterDisplay);
		mMonster.setDisplayable(mMonsterDisplay);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		mDungeon = null;
		mCharacter = null;
		mMonster = null;
	}

	public void testSpeed() throws Exception {
		mCharacter.setSpeed(10);
		mMonster.setCurrentHitPoints(1);
		AttackAction anAttack = new AttackAction(mCharacter, mMonster, new MyRandom());
		
		for (int i = 0; i < 10; i++) {
			anAttack.onGameLoop();
			assertFalse(mMonster.isDead());
			assertFalse(anAttack.actionCompleted());
		}
		// test to the speed of the attack
		anAttack.onGameLoop();
		assertEquals(mMonster.getHitPoints(), 0);
		assertTrue(mMonster.isDead());
		anAttack.onGameLoop();
		assertTrue(anAttack.actionCompleted());
	}

	public void testBonuses() throws Exception {
		mCharacter.setSpeed(10);
		mCharacter.setAttackBonus(0);
		assertEquals(0, mCharacter.getAttackBonus());
		mMonster.setCurrentHitPoints(1);
		assertEquals(1, mMonster.getHitPoints());
		mMonster.setDefenseBonus(5);
		assertEquals(5, mMonster.getDefenseBonus());
		
		AttackAction anAttack = new AttackAction(mCharacter, mMonster, new MyRandom());
		
		for (int i = 0; i < 10; i++) {
			anAttack.onGameLoop();
			assertFalse(mMonster.isDead());
			assertFalse(anAttack.actionCompleted());
		}
		
		// Attack failed because defense bonus is 1 too high
		anAttack.onGameLoop();
		assertFalse(mMonster.isDead());
		assertFalse(anAttack.actionCompleted());
		assertEquals(mMonster.getHitPoints(), 1);

		mCharacter.setAttackBonus(1);
		
		for (int i = 0; i < 10; i++) {
			anAttack.onGameLoop();
			assertFalse(mMonster.isDead());
			assertFalse(anAttack.actionCompleted());
		}
		
		anAttack.onGameLoop();
		assertEquals(mMonster.getHitPoints(), 0);
		assertTrue(mMonster.isDead());
		anAttack.onGameLoop();
		assertTrue(anAttack.actionCompleted());
	}
}
