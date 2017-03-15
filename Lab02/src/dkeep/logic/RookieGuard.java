package dkeep.logic;

public class RookieGuard extends Guard {

	public RookieGuard(char symbol, int x, int y, char[] path) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.path = path; //double check this
		this.isOverKey = false;
		this.stunned = false;
		this.turns = 0;
		this.weapon = null;
		i = -1;
	} 

	public int[] movement(){
		return normalMovement();
	}
	
	public int[] action() {
		return null;
	}

	@Override
	public char getSymbol() {
		return symbol;
	}
}
