package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;

import static com.mygdx.game.view.GameView.PIXEL_TO_METER;



public abstract class ElementView extends Actor {
    protected Sprite sprite;
    protected Animation<TextureRegion> animation;
    protected boolean flip = false;

    protected float stateTime = 0;

    public ElementView(Downwell game) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        sprite.draw((batch));
    }

    public void draw(SpriteBatch batch){
        sprite.draw((batch));
    }

    public abstract Sprite createSprite(Downwell game);
    public Animation<TextureRegion> getAnimation() { return animation;}

    public void update(ElementModel model) {
        sprite.setCenter(model.getX()/PIXEL_TO_METER, model.getY()/PIXEL_TO_METER);
    }

    public abstract void act(float delta);
}
