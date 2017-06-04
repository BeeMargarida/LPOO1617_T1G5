package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

public abstract class EnemyModel extends ElementModel {

    protected BehaviourModel behaviour;
    protected boolean flagForRemoval;
    protected boolean flip = false;
    protected int points;
    private int hp;

    public EnemyModel(float x, float y, BehaviourModel behaviour){
        super(x,y);
        this.behaviour = behaviour;
        flagForRemoval = false;
        hp = 3;
    }

    public void setForRemoval() {
        flagForRemoval = true;
    }

    public boolean getForRemoval() {
        return flagForRemoval;
    }

    public boolean getFlip(){
        return flip;
    }

    public boolean damage(){
        if(hp-- == 0)
            return true;
        else
            return false;
    }

    public float[] update(HeroBody hero){
        float[] res = behaviour.act(this.getX(), this.getY(), hero);
        return res;
    }

    public int getPoints(){
        return points;
    }
}
