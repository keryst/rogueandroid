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

package rogueandroid.cs428.sp10.view;

import rogueandroid.cs428.sp10.BoundingBox;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.model.Dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * View scrolls the screen and ensures that the map is not scrolled outside of
 * its bounds. It also updates the displayables screen location when the screen
 * is scrolled.
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
public class View {
    private Dungeon mDungeon;
    private TileEngine mTileEngine;
    private List<Displayable> mDisplayables = new ArrayList<Displayable>();
    private BoundingBox mTileEngineBounds;
    private int mScreenYPixels = 0;
    private int mSreenXPixels = 0;

    /**
     * Class constructor
     * 
     * @param theDungeon
     *            The Dungeon
     * @param theTileEngine
     *            The TileEngine
     * @param screenXWidth
     *            The horizontal width of the screen.
     * @param screenYWidth
     *            The vertical width of the screen.
     */
    public View(Dungeon theDungeon, TileEngine theTileEngine, int screenXWidth, int screenYWidth) {
        mDungeon = theDungeon;
        mTileEngine = theTileEngine;
        mTileEngineBounds = mTileEngine.getBoundingBox();
        mSreenXPixels = screenYWidth;
        mScreenYPixels = screenXWidth;
    }

    /**
     * Adds a Displayable to an ArrayList.
     * 
     * @param Displayable
     *            the potential offset value
     */
    public void addDisplayable(Displayable aDisplayable) {
        mDisplayables.add(aDisplayable);
    }

    /**
     * Is called by the Game and called onGameLoop method in the Displayable to
     * changes its screen location.
     * 
     * @param Displayable
     *            The graphical representation of a game object
     */
    public void onGameLoop() {
        for (Displayable d : mDisplayables) {
            d.onGameLoop();
        }
    }

    /**
     * Given an offset in x that can be used to scroll the map on the screen,
     * determine the valid offset so that the edge of the map doesn't become
     * visible
     * 
     * @param offsetX
     *            the potential offset value
     * @return the allowable offset
     */
    public int clipScrollX(int offsetX) {
        // clip the possible offset so we can't scroll off the map
        int deltaX = mTileEngineBounds.getMinX() + offsetX;
        if (deltaX > 0) {
            offsetX = offsetX - deltaX;
            return offsetX;
        }

        deltaX = mTileEngineBounds.getMaxX() + offsetX;
        if (deltaX < mSreenXPixels) {
            offsetX = offsetX + (mSreenXPixels - deltaX);
        }

        return offsetX;
    }

    /**
     * Given an offset in y that can be used to scroll the map on the screen,
     * determine the valid offset so that the edge of the map doesn't become
     * visible.
     * 
     * @param offsetY
     *            the potential offset value
     * @return the allowable offset
     */
    public int clipScrollY(int offsetY) {
        // clip the possible offset so we can't scroll off the map
        int deltaY = mTileEngineBounds.getMinY() + offsetY;
        if (deltaY > 0) {
            offsetY = offsetY - deltaY;
            return offsetY;
        }

        deltaY = mTileEngineBounds.getMaxY() + offsetY;

        if (deltaY < mScreenYPixels) {
            offsetY = offsetY + (mScreenYPixels - deltaY);
        }
        return offsetY;
    }

    /**
     * Scrolls the screen by setting the offset from the <code>Touch</code>
     * class and calling the <code>scroll</code> method in
     * <code>TileEngine</code> class. It makes sure the the map is not scrolled
     * outside of its bounds.
     * 
     * @param xOffset
     *            The horizontal offset value
     * @param yOffset
     *            The vertical offset value
     */
    public void scrollScreen(int xOffset, int yOffset) {
        xOffset = clipScrollX(xOffset);
        yOffset = clipScrollY(yOffset);

        mTileEngineBounds.offset(xOffset, yOffset);

        mTileEngine.scroll(xOffset, yOffset);
        DungeonTileCoordinate.scrollScreen(xOffset, yOffset);
    }
}
