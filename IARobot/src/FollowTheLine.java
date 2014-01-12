import java.io.File;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

public class FollowTheLine implements Behavior {
	private boolean suppressed = false;
	private static LightSensor ls;

	static int lastLineDirection, lastTarpoulinDirection;
	static String specialOrder = "0000";
	// specialOrder: 0000 - nothing, 0001 - set the correction beeing on the
	// line, 0002 - do correction

	static long currentTime;
	static long startTimeLine;
	long startTimeTourplin = 0;
	static Boolean lineNotFound = false;
	int angle = 15;
	int time = 2000;

	static File pw = new File("power_up_8bit.wav");

	public FollowTheLine(SensorPort LS, int Dark, int Light) {
		FollowTheLine.ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return (ls.getLightValue() > 1100 && ls.getLightValue() < 1500);
	}

	@Override
	public void action() {
		suppressed = false;
		// Start values - start from the line
		lastLineDirection = 0;
		lastTarpoulinDirection = 1;
		Motor.A.setSpeed(200);

		while (!suppressed) {
			// Line
			if (ls.getLightValue() > 1100) {
				// Direction correction
				if (specialOrder.equals("0001")) {
					specialOrder = "0002";

				} else if (lastTarpoulinDirection > 0) {
					turnLine(-angle);
				} else {
					turnLine(2 * angle);
				}
				// Reset time beetwen lines detection
				startTimeTourplin = System.currentTimeMillis();
				// Correction
			} else if (specialOrder.equals("0002")) {
				int angle2 = angle * 2;
				correctTheDirection(angle2, time);

				boolean goBack = false;
				currentTime = System.currentTimeMillis();
				if ((currentTime - startTimeTourplin) > time) {
					while (!(ls.getLightValue() > 1100) && !goBack) {
						Motor.A.backward();
					}
				}

				if (lineNotFound) {
					correctTheDirection2(-angle2, time);
				}
				// Tarpoulin
			} else if (ls.getLightValue() > 600 && ls.getLightValue() < 900) {
				// Stert time beetwen line detecton
				if (startTimeTourplin == 0) {
					startTimeTourplin = System.currentTimeMillis();
				}
				if (lastLineDirection > 0) {
					turnTarpaulin(-angle);
				} else {
					turnTarpaulin(angle);
				}
				// After not finding the line return to the last position
				boolean goBack = false;
				currentTime = System.currentTimeMillis();
				if ((currentTime - startTimeTourplin) > 4000) {
					while (!(ls.getLightValue() > 1100) && !goBack) {
						specialOrder = "0001";
						Motor.A.backward();
					}
				}
			}
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	public static void turnLine(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
		lastLineDirection = Motor.B.getTachoCount();
	}

	public static void turnTarpaulin(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
		lastTarpoulinDirection = Motor.B.getTachoCount();
	}

	public static void correctTheDirection(int angle, int time) {
		// Escape the line
		Motor.A.forward();
		startTimeLine = System.currentTimeMillis();
		if (lastLineDirection > 0) {
			while (!((currentTime = System.currentTimeMillis() - startTimeLine) > time)
					&& !(ls.getLightValue() > 1100)) {
				Motor.A.forward();
				turnLine(angle);
				lineNotFound = true;
			}
			if (ls.getLightValue() > 1100) {
				lineNotFound = false;
			}

		} else {
			startTimeLine = System.currentTimeMillis();
			while (!((currentTime = System.currentTimeMillis() - startTimeLine) > time)
					&& !(ls.getLightValue() > 1100)) {
				Motor.A.forward();
				turnLine(-angle);
				lineNotFound = true;
			}
			if (ls.getLightValue() > 1100) {
				lineNotFound = false;
			}
		}
	}

	// Test purposes
	public static void correctTheDirection2(int angle, int time) {
		Sound.playSample(pw, 25);
		// Escape the line
		Motor.A.forward();
		startTimeLine = System.currentTimeMillis();
		if (lastLineDirection > 0) {
			while (!((currentTime = System.currentTimeMillis() - startTimeLine) > time)
					&& (ls.getLightValue() > 1100)) {
				Sound.playSample(pw, 25);
				Motor.A.forward();
				turnLine(angle);
				lineNotFound = true;
			}
			if (ls.getLightValue() > 1100) {
				lineNotFound = false;
			}

		} else {
			startTimeLine = System.currentTimeMillis();
			while (!((currentTime = System.currentTimeMillis() - startTimeLine) > time)
					&& (ls.getLightValue() > 1100)) {
				Sound.playSample(pw, 25);
				Motor.A.forward();
				turnLine(-angle);
				lineNotFound = true;
			}
			if (ls.getLightValue() > 1100) {
				lineNotFound = false;
			}
		}
	}
}