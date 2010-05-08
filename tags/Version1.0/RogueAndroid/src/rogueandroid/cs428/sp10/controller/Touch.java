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

import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.Game;
import rogueandroid.cs428.sp10.ScreenPixelCoordinate;
import rogueandroid.cs428.sp10.model.AttackAction;
import rogueandroid.cs428.sp10.model.Character;
import rogueandroid.cs428.sp10.model.Dungeon;
import rogueandroid.cs428.sp10.model.Entity;
import rogueandroid.cs428.sp10.model.TravelAction;
import rogueandroid.cs428.sp10.view.View;

import com.stickycoding.Rokon.Debug;

import java.util.Random;

/**
 * Touch is the input handler portion of the controller in the MVC architecture.
 * It handles touch events and processes them.
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
public class Touch {
    final private int mInitialX;
    final private int mInitialY;
    private boolean mIsSwipe = false;
    private int mPreviousX;
    private int mPreviousY;
    private View mView;
    private Dungeon mDungeon;
    private Character mCharacter;

    final static int WIGGLE = 15; // Number of pixels that might be wiggled
    // without a swipe.
    static private final boolean LOCAL_LOGGING = false;

    /**
     * Touch creates the touch object
     * 
     * @param theView
     *            the View in the MVC architecture
     * @param theDungeon
     *            the holder of the game elements
     * @param theCharacter
     *            the user's avatar in the game
     * @param initialx
     *            the x location of the touch event
     * @param initialy
     *            the y location of the touch event
     * @return none
     */
    public Touch(View theView, Dungeon theDungeon, Character theCharacter, int initialX,
                                    int initialY) {
        Debug.print("Touch at x = " + ((Integer) initialX).toString() + ", y = "
                                        + ((Integer) initialY).toString());
        mView = theView;
        mDungeon = theDungeon;
        mInitialX = initialX;
        mInitialY = initialY;
        mPreviousX = initialX;
        mPreviousY = initialY;
        mCharacter = theCharacter;
    }

    /**
     * checkSwipeCriteria determines if the touch event is a single tap or the
     * start of a swipe event both single taps and swipe events are valid input
     * types in the game, but the underlying touch controller doesn't
     * distinguish between the two. A swipe is determined by two consecutive
     * touch events that are farther apart than the Wiggle, or the subtle
     * differences between what to the user are identical touches.
     * 
     * @param x
     *            the x coordinate of the touch event
     * @param y
     *            the y coordinate of the touch event
     * @return true if a swipe, false if a touch event
     */
    private boolean checkSwipeCriteria(int x, int y) {
        return mIsSwipe || Math.abs(mInitialX - x) > WIGGLE || Math.abs(mInitialY - y) > WIGGLE;
    }

    /**
     * onTouch process the on touch event
     * 
     * @param x
     *            the x coordinate of the touch event
     * @param y
     *            the y coordinate of the touch event
     * @return none
     */
    public void onTouch(int x, int y) {
        Debug.print("Touch.onTouch at x = " + ((Integer) x).toString() + ", y = "
                                        + ((Integer) y).toString());
        mIsSwipe = checkSwipeCriteria(x, y);
        if (mIsSwipe) {
            if (LOCAL_LOGGING) {
                Debug.print("Touch.onTouch mIsSwipe");
            }
            int offsetX = x - mPreviousX;
            int offsetY = y - mPreviousY;

            mView.scrollScreen(offsetX, offsetY);
            mPreviousX = x;
            mPreviousY = y;
        }
    }

    /**
     * onTouchUp event is called when the user stops touching the screen. There
     * can be multiple touch events without the user lifting their finger. If
     * these events are far enough part (ie. greater than wiggle) then they are
     * considered a swipe, otherwise they are considered as the same touch
     * event.
     * 
     * @param x
     *            the x location of the touch event
     * @param y
     *            the y location of the touch event
     * @return none
     */
    public void onTouchUp(int x, int y) {
        DungeonTileCoordinate touchDungeonTile = screenPixelToDungeonTile(x, y);

        if (LOCAL_LOGGING) {
            Debug.print("Touch.onTouchUp at x = " + ((Integer) x).toString() + ", y = "
                                            + ((Integer) y).toString() + " which is tile "
                                            + touchDungeonTile.toString());
        }
        if (mIsSwipe) {
            if (LOCAL_LOGGING) {
                Debug.print("Touch.onTouchUp mIsSwipe");
            }
            mView.scrollScreen(x - mPreviousX, y - mPreviousY);
        } else {
            // This user touch action was not a swipe, so it must be a travel
            // command or an attack command.
            centerScreenOnCharacter();

            Entity anEntity = mDungeon.getEntityAt(touchDungeonTile);
            if (anEntity == null) {
                // Nothing at the destination, travel there.
                if (LOCAL_LOGGING) {
                    Debug.print("Touch.onTouchUp null entity -- travel");
                }
                TravelAction aTravel = new TravelAction(mCharacter, touchDungeonTile, mDungeon,
                                                mView);
                mCharacter.queueAction(aTravel);
            } else if (anEntity == mCharacter) {
                // Nothing to do since the user just touched the Character.
                if (LOCAL_LOGGING) {
                    Debug.print("Touch.onTouchUp on self -- no action");
                }
            } else if (touchDungeonTile.distanceTo(mCharacter.getTileLocation()) == 1) {

                if (LOCAL_LOGGING) {
                    Debug.print("Touch.onTouchUp -- attack!");
                }
                AttackAction anAttack = new AttackAction(mCharacter, anEntity, new Random());
                mCharacter.queueAction(anAttack);
            } else {
                // A far off monster was targeted. No ranged weapons yet, so
                // move there.
                if (LOCAL_LOGGING) {
                    Debug.print("Touch.onTouchUp far off monster -- travel");
                }
                TravelAction aTravel = new TravelAction(mCharacter, touchDungeonTile, mDungeon,
                                                mView);
                mCharacter.queueAction(aTravel);
            }
        }
    }

    /**
     * screenPixelToDungeonTile converts a screen pixel coordinate to the
     * corresponding tile in the dungeon. This is required because the dungeon
     * is made up of tiles, and only a portion of those tiles are displayed on
     * the screen. Most game elements operate at the dungeon tile level.
     * 
     * @param x
     *            the x screen pixel coordinate
     * @param y
     *            the y screen pixel coordinate
     * @return a DungeonTileCooridate object initialized with the corresponding
     *         dungeon tile
     */
    private DungeonTileCoordinate screenPixelToDungeonTile(int x, int y) {
        // Work through the coordinate system transformations to figure out
        // where in the Dungeon the user touched.
        ScreenPixelCoordinate touchScreenPixel = new ScreenPixelCoordinate(x, y);
        DungeonPixelCoordinate touchDungeonPixel = touchScreenPixel.toDungeon();
        DungeonTileCoordinate touchDungeonTile = touchDungeonPixel.toDungeonTile();
        return touchDungeonTile;
    }

    /**
     * centerScreenOnCharacter Center the screen on the character. The character
     * can move location, and this method determines the proper viewport center
     * for the screen, and scrolls the screen until the character is centered.
     * 
     * @param none
     * @return none
     */
    private void centerScreenOnCharacter() {
        // center the camera back onto the character
        int centerX = mCharacter.getTileLocation().toDungeonPixel().toScreen().getX();
        int centerY = mCharacter.getTileLocation().toDungeonPixel().toScreen().getY();
        // Debug.print("Character Location: (" + centerX + ", " + centerY +
        // ")");
        // Find the difference in current location and a perfectly center
        // sprite.
        // For example, on a droid screen, a 96 pixel tile would be centered at
        // (192, 427)
        centerX -= 192;
        centerY -= 427;
        // Debug.print("Auto-center: (" + -centerX + ", " + -centerY + ")");

        // If the character is outside the free travel zone, re-center the
        // camera.
        // The free travel zone prevents screen jitter due to tiny movements.
        if (Math.abs(centerX) > Game.config().getTileSize()
                                        || Math.abs(centerY) > Game.config().getTileSize()) mView
                                        .scrollScreen(-centerX, -centerY);
    }
}
