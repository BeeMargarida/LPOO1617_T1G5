package dkeep.logic;

public class CellPosition {
	
	private int x;
	private int y;
	
	public CellPosition (int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equals(Object c) {
		if(this.x == ((CellPosition) c).getX() && this.y == ((CellPosition) c).getY())
			return true;
		return false;
	}
}