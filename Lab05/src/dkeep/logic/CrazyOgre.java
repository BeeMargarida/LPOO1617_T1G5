package dkeep.logic; 

import java.util.concurrent.ThreadLocalRandom;

/**
 * CrazyOgre is a class that keeps information about the enemy CrazyOgre of the game. This enemy has a symbol, coordinates x and y, two
 * booleans representing if he is in the same position of the key and if it is stunned (was it by the hero weapon).It also keeps 
 * information about the turns it still has to wait while stunned and it's weapon.
 * 
 * @see Character
 */
public class CrazyOgre extends Character {

	/**
	 * Constructor of CrazyOgre. Receives it's coordinates, it's symbol and the weapon, that could possibly be null or a Club.
	 * @param symbol char that will represent the ogre
	 * @param x coordinate 
	 * @param y coordinate
	 * @param weapon object Weapon that contains information about a Club
	 */
	public CrazyOgre(char symbol, int x, int y, Weapon weapon) {
		this.symbol = symbol;
		this.x = x;
		this.y = y;
		this.isOverKey = false;
		this.weapon = weapon;
		this.stunned = false;
		this.turns = 0;
	}

	/** 
	 * {@inheritDoc}
	 * It randomly chooses
	 * between one of 4 directions and calculates the respective coordinates based on the actual ones, then returns the calculated coordinates.
	 */
	@Override
	public int[] movement() {
		char[] dirpos = {'w','a','s','d'};
		int choice = ThreadLocalRandom.current().nextInt(0, 3 + 1);
		char dir = dirpos[choice];
		if(dir == 'w'){ 
			int[] mov = {x-1,y};
			return mov;
		}
		else if(dir == 'a') {
			int[] mov = {x,y-1};
			return mov;
		}
		else if(dir == 'd'){
			int[] mov = {x,y+1};
			return mov;
		}
		else{
			int[] mov = {x+1,y};
			return mov;
		}
	}

	/** 
	 * {@inheritDoc}
	 * If the Ogre is in the same position has the key or if it is stunned, it symbol is different that the default one.
	 */
	@Override
	public char getSymbol() {
		if(isOverKey)
			return '$';
		else if(stunned)
			return '8';
		else
			return symbol;
	}
}
