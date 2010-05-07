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

import rogueandroid.cs428.sp10.model.Item;

/**
 * ItemMaker is used to generate random items to be placed in random
 * locations throughout the dungeon
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
public class ItemMaker {
	private ItemMaker() {
	}
	
	/**
	 * generates random items
	 * 
	 * @param startLocation
	 * @param name
	 * 
	 * @return Item
	 */
	public static Item make(DungeonTileCoordinate startLocation, String name) {
		Item i = null;
		if (name == null || name == "") 
			i = Game.config().getRandomItem();
		else
			i = Game.config().getItem(name);
		// set uses to 1 by default
		i.setUses(1);
		// set the start location
		i.setLocation(startLocation);
		return (i);
	}
}