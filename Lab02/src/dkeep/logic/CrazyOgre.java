package dkeep.logic;

public class CrazyOgre extends Ogre {

	public CrazyOgre(char symbol, int x, int y) {
		this.symbol = symbol;
		this.x = x;
		this.y = y;
		this.isOverKey = false;
		this.weapon = new Club('*',2,4);
	}

	@Override
	public int[] movement() {
		// TODO Auto-generated method stub
		return null;
	}

}
