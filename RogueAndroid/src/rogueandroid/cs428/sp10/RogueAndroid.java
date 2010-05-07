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

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * The RogueAndroid Class is the main Activity class for the application. It is
 * run first when the application is loaded.
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
public class RogueAndroid extends ListActivity {
    /**
     * Called when the activity is created
     * 
     * @param b
     *            Resource bundle
     * @return none
     */
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        String[] options = new String[] { "Main Game", "RogueAndroid Spike", "Choose Character",
                "TiledBackground Demo", "New Main Menu" };
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));
    }

    /**
     * The main menu offers a selection of activities. When one is selected this
     * method is called.
     * 
     * @param l
     *            Listview corresponding to the menu choices displayed to the
     *            user
     * @param v
     *            not used
     * @param position
     *            Listview item number selected by the user
     * @param id
     *            not used
     * @return none
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent;
        switch (position) {
        default:
        case 0: /* Main Game */
            intent = new Intent(this, ChooseCharacterClass.class);
            startActivity(intent);
            break;
        case 1: /* Rogue Android Spike */
            intent = new Intent(this, RogueAndroidSpike.class);
            startActivity(intent);
            break;
        case 2: /* Choose Character */
            intent = new Intent(this, ChooseCharacterClass.class);
            startActivity(intent);
            break;
        case 3: // Tiled Background Demo
            intent = new Intent(this, TiledBackgroundDemo.class);
            startActivity(intent);
            break;
        case 4: // New Main Menu
            intent = new Intent(this, RogueAndroidMain.class);
            startActivity(intent);
            break;
        }
    }
}
