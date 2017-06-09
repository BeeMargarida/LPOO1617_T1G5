package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;

import static com.mygdx.game.view.GameView.PIXEL_TO_METER;

/**
 * ElementView is a class that deals with the view of each element, containing its sprite and animation and methods to update them.
 */
public abstract class ElementView extends Actor {
    protected Sprite sprite;
    protected Animation<TextureRegion> animation;
    protected boolean flip = false;

    protected float stateTime = 0;
    /**
     * Draws the sprite.
     * @param batch used to draw
     * @param parentAlpha alpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw((batch));
    }

    /**
     * Draws the sprite.
     * @param batch used to draw
     */
    public void draw(SpriteBatch batch){
        sprite.draw((batch));
    }

    /**
     * Creates tje sprite and animation for the element, getting the assets needed from the game.
     * @param game Downwell game, has the assets
     * @return sprite created
     */
    public abstract Sprite createSprite(Downwell game);

    /**
     * Returns de animation of the element.
     * @return animation
     */
    public Animation<TextureRegion> getAnimation() { return animation;}

    /**
     * Centers the dimensions of the sprite with the position of the element in the world.
     * @param model model of the element, has its coordinates
     */
    public void update(ElementModel model) {
        sprite.setCenter(model.getX()/PIXEL_TO_METER, model.getY()/PIXEL_TO_METER);
    }

    /**
     * Updates the frames of animations.
     * @param delta
     */
    public abstract void act(float delta);
}
