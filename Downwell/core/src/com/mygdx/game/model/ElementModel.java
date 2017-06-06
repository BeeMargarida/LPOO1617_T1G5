package com.mygdx.game.model;

/**
 * ElementModel is a class that contain the coordinates of the element, methods to update its position and the types of object the element
 * can be.
 */
public abstract class ElementModel {
    /**
     * Types of element the element can be. Way of identifying objects.
     */
    public enum ModelType {HERO, BAT, BUBBLE, SNAIL, MAPTILE_L_WALL, MAPTILE_R_WALL, MAPTILE_D_BLOCK, MAPTILE_I_BLOCK, HEALTHBAR, BULLET}

    protected float x;
    protected float y;

    /**
     * Constructor of the class that set the position of the element with the coordinates given.
     * @param x x coordinate of the element
     * @param y y coordiante of the element
     */
    public ElementModel(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the element.
     * @return x coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the element.
     * @return y coordinate
     */
    public float getY() {
        return  y;
    }

    /**
     * Sets the position of the element to the coordinates given.
     * @param x new x coordinate of the element
     * @param y new y coordinate of the element
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the type of element.
     * @return type of element
     */
    public abstract ModelType getType();
}
