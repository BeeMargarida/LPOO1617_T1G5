package dkeep.logic; 

import java.util.*;

public class Game {
	private Map map;
	private Logic logic;
	private int[] numEnemy;
	private int level;


	public Game(Map startingMap, Logic startingLogic, int[] numEnemy) {
		map = startingMap;
		logic = startingLogic;
		this.numEnemy = numEnemy;
		level = 0;
	}

	public boolean isGameOver() {
		return logic.gameOver();
	}

	public boolean victory(){
		return logic.getVictory();
	}

	public String print(){
		String text = new String("");
		
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
			text += String.valueOf(m[i]);
			text += "\n";
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
		return text;
	}

	
	public void moveHero(char dir){
		logic.moveHero(dir, map);
	}

	public void update(char dir) {
		logic.gameplay(dir, map);
		if(logic.getVictory()){
			if(level < numEnemy.length-1){
				System.out.println("new lvl");
				level++;
			}
			if(logic.nextLogic(map, numEnemy[level]) == null){
				logic.setGameOver();
				return;
			}
			map = map.nextMap();
			logic = logic.nextLogic(map, numEnemy[level]);
		}
	}

}
