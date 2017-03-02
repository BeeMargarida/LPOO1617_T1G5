package dkeep.logic;

public class KeepLogic extends Logic {

	public KeepLogic() {
		hero = new Hero('H',8,1);
		enemies = new Character[1];
		enemies[0] = new Ogre();
		isOver = false;
		victory = false;
	}

	@Override
	public void gameplay(char dir, Map map) {
		// TODO Auto-generated method stub

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
