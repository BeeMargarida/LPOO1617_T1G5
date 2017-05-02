package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.view.HeroView;


public class GameView extends ScreenAdapter{

    private static final boolean DEBUG_PHYSICS = true;
    public final static float PIXEL_TO_METER = 0.04f;
    private static final float VIEWPORT_WIDTH = 66;

    private final Downwell game;
    private final GameModel model;
    private final GameController controller;

    private final HeroView heroView;
    private final BatView batView;

    private final OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugCamera;

    public GameView(Downwell game, GameModel model, GameController controller) {
        this.game = game;
        this.model = model;
        this.controller = controller;

        loadAssets();
        heroView = new HeroView(game);
        batView = new BatView(game);

        camera = createCamera();
    }

    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

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

        this.game.getAssetManager().load( "bat1.png" , Texture.class);
        this.game.getAssetManager().load( "bat2.png" , Texture.class);
        this.game.getAssetManager().load( "bat3.png" , Texture.class);
        this.game.getAssetManager().load( "bat4.png" , Texture.class);
        this.game.getAssetManager().load( "bat5.png" , Texture.class);

        this.game.getAssetManager().load( "berserk-mark-brand-of-sacrifice_1.jpg", Texture.class);

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
    }

    @Override
    public void render(float delta) {
        handleInputs(delta);

        controller.update(delta);

        //camera.position.set(0/*model.getHeroModel().getX() / PIXEL_TO_METER*/, model.getHeroModel().getY() / PIXEL_TO_METER, 0);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(controller.getWorld(), debugCamera);
        }
    }

    private void drawEntities() {
        heroView.update(model.getHeroModel());
        heroView.act(0.1f);
        heroView.draw(game.getBatch());
        batView.update(model.getBatModel());
        batView.act(0.1f);
        batView.draw(game.getBatch());
    }

    private void drawBackground() {
        Texture background = game.getAssetManager().get("berserk-mark-brand-of-sacrifice_1.jpg", Texture.class);
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(GameController.ARENA_WIDTH / PIXEL_TO_METER), (int) (GameController.ARENA_HEIGHT / PIXEL_TO_METER));

    }
}
