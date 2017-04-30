package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.BatModel;
import com.mygdx.game.model.EnemyModel;

/**
 * Created by mc-guida on 27-04-2017.
 */

public class BatBody extends EnemyBody {

    public BatBody(World world, BatModel model){
        super(world,model);
        /*float density = 0.5f, friction = 0.4f, restitution = 0f;
        int width = 2, height = 2;*/
        /*createFixture(body, new float[]{
                32,12, 31,46, 34,51, 40,51, 43,46, 41,12
        }, width, height, density, friction, restitution);*/
        //createRectangleFixture(body,width,height,density,friction,restitution);
    }
}
