package com.mygdx.game.controller;

import com.mygdx.game.model.HeroModel;

/**
 * Created by mc-guida on 30-04-2017.
 */

public abstract class BehaviourBody {
    public abstract float[] act(float x, float y, HeroBody hero);
}
