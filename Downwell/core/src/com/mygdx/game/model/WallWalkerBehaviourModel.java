package com.mygdx.game.model;

/**
 * WallWalkerBehaviourModel is a class that calculates the velocity of the enemies that walk on walls.
 */
public class WallWalkerBehaviourModel extends BehaviourModel {

    /**
     * Constructor of the class. Empty.
     */
    public WallWalkerBehaviourModel() {}

    /**
     * Based on the current direction of the enemy, it calculates the velocity of the enemy. The only coordinate
     * of relevance is the y coordinate.
     * @param x x coordinate of the enemy
     * @param y y coordinate of the enemy
     * @param obj object that is the exterior factor that will influence the next position of the enemy
     * @return array with the coordinates with the velocity
     */
    @Override
    public float[] act(float x, float y, Object obj) {
        Integer dir = (Integer) obj;
        float[] res = {0,0};
        if(dir == 1){
            res[1] = (float)(y + 0.01);
        }
        else {
            res[1] = (float)(y - 0.01);
        }
        return res;
    }
}
