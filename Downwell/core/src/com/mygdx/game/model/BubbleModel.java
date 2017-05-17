package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

/**
 * Created by mc-guida on 04-05-2017.
 */

public class BubbleModel extends EnemyModel {

    private BehaviourModel behaviour;

    public BubbleModel(float x, float y) {
        super(x,y);
        this.behaviour = new FollowerBehaviourModel();
    }

    public float[] update(HeroBody hero){
        float[] res = behaviour.act(this.getX(), this.getY(), hero);
        return res;
        //setTransform(res[0], res[1], 0);
    }

    public ModelType getType(){
        return ModelType.BUBBLE;
    }
}
