package dkeep.logic;

public class KeepMap extends Map { 

	private char[][] p = {{'X','X','X','X','X','X','X','X','X'},{'I',' ',' ',' ','O',' ',' ','k','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X','H',' ',' ',' ',' ',' ',' ','X'},{'X','X','X','X','X','X','X','X','X'}};
	private int[] k = {1,7};

	public KeepMap() {
		super(null,null);
		this.map = p;
		this.key = k;
	}

	public void openDoor() {
		map[1][0] = 'S';
	}

	public Map nextMap() {
		return new KeepMap();
	}
}
