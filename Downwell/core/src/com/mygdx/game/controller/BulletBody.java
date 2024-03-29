package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.BulletModel;

import static com.mygdx.game.controller.GameController.BULLET_SPEED;

/**
 * BulletBody is a class that contains the information about with the physics body of the bullets.
 * @see ElementBody
 */
public class BulletBody extends ElementBody {
    /**
     * Constructor that creates the fixture for the Bullet. It has no gravity and it is set with a particular velocity, based on
     * it's model rotation and a fixed speed.
     * @param world the physics world
     * @param model the model of the bullet to be used in the super class constructor and to get the rotation
     */
    public BulletBody(World world, BulletModel model){
        super(world,model, BodyDef.BodyType.DynamicBody);
        float density = 1f, friction = 0, restitution = 0;
        float width = 0.1f, height = 0.1f;

        createRectangleFixture(body,width,height,density,friction,restitution,false);
        body.setGravityScale(0);
        float velx = (float )Math.cos(model.getRotation())*BULLET_SPEED;
        float vely = (float )Math.sin(model.getRotation())*BULLET_SPEED;
        body.setLinearVelocity(velx, vely);
    }
}
