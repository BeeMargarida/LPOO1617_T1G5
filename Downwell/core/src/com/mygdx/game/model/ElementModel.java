package com.mygdx.game.model;


public abstract class ElementModel {

    public enum ModelType {HERO, BAT, BUBBLE, SNAIL, MAPTILE_L_WALL, MAPTILE_R_WALL, MAPTILE_D_BLOCK, MAPTILE_I_BLOCK, BULLET}

    private float x;
    private float y;

    public ElementModel(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return  y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public abstract ModelType getType();
}
