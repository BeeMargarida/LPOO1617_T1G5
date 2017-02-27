package dkeep.logic;

public class DungeonLogic extends Logic {
	
	public DungeonLogic() {
		hero = new Hero('H',1,1);
		map = new DungeonMap();
		enemy[0] = new Guard('G',1,8);  
	}
	
	/*public void movementEnemies() {
		Behaviour b = enemy.get(0).getBehaviour();
	}*/
	
}
