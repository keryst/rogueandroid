RogueAndroid: A Diablo inspired role-playing game forAndroid

Project realease date: May 5, 2010
Version: 1.0
Authors: Drew Glass, Joshua Glovinsky, Hyun Soon Kim, David Kristola, Michael Lai, Henry Millson, John Svitek
Contact: https://code.google.com/p/rogueandroid/

======================
*PROJECT INFORMATION*
======================

RogueAndroid is a fast paced tile based role-playing game for Android devices based on the classic game Diablo. RogueAndroid provides an easy-to-use interface including "swiping" technology to view other sections of the map, "touch" technology to attack and travel, and  "auto pickup" of items to increase attack and defense bonuses.

In RogueAndroid, the game’s quest is to search the dungeon for the Red Dragon and to slay it. Many dangerous enemies are encountered along the way and a fight is always just around the corner. Magic potions can be used to heal and restore hit points lost during battles with monsters. Other items such as swords and shields can be used to raise attack and defense to increase the chance of defeating the Red Dragon and winning the game. 

Currently RogueAndroid supports three character classes: warrior, ranger and mage and two monster types: dragon and orc. There are various items supported including swords, helmets, shields, and healing potions. 

RogueAndroid is built using Rokon, an Android OpenGL game engine, Android SDK, Java 1.6, a newly developed tile engine, and a completely integrated testing suite.

======================
*INSTALLATION*
======================

HOW TO INSTALL ECLIPSE AND THE ANDROID SDK

We recommend using the Eclipse platform to install RogueAndroid for development purposes. The latest version of Eclipse can be downloaded at http://www.eclipse.org/. Here is the link that explains how to install the Android SDK and ADT Plugin for Eclipse:

Android SDK installation guide - http://developer.android.com/sdk/index.html 

ADT Plugin Updating guide -  http://developer.android.com/sdk/eclipse-adt.html 

After finishing the above steps, please make sure to create an Android Virtual Device with 2.0.1 version in order to run this program using Android emulator. The emulator icon will be listed on the menu of Eclipse and when clicked, a window named “Android SDK and AVD Manager” will pop up. Use this window to set up an emulator with various memory sizes and versions of Android SDK.

INSTALLATION OF THE ROGUEANDROID SOURCE FILES

The RogueAndroid source files can be imported by SVN into Eclipse. SVN does not come with Eclipse. The prefered plugin for SVN on Eclipse is Subversive. A good tutorial for installing it is located here. After the plugin is installed, a new project from SVN repository can be created using this repository: https://csil-projects.cs.uiuc.edu/svn/sp10/cs428/RogueAndroid/trunk/. Another project from SVN repository should be created for the testing suite: https://csil-projects.cs.uiuc.edu/svn/sp10/cs428/RogueAndroid/projecttest/trunk/. 

After the class, the sources for the game and testing suite will be moved to https://code.google.com/p/rogueandroid/.

INSTALLATION OF OTHER FILES FOR DEVELOPMENT

All required libraries such as Rokon and xStream are in the game’s package so that no additional installation is necessary to run the game.

PLAYING AND DEBUGGING THE GAME

The game can be played by right clicking on the RogueAndroid package in Eclipse and selecting Run As > Android Application. A run configuration can be set by right clicking on the RogueAndroid package and selecting Run As > Run Configurations. This option gives the abilitiy to set different parameters for the emulator when RogueAndroid is run.

RogueAndroid can be debugged with the logcat feature in the ADT plugin. To see the logcat output of system messages in Eclipse, select Window > Show View > Other > Android > LogCat. Rokon has a Debug class with a print method that can be used to output messages to the logcat. Debug statements should be removed before the product is released to users. The debug function can reduce performance.

INSTALLING ROGUEANDROID ON AN ANDROID DEVICE

The RogueAndroid application has not been signed with a private security certificate yet, and as such it cannot be installed using Android's Market or third-party app installers. The application is signed with the default debug certificate, allowing it to be installed on real hardware using the Android SDK toolkit. RogueAndroid can be installed on any device that supports Android 2.0 or newer.

1. Install the Android SDK located at http://developer.android.com/sdk/index.html

2. Install the latest Android USB driver by following the instructions at http://developer.android.com/sdk/win-usb.html. It is vital to follow the instructions carefully, as by default Windows will install a basic driver for an Android device that can use it as a mass storage device, but does not allow access to the operating system. By using the appropriate Android USB driver, the Android SDK can install applications on the device and use it as a debugging target.

3. Connect the Android device to the Windows computer. On the Android device, pull down the notification bar and ensure that USB debugging is enabled. If it is disabled, tap the debugging section and enable USB debugging.

4. Open a command window and navigate to the /tools directory in the Android SDK. Run the command:

adb install <full path of RogueAndoid.apk>

5. RogueAndroid will be installed on the device, and can run as a normal application, even without the USB connection active. While connected, the application can also be debugged directly from Eclipse by choosing the Android device as the debugging target.

======================
*CREDITS*
======================

Reiner's Tilesets: http://reinerstileset.4players.de
Rokon Game Engine: http://rokonandroid.com
Junit: http://www.junit.org
Android: http://www.android.com

======================
*Copyright*
======================

Copyright (C) 2010 Glass, Glovinsky, Kim, Kristola, Lai, Millson, Svitek
This source code is licensed under the Apache License, Version 2.0. You may not use this file except in compliance with the license. You may obtain a copy of the license at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by written agreement or applicable law, this software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OFANY KIND, either implied or express.  See the license for details.
