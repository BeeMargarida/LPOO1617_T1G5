package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.model.HeroModel;
import com.badlogic.gdx.physics.box2d.World;

/**
 * HeroBody is a class that contains the information about with the physics body of the Hero. It also contains methods to
 * keep the state of jumping of the hero.
 * @see ElementBody
 */
public class HeroBody extends ElementBody {

    private boolean Jumping = true;

    /**
     * Constructor, that gives the information to create a fixture for the body of the Hero.
     * @param world the physics world
     * @param model the model of the hero to be used in the super class constructor
     */
    public HeroBody(World world, HeroModel model){
        super(world,model, BodyDef.BodyType.DynamicBody);
        float density = 1f, friction = 0, restitution = 0;
        float width = 0.4f, height = 0.5f;

        createRectangleFixture(body,width,height,density,friction,restitution,true);
    }

    /**
     *  Sets the jumping state of the hero to true, meaning that the hero is jumping.
     */
    public void setState() { Jumping = true;}

    /**
     *  Sets the jumping state of the hero to false, meaning that the hero has touched
     *  a platform and is not jumping anymore.
     */
    public void removeState() { Jumping = false;}

    /**
     *  Returns the variable Jumping.
     * @return jumping variable (true or false)
     */
    public boolean getState() { return Jumping; }
}
