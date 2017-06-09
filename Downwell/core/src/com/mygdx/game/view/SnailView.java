package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.SnailModel;

/**
 * SnailView is a class that deals with the view of the snail, including the flips regarding the change in direction and its sprite.
 */
public class SnailView extends EnemyView {

    private boolean vflip = false;

    /**
     * Constructor of the class, calls the contructor of the super class.
     * @param game Downwell game, has the assets
     */
    public SnailView(Downwell game) {
        super(game);
    }

    /**
     * Gets the texture needed and creates the sprite with it.
     * @param game Downwell game, has the assets
     * @return sprite created
     */
    @Override
    public Sprite createSprite(Downwell game) {
        Texture texture = game.getAssetManager().get("snail.png");
        animation = null;
        sprite = new Sprite(texture);
        return sprite;
    }

    /**
     * Centers the sprite in the position of the model and checks if a flip will be needed according to it's direction.
     * @param model model of the element, has its coordinates
     */
    @Override
    public void update(ElementModel model) {
        super.update(model);
        flip = ((SnailModel) model).getFlip();
        if(((SnailModel) model).getDirection() == 1)
            vflip = false;
        else
            vflip = true;
    }

    /**
     * Flips the sprite according to its y coordinate.
     * @param delta time interval
     */
    @Override
    public void act(float delta) {
        sprite.setFlip(flip, vflip);
    }
}
