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
 *
 */

package rogueandroid.cs428.sp10;

/**
 * 
 * An abstract base class for an immutable family of coordinates.
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
abstract class Coordinate {

    // coordinate variables
    private final int mX;
    private final int mY;

    private static int sTileSize = 96;
    private static int sScreenX = 0;
    private static int sScreenY = 0;

    /**
     * Base class constructor
     * 
     * @param newX
     * @param newY
     */
    public Coordinate(int newX, int newY) {
        mX = newX;
        mY = newY;
    }

    /**
     * Set the tile size for use in the coordinate class hierarchy.
     * 
     * @param tileSize
     *            in pixels
     */
    public static void setTileSize(int tileSize) {
        sTileSize = tileSize;
    }

    /**
     * Get the tile size.
     * 
     * @return sTileSize
     */
    public static int getTileSize() {
        return sTileSize;
    }

    /**
     * Set the correlation between screen and dungeon pixel coordinates.
     * 
     * @param x
     * @param y
     */
    public static void setScreen(int x, int y) {
        sScreenX = x;
        sScreenY = y;
    }

    /**
     * Adjust the correlation between the screen and the dungeon by the offsets
     * provided.
     * 
     * @param xOffset
     * @param yOffset
     */
    public static void scrollScreen(int xOffset, int yOffset) {
        sScreenX += xOffset;
        sScreenY += yOffset;
    }

    /**
     * Return the X coordinate offset between screen and dungeon.
     * 
     * @return sScreenX
     */
    public static int getScreenX() {
        return sScreenX;
    }

    /**
     * Return the Y coordinate offset between screen and dungeon.
     * 
     * @return sScreenY
     */
    public static int getScreenY() {
        return sScreenY;
    }

    /**
     * Get the X coordinate value
     * 
     * @return mX (int)
     */
    public int getX() {
        return mX;
    }

    /**
     * Get the Y coordinate value
     * 
     * @return mY (int)
     */
    public int getY() {
        return mY;
    }

    /**
     * Compares two coordinates.
     * 
     * @param other
     * @return (boolean)
     */
    public boolean equals(Coordinate other) {
        if (this.getClass() != other.getClass()) {
            return false;
        }
        if (mX == other.mX && mY == other.mY) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Coordinate: [" + mX + "," + mY + "]";
    }
}
