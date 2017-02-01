package nochill;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;

/**
 * This class loads all the resources used by the program and contains some
 * utility algorithms
 */
public class Resources {

	private static Random rand;

	// Fonts
	private static TrueTypeFont guiFont;
	private static TrueTypeFont nameFont;
	private static TrueTypeFont scoreFont;

	// Sprites and Images
	private SpriteSheet playerSprites;
	private SpriteSheet tileSprites;
	private SpriteSheet drifterSprites;
	private Image vehicleSprites;

	private static Image[][] bearSprites = new Image[4][4];
	private static Animation[] bearAnimations = new Animation[4];
	private static Animation bearCase;

	private static Image[][] kidSprites = new Image[4][4];
	private static Animation[] kidAnimations = new Animation[4];
	private static Animation kidCase;

	private static Image[] drifters = new Image[5];
	private static Image[] obstacles = new Image[9];
	private static Image[] vehicles = new Image[5];

	private static Image[] grass = new Image[3];
	private static Image[] water = new Image[3];
	private static Image[] rock = new Image[3];
	private static Image[] lava = new Image[3];
	private static Image[] road = new Image[2];

	private static Image burger;
	private static Image burgerHUD;

	private static Image mainBg;
	private static Image leaderboardBg;
	private static Image pauseMenuBg;
	private static Image gameOverBg;

	public Resources() throws SlickException {
		rand = new Random();
		initSprites();
		initFonts();
	}

	/* Utilities */

	// Returns true x% of the time
	public static boolean percentChance(int x) {
		if (rand.nextInt(100) < x)
			return true;
		else
			return false;
	}

	/* GUI */

	public static Image getMainBg() {
		return mainBg;
	}

	public static Image getLeaderboardBg() {
		return leaderboardBg;
	}

	public static Image getPauseMenuBg() {
		return pauseMenuBg;
	}

	public static Image getGameOverBg() {
		return gameOverBg;
	}

	public static Image getBurgerHUD() {
		return burgerHUD;
	}

	/* PLAYER */

	public static Animation[] getAnimations(int index) {
		if (index == 0)
			return bearAnimations;
		else // index == 1
			return kidAnimations;
	}

	public static Animation[] getCaseAnimations() {
		return new Animation[] { bearCase, kidCase };
	}

	/* TILES */

	public static Image getRandomGrass() {
		return grass[rand.nextInt(3)];
	}

	public static Image getRandomWater() {
		return water[rand.nextInt(3)];
	}

	public static Image getRandomRock() {
		return rock[rand.nextInt(3)];
	}

	public static Image getRandomLava() {
		return lava[rand.nextInt(3)];
	}

	public static Image getRoad(int index) {
		return road[index];
	}

	public static Image getBurger() {
		return burger;
	}

	public static Image getRandomObstacle() {
		return obstacles[rand.nextInt(9)];
	}

	/* VEHICLES */

	public static Image getLargeVehicle() {
		return vehicles[rand.nextInt(2)].copy();
	}

	public static Image getSmallVehicle() {
		return vehicles[rand.nextInt(3) + 2].copy();
	}

	/* DRIFTERS */
	public static Image getLargeDrifter() {
		return drifters[rand.nextInt(2)].copy();
	}

	public static Image getSmallDrifter() {
		return drifters[rand.nextInt(3) + 2].copy();
	}

	/* FONTS */

	public static TrueTypeFont getGuiFont() {
		return guiFont;
	}

	public static TrueTypeFont getNameFont() {
		return nameFont;
	}

	public static TrueTypeFont getScoreFont() {
		return scoreFont;
	}

	private void initFonts() {
		guiFont = new TrueTypeFont(new java.awt.Font("Trebuchet MS", 0, 25), true);
		nameFont = new TrueTypeFont(new java.awt.Font("Trebuchet MS", 1, 40), true);
		scoreFont = new TrueTypeFont(new java.awt.Font("Trebuchet MS", 0, 40), true);
	}

