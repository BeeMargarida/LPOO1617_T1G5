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
		if(!logic.hero.hasKey()){
			m[map.getKey()[0]][map.getKey()[1]] = 'k';
		}
		//hero
		m[logic.hero.getX()][logic.hero.getY()] = logic.hero.getSymbol();
		//enemies
		for(int i = 0; i < logic.enemies.size(); i++){
			m[logic.enemies.get(i).getX()][logic.enemies.get(i).getY()] = logic.enemies.get(i).getSymbol();
		} 
		//weapons
		ArrayList<Weapon> weapons = logic.getWeapons();
		if(weapons.size() != 0){
			for(Weapon it : weapons){
				m[it.getX()][it.getY()] = it.getSymbol();
			}
		}
		if(logic.hero.weapon != null){
			m[logic.hero.weapon.getX()][logic.hero.weapon.getY()] = logic.hero.weapon.getSymbol();
		}
		//PRINT
		for(int i = 0; i < m.length; i++){
			System.out.println(m[i]);
		}
		//ERASE
		m[logic.hero.getX()][logic.hero.getY()] = ' ';
		for(int i = 0; i < logic.enemies.size(); i++){
			m[logic.enemies.get(i).getX()][logic.enemies.get(i).getY()] = ' ';
		}
		if(weapons.size() != 0){
			for(Weapon it : weapons){
				m[it.getX()][it.getY()] = ' ';
			}
		}
		if(logic.hero.weapon != null){
			m[logic.hero.weapon.getX()][logic.hero.weapon.getY()] = ' ';
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
