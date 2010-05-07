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

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.ScreenPixelCoordinate;

/**
 * Displayable is an interface that represents the graphical view of entities.
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
public interface Displayable {

    /**
     * Set the location of the Displayable in dungeon pixel coordinates.
     * 
     * @param location
     *            dungeon-fixed pixel location
     */
    public void setXY(DungeonPixelCoordinate location);

    /**
     * Get the location of the Displayable in dungeon pixel coordinates.
     * 
     * @return DungeonPixelCoordinate
     */
    public DungeonPixelCoordinate getXY();

    /**
     * Get the pixel coordinate on the screen for the Displayable.
     * 
     * @return ScreenPixelCoordinate
     */
    public ScreenPixelCoordinate getLocation();

    /**
     * Update the Displayable for the game loop.
     */
    public void onGameLoop();

    /**
     * This signals the displayable to start displaying attacking.
     * 
     * @param newFacing
     *            Direction
     */
    public void startAttacking(Direction newFacing);

    /**
     * This signals the displayable to stop displaying attacking.
     */
    public void stopAttacking();

    /**
     * This signals the displayable to start displaying traveling.
     * 
     * @param newFacing
     *            Direction
     */
    public void startTraveling(Direction newFacing);

    /**
     * This signals the displayable to stop displaying traveling.
     */
    public void stopTraveling();

    /**
     * This signals that the associated entity has passed away and the
     * displayable should clean itself up.
     */
    public void noteDeath();

}
