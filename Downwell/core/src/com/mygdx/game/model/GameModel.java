package com.mygdx.game.model;

import com.mygdx.game.controller.GameController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameModel {
    private HeroModel hero;
    private ArrayList<EnemyModel> enemies;
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
        enemies = new ArrayList<EnemyModel>();
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
        char en[] = {'b', 'u', 's'};
        Random rand = new Random();
        for(int i = 0; i < map.length - 1; i++) {
            for (int j = 1; j < map[i].length - 1; j++) {
                if (number == 0) {
                    return;
                }
                int n = rand.nextInt(2 + 1);
                if (en[n] == 'b' && map[i][j] != null && map[i + 1][j] == null) {
                    enemies.add(new BatModel(map[i][j].getX(), map[i][j].getY() - 1));
                    number--;
                    i += 4;
                } else if (en[n] == 'u' && map[i][j] == null && map[i + 1][j] != null) {
                    enemies.add(new BubbleModel(map[i + 1][j].getX(), map[i + 1][j].getY() - 1));
                    number--;
                    i += 4;
                } else if (en[n] == 's' && i > 8) {
                    if (j == 1 && map[i][j] == null) {
                        enemies.add(new SnailModel((float) (map[i][j - 1].getX() + 1), map[i][j - 1].getY()));
                    } else if (j == map[i].length - 1 && map[i][j] == null) {
                        enemies.add(new SnailModel((float) (map[i][j - 1].getX() - 1), map[i][j - 1].getY()));
                    } else
                        break;
                    number--;
                    i += 4;
                }
            }
        }
    }

    public HeroModel getHeroModel(){
        return hero;
    }

  /*  public BatModel getBatModel() {
        return bat;
    }
    public BubbleModel getBubbleModel() { return bubble; }*/

    public ArrayList<EnemyModel> getEnemies() { return enemies; }

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
