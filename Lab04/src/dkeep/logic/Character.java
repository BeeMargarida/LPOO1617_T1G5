package dkeep.logic; 

import java.util.ArrayList;
import java.util.List;

public abstract class Character {
	
	protected int x;
	protected int y;
	protected char symbol;
	protected boolean isOverKey; 
	protected boolean stunned;
	protected int turns;
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
	public void gotStunned(){
		stunned = true;
		turns = 2;
	}
	public void backToNormal(){
		stunned = false;
	}
	
	public abstract char getSymbol();
	
	public char getWeaponSymbol(){
		if(weapon == null)
			return ' ';
		else
			return weapon.getSymbol();
	}
	
	public abstract int[] movement();
}

