package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

/**
 *
 */
public class FollowerBehaviourModel extends BehaviourModel {

    private static final float ACTIVE_DISTANCE = 5f;
    private boolean active;

    /**
     * Constructor of the class, sets the active flag (following hero) to false.s
     */
    public FollowerBehaviourModel() {
        active = false;
    }

    /**
     * Calculates the distance between the enemy and the hero and, if that distance is less than the ACTIVE_DISTANCE, besides changing the
     * active state of the enemy, it will also calculate the x and y coordinates of the new direction to move the enemy to, following the hero.
     * It then returns those new coordinates. If the distance is bigger than the ACTIVE_DISTANCE, the resulting array will be with 0's, meaning
     * that the enemy won't be active.
     * @param x x coordinate of the enemy
     * @param y y coordinate of the enemy
     * @param obj body of the hero that will be used to calculate the direction of the enemy's movement
     * @return array with x and y coordinate, meaning the new direction for the enemy to take
     */
    @Override
    public float[] act(float x, float y, Object obj) {
        HeroBody hero = (HeroBody) obj;
        double dist = Math.sqrt(Math.pow(x - hero.getX(),2) + Math.pow(y-hero.getY(),2));
        if(dist < ACTIVE_DISTANCE) {
            if(!active)
                active = true;
            float distX = x-hero.getX();
            float distY = y-hero.getY();
            float[] res = {(float)(distX * (2 / dist)),(float)(distY*(2/dist))};
            return res;
        }
        float[] res = new float[]{0, 0};
        return res;
    }

    /**
     * Returns the flag active of the enemy.
     * @return true if the enemy is moving, false if not
     */
    public boolean isActive(){
        return active;
    }
}
