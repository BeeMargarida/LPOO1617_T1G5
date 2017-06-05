package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Downwell;

/**
 * Created by Utilizador on 30/05/2017.
 */

public class MainMenuScreen implements Screen {

    private static final float VIEWPORT_WIDTH = 638;
    private static final float VIEWPORT_HEIGHT = 543;

    private final static int OPTION_HEIGHT = 40;
    private final static int OPTION_WIDTH = 150;
    private final static int optionWidth = (int) VIEWPORT_WIDTH/4 + 80;
    private final int optionHeight[] = {(int) VIEWPORT_HEIGHT/2 - 45, (int) VIEWPORT_HEIGHT/2 - 90};
    private int option = 0;
    private Downwell game;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Sound menuSound;
    private Sound startGameSound;

    public MainMenuScreen(Downwell game){
        this.game = game;
        loadSoundFX();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();
        viewport.apply();
    }

    private void loadSoundFX() {
        menuSound = game.getAssetManager().get("menuselect.wav");
        startGameSound = game.getAssetManager().get("gamestart.ogg");
    }

    @Override
    public void render(float delta) {

        handleInputs(delta);
        update();

        Gdx.gl.glClearColor( 1/255f, 1/255f, 1/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        game.getBatch().end();
    }

    private void drawBackground() {
        Texture background = game.getAssetManager().get("mainMenuBackground.png", Texture.class);
        game.getBatch().draw(background,VIEWPORT_WIDTH/4,0);
    }

    private void update(){
        camera.update();
    }

    private void drawEntities() {
        if(option == -1)
            return;
        Texture box = game.getAssetManager().get("sBox.png", Texture.class);
        game.getBatch().draw(box, optionWidth, optionHeight[option]);
    }

    private void handleInputs(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (option > 0) {
                option--;
                menuSound.play();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (option < 1) {
                option++;
                menuSound.play();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if(option == 0) {
                startGameSound.play();
                game.setGameScreen();
            }
                //handler.setResultsScreen();
            else if(option == 1)
                System.exit(0);
                //game.exit();
        }
    }

    /*
    private void playMenuSFX(){
        Sound sound = game.getAssetManager().get("menuselect.wav");
        sound.play()
    }
    */

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        menuSound.dispose();
    }
}
