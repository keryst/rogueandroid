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

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.view.AnimationFrameSequence;
import rogueandroid.cs428.sp10.view.Displayable;

/** 
 * Entities are creatures (Character and Monster) that move
 * and interact in a dungeon.
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
public interface Entity {

    /**
     * Set the fixed dungeon location of entity.
     * @param tileLocation DungeonTileCoordinate
     * @param pixelLocation DungeonPixelCoordiate
     */
    public void setLocation(DungeonTileCoordinate tileLocation,
                                    DungeonPixelCoordinate pixelLocation);

    /**
     * Return the current dungeon location of entity.
     * @return DungeonTileCoordinate
     */
    public DungeonTileCoordinate getTileLocation();

    /**
     * Return the current dungeon location of entity.
     * @return DungeonPixelCoordinate
     */
    public DungeonPixelCoordinate getPixelLocation();

    /**
     * Set the entities displayable so that the graphical aspect of the game 
     * can be updated when the entity's state changes.  Note that this is a 
     * reversal of the traditional dependency of the Model-View-Controller
     * architecture pattern.
     * @param Displayable The gateway to the graphical representation of the 
     * entity.
     */
    public void setDisplayable(Displayable displayable);

    /**
     * Return a reference to the entity's displayable.
     * @return Displayable
     */
    public Displayable getDisplayable();

    /**
     * Set (replace the previous if it was set before) the entity's Action.
     * @param newAction
     */
    public void queueAction(Action newAction);

    /**
     * Return a reference to the entity's action, possibly null.
     * @return Action
     */
    public Action getAction();

    /**
     * Execute the entity-specific game tick processing code.
     */
    public void onGameLoop();

    /**
     * Get hit points
     * @return int
     */
    public int getHitPoints();

    /**
     * Get attack bonus
     * @return int
     */
    public int getAttackBonus();

    /**
     * Get defense bonus
     * @return
     */
    public int getDefenseBonus();

    /**
     * Get speed
     * @return int
     */
    public int getSpeed();

    /**
     * Cause entity to take damage
     * @param hitPoints int damage points
     */
    public void takeDamage(int hitPoints);

    /**
     * Check to see if the entity is dead
     * @return boolean
     */
    public boolean isDead();

    /**
     * Set the facing direction of the entity
     * @param newFacing Direction to face
     */
    public void setFacing(Direction newFacing);

    /**
     * Get the facing direction of the entity
     * @return Direction
     */
    public Direction getFacing();

    /**
     * Start the entity attack animation sequence
     * @param animation AnimationFrameSequence
     */
    public void startAttack(AnimationFrameSequence animation);

    /**
     * Stop the entity attack animation sequence
     */
    public void stopAttack();

    /**
     * Increase the hit points of the entity
     * @param hpBonus int points to increase
     */
    public void increaseHitPoints(int hpBonus);

    /**
     * Get the inventory of the entity
     * @return Inventory
     */
    public Inventory getInventory();
}