package com.mygdx.game.model;

public class BubbleModel extends EnemyModel {

    public BubbleModel(float x, float y) {
        super(x,y,new FollowerBehaviourModel());
    }

    public ModelType getType(){
        return ModelType.BUBBLE;
    }
}
