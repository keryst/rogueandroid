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

package rogueandroid.cs428.sp10.test;

import rogueandroid.cs428.sp10.RogueAndroid;
import rogueandroid.cs428.sp10.model.Inventory;
import rogueandroid.cs428.sp10.model.Item;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

public class InventoryTest extends ActivityInstrumentationTestCase2<RogueAndroid> {

	private Inventory mInventory;
	private Item mItem;
	
	protected void setUp() throws Exception {
		super.setUp();
		mInventory = new Inventory();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public InventoryTest(String name) {
		super("rogueandroid.cs428.sp10",RogueAndroid.class);
		setName(name);
	}

	public void testInsertItem() throws Throwable {
		mItem = new Item();
		// test to see that an item can be inserted
		// into the inventory
		Assert.assertTrue(mInventory.insertItem(mItem));			
		for (int i = 0; i < 19; i++)
		{
			mInventory.insertItem(mItem);			
		}
		// test to see that that an item is not inserted into
		// a full inventory
		Assert.assertFalse(mInventory.insertItem(mItem));
	}
	
	public void testRemoveItem() throws Throwable {
		mItem = new Item();
		mInventory.removeItem(mItem);
		// test to see that an item is correctly
		// removed from the inventory
		Assert.assertTrue(mInventory.insertItem(mItem));			
		mInventory.removeItem(mItem);		
	}
	
	public void testSize() throws Throwable {
		mItem = new Item();
		// test for the size of the inventory
		Assert.assertEquals(0, mInventory.size());
		Assert.assertTrue(mInventory.insertItem(mItem));			
		Assert.assertEquals(1, mInventory.size());
	}
	
	public void testGetItem() throws Throwable {
		mItem = new Item();
		// test getting an item from the inventory
		Assert.assertTrue(mInventory.insertItem(mItem));
		Assert.assertEquals(mItem.hashCode(), mInventory.getItem(0).hashCode());
	}
}
