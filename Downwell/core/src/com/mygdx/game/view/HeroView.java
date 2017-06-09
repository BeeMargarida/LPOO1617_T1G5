package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.HeroModel;

import static com.mygdx.game.model.HeroModel.state.JUMPING;
import static com.mygdx.game.model.HeroModel.state.ROLLING;

/**
 * HeroView is a class that deals with all the animations of the hero, checking its status to choose which of the animations to use.
 * @see ElementView
 */
public class HeroView extends ElementView {

    private Animation<TextureRegion> standingAnimation;
    private Animation<TextureRegion> walkingAnimation;
    private Animation<TextureRegion> rollingAnimation;
    private Texture jumpingFrame;
    private HeroModel.state lastState;
    private float alpha = 1f;

    /**
     * Constructor of the class, calls the methods to create the animations and set the state to JUMPING.
     * @param game Downwell game, has the assets
     */
    public HeroView(Downwell game){
        sprite = createSprite(game);
        animation = getAnimation();
        lastState = JUMPING;
    }

    /**
     * Creates the animations to all the states and sets the current sprite to the one correspondent to the jump.
     * @param game Downwell game, has the assets
     * @return sprite of the jump
     */
    @Override
    public Sprite createSprite(Downwell game) {
        getStandingAnimation(game);
        getWalkingAnimation(game);
        getRollingAnimation(game);
        jumpingFrame = game.getAssetManager().get("jump.png");
        animation = null;
        sprite = new Sprite(jumpingFrame);
        return sprite;
    }

    /**
     * Creates the animation of the rolling when jumping.
     * @param game Downwell game, has the assets
     */
    private void getRollingAnimation(Downwell game) {
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

        rollingAnimation = new Animation<TextureRegion>(0.35f, rollingFrames);
    }

    /**
     * Creates the animation of walking, that is, when the user inputs to walk to the right or left.
     * @param game Downwell game, has the assets
     */
    private void getWalkingAnimation(Downwell game) {
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

        walkingAnimation = new Animation<TextureRegion>(.5f,walkingFrames);
    }

    /**
     * Creates the animation of standing, when the game receives no input.
     * @param game Downwell game, has the assets
     */
    private void getStandingAnimation(Downwell game) {
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

        standingAnimation = new Animation<TextureRegion>(.5f,standingFrames);
    }

    /**
     * Depending the state of the hero, sets the according animation to it. It also diminishes the alpha if the hero is on invincible
     * mode.
     * @param model model of the element, has its coordinates
     */
    @Override
    public void update(ElementModel model) {
        super.update(model);

        if(((HeroModel) model).getInvincible())
            alpha = 0.5f;
        else
            alpha = 1f;

        HeroModel.state currState = ((HeroModel) model).getState();

        if(currState != ROLLING)
            flip = ((HeroModel) model).getFlip();

        if(currState != lastState) {
            switch (((HeroModel) model).getState()) {
                case STANDING:
                    animation = standingAnimation;
                    sprite.setRegion(animation.getKeyFrame(0));
                    break;
                case WALKING:
                    animation = walkingAnimation;
                    sprite.setRegion(animation.getKeyFrame(0));
                    break;
                case JUMPING:
                    animation = null;
                    sprite.setRegion(jumpingFrame);
                    break;
                case ROLLING:
                    animation = rollingAnimation;
                    sprite.setRegion(animation.getKeyFrame(0));
                    break;
            }
            lastState = currState;
        }

    }

    /**
     * Goes to the next frame of the animation and sets the respective flip.
     * @param delta
     */
    @Override
    public void act(float delta) {
        stateTime += delta;
        if(lastState != JUMPING)
            sprite.setRegion(animation.getKeyFrame(stateTime,true));
        sprite.setFlip(flip,false);
    }

    /**
     * Used to draw the sprite.
     * @param batch used to draw
     */
    public void draw(SpriteBatch batch){
        sprite.draw((batch), alpha);
    }

}
