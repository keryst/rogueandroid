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

import com.stickycoding.Rokon.Font;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.TextureAtlas;

import rogueandroid.cs428.sp10.ScreenPixelCoordinate;

/**
 * ViewGameOver is called when the user wins or loses the game. It displays text
 * on the screen to indicate that the game is over and instructions for the
 * user.
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
public class ViewGameOver {

    private DisplayableText mDispGameOver;
    private DisplayableText mDispGameOverTouch;
    private Font mFont;

    /**
     * Constructor class that creates the text and sets its location, color and
     * scale.
     * 
     * @param textAtlas
     *            The texture atlas used for Rokon text.
     * @param rokon
     *            The Rokon game engine
     * @param font
     *            The Rokon font
     * 
     */

    public ViewGameOver(TextureAtlas textAtlas, Rokon rokon, Font font) {
        mFont = font;

        ScreenPixelCoordinate gameOverLocation = new ScreenPixelCoordinate(60, 400);
        String gameOverString = "Game  Over";
        mDispGameOver = new DisplayableText(textAtlas, rokon, gameOverString, mFont);
        mDispGameOver.setXY(gameOverLocation);
        mDispGameOver.setColor(1, 1, 0, 1);
        mDispGameOver.setScale(100);

        ScreenPixelCoordinate gameOverTouchLocation = new ScreenPixelCoordinate(20, 500);
        String gameOverTouchString = "Touch  To  Play  Again";
        mDispGameOverTouch = new DisplayableText(textAtlas, rokon, gameOverTouchString, mFont);
        mDispGameOverTouch.setXY(gameOverTouchLocation);
        mDispGameOverTouch.setScale(70);
    }

}
