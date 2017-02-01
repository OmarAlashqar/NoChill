package nochill.stages;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import nochill.NoChill;
import nochill.Resources;
import nochill.objects.GameObject;
import nochill.objects.GameObject.ObjectId;
import nochill.stages.StageManager.RegionId;
import nochill.stages.StageManager.StageId;

/**
 * This class is the super-class for all stages Stages control all the tiles and
 * objects that reside in them
 */
public abstract class Stage {

	public static final int tiles_in_stage = 10;

	protected ArrayList<GameObject> objects;
	protected Image[] tiles;
	protected Random rand;

	protected StageId id;
	protected boolean hasBurger;
	GameObject burger;

	private int stageIndex;
	private float y;

	Stage(int stageIndex, RegionId region) {
		this.stageIndex = stageIndex;
		tiles = new Image[tiles_in_stage + 1];
		objects = new ArrayList<GameObject>();
		rand = new Random();

		if (Resources.percentChance(33))
			hasBurger = true;

		y = NoChill.height - stageIndex * 64 - 64;
	}

	// Used for changing the position of the stage when a new stage is generated
	public void setStageIndex(int index) {
		stageIndex = index;
		y = NoChill.height - index * 64 - 64;
		for (GameObject object : objects)
			object.updateY(stageIndex);
	}

	public GameObject getObjectColliding(Rectangle bounds) {

		if (hasBurger && burger.intersects(bounds))
			return burger;

		// Looping through objects in the stage
		for (int nObj = 0; nObj < objects.size(); nObj++) {
			GameObject tempObj = objects.get(nObj);
			if (tempObj.intersects(bounds)) {
				return tempObj;
			}
		}

		return null;
	}

	protected void update() {
		for (GameObject object : objects)
			object.update();
	}

	protected void render(Graphics g) {
		for (int xPos = 0; xPos < tiles.length; xPos++)
			tiles[xPos].draw(xPos * 64, y);

		for (GameObject object : objects)
			object.render(g);
	}

	public void remove(GameObject object) {
		if (object.getId() == ObjectId.burger)
			hasBurger = false;

		objects.remove(object);
	}

	public int getStageIndex() {
		return stageIndex;
	}

	public StageId getId() {
		return id;
	}
}
