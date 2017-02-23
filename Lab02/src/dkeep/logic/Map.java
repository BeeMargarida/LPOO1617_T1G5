package dkeep.logic;

public abstract class Map {
	
	protected char[][] map;
	
	
	public char[][] getMap(){
		return map;
	}
	
	public boolean isFree(int x, int y) {
		
		return true;
	}
	
	public abstract Map nextMap();

}
