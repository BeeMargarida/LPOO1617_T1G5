package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.BatModel;
import com.mygdx.game.model.ElementModel;

/**
 * BatView is a class that contains the animations of the bat, and the methods to update them.
 * @see EnemyView
 */
public class BatView extends EnemyView {

    private Texture sleepingFrame;
    private Animation<TextureRegion> flyingAnimation;
    private boolean sleeping = true;

    /**
     * Constructor of the class.
     * @param game Downwell game
     */
    public BatView(Downwell game){
        super(game);
    }

    /**
     * This method gets the resources already loaded and saves them in textures, also creating an animation
     * for the fly of the bat.
     * @param game Downwell game
     * @return starting sprite of the bat, the sleeping one
     */
    @Override
    public Sprite createSprite(Downwell game) {

        sleepingFrame = game.getAssetManager().get("sleepbat.png");

        Texture texture1 = game.getAssetManager().get("bat1.png");
        Texture texture2 = game.getAssetManager().get("bat2.png");
        Texture texture3 = game.getAssetManager().get("bat3.png");
        Texture texture4 = game.getAssetManager().get("bat4.png");
        Texture texture5 = game.getAssetManager().get("bat5.png");

        TextureRegion[] flyingFrames = new TextureRegion[5];
        flyingFrames[0] = new TextureRegion(texture1);
        flyingFrames[1] = new TextureRegion(texture2);
        flyingFrames[2] = new TextureRegion(texture3);
        flyingFrames[3] = new TextureRegion(texture4);
        flyingFrames[4] = new TextureRegion(texture5);

        flyingAnimation = new Animation<TextureRegion>(.5f,flyingFrames);

        animation = null;
        sprite = new Sprite(sleepingFrame);
        return sprite;
    }

    /**
     * Updates the sprite with the facing of it and checks if the bat is still sleeping. If it isn't, starts
     * the animation of the flight.
     * @param model
     */
    public void update(ElementModel model) {
        super.update(model);
        flip = ((BatModel) model).getFlip();

        if(sleeping) {
            sleeping = ((BatModel) model).isSleeping();
            if(!sleeping){
                animation = flyingAnimation;
                sprite.setRegion(animation.getKeyFrame(0));
            }
        }
    }

    /**
     * If the bat isn't sleeping (just one sprite), it calls the super method to change the frame of the animation
     * of the flight.
     * @param delta time interval
     */
    @Override
    public void act(float delta) {
        if(!sleeping)
            super.act(delta);
    }
}
