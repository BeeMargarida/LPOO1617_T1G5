package dkeep.logic;

public class KeepMap extends Map { 

	private static char[][] p = {{'X','X','X','X','X','X','X','X','X'},{'I',' ',' ',' ','O',' ',' ','k','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X','H',' ',' ',' ',' ',' ',' ','X'},{'X','X','X','X','X','X','X','X','X'}};
	private static int[] key = {1,7};
	
	public KeepMap() {
		super(p,key);
	}
	
	public void openDoor() {
		map[1][0] = 'S';
	}
	
	public Map nextMap() {
		return new KeepMap();
	}
}
