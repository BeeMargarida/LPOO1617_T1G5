package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Downwell;

/**
 * Created by Utilizador on 01/06/2017.
 */

public class ResultsScreen implements Screen {

    private Downwell game;
    private Stage stage;
    private float timerToContinue;
    private static final int RESULTS_WIDTH = 350;
    private static final float TIME_TO_CONTINUE = 3f;

    public ResultsScreen(Downwell game, int score, int level, int kills){
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        loadAssets();
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
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void loadAssets(){
        this.game.getAssetManager().load("MenuImage.jpg", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    private void addBackgroundImage(){
        Image image = new Image(game.getAssetManager().get(("MenuImage.jpg"), Texture.class));
        image.setWidth(Gdx.graphics.getWidth()/3f);
        image.setHeight(Gdx.graphics.getHeight());
        image.setPosition(Gdx.graphics.getWidth()/2f-image.getWidth()/2f,0);
        stage.addActor(image);
    }

    private void addLevelLabel(int level){
        Label label = new Label("Level: "+level, new Skin(Gdx.files.internal("uiskin.json")));
        label.setWidth(20);
        label.setHeight(20);
        label.setPosition(RESULTS_WIDTH,300);
        stage.addActor(label);
    }

    private void addScoreLabel(int score){
        Label label = new Label("Score: "+score, new Skin(Gdx.files.internal("uiskin.json")));
        label.setWidth(20);
        label.setHeight(20);
        label.setPosition(RESULTS_WIDTH,275);
        stage.addActor(label);
    }

    private void addKillsLabel(int kills){
        Label label = new Label("Kills: "+kills, new Skin(Gdx.files.internal("uiskin.json")));
        label.setWidth(20);
        label.setHeight(20);
        label.setPosition(RESULTS_WIDTH,250);
        stage.addActor(label);
    }

    private void addContinueLabel(){
        Label label = new Label("Press Enter to continue", new Skin(Gdx.files.internal("uiskin.json")));
        label.setWidth(50);
        label.setHeight(20);
        label.setPosition(RESULTS_WIDTH,200);
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

    }
}
