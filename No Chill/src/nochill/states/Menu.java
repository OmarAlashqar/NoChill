package nochill.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import nochill.NoChill;
import nochill.Resources;
import nochill.gui.Button;
import nochill.gui.Button.ActionListener;

/**
 * This state is for the main menu
 */
public class Menu extends BasicGameState {

	private static int id = 0;

	private Animation[] caseAnimations;
	private int playerIndex;
	private Button[] buttons;
	private Image bg;

	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		caseAnimations = Resources.getCaseAnimations();
		playerIndex = 0;
		initGui(gc, game);
	}

	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		caseAnimations[playerIndex].update(delta);

		for (Button btn : buttons)
			btn.update(gc.getInput());
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {

		bg.draw(0, 0);

		caseAnimations[playerIndex].draw(NoChill.width / 2 - 64, 100, caseAnimations[playerIndex].getWidth() * 2,
				caseAnimations[playerIndex].getHeight() * 2);

		for (Button btn : buttons)
			btn.render(g);
	}

	private void initGui(final GameContainer gc, final StateBasedGame game) {

		bg = Resources.getMainBg();

		// Initializing the buttons
		buttons = new Button[5];
		buttons[0] = new Button("<", 106, 150);
		buttons[1] = new Button(">", 356, 150);
		buttons[2] = new Button("Play", 1);
		buttons[3] = new Button("Leaderboard", 2);
		buttons[4] = new Button("Quit", 3);

		// Adding ActionListeners to the buttons
		buttons[0].addActionListener(new ActionListener() {
			public void actionEvent() {
				playerIndex--;

				// Clamp
				if (playerIndex < 0)
					playerIndex = caseAnimations.length;
				else if (playerIndex > 1)
					playerIndex = 0;
				
				caseAnimations[playerIndex].restart();
			}
		});

		buttons[1].addActionListener(new ActionListener() {
			public void actionEvent() {
				playerIndex++;
				
				// Clamp
				if (playerIndex < 0)
					playerIndex = caseAnimations.length;
				else if (playerIndex > 1)
					playerIndex = 0;
				
				caseAnimations[playerIndex].restart();
			}
		});

		buttons[2].addActionListener(new ActionListener() {
			public void actionEvent() {
				gc.getInput().clearKeyPressedRecord();
				Game tempGame = (Game) game.getState(1);
				tempGame.setPlayerIndex(playerIndex);
				tempGame.reset(gc, game);
				game.enterState(1);
			}
		});

		buttons[3].addActionListener(new ActionListener() {
			public void actionEvent() {
				gc.getInput().clearKeyPressedRecord();
				game.enterState(2);
			}
		});

		buttons[4].addActionListener(new ActionListener() {
			public void actionEvent() {
				game.closeRequested();
			}
		});
	}

	public int getID() {
		return id;
	}

}
