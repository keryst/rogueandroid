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
import rogueandroid.cs428.sp10.RogueAndroid;

import rogueandroid.cs428.sp10.model.Character;
import rogueandroid.cs428.sp10.model.Dungeon;
import rogueandroid.cs428.sp10.model.DungeonMock;
import rogueandroid.cs428.sp10.model.Item;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

public class ItemTest extends ActivityInstrumentationTestCase2<RogueAndroid> {
       
        protected void setUp() throws Exception {
                super.setUp();
        }

        @Override
        protected void tearDown() throws Exception {
                super.tearDown();
        }

        public ItemTest(String name) {
                super("rogueandroid.cs428.sp10",RogueAndroid.class);
                setName(name);
        }

        public void testItemUse() throws Throwable {
                Item item = new Item();
                                
                DungeonMock sampleDungeon = new DungeonMock();
                Dungeon dungeon = new Dungeon(sampleDungeon.tiles);
                
                DungeonTileCoordinate coord = new DungeonTileCoordinate(2, 2);
                Character character = new Character(dungeon, coord);                
                
                character.setAttackBonus(100);
                character.setDefenseBonus(100);
                character.increaseHitPoints(-50); // hit points are now 50 / 100.
                
                item.setHitPointBonus(100);
                item.setUses(5);
                
                item.use(character);
                // test to see that the potion raises hp
                Assert.assertEquals(100, character.getHitPoints());
                Assert.assertEquals(4, item.getUses());
                
                item.setAttackBonus(10);
                item.setDefenseBonus(5);
                
                item.use(character);
                
                // test to that the sword and helmet raise stats
                Assert.assertEquals(100, character.getHitPoints());
                Assert.assertEquals(110, character.getAttackBonus());
                Assert.assertEquals(105, character.getDefenseBonus());
                Assert.assertEquals(3, item.getUses());
                
                // use up all the uses
                item.use(character);
                item.use(character);
                item.use(character);
                
                // try to use again, should not have an effect
                item.use(character);

                Assert.assertEquals(100, character.getHitPoints());
                Assert.assertEquals(140, character.getAttackBonus());
                Assert.assertEquals(120, character.getDefenseBonus());
                Assert.assertEquals(0, item.getUses());
        }
}
