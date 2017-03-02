package dkeep.logic;

public abstract class Weapon {
	
	protected int x;
	protected int y;
	protected char symbol;
	
	public abstract int[] swing(int x, int y); //x and y are the coordinates of the enemy
}
