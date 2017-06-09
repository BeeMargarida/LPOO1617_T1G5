package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

/**
 * SnailModel is a class that contains and deals with information about the snail enemy, such has his points and
 * movement.
 */
public class SnailModel extends EnemyModel {

    private static final int POINTS = 100;
    int direction;

    /**
     * Constructor of the class. It sets the behaviour as WallWalkerBehaviourModel, it's direction to going down
     * and its flip according to its coordinates.
     * @param x x coordinate of its position
     * @param y y coordinate of its position
     */
    public SnailModel(float x, float y){
        super(x,y,new WallWalkerBehaviourModel());
        direction = -1;
        if(x > 5)
            flip = true;
        points = POINTS;
    }

    /**
     * Returns the ModelType of this enemy (SNAIL).
     * @return ModelType.SNAIL
     */
    public ModelType getType(){
        return ModelType.SNAIL;
    }

    /**
     * Returns the direction of the snail's movement.
     * @return -1 if going down, 1 if going up
     */
    public int getDirection() { return direction; }

    /**
     * It changes the direction of the snail, switching to going up or down according to current direction of the
     * snail.
     */
    public void changeDirection() {
        if(direction == -1)
            direction = 1;
        else
            direction = -1;
    }

    /**
     * Calls the method of the behaviour to get the velocity of the next movement and, if the y coordinate of
     * the result the behaviour gives is less than 0, it switches the direction of the snail and gives that coordinate
     * the value 0.
     * @param hero body of the hero used to make calculations in the act method of the behaviour
     * @return the velocity of the snail
     */
    public float[] update(HeroBody hero){
        Integer dir = direction;
        float[] res = behaviour.act(this.getX(), this.getY(), dir);
        if(res[1] < 0){
            changeDirection();
            res[1] = 0;
        }
        return res;
    }
}
