package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.controller.HeroBody;
import com.mygdx.game.model.GameModel;

public class GameController {
    public static final int ARENA_WIDTH = 100;
    public static final int ARENA_HEIGHT = 150;
    private final World world;
    private final HeroBody hero;
    private final BatBody bat;

    public GameController(GameModel model){
        world = new World(new Vector2(0,0),true);
        hero = new HeroBody(world,model.getHeroModel());
        bat = new BatBody(world,model.getBatModel());
    }


    public void update(float delta){

    }

    public World getWorld() {
        return world;
    }
}
