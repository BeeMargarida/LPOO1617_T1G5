package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.controller.HeroBody;
import com.mygdx.game.model.BatModel;
import com.mygdx.game.model.BubbleModel;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.EnemyModel;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.HeroModel;
import com.mygdx.game.model.MapTileModel;
import com.mygdx.game.model.SnailModel;

public class GameController implements ContactListener {
    public static int ARENA_WIDTH;
    public static int ARENA_HEIGHT;
    public static int TILE_DIMENSIONS = 10;

    private final World world;
    private final GameModel model;
    private final HeroBody hero;
  /*  private final BatBody bat;
    private final BubbleBody bubble;*/
    private EnemyBody enemies[];
    private final MapTileBody tile;
    private float accumulator;

    public GameController(GameModel model){
        world = new World(new Vector2(0,-4f),true);

        hero = new HeroBody(world,model.getHeroModel());
        /*bat = new BatBody(world,model.getBatModel());
        bubble = new BubbleBody(world,model.getBubbleModel());*/
        enemies = new EnemyBody[model.getEnemies().length];
        tile = new MapTileBody(world,model.getMap()[0][0]);
        this.model = model;
        fillWorld();
        world.setContactListener(this);
    }


    private void fillWorld(){
        MapTileModel map[][] = model.getMap();
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j] != null)
                    new MapTileBody(world,map[i][j]);
            }
        }
        for(int i = 0; i < model.getEnemies().length; i++){
            if(model.getEnemies()[i] instanceof BatModel) {
                new BatBody(world, (BatModel) model.getEnemies()[i]);
                enemies[i] = new BatBody(world, (BatModel) model.getEnemies()[i]);
                System.out.println("FACK");
            }
            else if(model.getEnemies()[i] instanceof BubbleModel) {
                new BubbleBody(world, (BubbleModel) model.getEnemies()[i]);
                enemies[i] = new BubbleBody(world, (BubbleModel) model.getEnemies()[i]);
                System.out.println("FACK1");
            }
            else if(model.getEnemies()[i] instanceof SnailModel) {
                new SnailBody(world, (SnailModel) model.getEnemies()[i]);
                enemies[i] = new SnailBody(world, (SnailModel) model.getEnemies()[i]);
                System.out.println("FACK2");
            }
            //System.out.println("FACK3");
        }
    }

    public void enemiesUpdate() {
        for(int i = 0; i < model.getEnemies().length; i++){
            float[] res = model.getEnemies()[i].update(hero);
            enemies[i].setTransform(res[0],res[1],0);
        }
    }

    public void update(float delta){
        enemiesUpdate();

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

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof HeroModel)
            if(contact.getFixtureA() == hero.getUnder()) {
                hero.removeState();
            }
        //Tile
        if (bodyB.getUserData() instanceof MapTileModel) {
            if(contact.getFixtureB() == tile.getAbove()) {
                hero.removeState();
            }
        }
        //Enemy
        /*if(bodyB.getUserData() instanceof EnemyModel) {
            if(contact.getFixtureB() == bat.getAbove()) {
                System.out.println("HIT1!");
                //destroys enemy
            }
        }*/
        //Tile
        if (bodyA.getUserData() instanceof HeroModel && bodyB.getUserData() instanceof MapTileModel)
            if(contact.getFixtureA() == hero.getUnder() && contact.getFixtureB() == tile.getAbove()) {
                hero.removeState();
            }
        if (bodyA.getUserData() instanceof MapTileModel && bodyB.getUserData() instanceof HeroModel)
            if(contact.getFixtureB() == hero.getUnder() && contact.getFixtureA() == tile.getAbove()) {
                hero.removeState();
            }
        //Enemy
       /* if (bodyA.getUserData() instanceof HeroModel && bodyB.getUserData() instanceof EnemyModel)
            if(contact.getFixtureA() == hero.getUnder() && contact.getFixtureB() == bat.getAbove()) {
                System.out.println("HIT2!");
            }
        if (bodyA.getUserData() instanceof EnemyModel && bodyB.getUserData() instanceof HeroModel)
            if(contact.getFixtureB() == hero.getUnder() && contact.getFixtureA() == bat.getAbove()) {
                System.out.println("HIT3!");
            }
         */
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void moveHeroLeft(){
        //hero.setTransform(hero.getX()-1, hero.getY(),0);
        hero.body.applyForceToCenter(-20f,0,true);
    }

    public void moveHeroRight(){
        //hero.setTransform(hero.getX()+1, hero.getY(),0);
        hero.body.applyForceToCenter(20f,0,true);
    }


    public void jumpHero(){
        if(!hero.getState() && hero.body.getLinearVelocity().y == 0) { //it isn't jumping or falling
            hero.setState();
            hero.body.applyForceToCenter(0,200f, true);
        }
    }


    public World getWorld() {
        return world;
    }
    public HeroBody getHeroBody() { return hero; }
    public EnemyBody[] getEnemiesBody() { return enemies; }
}
