package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.model.EnemyModel;
import com.badlogic.gdx.physics.box2d.World;

public abstract class EnemyBody extends ElementBody {

    public EnemyBody(World world, EnemyModel model){
        super(world, model, BodyDef.BodyType.KinematicBody);

        float density = 1f, friction = 0.4f, restitution = 0f;
        float width = 0.4f, height = 0.4f;
        //int dimension = GameController.TILE_DIMENSIONS;
        createRectangleFixture(body,width,height,density,friction,restitution);
    }
}
