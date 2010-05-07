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

import rogueandroid.cs428.sp10.controller.Controller;
import rogueandroid.cs428.sp10.model.Character;
import rogueandroid.cs428.sp10.model.Dungeon;
import rogueandroid.cs428.sp10.model.Item;
import rogueandroid.cs428.sp10.model.Monster;
import rogueandroid.cs428.sp10.model.UseAction;
import rogueandroid.cs428.sp10.view.DisplayableEntity;
import rogueandroid.cs428.sp10.view.DisplayableItem;
import rogueandroid.cs428.sp10.view.DisplayableText;
import rogueandroid.cs428.sp10.view.TileEngine;
import rogueandroid.cs428.sp10.view.TileEngineBackground;
import rogueandroid.cs428.sp10.view.View;
import rogueandroid.cs428.sp10.view.ViewGameOver;

import android.content.Intent;
import android.util.Log;

import com.stickycoding.Rokon.Font;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;

import java.io.FileInputStream;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Game is the main class for RogueAndorid. It extends Rokon Activity and
 * creates the rokon game engine
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
public class Game extends RokonActivity {
    public Dungeon dungeon;
    public TileEngine tileEngine;
    public TextureAtlas charAtlas; // Atlas for Main Character
    public TextureAtlas textAtlas; // Atlas for Screen text
    public HashMap<String, TextureAtlas> monsterAtlasMap = new HashMap<String, TextureAtlas>(); // Monster
    // Atlas
    // HashMap
    public int sTextures = MONSTERS + 2;
    public Texture[] textures = new Texture[sTextures];

    private static final int ITEMS = 10;
    private static final int STARTING_INVENTORY = 5;
    public Item[] item = new Item[ITEMS];
    public DisplayableItem[] itemDisp = new DisplayableItem[ITEMS];
    public HashMap<String, TextureAtlas> itemsAtlasMap = new HashMap<String, TextureAtlas>(); // Items
    // Atlas
    // HashMap
    private TextureAtlas mItemAtlas;

    public Character character;
    private static final int MONSTERS = 10;
    public Monster[] monster = new Monster[MONSTERS];
    public DisplayableEntity[] monsterDisp = new DisplayableEntity[MONSTERS];
    public DisplayableEntity dispChar;

    private Controller mController;
    private View mView;
    private DisplayableText mDispCharHP;
    private DisplayableText mDispHP;
    private DisplayableText mDispCharMaxHP;

    private DungeonReader mDungeonReader;
    public Monster mBoss;
    private DisplayableEntity mDispBoss;

    // Game states
    private int mMode = RUNNING;
    private ViewGameOver mGameOver;
    private Font mFont;
    private Intent mIntent;
    public static final int PAUSE = 0;
    public static final int RUNNING = 1;
    public static final int LOSE = 2;
    public static final int WIN = 3;

    /** The Method grabs the game settings and creates the Rokon engine 
     * 
     * @param none
     * @return none
     * */
    public void onCreate() {
        createConfig();
        createEngine("graphics/loading.png", Game.config().getScreenYPixels(), Game.config()
                                        .getScreenXPixels(), false);
    }

    /** 
     * The Method loads textures and sprites into the Rokon engine 
     * 
     * @param none
     * @return none
     * */
    @Override
    public void onLoad() {
        mDungeonReader = new DungeonReader(getResources().openRawResource(R.raw.dungeon));

        createTextureAtlases();

        setupFont();

        dungeon = new Dungeon(mDungeonReader.getDungeonFloorplan());

        tileEngine = new TileEngineBackground(rokon, Game.config().getTileSize(), Game.config()
                                        .getDungeonTextures(), dungeon.getNumberTilesX(), dungeon
                                        .getNumberTilesY());

        int screenYPixels = Game.config().getScreenYPixels();
        int screenXPixels = Game.config().getScreenXPixels();
        mView = new View(dungeon, tileEngine, screenXPixels, screenYPixels);

        Coordinate.setTileSize(Game.config().getTileSize());

        String texture = this.ReadCharacterSettings();

        // populate items
        Random generator = new Random();
        generateItems(generator);

        // populate with monsters
        generateMonsters(generator);

        setupBoss();

        setupCharacter(texture);
        mController = new Controller(character, dungeon, mView);

        setupHPDisplay();
        character.addObserver(mDispCharHP);

        loadTextureAtlases();

        loadTileEngine();

    }

    /**
     * Loads the tiles into the tile engine to create the map background
     * 
     * @param none
     * @return none
     * */
    private void loadTileEngine() {
        int numX = dungeon.tiles.length;
        int numY = dungeon.tiles[0].length;
        for (int i = 0; i < numX; i++) {
            for (int j = 0; j < numY; j++) {
                tileEngine.setTile(i, j, dungeon.tiles[i][j]);
                tileEngine.setTileBlocking(i, j, dungeon.blocked[i][j]);
            }
        }
    }

    /**
     * Loads the texture atlases into the texture manager for rendering
     *
     * @param none
     * @return none
     */
    private void loadTextureAtlases() {
        TextureManager.load(charAtlas);
        TextureManager.load(textAtlas);
        TextureManager.load(mItemAtlas);

        for (TextureAtlas tempAtlas : monsterAtlasMap.values())
            TextureManager.load(tempAtlas);
    }

