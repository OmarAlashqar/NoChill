package nochill.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import nochill.NoChill;
import nochill.Resources;
import nochill.gui.Button.ActionListener;

/**
 * This class controls the pause screen in the Game state
 */
public class PauseMenu {

	private final Image bg = Resources.getPauseMenuBg();

	private Button[] buttons;
	private boolean active;

	public PauseMenu(final GameContainer gc, StateBasedGame game) {
		initGui(gc, game);
	}

	public void flipState() {
		active = !active;
	}

	public void update(Input input) {
		for (Button btn : buttons)
			btn.update(input);
	}

	public void render(Graphics g) {
		bg.draw(NoChill.width / 2 - bg.getWidth() / 2, NoChill.height / 2 - bg.getHeight() / 2);

		for (Button btn : buttons)
			btn.render(g);
	}

	private void initGui(final GameContainer gc, final StateBasedGame game) {

		// Initializing the buttons
		buttons = new Button[2];
		buttons[0] = new Button("Resume", 1);
		buttons[1] = new Button("Main Menu", 2);

		// Adding ActionListeners to the buttons
		buttons[0].addActionListener(new ActionListener() {
			public void actionEvent() {
				gc.getInput().clearKeyPressedRecord();
				active = false;
			}
		});

		buttons[1].addActionListener(new ActionListener() {
			public void actionEvent() {
				gc.getInput().clearKeyPressedRecord();
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
