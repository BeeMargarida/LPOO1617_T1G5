package dkeep.logic;

/**
 * RookieGuard is a class that contains information about the simplest of the guards. This guard merely follows the path given to him.
 * @see Guard
 */
public class RookieGuard extends Guard {

	/**
	 * Constructor of the RookieGuard.  Sets all flags to false, puts the i variable to -1 and initializes other variables with the given values.
	 * @param symbol char that represents the RookieGuard
	 * @param x coordinate
	 * @param y coordinate
	 * @param path array of movements of the guard
	 */
	public RookieGuard(char symbol, int x, int y, char[] path) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.path = path; //double check this
		this.isOverKey = false;
		this.stunned = false;
		this.turns = 0;
		this.weapon = null;
		i = -1;
	} 
	/**
	 * {@inheritDoc}
	 * In this particular class, this function only calls the normalMovement() function, that follows the normal order of the path, and
	 * returns its output.
	 */
	@Override
	public int[] movement(){
		return normalMovement();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public char getSymbol() {
		return symbol;
	}
}