    /**
     * Creates the texture atlases used for the character, monsters, and text
     *
     * @param none
     * @return none
     */
    private void createTextureAtlases() {
        // TODO: Make sure we optimize the atlases
        charAtlas = new TextureAtlas(1024, 2048);
        textAtlas = new TextureAtlas(512, 512);
        mItemAtlas = new TextureAtlas(512, 512);
    }

    /**
     * generates monsters and places them randomly on the dungeon
     * 
     * @param generator
     * 			generator is a random used to seed the MonsterMaker
     * @return none
     */
    private void generateMonsters(Random generator) {
        for (int loop = 0; loop < MONSTERS; loop++) {
            DungeonTileCoordinate monsterStartTile = dungeon.randomUnobstructedTile(generator);
            DungeonPixelCoordinate monsterStart = monsterStartTile.toDungeonPixel();
            monster[loop] = MonsterMaker.make(dungeon, monsterStartTile, null);
            monsterDisp[loop] = new DisplayableEntity(monster[loop], monsterAtlasMap, rokon, Game
                                            .config().getTileSize(), monster[loop].mResource);
            monsterDisp[loop].setXY(monsterStart); // TODO: have the display
            // created with its entity,
            // and ask for its coord
            monster[loop].addObserver(monsterDisp[loop]);
            monster[loop].setDisplayable(monsterDisp[loop]);
            mView.addDisplayable(monsterDisp[loop]);
        }
    }

    /**
     * generates items and places them randomly on the dungeon
     * 
     * @param generator
     * 			generator is a random used to seed the ItemMaker
     * @return none
     */
    private void generateItems(Random generator) {
        for (int loop = 0; loop < ITEMS; loop++) {
            DungeonTileCoordinate itemStartTile = dungeon.randomUnobstructedTile(generator);
            DungeonPixelCoordinate itemStart = itemStartTile.toDungeonPixel();
            item[loop] = ItemMaker.make(itemStartTile, null);
            item[loop].registerItem(dungeon);
            itemDisp[loop] = new DisplayableItem(item[loop], mItemAtlas, rokon, Game.config()
                                            .getTileSize(), item[loop].getResource());
            itemDisp[loop].setXY(itemStart);
            mView.addDisplayable(itemDisp[loop]);
        }
    }

    /**
     * sets up the character to be rendered at a specific DTC
     * 
     * @param texture
     * 			texture is the asset used to render the character
     */
    private void setupCharacter(String texture) {
        DungeonTileCoordinate characterTileStart = new DungeonTileCoordinate(3, 3);
        character = new Character(dungeon, characterTileStart);
        // TODO: make character completely data driven
        DungeonPixelCoordinate characterPixelStart = characterTileStart.toDungeonPixel();
        character.setLocation(characterTileStart, characterPixelStart);
        dispChar = new DisplayableEntity(character, charAtlas, rokon, Game.config().getTileSize(),
                                        texture);
        dispChar.setXY(characterPixelStart);

        character.setDisplayable(dispChar);

        mView.addDisplayable(dispChar);
        character.addObserver(dispChar);

        // give the character a few starting items
        DungeonTileCoordinate dtc = new DungeonTileCoordinate(0, 0);
        for (int loop = 0; loop < STARTING_INVENTORY; loop++) {
            Item aItem = ItemMaker.make(dtc, null);
            character.getInventory().insertItem(aItem);
        }

        // auto-use what the character starts with
        for (int loop = 0; loop < character.getInventory().size(); loop++) {
            UseAction aUse = new UseAction(character, character.getInventory().getItem(loop));
            character.queueAction(aUse);
        }
    }

    /**
     * creates the boss entity and places on the Dungeon
     *
     * @param none
     * @return none
     */
    private void setupBoss() {
        DungeonTileCoordinate bossTileStart = new DungeonTileCoordinate(17, 25);
        // dungeon.randomUnobstructedTile(generator);
        DungeonPixelCoordinate bossPixelStart = bossTileStart.toDungeonPixel();
        mBoss = MonsterMaker.make(dungeon, bossTileStart, "boss");
        mDispBoss = new DisplayableEntity(mBoss, monsterAtlasMap, rokon, 150, mBoss.mResource);
        mDispBoss.setXY(bossPixelStart);
        mBoss.setDisplayable(mDispBoss);

        mBoss.addObserver(mDispBoss);
    }

    /**
     * renders the character's hitpoints on the screen using a DisplayableText
     * Entity
     *
     * @param none
     * @return none
     */
    private void setupHPDisplay() {
        ScreenPixelCoordinate hpStart = new ScreenPixelCoordinate(0, 0);
        ScreenPixelCoordinate charHPStart = new ScreenPixelCoordinate(50, 0);
        ScreenPixelCoordinate charMaxHPStart = new ScreenPixelCoordinate(100, 0);
        String hpString = "HP: ";
        String charHPString = Integer.toString(character.getHitPoints());
        String charMaxHPString = Integer.toString(character.getMaxHitPoints());
        mDispHP = new DisplayableText(textAtlas, rokon, hpString, mFont);
        mDispCharHP = new DisplayableText(textAtlas, rokon, charHPString, mFont);
        mDispCharMaxHP = new DisplayableText(textAtlas, rokon, charMaxHPString, mFont);
        mDispHP.setXY(hpStart);
        mDispCharHP.setXY(charHPStart);
        mDispCharMaxHP.setXY(charMaxHPStart);
        mDispCharHP.setColor(1, 1, 0, 1);
    }

