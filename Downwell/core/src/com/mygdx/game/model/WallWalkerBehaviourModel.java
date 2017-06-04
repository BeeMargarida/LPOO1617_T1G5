package com.mygdx.game.model;

public class WallWalkerBehaviourModel extends BehaviourModel {


    public WallWalkerBehaviourModel() {}

    @Override
    public float[] act(float x, float y, Object obj) {
        String dir = (String) obj;
        float[] res = {0,0};
        if(dir == "down"){
            res[1] = (float)(y + 0.01);
        }
        else {
            res[1] = (float)(y - 0.01);
        }
        return res;
    }
}
