package com.mygdx.game.model;


import static com.mygdx.game.model.HeroModel.state.JUMPING;

public class HeroModel extends ElementModel {

    public enum state {STANDING, WALKING, JUMPING, SHOOTING}

    private state heroState;
    private boolean flip;

    public HeroModel(float x, float y) {
        super(x,y);
        heroState = JUMPING;
        flip = false;
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
}
