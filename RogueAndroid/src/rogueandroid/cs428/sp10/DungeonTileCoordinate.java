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
 * Coordinate representing an [X, Y] tile location relative to the fixed dungeon.
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
public final class DungeonTileCoordinate extends Coordinate {

	/**
	 * Create a new DungeonTileCoordinate
	 * @param newX
	 * @param newY
	 */
	public DungeonTileCoordinate(int newX, int newY) {
		super(newX, newY);
	}
	
	public DungeonTileCoordinate(DungeonTileCoordinate dtc) {
		super(dtc.getX(), dtc.getY());
	}

	/**
	 * Convert (create a new) DungeonPixelCoordinate from the dungeon tile's coordinate.
	 * @return DungeonPixelCoordinate
	 */
	public DungeonPixelCoordinate toDungeonPixel() {
		DungeonPixelCoordinate coord = new DungeonPixelCoordinate(getX() * getTileSize(), getY() * getTileSize());
		return coord;
	}
	
	/**
	 * Compute the distance from one tile to another along the shortest walking path.
	 * @param tile DungeonTileCoordinate
	 * @return int distance from one tile to another
	 */
	public int distanceTo(DungeonTileCoordinate tile) {
		return Math.abs(this.getX() - tile.getX()) + Math.abs(this.getY() - tile.getY());
	}
	
	/**
	 * Compute the center pixel coordinate for the specified tile.
	 * @return DungeonPixelCoordinate
	 */
	public DungeonPixelCoordinate tileCenterPixel() {
		// TODO: Make this real.
		DungeonPixelCoordinate coord = new DungeonPixelCoordinate(getX() * getTileSize(), getY() * getTileSize());
		return coord;
	}
	
}
