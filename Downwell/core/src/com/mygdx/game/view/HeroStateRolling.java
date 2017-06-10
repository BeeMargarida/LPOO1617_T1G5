package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Downwell;

/**
 * Created by MC-Guida on 10/06/2017.
 */

public class HeroStateRolling extends HeroState {

    public HeroStateRolling(Downwell game) {
        createSprite(game);
    }

    @Override
    public Sprite createSprite(Downwell game) {
        Texture texture1 = game.getAssetManager().get("jr1.png");
        Texture texture2 = game.getAssetManager().get("jr2.png");
        Texture texture3 = game.getAssetManager().get("jr3.png");
        Texture texture4 = game.getAssetManager().get("jr4.png");
        Texture texture5 = game.getAssetManager().get("jr5.png");
        Texture texture6 = game.getAssetManager().get("jr6.png");
        Texture texture7 = game.getAssetManager().get("jr7.png");

        TextureRegion[] rollingFrames = new TextureRegion[7];
        rollingFrames[0] = new TextureRegion(texture1);
        rollingFrames[1] = new TextureRegion(texture2);
        rollingFrames[2] = new TextureRegion(texture3);
        rollingFrames[3] = new TextureRegion(texture4);
        rollingFrames[4] = new TextureRegion(texture5);
        rollingFrames[5] = new TextureRegion(texture6);
        rollingFrames[6] = new TextureRegion(texture7);

        animation = new Animation<TextureRegion>(0.35f, rollingFrames);
        //sprite = new Sprite(texture1);
        //sprite.setRegion(animation.getKeyFrame(0));
        sprite = new Sprite(animation.getKeyFrame(0));
        return sprite;
    }
}
