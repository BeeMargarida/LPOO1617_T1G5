package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.MapTileModel;

/**
 * MapTileBody is a class that contains the information about with the physics body of the MapTile.
 * @see ElementBody
 */
public class MapTileBody extends ElementBody {
    /**
     * Constructor that creates the fixture for the MapTile. It gives it it's attributes and constructs it's fixture. It differs
     * from other entities in the game by having a KinematicBody.
     * @param world the physics world
     * @param model the model of the maptile to be used in the super class constructor
     */
    MapTileBody(World world, MapTileModel model){
        super(world,model, BodyDef.BodyType.KinematicBody);

        float density = 1f, friction = 0f, restitution = 0;
        float width = 0.5f, height = 0.5f;

        createRectangleFixture(body,width,height,density,friction,restitution,true);
    }
}
