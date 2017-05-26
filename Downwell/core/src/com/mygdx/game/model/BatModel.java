package com.mygdx.game.model;


import com.mygdx.game.controller.HeroBody;

public class BatModel extends EnemyModel {

    public BatModel(float x, float y) {
        super(x,y,new FollowerBehaviourModel());
        if(x > 5)
            flip = false;
        else
            flip = true;
    }

    public ModelType getType(){
        return ModelType.BAT;
    }

    public float[] update(HeroBody hero){
        float res[];
        res = super.update(hero);
        if(this.getX() > hero.getX())
            flip = true;
        else
            flip = false;
        return res;
    }
}
