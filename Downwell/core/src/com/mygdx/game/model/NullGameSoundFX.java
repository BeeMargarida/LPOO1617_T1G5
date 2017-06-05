package com.mygdx.game.model;

/**
 * Created by Utilizador on 05/06/2017.
 */

public class NullGameSoundFX extends GameSoundFX {

    @Override
    public boolean isNull() {
        return true;
    }

    public void playJumpSound(){}

    public void playLandSound() {}

    public void playShootSound() {}

    public void playHeroDamageSound() {}

    public void playBlockExplosionSound() {}

    public void playEnemyExplosionSound() {}

    public void playBulletHitSound() {}
}
