package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;


public abstract class StatusElementsView {

    protected static final int VIEWPORT_WIDTH = 1000;
    protected final float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
    protected Stage stage;
    protected Image img;
    protected Viewport viewport;
    protected OrthographicCamera camera;

    public abstract void update(Object obj);
    public void draw() {
        stage.draw();
    }
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
    }
    public void dispose() {
        stage.dispose();
    }
}
