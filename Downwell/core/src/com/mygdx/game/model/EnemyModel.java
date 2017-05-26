package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

/**
 * Created by mc-guida on 27-04-2017.
 */

public abstract class EnemyModel extends ElementModel {

    protected BehaviourModel behaviour;
    protected boolean flagForRemoval;
    protected boolean flip = false;

    public EnemyModel(float x, float y, BehaviourModel behaviour){
        super(x,y);
        this.behaviour = behaviour;
        flagForRemoval = false;
    }

    public void setForRemoval() {
        flagForRemoval = true;
    }

    public boolean getForRemoval() {
        return flagForRemoval;
    }

    public boolean getFlip(){
        return flip;
    }

    public float[] update(HeroBody hero){
        float[] res = behaviour.act(this.getX(), this.getY(), hero);
        return res;
    }
}
