package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.BatModel;
import com.mygdx.game.model.BubbleModel;
import com.mygdx.game.model.BulletModel;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.EnemyModel;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.HeroModel;
import com.mygdx.game.model.MapTileModel;
import com.mygdx.game.model.SnailModel;

import java.util.ArrayList;

import static com.mygdx.game.model.HeroModel.state.JUMPING;
import static com.mygdx.game.model.HeroModel.state.ROLLING;
import static com.mygdx.game.model.HeroModel.state.STANDING;
import static com.mygdx.game.model.HeroModel.state.WALKING;

/**
 * GameController class controls everything related to the physics of the game. It controls all collisions between
 * bodies and updates their status and positions.
 */
public class GameController implements ContactListener {

    private enum mov { MS_LEFT, MS_RIGHT, MS_STOP}

    private static final float MOV_SPEED = 5f;
    private static final float MAX_SPEED = -9f;
    private static final float PUSH_SPEED = 5f;
    private static final float BOUNCE_SPEED = 3f;
    public static final float BULLET_SPEED = 10f;
    public static final int MAX_SHOTS = 8;
    private static final float TIME_BETWEEN_SHOTS = .15f;

    private final World world;
    private final GameModel model;
    private final HeroBody hero;
    private mov moveState;

    private ArrayList<ElementBody> enemies;
    private float accumulator;
    private float timeToNextShoot;
    private int shots;

    /**
     * Constructor that fill the physics world with the bodies of the game entities, given by the GameModel.
     * @param model GameModel that contain all the models of the game entities
     * @see GameController#fillWorld()
     */
    public GameController(GameModel model){
        world = new World(new Vector2(0,-15f),true);
        hero = new HeroBody(world,model.getHeroModel());
        enemies = new ArrayList<ElementBody>();
        this.model = model;
        fillWorld();
        moveState = mov.MS_STOP;
        timeToNextShoot = 0;
        shots = MAX_SHOTS;
        world.setContactListener(this);
    }

