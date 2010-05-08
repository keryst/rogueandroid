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

import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.stickycoding.Rokon.Debug;


/**
 * The DungeonReader class reads the resource text file for the dungeon map and
 * converts it into an array.
 * 
 * '#' is a blocked tile and '.' is an open tile
 * 
 * TODO: The chars for blocked and open tiles are hard coded. They need to be
 * moved to the config file
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
public class DungeonReader {

    // private StreamToString sts;
    public static Integer[][] dungeonFloorplanArr;
    public static int xSize = 0;
    public static int ySize = 0;
    static ArrayList<String> sLineHolder = new ArrayList<String>();
    private Integer[][] mDungeonArr;
    
    /**
     * constructor
     * 
     * @param ins
     * @return none
     */
    public DungeonReader(InputStream ins) {

        try {
            mDungeonArr = convertToArray(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the dungeon floorplan as an array
     * 
     * @param none
     * @return Integer[][]
     */
    public Integer[][] getDungeonFloorplan() {
        return mDungeonArr;
    }

    /**
     * Convert dungeon.txt to a matrix
     * 
     * @param InputStream
     * @return Integer[][]
     */
    public static Integer[][] convertToArray(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the
         * BufferedReader.readLine() method. We iterate until the BufferedReader
         * return null which means there's no more data to read. Each line will
         * appended to a StringBuilder and returned as String.
         */
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                // get size of matrix to create array
                while ((line = reader.readLine()) != null) {
                    char[] lineCharArray = line.toCharArray();
                    sLineHolder.add(line);
                    ySize = lineCharArray.length;
                    xSize++;
                }
                Debug.print("Just read dungeon sized x= " + ((Integer) xSize).toString() + " y= "
                                                + ((Integer) ySize).toString());
                dungeonFloorplanArr = new Integer[xSize][ySize];

                // populate array
                for (int i = 0; i < xSize; i++) {
                    char[] lineCharArray = sLineHolder.get(i).toCharArray();
                    for (int j = 0; j < ySize; j++) {
                        dungeonFloorplanArr[i][j] = convertToInt(lineCharArray[j]);
                    }
                }

            } finally {
                is.close();
            }
            return dungeonFloorplanArr;
        } else {
            return null;
        }
    }

    /**
     * Convert character from dungeon.txt to and integer
     * 
     * @param char
     * @return int
     */
    public static int convertToInt(char tileChar) {
        if (tileChar == '#') {
            return 1;
        } else {
            return 0;
        }
    }
}
