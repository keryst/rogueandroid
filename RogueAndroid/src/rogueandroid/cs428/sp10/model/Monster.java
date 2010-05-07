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

import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;

import java.util.Random;

/**
 * Monster represents any mobile creature in the dungeon that can move around and
 * fight the character.
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
public class Monster extends EntityClass {

    static private int mMonsterCount = 0;
    private int mMonsterId;

    public Monster() {
        super();
        this.mDungeon = null;
        this.mPixelLocation = null;
        this.mTileLocation = null;
        this.mAction = null;
        this.mMaxHitPoints = 0;
        this.mCurrentHitPoints = 0;
        this.mSpeed = 0;
        this.mName = null;
        this.mAttackBonus = 0;
        this.mDefenseBonus = 0;
        this.mResource = null;
        this.mMonsterId = mMonsterCount++;
    }

    public Monster(Monster m) {
        this.mDungeon = m.mDungeon;
        this.mPixelLocation = m.mPixelLocation;
        this.mTileLocation = m.mTileLocation;
        this.mAction = m.mAction;
        this.mMaxHitPoints = m.mMaxHitPoints;
        this.mCurrentHitPoints = m.mCurrentHitPoints;
        this.mSpeed = m.mSpeed;
        this.mName = new String(m.mName);
        this.mAttackBonus = m.mAttackBonus;
        this.mDefenseBonus = m.mDefenseBonus;
        this.mResource = new String(m.mResource);
        this.mMonsterId = mMonsterCount++;
    }

    public Monster(Dungeon dungeon, DungeonTileCoordinate startLocation) {
        mDungeon = dungeon;
        mTileLocation = startLocation;
        mPixelLocation = mTileLocation.toDungeonPixel();
//        mDungeon.setMonsterLocation(startLocation);

        mDungeon.registerEntity(this);
        this.mMonsterId = mMonsterCount++;
    }

    /**
     * Set the dungeon that will constrain this monster's movements
     * @param dungeon - The Dungeon that this monster exists in
     */
    public void setDungeon(Dungeon dungeon) {
        mDungeon = dungeon;
    }

    /**
     * Set the location of the monster in the dungeon.
     * @param startLocation - The location of the tile that the monster will be on at the start
     */  
    public void setLocation(DungeonTileCoordinate startLocation) {
        mTileLocation = startLocation;
        mPixelLocation = mTileLocation.toDungeonPixel();
//        mDungeon.setMonsterLocation(startLocation);
    }

    /**
     * Register the monster to the dungeon, so that the dungeon can track it.
     */           
    public void registerDungeon() {
        mDungeon.registerEntity(this);
    }

    /**
     * Set the location of the monster in the dungeon.
     * @param tileLocation - The location of the tile that the monster is on
     * @param pixelLocation - The location of the pixel in the dungeon that will be
     *                        the top-left corner of the item's sprite
     */        
    public void setLocation(DungeonTileCoordinate tileLocation, DungeonPixelCoordinate pixelLocation) {
        super.setLocation(tileLocation, pixelLocation);
//        mDungeon.setMonsterLocation(tileLocation); 
    }

    /**
     * Called once per main game loop.
     */         
    public void onGameLoop() {
        if (!this.isDead()) {
            super.onGameLoop();
            executeAI();
        }
    }

    /**
     * Determine a course of action for the monster.  Once an action has been chosen, a new
     * action will not begin until the current one finishes.
     */      
    private void executeAI() {
        if (mAction == null) {
            if (Math.abs(mDungeon.getCharacterLocation().getX() - 
                this.getTileLocation().getX()) < 10) {
                // Can monster attack the character -> use CheckAdjacency
                if (mDungeon.getCharacterLocation().distanceTo(mTileLocation) == 1) { // Attack
                    AttackAction anAttack = new AttackAction(this, mDungeon.getCharacter(),
                                                             new Random());
                    this.queueAction(anAttack);
                } else { // Move to Striking distance
                    this.queueAction(new TravelAction(this, mDungeon.getCharacterLocation(),
                                                      mDungeon, null));
                }
            } else { // Sleep
                this.queueAction(new SleepAction(5));
            }

        }
    }

    /**
     * Get the monster's attack bonus, which dictates how much damage it can do
     * @return The attack bonus of the monster
     */
    public int getAttackBonus() {
        return 5;
    }

    /**
     * Get the monster's defense bonus, which dictates how much damage the character can do
     * @return The defense bonus of the monster
     */
    public int getDefenseBonus() {
        return 5;
    }

}
