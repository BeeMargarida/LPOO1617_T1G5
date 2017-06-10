package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * HeroState is a class that implements the State Design Pattern, meaning that each child of this class is a possible
 * state of the hero, dealing with its animations in the respective state.
 */
public abstract class HeroState extends ElementView{

    private float alpha = 1f;

    /**
     * Sets the next frame of the animation and sets the flip.
     * @param delta time interval
     */
    @Override
    public void act(float delta) {
        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime,true));
        sprite.setFlip(flip,false);
    }

    /**
     * Sets the value alpha to the one given.
     * @param alpha new alpha value
     */
    public void setAlpha(float alpha) { this.alpha = alpha; }

    /**
     * Draws the sprite.
     * @param batch used to draw
     */
    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch, alpha);
    }

    /**
     * Sets the state time to 0.
     */
    public void resetTime(){
        stateTime = 0;
    }
}
