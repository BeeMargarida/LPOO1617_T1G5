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
	
	public char[][] getGameMap() {
		return map.getMap();
	}
	
	public void print(){
		char[][] m = map.getMap();
		m[logic.hero.getX()][logic.hero.getY()] = logic.hero.symbol;
		for(int i = 0; i < logic.enemy.length; i++){
			m[logic.enemy[i].getX()][logic.enemy[i].getY()] = logic.enemy[i].symbol;
		}
		/*if(logic.openDoors){ //is it really best this way?
			for(int i = 0; i < m.length; i++){
				for(int j = 0; j < m[i].length; j++){
					if(m[i][j] == 'I')
						m[i][j] = 'S';
				}
			}
		}*/
		for(int i = 0; i < m.length; i++){
			System.out.println(m[i]);
		}
		m[logic.hero.getX()][logic.hero.getY()] = ' ';
	}
	
	public void update(char dir) {
		logic.movement(dir, map);
	}
	
}
