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

        float density = 1f, friction = 0.4f, restitution = 0.5f;
        float width = 1, height = 1;

        createRectangleFixture(body,width,height,density,friction,restitution);
    }
}
