package nochill.stages;

import org.newdawn.slick.Graphics;

import nochill.Resources;
import nochill.objects.Burger;
import nochill.objects.GameObject.Direction;
import nochill.stages.StageManager.RegionId;
import nochill.stages.StageManager.StageId;

/**
 * This stage contains Drifters
 */
class FlowingStage extends Stage {
	
	private Direction dir;
	
	FlowingStage(int stageIndex, RegionId region, Direction dir) {
		super(stageIndex, region);
		this.dir = dir;
		id = StageId.flowing;
		initTiles(region);
		initObjects();
	}

	private void initTiles(RegionId region) {
		for (int xPos = 0; xPos < tiles.length; xPos++) {
			if (region == RegionId.forest)
				tiles[xPos] = Resources.getRandomWater();
			else if (region == RegionId.lava)
				tiles[xPos] = Resources.getRandomLava();
		}
	}

	private void initObjects() {

		int randPos = rand.nextInt(11);

		objects.addAll(StageManager.getObjectsSetup(this, dir));

		if (hasBurger) {
			burger = new Burger(randPos, this);
			objects.add(burger);
		}
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
	}
}
