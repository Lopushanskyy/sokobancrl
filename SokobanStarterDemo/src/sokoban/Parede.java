package sokoban;

import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement implements Obstaculo{
	

	public Parede(Point2D position) {
		super(position, 2, "Parede");
	}

	@Override
	public boolean passavel(Point2D position) {
		if(position.equals(super.getPosition()))
		return false;
		return true;
	}

}
