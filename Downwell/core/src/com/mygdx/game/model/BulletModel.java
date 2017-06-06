package com.mygdx.game.model;

import static com.mygdx.game.model.ElementModel.ModelType.BULLET;

/**
 * BulletModel class is a class that contains information about the bullet, including its time left of existence, its rotation and if
 * it marked for removal.
 * @see ElementModel
 */
public class BulletModel extends ElementModel {

    private boolean flagForRemoval;
    private float timeToLive;
    private float rotation;

    /**
     * Constructor of the class, sets the coordinates and the rotation.
     * @param x x coordinate of the bullet
     * @param y y coordinate of the bullet
     * @param rotation rotation of the bullet
     */
    public BulletModel(float x, float y, float rotation){
        super(x,y);
        this.rotation = rotation;
    }

    /**
     * Sets the rotation with a new value.
     * @param rotation new rotatio of the bullet
     */
    public void setRotation(float rotation){
        this.rotation = rotation;
    }

    /**
     * Returns the rotation of the bullet
     * @return rotation of the bullet
     */
    public float getRotation() { return rotation; }

    /**
     * Decreases the time to live of the bullet, returning if that time has ended.
     * @param delta time interval
     * @return true if the time has ended, false if not
     */
    public boolean decreaseTimeToLive(float delta) {
        timeToLive -= delta;
        return timeToLive < 0;
    }

    /**
     * Sets the time of existence of the bullet with the value given.
     * @param timeToLive new time of existence of the bullet
     */
    public void setTimeToLive(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * Flags the bullet for removal, meaning that on the next verification, this entity will cease to exist.
     * @param flag
     */
    public void setForRemoval(boolean flag) {
        flagForRemoval = flag;
    }

    /**
     * Returns the flag flagForRemoval
     * @return flagForRemoval
     */
    public boolean getForRemoval() {
        return flagForRemoval;
    }

    /**
     * Returns the type of object (BULLET).
     * @return ModelType.BULLET
     */
    @Override
    public ModelType getType(){
        return BULLET;
    }
}
