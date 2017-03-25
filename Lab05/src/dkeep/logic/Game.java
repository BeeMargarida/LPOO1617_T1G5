package dkeep.logic; 

import java.io.Serializable;
import java.util.*;

/**
 * Game is a class that deals with the whole game, connecting logics with the respective map. This class checks if the game is over and either proceeds to the next level 
 * (if there is one), or ends the game and transmits that information to the main function. It contains the map and logic of the game, the enemy's options of each level and
 * the current level of the game being played.
 */
public class Game implements Serializable {
	private Map map;
	private Logic logic;
	private int[] numEnemy;
	private int level;


	/**
	 * Constructor of Game. Initializes the map, logic and enemyoptions with the attributes given and sets the level to 0.
	 * @param startingMap Map that is playable in the beginning of the game
	 * @param startingLogic Logic that is playable in the beginning of the game
	 * @param numEnemy Options regarding the enemies of the existing levels
	 */
	public Game(Map startingMap, Logic startingLogic, int[] numEnemy) {
		map = startingMap;
		logic = startingLogic;
		this.numEnemy = numEnemy;
		level = 0;
	}

	/**
	 * Checks with the logic to see if the game in the level is over, return true if it is, false if not.
	 * @return true if the level is over, false if not
	 */
	public boolean isGameOver() {
		return logic.gameOver();
	}

	/**
	 * Checks with the logic to see if the game in the level ended in victory, return true if it has, false if not.
	 * @return true if the level ended in victory, false if not
	 */
	public boolean victory(){
		return logic.getVictory();
	}
	
	/**
	 * Prints in the map the key symbol in it's current position.
	 * @param m matrix of chars that represent the map of the game
	 */
	public void getBoardKey(char[][] m){
		if(!logic.getHero().hasKey()){
			m[map.getKey()[0]][map.getKey()[1]] = 'k';
		}
	}
	
	/**
	 * Erases from the map the key symbol.
	 * @param m matrix of chars that represent the map of the game
	 */
	public void eraseBoardKey(char[][] m){
		m[map.getKey()[0]][map.getKey()[1]] = ' ';
	}
	/**
	 * Prints in the map the hero symbol and its weapon in their current position.
	 * @param m matrix of chars that represent the map of the game
	 */
	public void getBoardHero(char[][] m){
		m[logic.getHero().getX()][logic.getHero().getY()] = logic.getHero().getSymbol();
		if(logic.getHero().getWeapon() != null){
			if(logic.getHero().getWeapon().getValid()){
				m[logic.getHero().getWeapon().getX()][logic.getHero().getWeapon().getY()] = logic.getHero().getWeapon().getSymbol();
			}
		}
	}
	/**
	 * Erases from the map the hero symbol and its weapon.
	 * @param m matrix of chars that represent the map of the game
	 */
	public void eraseBoardHero(char[][] m){
		m[logic.getHero().getX()][logic.getHero().getY()] = ' ';
		if(logic.getHero().getWeapon() != null){
			m[logic.getHero().getWeapon().getX()][logic.getHero().getWeapon().getY()] = ' ';
		}
	}
	
	/**
	 * Prints in the map the enemies symbol and their weapon, in their current position.
	 * @param m matrix of chars that represent the map of the game
	 */
	public void getBoardEnemies(char[][] m){
		for(int i = 0; i < logic.getEnemies().size(); i++){
			m[logic.getEnemies().get(i).getX()][logic.getEnemies().get(i).getY()] = logic.getEnemies().get(i).getSymbol();
		}
		ArrayList<Weapon> weapons = logic.getWeapons();
		if(weapons.size() != 0){
			for(Weapon it : weapons){
				if(it.getValid())
					m[it.getX()][it.getY()] = it.getSymbol();
			}
		}
	}
	/**
	 * Erases from the map the enemies symbol and their weapon.
	 * @param m matrix of chars that represent the map of the game
	 */
	public void eraseBoardEnemies(char[][] m){
		for(int i = 0; i < logic.getEnemies().size(); i++){
			m[logic.getEnemies().get(i).getX()][logic.getEnemies().get(i).getY()] = ' ';
		}
		ArrayList<Weapon> weapons = logic.getWeapons();
		if(weapons.size() != 0){
			for(Weapon it : weapons){
				m[it.getX()][it.getY()] = ' ';
			}
		}
	}
	/**
	 * Puts all the non-static elements of the game on the map, saves it into a matrix of chars and also prints it. In the end, erases all the non-static elements.
	 * @return matrix of chars that contain the current map of the game
	 */
	public char[][] getBoard(){
		char[][] m = map.getMap();
		getBoardKey(m);
		getBoardEnemies(m);
		getBoardHero(m);
		for(int i = 0; i < m.length; i++){
			System.out.println(m[i]);
		}
		char[][] tmp = new char[m.length][];
		for(int i = 0; i < tmp.length; i++){
			tmp[i] = m[i].clone();
		}
		eraseBoardHero(m);
		eraseBoardEnemies(m);
		eraseBoardKey(m);
		return tmp;
	}

	/**
	 * Returns the current level of the game.
	 * @return level attribute
	 */
	public int getLevel(){
		return level;
	}

	/**
	 * Sets the level attribute to a new value.
	 * @param level new value of level
	 */
	public void setLevel(int level){
		this.level = level;
	}

	/**
	 * Moves the hero in the direction chosen by the player.
	 * @param dir direction to which move the hero
	 */
	public void moveHero(char dir){
		logic.moveHero(dir, map);
	}

	/**
	 * Updates the state of the game and checks if it ended in victory. If that is case and there is a next level, it requests the logic and map of the next level.
	 * @param dir direction to which move the hero
	 */
	public void update(char dir) {
		logic.gameplay(dir, map);
		if(logic.getVictory()){
			if(level < numEnemy.length-1){
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
