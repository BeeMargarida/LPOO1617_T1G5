package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.MapTileModel;

/**
 * Created by Utilizador on 27/04/2017.
 */

public class MapTileBody extends ElementBody {

    MapTileBody(World world, MapTileModel model){
        super(world,model, BodyDef.BodyType.KinematicBody);

        float density = 1f, friction = 0f, restitution = 0;
        float width = 0.5f, height = 0.5f;

        createRectangleFixture(body,width,height,density,friction,restitution);
    }
}
