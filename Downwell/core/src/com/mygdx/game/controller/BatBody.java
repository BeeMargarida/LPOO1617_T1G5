package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.*;

/**
 * BatBody is a class that contains the information about with the physics body of the Bat Enemy.
 * @see ElementBody
 */
public class BatBody extends ElementBody {
    /**
     * Constructor that creates the fixture for the Bat. It gives it it's attributes and constructs it's fixture.
     * @param world the physics world
     * @param model the model of the bat to be used in the super class constructor
     */
    public BatBody(World world, BatModel model){
        super(world,model, BodyDef.BodyType.DynamicBody);

        float density = 1f, friction = 0.4f, restitution = 0f;
        float width = 0.4f, height = 0.4f;
        createRectangleFixture(body,width,height,density,friction,restitution,true);
    }
}
