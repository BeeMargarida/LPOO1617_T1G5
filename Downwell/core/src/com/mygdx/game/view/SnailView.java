package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;

/**
 * Created by mc-guida on 18-05-2017.
 */

public class SnailView extends EnemyView {

    public SnailView(Downwell game) {
        super(game);
    }

    @Override
    public Sprite createSprite(Downwell game) {
        Texture texture1 = game.getAssetManager().get("shooting.png");

        TextureRegion[] frames = new TextureRegion[1];
        frames[0] = new TextureRegion(texture1);

        animation = new Animation<TextureRegion>(.5f,frames);
        sprite = new Sprite(animation.getKeyFrame(0));
        return sprite;
    }

    @Override
    public void update(ElementModel model) {
        super.update(model);
    }

    @Override
    public void act(float delta) {
        //stateTime += delta;
        //sprite.setRegion(animation.getKeyFrame(stateTime,true));
    }
}
