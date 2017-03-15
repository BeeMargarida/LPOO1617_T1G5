package dkeep.logic; 

public class DungeonMap extends Map {

	private static char[][] p ={{'X','X','X','X','X','X','X','X','X','X'},{'X','H',' ',' ','I',' ','X',' ','G','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'X',' ','I',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'X','X','X',' ','X','X','X','X',' ','X'},{'X',' ','I',' ','I',' ','X','k',' ','X'},{'X','X','X','X','X','X','X','X','X','X'}};
	private static int[] key = {8,7};
	
	public DungeonMap() {
		super(p,key);
	}

	public void openDoor(){
		map[5][0] = 'S';
		map[6][0] = 'S';
	}

	public Map nextMap() {
		return new KeepMap();
	}

}
