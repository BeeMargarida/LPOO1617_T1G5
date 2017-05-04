package com.mygdx.game.model;


import com.mygdx.game.controller.HeroBody;
import com.mygdx.game.model.BehaviourModel;

public class BatModel extends EnemyModel {

    private BehaviourModel behaviour;

    public BatModel(float x, float y) {
        super(x,y);
        this.behaviour = new FollowerBehaviourModel();
    }

    public float[] update(HeroBody hero){
        float[] res = behaviour.act(this.getX(), this.getY(), hero);
        return res;
        //setTransform(res[0], res[1], 0);
    }
}
