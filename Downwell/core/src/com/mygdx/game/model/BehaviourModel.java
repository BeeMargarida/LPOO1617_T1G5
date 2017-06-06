package com.mygdx.game.model;

/**
 * BehaviourModel is an abstract class that serves to contain the possible behaviours of the enemies, giving opportunity to add more.
 * @see FollowerBehaviourModel
 * @see WallWalkerBehaviourModel
 */
public abstract class BehaviourModel {
    /**
     * This method calculates the direction of the movement of the enemy will make, according to a number of variables exterior to it.
     * @param x x coordinate of the enemy
     * @param y y coordinate of the enemy
     * @param obj object that is the exterior factor that will influence the next position of the enemy
     * @return array of coordinate x and y
     * @see FollowerBehaviourModel#act(float, float, Object)
     * @see WallWalkerBehaviourModel#act(float, float, Object)
     */
    public abstract float[] act(float x, float y, Object obj);
}
