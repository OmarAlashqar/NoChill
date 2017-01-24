package nochill.stages;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import nochill.Resources;
import nochill.objects.Drifter;
import nochill.objects.GameObject;
import nochill.objects.GameObject.Direction;
import nochill.objects.MovingObject;
import nochill.objects.MovingObject.Type;
import nochill.objects.Vehicle;

/**
 * This class creates and manages all the stages in a game
 */
public class StageManager {

	public enum StageId {
		ground, flowing, road
	};

	enum RegionId {
		forest, lava
	};

	public static final int num_stages = 15;

	private final static int vehicleLargeWidth = 292;
	private final static int vehicleSmallWidth = 121;
	private final static int drifterLargeWidth = 3 * 64;
	private final static int drifterSmallWidth = 2 * 64;

	private ArrayList<Stage> stages;
	private RegionId currRegion;
	private Direction nextStageDir;

	private int regionCounter = 0;
	private int groundCount = 3;
	private int roadCount = 0;
	private int flowingCount = 0;

	private static Random rand = new Random();

	public StageManager() {
		stages = new ArrayList<Stage>();
		initStages();
	}

	private void initStages() {
		currRegion = RegionId.forest;
		nextStageDir = Direction.left;

		// Initializing an obstacle-less stage
		stages.add(new GroundStage(0, false, currRegion));

		// Filling the stages list with new stages
		for (int i = 1; i < num_stages; i++)
			stages.add(generateStage(i));

		regionCounter = 10;
	}

	// Generates the next stage and discards of the oldest stage
	public void next() {

		// Pushing all the stages indeces back
		for (int stageIndex = 0; stageIndex < stages.size() - 1; stageIndex++) {
			stages.set(stageIndex, stages.get(stageIndex + 1));
			stages.get(stageIndex).setStageIndex(stageIndex);
		}

		// Generating a new stage at the end of the list
		stages.set(stages.size() - 1, generateStage(stages.size() - 1));

		regionCounter++;
		if (regionCounter == 20) {
			flipRegion();
		}
	}

	// Returns a new Stage with a 33% chance of being one of the three stage
	// types
	private Stage generateStage(int index) {
		Stage stage = null;
		while (stage == null) {
			if (groundCount < 2 && Resources.percentChance(33)) {
				stage = new GroundStage(index, currRegion);
				groundCount++;
				roadCount = 0;
				flowingCount = 0;
			} else if (roadCount < 4 && Resources.percentChance(33)) {
				stage = new RoadStage(index, currRegion, nextStageDir);
				flipDir();
				roadCount++;
				groundCount = 0;
				flowingCount = 0;
			} else if (flowingCount < 4 && Resources.percentChance(33)) {
				stage = new FlowingStage(index, currRegion, nextStageDir);
				flipDir();
				flowingCount++;
				groundCount = 0;
				roadCount = 0;
			}
		}

		return stage;
	}

	private void flipDir() {
		if (nextStageDir == Direction.left)
			nextStageDir = Direction.right;
		else if (nextStageDir == Direction.right)
			nextStageDir = Direction.left;
	}

	private void flipRegion() {
		regionCounter = 0;
		if (currRegion == RegionId.forest)
			currRegion = RegionId.lava;
		else if (currRegion == RegionId.lava)
			currRegion = RegionId.forest;
	}

	// Returns an object array that has one of multiple setups for vehicles or
	// drifters
	protected static ArrayList<MovingObject> getObjectsSetup(Stage stage, Direction dir) {
		ArrayList<MovingObject> objects = new ArrayList<MovingObject>();

		// int randInt = rand.nextInt(3);
		int randInt = 2;

		float slow = rand.nextFloat() * (0.2f - 0.1f) + 0.1f;
		float fast = rand.nextFloat() * (0.3f - 0.2f) + 0.2f;

		int shift = rand.nextInt(3);

		// Road setups
		if (stage.getId() == StageId.road) {
			if (randInt == 0) {
				objects.add((new Vehicle(0 + shift, stage, dir, fast, Type.large, vehicleLargeWidth)));
				objects.add((new Vehicle(7 + shift, stage, dir, fast, Type.large, vehicleLargeWidth)));
			}

			else if (randInt == 1) {
				objects.add((new Vehicle(0 + shift, stage, dir, slow, Type.small, vehicleSmallWidth)));
				objects.add((new Vehicle(4 + shift, stage, dir, slow, Type.small, vehicleSmallWidth)));
				objects.add((new Vehicle(8 + shift, stage, dir, slow, Type.small, vehicleSmallWidth)));
			}

			else if (randInt == 2) {
				objects.add((new Vehicle(-3 + shift, stage, dir, slow, Type.large, vehicleLargeWidth)));
				objects.add((new Vehicle(4 + shift, stage, dir, slow, Type.small, vehicleLargeWidth)));
				objects.add((new Vehicle(8 + shift, stage, dir, slow, Type.small, vehicleLargeWidth)));
			}
		}

		// Flowing setups
		else if (stage.getId() == StageId.flowing) {
			if (randInt == 0) {
				objects.add((new Drifter(0 + shift, stage, dir, fast, Type.large, drifterLargeWidth)));
				objects.add((new Drifter(7 + shift, stage, dir, fast, Type.large, drifterLargeWidth)));
			}

			else if (randInt == 1) {
				objects.add((new Drifter(0 + shift, stage, dir, slow, Type.small, drifterSmallWidth)));
				objects.add((new Drifter(4 + shift, stage, dir, slow, Type.small, drifterSmallWidth)));
				objects.add((new Drifter(8 + shift, stage, dir, slow, Type.small, drifterSmallWidth)));
			}

			else if (randInt == 2) {
				objects.add((new Drifter(-1 + shift, stage, dir, slow, Type.large, drifterLargeWidth)));
				objects.add((new Drifter(4 + shift, stage, dir, slow, Type.small, drifterLargeWidth)));
				objects.add((new Drifter(8 + shift, stage, dir, slow, Type.small, drifterLargeWidth)));
			}
		}

		return objects;
	}
	
	public GameObject getObjectColliding(int stageIndex, Rectangle bounds){
		return stages.get(stageIndex).getObjectColliding(bounds);
	}

	public Stage getStage(int index) {
		return stages.get(index);
	}

	public void update() {
		for (Stage stage : stages)
			stage.update();
	}

	// Renders stages from "from" (inclusive) to "to" (inclusive)
	public void render(Graphics g, int from, int to) {
		for (int stageIndex = from; stageIndex >= to; stageIndex--)
			stages.get(stageIndex).render(g);
	}
}
