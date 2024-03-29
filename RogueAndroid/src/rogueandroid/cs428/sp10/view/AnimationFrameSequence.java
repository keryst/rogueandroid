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

/**
 * AnimationFrameSequence defines a sequence of still frames used to animate a
 * particular action.
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
public interface AnimationFrameSequence {

    /**
     * Return the start frame of an animation sequence.
     * 
     * @return int start frame
     */
    int getAnimationStartFrame();

    /**
     * Return the stop frame of an animation sequence.
     * 
     * @return int stop frame
     */
    int getAnimationStopFrame();

}