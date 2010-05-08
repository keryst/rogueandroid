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

import rogueandroid.cs428.sp10.Direction;

/**
 * AttackAnimationFrameSequence implements the AnimationFrameSequence interface
 * and tells the Rokon sprite animation what frames are used for the start and
 * stop of the animation sequence.
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
public class AttackAnimationFrameSequence implements AnimationFrameSequence {

    final private int mStartFrame;
    final private int mStopFrame;

    final static int FRAME_COUNT = 13;
    final static int POS_X_START_FRAME = 33;
    final static int POS_Y_START_FRAME = POS_X_START_FRAME + FRAME_COUNT;
    final static int NEG_X_START_FRAME = POS_Y_START_FRAME + FRAME_COUNT;
    final static int NEG_Y_START_FRAME = NEG_X_START_FRAME + FRAME_COUNT;

    /**
     * Create an animation frame sequence for an attack animation using the
     * supplied direction.
     * 
     * @param facing
     *            The direction that the entity is facing.
     * 
     */
    public AttackAnimationFrameSequence(Direction facing) {
        switch (facing) {
        case POS_X:
            mStartFrame = POS_X_START_FRAME;
            break;
        case NEG_X:
            mStartFrame = NEG_X_START_FRAME;
            break;
        case POS_Y:
            mStartFrame = POS_Y_START_FRAME;
            break;
        case NEG_Y:
            mStartFrame = NEG_Y_START_FRAME;
            break;
        default:
            mStartFrame = POS_X_START_FRAME;
            break;
        }
        mStopFrame = mStartFrame + FRAME_COUNT - 1;
    }

    /**
     * Gets the frame for the Rokon sprite animation to stop on.
     * 
     * @return the frame for the Rokon sprite animation to stop on.
     * 
     */
    public int getAnimationStopFrame() {
        return mStopFrame;
    }

    /**
     * Gets the frame for the Rokon sprite animation to start on.
     * 
     * @return the frame for the Rokon sprite animation to stop on
     * 
     */
    public int getAnimationStartFrame() {
        return mStartFrame;
    }

}
