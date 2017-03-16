package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.Logic;
import dkeep.logic.Map;
import dkeep.logic.DungeonMap;
import dkeep.logic.Game;
import dkeep.logic.CellPosition;
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
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false);
		Game game = new Game(map, logic);
		assertEquals(new CellPosition(1,1),logic.getHeroPosition());
		game.moveHero('d');
		assertEquals(new CellPosition(1,2),logic.getHeroPosition());
		assertTrue(game.isGameOver());
	}
	
	@Test
	public void testOgreChangesSymbolWhenOnKey() {
		Map map = new Map(m,new int[] {3,1});
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false);
		Game game = new Game(map, logic);
		game.moveHero('s');
		game.moveHero('s');
		assertEquals('K', logic.getHeroSymbol());
	}
	
	@Test
	public void testMoveHeroIntoClosedDoors() {
		Map map = new Map(m,new int[] {3,1});
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false);
		Game game = new Game(map, logic);
		game.moveHero('s');
		game.moveHero('a');
		assertEquals(new CellPosition(2,1),logic.getHeroPosition());
		assertFalse(game.victory());
	}
	
	@Test
	public void testMoveHeroOpensDoorsWithKey(){
		Map map = new Map(m,new int[] {3,1});
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false);
		Game game = new Game(map, logic);
		game.moveHero('s');
		game.moveHero('s');
		game.moveHero('a');
		System.out.println(logic.getHeroPosition().getX());
		System.out.println(logic.getHeroPosition().getY());
		//assertEquals(new CellPosition(3,1),logic.getHeroPosition());
		assertTrue(map.areDoorsOpen());
	}
	
	@Test
	public void testMoveHeroOpensDoorsandEscapes(){
		Map map = new Map(m,new int[] {3,1});
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false);
		Game game = new Game(map, logic);
		game.moveHero('s');
		game.moveHero('s');
		game.moveHero('a');
		game.moveHero('a');
		assertTrue(game.victory());
	}
	
	@Test
	public void testMapOpeningDoors(){
		Map map = new KeepMap();
		map.openDoor();
		assertTrue(map.areDoorsOpen());
	}
}
