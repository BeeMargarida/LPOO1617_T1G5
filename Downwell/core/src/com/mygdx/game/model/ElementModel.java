package com.mygdx.game.model;


public abstract class ElementModel {

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
}
