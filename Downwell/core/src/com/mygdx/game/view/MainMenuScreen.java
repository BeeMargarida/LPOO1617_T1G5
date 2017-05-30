package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Downwell;
import com.mygdx.game.controller.GameController;

/**
 * Created by Utilizador on 30/05/2017.
 */

public class MainMenuScreen implements Screen {

    private final static int OPTION_HEIGHT = 40;
    private final static int OPTION_WIDTH = 150;
    private final static int optionWidth = 245;
    private final int optionHeight[] = {198, 158};
    private Downwell game;
    private int option = -1;
    private int posX = -1;
    private int posY = -1;
    private boolean controlMethod = true;

    public MainMenuScreen(Downwell game){
        this.game = game;
        loadAssets();
    }
    @Override
    public void render(float delta) {
        //super.render(delta);;

        handleInputs(delta);
        update();

        Gdx.gl.glClearColor( 1/255f, 1/255f, 1/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );


        game.getBatch().begin();
        drawBackground();
        drawEntities();
        game.getBatch().end();
    }


    private void loadAssets(){
        this.game.getAssetManager().load( "sBox.png" , Texture.class);
        this.game.getAssetManager().load( "mainMenuBackground.png" , Texture.class);
        this.game.getAssetManager().load( "berserk-mark-brand-of-sacrifice_1.jpg", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    private void drawBackground() {
        Texture background = game.getAssetManager().get("mainMenuBackground.png", Texture.class);
        game.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void update(){
        if(controlMethod) {
            int posY = Gdx.graphics.getHeight()-this.posY;
            for (int i = 0; i < optionHeight.length; i++) {
                if (posX < (optionWidth + OPTION_WIDTH) && posX > optionWidth && posY < (optionHeight[i] + OPTION_HEIGHT) && posY > optionHeight[i]) {
                    option = i;
                    return;
                }
            }
            option = -1;
        }
    }
    private void drawEntities() {
        if(option == -1)
            return;
        Texture box = game.getAssetManager().get("sBox.png", Texture.class);
        //System.out.println(Gdx.graphics.getWidth()/2-box.getWidth()/2);
        game.getBatch().draw(box, optionWidth, optionHeight[option]);
    }

    private void handleInputs(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if(!controlMethod) {
                if (option > 0)
                    option--;
            }
            else{
                controlMethod = false;
                option = 0;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if(!controlMethod) {
                if (option < 1)
                    option++;
            }
            else{
                controlMethod = false;
                option = 0;
            }
        }

        int newX = Gdx.input.getX();
        int newY = Gdx.input.getY();
        if(posX != newX || posY != newY){
            controlMethod = true;
            option = -1;
        }
        posX = newX;
        posY = newY;
    }

    @Override
    public void resize(int width, int height) {
        //super.resize(width, height);
    }

    @Override
    public void show() {
        //super.show();
    }

    @Override
    public void hide() {
        //super.hide();
    }

    @Override
    public void pause() {
        //super.pause();
    }

    @Override
    public void resume() {
        //super.resume();
    }

    @Override
    public void dispose() {
        //super.dispose();
    }
}
