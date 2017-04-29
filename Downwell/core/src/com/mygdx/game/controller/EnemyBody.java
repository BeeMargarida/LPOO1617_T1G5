package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.model.EnemyModel;
import com.badlogic.gdx.physics.box2d.World;

public abstract class EnemyBody extends ElementBody {

    public EnemyBody(World world, EnemyModel model){
        super(world, model, BodyDef.BodyType.DynamicBody);

        /*
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(model.getX(), model.getY());
        //bodyDef.angle = model.getRotation();

        body = world.createBody(bodyDef);
        body.setUserData(model);
        */

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        int dimension = GameController.TILE_DIMENSIONS;
        createRectangleFixture(body,dimension,dimension,density,friction,restitution);
    }
}
