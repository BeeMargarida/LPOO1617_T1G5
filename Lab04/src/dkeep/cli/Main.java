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

	public static void main(String[] args){
		
		Map map = new DungeonMap();
		int[] numEnemy = {1,1};
		int[] heropos = map.getHeroPos();
		Logic logic = new DungeonLogic(map, heropos, numEnemy[0]); //ACABAR
		Game g = new Game(map, logic, numEnemy);
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
