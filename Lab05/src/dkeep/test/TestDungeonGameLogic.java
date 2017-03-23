package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.Logic;
import dkeep.logic.Map;
import dkeep.logic.DungeonMap;
import dkeep.logic.Game;
import dkeep.logic.KeepMap;
import dkeep.logic.CellPosition;
import dkeep.logic.DungeonLogic;
import dkeep.logic.KeepMap;
import dkeep.logic.Hero;
import dkeep.logic.KeepLogic;

public class TestDungeonGameLogic  {

	char[][] m = {{'X','X','X','X','X'},
					{'X',' ',' ','G','X'},
					{'I',' ',' ',' ','X'},
					{'I','k',' ',' ','X'},
					{'X','X','X','X','X'}};
	
	@Test
	public void testMoveHeroIntoFreeCell() {
		Map map = new Map(m,new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new DungeonLogic(map,heropos, numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		assertEquals(new CellPosition(1,1),logic.getHeroPosition());
		game.moveHero('s');
		assertEquals(new CellPosition(2,1),logic.getHeroPosition());
	}
	
	@Test
	public void testHeroIsCapturedByGuard() {
		Map map = new Map(m, new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new DungeonLogic(map,heropos, numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		assertFalse(game.isGameOver());
		game.moveHero('d');
		assertTrue(game.isGameOver());
	}
	
	@Test
	public void testMoveHeroIntoWall(){
		Map map = new Map(m, new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new DungeonLogic(map,heropos, numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		assertEquals(new CellPosition(1,1),logic.getHeroPosition());
		game.update('w');
		assertEquals(new CellPosition(1,1),logic.getHeroPosition());
	}
	
	@Test
	public void testMoveHeroIntoClosedDoors(){
		Map map = new Map(m, new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new DungeonLogic(map,heropos, numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		game.moveHero('s');
		game.moveHero('a');
		assertEquals(new CellPosition(2,1),logic.getHeroPosition());
		assertFalse(game.victory());
	}
	
	@Test
	public void testMoveHeroPullsLeverandOpensDoors(){
		Map map = new Map(m, new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new DungeonLogic(map,heropos,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		game.moveHero('s');
		game.moveHero('s');
		assertTrue(map.areDoorsOpen());
	}
	
	@Test
	public void testMoveHeroOpensDoorsandEscapes(){
		Map map = new Map(m, new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new DungeonLogic(map,heropos,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		game.moveHero('s');
		game.moveHero('s');
		System.out.println(logic.getVictory());
		game.moveHero('a');
		assertTrue(game.victory());
		assertEquals(1,logic.nextLogic(map, 1).getEnemies().size());
		logic.setGameOver();
	}
	
	@Test
	public void testMapOpeningDoors(){
		Map map = new DungeonMap();
		map.openDoor();
		assertTrue(map.areDoorsOpen());
	}
	
	@Test(timeout=1000)
	public void testDrunkenGuard(){
		Map map = new DungeonMap();
		int[] numEnemy = {2};
		int[] heropos = {1,1};
		Logic logic = new DungeonLogic(map,heropos,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		
		boolean enemySymbol = false;
		while(!enemySymbol){
			logic.gameplay('a', map);
			if(logic.getEnemySymbol() == 'g')
				enemySymbol = true;
		}
	}
	
	@Test(timeout=1000)
	public void testSuspiciousGuard(){
		Map map = new DungeonMap();
		int[] numEnemy = {3};
		int[] heropos = {1,1};
		Logic logic = new DungeonLogic(map,heropos,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		
		assertEquals('U',logic.getEnemies().get(0).getSymbol());
		boolean enemyThere = false;
		while(!enemyThere){
			logic.gameplay('a', map);
			if(logic.getEnemies().get(0).getX() == 3 && logic.getEnemies().get(0).getY() == 8)
				enemyThere = true;
		}
	}
	
	public void testNextMap(){
		Map map = new DungeonMap();
		assertEquals(1,map.nextMap().getKey()[0]);
	}
}