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
 * Coordinate representing an [X, Y] pixel location relative to the screen.
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
public final class ScreenPixelCoordinate extends Coordinate {

    /**
     * Create a new ScreenPixelCoordinate.
     * 
     * @param newX
     *            x value in screen pixels
     * @param newY
     *            y value in screen pixels
     * @return none
     */
    public ScreenPixelCoordinate(int newX, int newY) {
        super(newX, newY);
    }

    /**
     * Converts the screen pixel coordinate to the corresponding dungeon pixel
     * coordinate.
     * 
     * @param none
     * @return none
     */
    public DungeonPixelCoordinate toDungeon() {
        DungeonPixelCoordinate coord = new DungeonPixelCoordinate(getX() - getScreenX(), getY()
                                        - getScreenY());
        return coord;
    }
}
