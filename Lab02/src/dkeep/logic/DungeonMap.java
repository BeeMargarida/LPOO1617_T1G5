package dkeep.logic;

public class DungeonMap extends Map {


	public DungeonMap() {
		char[][] p ={{'X','X','X','X','X','X','X','X','X','X'},{'X',' ',' ',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'X',' ','I',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'X','X','X',' ','X','X','X','X',' ','X'},{'X',' ','I',' ','I',' ','X','k',' ','X'},{'X','X','X','X','X','X','X','X','X','X'}};
		map = p;
	}

	public void openDoor(){
		map[5][0] = 'S';
		map[6][0] = 'S';
	}

	public Map nextMap() {
		return new KeepMap();
	}

}
