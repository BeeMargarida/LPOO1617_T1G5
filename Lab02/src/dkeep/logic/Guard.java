package dkeep.logic;

public abstract class Guard extends Character {
	
	protected char[] path;
	protected int i;
	
	public int[] normalMovement(){
		int[] mov;
		i++;
		if(i == path.length){
			i = 0;
		}
		System.out.println(path[i]);
		if(path[i] == 'w'){ 
			mov = new int[] {x-1,y};
			
		}
		else if(path[i] == 'a') {
			mov = new int[] {x,y-1};
		}
		else if(path[i] == 'd'){
			mov = new int[] {x,y+1};
		}
		else{
			mov = new int[] {x+1,y};
		}
		//i++;
		return mov;
	}
	
	public int[] reverseMovement(){
		int[] mov;
		if(i <= 0){
			i = path.length - 1; 
		}
		//i--;
		System.out.println(path[i]);
		if(path[i] == 'w'){ 
			mov = new int[]{x+1,y};
		}
		else if(path[i] == 'a') {
			mov = new int[]{x,y+1};
		}
		else if(path[i] == 'd'){
			mov = new int[]{x,y-1};
		}
		else{
			mov = new int[]{x-1,y};
		}
		i--;
		return mov;
	}
	
	public abstract int[] movement();
	
	public abstract char getSymbol();
	
	public abstract int[] action();

}
