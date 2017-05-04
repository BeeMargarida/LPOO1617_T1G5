package com.mygdx.game.model;


public class SnailModel extends EnemyModel {

    private BehaviourModel behaviour;

    public SnailModel(float x, float y){
        super(x,y);
        this.behaviour = new WallWalkerBehaviourModel();
    }

    public float[] update(){
        float[] res = {0,0};/*behaviour.act(this.getX(), this.getY());*/
        return res;
        //(res[0], res[1], 0);
    }
}
