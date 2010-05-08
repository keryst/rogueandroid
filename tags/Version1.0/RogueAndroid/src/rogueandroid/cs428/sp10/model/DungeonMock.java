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

package rogueandroid.cs428.sp10.model;

import java.util.ArrayList;

/** 
 * Dungeon Mock for unit testing purposes
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
public class DungeonMock {

    public Integer [][] tiles = 
    { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                                    { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }};

    // the block array shows impassable areas.  We can use this value as a bitmask on the tiles later.
    // for now, impassable areas will be tinted red 
    public boolean [][] blocked = { 
                                    { true, true, true, true, true, true, true, true, true, true, true, false, true, true, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { false, false, false, false, false, false, false, false, false, false, false, false, false, false, false },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, false, false, false, false, false, false, false, false, false, false, false, false, false, true },
                                    { true, true, true, true, true, true, true, true, true, true, true, false, true, true, true } };

    public ArrayList<String> mTextureList = new ArrayList<String>();

    private int numTilesX;
    private int numTilesY;;

    /**
     * Constructor for DungeonMock
     */
    public DungeonMock() {
        mTextureList.add("graphics/sprites/se_free_grass_texture.bmp");
        mTextureList.add("graphics/sprites/se_free_road_texture.bmp");
        mTextureList.add("graphics/sprites/se_free_dirt_texture.bmp");

        numTilesX = tiles.length;
        numTilesY = tiles[0].length;
    }

    /**
     * Get number of x tiles in Dungeon
     * @return int
     */
    public int getNumTilesX() {
        return numTilesX;
    }

    /**
     * Get number of y tiles in Dungeon
     * @return int
     */
    public int getNumTilesY() {
        return numTilesY;
    }
}