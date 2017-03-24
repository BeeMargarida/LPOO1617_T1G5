package dkeep.logic;

import java.io.Serializable;

/**
 * Weapon is a class that represents a weapon possessed by a character. It keeps information about it's symbols, coordinates, and flags
 * informing if it's position is above key and if it is in a vlid position to be printed. 
 */
public abstract class Weapon implements Serializable{
	
	protected int x;
	protected int y;
	protected char symbol;
	protected char secsymbol;
	protected boolean above;
	protected boolean valid;
	
	/**
	 * Using the coordinates of the weapon's character (x and y), randomly calculates the direction of the next position of the weapon,
	 * returning a array of ints that represent the new coordinates for the weapon to take.
	 * @param x coordinates of the weapon's Character
	 * @param y coordinates of the weapon's Character
	 * @return array of ints with coordinates x and y
	 */
	public abstract int[] swing(int x, int y); //x and y are the coordinates of the enemy or hero
	
	/**
	 * Retrieve the valor of the coordinate x.
	 * @return the coordinate x, type int
	 */
	public int getX(){
		return x;
	}
	/**
	 * Retrieve the valor of the coordinate y.
	 * @return the coordinate y, type int
	 */
	public int getY(){
		return y;
	}
	/**
	 * Set the coordinate x to the value given.
	 * @param x new coordinate to be updated
	 */
	public void setX(int x){
		this.x = x;
	}
	/**
	 * Set the coordinate y to the value given.
	 * @param y new coordinate to be updated
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Sets the valid flag to false, meaning that the weapon is in a position not valid to be printed.
	 */
	public void setNotValid(){
		valid = false;
	}
	/**
	 * Sets the valid flag to true, meaning that the weapon is in a position valid to be printed.
	 */
	public void setValid(){
		valid = true;
	}
	/**
	 * Returns the value of the flag valid.
	 * @return true if valid is true, false if valid is false
	 */
	public boolean getValid(){
		return valid;
	}
	/**
	 * Returns the char representing the weapon based on it's status.
	 * @return char representing the weapon
	 */
	public char getSymbol(){
		if(above)
			return secsymbol;
		else
			return symbol;
	}
}
