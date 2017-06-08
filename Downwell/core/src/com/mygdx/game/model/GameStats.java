package com.mygdx.game.model;

/**
 * GameStats is a class that contains and deals with the game information, such has score, number of kills, level of game
 * and the hero health points.
 */
public class GameStats {
    private int score;
    private int kills;
    private int level;
    private int heroHp;

    /**
     * Constructor of class. Sets the variables with the values given.
     * @param level level of the game
     * @param heroHp health points of the hero
     */
    public GameStats(int level, int heroHp){
        score = 0;
        kills = 0;
        this.level = level;
        this.heroHp = heroHp;
    }

    /**
     * Returns the current score of the game.
     * @return score of the game
     */
    public int getScore(){
        return score;
    }

    /**
     * Returns number of enemy kills of the game.
     * @return number of enemy kills
     */
    public int getKills(){
        return kills;
    }

    /**
     * Returns the current level of the game.
     * @return level of the game
     */
    public int getLevel(){
        return level;
    }

    /**
     * Returns the current hero's health points.
     * @return health points of the hero
     */
    public int getHeroHp(){
        return heroHp;
    }

    /**
     * Sets the hero health points with the value given.
     * @param hp new health points of the hero
     */
    public void setHeroHp(int hp){
        heroHp = hp;
    }

    /**
     * Increments the current score with the value given.
     * @param points value to be added to the current score
     */
    public void incScore(int points){
        score += points;
    }

    /**
     * Increments by 1 the level of the game.
     */
    public void incLevel(){
        level++;
    }

    /**
     * Increments by 1 the number of enemy kills.
     */
    public void incKills(){
        kills++;
    }

}
