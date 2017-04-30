package com.mygdx.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.Downwell;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.model.MenuModel;

public class MenuView extends ScreenAdapter{

    private static final boolean DEBUG_PHYSICS = true;
    public final static float PIXEL_TO_METER = 0.04f;
    private static final float VIEWPORT_WIDTH = 66;

    private final Downwell game;
    private final MenuModel model;
    private final MenuController controller;

    private final OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugCamera;

    public MenuView(Downwell game, MenuModel model, MenuController controller){
        this.game = game;
        this.model = model;
        this.controller = controller;

        loadAssets();
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
        game.getAssetManager().load("MenuImage.jpg",Texture.class);
        game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        drawEntities();
        game.getBatch().end();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            //debugRenderer.render(controller.getWorld(), debugCamera);
        }
    }

    private void drawEntities() {
        Texture background = game.getAssetManager().get("MenuImage.jpg", Texture.class);
        //background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        game.getBatch().draw(background, 0, 0, 0, 0, (int)(MenuController.ARENA_WIDTH / PIXEL_TO_METER), (int) (MenuController.ARENA_HEIGHT / PIXEL_TO_METER));
    }
}
