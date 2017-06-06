package com.mygdx.game.model;

/**
 *
 */
public class GameStats {
    private int score;
    private int kills;
    private int level;
    private int heroHp;

    /**
     *
     * @param level
     * @param heroHp
     */
    public GameStats(int level, int heroHp){
        score = 0;
        kills = 0;
        this.level = level;
        this.heroHp = heroHp;
    }

    /**
     *
     * @return
     */
    public int getScore(){
        return score;
    }

    /**
     *
     * @return
     */
    public int getKills(){
        return kills;
    }

    /**
     *
     * @return
     */
    public int getLevel(){
        return level;
    }

    /**
     *
     * @return
     */
    public int getHeroHp(){
        return heroHp;
    }

    /**
     *
     * @param hp
     */
    public void setHeroHp(int hp){
        heroHp = hp;
    }

    /**
     *
     * @param points
     */
    public void incScore(int points){
        score += points;
    }

    /**
     *
     */
    public void incLevel(){
        level++;
    }

    /**
     *
     */
    public void incKills(){
        kills++;
    }

}
