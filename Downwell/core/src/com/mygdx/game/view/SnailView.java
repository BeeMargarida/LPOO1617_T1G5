package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.SnailModel;

/**
 * Created by mc-guida on 18-05-2017.
 */

public class SnailView extends EnemyView {

    private boolean vflip = false;

    public SnailView(Downwell game) {
        super(game);
    }

    @Override
    public Sprite createSprite(Downwell game) {
        /*
        Texture texture1 = game.getAssetManager().get("shooting.png");

        TextureRegion[] frames = new TextureRegion[1];
        frames[0] = new TextureRegion(texture1);

        animation = new Animation<TextureRegion>(.5f,frames);
        sprite = new Sprite(animation.getKeyFrame(0));
        return sprite;
        */
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
        //stateTime += delta;
        //sprite.setRegion(animation.getKeyFrame(stateTime,true));
    }
}
