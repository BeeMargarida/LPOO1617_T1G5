package dkeep.logic;

public class DungeonMap extends Map {
	
	
	public DungeonMap() {
		char[][] p ={{'X','X','X','X','X','X','X','X','X','X'},{'X',' ',' ',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'X',' ','I',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'X','X','X',' ','X','X','X','X',' ','X'},{'X',' ','I',' ','I',' ','X','k',' ','X'},{'X','X','X','X','X','X','X','X','X','X'}};
		map = p;
	}
	
	public Map nextMap() {
		return new KeepMap();
	}

}
