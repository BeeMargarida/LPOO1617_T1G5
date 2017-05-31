package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.HeroModel;

import static com.mygdx.game.view.GameView.PIXEL_TO_METER;


public class HealthBarView extends Stage {

    private Downwell game;
    protected Sprite sprite;
    protected Image img;
    private Texture tex1;
    private Texture tex2;
    private Texture tex3;
    private Texture tex4;
    static final int VIEWPORT_WIDTH = 33;

    public HealthBarView(Downwell game){
        tex1 = game.getAssetManager().get("lifebar1.png");
        tex2 = game.getAssetManager().get("lifebar2.png");
        tex3 = game.getAssetManager().get("lifebar3.png");
        tex4 = game.getAssetManager().get("lifebar4.png");
        this.game = game;
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        setViewport(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_WIDTH * ratio));
        getViewport().update(1,1);
        //createSprite();
        //sprite.setPosition(0, 0);
        img = new Image(tex4);
        addActor(img);
    }

    public Sprite createSprite() {
        sprite = new Sprite(tex4);
        return sprite;
    }

    public void update(ElementModel model) {
        switch(((HeroModel) model).getHp()){
            case 1:
                //sprite.setRegion(tex1);
                img = new Image(tex1);
                break;
            case 2:
                img = new Image(tex2);
                //sprite.setRegion(tex2);
                break;
            case 3:
                img = new Image(tex3);
                //sprite.setRegion(tex3);
                break;
            case 4:
                img = new Image(tex4);
                //sprite.setRegion(tex4);
                break;
        }
    }

    @Override
    public void draw() {
        //super.draw();
        img.draw(game.getBatch(),1);
    }
}
