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

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * It lists type of characters and let player to choose one of them to play. It
 * extends ListActivities so there is number of override methods.
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
public class ChooseCharacterClass extends ListActivity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        String[] characters = new String[] { "Warrior", "Mage", "Ranger" };
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                                        characters));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent;
        final Context context = ChooseCharacterClass.this;
        switch (position) {
        default:
        case 0: // Warrior
            WriteSettings(context, "Warrior");
            break;
        case 1: // Mage
            WriteSettings(context, "Mage");
            break;
        case 2: // Ranger
            WriteSettings(context, "Ranger");
            break;
        }

        intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    /**
     * It saves settings.
     * 
     * @param context
     * @param data
     */
    public void WriteSettings(Context context, String data) {
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;

        try {
            fOut = openFileOutput("character.dat", MODE_PRIVATE);
            osw = new OutputStreamWriter(fOut);
            osw.write(data);
            osw.flush();
            Toast.makeText(context, "Character saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Character not saved", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                osw.close();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
