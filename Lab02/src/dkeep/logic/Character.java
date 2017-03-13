package dkeep.logic; 

import java.util.ArrayList;
import java.util.List;

public abstract class Character {
	
	protected int x;
	protected int y;
	protected char symbol;
	protected boolean isOverKey; //for the enemies
	protected boolean stunned;
	protected Weapon weapon;
	
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
	
	public abstract char getSymbol();
	
	public abstract int[] movement();
}

