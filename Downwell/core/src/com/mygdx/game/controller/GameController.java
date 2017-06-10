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
import com.mygdx.game.model.BatModel;
import com.mygdx.game.model.BubbleModel;
import com.mygdx.game.model.BulletModel;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.EnemyModel;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.HeroModel;
import com.mygdx.game.model.MapTileModel;
import com.mygdx.game.model.SnailModel;

import java.awt.geom.Point2D;
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

    private enum mov { MS_LEFT, MS_RIGHT, MS_STOP, RECOVERY}

    private static final float MOV_SPEED = 5f;
    private static final float MAX_SPEED = -9f;
    private static final float PUSH_SPEED = 5f;
    private static final float BOUNCE_SPEED = 3f;
    public static final float BULLET_SPEED = 10f;
    public static final int MAX_SHOTS = 8;
    private static final float TIME_BETWEEN_SHOTS = .15f;
    private static final float RECOVERY_TIME = 0.1f;

    private final World world;
    private final GameModel model;
    private final HeroBody hero;
    private mov moveState;

    private ArrayList<ElementBody> enemies;
    private float accumulator;
    private float timeToNextShoot;
    private float timeToRecover;
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
        timeToRecover = 0;
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

        if(timeToRecover >= 0) {
            timeToRecover -= delta;
            if(timeToRecover < 0)
                moveState = mov.MS_STOP;
        }

        //System.out.println(delta);
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;

            if(moveState == mov.RECOVERY)
                continue;

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
        float force = hero.body.getMass() * velChange / (1/60f); //f = mv/t
        hero.body.applyForceToCenter(0,force, true);
        if(shot)
            model.getHeroModel().setState(JUMPING);
        else
            model.getHeroModel().setState(ROLLING);
    }

    /**
     * Calculates the velocity to be applied to the hero when it collides with a enemy, and applies it to it's body.
     * @param heroPos coordinates of the hero
     * @param enemyPos coordinates of the enemy the hero collided with
     */
    private void pushHero(Point2D heroPos, Point2D enemyPos){
        Point2D p =  new Point2D.Float(0,0);
        float diffx = (float) heroPos.getX() - (float) enemyPos.getX();
        float diffy = (float) heroPos.getY() - (float) enemyPos.getY();
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

    private void recoverHero(){
        moveState = mov.RECOVERY;
        timeToRecover = RECOVERY_TIME;
    }

    /**
     * Handles the contact between the hero and the snail, giving damage to the hero if that is not in the invincible mode.
     * It then sets the hero with the invincible mode and push it away from the snail.
     * @param snail fixture of the snail
     * @param hero fixture of the hero
     */
    private void handleSnailHeroContact(Fixture snail, Fixture hero){
        Body bodyA = snail.getBody();
        Body bodyB = hero.getBody();

        if(((HeroModel) bodyB.getUserData()).damage()){
            model.getSoundFX().playHeroDamageSound();
            Point2D heroPos = ((HeroModel) bodyB.getUserData()).getPos();
            Point2D enemyPos = ((SnailModel) bodyA.getUserData()).getPos();
            pushHero(heroPos, enemyPos);
            recoverHero();
        }
        ((HeroModel) bodyB.getUserData()).setInvincible(true);
    }

    /**
     * Handles the snail contact width anything, making the snail change directions if that contact is between the upper of bottom
     * part of its fixture.
     * @param snail fixture of the snail
     * @param other fixture of the other entity
     */
    private void handleSnailMapContact(Fixture snail, Fixture other){
        Body bodyA = snail.getBody();

        if(snail.getUserData() == "down" && other.getUserData() == "up"){
            ((SnailModel) bodyA.getUserData()).changeDirection();
        }
        else if(snail.getUserData() == "up" && other.getUserData() == "down") {
            ((SnailModel) bodyA.getUserData()).changeDirection();
        }
    }

    /**
     * Verifies if the collision is between the Snail and the Hero or between the Snail and some other body.
     * It then calls the methods to deal with those situations.
     * @param contact contains information about the contact, including the bodies
     * @see GameController#handleSnailMapContact(Fixture, Fixture)
     * @see GameController#handleSnailHeroContact(Fixture, Fixture)
     */
    private void snailBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof  SnailModel)
            handleSnailMapContact(contact.getFixtureA(), contact.getFixtureB());
        if(bodyB.getUserData() instanceof SnailModel)
            handleSnailMapContact(contact.getFixtureB(), contact.getFixtureA());

        if(bodyA.getUserData() instanceof  SnailModel && bodyB.getUserData() instanceof HeroModel)
            handleSnailHeroContact(contact.getFixtureA(), contact.getFixtureB());
        if(bodyA.getUserData() instanceof  HeroModel && bodyB.getUserData() instanceof SnailModel)
            handleSnailHeroContact(contact.getFixtureB(), contact.getFixtureA());
    }

    /**
     * Handles the collision between Hero and Bat. If the bottom of the hero collides with the top of the Bat, the Bat will be set
     * for removal, the shots of the Hero will be set to maximum and the stats (score) will be update. If that is not
     * the case, the hero will suffer damage and enter invincible mode. It also applies the sound specific to the situation.
     * @param bat bat fixture
     * @param hero hero fixture
     */
    private void handleBatHeroContact(Fixture bat, Fixture hero){
        Body bodyA = bat.getBody();
        Body bodyB = hero.getBody();

        if(bat.getUserData() == "up" && hero.getUserData() == "down") {
            ((BatModel) bodyA.getUserData()).setForRemoval();
            bounceHero(false);
            shots = MAX_SHOTS;
            updateStats(((BatModel) bodyA.getUserData()).getPoints());
            model.getSoundFX().playEnemyExplosionSound();
        }
        else {
            if(((HeroModel) bodyB.getUserData()).damage()) {
                model.getSoundFX().playHeroDamageSound();
                Point2D heroPos = ((HeroModel) bodyB.getUserData()).getPos();
                Point2D enemyPos = ((BatModel) bodyA.getUserData()).getPos();
                pushHero(heroPos, enemyPos);
                recoverHero();
            }
            ((HeroModel) bodyB.getUserData()).setInvincible(true);
        }
    }

    /**
     * Verifies if the collision is between the Bat and the Hero. If the collision if verified, it checks which
     * parts of the body collided, calling for a method to verify that.
     * @param contact contains information about the contact, including the bodies
     * @see GameController#handleBatHeroContact(Fixture, Fixture)
     */
    private void batBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof  BatModel && bodyB.getUserData() instanceof HeroModel){
            handleBatHeroContact(contact.getFixtureA(), contact.getFixtureB());
        }
        if(bodyA.getUserData() instanceof  HeroModel && bodyB.getUserData() instanceof BatModel){
            handleBatHeroContact(contact.getFixtureB(), contact.getFixtureA());
        }
    }

    /**
     * Handles the collision between Hero and Bubble. If the bottom of the hero collides with the top of the Bubble, the Bubble will be set
     * for removal, the shots of the Hero will be set to maximum and the stats (score) will be update. If that is not
     * the case, the hero will suffer damage and enter invincible mode. It also applies the sound specific to the situation.
     * @param bubble bubble fixture
     * @param hero hero fixture
     */
    private void handleBubbleHeroContact(Fixture bubble, Fixture hero){
        Body bodyA = bubble.getBody();
        Body bodyB = hero.getBody();

        if(bubble.getUserData() == "up" && hero.getUserData() == "down") {
            ((BubbleModel) bodyA.getUserData()).setForRemoval();
            bounceHero(false);
            shots = MAX_SHOTS;
            updateStats(((BubbleModel) bodyA.getUserData()).getPoints());
            model.getSoundFX().playEnemyExplosionSound();
        }
        else {
            if(((HeroModel) bodyB.getUserData()).damage()) {
                model.getSoundFX().playHeroDamageSound();
                Point2D heroPos = ((HeroModel) bodyB.getUserData()).getPos();
                Point2D enemyPos = ((BubbleModel) bodyA.getUserData()).getPos();
                pushHero(heroPos, enemyPos);
                recoverHero();
            }
            ((HeroModel) bodyB.getUserData()).setInvincible(true);
        }
    }

    /**
     * Verifies if the collision is between the Bubble and the Hero. If the collision if verified, it checks which
     * parts of the body collided.
     * @param contact contains information about the contact, including the bodies
     * @see GameController#handleBubbleHeroContact(Fixture, Fixture)
     */
    private void bubbleBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof  BubbleModel && bodyB.getUserData() instanceof HeroModel){
            handleBubbleHeroContact(contact.getFixtureA(), contact.getFixtureB());
        }
        if(bodyA.getUserData() instanceof  HeroModel && bodyB.getUserData() instanceof BubbleModel){
            handleBubbleHeroContact(contact.getFixtureB(), contact.getFixtureA());
        }
    }

    /**
     * Verifies if the collision is between a Bullet and a Enemy or a MapTileModel that is destroyable. If it is
     * with a enemy, that enemy and the bullet will be set for removal and the stats (score) will be updated. If it
     * is with a MapTile that is destroyable, the MapTile and the bullet will be set for removal.
     * @param bullet bullet fixture
     * @param other other entity fixture
     */
    private void handleBulletContact(Fixture bullet, Fixture other){
        Body bodyA = bullet.getBody();
        Body bodyB = other.getBody();

        if(bodyA.getUserData() instanceof BulletModel) {
            if (bodyB.getUserData() instanceof EnemyModel) {
                if(((EnemyModel) bodyB.getUserData()).damage()) {
                    ((EnemyModel) bodyB.getUserData()).setForRemoval();
                    updateStats(((EnemyModel)bodyB.getUserData()).getPoints());
                    model.getSoundFX().playEnemyExplosionSound();
                }
                ((BulletModel) bodyA.getUserData()).setForRemoval(true);
            }
            else if(bodyB.getUserData() instanceof MapTileModel){
                if(((MapTileModel) bodyB.getUserData()).getTileType() == MapTileModel.TileType.D_BLOCK) {
                    ((MapTileModel) bodyB.getUserData()).setForRemoval();
                    model.getSoundFX().playBlockExplosionSound();
                }
                ((BulletModel) bodyA.getUserData()).setForRemoval(true);
            }
            else if(! (bodyB.getUserData() instanceof BulletModel))
                ((BulletModel) bodyA.getUserData()).setForRemoval(true);
            model.getSoundFX().playBulletHitSound();
        }
    }

    /**
     * Verifies if the collision happened with a bullet as one of the bodies. If it is, calls the appropriate method.
     * @param contact contains information about the contact, including the bodies
     * @see GameController#handleBulletContact(Fixture, Fixture)
     */
    private void bulletBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof BulletModel)
            handleBulletContact(contact.getFixtureA(), contact.getFixtureB());

        if(bodyB.getUserData() instanceof BulletModel)
            handleBulletContact(contact.getFixtureB(), contact.getFixtureA());
    }

    /**
     * Handles the collision between the hero and the destroyable map tiles. If that collision happens between the bottom of the hero and the top of the MapTile,
     * the hero jumping state is updated, the bullet shots number is back to it's maximum and the hero state is set
     * to be standing. It the collision happens between the top of the hero and the bottom of a destroyable MapTile,
     * the MapTile is set for removal.
     * @param mapTile mapTile fixture
     * @param hero hero fixture
     */
    private void handleMapTileHeroContact(Fixture mapTile, Fixture hero){
        Body bodyA = mapTile.getBody();

        if(hero.getUserData() == "down" && mapTile.getUserData() == "up") {
            this.hero.removeState();
            model.getHeroModel().setState(HeroModel.state.STANDING);
            shots = MAX_SHOTS;
            model.getSoundFX().playLandSound();
        }
        else if(hero.getUserData() == "up" && mapTile.getUserData() == "down") {
            if(((MapTileModel) bodyA.getUserData()).getTileType() == MapTileModel.TileType.D_BLOCK) {
                ((MapTileModel) bodyA.getUserData()).setForRemoval();
                model.getSoundFX().playBlockExplosionSound();
            }
        }
    }

    /**
     * Checks if the collision is between the hero and a map tile and calls the appropriated method.
     * @param contact contains information about the contact, including the bodies
     * @see GameController#bubbleBeginContact(Contact)
     */
    public void mapTileBeginContact(Contact contact){
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof HeroModel && bodyB.getUserData() instanceof MapTileModel)
            handleMapTileHeroContact(contact.getFixtureB(), contact.getFixtureA());

        if (bodyA.getUserData() instanceof MapTileModel && bodyB.getUserData() instanceof HeroModel)
            handleMapTileHeroContact(contact.getFixtureA(), contact.getFixtureB());
    }

    /**
     * Calls all the methods that verify collision between entities.
     * @param contact contains information about the contact, including the bodies
     * @see GameController#snailBeginContact(Contact)
     * @see GameController#batBeginContact(Contact)
     * @see GameController#bubbleBeginContact(Contact)
     * @see GameController#bulletBeginContact(Contact)
     * @see GameController#mapTileBeginContact(Contact)
     */
    @Override
    public void beginContact(Contact contact) {
        snailBeginContact(contact);
        batBeginContact(contact);
        bubbleBeginContact(contact);
        bulletBeginContact(contact);
        mapTileBeginContact(contact);
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
        if(moveState == mov.RECOVERY)
            return;
        moveState = mov.MS_LEFT;
        if(model.getHeroModel().getState() != ROLLING)
            model.getHeroModel().setFlip(true);
    }

    /**
     * Sets the state of the hero to moving right and having the animation flip.
     */
    public void moveHeroRight(){
        if(moveState == mov.RECOVERY)
            return;
        moveState = mov.MS_RIGHT;
        if(model.getHeroModel().getState() != ROLLING)
            model.getHeroModel().setFlip(false);
    }

    /**
     * Verifies if the hero isn't already jumping and, if that isn't the case, applies a force to the hero
     * and changes the state of the hero according to it's linear velocity in x.
     */
    public void jumpHero() {
        if(!hero.getState() && hero.body.getLinearVelocity().y == 0) { //it isn't jumping or falling
            hero.setState();
            hero.body.applyForceToCenter(0,700f, true);
            if(Math.abs(hero.body.getLinearVelocity().x) > 0.2)
                model.getHeroModel().setState(ROLLING);
            else
                model.getHeroModel().setState(JUMPING);
            model.getSoundFX().playJumpSound();
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
                model.getSoundFX().playShootSound();
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
