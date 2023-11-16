package sokoban;

import pt.iscte.poo.utils.Point2D;


public interface Obstaculo {
	
	boolean passavel(Point2D position);
	
	public Point2D getPosition();

}

