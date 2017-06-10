package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Downwell;

/**
 * Created by MC-Guida on 10/06/2017.
 */

public class HeroStateStanding extends HeroState {

    public HeroStateStanding(Downwell game) {
        createSprite(game);
    }

    @Override
    public Sprite createSprite(Downwell game) {
        Texture texture1 = game.getAssetManager().get("r1.png");
        Texture texture2 = game.getAssetManager().get("r2.png");
        Texture texture3 = game.getAssetManager().get("r3.png");
        Texture texture4 = game.getAssetManager().get("r4.png");
        Texture texture5 = game.getAssetManager().get("r5.png");
        Texture texture6 = game.getAssetManager().get("r6.png");
        Texture texture7 = game.getAssetManager().get("r7.png");

        TextureRegion[] standingFrames = new TextureRegion[7];
        standingFrames[0] = new TextureRegion(texture1);
        standingFrames[1] = new TextureRegion(texture2);
        standingFrames[2] = new TextureRegion(texture3);
        standingFrames[3] = new TextureRegion(texture4);
        standingFrames[4] = new TextureRegion(texture5);
        standingFrames[5] = new TextureRegion(texture6);
        standingFrames[6] = new TextureRegion(texture7);

        animation = new Animation<TextureRegion>(.5f,standingFrames);
        sprite = new Sprite(texture1);
        //sprite.setRegion(animation.getKeyFrame(0));
        //sprite = new Sprite(animation.getKeyFrame(0));
        return sprite;
    }
}
