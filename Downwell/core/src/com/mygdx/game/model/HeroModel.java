package com.mygdx.game.model;


import static com.mygdx.game.model.HeroModel.state.JUMPING;

public class HeroModel extends ElementModel {

    public enum state {STANDING, WALKING, JUMPING, ROLLING, SHOOTING}

    private static final float INVIC_TIME = 2f;
    private state heroState;
    private boolean flip;
    private boolean invincible;
    private float invincibleTime;
    private HealthBarModel hp;

    public HeroModel(float x, float y) {
        super(x,y);
        heroState = JUMPING;
        flip = false;
        invincible = false;
        invincibleTime = INVIC_TIME;
        hp = new HealthBarModel(-3,y,4);
    }

    public ModelType getType(){
        return ModelType.HERO;
    }

    public state getState(){
        return heroState;
    }

    public void setState(state newState){
        heroState = newState;
    }

    public boolean getFlip(){
        return flip;
    }

    public void setFlip(boolean flip){
        this.flip = flip;
    }

    public void setInvincible(boolean flag){
        invincible = flag;
    }

    private boolean decreaseInvincibleTime(float delta){
        invincibleTime -= delta;
        if(invincibleTime  < 0)
            return true;
        else
            return false;
    }

    public void update(float delta){
        if(invincible){
            if(decreaseInvincibleTime(delta)){
                invincible = false;
                invincibleTime = INVIC_TIME;
            }
        }
        hp.udpate(this.y);
    }

    public boolean getInvincible(){
        return invincible;
    }

    public HealthBarModel getHp(){  return hp; }

    public boolean damage(){
        if(invincible)
            return false;
        hp.gotDamage();
        System.out.println(hp.getHp());
        if(hp.getHp() <= 0)
            return true;
        else
            return false;
    }
}
