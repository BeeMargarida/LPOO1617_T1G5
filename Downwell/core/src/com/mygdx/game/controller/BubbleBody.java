package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.*;

/**
 * BubbleBody is a class that contains the information about with the physics body of the Bubble Enemy.
 * @see ElementBody
 */
public class BubbleBody extends ElementBody {
    /**
     * Constructor that creates the fixture for the Bubble. It gives it it's attributes and constructs it's fixture.
     * @param world the physics world
     * @param model the model of the bubble to be used in the super class constructor
     */
    public BubbleBody(World world, BubbleModel model) {
        super(world,model, BodyDef.BodyType.DynamicBody);

        float density = 1f, friction = 0.4f, restitution = 0f;
        float width = 0.5f, height = 0.4f;
        createRectangleFixture(body,width,height,density,friction,restitution,true);
    }
}
