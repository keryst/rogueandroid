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
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.DungeonTileCoordinate;

/**
 * Scroll is an object in the dungeon that can picked up and gives certain bonuses to the character.
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
public class Scroll extends Item {
    public Scroll() {
        super();
    }

    public Scroll(Item i) {
        this.mName = new String(i.mName);
        this.mWeight = i.mWeight;
        this.mEquip = i.mEquip;
        this.mUses = i.mUses;
        this.mAttackBonus = i.mAttackBonus;
        this.mDefenseBonus = i.mDefenseBonus;
        this.mHitPointBonus = i.mHitPointBonus;
        this.mResource = new String(i.getResource());
        mPixelLocation = new DungeonPixelCoordinate(i.mPixelLocation);
        mTileLocation = new DungeonTileCoordinate(i.mTileLocation);
        this.mPickedUp = i.mPickedUp;
    }

    /**
     * The character uses the scroll, expending one of its uses, and bestowing
     * the scroll's bonuses on the character
     * @param character - The character that is reading the scroll
     */     
    public void use(Character character) {
        if (this.getUses() > 0) {
            character.increaseHitPoints(this.getHitPointBonus());
            character.increaseAttackBonus(this.getAttackBonus());
            character.increaseDefenseBonus(this.getDefenseBonus());
            this.setUses(getUses() - 1);
        }
    }

}
