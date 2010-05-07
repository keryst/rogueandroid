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

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.ScreenPixelCoordinate;
import rogueandroid.cs428.sp10.model.Item;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * DisplayableItem represents a usable item that exists in the game world and
 * therefore must be represented graphically.
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
public class DisplayableItem implements Displayable, Observer {

    private Sprite mSprite;
    private Texture mTexture;
    private ScreenPixelCoordinate mScreenLocation;
    private DungeonPixelCoordinate mDungeonCoordinate;
    private Item mItem;

    final static private int DEFAULT_ATLAS_X = 1024;
    final static private int DEFAULT_ATLAS_Y = 2048;

    /**
     * Create a new entity Displayable from a pre-existing TextureAtlas.
     * 
     * @param theItem
     * @param atlas
     * @param rokon
     * @param tileSize
     * @param assetPath
     */
    public DisplayableItem(Item theItem, TextureAtlas atlas, Rokon rokon, int tileSize,
                                    String assetPath) {
        mItem = theItem;
        loadTextures(atlas, tileSize, assetPath);
        createSprite(rokon, tileSize);
    }

    /**
     * Create a new entity Displayable from a pre-existing TextureAtlas stored
     * in a Hash map.
     * 
     * @param theItem
     * @param atlasMap
     * @param rokon
     * @param tileSize
     * @param assetPath
     */
    public DisplayableItem(Item theItem, HashMap<String, TextureAtlas> atlasMap, Rokon rokon,
                                    int tileSize, String assetPath) {
        mItem = theItem;
        loadTextures(atlasMap, tileSize, assetPath);
        createSprite(rokon, tileSize);
    }

    /**
     * Load the entity texture (image) using the Rokon Texture class.
     * 
     * @param atlas
     *            TextureAtlas the Rokon resource where graphics are stored
     * @param tileSize
     *            int size (height and width) of the graphic tile
     * @param assetPath
     *            String path to the graphic to use
     */
    public void loadTextures(TextureAtlas atlas, int tileSize, String assetPath) {
        mTexture = new Texture(assetPath);
        mTexture.setTileSize(tileSize, tileSize);
        atlas.insert(mTexture);
        mTexture.setTextureAtlas(atlas);
    }

    /**
     * Load the entity texture (image) using the Rokon Texture class.
     * 
     * @param atlasMap
     *            TextureAtlas Hashmap the Rokon resource where graphics are
     *            stored
     * @param tileSize
     *            int size (height and width) of the graphic tile
     * @param assetPath
     *            String path to the graphic to use
     */
    public void loadTextures(HashMap<String, TextureAtlas> atlasMap, int tileSize, String assetPath) {
        TextureAtlas tempAtlas;
        if (atlasMap.containsKey(assetPath)) {
            mTexture = atlasMap.get(assetPath).textureAtIndex(0);
        } else {
            mTexture = new Texture(assetPath);
            mTexture.setTileSize(tileSize, tileSize);
            tempAtlas = new TextureAtlas(DEFAULT_ATLAS_X, DEFAULT_ATLAS_Y);
            // TODO: should only use 1 texture per atlas
            tempAtlas.insert(mTexture, 0, 0);
            mTexture.setTextureAtlas(tempAtlas);
            atlasMap.put(assetPath, tempAtlas);
        }
    }

    /**
     * Create the displayable sprite using the Rokon sprite class.
     * 
     * @param rokon
     * @param tileSize
     */
    public void createSprite(Rokon rokon, int tileSize) {
        mSprite = new Sprite(0, 0, tileSize, tileSize, mTexture);
        rokon.addSprite(mSprite);
    }

    /**
     * Set the location of the Displayable in dungeon pixel coordinates.
     * 
     * @param location
     *            dungeon-fixed pixel location
     */
    public void setXY(DungeonPixelCoordinate location) {
        mDungeonCoordinate = location;
        mScreenLocation = location.toScreen();
        mSprite.setXY(mScreenLocation.getX(), mScreenLocation.getY());
    }

    /**
     * Get the dungeon-fixed pixel location of the displayable.
     * 
     * @return DungeonPixelCoordinate
     */
    public DungeonPixelCoordinate getXY() {
        return mScreenLocation.toDungeon();
    }

    /**
     * Get the pixel coordinate on the screen for the item.
     * 
     * @return ScreenPixelCoordinate
     */
    public ScreenPixelCoordinate getLocation() {
        return mScreenLocation;
    }

    /**
     * React to an update in the observed item.
     * 
     * @param modelObject
     * @param updatedValue
     */
    public void update(Observable modelObject, Object updatedValue) {
        if (updatedValue instanceof DungeonPixelCoordinate) {
            setXY(((DungeonPixelCoordinate) updatedValue));
        } else {
            if (mItem.isPickedUp()) {
                mSprite.markForRemoval();
            }
        }
    }

    /**
     * Update the screen position of the Displayable.
     */
    public void onGameLoop() {
        if (mItem.isPickedUp()) {
            mSprite.markForRemoval();
        } else {
            mScreenLocation = mDungeonCoordinate.toScreen();
            mSprite.setXY(mScreenLocation.getX(), mScreenLocation.getY());
        }
    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to items.
     * 
     * @param newFacing
     *            Direction
     */
    public void startAttacking(Direction newFacing) {

    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to items.
     */
    public void stopAttacking() {

    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to items.
     * 
     * @param newFacing
     *            Direction
     */
    public void startTraveling(Direction newFacing) {

    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to items.
     */
    public void stopTraveling() {

    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to items.
     */
    public void noteDeath() {

    }

}
