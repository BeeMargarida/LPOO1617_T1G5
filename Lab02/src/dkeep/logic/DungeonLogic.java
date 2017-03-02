package dkeep.logic;

public class DungeonLogic extends Logic {
	
	public DungeonLogic() {
		hero = new Hero('H',1,1);
		//map = new DungeonMap();
		enemies = new Character[1];
		char[] path = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
		enemies[0] = new RookieGuard('G',1,8, path);
		isOver = false;
		victory = false;
	}
	
	/*public void movementEnemies() {
		Behaviour b = enemy.get(0).getBehaviour();
	}*/
	
	public void gameplay(char dir, Map map) {
		int[] enemymov = enemies[0].movement();
		enemies[0].setX(enemymov[0]);
		enemies[0].setY(enemymov[1]);
		
		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if(((heromov[0] == enemymov[0] && heromov[1] == enemymov[1]) || (heromov[0]+1 == enemymov[0] && heromov[1] == enemymov [1]) || (heromov[0]-1 == enemymov[0] && heromov[1] == enemymov [1]) || (heromov[1]+1 == enemymov[1] && heromov[0] == enemymov [0]) || (heromov[1]-1 == enemymov[1] && heromov[0] == enemymov [0])))
				isOver = true;
			if(map.isKey(heromov[0], heromov[1])){
				map.openDoor();
				return;
			}
			if(map.isS(heromov[0], heromov[1])){
				victory = true;
			}
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
		}
	}
	
	public boolean getVictory(){
		return victory; 
	}
	
	public Logic nextLogic(){
		return new KeepLogic();
	}
	
}
