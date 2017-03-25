package dkeep.logic; 

/**
 * Hero is a class that represents the hero of the game, that is, the character controlled by the user.
 * @see Character
 */
public class Hero extends Character {

	private char dir;
	private boolean hasKey;

	/**
	 * Constructor of Hero. Puts all flags to false and initialize all elements with the information given. The weapon can be null.
	 * @param s char that represents the hero
	 * @param x coordinate
	 * @param y coordinate
	 * @param weapon object Weapon that contains information about a Club
	 */
	public Hero(char s, int x, int y, Weapon weapon) {
		super(x,y,s,weapon);
		hasKey = false;
	}

	/**
	 * Sets the direction of the hero to a particular direction (a - left, d - right, w - up, s - down).
	 * @param dir movement direction taken by the hero
	 */
	public void setDir(char dir) {
		this.dir = dir;
	}
	/**
	 * {@inheritDoc}
	 */
	public int[] movement(){
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
		else if(dir == 's'){
			int[] mov = {x+1,y};
			return mov;
		}
		else {
			int[] mov = {x,y};
			return mov;
		}
	}

	/**
	 * Returns boolean corresponding to the hero's possession of the key.
	 * @return true if the hero has the key, false if not
	 */
	public boolean hasKey(){
		return hasKey;
	}

	/**
	 * Sets true the hasKey flag, meaning that the hero has caught the key.
	 */
	public void setKeyTrue(){
		hasKey = true;
	}
	/**
	 * {@inheritDoc}
	 * In this specific class, if the hero has caught the key, the symbol will be different.
	 */
	@Override
	public char getSymbol() {
		if(hasKey)
			return 'K';
		else
			return symbol;
	}

}
