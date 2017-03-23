package dkeep.logic;

/**
 * CellPosition is a class that keep information of the coordinates of a cell in the game.
 *
 */
public class CellPosition {
	
	private int x;
	private int y;
	
	/**
	 * Class constructor.
	 * 
	 * @param x coordinate x of the cell position
	 * @param y coordinate y of the cell position
	 */
	public CellPosition (int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Retrieve the valor of the coordinate x.
	 * @return the coordinate x, type int
	 */
	public int getX(){
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
	 * Compares two CellPositions, comparing the coordinates of each one.
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @return boolean true if equals, false if not equals
	 */
	public boolean equals(Object c) {
		if(this.x == ((CellPosition) c).getX() && this.y == ((CellPosition) c).getY())
			return true;
		return false;
	}
}