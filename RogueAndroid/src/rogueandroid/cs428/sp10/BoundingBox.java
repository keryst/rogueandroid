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
 * A simple bounding box that will surround a rectangular part of the screen.
 * The coordinates of the bounding box may be completely off the screen
 * (negative)
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
public class BoundingBox {

    private int mMinX;
    private int mMinY;

    private int mMaxX;
    private int mMaxY;

    /**
     * Base Class Constructor
     * 
     * @param minX
     * @param minY
     * @param maxX
     * @param maxY
     */
    public BoundingBox(int minX, int minY, int maxX, int maxY) {
        mMinX = minX;
        mMinY = minY;
        mMaxX = maxX;
        mMaxY = maxY;
    }

    /**
     * Empty Constructor
     * 
     */
    public BoundingBox() {
    }

    /**
     * Sets minimum X value of the box
     * 
     * @param minX
     */
    public void setMinX(int minX) {
        this.mMinX = minX;
    }

    /**
     * Gets minimum X value of the box
     * 
     * @return
     */
    public int getMinX() {
        return mMinX;
    }

    /**
     * Sets minimum Y value of the box
     * 
     * @param minY
     */
    public void setMinY(int minY) {
        this.mMinY = minY;
    }

    /**
     * Gets minimum Y value of the box
     * 
     * @return
     */
    public int getMinY() {
        return mMinY;
    }

    /**
     * Sets maximum X value of the box
     * 
     * @param maxX
     */
    public void setMaxX(int maxX) {
        this.mMaxX = maxX;
    }

    /**
     * Gets maximum X value of the box
     * 
     * @return
     */
    public int getMaxX() {
        return mMaxX;
    }

    /**
     * Sets maximum Y value of the box
     * 
     * @param maxY
     */
    public void setMaxY(int maxY) {
        this.mMaxY = maxY;
    }

    /**
     * Gets maximum Y value of the box
     * 
     * @return
     */
    public int getMaxY() {
        return mMaxY;
    }

    /**
     * Adds x and y offset value to min(x,y) and max(x,y)
     * 
     * @param xOffset
     * @param yOffset
     */
    public void offset(int xOffset, int yOffset) {
        mMinX += xOffset;
        mMinY += yOffset;
        mMaxX += xOffset;
        mMaxY += yOffset;
    }

}
