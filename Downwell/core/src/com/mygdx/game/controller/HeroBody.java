package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.model.HeroModel;
import com.badlogic.gdx.physics.box2d.World;


public class HeroBody extends ElementBody {

    public HeroBody(World world, HeroModel model){
        super(world,model, BodyDef.BodyType.DynamicBody);
        float density = 0.5f, friction = 0.4f, restitution = 0;
        float width = 1, height = 1;

        createRectangleFixture(body,width,height,density,friction,restitution);
    }
}
