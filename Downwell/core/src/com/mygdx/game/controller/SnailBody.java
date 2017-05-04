package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.SnailModel;

public class SnailBody extends EnemyBody {

    public SnailBody(World world, SnailModel model) {
        super(world,model);
    }
}
