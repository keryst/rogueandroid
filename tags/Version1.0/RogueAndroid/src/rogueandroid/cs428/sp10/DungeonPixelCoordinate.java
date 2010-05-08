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

/**
 * Coordinate representing an [X, Y] pixel location relative to the fixed dungeon.
 * @author davidkristola
 * @author John Svitek
 * @author Drew Glass
 * @author Josh Glovinsky
 * @author Michael Lai
 * @author Henry (Chip) Millson
 * @author Hyun Soon Kim
 *
 */
public final class DungeonPixelCoordinate extends Coordinate {

	/**
	 * Create a new DungeonPixelCoordinate.
	 * 
	 * @param newX
	 * @param newY
	 */
	public DungeonPixelCoordinate(int newX, int newY) {
		super(newX, newY);
		// TODO Auto-generated constructor stub
	}
	
	public DungeonPixelCoordinate(DungeonPixelCoordinate dpc) {
		super(dpc.getX(), dpc.getY());
	}

	/**
	 * Convert from a fixed pixel coordinate to a fixed dungeon coordinate.
	 * 
	 * @param none
	 * @return DungeonTileCoordinate 
	 */
	public DungeonTileCoordinate toDungeonTile() {
		DungeonTileCoordinate coord = new DungeonTileCoordinate(getX() / getTileSize(), getY() / getTileSize());
		return coord;
	}
	
	/**
	 * Convert from a fixed pixel coordinate to a screen coordinate.
	 * 
	 * @param none
	 * @return ScreenPixelCoordinate
	 */
	public ScreenPixelCoordinate toScreen() {
		ScreenPixelCoordinate coord = new ScreenPixelCoordinate(getX() + getScreenX(), getY() + getScreenY());
		return coord;
	}

}
