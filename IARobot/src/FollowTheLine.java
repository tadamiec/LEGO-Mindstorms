import java.util.ArrayList;
import java.util.List;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;

public class FollowTheLine implements Behavior {
	private boolean suppressed = false;
	private LightSensor ls;

	static List<Integer> lastDirectionLine = new ArrayList<Integer>();
	static List<Integer> lastDirectionTarpoulin = new ArrayList<Integer>();

	static int lastLineDirection, lastTarpoulinDirection;
	long currentTime, startTime;

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
		lastDirectionLine.add(0);
		lastDirectionTarpoulin.add(1);
		Motor.A.setSpeed(360);

		while (!suppressed) {
			// Clear Arrays
			while (lastDirectionLine.size() > 5) {
				lastDirectionLine.remove(0);
			}
			while (lastDirectionTarpoulin.size() > 5) {
				lastDirectionTarpoulin.remove(0);
				
			}

			// Line
			if (ls.getLightValue() > 1100) {
				lastTarpoulinDirection = lastDirectionTarpoulin
						.get(lastDirectionTarpoulin.size() - 1);
				// Direction correction
				if (lastTarpoulinDirection == 0000) {

					if (lastLineDirection > 0) {
						currentTime = System.currentTimeMillis();
						while ((currentTime - startTime) > 1000) {
							turnLine(-20);
						}
					} else {
						turnLine(10);
					}
				} else if (lastTarpoulinDirection > 0) {
					turnLine(-20);
				} else {
					turnLine(10);
				}
				// Tarpoulin
			} else if (ls.getLightValue() > 600 && ls.getLightValue() < 900) {
				lastLineDirection = lastDirectionLine.get(lastDirectionLine
						.size() - 1);
				if (lastLineDirection > 0) {
					turnTarpaulin(-10);
				} else {
					turnTarpaulin(10);
				}
				boolean goBack = false;
				startTime = System.currentTimeMillis();
				while (!(ls.getLightValue() > 1100) || !goBack) {
					currentTime = System.currentTimeMillis();
					if ((currentTime - startTime) > 2000) {
						lineNotfound();
						goBack = true;
						lastDirectionTarpoulin.add(0000);
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
		lastDirectionLine.add(Motor.B.getTachoCount());
	}

	public static void turnTarpaulin(int angle) {
		Motor.B.rotateTo(angle);
		Motor.A.forward();
		lastDirectionTarpoulin.add(Motor.B.getTachoCount());
	}

	public static void lineNotfound() {
		Motor.A.backward();
	}
}