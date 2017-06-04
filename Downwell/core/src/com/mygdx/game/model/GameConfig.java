package com.mygdx.game.model;

/**
 * Created by Utilizador on 03/06/2017.
 */

public class GameConfig {
    private int levelDepth;
    private int enemiesNumber;

    public GameConfig(int lvlDepth, int enemiesNo){
        levelDepth = lvlDepth;
        enemiesNumber = enemiesNo;
    }

    public int getEnemiesNumber() {
        return enemiesNumber;
    }

    public int getLevelDepth(){
        return levelDepth;
    }

    public void incDifficulty(){
        levelDepth += 10;
        enemiesNumber *= 2;
    }
}
