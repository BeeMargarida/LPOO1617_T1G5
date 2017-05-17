package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.MapTileModel;

/**
 * Created by Utilizador on 04/05/2017.
 */

public class MapTileView extends ElementView {

    private MapTileModel.TileType tileType;

    public MapTileView(Downwell game, MapTileModel.TileType tileType){
        super(game);
        this.tileType = tileType;
        sprite = createSprite(game);
        animation = getAnimation();
    }

    public Sprite createSprite(Downwell game){
        Texture texture;
        //System.out.println(tileType);
        if(this.tileType == MapTileModel.TileType.R_WALL)
            texture = game.getAssetManager().get("sideWall.png");
        else if(this.tileType == MapTileModel.TileType.L_WALL) {
            System.out.println("wall");
            flip = true;
            texture = game.getAssetManager().get("sideWall.png");
        }
        else if(this.tileType == MapTileModel.TileType.D_BLOCK)
            texture = game.getAssetManager().get("dBlock.png");
        else if(this.tileType == MapTileModel.TileType.I_BLOCK)
            texture = game.getAssetManager().get("dBlock.png");
        else {
            //System.out.println("outros");
            texture = game.getAssetManager().get("dBlock.png");
        }
        sprite = new Sprite(texture);
        return sprite;
    }

    public void update(ElementModel model) {
        super.update(model);
    }

    public void act(float delta) {
        return;
    }
}
