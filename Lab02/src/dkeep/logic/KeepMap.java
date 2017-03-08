package dkeep.logic;

public class KeepMap extends Map {

	public KeepMap() {
		char[][] p = {{'X','X','X','X','X','X','X','X','X'},{'I',' ',' ',' ','O',' ',' ','k','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X','H',' ',' ',' ',' ',' ',' ','X'},{'X','X','X','X','X','X','X','X','X'}};
		map = p;
		key = new int[2];
		key[0] = 1;
		key[1] = 7;
	}
	
	public void openDoor() {
		map[1][0] = 'S';
	}
	
	public Map nextMap() {
		return new KeepMap();
	}
}
