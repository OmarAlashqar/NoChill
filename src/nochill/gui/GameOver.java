package nochill.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import nochill.NoChill;
import nochill.Resources;
import nochill.gui.Button.ActionListener;
import nochill.states.Game;
import nochill.states.Leaderboard;

/**
 * This class controls the game over screen in the Game state
 */
public class GameOver {

	private final Image bg = Resources.getGameOverBg();
	private final Image burger = Resources.getBurgerHUD();

	private final Font nameFont = Resources.getNameFont();
	private final Font scoreFont = Resources.getScoreFont();

	private char[] name;
	private Button[] nameButtons;
	private Button[] menuButtons;

	private boolean active;
	private boolean nameMenu;
	private int score;

	private Leaderboard lBoard;

	public GameOver(final GameContainer gc, StateBasedGame game) {
		lBoard = (Leaderboard) game.getState(2);
		name = new char[] { 'A', 'A', 'A' };
		initGui(gc, game);
	}

	// Activates the Game Over screen with the player's score
	public void activate(int score) {
		this.score = score;
		active = !active;

		if (score >= lBoard.getLowestScore())
			nameMenu = true;
	}

	public void update(Input input) {
		if (nameMenu)
			for (Button btn : nameButtons)
				btn.update(input);
		else
			for (Button btn : menuButtons)
				btn.update(input);
	}

	public void render(Graphics g) {
		bg.draw(NoChill.width / 2 - bg.getWidth() / 2, NoChill.height / 2 - bg.getHeight() / 2);

		// Drawing the player's score
		burger.draw(180, 120);

		g.setFont(scoreFont);
		g.setColor(Color.white);
		g.drawString(String.valueOf(score), 260, 125);

		// Drawing the GUI
		if (nameMenu) {
			for (Button btn : nameButtons)
				btn.render(g);

			// Name
			g.setFont(nameFont);
			g.setColor(Color.white);
			for (int i = 0; i < 3; i++)
				g.drawString(String.valueOf(name[i]), 143 + 100 * i, 275);
		} else
			for (Button btn : menuButtons)
				btn.render(g);
	}

	private void initGui(final GameContainer gc, final StateBasedGame game) {

		// Initializing the buttons
		nameButtons = new Button[7];
		menuButtons = new Button[2];

		// Initializing all the buttons and their actions
		for (int i = 0; i < 6; i++) {
			final int j = i;
			if (i % 2 == 0) {
				nameButtons[i] = new Button("/\\", 131 + 100 * (i / 2), 200);
				nameButtons[i].addActionListener(new ActionListener() {
					public void actionEvent() {
						name[j / 2]--;
						if (name[j / 2] < 65)
							name[j / 2] = 90;
					}
				});
			}

			else {
				nameButtons[i] = new Button("\\/", 131 + 100 * (i / 2), 350);
				nameButtons[i].addActionListener(new ActionListener() {
					public void actionEvent() {
						name[j / 2]++;
						if (name[j / 2] > 90)
							name[j / 2] = 65;
					}
				});
			}

		}

		nameButtons[6] = new Button("Save Score", 3);
		nameButtons[6].addActionListener(new ActionListener() {
			public void actionEvent() {
				lBoard.append(String.valueOf(name), score);
				nameMenu = false;
			}
		});

		menuButtons[0] = new Button("Play Again", 1);
		menuButtons[0].addActionListener(new ActionListener() {
			public void actionEvent() {
				gc.getInput().clearKeyPressedRecord();
				Game tempGame = (Game) game.getState(1);
				tempGame.reset(gc, game);
				game.enterState(1);
			}
		});

		menuButtons[1] = new Button("Main Menu", 2);
		menuButtons[1].addActionListener(new ActionListener() {
			public void actionEvent() {
				game.enterState(0);
			}
		});
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
}
