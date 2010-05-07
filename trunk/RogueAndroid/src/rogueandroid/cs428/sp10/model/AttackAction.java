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

import java.util.Random;

import rogueandroid.cs428.sp10.Direction;
import rogueandroid.cs428.sp10.view.Displayable;

import com.stickycoding.Rokon.Debug;

/**
 * AttackAction describes attack actions that Entities will use
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
public class AttackAction implements Action {

    private static final int ATTACK_SPEED = 20;
    private static final int ATTACK_ROLL = 10;
    private static final int DAMAGE_ROLL = 10;
    private Entity mAttacker;
    private Entity mTarget;
    private boolean mAttacking = true;
    private int mLoopsToGo = 0;
    private Random mDieRoller;
    private Displayable mDisplayable;
    private boolean mReplaceable;

    static private final boolean LOCAL_LOGGING = false;

    /**
     * Constructor for AttackAction
     * 
     * @param attacker
     *            Entity of attacker
     * @param target
     *            Entity of target of attack
     * @param generator
     *            Random
     */
    public AttackAction(Entity attacker, Entity target, Random generator) {
        mAttacker = attacker;
        mTarget = target;
        mLoopsToGo = timeToNextAttack();
        mDieRoller = generator;
        mDisplayable = attacker.getDisplayable();
        mReplaceable = false;
    }

    /**
     * 
     * @return Entity the Attacker
     */
    public Entity getmAttacker() {
        return mAttacker;
    }

    /**
     * Returns the Target Entity
     * 
     * @return Entity the Target
     */
    public Entity getmTarget() {
        return mTarget;
    }

    /**
     * Compute the time (in game loops) to the next attack.
     * 
     * @return int the number of game loops to the next attack.
     */
    private int timeToNextAttack() {
        return ATTACK_SPEED - mAttacker.getSpeed();
    }

    /**
     * Check to see if action is completed
     * 
     * @return boolean attacking
     */
    public boolean actionCompleted() {
        return !mAttacking;
    }

    /**
     * Action upon Gameloop
     */
    public void onGameLoop() {
        if (mAttacking) {
            // First, check to see if the attack can still happen (no one ran
            // away)
            if (mTarget.isDead()) {
                stopAttack();
            } else if (mAttacker.getTileLocation().distanceTo(mTarget.getTileLocation()) > 1) {
                stopAttack();
            } else {
                if (mLoopsToGo > 0) {
                    mLoopsToGo -= 1;
                    mReplaceable = false;
                    if (LOCAL_LOGGING) {
                        Debug.print("AttackAction mLoopsToGo = "
                                                        + ((Integer) mLoopsToGo).toString());
                    }
                } else {
                    attackEntity();
                }
            }
        }
    }

    /**
     * Attack the Entity
     */
    private void attackEntity() {
        mReplaceable = true;
        try {
            Direction facing = Direction.directionBetween(mAttacker.getTileLocation(), mTarget
                                            .getTileLocation());
            mDisplayable.startAttacking(facing);
        } catch (Exception e) {
            if (LOCAL_LOGGING) {
                Debug.print("ERROR: attack facing calculation threw an" + " exception.");
            }
            mAttacking = false;
        }

        mLoopsToGo = timeToNextAttack();
        int attackBonus = mAttacker.getAttackBonus();
        int defenseBonus = mTarget.getDefenseBonus();
        int roll = mDieRoller.nextInt(ATTACK_ROLL);
        int result = roll + attackBonus - defenseBonus;
        if (LOCAL_LOGGING) {
            Debug.print("AttackAction result = " + ((Integer) result).toString());
        }
        if (result > 0) {
            // TODO: Get the damage from the attacker so that it can be based on
            // the weapon or attack method.
            int damage = mDieRoller.nextInt(DAMAGE_ROLL);
            if (LOCAL_LOGGING) {
                Debug.print("AttackAction damage = " + ((Integer) damage).toString());
            }
            mTarget.takeDamage(damage);
        }
    }

    /**
     * Stop attacking
     */
    private void stopAttack() {
        mAttacking = false;
        mReplaceable = true;
        mAttacker.stopAttack();
        mDisplayable.stopAttacking();
        if (LOCAL_LOGGING) {
            Debug.print("AttackAction.stopAttack");
        }
    }

    /**
     * Returns boolean if the action is replaceable
     * 
     * @return boolean Replaceable
     */
    public boolean actionReplaceable() {
        return mReplaceable;
    }

    /**
     * Stop Attack
     */
    public void stopAsSoonAsPossible() {
        stopAttack();
    }
}
