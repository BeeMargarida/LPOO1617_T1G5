package com.mygdx.game.model;

/**
 * GameConfig is a class that keeps information about the configuration of the level, such has the depth
 * of the level and the number of enemies in it. After passing a level, it will increment the depth and number
 * of enemies, increasing the difficulty.
 */
public class GameConfig {
    private int levelDepth;
    private int enemiesNumber;

    /**
     * Constructor that set the depth and number of enemies of the level to the ones given (minimum difficulty).
     * @param lvlDepth depth of the level
     * @param enemiesNo number of enemies on the level
     */
    public GameConfig(int lvlDepth, int enemiesNo){
        levelDepth = lvlDepth;
        enemiesNumber = enemiesNo;
    }

    /**
     * Return the number of enemies on the configuration.
     * @return number of enemies
     */
    public int getEnemiesNumber() {
        return enemiesNumber;
    }

    /**
     * Returns the depth of the level on the configuration.
     * @return depth of level
     */
    public int getLevelDepth(){
        return levelDepth;
    }

    /**
     * Increments the depth level by 10 unities and the number duplicates the number of enemies.
     */
    public void incDifficulty(){
        levelDepth += 10;
        enemiesNumber *= 2;
    }
}
