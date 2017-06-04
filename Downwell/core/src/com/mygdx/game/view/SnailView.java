package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.SnailModel;


public class SnailView extends EnemyView {

    private boolean vflip = false;

    public SnailView(Downwell game) {
        super(game);
    }

    @Override
    public Sprite createSprite(Downwell game) {
        Texture texture = game.getAssetManager().get("snail.png");
        animation = null;
        sprite = new Sprite(texture);
        return sprite;
    }

    @Override
    public void update(ElementModel model) {
        super.update(model);
        flip = ((SnailModel) model).getFlip();
        if(((SnailModel) model).getDirection() == 1)
            vflip = false;
        else
            vflip = true;
    }

    @Override
    public void act(float delta) {
        sprite.setFlip(flip, vflip);
    }
}
