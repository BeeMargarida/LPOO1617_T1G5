package com.mygdx.game.model;


public class HeroModel extends ElementModel {

    public HeroModel(float x, float y) {
        super(x,y);
    }

    public ModelType getType(){
        return ModelType.HERO;
    }
}
