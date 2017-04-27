package com.mygdx.game.model;

public class GameModel {

    private HeroModel hero;
    private BatModel bat;

    public GameModel(float x, float y) {
        hero = new HeroModel(x,y);
        bat = new BatModel(x,y);
    }

    public HeroModel getHeroModel(){
        return hero;
    }
    public BatModel getBatModel() { return bat;}
}
