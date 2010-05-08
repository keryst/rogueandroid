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
import java.util.List;
import java.util.Observable;
import java.util.Random;

import rogueandroid.cs428.sp10.DungeonTileCoordinate;
import rogueandroid.cs428.sp10.Game;

import com.stickycoding.Rokon.Debug;

/** 
 * The Dungeon is a model class that stores the floor plan 
 * and Character location in tile coordinates.
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
public class Dungeon extends Observable {

    private DungeonTileCoordinate mCharacterTileLocation;

    public Integer[][] tiles;
    public boolean [][] blocked;
    public Integer [][] entityLocations;
    private int mNumberTilesX;
    private int mNumberTilesY;

    private List<Entity> mEntities = new ArrayList<Entity>();
    private List<Item> mItems = new ArrayList<Item>();

    static private final boolean LOCAL_LOGGING = false;

    /**
     * Constructor for Dungeon
     * @param dungeonArr Integer double array that contains the tileMap
     */
    public Dungeon(Integer[][] dungeonArr) {
        tiles = dungeonArr;
        mNumberTilesX = tiles.length;
        mNumberTilesY = tiles[0].length;

        blocked = new boolean[mNumberTilesX][mNumberTilesY];
        entityLocations = new Integer[mNumberTilesX][mNumberTilesY];
        createBlockedArray();
    }

    /**
     * Creates the Blocked integer array from the tileMap
     */
    private void createBlockedArray() {
        for ( int i = 0; i < mNumberTilesX; i++ ) {
            for ( int j = 0; j < mNumberTilesY; j++ ) {
                if (tiles[i][j] == 1){
                    blocked[i][j] =  true;
                }else{
                    blocked[i][j] =  false;
                }
            }
        }
    }

    /**
     * Get number of x tiles in Dungeon
     * @return int
     */
    public int getNumberTilesX() {
        return mNumberTilesX;
    }

    /**
     * Get number of y tiles in Dungeon
     * @return int
     */
    public int getNumberTilesY() {
        return mNumberTilesY;
    }

    /**
     * Looks for a random not blocked tile
     * @param generator Random
     * @return DungeonTileCoordinate 
     */
    public DungeonTileCoordinate randomUnobstructedTile(Random generator) {
        int x;
        int y;
        DungeonTileCoordinate coord;
        do {
            x = generator.nextInt(mNumberTilesX);
            y = generator.nextInt(mNumberTilesY);
            coord = new DungeonTileCoordinate(x, y);
        } while (isObstructed(coord));
        return coord;
    }

    /**
     * Set the Character's location.
     * @param DungeonTileCoordinate
     */
    public void setCharacterLocation(DungeonTileCoordinate tileLocation) {
        if (mCharacterTileLocation != null){
            entityLocations[mCharacterTileLocation.getX()]
                            [mCharacterTileLocation.getY()] = 0;
        }

        mCharacterTileLocation = tileLocation;
        entityLocations[tileLocation.getX()][tileLocation.getY()] = 1;
    }

    /**
     * Return the Character's current tile location.
     * @return DungeonTileCoordinate
     */
    public DungeonTileCoordinate getCharacterLocation() {
        return mCharacterTileLocation;

    }

    /**
     * Return the Entity matrix.
     * @return Integer[][]
     */
    public Integer[][] getEntityLocations() {
        return entityLocations;
    }

    /**
     * Returns entity located at tile location if any
     * @return int
     */
    public int entityLocatedOnTile(DungeonTileCoordinate tileLocation) {
        Integer possibleEntity = entityLocations[tileLocation.getX()][tileLocation.getY()];
        return possibleEntity;
    }

    /**
     * Adds an Entity to the dungeon
     * @param anEntity
     */
    public void registerEntity(Entity anEntity) {
        mEntities.add(anEntity);
    }

    /**
     * Returns a character
     * @return Character
     */
    public Entity getCharacter() {
        for (Entity e : mEntities) {
            if (e instanceof Character) {
                return e;
            }
        }
        return null;
    }

    /**
     * This returns the Entity located at <code>location</code>
     * @param location DungeonTileCoordinate
     * @return Entity located at location, or null
     */
    public Entity getEntityAt(DungeonTileCoordinate location) {
        for (Entity e : mEntities) {
            if (location.equals(e.getTileLocation())) {
                return e;
            }
        }
        return null;
    }

    /**
     * Runs on the gameloop
     */
    public void onGameLoop() {
        List<Entity> deadList = new ArrayList<Entity>();
        for (Entity e : mEntities) {
            if (e.isDead()) {
                if (LOCAL_LOGGING) {
                    Debug.print("Dungeon removing dead entity" +
                                                    " from " +
                                                    e.getTileLocation().
                                                    toString());
                }
                deadList.add(e);
            } else {
                e.onGameLoop();
            }
        }
        mEntities.removeAll(deadList);
    }

    /**
     * Returns <code>true</code> if the specified <code>location</code> is 
     * blocked (not passable).
     * @param location DungeonTileCoordinate
     * @return boolean
     */
    public boolean isBlocked(DungeonTileCoordinate location) {
        if (location.getX() < 0 || location.getX() >= mNumberTilesX) return true;
        if (location.getY() < 0 || location.getY() >= mNumberTilesY) return true;
        return blocked[location.getX()][location.getY()];
    }

    /**
     * Returns <code>true</code> if the specified <code>location</code> is 
     * either blocked or occupied.
     * @param location DungeonTileCoordinate
     * @return boolean
     */
    public boolean isObstructed(DungeonTileCoordinate location) {
        return isBlocked(location) || (getEntityAt(location) != null);
    }

    /**
     * Add Item to dungeon
     * @param item Item to be added
     */
    public void registerItem(Item item) {
        mItems.add(item);
    }

    /**
     * Gets an item at a specific location
     * @param location DungeonTileCoordinate
     * @return Item at location
     */
    public Item getItemAt(DungeonTileCoordinate location) {
        Item i = null;
        for (int loop = 0; loop < mItems.size(); loop++) {
            if (LOCAL_LOGGING) {
                Debug.print("item location in getItemAt loop " 
                                                + mItems.get(loop).
                                                getLocation().toString());
            }
            if (location.getX() == mItems.get(loop).getLocation().getX()
                                            && location.getY() == 
                                                mItems.get(loop).getLocation().
                                                getY())
            {
                if (LOCAL_LOGGING) {
                    Debug.print("found item location in getItemAt loop " +
                                                    mItems.get(loop).
                                                    getLocation().toString());
                }
                return (mItems.get(loop));
            }
        }
        return i;
    }

    /**
     * Removes the item at location
     * @param location DungeonTileCoordiate location of item
     */
    public void removeItemAt(DungeonTileCoordinate location) {
        for (int loop = 0; loop < mItems.size(); loop++) {
            if (LOCAL_LOGGING) {
                Debug.print("removeItemAt loop " + 
                                                mItems.get(loop).getLocation().
                                                toString());
            }
            if (location.getX() == mItems.get(loop).getLocation().getX() && 
                                            location.getY() == mItems.get(loop).
                                            getLocation().getY()) {
                Game.config().log().severe("pickup item at " + mItems.get(loop).
                                                getLocation().getX() + " " + 
                                                mItems.get(loop).getLocation().
                                                getY());	
                mItems.get(loop).pickUp(true);
                break;
            }
        }
    }
}