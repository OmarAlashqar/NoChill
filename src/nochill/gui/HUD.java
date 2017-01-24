package nochill.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import nochill.Resources;
import nochill.objects.Player;

/**
 * This class controls the HUD elements in the Game state
 */
public class HUD {

	private final Player player;

	private final Font scoreFont;
	private final Image burger;

	public HUD(Player player) {
		this.player = player;

		scoreFont = Resources.getScoreFont();
		burger = Resources.getBurgerHUD();
		burger.setRotation(-10);
	}

	public void render(Graphics g) {

		burger.draw(10, 10);

		g.setColor(Color.white);
		g.setFont(scoreFont);
		String scoreStr = String.valueOf(player.getScore());
		g.drawString(scoreStr, 40 - scoreFont.getWidth(scoreStr) / 2, 20);
	}
}
