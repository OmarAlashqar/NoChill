package nochill.objects;

import org.newdawn.slick.Graphics;

import nochill.Resources;
import nochill.stages.Stage;

/**
 * This object serves as a pick-up score item
 */
public class Burger extends GameObject {

	private float yShift;
	
	public Burger(int xPos, Stage stage) {
		super(xPos, stage);
		id = ObjectId.burger;
		sprite = Resources.getBurger();
		
		yShift = rand.nextInt(360);
	}

	@Override
	public void update() {
		
		// Looping through values of 0 to 360
		yShift += 0.005f;
		if (yShift > 360)
			yShift = 0;
	}

	@Override
	public void render(Graphics g) {
		sprite.draw(x, (float) (y + 5 * Math.sin(yShift)) - 15);
	}

}
