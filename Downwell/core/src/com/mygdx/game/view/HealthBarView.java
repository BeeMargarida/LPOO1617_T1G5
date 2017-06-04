package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.HeroModel;

import static com.mygdx.game.view.GameView.PIXEL_TO_METER;


public class HealthBarView extends  StatusElementsView {

    private Texture tex1;
    private Texture tex2;
    private Texture tex3;
    private Texture tex4;

    public HealthBarView(Downwell game){
        tex1 = game.getAssetManager().get("lifebar1.png");
        tex2 = game.getAssetManager().get("lifebar2.png");
        tex3 = game.getAssetManager().get("lifebar3.png");
        tex4 = game.getAssetManager().get("lifebar4.png");

        camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ratio);
        this.viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_WIDTH * ratio, camera);
        this.stage = new Stage(viewport,game.getBatch());
        camera.update();
        viewport.apply();


        img = new Image(tex4);
        img.setPosition(0,VIEWPORT_WIDTH*ratio - VIEWPORT_WIDTH*ratio/10);
        img.scaleBy(1);
        stage.addActor(img);
    }

    public void update(Object obj) {
        HeroModel hero = (HeroModel) obj;
        stage.unfocus(img);
        switch(hero.getHp()){
            case 1:
                img = new Image(tex1);
                img.setPosition(0,VIEWPORT_WIDTH*ratio -VIEWPORT_WIDTH*ratio/10);
                img.scaleBy(1);
                stage.addActor(img);
                break;
            case 2:
                img = new Image(tex2);
                img.setPosition(0,VIEWPORT_WIDTH*ratio -VIEWPORT_WIDTH*ratio/10);
                img.scaleBy(1);
                stage.addActor(img);
                break;
            case 3:
                img = new Image(tex3);
                img.setPosition(0,VIEWPORT_WIDTH*ratio - VIEWPORT_WIDTH*ratio/10);
                img.scaleBy(1);
                stage.addActor(img);
                break;
            case 4:
                img = new Image(tex4);
                img.setPosition(0,VIEWPORT_WIDTH*ratio - VIEWPORT_WIDTH*ratio/10);
                img.scaleBy(1);
                stage.addActor(img);
                break;
        }
    }
}
