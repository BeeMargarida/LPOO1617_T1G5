package com.mygdx.game.model;

import static com.mygdx.game.model.ElementModel.ModelType.BULLET;

/**
 * Created by Utilizador on 26/05/2017.
 */

public class BulletModel extends ElementModel {

    private boolean flagForRemoval;
    private float timeToLive;
    private float rotation;

    public BulletModel(float x, float y, float rotation){
        super(x,y);
        this.rotation = rotation;
    }

    public void setRotation(float rotation){
        this.rotation = rotation;
    }

    public boolean decreaseTimeToLive(float delta) {
        timeToLive -= delta;
        return  timeToLive < 0;
    }

    public void setTimeToLive(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setForRemoval(boolean flag) {
        flagForRemoval = flag;
    }

    public boolean getForRemoval() {
        return flagForRemoval;
    }

    @Override
    public ModelType getType(){
        return BULLET;
    }
}
