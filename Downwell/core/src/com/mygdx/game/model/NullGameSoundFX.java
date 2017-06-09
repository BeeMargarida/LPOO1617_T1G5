package com.mygdx.game.model;

/**
 * NullGameSoundFX is a class that serves as a null GameSoundFX, no playing any sound. It always returns null.
 */
public class NullGameSoundFX extends GameSoundFX {
    /**
     * Always returns true.
     * @return true
     */
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
