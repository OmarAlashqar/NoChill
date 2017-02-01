package nochill.gui;

import nochill.NoChill;
import nochill.objects.Player;
import nochill.stages.Stage;

/**
 * This class controls the player viewport movement
 */
public class Camera {

	private float x, y;

	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void update(Player player) {

		// Tweening algorithm (smooth camera)
		x += ((player.getX() - (NoChill.width / 2 - 32) - x)) * 0.002;
		y += ((player.getY() - (NoChill.height / 2) - y)) * 0.002;

		// Clamping
		if (x < 0)
			x = 0;
		else if (x + NoChill.width > 64 * Stage.tiles_in_stage)
			x = 64 * Stage.tiles_in_stage - NoChill.width;

		if (y > 0)
			y = 0;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
}
