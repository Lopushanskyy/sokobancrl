package sokoban;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{
	
	private Point2D position;
	private int layer;
	private String imageName;
	
	
	
	public GameElement(Point2D position, int layer, String imageName) {
		this.position = position;
		this.layer = layer;
		this.imageName = imageName;
		
		
	}
	

	@Override
	public String getName() {
		return imageName;
	}
	
	@Override
	public Point2D getPosition() {
		return position;
	}
	
	@Override
	public int getLayer() {
		return layer;
	}
	
	public void setImage(String newImage) {
		this.imageName = newImage;
	}
	
	
	public void setPosition(Point2D newPosition) {
		this.position = newPosition;
	}
	
	
	
	
	
	public static GameElement createElement(char c, Point2D position) {
		if(c == 'E') return Empilhadora.getInstance(position);
		else if(c == 'C') return new Caixote(position);
		else if(c == 'X') return new Alvo(position);
		else if(c == 'B') return new Bateria(position);
		else if(c == '#') return new Parede(position);
		else if(c == ' ') return new Chao(position);
		else if(c == '=') return new Vazio(position);
		else if(c == 'O') return new Buraco(position);
		else if(c == 'P') return new Palete(position);
		else if(c == 'M') return new Martelo(position);
		else if(c == '%') return new ParedeRachada(position);
		else if(c == 'T') return new Teleporte(position);
		return null;
	}
		

}
