package dkeep.logic;
import java.io.Serializable;
import java.util.ArrayList; 

/**
 * Logic is a class that deals with all the logic of the current level of the game. It contains information about the current game elements,
 * such as the hero, the enemies and a flag checks if the game is over and another that checks if it is a victory. 
 * It contains methods that deal with the movement of all the characters in the game and the collisions between them.
 */
public class Logic implements Serializable{

	protected Hero hero;
	protected ArrayList<Character> enemies;
	protected boolean isOver;
	protected boolean victory;
	
	/**
	 * Constructor of Logic. It initializes the hero with the variable given and puts all the flags to false.
	 * @param hero Object Hero 
	 */
	public Logic(Hero hero) {
		this.hero = hero;
		isOver = false;
		victory = false;
	}
	
	/**
	 * Adds an enemy to the arraylist of enemies
	 * @param enemy Object Character that represents an enemy
	 */
	public void addEnemy(Character enemy) {
		enemies.add(enemy);
	}
	
	/**
	 * Return all the enemies that exist in the current level.
	 * @return ArrayList of Characters that correspond to the enemies of the level
	 */
	public ArrayList<Character> getEnemies(){
		return enemies;
	}
	
	/**
	 * Returns the CellPosition with the current coordinates of the hero.
	 * @return Object CellPosition that correspond to the position of the hero
	 */
	public CellPosition getHeroPosition(){
		CellPosition c = new CellPosition(hero.getX(),hero.getY());
		return c;
	}
	
	/**
	 * Returns the hero.
	 * @return Object Hero
	 */
	public Hero getHero(){
		return hero;
	}
	
	/**
	 * Returns the symbol with which the hero is represented in the map.
	 * @return char that represents the hero
	 */
	public char getHeroSymbol(){
		return hero.getSymbol();
	}
	
	/**
	 * Returns the symbol with which the hero's weapon is represented in the map.
	 * @return char that represents the hero's weapon
	 */
	public char getHeroWeaponSymbol(){
		return hero.getWeaponSymbol();
	}
	/**
	 * Returns the symbol of the first enemy in the array of enemies.
	 * @return char that represents the enemy
	 */
	public char getEnemySymbol(){
		return enemies.get(0).getSymbol();
	}
	/**
	 * Returns the symbol of the first enemy's weapon in the array of enemies.
	 * @return char that represents the enemy's weapon
	 */
	public char getEnemyWeaponSymbol(){
		return enemies.get(0).getWeaponSymbol();
	}
	
	/**
	 * Checks if one of the elements is in the cell to the left of the other element. If it is, it is a collision.
	 * @param pos1 array of ints with the position of an element of the game
	 * @param pos2 array of ints with the position of an element of the game
	 * @return true if there is a collision, false if not
	 */
	public boolean collideLeft(int[] pos1, int[] pos2){
		if(((pos1[0] == pos2[0]) && (pos1[1] == pos2[1] - 1)) || ((pos1[0] == pos2[0]) && (pos1[1] == pos2[1])))
			return true;
		return false;
	}
	/**
	 * Checks if one of the elements is in the cell to the right of the other element. If it is, it is a collision.
	 * @param pos1 array of ints with the position of an element of the game
	 * @param pos2 array of ints with the position of an element of the game
	 * @return true if there is a collision, false if not
	 */
	public boolean collideRight(int[] pos1, int[] pos2){
		if((pos1[0] == pos2[0]) && (pos1[1] == pos2[1] + 1))
			return true;
		return false;
	}
	/**
	 * Checks if one of the elements is in the cell above the other element. If it is, it is a collision.
	 * @param pos1 array of ints with the position of an element of the game
	 * @param pos2 array of ints with the position of an element of the game
	 * @return true if there is a collision, false if not
	 */
	public boolean collideUp(int[] pos1, int[] pos2){
		if((pos1[0] == pos2[0]-1) && (pos1[1] == pos2[1]))
			return true;
		return false;
	}
	/**
	 * Checks if one of the elements is in the cell under the other element. If it is, it is a collision.
	 * @param pos1 array of ints with the position of an element of the game
	 * @param pos2 array of ints with the position of an element of the game
	 * @return true if there is a collision, false if not
	 */
	public boolean collideDown(int[] pos1, int[] pos2){
		if((pos1[0] == pos2[0]+1) && (pos1[1] == pos2[1]))
			return true;
		return false;
	}

