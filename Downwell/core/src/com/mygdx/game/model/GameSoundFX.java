package com.mygdx.game.model;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Downwell;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * GameSoundFX deals with all the sounds of the events of the game.
 */
public class GameSoundFX {
    Downwell game;
    private Sound jumpSound;
    private Sound landSound;
    private Sound shootSound;
    private Sound heroDamageSound;
    private Sound blockExplosionSound;
    private Sound enemyExplosionSound;
    private Sound bulletHitSound;
    private Music bgMusic[];
    private Music gameOverMusic;
    private Music titleScreenMusic;
    private int currSong;

    /**
     * Empty Constructor of the class.
     */
    public GameSoundFX(){}

    /**
     * Constructor of the game, saving all the assets to its respective variables.
     * @param game Downwell game, to access the batch
     */
    public GameSoundFX(Downwell game) {
        this.game = game;
        jumpSound = game.getAssetManager().get("jump.wav");
        landSound = game.getAssetManager().get("land.wav");
        shootSound = game.getAssetManager().get("shoot.wav");
        heroDamageSound = game.getAssetManager().get("hit.wav");
        blockExplosionSound = game.getAssetManager().get("bexplosion.wav");
        enemyExplosionSound = game.getAssetManager().get("eexplosion.wav");
        bulletHitSound = game.getAssetManager().get("hitbullet.wav");
        loadMusic();
        randomize();
    }

    private void loadMusic(){
        bgMusic = new Music[2];
        bgMusic[0] = game.getAssetManager().get("Artificial Intelligence Bomb.mp3");
        bgMusic[1] = game.getAssetManager().get("devil1.mp3");
        gameOverMusic = game.getAssetManager().get("gameover.mp3");
        titleScreenMusic = game.getAssetManager().get("mainmenu.mp3");
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

    public void randomize(){
        int nextSong = Math.abs(random.nextInt())%bgMusic.length;
        if(currSong != nextSong){
            bgMusic[currSong].stop();
            currSong = nextSong;
        }
    }

    public void playBackGroundMusic(){
        bgMusic[currSong].setLooping(true);
        bgMusic[currSong].play();
    }

    public void stopBackGroundMusic(){
        bgMusic[currSong].stop();
    }

    public void playGameOverMusic(){
        gameOverMusic.play();
    }

    public void stopGameOverMusic(){
        gameOverMusic.stop();
    }

    public void playTitleScreenMusic(){
        titleScreenMusic.play();
    }

    public void stopTitleScreenMusic(){
        titleScreenMusic.stop();
    }

    public void dispose(){
        jumpSound.dispose();
        landSound.dispose();
        shootSound.dispose();
        heroDamageSound.dispose();
        blockExplosionSound.dispose();
        enemyExplosionSound.dispose();
        bulletHitSound.dispose();
        for(int i = 0; i < bgMusic.length;i++){
            bgMusic[i].dispose();
        }
    }
}
