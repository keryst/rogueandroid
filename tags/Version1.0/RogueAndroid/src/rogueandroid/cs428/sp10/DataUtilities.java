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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * This class access XML in order to bring out character information. if player
 * has selected on a particular character which will be brought out from XML,
 * and this character will be saved to file in order to execute the game with
 * that character.
 * 
 * For now there are three differenct types of characters: Warrior, Mage, Archer
 * It is also possible to put new character types into XML.
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
public class DataUtilities {

    /**
     * Put new kind of character into XML
     * 
     * @param character
     * @return xstream.toXML(character)
     */
    public static String convertCharacterToXml(Character character) {
        XStream xstream = new XStream(new DomDriver());
        return xstream.toXML(character);
    }

    /**
     * Pull out character from XML
     * 
     * @param characterXml
     * @return character
     */
    public static Character getCharacterFromXml(String characterXml) {
        Character character = null;
        XStream xstream = new XStream(new DomDriver());
        Object obj = xstream.fromXML(characterXml);
        if (obj instanceof Character) {
            character = (Character) obj;
        }
        return character;
    }

    /**
     * Save character from XML to File
     * 
     * @param character
     * @param filename
     * @return saved (Boolean)
     */
    public static Boolean saveCharacterXmlToFile(Character character, String filename) {
        Boolean saved = false;
        saved = saveStringToFile(filename, convertCharacterToXml(character));

        return saved;

    }

    /**
     * Called by saveCharacterXmlToFile. It saves string value gotten from
     * character value to the file
     * 
     * This class is good to be private for security issue, but leave it as
     * public for debug process of future developers.
     * 
     * @param filename
     * @param saveString
     * @return saved (Boolean)
     */
    public static Boolean saveStringToFile(String filename, String saveString) {
        Boolean saved = false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filename));
            try {
                bw.write(saveString);
                saved = true;
            } finally {
                bw.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return saved;
    }

    /**
     * Receive string value from file
     * 
     * @param filename
     * @return sb.toString (String)
     */
    public static String getStringFromFile(String filename) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(filename));
            try {
                String s;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                br.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * Retrieves character information from XML to read data
     * 
     * @param filename
     * @return character
     */
    public static Character readCharacterXml(String filename) {
        Character character = getCharacterFromXml(getStringFromFile(filename));
        return character;
    }
}
