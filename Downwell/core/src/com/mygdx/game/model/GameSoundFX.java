package com.mygdx.game.model;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Downwell;

/**
 * GameSoundFX deals with all the sounds of the events of the game.
 */
public class GameSoundFX {
    private Sound jumpSound;
    private Sound landSound;
    private Sound shootSound;
    private Sound heroDamageSound;
    private Sound blockExplosionSound;
    private Sound enemyExplosionSound;
    private Sound bulletHitSound;

    /**
     * Empty Constructor of the class.
     */
    public GameSoundFX(){}

    /**
     * Constructor of the game, saving all the assets to its respective variables.
     * @param game Downwell game, to access the batch
     */
    public GameSoundFX(Downwell game){
        jumpSound = game.getAssetManager().get("jump.wav");
        landSound = game.getAssetManager().get("land.wav");
        shootSound = game.getAssetManager().get("shoot.wav");
        heroDamageSound = game.getAssetManager().get("hit.wav");
        blockExplosionSound = game.getAssetManager().get("bexplosion.wav");
        enemyExplosionSound = game.getAssetManager().get("eexplosion.wav");
        bulletHitSound = game.getAssetManager().get("hitbullet.wav");
    }

    /**
     * Returns false.
     * @return false
     */
    public boolean isNull(){
        return false;
    }

    /**
     * Plays the sound when the hero jumps.
     */
    public void playJumpSound(){
        jumpSound.play();
    }

    /**
     * Plays the sound when the hero collides with a map tile.
     */
    public void playLandSound() {
        landSound.play();
    }

    /**
     * Plays the sound when the hero shoots.
     */
    public void playShootSound() {
        shootSound.play();
    }

    /**
     * Plays the sound when the hero takes damage.
     */
    public void playHeroDamageSound() {
        heroDamageSound.play();
    }

    /**
     * Plays the sound when a map tiles is destroyed.
     */
    public void playBlockExplosionSound() {
        blockExplosionSound.play();
    }

    /**
     * Plays the sound when a enemy is destroyed.
     */
    public void playEnemyExplosionSound() {
        enemyExplosionSound.play();
    }

    /**
     * Plays the sound when a bullet collides with something.
     */
    public void playBulletHitSound() {
        bulletHitSound.play();
    }
}
