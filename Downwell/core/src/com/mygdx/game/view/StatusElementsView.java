package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * StatusElementsView is a class that contains the stage, image, viewport and camera regarding elements adjacent to the game, that is,
 * the healthBar and the bulletBar.
 */
public abstract class StatusElementsView {

    protected static final int VIEWPORT_WIDTH = 1000;
    protected final float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
    protected Stage stage;
    protected Image img;
    protected Viewport viewport;
    protected OrthographicCamera camera;

    /**
     * Abstract method, it updates the img according to information of the game.
     * @param obj
     */
    public abstract void update(Object obj);

    /**
     * Draws the elements on the stage.
     */
    public void draw() {
        stage.draw();
    }

    /**
     * Updates the viewport with the new dimensions.
     * @param width new width
     * @param height new height
     */
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
    }

    /**
     * Disposes of the stage.
     */
    public void dispose() {
        stage.dispose();
    }
}
