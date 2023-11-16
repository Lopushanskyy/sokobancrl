package sokoban;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.sokobanstarter.Empilhadora;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.Obstaculo;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer { //criar interface para objetos que se movem e outra para obstaculos e outra para objetos de interacao

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	
	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<GameElement> gameElements;
	private List<Movable> movables;
	private List<Obstaculo> obstaculos;
	
	
	private GameEngine() {  
		gameElements = new ArrayList<>();
		
	}
	
	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}
	
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();    // obtem o codigo da tecla pressionada 
		
		if (!Direction.isDirection(key)) return;
		for (Obstaculo o: obstaculos)
			if (Empilhadora.getInstance().newPosition(key).equals(o.getPosition()))
				return;
		Empilhadora.getInstance().move(key);			
		

		gui.update();                  // redesenha a lista de ImageTiles na GUI, 
		                               // tendo em conta as novas posicoes dos objetos
	}
	
	
	public void start() {

		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que ser feitos!
		
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI
		
		
		
		sendImagesToGUI();      // enviar as imagens para a GUI
		//gui.update(); acho que nao é preciso colocar aqui 
		runGmElemAddToList();
				
		// Escrever uma mensagem na StatusBar
		gui.setStatusMessage("Sokoban");
		
	}
	
	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
		// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
		private void sendImagesToGUI() {
			gui.addImages(createElements(5));
		}
	
	
	
	private List<ImageTile> createElements(int level) {
		List<ImageTile> tileList = new ArrayList<>();
		GameElement element = null;
		int lineNumber = 0; //(y)
		try {
            Scanner fileScanner = new Scanner(new File("levels/level" + level + ".txt"));
            while (fileScanner.hasNextLine()) {
            	String line = fileScanner.nextLine();
            	for (int x = 0; x < line.length() ; x++) {
            		tileList.add(new Chao(new Point2D(x,lineNumber))); //adiciona o chao na zona toda senao a empilhadora fica com fundo branco
            		element = GameElement.createElement(line.charAt(x), new Point2D(x,lineNumber));
            		tileList.add(element);//cria o elemento de jogo conforme a letra que aparece no ficheiro de texto na posicao
            		gameElements.add(element);
            	}
            	lineNumber++;
            }
            fileScanner.close();
		}
		catch(FileNotFoundException e) {
			System.err.println("Erro na abertura do ficheiro");
		}
		return tileList;
		
	}
	
	
	public String getImageTileAtPosition(Point2D position) {
        for (GameElement g : gameElements) {
        	if (g.getLayer() == 2 && g.getPosition().equals(position)) { 
                return g.getName();
            }
        }
        
        return null; 
    }
	
	private void runGmElemAddToList() {
		for (GameElement g: gameElements)
			addToList(g);
	}
	
	private void addToList(GameElement g) {
		switch(g.getName()) {
		case "Parede": obstaculos.add((Obstaculo) g);
		}
	}
	
	public void addMovable() {
		for(GameElement g : gameElements) {
			if(g.getName().equals("Caixote"))
				movables.add((Movable) g);
		}
	}
	
	public void addObstaculo() {
		for(GameElement g : gameElements) {
			if(g.getName().equals("Parede"))
				obstaculos.add((Obstaculo) g);
		}
	}

	
	
}

