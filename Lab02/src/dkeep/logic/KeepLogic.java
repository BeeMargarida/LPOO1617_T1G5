package dkeep.logic;

public class KeepLogic extends Logic {

	public KeepLogic() {
		hero = new Hero('H',7,1);
		enemies = new Character[1];
		weapons = new Weapon[1];
		//enemies[0] = new StandardOgre('O',1,4);
		enemies[0] = new CrazyOgre('O',1,4);
		weapons[0] = enemies[0].weapon;
		isOver = false;
		victory = false;
	}

	@Override
	public void gameplay(char dir, Map map) {
		//enemy
		int[] enemymov = enemies[0].movement();
		if(map.isFree(enemymov[0],enemymov[1])){
			if(map.isKey(enemymov[0],enemymov[1])){
				enemies[0].isOverKey = true;
			}
			else
				enemies[0].isOverKey = false;
			enemies[0].setX(enemymov[0]);
			enemies[0].setY(enemymov[1]);
			
		}
		//weapon
		if(enemies[0].weapon != null){
			int[] weaponmov = enemies[0].weapon.swing(enemies[0].getX(), enemies[0].getY());
			if(map.isFree(weaponmov[0], weaponmov[1])){
				if(map.isKey(weaponmov[0], weaponmov[1])){
					enemies[0].weapon.above = true;
				} 
				else
					enemies[0].weapon.above = false;
				enemies[0].weapon.setX(weaponmov[0]);
				enemies[0].weapon.setY(weaponmov[1]);
				if((hero.getX() == weaponmov[0] && hero.getY() == weaponmov[1]) || (hero.getX()+1 == weaponmov[0] && hero.getY() == weaponmov [1]) || (hero.getX()-1 == weaponmov[0] && hero.getY() == weaponmov[1]) || (hero.getY()+1 == weaponmov[1] && hero.getX() == weaponmov[0]) || (hero.getY()-1 == weaponmov[1] && hero.getX() == weaponmov[0])){
					isOver = true;
				}
			}
		}
		//hero
		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if(((heromov[0] == enemies[0].getX() && heromov[1] == enemies[0].getY()) || (heromov[0]+1 == enemies[0].getX() && heromov[1] == enemies[0].getY()) || (heromov[0]-1 == enemies[0].getX() && heromov[1] == enemies[0].getY()) || (heromov[1]+1 == enemies[0].getY() && heromov[0] == enemies[0].getX()) || (heromov[1]-1 == enemies[0].getY() && heromov[0] == enemies[0].getX())))
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
		return null;
	}

	@Override
	public boolean getVictory() {
		return victory;
	}

}
