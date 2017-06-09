package com.mygdx.game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.HeroModel;
import static com.mygdx.game.view.GameView.PIXEL_TO_METER;

/**
 * HealthBarView is a class that deals with the view of the health points of the hero.
 * @see StatusElementsView
 */
public class HealthBarView extends  StatusElementsView {

    private Texture tex1;
    private Texture tex2;
    private Texture tex3;
    private Texture tex4;

    /**
     * Constructor class, it gets the assets needed from the game and saves them, it sets the camera and viewport for the stage. It
     * sets as image and adds has actor.
     * @param game Downwell game, has the assets
     */
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

    /**
     * Depending on the health points of the hero, it sets as image the appropriate texture.
     * @param obj HeroModel, to get the health points
     */
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
