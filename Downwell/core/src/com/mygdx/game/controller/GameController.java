package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.controller.HeroBody;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.MapTileModel;

public class GameController {
    public static int ARENA_WIDTH;
    public static int ARENA_HEIGHT;
    public static int TILE_DIMENSIONS = 10;

    private final World world;
    private float accumulator;
    private final HeroBody hero;
    //private final BatBody bat;

    public GameController(GameModel model){
        world = new World(new Vector2(0,-1f),true);

        hero = new HeroBody(world,model.getHeroModel());
        //bat = new BatBody(world,model.getBatModel());
        fillWorld(model);
}


    private void fillWorld(GameModel model){
        MapTileModel map[][] = model.getMap();
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j] != null)
                    new MapTileBody(world,map[i][j]);
            }
        }
    }

    public void update(float delta){
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for(Body body : bodies){
            ((ElementModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }
    }

    public void moveHeroLeft(){
        //hero.setTransform(hero.getX()-1, hero.getY(),0);
        hero.body.applyForceToCenter(-1f,0,true);
    }

    public void moveHeroRight(){
        //hero.setTransform(hero.getX()+1, hero.getY(),0);
        hero.body.applyForceToCenter(1f,0,true);
    }


    public void jumpHero(){
        hero.body.applyForceToCenter(0,1f,true);
    }


    public World getWorld() {
        return world;
    }
}
