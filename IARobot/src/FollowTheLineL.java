import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;

public class FollowTheLineL implements Behavior {
	private boolean suppressed = false;
	private LightSensor ls;

	// static List<Integer> directionLine = new ArrayList<Integer>();
	// static List<Integer> directionTarpoulin = new ArrayList<Integer>();

	public FollowTheLineL(SensorPort LS, int Dark, int Light) {
		this.ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (ls.getLightValue() > 1100);
	}

	@Override
	public void action() {
		suppressed = false;
		// directionLine.add(0);
		// directionTarpoulin.add(1);
		Motor.A.setSpeed(100);
		Motor.A.forward();

		while ((ls.getLightValue() > 1100) && !suppressed) {
			// LastTarpoulin = directionTarpoulin.size() - 1;
			if (Motor.A.getTachoCount() > 0) {
				turnLine(-10);
			} else {
				turnLine(10);
			}
			Thread.yield();
		}
		suppress();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	static int LastLine, LastTarpoulin;

	public static void turnLine(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
		// directionLine.add(Motor.A.getTachoCount());
	}

	public static void turnTarpaulin(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
		// directionTarpoulin.add(Motor.A.getTachoCount());
	}
}