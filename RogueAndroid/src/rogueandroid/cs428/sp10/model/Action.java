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
 * Define the interface for different types of game actions for entities
 * (character and monsters). Actions affect the game model and inform the game
 * view so that the view can properly display the affects of the actions.
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
public interface Action {

    /**
     * onGameLoop Provides the processing hook for the action to advance its
     * state.
     */
    public void onGameLoop();

    /**
     * actionComplete Returns <code>true</code> once the action has completed
     * its state machine
     * 
     * @return boolean
     */
    public boolean actionCompleted();

    /**
     * actionCompleted Returns <code>true</code> when the current action can be
     * replaced by another one. Actions may take multiple game loops to
     * complete. If, while executing, a new action needs to replace the existing
     * action, it should happen when this function returns <code>true</code>.
     * 
     * @return boolean
     */
    public boolean actionReplaceable();

    /**
     * actionReplaceable Notifies the action that a replacement is waiting and
     * that it should finish up as soon as possible.
     */
    public void stopAsSoonAsPossible();
}
