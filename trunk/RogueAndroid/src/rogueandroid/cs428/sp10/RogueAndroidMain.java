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

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

/**
 * RogueAndroidMain is the main entrypoint. It creates the Rokon game engine,
 * loads some textures, and then presents the main menu to the user.
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
public class RogueAndroidMain extends RokonActivity {
    public Texture[] mTextures = new Texture[2];
    public FixedBackground mBackground;
    public TextureAtlas mAtlas;
    public static RogueAndroidMain mSingleton;

    /**
     * Called when the activity is created, it creates the Rokon engine instance
     * as a singleton, and displays the loading screen.
     * 
     * @param none
     * @return none
     */
    @Override
    public void onCreate() {
        mSingleton = this;
        createEngine("graphics/loading.png", 480, 320, false);

    }

    /** The Method loads textures into the Rokon engine */
    /**
     * Called when the class is loaded, it inserts the textures into the Rokon
     * engine.
     * 
     * @param none
     * @return none
     */
    @Override
    public void onLoad() {
        mAtlas = new TextureAtlas(1024, 1024);
        mAtlas.insert(mTextures[0] = new Texture("graphics/menu/start.bmp"));
        mAtlas.insert(mTextures[1] = new Texture("graphics/menu/startButton.bmp"));
        TextureManager.load(mAtlas);
        mBackground = new FixedBackground(mTextures[0]);
    }

    /**
     * onLoadComplete renders the main menu of choices to the user.
     * 
     * @param none
     * @return none
     */
    @Override
    public void onLoadComplete() {
        rokon.showMenu(new MainMenu(mTextures[1], mBackground));
    }
}
