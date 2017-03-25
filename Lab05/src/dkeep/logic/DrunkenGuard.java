package dkeep.logic;
 
import java.util.concurrent.ThreadLocalRandom;

/**
 * DrunkenGuard is a class that extend the class Guard. It contains specific information about this type of guard, such as the boolean
 * that says if the guard is asleep and the corresponding char that expresses that status. This guard randomly chooses to follows the normal
 * path, the reverse path or to be asleep. This last one consist of staying in the same position, changing it's symbol and being unaware
 * of the hero movements, even in the adjacent tiles.
 * @see Guard
 */
public class DrunkenGuard extends Guard {
	
	private boolean asleep;
	private char asleepChar;
	
	/**
	 * Constructor of DrunkenGuard. Sets all flags to false, puts the i variable to -1 and initializes other variables with the given values.
	 * @param symbol char that represents the DrunkenGuard
	 * @param x coordinate
	 * @param y coordinate
	 * @param path array of movements of the guard
	 */
	public DrunkenGuard(char symbol, int x, int y,char[] path){
		super(symbol,x,y,path,-1);
		asleep = false;
		asleepChar = 'g';
	}

	/**
	 * {@inheritDoc}
	 * in this particular class, this function randomly chooses between 3 behaviours, the normal one (follows the path and calls the
	 * normalMovement() function), the reverse one (follows the path in reverse order, calls the reverseMovement() function) and the
	 * asleep one, that puts the enemy in a temporary status of 2 turns, making it oblivious to the hero movements.
	 */
	@Override
	public int[] movement() {
		char[] behaviour = {'n','s','r'}; //n-normal, s-sleep, r - awake and reverse;
		int choice = ThreadLocalRandom.current().nextInt(0, 2 + 1);
		char beh = behaviour[choice];
		if(beh == 'n'){
			asleep = false;
			return normalMovement();
		}
		else if (beh == 's'){
			asleep = true;
			int[] mov = {x,y};
			return mov;
		}
		else {
			asleep = false;
			return reverseMovement();
		}
	}
	/**
	 * {@inheritDoc}
	 * If the guard is asleep, the symbol is different.
	 */
	@Override
	public char getSymbol() {
		if(asleep){
			return asleepChar;
		}
		else
			return symbol;
	}

}
