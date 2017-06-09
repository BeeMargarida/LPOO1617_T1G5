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
 * MainMenuScreen is a class that deals with the view of the main menu screen, dealing with the input in it, the images displayed in
 * it and the launching of the game if the PLAY option is chose, or the exit of the game if that option is chose.
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

    /**
     * Constructor of the class, loads the sound, creates a camera and viewport.
     * @param game Downwell game, has the assets
     */
    public MainMenuScreen(Downwell game){
        this.game = game;
        loadSoundFX();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();
        viewport.apply();
    }

    /**
     * Loads the sounds of the menu and startGame.
     */
    private void loadSoundFX() {
        menuSound = game.getAssetManager().get("menuselect.wav");
        startGameSound = game.getAssetManager().get("gamestart.ogg");
    }

    /**
     * Handles the inputs, updates the camera and draws the background image and entities.
     * @param delta time interval
     */
    @Override
    public void render(float delta) {

        handleInputs(delta);

        Gdx.gl.glClearColor( 1/255f, 1/255f, 1/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        drawBackground();
        drawEntities();
        game.getBatch().end();
    }

    /**
     * Gets the image background and draws it.
     */
    private void drawBackground() {
        Texture background = game.getAssetManager().get("mainMenuBackground.png", Texture.class);
        game.getBatch().draw(background,VIEWPORT_WIDTH/4,0);
    }

    /**
     * Draws a box around the text if said text is selected.
     */
    private void drawEntities() {
        if(option == -1)
            return;
        Texture box = game.getAssetManager().get("sBox.png", Texture.class);
        game.getBatch().draw(box, optionWidth, optionHeight[option]);
    }

    /**
     * Handles the keyboard input.
     * @param delta time interval
     */
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

    /**
     * Resize the viewport with the new dimensions and updates the camera to center it.
     * @param width new width
     * @param height new height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();
    }

    /**
     * Override method, not used.
     */
    @Override
    public void show() {

    }

    /**
     * Override method, not used.
     */
    @Override
    public void hide() {

    }

    /**
     * Override method, not used.
     */
    @Override
    public void pause() {

    }

    /**
     * Override method, not used.
     */
    @Override
    public void resume() {

    }

    /**
     * Disposes of the menuSound sound.
     */
    @Override
    public void dispose() {
        menuSound.dispose();
    }
}
