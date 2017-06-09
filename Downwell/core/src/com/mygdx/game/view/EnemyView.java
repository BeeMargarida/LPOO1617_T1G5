package com.mygdx.game.view;

import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;

/**
 * EnemyView is a class that deals with the view of each of the enemy entities, containing methods to update their view.
 * @see ElementView
 */
public abstract class EnemyView extends ElementView {
    /**
     * Constructor of the class, it calls the method to create the sprite and animation.
     * @param game Downwell game, contains the assets
     */
    public EnemyView(Downwell game){
        sprite = createSprite(game);
        animation = getAnimation();
    }
    /**
     * Changes the frame of the animation.
     * @param delta time interval
     */
    @Override
    public void act(float delta) {
        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime,true));
        sprite.setFlip(flip,false);
    }
}
