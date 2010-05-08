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

package rogueandroid.cs428.sp10;

import rogueandroid.cs428.sp10.model.Dungeon;
import rogueandroid.cs428.sp10.model.Monster;

/**
 * MonsterMaker generates random monsters in places them on random
 * tiles on the dungeon
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
public class MonsterMaker {
	private MonsterMaker() {
	}
	
	/**
	 * Generates random monsters in places them on random
	 * tiles on the dungeon
	 * 
	 * @param dungeon
	 * @param startLocation
	 * @param name
	 * 
	 * @return Monster
	 */
	public static Monster make(Dungeon dungeon, DungeonTileCoordinate startLocation, String name) {
		Monster m = null;
		if (name == null || name == "") 
			m = new Monster(Game.config().getRandomMonster());
		else
			m = new Monster(Game.config().getMonster(name));
		// set the dungeon and start location
		m.setDungeon(dungeon);
		m.setLocation(startLocation);
		m.registerDungeon();
		return (m);
	}
}
