package nochill.objects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import nochill.NoChill;
import nochill.stages.Stage;
import nochill.stages.StageManager;
import nochill.stages.StageManager.StageId;
import nochill.states.Game;

/**
 * This object controls the player
 */
public class Player extends GameObject {

	private Game game;
	private StageManager stgManager;

	private Animation[] animations;
	private Animation currAnimation;

	private final float speed = 0.5f;
	private Direction dir;
	private int score;

	private boolean transitioning;
	private float moveCounter;

	private boolean alive;
	private float soulOpacity;

	public Player(Animation[] animations, int xPos, Stage stage, Game game, StageManager stgManager) {
		super(xPos, stage);
		this.animations = animations;
		this.game = game;
		this.stgManager = stgManager;

		currAnimation = animations[3];
		currAnimation.setCurrentFrame(3);

		dir = Direction.down;
		score = 0;

		alive = true;
		soulOpacity = 1f;
	}

	public void update(int delta) {

		if (alive) {

			currAnimation.update(delta);

			Rectangle bounds = getBounds();

			// Player dies when they touch the left and right edges
			if (bounds.getX() < 0 || bounds.getX() + bounds.getWidth() > Stage.tiles_in_stage * 64)
				destroy();

			GameObject objColliding = stgManager.getObjectColliding(getStageIndex(), bounds);

			if (objColliding != null) {
				if (objColliding.getId() == ObjectId.burger) {
					objColliding.destroy();
					score++;
				} else if (!transitioning && objColliding.getId() == ObjectId.drifter)
					x += ((Drifter) objColliding).getSpeed();
				else if (objColliding.getId() == ObjectId.vehicle)
					destroy();
			}

			else if (objColliding == null) {
				if (!transitioning && stgManager.getStage(getStageIndex()).getId() == StageId.flowing)
					destroy();
			}

			// Moving
			if (moveCounter > 0) {

				if (dir == Direction.up)
					y -= speed;
				else if (dir == Direction.down)
					y += speed;

				moveCounter -= speed;
				if (moveCounter == 0)
					transitioning = false;
			}
		}

		else if (!alive) {
			soulOpacity -= 0.001f;
			if (soulOpacity < 0.7)
				game.setShake(false);
		}
	}

	// Attempts to move the player with regards to the user input
	public void move(Direction dir) {
		if (alive && !transitioning && canMove(dir)) {
			this.dir = dir;
			selectAnimation();

			if (dir == Direction.up || dir == Direction.down) {
				moveCounter = 64;
				transitioning = true;

				// Midscreen clamp
				if (dir == Direction.up && getY() <= 0) {
					game.generateStage();
					y += 64;
				}
			} else {
				if (dir == Direction.left)
					x -= speed / 2;
				else if (dir == Direction.right)
					x += speed / 2;
			}
		}
	}

	// Returns true if there is no obstacle blocking the way
	private boolean canMove(Direction dir) {
		GameObject obj;

		if (dir == Direction.up)
			obj = stgManager.getObjectColliding(getStageIndex() + 1, getBounds(Direction.up));
		else if (dir == Direction.down) {
			try {
				obj = stgManager.getObjectColliding(getStageIndex() - 1, getBounds(Direction.down));
			} catch (Exception e) {
				return false;
			}
		} else
			obj = stgManager.getObjectColliding(getStageIndex(), getBounds(dir));

		if (obj == null || obj.getId() != ObjectId.obstacle)
			return true;
		else
			return false;
	}

	@Override
	public void destroy() {
		alive = false;
		game.setShake(true);
	}

	@Override
	public void render(Graphics g) {
		if (alive)
			currAnimation.draw(x, y - 25);
		else
			currAnimation.draw(x, y - 25 - (1 - soulOpacity) * 10, new Color(255, 255, 255, soulOpacity));
	}

	private void selectAnimation() {
		switch (dir) {
		case left:
			currAnimation = animations[0];
			currAnimation.start();
			break;
		case right:
			currAnimation = animations[1];
			currAnimation.start();
			break;
		case up:
			currAnimation = animations[2];
			currAnimation.restart();
			break;
		case down:
			currAnimation = animations[3];
			currAnimation.restart();
		case idle:
			if (currAnimation == animations[0] || currAnimation == animations[1]) {
				currAnimation.stop();
				currAnimation.setCurrentFrame(1);
			}
			break;
		}

	}

	public boolean isAlive() {
		if (!alive && soulOpacity < -0.5)
			return false;
		else
			return true;
	}

	public float getX() {
		return x;
	}

	// Returns a calculated stageIndex based on the player's y position
	public int getStageIndex() {
		return (int) (-(y + 64 - NoChill.height) / 64);
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x + 20, y + 5, 25, 35);
	}

	private Rectangle getBounds(Direction dir) {
		switch (dir) {
		case left:
			return new Rectangle(x + 20 - 5, y + 5, 25, 35);
		case right:
			return new Rectangle(x + 20 + 5, y + 5, 25, 35);
		case up:
			return new Rectangle(x + 20, y + 5 - 64, 25, 35);
		case down:
		default:
			return new Rectangle(x + 20, y + 5 + 64, 25, 35);
		}
	}

	public int getScore() {
		return score;
	}

	public boolean isMoving() {
		return transitioning;
	}
}
