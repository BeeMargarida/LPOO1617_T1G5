package dkeep.logic;

public class RookieGuard extends Guard {

	private char[] path;
	private int i;

	public RookieGuard( char symbol, int x, int y, char[] path) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.path = path; //double check this
		i = -1;
	}

	public int[] movement(){
		i++;
		if(i == path.length){
			i = 0;
		}
		if(path[i] == 'w'){ 
			int[] mov = {x-1,y};
			return mov;
		}
		else if(path[i] == 'a') {
			int[] mov = {x,y-1};
			return mov;
		}
		else if(path[i] == 'd'){
			int[] mov = {x,y+1};
			return mov;
		}
		else{
			int[] mov = {x+1,y};
			return mov;
		}
	}
	
	public int[] action() {
		return null;
	}
}
