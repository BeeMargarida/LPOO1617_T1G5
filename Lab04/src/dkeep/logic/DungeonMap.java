package dkeep.logic; 

public class DungeonMap extends Map {

	private char[][] p ={{'X','X','X','X','X','X','X','X','X','X'},{'X','H',' ',' ','I',' ','X',' ','G','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'X',' ','I',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'X','X','X',' ','X','X','X','X',' ','X'},{'X',' ','I',' ','I',' ','X','k',' ','X'},{'X','X','X','X','X','X','X','X','X','X'}};
	private int[] k = {8,7};
	
	public DungeonMap() {
		super(null,null);
		this.map = p;
		this.key = k;
	}

	public void openDoor(){
		map[5][0] = 'S';
		map[6][0] = 'S';
	}

	public Map nextMap() {
		return new KeepMap();
	}

}
