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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Handles behaviors of Config.java. Extends DefaultHandler
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

public class ConfigHandler extends DefaultHandler {

    // Tags and their meanings
    public static String TILESIZE = "TileSize";
    public static String SCREENXPIXELS = "ScreenXPixels";
    public static String SCREENYPIXELS = "ScreenYPixels";
    public static String DUNGEONTEXTURE = "DungeonTexture";

    private StringBuilder builder;

    /**
     * Config as a data set
     */
    private Config config = new Config();

    /**
     * all the work is done here
     * 
     */
    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (localName.equalsIgnoreCase(TILESIZE)) config.setTileSize(Integer.parseInt(builder
                                        .toString()));
        else if (localName.equalsIgnoreCase(SCREENXPIXELS)) config.setScreenXPixels(Integer
                                        .parseInt(builder.toString()));
        else if (localName.equalsIgnoreCase(SCREENYPIXELS)) config.setScreenYPixels(Integer
                                        .parseInt(builder.toString()));
        else if (localName.equalsIgnoreCase(DUNGEONTEXTURE)) config.setScreenYPixels(Integer
                                        .parseInt(builder.toString()));
        else
        ;
        // clear builder
        builder.setLength(0);
    }

    /**
     * Returns parsed data
     * 
     * @return this.config
     */
    public Config getParsedData() {
        return this.config;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        this.config = new Config();
        builder = new StringBuilder();
    }

    /**
     * If need to create new objects in config do it here
     */
    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes)
                                    throws SAXException {
        super.startElement(uri, localName, name, attributes);
    }

    /**
     * parse characters between open and close tags
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }
}