    /**
     * Fills the world with all the entities of the game, contained in the GameModel. It adds to the world the
     * map tiles and the enemies, giving them 0 in gravity.
     */
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
                enemies.add(new BatBody(world, (BatModel) model.getEnemies().get(i)));
                enemies.get(i).body.setGravityScale(0);
            }
            else if(model.getEnemies().get(i) instanceof BubbleModel) {
                enemies.add(new BubbleBody(world, (BubbleModel) model.getEnemies().get(i)));
                enemies.get(i).body.setGravityScale(0);
            }
            else if(model.getEnemies().get(i) instanceof SnailModel) {
                enemies.add(new SnailBody(world, (SnailModel) model.getEnemies().get(i)));
                enemies.get(i).body.setGravityScale(0);
            }
        }
    }

    /**
     * Goes through all the enemies, calls their method of update and updates their velocity with the result of that call.
     * For the Snail Enemy, it sets it's linear velocity according to the direction it is going.
     */
    private void enemiesUpdate() {
        int j = 0;
        for(int i = 0; i < model.getEnemies().size(); i++){
            float[] res;
            if(model.getEnemies().get(i) != null) {
                res = model.getEnemies().get(i).update(hero);
                if(model.getEnemies().get(i) instanceof SnailModel){
                    enemies.get(j).body.setLinearVelocity(0, ((SnailModel) model.getEnemies().get(i)).getDirection());
                }
                if(res[1] != 0 && res[0] != 0) {
                    enemies.get(j).body.setLinearVelocity(-res[0],-res[1]);
                }
                j++;
            }
        }
    }

    /**
     * Updates the bullets, decreasing their time of live and, if that time has ended, set the bullet for removal.
     * @param delta time interval
     */
    private void bulletsUpdate(float delta){
        ArrayList<BulletModel> bullets = model.getBullets();
        for(int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).decreaseTimeToLive(delta)){
                bullets.get(i).setForRemoval(true);
            }
        }
    }

    /**
     * Updates all the entities of game, makes a step on the world, updates the velocities of the hero and it's direction,
     * updates the state of the hero and the positions in the world of every body in it.
     * @param delta time interval
     */
    public void update(float delta){
        model.checkGameStatus();
        remove();
        model.getHeroModel().update(delta);
        enemiesUpdate();
        bulletsUpdate(delta);

        if(timeToNextShoot >= 0)
            timeToNextShoot -= delta;

        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);

            float vel = hero.body.getLinearVelocity().x;
            float desiredVel = 0;
            switch ( moveState )
            {
                case MS_LEFT:  desiredVel = -MOV_SPEED; break;
                case MS_STOP:  desiredVel = 0; break;
                case MS_RIGHT: desiredVel =  MOV_SPEED; break;
            }
            float velChange = desiredVel - vel;
            float force = hero.body.getMass() * velChange / (1/60f); //f = mv/t
            hero.body.applyForceToCenter(force,0,true);
            moveState = mov.MS_STOP;

            accumulator -= 1/60f;
        }

        if(Math.abs(hero.body.getLinearVelocity().y) > 0.2) {
            if(model.getHeroModel().getState() != ROLLING)
                model.getHeroModel().setState(JUMPING);
        }

        if(model.getHeroModel().getState() != JUMPING && model.getHeroModel().getState() != ROLLING){
            if(hero.body.getLinearVelocity().x == 0)
                model.getHeroModel().setState(STANDING);
            else
                model.getHeroModel().setState(WALKING);

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

    /**
     * Updates the stats of the kills and score of the level.
     * @param points number of points to be added to the score
     */
    private void updateStats(int points){
        model.getStats().incScore(points);
        model.getStats().incKills();
    }

    /**
     * Applies to the hero a positive vertical force whenever the hero shoots a bullet or collides with a enemy with a kill blow.
     * If the variable shot is true (when the hero shot a bullet), the hero state changes to jumping. When it
     * is false (collision with a enemy for a kill blow), the hero state changes to rolling.
     * @param shot variable that tells if the hero shot a bullet or if it collided with a enemy for a kill blow
     */
    private void bounceHero(boolean shot){
        float velChange = BOUNCE_SPEED - hero.body.getLinearVelocity().y;
        float force = hero.body.getMass() * velChange / (1/60f);
        hero.body.applyForceToCenter(0,force, true);
        if(shot)
            model.getHeroModel().setState(JUMPING);
        else
            model.getHeroModel().setState(ROLLING);
    }

    /**
     * Calculates the velocity to be applied to the hero when it collides with a enemy, and applies it to it's body.
     * @param posXHero x coordinate of the hero
     * @param posYHero y coordinate of the hero
     * @param posXEnemy x coordinate of the enemy the hero collided with
     * @param posYEnemy y coordinate of the enemy the hero collided with
     */
    private void pushHero(float posXHero, float posYHero, float posXEnemy, float posYEnemy){
        float diffx = posXHero - posXEnemy;
        float diffy = posYHero - posYEnemy;
        double norm = Math.sqrt(diffx*diffx + diffy*diffy);
        float normx = diffx/(float) norm;
        float normy = diffy/(float) norm;
        float velx = normx*PUSH_SPEED;
        float vely = normy*PUSH_SPEED;

        float velChange = velx - hero.body.getLinearVelocity().x;
        float forceX = hero.body.getMass() * velChange / (1/60f);

        velChange = vely - hero.body.getLinearVelocity().y;
        float forceY = hero.body.getMass() * velChange / (1/60f);

        hero.body.applyForceToCenter(forceX,forceY,true);
    }

    /**
     * Verifies if the collision is between the Snail and the Hero or between the Snail and some other body.
     * If the collision is verified, the snail changes it's direction. If the collision is with the hero,
     * the hero takes damage, gets invincible and is pushed.
     * @param contact contains information about the contact, including the bodies
     */
    private void snailBeginContact(Contact contact) {
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
            ((HeroModel) bodyB.getUserData()).damage();
            ((HeroModel) bodyB.getUserData()).setInvincible(true);
            pushHero(((HeroModel) bodyB.getUserData()).getX(), ((HeroModel) bodyB.getUserData()).getY(), ((SnailModel) bodyA.getUserData()).getX(), ((SnailModel) bodyA.getUserData()).getY());
        }
        if(bodyA.getUserData() instanceof  HeroModel && bodyB.getUserData() instanceof SnailModel){
            ((HeroModel) bodyA.getUserData()).damage();
            ((HeroModel) bodyA.getUserData()).setInvincible(true);
            pushHero(((HeroModel) bodyA.getUserData()).getX(), ((HeroModel) bodyA.getUserData()).getY(), ((SnailModel) bodyB.getUserData()).getX(), ((SnailModel) bodyB.getUserData()).getY());
        }
    }

    /**
     * Verifies if the collision is between the Bat and the Hero. If the collision if verified, it checks which
     * parts of the body collided. If the bottom of the hero collides with the top of the Bat, the Bat will be set
     * for removal, the shots of the Hero will be set to maximum and the stats (score) will be update. If that is not
     * the case, the hero will suffer damage and enter invincible mode.
     * @param contact contains information about the contact, including the bodies
     */
    private void batBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof  BatModel && bodyB.getUserData() instanceof HeroModel){
            if(contact.getFixtureA().getUserData() == "up" && contact.getFixtureB().getUserData() == "down") {
                ((BatModel) bodyA.getUserData()).setForRemoval();
                bounceHero(false);
                shots = MAX_SHOTS;
                updateStats(((BatModel) bodyA.getUserData()).getPoints());
            }
            else {
                ((HeroModel) bodyB.getUserData()).damage();
                //pushHero(((HeroModel) bodyB.getUserData()).getX(), ((HeroModel) bodyB.getUserData()).getY(), ((BatModel) bodyA.getUserData()).getX(), ((BatModel) bodyA.getUserData()).getY());
                ((HeroModel) bodyB.getUserData()).setInvincible(true);
            }
        }
        if(bodyA.getUserData() instanceof  HeroModel && bodyB.getUserData() instanceof BatModel){
            if(contact.getFixtureA().getUserData() == "down" && contact.getFixtureB().getUserData() == "up"){
                ((BatModel) bodyB.getUserData()).setForRemoval();
                bounceHero(false);
                shots = MAX_SHOTS;
                updateStats(((BatModel) bodyB.getUserData()).getPoints());
            }
            else {
                ((HeroModel) bodyA.getUserData()).damage();
                //pushHero(((HeroModel) bodyA.getUserData()).getX(), ((HeroModel) bodyA.getUserData()).getY(), ((BatModel) bodyB.getUserData()).getX(), ((BatModel) bodyB.getUserData()).getY());
                ((HeroModel) bodyA.getUserData()).setInvincible(true);
            }
        }
    }

    /**
     * Verifies if the collision is between the Bubble and the Hero. If the collision if verified, it checks which
     * parts of the body collided. If the bottom of the hero collides with the top of the Bubble, the Bubble will be set
     * for removal, the shots of the Hero will be set to maximum and the stats (score) will be update. If that is not
     * the case, the hero will suffer damage and enter invincible mode.
     * @param contact contains information about the contact, including the bodies
     */
    private void bubbleBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof  BubbleModel && bodyB.getUserData() instanceof HeroModel){
            if(contact.getFixtureA().getUserData() == "up" && contact.getFixtureB().getUserData() == "down") {
                ((BubbleModel) bodyA.getUserData()).setForRemoval();
                bounceHero(false);
                shots = MAX_SHOTS;
                updateStats(((BubbleModel) bodyA.getUserData()).getPoints());
            }
            else {
                ((HeroModel) bodyB.getUserData()).damage();
                //pushHero(((HeroModel) bodyB.getUserData()).getX(), ((HeroModel) bodyB.getUserData()).getY(), ((BubbleModel) bodyA.getUserData()).getX(), ((BubbleModel) bodyA.getUserData()).getY());
                ((HeroModel) bodyB.getUserData()).setInvincible(true);
            }
        }
        if(bodyA.getUserData() instanceof  HeroModel && bodyB.getUserData() instanceof BubbleModel){
            if(contact.getFixtureA().getUserData() == "down" && contact.getFixtureB().getUserData() == "up"){
                ((BubbleModel) bodyB.getUserData()).setForRemoval();
                bounceHero(false);
                shots = MAX_SHOTS;
                updateStats(((BubbleModel) bodyB.getUserData()).getPoints());
            }
            else {
                ((HeroModel) bodyA.getUserData()).damage();
                //pushHero(((HeroModel) bodyA.getUserData()).getX(), ((HeroModel) bodyA.getUserData()).getY(), ((BubbleModel) bodyB.getUserData()).getX(), ((BubbleModel) bodyB.getUserData()).getY());
                ((HeroModel) bodyA.getUserData()).setInvincible(true);
            }
        }
    }

    /**
     * Verifies if the collision is between a Bullet and a Enemy or a MapTileModel that is destroyable. If it is
     * with a enemy, that enemy and the bullet will be set for removal and the stats (score) will be updated. If it
     * is with a MapTile that is destroyable, the MapTile and the bullet will be set for removal.
     * @param contact contains information about the contact, including the bodies
     */
    private void bulletBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof BulletModel) {
            if (bodyB.getUserData() instanceof EnemyModel) {
                if(((EnemyModel) bodyB.getUserData()).damage()) {
                    ((EnemyModel) bodyB.getUserData()).setForRemoval();
                    updateStats(((EnemyModel)bodyB.getUserData()).getPoints());
                }
                ((BulletModel) bodyA.getUserData()).setForRemoval(true);
            }
            else if(bodyB.getUserData() instanceof MapTileModel){
                if(((MapTileModel) bodyB.getUserData()).getTileType() == MapTileModel.TileType.D_BLOCK)
                    ((MapTileModel) bodyB.getUserData()).setForRemoval();
                ((BulletModel) bodyA.getUserData()).setForRemoval(true);
            }
            else if(! (bodyB.getUserData() instanceof BulletModel))
                ((BulletModel) bodyA.getUserData()).setForRemoval(true);
        }

        if(bodyB.getUserData() instanceof BulletModel) {
            if (bodyA.getUserData() instanceof EnemyModel) {
                if(((EnemyModel) bodyA.getUserData()).damage()) {
                    ((EnemyModel) bodyA.getUserData()).setForRemoval();
                    updateStats(((EnemyModel) bodyA.getUserData()).getPoints());
                }
                ((BulletModel) bodyB.getUserData()).setForRemoval(true);
            }
            else if(bodyA.getUserData() instanceof MapTileModel){
                if(((MapTileModel) bodyA.getUserData()).getTileType() == MapTileModel.TileType.D_BLOCK)
                    ((MapTileModel) bodyA.getUserData()).setForRemoval();
                ((BulletModel) bodyB.getUserData()).setForRemoval(true);
            }
            else if(! (bodyA.getUserData() instanceof BulletModel))
                ((BulletModel) bodyB.getUserData()).setForRemoval(true);
        }
    }

    /**
     * Calls all the methods that verify collision between enemies and bullets and checks collisions between the
     * hero and the MapTiles. If that collision happens between the bottom of the hero and the top of the MapTile,
     * the hero jumping state is updated, the bullet shots number is back to it's maximum and the hero state is set
     * to be standing. It the collision happens between the top of the hero and the bottom of a destroyable MapTile,
     * the MapTile is set for removal.
     * @param contact contains information about the contact, including the bodies
     */
    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        snailBeginContact(contact);
        batBeginContact(contact);
        bubbleBeginContact(contact);
        bulletBeginContact(contact);

        //Tile
        if (bodyA.getUserData() instanceof HeroModel && bodyB.getUserData() instanceof MapTileModel)
            if(contact.getFixtureA().getUserData() == "down" && contact.getFixtureB().getUserData() == "up") {
                hero.removeState();
                model.getHeroModel().setState(HeroModel.state.STANDING);
                shots = MAX_SHOTS;
            }
            else if(contact.getFixtureA().getUserData() == "up" && contact.getFixtureB().getUserData() == "down") {
                if(((MapTileModel) bodyB.getUserData()).getTileType() == MapTileModel.TileType.D_BLOCK)
                    ((MapTileModel) bodyB.getUserData()).setForRemoval();
            }

        if (bodyA.getUserData() instanceof MapTileModel && bodyB.getUserData() instanceof HeroModel)
            if(contact.getFixtureB().getUserData() == "down" && contact.getFixtureA().getUserData() == "up") {
                hero.removeState();
                model.getHeroModel().setState(HeroModel.state.STANDING);
                shots = MAX_SHOTS;
            }
            else if(contact.getFixtureB().getUserData() == "up" && contact.getFixtureA().getUserData() == "down") {
                if(((MapTileModel) bodyA.getUserData()).getTileType() == MapTileModel.TileType.D_BLOCK)
                    ((MapTileModel) bodyA.getUserData()).setForRemoval();
            }
    }

    /**
     * ContactListener implementation method. Not used.
     * @param contact contains information about the contact, including the bodies
     */
    @Override
    public void endContact(Contact contact) {

    }

    /**
     * ContactListener implementation method. Not used.
     * @param contact contains information about the contact, including the bodies
     * @param oldManifold manifold
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     * ContactListener implementation method. Not used.
     * @param contact contains information about the contact, including the bodies
     * @param impulse manifold
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    /**
     * Goes through all the bodies in the world and check if they are set for removal. If that
     * is the case, their bodies and the respective model will be erased.
     */
    public void remove() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (int i = 0; i < bodies.size; i++) {
            if (bodies.get(i).getUserData() instanceof EnemyModel) {
                if (((EnemyModel) bodies.get(i).getUserData()).getForRemoval()) {
                    model.removeEnemy((EnemyModel) bodies.get(i).getUserData());
                    for(int j = 0; j < enemies.size(); j++){
                        if(enemies.get(j).body.getUserData() == bodies.get(i).getUserData()) {
                            enemies.remove(j);
                            break;
                        }
                    }
                    world.destroyBody(bodies.get(i));
                }
            }
            if(bodies.get(i).getUserData() instanceof BulletModel){
                if(((BulletModel) bodies.get(i).getUserData()).getForRemoval()){
                    model.removeBullet((BulletModel) bodies.get(i).getUserData());
                    world.destroyBody(bodies.get(i));
                }
            }

            if(bodies.get(i).getUserData() instanceof MapTileModel){
                if(((MapTileModel) bodies.get(i).getUserData()).getForRemoval()){
                    model.removeTile((MapTileModel) bodies.get(i).getUserData());
                    world.destroyBody(bodies.get(i));
                }
            }
        }
    }

    /**
     * Sets the state of the hero to moving left and having the animation flip.
     */
    public void moveHeroLeft(){
        moveState = mov.MS_LEFT;
        model.getHeroModel().setFlip(true);
    }

    /**
     * Sets the state of the hero to moving right and having the animation flip.
     */
    public void moveHeroRight(){
        moveState = mov.MS_RIGHT;
        model.getHeroModel().setFlip(false);
    }

    /**
     * Verifies if the hero isn't already jumping and, if that isn't the case, applies a force to the hero
     * and changes the state of the hero according to it's linear velocity in x.
     */
    public void jumpHero() {
        if(!hero.getState() && hero.body.getLinearVelocity().y == 0) {
            hero.setState();
            hero.body.applyForceToCenter(0,700f, true);
            if(Math.abs(hero.body.getLinearVelocity().x) > 0.2)
                model.getHeroModel().setState(ROLLING);
            else
                model.getHeroModel().setState(JUMPING);
        }
    }

    /**
     * If the hero is jumping, the time for next shoot is set, the number of shots is decreased, the hero
     * is applied a force and a bullet is created.
     * @see GameController#bounceHero(boolean)
     */
    public void shootHero() {
        if(hero.getState() || hero.body.getLinearVelocity().y != 0) {
            if(timeToNextShoot < 0 && shots > 0) {
                BulletModel bullet = model.createBullet(model.getHeroModel());
                new BulletBody(world, bullet);
                timeToNextShoot = TIME_BETWEEN_SHOTS;
                bounceHero(true);
                shots--;
            }
        }
    }

    /**
     * Returns the number os shots left to be given.
     * @return number of shots
     */
    public int getShots() {  return shots; }

    /**
     * Returns the world.
     * @return the variable world
     */
    public World getWorld() {
        return world;
    }
}