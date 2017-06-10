package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Downwell;

/**
 * HeroStateJumping is a class that deals with the sprite of the hero when an action has been performed for it to jump.
 */
public class HeroStateJumping extends HeroState {
    /**
     * Calls the method to get the needed assets and create the sprite.
     * @param game
     */
    public HeroStateJumping(Downwell game) {
        createSprite(game);
    }

    /**
     * Gets the resources needed and creates a sprite with them.
     * @param game Downwell game, has the assets
     * @return sprite created
     */
    @Override
    public Sprite createSprite(Downwell game) {
        Texture texture = game.getAssetManager().get("jump.png");
        sprite = new Sprite(texture);
        animation = null;
        return sprite;
    }

    /**
     * Overloading of the method act, it only increments the stateTime and sets the flip on the sprite.
     * @param delta time interval
     */
    @Override
    public void act(float delta) {
        stateTime += delta;
        sprite.setFlip(flip,false);
    }
}
