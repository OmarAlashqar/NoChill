package nochill.objects;

import org.newdawn.slick.geom.Rectangle;

import nochill.stages.Stage;

/**
 * This class is a super-class for all moving objects
 */
public abstract class MovingObject extends GameObject {

	public enum Type {
		small, large
	};

	protected final Direction dir;
	protected float speed;

	MovingObject(int xPos, Stage stage, Direction dir, float speed) {
		super(xPos, stage);
		this.dir = dir;
		this.speed = speed;
	}

	// Vertically reflects the sprite
	protected void fixReflection() {
		if (dir == Direction.left)
			sprite = sprite.getFlippedCopy(true, false);
	}

	@Override
	protected Rectangle getBounds() {
		return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
	}

	@Override
	public void update() {
		
		// Moving the object
		if (dir == Direction.left)
			x -= speed;
		else if (dir == Direction.right)
			x += speed;
	}

	public Direction getDir() {
		return dir;
	}

}
