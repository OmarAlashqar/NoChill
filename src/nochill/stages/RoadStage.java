package nochill.stages;

import org.newdawn.slick.Graphics;

import nochill.Resources;
import nochill.objects.Burger;
import nochill.objects.GameObject.Direction;
import nochill.stages.StageManager.RegionId;
import nochill.stages.StageManager.StageId;

/**
 * This stage contains Ships
 */
class RoadStage extends Stage {

	private Direction dir;
	
	RoadStage(int stageIndex, RegionId region, Direction dir) {
		super(stageIndex, region);
		this.dir = dir;
		id = StageId.road;
		initTiles(region);
		initObjects();
	}

	private void initTiles(RegionId region) {
		for (int xPos = 0; xPos < tiles.length; xPos++) {
			if (region == RegionId.forest)
				tiles[xPos] = Resources.getRoad(0);
			else if (region == RegionId.lava)
				tiles[xPos] = Resources.getRoad(1);
		}
	}

	private void initObjects() {

		int randPos = rand.nextInt(11);

		if (hasBurger){
			burger = new Burger(randPos, this);
			objects.add(burger);
		}
			
		objects.addAll(StageManager.getObjectsSetup(this, dir));
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
	}
}
