package dkeep.logic; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Character is a class that keeps the information about the moving elements of the game. It keeps information about it's coordinates,
 * symbol, if it's above a key or stunned, it's turns (movement depends on it) and it's weapon.
 */
public abstract class Character implements Serializable{
	
	protected int x;
	protected int y;
	protected char symbol;
	protected boolean isOverKey = false; 
	protected boolean stunned = false;
	protected int turns;
	protected Weapon weapon;
	
	/**
	 * Constructor of Character. Receives it's coordinates, symbol and weapon.
	 * @param x coordinate
	 * @param y coordinate
	 * @param symbol char that represents the character
	 * @param weapon Object weapon that contains information about the weapon
	 */
	public Character(int x, int y, char symbol,Weapon weapon){
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.turns = 0;
		this.weapon = weapon;
	}
	/**
	 * Retrieve the valor of the coordinate x.
	 * @return the coordinate x, type int
	 */
	public int getX() {
		return x;
	}
	/**
	 * Retrieve the valor of the coordinate y.
	 * @return the coordinate y, type int
	 */
	public int getY() {
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
	 * Puts the flag stunned true and updates the turns to 2. Only happens for enemies if they got "hit" with the hero weapon.
	 */
	public void gotStunned(){
		stunned = true;
		turns = 2;
	}
	/**
	 * Puts the flag stunned back to false. Happens when the number of turns has reached 0.
	 */
	public void backToNormal(){
		stunned = false;
	}
	
	/**
	 * Returns the symbol of the Character, depending on it's state and qualities.
	 * @return char representing it's display on the game
	 */
	public abstract char getSymbol();
	
	/**
	 * Retrieve the weapon of the character. If null, it will return null
	 * @return weapon of the character, Object Weapon
	 */
	public Weapon getWeapon(){
		return weapon;
	}
	
	/**
	 * Retrieve the weapon char depending on it's state. If weapon is null, it will return a char with empty space.
	 * @return char representing the weapon symbol
	 */
	public char getWeaponSymbol(){
		if(weapon == null)
			return ' ';
		else
			return weapon.getSymbol();
	}
	
	/**
	 * Retrieve a array on 2 ints with the possible new coordinates x and y of the character. 
	 * @return array of 2 ints with the coordinates x and y
	 */
	public abstract int[] movement();
}

