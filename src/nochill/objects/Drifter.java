package nochill.objects;

import org.newdawn.slick.geom.Rectangle;

import nochill.Resources;
import nochill.stages.Stage;

/**
 * This moving object serves as a means for the player to cross bodies of
 * water/lava
 */
public class Drifter extends MovingObject {

	private final int maxWidth;

	public Drifter(int xPos, Stage stage, Direction dir, float speed, Type type, int maxWidth) {
		super(xPos, stage, dir, speed);
		this.maxWidth = maxWidth;
		id = ObjectId.drifter;

		if (type == Type.large)
			sprite = Resources.getLargeDrifter();

		else if (type == Type.small)
			sprite = Resources.getSmallDrifter();

		fixReflection();
	}

	public float getSpeed() {
		if (dir == Direction.left)
			return -speed;
		else
			return speed;
	}

	@Override
	protected Rectangle getBounds() {
		Rectangle bounds = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
		bounds.grow(-15, 0);
		return bounds;
	}

	@Override
	public void update() {
		super.update();

		// Resetting the position of the drifter when off-screen
		if (dir == Direction.left && x + maxWidth < 0)
			x = Stage.tiles_in_stage * 64;

		else if (dir == Direction.right && x > Stage.tiles_in_stage * 64)
			x = 0 - maxWidth;
	}
}
