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

import java.util.Observable;

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.view.AnimationFrameSequence;
import rogueandroid.cs428.sp10.view.Displayable;

import com.stickycoding.Rokon.Debug;

/** 
 * Implementation of Entity class for Characters and Monsters
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
public abstract class EntityClass extends Observable implements Entity {

    private static final boolean LOCAL_LOGGING = false;
    protected Dungeon mDungeon;
    protected DungeonPixelCoordinate mPixelLocation;
    protected DungeonTileCoordinate mTileLocation;
    protected Action mAction;
    protected Action mWaitingAction;
    // TODO: these values need to be set on creation!
    protected int mMaxHitPoints = 100;
    protected int mCurrentHitPoints = 100;
    protected int mSpeed = 10;
    protected String mName = null;
    protected int mAttackBonus = 0;
    protected int mDefenseBonus = 0;
    public String mResource = null;
    protected Inventory inventory = new Inventory();
    protected Displayable mDisplayable;
    protected Direction mFacing = Direction.NEG_X;

    /**
     * Constructor
     */
    public EntityClass() {
        super();
    }

    /**
     * Set the fixed dungeon location of character. This is for testing and
     * internal use only.
     * @param tileLocation
     * @param pixelLocation
     */
    public void setLocation(DungeonTileCoordinate tileLocation,
                                    DungeonPixelCoordinate pixelLocation) {
        mTileLocation = tileLocation;
        mPixelLocation = pixelLocation;
        if (mDisplayable != null) {
            mDisplayable.setXY(pixelLocation);
        }
        setChanged();
        notifyObservers(pixelLocation);
    }

    /**
     * Return the current tile location.
     * @return DungeonTileCoordiante location
     */
    public DungeonTileCoordinate getTileLocation() {
        return mTileLocation;
    }

    /**
     * Set (replace the previous if it was set before) the character's Action.
     * @param newAction Action to queue
     */
    public void queueAction(Action newAction) {
        if (mAction == null) {
            mAction = newAction;
        } else {
            mAction.stopAsSoonAsPossible();
            mWaitingAction = newAction;
        }
    }

    /**
     * Return a reference to the character's action, possibly null.
     * 
     * @return Action
     */
    public Action getAction() {
        return mAction;
    }

    /**
     * Get the DungeonPixel Location
     * 
     * @return DungeonPixelCoordinate
     */
    public DungeonPixelCoordinate getPixelLocation() {
        return mPixelLocation;
    }

    /**
     * Update the action queue
     */
    private void updateActionQueue() {
        // If an action is waiting...
        if (mWaitingAction != null) {
            // If an action is ongoing...
            if (mAction != null) {
                // Do nothing if the ongoing action is uncompleted or
                // un-replaceable.
                if (!mAction.actionCompleted()) return;
                if (!mAction.actionReplaceable()) return;
            }
            mAction = mWaitingAction;
            mWaitingAction = null;
            if (LOCAL_LOGGING) {
                Debug.print("EntityClass.updateActionQueue: " +
                "Dequeue next action.");
            }
        }
    }

    /**
     * Every entity needs to process its action in the game loop.
     */
    public void onGameLoop() {
        updateActionQueue();
        if (mAction != null) {
            mAction.onGameLoop();
            if (mAction.actionCompleted()) {
                mAction = null;
            }
        }
    }

    /**
     * Set the entities displayable so that the graphical aspect of the game can
     * be updated when the entity's state changes. Note that this is a reversal
     * of the traditional dependency of the Model-View-Controller architecture
     * pattern.
     * 
     * @param displayable
     *            The gateway to the graphical representation of the entity.
     */
    public void setDisplayable(Displayable displayable) {
        mDisplayable = displayable;
    }

    /**
     * Return a reference to the entity's displayable.
     * 
     * @return Displayable
     */
    public Displayable getDisplayable() {
        return mDisplayable;
    }

    /**
     * Set the maximum hit points
     * @param val int
     */
    public void setMaxHitPoints(int val) {
        mMaxHitPoints = val;
    }

    /**
     * Set the current hit points
     * @param val int
     */
    public void setCurrentHitPoints(int val) {
        mCurrentHitPoints = val;
    }

    /**
     * Set the speed
     * @param val int
     */
    public void setSpeed(int val) {
        mSpeed = val;
    }

    /**
     * Set the name
     * @param name String
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * Return the name 
     * @return String
     */
    public String getName() {
        return mName;
    }

    /**
     * Set the attack bonus
     * @param val int
     */
    public void setAttackBonus(int val) {
        mAttackBonus = val;
    }

    /**
     * Get the attack bonus
     * @return mAttackBonus int
     */
    public int getAttackBonus() {
        return mAttackBonus;
    }

    /**
     * Set the defense bonus 
     * @param val int
     */
    public void setDefenseBonus(int val) {
        mDefenseBonus = val;
    }

    /**
     * Get the defense bonus
     * @return mDefenseBonus int
     */
    public int getDefenseBonus() {
        return mDefenseBonus;
    }

    /**
     * Set the resource
     * @param val String
     */
    public void setResource(String val) {
        mResource = val;
    }

    /**
     * Get the resource
     * @return String
     */
    public String getResource() {
        return mResource;
    }

    /**
     * Get the current hit points
     * @return int
     */
    public int getHitPoints() {
        return mCurrentHitPoints;
    }

    /**
     * Get the maximum hit points
     * @return int
     */
    public int getMaxHitPoints() {
        return mMaxHitPoints;
    }

    /**
     * Get the speed
     * @return int
     */
    public int getSpeed() {
        return mSpeed;
    }

    /**
     * Causes the Entity to take damage and reduce hit points
     * @param hitPoints int
     */
    public void takeDamage(int hitPoints) {
        if ((mCurrentHitPoints - hitPoints) <= 0) {
            mCurrentHitPoints = 0;
            mDisplayable.noteDeath();
        } else {
            mCurrentHitPoints -= hitPoints;
        }
        if (LOCAL_LOGGING) {
            Debug.print("EntityClass.takeDamage damage = " + 
                                            ((Integer) hitPoints).toString());
            Debug.print("EntityClass.takeDamage " +
                                            "mCurrentHitPoints = " + 
                                            ((Integer) mCurrentHitPoints).
                                            toString());
        }
        setChanged();
        notifyObservers(mCurrentHitPoints);
    }

    /**
     * Set the facing direction of the entity
     * @param newFacing Direction to face
     */
    public void setFacing(Direction newFacing) {
        mFacing = newFacing;
    }

    /**
     * Get the facing direction of the entity
     * @return Direction
     */
    public Direction getFacing() {
        return mFacing;
    }

    /**
     * Start the entity attack animation sequence
     */
    public void startAttack(AnimationFrameSequence animation) {
        if (LOCAL_LOGGING)
        {
            Debug.print("EntityClass.startAnimation: Notify Observers");
        }
        setChanged();
        notifyObservers(animation);
    }

    /**
     * Stop the entity attack animation
     */
    public void stopAttack() {
        setChanged();
        notifyObservers();
    }

    /**
     * Check to see if the Entity is dead state
     */
    public boolean isDead() {
        return mCurrentHitPoints <= 0;
    }

    /**
     * Increase the hit points of the entity
     * @param hpBonus int value to increase
     */
    public void increaseHitPoints(int hpBonus) {
        if ((mCurrentHitPoints + hpBonus) > mMaxHitPoints) {
            mCurrentHitPoints = mMaxHitPoints;
        } else {
            mCurrentHitPoints += hpBonus;
        }
        if (LOCAL_LOGGING)
        {
            Debug.print("EntityClass.increaseHitPoints: increaseHP = "
                                            + mCurrentHitPoints);
        }
        setChanged();
        notifyObservers(mCurrentHitPoints);
    }

    /**
     * Increase attack bonus
     * @param attackBonus int attack to increase
     */
    public void increaseAttackBonus(int attackBonus) {
        mAttackBonus += attackBonus;
    }

    /**
     * Increase defense bonus
     * @param defenseBonus int defense to increase
     */
    public void increaseDefenseBonus(int defenseBonus) {
        mDefenseBonus += defenseBonus;
    }

    /**
     * Get the inventory
     * @return Inventory of entity
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Add item to inventory
     * @param item Item to add
     */
    public void addInventoryItem(Item item) {
        inventory.insertItem(item);
    }
}