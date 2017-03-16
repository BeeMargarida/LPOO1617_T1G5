package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.Logic;
import dkeep.logic.Map;
import dkeep.logic.DungeonMap;
import dkeep.logic.Game;
import dkeep.logic.CellPosition;
import dkeep.logic.KeepLogic;
import dkeep.logic.KeepMap;
import dkeep.logic.Hero;

public class TestOgreBehaviour {
	char[][] m = {{'X','X','X','X','X'},
			{'X',' ',' ','O','X'},
			{'I',' ',' ',' ','X'},
			{'I','k',' ',' ','X'},
			{'X','X','X','X','X'}};

	@Test(timeout=1000)
	public void testOgreBehaviourChangeSymbol() {    //verifica se a arma e o ogre mudam o seu simbolo quando estao em cima da chave
		Map map = new Map(m,new int[] {3,1});
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,false);
		Game game = new Game(map, logic);

		boolean clubsymbol = false, ogresymbol = false;

		while(!clubsymbol || !ogresymbol){
			game.update('a');
			//System.out.println(logic.getEnemySymbol());
			if(logic.getEnemySymbol() == '$')
				ogresymbol = true;
			//System.out.println(logic.getEnemyWeaponSymbol());
			if(logic.getEnemyWeaponSymbol() == '$')
				clubsymbol = true;
		}
	}

	@Test(timeout=1000)
	public void testHeroWeapon() {
		Map map = new Map(m,new int[] {3,1});
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,true);
		Game game = new Game(map, logic);

		boolean clubsymbol = false;
		game.moveHero('s');
		while(!clubsymbol){
			game.update('a');
			//System.out.println(logic.getEnemyWeaponSymbol());
			if(logic.getHeroWeaponSymbol() == '$')
				clubsymbol = true;
		}	
	}

	@Test(timeout=1000)
	public void testHeroWeaponDamage() {
		Map map = new Map(m,new int[] {3,1});
		int[] heropos = {1,1};
		Logic logic = new KeepLogic(map,heropos,true);
		Game game = new Game(map, logic);

		boolean enemysymbol = false;
		game.moveHero('d');
		
		while(!enemysymbol){
			game.update('d');
			//System.out.println(logic.getEnemyWeaponSymbol());
			if(logic.getEnemySymbol() == '8')
				enemysymbol = true;
		}
		game.print();
	}
}
