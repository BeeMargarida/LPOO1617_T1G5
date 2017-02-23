package dkeep.logic;

public class KeepMap extends Map {

	public KeepMap() {
		char[][] p = {{'X','X','X','X','X','X','X','X','X'},{'I',' ',' ',' ','O',' ',' ','k','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X','H',' ',' ',' ',' ',' ',' ','X'},{'X','X','X','X','X','X','X','X','X'}};
		map = p;
	}
	
	public Map nextMap() {
		return new KeepMap();
	}
}
