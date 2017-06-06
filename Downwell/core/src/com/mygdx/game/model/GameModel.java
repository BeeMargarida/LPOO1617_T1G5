package com.mygdx.game.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * GameModel class deals with the construction of all the entities that make the game, such as the map, the enemies,
 * the hero, the stats, the sound and the game configuration. It has methods that check the state of the game, the
 * removal of entities that were set for removal and the creation of bullets.
 */
public class GameModel {
    private GameStats stats;
    private GameSoundFX soundFX;
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

    /**
     * Constructor of the class, creates all the entities of the level, such has hero, map, and fills the map with enemies.
     * @param config configuration of the level, such has depth and number of enemies
     * @param stats score of the game
     * @param soundFX sound of the game
     */
    public GameModel(GameConfig config, GameStats stats, GameSoundFX soundFX) {
        this.stats = stats;
        this.soundFX = soundFX;
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

    /**
     * Returns the flag gameOver.
     * @return true if the game ended, false if not
     */
    public boolean getGameOver() { return gameOver; }

    /**
     * Returns the flag nextLevel
     * @return true if the next step is to go to other level, false if not
     */
    public boolean getNextLevel() { return nextLevel; }

    /**
     * Checks the state of game over. If the hero has lost all its health points, the game is definitely over.
     * If the hero has passed the end of the level, a next level will follow.
     */
    public void checkGameStatus(){
        if(hero.getHp() <= 0){
            gameOver = true;
            return;
        }
        if(hero.getY() <= HERO_POS_OFFSET){
            nextLevel = true;
            return;
        }
    }

    /**
     * Returns a type of MapTileModel after calculations made based on the probabilities (tileprobfixed) of each type, including not having a block
     * att all.
     * @param x x coordinate of the position we want to fill
     * @param y y coordinate of the position we want fo fill
     * @return null if there will be no block in that position or MapTileModel of specific kind
     */
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

    /**
     * Goes through all the positions of the map, fills the borders with indestructible tiles and the middle with empty spaces or two
     * different types of tiles, destroyable or not.
     * @see GameModel#getTile(int, int)
     */
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

    /**
     * Fills the map with different types of enemies according to certain areas. The bats and bubble always appear under a block, and the
     * snails appear near the border wall.
     * @param number number of enemies to be created
     */
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

    /**
     * Returns the hero model.
     * @return hero model
     */
    public HeroModel getHeroModel(){
        return hero;
    }

    /**
     * Returns a ArrayList with all the bullets.
     * @return ArrayList of bullets
     */
    public ArrayList<BulletModel> getBullets() {
        return bullets;
    }

    /**
     * Returns a ArrayList with all the enemies.
     * @return ArrayList of enemies
     */
    public ArrayList<EnemyModel> getEnemies() { return enemies; }

    /**
     * Goes through all the map and removes (equals to null) the tile equal to the one given as parameter.
     * @param model tile we want to remove
     */
    public void removeTile(MapTileModel model){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == model)
                    map[i][j] = null;
            }
        }
    }

    /**
     * Remove a enemy from the ArrayList of enemies.
     * @param model model of the enemy we want to erase
     */
    public void removeEnemy(EnemyModel model) {
        enemies.set(enemies.indexOf(model), null);
    }

    /**
     * Removes a bullet from the ArrayList of bullets and from the pool.
     * @param bullet model of the bullet we want to erase
     */
    public void removeBullet(BulletModel bullet){
        bullets.remove(bullet);
        bulletPool.free(bullet);
    }

    /**
     * Creates a bullet under the hero, with a random rotation between a limit. It is added to the ArrayList of bullets.
     * @param hero model of the hero to get the position of the bullet
     * @return model of bullet created
     */
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

    /**
     * Returns the width of the map.
     * @return width of the map
     */
    public int getWidth(){
        return width;
    }

    /**
     * Returns the height (depth) of the map.
     * @return depth of the map
     */
    public int getDepth(){
        return totalDepth;
    }

    /**
     * Returns the map of the level.
     * @return map of the level
     */
    public MapTileModel[][] getMap(){
        return map;
    }

    /**
     * Return the stats of the game.
     * @return stats of the game
     */
    public GameStats getStats() { return stats; }

    /**
     * Return the sound of the level.
     * @return sound of the level
     */
    public GameSoundFX getSoundFX() {  return soundFX; }
}
