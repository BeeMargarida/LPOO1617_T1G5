package dkeep.logic;

/**
 * Keepmap is a class that keeps information about the Keep map, containing the matrix of chars representing the map and it's key.
 * @see Map
 */
public class KeepMap extends Map { 

	private char[][] p = {{'X','X','X','X','X','X','X','X','X'},
			{'I',' ',' ',' ','O',' ',' ','k','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X','H',' ',' ',' ',' ',' ',' ','X'},
			{'X','X','X','X','X','X','X','X','X'}};
	private int[] k = {1,7};

	/**
	 * Constructor of KeepMap. Initializes the Map attributes with the private attributes of this class.
	 */
	public KeepMap() {
		super(null,null);
		this.map = p;
		this.key = k;
	}

	/** 
	 * {@inheritDoc}
	 * In this particular class it doesn't go through the map, it only opens the only door of the map. 
	 */
	public void openDoor() {
		map[1][0] = 'S';
	}

	/**
	 * {@inheritDoc}
	 */
	public Map nextMap() {
		return null;
	}
}
