package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.SnailModel;

public class SnailBody extends ElementBody {

    public SnailBody(World world, SnailModel model) {
        super(world,model, BodyDef.BodyType.DynamicBody);

        float density = 1f, friction = 0.4f, restitution = 0f;
        float width = 0.4f, height = 0.4f;
        //int dimension = GameController.TILE_DIMENSIONS;
        createRectangleFixture(body,width,height,density,friction,restitution);
    }
}