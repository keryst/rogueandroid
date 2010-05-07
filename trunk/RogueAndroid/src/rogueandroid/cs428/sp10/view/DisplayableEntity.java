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

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.ScreenPixelCoordinate;
import rogueandroid.cs428.sp10.model.Entity;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * The DisplayableEntity class implements Displayable and represents a common
 * Entity display controller. This class is responsible to rendering the entity
 * on the display, moving it accordingly, and animating it as needed.
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
public class DisplayableEntity implements Displayable, Observer {

    private Sprite mSprite;
    private Texture mTexture;
    private ScreenPixelCoordinate mScreenLocation;
    private DungeonPixelCoordinate mDungeonCoordinate;
    private Entity mEntity;

    final static private int DEFAULT_ATLAS_X = 1024;
    final static private int DEFAULT_ATLAS_Y = 2048;
    final static private int FRAME_DELAY_MILLISECONDS = 5;

    static private final boolean LOCAL_LOGGING = false;

    /**
     * Create a new entity Displayable from a pre-existing TextureAtlas.
     * 
     * @param theEntity
     * @param atlas
     * @param rokon
     * @param tileSize
     * @param assetPath
     */
    public DisplayableEntity(Entity theEntity, TextureAtlas atlas, Rokon rokon, int tileSize,
                                    String assetPath) {
        mEntity = theEntity;
        loadTextures(atlas, tileSize, assetPath);
        createSprite(rokon, tileSize);
        if (mEntity != null) {
            this.setXY(mEntity.getPixelLocation());
        }
    }

    /**
     * Create a new entity Displayable from a pre-existing TextureAtlas stored
     * in a Hash map.
     * 
     * @param theEntity
     * @param atlasMap
     * @param rokon
     * @param tileSize
     * @param assetPath
     */
    public DisplayableEntity(Entity theEntity, HashMap<String, TextureAtlas> atlasMap, Rokon rokon,
                                    int tileSize, String assetPath) {
        mEntity = theEntity;
        loadTextures(atlasMap, tileSize, assetPath);
        createSprite(rokon, tileSize);
        if (mEntity != null) {
            this.setXY(mEntity.getPixelLocation());
        }
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
     * Get the pixel coordinate on the screen for the displayable.
     * 
     * @return ScreenPixelCoordinate
     */
    public ScreenPixelCoordinate getLocation() {
        return mScreenLocation;
    }

    /**
     * React to an update in the observed entity.
     * 
     * @param modelObject
     * @param updatedValue
     */
    public void update(Observable modelObject, Object updatedValue) {
        // This routine is no longer needed now that the Model/View dependency
        // has been reversed between entities and their displayables.
    }

    /**
     * Stop the animation.
     */
    private void stopAnimation() {
        mSprite.stopAnimation();
    }

    /**
     * Update the screen position of the Displayable.
     */
    public void onGameLoop() {
        mScreenLocation = mDungeonCoordinate.toScreen();
        mSprite.setXY(mScreenLocation.getX(), mScreenLocation.getY());
    }

    /**
     * Signal the displayable to start displaying attacking.
     * 
     * @param newFacing
     *            Direction
     */
    public void startAttacking(Direction newFacing) {
        AttackAnimationFrameSequence animation = new AttackAnimationFrameSequence(newFacing);
        startAnimation(animation);
    }

    /**
     * Signal the displayable to stop displaying attacking.
     */
    public void stopAttacking() {
        stopAnimation();
    }

    /**
     * Signal the displayable to start displaying traveling.
     * 
     * @param newFacing
     *            Direction
     */
    public void startTraveling(Direction newFacing) {
        TravelAnimationFrameSequence animation = new TravelAnimationFrameSequence(newFacing);
        startAnimation(animation);
    }

    /**
     * Signal the displayable to stop displaying traveling.
     */
    public void stopTraveling() {
        stopAnimation();
    }

    /**
     * Start the animation.
     * 
     * @param animation
     */
    public void startAnimation(AnimationFrameSequence animation) {
        mSprite.animate(animation.getAnimationStartFrame(), animation.getAnimationStopFrame(),
                                        FRAME_DELAY_MILLISECONDS);
    }

    /**
     * Signal that the associated entity has passed away and the displayable
     * should clean itself up.
     */
    public void noteDeath() {
        if (LOCAL_LOGGING) {
            Debug.print("DisplayableEntity marking sprite for removal.");
        }
        mSprite.markForRemoval();
    }

    /**
     * Return the sprite.
     * 
     * @return the mSprite
     */
    public Sprite getSprite() {
        return mSprite;
    }

    /**
     * Return the texture.
     * 
     * @return the mTexture
     */
    public Texture getTexture() {
        return mTexture;
    }

}