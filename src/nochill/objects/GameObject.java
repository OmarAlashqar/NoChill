package nochill.objects;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import nochill.NoChill;
import nochill.stages.Stage;

/**
 * This class is the super-class for all objects
 */
public abstract class GameObject {

	public enum ObjectId {
		obstacle, vehicle, drifter, burger
	};

	public enum Direction {
		left, right, up, down, idle
	};

	protected Random rand;

	private Stage stage;
	protected ObjectId id;

	protected Image sprite;
	protected float x, y;

	public GameObject(int xPos, Stage stage) {
		this.stage = stage;
		rand = new Random();

		x = xPos * 64;
		y = NoChill.height - stage.getStageIndex() * 64 - 64;
	}

	// Updates the y coordinate for stages to be rendered at
	public void updateY(int index) {
		y = NoChill.height - index * 64 - 64;
	}

	// Returns true if the object is intersecting with this object
	public boolean intersects(Rectangle bounds) {
		if (bounds.intersects(this.getBounds()))
			return true;
		else
			return false;
	}

	// Returns the hitbox of the object
	protected Rectangle getBounds() {
		Rectangle bounds = new Rectangle(x, y, 64, 64);
		bounds.grow(-5, -5);
		return bounds;
	}

	// Removes the object from existence
	protected void destroy() {
		stage.remove(this);
	}

	public ObjectId getId() {
		return id;
	}

	public void update() {

	}

	public void render(Graphics g) {
		sprite.draw(x, y);
	}

	protected float getY() {
		return y;
	}
}
