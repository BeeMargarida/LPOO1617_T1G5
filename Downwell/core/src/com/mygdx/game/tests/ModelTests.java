//package com.mygdx.game.tests;

import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.BatModel;
import com.mygdx.game.model.EnemyModel;
import com.mygdx.game.model.GameConfig;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.GameStats;
import com.mygdx.game.model.MapTileModel;
import com.mygdx.game.model.NullGameSoundFX;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class ModelTests extends GameTest{

    private void createTestArena(GameModel model){
        int depth = 15;
        MapTileModel map[][] = model.getMap();
        for(int i = 0; i < map[depth].length; i++){
            if(map[depth][i]==null){
                map[depth][i] =  new MapTileModel(i+0.5f,map.length-depth+0.5f, MapTileModel.TileType.I_BLOCK);
            }
        }
    }

    private void addTestEnemy(EnemyModel enemy, GameModel model){
        ArrayList<EnemyModel> enemies = model.getEnemies();
        enemies.add(enemy);
    }

    private void addTestBlock(int posX, int posY, GameModel model){
        MapTileModel map[][] = model.getMap();
        map[posY][posX] = new MapTileModel(posX+0.5f, model.getDepth()-posY+0.5f, MapTileModel.TileType.D_BLOCK);;
    }

    private float calcDist(float ePosX, float ePosY, float hPosX, float hPosY){
        float diffX= hPosX-ePosX;
        float diffY = hPosY -ePosY;
        return (float) Math.sqrt(diffX*diffX+diffY+diffY);
    }

    @Test
    public void testScoreIncrease() {
        GameStats stats = new GameStats(1,4);
        GameConfig config = new GameConfig(50,0);

        GameModel model = new GameModel(config,stats,new NullGameSoundFX());
        createTestArena(model);
        addTestEnemy(new BatModel(6,model.getDepth()-14), model);
        GameController controller = new GameController(model);
        int timeout = 100, i;
        for(i = 0; i < timeout; i++){
            controller.update(1/60f);
            if(stats.getScore() > 0) {
                break;
            }
        }
        if(i == timeout)
            fail();
    }

    @Test
    public void testEnemyDeletedAfterKilled(){
        GameStats stats = new GameStats(1,4);
        GameConfig config = new GameConfig(50,0);
        GameModel model = new GameModel(config,stats,new NullGameSoundFX());
        createTestArena(model);
        addTestEnemy(new BatModel(6,model.getDepth()-14), model);
        if(model.getEnemies().contains(null)) {
            fail();
        }
        GameController controller = new GameController(model);
        int timeout = 100, i;
        for(i = 0; i < timeout; i++){
            controller.update(1/60f);
            if(stats.getScore() > 0) {
                break;
            }
        }
        controller.update(1/60f);

        for(i = 0; i < model.getEnemies().size(); i++){
            if(model.getEnemies().get(i) == null)
                break;
        }

        assertTrue(model.getEnemies().contains(null));
    }

    @Test
    public void testBlockDeletedAfterDestroyed(){
        GameStats stats = new GameStats(1,4);
        GameConfig config = new GameConfig(50,0);
        GameModel model = new GameModel(config,stats,new NullGameSoundFX());
        createTestArena(model);
        addTestBlock(6,14,model);
        if(model.getEnemies().contains(null)) {
            fail();
        }
        GameController controller = new GameController(model);
        int timeout = 100, i;
        for(i = 0; i < timeout; i++){
            controller.update(1/60f);
            controller.shootHero();
        }

        assertNull(model.getMap()[14][6]);
    }

    @Test
    public void testHeroDamage() {
        GameStats stats = new GameStats(1,4);
        GameConfig config = new GameConfig(50,0);
        GameModel model = new GameModel(config,stats,new NullGameSoundFX());
        createTestArena(model);
        BatModel bat =  new BatModel(8, model.getDepth()-14);
        addTestEnemy(bat, model);
        GameController controller = new GameController(model);
        int timeout = 100, i;
        for(i = 0; i < timeout; i++){
            controller.update(1/60f);
            if(model.getHeroModel().getHp() < 4)
                break;
        }
        if(i == timeout)
            fail();
    }

    @Test
    public void generateCorrectNumberOfEnemies(){
        GameStats stats = new GameStats(1,4);
        GameConfig config = new GameConfig(50,0);
        GameModel model = new GameModel(config,stats,new NullGameSoundFX());
        assertTrue(model.getEnemies().size() <= 20);
    }

    @Test
    public void testHeroInBounds(){
        GameStats stats = new GameStats(1,4);
        GameConfig config = new GameConfig(50,0);
        GameModel model = new GameModel(config,stats,new NullGameSoundFX());
        createTestArena(model);
        GameController controller = new GameController(model);
        int timeout = 100, i;
        for(i = 0; i < timeout; i++){
            controller.update(1/60f);
            controller.moveHeroRight();
        }
        float posX = model.getHeroModel().getX();
        float posY = model.getHeroModel().getY();
        assertTrue(posX > 0 && posX < model.getWidth()+0.5 && posY > model.getDepth()-15);
    }

    @Test
    public void testHeroDestroysEnemyWithBullets(){
        GameStats stats = new GameStats(1,4);
        GameConfig config = new GameConfig(50,0);
        GameModel model = new GameModel(config,stats,new NullGameSoundFX());
        createTestArena(model);
        addTestEnemy(new BatModel(6,model.getDepth()-14), model);
        if(model.getEnemies().contains(null)) {
            fail();
        }
        GameController controller = new GameController(model);
        int timeout = 100, i;
        for(i = 0; i < timeout; i++){
            controller.update(1/60f);
            controller.shootHero();
            if(stats.getKills() > 0) {
                break;
            }
        }

        if(i == timeout)
            fail();
    }

    @Test
    public void testEnemyFollowsHero(){
        GameStats stats = new GameStats(1,4);
        GameConfig config = new GameConfig(50,0);
        GameModel model = new GameModel(config,stats,new NullGameSoundFX());
        createTestArena(model);
        BatModel bat =  new BatModel(8, model.getDepth()-14);
        addTestEnemy(bat, model);
        GameController controller = new GameController(model);
        int timeout = 100, i, distInfCounter = 0, distMinTimes = 5;
        float dist = calcDist(bat.getX(), bat.getY(), model.getHeroModel().getX(), model.getHeroModel().getY());
        float newDist;
        for(i = 0; i < timeout; i++){
            controller.update(1/60f);
            newDist = calcDist(bat.getX(), bat.getY(), model.getHeroModel().getX(), model.getHeroModel().getY());
            if(newDist < dist)
                distInfCounter++;
            if(distInfCounter > distMinTimes)
                break;
            dist = newDist;
        }
        if(i == timeout)
            fail();
    }

    @Test
    public void testGameOver(){
        GameStats stats = new GameStats(1,1);
        GameConfig config = new GameConfig(50,0);
        GameModel model = new GameModel(config,stats,new NullGameSoundFX());
        createTestArena(model);
        addTestEnemy(new BatModel(8,model.getDepth()-14), model);
        GameController controller = new GameController(model);
        int timeout = 100, i;
        for(i = 0; i < timeout; i++){
            controller.update(1/60f);
            if(model.getHeroModel().getHp() <= 0) {
                break;
            }
        }

        controller.update(1/60f);

        assertTrue(model.getGameOver());
    }

    @Test
    public void testCorrectMapLayout(){
        GameStats stats = new GameStats(1,4);
        GameConfig config = new GameConfig(50,0);
        GameModel model = new GameModel(config,stats,new NullGameSoundFX());
        MapTileModel map[][] = model.getMap();
        for(int i = 0; i < map.length; i++) {
            if (i < GameModel.INIT_MAP_DEPTH || i > GameModel.INIT_MAP_DEPTH+50){
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] != null && j != 0 && j != map[i].length - 1) {
                        fail();
                    }
                }
            }
        }
    }

}
