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

    public ResultsScreen(Downwell game, int score, int level, int kills){
        this.game = game;
        //float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
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

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor( 1/255f, 1/255f, 1/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        game.getBatch().setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void addBackgroundImage(){
        Image image = new Image(game.getAssetManager().get(("scoreImage_2.jpg"), Texture.class));
        image.setPosition(0,0);
        stage.addActor(image);
    }

    private void addLevelLabel(int level){
        Label label = new Label("Level: "+level,new Label.LabelStyle(font, Color.WHITE));
        label.setWidth(40);
        label.setHeight(40);
        label.scaleBy(2);
        label.setPosition(RESULTS_WIDTH,500/*300*/);
        stage.addActor(label);
    }

    private void addScoreLabel(int score){
        Label label = new Label("Score: "+score,new Label.LabelStyle(font, Color.WHITE));
        label.setWidth(40);
        label.setHeight(40);
        label.scaleBy(2);
        label.setPosition(RESULTS_WIDTH,450/*275*/);
        stage.addActor(label);
    }

    private void addKillsLabel(int kills){
        Label label = new Label("Kills: "+kills,new Label.LabelStyle(font, Color.WHITE));
        label.setWidth(40);
        label.setHeight(40);
        label.scaleBy(2);
        label.setPosition(RESULTS_WIDTH,400/*250*/);
        stage.addActor(label);
    }

    private void addContinueLabel(){
        Label label = new Label("Press  Enter",new Label.LabelStyle(font, Color.WHITE));
        label.setWidth(100);
        label.setHeight(40);
        label.scaleBy(2);
        label.setPosition(RESULTS_WIDTH,300/*200*/);
        stage.addActor(label);
    }

    private void update(float delta){
        if(timerToContinue >= TIME_TO_CONTINUE) {
            addContinueLabel();
            handleInputs();
        }
        else
            timerToContinue += delta;
    }

    private void handleInputs(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            game.setMainMenuScreen();
        }
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
    public void resize(int width, int height) {
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }
}
