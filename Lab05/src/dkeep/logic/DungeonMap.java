package dkeep.logic;

import java.io.Serializable;

/**
 * DungeonMap is a class that keeps information about the Dungeon map, containing the matrix of chars representing the map and it's key.
 * @see Map
 */
public class DungeonMap extends Map implements Serializable{

	private char[][] p ={{'X','X','X','X','X','X','X','X','X','X'},
			{'X','H',' ',' ','I',' ','X',' ','G','X'},
			{'X','X','X',' ','X','X','X',' ',' ','X'},
			{'X',' ','I',' ','I',' ','X',' ',' ','X'},
			{'X','X','X',' ','X','X','X',' ',' ','X'},
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X','X','X',' ','X','X','X','X',' ','X'},
			{'X',' ','I',' ','I',' ','X','k',' ','X'},
			{'X','X','X','X','X','X','X','X','X','X'}};
	
	private int[] k = {8,7};
	
	/**
	 * Constructor of DungeonMap. Initializes the Map attributes with the private attributes of this class.
	 */
	public DungeonMap() {
		super(null,null);
		this.map = p;
		this.key = k;
	}
	/** 
	 * {@inheritDoc}
	 * In this particular class it doesn't go through the map, it only opens the only two doors of the map. 
	 */
	public void openDoor(){
		map[5][0] = 'S';
		map[6][0] = 'S';
	}

	/**
	 * {@inheritDoc}
	 */
	public Map nextMap() {
		return new KeepMap();
	}

}
