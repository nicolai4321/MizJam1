import java.awt.Color;
import java.awt.image.BufferedImage;

import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.GameWindow;
import nWiweEngine.LevelController;
import nWiweEngine.Sprite;

public class Main {
	private static int FPS = 60;
	private static boolean SHOW_DEBUG = true;
	private static String TITLE = "GAME TITLE";
	private static int WIDTH = 1820;
	private static int HEIGHT = 980;
	private static float SCALE = 1f;
	private static int GRID_SIZE = 32;

	public static void main(String args[]) {
		//Sprite kit
		BufferedImage sprites = Sprite.readImage("images/colored_transparent_packed.png");
				
		//UI
		UI ui = new UI(sprites);
		
		//GameController
		GameController gameController = new GameController(TITLE, WIDTH, HEIGHT, SCALE, GRID_SIZE);
		gameController.constructControllers(ui);
		
		
		//Add different objects
		GameObject[] objectTypes = {
				new Player(gameController, 0, 0, sprites, ui),
				new Tree(gameController, 0, 0, sprites),
				new Wall(gameController, 0, 0, sprites),
				new Water(gameController, 0, 0),
				new Warrior(gameController, 0, 0, sprites),
				new Wizard(gameController, 0, 0, sprites),
				new Key(gameController, 0, 0, sprites),
				new Door(gameController, 0, 0, sprites)};
		gameController.addObjectTypes(objectTypes);
		
		//Lights
		Class[] lightWalls = {};
		gameController.addLightWalls(lightWalls);
		
		LevelController levelController = gameController.getLevelController();
		
		boolean useEditor = false;
		if(useEditor) {
			levelController.useEditor();			
		} else {
			levelController.loadLevel("level.lvl");
			levelController.startGame();
		}

		GameWindow gameWindow = gameController.getGameWindow();
		gameWindow.constructWindow();
		gameWindow.getGraphicCanvas().setDefaultColor(new Color(55, 65, 35));
		gameController.startGame(FPS, SHOW_DEBUG);
	}
}
