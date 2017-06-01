package com.mygdx.game.model;

/**
 * Created by Utilizador on 01/06/2017.
 */

public class GameStats {
    private int score;
    private int kills;
    private int level;
    private int heroHp;

    public GameStats(int level, int heroHp){
        score = 0;
        kills = 0;
        this.level = level;
        this.heroHp = heroHp;
    }

    public int getScore(){
        return score;
    }

    public int getKills(){
        return kills;
    }

    public int getLevel(){
        return level;
    }

    public int getHeroHp(){
        return heroHp;
    }

    public void setHeroHp(int hp){
        heroHp = hp;
    }

    public void incScore(int points){
        score += points;
    }

    public void incLevel(){
        level++;
    }

    public void incKills(){
        kills++;
    }

}
