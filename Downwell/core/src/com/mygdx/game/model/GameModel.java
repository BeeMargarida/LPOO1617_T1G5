package com.mygdx.game.model;

import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameModel {
    private GameStats stats;
    private HeroModel hero;

    private ArrayList<BulletModel> bullets;

    Pool<BulletModel> bulletPool = new Pool<BulletModel>() {
        protected BulletModel newObject() {
            return new BulletModel(0, 0, 0);
        }
    };

    private ArrayList<EnemyModel> enemies;
    private MapTileModel map[][];
    private int width = 11;
    private int depth;
    private int totalDepth;
    private char tiles[] = {'e','d','i'};
    private int tileprobfixed[] = {90, 8, 2};
    private int tileprob[] = {90, 8, 2};
    private int tilecounter[] = {0,0,0};
    private float dd = 0.5f;
    private int ic = 0;
    private static final float BULLET_ANG_VARIATION = 5;
    public static final int INIT_MAP_DEPTH = 30;
    public static final int FINAL_MAP_DEPTH = 20;
    public static final int HERO_POS_OFFSET = 10;

    private boolean gameOver;
    private boolean nextLevel;

    public GameModel(GameConfig config, GameStats stats) {
        this.stats = stats;
        this.depth = config.getLevelDepth();
        this.totalDepth = depth+INIT_MAP_DEPTH+FINAL_MAP_DEPTH;
        hero = new HeroModel((float) width/2+dd, totalDepth-HERO_POS_OFFSET, stats.getHeroHp());
        bullets = new ArrayList<BulletModel>();
        makeMap();
        enemies = new ArrayList<EnemyModel>();
        gameOver = false;
        nextLevel = false;
        addEnemies(config.getEnemiesNumber());
    }

    public boolean getGameOver() { return gameOver; }

    public boolean getNextLevel() { return nextLevel; }

    public void checkGameStatus(){
        if(hero.getHp() <= 0){
            gameOver = true;
            System.out.println("You are DEAD!");
            return;
        }
        if(hero.getY() <= HERO_POS_OFFSET){
            nextLevel = true;
            return;
        }
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
        map = new MapTileModel[totalDepth][width];
        int ts, y = totalDepth, x;
        for (int i = 0; i < totalDepth; i++) {
            ic = 0;
            x = 0;
            y--;
            if (i < INIT_MAP_DEPTH || i > (totalDepth-FINAL_MAP_DEPTH)) {
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
                if(i > map.length - 2)
                    return;
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
                        enemies.add(new SnailModel((map[i][j - 1].getX() + 1), map[i][j - 1].getY()));
                    } else if (j == map[i].length - 1 && map[i][j] == null) {
                        enemies.add(new SnailModel((map[i][j - 1].getX() - 1), map[i][j - 1].getY()));
                    } else
                        break;
                    number--;
                    i += 4;
                }
                if(i >= map.length)
                    return;
            }
        }
    }

    public HeroModel getHeroModel(){
        return hero;
    }

    public ArrayList<BulletModel> getBullets() {
        return bullets;
    }

    public ArrayList<EnemyModel> getEnemies() { return enemies; }

    public void removeTile(MapTileModel model){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == model)
                    map[i][j] = null;
            }
        }
    }

    public void removeEnemy(EnemyModel model) {
        enemies.set(enemies.indexOf(model), null);
    }

    public void removeBullet(BulletModel bullet){
        bullets.remove(bullet);
        bulletPool.free(bullet);
    }

    public BulletModel createBullet(HeroModel hero) {
        BulletModel bullet = bulletPool.obtain();

        bullet.setForRemoval(false);
        bullet.setPosition(hero.getX(), hero.getY()-0.8f);
        float rotation = random.nextInt()%BULLET_ANG_VARIATION -90;
        rotation *= Math.PI / 180.0;
        bullet.setRotation(rotation);
        bullet.setTimeToLive(.5f);

        bullets.add(bullet);

        return bullet;
    }

    public int getWidth(){
        return width;
    }

    public int getDepth(){
        return totalDepth;
    }

    public MapTileModel[][] getMap(){
        return map;
    }

    public GameStats getStats() { return stats; }
}
