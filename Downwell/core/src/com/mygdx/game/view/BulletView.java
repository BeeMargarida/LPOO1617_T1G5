package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Downwell;

/**
 * BulletView is a class that contains the texture of the bullet and deals with the view of it.
 * @see ElementView
 */
public class BulletView extends ElementView {
    /**
     * Constructor  of the class, it calls the function to create a sprite.
     * @param game Downwell game, has the assets
     */
    public BulletView(Downwell game){
        sprite = createSprite(game);
        animation = getAnimation();
    }

    /**
     * Creates the sprite for the bullet.
     * @param game Downwell game, used to gt the assets needed
     * @return sprite created for the bullet
     */
    public Sprite createSprite(Downwell game){
        animation = null;
        Texture texture = game.getAssetManager().get("big bullet.png");
        sprite = new Sprite(texture);
        return sprite;
    }

    /**
     * Overriding method.
     * @param delta time interval
     */
    @Override
    public void act(float delta) {

    }

}
