package nochill.states;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import nochill.NoChill;
import nochill.Resources;
import nochill.gui.Camera;
import nochill.gui.GameOver;
import nochill.gui.HUD;
import nochill.gui.PauseMenu;
import nochill.objects.Player;
import nochill.objects.GameObject.Direction;
import nochill.stages.Stage;
import nochill.stages.StageManager;

/**
 * This state is for the actual game
 */
public class Game extends BasicGameState {

	private static int id = 1;

	private StageManager stgManager;
	private Player player;

	private int playerIndex;

	private PauseMenu pauseMenu;
	private GameOver gameOver;

	private HUD hud;
	private Camera cam;

	private boolean shake;

	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		reset(gc, game);
	}

	public void reset(GameContainer gc, StateBasedGame game) {
		stgManager = new StageManager();
		player = new Player(Resources.getAnimations(playerIndex), 5, stgManager.getStage(0), this, stgManager);

		pauseMenu = new PauseMenu(gc, game);
		gameOver = new GameOver(gc, game);

		hud = new HUD(player);
		cam = new Camera(Stage.tiles_in_stage * 64 / 2 - NoChill.width / 2 + 32, -300);
	}

	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {

		checkInput(gc.getInput());

		// Activating the Game Over screen if player dies
		if (!gameOver.isActive() && !player.isAlive()) {
			gameOver.activate(player.getScore());
			gc.getInput().clearKeyPressedRecord();
		}

		if (pauseMenu.isActive())
			pauseMenu.update(gc.getInput());
		else if (gameOver.isActive())
			gameOver.update(gc.getInput());
		else {
			stgManager.update();
			player.update(delta);
			cam.update(player);
		}
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {

		// Shakes the viewport
		if (shake) {
			Random rand = new Random();
			g.translate(rand.nextInt(10), rand.nextInt(10));
		}

		// Rendering everything
		g.translate(-cam.getX(), -cam.getY());
		stgManager.render(g, StageManager.num_stages - 1, player.getStageIndex());
		player.render(g);
		stgManager.render(g, player.getStageIndex() - 1, 0);
		g.translate(cam.getX(), cam.getY());

		if (!gameOver.isActive())
			hud.render(g);

		if (pauseMenu.isActive())
			pauseMenu.render(g);

		else if (gameOver.isActive())
			gameOver.render(g);
	}

	public void setShake(boolean shake) {
		this.shake = shake;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	private void checkInput(Input input) {

		if (player.isAlive()) {
			if (input.isKeyPressed(Input.KEY_ESCAPE)) {
				pauseMenu.flipState();
				input.clearKeyPressedRecord();
			}

			if (!pauseMenu.isActive()) {
				if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W))
					player.move(Direction.up);
				else if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_S))
					player.move(Direction.down);
				else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
					player.move(Direction.left);
				else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
					player.move(Direction.right);
				else
					player.move(Direction.idle);
			}
		}
	}

	public void generateStage() {
		stgManager.next();
		cam.setY(cam.getY() + 64);
	}

	public int getID() {
		return id;
	}

}
