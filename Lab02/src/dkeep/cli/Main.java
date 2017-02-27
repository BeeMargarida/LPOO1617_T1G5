package dkeep.cli;
////////////////////////////////////////////IS THIS CORRECT?????????????????????????????/////////////////////
import dkeep.logic.Map;
import dkeep.logic.Game;
import dkeep.logic.DungeonMap;
import dkeep.logic.Logic;
import dkeep.logic.DungeonLogic;
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
		Map map = new DungeonMap();
		System.out.println("chegou aqui");
		Logic logic = new DungeonLogic();
		Game g = new Game(map, logic);
		while(!g.isGameOver()){
			//print(g.getGameMap());
			g.print();
			char dir = askUser();
			g.update(dir);
		}
	}
}
