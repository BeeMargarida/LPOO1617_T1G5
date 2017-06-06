package com.mygdx.game.model;

import com.mygdx.game.controller.HeroBody;

/**
 * EnemyModel class deals with with the state of the enemy, its health points, its removal flag and its behaviour, updating its position.
 * @see ElementModel
 */
public abstract class EnemyModel extends ElementModel {

    protected BehaviourModel behaviour;
    protected boolean flagForRemoval;
    protected boolean flip = false;
    protected int points;
    private int hp;

    /**
     * Constructor of the class, it sets the coordinates, health points and behaviour of the enemy.
     * @param x
     * @param y
     * @param behaviour
     */
    public EnemyModel(float x, float y, BehaviourModel behaviour){
        super(x,y);
        this.behaviour = behaviour;
        flagForRemoval = false;
        hp = 3;
    }

    /**
     * Flags the enemy for removal, meaning that on the next verification, this entity will cease to exist.
     */
    public void setForRemoval() {
        flagForRemoval = true;
    }

    /**
     * Returns the flag flagForRemoval
     * @return flagForRemoval
     */
    public boolean getForRemoval() {
        return flagForRemoval;
    }

    /**
     * Returns the flag flip that informs if the sprite must be flipped.
     * @return flag flip
     */
    public boolean getFlip(){
        return flip;
    }

    /**
     * Decreases the health points by one and, if the health points are equal to 0, return true. Else returns false.
     * @return true if health points equal to 0, false if not
     */
    public boolean damage(){
        if(hp-- == 0)
            return true;
        else
            return false;
    }

    /**
     * Calls the method act of the behaviour of the enemy, saving its result in a array. It then returns that array.
     * @param hero body of the hero used to make calculations in the act method of the behaviour
     * @return array of the new direction coordinates x and y of the enemy movement
     */
    public float[] update(HeroBody hero){
        float[] res = behaviour.act(this.getX(), this.getY(), hero);
        return res;
    }

    /**
     * Return the value in points that result of killing this enemy.
     * @return points that killing this enemy results in
     */
    public int getPoints(){
        return points;
    }
}
