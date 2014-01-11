import java.io.File;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class FollowTheLine implements Behavior {
	private boolean suppressed = false;
	private static LightSensor ls;

	static int lastLineDirection, lastTarpoulinDirection;
	static String specialOrder = "0000";
	// specialOrder: 0000 - nothing, 0001 - correction

	static long currentTime;
	static long startTimeLine;
	long startTimeTourplin = 0;
	static Boolean lineNotFound = false;
	int angle = 15;

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
					specialOrder = "0000";

					correctTheDirection(angle);

					if (lineNotFound) {
						correctTheDirection(-angle);
						lineNotFound = false;
					}
				} else if (lastTarpoulinDirection > 0) {
					turnLine(-15);
				} else {
					turnLine(30);
				}
				// Reset time beetwen lines detection
				startTimeTourplin = System.currentTimeMillis();
				// Tarpoulin
			} else if (ls.getLightValue() > 600 && ls.getLightValue() < 900) {
				// Stert time beetwen line detecton
				if (startTimeTourplin == 0) {
					startTimeTourplin = System.currentTimeMillis();
				}
				if (lastLineDirection > 0) {
					turnTarpaulin(-15);
				} else {
					turnTarpaulin(15);
				}
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

	public static void correctTheDirection(int angle) {
		// Escape the line
		Motor.A.forward();
		Sound.playSample(pw, 25);
		startTimeLine = System.currentTimeMillis();
		if (lastLineDirection > 0) {
			while (!((currentTime = System.currentTimeMillis() - startTimeLine) > 500)
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
			while (!((currentTime = System.currentTimeMillis() - startTimeLine) > 500)
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
}