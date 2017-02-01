package nochill;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import nochill.states.Game;
import nochill.states.Leaderboard;
import nochill.states.Menu;

/**
 * This class initializes the whole program and controls all the states.
 * <p>
 * This program is a game in which the player traverses an endless and randomly
 * generated world while avoiding obstacles and collecting points.
 * <p>
 * 
 * @author Omar Alashqar
 * @since January 23, 2017
 *
 */
public class NoChill extends StateBasedGame {

	public static final int width = 512;
	public static final int height = 640;
	private static final String name = "No Chill";

	public static void main(String[] args) {

		try {
			AppGameContainer appgc = new AppGameContainer(new NoChill(name));

			appgc.setDisplayMode(width, height, false);
			appgc.setShowFPS(false);
			appgc.setMaximumLogicUpdateInterval(1);
			appgc.setIcon("res/favicon.png");
			appgc.start();

		} catch (SlickException ex) {
			Logger.getLogger(NoChill.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private NoChill(String name) throws SlickException {
		super(name);
	}

	// Initializes all the game states and starts the game
	public void initStatesList(GameContainer gc) throws SlickException {
		new Resources();

		addState(new Menu());
		addState(new Game());
		addState(new Leaderboard());
		enterState(0);
	}

	// Makes sure that the leaderboard is saved when the game is closed
	@Override
	public boolean closeRequested() {
		((Leaderboard) getState(2)).writeHighScores();
		System.exit(0);
		return false;
	}

}
