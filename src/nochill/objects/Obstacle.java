package nochill.objects;

import nochill.Resources;
import nochill.stages.Stage;

/**
 * This object serves as an obstacle that impedes the player's movement
 */
public class Obstacle extends GameObject {

	public Obstacle(int xPos, Stage stage) {
		super(xPos, stage);
		id = ObjectId.obstacle;
		sprite = Resources.getRandomObstacle();
	}

}
