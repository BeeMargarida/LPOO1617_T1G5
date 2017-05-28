package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.HeroModel;

import static com.mygdx.game.model.HeroModel.state.JUMPING;

public class HeroView extends ElementView {

    //public enum state {STANDING, WALKING, JUMPING, SHOOTING}

    private Animation<TextureRegion> standingAnimation;
    private Animation<TextureRegion> walkingAnimation;
    private Animation<TextureRegion> rollingAnimation;
    private Texture jumpingFrame;
    private HeroModel.state lastState;

    public HeroView(Downwell game){
        super(game);
        sprite = createSprite(game);
        animation = getAnimation();
        lastState = JUMPING;
    }

    @Override
    public Sprite createSprite(Downwell game) {
        Texture texture1 = game.getAssetManager().get("r1.png");
        Texture texture2 = game.getAssetManager().get("r2.png");
        Texture texture3 = game.getAssetManager().get("r3.png");
        Texture texture4 = game.getAssetManager().get("r4.png");
        Texture texture5 = game.getAssetManager().get("r5.png");
        Texture texture6 = game.getAssetManager().get("r6.png");
        Texture texture7 = game.getAssetManager().get("r7.png");

        TextureRegion[] standingFrames = new TextureRegion[7];
        standingFrames[0] = new TextureRegion(texture1);
        standingFrames[1] = new TextureRegion(texture2);
        standingFrames[2] = new TextureRegion(texture3);
        standingFrames[3] = new TextureRegion(texture4);
        standingFrames[4] = new TextureRegion(texture5);
        standingFrames[5] = new TextureRegion(texture6);
        standingFrames[6] = new TextureRegion(texture7);

        standingAnimation = new Animation<TextureRegion>(.5f,standingFrames);

        Texture texture8 = game.getAssetManager().get("1.png");
        Texture texture9 = game.getAssetManager().get("2.png");
        Texture texture10 = game.getAssetManager().get("3.png");
        Texture texture11 = game.getAssetManager().get("4.png");
        Texture texture12 = game.getAssetManager().get("5.png");
        Texture texture13 = game.getAssetManager().get("6.png");
        Texture texture14 = game.getAssetManager().get("7.png");

        TextureRegion[] walkingFrames = new TextureRegion[7];
        walkingFrames[0] = new TextureRegion(texture8);
        walkingFrames[1] = new TextureRegion(texture9);
        walkingFrames[2] = new TextureRegion(texture10);
        walkingFrames[3] = new TextureRegion(texture11);
        walkingFrames[4] = new TextureRegion(texture12);
        walkingFrames[5] = new TextureRegion(texture13);
        walkingFrames[6] = new TextureRegion(texture14);

        walkingAnimation = new Animation<TextureRegion>(.5f,walkingFrames);

        Texture texture15 = game.getAssetManager().get("jr1.png");
        Texture texture16 = game.getAssetManager().get("jr2.png");
        Texture texture17 = game.getAssetManager().get("jr3.png");
        Texture texture18 = game.getAssetManager().get("jr4.png");
        Texture texture19 = game.getAssetManager().get("jr5.png");
        Texture texture20 = game.getAssetManager().get("jr6.png");
        Texture texture21 = game.getAssetManager().get("jr7.png");

        TextureRegion[] rollingFrames = new TextureRegion[7];
        rollingFrames[0] = new TextureRegion(texture15);
        rollingFrames[1] = new TextureRegion(texture16);
        rollingFrames[2] = new TextureRegion(texture17);
        rollingFrames[3] = new TextureRegion(texture18);
        rollingFrames[4] = new TextureRegion(texture19);
        rollingFrames[5] = new TextureRegion(texture20);
        rollingFrames[6] = new TextureRegion(texture21);

        rollingAnimation = new Animation<TextureRegion>(0.35f, rollingFrames);

        jumpingFrame = game.getAssetManager().get("jump.png");

        //animation = standingAnimation;
        //sprite = new Sprite(animation.getKeyFrame(0));
        animation = null;
        sprite = new Sprite(jumpingFrame);

        return sprite;
    }

    @Override
    public void update(ElementModel model) {
        super.update(model);

        flip = ((HeroModel) model).getFlip();


        HeroModel.state currState = ((HeroModel) model).getState();
        //System.out.println(currState);
        if(currState != lastState) {
            switch (((HeroModel) model).getState()) {
                case STANDING:
                    animation = standingAnimation;
                    sprite.setRegion(animation.getKeyFrame(0));
                    break;
                case WALKING:
                    animation = walkingAnimation;
                    sprite.setRegion(animation.getKeyFrame(0));
                    break;
                case JUMPING:
                    System.out.println("Jump");
                    animation = null;
                    sprite.setRegion(jumpingFrame);
                    break;
                case ROLLING:
                    animation = rollingAnimation;
                    sprite.setRegion(animation.getKeyFrame(0));
                    break;
            }
            lastState = currState;
        }

    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        if(lastState != JUMPING)
            sprite.setRegion(animation.getKeyFrame(stateTime,true));
        sprite.setFlip(flip,false);
    }

    /*
    public void setAnimation(state state){
        if(state == STANDING) {
            animation = standingAnimation;
            sprite = new Sprite(animation.getKeyFrame(0));
        }
        else if(state == WALKING){
            animation = walkingAnimation;
            sprite = new Sprite(animation.getKeyFrame(0));
        }
        else if(state == JUMPING){
            animation = null;
            sprite = new Sprite(jumpingFrame);
        }
    }
    */
}
