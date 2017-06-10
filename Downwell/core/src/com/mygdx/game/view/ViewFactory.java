package com.mygdx.game.view;

import com.mygdx.game.Downwell;
import com.mygdx.game.model.ElementModel;
import com.mygdx.game.model.MapTileModel;

import java.util.HashMap;
import java.util.Map;

/**
 * ViewFactory is a implementation of the Factory Design Pattern, returning a view according to the type of element given.
 */
public class ViewFactory {
    private static Map<ElementModel.ModelType, ElementView> cache =
            new HashMap<ElementModel.ModelType, ElementView>();

    /**
     * Returns the view correspondent to the model given.
     * @param game Downwell game, has the assets
     * @param model model that contains information about the type of object
     * @return the view correspondent to the model
     */
    public static ElementView makeView(Downwell game, ElementModel model) {
       if (!cache.containsKey(model.getType())) {
            //if (model.getType() == ElementModel.ModelType.HERO) cache.put(model.getType(), new HeroView(game));
            if (model.getType() == ElementModel.ModelType.BULLET) cache.put(model.getType(), new BulletView(game));
            if (model.getType() == ElementModel.ModelType.MAPTILE_L_WALL) cache.put(model.getType(), new MapTileView(game, MapTileModel.TileType.L_WALL));
            if (model.getType() == ElementModel.ModelType.MAPTILE_R_WALL) cache.put(model.getType(), new MapTileView(game, MapTileModel.TileType.R_WALL));
            if (model.getType() == ElementModel.ModelType.MAPTILE_D_BLOCK) cache.put(model.getType(), new MapTileView(game, MapTileModel.TileType.D_BLOCK));
            if (model.getType() == ElementModel.ModelType.MAPTILE_I_BLOCK) cache.put(model.getType(), new MapTileView(game, MapTileModel.TileType.I_BLOCK));
       }
        return cache.get(model.getType());
    }
}
