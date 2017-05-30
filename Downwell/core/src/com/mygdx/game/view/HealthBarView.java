package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.HealthBarModel;


public class HealthBarView extends ElementView {

    private Texture tex1;
    private Texture tex2;
    private Texture tex3;
    private Texture tex4;

    public HealthBarView(Downwell game){
        super(game);
        tex1 = game.getAssetManager().get("lifebar1.png");
        tex2 = game.getAssetManager().get("lifebar2.png");
        tex3 = game.getAssetManager().get("lifebar3.png");
        tex4 = game.getAssetManager().get("lifebar4.png");
        sprite = createSprite(game);
    }

    @Override
    public Sprite createSprite(Downwell game) {

        animation = null;
        sprite = new Sprite(tex4);
        return sprite;
    }

    @Override
    public void update(ElementModel model) {
        super.update(model);
        switch(((HealthBarModel) model).getHp()){
            case 1:
                this.sprite = new Sprite(tex1);
            case 2:
                this.sprite = new Sprite(tex2);
            case 3:
                this.sprite = new Sprite(tex3);
            case 4:
                this.sprite = new Sprite(tex4);
        }
    }

    @Override
    public void act(float delta) {

    }
}
