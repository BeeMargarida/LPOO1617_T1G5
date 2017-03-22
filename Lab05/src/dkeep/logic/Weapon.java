package dkeep.logic;  

public abstract class Weapon {
	
	protected int x;
	protected int y;
	protected char symbol;
	protected char secsymbol;
	protected boolean above;
	protected boolean valid;
	
	public abstract int[] swing(int x, int y); //x and y are the coordinates of the enemy or hero
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public void setNotValid(){
		valid = false;
	}
	
	public void setValid(){
		valid = true;
	}
	
	public boolean getValid(){
		return valid;
	}
	
	public char getSymbol(){
		if(above)
			return secsymbol;
		else
			return symbol;
	}
}
