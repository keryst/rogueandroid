/**
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
 *
 */

package rogueandroid.cs428.sp10;

/**
 * 
 * This class defines 4 directions as numbers. It has a single method that
 * determines which direction the character should face and move when the
 * character is in movement.
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
public enum Direction {
    // four directions numbered from 0 to 3
    POS_X, NEG_X, POS_Y, NEG_Y;

    /**
     * This function returns the direction from two adjacent dungeon tile
     * coordinates.
     * 
     * @param fromTile
     *            DungeonTileCoordinate
     * @param toTile
     *            DungeonTileCoordinate
     * @return Direction from fromTile to toTile
     * @throws Exception
     *             when the tiles are not adjacent!
     */
    static public Direction directionBetween(DungeonTileCoordinate fromTile,
                                    DungeonTileCoordinate toTile) throws Exception {
        if (fromTile.distanceTo(toTile) != 1) {
            throw new Exception("ERROR: tiles are not adjacent!");
        }
        // if fromTile = (5, 5) and toTile = (6, 5), then
        if (fromTile.getX() < toTile.getX()) return POS_X;
        if (fromTile.getY() < toTile.getY()) return POS_Y;
        if (fromTile.getX() > toTile.getX()) return NEG_X;
        return NEG_Y;
    }
}
