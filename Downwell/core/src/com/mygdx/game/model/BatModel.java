package com.mygdx.game.model;


public class BatModel extends EnemyModel {

    public BatModel(float x, float y) {
        super(x,y,new FollowerBehaviourModel());
    }

    public ModelType getType(){
        return ModelType.BAT;
    }
}
