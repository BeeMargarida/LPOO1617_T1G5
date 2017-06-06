package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

/**
 * BatModel is a class that contains information about the behaviour of the bat, its value in points and its type.
 * @see EnemyModel
 */
public class BatModel extends EnemyModel {

    private static final int POINTS = 50;

    /**
     * Constructor of the class that sets the behaviour to the Follower Behaviour. It also checks the coordinates to see if is necessary
     * to flip the sprite.
     * @param x coordinate x of its position
     * @param y coordinate y of its position
     */
    public BatModel(float x, float y) {
        super(x,y,new FollowerBehaviourModel());
        if(x > 5)
            flip = false;
        else
            flip = true;
        points = POINTS;
    }

    /**
     * Returns the type of enemy that this class is (BAT).
     * @return ModelType.BAT
     */
    public ModelType getType(){
        return ModelType.BAT;
    }

    /**
     * Calls the update of the superclass and check if it necessary to put the flag that confirms if it will be necessary
     * to flip the sprite, according to the bat and hero coordinates.
     * @param hero body of the hero
     * @return array of coordinate x and y of the movement direction of the bat
     * @see EnemyModel#update(HeroBody)
     */
    public float[] update(HeroBody hero){
        float res[];
        res = super.update(hero);
        if(this.getX() > hero.getX())
            flip = true;
        else
            flip = false;
        return res;
    }

    /**
     * Checks if the behaviour is active, that is, if the hero and the enemy are closer to each other than the minimum distance.
     * @return true if it isn't following the hero, false if it is
     */
    public boolean isSleeping(){
        return !((FollowerBehaviourModel) behaviour).isActive();
    }
}
