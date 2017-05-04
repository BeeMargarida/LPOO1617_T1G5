package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.BubbleModel;

/**
 * Created by mc-guida on 04-05-2017.
 */

public class BubbleBody extends EnemyBody {

    private BehaviourBody behaviour;

    public BubbleBody(World world, BubbleModel model) {
        super(world,model);
        behaviour = new FollowerBehaviourBody();
    }

    public void update(HeroBody hero){
        float[] res = behaviour.act(this.getX(), this.getY(), hero);
        setTransform(res[0], res[1], 0);
    }
}
