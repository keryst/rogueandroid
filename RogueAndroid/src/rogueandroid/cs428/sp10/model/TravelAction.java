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

package rogueandroid.cs428.sp10.model;

import com.stickycoding.Rokon.Debug;

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.view.Displayable;
import rogueandroid.cs428.sp10.view.View;

import java.util.Observable;

/**
 * TravelAction maneuvers the traveling entity to the specified dungeon tile
 * coordinate over a series of pixel moves from tile to un-obstructed tile of
 * the dungeon.
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
public class TravelAction extends Observable implements Action {

    // This value is set to true when this action can be replaced by another
    // one.
    private boolean mReplaceable;

    // These values hold information captured at creation time.
    private Entity mTraveler;
    private Dungeon mDungeon;
    private View mView;
    private int mSpeed;
    private Displayable mDisplayable;

    // These two coordinates represent the final goal destination.
    private DungeonTileCoordinate mTileDestination;
    private DungeonPixelCoordinate mPixelDestination;

    // These two coordinates represent the current entity location.
    private DungeonTileCoordinate mTileLocation;
    private DungeonPixelCoordinate mPixelLocation;

    // This coordinate represent an intermediate step towards the final goal.
    // Steps are made one tile at a time, but the entity's tile location is
    // changed when the step is taken. The dungeon pixel coordinate must then be
    // update over time to reach the proper location on the tile.
    private DungeonPixelCoordinate mPixelStep;

    // static private final boolean LOCAL_LOGGING = false;
    private boolean LOCAL_LOGGING = false;

    /**
     * Create a new TravelAction to travel from the traveler's current location
     * to the specified destination.
     * 
     * @param traveler - The Entity that is moving
     * @param destination - The DungeonTileCoordinate that the traveler is moving towards
     * @param dungeon - The Dungeon that the traveler is moving through
     * @param view - The main game View
     */
    public TravelAction(Entity traveler, DungeonTileCoordinate destination, Dungeon dungeon,
                                    View view) {
        mTraveler = traveler;
        mDungeon = dungeon;
        mView = view;
        mTileDestination = destination;
        mPixelDestination = mTileDestination.toDungeonPixel();
        mDisplayable = traveler.getDisplayable();
        mSpeed = mTraveler.getSpeed();

        // Pre-get this to avoid null references.
        mPixelLocation = mTraveler.getPixelLocation();

        mReplaceable = false;
        LOCAL_LOGGING = (mTraveler instanceof Character);
    }

    /**
     * Indicate when the action has completed.
     * @return True if the action has finished and the traveler is no moving
     */
    public boolean actionCompleted() {
        return !traveling();
    }

    /**
     * Perform game loop processing by moving the traveler.
     */
    public void onGameLoop() {
        // If the travel action was not completely initialized (which it can't
        // be until game loops start running), complete the initialization.
        if (mTileLocation == null) {
            mTileLocation = mTraveler.getTileLocation();
            mPixelLocation = mTraveler.getPixelLocation();

            // This completes the class initialization by initiating the travel
            // action.
            pickNextStep();
        }
        if (traveling()) {
            int oldX = mPixelLocation.toScreen().getX();
            int oldY = mPixelLocation.toScreen().getY();

            moveALittle();

            // For the character, autoscroll the screen and check if over an
            // item.  If so, pick it up
            if (mTraveler instanceof Character) {
                if (mView != null) {
                    int newX = mPixelLocation.toScreen().getX();
                    int newY = mPixelLocation.toScreen().getY();

                    int xOffset = newX - oldX;
                    int yOffset = newY - oldY;
                    mView.scrollScreen(-xOffset, -yOffset);
                }
            }
        }
    }

    /**
     * Return the current dungeon pixel location of the traveler.
     * @return The DungeonPixelCoordinate representing traveler's location 
     */
    public DungeonPixelCoordinate getPixelLocation() {
        return mPixelLocation;
    }

    /**
     * Return the current dungeon pixel destination of the traveler.
     * @return  The DungeonPixelCoordinate representing traveler's destination
     */
    public DungeonPixelCoordinate getPixelDestination() {
        return mPixelDestination;
    }

    /**
     * Return the step destination (the current increment of movement)
     * dungeon pixel coordinate.
     * @return The DungeonPixelCoordinate representing traveler's current
     *         step destination.
     */
    public DungeonPixelCoordinate getPixelStep() {
        return mPixelStep;
    }

    /**
     * Return the current goal destination tile coordinate.
     * @return The DungeonTileCoordinate representing traveler's destination
     */
    public DungeonTileCoordinate getDestination() {
        return mTileDestination;
    }

    /**
     * Determine if the traveler is currently moving towards a destination
     * @return True if traveling to destination.
     */
    public boolean traveling() {
        return !mPixelLocation.equals(mPixelDestination);
    }

    /**
     * Compute a value closer to the goal limited by the max step allowed.
     * The step is calculated in a single direction.
     * @param current - The current tile location, either x or y.
     * @param goal - The destination tile location, either x or y.
     * @param max - The maximum number of steps possible for this movement
     * @return The next tile location (either x or y) that is closer to goal
     */
    public int stepTowardsGoal(int current, int goal, int max) {
        int delta = Math.abs(current - goal);
        int offset = Math.min(max, delta);
        if (current < goal) {
            current += offset;
        } else if (current > goal) {
            current -= offset;
        }
        return current;
    }

    /**
     * Pick the next tile step.
     */
    public void pickNextStep() {
        int newX = mTileLocation.getX();
        int newY = mTileLocation.getY();
        int deltaX = Math.abs(newX - mTileDestination.getX());
        int deltaY = Math.abs(newY - mTileDestination.getY());
        DungeonTileCoordinate tileStep = firstChoiceStepTile(newX, newY, deltaX, deltaY);
        if (isStepObstructed(tileStep)) {
            tileStep = secondChoiceStepTile(newX, newY, deltaX, deltaY);
        }
        mPixelStep = tileStep.toDungeonPixel();
        if (isStepObstructed(tileStep)) {
            reviseDestination();
        } else {
            try {
                Direction facing = Direction.directionBetween(mTileLocation, tileStep);
                if (LOCAL_LOGGING) {
                    Debug.print("From " + mTileLocation.toString() + " to " + tileStep.toString()
                                                    + " facing = " + facing.toString());
                }
                mDisplayable.startTraveling(facing);
            } catch (Exception e) {
                Debug.print("ERROR: TravelAction.pickNextStep exception on setting a facing.");
            }
            mTileLocation = tileStep;
        }
    }

    /**
     * Determine the best tile to move towards to the destination
     * @param newX - The current tile x coordinate of the traveler 
     * @param newY - The current tile y coordinate of the traveler
     * @param deltaX - The current x direction and distance to destination
     * @param deltaY - The current y direction and distance to destination
     * @return The tile that is closest towards the destination 
     */
    private DungeonTileCoordinate firstChoiceStepTile(int newX, int newY, int deltaX, int deltaY) {
        if (deltaX > deltaY) {
            newX = stepTowardsGoal(newX, mTileDestination.getX(), 1);
        } else {
            newY = stepTowardsGoal(newY, mTileDestination.getY(), 1);
        }
        DungeonTileCoordinate tileStep = new DungeonTileCoordinate(newX, newY);
        return tileStep;
    }

    /**
     * Determine the second best tile to move towards to the destination (in case the
     * first choice was blocked)
     * @param newX - The current tile x coordinate of the traveler 
     * @param newY - The current tile y coordinate of the traveler
     * @param deltaX - The current x direction and distance to destination
     * @param deltaY - The current y direction and distance to destination
     * @return The tile that is second closest towards the destination
     */
    private DungeonTileCoordinate secondChoiceStepTile(int newX, int newY, int deltaX, int deltaY) {
        if (deltaX <= deltaY) {
            newX = stepTowardsGoal(newX, mTileDestination.getX(), 1);
        } else {
            newY = stepTowardsGoal(newY, mTileDestination.getY(), 1);
        }
        DungeonTileCoordinate tileStep = new DungeonTileCoordinate(newX, newY);
        return tileStep;
    }

    /**
     * Move in the pixel domain towards the next tile step.
     */
    public void moveTowardsNextStep() {
        int currentX = mPixelLocation.getX();
        int currentY = mPixelLocation.getY();
        int goalX = mPixelStep.getX();
        int goalY = mPixelStep.getY();
        currentX = stepTowardsGoal(currentX, goalX, mSpeed);
        currentY = stepTowardsGoal(currentY, goalY, mSpeed);
        mPixelLocation = new DungeonPixelCoordinate(currentX, currentY);
        mReplaceable = (mPixelLocation.equals(mPixelStep));
    }

    /**
     * Check whether the step destination tile can be entered.
     * @param  tileToCheck - The tile representing that step destination
     * @return True if the tile is blocked
     */
    private boolean isStepObstructed(DungeonTileCoordinate tileToCheck) {
        if (mDungeon.isObstructed(tileToCheck)) {
            if (LOCAL_LOGGING) {
                Debug.print("TravelAction tile step blocked at " + tileToCheck.toString());
            }
            return true;
        }
        return false;
    }

    /**
     * Change the goal destination to the "proper" pixel of the current tile.
     * This routine causes the travel action to end on the current tile once the
     * current pixel location is properly set.
     */
    private void reviseDestination() {
        // Stop traveling by setting destination to current location.
        if (mTileLocation == null) {
            mTileLocation = mTraveler.getTileLocation();
            mPixelLocation = mTraveler.getPixelLocation();
        }
        mTileDestination = mTileLocation;
        mPixelStep = mTileLocation.toDungeonPixel();
        mPixelDestination = mPixelStep;
        if (LOCAL_LOGGING) {
            Debug.print("TravelAction revised to " + mTileLocation.toString());
        }
    }

    /**
     * Compute the next pixel and tile movement.
     */
    public void moveALittle() {
        if (mPixelStep.equals(mPixelLocation)) {
            // I have reached the step destination.
            // If I have not arrived at my final destination...
            if (traveling()) {
                pickNextStep();
            }
        }
        if (traveling()) {
            moveTowardsNextStep();
        }
        if (!traveling()) {
            mDisplayable.stopTraveling();
            if (LOCAL_LOGGING) {
                Debug.print("TravelAction arrived at (pixel) " + mPixelLocation.toString());
            }
        }
        mTraveler.setLocation(mTileLocation, mPixelLocation);
    }

    /**
     * Determine if the action can be stopped and replaced with some other action.
     * @return True if the action can be stopped.
     */
    public boolean actionReplaceable() {
        return mReplaceable;
    }

    /**
     * Set the travel destination to the current tile, which will cause the
     * travel action to stop in the shortest possible time. 
     */
    public void stopAsSoonAsPossible() {
        reviseDestination();
    }
}
