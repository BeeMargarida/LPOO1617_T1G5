package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class HeroState extends ElementView{

    private float alpha = 1f;

    @Override
    public void act(float delta) {
        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime,true));
        sprite.setFlip(flip,false);
    }

    public void setAlpha(float alpha) { this.alpha = alpha; }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch, alpha);
    }
}
