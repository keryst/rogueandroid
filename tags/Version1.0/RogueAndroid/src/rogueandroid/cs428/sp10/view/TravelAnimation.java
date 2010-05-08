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
 * TravelAnimation implements the AnimationFrameSequence interface and tells the Rokon sprite
 * animation what tiles are used for the start and stop.
 * 
 */
public class TravelAnimation implements AnimationFrameSequence {
    private int mStartTile;
    private int mStopTile;

    /**
     * Class constructor that sets the start and stop tiles for the Rokon sprite
     * animation.
     * 
     * @param animationStartTile
     *            The tile that the Rokon sprite animation starts on.
     * 
     * @param animationStopTile
     *            The tile that the Rokon sprite animation stops on.
     */
    public TravelAnimation(int animationStartTile, int animationStopTile) {
        mStartTile = animationStartTile;
        mStopTile = animationStopTile;
    }

    /**
     * Class constructor that uses Direction to set the start and stop tiles for
     * the Rokon sprite animation.
     * 
     * @param facing
     *            The direction that the entity is facing.
     * 
     */
    public TravelAnimation(Direction facing) {
        switch (facing) {
        case POS_X:
            mStartTile = 1;
            break;
        case NEG_X:
            mStartTile = 17;
            break;
        case POS_Y:
            mStartTile = 9;
            break;
        case NEG_Y:
            mStartTile = 25;
            break;
        }
        mStopTile = mStartTile + 7;
    }

    /**
     * Gets the tile for the Rokokon sprite animation to stop on.
     * 
     * @return the tile for the Rokokon sprite animation to stop on.
     * 
     */
    public int getAnimationStopFrame() {
        return mStopTile;
    }

    /**
     * Gets the tile for the Rokokon sprite animation to start on.
     * 
     * @return the tile for the Rokokon sprite animation to stop on
     * 
     */
    public int getAnimationStartFrame() {
        return mStartTile;
    }

}
