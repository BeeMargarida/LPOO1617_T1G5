package com.mygdx.game.model;

public class BubbleModel extends EnemyModel {
    private static final int POINTS = 50;

    public BubbleModel(float x, float y) {
        super(x,y,new FollowerBehaviourModel());
        points = POINTS;
    }

    public ModelType getType(){
        return ModelType.BUBBLE;
    }
}
