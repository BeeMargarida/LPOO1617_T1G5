package com.mygdx.game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Downwell;
import com.mygdx.game.controller.GameController;

import static com.mygdx.game.view.GameView.PIXEL_TO_METER;

/**
 * BulletBarView is a class that deals with the textures of the bullet bar.
 * @see StatusElementsView
 */
public class BulletBarView extends StatusElementsView{

    private Texture[] bar;
    private Image[] imgBar;
    private Image currImage;

    /**
     * Constructor of the class, it sets he camera, viewport, saves the textures needed, makes and image and sets it
     * in correct position.
     * @param game Downwell game, needed to get the assets
     */
    public BulletBarView(Downwell game) {
        camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ratio);
        this.viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_WIDTH * ratio, camera);
        this.stage = new Stage(viewport,game.getBatch());
        camera.update();
        viewport.apply();

        imgBar = new Image[9];
        bar = new Texture[9];
        bar[0] = game.getAssetManager().get("wb0.png");
        bar[1] = game.getAssetManager().get("wb1.png");
        bar[2] = game.getAssetManager().get("wb2.png");
        bar[3] = game.getAssetManager().get("wb3.png");
        bar[4] = game.getAssetManager().get("wb4.png");
        bar[5] = game.getAssetManager().get("wb5.png");
        bar[6] = game.getAssetManager().get("wb6.png");
        bar[7] = game.getAssetManager().get("wb7.png");
        bar[8] = game.getAssetManager().get("wb8.png");

        for(int i = 0; i < bar.length; i++){
            imgBar[i] = new Image(bar[i]);
        }

        currImage = imgBar[8];
        currImage.setPosition(VIEWPORT_WIDTH - VIEWPORT_WIDTH/4,(VIEWPORT_WIDTH*ratio)/100);
        stage.addActor(currImage);
    }

    /**
     * Gets the texture correspondent to the number of bullets still in the hero's possession and shows it.
     * @param obj GameController, needed to get the number of shots still not used.
     */
    @Override
    public void update(Object obj) {
        GameController controller = (GameController) obj;
        if(currImage != imgBar[controller.getShots()]){
            currImage.remove();
            currImage = imgBar[controller.getShots()];
            currImage.setPosition(VIEWPORT_WIDTH - VIEWPORT_WIDTH/4,(VIEWPORT_WIDTH*ratio)/100);
            stage.addActor(currImage);
        }
    }
}
