package com.mygdx.game.model;

import static com.mygdx.game.model.HeroModel.state.JUMPING;

/**
 * HeroModel is a class that contains and deals with the information regarding the state of the hero, his health points,
 * is mode of invincibility.
 */
public class HeroModel extends ElementModel {

    public enum state {STANDING, WALKING, JUMPING, ROLLING} /**Possible states of the hero*/

    private static final float INVIC_TIME = 2f;
    private state heroState;
    private boolean flip;
    private boolean invincible;
    private float invincibleTime;
    private int hp;

    /**
     * Constructor of the class, it sets the state of the hero to jumping, his hp and the rest as false.
     * @param x x coordinate of the hero position
     * @param y y coordinate of the hero position
     * @param hp health points of the hero
     */
    public HeroModel(float x, float y, int hp) {
        super(x,y);
        heroState = JUMPING;
        flip = false;
        invincible = false;
        invincibleTime = INVIC_TIME;
        this.hp = hp;
    }

    /**
     * Returns the ModelType of the hero.
     * @return ModelType.HERO
     */
    public ModelType getType(){
        return ModelType.HERO;
    }

    /**
     * Returns the current state of the hero.
     * @return current state of the hero
     */
    public state getState(){
        return heroState;
    }

    /**
     * Sets the variable state with the value given.
     * @param newState new state of the hero
     */
    public void setState(state newState){
        heroState = newState;
    }

    /**
     * Returns the value of the flag flip.
     * @return value of flip
     */
    public boolean getFlip(){
        return flip;
    }

    /**
     * Sets the flag flip with the value given
     * @param flip new value for the variable flip
     */
    public void setFlip(boolean flip){
        this.flip = flip;
    }

    /**
     * Sets the flag invincible with the value given.
     * @param flag new value fot the variable invincible
     */
    public void setInvincible(boolean flag){
        invincible = flag;
    }

    /**
     * Decreases the value of the invincibleTime with the time that has passes and returns true if the invincible time is less that 0.
     * Returns false if not.
     * @param delta time interval
     * @return true if invincibleTime less that 0, false if not
     */
    private boolean decreaseInvincibleTime(float delta){
        invincibleTime -= delta;
        if(invincibleTime  < 0)
            return true;
        else
            return false;
    }

    /**
     * If the hero is invincible, decreases the time of invincibility. If the time has run out, sets the flag invincibility to false,
     * returning the hero to a state that can take damage.
     * @param delta time interval
     */
    public void update(float delta){
        if(invincible){
            if(decreaseInvincibleTime(delta)){
                invincible = false;
                invincibleTime = INVIC_TIME;
            }
        }
    }

    /**
     * Returns the flag invincible.
     * @return value of the variable invincible
     */
    public boolean getInvincible(){
        return invincible;
    }

    /**
     * Returns the hero's health points.
     * @return value of the variable hp.
     */
    public int getHp(){  return hp; }

    /**
     * If the hero is invincible, the hero doesn't take damage. If not, it decreases one in the hp variable and returns true if
     * it is less or equal to 0, false if not.
     * @return true if hp is less or equal to 0, false if not.
     */
    public boolean damage(){
        if(invincible)
            return false;
        else {
            hp--;
            return true;
        }
    }
}
