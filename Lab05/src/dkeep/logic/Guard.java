package dkeep.logic;

import java.io.Serializable;

/**
 * Guard is a class that keeps information about the type of enemy Guard. This information consists of the path corresponding to it's
 * movement on the map and the index that keeps track of that path. It also contains the methods for the normal movement (following the path)
 * and the reverse movement(following the path in reverse order).
 * @see Character
 */
public abstract class Guard extends Character implements Serializable{
	
	protected char[] path;
	protected int i;
	
	/**
	 * Constructor of Guard. It initializes the character variables and also the path and index.
	 * @param symbol char that represents the guard
	 * @param x coordinate
	 * @param y coordinate
	 * @param path array of chars that represent the path that the enemy will take
	 * @param i int that keeps the progress in the array of path
	 */
	public Guard(char symbol, int[] coord, char[] path){
		super(coord,symbol,null);
		this.path = path;
		this.i = -1;
	}
	/**
	 * Follows the path of the guard, using the index to know the next direction of the guard. Returns a array with the new coordinates of the
	 * guard and updates the index.
	 * @return array of 2 ints with the coordinates x and y
	 */
	
	public int[] normalMovement(){
		int[] mov;
		i++;
		if(i == path.length){
			i = 0;
		}
		if(path[i] == 'w'){ 
			mov = new int[] {x-1,y};
			
		}
		else if(path[i] == 'a') {
			mov = new int[] {x,y-1};
		}
		else if(path[i] == 'd'){
			mov = new int[] {x,y+1};
		}
		else{
			mov = new int[] {x+1,y};
		}
		return mov;
	}
	
	/**
	 * Follows the path of the guard but in reverse order. Instead of adding 1 to the index, it subtracts 1. Returns a array with the new 
	 * coordinates of the guard.
	 * 
	 * @return array of 2 ints with the coordinates x and y
	 */
	public int[] reverseMovement(){
		int[] mov;
		if(i < 0){
			i = path.length - 1; 
		}
		if(path[i] == 'w'){ 
			mov = new int[]{x+1,y};
		}
		else if(path[i] == 'a') {
			mov = new int[]{x,y+1};
		}
		else if(path[i] == 'd'){
			mov = new int[]{x,y-1};
		}
		else{
			mov = new int[]{x-1,y};
		}
		i--;
		return mov;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int[] movement(){ return null;};
	
	/**
	 * {@inheritDoc}
	 */
	public char getSymbol(){ return ' ';};
	
}