	private void initSprites() throws SlickException {
		playerSprites = new SpriteSheet("res/sprites/player.png", 64, 64);
		tileSprites = new SpriteSheet("res/sprites/tiles.png", 64, 64);
		drifterSprites = new SpriteSheet("res/sprites/drifters.png", 64, 64);
		vehicleSprites = new Image("res/sprites/vehicles.png");

		// Bear Player
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++)
				bearSprites[row][col] = playerSprites.getSprite(col, row);

		for (int i = 0; i < 4; i++)
			bearAnimations[i] = new Animation(bearSprites[i], 150);

		bearAnimations[2].setLooping(false);
		bearAnimations[3].setLooping(false);

		bearCase = new Animation(
				new Image[] { bearSprites[0][1], bearSprites[2][1], bearSprites[1][1], bearSprites[3][1] }, 1000);

		// Kid Player
		for (int row = 4; row < 8; row++)
			for (int col = 0; col < 4; col++)
				kidSprites[row - 4][col] = playerSprites.getSprite(col, row);

		for (int i = 0; i < 4; i++)
			kidAnimations[i] = new Animation(kidSprites[i], 150);

		kidAnimations[2].setLooping(false);
		kidAnimations[3].setLooping(false);

		kidCase = new Animation(new Image[] { kidSprites[0][1], kidSprites[2][1], kidSprites[1][1], kidSprites[3][1] },
				1000);

		// Vehicles
		vehicles[0] = vehicleSprites.getSubImage(0, 0, 292, 94);
		vehicles[1] = vehicleSprites.getSubImage(0, 100, 230, 92);
		vehicles[2] = vehicleSprites.getSubImage(0, 199, 109, 64);
		vehicles[3] = vehicleSprites.getSubImage(128, 199, 121, 64);
		vehicles[4] = vehicleSprites.getSubImage(256, 199, 100, 64);

		// Obstacles
		int index = 0;
		for (int row = 4; row < 7; row++)
			for (int col = 0; col < 3; col++)
				obstacles[index++] = tileSprites.getSprite(col, row);

		// Grass
		grass[0] = tileSprites.getSprite(0, 1);
		grass[1] = tileSprites.getSprite(1, 1);
		grass[2] = tileSprites.getSprite(2, 1);

		// Water
		water[0] = tileSprites.getSprite(0, 0);
		water[1] = tileSprites.getSprite(1, 0);
		water[2] = tileSprites.getSprite(2, 0);

		// Rock
		rock[0] = tileSprites.getSprite(0, 3);
		rock[1] = tileSprites.getSprite(1, 3);
		rock[2] = tileSprites.getSprite(2, 3);

		// Lava
		lava[0] = tileSprites.getSprite(0, 2);
		lava[1] = tileSprites.getSprite(1, 2);
		lava[2] = tileSprites.getSprite(2, 2);

		// Drifters
		drifters[0] = drifterSprites.getSubImage(0, 0, 3 * 64, 64);
		drifters[1] = drifterSprites.getSubImage(0, 1 * 64, 3 * 64, 64);
		drifters[2] = drifterSprites.getSubImage(0, 2 * 64, 2 * 64, 64);
		drifters[3] = drifterSprites.getSubImage(0, 3 * 64, 2 * 64, 64);
		drifters[4] = drifterSprites.getSubImage(0, 4 * 64, 2 * 64, 64);

		// Road
		road[0] = tileSprites.getSprite(0, 7);
		road[1] = tileSprites.getSprite(1, 7);

		// Burger
		burger = tileSprites.getSprite(2, 7);

		// GUI
		burgerHUD = new Image("res/burgerHUD.png");
		mainBg = new Image("res/screens/mainBg.png");
		leaderboardBg = new Image("res/screens/leaderboardBg.png");
		pauseMenuBg = new Image("res/screens/pauseMenu.png");
		gameOverBg = new Image("res/screens/gameOver.png");
	}
}
