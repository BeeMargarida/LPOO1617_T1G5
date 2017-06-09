package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Downwell;

/**
 * ResultsScreen is a class that deals with the view of the stats at the end of the game.
 */
public class ResultsScreen implements Screen {

    private static final float VIEWPORT_WIDTH = 1280;
    private static final float VIEWPORT_HEIGHT = 720;
    private Downwell game;
    private Stage stage;
    private float timerToContinue;
    private static final int RESULTS_WIDTH = (int) (VIEWPORT_WIDTH/2);
    private static final float TIME_TO_CONTINUE = 3f;
    private Viewport viewport;
    private OrthographicCamera camera;

    private BitmapFont font;

    /**
     * Constructor of the class, it creates a viewport, a camera and a stage. It also creates a font and adds all the entities
     * (background, level, score and kills, the lsat three represented as labels) to the stage created.
     * @param game
     * @param score
     * @param level
     * @param kills
     */
    public ResultsScreen(Downwell game, int score, int level, int kills){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();
        this.stage = new Stage(viewport);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("5x5.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 32;
        font = generator.generateFont(parameter);
        generator.dispose();

        addBackgroundImage();
        addLevelLabel(level);
        addScoreLabel(score);
        addKillsLabel(kills);
        timerToContinue = 0;
    }

    /**
     * Updates the view and draws all the entities.
     * @param delta time interval
     */
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor( 1/255f, 1/255f, 1/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        game.getBatch().setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();
    }

    /**
     * Disposes of the stage.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Gets the background image, sets its position and adds it to the stage.
     */
    private void addBackgroundImage(){
        Image image = new Image(game.getAssetManager().get(("scoreImage_2.jpg"), Texture.class));
        image.setPosition(0,0);
        stage.addActor(image);
    }

    /**
     * Adds to the stage a label with the number of the level reached.
     * @param level number of the level reached
     */
    private void addLevelLabel(int level){
        Label label = new Label("Level: "+level,new Label.LabelStyle(font, Color.WHITE));
        label.setWidth(40);
        label.setHeight(40);
        label.scaleBy(2);
        label.setPosition(RESULTS_WIDTH,500);
        stage.addActor(label);
    }

    /**
     * Adds to the stage a label with the score acquired.
     * @param score score acquired through thr game
     */
    private void addScoreLabel(int score){
        Label label = new Label("Score: "+score,new Label.LabelStyle(font, Color.WHITE));
        label.setWidth(40);
        label.setHeight(40);
        label.scaleBy(2);
        label.setPosition(RESULTS_WIDTH,450);
        stage.addActor(label);
    }

    /**
     * Adds to the stage a label with the number of kills made.
     * @param kills number of kills made through the game
     */
    private void addKillsLabel(int kills){
        Label label = new Label("Kills: "+kills,new Label.LabelStyle(font, Color.WHITE));
        label.setWidth(40);
        label.setHeight(40);
        label.scaleBy(2);
        label.setPosition(RESULTS_WIDTH,400);
        stage.addActor(label);
    }

    /**
     * Adds to the stage a label with the information to press Enter to continue.
     */
    private void addContinueLabel(){
        Label label = new Label("Press  Enter",new Label.LabelStyle(font, Color.WHITE));
        label.setWidth(100);
        label.setHeight(40);
        label.scaleBy(2);
        label.setPosition(RESULTS_WIDTH,300);
        stage.addActor(label);
    }

    /**
     * If the timeToContinue has passed, a label will be showed to inform the user that it can press Enter to continue to the MainMenu.
     * It then handles the inputs. If the timeToContinue hasn't passed, it will be incremented with the delta.
     * @param delta time interval
     */
    private void update(float delta){
        if(timerToContinue >= TIME_TO_CONTINUE) {
            addContinueLabel();
            handleInputs();
        }
        else
            timerToContinue += delta;
    }

    /**
     * If the Enter key was pressed, a request is made to the game to forward to the Main Menu.
     */
    private void handleInputs(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            game.setMainMenuScreen();
        }
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
     * Resize the viewport with the new dimensions and updates the camera to center it.
     * @param width new width
     * @param height new height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        camera.update();
    }
}
