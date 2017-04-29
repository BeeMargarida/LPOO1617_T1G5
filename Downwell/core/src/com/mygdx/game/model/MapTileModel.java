package com.mygdx.game.model;

/**
 * Created by Utilizador on 27/04/2017.
 */

public class MapTileModel extends ElementModel {

    public enum TileType {L_WALL, R_WALL, WALL, D_BLOCK, I_BLOCK}

    private TileType tileType;

    MapTileModel(float x, float y, TileType tileType){
        super(x,y);
        this.tileType = tileType;
    }

    public TileType getTileType(){
        return tileType;
    }
}
