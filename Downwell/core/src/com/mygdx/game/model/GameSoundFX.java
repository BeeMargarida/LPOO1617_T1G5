package com.mygdx.game.model;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Downwell;

/**
 * Created by Utilizador on 05/06/2017.
 */

public class GameSoundFX {
    private Sound jumpSound;
    private Sound landSound;
    private Sound shootSound;
    private Sound heroDamageSound;
    private Sound blockExplosionSound;
    private Sound enemyExplosionSound;
    private Sound bulletHitSound;

    public GameSoundFX(){}

    public GameSoundFX(Downwell game){
        jumpSound = game.getAssetManager().get("jump.wav");
        landSound = game.getAssetManager().get("land.wav");
        shootSound = game.getAssetManager().get("shoot.wav");
        heroDamageSound = game.getAssetManager().get("hit.wav");
        blockExplosionSound = game.getAssetManager().get("bexplosion.wav");
        enemyExplosionSound = game.getAssetManager().get("eexplosion.wav");
        bulletHitSound = game.getAssetManager().get("hitbullet.wav");
    }

    public boolean isNull(){
        return false;
    }

    public void playJumpSound(){
        jumpSound.play();
    }

    public void playLandSound() {
        landSound.play();
    }

    public void playShootSound() {
        shootSound.play();
    }

    public void playHeroDamageSound() {
        heroDamageSound.play();
    }

    public void playBlockExplosionSound() {
        blockExplosionSound.play();
    }

    public void playEnemyExplosionSound() {
        enemyExplosionSound.play();
    }

    public void playBulletHitSound() {
        bulletHitSound.play();
    }
}
