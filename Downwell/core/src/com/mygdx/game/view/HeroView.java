package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.HeroModel;

import static com.mygdx.game.model.HeroModel.state.JUMPING;
import static com.mygdx.game.model.HeroModel.state.ROLLING;

/**
 * HeroView is a class that deals with all the animations of the hero, checking its status to choose which of the animations to use.
 */
public class HeroView  {

    private HeroStateJumping jumpingState;
    private HeroStateWalking walkingState;
    private HeroStateRolling rollingState;
    private HeroStateStanding standingState;
    private HeroState currentState;
    private HeroModel.state lastState;

    /**
     * Constructor of the class, creates the states and sets the beginning one to JUMPING.
     * @param game Downwell game, has the assets
     */
    public HeroView(Downwell game){
        jumpingState = new HeroStateJumping(game);
        walkingState = new HeroStateWalking(game);
        rollingState = new HeroStateRolling(game);
        standingState = new HeroStateStanding(game);
        currentState = jumpingState;
        lastState = JUMPING;
    }
    /**
     * Sets the state according to the action of the hero. It also updates the alpha if the hero is invincible or not.
     * mode.
     * @param model model of the element, has the current state
     */

    public void update(ElementModel model) {
        if(((HeroModel) model).getInvincible())
            currentState.setAlpha(0.5f);
        else
            currentState.setAlpha(1f);

        HeroModel.state currState = ((HeroModel) model).getState();

        if(currState != lastState) {
            switch (((HeroModel) model).getState()) {
                case STANDING:
                    currentState = standingState;
                    break;
                case WALKING:
                    currentState = walkingState;
                    break;
                case JUMPING:
                    currentState = jumpingState;
                    break;
                case ROLLING:
                    currentState = rollingState;
                    currentState.flip = ((HeroModel) model).getFlip();
                    break;
            }
            lastState = currState;
            currentState.resetTime();
        }

        if(currState != ROLLING)
            currentState.flip = ((HeroModel) model).getFlip();

        currentState.update(model);
    }

    /**
     * Calls the current state act method.
     * @param delta time interval
     */
    public void act(float delta) {
        currentState.act(delta);
    }

    /**
     * Calls the method of the current state to draw.
     * @param batch used to draw
     */
    public void draw(SpriteBatch batch){
        currentState.draw((batch));
    }

}
