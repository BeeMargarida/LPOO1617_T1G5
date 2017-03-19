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

public class TestDungeonGameLogic  {
//	char[][] m = {{'X','X','X','X','X','X','X','X','X','X'},
//			{'X',' ',' ',' ','I',' ','X',' ',' ','X'},
//			{'X','X','X',' ','X','X','X',' ',' ','X'},
//			{'X',' ','I',' ','I',' ','X',' ',' ','X'},
//			{'X','X','X',' ','X','X','X',' ',' ','X'},
//			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
//			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
//			{'X','X','X',' ','X','X','X','X',' ','X'},
//			{'X',' ','I',' ','I',' ','X','k',' ','X'},
//			{'X','X','X','X','X','X','X','X','X','X'}};
	
	char[][] m = {{'X','X','X','X','X'},
					{'X',' ',' ','G','X'},
					{'I',' ',' ',' ','X'},
					{'I','k',' ',' ','X'},
					{'X','X','X','X','X'}};
	
	@Test
	public void testMoveHeroIntoFreeCell() {
		Map map = new Map(m,new int[] {3,1});
		//Hero hero = new Hero('H',1,1,null);
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
		/*
		System.out.println(logic.getHeroPosition().getX());
		System.out.println(logic.getHeroPosition().getY());
		System.out.println(game.isGameOver());
		*/
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
	}
	
	@Test
	public void testMapOpeningDoors(){
		Map map = new DungeonMap();
		map.openDoor();
		assertTrue(map.areDoorsOpen());
	}
}