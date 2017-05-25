package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;

public abstract class EnemyView extends ElementView {

    public EnemyView(Downwell game){
        super(game);
        sprite = createSprite(game);
        animation = getAnimation();
    }

    @Override
    public void update(ElementModel model) {

        super.update(model);
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        sprite.setRegion(animation.getKeyFrame(stateTime,true));
    }
}
