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

	public static void main(String[] args){
		Map map = new DungeonMap();
		int[] heropos = map.getHeroPos(); 
		Logic logic = new DungeonLogic(map, heropos);
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
