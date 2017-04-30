package com.mygdx.game.model;

import com.badlogic.gdx.Game;
import com.mygdx.game.controller.GameController;

import java.util.Arrays;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameModel {
    private HeroModel hero;
    private BatModel bat;
    private MapTileModel map[][];
    private int width = 11;
    private int depth;
    private char tiles[] = {'e','d','i'};
    private int tileprobfixed[] = {90, 8, 2};
    private int tileprob[] = {90, 8, 2};
    private int tilecounter[] = {0,0,0};
    private float dd = 0.5f;
    private int ic = 0;

    public GameModel(float x, float y, int depth) {
        GameController.ARENA_WIDTH = width;
        GameController.ARENA_HEIGHT = depth;
        this.depth = depth;
        hero = new HeroModel(GameController.ARENA_WIDTH/2, GameController.ARENA_HEIGHT);
        //hero = new HeroModel(0.5f,0);
        makeMap();
        //bat = new BatModel(x,y);
    }



    private MapTileModel getTile(int x, int y){
        tileprob = Arrays.copyOf(tileprobfixed,tileprobfixed.length);

        if(ic > 2) {
            tileprob[1] += tileprob[2];
            tileprob[2] = 0;
        }

        int probCounter = 0;
        int value = random.nextInt()% 100;
        value = Math.abs(value);
        System.out.println(value);
        int i;
        for(i = 0; i < tiles.length; i++){
            if(probCounter+tileprob[i] == probCounter)
                continue;

            if(value >= probCounter && value < probCounter+tileprob[i])
                break;
            else
                probCounter += tileprob[i];
        }

        if(i >= tiles.length)
            return null;

        tilecounter[i]++;

        if(tiles[i] == 'e')
            return null;
        else if(tiles[i] == 'd') {
            return new MapTileModel(((float) x)+dd, ((float) y)+dd, MapTileModel.TileType.D_BLOCK);
        }
        else if(tiles[i] == 'i') {
            ic++;
            return new MapTileModel( ((float) x)+dd, ((float) y)+dd, MapTileModel.TileType.I_BLOCK);
        }
        else
            return null;
    }

    private void makeMap() {
        //random.setSeed();
        map = new MapTileModel[depth][width];
        int ts, y = GameController.ARENA_HEIGHT, x;
        for (int i = 0; i < depth; i++) {
            ic = 0;
            x = 0;
            y--;
            if (i < 5) {
                for (int j = 0; j < width; j++) {
                    if (j == 0)
                        map[i][j] = new MapTileModel(((float) x)+dd, ((float) y)+dd, MapTileModel.TileType.L_WALL);
                    else if (j == width - 1)
                        map[i][j] = new MapTileModel( ((float) x)+dd, ((float) y)+dd, MapTileModel.TileType.R_WALL);
                    else
                        map[i][j] = null;
                    x++;
                }
                continue;
            }
            for (int j = 0; j < width; j++) {
                if (j == 0)
                    map[i][j] = new MapTileModel( ((float) x)+dd, ((float) y)+dd, MapTileModel.TileType.L_WALL);
                else if (j == width - 1)
                    map[i][j] = new MapTileModel( ((float) x)+dd, ((float) y)+dd, MapTileModel.TileType.R_WALL);
                else
                    map[i][j] = getTile(x,y);
                x++;
            }
        }
        System.out.println(tilecounter[0]);
        System.out.println(tilecounter[1]);
        System.out.println(tilecounter[2]);
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
