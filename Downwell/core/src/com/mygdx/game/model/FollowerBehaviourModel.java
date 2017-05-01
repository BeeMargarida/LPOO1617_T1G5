package com.mygdx.game.model;

/**
 * Created by mc-guida on 30-04-2017.
 */

public class FollowerBehaviourModel extends BehaviourModel {

    public FollowerBehaviourModel() {

    }

    @Override
    public float[] act(float x, float y, HeroModel model) {
        float distX = (float) (Math.abs(x - model.getX())*0.1);
        float distY = (float) (Math.abs(y - model.getY())*0.1);
        float[] res = new float[]{x + distX, y + distY};
        return res;
    }
}
