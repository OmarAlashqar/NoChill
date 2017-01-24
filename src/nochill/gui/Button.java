package nochill.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import nochill.NoChill;
import nochill.Resources;

/*
 * This class is a gui element that fires an action when clicked
 */
public class Button {

	/*
	 * This interface must be implemented after the creation of a Button in
	 * order to assign an action to it
	 */
	public interface ActionListener {
		public void actionEvent();
	}

	private enum Style {
		centred, exact
	};

	private final Color btnColor = new Color(255, 255, 255, 0.5f);
	private final Font guiFont = Resources.getGuiFont();
	private final int minWidth = 200;
	private final int maxWidth = 400;
	private final int height = 50;

	private ActionListener actListener;
	private final String btnText;
	private Style style;

	private boolean mouseHover;
	private int width;
	private int x;
	private int y;

	// Creates a button in the grid
	public Button(String btnText, int pos) {
		this.btnText = btnText;
		style = Style.centred;
		width = minWidth;

		x = NoChill.width / 2 - minWidth / 2;
		y = NoChill.height / 7 * (pos + 2);
	}

	// Creates a button at the given position
	public Button(String btnText, int x, int y) {
		this.btnText = btnText;
		this.x = x;
		this.y = y;
		style = Style.exact;
		width = 50;
	}

	// Implements the ActionListener interface to assign an event to the Button
	public void addActionListener(ActionListener actListener) {
		this.actListener = actListener;
	}

	// Runs this Button's action command
	private void fireAction() {
		actListener.actionEvent();
		resetButton();
	}

	// Resets the button to default width
	private void resetButton() {
		if (style == Style.centred) {
			x = NoChill.width / 2 - minWidth / 2;
			width = minWidth;
		}
	}

	public void update(Input input) {
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		Rectangle btnRect = new Rectangle(x, y, width, height);
		Rectangle mouseRect = new Rectangle(mouseX, mouseY, 0, 0);

		// Setting mouse hover true or false
		if (btnRect.intersects(mouseRect) && !mouseHover)
			mouseHover = true;
		else if (!btnRect.intersects(mouseRect) && mouseHover)
			mouseHover = false;

		// Firing the ActionEvent
		if (mouseHover && input.isMousePressed(0))
			fireAction();

		// Growing or shrinking based on hover state
		if (style == Style.centred) {
			if (mouseHover && width < maxWidth) {
				width += 2;
				x--;
			} else if (!mouseHover && width > minWidth) {
				width -= 2;
				x++;
			}
		}
	}

	public void render(Graphics g) {
		g.setFont(guiFont);

		if (mouseHover) {
			g.setColor(Color.white);
			g.fillRect(x, y, width, height);
			g.setColor(Color.black);
		} else {
			g.setColor(btnColor);
			g.fillRect(x, y, width, height);
			g.setColor(Color.darkGray);
		}

		// Draws the btnText centred in the button
		g.drawString(btnText, x + width / 2 - guiFont.getWidth(btnText) / 2,
				y + height / 2 - guiFont.getHeight(btnText) / 2);
	}
}
