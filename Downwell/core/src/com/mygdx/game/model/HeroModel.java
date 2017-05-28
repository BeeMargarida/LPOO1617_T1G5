package com.mygdx.game.model;


import static com.mygdx.game.model.HeroModel.state.JUMPING;

public class HeroModel extends ElementModel {

    public enum state {STANDING, WALKING, JUMPING, ROLLING, SHOOTING}

    private state heroState;
    private boolean flip;
    private boolean invincible;
    private int hp;

    public HeroModel(float x, float y) {
        super(x,y);
        heroState = JUMPING;
        flip = false;
        invincible = false;
        hp = 4;
    }

    public ModelType getType(){
        return ModelType.HERO;
    }

    public state getState(){
        return heroState;
    }

    public void setState(state newState){
        heroState = newState;
    }

    public boolean getFlip(){
        return flip;
    }

    public void setFlip(boolean flip){
        this.flip = flip;
    }

    public void setInvincible(boolean flag){
        invincible = flag;
    }

    public boolean getInvincible(){
        return invincible;
    }

    public int getHp(){
        return hp;
    }

    public boolean damage(){
        if(invincible)
            return false;
        if(hp-- == 0)
            return true;
        else
            return false;
    }
}
