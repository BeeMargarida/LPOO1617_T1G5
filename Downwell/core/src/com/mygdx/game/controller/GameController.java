package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
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

    private enum mov { MS_LEFT, MS_RIGHT, MS_STOP}

    public static int ARENA_WIDTH;
    public static int ARENA_HEIGHT;
    public static int TILE_DIMENSIONS = 10;

    public static float MAX_SPEED = -5f;

    private final World world;
    private final GameModel model;
    private final HeroBody hero;
    private mov moveState;

    private ElementBody enemies[];
    private final MapTileBody tile;
    private float accumulator;

    public GameController(GameModel model){
        world = new World(new Vector2(0,-4f),true);
        hero = new HeroBody(world,model.getHeroModel());
        enemies = new ElementBody[model.getEnemies().size()];
        tile = new MapTileBody(world,model.getMap()[0][0]);
        this.model = model;
        fillWorld();
        moveState = mov.MS_STOP;
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
        for(int i = 0; i < model.getEnemies().size(); i++){
            if(model.getEnemies().get(i) instanceof BatModel) {
                enemies[i] = new BatBody(world, (BatModel) model.getEnemies().get(i));
                enemies[i].body.setGravityScale(0);
            }
            else if(model.getEnemies().get(i) instanceof BubbleModel) {
                enemies[i] = new BubbleBody(world, (BubbleModel) model.getEnemies().get(i));
                enemies[i].body.setGravityScale(0);
            }
            else if(model.getEnemies().get(i) instanceof SnailModel) {
                enemies[i] = new SnailBody(world, (SnailModel) model.getEnemies().get(i));
                enemies[i].body.setGravityScale(0);
            }
        }
    }

    public void enemiesUpdate() {
        for(int i = 0; i < model.getEnemies().size(); i++){
            float[] res = model.getEnemies().get(i).update(hero);
            enemies[i].setTransform(res[0],res[1],0);
        }
    }

    public void update(float delta){
        enemiesUpdate();


        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);

            float vel = hero.body.getLinearVelocity().x;
            float desiredVel = 0;
            switch ( moveState )
            {
                case MS_LEFT:  desiredVel = -5; break;
                case MS_STOP:  desiredVel = 0; break;
                case MS_RIGHT: desiredVel =  5; break;
            }
            float velChange = desiredVel - vel;
            float force = hero.body.getMass() * velChange / (1/60f); //f = mv/t
            hero.body.applyForceToCenter(force,0,true);
            moveState = mov.MS_STOP;

            accumulator -= 1/60f;
        }

        if(hero.body.getLinearVelocity().y < MAX_SPEED)
            hero.body.setGravityScale(0);
        else
            hero.body.setGravityScale(1f);

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for(Body body : bodies){
            ((ElementModel) body.getUserData()).setPosition(body.getPosition().x, body.getPosition().y);
        }
    }

    public void snailBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof  SnailModel){
            if(contact.getFixtureA().getUserData() == "down" && contact.getFixtureB().getUserData() == "up"){
                ((SnailModel) bodyA.getUserData()).changeDirection();
            }
            else if(contact.getFixtureA().getUserData() == "up" && contact.getFixtureB().getUserData() == "down") {
                ((SnailModel) bodyA.getUserData()).changeDirection();
            }
        }
        if(bodyB.getUserData() instanceof SnailModel){
            if(contact.getFixtureA().getUserData() == "down" && contact.getFixtureB().getUserData() == "up"){
                ((SnailModel) bodyB.getUserData()).changeDirection();
            }
            else if(contact.getFixtureA().getUserData() == "up" && contact.getFixtureB().getUserData() == "down") {
                ((SnailModel) bodyB.getUserData()).changeDirection();
            }
        }
        if(bodyA.getUserData() instanceof  SnailModel && bodyB.getUserData() instanceof HeroModel){
            if(contact.getFixtureA().getUserData() == "up" && contact.getFixtureB().getUserData() == "down") {
                //erase enemy
            }
            else {
                //damage hero
            }
        }
        if(bodyA.getUserData() instanceof  HeroModel && bodyB.getUserData() instanceof SnailModel){
            if(contact.getFixtureA().getUserData() == "down" && contact.getFixtureB().getUserData() == "up"){
                //erase enemy
            }
            else {
                //damage hero
            }
        }
    }

    public void batBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof  BatModel && bodyB.getUserData() instanceof HeroModel){
            if(contact.getFixtureA().getUserData() == "up" && contact.getFixtureB().getUserData() == "down") {
                ((BatModel) bodyA.getUserData()).setForRemoval();
            }
            else {
                //damage hero
            }
        }
        if(bodyA.getUserData() instanceof  HeroModel && bodyB.getUserData() instanceof BatModel){
            if(contact.getFixtureA().getUserData() == "down" && contact.getFixtureB().getUserData() == "up"){
                //erase enemy
            }
            else {
                //damage hero
            }
        }
    }

    public void bubbleBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof  BubbleModel && bodyB.getUserData() instanceof HeroModel){
            if(contact.getFixtureA().getUserData() == "up" && contact.getFixtureB().getUserData() == "down") {
                //erase enemy
            }
            else {
                //damage hero
            }
        }
        if(bodyA.getUserData() instanceof  HeroModel && bodyB.getUserData() instanceof BubbleModel){
            if(contact.getFixtureA().getUserData() == "down" && contact.getFixtureB().getUserData() == "up"){
                //erase enemy
            }
            else {
                //damage hero
            }
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        snailBeginContact(contact);
        batBeginContact(contact);
        bubbleBeginContact(contact);

        if (bodyA.getUserData() instanceof HeroModel) {
            if (contact.getFixtureA().getUserData() == "down") {
                hero.removeState();
                model.getHeroModel().setState(HeroModel.state.STANDING);
            }
        }
        //Tile
        if (bodyB.getUserData() instanceof MapTileModel) {
            if(contact.getFixtureB().getUserData() == "up") {
                hero.removeState();
                model.getHeroModel().setState(HeroModel.state.STANDING);
            }
        }

        //Tile
        if (bodyA.getUserData() instanceof HeroModel && bodyB.getUserData() instanceof MapTileModel)
            if(contact.getFixtureA().getUserData() == "down" && contact.getFixtureB().getUserData() == "up") {
                hero.removeState();
            }
        if (bodyA.getUserData() instanceof MapTileModel && bodyB.getUserData() instanceof HeroModel)
            if(contact.getFixtureB().getUserData() == "down" && contact.getFixtureA().getUserData() == "up") {
                hero.removeState();
            }
        //Enemy
    }

    @Override
    public void endContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof SnailModel && bodyB.getUserData() instanceof MapTileModel) {
                ((SnailModel) bodyA.getUserData()).changeDirection();
        }
        if(bodyA.getUserData() instanceof MapTileModel && bodyB.getUserData() instanceof SnailModel) {
                ((SnailModel) bodyB.getUserData()).changeDirection();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void remove() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            if (((EnemyModel)body.getUserData()).getForRemoval()) {
                model.remove((EnemyModel)body.getUserData());
                world.destroyBody(body);
            }
        }
    }

    public void moveHeroLeft(){
        //hero.setTransform(hero.getX()-1, hero.getY(),0);
        //hero.body.applyForceToCenter(-20f,0,true);
        moveState = mov.MS_LEFT;

        /*
        float vel = hero.body.getLinearVelocity().x;
        float desiredVel = -5;
        float velChange = desiredVel - vel;
        float force = hero.body.getMass() * velChange / (1/60f); //f = mv/t
        hero.body.applyForceToCenter(force,0,true);
        */

        if(model.getHeroModel().getState() != HeroModel.state.JUMPING)
            model.getHeroModel().setState(HeroModel.state.WALKING);
        model.getHeroModel().setFlip(true);
    }

    public void moveHeroRight(){
        //hero.setTransform(hero.getX()+1, hero.getY(),0);
        //hero.body.applyForceToCenter(20f,0,true);
        moveState = mov.MS_RIGHT;

        /*
        float vel = hero.body.getLinearVelocity().x;
        float desiredVel = 5;
        float velChange = desiredVel - vel;
        float force = hero.body.getMass() * velChange / (1/60f); //f = mv/t
        hero.body.applyForceToCenter(force,0,true);
        */

        if(model.getHeroModel().getState() != HeroModel.state.JUMPING)
            model.getHeroModel().setState(HeroModel.state.WALKING);
        model.getHeroModel().setFlip(false);
    }


    public void jumpHero(){
        if(!hero.getState() && hero.body.getLinearVelocity().y == 0) { //it isn't jumping or falling
            hero.setState();
            hero.body.applyForceToCenter(0,200f, true);
            model.getHeroModel().setState(HeroModel.state.JUMPING);
        }
    }


    public World getWorld() {
        return world;
    }
    public HeroBody getHeroBody() { return hero; }
    public ElementBody[] getEnemiesBody() { return enemies; }
}
