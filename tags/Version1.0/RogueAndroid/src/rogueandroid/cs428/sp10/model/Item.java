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

/**
 * Item represents any object in the dungeon that can be picked up and used by the character
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
public class Item {
    protected String mName;
    protected int mWeight;
    protected boolean mEquip;
    protected int mUses;
    protected int mAttackBonus;
    protected int mDefenseBonus;
    protected int mHitPointBonus;
    protected String mResource;
    protected DungeonPixelCoordinate mPixelLocation;
    protected DungeonTileCoordinate mTileLocation;
    protected boolean mPickedUp;

    public Item() {
        super();
    }

    public Item(Item i) {
        this.mName = new String(i.mName);
        this.mWeight = i.mWeight;
        this.mEquip = i.mEquip;
        this.mUses = i.mUses;
        this.mAttackBonus = i.mAttackBonus;
        this.mDefenseBonus = i.mDefenseBonus;
        this.mHitPointBonus = i.mHitPointBonus;
        this.mResource = new String(i.getResource());
        if (i.mPixelLocation != null) mPixelLocation = new DungeonPixelCoordinate(i.mPixelLocation);
        if (i.mTileLocation != null) mTileLocation = new DungeonTileCoordinate(i.mTileLocation);
        this.mPickedUp = i.mPickedUp;
    }

    /**
     * Set flag that dictates if the item has been picked up or not
     * @param pickedUp - True if the item has been picked up
     */
    public void pickUp(boolean pickedUp) {
        this.mPickedUp = pickedUp;
    }

    /**
     * Returns whether or not the item has been picked up
     * @return true if the item has been picked up
     */    
    public boolean isPickedUp() {
        return mPickedUp;
    }

    /**
     * Set flag that dictates if the item has been equipped to the character
     * @param equip - True if the item has been equipped
     */    
    public void equip(boolean equip) {
        this.mEquip = equip;
    }

    /**
     * Returns whether or not the item has been equipped
     * @return true if the item has been equipped
     */
    public boolean isEquipped() {
        return mEquip;
    }

    /**
     * Set the name of the item
     * @param name - The name of the item
     */    
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Returns the name of the item
     * @return the name of the item
     */    
    public String getName() {
        return mName;
    }

    /**
     * Sets the weight of the item
     * @param weight - A measure of how much the item weighs and encumbers the character
     */    
    public void setWeight(int weight) {
        this.mWeight = weight;
    }

    /**
     * Gets the weight of the item
     * @return the weight of the item
     */    
    public int getWeight() {
        return mWeight;
    }

    /**
     * Set the number of times the item can be used before it becomes unusable
     * @param uses - The number of times the item can still be used
     */    
    public void setUses(int uses) {
        this.mUses = uses;
    }

    /**
     * Gets the number of times the item can be used 
     * @return the number of times the item can be used
     */    
    public int getUses() {
        return mUses;
    }

    /**
     * Set the attack bonus that the item grants the character when it is equipped
     * @param val - The attack bonus that the item provides
     */    
    public void setAttackBonus(int val) {
        mAttackBonus = val;
    }

    /**
     * Gets the attack bonus that the item grants the character when it is equipped
     * @return the item's attack bonus
     */    
    public int getAttackBonus() {
        return mAttackBonus;
    }

    /**
     * Set the defense bonus that the item grants the character when it is equipped
     * @param val - The defense bonus that the item provides
     */  
    public void setDefenseBonus(int val) {
        mDefenseBonus = val;
    }

    /**
     * Gets the defense bonus that the item grants the character when it is equipped
     * @return the item's defense bonus
     */      
    public int getDefenseBonus() {
        return mDefenseBonus;
    }

    /**
     * Set the number of hit points returned to the player when it is used
     * @param val - The number of hit points the item will provide
     */      
    public void setHitPointBonus(int val) {
        mHitPointBonus = val;
    }

    /**
     * Gets the number of hit points returned to the player when it is used
     * @return the item's hit point bonus
     */    
    public int getHitPointBonus() {
        return mHitPointBonus;
    }

    /**
     * Set the path to the graphical asset for the item (its sprite)
     * @param val - A path to the item's graphical asset
     */       
    public void setResource(String val) {
        mResource = val;
    }

    /**
     * Get the path to the graphical asset for the item (its sprite)
     * @return the path to the item's graphical asset
     */       
    public String getResource() {
        return mResource;
    }

    /**
     * Set the location of the item in the dungeon.
     * @param startLocation - The location of the tile that the item will rest on
     */    
    public void setLocation(DungeonTileCoordinate startLocation) {
        mTileLocation = startLocation;
        mPixelLocation = mTileLocation.toDungeonPixel();
    }

    /**
     * Set the location of the item in the dungeon.
     * @param tileLocation - The location of the tile that the item will rest on
     * @param pixelLocation - The location of the pixel in the dungeon that will be
     *                        the top-left corner of the item's sprite
     */    
    public void setLocation(DungeonTileCoordinate tileLocation, DungeonPixelCoordinate pixelLocation) {
        mTileLocation = tileLocation;
        mPixelLocation = pixelLocation;
    }

    /**
     * Get the location of the tile where the item currently is
     * @return the tile location of the item
     */      
    public DungeonTileCoordinate getLocation() {
        return mTileLocation;
    }

    /**
     * Get the pixel location in the dungeon of the item's sprite
     * @return the dungeon's pixel location of the item's sprite
     */      
    public DungeonPixelCoordinate getPixelLocation() {
        return mPixelLocation;
    }

    /**
     * The character uses the item, expending one of its uses, and bestowing
     * the item's bonuses on the character
     * @param character - The character that is consuming the item
     */         
    public void use(Character character) {
        if (this.getUses() > 0) {
            character.increaseHitPoints(this.getHitPointBonus());
            character.increaseAttackBonus(this.getAttackBonus());
            character.increaseDefenseBonus(this.getDefenseBonus());
            mUses -= 1;
        }
    }

    /**
     * Called once per main game loop.  Does nothing.
     */      
    public void onGameLoop() {
    }

    /**
     * Register the item to the dungeon, so that the dungeon can track it.
     * @param dungeon - The dungeon that is tracking the item
     */       
    public void registerItem(Dungeon dungeon) {
        dungeon.registerItem(this);
    }
}
