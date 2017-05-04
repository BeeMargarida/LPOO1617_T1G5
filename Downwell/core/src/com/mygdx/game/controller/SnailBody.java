package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.SnailModel;


public class SnailBody extends EnemyBody {

    private BehaviourBody behaviour;

    public SnailBody(World world, SnailModel model) {
        super(world,model);
        behaviour = new WallWalkerBehaviourBody();
    }

    public void update(){
        float[] res = behaviour.act(this.getX(), this.getY());
        setTransform(res[0], res[1], 0);
    }
}