	/**
	 * Checks if the hero is in any position adjacent (up, down, left, right) to any of the enemies. If it is, it will return true,
	 * else it will return false.
	 * @param x coordinate of the hero
	 * @param y coordinate of the hero
	 * @param vector contains all the enemies of the levels
	 * @return true if the hero collides with any of the enemies, false if it doesn't
	 */
	public boolean collideEnemy(int[] pos, ArrayList<Character> vector) {
		for(int i = 0; i < vector.size(); i++){
			/*if((vector.get(i).stunned == false) && ((x == vector.get(i).getX() && y == vector.get(i).getY()) || (x+1 == vector.get(i).getX() && y == vector.get(i).getY()) || (x-1 == vector.get(i).getX() && y == vector.get(i).getY()) || (y+1 == vector.get(i).getY() && x == vector.get(i).getX()) || (y-1 == vector.get(i).getY() && x == vector.get(i).getX()))){
				return true;
			}*/
			if((!vector.get(i).stunned) && (collideLeft(pos,new int[] {vector.get(i).getX(),vector.get(i).getY()}) || collideRight(pos,new int[] {vector.get(i).getX(),vector.get(i).getY()}) || collideUp(pos,new int[] {vector.get(i).getX(),vector.get(i).getY()}) || collideDown(pos,new int[] {vector.get(i).getX(),vector.get(i).getY()})))
				return true;
		}
		return false;
	}
	/**
	 * Checks if the Character (can be both hero or enemy) is in any adjacent position to a weapon (can be from hero or enemy). If it is,
	 * it will return true, else false.
	 * @param x coordinate of the Character
	 * @param y coordinate of the Character
	 * @param vector contains the weapons to be tested
	 * @return true if any of the weapons collide with the Character, false id it doesn't
	 */
	public boolean collideWeapon(int[] pos, ArrayList<Weapon> vector) {
		for(int i = 0; i < vector.size(); i++){
			if(collideLeft(pos,new int[] {vector.get(i).getX(),vector.get(i).getY()}) || collideRight(pos,new int[] {vector.get(i).getX(),vector.get(i).getY()}) || collideUp(pos,new int[] {vector.get(i).getX(),vector.get(i).getY()}) || collideDown(pos,new int[] {vector.get(i).getX(),vector.get(i).getY()}))
				return true;
		}
		return false;
	}

	/**
	 * Puts the flag of the game over to true.
	 */
	public void setGameOver(){
		isOver = true;
	}

	/**
	 * Goes through the enemies arraylist and groups their weapons into an arraylist, returning it.
	 * @return ArrayList with all the weapons of the enemies
	 */
	public ArrayList<Weapon> getWeapons(){
		ArrayList<Weapon> res = new ArrayList<Weapon>();
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).weapon != null)
				res.add(enemies.get(i).weapon);
		}
		return res;
	}
	
	
	/**
	 * Moves the hero accordingly to the dir variable, checking if it collides with the enemy (which makes the flag isOver to turn to true).
	 * It also checks if the direction chosen is a valid one, making sure it doesn't overlap any of the static elements of the game.
	 * Furthermore, it also checks if the hero has caught the key or pulled the lever, using this information to change it's behaviour
	 * once in front of doors.
	 * @param dir char that correspond to the direction of movement chosen by the player
	 * @param map Object Map that corresponds to the current map of the game
	 * @return true if the hero has made with no collisions, false if it was caught
	 */
	public boolean moveHero(char dir, Map map){return true;}

	/**
	 * Handles all the movement logic of the game. It calls the functions that deal with the hero's and hero's weapon movement, and the 
	 * enemies's and enemies's weapons movement.
	 * @param dir char that correspond to the direction of the hero movement chosen by the player
	 * @param map Object Map that corresponds to the current map of the game
	 */
	public void gameplay(char dir, Map map){}

	/**
	 * Returns the logic of the next level.
	 * @param map Map of the next level
	 * @param option number or behaviour of the enemies of the next level
	 * @return Logic of the next level
	 */
	public Logic nextLogic(Map map, int option) {
		return null;
	}

	/**
	 * Returns true if the game is over, false if it isn't.
	 * @return true if the isOver is true, false if it isn't
	 */
	public boolean gameOver() {
		return isOver;
	}

	/**
	 * Returns true if the player has won, false if it hasn't.
	 * @return true if victory is true, false if it isn't
	 */
	public boolean getVictory(){
		return victory;
	}
}
