package dkeep.logic;

public class Enemy extends Character {

	private Behaviour behaviour;
	
	public Enemy(char symbol, int x, int y){
		this.symbol = symbol;
		this.x = x;
		this.y = y;
	}
	
	public Behaviour getBehaviour() {
		return behaviour;
	}
	
	
}
