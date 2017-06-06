package com.mygdx.game.model;

/**
 * BubbleModel is a class that contains information about the behaviour of the bubble, its value in points and its type.
 * @see EnemyModel
 */
public class BubbleModel extends EnemyModel {
    private static final int POINTS = 50;

    /**
     * Constructor of the class that sets the behaviour to the Follower Behaviour.
     * @param x x coordinate of the bubble
     * @param y y coordinate of the bubble
     */
    public BubbleModel(float x, float y) {
        super(x,y,new FollowerBehaviourModel());
        points = POINTS;
    }

    /**
     * Returns the type of enemy that this class is (BUBBLE).
     * @return ModelType.BUBBLE
     */
    public ModelType getType(){
        return ModelType.BUBBLE;
    }
}
