package dkeep.cli;
////////////////////////////////////////////IS THIS CORRECT?????????????????????????????/////////////////////
import dkeep.logic.Map;
import dkeep.logic.Game;
import dkeep.logic.DungeonMap;
import dkeep.logic.Logic;
import dkeep.logic.DungeonLogic;

import dkeep.logic.KeepMap;
import dkeep.logic.KeepLogic;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
import java.util.*;


public class Main { 
	 
	
	public static char askUser() {
		Scanner s = new Scanner(System.in);
		return s.next().charAt(0); //retorna o primeiro char do input
		
	} 
	
	/*public static void print(char[][] map){
		for(int i = 0; i < map.length; i++){
			System.out.println(map[i]);
		}
	}*/
	
	public static void main(String[] args){
//		Map map = new DungeonMap();
//		Logic logic = new DungeonLogic();
		Map map = new KeepMap();
		Logic logic = new KeepLogic();
		Game g = new Game(map, logic);
		while(!g.isGameOver()){
			g.print();
			char dir = askUser();
			g.update(dir);
		}
		g.print();
		System.out.println("End of Game!");
		return;
	}
}
