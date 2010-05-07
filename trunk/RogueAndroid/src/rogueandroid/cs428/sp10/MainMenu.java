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

package rogueandroid.cs428.sp10;

import android.content.Intent;

import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Menu.Menu;
import com.stickycoding.Rokon.Menu.MenuObject;
import com.stickycoding.Rokon.Menu.Objects.MenuButton;

/**
 * MainMenu renders the buttons on the start screen
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
public class MainMenu extends Menu {
    MenuButton button;

    /**
     * Set the menu background and render menu items
     * 
     * @param texture
     * 			texture is the asset used for rendering the button
     * @param background
     * 			background is the asset used for rendering the background
     */
    public MainMenu(Texture texture, Background background) {
        setBackground(background);
        addMenuObject(button = new MenuButton(1, 100, 70, 300, 30, texture));
    }

    /**
     * Handles the button onTouchUp event
     * 
     * @param menuObject
     * 			menuObject is the object that the user interfaces with
     * @return none
     */
    @Override
    public void onMenuObjectTouchUp(MenuObject menuObject) {
        button.fadeOut(1000);
        Intent intent = new Intent(RogueAndroidMain.mSingleton, ChooseCharacterClass.class);
        RogueAndroidMain.mSingleton.startActivity(intent);
    }

}
