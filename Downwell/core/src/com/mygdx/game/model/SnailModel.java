package com.mygdx.game.model;

public class SnailModel extends EnemyModel {

    public SnailModel(float x, float y){
        super(x,y,new WallWalkerBehaviourModel());
    }

    public ModelType getType(){
        return ModelType.SNAIL;
    }
}
