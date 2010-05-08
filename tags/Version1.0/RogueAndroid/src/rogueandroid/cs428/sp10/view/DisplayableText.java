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

import com.stickycoding.Rokon.Font;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Text;
import com.stickycoding.Rokon.TextureAtlas;

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.DungeonPixelCoordinate;
import rogueandroid.cs428.sp10.ScreenPixelCoordinate;

import java.util.Observable;
import java.util.Observer;

/**
 * DisplayableText represents textual information that exists on the display.
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
public class DisplayableText implements Displayable, Observer {

    private ScreenPixelCoordinate mScreenLocation;
    private DungeonPixelCoordinate mDungeonCoordinate;
    private Text mText;
    private String mString;
    private Font mFont;

    final static private int DEFAULT_SCALE = 50;

    /**
     * Create a displayable for displaying text.
     * 
     * @param atlas
     * @param rokon
     * @param string
     * @param font
     */
    public DisplayableText(TextureAtlas atlas, Rokon rokon, String string, Font font) {
        mString = string;
        mFont = font;
        createText(rokon);
    }

    /**
     * Create the text using the Rokon text class.
     * 
     * @param rokon
     */
    public void createText(Rokon rokon) {
        mText = new Text(mString, mFont, 0, 0, DEFAULT_SCALE);

        rokon.addText(mText);
        mText.setVisible(true);
    }

    /**
     * Set the location of the Displayable in screen pixel coordinates.
     * 
     * @param location
     *            screen-fixed pixel location
     */
    public void setXY(ScreenPixelCoordinate location) {
        mScreenLocation = location;
        mText.setXY(mScreenLocation.getX(), mScreenLocation.getY());
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
     * Get the pixel coordinate on the screen for the text.
     * 
     * @return ScreenPixelCoordinate
     */
    public ScreenPixelCoordinate getScreenPixelLocation() {
        return mScreenLocation;
    }

    /**
     * Get the scale.
     * 
     * @return int
     */
    public int getScale() {
        return mText.getScale();
    }

    /**
     * Get the text.
     * 
     * @return String
     */
    public String getText() {
        return mText.getText();
    }

    /**
     * Get the pixel coordinate on the screen for the DisplayableText.
     * 
     * @return ScreenPixelCoordinate
     */
    public ScreenPixelCoordinate getLocation() {
        return mScreenLocation;
    }

    /**
     * React to an update in the observed object.
     * 
     * @param observable
     * @param updatedValue
     */
    public void update(Observable observable, Object updatedValue) {

        if (updatedValue instanceof Integer) {
            String string = Integer.toString((Integer) updatedValue);
            setText(string);
        }

    }

    /**
     * Update the screen position of the Displayable.
     */
    public void onGameLoop() {
        mScreenLocation = mDungeonCoordinate.toScreen();
        mText.setXY(mScreenLocation.getX(), mScreenLocation.getY());
    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to text.
     * 
     * @param newFacing
     *            Direction
     */
    public void startAttacking(Direction newFacing) {

    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to text.
     */
    public void stopAttacking() {

    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to text.
     * 
     * @param newFacing
     *            Direction
     */
    public void startTraveling(Direction newFacing) {

    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to text.
     */
    public void stopTraveling() {

    }

    /**
     * Set the value of the displayed text.
     * 
     * @param string
     */
    public void setText(String string) {
        mText.setText(string);
    }

    /**
     * Set the color of the displayed text.
     * 
     * @param red
     * @param green
     * @param blue
     * @param alpha
     */
    public void setColor(int red, int green, int blue, int alpha) {
        mText.setColor(red, green, blue, alpha);
    }

    /**
     * Set the scale of the displayed text.
     * 
     * @param scale
     */
    public void setScale(int scale) {
        mText.setScale(scale);
    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to text.
     * 
     * @param location
     *            dungeon-fixed pixel location
     */
    public void setXY(DungeonPixelCoordinate location) {

    }

    /**
     * Perform no action because this interface-defined behavior is not
     * applicable to text.
     */
    public void noteDeath() {

    }

}
