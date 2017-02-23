package dkeep.cli;

import dkeep.logic.Game;


public class Main {
	
	public static void main(String[] args){
		Game g = new Game();
		while(!g.isGameOver()){
			print(g.getGameMap());
			char dir = askUser();
			g.update(dir);
		}
		
	}
}
