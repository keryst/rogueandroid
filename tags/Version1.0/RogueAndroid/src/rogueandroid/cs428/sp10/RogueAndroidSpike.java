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

import android.util.Log;

import com.stickycoding.Rokon.Font;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Text;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.Handlers.AnimationHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * RogueAndroidSpike was created to demonstrate the basics of a game built using
 * the Rokon engine. It shows how to create the engine, load some textures,
 * display elements on the screen, and show how animation and movement of those
 * elements works. It proved that Rokon was stable and full featured enough to
 * form the basis of RogueAndroid.
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
public class RogueAndroidSpike extends RokonActivity {
    public TextureAtlas mAtlas;
    public Texture[] mArrayTextures = new Texture[4];
    public FixedBackground mBackground;
    public int mXDestination;
    public int mYDestination;
    public boolean mTraveling;
    public int mCount = 0;
    public Font mFont;
    public String mData;
    // create sprites
    public Sprite[] mNarutoSprite = new Sprite[2];
    public Sprite mDragonSprite;

    /**
     * onCreate is called when the activity is created. It loads the Rokon game
     * engine.
     * 
     * @param none
     * @return none
     */
    public void onCreate() {
        createEngine("graphics/loading.png", 480, 320, true);
    }

    /**
     * onLoad is called after onCreate completes and the game engine has been
     * initialized. It loads the graphics elements as textures into the Rokon
     * engine.
     * 
     * @param none
     * @return none
     */
    @Override
    public void onLoad() {
        mAtlas = new TextureAtlas(1024, 2048);
        // final Context context = RogueAndroidSpike.this;
        String data = readSettings();
        String mage = "Mage";
        String ranger = "Rang";
        data = data.substring(0, 4);
        Log.v("CharacterClass", data);
        if (data.equals(mage)) {
            mAtlas.insert(mArrayTextures[0] = new Texture("graphics/sprites/mage.png"));
        } else if (data.equals(ranger)) {
            mAtlas.insert(mArrayTextures[0] = new Texture("graphics/sprites/ranger.png"));
        } else {
            mAtlas.insert(mArrayTextures[0] = new Texture("graphics/sprites/blueknight.png"));
        }
        mAtlas.insert(mArrayTextures[1] = new Texture("graphics/menu/start.png"));
        mAtlas.insert(mArrayTextures[2] = new Texture("graphics/sprites/dragon.png"));
        mAtlas.insert(mArrayTextures[3] = new Texture("graphics/sprites/explosion.png"));
        mAtlas.insert(mFont = new Font("fonts/256BYTES.TTF"));
        mArrayTextures[0].setTileSize(96, 96);
        mArrayTextures[3].setTileCount(5, 5);
        mArrayTextures[2].setTileCount(3, 3);
        mArrayTextures[2].setTileSize(128, 128);
        TextureManager.load(mAtlas);

        // create sprite texture
        mNarutoSprite[0] = new Sprite(100, 100, 96, 96, mArrayTextures[0]);

        // add dragon
        Sprite dragonSprite = new Sprite(200, 100, 128, 128, mArrayTextures[2]);
        rokon.addSprite(dragonSprite);
        rokon.addSprite(mNarutoSprite[0]);
        dragonSprite.animate(1, 8, 35);
        Text text = new Text("Level 1 Boss: Red Dragon", mFont, 10, 280, 32);
        rokon.addText(text);

        mBackground = new FixedBackground(mArrayTextures[1]);
    }

    /**
     * onLoadComplete is called after onLoad finishes processing. The Rokon
     * engine was created, initialized with graphics, and is now ready to
     * display the background.
     * 
     * @param none
     * @return none
     */
    @Override
    public void onLoadComplete() {
        rokon.setBackground(mBackground);

    }

    /**
     * onGameLoop is the main processing loop of the activity. If an element is
     * moving, as denoted by traveling being true, the actual onscreen movement
     * is performed when this method is called. One step of movement takes place
     * each time this method is called until the element arrives at the
     * destination.
     * 
     * @param none
     * @return none
     */
    @Override
    public void onGameLoop() {
        float x;
        float y;
        boolean xReached = false;
        boolean yReached = false;

        if (mTraveling) {
            if (mNarutoSprite[0].getX() < mXDestination) {
                x = mNarutoSprite[0].getX() + 1;
            } else if (mNarutoSprite[0].getX() > mXDestination) {
                x = mNarutoSprite[0].getX() - 1;
            } else { // equals
                x = mNarutoSprite[0].getX();
                xReached = true;
            }
            if (mNarutoSprite[0].getY() < mYDestination) {
                y = mNarutoSprite[0].getY() + 1;
            } else if (mNarutoSprite[0].getY() > mYDestination) {
                y = mNarutoSprite[0].getY() - 1;
            } else { // equals
                y = mNarutoSprite[0].getY();
                yReached = true;
            }
            mNarutoSprite[0].setXY(x, y);

            if (xReached && yReached) {
                // Stop sprite animation
                mNarutoSprite[0].stopAnimation();
            }
        }

        if (mCount > 100) {
            x = mNarutoSprite[0].getX();
            y = mNarutoSprite[0].getY();
            // Throw in some random explosions
            final Sprite explosionSprite = new Sprite(x, y, mArrayTextures[3]);
            explosionSprite.setAnimationHandler(new AnimationHandler() {
                @Override
                public void finished(Sprite sprite) {
                    rokon.removeSprite(sprite);
                }
            });
            rokon.addSprite(explosionSprite);
            explosionSprite.animate(1, 25, 35, 1, false);
            // final Context context = RogueAndroidSpike.this;
            // Toast.makeText(context, "You have been hit...",
            // Toast.LENGTH_LONG).show();
            mCount = 0;
        }

        mCount++;
    }

    /**
     * onTouchDown is an event that is triggered by the user touching a point on
     * the touch screen. It is used in the spike to denote that the user wants
     * an element to move to the touched location. Touching triggers animation
     * in the element about to start moving.
     * 
     * @param x
     *            the x coordinate touched, as determined by the underlying
     *            touch input controller. The user actually touches a large
     *            number of points, and the underlying controller determines
     *            which single point the touch will resolve to.
     * @param y
     *            the y coordinate touched, as determined by the underlying
     *            touch input controller
     * @param hotspot
     *            not used
     * @return none
     */
    public void onTouchDown(int x, int y, boolean hotspot) {
        // animate the sprite
        mNarutoSprite[0].animate(9, 16, 35);
        mXDestination = x;
        mYDestination = y;
        mTraveling = true;
    }

    /**
     * onRestart if the activity is paused, perhaps due to a phone call or other
     * phone event, this method is called to notify the application that it is
     * active again.
     * 
     * @param none
     * @return none
     */
    @Override
    public void onRestart() {
        super.onRestart();
        rokon.unpause();
    }

    /**
     * readSettings reads various configuration parameters from the file system.
     * 
     * @param none
     * @return String the configuration settings as a string
     */
    public String readSettings() {
        FileInputStream fIn = null;
        InputStreamReader isr = null;

        char[] inputBuffer = new char[255];
        String data = null;

        try {
            fIn = openFileInput("character.dat");
            isr = new InputStreamReader(fIn);
            isr.read(inputBuffer);
            data = new String(inputBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                isr.close();
                fIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
