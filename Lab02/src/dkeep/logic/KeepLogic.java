package dkeep.logic;

public class KeepLogic extends Logic {

	public KeepLogic() {
		hero = new Hero('H',7,1);
		enemies = new Character[1];
		enemies[0] = new StandardOgre('O',1,4);
		isOver = false;
		victory = false;
	}

	@Override
	public void gameplay(char dir, Map map) {
		int[] enemymov = enemies[0].movement();
		if(map.isFree(enemymov[0],enemymov[1])){
			if(map.isKey(enemymov[0],enemymov[1])){
				enemies[0].isOverKey = true;
			}
			enemies[0].setX(enemymov[0]);
			enemies[0].setY(enemymov[1]);
		}
		if(enemies[0].weapon != null){
			int[] weaponmov = enemies[0].weapon.swing(enemymov[0], enemymov[1]);
			if(map.isFree(weaponmov[0], weaponmov[1])){
				enemies[0].weapon.setX(weaponmov[0]);
				enemies[0].weapon.setY(weaponmov[1]);
			}
			if(((hero.getX() == weaponmov[0] && hero.getY() == weaponmov[1]) || (hero.getX()+1 == weaponmov[0] && hero.getY() == weaponmov [1]) || (hero.getX()-1 == weaponmov[0] && hero.getY() == weaponmov [1]) || (hero.getY()+1 == weaponmov[1] && hero.getX() == weaponmov [0]) || (hero.getY()-1 == weaponmov[1] && hero.getX() == weaponmov [0]))){
				isOver = true;
			}
			if(map.isKey(weaponmov[0], weaponmov[1])){
				
			}
		}

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

	@Override
	public Logic nextLogic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getVictory() {
		// TODO Auto-generated method stub
		return victory;
	}

}
