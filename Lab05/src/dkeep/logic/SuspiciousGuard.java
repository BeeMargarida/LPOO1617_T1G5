package dkeep.logic;

import java.util.concurrent.ThreadLocalRandom; 

/**
 *	SuspiciousGuard is a class that contains information about a specific guard, which behaviour alternates between following the path
 *	in the normal order or in the reverse order.
 *	@see Guard
 */
public class SuspiciousGuard extends Guard {
	
	/**
	 * Constructor of the SuspiciousGuard. Puts the i variable to -1 and initializes other variables with the given values, calling the Guard constructor.
	 * @param symbol char that represents the SuspiciousGuard
	 * @param x coordinate
	 * @param y coordinate
	 * @param path array of movements of the guard
	 */
	public SuspiciousGuard(char symbol, int[] coord, char[] path) {
		super(symbol,coord,path);
	}
	/**
	 * {@inheritDoc}
	 * in this particular class, this function randomly chooses between 2 behaviours, the normal one (follows the path and calls the
	 * normalMovement() function) and the reverse one (follows the path in reverse order, calls the reverseMovement() function).
	 */
	@Override
	public int[] movement() {
		char[] behaviour = {'n','s'}; //n - normal, s - reverse
		int choice = ThreadLocalRandom.current().nextInt(0, 1 + 1);
		char beh = behaviour[choice];
		if(beh == 'n'){
			return normalMovement();
		}
		else {
			return reverseMovement();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public char getSymbol() {
		return symbol;
	}

}
