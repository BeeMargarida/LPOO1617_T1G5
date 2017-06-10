package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Downwell;

/**
 * Created by MC-Guida on 10/06/2017.
 */

public class HeroStateWalking extends HeroState {

    public HeroStateWalking(Downwell game) {
        createSprite(game);
    }

    @Override
    public Sprite createSprite(Downwell game) {
        Texture texture1 = game.getAssetManager().get("1.png");
        Texture texture2 = game.getAssetManager().get("2.png");
        Texture texture3 = game.getAssetManager().get("3.png");
        Texture texture4 = game.getAssetManager().get("4.png");
        Texture texture5 = game.getAssetManager().get("5.png");
        Texture texture6 = game.getAssetManager().get("6.png");
        Texture texture7 = game.getAssetManager().get("7.png");

        TextureRegion[] walkingFrames = new TextureRegion[7];
        walkingFrames[0] = new TextureRegion(texture1);
        walkingFrames[1] = new TextureRegion(texture2);
        walkingFrames[2] = new TextureRegion(texture3);
        walkingFrames[3] = new TextureRegion(texture4);
        walkingFrames[4] = new TextureRegion(texture5);
        walkingFrames[5] = new TextureRegion(texture6);
        walkingFrames[6] = new TextureRegion(texture7);

        animation = new Animation<TextureRegion>(.5f,walkingFrames);
        sprite = new Sprite(animation.getKeyFrame(0));
        return sprite;
    }
}
