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

import android.content.res.XmlResourceParser;
import android.content.*;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;

/**
 * Actual implementation of parser
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
public class ConfigParse {

    private static Config config = new Config();

    /**
     * Get configuration value
     * 
     * @return config
     */
    public static Config getConfig() {
        return config;
    }

    /**
     * Actual parse of configurations. uses the pull parser
     * 
     * @param context
     * @param resourceID
     * @return parseSuccessful
     */
    public static boolean parse(Context context, int resourceID) {
        boolean parseSuccessful = false;
        try {
            // Use the Pull Parser
            XmlResourceParser xrp = context.getResources().getXml(resourceID);
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals("screen")) {
                        config.setScreenXPixels(xrp.getAttributeIntValue(null, "xpixels", 0));
                        config.setScreenYPixels(xrp.getAttributeIntValue(null, "ypixels", 0));
                    } else if (s.equals("tiles")) {
                        config.setTileSize(xrp.getAttributeIntValue(null, "size", 0));
                    } else if (s.equals("dungeontexture")) {
                        config.setDungeonTextures(xrp.getAttributeValue(null, "graphic"));
                    } else if (s.equals("monster")) {
                        config.setMonster(xrp.getAttributeValue(null, "char"));
                    } else if (s.equals("item")) {
                        config.setItem(xrp.getAttributeValue(null, "char"));
                    } else if (s.equals("plaintag")) {
                    }
                } else if (xrp.getEventType() == XmlResourceParser.END_TAG) {
                    ;
                }
                xrp.next();
            }
            xrp.close();
            parseSuccessful = true;
        } catch (XmlPullParserException xppe) {
            xppe.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return (parseSuccessful);
    }
}