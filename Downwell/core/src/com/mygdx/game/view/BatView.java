package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;

public class BatView extends EnemyView {

    public BatView(Downwell game){
        super(game);
    }

    @Override
    public Sprite createSprite(Downwell game) {
        Texture texture1 = game.getAssetManager().get("bat1.png");
        Texture texture2 = game.getAssetManager().get("bat2.png");
        Texture texture3 = game.getAssetManager().get("bat3.png");
        Texture texture4 = game.getAssetManager().get("bat4.png");
        Texture texture5 = game.getAssetManager().get("bat5.png");

        TextureRegion[] frames = new TextureRegion[5];
        frames[0] = new TextureRegion(texture1);
        frames[1] = new TextureRegion(texture2);
        frames[2] = new TextureRegion(texture3);
        frames[3] = new TextureRegion(texture4);
        frames[4] = new TextureRegion(texture5);

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
        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime,true));
    }
}
