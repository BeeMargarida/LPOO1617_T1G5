package dkeep.logic;
 
import java.util.concurrent.ThreadLocalRandom;

/**
 * Club is a class that extends class Weapon, meaning that is a type of weapon that can be "used" by a Character. This particular weapon
 * as a specific symbol.
 * @see Weapon
 */
public class Club extends Weapon {

	/**
	 * Constructor of Club. Sets all flags to false and initializes all data with the given values.
	 * @param symbol char that represents the Club in it's default status
	 * @param secsymbol char that represents the Club in certain situations
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Club(char symbol, char secsymbol, int x, int y) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.secsymbol = secsymbol;
		this.above = false;
		this.valid = false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int[] swing(int x, int y) { //x and y are the enemy's or hero's coordenates
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
		else {
			int[] mov = {x+1,y};
			return mov;
		}
	}

}
