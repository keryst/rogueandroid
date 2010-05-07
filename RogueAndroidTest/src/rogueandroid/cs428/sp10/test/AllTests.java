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

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for rogueandroid.cs428.sp10.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(GameTest.class);
		suite.addTestSuite(CharacterTest.class);
		suite.addTestSuite(MonsterTest.class);
		suite.addTestSuite(CoordinateTest.class);
		suite.addTestSuite(XMLTest.class);
		suite.addTestSuite(DungeonTest.class);
		suite.addTestSuite(DisplayableEntityTest.class);
		suite.addTestSuite(TravelTest.class);
		suite.addTestSuite(TileEngineBackgroundTest.class);
		suite.addTestSuite(DisplaybleTextTest.class);
		suite.addTestSuite(DirectionTest.class);
		suite.addTestSuite(InventoryTest.class);
		suite.addTestSuite(GameOverTest.class);
		suite.addTestSuite(ItemTest.class);
		suite.addTestSuite(AttackActionTest.class);
		suite.addTestSuite(TouchTest.class);
		suite.addTestSuite(AnimationTest.class);
		//$JUnit-END$
		return suite;
	}

}
