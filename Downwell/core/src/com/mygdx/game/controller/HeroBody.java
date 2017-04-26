package com.mygdx.game.controller;

import com.mygdx.game.model.HeroModel;
import com.badlogic.gdx.physics.box2d.World;


public class HeroBody extends ElementBody {

    public HeroBody(World world, HeroModel model){
        super(world,model);
        float density = 0.5f, friction = 0.4f, restitution = 0.5f;
        int width = 2, height = 2;
        createFixture(body, new float[]{
                32,12, 31,46, 34,51, 40,51, 43,46, 41,12
        }, width, height, density, friction, restitution);
    }
}
