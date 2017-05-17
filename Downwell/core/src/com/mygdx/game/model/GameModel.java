package com.mygdx.game.model;

import com.badlogic.gdx.Game;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.view.BubbleView;

import java.util.Arrays;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameModel {
    private HeroModel hero;
    /*private BatModel bat;
    private BubbleModel bubble;*/
    private EnemyModel enemies[];
    private MapTileModel map[][];
    private int width = 11;
    private int depth;
    private char tiles[] = {'e','d','i'};
    private int tileprobfixed[] = {90, 8, 2};
    private int tileprob[] = {90, 8, 2};
    private int tilecounter[] = {0,0,0};
    private float dd = 0.5f;
    private int ic = 0;

    public GameModel(float x, float y, int depth, int number) {
        GameController.ARENA_WIDTH = width;
        GameController.ARENA_HEIGHT = depth;
        this.depth = depth;
        hero = new HeroModel(GameController.ARENA_WIDTH/2, GameController.ARENA_HEIGHT);
        /*bat = new BatModel(GameController.ARENA_WIDTH/2,40);
        bubble = new BubbleModel(GameController.ARENA_WIDTH/3,20);*/
        makeMap();
        enemies = new EnemyModel[number];
        addEnemies(number);
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
        //System.out.println(value);
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
    }

    private void addEnemies(int number){
        Random rand = new Random();
        int bats = rand.nextInt(number - 1 + 1);
        int bubbles = number - bats;
        int index = 0;
        for(int i = 3; i < map.length - 1; i += 3){
            for(int j = 1; j < map[i].length - 1; j++){
                if(map[i][j] != null && map[i+1][j] == null && bats > 0) {
                    enemies[index] = new BatModel(map[i][j].getX(), map[i][j].getY() - 1);
                    bats--;
                    index++;
                    break;
                }
                else if(map[i][j] == null && map[i+1][j] != null && bubbles > 0){
                    enemies[index] = new BubbleModel(map[i+1][j].getX(), map[i+1][j].getY() - 1);
                    bubbles--;
                    index++;
                    break;
                }
            }
        }
        /*for(int i = 0; i < number; i++){
            for(int j = 5; j < map.length - 1; j++){
                for(int k = 1; k < map[j].length - 1; k++){
                    if(k + 2 < map[j].length - 1) {
                        if (map[j][k] != null && map[j + 1][k] == null && bats > 0) {
                            enemies[i] = new BatModel(j + 1, k);
                            bats--;
                            //j += 2;
                            //break;
                        }
                        if (map[j][k] != null && map[j][k + 1] != null && map[j][k + 2] != null) {
                            enemies[i] = new BubbleModel(j, k);
                            bubbles--;
                            //j += 2;
                            //break;
                        }
                    }
                    break;
                }
            }
        }*/
    }

    public HeroModel getHeroModel(){
        return hero;
    }

  /*  public BatModel getBatModel() {
        return bat;
    }
    public BubbleModel getBubbleModel() { return bubble; }*/

    public EnemyModel[] getEnemies() { return enemies; }

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
