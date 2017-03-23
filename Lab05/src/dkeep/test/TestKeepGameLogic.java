package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.Logic;
import dkeep.logic.Map;
import dkeep.logic.Weapon;
import dkeep.logic.DungeonMap;
import dkeep.logic.Game;
import dkeep.logic.CellPosition;
import dkeep.logic.Club;
import dkeep.logic.CrazyOgre;
import dkeep.logic.DungeonLogic;
import dkeep.logic.KeepLogic;
import dkeep.logic.KeepMap;
import dkeep.logic.Hero;

public class TestKeepGameLogic {
	char[][] m = {{'X','X','X','X','X'},
				  {'X',' ',' ','O','X'},
				  {'I',' ',' ',' ','X'},
			 	  {'I','k',' ',' ','X'},
				  {'X','X','X','X','X'}};
	
	@Test
	public void testHeroIsCapturedByOgre() {
		Map map = new Map(m,new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		assertEquals(new CellPosition(1,1),logic.getHeroPosition());
		game.moveHero('d');
		assertEquals(new CellPosition(1,2),logic.getHeroPosition());
		assertTrue(game.isGameOver());
	}
	
	@Test
	public void testVictoryKeep(){
		Map map = new Map(m,new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		game.moveHero('s');
		game.moveHero('s');
		game.moveHero('a');
		game.update('a');
		assertTrue(game.victory());
	}
	
	@Test
	public void testOgreChangesSymbolWhenOnKey() {
		Map map = new Map(m,new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		game.moveHero('s');
		game.moveHero('s');
		assertEquals('K', logic.getHeroSymbol());
	}
	
	@Test
	public void testMoveHeroIntoClosedDoors() {
		Map map = new Map(m,new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		game.moveHero('s');
		game.moveHero('a');
		assertEquals(new CellPosition(2,1),logic.getHeroPosition());
		assertFalse(game.victory());
	}
	
	@Test
	public void testMoveHeroOpensDoorsWithKey(){
		Map map = new Map(m,new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		game.moveHero('s');
		game.moveHero('s');
		game.moveHero('a');
		assertTrue(map.areDoorsOpen());
	}
	
	@Test
	public void testMoveHeroOpensDoorsandEscapes(){
		Map map = new Map(m,new int[] {3,1});
		int[] numEnemy = {1};
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		game.moveHero('s');
		game.moveHero('s');
		game.moveHero('a');
		game.moveHero('a');
		assertTrue(game.victory());
		char[][] board = game.getBoard();
		assertEquals(5,board.length);
		game.setLevel(1);
		assertEquals(1,game.getLevel());
	}
	
	@Test
	public void testMapOpeningDoors(){
		Map map = new KeepMap();
		map.openDoor();
		assertTrue(map.areDoorsOpen());
	}
	
	@Test
	public void testNumberOgres(){
		Map map = new Map(m,new int[] {3,1});
		int[] numEnemy = {6};
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false,numEnemy[0]);
		Game game = new Game(map, logic, numEnemy);
		assertEquals(6,logic.getEnemies().size());
		assertEquals(6,logic.getWeapons().size());
	}
	
	@Test
	public void testAddEnemiesandNextLogic(){
		Map map = new KeepMap();
		int[] heropos = map.getHeroPos();
		Logic logic = new KeepLogic(map,heropos,false,0);
		int[] ogrePos = map.getOgrePos();
		Weapon weaponE = new Club('*','$',ogrePos[0],ogrePos[1]); 
		logic.addEnemy(new CrazyOgre('O',ogrePos[0], ogrePos[1], weaponE));
		assertEquals(1,logic.getEnemies().size());
		assertNull(logic.nextLogic(map,0));
	}
}
