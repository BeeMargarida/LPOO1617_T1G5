package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Downwell;

/**
 * Created by MC-Guida on 10/06/2017.
 */

public class HeroStateJumping extends HeroState {

    public HeroStateJumping(Downwell game) {
        createSprite(game);
    }

    @Override
    public Sprite createSprite(Downwell game) {
        Texture texture = game.getAssetManager().get("jump.png");
        sprite = new Sprite(texture);
        animation = null;
        return sprite;
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        sprite.setFlip(flip,false);
    }
}
