package com.mygdx.game.model;

/**
 * MapTileModel is a class that contains and deals with all the information about a MapTile,including its type of
 * tile and its flag for removal.
 */
public class MapTileModel extends ElementModel {

    public enum TileType {L_WALL, R_WALL, D_BLOCK, I_BLOCK}

    private boolean flagForRemoval;
    private TileType tileType;

    /**
     * Constructor of class. Sets its position with the values given and its type with the one given.
     * @param x x coordinate of its position
     * @param y y coordinate of its position
     * @param tileType type of tile
     */
    public MapTileModel(float x, float y, TileType tileType){
        super(x,y);
        this.tileType = tileType;
        flagForRemoval = false;
    }

    /**
     * Sets the flag flagForRemoval to true, meaning that it will be erased when the verification is made.
     */
    public void setForRemoval() {
        flagForRemoval = true;
    }

    /**
     * Returns the value o the flag flagForRemoval.
     * @return value of flagForRemoval
     */
    public boolean getForRemoval() {
        return flagForRemoval;
    }

    /**
     * Returns the type of tile.
     * @return this tile's TileType
     */
    public TileType getTileType(){
        return tileType;
    }

    /**
     * Checks the tileType of this tile and returns the respective ModelType.
     * @return ModelType correspondent to the tileType
     */
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
