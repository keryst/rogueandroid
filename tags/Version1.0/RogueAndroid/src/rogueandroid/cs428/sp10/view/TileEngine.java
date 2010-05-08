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

import rogueandroid.cs428.sp10.BoundingBox;

/**
 * The TileEngine is an abstract interface describing common functionality for
 * tile maps.
 * 
 * A note on nomenclature: i and j are used to denote tile coordinates x and y
 * are used to denote screen pixel coordinates (on the 480x854 pixel Droid
 * screen, for example)
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
public interface TileEngine {

    /**
     * Load textures into an initialized tile engine. Each texture is identified
     * with a number. Call repeatedly, once for each texture. There is no rules
     * on how the ids can be used, but they must all be unique.
     * 
     * @param id
     *            A numeric id which will be used per tile to determine the
     *            image to draw there
     * @param assetPath
     *            A file path to the image to be drawn. Images must be 128x128
     *            pixels.
     */
    public void setTexture(Integer id, String assetPath);

    /**
     * Set the texture of a particular tile in the initialized tile engine. Do
     * this after the textures have all been loaded. Call repeatedly, once for
     * each tile on the map.
     * 
     * @param i
     *            The i-coordinate of the tile. The column. (not a pixel
     *            coordinate!)
     * @param j
     *            The j-coordinate of the tile. The row. (not a pixel
     *            coordinate!)
     * @param textureId
     *            The id of the texture to draw for this tile.
     */
    public void setTile(int i, int j, Integer textureId);

    /**
     * Set the blocking information of a particular tile.
     * 
     * @param i
     *            The i-coordinate of the tile. The column. (not a pixel
     *            coordinate!)
     * @param j
     *            The j-coordinate of the tile. The row. (not a pixel
     *            coordinate!)
     * @param blocked
     *            True if the given tile is considered impassable to the
     *            player.
     */
    public void setTileBlocking(int i, int j, boolean blocked);

    /**
     * Get the screen pixel x-coordinate that shows where the left edge of the
     * given tile coordinate lies.
     * 
     * @param tileICoordinate
     *            The i-coordinate of the tile. The column. (not a pixel
     *            coordinate!)
     * @return The screen pixel x location. This location can be off screen, or
     *         negative.
     */
    public int getScreenX(int tileICoordinate);

    /**
     * Get the screen pixel y-coordinate that shows where the left edge of the
     * given tile coordinate lies.
     * 
     * @param tileJCoordinate
     *            The j-coordinate of the tile. The column. (not a pixel
     *            coordinate!)
     * @return int The screen pixel y location. This location can be off screen,
     *         or negative.
     */
    public int getScreenY(int tileJCoordinate);

    /**
     * Get the number of tiles that are mapped in the i direction.
     * 
     * @return The number of tiles in i.
     */
    public int getNumTilesI();

    /**
     * Get the number of tiles that are mapped in the j direction.
     * 
     * @return The number of tiles in j.
     */
    public int getNumTilesJ();

    /**
     * Sets the background of the Rokon Engine
     */
    public void setBackground();

    /**
     * Scroll the background tiles by the number of pixels given. Assume the
     * given values are always legal For example, providing an x value of 5 will
     * move the entire map 5 pixels in the +x direction.
     * 
     * @param x
     *            The number of pixels in the x-direction to move the map. Can
     *            be negative, 0, or positive.
     * @param y
     *            The number of pixels in the y-direction to move the map. Can
     *            be negative, 0, or positive.
     */
    public void scroll(int x, int y);

    /**
     * Get the current size and location of the tile map in pixels. Some of the
     * returned coordinates will probably be off the screen. For efficiency
     * sake, it would be best to call once and cache the resultant values.
     * 
     * @return A bounding box object representing the bounds of the entire tile
     *         map.
     */
    public BoundingBox getBoundingBox();
}