package com.mygdx.game.model;

public class HealthBarModel extends ElementModel {

    private int hp;

    public HealthBarModel(float x, float y, int hp){
        super(x,y);
        this.hp = hp;
    }

    public void gotDamage() { hp -= 1; }
    public int getHp() { return hp; }
    public void udpate(float y) { this.y = y + 10; }

    @Override
    public ModelType getType() {
        return ModelType.HEALTHBAR;
    }
}
