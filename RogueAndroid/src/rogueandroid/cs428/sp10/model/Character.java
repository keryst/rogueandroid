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
 * The Character class loads the textures and sprites.
 * It also control movement of the character on the map. 
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
public class Character extends EntityClass {

    private static final int DEFENSE_BONUS = 5;
    private static final int ATTACK_BONUS = 5;
    // Character attributes
    private String mCharacterClass;
    private String mCharacterName;

    /**
     * Constructor for Character
     * @param dungeon Dungeon the Character will belong to
     * @param startLocation DungeonTileCoordinate the Character will start at
     */
    public Character(Dungeon dungeon, DungeonTileCoordinate startLocation) {
        super();
        this.setAttackBonus(ATTACK_BONUS);
        this.setDefenseBonus(DEFENSE_BONUS);
        mDungeon = dungeon;
        mTileLocation = startLocation;
        mPixelLocation = mTileLocation.toDungeonPixel();

        mDungeon.setCharacterLocation(mTileLocation);

        mDungeon.registerEntity(this);
    }

    /**
     * Sets the Character class String
     * @param characterClass String character class to set
     */
    public void setCharacterClass(String characterClass) {
        this.mCharacterClass = characterClass;
    }

    /**
     * Returns the Character class as a String
     * @return the characterClass as a String
     */
    public String getCharacterClass() {
        return mCharacterClass;
    }

    /**
     * Sets the Character name 
     * @param characterName String characterName to set
     */
    public void setCharacterName(String characterName) {
        this.mCharacterName = characterName;
    }

    /**
     * Returns the Character name as String
     * @return String the characterName
     */
    public String getCharacterName() {
        return mCharacterName;
    }

    /**
     * Set the location of the character in the dungeon,
     * notify the view of the update
     * @param tileLocation DungeonTileCoordinate 
     * @param pixelLocation DungeonPixelCoordinate 
     */
    @Override
    public void setLocation(DungeonTileCoordinate tileLocation,
                                    DungeonPixelCoordinate pixelLocation) {
        // check if landed on new tile
        if (!mTileLocation.equals(tileLocation)) {
            onNewTile(mTileLocation, tileLocation);
        }
        super.setLocation(tileLocation, pixelLocation);
        mDungeon.setCharacterLocation(tileLocation);
    }

    /**
     * Called when character lands on a new tile
     * @param tileLocationOld
     * @param tileLocationNew
     */
    public void onNewTile(DungeonTileCoordinate tileLocationOld,
                                    DungeonTileCoordinate tileLocationNew) {
        checkItems(tileLocationNew);
    }

    /**
     * Check if item on tile, if so, pickup and add to inventory unless
     * inventory is full
     * @param dtcNew DungoneTileCoordinate new coordinate to check for items
     */	
    private void checkItems(DungeonTileCoordinate dtcNew) {
        // see if character is on a tile with an item, if so try to add to inventory
        Item item = mDungeon.getItemAt(dtcNew);
        if (item != null) {
            item.use(this);	// use the item
            // three possible cases
            if (item.getUses() > 0) {
                boolean bSuccess = getInventory().insertItem(item);				
                if (bSuccess) {
                    // case 1: item is picked up and added to inventory, remove from view
                    mDungeon.removeItemAt(dtcNew);
                }
            } else {
                // case 2: items is used up so remove from view
                mDungeon.removeItemAt(dtcNew);
            }
            // case 3: item is usable but inventory full so just leave on ground
            setChanged();
            notifyObservers(dtcNew);
            notifyObservers(mCurrentHitPoints);                 
        }
    }
}