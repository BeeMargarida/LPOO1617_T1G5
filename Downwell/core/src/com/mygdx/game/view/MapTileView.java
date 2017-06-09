package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.MapTileModel;

/**
 * MapTileView is a class that deals with the view of a tile, identifying its type and choosing a texture according to it.
 * @see ElementView
 */
public class MapTileView extends ElementView {

    private MapTileModel.TileType tileType;

    /**
     * Constructor of the class, it creates a sprite and sets its animation to null, because it won't have animation.
     * @param game Downwell game, has the assets
     * @param tileType type of the tile
     */
    public MapTileView(Downwell game, MapTileModel.TileType tileType){
        this.tileType = tileType;
        sprite = createSprite(game);
        sprite.setFlip(flip, false);
        animation = getAnimation();
    }

    /**
     * Chooses a texture according to the type of the tile and the side of it.
     * @param game Downwell game, has the assets
     * @return sprite respective to the type of the tile
     */
    public Sprite createSprite(Downwell game){
        Texture texture;
        if(this.tileType == MapTileModel.TileType.R_WALL)
            texture = game.getAssetManager().get("sideWall.png");
        else if(this.tileType == MapTileModel.TileType.L_WALL) {
            flip = true;
            texture = game.getAssetManager().get("sideWall.png");
        }
        else if(this.tileType == MapTileModel.TileType.D_BLOCK)
            texture = game.getAssetManager().get("dBlock.png");
        else if(this.tileType == MapTileModel.TileType.I_BLOCK)
            texture = game.getAssetManager().get("iBlock.png");
        else {
            texture = game.getAssetManager().get("dBlock.png");
        }
        sprite = new Sprite(texture);
        return sprite;
    }

    /**
     * Override method.
     * @param delta
     */
    public void act(float delta) {
        return;
    }
}
