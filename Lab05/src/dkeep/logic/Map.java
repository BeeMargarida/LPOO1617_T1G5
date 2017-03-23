package dkeep.logic;
import java.util.*;
 
/**
 * Map is a class that keeps information about the map used in the game. It has a matrix of chars, used to store the map, and the 
 * position of it's key.
 */
public class Map {

	protected char[][] map;
	protected int[] key;
	
	/**
	 * Constructor of Map. It initializes its attributes with the values given.
	 * @param map matrix of chars with the map setup
	 * @param key array of ints with the coordinates of the key in the map
	 */
	public Map(char[][] map, int[] key){
		this.map = map;
		this.key = key;
	}

	/**
	 * Return a array of arrays that represent the map.
	 * @return array of array (matrix) of chars that represent the map
	 */
	public char[][] getMap(){
		return map;
	}
	
	/**
	 * Sets the map attribute with the variable given.
	 * @param map variable that represents the map to be updated
	 */
	public void setMap(char[][] map){
		this.map = map;
	}
	 
	/**
	 * Returns the map width.
	 * @return map width, which can also be called the maximum y coordinate - 1
	 */
	public int getWidth(){
		if(map != null)
			return map[0].length;
		return 0;
	}
	/**
	 * Returns the map height.
	 * @return map height, which can also be called the maximum x coordinate - 1
	 */
	public int getHeight(){
		if(map != null)
			return map.length;
		return 0;
	}
	
	/**
	 * Returns the coordinates of the key.
	 * @return array of ints with the coordinates of the key in the map
	 */
	public int[] getKey(){
		return key;
	}
	
	
	/**
	 * Goes through the map and coordinates of the hero, returning them.
	 * @return array of ints with the actual coordinates of the hero
	 */
	public int[] getHeroPos(){
		int[] pos = new int[2];
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				if(map[i][j] == 'H'){
					pos[0] = i;
					pos[1] = j;
					return pos;
				}
			}
		}
		return pos;
	}
	
	/**
	 * Goes through the map and coordinates of the enemy, returning them. Only works for RookieGuards.
	 * @return array of ints with the actual coordinates of the guard
	 */
	public int[] getGuardPos(){
		int[] pos = new int[2];
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				if(map[i][j] == 'G'){
					pos[0] = i;
					pos[1] = j;
					return pos;
				}
			}
		}
		return pos;
	}
	/**
	 * Goes through the map and coordinates of the enemy, returning them. Only works for CrazyOgres.
	 * @return array of ints with the actual coordinates of the ogre
	 */
	public int[] getOgrePos(){
		int[] pos = new int[2];
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				if(map[i][j] == 'O'){
					pos[0] = i;
					pos[1] = j;
					return pos;
				}
			}
		}
		return pos;
	}
	
	/**
	 * Goes through the map, finding the chars representing the closed doors and switching them by chars representing the open doors.
	 */
	public void openDoor() {
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == 'I')
                    map[i][j] = 'S';
            }
		}
	}
	
	/**
	 * Goes through the map and return true if it finds a open door.
	 * @return true if a door is open, false if it is not
	 */
	public boolean areDoorsOpen(){
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == 'S')
                    return true;
            }
		}
		return false;
	}

	/**
	 * Checks if the cell with the coordinates given is free, meaning that in that position can't be walls('X'), closed and doors ('I','S').
	 * @param x coordinate
	 * @param y coordinate
	 * @return true if position is free, false if it's not
	 */
	public boolean isFree(int x, int y) { 
		if(map[x][y] == 'X'){
			return false;
		} 
		else if(map[x][y] == 'I'){
			return false;
		}
		else if(map[x][y] == 'S'){
			return false;
		}
		return true;
	}

	/**
	 * Checks if the given coordinates match the position of the key.
	 * @param x coordinate
	 * @param y coordinate
	 * @return true if the key is in given coordinates, false if it's not
	 */
	public boolean isKey(int x, int y){
		if(x == key[0] && y == key[1])
			return true;
		return false;
	}
	
	/**
	 * Checks if the given coordinates correspond to a open door.
	 * @param x coordinate
	 * @param y coordinate
	 * @return true if in the given coordinates is a open door, false if it's not
	 */
	public boolean isS(int x, int y){
		if(map[x][y] == 'S')
			return true;
		return false;
	}
	
	/**
	 * Checks if the given coordinates correspond to a closed door.
	 * @param x coordinate
	 * @param y coordinate
	 * @return true if in the given coordinates is a closed door, false if it's not
	 */
	public boolean isI(int x, int y){
		if(map[x][y] == 'I')
			return true;
		return false;
	}

	/**
	 * Returns a new map if there is a next level, return null if there isn't a next level.
	 * @return Map or null
	 */
	public Map nextMap() {
		return null;
	}

}
