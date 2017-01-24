package nochill.stages;

import java.util.ArrayList;
import java.util.Collections;

import nochill.Resources;
import nochill.objects.Burger;
import nochill.objects.Obstacle;
import nochill.stages.StageManager.RegionId;
import nochill.stages.StageManager.StageId;

/**
 * This stage contains Obstacles
 */
class GroundStage extends Stage {

	GroundStage(int stageIndex, RegionId region) {
		super(stageIndex, region);
		id = StageId.ground;
		initTiles(region);
		initObjects();
	}

	GroundStage(int stage, boolean trees, RegionId region) {
		super(stage, region);
		initTiles(region);
		if (trees)
			initObjects();
		else
			hasBurger = false;
	}

	private void initTiles(RegionId region) {
		for (int xPos = 0; xPos < tiles.length; xPos++) {
			if (region == RegionId.forest)
				tiles[xPos] = Resources.getRandomGrass();
			else if (region == RegionId.lava)
				tiles[xPos] = Resources.getRandomRock();
		}
	}

	private void initObjects() {

		// A random number of obstacles will be generated
		int nObstacles = rand.nextInt(3) + 2;

		// Creating an array of unique random obstacle positions in the stage
		ArrayList<Integer> obsPos = new ArrayList<Integer>();
		for (int i = 0; i < Stage.tiles_in_stage; i++)
			obsPos.add(i);
		Collections.shuffle(obsPos);

		for (int i = 0; i < nObstacles; i++)
			objects.add(new Obstacle(obsPos.get(i), this));

		if (hasBurger) {
			burger = new Burger(obsPos.get(nObstacles), this);
			objects.add(burger);
		}
	}
}
