package nochill.objects;

import org.newdawn.slick.Graphics;

import nochill.Resources;
import nochill.stages.Stage;

/**
 * This moving object serves as an enemy that destorys the Player
 */
public class Vehicle extends MovingObject {

	private Type type;
	private final int maxWidth;

	public Vehicle(int xPos, Stage stage, Direction dir, float speed, Type type, int maxWidth) {
		super(xPos, stage, dir, speed);
		this.type = type;
		this.maxWidth = maxWidth;
		id = ObjectId.vehicle;

		if (type == Type.large)
			sprite = Resources.getLargeVehicle();
		else if (type == Type.small)
			sprite = Resources.getSmallVehicle();

		fixReflection();
	}

	@Override
	public void update() {
		super.update();

		// Resetting the position of the vehicle when off-screen
		if (dir == Direction.left && x + maxWidth < 0)
			x = Stage.tiles_in_stage * 64;

		else if (dir == Direction.right && x > Stage.tiles_in_stage * 64)
			x = 0 - maxWidth;
	}

	@Override
	public void render(Graphics g) {
		if (type == Type.large)
			sprite.draw(x, y - 40);
		else if (type == Type.small)
			sprite.draw(x, y - 10);
	}

}
