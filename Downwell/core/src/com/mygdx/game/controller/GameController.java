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

import java.util.ArrayList;

import static com.mygdx.game.model.HeroModel.state.JUMPING;
import static com.mygdx.game.model.HeroModel.state.ROLLING;
import static com.mygdx.game.model.HeroModel.state.STANDING;
import static com.mygdx.game.model.HeroModel.state.WALKING;

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

    private void heroUpdate(float delta){
        model.getHeroModel().update(delta);
    }

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

    private void bulletsUpdate(float delta){
        ArrayList<BulletModel> bullets = model.getBullets();
        for(int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).decreaseTimeToLive(delta)){
                bullets.get(i).setForRemoval(true);
            }
        }
    }

    public void update(float delta){
        model.checkGameStatus();
        remove();
        heroUpdate(delta);
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

    private void updateStats(int points){
        model.getStats().incScore(points);
        model.getStats().incKills();
    }

    private void bounceHero(boolean shot){
        float velChange = BOUNCE_SPEED - hero.body.getLinearVelocity().y;
        float force = hero.body.getMass() * velChange / (1/60f); //f = mv/t
        hero.body.applyForceToCenter(0,force, true);
        if(shot)
            model.getHeroModel().setState(JUMPING);
        else
            model.getHeroModel().setState(ROLLING);
    }

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

    private void handleSnailHeroContact(Fixture snail, Fixture hero){
        Body bodyA = snail.getBody();
        Body bodyB = hero.getBody();

        ((HeroModel) bodyB.getUserData()).damage();
        ((HeroModel) bodyB.getUserData()).setInvincible(true);
        pushHero(((HeroModel) bodyB.getUserData()).getX(), ((HeroModel) bodyB.getUserData()).getY(), ((SnailModel) bodyA.getUserData()).getX(), ((SnailModel) bodyA.getUserData()).getY());
        if(!((HeroModel) bodyB.getUserData()).getInvincible())
            model.getSoundFX().playHeroDamageSound();
    }

    private void handleSnailMapContact(Fixture snail, Fixture other){
        Body bodyA = snail.getBody();

        if(snail.getUserData() == "down" && other.getUserData() == "up"){
            ((SnailModel) bodyA.getUserData()).changeDirection();
        }
        else if(snail.getUserData() == "up" && other.getUserData() == "down") {
            ((SnailModel) bodyA.getUserData()).changeDirection();
        }
    }

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
            ((HeroModel) bodyB.getUserData()).damage();
            //pushHero(((HeroModel) bodyB.getUserData()).getX(), ((HeroModel) bodyB.getUserData()).getY(), ((BatModel) bodyA.getUserData()).getX(), ((BatModel) bodyA.getUserData()).getY());
            ((HeroModel) bodyB.getUserData()).setInvincible(true);
            if(!((HeroModel) bodyB.getUserData()).getInvincible())
                model.getSoundFX().playHeroDamageSound();
        }
    }

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
            if(((HeroModel) bodyB.getUserData()).damage());
                model.getSoundFX().playHeroDamageSound();
            //pushHero(((HeroModel) bodyB.getUserData()).getX(), ((HeroModel) bodyB.getUserData()).getY(), ((BubbleModel) bodyA.getUserData()).getX(), ((BubbleModel) bodyA.getUserData()).getY());
            ((HeroModel) bodyB.getUserData()).setInvincible(true);
        }
    }

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

    private void bulletBeginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof BulletModel) {
            handleBulletContact(contact.getFixtureA(), contact.getFixtureB());
        }

        if(bodyB.getUserData() instanceof BulletModel) {
            handleBulletContact(contact.getFixtureB(), contact.getFixtureA());
        }
    }

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

    public void mapTileBeginContact(Contact contact){
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof HeroModel && bodyB.getUserData() instanceof MapTileModel)
            handleMapTileHeroContact(contact.getFixtureB(), contact.getFixtureA());

        if (bodyA.getUserData() instanceof MapTileModel && bodyB.getUserData() instanceof HeroModel)
            handleMapTileHeroContact(contact.getFixtureA(), contact.getFixtureB());
    }

    @Override
    public void beginContact(Contact contact) {
        snailBeginContact(contact);
        batBeginContact(contact);
        bubbleBeginContact(contact);
        bulletBeginContact(contact);
        mapTileBeginContact(contact);
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

    public void moveHeroLeft(){
        moveState = mov.MS_LEFT;
        model.getHeroModel().setFlip(true);
    }

    public void moveHeroRight(){
        moveState = mov.MS_RIGHT;
        model.getHeroModel().setFlip(false);
    }

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

    public int getShots() {  return shots; }
    public World getWorld() {
        return world;
    }
}
