package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Downwell;


public class BulletView extends ElementView {
    public BulletView(Downwell game){
        super(game);
        sprite = createSprite(game);
        animation = getAnimation();
    }

    public Sprite createSprite(Downwell game){
        animation = null;
        Texture texture = game.getAssetManager().get("big bullet.png");
        sprite = new Sprite(texture);
        return sprite;
    }

    public void act(float delta) {

    }
}
