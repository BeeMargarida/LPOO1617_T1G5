package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Downwell;

import static com.mygdx.game.model.HeroModel.state.JUMPING;

/**
 * Created by Utilizador on 26/05/2017.
 */

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
        /*
            sprite.setRegion(animation.getKeyFrame(stateTime,true));
        sprite.setFlip(flip,false);
        */
    }
}
