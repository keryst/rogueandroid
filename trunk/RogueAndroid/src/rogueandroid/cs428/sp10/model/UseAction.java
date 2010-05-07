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

/**
 * UseAction allows the character to consume an item and be affected by any bonuses
 * that the item can bestow.
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
public class UseAction implements Action {
    private Item mItem;
    private Character mCharacter;

    public UseAction(Character character, Item item) {
        mItem = item;
        mCharacter = character;
    }

    /**
     * Call once per cycle of the game loop to perform the action (in this case, use the item) 
     */     
    public void onGameLoop() {
        mItem.use(mCharacter);
    }

    /**
     *  Determine if the use action has been completed.
     *  @return Always true because the action is instantaneous
     */        
    public boolean actionCompleted() {
        return true;
    }

    /**
     *  Determine if the action can be interrupted and replaced with a different action.
     *  @return Always true because the action is instantaneous
     */        
    public boolean actionReplaceable() {
        return true;
    }

    /**
     *  Force the use action to stop immediately.  Does nothing because
     *  the action is instantaneous.
     */         
    public void stopAsSoonAsPossible() {
    }
}
