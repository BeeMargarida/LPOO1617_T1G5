package com.mygdx.game.model;

/**
 * Created by mc-guida on 23-04-2017.
 */

public class GameModel {

    private HeroModel hero;

    public GameModel(float x, float y) {
        hero = new HeroModel(x,y);
    }

    public HeroModel getHeroModel(){
        return hero;
    }
}
