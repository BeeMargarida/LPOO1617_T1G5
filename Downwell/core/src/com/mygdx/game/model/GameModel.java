package com.mygdx.game.model;

import com.badlogic.gdx.Game;
import com.mygdx.game.controller.GameController;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameModel {
    private HeroModel hero;
    private BatModel bat;
    private MapTileModel map[][];
    private int width = 11;
    private int depth;
    private char tiles[] = {'e','d','i'};

    public GameModel(float x, float y, int depth) {
        GameController.ARENA_WIDTH = width;
        GameController.ARENA_HEIGHT = depth;
        this.depth = depth;
        hero = new HeroModel(GameController.ARENA_WIDTH/2, GameController.ARENA_HEIGHT);
        //hero = new HeroModel(0.5f,0);
        makeMap();
        //bat = new BatModel(x,y);
    }


    private void makeMap() {
        map = new MapTileModel[depth][width];
        System.out.println("fuck");
        int ts, y = GameController.ARENA_HEIGHT, x;
        for (int i = 0; i < depth; i++) {
            int ic = 0;
            x = 0;
            y--;
            if (i < 5) {
                for (int j = 0; j < width; j++) {
                    if (j == 0)
                        map[i][j] = new MapTileModel(x, y, MapTileModel.TileType.L_WALL);
                    else if (j == width - 1)
                        map[i][j] = new MapTileModel(x, y, MapTileModel.TileType.R_WALL);
                    else
                        map[i][j] = null;
                    x++;
                }
                continue;
            }
            for (int j = 0; j < width; j++) {
                if (j == 0)
                    map[i][j] = new MapTileModel(x, y, MapTileModel.TileType.L_WALL);
                else if (j == width - 1)
                    map[i][j] = new MapTileModel(x, y, MapTileModel.TileType.R_WALL);
                else {
                    ts = tiles.length;
                    if (ic > 2)
                        ts = ts--;
                    int ti = random.nextInt() % ts;
                    if(ti < 0)
                        ti *= -1;
                    if (tiles[ti] == 'i') {
                        ic++;
                        map[i][j] = new MapTileModel(x, y, MapTileModel.TileType.I_BLOCK);
                    } else if (tiles[ti] == 'd')
                        map[i][j] = new MapTileModel(x, y, MapTileModel.TileType.D_BLOCK);
                    else
                        map[i][j] = null;
                }
                x++;
            }
        }
    }

    public HeroModel getHeroModel(){
        return hero;
    }
    public BatModel getBatModel() { return bat;}


    public int getWidth(){
        return width;
    }

    public int getDepth(){
        return depth;
    }

    public MapTileModel[][] getMap(){
        return map;
    }
}
