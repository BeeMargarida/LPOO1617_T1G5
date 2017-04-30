package com.mygdx.game.model;

/**
 * Created by mc-guida on 27-04-2017.
 */

public class BatModel extends EnemyModel {

    private boolean ALERT;
    private BehaviourModel behaviour;

    public BatModel(float x, float y) {
        super(x,y);
        ALERT = false;
    }

    public void updateState(HeroModel hero){
        double dist = Math.sqrt(Math.pow(this.getX() - hero.getX(),2) + Math.pow(this.getY()-hero.getY(),2));
        if(dist < 5) {
            behaviour = new FollowerBehaviourModel();
            ALERT = true;
            movement(hero);
        }
        else
            ALERT = false;
    }

    public void movement(HeroModel hero) {
        float[] res = behaviour.act(this.getX(), this.getY(), hero);
        setPosition(res[0],res[1]);
    }

    public boolean getAlert() { return ALERT;}
}
