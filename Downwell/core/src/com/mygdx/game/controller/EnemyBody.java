package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.model.EnemyModel;
import com.badlogic.gdx.physics.box2d.World;

public abstract class EnemyBody extends ElementBody {

    public EnemyBody(World world, EnemyModel model){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(model.getX(), model.getY());
        //bodyDef.angle = model.getRotation();

        body = world.createBody(bodyDef);
        body.setUserData(model);
    }
}
