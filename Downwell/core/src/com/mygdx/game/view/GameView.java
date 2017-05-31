package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.BatModel;
import com.mygdx.game.model.BubbleModel;
import com.mygdx.game.model.BulletModel;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.EnemyModel;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.HeroModel;
import com.mygdx.game.model.MapTileModel;
import com.mygdx.game.model.SnailModel;
import com.mygdx.game.view.HeroView;

import java.util.ArrayList;


public class GameView extends ScreenAdapter{

    private static final boolean DEBUG_PHYSICS = true;
    public final static float PIXEL_TO_METER = 0.04f;
    private static final float VIEWPORT_WIDTH = 33;     //66 full map; 10 zoom
    //private static final float VIEWPORT_HEIGHT = 20;

    private final Downwell game;
    private final GameModel model;
    private final GameController controller;

    private HealthBarView healthBar;

    private final OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugCamera;
    private ArrayList<EnemyView> enemyViews;

    public GameView(Downwell game, GameModel model, GameController controller) {
        this.game = game;
        this.model = model;
        this.controller = controller;

        loadAssets();

        this.healthBar = new HealthBarView(game);

        camera = createCamera();
        ArrayList<EnemyModel> enemyModel = model.getEnemies();
        enemyViews = new ArrayList<EnemyView>();
        for(int i = 0; i < enemyModel.size(); i++){
            if(enemyModel.get(i) instanceof BatModel)
                enemyViews.add(new BatView(game));
            else if(enemyModel.get(i) instanceof BubbleModel)
                enemyViews.add(new BubbleView(game));
            else if(enemyModel.get(i) instanceof SnailModel)
                enemyViews.add(new SnailView(game));
        }
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));
        //OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_HEIGHT / PIXEL_TO_METER );

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        //camera.position.set(0,0,0);
        camera.update();

        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

    private void loadAssets() {
        this.game.getAssetManager().load( "1.png" , Texture.class);
        this.game.getAssetManager().load( "2.png" , Texture.class);
        this.game.getAssetManager().load( "3.png" , Texture.class);
        this.game.getAssetManager().load( "4.png" , Texture.class);
        this.game.getAssetManager().load( "5.png" , Texture.class);
        this.game.getAssetManager().load( "6.png" , Texture.class);
        this.game.getAssetManager().load( "7.png" , Texture.class);

        this.game.getAssetManager().load( "r1.png" , Texture.class);
        this.game.getAssetManager().load( "r2.png" , Texture.class);
        this.game.getAssetManager().load( "r3.png" , Texture.class);
        this.game.getAssetManager().load( "r4.png" , Texture.class);
        this.game.getAssetManager().load( "r5.png" , Texture.class);
        this.game.getAssetManager().load( "r6.png" , Texture.class);
        this.game.getAssetManager().load( "r7.png" , Texture.class);

        this.game.getAssetManager().load( "jr1.png" , Texture.class);
        this.game.getAssetManager().load( "jr2.png" , Texture.class);
        this.game.getAssetManager().load( "jr3.png" , Texture.class);
        this.game.getAssetManager().load( "jr4.png" , Texture.class);
        this.game.getAssetManager().load( "jr5.png" , Texture.class);
        this.game.getAssetManager().load( "jr6.png" , Texture.class);
        this.game.getAssetManager().load( "jr7.png" , Texture.class);

        this.game.getAssetManager().load( "jump.png" , Texture.class);

        this.game.getAssetManager().load( "bat1.png" , Texture.class);
        this.game.getAssetManager().load( "bat2.png" , Texture.class);
        this.game.getAssetManager().load( "bat3.png" , Texture.class);
        this.game.getAssetManager().load( "bat4.png" , Texture.class);
        this.game.getAssetManager().load( "bat5.png" , Texture.class);

        this.game.getAssetManager().load( "snail.png", Texture.class);
        this.game.getAssetManager().load( "bubble.png", Texture.class);

        this.game.getAssetManager().load( "berserk-mark-brand-of-sacrifice_1.jpg", Texture.class);
        this.game.getAssetManager().load( "big bullet.png", Texture.class);
        this.game.getAssetManager().load( "shooting.png", Texture.class);

        this.game.getAssetManager().load("dBlock.png", Texture.class);
        this.game.getAssetManager().load("iBlock.png", Texture.class);

        this.game.getAssetManager().load("sideWall.png", Texture.class);

        this.game.getAssetManager().load("lifebar1.png", Texture.class);
        this.game.getAssetManager().load("lifebar2.png", Texture.class);
        this.game.getAssetManager().load("lifebar3.png", Texture.class);
        this.game.getAssetManager().load("lifebar4.png", Texture.class);

        this.game.getAssetManager().finishLoading();
    }

    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.set(camera.position.x-2, camera.position.y,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.set(camera.position.x+2, camera.position.y,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.set(camera.position.x, camera.position.y+2,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.set(camera.position.x, camera.position.y-2,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            controller.moveHeroLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            controller.moveHeroRight();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            controller.jumpHero();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.K)){
            controller.shootHero();
        }
    }

    @Override
    public void render(float delta) {
        handleInputs(delta);

        controller.update(delta);
        if(model.getGameOver()){
            game.startGame();
            return;
        }
        camera.position.set(GameController.ARENA_WIDTH/2f / PIXEL_TO_METER, model.getHeroModel().getY() / PIXEL_TO_METER, 0);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 1/255f, 1/255f, 1/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        healthBar.update(model.getHeroModel());
        healthBar.draw();
        game.getBatch().end();


        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(controller.getWorld(), debugCamera);
        }
    }

    private void drawEntities() {

        MapTileModel[][] map =  model.getMap();
        for(int i = 0; i < model.getDepth(); i++){
            for(int j = 0; j < model.getWidth(); j++)
                if (map[i][j] != null) {
                    ElementView view = ViewFactory.makeView(game, map[i][j]);
                    view.update(map[i][j]);
                    view.draw(game.getBatch());
                }
        }

        ArrayList<EnemyModel> enemies = model.getEnemies();

        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i) == null){
                enemyViews.remove(i);
                enemies.remove(i);
                i--;
                continue;
            }
            enemyViews.get(i).update(enemies.get(i));
            enemyViews.get(i).act(0.1f); //pq 0.3 e nao outro...0.4 fica mt rapido na mesma
            enemyViews.get(i).draw(game.getBatch());
        }

        ArrayList<BulletModel> bullets = model.getBullets();
        for(int i = 0; i < bullets.size(); i++){
            ElementView view = ViewFactory.makeView(game, bullets.get(i));
            view.update(bullets.get(i));
            view.draw(game.getBatch());
        }

        HeroModel hero = model.getHeroModel();
        ElementView view = ViewFactory.makeView(game, hero);
        view.update(hero);
        view.act(0.1f);
        view.draw(game.getBatch());

    }

    private void drawBackground() {
        Texture background = game.getAssetManager().get("berserk-mark-brand-of-sacrifice_1.jpg", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(GameController.ARENA_WIDTH / PIXEL_TO_METER), (int) (GameController.ARENA_HEIGHT / PIXEL_TO_METER));

    }
}
