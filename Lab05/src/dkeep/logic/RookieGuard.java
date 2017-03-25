package dkeep.logic;

/**
 * RookieGuard is a class that contains information about the simplest of the guards. This guard merely follows the path given to him.
 * @see Guard
 */
public class RookieGuard extends Guard {

	/**
	 * Constructor of the RookieGuard. Puts the i variable to -1 and initializes other variables with the given values.
	 * @param symbol char that represents the RookieGuard
	 * @param x coordinate
	 * @param y coordinate
	 * @param path array of movements of the guard
	 */
	public RookieGuard(char symbol, int[] coord, char[] path) {
		super(symbol,coord,path);
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
