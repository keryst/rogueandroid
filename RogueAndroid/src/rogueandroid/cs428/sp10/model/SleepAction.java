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
 * SleepAction performs no action for the specified period of time.
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
public class SleepAction implements Action {
    private int mCountDown;

    /**
     * Construct a SleepAction object.  
     * @param loopCount - The number of cycles through the game loop to perform no actions 
     */    
    public SleepAction(int loopCount) {
        mCountDown = loopCount;
    }

    /**
     * Call once per cycle of the game loop to perform the action (in this case, do nothing) 
     */    
    public void onGameLoop() {
        if (mCountDown > 0) {
            mCountDown = mCountDown - 1;
        }
    }

    /**
     *  Determine if the sleep action has been completed.
     *  @return true if the action has finished.
     */    
    public boolean actionCompleted() {
        return (mCountDown == 0);
    }

    /**
     *  Determine if the action can be interrupted and replaced with a different action.
     *  @return true if the sleep action can be interrupted.
     */        
    public boolean actionReplaceable() {
        return true;
    }

    /**
     *  Force the sleep action to stop immediately.
     */        
    public void stopAsSoonAsPossible() {
        mCountDown = 0;
    }
}
