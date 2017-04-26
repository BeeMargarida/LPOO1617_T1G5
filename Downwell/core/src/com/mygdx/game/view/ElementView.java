package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;

import static com.mygdx.game.view.GameView.PIXEL_TO_METER;



public abstract class ElementView {
    protected Sprite sprite;
    protected Animation<TextureRegion> animation;

    protected float stateTime = 0;

    public ElementView(Downwell game) {
        sprite = createSprite(game);
        animation = getAnimation();
    }

    public void draw(SpriteBatch batch){
        sprite.draw((batch));
    }

    public abstract Sprite createSprite(Downwell game);
    public Animation<TextureRegion> getAnimation() { return animation;}

    public void update(ElementModel model){} //?

    public void act(float delta){};
}
