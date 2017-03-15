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

public class TestDungeonGameLogic  {
//	char[][] map = {{'X','X','X','X','X','X','X','X','X','X'},
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
		Logic logic = new DungeonLogic();
		Game game = new Game(map, logic);
		assertEquals(new CellPosition(1,1),logic.getHeroPosition());
		game.update('s');
	}
	
	@Test
	public void testHeroIsCapturedByGuard() {
		Map map = new DungeonMap();
		Logic logic = new DungeonLogic();
		Game game = new Game(map, logic);
		assertFalse(logic.gameOver());
		//acabar
	}

}