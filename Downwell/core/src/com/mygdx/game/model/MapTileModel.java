package com.mygdx.game.model;

/**
 * Created by Utilizador on 27/04/2017.
 */

public class MapTileModel extends ElementModel {

    public enum TileType {L_WALL, R_WALL, WALL, D_BLOCK, I_BLOCK}

    private boolean flagForRemoval;
    private TileType tileType;

    MapTileModel(float x, float y, TileType tileType){
        super(x,y);
        this.tileType = tileType;
        flagForRemoval = false;
    }

    public void setForRemoval() {
        flagForRemoval = true;
    }

    public boolean getForRemoval() {
        return flagForRemoval;
    }

    public TileType getTileType(){
        return tileType;
    }

    public ModelType getType(){
        switch(tileType){
            case L_WALL:
                return ModelType.MAPTILE_L_WALL;
            case R_WALL:
                return ModelType.MAPTILE_R_WALL;
            case D_BLOCK:
                return ModelType.MAPTILE_D_BLOCK;
            case I_BLOCK:
                return ModelType.MAPTILE_I_BLOCK;
            default:
                return  ModelType.MAPTILE_D_BLOCK;
        }
    }
}
