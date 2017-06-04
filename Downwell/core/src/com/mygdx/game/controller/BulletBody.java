package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.BulletModel;

import static com.mygdx.game.controller.GameController.BULLET_SPEED;

public class BulletBody extends ElementBody {
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
