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
 *
 */

package rogueandroid.cs428.sp10;

import rogueandroid.cs428.sp10.model.Armor;
import rogueandroid.cs428.sp10.model.Item;
import rogueandroid.cs428.sp10.model.Monster;
import rogueandroid.cs428.sp10.model.Potion;
import rogueandroid.cs428.sp10.model.Scroll;
import rogueandroid.cs428.sp10.model.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.Random;
import java.util.Set;

/**
 * This class provides set of functionalities/attributes that are required to
 * set up configurations of dungeon map. It also can decide which items and
 * monsters to distribute at dungeon map. It can generate randomly the offense
 * and defense value of monsters and capability of items such as increasing HP,
 * defense points, attack points, etc.
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
public class Config {

    /**
     * config values
     * 
     * These can be any data type, if not a primitive type create the data type
     * in the open tag handler IMPORTANT: provide default values in case parse
     * fails
     */
    private int tileSize = 0;
    private int screenXPixels = 0;
    private int screenYPixels = 0;
    private ArrayList<String> dungeonTextures = new ArrayList<String>();
    // use the flyweight design pattern for monsters - create a template and
    // replicate for each monster created
    private HashMap<String, Monster> monsters = new HashMap<String, Monster>();
    // items
    private HashMap<String, Item> items = new HashMap<String, Item>();
    // item options
    private static final int ARMOR = 0;
    private static final int WEAPON = 1;
    private static final int POTION = 2;
    private static final int SCROLL = 3;

    /**
     * Gets tile size
     * 
     * @return tileSize
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Sets tile size
     * 
     * @param extractedInt
     */
    public void setTileSize(int extractedInt) {
        tileSize = extractedInt;
    }

    /**
     * Gets screen pixel value of x-axis
     * 
     * @return screenXPixels
     */
    public int getScreenXPixels() {
        return screenXPixels;
    }

    /**
     * Sets screen pixel value of x-axis
     * 
     * @param extractedInt
     */
    public void setScreenXPixels(int extractedInt) {
        screenXPixels = extractedInt;
    }

    /**
     * Gets screen pixel value of y-axis
     * 
     * @return
     */
    public int getScreenYPixels() {
        return screenYPixels;
    }

    /**
     * Sets screen pixel value of y-axis
     * 
     * @param extractedInt
     */
    public void setScreenYPixels(int extractedInt) {
        screenYPixels = extractedInt;
    }

    /**
     * Gets item of corresponding name
     * 
     * @param name
     * @return new Item(items.get(name))
     */
    public Item getItem(String name) {
        return new Item(items.get(name));
    }

    /**
     * Gets random item
     * 
     * @return
     */
    public Item getRandomItem() {
        int nItems = items.size();
        Set<String> keys = items.keySet();
        Object sKeys[] = keys.toArray();
        Random generator = new Random();
        int number = generator.nextInt(nItems);
        return new Item(items.get(sKeys[number]));
    }

    /**
     * Decides/Sets capability of item For now it is only one of four choices,
     * but further development of game can apply more options. After it decides
     * the option, it sets random integer value in order to give quality of
     * capability such as whether the potion gives lots of HP or small amount of
     * HP
     * 
     * @param extractedString
     */
    public void setItem(String extractedString) {
        // parse string into Monster class
        Item i = null;
        String[] string = extractedString.split(",");
        switch (Integer.parseInt(string[0].trim())) {
        case ARMOR:
            i = new Armor();
            break;
        case WEAPON:
            i = new Weapon();
            break;
        case POTION:
            i = new Potion();
            break;
        case SCROLL:
            i = new Scroll();
            break;
        }
        i.setName(string[1].trim());
        i.setWeight(Integer.parseInt(string[2].trim()));
        i.setAttackBonus(Integer.parseInt(string[3].trim()));
        i.setDefenseBonus(Integer.parseInt(string[4].trim()));
        i.setHitPointBonus(Integer.parseInt(string[5].trim()));
        i.setResource(string[6].trim());
        items.put(i.getName(), i);
    }

    /**
     * Gets a monster of corresponding name
     * 
     * @param monsterName
     * @return
     */
    public Monster getMonster(String monsterName) {
        Monster m = null;
        if (monsters.containsKey(monsterName)) m = monsters.get(monsterName);
        return (m);
    }

    /**
     * Gets random monster
     * 
     * @return
     */
    public Monster getRandomMonster() {
        int nMonsters = monsters.size();
        Set<String> keys = monsters.keySet();
        Object sKeys[] = keys.toArray();
        Random generator = new Random();
        int number = generator.nextInt(nMonsters);
        return (new Monster(monsters.get(sKeys[number])));
    }

    /**
     * Applies various attributes to given monster. Provides attack bonus,
     * defense bonus, HP, speed, etc.
     * 
     * @param extractedString
     */
    public void setMonster(String extractedString) {
        // parse string into Monster class
        Monster m = new Monster();
        String[] string = extractedString.split(",");
        m.setName(string[0].trim());
        m.setAttackBonus(Integer.parseInt(string[1].trim()));
        m.setDefenseBonus(Integer.parseInt(string[2].trim()));
        m.setMaxHitPoints(Integer.parseInt(string[3].trim()));
        m.setCurrentHitPoints(m.getMaxHitPoints());
        m.setSpeed(Integer.parseInt(string[4].trim()));
        m.setResource(string[5].trim());
        monsters.put(m.getName(), m);
    }

    /**
     * Gets dungeon textures
     * 
     * @return dungeonTextures
     */
    public ArrayList<String> getDungeonTextures() {
        return dungeonTextures;
    }

    /**
     * Sets dungeon textures
     * 
     * @param extractedString
     */
    public void setDungeonTextures(String extractedString) {
        dungeonTextures.add(extractedString);
    }

    /**
     * Logging
     * 
     */
    private static Logger logger = Logger.getLogger("RogueAndroidLog");

    public static Logger log() {
        return logger;
    }
}