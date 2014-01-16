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
//		boolean tryRight = false;
//		boolean tryLeft = false;
		boolean fromLeft = false;
//		int bothSide;

		while ((Main.ftl || Main.level == 4) && !suppressed) {
			Main.pilot.forward();

			if (!tinyAngle(400)) {
				currentTime = System.currentTimeMillis();

				fromLeft = false;
//				bothSide =0;
				while (ls.getLightValue() < 1400) {

					Main.pilot.setRotateSpeed(45);

					 if (fromLeft) {
//					if (!tryRight) {
						Main.pilot.rotateRight();
						if (System.currentTimeMillis() - currentTime > 3000) {
//							tryRight = true;
//							bothSide++;
							// bothSide = tryLeft && tryRight;
							bothSide = true;

							Main.pilot.setRotateSpeed(90);

							Main.pilot.rotate(145);
//							currentTime = System.currentTimeMillis();
							break;

						}
//					}
					 } else {
//					if (!tryLeft && tryRight) {
						Main.pilot.rotateLeft();
						if (System.currentTimeMillis() - currentTime > 2000) {
//							tryLeft = true;
//							tryRight = false;
//							bothSide++;
							fromLeft = true;
							Main.pilot.setRotateSpeed(90);

							Main.pilot.rotate(-90);
							currentTime = System.currentTimeMillis();

						}
					}
//					if(bothSide == 2){
//						Main.ftl = false;
//						break;
//					}
				}
				
			}

			if (bothSide){
				Main.ftl = false;
				if (Main.level == 4)
					Main.level = 0;
			}
			

			Thread.yield();

		}

		

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
		while (ls.getLightValue() < 1400) {
			Main.pilot.setRotateSpeed(45);
			if (left) {

				Main.pilot.rotateRight();
				if (System.currentTimeMillis() - currentTime > 2 * time) {
					// tryRight = true;

					// bothSide = tryLeft && tryRight;

					Main.pilot.rotate((time / 1000) * 45);
					return false;
				}
			} else {
				// if (/*!tryLeft &&*/ !fromLeft) {

				Main.pilot.rotateLeft();
				if (System.currentTimeMillis() - currentTime > time) {
					// tryLeft = true;
					left = true;

					// Main.pilot.rotate(-time / 1000 * 90);
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
