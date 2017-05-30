package com.mygdx.game.model;

public class HealthBarModel extends ElementModel {

    private int hp;

    public HealthBarModel(float x, float y, int hp){
        super(x,y);
        this.hp = hp;
    }

    public void gotDamage() { hp--; }
    public int getHp() { return hp; }

    @Override
    public ModelType getType() {
        return ModelType.HEALTHBAR;
    }
}
