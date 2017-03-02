package dkeep.logic;

public class Hero extends Character {
	
	private char dir;
	
	public Hero(char s, int x, int y) {
		symbol = s;
		this.x = x;
		this.y = y;
	}
	
	public void setDir(char dir) {
		this.dir = dir;
	}
	
	public int[] movement(){
		if(dir == 'w'){ 
			int[] mov = {x-1,y};
			return mov;
		}
		else if(dir == 'a') {
			int[] mov = {x,y-1};
			return mov;
		}
		else if(dir == 'd'){
			int[] mov = {x,y+1};
			return mov;
		}
		else if(dir == 's'){
			int[] mov = {x+1,y};
			return mov;
		}
		else {
			int[] mov = {x,y};
			return mov;
		}
	}

}
