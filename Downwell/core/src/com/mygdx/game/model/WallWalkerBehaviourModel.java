package com.mygdx.game.model;

/**
 * Created by mc-guida on 04-05-2017.
 */

public class WallWalkerBehaviourModel extends BehaviourModel {


    public WallWalkerBehaviourModel() {}

    @Override
    public float[] act(float x, float y, Object obj) {
        String dir = (String) obj;
        float[] res = {x,y};
        if(dir == "down"){
            res[1] = y + 1;
        }
        else {
            res[1] = y - 1;
        }
        return res;
    }
}
