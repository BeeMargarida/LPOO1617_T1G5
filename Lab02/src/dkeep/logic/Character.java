package dkeep.logic;

public abstract class Character {
	
	protected int x;
	protected int y;
	protected char symbol;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
}

