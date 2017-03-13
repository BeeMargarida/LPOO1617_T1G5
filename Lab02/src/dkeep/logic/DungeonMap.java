package dkeep.logic; 

public class DungeonMap extends Map {


	public DungeonMap() {
		char[][] p ={{'X','X','X','X','X','X','X','X','X','X'},{'X',' ',' ',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'X',' ','I',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'X','X','X',' ','X','X','X','X',' ','X'},{'X',' ','I',' ','I',' ','X','k',' ','X'},{'X','X','X','X','X','X','X','X','X','X'}};
		map = p;
		key = new int[2];
		key[0] = 8;
		key[1] = 7;
	}

	public void openDoor(){
		map[5][0] = 'S';
		map[6][0] = 'S';
	}

	public Map nextMap() {
		return new KeepMap();
	}

}