    /**
     * Return the game's MVC Controller class instance.
     * 
     * @return Controller
     * @return none
     */
    public Controller getController() {
        return mController;
    }

    /**
     * Sets the tile background
     *
     * @param none
     * @return none
     */
    @Override
    public void onLoadComplete() {
        tileEngine.setBackground();
    }

    /**
     * Main Game Loop
     *
     * @param none
     * @return none
     */
    @Override
    public void onGameLoop() {
        if (mMode == RUNNING) {
            // Call the main Model class to process the game simulation.
            dungeon.onGameLoop();

            // Call the View to update the screen.
            mView.onGameLoop();

            if (mBoss.isDead()) {
                setMode(WIN);
            }

            if (character.isDead()) {
                setMode(LOSE);
            }
        }

    }

    /**
     * Clean Up
     *
     * @param none
     * @return none
     */
    public void onDestroy() {
        super.onDestroy();

    }

    /**
     * Garbage Collection to prevent memory leaks
     *
     * @param none
     * @return none
     */
    public void shutdownGame() {
        TextureManager.remove(charAtlas);
        TextureManager.remove(textAtlas);
        TextureManager.remove(mItemAtlas);

        for (TextureAtlas tempAtlas : monsterAtlasMap.values())
            TextureManager.remove(tempAtlas);

        mView.scrollScreen(2000, 2000);// reset scroll

        mIntent = new Intent(this, ChooseCharacterClass.class);
        startActivity(mIntent);
    }

    /**
     *Sets the mode of the Game
     * 
     * @param newMode
     * 		 	newMode is an integer that indicates win or lose
     * @return none
     */
    public void setMode(int newMode) {
        mMode = newMode;
        if (mMode == WIN || mMode == LOSE) {
            mGameOver = new ViewGameOver(textAtlas, rokon, mFont);
        }

    }
    
    /**
     *gets the current mode of the game
     * 
     * @param none
     * @return mMode
     */
    public int getMode() {
        return mMode;
    }

    /**
     * Load the font onto its own texture atlas
     *
     * @param none
     * @return none
     */
    public void setupFont() {
        textAtlas.insert(mFont = new Font("fonts/ARIAL.TTF"));
        mFont.setTextureAtlas(textAtlas);
    }

    public int userTouchDownX = 0;
    public int userTouchDownY = 0;

    /**
     * The user just touched the screen. Store the coordinates of where they
     * touched.
     *
     * @param x
     * @param y
     * @param hotspot
     * @return none
     */
    public void onTouchDown(int x, int y, boolean hotspot) {
        userTouchDownX = x;
        userTouchDownY = y;
        if (mMode == RUNNING) {
            mController.onTouchDown(x, y, hotspot);
        } else if (mMode == WIN || mMode == LOSE) {
            shutdownGame();
        }
    }

    /**
     * The user is touching the screen. If they've 'swiped' far enough, scroll
     * the background. Once we start scrolling, don't check for swipe distance
     * anymore.
     * 
     * @param x
     * @param y
     * @param hotspot
     * @return none
     */
    public void onTouch(int x, int y, boolean hotspot) {
        mController.onTouch(x, y, hotspot);
    }

    /**
     * The user let go of the screen. If they 'selected' a tile, do some action.
     *
     * @param none
     * @return none
     */
    public void onTouchUp(int x, int y, boolean hotspot) {
        mController.onTouchUp(x, y, hotspot);
    }

    /**
     * Restarts the game
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
     * load the config file or use the defaults singleton pattern
     *
     * @param none
     * @return none
     */
    // configuration object
    private static int configResourceID = R.xml.config;
    private static Config config = null;

    /**
     * The config object that contains all of the game settings
     * 
     * @param none
     * @return Config
     */
    public static Config config() {
        return (config);
    }

    /**
     * create the config file or use the defaults singleton pattern
     *
     * @param none
     * @return none
     */
    private void createConfig() {
        // read config file
        if (config == null) {
            ConfigParse.parse(this, configResourceID);
            config = ConfigParse.getConfig();
        }
    }

    /**
     * Read charcacter settings from a textfile
     *
     * @param none
     * @return none
     */
    public String ReadCharacterSettings() {
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
        String mage = "Mage";
        String ranger = "Rang";
        String savedCharacter = data.substring(0, 4);
        Log.v("CharacterClass", savedCharacter);
        String texture;
        if (savedCharacter.equals(mage)) {
            texture = "graphics/sprites/mage.png";
        } else if (savedCharacter.equals(ranger)) {
            texture = "graphics/sprites/ranger.png";
        } else {
            texture = "graphics/sprites/blueknight.png";
        }

        return texture;
    }
}