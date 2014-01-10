import java.util.ArrayList;
import java.util.List;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;

public class FollowTheLine implements Behavior {
	private boolean suppressed = false;
	private LightSensor ls;

	static List<Integer> directionLine = new ArrayList<Integer>();
	static List<Integer> directionTarpoulin = new ArrayList<Integer>();

	public FollowTheLine(SensorPort LS, int Dark, int Light) {
		this.ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		directionLine.add(0);
		directionTarpoulin.add(1);
		Motor.A.setSpeed(100);

		while (!suppressed) {
			// Line
			if (ls.getLightValue() > 1100) {
				LastTarpoulin = directionTarpoulin.size() - 1;
				if (LastTarpoulin > 0) {
					turnLine(10);
				} else {
					turnLine(-10);
				}
				// Tarpoulin
			} else if (ls.getLightValue() > 600 && ls.getLightValue() < 900) {
				LastLine = directionLine.size() - 1;
				if (LastLine > 0) {
					turnTarpaulin(10);
				} else {
					turnTarpaulin(-10);
				}
			}
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	static int LastLine, LastTarpoulin;

	public static void turnLine(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
		directionLine.add(Motor.A.getTachoCount());
	}

	public static void turnTarpaulin(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
		directionTarpoulin.add(Motor.A.getTachoCount());
	}
}