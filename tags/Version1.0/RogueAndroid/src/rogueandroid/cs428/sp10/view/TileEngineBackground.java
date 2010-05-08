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
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Backgrounds.ScrollingTiledBackground;

import rogueandroid.cs428.sp10.BoundingBox;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TileEngineBacground implements the TileEngine interface and creates the
 * graphical representation of the map by setting the background in the Rokon
 * framework.
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
public class TileEngineBackground implements TileEngine {

    private int mNumTilesX;
    private int mNumTilesY;
    private Rokon mRokon;
    private int mTileSize;
    private HashMap<Integer, Texture> mTextures = new HashMap<Integer, Texture>();
    private int[][] mTiles;
    private Integer[][] mTilesBlocked;
    private ArrayList<String> mTextureList;
    private ScrollingTiledBackground mBackground;

    /**
     * Class constructor
     * 
     * @param rokon
     *            The Rokon engine.
     * @param tileSize
     *            The size of the tiles for the map.
     * @param textureList
     *            An ArrayList of the textures used for the tiles.
     * @param numTilesX
     *            The number of horizontal tiles on the map.
     * @param numTilesy
     *            The number of vertical tiles on the map
     */
    public TileEngineBackground(Rokon rokon, int tileSize, ArrayList<String> textureList,
                                    int numTilesX, int numTilesy) {
        mRokon = rokon;
        mTileSize = tileSize;
        mTextureList = textureList;
        mNumTilesX = numTilesX;
        mNumTilesY = numTilesy;
        mTiles = new int[mNumTilesX][mNumTilesY];
        mTilesBlocked = new Integer[mNumTilesX][mNumTilesY];

        setTextures();
    }

    public int getNumTilesI() {
        return mNumTilesX;
    }

    public int getNumTilesJ() {
        return mNumTilesY;
    }

    public int getScreenX(int tileICoordinate) {
        return (tileICoordinate * mTileSize) - 5; // cut off 5 pixels along the
        // long side
    }

    public int getScreenY(int tileJCoordinate) {
        return (tileJCoordinate * mTileSize);
    }

    /**
     * Set the textures and creates the instance of the background in the Rokon
     * framework.
     */
    public void setTextures() {
        int index = 0;
        for (String graphic : mTextureList) {
            setTexture(index, graphic);
            index++;
        }
        mBackground = new ScrollingTiledBackground(mTextures, mTiles, mTileSize, mTileSize);
    }

    /**
     * Scrolls the background.
     * 
     * @param offsetX
     *            offset in X
     * @param offsetY
     *            offset in Y
     */
    public void scroll(int offsetX, int offsetY) {
        mBackground.setScroll(offsetX, offsetY);
    }

    /**
     * Sets the texture for a tile on the map.
     * 
     * @param id
     *            interger index of texture
     * @param assetPath
     *            String of path to texture asset
     */
    public void setTexture(Integer id, String assetPath) {
        Texture texture = new Texture(assetPath);
        texture.setTileSize(128, 128);
        mTextures.put(id, texture);
    }

    /**
     * Sets the tile to the texture index.
     * 
     * @param i
     *            X coordinate
     * @param j
     *            Y coordinate
     * @param textureId
     *            integer index of texture
     */
    public void setTile(int i, int j, Integer textureId) {
        mTiles[i][j] = textureId;
    }

    /**
     * Sets the open and closed tiles on the map.
     * 
     * @param i
     *            X coordinate
     * @param j
     *            Y coordinate
     * @param blocked
     *            boolean blocked at tileMap index
     */
    public void setTileBlocking(int i, int j, boolean blocked) {
        mTilesBlocked[i][j] = blocked ? 1 : 0;
    }

    /**
     * Sets the background in Rokon.
     */
    public void setBackground() {
        mRokon.setBackground(mBackground);
    }

    /**
     * Creates and returns a BoundingBox object depending upon limits of
     * tileMap.
     * 
     * @return a BoundingBox object depending upon limits of tileMap
     */
    public BoundingBox getBoundingBox() {
        int minX, minY, maxX, maxY;

        minX = 0;
        minY = 0;
        maxX = (mTiles.length * mTileSize) - 1;
        maxY = (mTiles[0].length * mTileSize) - 1;

        return new BoundingBox(minX, minY, maxX, maxY);
    }
}