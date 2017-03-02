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
		m[logic.hero.getX()][logic.hero.getY()] = logic.hero.symbol;
		for(int i = 0; i < logic.enemies.length; i++){
			if(logic.enemies[i].isOverKey){
				m[logic.enemies[i].getX()][logic.enemies[i].getY()] = '$';
			}
			else{
				m[logic.enemies[i].getX()][logic.enemies[i].getY()] = logic.enemies[i].symbol;
			}
		}
		for(int i = 0; i < m.length; i++){
			System.out.println(m[i]);
		}
		m[logic.hero.getX()][logic.hero.getY()] = ' ';
		for(int i = 0; i < logic.enemies.length; i++){
			m[logic.enemies[i].getX()][logic.enemies[i].getY()] = ' ';
		}
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
