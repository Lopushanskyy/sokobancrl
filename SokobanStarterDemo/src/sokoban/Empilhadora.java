package sokoban;


import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement{
	
	private static Empilhadora INSTANCE;

	private Empilhadora(Point2D position) {
		super(position, 3, "Empilhadora_U");
		
	}
	
	private Empilhadora() {
		this(null);
	}
	
	public static Empilhadora getInstance(Point2D position) {
		if(INSTANCE==null)
			INSTANCE = new Empilhadora(position);
		return INSTANCE;
	}
	
	public static Empilhadora getInstance() {
		if (INSTANCE == null)
            INSTANCE = new Empilhadora();
        return INSTANCE;
	}
	
	private String refreshImage (int key) {
		switch (key) {
		case 0: return "Empilhadora_L";
		case 1: return "Empilhadora_U";
		case 2: return "Empilhadora_R";
		default: return "Empilhadora_D";
		}
	}

	
	
	public Point2D move(int key) {    
		Point2D newPosition = null;
		if(Direction.isDirection(key)) { // se nao usar este metodo e carregar numa outra tecla que nao as definidas ele chama o illegalArgument da funcao directionFor(key)
			
			Direction direction = Direction.directionFor(key);
			super.setImage(refreshImage(direction.ordinal()));

		
			// Move segundo a direcao gerada, mas so' se estiver dentro dos limites
			newPosition = getPosition().plus(direction.asVector()); // usar newPosition para ver se naquela posicao existe algum objeto para mexer,etc?
			if (newPosition.getX()>=0 && newPosition.getX()<GameEngine.GRID_WIDTH && 
					newPosition.getY()>=0 && newPosition.getY()<GameEngine.GRID_HEIGHT ){
				setPosition(newPosition);
			}
		}
		return newPosition;
	}
	
	

}
