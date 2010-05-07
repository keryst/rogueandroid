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
package rogueandroid.cs428.sp10.controller;

import com.stickycoding.Rokon.Debug;

import rogueandroid.cs428.sp10.model.Character;
import rogueandroid.cs428.sp10.model.Dungeon;
import rogueandroid.cs428.sp10.view.View;

/**
 * Controller is the input handler for touch events. RogueAndroid uses an MVC
 * architecture, and controller is where all input events are handled.
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
public class Controller {
    private Character mCharacter;
    private View mView;

    /*
     * user input stuff These are the coordinates where user first touched the
     * screen. If the user lets go within 30 pixels of these coordinates, the
     * user is "selecting" the tile at these coordinates. If the user continues
     * holding down on the screen after passing that threshold, the user is
     * "swiping" the screen and the map will scroll around.
     */
    public int mUserTouchDownX = 0;
    public int mUserTouchDownY;
    public int mUserLastTouchX;
    public int mUserLastTouchY;

    public boolean mIsUserSwiping = false;
    public boolean mIsAdjacent = false;
    public boolean mIsMonster = false;
    private Dungeon mDungeon;

    /**
     * Controller initializes the class with the character, dunegon, and the
     * view.
     * 
     * @param theCharacter
     *            the users entity in the game
     * @param dungeon
     *            the dungeon, which houses the elements of the game
     * @param theView
     *            the View in the MVC architecture
     * @return none
     */
    public Controller(Character theCharacter, Dungeon dungeon, View theView) {
        mCharacter = theCharacter;
        mDungeon = dungeon;
        mView = theView;
    }

    private Touch mTouch;

    /**
     * onTouchDown The user just touched the screen, store the coordinates of
     * the touch event
     * 
     * @param x
     *            the x coordinate of the touch event
     * @param y
     *            the y coordinate of the touch event
     * @param hotspot
     *            not used
     * @return none
     */
    public void onTouchDown(int x, int y, boolean hotspot) {
        mUserTouchDownX = x;
        mUserLastTouchX = x;

        mUserTouchDownY = y;
        mUserLastTouchY = y;

        Debug.print("Controller.onTouchDown at x = " + ((Integer) x).toString() + ", y = "
                                        + ((Integer) y).toString());
        mTouch = new Touch(mView, mDungeon, mCharacter, x, y);
    }

    /**
     * onTouch The user is touching the screen. If they've 'swiped' far enough,
     * scroll the background. Once we start scrolling, don't check for swipe
     * distance anymore.
     * 
     * @param x
     *            the x coordinate of the touch event
     * @param y
     *            the y coordinate of the touch event
     * @param hotspot
     *            not used
     * @return none
     */
    public void onTouch(int x, int y, boolean hotspot) {
        // Sometimes the touch events come out of order. That means that we can
        // get here
        // without having mTouch set.
        if (mTouch == null) {
            Debug.print("Controller.onTouch creating a new touch at x = "
                                            + ((Integer) x).toString() + ", y = "
                                            + ((Integer) y).toString());
            mTouch = new Touch(mView, mDungeon, mCharacter, x, y);
        } else {
            mTouch.onTouch(x, y);
        }
    }

    /**
     * onTouchUp The user let go of the screen. If they 'selected' a tile, do
     * some action.
     * 
     * @param x
     *            the x coordinate of the touch event
     * @param y
     *            the y coordinate of the touch event
     * @param hotspot
     *            not used
     * @return none
     */
    public void onTouchUp(int x, int y, boolean hotspot) {
        // Sometimes the touch events come out of order. That means that we can
        // get here
        // without having mTouch set.
        if (mTouch == null) {
            Debug.print("Controller.onTouchUp creating a new touch at x = "
                                            + ((Integer) x).toString() + ", y = "
                                            + ((Integer) y).toString());
            mTouch = new Touch(mView, mDungeon, mCharacter, x, y);
        }
        mTouch.onTouchUp(x, y);
        mTouch = null;
    }
}
