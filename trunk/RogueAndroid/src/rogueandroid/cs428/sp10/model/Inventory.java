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
 * Inventory represents a list of items that can be carried by an entity.
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
public class Inventory {
    private ArrayList<Item> mInventory = new ArrayList<Item>();

    private static int sMaxInventory = 20;

    /**
     * Insert a single item into the first available slot in the inventory
     * @param item - The item to be inserted
     * @return true if there was enough room to insert the item
     */
    public boolean insertItem(Item item) {
        boolean bSuccess = false;
        if (mInventory.size() < sMaxInventory) {
            mInventory.add(item);
            bSuccess = true;
        }
        return (bSuccess);
    }

    /**
     * Remove an item from the inventory
     * @param item - The item to be removed from the inventory.
     */
    public void removeItem(Item item) {
        if (mInventory.contains(item)) 
            mInventory.remove(item);
    }

    /**
     * Get the number of items stored in the inventory.
     * @return the number of items in the inventory
     */
    public int size() {
        return (mInventory.size());
    }

    /**
     * Get an item stored in the inventory by its location
     * @param index The index of the item in the inventory
     * @return the desired Item
     */
    public Item getItem(int index) {
        return (mInventory.get(index));
    }
}
