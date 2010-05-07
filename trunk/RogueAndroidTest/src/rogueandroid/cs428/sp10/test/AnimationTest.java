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

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.RogueAndroid;
import rogueandroid.cs428.sp10.view.AttackAnimationFrameSequence;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

public class AnimationTest extends ActivityInstrumentationTestCase2<RogueAndroid> {
       
        protected void setUp() throws Exception {
                super.setUp();
        }

        @Override
        protected void tearDown() throws Exception {
                super.tearDown();
        }

        public AnimationTest(String name) {
                super("rogueandroid.cs428.sp10",RogueAndroid.class);
                setName(name);
        }

        public void testAnimationFacing() throws Throwable {
            // test for facing right
            AttackAnimationFrameSequence animation = null;
            animation = new AttackAnimationFrameSequence(Direction.POS_X);
            Assert.assertEquals(33, animation.getAnimationStartFrame() );
            Assert.assertEquals(45, animation.getAnimationStopFrame() );
            animation = null;
            
            // test for facing left
            animation = new AttackAnimationFrameSequence(Direction.NEG_X);
            Assert.assertEquals(59, animation.getAnimationStartFrame() );
            Assert.assertEquals(71, animation.getAnimationStopFrame() );
            animation = null;

            // test for facing up
            animation = new AttackAnimationFrameSequence(Direction.POS_Y);
            Assert.assertEquals(46, animation.getAnimationStartFrame() );
            Assert.assertEquals(58, animation.getAnimationStopFrame() );
            animation = null;
            
            // test for facing down
            animation = new AttackAnimationFrameSequence(Direction.NEG_Y);
            Assert.assertEquals(72, animation.getAnimationStartFrame() );
            Assert.assertEquals(84, animation.getAnimationStopFrame() );
            animation = null;            
        }
}
