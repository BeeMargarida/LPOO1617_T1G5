package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

/**
 * Created by mc-guida on 27-04-2017.
 */

public abstract class EnemyModel extends ElementModel {

    private BehaviourModel behaviour;

    public EnemyModel(float x, float y, BehaviourModel behaviour){
        super(x,y);
        this.behaviour = behaviour;
    }

    public float[] update(HeroBody hero){
        float[] res = behaviour.act(this.getX(), this.getY(), hero);
        return res;
    }
}
