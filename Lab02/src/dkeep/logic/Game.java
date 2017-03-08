package dkeep.logic;

import java.util.*;

public class Game {
	private Map map;
	private Logic logic;


	public Game(Map startingMap, Logic startingLogic) {
		map = startingMap;
		logic = startingLogic;
	}

	public boolean isGameOver() {
		return logic.gameOver();
	}

	public void print(){
		char[][] m = map.getMap();
		//hero
		m[logic.hero.getX()][logic.hero.getY()] = logic.hero.symbol;
		//enemies
		for(int i = 0; i < logic.enemies.length; i++){
			if(logic.enemies[i].isOverKey){
				m[logic.enemies[i].getX()][logic.enemies[i].getY()] = '$';
			}
			else{
				m[logic.enemies[i].getX()][logic.enemies[i].getY()] = logic.enemies[i].symbol;
				m[map.getKey()[0]][map.getKey()[1]] = 'k'; //prints key
			}
		} 
		//weapons
		if(logic.weapons != null){
			for(Weapon it : logic.weapons){
				if(it.above){
					m[it.getX()][it.getY()] = it.secsymbol;
				}
				else{
					m[it.getX()][it.getY()] = it.symbol;
					m[map.getKey()[0]][map.getKey()[1]] = 'k';//prints key
				}
			}
		}
		//PRINT
		for(int i = 0; i < m.length; i++){
			System.out.println(m[i]);
		}
		//ERASE
		m[logic.hero.getX()][logic.hero.getY()] = ' ';
		for(int i = 0; i < logic.enemies.length; i++){
			m[logic.enemies[i].getX()][logic.enemies[i].getY()] = ' ';
		}
		if(logic.weapons!= null){
			for(Weapon it : logic.weapons){
				m[it.getX()][it.getY()] = ' ';
			}
		}
		m[map.getKey()[0]][map.getKey()[1]] = ' ';//erases key
	}

	public void update(char dir) {
		logic.gameplay(dir, map);
		if(logic.getVictory()){
			if(logic.nextLogic() == null){
				logic.setGameOver();
				return;
			}
			map = map.nextMap();
			logic = logic.nextLogic();
		}
	}

}
