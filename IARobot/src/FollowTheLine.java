import java.io.File;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;

//import lejos.nxt.Sound;

public class FollowTheLine implements Behavior {
	private LightSensor ls;
	private boolean suppressed = false;

	static File pw = new File("power_up_8bit.wav");

	public FollowTheLine(SensorPort LS, int Dark, int Light) {
		ls = new LightSensor(LS);
		ls.setLow(Dark);
		ls.setHigh(Light);
	}

	@Override
	public boolean takeControl() {
		return Main.ftl || Main.level == 4;
	}

	@Override
	public void action() {
		LCD.clear();
		LCD.drawString("FollowTheLine", 0, 0);
		suppressed = false;
		long currentTime;
		boolean bothSide = false;
		// boolean tryRight = false;
		// boolean tryLeft = false;
		boolean fromLeft = false;

		while ((Main.ftl || Main.level == 4) && !suppressed) {
			Main.pilot.forward();
			currentTime = System.currentTimeMillis();
			fromLeft = false;

			if (!tinyAngle(200)) {

				while (ls.getLightValue() < 1700) {
					Main.pilot.setRotateSpeed(45);

					if (/* !tryRight && */fromLeft) {
						Main.pilot.rotateRight();
						if (System.currentTimeMillis() - currentTime > 3000) {
							// tryRight = true;

							// bothSide = tryLeft && tryRight;
							bothSide = true;

							Main.pilot.setRotateSpeed(90);

							Main.pilot.rotate(145);
							break;

						}
					} else {
						// if (/*!tryLeft &&*/ !fromLeft) {
						Main.pilot.rotateLeft();
						if (System.currentTimeMillis() - currentTime > 2000) {
							// tryLeft = true;
							fromLeft = true;
							Main.pilot.setRotateSpeed(90);

							Main.pilot.rotate(-90);
							currentTime = System.currentTimeMillis();

						}
					}
				}
			}

			if (bothSide)
				Main.ftl = false;

			Thread.yield();

		}

		if (Main.level == 4)
			Main.level = 0;

		LCD.clear();

		Main.pilot.stop();
		suppress();

	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	public boolean tinyAngle(double time) {

		long currentTime = System.currentTimeMillis();

		boolean left = false;
		while (ls.getLightValue() < 1700) {
			Main.pilot.setRotateSpeed(90);
			if (left) {
				Main.pilot.rotateRight();
				if (System.currentTimeMillis() - currentTime > time) {
					// tryRight = true;

					// bothSide = tryLeft && tryRight;

					Main.pilot.rotate((time / 1000) * 90);
					return false;
				}
			} else {
				// if (/*!tryLeft &&*/ !fromLeft) {
				Main.pilot.rotateLeft();
				if (System.currentTimeMillis() - currentTime > time) {
					// tryLeft = true;
					left = true;

					Main.pilot.rotate(-time / 1000 * 90);
					currentTime = System.currentTimeMillis();

				}
			}
		}
		return true;
	}

	// private void findStrightLine(int limitAngle) {
	// Main.pilot.rotate(limitAngle);
	//
	// if (!(ls.getLightValue() > 1400)) {
	// Main.pilot.rotate(-limitAngle);
	// }
	// }

}
